<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../init.jspf" %>
<f:setBundle basename="content.chartviz"/>

<%@page import="static net.kurochenko.pv230.portlet.Constants.*" %>

<portlet:actionURL var="editFormActionURL" name="<%= CONFIG_FORM_NAME %>" />
<form:form action="${editFormActionURL}" method="post" modelAttribute="<%=CONFIG_FORM_MODEL%>">
    <form:label path="imgWidth"><spring:message code="edit.img.width" /></form:label>
    <form:input path="imgWidth" /><br />

    <form:label path="imgHeight"><spring:message code="edit.img.height" /></form:label>
    <form:input path="imgHeight" /><br />

    <input type="submit" value="<spring:message code="edit.btn.submit" />" />
</form:form>


<%--<portlet:actionURL var="currenciesFormActionURL" name="<%= CURRENCY_FORM_NAME %>" />--%>
<%--<form:form action="${currenciesFormActionURL}" method="post" modelAttribute="<%=CURRENCY_FORM_MODEL%>">--%>
    <%--<form:checkboxes path="currencies" items="${currencies}" itemLabel="name" itemValue="visible" />--%>
<%--</form:form>--%>

