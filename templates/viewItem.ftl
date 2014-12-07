<html>
<head>
    <title>View Item</title>
</head>
<body>
	<h1>${item.name}</h1>
	<br>
	<h3>${item.identifier}</h3>	
	
	<p>Description:</p>
	<div>${item.description}</div>
	<br>
	<p>Current Bid:${currentBid?string.currency}</>
	<br>
	<p>Expiration:${expiration}</>
	
	<#if owned==true>
		<p>Because you are the owner of this item, bidding is currently disabled.</p>
	<#elseif highestBidder==true>
		<p>You are the current highest bidder of this item</p>
	<#else>
	<form method="get" action="bid_on_item">
		<input type="hidden" value="${auction.id}" name="auction_id">
		<input type="submit" value="Bid on Item">
	</form>
	</#if>
	<br>
	<br>
	<form>
		<button formmethod="get" formaction="findItems" type="submit">Return to Auctions</button>
	</form>

</body>
<html>