<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/eb/ebc/EBCVisitEduCtrl">
    <div class="cont-wrap">
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">방문교육신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <div class="step-list">
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
            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            <!--LNB 끝-->

            <div class="right-con-area">
                <div class="guide-info-area scroll-motion">
                    <div class="for-motion">
                        <div class="divide-box">
                            <p class="exclamation-txt f-sub-head">
                                회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.
                                <br/>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.
                            </p>
                        </div>
                        <div class="divide-box">
                            <div class="btn-wrap">
                                <a class="btn-text-icon download" href="javascript:" title="신청서 작성예시 파일 다운로드" download=""><span>신청서 작성예시</span></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">신청자 기본정보를 확인해주세요</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
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
                                                    ${not empty applicantInfo.hpNo ? applicantInfo.hpNo : "-"}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>
                                                    ${not empty applicantInfo.email ? applicantInfo.email : "-"}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>일반 전화번호</th>
                                                <td>
                                                    ${not empty applicantInfo.telNo ? applicantInfo.telNo : "-"}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>부서</th>
                                                <td>${not empty applicantInfo.deptCdNm ? applicantInfo.deptCdNm : "-"}
                                                    <c:choose>
                                                        <c:when test="${not empty applicantInfo.deptCdNm}">
                                                            (${applicantInfo.deptDtlNm})
                                                        </c:when>
                                                        <c:otherwise>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>
                                                    ${not empty applicantInfo.pstnCdNm ? applicantInfo.pstnCdNm : "-"}
                                                    <c:if test="${applicantInfo.pstnCd eq 'MEM_CD01007'}" >
                                                        (${applicantInfo.pstnNm})
                                                    </c:if>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="btn-wrap align-right">
                                        <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/certification"><span>신청자 기본정보 수정</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">소속 부품사 기본정보를 확인해주세요</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
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
                                                <td>${not empty rtnInfo.cmpnNm ? rtnInfo.cmpnNm : "-"}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${not empty rtnInfo.rprsntNm ? rtnInfo.rprsntNm : "-"}</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>${rtnInfo.ctgryNm}</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>${not empty rtnInfo.sizeNm ? rtnInfo.sizeNm : "-"}</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${not empty rtnInfo.stbsmDt ? kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '') : "-"}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${not empty rtnInfo.telNo ? rtnInfo.telNo : "-"}</td>
                                            </tr>

                                            <tr>
                                                <th>본사주소</th>
                                                <td>(${rtnInfo.zipcode}) ${rtnInfo.bscAddr} ${rtnInfo.dtlAddr}</td>
                                            </tr>

                                            <tr>
                                                <th>매출액</th>
                                                <td>${not empty rtnInfo.slsPmt ? rtnInfo.slsPmt : "-"}억 원(${not empty rtnInfo.slsYear ? rtnInfo.slsYear : "-"}년)</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>${not empty rtnInfo.mpleCnt ? rtnInfo.mpleCnt : "-"}명</td>
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
                                                            <c:choose>
                                                                <c:when test="${empty sqInfoList.list}">
                                                                    -
                                                                </c:when>
                                                                <c:when test="${empty sqInfoList.list[0].nm}">
                                                                    -
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach var="item" items="${sqInfoList.list}" varStatus="status">
                                                                        <c:choose>
                                                                            <c:when test="${empty item.nm and empty item.score and empty item.year and empty item.crtfnCmpnNm}">

                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:if test="${empty item.nm}">
                                                                                    <c:set var="nm" value="-"/>
                                                                                </c:if>
                                                                                <c:if test="${not empty item.nm}">
                                                                                    <c:set var="nm" value="${item.nm}"/>
                                                                                </c:if>

                                                                                <c:if test="${empty item.score}">
                                                                                    <c:set var="score" value="-"/>
                                                                                </c:if>
                                                                                <c:if test="${not empty item.score}">
                                                                                    <c:set var="score" value="${item.score}"/>
                                                                                </c:if>

                                                                                <c:if test="${empty item.year}">
                                                                                    <c:set var="year" value="-"/>
                                                                                </c:if>
                                                                                <c:if test="${not empty item.year}">
                                                                                    <c:set var="year" value="${item.year} 년"/>
                                                                                </c:if>

                                                                                <c:if test="${empty item.crtfnCmpnNm}">
                                                                                    <c:set var="crtfnCmpnNm" value="-"/>
                                                                                </c:if>
                                                                                <c:if test="${not empty item.crtfnCmpnNm}">
                                                                                    <c:set var="crtfnCmpnNm" value="${item.crtfnCmpnNm}"/>
                                                                                </c:if>
                                                                                <p class="f-body1">${status.count}. ${nm} / ${score} / ${year} / ${crtfnCmpnNm}</p>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01001'}">
                                                    <tr>
                                                        <th>품질5스타</th>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty rtnInfo.qlty5StarCdNm}">
                                                                    ${rtnInfo.qlty5StarCdNm} / ${rtnInfo.qlty5StarYear}년
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>납입5스타</th>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty rtnInfo.pay5StarCdNm}">
                                                                    ${rtnInfo.pay5StarCdNm} / ${rtnInfo.pay5StarYear}년
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>기술5스타</th>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty rtnInfo.tchlg5StarCdNm}">
                                                                    ${rtnInfo.tchlg5StarCdNm} / ${rtnInfo.tchlg5StarYear}년
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="btn-wrap align-right">
                                        <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/certification?applyYn=Y"><span>부품사 기본정보 수정</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" id="cancelBtn" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="/education/visit/step2"><span>다음</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
