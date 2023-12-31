<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" data-controller="controller/eb/eba/EBACouseDtlCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" id="episdSeq" name="episdSeq" value="" />

        <input type="hidden" id="episdYear" name="episdYear" value="" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="" />
        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnData.edctnSeq}" />
        <% pageContext.setAttribute("newLine", "\n"); %>
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <!--
              교육 사업: edu-biz
              컨실팅 사업: consult-biz
              상생 사업: coexisting-biz
            -->
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-notice.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-notice-mobile.jpg" alt="">
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

                <!--LNB 종료-->
                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="training-view-page">
                                    <div class="training-list">
                                        <div class="img-area">
                                            <img src="${rtnData.webPath}" alt="">
                                        </div>
                                        <div class="txt-area">
                                            <div class="top-line">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                                    <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                                                </div>
                                                <p class="training-name f-title1">${rtnData.nm}</p>
                                                <p class="training-explain-txt">${rtnData.smmryNm}</p>
                                            </div>
                                            <div class="class-property-w">
                                                <div class="property-list<c:if test="${rtnData.stduyMthdCd eq 'STDUY_MTHD01'}">offline</c:if>"><!-- offline: 집체교육 -->
                                                    <p class="txt">
                                                        <span>${rtnData.stduyMthdCdNm}</span>
                                                    </p>
                                                </div>
                                                <div class="property-list time"><!-- time: 학습시간 -->
                                                    <p class="txt">
                                                        <span>${rtnData.stduyDdCdNm}일(${rtnData.stduyTimeCdNm}시간)</span>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="roundSection">
                            <div class="for-motion">
                                <div class="sec-tit-area non-block"><!-- non-block : pc/mobile 둘다 flex -->
                                    <p class="f-title3">회차정보</p>
                                    <p class="article-total-count f-body2">총 <span>15</span>개</p>
                                </div>
                                <div class="sec-con-area">

                                    <div class="index-list-w" id="listContainer">
                                        <!-- 회차정보 호출 -->
                                    </div>



                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="outlineSection">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">과정소개</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="txt-sec">
                                        ${fn:replace(rtnData.itrdcCntn, newLine, '<br>')}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="goalSection">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습목표</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="txt-sec">
                                        ${fn:replace(rtnData.stduyTrgtCntn, newLine, '<br>')}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습대상</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll">
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <c:forEach var="list" items="${rtnTrgtData}">
                                                    <tr>
                                                        <th>${list.prntTargetCdNm}</th><td>${list.targetCdNm}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습 기본정보</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll">
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>학습방식</th>
                                                    <td>${rtnData.stduyMthdCdNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>수료기준</th>
                                                    <td>출석 ${rtnData.cmptnStndCdNm}% 이상, 평가 ${rtnData.cmptnJdgmtCdNm}점 이상</td>
                                                </tr>
                                                <tr>
                                                    <th>학습시간</th>
                                                    <td>${rtnData.stduyDdCdNm}일 / ${rtnData.stduyTimeCdNm}시간</td>
                                                </tr>
                                                <tr>
                                                    <th>학습 준비물</th>
                                                    <td>${rtnData.stduySuplsNm}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습내용</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="curriculum-div">
                                        <c:choose>
                                            <c:when test="${deviceType eq 'web'}">
                                                ${rtnData.pcStduyCntn}
                                            </c:when>
                                            <c:otherwise>
                                                ${rtnData.mblStduyCntn}
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- PC 모바일 구분해야됨-->



                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="connectedSection">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">연계학습</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="training-swiper-area swiper-role">
                                        <div class="inner-title">
                                            <p class="f-title2">선수과정(${relList1.totalCount})</p>
                                        </div>

                                        <c:choose>
                                            <c:when test="${relList1.totalCount ne 0}">

                                                <div class="swiper-container training-swiper">
                                                    <div class="swiper-wrapper marquee_wrapper1">

                                                        <c:forEach var="list" items="${relList1.list}" varStatus="status">
                                                            <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                                                <!--
                                                                  접수중: accepting
                                                                  접수대기: waiting
                                                                  접수마감: end
                                                                -->
                                                                <div class="img-area">
                                                                    <img src="${list.webPath}" alt="">
                                                                </div>
                                                                <div class="txt-area">
                                                                    <div class="top-label-area">
                                                                        <p class="box-label bigger available"><span>비회원 신청 가능</span></p>
                                                                    </div>

                                                                    <div class="sort-label-area">
                                                                        <p class="label"><span>${list.prntCdNm}</span></p>
                                                                        <p class="label"><span>${list.ctgryCdNm}</span></p>
                                                                    </div>
                                                                    <p class="training-name">${list.nm}</p>
                                                                    <div class="date-info-w">
                                                                        <div class="list">
                                                                            <p class="txt">교육기간</p>
                                                                            <p class="date">
                                                                                    ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                ~
                                                                                    ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                (${list.stduyDdCdNm}일간)
                                                                            </p>
                                                                        </div>
                                                                        <div class="list">
                                                                            <p class="txt">${list.stduyMthdCdNm}</p>
                                                                            <p class="date"><span class="item">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</span>
                                                                                <span class="item">
                                                                            <c:if test="${list.fxnumImpsbYn eq 'Y'}">
                                                                                정원${list.fxnumCnt}명(${list.rcrmtMthdCdNm})
                                                                            </c:if>
                                                                            <c:if test="${list.fxnumImpsbYn eq 'N'}">
                                                                                정원제한 없음
                                                                            </c:if>
                                                                        </span>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                    <div class="status-info-w">
                                                                        <p class="box-label bigger"><span>${list.placeNm}</span></p>
                                                                        <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                                                    </div>
                                                                </div>
                                                                <!-- hover 시 노출되는 영역 -->
                                                                <div class="hover-area">
                                                                    <div class="for-position">
                                                                        <div class="sort-label-area">
                                                                            <p class="label"><span>${list.prntCdNm}</span></p>
                                                                            <p class="label"><span>${list.ctgryCdNm}</span></p>
                                                                        </div>
                                                                        <p class="training-name">${list.nm}</p>
                                                                        <div class="date-info-w">
                                                                            <div class="list">
                                                                                <div class="tit">접수기간</div>
                                                                                <div class="txt">
                                                                                        ${ empty list.accsStrtDtm ? '-' : kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    ~
                                                                                        ${ empty list.accsEndDtm ? '-' : kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                </div>
                                                                            </div>
                                                                            <div class="list">
                                                                                <div class="tit">교육기간</div>
                                                                                <div class="txt">
                                                                                        ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    ~
                                                                                        ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    (${list.stduyDdCdNm}일간)
                                                                                </div>
                                                                            </div>
                                                                            <div class="list ">
                                                                                <div class="tit">${list.stduyMthdCdNm}</div>
                                                                                <div class="txt status-txt">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="status-info-w">
                                                                            <p class="box-label bigger"><span>${list.placeNm}</span></p>
                                                                            <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                                                        </div>
                                                                        <div class="btn-wrap">
                                                                            <c:if test="${list.accsStatusOrder eq 3}">
                                                                                <div class="btn-solid small black-bg" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                                                            </c:if>
                                                                            <c:if test="${list.accsStatusOrder ne 3}">
                                                                                <div class="btn-solid small black-bg episdDtl" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                                                            </c:if>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </a>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="swiper-button-next circle-arr btn_next"></div>
                                                <div class="swiper-button-prev circle-arr btn_prev"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
                                                    <div class="txt-box">
                                                        <p class="txt f-body1">등록된 선수과목이 없습니다.</p>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="training-swiper-area swiper-role">
                                        <div class="inner-title">
                                            <p class="f-title2">후속과정(${relList2.totalCount})</p>
                                        </div>
                                        <c:choose>
                                            <c:when test="${relList2.totalCount ne 0}">
                                                <div class="swiper-container training-swiper">
                                                    <div class="swiper-wrapper marquee_wrapper1">

                                                        <c:forEach var="list" items="${relList2.list}" varStatus="status">
                                                            <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                                                <!--
                                                                  접수중: accepting
                                                                  접수대기: waiting
                                                                  접수마감: end
                                                                -->
                                                                <div class="img-area">
                                                                    <img src="${list.webPath}" alt="">
                                                                </div>
                                                                <div class="txt-area">
                                                                    <div class="top-label-area">
                                                                        <p class="box-label bigger available"><span>비회원 신청 가능</span></p>
                                                                    </div>

                                                                    <div class="sort-label-area">
                                                                        <p class="label"><span>${list.prntCdNm}</span></p>
                                                                        <p class="label"><span>${list.ctgryCdNm}</span></p>
                                                                    </div>
                                                                    <p class="training-name">${list.nm}</p>
                                                                    <div class="date-info-w">
                                                                        <div class="list">
                                                                            <p class="txt">교육기간</p>
                                                                            <p class="date">
                                                                                    ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                ~
                                                                                    ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                (${list.stduyDdCdNm}일간)
                                                                            </p>
                                                                        </div>
                                                                        <div class="list">
                                                                            <p class="txt">${list.stduyMthdCdNm}</p>
                                                                            <p class="date"><span class="item">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</span>
                                                                                <span class="item">
                                                                            <c:if test="${list.fxnumImpsbYn eq 'Y'}">
                                                                                정원${list.fxnumCnt}명(${list.rcrmtMthdCdNm})
                                                                            </c:if>
                                                                            <c:if test="${list.fxnumImpsbYn eq 'N'}">
                                                                                정원제한 없음
                                                                            </c:if>
                                                                        </span>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                    <div class="status-info-w">
                                                                        <p class="box-label bigger"><span>${list.placeNm}</span></p>
                                                                        <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                                                    </div>
                                                                </div>
                                                                <!-- hover 시 노출되는 영역 -->
                                                                <div class="hover-area">
                                                                    <div class="for-position">
                                                                        <div class="sort-label-area">
                                                                            <p class="label"><span>${list.prntCdNm}</span></p>
                                                                            <p class="label"><span>${list.ctgryCdNm}</span></p>
                                                                        </div>
                                                                        <p class="training-name">${list.nm}</p>
                                                                        <div class="date-info-w">
                                                                            <div class="list">
                                                                                <div class="tit">접수기간</div>
                                                                                <div class="txt">
                                                                                        ${ empty list.accsStrtDtm ? '-' : kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    ~
                                                                                        ${ empty list.accsEndDtm ? '-' : kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                </div>
                                                                            </div>
                                                                            <div class="list">
                                                                                <div class="tit">교육기간</div>
                                                                                <div class="txt">
                                                                                        ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    ~
                                                                                        ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                                                    (${list.stduyDdCdNm}일간)
                                                                                </div>
                                                                            </div>
                                                                            <div class="list ">
                                                                                <div class="tit">${list.stduyMthdCdNm}</div>
                                                                                <div class="txt status-txt">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="status-info-w">
                                                                            <p class="box-label bigger"><span>${list.placeNm}</span></p>
                                                                            <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                                                        </div>
                                                                        <div class="btn-wrap">
                                                                            <c:if test="${list.accsStatusOrder eq 3}">
                                                                                <div class="btn-solid small black-bg" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                                                            </c:if>
                                                                            <c:if test="${list.accsStatusOrder ne 3}">
                                                                                <div class="btn-solid small black-bg episdDtl" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                                                            </c:if>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </a>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="swiper-button-next circle-arr btn_next"></div>
                                                <div class="swiper-button-prev circle-arr btn_prev"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
                                                    <div class="txt-box">
                                                        <p class="txt f-body1">등록된 후속과목이 없습니다.</p>
                                                    </div>
                                                </div>
                                            </c:otherwise>

                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap align-center for-motion">
                            <a class="btn-solid small black-bg" href="/education/apply/list"><span>목록</span></a>
                        </div>
                    </div>

                    <!-- 접수중 하단 플로팅 영역 -->
                    <div class="accepting-fixed-area">
                        <div class="for-position">
                            <button class="open-click-area" type="button">
                                <p class="tit floatingEpisdOrd"><span class="status">접수중</span></p>
                                <div class="btn-text-icon plus"><span>더보기</span></div>
                            </button>

                            <div class="hide-area">
                                <div class="inner-con">
                                    <div class="tit-area">
                                        <p class="f-title1 edctnNm">${rtnData.nm}</p>
                                    </div>
                                    <div class="con-area">
                                        <div class="scroll-area">
                                            <div class="info-line-list-w">
                                                <div class="list">
                                                    <p class="tit">접수일자</p>
                                                    <p class="txt floatingAccsStrtDtm">2023.02.01 10:00 ~ 2023.02.01 17:00</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">교육일자</p>
                                                    <p class="txt floatingEdctnStrtDtm">2023.02.01 10:00 - 2023.02.01 17:00</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">강사</p>
                                                    <p class="txt floatingIsttrGroupName">
                                                        <span>홍길동</span>
                                                    </p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">정원</p>
                                                    <p class="txt floatingFxnumImpsb">30명</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">교육장소</p>
                                                    <p class="txt floatingPlaceNm">경주GPC
                                                    </p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">학습방식</p>
                                                    <p class="txt floatingStduyMthdCdNm">집체교육
                                                    </p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">학습시간</p>
                                                    <p class="txt floatingStduyDdCdNm">2일/14시간
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="btn-wrap">
                                        <div class="btn-set">
                                            <a class="btn-solid small gray-bg has-icon tel floatingPop" data-popType="1" href="javascript:" title="회차 담당자 문의하기"><span>회차 담당자 문의</span></a>
                                            <a class="btn-solid small gray-bg has-icon lecture floatingPop"data-popType="2" href="javascript:" title="온라인 강의목차 보기"><span>온라인 강의목차</span></a>
                                            <a class="btn-solid small gray-bg has-icon download floatingPop" data-popType="3" href="javascript:" title="안내문 다운로드"><span>안내문 다운로드</span></a>
                                        </div>
                                        <div class="btn-set">
                                            <a class="btn-solid small black-bg applyBtn" href="javascript:" style="display:none;"><span>신청하기</span></a>
                                        </div>
                                    </div>

                                    <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 앵커 영역 -->
                    <div class="anchor-btn-w">
                        <button class="anchor-btn active" href="javascript:">
                            <span>회차정보</span>
                        </button>
                        <button class="anchor-btn" href="javascript:">
                            <span>과정소개</span>
                        </button>
                        <button class="anchor-btn" href="javascript:">
                            <span>학습정보</span>
                        </button>
                        <button class="anchor-btn" href="javascript:">
                            <span>연계학습</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>

    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAPicLayer.jsp"></jsp:include><!-- 문의 담당자 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduLctrLayer.jsp"></jsp:include><!-- 온라인 강의 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduRoomLayer.jsp"></jsp:include><!-- 교육장 팝업 -->


</div>




