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
                                <p class="f-title3">ESG 종합지원 플랫폼 사업 개요</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="txt-sec">
                                    <div class="paragraph">
                                        <p class="f-sub-head">
                                            자동차부품산업진흥재단은 국내외 자동차부품 협력사를 대상으로 공급망 실사 및 ESG 종합 플랫폼 지원 사업을 추진하고 있습니다.<br>
                                            협력사의 ESG 역량 강화를 위한 체계적인 지원과 다양한 자원을 제공하는 것을 목표로 하고 있으며,<br>
                                            사업 상세 내용은 추후 공지 예정입니다.
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <br/>
                            <div class="sec-tit-area">
                                <p class="f-title3">Coming Soon</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="txt-sec">
                                    <div class="paragraph">
                                        <p class="f-sub-head">
                                            재단은 플랫폼의 정식 오픈 전까지 협력사들의 ESG 관련 문의를 접수받아 신속하고 정확한 답변을 제공할 예정입니다.<br>
                                            협력사에서 고민 중인 ESG 관련 모든 문의 사항(법률 자문 포함)을 편하게 아래 1:1 문의 창구를 통해 접수해 주시기 바랍니다.
                                        </p>
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
                                                                    <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                        <c:if test="${fn:length(cdList.cd) < 6 and fn:contains(cdList.cdNm, 'ESG종합지원')}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnData.parntCtgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" data-name="ctgryCd" id="ctgryCd" name="ctgryCd">
                                                                <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                                    <c:if test="${fn:length(cdList.cd) >= 6 and fn:contains(cdList.cdNm, 'ESG종합지원')}">
                                                                        <option value="${cdList.cd}" <c:if test="${rtnData.parntCtgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                    </c:if>
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
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능한 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 최대 업로드 용량(50MB), 최대 1개 파일 등록 가능</P>
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
                                                                <input type="file" id="searchFile" name="atchFile" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip" class="fileInput notRequired"/>
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