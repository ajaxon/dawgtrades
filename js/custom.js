$(document).ready(function() {


    var x = $("#count").html();
    var count = parseInt(x);
    if(count==0){
        count = 1;
    }else{
        count+=1;
    }
    $("#addAttributeType").click(
        function () {

            var newDiv = "Attribute Type:"+"<input type=\"text\" " + "name=\"attr_name"+count+ "\"" + " value=\" \">";
            $("#attributeTypes").append(newDiv);
            count++;
        }
    )


});