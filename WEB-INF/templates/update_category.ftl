<html>
<head>
    <title>Update Category ${name}</title>
</head>
<body>

<h1>Update Category</h1><br>
<form method=post name="Category" action="update_category" style="margin:0;padding:0;">
    <input type="text" name="name" value="${name}">
    <input type="text" name="parentID" value="${parent_id}">

<#-- Attribute types for category-->
<#list attribute_types as type>
    <input type="text" name="" value="type">
</#list>

    <input type="submit" name="Add" value="submit">
</form>
</body>
</html>