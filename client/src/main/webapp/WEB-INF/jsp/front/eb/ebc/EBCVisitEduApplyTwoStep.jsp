<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/eb/EBCVisitEduController">
    <form class="form-horizontal" id="frmData" name="frmData" method="post" >
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <!--
              교육 사업: edu-biz
              컨실팅 사업: consult-biz
              상생 사업: coexisting-biz
            -->
            <div class="sub-top-vis-area apply-page consult-biz">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">방문교육신청</span></p>
                    <div class="apply-step-w">
                        <div class="for-move">
                            <div class="step-list completed"><!-- 완료: , 진행 중: ongoing 클래스 추가 -->
                                <p class="step-num">1</p>
                                <p class="step-con">기본정보</p>
                            </div>
                            <div class="step-list ongoing">
                                <p class="step-num">2</p>
                                <p class="step-con">정보입력</p>
                            </div>
                            <div class="step-list">
                                <p class="step-num">3</p>
                                <p class="step-con">신청완료</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="img-area">
                    <div class="gray-bg"></div>
                    <div class="graphic-item-w">
                        <div class="item"></div>
                        <div class="item"></div>
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <div class="lnb-area">
                    <div class="for-motion">
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu" href="javascript:"><span>교육사업 소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu" href="javascript:"><span>교육신청</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu active" href="javascript:"><span>방문교육</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                    </div>
                </div>

                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">방문교육 신청내용을 입력해주세요</p>
                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">신청사유<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-textarea">
                                                                <textarea name="" id="appctnRsn" name="appctnRsn" placeholder="신청사유 입력"></textarea>
                                                                <div class="check-byte">
                                                                    <p class="txt" id="appctnRsnTextCnt"><span class="current-byte">0</span>자</p>
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
                                                <p class="title f-head">신청분야<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="inner-line">
                                                            <div class="form-group">
                                                                <div class="form-select">
                                                                    <select id="appctnFldCd" name="appctnFldCd" title="신청분야">
                                                                        <option value="">선택</option>
                                                                        <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                                                            <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD01') and fn:length(cdList.cd) eq 17}">
                                                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.appctnFldCd eq cdList.cd}">selected</c:if>>
                                                                                        ${cdList.cdNm}
                                                                                </option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="checkBoxArea"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">신청주제<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-textarea">
                                                                <textarea name="" id="appctnThemeCntn" name="appctnThemeCntn" placeholder="신청주제 입력"></textarea>
                                                                <div class="check-byte">
                                                                    <p class="txt" id="appctnThemeCntnTextCnt"><span class="current-byte">0</span>자</p>
                                                                    <p class="txt"><span class="max-byte">500</span>자</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="noti-txt-w">
                                                            <p class="bullet-noti-txt f-caption2">* 신청분야 상세내용을 참고하여 입력 바랍니다.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">교육희망일<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input calendar">
                                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="hopeDt" name="hopeDt" value="${kl:convertDate(rtnInfo.hopeDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="설립일자" />
                                                                <span class="input-group-btn" style="z-index:0;">
                                                                    <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                                                                        <em class="ion-calendar"></em>
                                                                    </button>
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">교육장소<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="middle-line">
                                                            <div class="form-checkbox">
                                                                <input type="checkbox" id="sameAsHQChk" name="">
                                                                <label for="sameAsHQChk">본사와 동일</label>
                                                            </div>
                                                        </div>
                                                        <div class="middle-line">
                                                            <div class="form-address">
                                                                <div class="form-group">
                                                                    <div class="form-input">
                                                                        <input type="text" id="placeZipcode" name="placeZipcode" placeholder="우편번호" value="" readonly="">
                                                                    </div>
                                                                    <div class="form-input w-longer">
                                                                        <input type="text" id="placeBscAddr" name="placeBscAddr" placeholder="주소" value="" readonly="">
                                                                    </div>
                                                                    <div class="btn-wrap">
                                                                        <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <div class="form-input w-longest">
                                                                        <input type="text" id="placeDtlAddr" name="placeDtlAddr" placeholder="상세주소 입력" value="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">참석대상<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-textarea">
                                                                <textarea id="ptcptTrgtCntn" name="ptcptTrgtCntn" placeholder="참석대상 입력"></textarea>
                                                                <div class="check-byte">
                                                                    <p class="txt" id="ptcptTrgtCntnTextCnt"><span class="current-byte">0</span>자</p>
                                                                    <p class="txt"><span class="max-byte">500</span>자</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="noti-txt-w">
                                                            <p class="bullet-noti-txt f-caption2">* 참석대상의 부서와 직급을 입력해주세요.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">교육 인원<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="number" id="ptcptCnt" name="ptcptCnt" value="" placeholder="인원수 입력">
                                                                <p class="unit-txt">명</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">교육 시간<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select id="ptcptHh" name="ptcptHh" title="시간 선택">
                                                                    <option value="">선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                                                        <c:if test="${cdList.cd ne 'EBC_VISIT_CD04' and fn:contains(cdList, 'EBC_VISIT_CD04')}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.ptcptHh eq cdList.cd}">selected</c:if>>
                                                                                    ${cdList.cdNm}
                                                                            </option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
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
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">방문교육 선정을 위한 첨부파일을 등록해주세요</p>
                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">회사소개서<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="noti-txt-w">
                                                            <p class="f-body2">방문교육 신청 시 회사소개서(자율 양식)가 필수로 첨부되어야 합니다.</p>
                                                            <p class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</p>
                                                        </div>
                                                    </div>
                                                    <div class="data-line">
                                                        <p class="data-title f-body1">첨부파일</p>
                                                        <div class="form-group">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" id="searchFile">
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

                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap for-motion">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
                            </div>
                            <div class="btn-set">
                                <a class="btn-solid small black-bg" href="javascript:"><span>신청하기</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
