<html>
<head>
    <title>Update Category ${category.name}</title>
<#include "head.ftl">
</head>
<body>


<form method=post name="Category" action="update_category" style="margin:0;padding:0;">
    <input type="hidden" name="id" value="${category.id}">
    Name:<input type="text" name="name" value="${category.name}"><br>

    <select name="parent_id">

            <option value="${parent_id}" selected>${parent_name}</option>

    <#list categories as category>
        <option value="${category.id}">${category.name}</option>
    </#list>
    </select>

    <br><br>
    <h3>Attribute Types</h3>
<#-- Attribute types for category-->
    <#list attribute_types as attr>
        Attribute Type<input type="text" name="attr_name${attr_index+1}" value="${attr.name}"><br>
        <#assign count = attr_index+1>
    </#list>
    <div id="addAttributeType">Add another Attribute Type</div>

    <input type="submit" name="Add" value="submit">
</form>
</body>
</html>