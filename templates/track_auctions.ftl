<html>
<head>
    <title>Track Auctions</title>

<#include "head.ftl">
</head>
<body>
<h1>Auctions being Tracked</h1>
<br>



<#list items as item>
    ${item.name}
</#list>

<#list auctions?keys as auction>
<form method="post" action="findItems">
    <td><p></p></td>><button class="btn btn-default" type="submit">View Auction</button>
    </tr>
    <input type="hidden" name="auction_id" value="${auction}">
</form>
</#list>




<br>
<br>

</body>
<html>