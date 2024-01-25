<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbf/WBFSmartFactoryCtrl">
    <div class="sub-top-vis-area apply-page consult-biz">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">기본정보</p>
                    </div>
                    <div class="step-list ">
                        <p class="step-num">2</p>
                        <p class="step-con">정보입력</p>
                    </div>
                    <div class="step-list ">
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
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="guide-info-area">
                            <div class="divide-box">
                                <p class="exclamation-txt f-sub-head">회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.<br>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.</p>
                            </div>
                        </div>
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
                                            <td>${rtnUser.name}</td>
                                        </tr>
                                        <tr>
                                            <th>휴대폰번호</th>
                                            <td>${rtnUser.hpNo}</td>
                                        </tr>
                                        <tr>
                                            <th>이메일</th>
                                            <td>${rtnUser.email}</td>
                                        </tr>
                                        <tr>
                                            <th>일반 전화번호</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty rtnUser.memTelNo}">
                                                        ${rtnUser.memTelNo}
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>부서(부서상세)</th>
                                            <td>
                                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                    <c:if test="${cdList.cd eq rtnUser.deptCd}">
                                                        <c:choose>
                                                            <c:when test="${empty(rtnUser.deptNm)}">
                                                                ${cdList.cdNm}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${cdList.cdNm}(${rtnUser.deptNm})
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>직급(기타직급)</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty(rtnUser.pstnNm)}">
                                                        ${rtnUser.pstnCdNm}
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${rtnUser.pstnCdNm}(${rtnUser.pstnNm})
                                                    </c:otherwise>
                                                </c:choose>
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
                        <c:set var="registerDtl" value="${rtnData.registerDtl}"/>
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
                                            <td>${fn:substring(registerDtl.bsnmNo,0,3) } - ${fn:substring(registerDtl.bsnmNo,3,5) } - ${fn:substring(registerDtl.bsnmNo,5, 10) }</td>
                                        </tr>
                                        <tr>
                                            <th>부품사명</th>
                                            <td>${registerDtl.cmpnNm}</td>
                                        </tr>
                                        <tr>
                                            <th>대표자명</th>
                                            <td>${registerDtl.rprsntNm}</td>
                                        </tr>
                                        <tr>
                                            <th>구분</th>
                                            <td>${registerDtl.ctgryCdNm}</td>
                                        </tr>
                                        <tr>
                                            <th>규모</th>
                                            <td>${registerDtl.sizeCdNm}</td>
                                        </tr>
                                        <tr>
                                            <th>설립일자</th>
                                            <td>${registerDtl.stbsmDt}</td>
                                        </tr>
                                        <tr>
                                            <th>회사 전화번호</th>
                                            <td>${registerDtl.compTel}</td>
                                        </tr>
                                        <tr>
                                            <th>본사주소</th>
                                            <td>
                                                <c:if test="${not empty registerDtl.zipcode}">
                                                    (${registerDtl.zipcode}) ${registerDtl.bscAddr} ${registerDtl.dtlAddr}
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>매출액</th>
                                            <td>
                                                <c:if test="${not empty registerDtl.slsPmt}">
                                                    ${registerDtl.slsPmt}억 원(${registerDtl.slsYear}년)
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>직원수</th>
                                            <td>
                                                <c:if test="${not empty registerDtl.mpleCnt}">
                                                    ${registerDtl.mpleCnt}명
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>주생산품</th>
                                            <td>
                                                <c:if test="${not empty registerDtl.mjrPrdct1}">
                                                    ① ${registerDtl.mjrPrdct1}
                                                </c:if>
                                                <c:if test="${not empty registerDtl.mjrPrdct2}">
                                                    ② ${registerDtl.mjrPrdct2}
                                                </c:if>
                                                <c:if test="${not empty registerDtl.mjrPrdct3}">
                                                    ③ ${registerDtl.mjrPrdct3}
                                                </c:if>
                                            </td>
                                        </tr>
                                        <c:if test="${registerDtl.ctgryCd eq 'COMPANY01001'}">
                                            <tr>
                                                <th>품질5스타</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty registerDtl.qlty5starCd}">
                                                            ${registerDtl.qlty5starCdNm} / ${registerDtl.qlty5starYear}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>납입5스타</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty registerDtl.pay5starCd}">
                                                            ${registerDtl.pay5starCdNm} / ${registerDtl.pay5starYear}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>기술5스타</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty registerDtl.tchlg5starCd}">
                                                            ${registerDtl.tchlg5starCdNm} / ${registerDtl.tchlg5starYear}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${registerDtl.ctgryCd eq 'COMPANY01002'}">
                                            <tr>
                                                <th>SQ정보</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${empty rtnData.sqInfoList}">
                                                            -
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="item" items="${rtnData.sqInfoList}" varStatus="status">
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
                                                                    <c:set var="year" value="${item.year}"/>
                                                                </c:if>

                                                                <c:if test="${empty item.crtfnCmpnNm}">
                                                                    <c:set var="crtfnCmpnNm" value="-"/>
                                                                </c:if>
                                                                <c:if test="${not empty item.crtfnCmpnNm}">
                                                                    <c:set var="crtfnCmpnNm" value="${item.crtfnCmpnNm}"/>
                                                                </c:if>
                                                                <p class="f-body1">${status.index}. ${nm} / ${score} / ${year} / ${crtfnCmpnNm}</p>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/certification"><span>부품사 기본정보 수정</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion">
                    <div class="btn-set">
                        <a class="btn-solid small gray-bg btnCancle" href="javascript:"><span>취소</span></a>
                    </div>
                    <div class="btn-set">
                        <input type="hidden" id="ctgryCd" value="${registerDtl.ctgryCd}"/>
                        <a class="btn-solid small black-bg" data-episd-seq="${rtnData.episdSeq}" id="nextBtnStep2" href="javascript:void(0);"><span>다음</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->