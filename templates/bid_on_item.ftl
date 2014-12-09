<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
</head>
<body>

<div class="container">
	<div class="row" style="margin-top:"20px">
			<div class="panel panel-default">
				<div class="panel-heading">	
						<h2>You are currently placing a bid on ${item.name}</h2>
				</div>
				<div class="panel-body">
					<div class="row">		
							<h4>${message}</h4>
						<br>
					
						<p>The current price of the item is ${currentBid?string.currency}</p>
						<br>
						<form role="form" class="form-inline" method="post" action="bid_on_item">
							<div class="form-group">
							<label>Please enter your bid:</label><input class="form-control" type="text" name="bid">
							</div>
							<input type="hidden" value="${auction.id}" name="auction_id">
							<button type="submit" class="btn btn-success">Submit Bid</button>
						</form>
						
						<br>
						<br>
					</div>	
				</div>
				<div class="panel-footer">
					<form method="post" action="findItems">
							<input type="hidden" value="${auction.id}" name="auction_id">
							<button type="submit" class="btn btn-default">Back To Auction Details</button>
						</form>
				</div>
			</div>
	</div>
<div>
</body>
</html>