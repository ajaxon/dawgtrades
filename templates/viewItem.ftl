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
	<p>Current Bid:${auction.minPrice}</>
	<br>
	<p>Expiration:${expiration}</>
	<form method="post" action="bid_on_item">
		<inut type="hidden" value="${auction.id}">
		<input type="submit" value="Bid on Item">
	</form>


</body>
<html>