<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
</head>
<body>
	<h2>You are currently placing a bid on ${item.name}</h2>
		
		<h4>${message}</h4>
	<br>
	<p>The current price of the item is ${currentBid?string.currency}</p>
	<br>
	<form method="post" action="bid_on_item">
		<label>Please enter your bid</label><input type="text" name="bid">
		<input type="hidden" value="${auction.id}" name="auction_id">
		<input type="submit" value="Submit Bid">
	</form>
	
	<br>
	<br>
	<form method="post" action="findItems">
		<input type="hidden" value="${auction.id}" name="auction_id">
		<input type="submit" value="Back to Auction Details">
	</form>


</body>
</html>