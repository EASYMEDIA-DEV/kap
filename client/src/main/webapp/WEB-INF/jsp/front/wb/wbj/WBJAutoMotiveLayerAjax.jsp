<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:forEach var="item" items="${rtnData.list}" varStatus="status">
    <li class="list-item" data-total-count="${rtnData.totalCount}">
        <input type="hidden" class="notRequired ctgryNm" value="${item.ctgryNm}"/>
        <div class="txt-area">
            <div class="form-checkbox no-txt num">
                <input type="checkbox" id="Chk${item.bsnmNo}" class="chk" name="chk" value="${item.bsnmNo}">
                <label for="Chk${item.bsnmNo}"></label>
            </div>
            <p class="num f-sub-head">${status.count}</p>
            <div class="training">
                <div class="dl">
                    <div class="dt f-body2 w-longest">부품사명</div>
                    <div class="dd f-body2 srchListView">${item.cmpnNm}</div>
                </div>
                <div class="dl">
                    <div class="dt f-body2 w-longest">사업자등록번호</div>
                    <div class="dd f-body2">${item.bsnmNo}</div>
                </div>
            </div>
        </div>
    </li>
</c:forEach>