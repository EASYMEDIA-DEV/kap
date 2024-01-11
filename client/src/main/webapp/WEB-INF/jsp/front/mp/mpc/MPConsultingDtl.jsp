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
                    <div class="cont-sec no-border scroll-motion" >
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3"></p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="flex">
                                        <input type="hidden" name="bsnYear" class="bsnYear" value="${rtnData.bsnYear}">
                                        <p class="f-title1">${rtnData.bsnYear} <br class="only-mobile"/>${rtnData.cnstgNm}</p>
                                        <div class="group">
                                            <c:if test="${rtnData.cnstgCd eq 'CONSULT_GB01'}">
                                                <c:choose>
                                                    <c:when test="${rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_08' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_09' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_10' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_11' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_12' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS_13'}">
                                                        <p class="f-head">지도단계</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p class="f-head">신청단계</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${rtnData.cnstgCd eq 'CONSULT_GB02'}">
                                                <c:choose>
                                                    <c:when test="${rtnData.rsumeSttsCd eq 'MNGTECH_STATUS10' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS11' || rtnData.rsumeSttsCd eq 'MNGTECH_STATUS12'}">
                                                        <p class="f-head">지도단계</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p class="f-head">신청단계</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${fn:contains(rtnData.rsumeSttsNm, '탈락')}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger arr"><span>${rtnData.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger accepting"><span>${rtnData.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="def-list-w">
                                        <div class="def-list">
                                            <p class="tit f-head">신청일자</p>
                                            <p class="txt f-sub-head">${rtnData.appctnDt}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${srvCnt > 0}"> <!-- 설문 응답 가능 기간에 포함되는 설문-->
                        <c:if test="${rspnCnt < 1 }"> <!-- 설문 조사 참여 여부 -->
                            <div class="cont-sec no-border scroll-motion">
                                <div class="for-motion">
                                    <div class="sec-tit-area">
                                        <p class="f-title3">컨설팅 만족도 설문 조사${rspnCnt}!!</p>
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="graphic-sec">
                                            <div class="box-btn-area">
                                                <div class="bg-area">
                                                    <div class="img" style="background-image: url('/common/images/img-assessment-btn-bg.jpg');"></div>
                                                </div>
                                                <div class="txt-area">
                                                    <p class="txt f-head">신청하신 컨설팅 사업에 대한 만족도 설문 조사에 참여해주세요.</p>
                                                </div>
                                                <div class="btn-wrap">
                                                    <a class="btn-solid small white-bg survey" data-seq="${rtnData.cnstgSeq}" href="javascript:"><span>참여하기</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">신청자 기본정보 ${rtnData}</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>신청자</th><!-- 2023-12-12 텍스트 수정 -->
                                                <td>${rtnData.name}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                <td>${rtnData.hpNo}</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${rtnData.email}</td>
                                            </tr>
                                            <!-- 2023-12-22 수정 -->
                                            <tr>
                                                <th>일반 전화번호</th>
                                                <td><c:if test="${not empty rtnData.telNo}">${rtnData.telNo}</c:if><c:if test="empty rtnData.telNo">-</c:if></td>
                                            </tr>
                                            <!-- // 2023-12-22 수정 -->
                                            <tr>
                                                <th>부서</th>
                                                <td>품질(품질기술지원팀)</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>과장</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">소속 부품사 기본정보</p><!-- 2023-12-12 텍스트 수정 -->
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>소속 부품사 기본정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>사업자등록번호</th>
                                                <td>123-45-67890</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>에이테크</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>홍상직</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>1차</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>중소기업</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>2002-07-15</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>031-1234-5678</td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>(04365) 서울 용산구 원효로 74 현대자동차원효로 사옥 5층</td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>100억 원(2022년)</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>50명</td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>① 알루미늄 실린더 ② 섀시</td>
                                            </tr>
                                            <tr>
                                                <th>품질5스타</th>
                                                <td>★★★★★ / 2022년</td>
                                            </tr>
                                            <tr>
                                                <th>납입5스타</th>
                                                <td>★★★ / 2018년</td>
                                            </tr>
                                            <tr>
                                                <th>기술5스타</th>
                                                <td>-</td>
                                            </tr>
                                            <tr>
                                                <th>SQ정보</th>
                                                <td>
                                                    <p>1. SQ1 / 2000 / 2018 년 / 와이즈랩</p>
                                                    <p>2. SQ3 / 2000 / 2018 년 / 와이즈랩</p>
                                                    <p>3. SQ2 / 2000 / 2018 년 / 와이즈랩</p>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">사업신청 정보</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="data-view-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">거래처별 매출비중</p>
                                            </div>
                                            <div class="td">
                                                <div class="dash-list-w">
                                                    <div class="dash-list">
                                                        <div class="item">
                                                            <p class="item-title f-body2">업체명</p>
                                                            <p class="item-txt f-head">㈜만도</p>
                                                        </div>
                                                        <div class="item">
                                                            <p class="item-title">매출비중</p>
                                                            <p class="item-txt f-head">10%</p>
                                                        </div>
                                                    </div>
                                                    <div class="dash-list">
                                                        <div class="item">
                                                            <p class="item-title f-body2">업체명</p>
                                                            <p class="item-txt f-head">㈜만도</p>
                                                        </div>
                                                        <div class="item">
                                                            <p class="item-title">매출비중</p>
                                                            <p class="item-txt f-head">10%</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">완성차 의존율</p>
                                            </div>
                                            <div class="td">
                                                <div class="dash-list-w">
                                                    <div class="dash-list">
                                                        <div class="item">
                                                            <p class="item-title f-body2">업체명</p>
                                                            <p class="item-txt f-head">㈜현대자동차테크놀로지비전_기아자동차테크놀로지비전</p>
                                                        </div>
                                                        <div class="item">
                                                            <p class="item-title">의존비중</p>
                                                            <p class="item-txt f-head">50%</p>
                                                        </div>
                                                    </div>
                                                    <div class="dash-list">
                                                        <div class="item">
                                                            <p class="item-title f-body2">업체명</p>
                                                            <p class="item-txt f-head">㈜현대자동차테크놀로지비전_기아자동차테크놀로지비전</p>
                                                        </div>
                                                        <div class="item">
                                                            <p class="item-title">의존비중</p>
                                                            <p class="item-txt f-head">50%</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">품질담당 인원</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">50 명</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">FAX</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">031-1234-5678</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">지도요청 공장주소</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">
                                                    12345 서울시 구로구 디지털로32길 28
                                                    <br/>1201호 ㈜이지미디어
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">소재지역</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">서울시 구로구</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">대표자 승인여부</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">승인</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">신청사유</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">자발적신청</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">업종</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">금속분야 - 도금</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">신청사항</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">공정관리 / 품질관리</p>
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
                                <p class="f-title3">컨설팅 만족도 설문조사 참여내역</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="f-head">컨설팅 만족도 설문 조사</p>
                                            <div class="ul-txt-w info">
                                                <div class="ul-txt-list">
                                                    <div class="ul-txt">
                                                        <dl><dt class="f-caption2">등록일시</dt><dd class="f-caption1">-</dd></dl>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                        <p class="txt f-body1">미참여</p>
                                    </div>
                                </div>
                                <div class="gray-bg-sec">
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="f-head">컨설팅 만족도 설문 조사</p>
                                            <div class="ul-txt-w info">
                                                <div class="ul-txt-list">
                                                    <div class="ul-txt">
                                                        <dl><dt class="f-caption2">등록일시</dt><dd class="f-caption1">2023.01.01 10:30</dd></dl>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                        <p class="txt f-body1">참여완료</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion align-right">
                    <a class="btn-solid small black-bg" href="javascript:"><span>목록</span></a>
                </div>
            </div>
        </div>
    </div>
</div>