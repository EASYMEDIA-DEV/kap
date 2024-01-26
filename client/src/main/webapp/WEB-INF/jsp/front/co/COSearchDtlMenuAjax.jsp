<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${menuCnt > 0}">
        <div class="menu-depth-info" id="menuDepthInfo">
            <c:forEach var="list" items="${tabMenuList}" varStatus="status">
                <a class="menu-link" id="menuLink" href="${list.userUrl}">
                    <c:set var="menuNmList" value="${fn:split(list.menuNm, '__')}"/>
                    <c:forEach var="menuNm" items="${menuNmList}"
                               varStatus="status">
                        <p class="menu f-head">${menuNm}</p>
                    </c:forEach>
                </a>
            </c:forEach>
            <br>
        </div>
    </c:when>
    <c:otherwise>
        <div class="no-data-area">
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>
