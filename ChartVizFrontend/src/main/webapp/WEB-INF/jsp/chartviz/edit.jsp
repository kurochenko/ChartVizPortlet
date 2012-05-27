<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../init.jspf" %>
<f:setBundle basename="content.chartviz"/>

<%@page import="static net.kurochenko.pv230.portlet.Constants.*" %>

<fieldset>
    <legend><spring:message code="edit.img.legend" /></legend>
<portlet:actionURL var="editFormActionURL" name="<%= CONFIG_FORM_NAME %>" />
<form:form action="${editFormActionURL}" method="post" modelAttribute="<%=CONFIG_FORM_MODEL%>">
    <form:label path="imgWidth"><spring:message code="edit.img.width" /></form:label>
    <form:input path="imgWidth" /><br />

    <form:label path="imgHeight"><spring:message code="edit.img.height" /></form:label>
    <form:input path="imgHeight" /><br />

    <input type="submit" value="<spring:message code="edit.btn.submit" />" />
</form:form>
</fieldset>


<fieldset>
    <legend><spring:message code="edit.currency.legend" /></legend>
<portlet:actionURL var="currenciesFormActionURL" name="<%= CURRENCY_FORM_NAME %>" />
<form:form action="${currenciesFormActionURL}" method="post" modelAttribute="<%=VISIBLE_CURRENCY_FORM_MODEL%>">
    <form:checkboxes path="currencies" items="${currencyWrapper.currencies}"
                     itemLabel="name" itemValue="id" delimiter="<br />" /><br />
    <input type="submit" value="<spring:message code="edit.btn.submit.currency" />" />
</form:form>
</fieldset>

