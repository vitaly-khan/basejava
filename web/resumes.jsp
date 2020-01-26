<%--
  Created by IntelliJ IDEA.
  User: V
  Date: 026 26.01.20
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>--%>

<html>
<head>
    <title>Resumes List</title>
</head>
<body>
<br><br>
<%@ page import="com.vitalykhan.webapps.Config" %>
<%@ page import="com.vitalykhan.webapps.model.Resume" %>
<%@ page import="java.util.List" %>
<p>
    <% List<Resume> resumeList = Config.get().getStorage().getAllSorted();
        for (Resume resume : resumeList) {
            out.println("UUID: \t" + resume.getUuid());
            out.println("Full Name: \t" + resume.getUuid());
            out.println("=============================");
        };
    %>
    <% out.println(new java.util.Date());%>
</p>
</body>
</html>
