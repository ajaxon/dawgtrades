<html>
<head>
    <title>Pick a user</title>
<#include "head.ftl">
</head>
<body>
    <form method="post" name="DeleteUser" action="delete_user">
        Users: <select name="user_id">
        <option value="0" selected>None</option>
        <#list users as user>
            <option name="user_id" value="${user.id}">${user.name}</option>
        </#list>
        </select>
        <input type="submit" value="Delete User">
        </form>
<br>
<a href="login">Back to Home</a>
</body>
</html>