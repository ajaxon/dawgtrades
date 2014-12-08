<html>
<head>
    <title><#if category?has_content> ${category.name}<#else>Categories</#if></title>
</head>
<body>

<#if category?has_content>

<h1>${category.name}</h1>
<a href="list_item?categoryID=${category.id}">List item</a><br>
<#list children as child>
    <a href="?categoryID=${child.id}">${child.name}<br>
</#list>

    <#if items?has_content>
        <table>
            <th>Listed Items</th>
            <#list items as item>
                <tr>
                    <form method="post" action="findItems">
                        <td><p>${item.name}</p></td><td> <input type="submit" value="View Auction"></td>
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
</body>
</html>