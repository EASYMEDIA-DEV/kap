<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData}">



        <c:set var="lctrList" value="${rtnData.list}"/>

        <c:set var="previousIndexSeq" value=""/>
        <c:set var="previousItem" value=""/>
        <c:set var="nextIndexSeq" value=""/>
        <c:set var="nextItem" value=""/>

        <c:forEach var="list" items="${lctrList}" varStatus="loopStatus">

            <c:set var="currentIndexSeq" value="${loopStatus.index}" />

            <!-- 앞의 항목 -->
            <c:if test="${loopStatus.index > 0}">
                <c:set var="previousIndexSeq" value="${loopStatus.index - 1}" />

                <c:if test="${nowLctrSeq eq list.lctrSeq}">
                    <c:set var="previousItem" value="${lctrList[previousIndexSeq]}" />
                </c:if>
                <!-- 이제 previousIndexSeq, previousItem을 사용할 수 있습니다 -->
            </c:if>



            <!-- 현재 항목 -->
            <!-- currentIndexSeq, item을 사용할 수 있습니다 -->

            <!-- 뒤의 항목 -->
            <c:if test="${loopStatus.index < fn:length(lctrList) - 1}">
                <c:set var="nextIndexSeq" value="${loopStatus.index + 1}" />
                <c:if test="${nowLctrSeq eq list.lctrSeq}">
                    <c:set var="nextItem" value="${lctrList[nextIndexSeq]}" />
                </c:if>
                <!-- 이제 nextIndexSeq, nextItem을 사용할 수 있습니다 -->
            </c:if>
        </c:forEach>


        <c:forEach var="list" items="${lctrList}" >
            <c:if test="${nowLctrSeq eq list.lctrSeq}">

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



                <c:set var="url" value="https://www.youtube.com/embed/${urlkey}?&autoplay=1&loop=0&modestbranding=1&rel=0" />
                <div class="iframe-area">
                    <iframe width="100%" height="100%" src="${url}" title="" allowfullscreen></iframe>
                </div>

                <div class="page-bot-btn-sec reverse-order"><!-- 2023-12-06 reverse-order 클래스 추가 -->
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg onlineStep1" href="javascript:"><span>목록</span></a><!-- 2023-12-06 텍스트 수정 -->

                        </div>
                        <div class="btn-set">
                            <c:if test="${not empty previousItem}">
                                <a class="btn-solid small gray-bg btnPrevStep" href="javascript:" data-prevSeq="${previousItem.lctrSeq}"><span>이전 강의</span></a><!-- 2023-12-06 버튼 위치 이동 -->
                            </c:if>

                            <c:if test="${not empty nextItem}">
                                <a class="btn-solid small black-bg btnNextStep" href="javascript:"  data-nextSeq="${nextItem.lctrSeq}"><span>다음 강의</span></a>
                            </c:if>

                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>




    </c:when>
    <c:otherwise>


    </c:otherwise>
</c:choose>