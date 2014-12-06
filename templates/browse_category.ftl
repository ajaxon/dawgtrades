<html>
<head>
    <title>Categories</title>
</head>
<body>



    <#list categories as category>

        <b>${category.name}</b>
        <#if user.isAdmin==true>
        <a href="update_category?categoryID=${category.id}">Edit</a> | <a href="delete_category?categoryID=${category.id}">Delete</a>
        </#if>
    <br>
    </#list>

</body>
</html>