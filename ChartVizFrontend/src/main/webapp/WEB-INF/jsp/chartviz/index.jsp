<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../init.jspf" %>
<f:setBundle basename="content.chartviz"/>

<%@page import="static net.kurochenko.pv230.portlet.ChartVizPortletConstants.*" %>
<%@page import="static net.kurochenko.pv230.portlet.TimeRange.*" %>
<%@ page import="net.kurochenko.pv230.portlet.TimeRange" %>

<spring:message code="index.h1"/> :-)

<c:set var="timeRanges" value="<%=TimeRange.values()%>" />
<ul>
    <c:forEach items="${timeRanges}" var="timeRange">
        <portlet:renderURL var="graphRangeURL">
            <portlet:param name= "<%= TIME_RANGE_PARAM %>" value="${timeRange.name}"/>
        </portlet:renderURL>
        <li><a href="${graphRangeURL}"><spring:message code="${timeRange.msgBundleCode}" /></a></li>
    </c:forEach>
</ul>

<c:if test="${!empty plot}">
    ${plot}
</c:if>

Size: ${listSize}
