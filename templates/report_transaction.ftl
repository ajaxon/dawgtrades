<html>
<head>
    <title>Leave Feedback</title>
</head>
<body>
<h1>Please, tell us about your experience</h1>
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

<input type="submit" value="Leave Feedback">
</form>

</body>
</html>