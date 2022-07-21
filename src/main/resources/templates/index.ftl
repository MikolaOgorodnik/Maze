<#import "macro/common.ftl" as c>

<@c.page>
    <div>
        Hello, user ${username} <br>
        DateTime: ${datetime} <br>
    </div>
<#--<div id="box" class="col-lg-12 col-md-6 col-md-offset-6" style=" visibility: visible;">-->
    <main class="col-md-8 col-lg-9">
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
    </main>
<#--</div>-->
</@c.page>