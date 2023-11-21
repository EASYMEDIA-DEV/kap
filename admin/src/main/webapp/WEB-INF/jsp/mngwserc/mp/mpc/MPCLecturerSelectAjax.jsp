<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<select class="form-control input-sm" id="mngBsnCd" name="mngBsnCd" title="2차 선택" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
    <option value="">2차 선택</option>
    <c:forEach var="cdList" items="${cdTwoDtlList.LEC_CD}" varStatus="status">
        <option value="${cdList.cd}" <c:if test="${rtnDto.mngBsnCd eq cdList.cd}">selected</c:if>>
                ${cdList.cdNm}
        </option>
    </c:forEach>
</select>