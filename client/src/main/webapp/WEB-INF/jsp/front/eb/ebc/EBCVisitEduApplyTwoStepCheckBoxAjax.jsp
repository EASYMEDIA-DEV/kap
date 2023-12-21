<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:if test="${cdName eq 'EBC_VISIT_CD01002'}">
    <div class="inner-line">
        <div class="agree-box">
            <div class="gray-bg-sec narrow-pad">
                <p class="f-body2">
                    (공통)
                    <br/>업종 기초기술, 품질관리, 공정관리 개선, 레이아웃 개선, 설비관리 개선, 생산기술 개선, 3정 5행, 작업조건 관리, 개선 사례, (IT/전산) 전산화 현황 진단, 스마트공장 구축 지원사례
                </p>
            </div>
        </div>
    </div>
</c:if>
<div class="col-sm-11">
    <c:forEach var="cdList" items="${cdDtlList}" varStatus="status">
       <div class="form-checkbox">
            <input type="checkbox" id="checkboxSingle notRequired ${cdList.cd}" data-name="appctnTypeCdList" value="${cdList.cd}" name="appctnType">
            <label for="${cdList.cd}">${cdList.cdNm}</label>
       </div>
    </c:forEach>
</div>