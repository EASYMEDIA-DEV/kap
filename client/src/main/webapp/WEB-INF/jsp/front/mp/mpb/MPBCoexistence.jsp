<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBCocWriteCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <c:forEach var="item" items="${rtnInfo.applyList}" varStatus="status">
                        <c:choose>
                            <c:when test="${item.applyDtl.appctnSttsNm eq '접수전'}">
                                <c:set var="classType" value="waiting" />
                            </c:when>
                            <c:when test="${item.applyDtl.appctnSttsNm eq '접수완료' || item.applyDtl.appctnSttsNm eq '보완완료' || item.applyDtl.appctnSttsNm eq '적합' || item.applyDtl.appctnSttsNm eq '선정'}">
                                <c:set var="classType" value="accepting" />
                            </c:when>
                            <c:when test="${item.applyDtl.appctnSttsNm eq '보완요청' || item.applyDtl.appctnSttsNm eq '부적합' || item.applyDtl.appctnSttsNm eq '미선정'}">
                                <c:set var="classType" value="arr" />
                            </c:when>
                            <c:when test="${item.applyDtl.appctnSttsNm eq '사용자취소'}">
                                <c:set var="classType" value="end" />
                            </c:when>
                        </c:choose>
                        <div class="list-item <c:if test="${rtnInfo.stageOrd eq item.applyDtl.rsumeOrd}">active</c:if>"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                            <form name="frmData${status.index}" id="frmData${status.index}">
                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" class="notRequired" name="bsnCd" value="${rtnInfo.bsnCd}" />
                                <a class="acco-click-area" href="javascript:">
                                    <div class="txt-box">
                                        <p class="tit f-head">${item.stageNm}</p>
                                    </div>
                                    <c:if test="${not empty item.applyDtl.appctnSttsNm}">
                                        <p class="box-label bigger ${classType}"><span>${item.applyDtl.appctnSttsNm}</span></p>
                                    </c:if>
                                </a>
                                <div class="acco-hide-area">
                                    <c:if test="${not empty item.applyDtl.rtrnRsnCntn && item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_3'}">
                                        <p class="exclamation-txt f-body1">${item.applyDtl.rtrnRsnCntn}</p>
                                    </c:if>
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
                                                    <c:choose>
                                                        <c:when test="${not empty item.applyOptnList}">
                                                            <c:forEach var="itemOptn" items="${item.applyOptnList}" varStatus="status1">
                                                                <div class="data-line">
                                                                    <div class="inner-line">
                                                                        <p class="data-title f-body1">${itemOptn.optnNm}<span class="essential-mark color-sky">*</span></p>
                                                                        <div class="form-group">
                                                                            <c:if test="${item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_1' || item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_3'}">
                                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                    <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                </div>
                                                                                <div class="file-btn-area">
                                                                                    <input type="file" name="atchFile${status1.index}" id="searchFile${status1.index}" class="searchFile">
                                                                                    <input type="hidden" name="fileSeqList" value="${itemOptn.fileSeq}"/>
                                                                                    <label class="btn-solid gray-bg" for="searchFile${status1.index}">파일 찾기</label>
                                                                                </div>
                                                                            </c:if>
                                                                            <!-- 2024-01-03 추가 -->
                                                                            <c:if test="${not empty itemOptn.fileSeq}">
                                                                                <div class="file-prev-area">
                                                                                    <a href="/file/download?fileSeq=${itemOptn.fileSeq}&fileOrd=0" download="" title="파일 다운로드">${itemOptn.fileNm}</a>
                                                                                </div>
                                                                            </c:if>
                                                                            <!-- 2024-01-03 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="itemOptn" items="${item.applyTempOptnList}" varStatus="status1">
                                                                <div class="data-line">
                                                                    <div class="inner-line">
                                                                        <p class="data-title f-body1">${itemOptn.optnNm}<span class="essential-mark color-sky">*</span></p>
                                                                        <div class="form-group">
                                                                            <c:if test="${item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_1' || item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_3'}">
                                                                                <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                    <p class="empty-txt">선택된 파일 없음</p>
                                                                                    <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                </div>
                                                                            </c:if>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" name="atchFile${status1.index}" id="searchFile${status1.index}" class="searchFile">
                                                                                <input type="hidden" name="fileSeqList" value="${itemOptn.fileSeq}"/>
                                                                                <label class="btn-solid gray-bg" for="searchFile${status1.index}">파일 찾기</label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_1' || item.applyDtl.appctnSttsCd eq 'PRO_TYPE04_2_3'}">
                                        <div class="btn-wrap align-right">
                                            <a class="btn-solid small black-bg modify" href="javascript:" data-Seq="${status.index}" data-Status="${item.applyDtl.appctnSttsNm}"><span>저장</span></a>
                                        </div>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
