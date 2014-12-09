<html>
<head>
    <title>Track Auctions</title>

<#include "head.ftl">
</head>
<body>
<h1>Auctions being Tracked</h1>
<br>





<#list auctions?keys as auction>
<p>${auction.minBid}:</p><p></p>
</#list>




<br>
<br>

</body>
<html>