<html>
<head>
    <title><#if category?has_content> ${category.name}<#else>Categories</#if></title>
<#include "head.ftl">
</head>
<body>

<div class="container">
		<div class="row">
			<div class="panel panel-default">
				
<#if category?has_content>
<div class="panel-heading">
		<#if category.name?has_content>
		<h1>${category.name}</h1>
		<#else>
		<h1>Browse Categories</>
		</#if>
</div>


<div class="panel-body">



<nav class="navbar navbar-default" role="navigation">
 <div class="collapse navbar-collapse" id="">
  <ul class="nav navbar-nav navbar-left">
        <li>Subcategories</li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">View Subcategories<span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
          <#list children as child>
		    <li><a href="?categoryID=${child.id}">${child.name}</a></li>
		</#list>
		 </ul>

    </ul>
 	<ul class="nav navbar-nav navbar-right">
    <li><a href="create_item?categoryID=${category.id}">List item</a><br></li>
    </ul>
	</div>
 
</nav>






    <#if items?has_content>
        <table>
            <th>Listed Items</th>
            <#list items as item>
                <tr>

                    <form method="post" action="findItems">
                        <td><p>${item.name}</p></td><td> <button type="submit" class="btn-default">View Auction"</button></td>
                </tr>
                <input type="hidden" name="auction_id" value="${item.id}">
                </form>
            </#list>
        </table>
    </#if>


<#else>

    <#list categories as category>

        <b><a href="?categoryID=${category.id}"> ${category.name} </a></b><br>


        <#if user.isAdmin==true>
        <a href="update_category?categoryID=${category.id}">Edit</a> | <a href="delete_category?categoryID=${category.id}">Delete</a>
        </#if>
    <br>
    </#list>
</#if>
</div>
				<div class="panel-footer">
					<a href="login">Back to Home</a>
				</div>
			</div>
		</div>
</div>	

</body>
</html>