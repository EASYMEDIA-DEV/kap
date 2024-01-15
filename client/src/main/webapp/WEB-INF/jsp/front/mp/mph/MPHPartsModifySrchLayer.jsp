<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="mypage" data-controller="controller/mp/MPMemberPartsController "><!-- 마이페이지 mypage 클래스 추가 -->


    <!-- 부품사회원 전환 정보 팝업 -->
    <div class="layer-popup switchingMemberPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">부품사회원 전환 정보</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <div class="noti-txt-w t-align-right">
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">사업자등록번호<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="for-status-chk for-status-chk2"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                            <div class="form-group">
                                                                <div class="form-input">
                                                                    <input type="text" placeholder="사업자등록번호 입력" id="bsnmNo" class="cleanInit" name="bsnmNo" title="사업자등록번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="10">
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
                                                                <input type="text" placeholder="부품사명 입력" readonly title="부품사명" name="cmpn_nm_new" class="cmpn_nm_new cleanInit" >
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
                                                                <input type="text" placeholder="대표자명 입력" title="대표자명" name="rprsnt_nm"  class="rprsnt_nm cleanInit">
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
                                                                <select id="ctgryCd" class="cleanInit" name="ctgryCd" title="구분 선택">
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
                                                                <select  id="sizeCd" class="cleanInit" name="sizeCd" title="기업규모" >
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
                                                                <input id="stbsmDt" class="stbsmDt cleanInit" name="stbsmDt" type="date" placeholder="2022.07.15">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">회사 전화번호<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="text" class="form-control input-sm telNo cleanInit" id="telNo" name="telNo"   oninput="this.value=this.value.replace(/[^0-9]/g, '')"  title="회사 전화번호" maxlength="13"/>
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
                                                                        <input type="text" placeholder="우편번호" class="cleanInit" readonly id="zipcode" name="zipcode" title="우편번호">
                                                                    </div>
                                                                    <div class="form-input">
                                                                        <input type="text" placeholder="기본주소" class="cleanInit" readonly id="bscAddr" name="bscAddr" title="기본주소">
                                                                    </div>
                                                                    <div class="btn-wrap">
                                                                        <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <div class="form-input w-longer">
                                                                        <input type="text" placeholder="상세주소 입력" class="cleanInit" id="dtlAddr" name="dtlAddr" title="상세주소">
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
                                                                <input type="text" class="notRequired cleanInit" placeholder="매출액 입력"  id="slsPmt" name="slsPmt" title="매출액"  oninput="this.value=this.value.replace(/[^0-9]/g, '')">
                                                                <p class="unit-txt">억 원</p>
                                                            </div>
                                                            <div class="form-select">
                                                                <select id="slsYear" class="notRequired cleanInit" name="slsYear" title="연도 선택" title="매출액" >
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
                                                                <input type="text" class="notRequired cleanInit" placeholder="직원수 입력" id="mpleCnt" name="mpleCnt"  oninput="this.value=this.value.replace(/[^0-9]/g, '')" >
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
                                                                <input type="text" placeholder="주생산품(1) 입력" id="mjrPrdct1" name="mjrPrdct1" title="주생상품1" class="notRequired cleanInit">
                                                            </div>
                                                            <div class="form-input w-shorter">
                                                                <input type="text" placeholder="주생산품(2) 입력" id="mjrPrdct2" name="mjrPrdct2" title="주생상품2" class="notRequired cleanInit">
                                                            </div>
                                                            <div class="form-input w-shorter">
                                                                <input type="text" placeholder="주생산품(3) 입력" id="mjrPrdct3" name="mjrPrdct3" title="주생상품3" class="notRequired cleanInit">
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
                                                                <select class="notRequired cleanInit" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" >
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
                                                                <select id="qlty5StarYear" class="notRequired cleanInit" name="qlty5StarYear" title="품질5스타연도" >
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
                                                                <select  class="notRequired cleanInit" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급">
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
                                                                <select class="notRequired cleanInit" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" title="연도 선택">
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
                                                                <select  class="notRequired cleanInit" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급">
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
                                                                <select class="notRequired cleanInit" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" title="연도 선택">
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
                                        <div class="row gubunTwo" style="display: none;">
                                            <div class="th">
                                                <p class="title f-head">SQ정보</p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <c:forEach var="i" begin="1" end="3">
                                                        <div class="data-line sqCount data-line${i} lastindex${i}" style="display:none;">
                                                            <div class="form-group">
                                                                <div class="form-input w-shorter">
                                                                    <input type="text" class="notRequired cleanInit " placeholder="SQ 업종입력"  id="nm${i}" name="sqInfoList${i}">
                                                                </div>
                                                                <div class="form-select">
                                                                    <select class="notRequired cleanInit" id="year${i}" name="sqInfoList${i}" title="평가년도" title="SQ 평가 연도 선택">
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
                                                                    <input type="text" class="notRequired cleanInit" placeholder="SQ 점수입력" id="score${i}" name="sqInfoList${i}"   oninput="this.value=this.value.replace(/[^0-9.]/g, '')" >
                                                                </div>
                                                                <div class="form-input w-middle">
                                                                    <input type="text" class="notRequired cleanInit" placeholder="SQ 인증주관사 입력" id="crtfnCmpnNm${i}" name="sqInfoList${i}">
                                                                </div>
                                                                <div class="btn-wrap btn-add-line lastBtnIndex${i}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row chng">
                                            <div class="th">
                                                <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select id="deptCd" name="mpaUserDto.deptCd" title="부서" class="notRequired cleanInit">
                                                                    <option value="" selected>선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status" >
                                                                        <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                                                                            <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" id="deptDtlNm" title="부서상세" name="mpaUserDto.deptDtlNm" placeholder="부서 상세 입력" class="notRequired cleanInit" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row chng">
                                            <div class="th">
                                                <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select id="pstnCd" name="mpaUserDto.pstnCd" title="직급" class="notRequired cleanInit" title="직급" >
                                                                    <option value="" selected>선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                        <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                                                                            <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input pstnNmDis" style="display: none;" >
                                                                <input type="text" placeholder="기타 직급 입력" class="pstnNm notRequired cleanInit" title="기타" name="mpaUserDto.pstnNm">
                                                            </div>
                                                        </div>
                                                    </div>
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
                                                            <p class="data-txt cmpn_nm cleanInit"></p>
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
                                                            <p class="data-txt rprsnt_nm cleanInit rsNm" ></p>
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
                                                            <p class="data-txt gubun cleanInit"></p>
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
                                                                <p class="data-txt addr cleanInit"> </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row old chng" style="display: none;">
                                                <div class="th">
                                                    <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                                </div>
                                                <div class="td">
                                                    <div class="data-line-w">
                                                        <div class="data-line">
                                                            <div class="form-group">
                                                                <div class="form-select">
                                                                    <select id="deptCdOld" name="mpaUserDto.deptCd" title="부서" class="notRequired cleanInit deptCd">
                                                                        <option value="" selected>선택</option>
                                                                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status" >
                                                                            <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                                                                                <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                                <div class="form-input">
                                                                    <input type="text" id="deptDtlNmOld" title="부서상세" name="mpaUserDto.deptDtlNm" placeholder="부서 상세 입력" class="notRequired cleanInit" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row old chng" style="display: none;">
                                                <div class="th">
                                                    <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                                                </div>
                                                <div class="td">
                                                    <div class="data-line-w">
                                                        <div class="data-line">
                                                            <div class="form-group">
                                                                <div class="form-select">
                                                                    <select id="pstnCdOld" name="mpaUserDto.pstnCd" title="직급" class="notRequired cleanInit" title="직급" >
                                                                        <option value="" selected>선택</option>
                                                                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                            <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                                                                                <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                                <div class="form-input pstnNmDisOld" style="display: none;" >
                                                                    <input type="text" placeholder="기타 직급 입력" class="pstnNmOld notRequired cleanInit" title="기타" name="mpaUserDto.pstnNm">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg btnPopClose " href="javascript:"><span>취소</span></a>
                            </div>
                            <div class="btn-set">
                                <a class="btn-solid small black-bg btnSave"  href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btnClosePop btnPopClose btn-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

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