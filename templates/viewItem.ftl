<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
</head>
<body>

<div class="container">
<div class="row">
	<div class="panel panel-default">
		<div class="panel-heading">		
						<h1>${item.name}</h1><smaller>${item.identifier}</smaller>	
		</div>
		<div class="panel-body">
					<br>
					
					
					<h3>Description:</h3>
					<div>${item.description}</div>
					<br>
					
					<p>Current Bid:${currentBid?string.currency}</>
					<br>
					<p style="color:green">Expiration:${expiration}</>
					<br>
					<br>
					<h4>Descriptors</h4>
					<#list attributeAndType?keys as attributeType>
						<p><span style="font:bold">${attributeType}: </span> ${attributeAndType[attributeType]}</p>
					</#list>
					
					<#if owned==true>
						<p>Because you are the owner of this item, bidding is currently disabled.</p>
					<#elseif highestBidder==true>
						<p>You are the current highest bidder of this item</p>
					<#else>
					<form method="get" action="bid_on_item">
						<input type="hidden" value="${auction.id}" name="auction_id">
						<button type="submit" class="btn btn-default">Bid on Item</button>
					</form>
					</#if>
					<br>
					<br>
		</div>
	</div>
</div>
</div>
</body>
<html>