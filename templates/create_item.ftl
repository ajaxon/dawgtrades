<html>
<head>
	<title>Create An Item to be Auction</title>
<#include "head.ftl">
<head>
<body>


<form method="post" name="item" >
	<h1>Create an item to be auctioned</h1>
	<br>
	<label>Name of Item</label><input type="text" name="name">
	<br>
	<label>Enter in Catchy Identifier</label><br><input type="text" name="identifier">
	<br>
	<label>Enter in description</label><br><textarea name="description" cols="30" rows="4"></textarea>
	<br>
	<#if attribute_types?has_content>
		<#list attribute_types as attribute_type >
		<label>${attribute_type.name}</label><input type="text" name="${attribute_type.id}">

		</#list>
	</#if>
	<input type="hidden" name="category_id" value="${category.id}">
   	<br>
	<input formaction="create_item" type="submit" name="createItem" value="Save Item, Auction Later">
	<input formaction="createAndAuction" type="submit" name="createAndSaveItem" value="Auction Now">
</form>

<a href="login">Back to Index</a>
</body>
</html>