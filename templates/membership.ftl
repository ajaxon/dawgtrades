<html>
<head>
    <title>Modify Membership Price</title>
<#include "head.ftl">
</head>
<body>


<div class="container">
<div class="row">
<div class="panel panel-default">
	<div class="panel-heading">
		<h3>Set Membership Price</h3>
	</div>
	<div class="panel-body">
			<form method=post name="SetMembershipPrice" action="set_membership_price" style="margin:0;padding:0;">
			    <#if membership?has_content >
			    Membership Subscription Price:<input type="text" name="price" value="${membership.price}"><br>
			        <#else>
			            Membership Subscription Price:<input type="text" name="price" value="0"><br>
			    </#if>
			    <br><br>
			
			    <input type="submit" name="Add" value="submit">
			</form>
	</div>
		<div class="panel-footer">
					<a href="login">Back to Home</a>
				</div>
</div>
</div>
</div>
</body>
</html>