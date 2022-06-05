<#import "macro/common.ftl" as c>

<@c.page "${appname}">
    <form>
        <label for="width" class="form-label">Width</label>
        <input type="range" class="form-range" min="3" max="45" id="width">

        <label for="height" class="form-label">Height</label>
        <input type="range" class="form-range" min="3" max="45" id="height">

        <button type="submit" class="btn btn-primary">Generate</button>
    </form>
</@c.page>