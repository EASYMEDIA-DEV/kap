<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBSupplyWriteCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item active"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData" id="frmData">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnInfo.bsnCd}" />
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">신청</p>
                                </div>
                                <c:choose>
                                    <c:when test="${rtnData.appctnSttsCdNm eq '접수완료' || rtnData.appctnSttsCdNm eq '보완완료' || rtnData.appctnSttsCdNm eq '선정'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rtnData.appctnSttsCdNm eq '보완요청' || rtnData.appctnSttsCdNm eq '미선정'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                    <input type="hidden" id="appctnSttsCd" name="appctnSttsCd" value="">
                                    ${rtnData.appctnSttsCdNm}
                                </span></p>
                            </a>
                            <div class="acco-hide-area">
                                <p class="exclamation-txt f-body1">
                                    <c:if test="${rtnData.appctnSttsCd eq 'PRO_TYPE06001_01_002' or rtnData.appctnSttsCd eq 'PRO_TYPE06001_01_004'}">
                                        ${rtnData.rtrnRsnCntn}
                                    </c:if>
                                </p>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnData.appctnSttsCd eq 'PRO_TYPE06001_01_002'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile" id="searchFile" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnData.appctnSeq}"/>
                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                            </div>
                                                        </c:if>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnData.appctnSeq}&fileOrd=0" download="" title="파일 다운로드">${rtnData.fileNm}</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnData.appctnSttsCd eq 'PRO_TYPE06001_01_002'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
