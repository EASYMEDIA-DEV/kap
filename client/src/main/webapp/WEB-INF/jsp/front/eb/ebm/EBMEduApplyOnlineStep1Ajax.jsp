<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>

        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <li class="list-item">
                <div class="txt-area">
                    <p class="num f-sub-head">${status.count}</p>
                    <div class="training">
                        <div class="dl">
                            <c:set var="urlkey" value=""/>
                            <c:choose>

                                <c:when test="${list.url.indexOf('www.youtube.com') > -1}">

                                    <c:choose>

                                        <c:when test="${list.url.indexOf('/watch?v=') > -1}">
                                            <c:set var="urlkey" value="${fn:substring(list.url, list.url.indexOf('/watch?v=')+9, fn:length(list.url))}"/>
                                        </c:when>
                                        <c:when test="${list.url.lastIndexOf('embed/') > -1}">
                                            <c:set var="urlkey" value="${fn:substring(list.url, list.url.lastIndexOf('embed/')+6, fn:length(list.url))}"/>
                                        </c:when>

                                    </c:choose>

                                </c:when>

                                <c:when test="${list.url.indexOf('youtu.be') > -1}">
                                    <c:choose>
                                        <c:when test="${list.url.indexOf('si=') > -1}">

                                            <c:set var="urlkey" value="${fn:substring(list.url, list.url.indexOf('be/')+3, list.url.indexOf('si=')-1  )   }"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="urlkey" value="${fn:substring(list.url, list.url.indexOf('be/')+3, fn:length(list.url)  )   }"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>

                            <div class="dt f-body2 urlKey" data-urlkey="${urlkey}">강의명</div>
                            <div class="dd f-body2 urlName">
                                    ${list.nm}
                            </div>
                        </div>
                        <div class="dl">
                            <div class="dt f-body2">강의시간</div>
                            <div class="dd f-body2 urlTime"><span>${list.time}</span>분</div>
                        </div>
                    </div>
                </div>

                <div class="img-area">

                    <!-- 등록된 썸네일 없으면 유튜브 썸네일 갖고옴-->
                    <c:set var="url" value="" />
                    <c:choose>
                        <c:when test="${fn:length(list.webPath) ne 0}">
                            <c:set var="url" value="${list.webPath}" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="url" value="https://img.youtube.com/vi/${urlkey}/0.jpg" />
                        </c:otherwise>
                    </c:choose>
                    <img src="${url}" alt="">

                    <!-- 수강완료 상태 -->
                    <c:choose>
                        <c:when test="${not empty list.lctrDtm}">
                            <div class="status f-caption1">수강완료</div>
                        </c:when>
                    </c:choose>

                </div>
                <div class="btn-area">
                    <div class="btn-wrap">
                        <button class="btn-text-icon black-arrow onlineStep" data-lctrSeq="${list.lctrSeq}" type="button"><span>수강하기</span></button>
                        <%--<c:choose>
                            <c:when test="${not empty list.lctrDtm}">
                                <p class="hyphen">-</p>
                            </c:when>
                            <c:otherwise>

                                <button class="btn-text-icon black-arrow onlineStep" data-lctrSeq="${list.lctrSeq}" type="button"><span>수강하기</span></button>

                            </c:otherwise>
                        </c:choose>--%>
                    </div>
                </div>
            </li>

        </c:forEach>



    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>

    </c:otherwise>
</c:choose>