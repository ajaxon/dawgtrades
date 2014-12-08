<html>
<head>
    <title>Pay Membership</title>
</head>
<body>

<form method=post name="PayMembership" action="pay_membership" style="margin:0;padding:0;">
    The price of membership is ${membership.price}.
    If you would like to pay your dues, please enter your payment information below:

    First Name:<input type="text" name="firstName" value=""/><br>
    Last Name:<input type="text" name="lastName" value=""/><br>
    Credit card number:<input type="number" name="card_number" value=""/><br>
    Security code:<input type="number" name="security_code" value=""/><br>
    Billing street:<input type="text" name="address_line1" value=""/><br>
    Billing city: <input type="text" name="address_line2" value=""/><br>
    <input type="submit" name="Pay" value="submit" return confirm('Thanks!')" />


    </form>
</body>
</html>