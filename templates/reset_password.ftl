<html>
<title>Reset Password</title>
<head>
    <title>Reset Password</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Forgot your password?</h3>
            </div>
            <div class="panel-body">
                <h5>${message}</h5>
                <form method="post" name="ResetPassword" action="reset_password">
                    <br>Email: <input type="text" name="email" value="email">
                    <input type="submit" value="Send email">
                </form>
                <div>
                    <a href="login">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>