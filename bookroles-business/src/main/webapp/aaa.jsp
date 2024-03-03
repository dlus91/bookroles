<%@ page import="com.bookroles.Constant" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/9/23
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        long ms = Long.parseLong(request.getSession().getAttribute("currentMs").toString());
        Map<String, String> keyMap = Constant.getKeystoreMap(ms);
        System.out.println(keyMap);
    %>

    <%=keyMap.get("key")%>
    <%=keyMap.get("iv")%>
    <%=ms%>

</body>
</html>
