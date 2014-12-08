<html>
<head>
    <title>My Auctions</title>
</head>
<body>
	<br>
	<h3>List of your auctions</h3>
	<#if items?has_content>
		<table>
		<#list items as item>
			<tr>
			<form method="post" action="view_my_auctions">
			<td><p>${item.name}</p></td><td> <input type="submit" value="View Auction"></td>
			</tr>
			<input type="hidden" name="auction_id" value="${item.id}">
			</form>
		</#list>
		</table>
	<#else>
		   <p>You have no current auctions.</p>
	</#if>
	<a href="login">Back to Index</a>
</body>
<html>