<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/co/COGCntsViewCtrl">
    <c:choose>
        <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/location/content' }">
            <div class="sub-top-vis-area map-page"><!-- map-page: 오시는 길 -->
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
                </div>
                <div class="map-area">
                    <div class="map-div" id="locationMap" style="height: 500px;">

                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
                </div>

                <jsp:include page="/WEB-INF/jsp/front/co/COGCntsInclude.jsp" />

            </div>
        </c:otherwise>
    </c:choose>
    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <%-- content 시작 --%>
            ${rtnData.cnts}
        <%-- content 끝 --%>

        <%-- js 시작 --%>
            <c:if test="${not empty rtnData.jsCnts}">
                <script type="text/javascript">
                    ${rtnData.jsCnts}
                </script>
            </c:if>
        <%-- js 끝 --%>
    </div>
</div>