<html>
<head>
	<title>Auction My Items</title>
<#include "head.ftl">
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
  					<#list itemStatus?keys as status>
						<#if itemStatus[item.name]== "no_auction" >
							<td><input type="submit" value="Auction Now"></td>
						<#elseif itemStatus[item.name]=="reauction" >
							<td><input type="submit" value="Reauction"></td>
						<#elseif itemStatus[item.name]=="sold">
							<td>Item has sold</td>
						<#elseif itemStatus[item.name]=="in_auction">
							<td>Item is currently in auction</td>
						</#if>
					</#list>
					
					
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