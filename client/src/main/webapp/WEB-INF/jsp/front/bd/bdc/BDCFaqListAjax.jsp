<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <div class="list-item">
                <a class="acco-click-area" href="javascript:">
                    <div class="sub-info-wrap">
                        <span class="f-body2">${list.ctgryName}</span>
                    </div>
                    <div class="txt-box">
                        <p class="tit f-title3">${list.titl}</p>
                    </div>
                </a>
                <div class="acco-hide-area">
                    <p class="txt f-sub-head">
                            ${list.cntn}
                    </p>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="no-data-area">
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>

