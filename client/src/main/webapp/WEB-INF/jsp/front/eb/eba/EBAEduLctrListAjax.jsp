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
                            <div class="dt f-body2">강의명</div>
                            <div class="dd f-body2">${list.nm}</div>
                        </div>
                        <div class="dl">
                            <div class="dt f-body2">강의시간</div>
                            <div class="dd f-body2"><span>${list.time}</span>분</div>
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
                            <%--<c:set var="url" value="https://img.youtube.com/vi/${fn:substring(list.url, fn:indexOf(list.url,'embed/')+6, fn:length(list.url))}/0.jpg" />--%>
                            <%--
                                썸네일 이미지 크기 조정은 맨 끝의 문구만 바꾸면 됨
                                120×90: https://i1.ytimg.com/vi/VIDEO-ID/default.jpg
                                320×180: https://i1.ytimg.com/vi/VIDEO-ID/mqdefault.jpg
                                480×360: https://i1.ytimg.com/vi/VIDEO-ID/hqdefault.jpg
                                640×480: https://i1.ytimg.com/vi/VIDEO-ID/sddefault.jpg
                                1920×1080: https://i1.ytimg.com/vi/VIDEO-ID/maxresdefault.jpg
                            --%>
                            <c:set var="url" value="https://i1.ytimg.com/vi/${fn:substring(list.url, fn:indexOf(list.url,'v=')+2, fn:length(list.url))}/default.jpg" />
                        </c:otherwise>
                    </c:choose>
                    <img src="${url}" alt="">
                </div>
            </li>
        </c:forEach>


    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>

    </c:otherwise>
</c:choose>