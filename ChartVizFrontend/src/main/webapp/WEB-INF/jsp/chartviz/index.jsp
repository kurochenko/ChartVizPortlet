<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../init.jspf" %>
<f:setBundle basename="content.chartviz"/>

<%@page import="static net.kurochenko.pv230.portlet.Constants.*" %>
<%@ page import="net.kurochenko.pv230.backend.util.TimeRange" %>

<c:set var="timeRanges" value="<%=TimeRange.values()%>" />
<p class="chartVizP"><spring:message code="time.range" />:</p><ul class="chartVizUl">
    <c:forEach items="${timeRanges}" var="timeRange">
        <li>
            <portlet:renderURL var="graphRangeURL">
                <portlet:param name= "<%= TIME_RANGE_PARAM %>" value="${timeRange.name}"/>
            </portlet:renderURL>
            <a href="${graphRangeURL}"><spring:message code="${timeRange.msgBundleCode}" /></a>
        </li>
    </c:forEach>
</ul>
<br />

<p class="chartVizP"><spring:message code="currency" />:</p><ul class="chartVizUl">
    <c:forEach items="${currencies}" var="currency">
        <li>
            <portlet:renderURL var="currencyURL">
                <portlet:param name= "<%= CURRENCY_VAL_PARAM %>" value="${currency.name}"/>
            </portlet:renderURL>
            <a href="${currencyURL}">${currency.name}</a>
        </li>
    </c:forEach>
</ul>
<br />

<div class="chartVizClear">&nbsp;</div>
<portlet:resourceURL var="plot" escapeXml="false" id="<%=PLOT_RESOURCE_VAL%>" />
<img src="${plot}" alt="<spring:message code="img.plot.alt" arguments="${actualCurrency.name}"/>" usemap="#<%=IMAGEMAP_NAME%>" class="plotImage" />

${imagemap}

<script type="text/javascript">
    $("#<%=IMAGEMAP_NAME%> *").tooltip({
        track: true,
        delay: 0,
        showURL: false,
        showBody: " - ",
        fade: 250
    });
</script>

