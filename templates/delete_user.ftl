<html>
<head>
    <title>Pick a user</title>
<#include "head.ftl">
</head>
<body>
<div class="container">
	<div class="row">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>Delete A Registered User</h2>
			</div>
			<div class="panel-body">
				    <form method="post" name="DeleteUser" action="delete_user">
				        Users: <select name="user_id">
				        <option value="0" selected>None</option>
				        <#list users as user>
			            <option name="user_id" value="${user.id}">${user.name}</option>
			        </#list>
			        </select>
			        <button type="submit" class="btn btn-default"> Delete User </button>
			        </form>
			</div>
			<br>
			<div class="panel-footer">
			<a href="login">Back to Home</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>