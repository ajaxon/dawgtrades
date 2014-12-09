<html>
<head>
    <title>My Auctions</title>
<#include "head.ftl">
</head>
<body>
<div class="container">
<div class="row">
	<div class="panel panel-default">
		<div class="panel-heading">	
				<h3>List of your auctions</h3>
		</div>
		<div class="panel-body">
				<#if items?has_content>			
				<div class="table">
					<table class="table table-bordered">
					<th>Auctions</th>
					<#list items as item>
						<tr>
						<form method="post" action="findItems">
						<td><p>${item.name}</p></td><td><button class="btn btn-default" type="submit">View Auction</button>
						</tr>
						<input type="hidden" name="auction_id" value="${item.id}">
						</form>
					</#list>
					</table>
				</div>
				<#else>
					   <p>You have no current auctions.</p>
				</#if>
		</div>
		<div class="panel-footer">
				<a href="login">Back to Index</a>
		</div>
	</div>
</div>
</div>
</body>
<html>