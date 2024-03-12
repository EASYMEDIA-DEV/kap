<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnData" value="${rtnData}" />

<c:set var="rtnDtl" value="${rtnData.rsumeDtlList}" />
<c:set var="rtnSpprt" value="${rtnData.spprtList}" />
<c:set var="rtnMem" value="${rtnData.memList[0]}" />
<c:set var="rtnCmssr" value="${rtnData.memList[1]}" />
<c:set var="rtnCompany" value="${rtnData.companyDtl}" />
<c:set var="rtnSQ" value="${rtnCompany.dtlList}" />
<c:set var="rtnPbsn" value="${rtnData.pbsnDtlList}" />
<c:set var="rtnFile" value="${rtnData.fileDtlList}" />

<c:set var="maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBSafetyWriteCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
            <div class="btn-wrap">
                <c:if test="${
                    (rtnSpprt[0].appctnSttsCd eq 'PRO_TYPE03001_01_003' and rtnData.pmndvPmtYn eq 'Y') or
                    rtnSpprt[1].appctnSttsCd eq 'PRO_TYPE03002_01_003'
                }">
                    <p class="box-label bigger arr"><span>보완요청</span></p>
                </c:if>
                <a class="btn-text-icon black-arrow" id="popOpen" href="javascript:" title="팝업 열기"><span>지급정보관리</span></a>
            </div>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item <c:if test="${rtnData.maxRsumeOrd eq 1}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData1" id="frmData1">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" id="mngSttsCd1" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd1" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[0].appctnSttsCd}">

                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">신청</p>
                                </div>
                                <c:if test="${not empty rtnDtl[0].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnDtl[0].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[0].appctnSttsNm eq '접수완료' || rtnDtl[0].appctnSttsNm eq '보완완료' || rtnDtl[0].appctnSttsNm eq '선정'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[0].appctnSttsNm eq '보완요청' || rtnDtl[0].appctnSttsNm eq '미선정'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                        <c:when test="${rtnDtl[0].appctnSttsNm eq '사용자취소'}">
                                            <c:set var="classType" value="end" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnDtl[0].appctnSttsNm}
                                    </span></p>
                                </c:if>
                                <c:if test="${rtnDtl[0].appctnSttsCd ne 'PRO_TYPE01001_01_002'}">
                                    <c:set var="readonly1" value="readonly" />
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnDtl[0].rtrnRsnCntn }">
                                    <p class="exclamation-txt f-body1">${rtnDtl[0].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                        </div>

                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1"><span>종된사업장번호</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="sbrdnBsnmNo" name="wBDBSafetyMstInsertDTO.sbrdnBsnmNo" placeholder="종된사업장번호 입력" value="${rtnData.sbrdnBsnmNo}" maxlength="4" ${readonly1}>
                                                        </div>
                                                    </div>
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* 종된사업장별로 사업 신청이 가능합니다.</p>
                                                    </div>
                                                </div>

                                                <div class="data-line">
                                                    <div class="data-line">
                                                        <p class="data-title f-body1"><span>구축사업장</span><span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="text" id="zipCode" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].pbsnZipcode" placeholder="우편번호" value="${rtnPbsn[0].pbsnZipcode}" readonly="">
                                                            </div>
                                                            <div class="form-input w-longer">
                                                                <input type="text" id="bscAddr" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].pbsnBscAddr" placeholder="주소" value="${rtnPbsn[0].pbsnBscAddr}" readonly="">
                                                            </div>
                                                            <c:if test="${empty readonly1}">
                                                            <div class=" ">
                                                                <button class="btn-solid small gray-bg" id="searchPostCode" type="button"><span>우편번호 찾기</span></button>
                                                            </div>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longest">
                                                                <input type="text" id="dtlAddr" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].pbsnDtlAddr" placeholder="상세주소 입력" value="${rtnPbsn[0].pbsnDtlAddr}" maxlength="100" ${readonly1}>
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
                                                    <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[0].appctnSttsCd eq 'PRO_TYPE01001_01_002'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile1" id="searchFile1" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnFile[0].fileSeq}"/>
                                                                <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE01">
                                                            </div>
                                                        </c:if>
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnFile[0].fileSeq}&fileOrd=${rtnFile[0].fileOrd}" download="" title="파일 다운로드">${rtnFile[0].fileNm}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnDtl[0].appctnSttsCd eq 'PRO_TYPE01001_01_002'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="1" data-status="${rtnDtl[0].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>

                    <div class="list-item <c:if test="${rtnData.maxRsumeOrd eq 2}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData2" id="frmData2">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].bsnPlanDt" value="${today}">

                            <input type="hidden" id="mngSttsCd2" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[1].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd2" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[1].appctnSttsCd}">

                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />

                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">사업계획</p>
                                </div>
                                <c:if test="${not empty rtnDtl[1].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnDtl[1].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[1].appctnSttsNm eq '접수완료' || rtnDtl[1].appctnSttsNm eq '보완완료' || rtnDtl[1].appctnSttsNm eq '적합'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[1].appctnSttsNm eq '보완요청' || rtnDtl[1].appctnSttsNm eq '부적합'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnDtl[1].appctnSttsNm}
                                    </span></p>
                                </c:if>
                                <c:if test="${rtnDtl[1].appctnSttsCd ne 'PRO_TYPE01002_01_001' && rtnDtl[1].appctnSttsCd ne 'PRO_TYPE01002_01_003'}">
                                    <c:set var="readonly2" value="readonly" />
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnDtl[1].rtrnRsnCntn }">
                                    <p class="exclamation-txt f-body1">${rtnDtl[1].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                        </div>

                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">지원금 ①<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="spprtPmt" class="comma" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].spprtPmt" placeholder="지원금 입력" value="${rtnPbsn[1].spprtPmt}" ${readonly2} maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">자부담 ②<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="phswPmt" class="comma" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].phswPmt" placeholder="자부담 입력" value="${rtnPbsn[1].phswPmt}" ${readonly2} maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">총금액 ① + ②<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="sum" class="comma" placeholder="총금액 입력" value="${rtnPbsn[1].ttlPmt}" readonly>
                                                            <input type="hidden" class="notRequired" id="ttlPmt" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].ttlPmt" value="${rtnPbsn[1].ttlPmt}"/>
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
                                                    <p class="data-title f-body1">사업계획서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_001' or rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_003'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFilee2" id="searchFile2" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnFile[1].fileSeq}"/>
                                                                <label class="btn-solid gray-bg" for="searchFile2">파일 찾기</label>
                                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE08">
                                                            </div>
                                                        </c:if>
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnFile[1].fileSeq}&fileOrd=${rtnFile[1].fileOrd}" download="" title="파일 다운로드">${rtnFile[1].fileNm}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_001' or rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="2" data-status="${rtnDtl[1].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>

                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">최초점검</p>
                            </div>
                            <c:if test="${not empty rtnDtl[2].appctnSttsNm}">
                                <c:choose>
                                    <c:when test="${rtnDtl[2].appctnSttsNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rtnDtl[2].appctnSttsNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rtnDtl[2].appctnSttsNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rtnDtl[2].appctnSttsNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>

                    <div class="list-item <c:if test="${rtnData.maxRsumeOrd eq 4}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData4" id="frmData4">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].cmpltnBrfngDt" value="${today}">

                            <input type="hidden" id="mngSttsCd4" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[3].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd4" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[3].appctnSttsCd}">

                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />

                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">완료보고</p>
                                </div>
                                <c:if test="${not empty rtnDtl[3].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnDtl[3].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[3].appctnSttsNm eq '접수완료' || rtnDtl[3].appctnSttsNm eq '보완완료' || rtnDtl[3].appctnSttsNm eq '적합'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[3].appctnSttsNm eq '보완요청' || rtnDtl[3].appctnSttsNm eq '부적합'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnDtl[3].appctnSttsNm}
                                    </span></p>
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnDtl[3].rtrnRsnCntn }">
                                    <p class="exclamation-txt f-body1">${rtnDtl[3].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">완료보고서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_001' or rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_003'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile4" id="searchFile4" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnFile[3].fileSeq}"/>
                                                                <label class="btn-solid gray-bg" for="searchFile4">파일 찾기</label>
                                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE10">
                                                            </div>
                                                        </c:if>
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnFile[3].fileSeq}&fileOrd=${rtnFile[3].fileOrd}" download="" title="파일 다운로드">${rtnFile[3].fileNm}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_001' or rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="4" data-status="${rtnDtl[3].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>

                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">최종점검</p>
                            </div>
                            <c:if test="${not empty rtnDtl[4].appctnSttsNm}">
                                <c:choose>
                                    <c:when test="${rtnDtl[4].appctnSttsNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rtnDtl[4].appctnSttsNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rtnDtl[4].appctnSttsNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rtnDtl[4].appctnSttsNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>

                    <div class="list-item <c:if test="${rtnData.maxRsumeOrd eq 6}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData4" id="frmData6">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBDBSafetyMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBDBSafetyMstInsertDTO.pbsnDtlList[0].cmpltnBrfngDt" value="${today}">

                            <input type="hidden" id="mngSttsCd6" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[5].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd6" name="wBDBSafetyMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[5].appctnSttsCd}">

                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />

                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">검수보고</p>
                                </div>
                                <c:if test="${not empty rtnDtl[5].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnDtl[5].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[5].appctnSttsNm eq '접수완료' || rtnDtl[5].appctnSttsNm eq '보완완료' || rtnDtl[5].appctnSttsNm eq '적합'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnDtl[5].appctnSttsNm eq '보완요청' || rtnDtl[5].appctnSttsNm eq '부적합'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnDtl[5].appctnSttsNm}
                                    </span></p>
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnDtl[5].rtrnRsnCntn }">
                                    <p class="exclamation-txt f-body1">${rtnDtl[5].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">검수보고서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_001' or rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_005'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile6" id="searchFile6" class="searchFile">
                                                                <label class="btn-solid gray-bg" for="searchFile6">파일 찾기</label>
                                                                <input type="hidden" name="fileSeqList" value="${rtnFile[5].fileSeq}"/>
                                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE09">
                                                            </div>
                                                        </c:if>
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnFile[5].fileSeq}&fileOrd=${rtnFile[5].fileOrd}" download="" title="파일 다운로드">${rtnFile[5].fileNm}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_001' or rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_005'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="6" data-status="${rtnDtl[5].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- 지급정보관리 팝업 -->
    <div class="layer-popup paymentInfoManagPopup">
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">지급정보관리</p>
                    </div>
                    <div class="tab-btn-area">
                        <div class="txt-tab-swiper func-tab">
                            <div class="swiper-container">
                                <div class="swiper-wrapper">
                                    <c:if test="${rtnData.pmndvPmtYn eq 'Y'}">
                                        <a class="swiper-slide txt-tab-btn <c:if test="${rtnData.pmndvPmtYn eq 'Y'}">active</c:if>" href="javascript:">
                                            <p class="txt"><span class="menu-name <c:if test="${rtnSpprt[0].appctnSttsCd eq 'PRO_TYPE03001_01_003'}">privacy</c:if>">선급금</span></p>
                                        </a>
                                    </c:if>
                                    <a class="swiper-slide txt-tab-btn <c:if test="${rtnData.pmndvPmtYn eq 'N'}">active</c:if>" href="javascript:">
                                        <p class="txt"><span class="menu-name <c:if test="${rtnSpprt[1].appctnSttsCd eq 'PRO_TYPE03002_01_003'}">privacy</c:if>">지원금</span></p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="tab-con-w">
                                <div class="tab-con-box">
                                    <!-- 선급금 탭 -->
                                    <c:if test="${rtnData.pmndvPmtYn eq 'Y'}">
                                        <div id="spprt1" class="tab-con" <c:if test="${rtnSpprt[0].appctnSttsCd eq 'PRO_TYPE03001_01_005'}">style="pointer-events: none;"</c:if>>
                                            <form name="spprtform1" id="spprtform1">
                                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                                                <input type="hidden" class="notRequired" name="wBDBSafetyMstInsertDTO.spprtList[0].appctnSpprtSeq" value="${rtnSpprt[0].appctnSpprtSeq}" />
                                                <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                                                <input type="hidden" name="wBDBSafetyMstInsertDTO.spprtList[0].giveType" value="${rtnSpprt[0].giveType}" />

                                                <input type="hidden" id="spprtAppctnSttsCd1" name="wBDBSafetyMstInsertDTO.spprtList[0].appctnSttsCd" value="${rtnSpprt[0].appctnSttsCd}" />
                                                <input type="hidden" id="spprtMngSttsCd1" name="wBDBSafetyMstInsertDTO.spprtList[0].mngSttsCd" value="${rtnSpprt[0].mngSttsCd}" />
                                                <input type="hidden" class="tabFlag" value="${not empty rtnSpprt[0].acntNo ? 'update' : 'insert'}"/>

                                                <div class="tab-con-area">
                                                    <div class="p-cont-sec">
                                                        <div class="sec-tit-area">
                                                            <p class="f-head">지급정보를 입력해주세요</p>
                                                        </div>
                                                        <div class="sec-con-area">
                                                            <div class="data-enter-form">
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">접수일<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input calendar">
                                                                                        <input type="text" id="accsDt1" class="datetimepicker_input" name="wBDBSafetyMstInsertDTO.spprtList[0].accsDt" value="${kl:convertDate(rtnSpprt[0].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"  placeholder="날짜 선택">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">은행명<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" id="bankNm1" name="wBDBSafetyMstInsertDTO.spprtList[0].bankNm" placeholder="은행 입력" value="${rtnSpprt[0].bankNm}" maxlength="50">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">계좌번호<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" id="acntNo1" name="wBDBSafetyMstInsertDTO.spprtList[0].acntNo" placeholder="계좌번호 입력" value="${rtnSpprt[0].acntNo}" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="50">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">예금주<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" id="dpsitNm1" name="wBDBSafetyMstInsertDTO.spprtList[0].dpsitNm" placeholder="예금주 입력" value="${rtnSpprt[0].dpsitNm}" maxlength="50">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="p-cont-sec">
                                                        <div class="sec-tit-area">
                                                            <p class="f-head">파일을 첨부해주세요</p>
                                                        </div>
                                                        <div class="sec-con-area">
                                                            <div class="data-enter-form">
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">지원금신청서<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" name="spprtAppctnFileSeq" id="spprtAppctnFileSeq" class="searchFile">
                                                                                        <input type="hidden" name="fileSeqList" value="${rtnSpprt[0].spprtAppctnFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="spprtAppctnFileSeq">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area" <c:if test="${not empty rtnSpprt[0].spprtAppctnFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                        <a href="/file/download?fileSeq=${rtnSpprt[0].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[0].spprtAppctnFileOrd}" download="" title="파일 다운로드">${rtnSpprt[0].spprtAppctnFileNm}</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">협약서<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" name="agrmtFileSeq" id="agrmtFileSeq" class="searchFile">
                                                                                        <input type="hidden" name="fileSeqList" value="${rtnSpprt[0].agrmtFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="agrmtFileSeq">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area" <c:if test="${not empty rtnSpprt[0].agrmtFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                        <a href="/file/download?fileSeq=${rtnSpprt[0].agrmtFileSeq}&fileOrd=${rtnSpprt[0].agrmtFileOrd}" download="" title="파일 다운로드">${rtnSpprt[0].agrmtFileNm}</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">보증보험증<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" name="grnteInsrncFileSeq" id="grnteInsrncFileSeq" class="searchFile">
                                                                                        <input type="hidden" name="fileSeqList" value="${rtnSpprt[0].grnteInsrncFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="grnteInsrncFileSeq">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area" <c:if test="${not empty rtnSpprt[0].grnteInsrncFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                        <a href="/file/download?fileSeq=${rtnSpprt[0].grnteInsrncFileSeq}&fileOrd=${rtnSpprt[0].grnteInsrncFileOrd}" download="" title="파일 다운로드">${rtnSpprt[0].grnteInsrncFileNm}</a>
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
                                            </form>
                                        </div>
                                    </c:if>

                                    <!-- 지원금 탭 -->
                                    <div id="spprt2" class="tab-con" <c:if test="${rtnSpprt[1].appctnSttsCd eq 'PRO_TYPE03002_01_005'}">style="pointer-events: none;"</c:if>>
                                        <form name="spprtform2" id="spprtform2">
                                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <input type="hidden" name="wBDBSafetyMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                                            <input type="hidden" class="notRequired" name="wBDBSafetyMstInsertDTO.spprtList[0].appctnSpprtSeq" value="${rtnSpprt[1].appctnSpprtSeq}" />
                                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                                            <input type="hidden" name="wBDBSafetyMstInsertDTO.spprtList[0].giveType" value="${rtnSpprt[1].giveType}" />

                                            <input type="hidden" id="spprtAppctnSttsCd2" name="wBDBSafetyMstInsertDTO.spprtList[0].appctnSttsCd" value="${rtnSpprt[1].appctnSttsCd}" />
                                            <input type="hidden" id="spprtMngSttsCd2" name="wBDBSafetyMstInsertDTO.spprtList[0].mngSttsCd" value="${rtnSpprt[1].mngSttsCd}" />
                                            <input type="hidden" class="tabFlag" value="${not empty rtnSpprt[1].acntNo ? 'update' : 'insert'}"/>

                                            <div class="tab-con-area">
                                                <div class="p-cont-sec">
                                                    <div class="sec-tit-area">
                                                        <p class="f-head">지급정보를 입력해주세요</p>
                                                    </div>
                                                    <div class="sec-con-area">
                                                        <div class="data-enter-form">
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">접수일<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="form-input calendar">
                                                                                    <input type="text" id="accsDt2" class="datetimepicker_input" name="wBDBSafetyMstInsertDTO.spprtList[0].accsDt"  value="${kl:convertDate(rtnSpprt[1].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" placeholder="날짜 선택">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">은행명<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="form-input">
                                                                                    <input type="text" id="bankNm2" name="wBDBSafetyMstInsertDTO.spprtList[0].bankNm" placeholder="은행 입력" value="${rtnSpprt[1].bankNm}" maxlength="50">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">계좌번호<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="form-input">
                                                                                    <input type="text" id="acntNo2" name="wBDBSafetyMstInsertDTO.spprtList[0].acntNo" placeholder="계좌번호 입력" value="${rtnSpprt[1].acntNo}" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="50">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">예금주<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="form-input">
                                                                                    <input type="text" id="dpsitNm2" name="wBDBSafetyMstInsertDTO.spprtList[0].dpsitNm" placeholder="예금주 입력" value="${rtnSpprt[1].dpsitNm}" maxlength="50">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="p-cont-sec">
                                                    <div class="sec-tit-area">
                                                        <p class="f-head">파일을 첨부해주세요</p>
                                                    </div>
                                                    <div class="sec-con-area">
                                                        <div class="data-enter-form">
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">지원금신청서<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="spprtAppctnFileSeq" id="spprtAppctnFileSeq1" class="searchFile">
                                                                                    <input type="hidden" name="fileSeqList" value="${rtnSpprt[1].spprtAppctnFileSeq}">
                                                                                    <label class="btn-solid gray-bg" for="spprtAppctnFileSeq1">파일 찾기</label>
                                                                                </div>
                                                                                <div class="file-prev-area" <c:if test="${not empty rtnSpprt[1].spprtAppctnFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                    <a href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd}" download="" title="파일 다운로드">${rtnSpprt[1].spprtAppctnFileNm}</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">거래명세서<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="blingFileSeq" id="blingFileSeq" class="searchFile">
                                                                                    <input type="hidden" name="fileSeqList" value="${rtnSpprt[1].blingFileSeq}">
                                                                                    <label class="btn-solid gray-bg" for="blingFileSeq">파일 찾기</label>
                                                                                </div>
                                                                                <div class="file-prev-area" <c:if test="${not empty rtnSpprt[1].blingFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                    <a href="/file/download?fileSeq=${rtnSpprt[1].blingFileSeq}&fileOrd=${rtnSpprt[1].blingFileOrd}" download="" title="파일 다운로드">${rtnSpprt[1].blingFileNm}</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">매출전표<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="slsFileSeq" id="slsFileSeq" class="searchFile">
                                                                                    <input type="hidden" name="fileSeqList" value="${rtnSpprt[1].slsFileSeq}">
                                                                                    <label class="btn-solid gray-bg" for="slsFileSeq">파일 찾기</label>
                                                                                </div>
                                                                                <div class="file-prev-area" <c:if test="${not empty rtnSpprt[1].slsFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                    <a href="/file/download?fileSeq=${rtnSpprt[1].slsFileSeq}&fileOrd=${rtnSpprt[1].slsFileOrd}" download="" title="파일 다운로드">${rtnSpprt[1].slsFileNm}</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="th">
                                                                    <p class="title f-body1">검수확인서<span class="essential-mark color-sky">*</span></p>
                                                                </div>
                                                                <div class="td">
                                                                    <div class="data-line-w">
                                                                        <div class="data-line">
                                                                            <div class="form-group">
                                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="insptChkFileSeq" id="insptChkFileSeq" class="searchFile">
                                                                                    <input type="hidden" name="fileSeqList" value="${rtnSpprt[1].insptChkFileSeq}">
                                                                                    <label class="btn-solid gray-bg" for="insptChkFileSeq">파일 찾기</label>
                                                                                </div>
                                                                                <div class="file-prev-area" <c:if test="${not empty rtnSpprt[1].insptChkFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                    <a href="/file/download?fileSeq=${rtnSpprt[1].insptChkFileSeq}&fileOrd=${rtnSpprt[1].insptChkFileOrd}" download="" title="파일 다운로드">${rtnSpprt[1].insptChkFileNm}</a>
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
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap align-right">
                            <button class="btn-solid small black-bg btn-agree spprtModify" type="button"><span>저장</span></button>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>