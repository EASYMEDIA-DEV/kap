<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBCalibrationCtrl">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item active"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">신청</p>
                            </div>
                            <p class="box-label bigger arr"><span>${rtnData.applyList[0].appctnSttsNm}</span></p>
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
                                                    <c:set var="totalTchlg" value="${totalTchlg+status.index}"/>
                                                    <c:set var="totalTchlgCnt" value="${totalTchlgCnt+item.tchlgCnt}"/>
                                                        <div class="form-group equiment">
                                                            <div class="form-input w-longer">
                                                                <input type="text" class="tchlgNm" name="equiment.euipmentList[${status.index}].tchlgNm" value="${item.tchlgNm}" placeholder="장비명 입력" maxlength="50">
                                                            </div>
                                                            <div class="amount-div">
                                                                <button type="button" class="amount-btn <c:if test="${item.tchlgCnt == 1}">disabled</c:if> minus"><img src="/common/images/icon-add-data-minus.svg" alt=""></button><!-- 클릭 안될 때 disabled class 추가 -->
                                                                <div class="form-input">
                                                                    <input type="text" class="tchlgCnt numberChk" name="equiment.euipmentList[${status.index}].tchlgCnt" value="${item.tchlgCnt}" title="대상장비 수량">
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
                                                        <button type="button" class="amount-btn disabled"><img src="/common/images/icon-add-data-minus.svg" alt=""></button><!-- 클릭 안될 때 disabled class 추가 -->
                                                        <div class="form-input">
                                                            <input type="text" class="tchlgCnt" value="1" title="대상장비 수량">
                                                        </div>
                                                        <button type="button" class="amount-btn"><img src="/common/images/icon-add-data-plus.svg" alt=""></button>
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
                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                    </div>
                                                    <div class="file-btn-area">
                                                        <input type="file" id="searchFile">
                                                        <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                    <div class="file-prev-area">
                                                        <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">대상자비리스트<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">심사</p>
                            </div>
                            <p class="box-label bigger"><span>접수전</span></p>
                        </a>
                        <div class="acco-hide-area">
                            <p class="exclamation-txt f-body1">사업신청서에 전년도 매출액 누락되어 보완 바랍니다.</p>
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">신청내용</p>
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-title f-body1">검교정비용<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="검교정비용 입력" value="18,321,190">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">영수증</p>
                            </div>
                            <p class="box-label bigger"><span>접수전</span></p>
                        </a>
                        <div class="acco-hide-area">
                            <p class="exclamation-txt f-body1">사업신청서에 전년도 매출액 누락되어 보완 바랍니다.</p>
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
                                                    <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
