<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <form id="frmData" name="frmData" enctype="multipart/form-data">
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" class="notRequired" name="episdSeq" value="${episdSeq}" />
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <c:choose>
                            <c:when test="${fileYn eq 'Y'}">
                                <div class="step-list ">
                                    <p class="step-num">2</p>
                                    <p class="step-con">정보입력</p>
                                </div>
                                <div class="step-list ">
                                    <p class="step-num">3</p>
                                    <p class="step-con">신청완료</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="step-list ">
                                    <p class="step-num">2</p>
                                    <p class="step-con">신청완료</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
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
                                                <td>${fn:substring(rtnData.bsnmNo,0,3) } - ${fn:substring(rtnData.bsnmNo,3,5) } - ${fn:substring(rtnData.bsnmNo,5, 10) }</td>
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
                                                <td>${rtnData.ctgryNm}</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>${rtnData.sizeNm}</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${rtnData.stbsmDt}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${rtnData.compTel}</td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>
                                                    <c:if test="${not empty rtnData.zipcode}">
                                                        (${rtnData.zipcode}) ${rtnData.bscAddr} ${rtnData.dtlAddr}
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>
                                                    <c:if test="${not empty rtnData.slsPmt}">
                                                        ${rtnData.slsPmt}억 원(${rtnData.slsYear}년)
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>
                                                    <c:if test="${not empty rtnData.mpleCnt}">
                                                        ${rtnData.mpleCnt}명
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:if test="${not empty rtnData.mjrPrdct1}">
                                                        ① ${rtnData.mjrPrdct1}
                                                    </c:if>
                                                    <c:if test="${not empty rtnData.mjrPrdct2}">
                                                        ② ${rtnData.mjrPrdct2}
                                                    </c:if>
                                                    <c:if test="${not empty rtnData.mjrPrdct3}">
                                                        ③ ${rtnData.mjrPrdct3}
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:if test="${rtnData.ctgryCd eq 'COMPANY01001'}">
                                                <tr>
                                                    <th>품질5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnData.qlty5starCd}">
                                                                ${rtnData.qlty5starNm} / ${rtnData.qlty5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>납입5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnData.pay5starCd}">
                                                                ${rtnData.pay5starNm} / ${rtnData.pay5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>기술5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnData.tchlg5starCd}">
                                                                ${rtnData.tchlg5starNm} / ${rtnData.tchlg5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${rtnData.ctgryCd eq 'COMPANY01002'}">
                                                <tr>
                                                    <th>SQ정보</th>
                                                    <td>
                                                        <c:forEach var="item" items="${rtnData.sqInfoList}" varStatus="status">
                                                            <p class="f-body1">${status.index}. ${item.nm} / ${item.score} / ${item.year} 년 / ${item.crtfnCmpnNm}</p>
                                                        </c:forEach>
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
                            <a class="btn-solid small gray-bg" href="./content"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <c:choose>
                                <c:when test="${fileYn eq 'Y'}">
                                    <a class="btn-solid small black-bg" href="./step2?episdSeq=${episdSeq}"><span>다음</span></a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn-solid small black-bg insertSkip" href="javascript:"><span>다음</span></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- content 영역 end -->