<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- content 영역 start -->
<div class="cont-wrap" data-controller="controller/wb/wbl/WBLSurveyStep2Ctrl">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">상생협력 체감도 조사</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <div class="evaluation-category">
                <div class="left">
                    <p class="subject-tit f-title3">상생협력 체감도 조사</p>
                    <div class="sort-label-area">
                        <p class="label"><span>대상업체 ${rtnData.partCmpnNm1}</span></p>
                        <p class="label"><span>수행 ${rtnData.partCmpnNm2}</span></p>
                    </div>
                </div>
                <div class="right">
                    <div class="status-info-w">
                        <p class="box-label bigger"><span>${fn:substring(rtnData.year,0,4)}년 ${rtnData.episd}차</span></p>
                        <p class="box-label bigger teal"><span>${rtnData.qstnCnt}문항</span></p>
                    </div>
                </div>
            </div>
            <form class="form-horizontal" id="frmData" name="frmData" method="post" >
                <input type="hidden" name="srvSeq" value="${rtnData.srvSeq}">
                <input type="hidden" name="rfncCd" value="WIN">

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
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="sec-tit">${qstnList.cdNm}</p>
                            </div>
                            <div class="sec-con-area">
                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p><!-- 2023-12-26 추가 -->
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
                                            <c:choose>
                                                <c:when test="${qstnList.ncsYn eq 'Y' && qstnList.dpth eq '1'}">
                                                    <c:set var="notRequired" value="" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="notRequired" value="notRequired" />
                                                </c:otherwise>
                                            </c:choose>

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
                                                                    <input type="radio" class="${notRequired} answer" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="${qstnList.qstnNm}" data-next-no ="${exmplList.nextNo}">
                                                                    <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${qstnList.srvTypeCd eq 'QST02'}">    <!--객관식복수-->
                                                        <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                            <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                                <div class="form-checkbox exmplList">
                                                                    <input type="checkbox" class="${notRequired} answer" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="${qstnList.qstnNm}" data-next-no ="${exmplList.nextNo}">
                                                                    <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${qstnList.srvTypeCd eq 'QST03'}">    <!--주관식단답-->
                                                        <div class="form-input w-full exmplList">
                                                            <input type="text" placeholder="답변을 작성해주세요." class="${notRequired} answer" name="answer${qstnList.qstnSeq}" title="${qstnList.qstnNm}">
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${qstnList.srvTypeCd eq 'QST04'}">    <!--주관식서술-->
                                                        <div class="form-textarea exmplList">
                                                            <textarea name="answer${qstnList.qstnSeq}" class="${notRequired} answer" id="" cols="" rows="" placeholder="답변을 작성해주세요." title="${qstnList.qstnNm}"></textarea>
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
                                                                        <input type="radio" id="criterionOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" class="${notRequired} answer" name="answer${qstnList.qstnSeq}" value="${exmplList.exmplSeq}" title="${qstnList.qstnNm}" data-next-no ="${exmplList.nextNo}">
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
                        <a class="btn-solid small gray-bg" href="javascript:"><span>목록</span></a>
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