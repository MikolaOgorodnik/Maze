<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Maze Generator & Solver</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/maze.css" rel="stylesheet">
        <script src="/static/js/bootstrap.bundle.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>
    </head>
    <body>
    <#include "navigationbar.ftl">
    <div class="container mt-4">
        <#nested>
    </div>

    <!--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js"
            integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk"
            crossorigin="anonymous"></script> -->
    </body>
    </html>
</#macro>