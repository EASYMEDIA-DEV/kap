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

<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBSecurityWriteCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
            <div class="btn-wrap">
                <c:if test="${
                    rtnSpprt[0].appctnSttsCd eq 'PRO_TYPE03001_01_003' or
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
                        <form name="frmData1" id="frmData1" enctype="multipart/form-data">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" id="mngSttsCd1" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd1" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[0].appctnSttsCd}">

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
                                                            <input type="text" id="sbrdnBsnmNo" name="wBCBSecurityMstInsertDTO.sbrdnBsnmNo" placeholder="종된사업장번호 입력" value="${rtnData.sbrdnBsnmNo}" maxlength="4" ${readonly1}>
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
                                                                <input type="text" id="zipCode" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].pbsnZipcode" placeholder="우편번호" value="${rtnPbsn[0].pbsnZipcode}" readonly="">
                                                            </div>
                                                            <div class="form-input w-longer">
                                                                <input type="text" id="bscAddr" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].pbsnBscAddr" placeholder="주소" value="${rtnPbsn[0].pbsnBscAddr}" readonly="">
                                                            </div>
                                                            <c:if test="${empty readonly1}">
                                                                <div class=" ">
                                                                    <button class="btn-solid small gray-bg" id="searchPostCode" type="button"><span>우편번호 찾기</span></button>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longest">
                                                                <input type="text" id="dtlAddr" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].pbsnDtlAddr" placeholder="상세주소 입력" value="${rtnPbsn[0].pbsnDtlAddr}" maxlength="100" ${readonly1}>
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
                                                            <div class="file-list-area-wrap">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                    <%--<input type="file" name="atchFile1" id="searchFile1" class="searchFile">--%>
                                                                <input type="file" name="atchFile1" id="ord1FileSearch0" class="searchFile fileOrd1">
                                                                <input type="file" name="atchFile2" id="ord1FileSearch1" class="searchFile fileOrd1">
                                                                <input type="file" name="atchFile3" id="ord1FileSearch2" class="searchFile fileOrd1">
                                                                <input type="file" name="atchFile4" id="ord1FileSearch3" class="searchFile fileOrd1">
                                                                <input type="file" name="atchFile5" id="ord1FileSearch4" class="searchFile fileOrd1">
                                                                <label class="btn-solid gray-bg fileForm" for="ord1FileSearch">파일 찾기</label>
                                                                <%--<input type="hidden" name="fileSeqList" value="${rtnFile[0].fileSeq}"/>--%>
                                                                <c:forEach var="file" items="${rtnFile}">
                                                                    <c:if test="${file.rsumeOrd eq 1}">
                                                                        <c:set var="ord1FileSeq" value="${file.fileSeq}" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="hidden" name="fileSeqList" value="${ord1FileSeq}"/>
                                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE01">
                                                            </div>
                                                        </c:if>
                                                        <div class="attatched-file-area">
                                                            <%--<a href="/file/download?fileSeq=${rtnFile[0].fileSeq}&fileOrd=${rtnFile[0].fileOrd}" download="" title="파일 다운로드">${rtnFile[0].fileNm}</a>--%>
                                                            <c:forEach var="file" items="${rtnFile}">
                                                                <c:if test="${file.rsumeOrd eq 1}">
                                                                    <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${file.fileSeq}&fileOrd=${file.fileOrd}" title="파일 다운로드" download=""><span>${file.fileNm}</span></a>
                                                                </c:if>
                                                            </c:forEach>
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
                        <form name="frmData2" id="frmData2" enctype="multipart/form-data">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].bsnPlanDt" value="${today}">

                            <input type="hidden" id="mngSttsCd2" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[1].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd2" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[1].appctnSttsCd}">

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
                                            <%--                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>--%>
                                        </div>

                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">지원금 ①<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="spprtPmt" class="comma" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].spprtPmt" placeholder="지원금 입력" value="${rtnPbsn[1].spprtPmt}" ${readonly2} maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">자부담 ②<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="phswPmt" class="comma" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].phswPmt" placeholder="자부담 입력" value="${rtnPbsn[1].phswPmt}" ${readonly2} maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">총금액 ① + ②<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="sum" class="comma" placeholder="총금액 입력" value="${rtnPbsn[1].ttlPmt}" readonly>
                                                            <input type="hidden" class="notRequired" id="ttlPmt" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].ttlPmt" value="${rtnPbsn[1].ttlPmt}">
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
                                                    <p class="data-title f-body1">사업계획서 (최대 5개까지 첨부 가능)<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_001' or rtnDtl[1].appctnSttsCd eq 'PRO_TYPE01002_01_003'}">
                                                            <div class="file-list-area-wrap">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile1" id="ord2FileSearch0" class="searchFile fileOrd2">
                                                                <input type="file" name="atchFile2" id="ord2FileSearch1" class="searchFile fileOrd2">
                                                                <input type="file" name="atchFile3" id="ord2FileSearch2" class="searchFile fileOrd2">
                                                                <input type="file" name="atchFile4" id="ord2FileSearch3" class="searchFile fileOrd2">
                                                                <input type="file" name="atchFile5" id="ord2FileSearch4" class="searchFile fileOrd2">
                                                                <label class="btn-solid gray-bg fileForm" for="ord2FileSearch">파일 찾기</label>
                                                                <%--<input type="hidden" name="fileSeqList" value="${rtnFile[1].fileSeq}"/>--%>
                                                                <c:forEach var="file" items="${rtnFile}">
                                                                    <c:if test="${file.rsumeOrd eq 2}">
                                                                        <c:set var="ord2FileSeq" value="${file.fileSeq}" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="hidden" name="fileSeqList" value="${ord2FileSeq}"/>
                                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE08">
                                                            </div>
                                                        </c:if>
                                                        <div class="attatched-file-area">
                                                            <c:forEach var="file" items="${rtnFile}">
                                                                <c:if test="${file.rsumeOrd eq 2}">
                                                                    <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${file.fileSeq}&fileOrd=${file.fileOrd}" title="파일 다운로드" download=""><span>${file.fileNm}</span></a>
                                                                </c:if>
                                                            </c:forEach>
                                                        </div>
                                                        <%--<div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnFile[1].fileSeq}&fileOrd=${rtnFile[1].fileOrd}" download="" title="파일 다운로드">${rtnFile[1].fileNm}</a>
                                                        </div>--%>
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
                        <form name="frmData4" id="frmData4" enctype="multipart/form-data">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].cmpltnBrfngDt" value="${today}">

                            <input type="hidden" id="mngSttsCd4" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[3].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd4" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[3].appctnSttsCd}">

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
                                                    <p class="data-title f-body1">완료보고서 (최대 5개까지 첨부 가능)<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_001' or rtnDtl[3].appctnSttsCd eq 'PRO_TYPE01004_01_003'}">
                                                            <div class="file-list-area-wrap">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                    <%--<input type="file" name="atchFile4" id="searchFile4" class="searchFile">--%>
                                                                <input type="file" name="atchFile1" id="ord4FileSearch0" class="searchFile fileOrd4">
                                                                <input type="file" name="atchFile2" id="ord4FileSearch1" class="searchFile fileOrd4">
                                                                <input type="file" name="atchFile3" id="ord4FileSearch2" class="searchFile fileOrd4">
                                                                <input type="file" name="atchFile4" id="ord4FileSearch3" class="searchFile fileOrd4">
                                                                <input type="file" name="atchFile5" id="ord4FileSearch4" class="searchFile fileOrd4">
                                                                <label class="btn-solid gray-bg fileForm" for="ord4FileSearch">파일 찾기</label>
                                                                <%--<input type="hidden" name="fileSeqList" value="${rtnFile[3].fileSeq}"/>--%>
                                                                <c:forEach var="file" items="${rtnFile}">
                                                                    <c:if test="${file.rsumeOrd eq 4}">
                                                                        <c:set var="ord4FileSeq" value="${file.fileSeq}" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="hidden" name="fileSeqList" value="${ord4FileSeq}"/>
                                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE10">
                                                            </div>
                                                        </c:if>
                                                        <div class="attatched-file-area">
                                                            <%--<a href="/file/download?fileSeq=${rtnFile[3].fileSeq}&fileOrd=${rtnFile[3].fileOrd}" download="" title="파일 다운로드">${rtnFile[3].fileNm}</a>--%>
                                                            <c:forEach var="file" items="${rtnFile}">
                                                                <c:if test="${file.rsumeOrd eq 4}">
                                                                    <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${file.fileSeq}&fileOrd=${file.fileOrd}" title="파일 다운로드" download=""><span>${file.fileNm}</span></a>
                                                                </c:if>
                                                            </c:forEach>
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
                        <form name="frmData6" id="frmData6" enctype="multipart/form-data">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.detailsKey" value="${rtnData.appctnSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.episdSeq" value="${rtnData.episdSeq}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.memSeq" value="${rtnMem.memSeq}" />
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.bsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnBsnmNo" value="${rtnCompany.bsnmNo}"/>
                            <input type="hidden" name="wBCBSecurityMstInsertDTO.sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}"/>

                            <input type="hidden" name="wBCBSecurityMstInsertDTO.pbsnDtlList[0].cmpltnBrfngDt" value="${today}">

                            <input type="hidden" id="mngSttsCd6" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].mngSttsCd" value="${rtnDtl[5].mngSttsCd}">
                            <input type="hidden" id="appctnSttsCd6" name="wBCBSecurityMstInsertDTO.rsumeDtlList[0].appctnSttsCd" value="${rtnDtl[5].appctnSttsCd}">

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
                                                    <p class="data-title f-body1">검수보고서 (최대 5개까지 첨부 가능)<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_001' or rtnDtl[5].appctnSttsCd eq 'PRO_TYPE01006_01_005'}">
                                                            <div class="file-list-area-wrap">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                    <%--<input type="file" name="atchFile6" id="searchFile6" class="searchFile">--%>
                                                                <input type="file" name="atchFile1" id="ord6FileSearch0" class="searchFile fileOrd6">
                                                                <input type="file" name="atchFile2" id="ord6FileSearch1" class="searchFile fileOrd6">
                                                                <input type="file" name="atchFile3" id="ord6FileSearch2" class="searchFile fileOrd6">
                                                                <input type="file" name="atchFile4" id="ord6FileSearch3" class="searchFile fileOrd6">
                                                                <input type="file" name="atchFile5" id="ord6FileSearch4" class="searchFile fileOrd6">
                                                                <label class="btn-solid gray-bg fileForm" for="ord6FileSearch">파일 찾기</label>
                                                                <%--<input type="hidden" name="fileSeqList" value="${rtnFile[5].fileSeq}"/>--%>
                                                                <c:forEach var="file" items="${rtnFile}">
                                                                    <c:if test="${file.rsumeOrd eq 6}">
                                                                        <c:set var="ord6FileSeq" value="${file.fileSeq}" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="hidden" name="fileSeqList" value="${ord6FileSeq}"/>
                                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE09">
                                                            </div>
                                                        </c:if>
                                                        <div class="attatched-file-area">
                                                            <%--<a href="/file/download?fileSeq=${rtnFile[5].fileSeq}&fileOrd=${rtnFile[5].fileOrd}" download="" title="파일 다운로드">${rtnFile[5].fileNm}</a>--%>
                                                            <c:forEach var="file" items="${rtnFile}">
                                                                <c:if test="${file.rsumeOrd eq 6}">
                                                                    <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${file.fileSeq}&fileOrd=${file.fileOrd}" title="파일 다운로드" download=""><span>${file.fileNm}</span></a>
                                                                </c:if>
                                                            </c:forEach>
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
                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                                                <input type="hidden" class="notRequired" name="wBCBSecurityMstInsertDTO.spprtList[0].appctnSpprtSeq" value="${rtnSpprt[0].appctnSpprtSeq}" />
                                                <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                                                <input type="hidden" name="wBCBSecurityMstInsertDTO.spprtList[0].giveType" value="${rtnSpprt[0].giveType}" />

                                                <input type="hidden" id="spprtAppctnSttsCd1" name="wBCBSecurityMstInsertDTO.spprtList[0].appctnSttsCd" value="${rtnSpprt[0].appctnSttsCd}" />
                                                <input type="hidden" id="spprtMngSttsCd1" name="wBCBSecurityMstInsertDTO.spprtList[0].mngSttsCd" value="${rtnSpprt[0].mngSttsCd}" />
                                                <input type="hidden" class="tabFlag" value="${not empty rtnSpprt[0].acntNo ? 'update' : 'insert'}"/>

                                                <div class="tab-con-area">
                                                    <div class="p-cont-sec">
                                                        <div class="sec-tit-area">
                                                            <p class="f-head">지급정보를 입력해주세요</p>
                                                                <%--                                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>--%>
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
                                                                                        <input type="text" id="accsDt1" class="datetimepicker_input" name="wBCBSecurityMstInsertDTO.spprtList[0].accsDt" value="${kl:convertDate(rtnSpprt[0].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"  placeholder="날짜 선택">
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
                                                                                        <input type="text" id="bankNm1" name="wBCBSecurityMstInsertDTO.spprtList[0].bankNm" placeholder="은행 입력" value="${rtnSpprt[0].bankNm}" maxlength="50">
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
                                                                                        <input type="text" id="acntNo1" name="wBCBSecurityMstInsertDTO.spprtList[0].acntNo" placeholder="계좌번호 입력" value="${rtnSpprt[0].acntNo}" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="50">
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
                                                                                        <input type="text" id="dpsitNm1" name="wBCBSecurityMstInsertDTO.spprtList[0].dpsitNm" placeholder="예금주 입력" value="${rtnSpprt[0].dpsitNm}" maxlength="50">
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
                                        <form name="spprtform2" id="spprtform2" enctype="multipart/form-data">
                                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <input type="hidden" name="wBCBSecurityMstInsertDTO.appctnSeq" value="${rtnData.appctnSeq}" />
                                            <input type="hidden" class="notRequired" name="wBCBSecurityMstInsertDTO.spprtList[0].appctnSpprtSeq" value="${rtnSpprt[1].appctnSpprtSeq}" />
                                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                                            <input type="hidden" name="wBCBSecurityMstInsertDTO.spprtList[0].giveType" value="${rtnSpprt[1].giveType}" />

                                            <input type="hidden" id="spprtAppctnSttsCd2" name="wBCBSecurityMstInsertDTO.spprtList[0].appctnSttsCd" value="${rtnSpprt[1].appctnSttsCd}" />
                                            <input type="hidden" id="spprtMngSttsCd2" name="wBCBSecurityMstInsertDTO.spprtList[0].mngSttsCd" value="${rtnSpprt[1].mngSttsCd}" />
                                            <input type="hidden" class="tabFlag" value="${not empty rtnSpprt[1].acntNo ? 'update' : 'insert'}"/>

                                            <div class="tab-con-area">
                                                <div class="p-cont-sec">
                                                    <div class="sec-tit-area">
                                                        <p class="f-head">지급정보를 입력해주세요</p>
                                                        <%--                                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>--%>
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
                                                                                    <input type="text" id="accsDt2" class="datetimepicker_input" name="wBCBSecurityMstInsertDTO.spprtList[0].accsDt"  value="${kl:convertDate(rtnSpprt[1].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" placeholder="날짜 선택">
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
                                                                                    <input type="text" id="bankNm2" name="wBCBSecurityMstInsertDTO.spprtList[0].bankNm" placeholder="은행 입력" value="${rtnSpprt[1].bankNm}" maxlength="50">
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
                                                                                    <input type="text" id="acntNo2" name="wBCBSecurityMstInsertDTO.spprtList[0].acntNo" placeholder="계좌번호 입력" value="${rtnSpprt[1].acntNo}" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="50">
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
                                                                                    <input type="text" id="dpsitNm2" name="wBCBSecurityMstInsertDTO.spprtList[0].dpsitNm" placeholder="예금주 입력" value="${rtnSpprt[1].dpsitNm}" maxlength="50">
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
                                                        <p class="f-head">파일을 첨부해주세요 (최대 5개까지 첨부 가능)</p>
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
                                                                                <%-- 2024-07-29 첨부파일 개수 수정 --%>
                                                                                <div class="file-list-area-wrap">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="atchFile1" id="spprFileSearch0" class="searchFile fileSppr1">
                                                                                    <input type="file" name="atchFile2" id="spprFileSearch1" class="searchFile fileSppr1">
                                                                                    <input type="file" name="atchFile3" id="spprFileSearch2" class="searchFile fileSppr1">
                                                                                    <input type="file" name="atchFile4" id="spprFileSearch3" class="searchFile fileSppr1">
                                                                                    <input type="file" name="atchFile5" id="spprFileSearch4" class="searchFile fileSppr1">
                                                                                    <label class="btn-solid gray-bg fileForm" for="spprFileSearch">파일 찾기</label>
                                                                                    <input type="hidden" name="fileSeqList" value="${rtnSpprt[1].spprtAppctnFileSeq}"/>
                                                                                </div>
                                                                                <div class="attatched-file-area" <c:if test="${not empty rtnSpprt[1].spprtAppctnFileSeq}">style="pointer-events: auto;"</c:if>>
                                                                                    <c:if test="${not empty rtnSpprt[1].spprtAppctnFileOrd1 && not empty rtnSpprt[1].spprtAppctnFileNm1}">
                                                                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd1}" title="파일 다운로드" download=""><span>${rtnSpprt[1].spprtAppctnFileNm1}</span></a>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty rtnSpprt[1].spprtAppctnFileOrd2 && not empty rtnSpprt[1].spprtAppctnFileNm2}">
                                                                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd2}" title="파일 다운로드" download=""><span>${rtnSpprt[1].spprtAppctnFileNm2}</span></a>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty rtnSpprt[1].spprtAppctnFileOrd3 && not empty rtnSpprt[1].spprtAppctnFileNm3}">
                                                                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd3}" title="파일 다운로드" download=""><span>${rtnSpprt[1].spprtAppctnFileNm3}</span></a>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty rtnSpprt[1].spprtAppctnFileOrd4 && not empty rtnSpprt[1].spprtAppctnFileNm4}">
                                                                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd4}" title="파일 다운로드" download=""><span>${rtnSpprt[1].spprtAppctnFileNm4}</span></a>
                                                                                    </c:if>
                                                                                    <c:if test="${not empty rtnSpprt[1].spprtAppctnFileOrd5 && not empty rtnSpprt[1].spprtAppctnFileNm5}">
                                                                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${rtnSpprt[1].spprtAppctnFileSeq}&fileOrd=${rtnSpprt[1].spprtAppctnFileOrd5}" title="파일 다운로드" download=""><span>${rtnSpprt[1].spprtAppctnFileNm5}</span></a>
                                                                                    </c:if>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%-- 2024-07-29 첨부파일 항목 1개로 축소 --%>
                                                            <%--<div class="row">
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
                                                            </div>--%>
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