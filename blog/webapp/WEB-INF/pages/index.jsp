<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta charset="utf-8" />
<link type="text/css" rel="stylesheet" href="/stylesheet/index.css" />
<script type="text/template" id="post-card">

</script>
<div id="bg"></div>
<div class="post-list">
</div>
<div class="nav">
	<div class="plus"></div>
	<div class="prev"></div>
	<div class="next"></div>
</div>
<div id="shadow"></div>
<!--<div id="curtain"></div>-->

<script src="/scripts/lib/jquery.js"></script>
<script src="/scripts/lib/blur.js"></script> 
<script src="/scripts/lib/rebound.min.js"></script> 
<script src="/scripts/lib/CAGE.ajax.js"></script>
<script src="/scripts/lib/CAGE.util.js"></script>
<!-- <script src="/scripts/lib/Underscore.js"></script> -->
<script src="/scripts/APP/app.js"></script>
<script>
var app = new App();

document.body.setAttribute("style", "background-image: url(/static/img/"+(Math.floor(Math.random()*8)+1)+".jpg)");		
/*
$('#curtain').blurjs({
    radius: 4
});
*/
</script>