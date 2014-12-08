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
					
        			<td>${item.name}</td>
  					
						<#if itemStatus[item.name]== "no_auction" >
							<form method="get" action="item_to_auction">
							<input type="hidden" name="item_id" value="${item.id}">
							<td><input type="submit" value="Auction Now"></td>
							</form>
						<#elseif itemStatus[item.name]=="reauction" >
							<form method="get" action="item_to_auction">
							<input type="hidden" name="item_id" value="${item.id}">
							<td><input type="submit" value="Reauction"></td>
							</form>
						<#elseif itemStatus[item.name]=="sold">
							<td>Item has sold</td>
						<#elseif itemStatus[item.name]=="in_auction">
							<td>Item is currently in auction</td>
						</#if>
					
					
					<#if itemStatus[item.name]== "no_auction" ||  itemStatus[item.name]== "reauction">
					<form method="get" action="delete_item">
     							<input type="hidden" name="item_id" value="${item.id}">
     						<td><input type="submit" value="Delete Item"></td>
     				</form>
     				</#if>
     				<#if itemStatus[item.name]== "sold">
					<form method="get" action="report_transaction">
     							<input type="hidden" name="item_id" value="${item.id}">
     						<td><input type="submit" value="Leave Feedback"></td>
     				</form>	
     			    </#if>
     				
     		
     		</tr>
		</#list>
		</table>
    <#else>
		<p>No items in inventory to auction</p>
	</#if>
	<a href="login">Back to Index</a>

</body>
</html>