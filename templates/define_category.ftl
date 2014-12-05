<html>
<head>
    <title>Add Category</title>
</head>
<body>


<form method=post name="Category" action="add_category" style="margin:0;padding:0;">
    <input type="text" name="name" value="">

    <select name="parent_id">
        <option value="None" selected>None</option>
    <#list categories as category>
        <option value="${category.id}">${category.name}</option>
    </#list>
    </select>



    <#-- Attribute types for category-->
    <input type="text" name="attr_name1" value="">
    <input type="text" name="attr_name2" value="">
    <input type="submit" name="Add" value="submit">
</form>
</body>
</html>