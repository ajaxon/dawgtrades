<html>
<head>
    <title>Search Items for Auction</title>
</head>
<body>
	<h1>Search Items for Auction</h1>
	<br>
	<label>Search By Category</labe>
	<form method="get" name="category" action="findItems">
		<select name="category">
        	<option value="-1" selected>Search All</option>
   		 <#list categories as category>
        	<option value="${category.id}">${category.name}</option>
    	</#list>
    	</select>
    	<br>
    	<input type="submit" value="Search">
    </form>
    	
	<br>
	<h3>Auctions Listed for Sale</h3>
	<#if items?has_content>	
		<table>
		<th>Listed Items</th>
		<#list items as item>
			<tr>
			<form method="post" action="findItems">
			<td><p>${item.name}</p></td><td> <input type="submit" value="View Auction"></td>
			</tr>
			<input type="hidden" name="auction_id" value="${item.id}">
			</form>
		</#list>
		</table>
	<#else>
		   <p>No auctions found</p>
	</#if>
	<a href="login">Back to Index</a>
</body>
<html>