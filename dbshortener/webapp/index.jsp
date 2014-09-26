<html>
<body>
	<form action="/url/insert" method="GET">
		Long URL: <input type="text" name="longUrl" size="100" /> <input
			type="submit" value="Get Short !" />

	</form>
	</p>
	<%
        if (session.getAttribute("shortUrl") != null) {
    %>
	Hi, your short url is:
	<br />
	<br />
	<%=session.getAttribute("shortUrl")%>

	<%
        }
    %>
</body>
</html>
