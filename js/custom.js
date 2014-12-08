$(document).ready(function() {


    var count = $()
    $("#addAttributeType").click(
        function () {
            var someText = "my dynamic text";
            var newDiv = "<input type=\"text\" name=\"attr_name${attr_index+1}\" value=\"${attr.name}\"><br>";
            $(this).append(newDiv);
        }
    )


});