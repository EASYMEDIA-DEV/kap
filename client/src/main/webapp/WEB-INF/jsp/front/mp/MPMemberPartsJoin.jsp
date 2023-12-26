<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String birthdate = request.getParameter("birthdate");
    String gender = request.getParameter("gndr").equals("1")? "남" : "여";
    String genderCd = request.getParameter("gndr");
    String ci = request.getParameter("ci");
    String agree = request.getParameter("agree");
    String trmsAgmntYn = request.getParameter("trmsAgmntYn");
    String psnifAgmntYn = request.getParameter("psnifAgmntYn");
    String psnif3AgmntYn = request.getParameter("psnif3AgmntYn");
    String fndnNtfyRcvYn = request.getParameter("fndnNtfyRcvYn");
    String ntfySmsRcvYn = request.getParameter("ntfySmsRcvYn");
    String ntfyEmailRcvYn = request.getParameter("ntfyEmailRcvYn");
    String memCd = request.getParameter("param1");
//    String hpNo = request.getParameter("mobile_no");
    //TODO 휴대폰 번호 아직 안넘와서 임시로 세팅
    String hpNo = "010-1234-5678";

%>

<form name="formNextSubmit" id="formNextSubmit"  method="get"  >
    <input type="hidden" id="name" name="name" value="<%=name%>" />
    <input type="hidden" id="birthdate" name="birthdate" value="<%=birthdate%>" />
    <input type="hidden" id="gender" name="gender" value="<%=gender%>" />
    <input type="hidden" id="gndr" name="gndr" value="<%=genderCd%>" />
    <input type="hidden" id="ci" name="ci" value="<%=ci%>" />
    <input type="hidden" id="trmsAgmntYn" name="trmsAgmntYn" value="<%=trmsAgmntYn%>" />
    <input type="hidden" id="psnifAgmntYn" name="psnifAgmntYn" value="<%=psnifAgmntYn%>" />
    <input type="hidden" id="psnif3AgmntYn" name="psnif3AgmntYn" value="<%=psnif3AgmntYn%>" />
    <input type="hidden" id="fndnNtfyRcvYn" name="fndnNtfyRcvYn" value="<%=fndnNtfyRcvYn%>" />
    <input type="hidden" id="ntfySmsRcvYn" name="ntfySmsRcvYn" value="<%=ntfySmsRcvYn%>" />
    <input type="hidden" id="ntfyEmailRcvYn" name="ntfyEmailRcvYn" value="<%=ntfyEmailRcvYn%>" />
    <input type="hidden" id="hpNo" name="hpNo" value="<%=hpNo%>" />
    <input type="hidden" id="param1" name="param1" value="<%=memCd%>" />
    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" id="email" name="email" class="notRequired" />
    <input type="hidden" class="sqInfoList1" name="sqInfoList1" class="notRequired" />
    <input type="hidden" class="sqInfoList2" name="sqInfoList2" class="notRequired" />
    <input type="hidden" class="sqInfoList3" name="sqInfoList3" class="notRequired" />
    <input type="hidden" class="bsnmChk" name="bsnmChk" class="notRequired" />
    <div id="wrap" class="member"  data-controller="controller/mp/MPMemberPartsController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area t-align-center">
                    <p class="page-tit f-large-title">
                        <span class="for-move">회원가입</span>
                    </p>
                </div>
            </div>

            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">본인인증</p>
                    </div>
                    <div class="step-list completed">
                        <p class="step-num">2</p>
                        <p class="step-con">약관동의</p>
                    </div>
                    <div class="step-list ongoing">
                        <p class="step-num">3</p>
                        <p class="step-con">회원정보 입력</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">4</p>
                        <p class="step-con">가입완료</p>
                    </div>
                </div>
            </div>

            <div class="inner-con-box">
                <div class="cont-sec-w">
                    <div class="cont-sec">
                        <div class="sec-tit-area">
                            <p class="f-title3">회원 기본 정보</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="gray-bg-sec middle-pad dark-gray-bg">
                                <div class="list-txt-w">
                                    <div class="list-txt">
                                        <p class="tit">이름</p>
                                        <p class="txt"><%=name%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">생년월일</p>
                                        <p class="txt"><%=birthdate%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">성별</p>
                                        <p class="txt"><%=gender%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">휴대폰번호</p>
                                        <p class="txt"><%=hpNo%></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec">
                        <div class="sec-tit-area">
                            <p class="f-title3">부품사 정보 입력</p>
                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">사업자등록번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="for-status-chk for-status-chk2"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                            <div class="form-group">
                                                <div class="form-input">
                                                    <input type="text" placeholder="사업자등록번호 입력" id="bsnmNo" name="bsnmNo" title="사업자등록번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="10">
                                                </div>
                                                <div class="btn-wrap">
                                                    <button class="btn-solid small gray-bg btnCmpnChk" type="button" ><span>인증</span></button>
                                                </div>
                                            </div>
                                            <p class="satisfy-msg">인증되었습니다.</p>
                                            <!-- 2023-12-06 추가 -->
                                            <div class="noti-txt-w">
                                                <p class="bullet-noti-txt f-caption2">* 사업자등록번호 인증 시 등록된 부품사 정보가 없는 경우 신규 부품사 등록을 진행해 주세요.</p>
                                            </div>
                                            <!-- // 2023-12-06 추가 -->
                                        </div>
                                    </div>
                                </div>

                            <div class="new">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">부품사명<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" placeholder="부품사명 입력" readonly title="부품사명" name="cmpn_nm_new" class="cmpn_nm_new">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">대표자명<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" placeholder="대표자명 입력" title="대표자명" name="rprsnt_nm"  class="rprsnt_nm">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">구분<span class="essential-mark color-sky">*</span></p>
                                    </div>


                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                            <select id="ctgryCd" name="ctgryCd" title="구분 선택">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                                    <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
                                                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                                                ${cdList.cdNm}
                                                                        </option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">규모<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select  id="sizeCd" name="sizeCd" title="기업규모" >
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.sizeCd eq cdList.cd}">selected</c:if>>
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">설립일자<span class="essential-mark color-sky">*</span></p>
                                    </div>


                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input calendar" >
                                                        <input id="stbsmDt" class="stbsmDt" name="stbsmDt" type="date" placeholder="2022.07.15">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">회사 <br class="only-pc"/>전화번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" class="form-control input-sm telNo" id="telNo" name="telNo"   oninput="this.value=this.value.replace(/[^0-9]/g, '')"  title="회사 전화번호"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">본사주소<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <div class="form-address">
                                                        <div class="form-group">
                                                            <div class="form-input w-shortest">
                                                                <input type="text" placeholder="우편번호" readonly id="zipcode" name="zipcode" title="우편번호">
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="기본주소" readonly id="bscAddr" name="bscAddr" title="기본주소">
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longer">
                                                                <input type="text" placeholder="상세주소 입력" id="dtlAddr" name="dtlAddr" title="상세주소">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">매출액</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" class="notRequired" placeholder="매출액 입력" id="slsPmt" name="slsPmt" title="매출액"  oninput="this.value=this.value.replace(/[^0-9]/g, '')">
                                                        <p class="unit-txt">억 원</p>
                                                    </div>
                                                    <div class="form-select">
                                                        <select id="slsYear" class="notRequired" name="slsYear" title="연도 선택" title="매출액">
                                                            <option value="">연도 선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.slsYear eq cdList.cd}">selected</c:if>>
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">직원수</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" class="notRequired" placeholder="직원수 입력" id="mpleCnt" name="mpleCnt"  oninput="this.value=this.value.replace(/[^0-9]/g, '')">
                                                        <p class="unit-txt">명</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">주생산품</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-shorter">
                                                        <input type="text" placeholder="주생산품(1) 입력" id="mjrPrdct1" name="mjrPrdct1" title="주생상품1" class="notRequired">
                                                    </div>
                                                    <div class="form-input w-shorter">
                                                        <input type="text" placeholder="주생산품(2) 입력" id="mjrPrdct2" name="mjrPrdct2" title="주생상품2" class="notRequired">
                                                    </div>
                                                    <div class="form-input w-shorter">
                                                        <input type="text" placeholder="주생산품(3) 입력" id="mjrPrdct3" name="mjrPrdct3" title="주생상품3" class="notRequired">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row gubunOne">
                                    <div class="th">
                                        <p class="title f-head">품질5스타</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select class="notRequired" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" >
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                                    <option value="${cdList.cd}">
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-select">
                                                        <select id="qlty5StarYear" class="notRequired" name="qlty5StarYear" title="품질5스타연도" >
                                                            <option value="">연도 선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarYear eq cdList.cd}">selected</c:if>>
                                                                    ${cdList.cdNm}
                                                                    </option>
                                                                </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row gubunOne">
                                    <div class="th">
                                        <p class="title f-head">납입5스타</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select  class="notRequired" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급">
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                                <option value="${cdList.cd}">
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-select">
                                                        <select class="notRequired" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" title="연도 선택">
                                                            <option value="" selected>연도 선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarYear eq cdList.cd}">selected</c:if>>
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row gubunOne">
                                    <div class="th">
                                        <p class="title f-head">기술5스타</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select  class="notRequired" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급">
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                                <option value="${cdList.cd}">
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-select">
                                                        <select class="notRequired" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" title="연도 선택">
                                                            <option value="" selected>연도 선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                                <option value="${cdList.cd}">
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                                <div class="row gubunTwo"  style="display: none;">
                                    <div class="th">
                                        <p class="title f-head">SQ정보</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <c:forEach var="i" begin="1" end="3">
                                                <div class="data-line data-line${i} lastindex${i}" style="display:none;">
                                                    <div class="form-group">
                                                        <div class="form-input w-shorter">
                                                            <input type="text" class="notRequired" placeholder="SQ 업종입력"  id="nm${i}" name="sqInfoList${i}">
                                                        </div>
                                                        <div class="form-select">
                                                            <select class="notRequired" id="year${i}" name="sqInfoList${i}" title="평가년도" title="SQ 평가 연도 선택">
                                                                <option value="" selected>연도 선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                        ${cdList.cdNm}
                                                                </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="form-input w-shorter">
                                                            <input type="text" class="notRequired" placeholder="SQ 점수입력" id="score${i}" name="sqInfoList${i}"   oninput="this.value=this.value.replace(/[^0-9.]/g, '')" >
                                                        </div>
                                                        <div class="form-input w-middle">
                                                            <input type="text" class="notRequired" placeholder="SQ 인증주관사 입력" id="crtfnCmpnNm${i}" name="sqInfoList${i}">
                                                        </div>
                                                        <div class="btn-wrap btn-add-line lastBtnIndex${i}">
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="data-enter-form">
                                    <div class="row old" style="display: none;">
                                    <div class="th">
                                        <p class="title f-head">부품사명<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-txt cmpn_nm"></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row old" style="display: none;">
                                    <div class="th">
                                        <p class="title f-head">대표자명<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-txt rprsnt_nm" ></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row old" style="display: none;">
                                    <div class="th">
                                        <p class="title f-head">구분<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-txt gubun"></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row old" style="display: none;">
                                    <div class="th">
                                        <p class="title f-head">본사주소<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-txt addr"> </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">

                            <a class="btn-solid small gray-bg btnBack" href="javascript:"><span>이전</span></a>
                        </div>
                        <div class="btn-set">
                            <button class="btn-solid small black-bg" id="nextBtn"><span>다음</span></button>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form>

<style>
input[type="date"]::-webkit-calendar-picker-indicator,
input[type="date"]::-webkit-inner-spin-button {
display: none;
appearance: none;
}

input[type="date"]::-webkit-calendar-picker-indicator {
color: rgba(0, 0, 0, 0); //숨긴다
opacity: 1;
display: block;
padding-right: 36rem;
background: url('/common/images/icon-calendar.svg') no-repeat 98% 50%; // 대체할 아이콘
width: 30px;
height: 25px;
border-width: thin;
}

.form-input.calendar {
background : no-repeat;
padding-right: 1rem;
}

</style>