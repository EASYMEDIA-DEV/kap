<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- content 영역 start -->
<div class="cont-wrap">
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
                        <p class="label"><span>수행 동일전공</span></p>
                    </div>
                </div>
                <div class="right">
                    <div class="status-info-w">
                        <p class="box-label bigger"><span>${fn:substring(rtnData.year,0,4)}년 ${rtnData.episd}차</span></p>
                        <p class="box-label bigger teal"><span>${rtnData.qstnCnt}문항</span></p>
                    </div>
                </div>
            </div>
            <div class="survey-form">
                <div class="cont-sec-w">

                    <c:forEach var="qstnList" items="${rtnSurveyData.svSurveyQstnDtlList}" varStatus="qstnStatus">
                        <c:if test="${cd ne qstnList.cd && qstnStatus.count ne 1}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${cd ne qstnList.cd}">
                        <div class="cont-sec">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="sec-tit">${qstnList.cdNm}</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="survey-list-w">
                        </c:if>

                                    <div class="survey-list">
                                        <div class="question-tit">
                                            <p class="number">Q1</p>
                                            <p class="tit f-sub-head">${qstnList.qstnNm}</p>
                                        </div>
                                        <div class="survey-con">
                                            <c:choose>
                                                <c:when test="${qstnList.srvTypeCd eq 'QST01'}">    <!--객관식단일-->
                                                    <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                        <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                            <div class="form-radio">
                                                                <input type="radio" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnOrd}">
                                                                <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:when>
                                                <c:when test="${qstnList.srvTypeCd eq 'QST02'}">    <!--객관식복수-->
                                                    <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                        <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                            <div class="form-checkbox">
                                                                <input type="checkbox" id="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="answer${qstnList.qstnOrd}">
                                                                <label for="answerOption${qstnList.qstnOrd}-${exmplList.exmplOrd}">${exmplList.exmplNm}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:when>
                                                <c:when test="${qstnList.srvTypeCd eq 'QST03'}">    <!--주관식단답-->
                                                    <div class="form-input w-full">
                                                        <input type="text" placeholder="답변을 작성해주세요." name="answer${qstnList.qstnOrd}">
                                                    </div>
                                                </c:when>
                                                <c:when test="${qstnList.srvTypeCd eq 'QST04'}">    <!--주관식서술-->
                                                    <div class="form-textarea">
                                                        <textarea name="answer${qstnList.qstnOrd}" id="" cols="" rows="" placeholder="답변을 작성해주세요."></textarea>
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
                                                                <div class="form-radio">
                                                                    <input type="radio" id="criterionOption${qstnList.qstnOrd}-${exmplList.exmplOrd}" name="criterionOption01">
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

                     <c:set var="cd" value="${ qstnList.cd}" />
                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small gray-bg" href="javascript:"><span>목록</span></a>
                    </div>
                    <div class="btn-set">
                        <a class="btn-solid small black-bg" href="javascript:"><span>제출하기</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->