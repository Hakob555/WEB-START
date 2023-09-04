package com.smartcode.web.repository.cooment.impl;

import com.smartcode.web.model.Comment;
import com.smartcode.web.repository.cooment.CommentRepository;
import com.smartcode.web.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryimpl implements CommentRepository {

    private Connection connection = DataSource.getInstance().getConnection();

    public CommentRepositoryimpl() {
        try {
            connection.createStatement().execute("""
                    CREATE TABLE IF NOT EXISTS comments (
                        id serial primary key ,
                        title varchar(255) not null ,
                        description varchar(255) not null 
                    );
                    """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void create(Comment comment) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO comments (title,description) values(?,?)");
            preparedStatement.setString(1, comment.getTitle());
            preparedStatement.setString(2, comment.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Integer commentId, Comment comment) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE comments SET title,description = ? WHERE comment_id = ?");
            preparedStatement.setInt(1, commentId);
            preparedStatement.setString(2, comment.getTitle());
            preparedStatement.setString(3, comment.getDescription());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Comment> getAll(Integer userID) {
        List<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE id = ?");

            preparedStatement.setInt(1, userID);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int commentId = result.getInt(userID);
                String commentTitle = result.getString("commentTitle");
                String commentDescription = result.getString("commentDescription");

                Comment comment = new Comment(commentId, commentTitle, commentDescription);

                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }


    @Override
    public Comment getById(Integer commentId) {
        Comment comment = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE id = ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(commentId);
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                comment = new Comment(commentId, title, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public void delete(Integer commentId) {
        PreparedStatement preparedStatement= null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setInt(1,commentId);
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
            preparedStatement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }
}