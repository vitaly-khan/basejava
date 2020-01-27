<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Resumes List</title>

</head>
<body>
<br><br>
<%@ page import="com.vitalykhan.webapps.model.ContactType" %>
<section>
    <table cellspacing="10" cellpadding="5">
        <tr>
            <th>Имя</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
<%--            <jsp:useBean id="resume" type="com.vitalykhan.webapps.model.Resume"/>--%>
            <tr>
                <td><a href="resumes?uuid=${resume.uuid}">${resume.fullName}
                </a></td>
                <td>${resume.getContact(ContactType.EMAIL)}
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
