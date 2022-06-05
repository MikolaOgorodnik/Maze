<#import "macro/common.ftl" as c>

<@c.page "${appname}">
    <div>
        Hello, user ${username} <br>
        DateTime: ${datetime} <br>
    </div>
    <div>
        <#if isMazeExists??>
           <pre>
            ${maze}
           </pre>
        <#else>
            Maze is empty
        </#if>
    </div>
</@c.page>