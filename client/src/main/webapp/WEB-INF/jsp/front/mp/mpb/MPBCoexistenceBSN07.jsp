<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBExamCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item <c:if test="${rtnData.stageOrd eq 1}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData1" id="frmData1" enctype="multipart/form-data">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">신청</p>
                                </div>
                                <c:if test="${not empty rtnData.applyList[0].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnData.applyList[0].appctnSttsNm eq '접수완료' || rtnData.applyList[0].appctnSttsNm eq '보완완료' || rtnData.applyList[0].appctnSttsNm eq '선정'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnData.applyList[0].appctnSttsNm eq '보완요청' || rtnData.applyList[0].appctnSttsNm eq '미선정'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnData.applyList[0].appctnSttsNm}
                                    </span></p>
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnData.applyList[0].rtrnRsnCntn && rtnData.applyList[0].appctnSttsCd eq 'PRO_TYPE07001_01_002'}">
                                    <p class="exclamation-txt f-body1">${rtnData.applyList[0].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <c:set var="totalTchlg" value="0"/>
                                                    <c:set var="totalTchlgCnt" value="0"/>

                                                    <div class="data-inner-line addEquiment">
                                                        <c:forEach var="item" items="${rtnData.euipmentList}" varStatus="status">
                                                            <c:set var="totalTchlg" value="${totalTchlg+1}"/>
                                                            <c:set var="totalTchlgCnt" value="${totalTchlgCnt+item.tchlgCnt}"/>
                                                            <div class="form-group equiment">
                                                                <div class="form-input w-longer">
                                                                    <input type="text" class="tchlgNm" name="exam.euipmentList[${status.index}].tchlgNm" value="${item.tchlgNm}" placeholder="장비명 입력" maxlength="50">
                                                                </div>
                                                                <div class="amount-div">
                                                                    <button type="button" class="amount-btn <c:if test="${item.tchlgCnt == 1}">disabled</c:if> minus"><img src="/common/images/icon-add-data-minus.svg" alt=""></button><!-- 클릭 안될 때 disabled class 추가 -->
                                                                    <div class="form-input">
                                                                        <input type="text" class="tchlgCnt numberChk" name="exam.euipmentList[${status.index}].tchlgCnt" value="${item.tchlgCnt}" title="대상장비 수량">
                                                                    </div>
                                                                    <button type="button" class="amount-btn <c:if test="${item.tchlgCnt == 100}">disabled</c:if> plus"><img src="/common/images/icon-add-data-plus.svg" alt=""></button>
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <c:if test="${status.index ne 0}">
                                                                        <button type="button" class="btn-text-icon delete deleteBtn" href="javascript:"><span>삭제</span></button>
                                                                    </c:if>
                                                                    <button type="button" class="btn-solid small gray-bg addBtn" href="javascript:"><span>장비 추가</span></button>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="data-inner-line">
                                                        <div class="add-data-result">
                                                            <p class="f-body1">총합계</p>
                                                            <div class="amount-result-w">
                                                                <div class="amount-result">
                                                                    <div class="amount-num-div f-body1">대상장비<span class="f-title2 equimentCnt">${totalTchlg}</span>종</div>
                                                                </div>
                                                                <div class="amount-result">
                                                                    <div class="amount-num-div f-body1">총 수량<span class="f-title2 totalCnt">${totalTchlgCnt}</span>개</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="equimentHtml">
                                                    <div class="form-group equiment">
                                                        <div class="form-input w-longer">
                                                            <input type="text" class="tchlgNm" placeholder="장비명 입력" maxlength="50">
                                                        </div>
                                                        <div class="amount-div">
                                                            <button type="button" class="amount-btn disabled minus"><img src="/common/images/icon-add-data-minus.svg" alt=""></button><!-- 클릭 안될 때 disabled class 추가 -->
                                                            <div class="form-input">
                                                                <input type="text" class="tchlgCnt numberChk" value="1" title="대상장비 수량">
                                                            </div>
                                                            <button type="button" class="amount-btn plus"><img src="/common/images/icon-add-data-plus.svg" alt=""></button>
                                                        </div>
                                                        <div class="btn-wrap">
                                                            <button class="btn-text-icon delete deleteBtn" href="javascript:"><span>삭제</span></button>
                                                            <button class="btn-solid small gray-bg addBtn" href="javascript:"><span>장비 추가</span></button>
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
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnData.applyList[0].appctnSttsCd eq 'PRO_TYPE07001_01_002'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile" id="searchFile" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnData.applyList[0].fileInfoList[0].fileSeq}"/>
                                                                <input type="hidden" name="exam.fileCdList" value="ATTACH_FILE_TYPE01">
                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                            </div>
                                                        </c:if>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnData.applyList[0].fileInfoList[0].fileSeq}&fileOrd=0" download="" title="파일 다운로드">${rtnData.applyList[0].fileInfoList[0].fileNm}</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">대상장비리스트<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <c:if test="${rtnData.applyList[0].appctnSttsCd eq 'PRO_TYPE07001_01_002'}">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" name="atchFile1" id="searchFile1" class="searchFile">
                                                                <input type="hidden" name="fileSeqList" value="${rtnData.applyList[0].fileInfoList[1].fileSeq}"/>
                                                                <input type="hidden" name="exam.fileCdList" value="ATTACH_FILE_TYPE11">
                                                                <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                                                            </div>
                                                        </c:if>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="/file/download?fileSeq=${rtnData.applyList[0].fileInfoList[1].fileSeq}&fileOrd=0" download="" title="파일 다운로드">${rtnData.applyList[0].fileInfoList[1].fileNm}</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnData.applyList[0].appctnSttsCd eq 'PRO_TYPE07001_01_002'}">
                                    <div class="btn-wrap align-right"`>
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="1" data-status="${rtnData.applyList[0].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                    <div class="list-item <c:if test="${rtnData.stageOrd eq 2}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData2" id="frmData2">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">심사</p>
                                </div>
                                <c:if test="${not empty rtnData.applyList[1].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnData.applyList[1].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnData.applyList[1].appctnSttsNm eq '접수완료' || rtnData.applyList[1].appctnSttsNm eq '보완완료' || rtnData.applyList[1].appctnSttsNm eq '적합'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnData.applyList[1].appctnSttsNm eq '보완요청' || rtnData.applyList[1].appctnSttsNm eq '부적합'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnData.applyList[0].appctnSttsNm}
                                    </span></p>
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnData.applyList[1].rtrnRsnCntn && rtnData.applyList[1].appctnSttsCd eq 'PRO_TYPE07001_03_003'}">
                                    <p class="exclamation-txt f-body1">${rtnData.applyList[1].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">투자금액<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" class="comma" name="exam.wbgaMsEuipmentDTO.nvstmPmt" placeholder="투자금액 입력" value="${rtnData.applyList[1].msEquipmentList[0].nvstmPmt}">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnData.applyList[1].appctnSttsCd eq 'PRO_TYPE07001_03_001' || rtnData.applyList[1].appctnSttsCd eq 'PRO_TYPE07001_03_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="2" data-status="${rtnData.applyList[1].appctnSttsNm}"><span>저장</span></a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                    <div class="list-item <c:if test="${rtnData.stageOrd eq 3}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <form name="frmData3" id="frmData3">
                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">증빙</p>
                                </div>
                                <c:if test="${not empty rtnData.applyList[2].appctnSttsNm}">
                                    <c:choose>
                                        <c:when test="${rtnData.applyList[2].appctnSttsNm eq '접수전'}">
                                            <c:set var="classType" value="waiting" />
                                        </c:when>
                                        <c:when test="${rtnData.applyList[2].appctnSttsNm eq '접수완료' || rtnData.applyList[2].appctnSttsNm eq '보완완료' || rtnData.applyList[2].appctnSttsNm eq '적합'}">
                                            <c:set var="classType" value="accepting" />
                                        </c:when>
                                        <c:when test="${rtnData.applyList[2].appctnSttsNm eq '보완요청' || rtnData.applyList[2].appctnSttsNm eq '부적합'}">
                                            <c:set var="classType" value="arr" />
                                        </c:when>
                                    </c:choose>
                                    <p class="box-label bigger ${classType}"><span>
                                            ${rtnData.applyList[2].appctnSttsNm}
                                    </span></p>
                                </c:if>
                            </a>
                            <div class="acco-hide-area">
                                <c:if test="${not empty rtnData.applyList[2].rtrnRsnCntn && rtnData.applyList[2].appctnSttsCd eq 'PRO_TYPE07001_05_003'}">
                                    <p class="exclamation-txt f-body1">${rtnData.applyList[2].rtrnRsnCntn}</p>
                                </c:if>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청내용</p>
                                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                        </div>

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
                                                        <p class="data-title f-body1">영수증<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <c:if test="${rtnData.applyList[2].appctnSttsCd eq 'PRO_TYPE07001_05_001' || rtnData.applyList[2].appctnSttsCd eq 'PRO_TYPE07001_05_003'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" name="atchFile" id="searchFile2" class="searchFile">
                                                                    <input type="hidden" name="fileSeqList" value="${rtnData.applyList[2].fileInfoList[0].fileSeq}"/>
                                                                    <input type="hidden" name="exam.fileCdList" value="ATTACH_FILE_TYPE14">
                                                                    <label class="btn-solid gray-bg" for="searchFile2">파일 찾기</label>
                                                                </div>
                                                            </c:if>
                                                            <!-- 2024-01-03 추가 -->
                                                            <div class="file-prev-area">
                                                                <a href="/file/download?fileSeq=${rtnData.applyList[2].fileInfoList[0].fileSeq}&fileOrd=0" download="" title="파일 다운로드">${rtnData.applyList[2].fileInfoList[0].fileNm}</a>
                                                            </div>
                                                            <!-- 2024-01-03 추가 -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rtnData.applyList[2].appctnSttsCd eq 'PRO_TYPE07001_05_001' || rtnData.applyList[2].appctnSttsCd eq 'PRO_TYPE07001_05_003'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="3" data-status="${rtnData.applyList[2].appctnSttsNm}"><span>저장</span></a>
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