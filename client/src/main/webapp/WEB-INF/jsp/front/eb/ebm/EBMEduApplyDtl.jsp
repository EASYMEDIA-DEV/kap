<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.kap.core.dto.eb.ebb.EBBEpisdDTO" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="mypage" data-controller="controller/eb/ebm/EBMEduApplyDtlCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${ rtnData.edctnSeq }" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="${ rtnData.episdOrd }" />
        <input type="hidden" id="episdYear" name="episdYear" value="${ rtnData.episdYear }" />
        <input type="hidden" id="ptcptSeq" name="ptcptSeq" value="${ rtnData.ptcptSeq }" />
        <input type="hidden" id="memSeq" name="memSeq" value="${ rtnData.memSeq }" />

        <input type="hidden" id="srvYn" name="srvYn" value="${rtnData.srvYn}" />


        <!--교육일수 차이 계산-->
        <c:set var="dayVal" value=""/>
        <c:choose>
            <c:when test="${not empty rtnData.edctnStrtDtm && not empty rtnData.edctnEndDtm}">
                <c:set var="strtDt" value="${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                <c:set var="endDt" value="${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                <c:set var="dayVal" value="${kl:getDaysDiff(strtDt, endDt) + 1}"/>
            </c:when>
        </c:choose>

        <!-- content 영역 start -->
        <div class="cont-wrap">

            <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육 사업 신청내역</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />


                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-con-area">
                                    <div class="training-confirm">
                                        <div class="top-info">
                                            <div class="training-view-page">
                                                <div class="training-list">
                                                    <div class="img-area">
                                                        <c:if test="${not empty rtnData.webPath}">
                                                            <img src="${rtnData.webPath}" alt="${rtnData.fileDsc}">
                                                        </c:if>
                                                    </div>
                                                    <div class="txt-area">

                                                        <div class="top-line">
                                                            <div class="sort-label-area">
                                                                <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                                                <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                                                            </div>
                                                            <p class="training-name f-title3">${rtnData.nm}</p>
                                                            <p class="training-explain-txt">${rtnData.smmryNm}</p>
                                                        </div>

                                                        <div class="class-property-w ">
                                                            <c:set var="stduyNm" value=""/>
                                                            <c:choose>
                                                                <c:when test="${rtnData.stduyMthdCd ne 'STDUY_MTHD02'}">
                                                                    <c:set var="stduyNm" value="offline"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:set var="stduyNm" value="online"/>
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <div class="property-list ${stduyNm}"><!-- offline: 집체교육 -->
                                                                <p class="txt">
                                                                    <span>${rtnData.stduyMthdCdNm}</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list time"><!-- time: 학습시간 -->
                                                                <p class="txt">
                                                                    <span>${rtnData.stduyDdCdNm}일(${rtnData.stduyTimeCdNm}시간)</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list completion"><!-- completion: 수료여부 -->
                                                                <p class="txt">
                                                                    <c:choose>
                                                                        <c:when test="${rtnData.cmptnYn eq 'Y'}">
                                                                            <span>수료</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span>미수료</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </p>
                                                            </div>
                                                            <div class="property-list education"><!-- education: 교육상태 -->
                                                                <p class="txt">
                                                                    <c:choose>
                                                                        <c:when test="${rtnData.trnsfYn eq 'N'}">
                                                                            <span>${rtnData.eduStat}</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span>교육양도</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="bot-info">
                                            <div class="index-list-w">
                                                <div class="list-item">
                                                    <div class="cont">
                                                        <div class="top-area">
                                                            <div class="left">
                                                                <div class="group">
                                                                    <p class="index-num f-title3">${rtnData.episdOrd}회차</p>
                                                                    <div class="status-info-w">
                                                                        <c:if test="${not empty rtnData.cbsnCdNm}">
                                                                            <p class="box-label bigger">
                                                                            <span>
                                                                                    ${rtnData.cbsnCdNm}
                                                                            </span>
                                                                            </p>
                                                                        </c:if>

                                                                    </div>
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <div class="btn-set">
                                                                        <c:if test="${not empty rtnData.edctnNtctnFileSeq}">
                                                                            <a class="btn-text-icon download" href="/file/download?fileSeq=${rtnData.edctnNtctnFileSeq}&fileOrd=${rtnData.fileOrd}"><span>안내문</span></a>
                                                                        </c:if>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="cont-area">
                                                            <div class="info-list-w ">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">강사</p>

                                                                    <c:set var="isttrName" value="${not empty rtnData.isttrGroupName ? rtnData.rtnData : rtnData.isttrName}"/>
                                                                    <p class="txt f-body2">${isttrName}</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">정원</p>
                                                                    <p class="txt f-body2">
                                                                        <c:if test="${rtnData.fxnumImpsbYn eq 'Y'}">
                                                                            ${rtnData.fxnumCnt}명(${rtnData.rcrmtMthdCdNm})
                                                                        </c:if>
                                                                        <c:if test="${rtnData.fxnumImpsbYn eq 'N'}">
                                                                            정원제한 없음
                                                                        </c:if>
                                                                    </p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육장소</p>
                                                                    <p class="txt f-body2">

                                                                        <c:choose>
                                                                            <c:when test="${rtnData.stduyMthdCd eq 'STDUY_MTHD02'}">
                                                                                온라인
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="javascript:" class="mapBtn" data-mapchk="N" data-zipcode="${rtnData.zipcode}" data-nm="${rtnData.placeNm}" data-rprsntTelNo="${rtnData.rprsntTelNo}" data-bscAddr="${rtnData.bscAddr}" data-dtlAddr="${rtnData.dtlAddr}" title="교육장 안내 팝업 열기">${rtnData.placeNm}</a>
                                                                            </c:otherwise>
                                                                        </c:choose>

                                                                    </p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육일자</p>
                                                                    <p class="txt f-body2">
                                                                        ${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                                                        ~
                                                                        ${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }(${dayVal}일간)
                                                                    </p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">신청일시</p>
                                                                    <p class="txt f-body2">${ empty rtnData.ptcptDtm ? '-' : kl:convertDate(rtnData.ptcptDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</p>
                                                                </div>
                                                                <c:if test="${rtnData.rcrmtMthdCd eq 'RCRMT_MTHD02'}">
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">선발여부</p>
                                                                        <p class="txt f-body2">
                                                                                ${rtnData.sttsCdNm}
                                                                        </p>
                                                                    </div>
                                                                </c:if>

                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-text-icon black-arrow popupPicPrevSet" type="button" data-picNm="${rtnData.picNm}" data-picEmail="${rtnData.picEmail}" data-picTelNo="${rtnData.picTelNo}"><span>회차 담당자 문의</span></button>

                                                                <c:if test="${rtnData.stduyMthdCd ne 'STDUY_MTHD01'}">
                                                                    <button class="btn-text-icon black-arrow popupLctrPrevSet" type="button" data-episdYear="${rtnData.episdYear}" data-episdOrd="${rtnData.episdOrd}"><span>온라인 강의목차</span></button>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="btn-sec">
                                        <div class="btn-wrap align-right">
                                            <div class="btn-set">


                                                <c:if test="${rtnData.trnsfYn eq 'N' && rtnData.cmptnYn eq 'Y'}">
                                                    <button class="btn-solid small gray-bg icon btn-print cmptnPop" type="button" <%--onclick="printFn()"--%>><span>수료증 출력</span></button>
                                                </c:if>

                                               <c:set var="accsEndDt" value="${ empty rtnData.accsEndDtm ? '-' : kl:convertDate(rtnData.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }"/>
                                                <%--<c:set var="accsEndDt" value="2024.01.21"/>--%>
                                                <c:set var="edctnStatDt" value="${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }"/>
                                                <%--<c:set var="edctnStatDt" value="2024.01.24"/>--%>

                                                <%
                                                    Date nowDate = new Date();
                                                    pageContext.setAttribute("nowDate", nowDate);//현재 시간
                                                %>

                                                <fmt:formatDate pattern="yyyy.MM.dd" value="${nowDate}" var="nowDate" />

                                                <c:if test="${rtnData.trnsfYn eq 'N' && (rtnData.sttsCd eq 'EDU_STTS_CD01' || rtnData.sttsCd eq 'EDU_STTS_CD04') && accsEndDt lt nowDate && nowDate lt edctnStatDt}">
                                                    <button class="btn-solid small gray-bg icon transfer" type="button"><span>교육양도</span></button>
                                                </c:if>

                                                <c:if test="${rtnData.stduyMthdCd ne 'STDUY_MTHD01'}">
                                                    <!--출력조건  교육중, 교육대기지만 시작시간 30분전부터   -->

                                                    <c:choose>
                                                        <c:when test="${rtnData.eduStat eq '교육대기'}">


                                                            <%
                                                                // 현재 날짜와 시간 가져오기
                                                                Date currentDate = new Date();
                                                                EBBEpisdDTO  eBBEpisdDTO= (EBBEpisdDTO)request.getAttribute("rtnData");
                                                                String dateString = eBBEpisdDTO.getEdctnStrtDtm();//"2024-01-16 16:25:00";
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                Date convertedDate = sdf.parse(dateString);

                                                                // Calendar 객체를 사용하여 30분 전의 날짜 및 시간 계산
                                                                Calendar cal = Calendar.getInstance();
                                                                cal.setTime(convertedDate);
                                                                cal.add(Calendar.MINUTE, -30);

                                                                // 30분 전의 날짜와 시간을 가져오기
                                                                Date eduStrtDtm = cal.getTime();

                                                                // JSP 페이지의 속성으로 30분 전의 날짜와 현재 날짜를 전달
                                                                pageContext.setAttribute("currentDate", currentDate);//현재 시간
                                                                pageContext.setAttribute("edctnStrtDtm", eduStrtDtm);//교육 시작시간-30분
                                                            %>

                                                            <c:choose>
                                                                <c:when test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && edctnStrtDtm.before(currentDate) && rtnData.cmptnYn eq 'N'}">
                                                                    <button class="btn-solid small gray-bg icon taking onlineStep" type="button" data-lctrSeq="${list.lctrSeq}"><span>수강하기</span></button>
                                                                </c:when>
                                                                <c:otherwise>

                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:when>
                                                        <c:when test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && rtnData.eduStat eq '교육중' && rtnData.cmptnYn eq 'N'}">
                                                            <button class="btn-solid small gray-bg icon taking onlineStep" type="button"><span>수강하기</span></button>
                                                        </c:when>

                                                    </c:choose>


                                                </c:if>


                                                <%
                                                    // 현재 날짜와 시간 가져오기
                                                    Date currentDate = new Date();
                                                    // SimpleDateFormat을 사용하여 원하는 형식으로 포맷 지정
                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                    // 형식에 맞게 날짜를 문자열로 변환
                                                    String formattedDate = dateFormat.format(currentDate);

                                                    Date parsedDate = dateFormat.parse(formattedDate);

                                                    EBBEpisdDTO  eBBEpisdDTO= (EBBEpisdDTO)request.getAttribute("rtnData");
                                                    String dateString = eBBEpisdDTO.getEdctnStrtDtm();//"2024-01-15 16:25:00";
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                                    Date convertedDate = sdf.parse(dateString);

                                                    // Calendar 객체를 사용하여 30분 전의 날짜 및 시간 계산
                                                    Calendar cal = Calendar.getInstance();

                                                    cal.setTime(convertedDate);
                                                    cal.add(Calendar.DATE, -1);

                                                    // 30분 전의 날짜와 시간을 가져오기
                                                    Date eduStrtDtm = cal.getTime();

                                                    // JSP 페이지의 속성으로 30분 전의 날짜와 현재 날짜를 전달
                                                    Date now = new Date();
                                                    pageContext.setAttribute("now", now);

                                                    pageContext.setAttribute("currentDate", parsedDate);//현재 시간
                                                    pageContext.setAttribute("edctnStrtDtm", eduStrtDtm);//교육 시작시간 -1일

                                                    /*출석하기 버튼의 출력조건 nowAtndcYn값이 F가 아니어야 한다 F는 온라인교육임을 뜻함,
                                                    nowAtndcYn : F 온라인교육
                                                    nowAtndcYn : N 금일출석 안함
                                                    nowAtndcYn : Y 금일 출석 함
                                                    episdCheck : Y 모바일 QR코드를 통해 들어옴
                                                    episdCheck : N QR코드 이외의 접근
                                                    추가로 교육 시작일~교육 종료일안에 현재일이 위치해 있어야 출력함*/
                                                %>

                                                <c:choose>
                                                    <c:when test="${nowAtndcYn ne 'F'}">
                                                        <fmt:formatDate pattern="yyyy-MM-dd" value="${now}" var="nowDate" />

                                                        <c:set var="edctnStrtDt" value="${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                                                        <c:set var="edctnEndDt" value="${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>

                                                        <c:if test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && edctnStrtDt le nowDate && nowDate le edctnEndDt}">
                                                            <c:if test="${episdCheck eq 'Y' && nowAtndcYn eq 'N'}">
                                                                <button class="btn-solid small gray-bg icon attendance atndcCheck" type="button"><span>출석하기</span></button>
                                                            </c:if>

                                                            <c:if test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && nowAtndcYn eq 'Y' && nowLvgrmYn eq 'N' }">
                                                                <button class="btn-solid small gray-bg icon checkout lvgrmCheck" type="button"><span>퇴실하기</span></button>
                                                            </c:if>

                                                        </c:if>

                                                    </c:when>

                                                </c:choose>

                                                <fmt:formatDate pattern="yyyy-MM-dd" value="${now}" var="nowDate" />

                                                <c:set var="examStatDt" value="${ empty rtnData.examStrtDtm ? '-' : kl:convertDate(rtnData.examStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                                                <c:set var="examEndDt" value="${ empty rtnData.examEndDtm ? '-' : kl:convertDate(rtnData.examEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                                                <!-- 0: 평가하기 비활성화, 1:평가하기 활성화-->
                                                <c:set var="examStatus" value="0"/>

                                                <c:choose>
                                                    <c:when test="${rtnData.trnsfYn eq 'N' && not empty rtnData.examSeq && examStatDt le nowDate && nowDate le examEndDt}">
                                                        <c:set var="examStatus" value="1"/>
                                                    </c:when>
                                                </c:choose>

                                                <c:set var="examStart" value="" />

                                                <c:if test="${rtnData.otsdExamPtcptYn ne 'Y' && examStatus eq '1' && rtnData.sttsCd eq 'EDU_STTS_CD01'  && empty  rtnData.examPtcptSeq}">
                                                    <button class="btn-solid small gray-bg icon evaluation examStart" type="button"><span>평가하기</span></button>
                                                    <c:set var="examStart" value="examStart" />
                                                </c:if>

                                                <c:set var="srvListStart" value="" />

                                                <c:if test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && (srvStrt.before(currentDate) || srvStrt eq currentDate)  &&   (currentDate.before(srvEnd) || srvEnd eq currentDate) }">
                                                    <c:set var="srvListStart" value="srvListStart" />
                                                </c:if>

                                                <c:choose>
                                                    <c:when test="${rtnData.trnsfYn eq 'N' && (currentDate.before(edctnStrtDtm) || currentDate eq edctnStrtDtm)  && rtnData.sttsCd ne 'EDU_STTS_CD02'    }">
                                                        <button class="btn-solid small gray-bg icon apply-cancel applyCancel" type="button"><span>신청취소</span></button>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%


                            String srvStrtString = eBBEpisdDTO.getSrvStrtDtm();//"2024-01-15 16:25:00";
                            String srvEndString = eBBEpisdDTO.getSrvEndDtm();//"2024-01-15 16:25:00";

                            Date convertedDate1 = sdf.parse(srvStrtString);
                            Date convertedDate2 = sdf.parse(srvEndString);

                            // Calendar 객체를 사용하여 30분 전의 날짜 및 시간 계산

                            cal.setTime(convertedDate1);
                            Date srvStrt = cal.getTime();
                            cal.setTime(convertedDate2);
                            Date srvEnd = cal.getTime();

                            // JSP 페이지의 속성으로 30분 전의 날짜와 현재 날짜를 전달
                            pageContext.setAttribute("currentDate", parsedDate);//현재 시간
                            pageContext.setAttribute("srvStrt", srvStrt);//설문 시작시간
                            pageContext.setAttribute("srvEnd", srvEnd);//설문 시작시간
                        %>

                        <c:choose>
                            <c:when test="${not empty rtnData.srvSeq}">
                                <c:choose>
                                    <c:when test="${rtnData.sttsCd eq 'EDU_STTS_CD01' && rtnData.trnsfYn eq 'N' && (srvStrt.before(currentDate) || srvStrt eq currentDate)  &&   (currentDate.before(srvEnd) || srvEnd eq currentDate) }">
                                        <div class="cont-sec no-border scroll-motion">
                                            <div class="for-motion">
                                                <div class="sec-tit-area">
                                                    <p class="f-title3">교육 만족도 설문 조사</p>
                                                </div>
                                                <div class="sec-con-area ">
                                                    <div class="graphic-sec">
                                                        <div class="box-btn-area">
                                                            <div class="bg-area">
                                                                <div class="img" style="background-image: url('/common/images/img-assessment-btn-bg.jpg');"></div>
                                                            </div>
                                                            <div class="txt-area">
                                                                <p class="txt f-head">
                                                                    수강하신 교육 및 세미나에 대한 만족도와 소감을 남겨주세요.
                                                                    <br/>소중한 의견을 반영하여 더 나은 교육을 만들겠습니다.
                                                                </p>
                                                            </div>

                                                            <div class="btn-wrap">
                                                                <a class="btn-solid small white-bg srvStart" href="javascript:" data-episdseq="${rtnData.episdSeq}" data-edctnseq="${rtnData.edctnSeq}" data-episdyear="${rtnData.episdYear}" data-episdord="${rtnData.episdOrd}" data-srvseq="${rtnData.srvSeq}"><span>참여하기</span></a>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>

                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">신청자 기본정보</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <!-- 2023-12-07 수정 -->
                                                <tr>
                                                    <th>신청자</th>
                                                    <td>${applicantInfo.name}</td>
                                                </tr>
                                                <tr>
                                                    <th>성별</th>
                                                    <td>
                                                        <c:if test="${applicantInfo.gndr eq '1'}">
                                                            남
                                                        </c:if>
                                                        <c:if test="${applicantInfo.gndr ne '1'}">
                                                            여
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>휴대폰번호</th>
                                                    <td>
                                                        ${not empty applicantInfo.hpNo ? applicantInfo.hpNo : '-'}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>이메일</th>
                                                    <td>
                                                        ${not empty applicantInfo.email ? applicantInfo.email : '-'}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>일반 전화번호</th>
                                                    <td>
                                                        ${not empty applicantInfo.telNo ? applicantInfo.telNo : '-'}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>부서</th>
                                                    <td>${applicantInfo.deptCdNm}
                                                        <c:if test="${not empty applicantInfo.deptDtlNm}">
                                                            (${applicantInfo.deptDtlNm})
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>직급</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${applicantInfo.pstnCdNm ne '기타'}">
                                                                ${applicantInfo.pstnCdNm}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${applicantInfo.pstnCdNm}(${applicantInfo.pstnNm})
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>





                                                <!-- // 2023-12-07 수정 -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">소속 부품사 기본정보</p><!-- 2023-12-07 텍스트 수정 -->
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>소속 부품사 기본정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>


                                                <tr>
                                                    <th>사업자등록번호</th>
                                                    <td>${kl:bsnmNoConvert(rtnInfo.bsnmNo)}</td>
                                                </tr>
                                                <tr>
                                                    <th>부품사명</th>
                                                    <td>${rtnInfo.cmpnNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>대표자명</th>
                                                    <td>${rtnInfo.rprsntNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>구분</th>
                                                    <td>${rtnInfo.ctgryNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>규모</th>
                                                    <td>${rtnInfo.sizeNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>설립일자</th>
                                                    <td>${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</td>
                                                </tr>
                                                <tr>
                                                    <th>회사 전화번호</th>
                                                    <td>${rtnInfo.telNo}</td>
                                                </tr>
                                                <tr>
                                                    <th>본사주소</th>
                                                    <td>(${rtnInfo.zipcode}) ${rtnInfo.bscAddr} ${rtnInfo.dtlAddr}</td>
                                                </tr>
                                                <tr>
                                                    <th>매출액</th>
                                                    <td>${rtnInfo.slsPmt}억 원(${rtnInfo.slsYear}년)</td>
                                                </tr>
                                                <tr>
                                                    <th>직원수</th>
                                                    <td>${rtnInfo.mpleCnt}명</td>
                                                </tr>
                                                <tr>
                                                    <th>주생산품</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${empty rtnInfo.mjrPrdct1 and empty rtnInfo.mjrPrdct2 and empty rtnInfo.mjrPrdct3}">
                                                                -
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:if test="${not empty rtnInfo.mjrPrdct1}">
                                                                    ① ${rtnInfo.mjrPrdct1}
                                                                </c:if>
                                                                <c:if test="${not empty rtnInfo.mjrPrdct2}">
                                                                    ② ${rtnInfo.mjrPrdct2}
                                                                </c:if>
                                                                <c:if test="${not empty rtnInfo.mjrPrdct3}">
                                                                    ③ ${rtnInfo.mjrPrdct3}
                                                                </c:if>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <c:choose>
                                                    <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">
                                                        <tr>
                                                            <th>SQ정보</th>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${empty sqInfoList.list}">
                                                                        -
                                                                    </c:when>
                                                                    <c:when test="${empty sqInfoList.list[0].nm}">
                                                                        -
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:forEach var="item" items="${sqInfoList.list}" varStatus="status">
                                                                            <c:choose>
                                                                                <c:when test="${empty item.nm and empty item.score and empty item.year and empty item.crtfnCmpnNm}">

                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <c:if test="${empty item.nm}">
                                                                                        <c:set var="nm" value="-"/>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty item.nm}">
                                                                                        <c:set var="nm" value="${item.nm}"/>
                                                                                    </c:if>

                                                                                    <c:if test="${empty item.score}">
                                                                                        <c:set var="score" value="-"/>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty item.score}">
                                                                                        <c:set var="score" value="${item.score}"/>
                                                                                    </c:if>

                                                                                    <c:if test="${empty item.year}">
                                                                                        <c:set var="year" value="-"/>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty item.year}">
                                                                                        <c:set var="year" value="${item.year} 년"/>
                                                                                    </c:if>

                                                                                    <c:if test="${empty item.crtfnCmpnNm}">
                                                                                        <c:set var="crtfnCmpnNm" value="-"/>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty item.crtfnCmpnNm}">
                                                                                        <c:set var="crtfnCmpnNm" value="${item.crtfnCmpnNm}"/>
                                                                                    </c:if>
                                                                                    <p class="f-body1">${status.count}. ${nm} / ${score} / ${year} / ${crtfnCmpnNm}</p>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </c:forEach>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01001'}">
                                                        <tr>
                                                            <th>품질5스타</th>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty rtnInfo.qlty5StarCdNm}">
                                                                        ${rtnInfo.qlty5StarCdNm} / ${rtnInfo.qlty5StarYear}년
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        -
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>납입5스타</th>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty rtnInfo.pay5StarCdNm}">
                                                                        ${rtnInfo.pay5StarCdNm} / ${rtnInfo.pay5StarYear}년
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        -
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>기술5스타</th>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty rtnInfo.tchlg5StarCdNm}">
                                                                        ${rtnInfo.tchlg5StarCdNm} / ${rtnInfo.tchlg5StarYear}년
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        -
                                                                    </c:otherwise>
                                                                </c:choose>

                                                            </td>
                                                        </tr>
                                                    </c:when>
                                                </c:choose>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${rtnData.gpcYn eq 'Y'}">
                            <div class="cont-sec no-border scroll-motion">
                                <div class="for-motion">
                                    <div class="sec-tit-area">
                                        <p class="f-title3">GPC 아이디</p>
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="table-sec">
                                            <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                                <table class="basic-table">
                                                    <caption>신청자 기본 정보</caption>
                                                    <colgroup>
                                                        <col style="width: 273rem;">
                                                        <col style="width: 820rem;">
                                                    </colgroup>
                                                    <tbody>
                                                    <tr>
                                                        <th>GPC 아이디</th>
                                                        <td>${rtnData.gpcId}</td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>


                        <c:if test="${rtnData.trnsfYn eq 'N' and rtnData.stduyMthdCd ne 'STDUY_MTHD02'}">
                            <div class="cont-sec no-border scroll-motion">
                                <div class="for-motion">
                                    <div class="sec-tit-area">
                                        <p class="f-title3">출석내역</p>
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="progress-sec">
                                            <!-- @ data-progress에 수치 값 입력 (0~100) -->
                                            <c:set var="atndcTotLength" value="${fn:length(ptcptList)}"/>
                                            <c:set var="atndcLength" value="0"/>
                                            <c:forEach var="list" items="${ptcptList}" varStatus="status">
                                                <c:if test="${not empty list.atndcDtm}">
                                                    <c:set var="atndcLength" value="${atndcLength+1}"/>
                                                </c:if>
                                            </c:forEach>

                                            <div class="progress-area" data-progress="<fmt:formatNumber value="${(atndcLength/atndcTotLength)*100}" pattern=".00"/>">
                                                <div class="progress">
                                                    <div class="progress-bar"></div>
                                                </div>
                                                <div class="progress-info">
                                                    <p class="txt f-sub-head">출석률</p>
                                                    <p class="progress-num f-head color-sky">
                                                        <span class="num">0</span>
                                                        <span>%</span>
                                                    </p>
                                                </div>
                                            </div>
                                            <ul class="progress-list">


                                                <c:forEach var="list" items="${ptcptList}" varStatus="status">
                                                    <li class="list-item">
                                                        <div class="txt-area">
                                                            <p class="day f-head">${fn:length(ptcptList) - status.index}일차</p>
                                                            <p class="date f-caption2">${kl:convertDate(list.edctnDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</p>
                                                        </div>
                                                        <div class="status-area">
                                                            <dl class="status to">
                                                                <dt class="dt f-caption1">출석</dt>
                                                                <dd class="dd f-body1">${kl:convertDate(list.atndcDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '')}</dd>
                                                            </dl>
                                                            <dl class="status off">
                                                                <dt class="dt f-caption1">퇴실</dt>
                                                                <dd class="dd f-body1">
                                                                        ${kl:convertDate(list.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '')}

                                                                    <c:if test="${not empty list.etcNm}">
                                                                        <div class="tooltip-wrap">
                                                                            <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                                                            <div class="tooltip-box">
                                                                                <p class="txt f-caption2">${list.etcNm}</p>
                                                                                <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>


                                                                </dd>
                                                            </dl>
                                                        </div>
                                                    </li>
                                                </c:forEach>

                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:if>
                            <c:if test="${rtnData.trnsfYn eq 'N' and rtnData.sttsCd eq 'EDU_STTS_CD01'}">
                                <div class="cont-sec no-border scroll-motion">
                                    <div class="for-motion">
                                        <div class="sec-tit-area">
                                            <p class="f-title3">교육 만족도 설문 조사 참여내역</p>
                                        </div>
                                        <div class="sec-con-area ${srvListStart}" data-episdseq="${rtnData.episdSeq}" data-edctnseq="${rtnData.edctnSeq}" data-episdyear="${rtnData.episdYear}" data-episdord="${rtnData.episdOrd}" data-srvseq="${rtnData.srvSeq}">
                                            <div class="gray-bg-sec">
                                                <!-- 2024-02-23 a태그 감싸기 -->
                                                <a ${empty srvListStart ? "" : 'href="javascript:"'}>
                                                    <div class="con-list-box-w">
                                                        <div class="con-list-box">
                                                            <p class="f-head">교육 만족도 설문 조사</p>
                                                            <div class="ul-txt-w info">
                                                                <div class="ul-txt-list">
                                                                    <div class="ul-txt">
                                                                        <!-- <dl><dt class="f-caption2">평가점수</dt><dd class="f-caption1">95점</dd></dl> -->
                                                                        <dl><dt class="f-caption2">등록일시</dt><dd class="f-caption1">${ empty rtnData.srvDtm ? '-' : kl:convertDate(rtnData.srvDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }</dd></dl>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${rtnData.srvYn eq 'Y'}">
                                                            <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                                <p class="txt f-body1">참여완료</p>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                                <p class="txt f-body1">미참여</p>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a><!-- // 2024-02-23 a태그 감싸기 -->
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${rtnData.trnsfYn eq 'N' && (not empty rtnData.examSeq || rtnData.otsdExamPtcptYn eq 'Y') && rtnData.sttsCd eq 'EDU_STTS_CD01' }">
                                    <div class="cont-sec no-border scroll-motion">
                                        <div class="for-motion">
                                            <div class="sec-tit-area">
                                                <p class="f-title3">수료 평가 참여내역</p>
                                            </div>
                                            <div class="sec-con-area ${examStart}">
                                                <div class="gray-bg-sec">
                                                    <!-- 2024-02-23 a태그 감싸기 -->
                                                    <a ${empty examStart ? "" : 'href="javascript:"'}>
                                                        <div class="con-list-box-w">
                                                            <div class="con-list-box">
                                                                <p class="f-head">평가</p>
                                                                <div class="ul-txt-w info">
                                                                    <div class="ul-txt-list">
                                                                        <div class="ul-txt">
                                                                            <dl><dt class="f-caption2">평가점수</dt>
                                                                                <dd class="f-caption1">
                                                                                    <c:choose>
                                                                                        <c:when test="${not empty rtnData.examScore}">
                                                                                            ${rtnData.examScore}점
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            -
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </dd>
                                                                            </dl>
                                                                            <dl><dt class="f-caption2">등록일시</dt>
                                                                                <dd class="f-caption1">
                                                                                        ${ empty rtnData.examPtcptDtm ? '-' : kl:convertDate(rtnData.examPtcptDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }
                                                                                </dd></dl>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <c:choose>
                                                            <c:when test="${(examStatus eq '1' && rtnData.sttsCd eq 'EDU_STTS_CD01' && not empty rtnData.examScore || (rtnData.otsdExamPtcptYn eq 'Y' && not empty rtnData.examPtcptDtm))}">
                                                                <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                                    <p class="txt f-body1">참여완료</p>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                                    <p class="txt f-body1">미참여</p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </a><!-- // 2024-02-23 a태그 감싸기 -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                            </c:if>


                    </div>
                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap for-motion align-right">
                            <a class="btn-solid small black-bg" href="/my-page/edu-apply/list"><span>목록</span></a>
                        </div>
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