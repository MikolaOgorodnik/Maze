<#import "macro/common.ftl" as c>

<@c.page>
    <form action="/generate" method="post">
        <input name="height" value="${height}" class="form-control form-control-sm" type="text" placeholder="${height}"
               aria-label=".form-control-sm example">
        <br>
        <input name="width" value="${width}" class="form-control form-control-sm" type="text" placeholder="${width}"
               aria-label=".form-control-sm example">
        <br>
        <button type="submit" class="btn btn-primary">Generate</button>
    </form>
</@c.page>