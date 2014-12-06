<html>
<head>
    <title>Add Category</title>
</head>
<body>


<form method=post name="Category" action="define_category" style="margin:0;padding:0;">
    <input type="text" name="name" value="">

    <select name="parent_id">
        <option value="0" selected>None</option>
    <#list categories as category>
        <option value="${category.id}">${category.name}</option>
    </#list>
    </select>

<br><br>
    <h3>Attribute Types</h3>
    <#-- Attribute types for category-->
    Attribute Type<input type="text" name="attr_name1" value=""><br>
    Attribute Type<input type="text" name="attr_name2" value=""><br>
    <input type="submit" name="Add" value="submit">
</form>
</body>
</html>