<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">구분</label>
        <div class="col-sm-5">
            <label class="checkbox-inline c-checkbox">
                <input type="checkbox" class="checkboxAll" />
                <span class="ion-checkmark-round"></span> 전체
            </label>
            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                <c:if test="${fn:contains(cdList, 'COMPANY010')}">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" value="${cdList.cd}" />
                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                    </label>
                </c:if>
            </c:forEach>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">규모</label>
        <div class="col-sm-6">
            <label class="checkbox-inline c-checkbox">
                <input type="checkbox" class="checkboxAll" />
                <span class="ion-checkmark-round"></span> 전체
            </label>
            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                <c:if test="${fn:contains(cdList, 'COMPANY020')}">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" class="checkboxSingle" data-name="sizeCdList" value="${cdList.cd}" />
                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                    </label>
                </c:if>
            </c:forEach>
        </div>
    </div>
</fieldset>

