<html>
<head>
    <title>Track Auctions</title>
</head>
<body>
	<h1>The Auctions You Have Bid On</h1>
	<br>
	<#if item?has_content>
		<table>
		<th>Listed Items</th>
		<#list items as item>
			<tr>
			<form method="post" action="findItems">
			<td><p>Item: ${item.name} <br>  Your bid: ${bid.amount}  Highest bid: ${highest_bid.amount} <br>  Expiration: ${auction.expiration} </p>
			</td><td> <input type="submit" value="View Auction"></td>
			</tr>
			<input type="hidden" name="auction_id" value="${item.id}">
			</form>
		</#list>
		</table>
	<#else>
		   <p>No bids found.</p>
	</#if>
	<a href="login">Back to Index</a>
</body>
<html>