<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.vitalykhan.webapps.model.ContactType" %>
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
    <title>${resume.fullName}</title>
    <br>
    <br>
    <button class="key" onclick="window.history.back()">Назад</button>
    <button class="key" onclick="location.href='serv?uuid=${resume.uuid}&action=edit'">Редактировать</button>
    <h3>Имя, фамилия: ${resume.fullName}</h3>
    <c:forEach items="${resume.contactsMap}" var="contactEntry">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.vitalykhan.webapps.model.ContactType, java.lang.String>"/>
        <b><%=contactEntry.getKey().getTitle()%>: </b>
        <%=HtmlUtil.getFullReference(contactEntry.getKey(), contactEntry.getValue())%>
        <br>
    </c:forEach>
    <hr>
    <c:forEach items="${resume.sectionsMap}" var="sectionEntry">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.vitalykhan.webapps.model.SectionType,
                                                com.vitalykhan.webapps.model.Section>"/>
        <b><%=sectionEntry.getKey().getTitle()%>: </b>
<%--        --%>
        <dl>
            <c:forEach items="<%=HtmlUtil.sectionToHtmlList(sectionEntry.getValue())%>" var="entry">
                <dd>${entry}</dd>
            </c:forEach>
        </dl>
        <br>
    </c:forEach>
    <button class="key" onclick="window.history.back()">Назад</button>

</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>