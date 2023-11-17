<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label va-middle">지역구분</label>
        <div class="col-sm-11">
            <label class="checkbox-inline c-checkbox">
                <input type="checkbox" class="checkboxAll" />
                <span class="ion-checkmark-round"></span> 전체
            </label>
            <c:forEach var="cdList" items="${cdDtlList.ED_CITY_CODE}" varStatus="status">
                <c:if test="${status.count % 10 eq 0}">
                    <br />
                </c:if>
                <label class="checkbox-inline c-checkbox <c:if test='${status.count > 9}'>mt-sm</c:if>">
                    <input type="checkbox" class="checkboxSingle" data-name="rgnsCdList" value="${cdList.cd}" />
                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                </label>
            </c:forEach>
        </div>
    </div>
</fieldset>

