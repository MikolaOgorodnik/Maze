<#import "macro/common.ftl" as c>

<@c.page "${appname}">
    <div>
        Hello, user ${username} <br>
        DateTime: ${datetime} <br>
    </div>
    <div id="box" style="width: 328px; visibility: visible;">
        <div id="maze">
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
        </div>
    </div>
</@c.page>