<html>
<head>
    <title>Search Items for Auction</title>
</head>
<body>
	<h1>Search Items for Auction</h1>
	<#if items?has_content>	
		<#list items as item>

			<p>${item.name}</p>

		</#list>
	<#else>
		   <p>No items found</p>
	</#if>
	<a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/login">Back to Index</a>
</body>
<html>