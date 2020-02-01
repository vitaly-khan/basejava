<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.vitalykhan.webapps.model.ContactType" %>
<%@ page import="com.vitalykhan.webapps.model.SectionType" %>
<%@ page import="com.vitalykhan.webapps.utils.HtmlUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.vitalykhan.webapps.model.Resume" scope="request"/>
</head>
<body>
<section>

    <h2>${resume.uuid == null ? "Создание нового " : "Редактирование"} резюме</h2>
    <form method="post" action="serv" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <table>
            <tr>
                <td>Имя:</td>
                <td><input type="text" name="fullName" size=50 value="${resume.fullName}"></td>
            </tr>
        </table>
        <br>
        <table>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <tr>
                    <td>${type.title}</td>
                    <td><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <br>
        <table>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <tr>
                    <jsp:useBean id="type" type="com.vitalykhan.webapps.model.SectionType"/>
                    <td>
                            ${type.title}
                        <c:if test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATIONS ||
                                        type==SectionType.LANGUAGES}">
                            <span class="colortext">*</span>
                        </c:if>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE || type==SectionType.HOBBIES}">
                                <dd><input type="text" name="${type.name()}" size=108
                                           value="<%=HtmlUtil.getContent(type, resume)%>">
                                </dd>
                            </c:when>
                            <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATIONS}">
                                <dd><textarea name="${type.name()}" cols=99
                                              rows=5><%=HtmlUtil.getItems(type, resume)%></textarea>
                                </dd>
                            </c:when>
                            <c:when test="${type==SectionType.LANGUAGES}">
                                <dd><textarea name="${type.name()}" cols=99
                                              rows=3><%=HtmlUtil.getItems(type, resume)%></textarea>
                                </dd>
                            </c:when>
                            <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                                <dd><span class="colortext">(will be implemented in future versions)</span>
                                </dd>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <span class="colortext">* Используйте Enter для разбиения на пункты</span>

        <hr>
        <button class="key" type="submit">Сохранить</button>
        <button class="key" type="reset" onclick="window.history.go(-1)">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>