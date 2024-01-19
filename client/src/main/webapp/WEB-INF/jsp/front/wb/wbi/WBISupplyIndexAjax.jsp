<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:forEach var="item" items="${rtnData.list}" varStatus="status">
    <c:choose>
        <c:when test="${item.dateOrd eq 1}">
            <c:set var="classTag" value="accepting"/>
            <c:set var="applyButton" value="<a class='btn-solid small black-bg apply' href='javascript:'><span>신청하기</span></a>"/>
        </c:when>
        <c:when test="${item.dateOrd eq 2}">
            <c:set var="classTag" value="waiting"/>
            <c:set var="applyButton" value="<a class='btn-solid small black-bg disabled' href='javascript:'><span>${item.acctStatus}</span></a>"/>
        </c:when>
        <c:when test="${item.dateOrd eq 3}">
            <c:set var="classTag" value="end"/>
            <c:set var="applyButton" value="<a class='btn-solid small black-bg disabled' href='javascript:'><span>${item.acctStatus}</span></a>"/>
        </c:when>
    </c:choose>
    <div class="list-item <c:if test="${item.dateOrd eq 1}">available accepting</c:if>"><!-- available: 신청 가능한 회차 --><!-- accepting: 접수중 -->
        <p class="available-label">
            <span>신청 가능한 회차</span>
        </p>
        <div class="cont">
            <button class="top-area" type="button">
                <div class="left">
                    <div class="group">
                        <p class="index-num f-title3">${item.year} ${item.episd}차</p>
                        <div class="status-info-w">
                            <p class="box-label bigger ${classTag}"><span>${item.acctStatus}</span></p>
                            <c:if test="${item.dateOrd eq 1}">
                                <p class="box-label bigger"><span>D-${item.dday}</span></p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </button>
            <div class="cont-area">
                <div class="info-list-w">
                    <div class="info-list">
                        <p class="tit f-caption2">접수기간</p>
                        <p class="txt f-body2">
                                ${kl:convertDate(item.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}
                                    ~
                                    <br class="only-pc"/>
                                ${kl:convertDate(item.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}
                        </p>
                    </div>
                </div>
                <div class="btn-wrap">
                    <div class="btn-set">
                            ${applyButton}
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>