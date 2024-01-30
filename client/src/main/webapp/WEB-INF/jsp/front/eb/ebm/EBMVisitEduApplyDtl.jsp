<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/eb/ebm/EBMVisitEduApplyDtlCtrl">
    <input type="hidden" class="notRequired" id="vstSeq" name="vstSeq" value="${visitEduApplyList.vstSeq}" />
    <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${visitEduApplyList.memSeq}" />
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">교육사업 신청내역</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            <!--LNB 종료-->

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">방문교육</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="flex">
                                        <div class="def-list-w">
                                            <div class="def-list">
                                                <p class="tit f-head">강사</p>
                                                <p class="txt f-sub-head">
                                                    <c:forEach var="isttrList" items="${isttrList.list}">
                                                        <c:if test="${not empty isttrList.isttr1}">
                                                            ${isttrList.isttr1}
                                                        </c:if>
                                                        <c:if test="${not empty isttrList.isttr2}">
                                                            , ${isttrList.isttr2}
                                                        </c:if>
                                                        <c:if test="${not empty isttrList.isttr3}">
                                                            , ${isttrList.isttr3}
                                                        </c:if>
                                                        <c:if test="${not empty isttrList.isttr4}">
                                                            , ${isttrList.isttr4}
                                                        </c:if>
                                                        <c:if test="${not empty isttrList.isttr5}">
                                                            , ${isttrList.isttr5}
                                                        </c:if>
                                                        <c:if test="${not empty isttrList.isttr6}">
                                                            , ${isttrList.isttr6}
                                                        </c:if>
                                                        <c:if test="${empty isttrList.isttr1}">
                                                            -
                                                        </c:if>
                                                    </c:forEach>
                                                </p>
                                            </div>
                                            <div class="def-list">
                                                <p class="tit f-head">교육일자</p>
                                                <p class="txt f-sub-head">
                                                    <c:choose>
                                                        <c:when test="${not empty visitEduApplyList.edctnStrtDtm}">
                                                            ${kl:convertDate(visitEduApplyList.edctnStrtDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy.MM.dd', '')}
                                                            ~ ${kl:convertDate(visitEduApplyList.edctnEndDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy.MM.dd', '')}
                                                            (${visitEduApplyList.edctnDay}일간)
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p><!-- 2023-12-12 텍스트 수정 -->
                                            </div>
                                            <div class="def-list">
                                                <p class="tit f-head">신청일시</p>
                                                <p class="txt f-sub-head">
                                                    ${kl:convertDate(visitEduApplyList.regDtm , 'yyyy-MM-dd hh:mm:ss', 'yyyy.MM.dd hh:mm', '')}
                                                </p>
                                            </div>
                                        </div>
                                        <div class="group">
                                            <div class="status-info-w">
                                                <p class="box-label bigger"><span>${visitEduApplyList.edctnSttsName}</span></p>
                                            </div>
                                        </div>
                                    </div>

                                    <c:if test="${visitEduApplyList.edctnSttsCd eq 'EBC_VISIT_CD02001'}" >
                                        <div class="btn-wrap">
                                            <a class="btn-solid small black-bg" id="visitEduCancelBtn" href="javascript:"><span>신청취소</span></a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">신청자 기본정보</p>
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
                                                <th>신청자</th>
                                                <td>${applicantInfo.name}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty applicantInfo.hpNo}">
                                                            ${applicantInfo.hpNo}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty applicantInfo.email}">
                                                            ${applicantInfo.email}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>전화번호</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty applicantInfo.telNo}">
                                                            ${applicantInfo.telNo}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>부서</th>
                                                <td>${applicantInfo.deptCdNm}(${applicantInfo.deptDtlNm})</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>
                                                    ${applicantInfo.pstnCdNm}
                                                    <c:if test="${applicantInfo.pstnCd eq 'MEM_CD01007'}" >
                                                        (${applicantInfo.pstnNm})
                                                    </c:if>
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
                                                <td>${kl:bsnmNoConvert(rtnInfo.bsnmNo)}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${rtnInfo.cmpnNm}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${rtnInfo.rprsntNm}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명(약식)</th>
                                                <td>-</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>${rtnInfo.ctgryNm}</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>${rtnInfo.sizeNm}</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</td>
                                            </tr>
                                            <tr>
                                                <th>전화번호</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty rtnInfo.telNo}">
                                                            ${rtnInfo.telNo}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>(${rtnInfo.zipcode}) ${rtnInfo.bscAddr} ${rtnInfo.dtlAddr}</td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>${rtnInfo.slsPmt}억 원(${rtnInfo.slsYear}년)</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty rtnInfo.mpleCnt}">
                                                            ${rtnInfo.mpleCnt}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose> 명
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${empty rtnInfo.mjrPrdct1 and empty rtnInfo.mjrPrdct2 and empty rtnInfo.mjrPrdct3}">
                                                            -
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${not empty rtnInfo.mjrPrdct1}">
                                                                ① ${rtnInfo.mjrPrdct1}
                                                            </c:if>
                                                            <c:if test="${not empty rtnInfo.mjrPrdct2}">
                                                                ② ${rtnInfo.mjrPrdct2}
                                                            </c:if>
                                                            <c:if test="${not empty rtnInfo.mjrPrdct3}">
                                                                ③ ${rtnInfo.mjrPrdct3}
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <c:choose>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">
                                                    <tr>
                                                        <th>SQ정보</th>
                                                        <td>
                                                            <c:forEach var="list" items="${sqInfoList.list}" varStatus="status">
                                                                <p>
                                                                        ${status.count}.
                                                                        ${not empty list.nm ? list.nm : "-"}/
                                                                        ${not empty list.score ? list.score : "-"}/
                                                                        ${not empty list.year ? list.year : "-"} 년/
                                                                        ${not empty list.crtfnCmpnNm ? list.crtfnCmpnNm : "-"}
                                                                </p>
                                                            </c:forEach>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01001'}">
                                                    <tr>
                                                        <th>품질5스타</th>
                                                        <td>${rtnInfo.qlty5StarCdNm} / ${rtnInfo.qlty5StarYear}년</td>
                                                    </tr>
                                                    <tr>
                                                        <th>납입5스타</th>
                                                        <td>${rtnInfo.pay5StarCdNm} / ${rtnInfo.pay5StarYear}년</td>
                                                    </tr>
                                                    <tr>
                                                        <th>기술5스타</th>
                                                        <td>${rtnInfo.tchlg5StarCdNm} / ${rtnInfo.tchlg5StarYear}년</td>
                                                    </tr>
                                                </c:when>
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
                                <p class="f-title3">방문교육 신청내용</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="data-view-w">
                                        <div class="data-view-form">
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">신청사유</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.appctnRsn}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">신청분야</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.appctnFldName}</p>
                                                    <p class="txt f-body1 color-gray">
                                                        <c:forEach var="appctnTypeList" items="${appctnTypeList}">
                                                            - ${appctnTypeList.cdName} </br>
                                                        </c:forEach>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">신청주제</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.appctnThemeCntn}">
                                                                ${visitEduApplyList.appctnThemeCntn}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">신청내용</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.appctnRsn}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육 희망일</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${kl:convertDate(visitEduApplyList.hopeDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육장소</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.placeZipcode}">
                                                                ${visitEduApplyList.placeZipcode} ${visitEduApplyList.placeBscAddr} ${visitEduApplyList.placeDtlAddr}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">참석대상</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        ${visitEduApplyList.ptcptTrgtCntn}
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육인원</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.ptcptCnt}명</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육시간</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.ptcptHhNum}시간</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">회사소개서</p>
                                                </div>
                                                <div class="td">
                                                    <div class="attatched-file-area">
                                                        <a class="btn-text-icon download-bg gray" href="/file/download?fileSeq=${visitEduApplyList.itrdcFileSeq}&fileOrd=${visitEduApplyList.itrdcFileOrd}" title="파일 다운로드" download=""><span>${visitEduApplyList.itrdcFileName}</span></a>
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
                                <p class="f-title3">방문교육 결과</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="data-view-w">
                                        <div class="data-view-form">
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육상태</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.edctnSttsName}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">확정주제</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.cnfrmdTheme}">
                                                                ${visitEduApplyList.cnfrmdTheme}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육기간</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.edctnStrtDtm}">
                                                                ${kl:convertDate(visitEduApplyList.edctnStrtDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy.MM.dd', '')}
                                                                ~ ${kl:convertDate(visitEduApplyList.edctnEndDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy.MM.dd', '')}
                                                                (n일간)
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육장소</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.edctnPlace}">
                                                                ${visitEduApplyList.edctnPlace}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">강사</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:forEach var="isttrList" items="${isttrList.list}">
                                                            <c:if test="${not empty isttrList.isttr1}">
                                                                ${isttrList.isttr1}
                                                            </c:if>
                                                            <c:if test="${not empty isttrList.isttr2}">
                                                                , ${isttrList.isttr2}
                                                            </c:if>
                                                            <c:if test="${not empty isttrList.isttr3}">
                                                                , ${isttrList.isttr3}
                                                            </c:if>
                                                            <c:if test="${not empty isttrList.isttr4}">
                                                                , ${isttrList.isttr4}
                                                            </c:if>
                                                            <c:if test="${not empty isttrList.isttr5}">
                                                                , ${isttrList.isttr5}
                                                            </c:if>
                                                            <c:if test="${not empty isttrList.isttr6}">
                                                                , ${isttrList.isttr6}
                                                            </c:if>
                                                            <c:if test="${empty isttrList.isttr1}">
                                                                -
                                                            </c:if>
                                                        </c:forEach>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">교육장소</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${visitEduApplyList.placeZipcode} ${visitEduApplyList.placeBscAddr} ${visitEduApplyList.placeDtlAddr}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">수료인원</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.cmptnCnt}">
                                                                ${visitEduApplyList.cmptnCnt}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose> 명
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">출석률</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">
                                                        <c:choose>
                                                            <c:when test="${not empty visitEduApplyList.ptcptRate}">
                                                                ${visitEduApplyList.ptcptRate * 100}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose> %
                                                    </p>
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
                    <div class="btn-wrap for-motion align-right">
                        <a class="btn-solid small black-bg" id="listBtn" href="javascript:"><span>목록</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
</div>
