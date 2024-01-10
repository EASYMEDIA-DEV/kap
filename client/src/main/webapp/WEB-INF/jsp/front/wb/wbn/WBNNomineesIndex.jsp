<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbj/WBJAutoMotiveCtrl ">
    <c:set var="FCWinData" value="${FuCarData.winnerlist}"/>
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-san-reward-awarded.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-san-reward-awarded-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="tab-con-w scroll-motion">
                    <div class="for-motion">
                        <!-- 탭 버튼 -->
                        <div class="tab-btn-area">
                            <div class="txt-tab-swiper func-tab">
                                <div class="swiper-container">
                                    <div class="swiper-wrapper">
                                        <a class="swiper-slide txt-tab-btn active" href="javascript:">
                                            <p class="txt"><span class="menu-name">자동차부품산업대상</span></p>
                                        </a>
                                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                                            <p class="txt"><span class="menu-name">미래차공모전</span></p>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 탭 컨텐츠 -->
                        <div class="tab-con-box">
                            <div class="tab-con">
                                <c:forEach var="uniqueYear" items="${rtnData.list}" varStatus="yearStatus">
                                    <c:if test="${yearStatus.first or uniqueYear.year ne rtnData.list[yearStatus.index - 1].year}">
                                        <div class="cont-sec">
                                            <div class="sec-tit-area">
                                                <p class="f-title3">${uniqueYear.year}</p>
                                            </div>
                                            <div class="sec-con-area">
                                                <div class="table-sec">
                                                    <div class="table-box need-scroll">
                                                        <table class="basic-table">
                                                            <caption>신청자 기본 정보</caption>
                                                            <colgroup>
                                                                <col style="width: 193rem;">
                                                                <col style="width: 180rem;">
                                                                <col style="width: 180rem;">
                                                                <col style="width: 180rem;">
                                                                <col style="width: 180rem;">
                                                                <col style="width: 180rem;">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>훈격</th>
                                                                <th>포상부문</th>
                                                                <th>이름</th>
                                                                <th>소속</th>
                                                                <th>직급</th>
                                                                <th>근속년수</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="item" items="${rtnData.list}" varStatus="itemStatus">
                                                                    <c:if test="${uniqueYear.year eq item.year}">
                                                                        <tr>
                                                                            <td class="t-align-center">${item.mrtsCd}</td>
                                                                            <td class="t-align-center">${item.prizeCd}</td>
                                                                            <td class="t-align-center">${item.name}</td>
                                                                            <td class="t-align-center">${item.cmpnSeq}</td>
                                                                            <td class="t-align-center">${item.pstnNm}</td>
                                                                            <td class="t-align-center">${item.yrssvYearCnt}</td>
                                                                        </tr>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>

                            <div class="tab-con">
                                <c:forEach var="FcYear" items="${FCWinData}" varStatus="outerStatus">
                                    <c:if test="${outerStatus.first or FcYear.year ne FCWinData[outerStatus.index - 1].year}">
                                        <div class="cont-sec">
                                            <div class="sec-tit-area">
                                                <p class="f-title3 ">${FcYear.year}</p>
                                            </div>
                                            <div class="sec-con-area">
                                                <div class="table-sec">
                                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                                        <table class="basic-table">
                                                            <caption>신청자 기본 정보</caption>
                                                            <colgroup>
                                                                <col style="width: 140rem;">
                                                                <col style="width: 120rem;">
                                                                <col style="width: 140rem;">
                                                                <col style="width: 188rem;">
                                                                <col style="width: 140rem;">
                                                                <col style="width: 365rem;">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>시상부문</th>
                                                                <th>참여구분</th>
                                                                <th>이름</th>
                                                                <th>학교</th>
                                                                <th>주제</th>
                                                                <th>세부 내용</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="item" items="${FCWinData}" varStatus="index" >
                                                                <c:if test="${FcYear.year eq item.year}">
                                                                    <c:set var="partName" value='${fn:split(item.partName, ",")}'/>
                                                                    <c:set var="partSchlNm" value='${fn:split(item.partSchlNm, ",")}'/>
                                                                    <c:choose>
                                                                        <c:when test="${item.ptcptType eq '팀'}">
                                                                            <tr>
                                                                                <td rowspan="${fn:length(partName)+1}" class="t-align-center">${item.wdcrmCd}</td>
                                                                                <td rowspan="${fn:length(partName)+1}" class="t-align-center">${item.ptcptType}</td>
                                                                                <td class="t-align-center">${item.rdName}</td>
                                                                                <c:choose>
                                                                                    <c:when test="${item.rdSchlNm eq partSchlNm[0] && item.rdSchlNm eq partSchlNm[1]}">
                                                                                        <td rowspan="3" class="t-align-center">${item.rdSchlNm}</td>
                                                                                    </c:when>
                                                                                    <c:when test="${item.rdSchlNm eq partSchlNm[0] || item.rdSchlNm eq partSchlNm[1]}">
                                                                                        <td rowspan="2" class="t-align-center">${item.rdSchlNm}</td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td rowspan="1" class="t-align-center">${item.rdSchlNm}</td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                                <td rowspan="${fn:length(partName)+1}" class="t-align-center">${item.themeCd}</td>
                                                                                <td rowspan="${fn:length(partName)+1}" class="t-align-center">${item.dtlCntn}</td>
                                                                            </tr>
                                                                            <c:forEach items="${partName}" varStatus="partStatus">
                                                                                <c:choose>
                                                                                    <c:when test="${item.rdSchlNm eq partSchlNm[0] && item.rdSchlNm eq partSchlNm[1]}">
                                                                                        <tr>
                                                                                            <td class="t-align-center bdr" >${partName[partStatus.index]}</td>
                                                                                        </tr>
                                                                                    </c:when>
                                                                                    <c:when test="${item.rdSchlNm eq partSchlNm[0]}">
                                                                                        <tr>
                                                                                            <td class="t-align-center bdr" >${partName[partStatus.index]}</td>
                                                                                            <td class="t-align-center bdr partSchlNm" >${partSchlNm[partStatus.index]}</td>
                                                                                        </tr>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <tr>
                                                                                            <td class="t-align-center" >${partName[partStatus.index]}</td>
                                                                                            <td class="t-align-center bdr partSchlNm" >${partSchlNm[partStatus.index]}</td>
                                                                                        </tr>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td class="t-align-center">${item.wdcrmCd}</td>
                                                                            <td class="t-align-center">${item.ptcptType}</td>
                                                                            <td class="t-align-center">${item.rdName}</td>
                                                                            <td class="t-align-center">${item.rdSchlNm}</td>
                                                                            <td class="t-align-center">${item.themeCd}</td>
                                                                            <td class="t-align-center">${item.dtlCntn}</td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:if>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
