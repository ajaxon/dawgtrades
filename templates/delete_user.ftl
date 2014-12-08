<html>
<head>
    <title>Pick a user</title>
</head>
<body>
    <form method="get" name="DeleteUser" action="delete_user">
        Users: <select name="userID">
        <option value="0" selected>None</option>
        <#list users as user>
            <option value="${user.id}">${user.name}</option>
        </#list>
        </select>
<br>
<a href="login">Back to Home</a>
</body>
</html>