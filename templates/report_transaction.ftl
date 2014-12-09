<html>
<head>
    <title>Leave Feedback</title>
    <#include "head.ftl">
    
</head>
<body>
<div class="container">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>Leave Some Feedback</h4>	
					</div>
			<div class="panel-body">

						<br>
						<br>
						<h3>${message}</h3>
					<br>
					<br>
					<form method="post" action="report_transaction" >
					<label>Select A Rating:</label><br>
					<input type="radio" name="rating" value="5">5 - Outstanding<br>
					<input type="radio" name="rating" value="4">4 - Good<br>
					<input type="radio" name="rating" value="3">3 - Average<br>
					<input type="radio" name="rating" value="2">2 - Poor<br>
					<input type="radio" name="rating" value="1">1 - Terrible<br>
					<br>
					<label>Please explain your experience with the user</label>
					<br>
					<textarea name="description" cols="30" rows="4"></textarea>
					<br>
					<br>
					<input type="hidden" name="reviewer" value="${reviewer}">
					<input type="hidden" name="reviewee" value="${reviewee}">
					
					<button type="submit" class="btn btn-default">Leave Feedback</button>
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