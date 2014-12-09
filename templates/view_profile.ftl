<html>
<head>
    <title>View Profile</title>
<#include "head.ftl">
</head>
<body>

<div class="container">
	<div class="row top-buffer">
	<div class="panel panel-default">
	<div class="panel-heading">
    <h4>View Profile</h4>
	</div>
	
	<div class="panel-body">
	<#if message?has_content><p style="color:green">${message}</p><br></#if>
	<br>
    <form role="form" method="post" action="view_profile">
    
    	  <div class="form-group">
    	<label for="username">Username</label>	
       <input type="text" class="form-control" name="username" value="${user.name}" id="username">
		</div>
		
		  <div class="form-group">
    	<label for="password">Password</label>
        <input type="text" class="form-control" name="password" id="password" value="${user.password}">
		</div>

		  <div class="form-group">
    	<label for="firstName">First Name</label>
       <input type="text" class="form-control" name="firstName" id="firstName" value="${user.firstName}">
		</div>

		  <div class="form-group">
    	<label for="lastName">Last Name</label>
      <input type="text" class="form-control" name="lastName" id="lastName" value="${user.lastName}">
		</div>

		  <div class="form-group">
    	<label for="email">Email address</label>
       <input type="text" class="form-control" name="email" id="email" value="${user.email}">
		</div>

		  <div class="form-group">
   		 <label for="phoneNumber">Phone Number</label>
        <input type="text" class="form-control" name="phone" id="phoneNumber" value="${user.phone}">
		</div>	
	

		   <div class="checkbox">
						    <label>
						      <input type="checkbox" name="canText" <#if canText==true>checked </#if>> 
						      Can Text:
						   </label>
						  </div>

        
        <button type="submit" class="btn btn-success"> Update</button>
    </form>

	</div>
	<div class="panel-footer">
    <form action="login">
        <button type="submit" class="btn btn-default">Back To Index </button>
    </form>
    </div>
	</div>
	
	</div>
</div>
</body>
</html>