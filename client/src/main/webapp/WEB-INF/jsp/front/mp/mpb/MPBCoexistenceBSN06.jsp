<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBSmartFactoryCtrl ">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
            <div class="btn-wrap">
                <a class="btn-text-icon black-arrow btnSpprtPop" href="javascript:void(0);" title="팝업 열기"><span>지급정보관리</span></a>
            </div>
        </div>
        <c:set var="registerDtl" value="${rtnData.registerDtl}"/>
        <c:set var="spprtDtl" value="${rtnRegisterData.spprtDtl}"/>
        <c:set var="rsumeTaskDtl" value="${rtnRegisterData.rsumeTaskDtl}"/>

        <c:set var="rsumeLeng" value="${fn:length(rtnRegisterData.rsumeTaskDtl)-1}"/>
        <c:set var="nowRsumeTaskCd" value="${rsumeTaskDtl[rsumeLeng].rsumeSttsCd}"/>

        <div class="sec-con-area" id="contArea">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02001'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">신청</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[0].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[0].appctnSttsCdNm eq '접수전'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[0].appctnSttsCdNm eq '접수완료' || rsumeTaskDtl[0].appctnSttsCdNm eq '보완완료' || rsumeTaskDtl[0].appctnSttsCdNm eq '선정'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[0].appctnSttsCdNm eq '보완요청' || rsumeTaskDtl[0].appctnSttsCdNm eq '미선정'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[0].appctnSttsCdNm eq '사용자취소'}">
                                        <c:set var="classType" value="end" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[0].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                        <div class="acco-hide-area">
                            <form name="frmData" enctype="multipart/form-data">
                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSeq" value="${rtnDto.appctnSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnBsnmNo" value="${registerDtl.appctnBsnmNo}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSttsCd" value="${rsumeTaskDtl[0].appctnSttsCd}" />
                                <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.mngSttsCd" value="${rsumeTaskDtl[0].mngSttsCd}" />
                                <input type="hidden" class="notRequired rsumeSttsCd" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSttsCd" value="${rsumeTaskDtl[0].rsumeSttsCd}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[0].rsumeSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[0].rsumeOrd}" />

                                <c:if test="${rsumeTaskDtl[0].mngSttsCd eq 'PRO_TYPE02001_02_002' || rsumeTaskDtl[0].mngSttsCd eq 'PRO_TYPE02001_02_004'}">
                                    <p class="exclamation-txt f-body1">${rsumeTaskDtl[0].rtrnRsnCntn}</p>
                                </c:if>

                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                    <p class="data-title f-body1"><span>종된사업장번호</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" name="wBFBRegisterDTO.sbrdnBsnmNo" placeholder="종된사업장번호" value="${registerDtl.sbrdnBsnmNo}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                    <p class="data-title f-body1"><span>과제명</span><span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="taskCd" name="wBFBRegisterDTO.rsumeTaskDtl.taskCd" title="과제명">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${rtnData.roundMstDTO.asigtList}" varStatus="status">
                                                                    <option value="${cdList.bsnOptnSeq}" <c:if test="${registerDtl.taskCd eq cdList.bsnOptnSeq}" >selected</c:if>>
                                                                            ${cdList.optnNm}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                    <p class="data-title f-body1"><span>사업 유형</span><span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="bsnTypeCd" name="wBFBRegisterDTO.rsumeTaskDtl.bsnTypeCd" title="사업유형">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${rtnData.roundMstDTO.bsinList}" varStatus="status">
                                                                    <option value="${cdList.bsnOptnSeq}" <c:if test="${registerDtl.bsnTypeCd eq cdList.bsnOptnSeq}" >selected</c:if>>
                                                                            ${cdList.optnNm}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                    <p class="data-title f-body1"><span>스마트화 현재 수준</span><span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" id="smtfnPrsntCd" name="wBFBRegisterDTO.rsumeTaskDtl.smtfnPrsntCd" title="스마트화 현재 수준">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                                                    <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                                        <option value="${cdList.cd}" <c:if test="${registerDtl.smtfnPrsntCd eq cdList.cd}" >selected</c:if>>${cdList.cdNm}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                    <p class="data-title f-body1"><span>스마트화 목표 수준</span><span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" id="smtfnTrgtCd" name="wBFBRegisterDTO.rsumeTaskDtl.smtfnTrgtCd" title="스마트화 목표 수준">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                                                    <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                                        <option value="${cdList.cd}" <c:if test="${registerDtl.smtfnTrgtCd eq cdList.cd}" >selected</c:if>>${cdList.cdNm}</option>
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
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">신청서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rsumeTaskDtl[0].appctnSttsCd eq 'PRO_TYPE02001_01_002'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" id="searchFile1" class="searchFile" name="atchFile1">
                                                                <input type="hidden" name="fileSeqList" value="${rsumeTaskDtl[0].appctnFileInfo[0].fileSeq}"/>
                                                                <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                                                            </div>
                                                        </c:if>
                                                        <div class="file-prev-area">
                                                            <a
                                                                    href="/file/download?fileSeq=${rsumeTaskDtl[0].appctnFileInfo[0].fileSeq}&fileOrd=${rsumeTaskDtl[0].appctnFileInfo[0].fileOrd}"
                                                                    download=""
                                                                    title="파일 다운로드"
                                                            >${rsumeTaskDtl[0].appctnFileInfo[0].orgnFileNm}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">보안서약서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <c:if test="${rsumeTaskDtl[0].appctnSttsCd eq 'PRO_TYPE02001_01_002'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                    <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile2" class="searchFile" name="atchFile2">
                                                                    <input type="hidden" name="fileSeqList" value="${rsumeTaskDtl[0].appctnFileInfo[1].fileSeq}"/>
                                                                    <label class="btn-solid gray-bg" for="searchFile2">파일 찾기</label>
                                                                </div>
                                                            </c:if>
                                                            <div class="file-prev-area">
                                                                <a
                                                                        href="/file/download?fileSeq=${rsumeTaskDtl[0].appctnFileInfo[1].fileSeq}&fileOrd=${rsumeTaskDtl[0].appctnFileInfo[1].fileOrd}"
                                                                        download=""
                                                                        title="파일 다운로드"
                                                                >${rsumeTaskDtl[0].appctnFileInfo[1].orgnFileNm}</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">중소기업확인서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <c:if test="${rsumeTaskDtl[0].appctnSttsCd eq 'PRO_TYPE02001_01_002'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile3" class="searchFile" name="atchFile3">
                                                                    <input type="hidden" name="fileSeqList" value="${rsumeTaskDtl[0].appctnFileInfo[2].fileSeq}"/>
                                                                    <label class="btn-solid gray-bg" for="searchFile3">파일 찾기</label>
                                                                </div>
                                                            </c:if>
                                                            <div class="file-prev-area">
                                                                <a
                                                                        href="/file/download?fileSeq=${rsumeTaskDtl[0].appctnFileInfo[2].fileSeq}&fileOrd=${rsumeTaskDtl[0].appctnFileInfo[2].fileOrd}"
                                                                        download=""
                                                                        title="파일 다운로드"
                                                                >${rsumeTaskDtl[0].appctnFileInfo[2].orgnFileNm}</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">사업자등록증<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <c:if test="${rsumeTaskDtl[0].appctnSttsCd eq 'PRO_TYPE02001_01_002'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                    <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile4" class="searchFile" name="atchFile4">
                                                                    <input type="hidden" name="fileSeqList" value="${rsumeTaskDtl[0].appctnFileInfo[3].fileSeq}"/>
                                                                    <label class="btn-solid gray-bg" for="searchFile4">파일 찾기</label>
                                                                </div>
                                                            </c:if>
                                                            <div class="file-prev-area">
                                                                <a
                                                                        href="/file/download?fileSeq=${rsumeTaskDtl[0].appctnFileInfo[3].fileSeq}&fileOrd=${rsumeTaskDtl[0].appctnFileInfo[3].fileOrd}"
                                                                        download=""
                                                                        title="파일 다운로드"
                                                                >${rsumeTaskDtl[0].appctnFileInfo[3].orgnFileNm}</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rsumeTaskDtl[0].mngSttsCd eq 'PRO_TYPE02001_02_002'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg btnUpdate" data-Status="${rsumeTaskDtl[0].appctnSttsCdNm}" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div>

                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02002'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">사업계획</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[1].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[1].appctnSttsCdNm eq '접수전'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[1].appctnSttsCdNm eq '접수완료' || rsumeTaskDtl[1].appctnSttsCdNm eq '보완완료' || rsumeTaskDtl[1].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[1].appctnSttsCdNm eq '보완요청' || rsumeTaskDtl[1].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[1].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                        <div class="acco-hide-area">
                            <form name="frmData" enctype="multipart/form-data">
                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSeq" value="${rtnDto.appctnSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSttsCd" value="${rsumeTaskDtl[1].appctnSttsCd}" />
                                <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.mngSttsCd" value="${rsumeTaskDtl[1].mngSttsCd}" />
                                <input type="hidden" class="notRequired rsumeSttsCd" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSttsCd" value="${rsumeTaskDtl[1].rsumeSttsCd}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[1].rsumeSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[1].rsumeOrd}" />

                                <c:if test="${not empty rsumeTaskDtl[1].rtrnRsnCntn && rsumeTaskDtl[1].mngSttsCd eq 'PRO_TYPE02002_02_003'}">
                                    <p class="exclamation-txt f-body1">${rsumeTaskDtl[1].rtrnRsnCntn}</p>
                                </c:if>

                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">공급기업명<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="공급기업명" id="offerCmpnNm" name="wBFBRegisterDTO.rsumeTaskDtl.offerCmpnNm" value="${rsumeTaskDtl[1].offerCmpnNm}" readonly>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">└ 사업자등록번호<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" id="offerBsnmNo" name="wBFBRegisterDTO.rsumeTaskDtl.offerBsnmNo" placeholder="사업자등록번호" value="${rsumeTaskDtl[1].offerBsnmNo}">
                                                            <button type="button" class="btn-solid small gray-bg" id="bsnmNoAuth" href="javascript:void(0);"><span>인증</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">└ 담당자명<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="담당자명" id="offerPicNm" name="wBFBRegisterDTO.rsumeTaskDtl.offerPicNm" value="${rsumeTaskDtl[1].offerPicNm}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">└ 담당자 휴대폰<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="담당자 휴대폰" class="mobileChk" id="offerPicHpNo" name="wBFBRegisterDTO.rsumeTaskDtl.offerPicHpNo" value="${rsumeTaskDtl[1].offerPicHpNo}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">└ 담당자 이메일<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="담당자 이메일" id="offerPicEmail" name="wBFBRegisterDTO.rsumeTaskDtl.offerPicEmail" value="${rsumeTaskDtl[1].offerPicEmail}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">총 사업비<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" placeholder="총 사업비" id="ttlBsnPmt" class="comma" name="wBFBRegisterDTO.rsumeTaskDtl.ttlBsnPmt" value="<fmt:formatNumber value='${rsumeTaskDtl[1].ttlBsnPmt}' pattern='#,###'/>">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rsumeTaskDtl[1].mngSttsCd eq 'PRO_TYPE02002_02_001' or rsumeTaskDtl[1].mngSttsCd eq 'PRO_TYPE02002_02_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg btnUpdate" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02003'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">최초점검</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[2].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[2].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[2].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[2].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[2].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02005'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">원가계산</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[3].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[3].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[3].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[3].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[3].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02006'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">협약</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[4].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[4].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[4].appctnSttsCdNm eq '협약완료'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[4].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02004'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">중간점검</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[5].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[5].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[5].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[5].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[5].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02008'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">완료보고</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[6].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[6].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[6].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[6].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[6].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>

                        <div class="acco-hide-area">
                            <form name="frmData" enctype="multipart/form-data">
                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSeq" value="${rtnDto.appctnSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.appctnSttsCd" value="${rsumeTaskDtl[6].appctnSttsCd}" />
                                <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.mngSttsCd" value="${rsumeTaskDtl[6].mngSttsCd}" />
                                <input type="hidden" class="notRequired rsumeSttsCd" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSttsCd" value="${rsumeTaskDtl[6].rsumeSttsCd}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[6].rsumeSeq}" />
                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[6].rsumeOrd}" />

                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">완료보고서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <c:if test="${rsumeTaskDtl[6].appctnSttsCd eq 'PRO_TYPE02008_01_003' or rsumeTaskDtl[6].appctnSttsCd eq 'PRO_TYPE02008_01_001'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                    <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile14" class="searchFile" name="atchFile1">
                                                                    <input type="hidden" name="fileSeqList" value="${rsumeTaskDtl[6].appctnFileInfo[0].fileSeq}"/>
                                                                    <label class="btn-solid gray-bg" for="searchFile14">파일 찾기</label>
                                                                </div>
                                                            </c:if>
                                                            <div class="file-prev-area">
                                                                <a
                                                                        href="/file/download?fileSeq=${rsumeTaskDtl[6].appctnFileInfo[0].fileSeq}&fileOrd=${rsumeTaskDtl[6].appctnFileInfo[0].fileOrd}"
                                                                        download=""
                                                                        title="파일 다운로드"
                                                                >${rsumeTaskDtl[6].appctnFileInfo[0].orgnFileNm}</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rsumeTaskDtl[6].appctnSttsCd eq 'PRO_TYPE02008_01_001' or rsumeTaskDtl[6].appctnSttsCd eq 'PRO_TYPE02008_01_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg btnUpdate" data-Status="${rsumeTaskDtl[6].appctnSttsCdNm}" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div>
                    <div class="list-item <c:if test="${nowRsumeTaskCd eq 'PRO_TYPE02007'}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">최종점검</p>
                            </div>
                            <c:if test="${not empty rsumeTaskDtl[7].appctnSttsCdNm}">
                                <c:choose>
                                    <c:when test="${rsumeTaskDtl[7].appctnSttsCdNm eq '대기'}">
                                        <c:set var="classType" value="waiting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[7].appctnSttsCdNm eq '적합'}">
                                        <c:set var="classType" value="accepting" />
                                    </c:when>
                                    <c:when test="${rsumeTaskDtl[7].appctnSttsCdNm eq '부적합'}">
                                        <c:set var="classType" value="arr" />
                                    </c:when>
                                </c:choose>
                                <p class="box-label bigger ${classType}"><span>
                                        ${rsumeTaskDtl[7].appctnSttsCdNm}
                                </span></p>
                            </c:if>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--지급정보관리 -->
    <div id="wrap">
        <!-- 지급정보관리 팝업 -->
        <div class="layer-popup paymentInfoManagPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
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
                                        <c:if test="${registerDtl.pmndvPmtYn eq 'Y'}">
                                            <a class="swiper-slide txt-tab-btn btnSpprtTab" data-give-type="${spprtDtl[0].giveType}" data-stts-cd="${spprtDtl[0].mngSttsCd}" href="javascript:">
                                                <p class="txt"><span class="menu-name">선급금</span></p>
                                            </a>
                                        </c:if>
                                        <a class="swiper-slide txt-tab-btn btnSpprtTab" data-give-type="${spprtDtl[1].giveType}" data-stts-cd="${spprtDtl[1].mngSttsCd}" href="javascript:">
                                            <p class="txt"><span class="menu-name">지원금</span></p>
                                        </a>
                                        <a class="swiper-slide txt-tab-btn btnSpprtTab" data-give-type="${spprtDtl[2].giveType}" data-stts-cd="${spprtDtl[2].mngSttsCd}" href="javascript:">
                                            <p class="txt"><span class="menu-name">기술임치</span></p>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="con-area">
                            <div class="scroll-area">
                                <div class="tab-con-w">
                                    <div class="tab-con-box">
                                        <c:if test="${registerDtl.pmndvPmtYn eq 'Y'}">
                                            <!-- 선급금 탭 -->
                                            <div class="tab-con">
                                                <form name="frmData" data-give-type="${spprtDtl[0].giveType}" enctype="multipart/form-data">
                                                    <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                    <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSpprtSeq" value="${spprtDtl[0].appctnSpprtSeq}"/>
                                                    <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSeq" value="${spprtDtl[0].appctnSeq}"/>
                                                    <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSttsCd" value="${spprtDtl[0].appctnSttsCd}"/>
                                                    <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.spprtDtlList[0].mngSttsCd" value="${spprtDtl[0].mngSttsCd}"/>
                                                    <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].giveType" value="${spprtDtl[0].giveType}"/>
                                                    <input type="hidden" class="notRequired tabFlag" value="${not empty spprtDtl[0].gvmntSpprtPmt ? 'update' : 'insert'}"/>

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
                                                                                            <input class="datetimepicker_input accsDt" type="text" name="wBFBRegisterDTO.spprtDtlList[0].accsDt" value="${empty spprtDtl[0].accsDt ? today : spprtDtl[0].accsDt}" onclick="cmmCtrl.initCalendar(this);" placeholder="날짜 선택">

                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="th">
                                                                            <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                            <p class="title f-body1">
                                                                                <span>정부지원금<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i></span>
                                                                                <span class="essential-mark color-sky">*</span>
                                                                            </p>
                                                                        </div>
                                                                        <div class="td">
                                                                            <div class="data-line-w">
                                                                                <div class="data-line">
                                                                                    <div class="form-group">
                                                                                        <div class="form-input">
                                                                                            <input type="text" class="priceVal comma gvmntSpprtPmt" name="wBFBRegisterDTO.spprtDtlList[0].gvmntSpprtPmt" value="<fmt:formatNumber value='${spprtDtl[0].gvmntSpprtPmt}' pattern='#,###'/>" placeholder="정부지원금 입력" >
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="th">
                                                                            <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                            <p class="title f-body1">
                                                                                <span>대기업출연금<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                                <span class="essential-mark color-sky">*</span>
                                                                            </p>
                                                                        </div>
                                                                        <div class="td">
                                                                            <div class="data-line-w">
                                                                                <div class="data-line">
                                                                                    <div class="form-group">
                                                                                        <div class="form-input">
                                                                                            <input type="text" class="priceVal comma mjcmnAprncPmt" name="wBFBRegisterDTO.spprtDtlList[0].mjcmnAprncPmt" value="<fmt:formatNumber value='${spprtDtl[0].mjcmnAprncPmt}' pattern='#,###'/>"  placeholder="대기업출연금 입력">
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="th">
                                                                            <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                            <p class="title f-body1">
                                                                                <span>총금액<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i>+<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                                <span class="essential-mark color-sky">*</span>
                                                                            </p>
                                                                        </div>
                                                                        <div class="td">
                                                                            <div class="data-line-w">
                                                                                <div class="data-line">
                                                                                    <div class="form-group">
                                                                                        <div class="form-input">
                                                                                            <input type="text" class="ttlPmt comma" name="wBFBRegisterDTO.spprtDtlList[0].ttlPmt" value="<fmt:formatNumber value='${spprtDtl[0].ttlPmt}' pattern='#,###'/>" title="총금액" placeholder="총금액 입력" readonly>
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
                                                                                            <input type="text" class="bankNm" name="wBFBRegisterDTO.spprtDtlList[0].bankNm" value="${spprtDtl[0].bankNm}" placeholder="은행 입력" title="은행명">
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
                                                                                            <input type="text" class="numberChk acntNo" name="wBFBRegisterDTO.spprtDtlList[0].acntNo" value="${spprtDtl[0].acntNo}" placeholder="계좌번호 입력">
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
                                                                                            <input type="text" class="dpsitNm" name="wBFBRegisterDTO.spprtDtlList[0].dpsitNm" value="${spprtDtl[0].dpsitNm}" placeholder="예금주 입력">
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
                                                                            <p class="title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                                        </div>
                                                                        <div class="td">
                                                                            <div class="data-line-w">
                                                                                <div class="data-line">
                                                                                    <div class="form-group">
                                                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                        </div>
                                                                                        <div class="file-btn-area">
                                                                                            <input type="file" id="searchFile6" class="searchFile" name="atchFile2">
                                                                                            <input type="hidden" name="fileSeqList" value="${spprtDtl[0].acntFileSeq}">
                                                                                            <label class="btn-solid gray-bg" for="searchFile6">파일 찾기</label>
                                                                                        </div>
                                                                                        <div class="file-prev-area">
                                                                                            <a class="btnDownload" data-file-seq="${spprtDtl[0].acntFileSeq}" href="javascript:void(0);"
                                                                                               download="" title="파일 다운로드">${spprtDtl[0].acntFileNm}</a>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="th">
                                                                            <p class="title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                                        </div>
                                                                        <div class="td">
                                                                            <div class="data-line-w">
                                                                                <div class="data-line">
                                                                                    <div class="form-group">
                                                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                                                        </div>
                                                                                        <div class="file-btn-area">
                                                                                            <input type="file" id="searchFile7" class="searchFile" name="atchFile3">
                                                                                            <input type="hidden" name="fileSeqList" value="${spprtDtl[0].bnkbkFileSeq}">
                                                                                            <label class="btn-solid gray-bg" for="searchFile7">파일 찾기</label>
                                                                                        </div>
                                                                                        <div class="file-prev-area">
                                                                                            <a class="btnDownload" data-file-seq="${spprtDtl[0].bnkbkFileSeq}" href="javascript:void(0);"
                                                                                               download="" title="파일 다운로드">${spprtDtl[0].bnkbkFileNm}</a>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
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
                                                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                        </div>
                                                                                        <div class="file-btn-area">
                                                                                            <input type="file" id="searchFile5" class="searchFile" name="atchFile1">
                                                                                            <input type="hidden" name="fileSeqList" value="${spprtDtl[0].spprtAppctnFileSeq}">
                                                                                            <label class="btn-solid gray-bg" for="searchFile5">파일 찾기</label>
                                                                                        </div>
                                                                                        <div class="file-prev-area">
                                                                                            <a class="btnDownload" data-file-seq="${spprtDtl[0].spprtAppctnFileSeq}" href="javascript:void(0);"
                                                                                               download="" title="파일 다운로드">${spprtDtl[0].spprtAppctnFileNm}</a><!-- 204-01-03 속성 변경 -->
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
                                        <div class="tab-con">
                                            <form name="frmData" data-give-type="${spprtDtl[1].giveType}" enctype="multipart/form-data">
                                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSpprtSeq" value="${spprtDtl[1].appctnSpprtSeq}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSeq" value="${spprtDtl[1].appctnSeq}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSttsCd" value="${spprtDtl[1].appctnSttsCd}"/>
                                                <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.spprtDtlList[0].mngSttsCd" value="${spprtDtl[1].mngSttsCd}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].giveType" value="${spprtDtl[1].giveType}"/>
                                                <input type="hidden" class="notRequired tabFlag" value="${not empty spprtDtl[1].gvmntSpprtPmt ? 'update' : 'insert'}"/>

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
                                                                                        <input type="text" class="datetimepicker_input accsDt" name="wBFBRegisterDTO.spprtDtlList[0].accsDt" onclick="cmmCtrl.initCalendar(this);" value="${empty spprtDtl[1].accsDt ? today : spprtDtl[1].accsDt}"  placeholder="날짜 선택">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                        <p class="title f-body1">
                                                                            <span>정부지원금<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i></span>
                                                                            <span class="essential-mark color-sky">*</span>
                                                                        </p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" class="priceVal comma gvmntSpprtPmt" name="wBFBRegisterDTO.spprtDtlList[0].gvmntSpprtPmt" placeholder="정부지원금 입력" value="<fmt:formatNumber value='${spprtDtl[1].gvmntSpprtPmt}' pattern='#,###'/>">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                        <p class="title f-body1">
                                                                            <span>대기업출연금<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                            <span class="essential-mark color-sky">*</span>
                                                                        </p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" class="priceVal comma mjcmnAprncPmt" name="wBFBRegisterDTO.spprtDtlList[0].mjcmnAprncPmt" placeholder="대기업출연금 입력" value="<fmt:formatNumber value='${spprtDtl[1].mjcmnAprncPmt}' pattern='#,###'/>">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                        <p class="title f-body1">
                                                                            <span>총금액<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i>+<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                            <span class="essential-mark color-sky">*</span>
                                                                        </p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" class="ttlPmt comma ttlPmt" name="wBFBRegisterDTO.spprtDtlList[0].ttlPmt" placeholder="총금액 입력" value="<fmt:formatNumber value='${spprtDtl[1].ttlPmt}' pattern='#,###'/>" readonly>
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
                                                                                        <input type="text" class="bankNm" name="wBFBRegisterDTO.spprtDtlList[0].bankNm" placeholder="은행 입력" value="${spprtDtl[1].bankNm}">
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
                                                                                        <input type="text" class="numberChk acntNo" name="wBFBRegisterDTO.spprtDtlList[0].acntNo" placeholder="계좌번호 입력" value="${spprtDtl[1].acntNo}">
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
                                                                                        <input type="text" class="dpsitNm" name="wBFBRegisterDTO.spprtDtlList[0].dpsitNm" placeholder="예금주 입력" value="${spprtDtl[1].dpsitNm}">
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
                                                                        <p class="title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                        <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile9" class="searchFile" name="atchFile2">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].acntFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile9">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[1].acntFileSeq}" href="javascript:void(0);"
                                                                                           download="" title="파일 다운로드">${spprtDtl[1].acntFileNm}</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile10" class="searchFile" name="atchFile3">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].bnkbkFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile10">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[1].bnkbkFileSeq}" href="javascript:void(0);"
                                                                                           download="" title="파일 다운로드">${spprtDtl[1].bnkbkFileNm}</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
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
                                                                                        <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile8" class="searchFile" name="atchFile1">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].spprtAppctnFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile8">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[1].spprtAppctnFileSeq}" href="javascript:void(0);"
                                                                                           download="" title="파일 다운로드">${spprtDtl[1].spprtAppctnFileNm}</a>
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
                                        <!-- 기술임치 탭 -->
                                        <div class="tab-con">
                                            <form name="frmData" data-give-type="${spprtDtl[2].giveType}" enctype="multipart/form-data">
                                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSpprtSeq" value="${spprtDtl[2].appctnSpprtSeq}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSeq" value="${spprtDtl[2].appctnSeq}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].appctnSttsCd" value="${spprtDtl[2].appctnSttsCd}"/>
                                                <input type="hidden" class="notRequired mngSttsCd" name="wBFBRegisterDTO.spprtDtlList[0].mngSttsCd" value="${spprtDtl[2].mngSttsCd}"/>
                                                <input type="hidden" class="notRequired" name="wBFBRegisterDTO.spprtDtlList[0].giveType" value="${spprtDtl[2].giveType}"/>
                                                <input type="hidden" class="notRequired tabFlag" value="${not empty spprtDtl[2].cmssnPmt ? 'update' : 'insert'}"/>

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
                                                                                        <input type="text" class="datetimepicker_input" name="wBFBRegisterDTO.spprtDtlList[0].accsDt" value="${empty spprtDtl[2].accsDt ? today : spprtDtl[0].accsDt}" onclick="cmmCtrl.initCalendar(this);" placeholder="날짜 선택">
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">수수료<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="form-input">
                                                                                        <input type="text" class="comma cmssnPmt" name="wBFBRegisterDTO.spprtDtlList[0].cmssnPmt" placeholder="수수료 입력" value="<fmt:formatNumber value='${spprtDtl[2].cmssnPmt}' pattern='#,###'/>">
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
                                                                        <p class="title f-body1">기술임치증<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                        <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile12" class="searchFile" name="atchFile2">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].tchlgLseFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile12">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[2].tchlgLseFileSeq}" javascript:void(0);
                                                                                           download="" title="파일 다운로드">${spprtDtl[2].tchlgLseFileNm}</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="th">
                                                                        <p class="title f-body1">임치료 납입 영수증<span class="essential-mark color-sky">*</span></p>
                                                                    </div>
                                                                    <div class="td">
                                                                        <div class="data-line-w">
                                                                            <div class="data-line">
                                                                                <div class="form-group">
                                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile13" class="searchFile" name="atchFile3">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].lsePayFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile13">파일 찾기</label>
                                                                                    </div>
                                                                                    <!-- 2023-12-20 추가 -->
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[2].lsePayFileSeq}" javascript:void(0);
                                                                                           download="" title="파일 다운로드">${spprtDtl[2].lsePayFileNm}</a>
                                                                                    </div>
                                                                                    <!-- // 2023-12-20 추가 -->
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
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
                                                                                        <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                    </div>
                                                                                    <div class="file-btn-area">
                                                                                        <input type="file" id="searchFile11" class="searchFile" name="atchFile1">
                                                                                        <input type="hidden" name="fileSeqList" value="${spprtDtl[1].spprtAppctnFileSeq}">
                                                                                        <label class="btn-solid gray-bg" for="searchFile11">파일 찾기</label>
                                                                                    </div>
                                                                                    <div class="file-prev-area">
                                                                                        <a class="btnDownload" data-file-seq="${spprtDtl[2].spprtAppctnFileSeq}" javascript:void(0);
                                                                                           download="" title="파일 다운로드">${spprtDtl[2].spprtAppctnFileNm}</a>
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
                                <button class="btn-solid small black-bg btn-agree btnSpprtUpdate" data-status="${flag}" type="button"><span>저장</span></button>
                            </div>
                        </div>
                        <div class="user-opt-area">
                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="dimd"></div>
    </div>
</div>