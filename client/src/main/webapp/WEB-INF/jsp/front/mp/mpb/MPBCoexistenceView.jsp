<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/mp/mpb/MPBCoexistenceCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
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
                            <p class="f-title3">${rtnBsnData.title}</p><!-- 2023-12-22 텍스트 수정 -->
                        </div>
                        <c:choose>
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '부적합' || rtnBsnData.appctnSttsCdNm eq '미선정' || rtnBsnData.appctnSttsCdNm eq '사용자취소'
                                     || rtnBsnData.appctnSttsCdNm eq '보완요청' || rtnBsnData.appctnSttsCdNm eq '탈락'}">
                                <c:set var="classType" value="arr" />
                            </c:when>
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '접수전'}">
                                <c:set var="classType"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="classType" value="accepting" />
                            </c:otherwise>
                        </c:choose>

                        <div class="sec-con-area">
                            <div class="gray-bg-sec">
                                <div class="flex">
                                    <p class="f-title1">${rtnBsnData.year} ${rtnBsnData.episd}차 ${rtnBsnData.bsnNm}</p>
                                    <div class="group">
                                        <c:choose>
                                            <c:when test="${rtnBsnData.mrtsCd eq null}">
                                                <p class="index-num f-head">${rtnBsnData.rsumeSttsCdNm}</p>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger ${classType}"><span>${rtnBsnData.appctnSttsCdNm}</span></p>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${rtnBsnData.mrtsCd eq 'MNGCNSLT_DIS01'}">
                                                        <p class="box-label bigger complete"><span>장관상</span></p>
                                                    </c:when>
                                                    <c:when test="${rtnBsnData.mrtsCd eq 'MNGCNSLT_DIS02'}">
                                                        <p class="box-label bigger complete"><span>이사장</span></p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p class="box-label bigger complete"><span>회장상</span></p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="def-list-w">
                                    <div class="def-list">
                                        <p class="tit f-head">신청일자</p>
                                        <p class="txt f-sub-head">${ kl:convertDate(rtnBsnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p>
                                    </div>
                                    <div class="def-list">
                                        <p class="tit f-head">사업기간</p>
                                        <p class="txt f-sub-head">${ kl:convertDate(rtnBsnData.bsnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') } ~ ${ kl:convertDate(rtnBsnData.bsnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p>
                                        </div>
                                    </div>
                                    <c:if test="${rtnBsnData.cancelYn eq 'Y' && rtnBsnData.rsumeOrd == 1 && rtnBsnData.appctnSttsCdNm ne '사용자취소'}">
                                        <form name="cancelFrm" id="cancelFrm">
                                            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <input type="hidden" class="notRequired" name="bsnCd" value="${rtnBsnData.bsnCd}" />
                                            <div class="btn-wrap">
                                                <a class="btn-solid small black-bg cancel" href="javascript:"><span>신청취소</span></a>
                                            </div>
                                        </form>
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
                                                <th>성명</th>
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
                                                <td>${rtnUser.memTelNo}</td>
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
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">소속 부품사 기본정보</p>
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
                                                <td>${rtnCompany.bsnmNo}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${rtnCompany.cmpnNm}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${rtnCompany.rprsntNm}</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>${rtnCompany.ctgryNm}</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>${rtnCompany.sizeNm}</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${rtnCompany.stbsmDt}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${rtnCompany.compTel}</td>
                                            </tr>
                                            <tr>
                                                <th>본사주소</th>
                                                <td>
                                                    <c:if test="${not empty rtnCompany.zipcode}">
                                                        (${rtnCompany.zipcode}) ${rtnCompany.bscAddr} ${rtnCompany.dtlAddr}
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>매출액</th>
                                                <td>
                                                    <c:if test="${not empty rtnCompany.slsPmt}">
                                                        ${rtnCompany.slsPmt}억 원(${rtnCompany.slsYear}년)
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>
                                                    <c:if test="${not empty rtnCompany.mpleCnt}">
                                                        ${rtnCompany.mpleCnt}명
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:if test="${not empty rtnCompany.mjrPrdct1}">
                                                        ① ${rtnCompany.mjrPrdct1}
                                                    </c:if>
                                                    <c:if test="${not empty rtnCompany.mjrPrdct2}">
                                                        ② ${rtnCompany.mjrPrdct2}
                                                    </c:if>
                                                    <c:if test="${not empty rtnCompany.mjrPrdct3}">
                                                        ③ ${rtnCompany.mjrPrdct3}
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:if test="${rtnCompany.ctgryCd eq 'COMPANY01001'}">
                                                <tr>
                                                    <th>품질5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnCompany.qlty5starCd}">
                                                                ${rtnCompany.qlty5starNm} / ${rtnCompany.qlty5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>납입5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnCompany.pay5starCd}">
                                                                ${rtnCompany.pay5starNm} / ${rtnCompany.pay5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>기술5스타</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnCompany.tchlg5starCd}">
                                                                ${rtnCompany.tchlg5starNm} / ${rtnCompany.tchlg5starYear}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${rtnCompany.ctgryCd eq 'COMPANY01002'}">
                                                <tr>
                                                    <th>SQ정보</th>
                                                    <td>
                                                        <c:forEach var="item" items="${rtnCompany.sqInfoList}" varStatus="status">
                                                            <p class="f-body1">${status.index}. ${item.nm} / ${item.score} / ${item.year} 년 / ${item.crtfnCmpnNm}</p>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <c:choose>
                    <c:when test="${businessYn eq 'Y'}">
                        <jsp:include page="MPBCoexistence.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="MPBCoexistence${rtnBsnData.bsnCd}.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion align-right">
                    <a class="btn-solid small black-bg" href="javascript:"><span>목록</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
