<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="fileDtlList" value="${rtnData.fileDtlList}"/>
<c:set var="rsumeList" value="${rtnData.rsumeList}" />
<c:set var="lastStep" value="1" />
<c:forEach var="rsumeCnt" items="${rsumeList}" varStatus="status">
    <c:set var="lastStep" value="${rsumeCnt.rsumeOrd}" />
</c:forEach>

<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBFutureCarWriteCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3 lastStep" data-laststep="${lastStep}">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <form name="frmData" enctype="multipart/form-data">
                        <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeSeq" value="${rtnData.rsumeSeq}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeOrd" value="${rsumeList[0].rsumeOrd}" />

                        <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">서류심사</p>
                                </div>
                                <p class="box-label bigger "> <%-- accepting : 나머지 , waiting : 접수완료  , arr : 탈락  --%>
                                    <span class="rsumeColor">
                                    <c:choose>
                                        <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                        <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                        <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                        <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                        <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT004'}">사용자취소</c:when>
                                        <c:otherwise>접수전</c:otherwise>
                                    </c:choose>
                                    </span>
                                </p>
                            </a>
                            <div class="acco-hide-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                            <%--<p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>--%>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <%--<div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</p>
                                                    </div>
                                                </div>--%>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <%--<p class="data-title f-body1">신청서<span class="essential-mark color-sky">*</span></p>--%>
                                                        <div class="form-group">
                                                            <%--<c:if test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT001'}">
                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile1" class="searchFile" name="atchFile1" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip">
                                                                    <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                                                                    <input type="hidden" class="notRequired" name="wBKBRegisterDTO.fileCd" value="ATTACH_FILE_TYPE10" title="첨부파일유형"/>
                                                                </div>
                                                            </c:if>--%>
                                                            <div class="file-prev-area">
                                                                <a href="/file/download?fileSeq=${fileDtlList[0].fileSeq}&fileOrd=${fileDtlList[0].fileOrd}"
                                                                   download=""
                                                                   title="파일 다운로드"
                                                                >${fileDtlList[0].orgFileNm}</a><!-- 204-01-03 속성 변경 -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--<c:if test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT001'}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg btnUpdate" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>--%>
                            </div>
                        </div>
                    </form>

                    <form name="frmData" enctype="multipart/form-data">
                        <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeSeq" value="${rtnData.rsumeSeq}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeOrd" value="${rsumeList[1].rsumeOrd}" />

                        <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">1차 심사</p>
                                </div>
                                <p class="box-label bigger rsumeColor">
                                    <span>
                                        <c:choose>
                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                            <c:otherwise>접수전</c:otherwise>
                                        </c:choose>
                                    </span>
                                </p>
                            </a>
                            <div class="acco-hide-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                            <c:if test="${empty fileDtlList[1]}">
                                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                            </c:if>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <c:if test="${empty fileDtlList[1]}">
                                                    <div class="data-line">
                                                        <div class="noti-txt-w">
                                                            <P class="bullet-noti-txt f-caption2">*  확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,dox,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개) 가능</P>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">신청서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">


                                                            <c:choose>
                                                                <c:when test="${not empty fileDtlList[1]}">
                                                                    <div class="file-prev-area">
                                                                        <a href="/file/download?fileSeq=${fileDtlList[1].fileSeq}&fileOrd=${fileDtlList[1].fileOrd}"
                                                                           download=""
                                                                           title="파일 다운로드"
                                                                        >${fileDtlList[1].orgFileNm}</a><!-- 204-01-03 속성 변경 -->
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <%--<c:if test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT001'}">--%>
                                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                                        </div>
                                                                        <div class="file-btn-area">
                                                                            <input type="file" id="searchFile2" class="searchFile" name="atchFile2">
                                                                            <label class="btn-solid gray-bg" for="searchFile2">파일 찾기</label>
                                                                            <input type="hidden" class="notRequired" name="wBKBRegisterDTO.fileCd" value="ATTACH_FILE_TYPE11" title="첨부파일유형"/>
                                                                        </div>
                                                                    <%--</c:if>--%>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            
                                <c:if test="${empty fileDtlList[1]}">
                                    <c:if test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT001'}">
                                        <div class="btn-wrap align-right">
                                            <a class="btn-solid small black-bg btnUpdate" href="javascript:"><span>저장</span></a>
                                        </div>
                                    </c:if>
                                </c:if>

                            </div>
                        </div>
                    </form>

                    <form name="frmData" enctype="multipart/form-data">
                        <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeSeq" value="${rtnData.rsumeSeq}" />
                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.rsumeOrd" value="${rsumeList[2].rsumeOrd}" />

                        <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                            <a class="acco-click-area" href="javascript:">
                                <div class="txt-box">
                                    <p class="tit f-head">최종 심사</p>
                                </div>
                                <p class="box-label bigger rsumeColor">
                                    <span>
                                        <c:choose>
                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                            <c:otherwise>접수전</c:otherwise>
                                        </c:choose>
                                    </span>
                                </p>
                            </a>
                            <div class="acco-hide-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일</p>
                                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</p>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">신청서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">

                                                            <c:choose>
                                                                <c:when test="${not empty fileDtlList[2]}">
                                                                    <div class="file-prev-area">
                                                                        <a href="/file/download?fileSeq=${fileDtlList[2].fileSeq}&fileOrd=${fileDtlList[2].fileOrd}"
                                                                           download=""
                                                                           title="파일 다운로드"
                                                                        >${fileDtlList[2].orgFileNm}</a>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                        <div class="file-list">
                                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                                            <button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button>
                                                                        </div>
                                                                    </div>
                                                                    <div class="file-btn-area">
                                                                        <input type="file" id="searchFile3" class="searchFile" name="atchFile3">
                                                                        <label class="btn-solid gray-bg" for="searchFile3">파일 찾기</label>
                                                                        <input type="hidden" class="notRequired" name="wBKBRegisterDTO.fileCd" value="ATTACH_FILE_TYPE12" title="첨부파일유형"/>
                                                                    </div>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${empty fileDtlList[2]}">
                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg btnUpdate" href="javascript:"><span>저장</span></a>
                                    </div>
                                </c:if>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
