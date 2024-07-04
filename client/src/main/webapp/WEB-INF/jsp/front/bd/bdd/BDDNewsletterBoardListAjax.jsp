<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include/el.jspf" %>

<c:choose>
	<c:when test="${rtnData.pageIndex eq 1}">
        <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="9" />
        <input type="hidden" id="totalCount" name="totalCount" value="${ rtnData.totalCount }" />
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${ not empty rtnData.list }">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <a class="list-item listView" href="javascript:" title="링크 이동" data-details-key="${list.nwsltSeq}">
                <div class="img-box">
                    <c:if test="${ not empty list.webPath }">
                        <img src="${ list.webPath }" alt="${ list.fileDsc }">
                    </c:if>
                </div>
                <div class="txt-box">
                    <p class="tit f-head">
                        <c:if test="${list.newPostYn eq 'Y'}">
                            <span class="new-icon" aria-label="새로운 항목"></span>
                        </c:if>
                            ${list.titl}
                    </p>
                    <span class="sub-txt f-body2">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</span>
                </div>
            </a>
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
