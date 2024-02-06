<%@ page import="com.kap.core.dto.eb.ebb.EBBEpisdDTO" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage evaluation" data-controller="controller/eb/ebm/EBMEduApplyOnlineStep1Ctrl">
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

        <!-- content 영역 start -->
        <div class="cont-wrap">
            <div class="inner">
                <div class="sub-top-vis-area">
                    <div class="page-tit-area t-align-center">
                        <p class="page-tit f-large-title">
                            <span class="for-move">온라인 교육</span>
                        </p>
                    </div>
                </div>

                <div class="inner-con-box online-training"><!-- online-training: 온라인교육 대기 페이지 -->
                    <div class="cont-for-padding">
                        <p class="f-title1">${rtnData.nm}</p>
                        <div class="sort-label-area">
                            <p class="label"><span>${rtnData.prntCdNm}</span></p>
                            <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                        </div>

                        <div class="def-list-w">
                            <div class="def-list">
                                <p class="tit f-head">회차</p>
                                <p class="txt f-sub-head">${rtnData.episdYear}년 ${rtnData.episdOrd}차</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">업종</p>
                                <p class="txt f-sub-head">${not empty rtnData.cbsnCdNm ? rtnData.cbsnCdNm : '-'}</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">강사</p>
                                <p class="txt f-sub-head">${rtnData.isttrGroupName}</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">교육기간</p>
                                <p class="txt f-sub-head">
                                    ${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                    ~
                                    ${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                </p>
                            </div>

                        </div>
                    </div>

                    <c:set var="lctrTotLength" value="${fn:length(lctrList.list)}"/>
                    <c:set var="lctrLength" value="0"/>
                    <c:forEach var="list" items="${lctrList.list}" varStatus="status">
                        <c:if test="${not empty list.lctrDtm}">
                            <c:set var="lctrLength" value="${lctrLength+1}"/>
                        </c:if>
                    </c:forEach>

                    <div class="cont-line-sec">
                        <!-- 2023-12-06 추가 -->
                        <div class="sub-txt f-body2">
                            <p class="date">수강완료 <span>${lctrLength}</span>개</p>
                            <p class="view">총 교육 <span>${fn:length(lctrList.list)}</span>개</p>
                        </div>
                        <!-- // 2023-12-06 추가 -->
                        <ul class="article-list-w index-list" id="listLctrContainer">

                            <c:forEach var="list" items="${lctrList.list}" varStatus="status">
                                <li class="list-item">
                                    <div class="txt-area">
                                        <p class="num f-sub-head">1</p>
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
                                        <img src="${list.webPath}" alt="">
                                        <!-- 수강완료 상태 -->
                                        <c:choose>
                                            <c:when test="${not empty list.lctrDtm}">
                                                <div class="status f-caption1">수강완료</div>
                                            </c:when>
                                        </c:choose>

                                    </div>
                                    <div class="btn-area">
                                        <div class="btn-wrap">
                                            <c:choose>
                                                <c:when test="${not empty list.lctrDtm}">
                                                    <p class="hyphen">-</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn-text-icon black-arrow" type="button"><span>수강하기</span></button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </li>

                            </c:forEach>

                        </ul>

                        <div class="noti-txt-w">
                            <P class="bullet-noti-txt f-caption2">* 교육 시작시간 10분전부터 수강 가능합니다.</P>
                        </div>
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
                            cal.add(Calendar.MINUTE, -10);

                            // 30분 전의 날짜와 시간을 가져오기
                            Date eduStrtDtm = cal.getTime();

                            // JSP 페이지의 속성으로 10분 전의 날짜와 현재 날짜를 전달
                            pageContext.setAttribute("currentDate", currentDate);//현재 시간
                            pageContext.setAttribute("edctnStrtDtm", eduStrtDtm);//교육 시작시간-30분
                        %>
                        <c:set var="onlineSetYn" value="N"/>
                        <c:choose>
                            <c:when test="${edctnStrtDtm.before(currentDate)}">
                                <c:set var="onlineSetYn" value="Y"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="onlineSetYn" value="N"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="onlineStepYn" id="onlineStepYn" value="${onlineSetYn}" />

                        <div class="btn-wrap add-load align-center" style="display:none;">
                            <a class="btn-solid small black-line lctrPageSet" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                        </div>
                    </div>

                    <div class="page-bot-btn-sec">
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg myPageDetail" href="javascript:"><span>신청내역 상세</span></a>
                            </div>
                            <div class="btn-set">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>