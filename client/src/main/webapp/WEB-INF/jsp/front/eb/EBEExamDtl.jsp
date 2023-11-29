<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">평가하기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box" data-controller="controller/eb/EBEExamWriteCtrl">
            <div class="evaluation-category">
                <div class="left">
                    <p class="subject-tit f-title3">${ rtnData.nm }</p>
                    <div class="sort-label-area">
                        <c:set var="ctgryCdNmList" value="${ fn:split(rtnData.ctgryCdNm, '>')}" />
                        <c:forEach var="cdNm" items="${ctgryCdNmList}" varStatus="status">
                            <p class="label"><span>${ cdNm }</span></p>
                        </c:forEach>
                    </div>
                </div>
                <div class="right">
                    <div class="status-info-w">
                        <p class="box-label bigger"><span>${ rtnData.episdYear }년 ${ rtnData.episdOrd }차</span></p>
                        <p class="box-label bigger"><span>${ rtnData.cbsnCdNm }</span></p>
                        <c:set var="isttrNmList" value="${ fn:split(rtnData.isttrNm, '>')}" />
                        <c:forEach var="cdNm" items="${isttrNmList}" varStatus="status">
                            <p class="box-label bigger"><span>${ cdNm }</span></p>
                        </c:forEach>
                        <p class="box-label bigger teal"><span>${ rtnData.qstnCnt }문항</span></p>
                    </div>
                </div>
            </div>
            <form method="post">
                <input type="hidden" class="notRequired" name="ptcptSeq" value="${rtnData.ptcptSeq}" />
                <input type="hidden" class="notRequired" name="examSeq" value="${rtnData.examSeq}" />
                <div class="survey-form">
                    <div class="cont-sec-w">
                        <div class="cont-sec">
                            <div class="for-mootion">
                                <div class="sec-con-area">
                                    <div class="survey-list-w">
                                        <c:choose>
                                            <c:when test="${ rtnExamData.examSeq != null }">
                                                <c:forEach var="qstnList" items="${rtnExamData.exExamQstnDtlList}" varStatus="qstnStatus">
                                                    <div class="survey-list" data-srv-type-cd="${qstnList.srvTypeCd}">
                                                        <input type="hidden" class="notRequired" name="qstnSeq" value="${qstnList.qstnSeq}" />
                                                        <div class="question-tit">
                                                            <p class="number">Q${qstnList.qstnOrd}</p>
                                                            <p class="tit f-sub-head">${ qstnList.qstnNm } <span>(${qstnList.scord}점)</span></p>
                                                        </div>
                                                        <div class="survey-con">
                                                            <c:choose>
                                                                <c:when test="${ qstnList.srvTypeCd eq 'EXG_A' or qstnList.srvTypeCd eq 'EXG_B' }">
                                                                    <div class="opt-group verticality">
                                                                        <c:forEach var="exmplList" items="${qstnList.exExamExmplDtlList}" varStatus="exmplStatus">
                                                                            <div class="form-${kl:decode(qstnList.srvTypeCd, 'EXG_A', 'radio', 'checkbox')}">
                                                                                <input type="${kl:decode(qstnList.srvTypeCd, 'EXG_A', 'radio', 'checkbox')}" name="answerOption${qstnList.qstnOrd}" id="answerOption${qstnList.qstnOrd}_${exmplStatus.index}" value="${exmplList.exmplSeq}" title="필수 응답문항">
                                                                                <label for="answerOption${qstnList.qstnOrd}_${exmplStatus.index}">${ exmplList.exmplNm }</label>
                                                                            </div>

                                                                        </c:forEach>
                                                                    </div>
                                                                </c:when>
                                                                <c:when test="${ qstnList.srvTypeCd eq 'EXG_C'}">
                                                                    <div class="form-input w-full">
                                                                        <input type="text" placeholder="답변을 작성해주세요." title="필수 응답문항 " name="sbjctRply">
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="form-textarea">
                                                                        <textarea name="sbjctRply" cols="" rows="" class="textAreaSbjctRply" placeholder="답변을 작성해주세요." title="필수 응답문항" maxlength="2000"></textarea>
                                                                        <div class="check-byte">
                                                                            <p class="txt"><span class="current-byte maxlengthText">0</span>자</p>
                                                                            <p class="txt"><span class="max-byte">2,000</span>자</p>
                                                                        </div>
                                                                    </div>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
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