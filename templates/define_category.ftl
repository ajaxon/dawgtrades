<html>
<head>
    <title>Add Category</title>
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
			<form method=post name="Category" action="define_category" style="margin:0;padding:0;">
			    Category Name: <input type="text" name="name" value=""><br>
			
			   Parent: <select name="parent_id">
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
			</div>
	<div class="panel-footer">
					<a href="login">Back to Home</a>
				</div>
			</div>
		</div>
</div>
</body>
</html>