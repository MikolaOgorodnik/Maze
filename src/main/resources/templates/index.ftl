<#import "macro/common.ftl" as c>

<@c.page>
<#--<div>
    Hello, user ${username} <br>
    DateTime: ${datetime} <br>
</div>-->
    <#if isMazeExists??>
        <#list grid as row>
            <div class="mrow">
                <#list row as col>
                    <div class="${col.getBorderClass()}"></div>
                </#list>
            </div>
        </#list>
    <#else>
        Maze is empty
    </#if>
<#--</div>-->
</@c.page>