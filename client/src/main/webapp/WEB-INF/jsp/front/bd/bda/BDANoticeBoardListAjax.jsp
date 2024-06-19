<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
	<c:when test="${rtnData.pageIndex eq 1}">
        <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="totalCount" name="totalCount" value="${ rtnData.totalCount }" />
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <a class="list-item listView ${list.mainPostYn eq 'Y' ? 'mainPost' : 'normalPost'}" href="javascript:" title="링크 이동" data-details-key="${list.ntfySeq}" data-main-post-yn="${list.mainPostYn}">
                <div class="sub-info-wrap">
                    <c:choose>
                        <c:when test="${list.mainPostYn eq 'Y'}">
                            <span class="info f-body1">중요</span>
                        </c:when>
                        <c:otherwise>
                            <span class="num f-body2">${ rtnData.totalCount - rtnData.firstIndex - status.index + mainPostCnt }</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="txt-box">
                    <p class="tit f-head">
                        <c:if test="${list.newPostYn eq 'Y'}">
                           <span class="new-icon" aria-label="새로운 항목"></span>
                        </c:if>
                        ${list.titl}
                    </p>
                    <div class="sub-txt f-body2"><p class="date">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p><p class="view">조회수 <span>${list.readCnt}</span></p></div>
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