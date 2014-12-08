<html>
<head>
    <title>Pick a user</title>
</head>
<body>
    <form method="get" name="DeleteUser" action="delete_user">
        Users: <select name="user_id">
        <option value="0" selected>None</option>
        <#list users as user>
            <option name="userID" value="${user.id}">${user.name}</option>
        </#list>
        </select>
<br>
<a href="login">Back to Home</a>
</body>
</html>