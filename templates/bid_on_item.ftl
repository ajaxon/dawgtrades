<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
</head>
<body>

<div class="container">
	<div class="row">
			<div class="panel panel-default">
				<div class="panel-header">	
						<h2>You are currently placing a bid on ${item.name}</h2>
				</div>
				<div class="panel-body">
					<div class="row">		
							<h4>${message}</h4>
						<br>
					</div>
						<p>The current price of the item is ${currentBid?string.currency}</p>
						<br>
						<form role="form" class="form-inline" method="post" action="bid_on_item">
							<div class="form-group">
							<label>Please enter your bid</label><input class="form-control"  type="text" name="bid">
							</div>
							<div class="form-group>
							<input type="hidden" value="${auction.id}" name="auction_id">
							</div>
							<button type="submit" class="btn btn-success">Submit Bid</button>
						</form>
						
						<br>
						<br>
						<form method="post" action="findItems">
							<input type="hidden" value="${auction.id}" name="auction_id">
							<input type="submit" value="Back to Auction Details">
						</form>
				</div>
				<div class="panel-footer">
				</div>
			</div>
	</div>
<div>
</body>
</html>