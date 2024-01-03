<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<form class="form-horizontal" name="frmEduTotCalSearch" method="post" action="" data-del-type="none">
    <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
    <!-- 페이징 버튼 사이즈 -->
    <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
    <!-- 페이징 버튼 사이즈 -->
    <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
    <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" id="episdSeq" name="episdSeq" value="" />

    <c:set var="today" value="<%=new java.util.Date()%>" />
    <c:set var="month"><fmt:formatDate value="${today}" pattern="MM" /></c:set>


    <div class="layer-popup full-popup allTrainingSchedulePopup open"><!-- full-popup: 화면 꽉 차는 팝업 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-head">전체교육일정</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="tab-con-w">
                                    <div class="tab-btn-area">
                                        <div class="txt-major-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper">
                                                    <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                                        <a class="swiper-slide txt-tab-btn classTypeLayer <c:if test="${status.index eq 0}">active</c:if>" href="javascript:" data-prntCd="${cdList.cd}">
                                                            <p class="txt"><span class="menu-name">${cdList.cdNm}</span></p>
                                                        </a>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="txt-tab-swiper func-tab">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper ctgryCdLayer" id="ctgryCd">

                                                </div>
                                            </div>
                                        </div>

                                        <div class="txt-depth-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper edctnList">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="total-edu-area">
                                        <div class="edu-info-div" style="display:none;">
                                            <a class="btn-text-icon black-circle edctnNmLayer" href="javascript:"><span>품질을 알라면 개념을 알아야한다</span></a>
                                            <div class="status-info-w">
                                                <p class="box-label stduyMthdLayer"><span>집체교육</span></p>
                                                <p class="box-label stduyDdLayer"><span>nn일(nn시간)</span></p>
                                            </div>
                                        </div>
                                        <div class="edu-plan-area">
                                            <div class="period-area listNotEmpty">
                                                <div class="period-div">
                                                    <p class="period receive f-caption2">접수기간</p>
                                                    <p class="period education f-caption2">교육기간</p>
                                                </div>
                                                <div class="round-list">

                                                </div>
                                            </div>
                                            <div class="month-area listNotEmpty">
                                                <p class="f-head eduYear">2023년</p>
                                                <div class="scroll-div">
                                                    <div class="scroll-box">
                                                        <div class="month-wrap">
                                                            <c:forEach var="forMonth" varStatus="status" begin="1" end="12">
                                                                <p class="month <c:if test="${status.index eq month}">now</c:if>"><span class="f-body2">${forMonth}월</span></p>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="period-bar-list">

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="month-area">
                                                <p class="f-head">2023년</p>
                                                <div class="scroll-div">
                                                    <div class="scroll-box">
                                                        <div class="month-wrap">
                                                            <c:forEach var="forMonth" varStatus="status" begin="1" end="12">
                                                                <p class="month <c:if test="${status.index eq month}">now</c:if>"><span class="f-body2">${forMonth}월</span></p>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="period-bar-list">
                                                            <div class="no-data-area">
                                                                <div class="txt-box">
                                                                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="year-plan-btn">
                                            <a class="btn-text-icon download" href="/file/download?fileSeq=${rtnFormDto.ttlEdctnFileSeq}&fileOrd=${rtnFormDto.fileOrd}"><span>연간 일정 다운로드</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="copyEpisdSample" style="display:none;">
        <div class="round-div">
            <p class="f-body2 episdOrd">1회차</p>
            <div class="round-period-div">
                <div class="round-period">
                    <p class="tit f-caption2">접수</p>
                    <p class="period f-caption2 episdAccsDt">01.01 ~ 01.04</p>
                </div>
                <div class="round-period">
                    <p class="tit f-caption2">교육</p>
                    <p class="period f-caption2 episdEduDt">03.22 ~ 04.30</p>
                </div>
            </div>
        </div>
    </div>

    <div class="copyEpisdSample2" style="display:none;">
        <div class="period-bar-div">
            <p class="period-bar receive">
                <a href="javascript:"><span></span></a>
            </p>
            <p class="period-bar education">
                <span></span>
            </p>
        </div>
    </div>



    <div class="dimd"></div>

</form>





<%--</div>--%>




