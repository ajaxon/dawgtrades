<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
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
	
	<#list attributeAndType?keys as attributeType>
		<p>${attributeType}:</p><p>${attributeAndType[attributeType]}</p>
	</#list>
	
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
	
</body>
<html>