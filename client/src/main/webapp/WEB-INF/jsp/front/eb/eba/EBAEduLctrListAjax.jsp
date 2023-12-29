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
                            <c:set var="url" value="https://img.youtube.com/vi/${fn:substring(list.url, fn:indexOf(list.url,'embed/')+6, fn:length(list.url))}/0.jpg" />
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