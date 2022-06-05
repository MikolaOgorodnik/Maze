<#import "macro/common.ftl" as c>

<@c.page "${appname}">
    <form>
        <label for="width" class="form-label">Width</label>
        <input type="range" class="form-range" min="3" max="45" id="width" onInput="$('#xval').html($(this).val())">
        <span id="xval">21<!-- Default value --></span>
        <br>
        <label for="height" class="form-label">Height</label>
        <input type="range" class="form-range" min="3" max="45" id="height" onInput="$('#yval').html($(this).val())">
        <span id="yval">21<!-- Default value --></span>

        <button type="submit" class="btn btn-primary">Generate</button>
    </form>
</@c.page>