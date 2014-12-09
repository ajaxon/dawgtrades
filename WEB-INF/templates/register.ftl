<html>
<head>
    <title>Register</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
</head>
<body>

<div class="container">
	<div class="row top-buffer">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Register Today</h3>
		</div>
		<div class="panel-body">
		
				<form role="form" method="post" name="Register" action="register" style="margin:0;padding:0;">
					<div class="form-group">
    				<label for="name">
				    Name: </label><input class="form-control" type="text" name="name" id="name">
				    </div>
				    
				    <div class="form-group">
    				<label for="firstName">
				    First Name:</label><input class="form-control" type="text" name="firstName" id="firstName">
				    </div>
				    
				    <div class="form-group">
    				<label for="lastName">
				    Last Name:</label><input class="form-control" type="text" name="lastName" id="lastName">
				    </div>
				    <div class="form-group">
    				<label for="password">
				    Password:</label><input class="form-control" type="password" name="password" id="password">
				    </div>
				    
				    <div class="form-group">
    				<label for="email">
				    Email:</label><input class="form-control" type="text" name="email" id="email">
				    <p class="help-block">Please ensure a valid email.</p>
				    </div>
				    
				    
				    <div class="checkbox">
						    <label>Can Receive Text Message </label>
						      <input type="checkbox" name="canText"> 
						   
						  </div>
				    
				    
				    
				    <div class="form-group">
    				<label for="phone">
				    Phone:</label> <input class="form-control" type="text" name="phone" id="phone">
				    </div>
				    
				    <button type="submit" class="btn btn-default" name="Register">Register</button>
				
				    </form>
		</div>
	</div>
	</div>
</div>
				    
</body>
</html>