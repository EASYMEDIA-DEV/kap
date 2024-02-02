<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/mp/mpc/MPConsultingListCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <%--<p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>--%>
            <p class="page-tit f-xlarge-title"><span class="for-move">컨설팅 사업 <br/>신청내역</span></p>
        </div>
    </div>
    <div class="divide-con-area" >
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <form class="form-horizontal" id="frmData" name="frmData">
                        <div class="sec-con-area">
                            <div class="info-head">
                                <div class="left">
                                    <p class="article-total-count f-body2">총 <span>${totalCnt}</span>건</p>
                                    <div class="sort-select">
                                        <div class="form-select txt-select">
                                            <select id="ordFlag" name="ordFlag" title="정렬 바꾸기">
                                                <option value="1" selected="">업데이트순</option>
                                                <option value="2">신청일</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="right">
                                    <a class="filter-open-btn" href="javascript:" title="필터 열기">
                                        <span>필터</span>
                                    </a>
                                </div>
                            </div>

                            <div class="filter-popup">
                                <div class="for-flex">
                                    <div class="for-center">
                                        <div class="filter-con-area">
                                            <div class="data-enter-form">
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head">사업구분</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <div class="opt-group total-check-w">
                                                                    <div class="form-checkbox total-check">
                                                                        <input type="checkbox" id="businessChk1" name="statusChk" value="" checked>
                                                                        <label for="businessChk1">전체</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessChk2" name="statusChk" value="CONSULT_GB01" checked>
                                                                        <label for="businessChk2">기술지도</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessChk3" name="statusChk" value="CONSULT_GB02" checked>
                                                                        <label for="businessChk3">경영컨설팅</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head" name="q">키워드</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <div class="form-input srch-input w-longer">
                                                                    <input type="text" name="q" placeholder="사업명 입력">
                                                                    <div class="input-btn-wrap">
                                                                        <button class="delete-btn" title="지우기" type="button"></button>
                                                                        <button class="srch-btn searchBtn" title="검색"></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head">기간검색</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <p class="data-title f-body2">신청일자</p>
                                                                <div class="form-group form-calendar">
                                                                    <div class="form-input">
                                                                        <input type="text" placeholder="2023.01.01">
                                                                    </div>
                                                                    <div class="form-input calendar">
                                                                        <input type="text" placeholder="2023.01.01">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btn-wrap">
                                            <div class="btn-set">
                                                <button class="btn-solid small gray-bg btn-role-close" type="button"><span>닫기</span><!--ㅠ-->
                                                </button></div>
                                            <div class="btn-set">
                                                <button class="btn-solid small gray-bg filterInit"><span>필터 초기화</span></button>
                                                <button class="btn-solid small black-bg" id="btnSearch"><span>적용</span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <c:choose>
                                <c:when test="${not empty rtnData }">
                                    <div class="trainings-list-w">
                                        <c:forEach var="appctnList" items="${rtnData}" varStatus="status">
                                        <div class="training-confirm infoCard openCard">
                                            <div class="top-info">
                                                <div class="training-view-page">
                                                    <div class="training-list">
                                                        <!-- <div class="img-area">
                                                          <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                                        </div> -->
                                                        <c:choose>
                                                            <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB01'}">
                                                                <div class="txt-area">
                                                                    <input type="hidden" name="cnstgSeq" class="cnstgSeq" value="${appctnList.cnstgSeq}">
                                                                    <input type="hidden" name="cnstgCd" class="cnstgCd" value="${appctnList.cnstgCd}">
                                                                    <div class="top-line">
                                                                        <div class="sort-label-area">
                                                                            <p class="f-caption2"><span>${appctnList.cnstgNm}</span></p>
                                                                        </div>
                                                                            <p class="training-name f-title3 dtlBtn" data-seq="${appctnList.cnstgSeq}">${appctnList.bsnYear} 상주기술지도 </p>
                                                                    </div>
                                                                    <div class="group">
                                                                        <c:choose>
                                                                            <c:when test="${appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_08' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_09' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_10' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_11' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_12' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_13'}">
                                                                                <p class="index-num f-head ${appctnList.cnstgSeq}statusType">지도단계</p>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <p class="index-num f-head ${appctnList.cnstgSeq}statusType">신청단계</p>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <c:choose>
                                                                            <c:when test="${fn:contains(appctnList.rsumeSttsNm, '탈락')}">
                                                                                <div class="status-info-w">
                                                                                    <p class="box-label bigger arr"><span>${appctnList.rsumeSttsNm}</span></p>
                                                                                </div>
                                                                                <div class="tooltip-wrap">
                                                                                    <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                                                                    <div class="tooltip-box">
                                                                                        <p class="txt f-caption2">${appctnList.rsltCntn}</p>
                                                                                        <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                                                                    </div>
                                                                                </div>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <div class="status-info-w">
                                                                                    <p class="box-label bigger accepting"><span>${appctnList.rsumeSttsNm}</span></p>
                                                                                </div>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB02'}">
                                                            <div class="txt-area open">
                                                                <div class="top-line">
                                                                    <div class="sort-label-area">
                                                                        <p class="f-caption2"><span>${appctnList.cnstgNm}</span></p>
                                                                    </div>
                                                                        <p class="training-name f-title3 dtlBtn" data-seq="${appctnList.cnstgSeq}">${appctnList.bsnYear} 상주경영컨설팅 </p>
                                                                </div>
                                                                <div class="group">
                                                                    <c:choose>
                                                                        <c:when test="${appctnList.rsumeSttsCd eq 'MNGTECH_STATUS10' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS11' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS12'}">
                                                                            <p class="index-num f-head ${appctnList.cnstgSeq}statusType">지도단계</p>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p class="index-num f-head ${appctnList.cnstgSeq}statusType">신청단계</p>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                        <c:choose>
                                                                            <c:when test="${fn:contains(appctnList.rsumeSttsNm, '탈락')}">
                                                                                <div class="status-info-w">
                                                                                    <p class="box-label bigger arr"><span>${appctnList.rsumeSttsNm}</span></p>
                                                                                </div>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <div class="status-info-w">
                                                                                    <p class="box-label bigger accepting"><span>${appctnList.rsumeSttsNm}</span></p>
                                                                                </div>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        <div class="bot-info">
                                            <div class="index-list-w">
                                                <div class="list-item">
                                                    <div class="cont">
                                                        <div class="cont-area">
                                                            <div class="info-list-w ">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">신청업종</p>
                                                                    <c:choose>
                                                                        <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB01'}">
                                                                            <p class="txt f-body2">${appctnList.cbsnNm}</p>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p class="txt f-body2">${appctnList.appctnFldNm}</p>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">신청사항</p>
                                                                        <p class="txt f-body2 ${appctnList.cnstgSeq}appctnCd" style="text-overflow: ellipsis"></p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">신청일자</p>
                                                                        <p class="txt f-body2">${kl:convertDate(appctnList.appctnDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '-')}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    </div>
                                    </div>
                                <div class="btn-wrap add-load align-center moreBtn">
                                    <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count cntText"></span></a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="index-list-w">
                                    <!-- 데이터 없을 경우 노출되는 영역 -->
                                    <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
                                        <div class="txt-box">
                                            <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                        </div>
                                    </div>
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
    </div>
</div>