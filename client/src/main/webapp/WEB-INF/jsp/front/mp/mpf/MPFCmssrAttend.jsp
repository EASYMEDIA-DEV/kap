<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>


<div id="wrap" class="mypage" data-controller="controller/mp/mpf/MPFCmssrAttendController"><!-- 마이페이지 mypage 클래스 추가 -->
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <form name="formAttendSubmit" id="formAttendSubmit"  >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">근태체크</span></p>
            </div>
        </div>
        <div class="divide-con-area">
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

            <div class="right-con-area">
                <div class="cont-sec-w">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <div class="left">

                                    <p class="f-title1 gray-txt"><span class="key-txt">${loginMap.name}</span> 위원님 <span class="key-txt"> ${now_dt.date[0]}년  ${now_dt.date[1]}월  ${now_dt.date[2]}일</span> <br>어디로 출근하시나요?</p>
                                </div>
                                <div class="btn-wrap">
                                    <a class="btn-text-icon black-arrow" href="javascript:"><span>자료 업로드</span></a>
                                </div>
                            </div>

                            <div class="sec-con-area">
                                <div class="time-card-area">
                                    <div class="title-area">
                                        <p class="f-title2"> ${now_dt.date[0]}  ${now_dt.date[1]} 출근부</p>
                                    </div>
                                    <div class="time-card-box-w">
                                        <div class="time-card">
                                            <p class="card-title">출근</p>
                                            <p class="day-count  <c:if test="${rtnData.atndnCnt eq 0 }">zero</c:if>" > ${rtnData.atndnCnt}</p>
                                        </div>
                                        <div class="time-card">
                                            <p class="card-title">재택</p>
                                            <p class="day-count  <c:if test="${rtnData.homeCnt eq 0 }">zero</c:if>" > ${rtnData.homeCnt}</p>

                                        </div>
                                        <div class="time-card">
                                            <p class="card-title">연차</p>
                                            <p class="day-count  <c:if test="${rtnData.annualCnt eq 0 }">zero</c:if>" > ${rtnData.annualCnt}</p>

                                        </div>
                                        <div class="time-card">
                                            <p class="card-title">출장 </p>
                                            <div class="biz-trip-w">
                                                <div class="biz-trip-list">
                                                    <p class="day-count  <c:if test="${rtnData.cnstgCnt eq 0 }">zero</c:if>" > ${rtnData.cnstgCnt}</p>
                                                    <p class="info-txt">지도</p>
                                                </div>
                                                <div class="biz-trip-list">
                                                    <p class="day-count  <c:if test="${rtnData.lectureCnt eq 0 }">zero</c:if>" > ${rtnData.lectureCnt}</p>
                                                    <p class="info-txt">강의</p>
                                                </div>
                                                <div class="biz-trip-list">
                                                    <p class="day-count  <c:if test="${rtnData.cpcptyDvlpmCnt eq 0 }">zero</c:if>" > ${rtnData.cpcptyDvlpmCnt}</p>
                                                    <p class="info-txt">역량개발</p>
                                                </div>
                                                <div class="biz-trip-list">
                                                    <p class="day-count  <c:if test="${rtnData.etcCnt eq 0 }">zero</c:if>" > ${rtnData.etcCnt}</p>
                                                    <p class="info-txt">기타</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="attendance-area data-line-w">
                                    <div class="option-row">
                                        <c:forEach var="list" items="${cdDtlList.CMSSR_ATTEND}" varStatus="status">
                                            <c:if test="${list.cd eq 'CMSSR_ATTEND_001'}">
                                                <div class="form-radio bigger">
                                                    <input type="radio" id="attendanceRadio${status.index}" name="atndcCd" value="${list.cd}" title="근태 옵션"  ${rtnDataCmpn.atndcCd == list.cd ? 'checked' : rtnDataCmpn.atndcCd == null ? '' :'disabled'}>
                                                    <label for="attendanceRadio${status.index}">${list.cdNm}</label>
                                                </div>
                                                <div class="data-enter-form">
                                                    <div class="row horizontally"><!-- horizontally 옆으로 나란히 배치 -->
                                                        <div class="th">
                                                            <p class="title f-head">지도부품사1</p>
                                                        </div>
                                                        <div class="td">
                                                            <div class="data-line-w">
                                                                <div class="data-line">
                                                                    <div class="form-group">
                                                                        <div class="form-select">
                                                                            <select id="guidePartCmpn1" title="지도부품사 선택" name="guidePartCmpn1" title="지도부품사1" class="notRequired" ${rtnDataCmpn.atndcCd == null ? '' : 'disabled'}>
                                                                                <option value="" selected="">선택</option>
                                                                                <c:forEach var="list" items="${cmpnData.list}" varStatus="status">
                                                                                    <option <c:if test="${rtnDataCmpn.cnstgSeq1 eq list.cnstgSeq}">selected</c:if>  value="${list.cmpnNm}/${list.firstRgnsCdNm} ${list.scndRgnsCdNm}/${list.cnstgSeq}">${list.cmpnNm}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 지도부품사 선택 시 해당 지도 부품사 신청 소재지역 표시 -->
                                                        <div class="th">
                                                            <p class="title f-head">소재지역1</p>
                                                        </div>
                                                        <div class="td">
                                                            <p class="data-txt" id="rgnsOne" title="소재지역1">${rtnDataCmpn.rgns1 != null ?rtnDataCmpn.rgns1 :'-'}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row horizontally"><!-- horizontally 옆으로 나란히 배치 -->
                                                        <div class="th">
                                                            <p class="title f-head">지도부품사2</p>
                                                        </div>
                                                        <div class="td">
                                                            <div class="data-line-w">
                                                                <div class="data-line">
                                                                    <div class="form-group">
                                                                        <div class="form-select">
                                                                            <select id="guidePartCmpn2" title="지도부품사 선택" name="guidePartCmpn2" class="notRequired" title="지도부품사2" ${rtnDataCmpn.atndcCd == null ? '' : 'disabled'}>
                                                                                <option value="" selected="">선택</option>
                                                                                <c:forEach var="list" items="${cmpnData.list}" varStatus="status">
                                                                                    <option <c:if test="${rtnDataCmpn.cnstgSeq2 eq list.cnstgSeq}">selected</c:if> value="${list.firstRgnsCdNm}/${list.firstRgnsCd} ${list.scndRgnsCdNm}/${list.scndRgnsCd}/${list.cnstgSeq}/${list.cmpnNm}">${list.cmpnNm}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 지도부품사 선택 시 해당 지도 부품사 신청 소재지역 표시 -->
                                                        <div class="th">
                                                            <p class="title f-head">소재지역2</p>
                                                        </div>
                                                        <div class="td">
                                                            <p class="data-txt" id="rgnsTwo" title="소재지역2">${rtnDataCmpn.rgns2 != null ?rtnDataCmpn.rgns2 :'-'}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="th">
                                                            <p class="title f-head">기타출장</p>
                                                        </div>
                                                        <div class="td">
                                                            <div class="form-input">
                                                                <input type="text" placeholder="출장지 입력" title="출장지" class="notRequired" name="etcBsntrp" ${rtnDataCmpn.atndcCd == null ? '' : 'disabled'}>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${list.cd eq 'CMSSR_ATTEND_002' or
                                                        list.cd eq 'CMSSR_ATTEND_003' or
                                                        list.cd eq 'CMSSR_ATTEND_004' or
                                                        list.cd eq 'CMSSR_ATTEND_005' or
                                                        list.cd eq 'CMSSR_ATTEND_006' }">
                                                <div class="option-row">
                                                    <div class="form-radio bigger">
                                                        <input type="radio" id="attendanceRadio${status.index}" name="atndcCd" value="${list.cd}" title="근태 옵션" ${rtnDataCmpn.atndcCd == list.cd ? 'checked' : rtnDataCmpn.atndcCd == null ? '' :'disabled'}>
                                                        <label for="attendanceRadio${status.index}" >${list.cdNm}</label>
                                                    </div>
                                                </div>
                                            </c:if>
                                    <c:if test="${list.cd eq 'CMSSR_ATTEND_007'}">
                                        <div class="option-row">
                                            <div class="etc-option-w">
                                                <div class="form-group">
                                                    <div class="form-radio bigger">
                                                        <input type="radio" id="etcattendanceRadio${status.index}" name="atndcCd" value="${list.cd}" title="근태 옵션" ${rtnDataCmpn.atndcCd == list.cd ? 'checked' : rtnDataCmpn.atndcCd == null ? '' :'disabled'}>
                                                        <label for="etcattendanceRadio${status.index}">기타</label>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="기타 입력" disabled="" id="etcInput" name="etc" class="notRequired" title="기타 내용" value="${rtnDataCmpn.etc}">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>

                                        </c:forEach>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec scroll-motion" <c:if test="${rtnDataCmpn.atndcCd != null}">style="display: none"</c:if> >
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                        </div>
                        <div class="btn-set">
                            <button class="btn-solid small black-bg" type="submit"><span>저장</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>