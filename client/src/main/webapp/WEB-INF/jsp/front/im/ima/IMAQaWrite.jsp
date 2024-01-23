<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/im/ima/IMAQaWriteCtrl controller/co/COFormCtrl">

    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-Inquiry.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-Inquiry-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->
        <form id="frmData" name="frmData" enctype="multipart/form-data">
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="parntCtgryNm" name="parntCtgryNm" value="" />
            <input type="hidden" id="ctgryNm" name="ctgryNm" value="" />

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">자동차부품산업진흥재단에 궁금한점을 문의해보세요.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="graphic-sec">
                                    <div class="guide-step-w has-border"><!-- has-border: 테두리 있을 때 -->
                                        <div class="step-list">
                                            <p class="step-num">STEP 01</p>
                                            <div class="step-info">
                                                <p class="step-con">문의등록</p>
                                                <p class="box-label bigger"><span>접수대기</span></p>
                                            </div>
                                        </div>
                                        <div class="step-list">
                                            <p class="step-num">STEP 02</p>
                                            <div class="step-info">
                                                <p class="step-con">담당자 확인 및 답변등록</p>
                                                <p class="box-label bigger waiting"><span>접수완료</span></p>
                                            </div>
                                        </div>
                                        <div class="step-list">
                                            <p class="step-num">STEP 03</p>
                                            <div class="step-info">
                                                <p class="step-con">답변 확인</p>
                                                <p class="box-label bigger complete"><span>답변완료</span></p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="divide-flex-box v-align-center narrow-mt">
                                        <div class="left">
                                            <p class="icon-txt tel">
                                                <span>대표전화</span>
                                                <a href="tel:02-3271-2965">02-3271-2965</a>
                                            </p>
                                        </div>
                                        <div class="btn-wrap">
                                            <a class="btn-text-icon black-circle" href="/my-page/member/qa/list"><span>나의 1:1 문의</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">문의내용</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">문의유형<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" data-name="parntCtgryCd" id="parntCtgryCd" name="parntCtgryCd">
                                                                <option value="">선택</option>
                                                                <c:choose>
                                                                    <c:when test="${ not empty rtnData.inqSec and empty rtnData.inqFir }">
                                                                        <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                            <c:if test="${fn:length(cdList.cd) < 6}">
                                                                                <option value="${cdList.cd}" <c:if test="${fn:contains(cdList.cd, 'INQ02')}">selected</c:if>>${cdList.cdNm}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:when>
                                                                    <c:when test="${ not empty rtnData.inqSec and not empty rtnData.inqFir }">
                                                                        <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                            <c:if test="${fn:length(cdList.cd) < 6}">
                                                                                <option value="${cdList.cd}" <c:if test="${fn:contains(cdList.cd, rtnData.inqFir)}">selected</c:if>>${cdList.cdNm}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                            <c:if test="${fn:length(cdList.cd) < 6}">
                                                                                <option value="${cdList.cd}" <c:if test="${rtnData.parntCtgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </select>
                                                        </div>
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" data-name="ctgryCd" id="ctgryCd" name="ctgryCd">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                <c:choose>
                                                                    <c:when test="${ not empty rtnData.inqSec and empty rtnData.inqFir }">
                                                                        <option class="ctgryCd" <c:if test="${ not fn:contains(cdList.cd, 'INQ02') }">style="display: none;"</c:if> value="${cdList.cd}" <c:if test="${rtnData.inqSec eq cdList.cdNm}">selected</c:if>>${cdList.cdNm}</option>
                                                                    </c:when>
                                                                    <c:when test="${ not empty rtnData.inqSec and not empty rtnData.inqFir }">
                                                                        <option class="ctgryCd" <c:if test="${ not fn:contains(cdList.cd, rtnData.inqFir) }">style="display: none;"</c:if> value="${cdList.cd}" <c:if test="${rtnData.inqSec eq cdList.cdNm}">selected</c:if>>${cdList.cdNm}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option class="ctgryCd" style="display: none;" value="${cdList.cd}" <c:if test="${rtnData.ctgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">제목<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-full">
                                                            <input type="text" placeholder="제목입력" id="titl" name="titl" maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">내용<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-textarea">
                                                            <textarea name="cntn" id="cntn" cols="" rows="" placeholder="내용입력" maxlength="500"></textarea>
                                                            <div class="check-byte">
                                                                <p class="txt"><span class="current-byte" id="cntnCnt">0</span>자</p>
                                                                <p class="txt"><span class="max-byte">500</span>자</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능한 확장자(pdf, ppt, pptx, doc, docx, xls, xlsx, zip) / 최대 업로드 용량(50MB), 최대 5개 파일 등록 가능</P>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <div class="form-group">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                <%--<div class="file-list">
                                                                    <p class="file-name">
                                                                        <span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span>
                                                                        <span class="unit">pdf</span>
                                                                    </p>
                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                </div>--%>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" id="searchFile" name="atchFile" accept="pdf,ppt,pptx,doc,docx,xls,xlsx,zip" class="fileInput notRequired" multiple />
                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="javascript:" id="qaSubmit"><span>문의등록</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>