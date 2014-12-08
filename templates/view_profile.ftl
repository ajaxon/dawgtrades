<html>
<head>
    <title>View Profile</title>
<#include "head.ftl">
</head>
<body>
<#if message?has_content>${message}<br></#if>


    <h4>View Profile</h4>
    <form method="post" action="view_profile">
        <br>Name: <input type="text" name="username" value="${user.name}">
        <br>Password: <input type="text" name="password" value="${user.password}">
        <br>First Name: <input type="text" name="firstName" value="${user.firstName}">
        <br>Last Name: <input type="text" name="lastName" value="${user.lastName}">
        <br>Email: <input type="text" name="email" value="${user.email}">
        <br>Phone: <input type="text" name="phone" value="${user.phone}">
        <br>Text: <input type="checkbox" name="canText" value="" <#if canText==true>checked </#if>>
        <br>
        <input type="submit" value="Update">
    </form>
    <form action="login">
        <input type="submit" value="Back to Index">
    </form>
</body>
</html>