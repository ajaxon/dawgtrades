<html>
<head>
	<title>Create An Item to be Auction</title>
<head>
<body>


<form method="post" name="item" action="create_item">
	<h1>Create an item to be auctioned</h1>
	<br>
	<label>Name of Item</label><input type="text" name="name">
	<br>
	<label>Enter in Catchy Identifier</label><br><input type="text" name="identifier">
	<br>
	<label>Enter in description</label><br><textarea name="description" cols="30" rows="4"></textarea>
	<br>
	<select name="category">
        <!-- <option value="-1" selected>Search All</option> --!>
   		 <#list categories as category>
        	<option value="${category.id}">${category.name}</option>
    	</#list>
    	</select>
   	<br>
	<input type="submit" name="createItem" value="Post Item">
</form>

<a href="login">Back to Index</a>
</body>
</html>