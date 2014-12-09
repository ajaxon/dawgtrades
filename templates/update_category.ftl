<html>
<head>
    <title>Update Category ${category.name}</title>
<#include "head.ftl">
</head>
<body>
<div class="container">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1>Your item has been added to the current auction list</h1>			
					</div>
			<div class="panel-body">

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
				    <#assign count=0>
				<#-- Attribute types for category-->
				    <div id="attributeTypes">
				    <#list attribute_types as attr>
				        Attribute Type: <input type="text" name="attr_name${attr_index+1}" value="${attr.name}"><br>
				        <#assign count = attr_index+1>
				    </#list>
				    </div>
				    <div id="count">${count}</div>
				    <div id="addAttributeType">Add another Attribute Type</div>
				
				    <input type="submit" name="Add" value="submit">
				</form>
		</div>
	<div class="panel-footer">
					<a href="login">Back to Home</a>
				</div>
			</div>
		</div>
</div>
</body>
</html>