<html>
<head>
    <title>Welcome To DawgTrades</title>
</head>
<body>
<h1>Hello ${user.firstName}, Welcome To the DawgTrades Auction System</h1>
<br>
<span>1.</span><a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/createCategory">View Auctions</a>
<br>
<#if user.isAdmin==true>
<span>2.</span><a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/createCategory">Create Categories</a>
<br>
<span>3.</span><a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/setMembershipPrice">Set Membership Price</a>
<br>
<span>4.</span><a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/printReport">Print Report</a>
<br>
<span>5.</span><a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/deleteUser">Delete User</a>
<br>
</#if>

<br>
<br>
<br>
<a href="http://uml.cs.uga.edu:8080/team2_dawgtrades/logout">Logout</a>

<br>
</body>
</html>