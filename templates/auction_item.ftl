<html>
<head>
	<title>Auction My Items</title>
<#include "head.ftl">
</head>
<body>
	<div class="container">
		<div clas="row">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1>Auction Items from Your Inventory</h1>
				</div>
				
				<div class="panel-body">	
						<br>
						<#if items?has_content>	
						<div class="table">
							<table class="table table-bordered">
							<th>Item</th><th>Status</th><th>Manage Item</th>
							
					   		 <#list items as item>
								
								<br>
								<tr>
										
					        			<td>${item.name}</td>
					  					
											<#if itemStatus[item.name]== "no_auction" >
												<td>
												<form method="get" action="item_to_auction">
												<input type="hidden" name="item_id" value="${item.id}">
												<input type="submit" value="Auction Now">
												</form>
												</td>
											<#elseif itemStatus[item.name]=="reauction" >
												<td>
												<form method="get" action="item_to_auction">
												<input type="hidden" name="item_id" value="${item.id}">
												<td><input type="submit" value="Reauction"></td>
												</form>
												</td>
											<#elseif itemStatus[item.name]=="sold">
												<td>Item has sold</td>
											<#elseif itemStatus[item.name]=="in_auction">
												<td>Item is currently in auction</td>
											<#else>
												<td></td>
											</#if>
										
										<td>
										<#if itemStatus[item.name]== "no_auction" ||  itemStatus[item.name]== "reauction">
										<form method="get" action="delete_item">
					     							<input type="hidden" name="item_id" value="${item.id}">
					     						<input type="submit" value="Delete Item">
					     				</form>
					     				</#if>
					     				<#if itemStatus[item.name]== "sold">
										<form method="get" action="report_transaction">
					     							<input type="hidden" name="item_id" value="${item.id}">
					     						<input type="submit" value="Leave Feedback">
					     				</form>	
					     			    </#if>
										</td>
					     				
					     		
					     		</tr>
							</#list>
							</table>
						</div>
					    <#else>
							<p>No items in inventory to auction</p>
						</#if>
				
				</div>
				<div class="panel-footer">
						<a href="login">Back to Index</a>
				</div>
		</div>
	</div>
</body>
</html>