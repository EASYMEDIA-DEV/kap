<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<script>
    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            alert("정상적인 접근이 아닙니다.");
            location.href="/";
        }
    }
</script>
<!-- jspdfDownload 관련 -->
<script type="text/javascript" src="/common/js/lib/jspdf/bluebird.min.js"></script>
<script type="text/javascript" src="/common/js/lib/jspdf/html2canvas.js"></script>
<script type="text/javascript" src="/common/js/lib/jspdf/jspdf.min.js"></script>
<!-- content 영역 start -->
<div class="cont-wrap" data-controller="controller/mp/mpc/MPConsultingSurveyStep2Ctrl" >
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">컨설팅 만족도 설문 조사 참여하기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <div class="divide-flex-box download">
                <div class="left">
                    <p class="info-guide-txt f-body2">설문을 응답해주시는 참여자의 정보를 입력해주세요.</p><!-- 2023-12-07 f-body2 class 추가 -->
                    <!-- <p class="f-caption2">* 해당 설문은 컨설팅 만족도를 높이기 위한 자료로 사용되니 신중하게 응답해주시기를 바랍니다.</p> -->
                    <div class="noti-txt-w">
                        <P class="bullet-noti-txt f-caption2">* 해당 설문은 컨설팅 만족도를 높이기 위한 자료로 사용되니 신중하게 응답해주시기를 바랍니다.</P>
                    </div>
                </div>
                <div class="right has-button">
                    <div class="btn-wrap">
                        <a class="btn-text-icon download" href="javascript:" download="" id="pdfDownload"><span>설문양식 다운로드</span></a>
                    </div>
                </div>
            </div>

            <form class="form-horizontal" id="frmData" name="frmData" method="post" >

                <input type="hidden" value="${rtnData.memSeq}" name="rtnMemSeq" class="notRequired" >
                <input type="hidden" value="${rtnData.name}" name="rtnPtcptName" class="notRequired" >
                <input type="hidden" value="${rtnData.telNo}"  name="rtnPtcptTelno" class="notRequired" >
                <input type="hidden" value="${loginMap.hpNo}"  name="rtnPtcptHpno" class="notRequired" >

                <input type="hidden" value="${rtnData.pstnNm}"  name="rtnPtcptPstn" class="notRequired" >
                <input type="hidden" value="${rtnData.email}" name="rtnPtcptEmail" class="notRequired" >
                <input type="hidden" value="${rtnData.cnstgSeq}" name="rtnCnstgSeq" >
                <input type="hidden" name="srvSeq" value="${rtnData.srvSeq}">
                <input type="hidden" name="rfncCd" value="CON">
            <div class="gray-bg-enter-form">
                <div class="abs-option">
                    <div class="form-checkbox">
                        <input type="checkbox" id="sameAsSubscriberChk" class="notRequired" name="sameAsSubscriberChk">
                        <label for="sameAsSubscriberChk">신청인과 동일</label>
                    </div>
                </div>
                <div class="data-enter-form">
                    <div class="row">
                        <div class="th">
                            <p class="title f-head">참여자</p>
                        </div>
                        <div class="td">
                            <div class="data-line-w">
                                <div class="data-line">
                                    <div class="form-group">
                                        <div class="form-input">
                                            <input type="text" placeholder="이름 입력" name="ptcptName" title="참여자" maxlength="50">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="th">
                            <p class="title f-head">연락처</p>
                        </div>
                        <div class="td">
                            <div class="data-line-w">
                                <div class="data-line">
                                    <div class="form-group">
                                        <div class="form-input">
                                            <input type="text" placeholder="연락처 입력" name="ptcptTelno" title="연락처" class="phoneChk" maxlength="13">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="th">
                            <p class="title f-head">직급</p>
                        </div>
                        <div class="td">
                            <div class="data-line-w">
                                <div class="data-line">
                                    <div class="form-group">
                                        <div class="form-input">
                                            <input type="text" placeholder="직급 입력" name="ptcptPstn" title="직급" maxlength="50">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="evaluation-category">
                <div class="left">
                    <p class="subject-tit f-title3">2023 상주기술지도</p>
                    <div class="sort-label-area">
                        <p class="label"><span>기술지도</span></p>
                    </div>
                </div>
                <div class="right">
                    <div class="status-info-w">
                        <p class="box-label bigger"><span>2023-01-01 ~ 2023-01-01</span></p>
                        <p class="box-label bigger"><span>금속분야- 도금</span></p>
                        <p class="box-label bigger"><span>홍길동</span></p>
                        <p class="box-label bigger teal"><span>${rtnData.qstnCnt}문항</span></p>
                    </div>
                </div>
            </div>

                <div class="survey-form">
                    <div class="cont-sec-w">

                        <c:forEach var="qstnList" items="${rtnSurveyData.svSurveyQstnDtlList}" varStatus="qstnStatus">
                            <c:if test="${cd ne qstnList.cd && qstnStatus.count ne 1 && qstnList.dpth eq '1'}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                           </c:if>
                            <c:if test="${cd ne qstnList.cd && qstnList.dpth eq '1'}">
                            <div class="cont-sec">
                                <div class="for-mootion">
                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p><!-- 2023-12-26 추가 --><!-- 2024-01-09 마크업 위치 이동 -->
                                    <div class="sec-tit-area">
                                        <c:if test="${qstnList.cd eq 'CON0101' || qstnList.cd eq 'CON0201'}"><p class="sec-tit">${qstnList.parentCdNm}</p></c:if>
                                        <p class="sec-tit sub-tit">${qstnList.cdNm}</p><!-- 2024-01-09 sub-tit 클래스 추가 -->
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="survey-list-w">
                            </c:if>
                                <c:choose>
                                <c:when test="${qstnList.dpth eq '1'}">
                                <c:set var="dpthCnt" value="0"/>
                                <c:set var="qstnCnt" value="${qstnList.qstnCnt}"/>
                                <div class="survey-list">
                                </c:when>
                                <c:otherwise>
                                    <c:set var="dpthCnt" value="${dpthCnt+1}"/>
                                </c:otherwise>
                                </c:choose>
                                    <div class="survey-list-inner surveyList ${qstnList.cd}" data-survey-type="${qstnList.cd}" <c:if test="${qstnList.dpth eq '2'}">style="display:none"</c:if>>
                                        <input type="hidden" name="dpth" value="${qstnList.dpth}">
                                        <input type="hidden" name="qstnSeq" value="${qstnList.qstnSeq}">
                                        <input type="hidden" name="srvTypeCd" value="${qstnList.srvTypeCd}">

                                    <div class="question-tit">
                                        <p class="number ${qstnList.cd}questionTxt">Q1</p>
                                        <p class="tit f-sub-head">${qstnList.qstnNm} <c:if test="${qstnList.ncsYn eq 'Y' && qstnList.dpth eq '1'}"><span class="essential-mark color-sky">*</span></c:if></p>
                                    </div>
                                    <div class="survey-con">
                                        <c:choose>
                                            <c:when test="${qstnList.srvTypeCd eq 'QST01'}">    <!--객관식단일-->
                                                <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                    <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                        <div class="form-radio exmplList">
                                                            <input type="radio" class="${notRequired} answer" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="필수 응답문항" data-next-no ="${exmplList.nextNo}">
                                                            <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </c:when>
                                            <c:when test="${qstnList.srvTypeCd eq 'QST02'}">    <!--객관식복수-->
                                                <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                    <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                        <div class="form-checkbox exmplList">
                                                            <input type="checkbox" class="${notRequired} answer" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="필수 응답문항" data-next-no ="${exmplList.nextNo}">
                                                            <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </c:when>
                                            <c:when test="${qstnList.srvTypeCd eq 'QST03'}">    <!--주관식단답-->
                                                <div class="form-input w-full exmplList">
                                                    <input type="text" placeholder="답변을 작성해주세요." class="${notRequired} answer" name="answer${qstnList.qstnSeq}" title="필수 응답문항">
                                                </div>
                                            </c:when>
                                            <c:when test="${qstnList.srvTypeCd eq 'QST04'}">    <!--주관식서술-->
                                                <div class="form-textarea exmplList">
                                                    <textarea name="answer${qstnList.qstnSeq}" class="${notRequired} answer" id="" cols="" rows="" placeholder="답변을 작성해주세요." title="필수 응답문항" maxlength="2000"></textarea>
                                                    <div class="check-byte">
                                                        <p class="txt"><span class="current-byte">0</span>자</p>
                                                        <p class="txt"><span class="max-byte">2,000</span>자</p>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:when test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">    <!--척도-->
                                                <div class="criterion-form">
                                                    <div class="criterion-list-w">
                                                        <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                            <div class="form-radio exmplList">
                                                                <input type="radio" id="criterionOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" class="${notRequired} answer" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="필수 응답문항" data-next-no ="${exmplList.nextNo}">
                                                                <label for="criterionOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplOrd}</label>
                                                            </div>
                                                            <c:if test="${exmplStatus.first}">
                                                                <c:set var="firstText" value="${exmplList.exmplNm}"/>
                                                            </c:if>
                                                            <c:if test="${exmplStatus.last}">
                                                                <c:set var="lastText" value="${exmplList.exmplNm}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="criterion-guide-txt">
                                                        <div class="txt">${firstText}</div>
                                                        <div class="txt">${lastText}</div>
                                                    </div>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            <c:if test="${qstnCnt eq dpthCnt}">
                            </div>
                            </c:if>
                            <c:set var="cd" value="${ qstnList.cd}" />
                        </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            </form>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small gray-bg" href="/my-page/consulting/surveyIndex?detailsKey=${rtnData.cnstgSeq}"><span>목록</span></a><!-- 2023-12-22 추가 -->
                    </div>
                    <div class="btn-set">
                        <a class="btn-solid small black-bg" href="javascript:" id="btnSubmit"><span>제출하기</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->