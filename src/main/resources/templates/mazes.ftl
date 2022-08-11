<#import "macro/common.ftl" as c>

<@c.page>
    <#if mazesList??>
        <ul class="list-group">
            <#list mazesList as row>
                <li class="list-group-item">
                    <a href="index/${row?index}" class="stretched-link">${row?index}</a>
                    <span>&nbsp;: &nbsp;</span>
                    <span>Maze #${row?index}</span>
                </li>
            </#list>
        </ul>
    <#else>
        Mazes List is empty
    </#if>
</@c.page>