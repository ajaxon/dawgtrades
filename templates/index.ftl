<html>
<head>
    <title>Welcome To DawgTrades</title>
<#include "head.ftl">
</head>
<body>
<div class="container top-buffer">
	<div class="row">
		<div class="panel panel-default"> 
			<div class="panel-heading">
				<h1>Hello ${user.firstName}, Welcome To the DawgTrades Auction System</h1>
			</div>
			<div class="panel-body">
						<br>
						<#if message?has_content>${message}</#if><br>
						
						
						<ul class="nav nav-pills nav-stacked">
 							 <li role="presentation"><a href="browse_category">1.Browse Auctions By Categories</a></li>
  							<li role="presentation"><a href="view_profile"> 2.View Profile</a></li>
  							<li role="presentation"><a href="auction_item">3.View Item Inventory</a></li>
  							<li role="presentation"><a href="view_my_auctions">4.View My Auctions</a></li>
  						<#if user.isAdmin==true>
  							 <li role="presentation"><a href="define_category">5.Create Categories</a></li>
  							 <li role="presentation"><a href="set_membership_price">6.Set Membership Price</a></li>
  							  <li role="presentation"><a href="delete_user">7.Delete A User</a></li>
  			  				<li role="presentation"><a href="print_report">8.Print Report</a></li>
  			
  							 
  							 
  							
  						</#if>
						</ul>
						
					
						
						
				
			</div>
			<div class="panel-footer">
				<form method="get" action="logout">
				<button type="submit" class="btn btn-default">Logout</button>
				</form>		
				
			</div>
		</div>				
	</div>					
</div>
	</body>
</html>