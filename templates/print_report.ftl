<html>
<head>
    <title>Report of System</title>
    <#include "head.ftl">
</head>
<body>
<div class="container">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>Dawg Trades Current Report</h4>	
					</div>
			<div class="panel-body">
    <h4>System Information</h4>
        <br>DawgTrades users: ${users}
        <br>Total of membership fee revenue: ${revenue}
        <br>Completed auctions: ${comauctions}
        <br>Total value of completed auctions: ${auctionsvalue}
        <br>Ongoing auctions: ${ongauctions}
        <br>Admins: ${admins}
        <br>

	</div> 
	<div class="panel-footer">
					<a href="login">Back to Home</a>
				</div>
			</div>
		</div>
</div>
</body>
</html>