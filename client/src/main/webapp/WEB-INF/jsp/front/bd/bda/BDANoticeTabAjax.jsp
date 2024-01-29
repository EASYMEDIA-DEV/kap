<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="board-list">
    <c:choose>
        <c:when test="${ not empty rtnData.list}">
            <div class="article-list-w txt-list" id="infoCard">
                <c:forEach var="mainPostList" items="${mainPostData.mainPostList}" varStatus="status1">
                    <a class="list-item noticeListView mainPost open" href="javascript:" title="링크 이동" data-details-key="${mainPostList.ntfySeq}">
                        <div class="sub-info-wrap">
                            <span class="info f-body1">중요</span>
                        </div>
                        <div class="txt-box">
                            <p class="tit f-head">${mainPostList.titl}</p>
                            <div class="sub-txt f-body2"><p class="date">${ kl:convertDate(mainPostList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p><p class="view">조회수 <span>${mainPostList.readCnt}</span></p></div>
                        </div>
                    </a>
                </c:forEach>
                <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                    <a class="list-item noticeListView normalPost open" href="javascript:" title="링크 이동" data-details-key="${list.ntfySeq}">
                        <div class="sub-info-wrap">
                            <span class="num f-body2">${ rtnData.totalCount - rtnData.firstIndex - status.index }</span>
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
</div>