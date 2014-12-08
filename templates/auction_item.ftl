<html>
<head>
	<title>Auction My Items</title>
</head>
<body>

<h1>Auction Items from Your Inventory</h1>


	<br>
	<#if items?has_content>	
		
		<table>
		<th>Item</th>
		
   		 <#list items as item>
			
			<br>
			<tr>
			<form method="get" action="item_to_auction">
			<input type="hidden" name="item_id" value="${item.id}">
        	<td>${item.name}</td>
			<td><input type="submit" value="Auction Now"></td>
     		</form>
     		<form method="get" action="delete_item">
     		<input type="hidden" name="item_id" value="${item.id}">
     		<td><input type="submit" value="Delete Item"></td>
     		</form>
     		<tr>
		</#list>
		</table>
    <#else>
		<p>No items in inventory to auction</p>
	</#if>
	<a href="login">Back to Index</a>

</body>
</html>