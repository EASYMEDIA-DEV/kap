<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:if test="${rtnBsnData.bsnCd eq 'BSN11'}">
    <c:set var="partList" value="${ rtnData.partList }" />
</c:if>
<div class="cont-wrap" data-controller="controller/mp/mpb/MPBCoexistenceCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <div class="sub-top-vis-area">
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
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '접수전' || rtnBsnData.appctnSttsCdNm eq '대기' || rtnBsnData.appctnSttsCdNm eq '결과대기'}">
                                <c:set var="classType" value="waiting"/>
                            </c:when>
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '접수완료' || rtnBsnData.appctnSttsCdNm eq '적합' || rtnBsnData.appctnSttsCdNm eq '선정' ||
                             rtnBsnData.appctnSttsCdNm eq '보완완료' || rtnBsnData.appctnSttsCdNm eq '지급완료'}">
                                <c:set var="classType" value="accepting"/>
                            </c:when>
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '사용자취소'}">
                                <c:set var="classType" value="end"/>
                            </c:when>
                            <c:when test="${rtnBsnData.appctnSttsCdNm eq '보완요청' || rtnBsnData.appctnSttsCdNm eq '부적합'}">
                                <c:set var="classType" value="arr"/>
                            </c:when>
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
<c:choose>
    <c:when test="${rtnBsnData.bsnCd eq 'BSN11'}">
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
                                    <td>${coUser.name}</td>
                                </tr>
                                <tr>
                                    <th>휴대폰번호</th>
                                    <td>${coUser.hpNo}</td>
                                </tr>
                                <tr>
                                    <th>이메일</th>
                                    <td>${coUser.email}</td>
                                </tr>
                                <tr>
                                    <th>일반 전화번호</th>
                                    <td>${coUser.telNo}</td>
                                </tr>
                                <tr>
                                    <th>성별</th>
                                    <td>${coUser.gndr =='1' ? '남' : '여'}</td>
                                </tr>
                                <tr>
                                    <th>생년월일</th>
                                    <td>${coUser.birth}</td>
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
                        <div class="data-view-w">
                            <p class="data-view-tit f-head">팀장 정보</p>
                            <div class="data-view-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">이름</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.rdName}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">이메일</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.rdEmail}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">학교</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.rdSchlNm}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">학년</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.rdGrd} / ${rtnData.rdGrdCdNm}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">참여구분</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.ptcptTypeNm}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">주제</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.themeCdNm}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-body2">세부 내용</p>
                                    </div>
                                    <div class="td">
                                        <p class="txt f-body1">${rtnData.dtlCntn}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="data-view-w">

                         <c:if test="${not empty partList }">
                                <p class="data-view-tit f-head">팀원 정보</p>
                                    <c:forEach var="partList" items="${partList}" varStatus="qstnStatus">
                                        <div class="data-view-form">
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">이름</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${partList.name}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">휴대폰번호</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${partList.hpNo}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">이메일</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${partList.email}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">학교</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${partList.schlNm}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="th">
                                                    <p class="title f-body2">학년</p>
                                                </div>
                                                <div class="td">
                                                    <p class="txt f-body1">${partList.grd} / ${partList.grdCdNm}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                        <br>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
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
                                            <th>신청자</th><!-- 2024-01-18 텍스트 수정 -->
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
                                            <th>일반 전화번호</th><!-- 2024-01-19 텍스트 수정 -->
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
                                            <th>부서</th>
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
                                            <th>직급</th>
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
                                                <td>${kl:bsnmNoConvert(rtnCompany.bsnmNo)}</td>
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
                                                        <c:choose>
                                                            <c:when test="${empty rtnCompany.sqInfoList}">
                                                                -
                                                            </c:when>
                                                            <c:when test="${empty rtnCompany.sqInfoList[0].nm}">
                                                                -
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:forEach var="item" items="${rtnCompany.sqInfoList}" varStatus="status">
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
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </c:otherwise>
            </c:choose>
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
                    <a class="btn-solid small black-bg" href="./list"><span>목록</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
