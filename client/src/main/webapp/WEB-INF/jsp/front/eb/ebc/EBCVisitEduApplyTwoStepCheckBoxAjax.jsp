<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="opt-group">
    <c:set var="cdName" value="${cdName}" />
    <c:forEach var="cdList" items="${cdDtlList}" varStatus="status">
        <div class="form-checkbox">
            <input type="checkbox" id="${cdList.cd}" name="appctnTypeCdList" data-name="appctnTypeCdList" value="${cdList.cd}" name="appctnType">
            <label for="${cdList.cd}">${cdList.cdNm}</label>
        </div>
    </c:forEach>
</div>
