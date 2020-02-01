<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.vitalykhan.webapps.model.ContactType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Resumes List</title>
</head>
<body>
<section>
    <h2>Список резюме</h2>
    <button class="key" onclick="location.href='serv?action=new'">Создать</button>
    <br>
    <br>
    <table cellspacing="15" cellpadding="5">
        <tr>
            <th>Имя</th>
            <th>Телефон</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.vitalykhan.webapps.model.Resume"/>
            <tr>
                <td><a href="serv?uuid=${resume.uuid}&action=view" title="Открыть резюме ${resume.fullName}">${resume.fullName}</a></td>
                <td>
                    <c:set var="phone" value="${resume.getContact(ContactType.PHONE_NUMBER)}"/>
                    <c:if test="${phone==null}">
                        <span class="colortext">не указан</span>
                    </c:if>
                    <c:if test="${phone!=null}">${phone}</c:if>
                </td>
                <td>
                    <c:set var="email" value="${resume.getContact(ContactType.EMAIL)}"/>
                    <c:if test="${email==null}">
                        <span class="colortext">не указан</span>
                    </c:if>
                    <c:if test="${email!=null}">${email}</c:if>
                </td>
                <td></td>
                <td>
                    <button class="key" onclick="location.href='serv?uuid=${resume.uuid}&action=edit'">
                        Редактировать
                    </button>
                </td>
                <td>
                    <button class="key" onclick="location.href='serv?uuid=${resume.uuid}&action=delete'">
                        Удалить
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
