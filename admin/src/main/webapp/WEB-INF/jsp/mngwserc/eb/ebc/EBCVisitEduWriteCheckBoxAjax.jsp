<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:if test="${cdName eq 'EBC_VISIT_CD01002'}">
    <div class="col-sm-12 card mt-sm">
        <div class="col-sm-10 card-body">
            (공통) <br>
            업종 기초기술, 품질관리, 공정관리 개선, 레이아웃 개선, 설비관리 개선, 생산기술 개선, <br>
            3정 5행, 작업조건 관리, 개선 사례, (IT/전산) 전산화 현황 진단, 스마트공장 구축 지원사례
        </div>
    </div>
</c:if>
<div class="col-sm-11">
    <c:if test="${not empty cdDtlList}">
        <label class="checkbox-inline c-checkbox">
            <input type="checkbox" class="checkboxAll appctnTypeAll notRequired" name="appctnTypeAll" />
            <span class="ion-checkmark-round"></span> 전체
        </label>
    </c:if>
    <c:forEach var="cdList" items="${cdDtlList}" varStatus="status">
        <label class="checkbox-inline c-checkbox">
            <c:set var="tempChk" value="N" />
            <c:forEach var="appctnTypeList" items="${appctnTypeList}">
                <c:if test="${tempChk eq 'N'}">
                    <c:if test="${appctnTypeList.cd eq cdList.cd}">
                        <c:set var="tempChk" value="Y" />
                    </c:if>
                    <c:if test="${appctnTypeList.cd ne cdList.cd}">
                        <c:set var="tempChk" value="N" />
                    </c:if>
                </c:if>
            </c:forEach>
            <input type="checkbox" class="checkboxSingle notRequired" data-name="appctnTypeCdList" value="${cdList.cd}" name="appctnType" <c:if test="${tempChk eq 'Y'}">checked</c:if> />
            <span class="ion-checkmark-round"></span> ${cdList.cdNm}
        </label>
    </c:forEach>
</div>
