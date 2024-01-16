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
                                        <p class="f-title3">컨설팅 만족도 설문 조사</p>
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
                                <p class="f-title3">신청자 기본정보 </p>
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
                                                <td>${rtnData.deptNm}(${rtnData.deptDtlNm})</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>${rtnData.pstnNm}</td>
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
                                                <td>${rtnData.bsnmNo}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${rtnData.cmpnNm}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${rtnData.rprsntNm}</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${rtnData.ctgryCd eq 'COMPANY01001'}">1차</c:when>
                                                        <c:when test="${rtnData.ctgryCd eq 'COMPANY01002'}">2차</c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02001'}">대기업</c:when>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02002'}">중견기업</c:when>
                                                        <c:when test="${rtnData.sizeCd eq 'COMPANY02003'}">중소기업</c:when>
                                                        <c:otherwise>기타</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${rtnData.stbsmDt}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${rtnData.cmpnTelNo}</td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>(${rtnData.zipcode}) ${rtnData.bscAddr} ${rtnData.dtlAddr}</td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>${rtnData.slsPmt}(${rtnData.slsYear})</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>${rtnData.mpleCnt}</td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:if test="${not empty rtnData.mjrPrdct1}">①${rtnData.mjrPrdct1}</c:if>
                                                    <c:if test="${not empty rtnData.mjrPrdct1}">②${rtnData.mjrPrdct2}</c:if>
                                                    <c:if test="${not empty rtnData.mjrPrdct1}">③${rtnData.mjrPrdct3}</c:if>
                                                </td>
                                            </tr>
                                            <c:choose>
                                                <c:when test="${rtnData.ctgryCd eq 'COMPANY01001'}">
                                                    <c:forEach var="info" items="${cmpnInfo.list}" varStatus="status">
                                                        <tr>
                                                            <th>품질5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.qlty5StarCdNm}">
                                                                    ${info.qlty5StarCdNm} / ${info.qlty5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.qlty5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>납입5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.pay5StarCdNm}">
                                                                    ${info.pay5StarCdNm} / ${info.pay5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.pay5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>기술5스타</th>
                                                            <td>
                                                                <c:if test="${not empty info.tchlg5StarCdNm}">
                                                                    ${info.tchlg5StarCdNm} / ${info.tchlg5StarYear}년
                                                                </c:if>
                                                                <c:if test="${empty info.tchlg5StarCdNm}">
                                                                    -
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <th>SQ정보</th>
                                                        <td>
                                                            <c:forEach var="info" items="${cmpnInfo.list}" varStatus="status">
                                                                <c:set var="i" value="${i+1}"/>
                                                                <p>${i}. ${info.nm} / ${info.score} / ${info.year} / ${info.crtfnCmpnNm}</p>
                                                            </c:forEach>
                                                        </td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
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
                                                    <c:forEach var="dlvryList" items="${rtnData.dlvryCmpnList}" varStatus="status">
                                                        <div class="dash-list">
                                                            <div class="item">
                                                                <p class="item-title f-body2">업체명</p>

                                                                <p class="item-txt f-head">${dlvryList.dlvryCmpnNm}</p>
                                                            </div>
                                                            <div class="item">
                                                                <p class="item-title">매출비중</p>
                                                                <p class="item-txt f-head">${dlvryList.dlvryRate}%</p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">완성차 의존율</p>
                                            </div>
                                            <div class="td">
                                                <div class="dash-list-w">
                                                    <c:forEach var="dpndList" items="${rtnData.dpndCmpnList}" varStatus="status">
                                                        <div class="dash-list">
                                                            <div class="item">
                                                                <p class="item-title f-body2">업체명</p>
                                                                <p class="item-txt f-head">${dpndList.dpndnCmpnNm}</p>
                                                            </div>
                                                            <div class="item">
                                                                <p class="item-title">의존비중</p>
                                                                <p class="item-txt f-head">${dpndList.dpndnRate}%</p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">품질담당 인원</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${rtnData.mpleCnt}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">FAX</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${rtnData.faxNo}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">지도요청 공장주소</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">
                                                    ${rtnData.fctryZipcode} ${rtnData.fctryBscAddr}
                                                        <br/>${rtnData.fctryDtlAddr}
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">소재지역</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">${rtnData.firstRgnsNm} ${rtnData.scndRgnsNm}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">대표자 승인여부</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">
                                                    <c:choose>
                                                        <c:when test="${rtnData.rprsntApprvYn eq 'Y'}">
                                                            승인
                                                        </c:when>
                                                        <c:otherwise>
                                                            미승인
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">신청사유</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">
                                                <c:choose>
                                                    <c:when test="${rtnData.appctnRsnCd eq 'APPCTN_RS_CD_TYPE01'}">
                                                        자발적 신청
                                                    </c:when>
                                                    <c:when test="${rtnData.appctnRsnCd eq 'APPCTN_RS_CD_TYPE02'}">
                                                        1차 부품사 권유
                                                    </c:when>
                                                    <c:otherwise>
                                                        완성차 권유
                                                    </c:otherwise>
                                                </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">업종</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1">
                                                    <c:choose>
                                                        <c:when test="${fn:contains(rtnData.cbsnCd,'INDUS')}">
                                                            기타 - ${rtnData.etcNm}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${fn:contains(rtnData.cbsnCd,'METAL')}">금속분야 - ${rtnData.cbsnNm}</c:if>
                                                            <c:if test="${fn:contains(rtnData.cbsnCd,'NON')}">비금속분야 - ${rtnData.cbsnNm}</c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-body2">신청사항</p>
                                            </div>
                                            <div class="td">
                                                <p class="txt f-body1" id="appctnType"><c:forEach var="appctnTypeList" items="${rtnData.appctnTypeList}" varStatus="status">${appctnTypeList.appctnTypeNm}/</c:forEach></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${srvCnt > 0}"> <!-- 설문 응답 가능 기간에 포함되는 설문-->
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">컨설팅 만족도 설문조사 참여내역</p>
                                </div>
                                <div class="sec-con-area">
                                    <c:forEach var="srvList" items="${rtnData.rsumeList}" varStatus="status">
                                        <div class="gray-bg-sec">
                                            <div class="con-list-box-w">
                                                <div class="con-list-box">
                                                    <p class="f-head">컨설팅 만족도 설문</p>
                                                    <div class="ul-txt-w info">
                                                        <div class="ul-txt-list">
                                                            <div class="ul-txt">
                                                                <dl><dt class="f-caption2">등록일시</dt>
                                                                    <dd class="f-caption1">${ kl:convertDate(srvList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</dd>
                                                                </dl>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${rspnCnt != 1 }"> <!-- 설문 조사 참여 여부 -->
                                                <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                    <p class="txt f-body1">미참여</p>
                                                </div>
                                            </c:if>
                                            <c:if test="${rspnCnt == 1 }">
                                                <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                    <p class="txt f-body1">참여완료</p>
                                                </div>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion align-right">
                        <a class="btn-solid small black-bg" href="/consulting/list"><span>목록</span></a>
                    </div>
                </div>
            </div>
        </div>
        </div>