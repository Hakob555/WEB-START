<%@ page import="com.smartcode.web.repository.cooment.CommentRepository" %>
<%@ page import="com.smartcode.web.repository.cooment.impl.CommentRepositoryimpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.smartcode.web.model.Comment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>

<%
    String username = (String) request.getSession().getAttribute("username");
    if (username == null) {
        request.setAttribute("message", "Login first!!!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>


<h2>Welcome dear <%=request.getSession().getAttribute("username")%>
</h2>
<table>
    <tr>
        <th>Comment title</th>
        <th>Description</th>
    </tr>
    <%
        CommentRepository commentRepository = new CommentRepositoryimpl();
        List<Comment> all = commentRepository.getAll(1);

        for (Comment comment : all) {
    %>
    <tr>

        <td><%= comment.getTitle()%></td>
        <td><%= comment.getDescription()%></td>

    </tr>
    <%
    }
%>



    </tr>
</table>

</body>
</html>