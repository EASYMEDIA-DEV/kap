<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnInfo" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebc/EBCVisitEduWriteCtrl">
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.vstSeq}" />
            <input type="hidden" class="notRequired" id="itrdcFileSeq" name="itrdcFileSeq" value="${rtnInfo.itrdcFileSeq}" />
            <input type="hidden" class="notRequired" id="lctrFileSeq" name="lctrFileSeq" value="${rtnInfo.lctrFileSeq}" />
            <input type="hidden" class="notRequired" id="etcMatlsFileSeq" name="etcMatlsFileSeq" value="${rtnInfo.etcMatlsFileSeq}" />
            <input type="hidden" class="notRequired" id="selectCtgryCd" name="selectCtgryCd" value="${rtnInfo.selectCtgryCd}" />

            <h6 class="mt-lg"> 강사 기본 정보 </h6>
            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:10%;">
                        <col style="width:40%;">
                        <col style="width:10%;">
                        <col style="width:40%;">
                    </colgroup>
                    <tbody>
                    <tr class="form-inline">
                        <th scope="row" class="bg-gray-lighter">이름</th>
                        <td>
                            <input type="text" class="form-control input-sm" id="memName" name="memName" value="${rtnInfo.memName}" title="회원이름 및 아이디" readonly style="width: 150px;"/>
                            <button type="button" class="btn btn-sm bsnmNoBtn">회원검색</button>
                        </td>
                        <th scope="row" class="bg-gray-lighter">이메일</th>
                        <td>${rtnInfo.memEmail}</td>
                    </tr>
                    <tr class="form-inline">
                        <th scope="row" class="bg-gray-lighter">부서</th>
                        <td>
                            <select class="form-control input-sm" id="memDeptCd" name="memDeptCd" title="부서" >
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'MEM_CD02')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.memDeptCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control input-sm" id="memDeptDtlName" name="memDeptDtlName" value="${rtnInfo.memDeptDtlName}" title="부서상세명" />
                        </td>
                        <th scope="row" class="bg-gray-lighter">직급</th>
                        <td>
                            <select class="form-control input-sm" id="memPstnCd" name="memPstnCd" title="직급" >
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'MEM_CD01') and cdList.cd ne 'COMPANY01'}">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.memPstnCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control input-sm" id="memPstnName" name="memPstnName" value="${rtnInfo.memPstnName}" title="직급상세명" />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">휴대폰번호</th>
                        <td>${rtnInfo.memHpNo}</td>
                        <th scope="row" class="bg-gray-lighter">일반 전화번호</th>
                        <td>
                            <input type="text" class="form-control input-sm" id="memTelNo" name="memTelNo" value="${rtnInfo.memTelNo}" title="전화번호" />
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="text-left mb-xl"><h5>부품사 정보</h5></div>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="cmpnNm" name="cmpnNm" value="${rtnInfo.cmpnNm}" title="부품사명" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
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
                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="기업규모" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
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
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="rprsntNm" name="rprsntNm" value="${rtnInfo.rprsntNm}" title="대표자명" style="width: 200px;"/>
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="input-group" style="z-index:0;width: 220px;">
                            <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt" value="${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="설립일자" />
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" maxlength="50" placeholder="회사 전화번호 입력" style="width: 200px;"/>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        ${rtnInfo.appctnBsnmNo}
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnInfo.zipcode}" readonly placeholder="우편번호" style="width: 130px;"/>
                        <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" style="width: 400px;"/><br>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnInfo.dtlAddr}" title="우편번호" placeholder="상세주소 입력" maxlength="50" style="width: 400px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${rtnInfo.slsPmt}" title="매출액" placeholder="매출액 입력" style="width: 220px;"/> 억 원
                        <select class="form-control input-sm notRequired" id="slsYear" name="slsYear" title="선택" style="width: 100px;">
                            <option value="">연도 선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${rtnInfo.slsYear eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select> 년
                    </div>
                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력" style="width: 220px;"/> 명
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품1" maxlength="50" placeholder="주생산품(1) 입력" style="width: 200px;"/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품2" maxlength="50" placeholder="주생산품(2) 입력" style="width: 200px;"/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품3" maxlength="50" placeholder="주생산품(3) 입력" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="qlty5StarArea" <c:if test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">style="display:none;"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">품질5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" id="qlty5StarYear" name="qlty5StarYear" title="품질5스타연도" style="width: 100px;">
                            <option value="">연도 선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarYear eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select> 년
                    </div>
                </div>
            </fieldset>
            <fieldset class="pay5StarArea" <c:if test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">style="display:none;"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">납입5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarCd eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" style="width: 100px;">
                            <option value="">연도 선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarYear eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select> 년
                    </div>
                </div>
            </fieldset>
            <fieldset class="tchlg5StarArea" <c:if test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">style="display:none;"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">기술5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" style="width:100px;">
                            <option value="">연도 선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarYear eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select> 년
                    </div>
                </div>
            </fieldset>
            <fieldset class="sqInfoArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01001'}">style="display:none;"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">SQ 정보</label>
                    <div class="col-sm-5">
                        <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">${sqInfoList.sqInfoList}
                            <input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value="${list.cbsnSeq}"/>
                            <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="${list.nm}" title="SQ 업종" placeholder="SQ 업종입력"/>
                            <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="${list.score}" title="점수" placeholder="SQ 점수입력"/>
                            <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                <option value="">SQ 평가년도 선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="${list.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                            </br>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${sqInfoListCnt == 0}">
                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                    <input type="hidden" class="notRequired" id="cbsnSeq${ status.count}" name="sqInfoList${status.count}" value=""/>
                                    <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                    <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                    <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                        <option value="">SQ 평가년도 선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                    </br>
                                </c:forEach>
                            </c:when>
                            <c:when test="${sqInfoListCnt != 3 and sqInfoListCnt > 0}">
                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                    <input type="hidden" class="notRequired" id="cbsnSeq${status.count + 1}" name="sqInfoList${status.count + 1}" value=""/>
                                    <input type="text" class="form-control input-sm notRequired" id="nm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                    <input type="text" class="form-control input-sm notRequired" id="score${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                    <select class="form-control input-sm notRequired" id="year${status.count + 1}" name="sqInfoList${status.count + 1}" title="평가년도" style="width:auto;">
                                        <option value="">SQ 평가년도 선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                    </br>
                                </c:forEach>
                            </c:when>
                            <c:when test="${sqInfoListCnt == 3}">
                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                    <input type="hidden" class="notRequired" id="cbsnSeq${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value=""/>
                                    <input type="text" class="form-control input-sm notRequired" id="nm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                    <input type="text" class="form-control input-sm notRequired" id="score${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                    <select class="form-control input-sm notRequired" id="year${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" title="평가년도" style="width:auto;">
                                        <option value="">SQ 평가년도 선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                    </br>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
            <hr />
            <h6 class="mt-lg"> 담당 임원 정보 </h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">성명<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picName" name="picName" value="${rtnInfo.picName}" title="담당자이름" maxlength="50" style="width: 200px;"/>
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picEmail" name="picEmail" value="${rtnInfo.picEmail}" title="담당자이메일" maxlength="50" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picDeptNm" name="picDeptNm" value="${rtnInfo.picDeptNm}" title="담당자부서명" maxlength="50" style="width: 200px;"/>
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picPstnNm" name="picPstnNm" value="${rtnInfo.picPstnNm}" title="담당자직급명" maxlength="50" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picHpNo" name="picHpNo" value="${rtnInfo.picHpNo}" title="담당자휴대전화번호" maxlength="50" style="width: 200px;"/>
                    </div>
                    <label class="col-sm-1 control-label">전화번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picCmpnTelNo" name="picCmpnTelNo" value="${rtnInfo.picCmpnTelNo}" title="담당자회사전화번호" maxlength="50" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>

            <h6 class="mt-lg"> 부품사 추가정보 </h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사 규모<span class="star"> *</span></label>
                    <div class="col-sm-11 cmpnSizeCd">
                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                            <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY02') and cdList.cdNm ne '기타'}">
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="cmpnSizeCd" value="${cdList.cd}" <c:if test="${rtnInfo.cmpnSizeCd eq cdList.cd}">checked</c:if> />
                                    <span class="ion-record"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">승용/상용 구분 <span class="star"> *</span></label>
                    <div class="col-sm-11 rdngCmbsnCd">
                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                            <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY04')}">
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="rdngCmbsnCd" value="${cdList.cd}" <c:if test="${rtnInfo.rdngCmbsnCd eq cdList.cd}">checked</c:if> />
                                    <span class="ion-record"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">소재지역</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired classType" id="firstRgnsCd" name="firstRgnsCd" title="첫번째지역코드" style="width:auto;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${twoDpthCdDtlList.ADDR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnInfo.firstRgnsCd eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm wd-sm scndRgnsCd" name="scndRgnsCd" id="scndRgnsCd" title="두번째지역코드" data-scnd-rgns-cd="${rtnInfo.scndRgnsCd}">
                            <option value="">2차 선택</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">홈페이지 주소</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="hmpgeUrl" name="hmpgeUrl" value="${rtnInfo.hmpgeUrl}" title="홈페이지 주소" style="width: 200px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청사유<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="appctnRsn" name="appctnRsn" title="신청사유" rows="5">${rtnInfo.appctnRsn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">신청분야</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm" id="appctnFldCd" name="appctnFldCd" title="신청분야" >
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD01') and fn:length(cdList.cd) eq 17}">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.appctnFldCd eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <div class="checkBoxArea"></div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청주체<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="appctnMngntCntn" name="appctnMngntCntn" title="신청주체" rows="5">${rtnInfo.appctnMngntCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="appctnCntn" name="appctnCntn" title="신청내용" rows="5">${rtnInfo.appctnCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육희망일<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <div class="input-group" style="z-index:0;width: 220px;">
                            <input type="text" class="form-control input-sm datetimepicker_strtDt" id="hopeDt" name="hopeDt" value="${kl:convertDate(rtnInfo.hopeDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="설립일자" />
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="button" class="btn btn-sm" id="searchEduPlacePostCode" value="우편번호 찾기">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" id="samePlaceBtn" data-name="samePlaceYn" value=""  />
                            <span class="ion-checkmark-round"></span> 본사와 동일
                        </label>
                        <br><br>
                        <input type="text" class="form-control input-sm" id="placeZipcode" name="placeZipcode" value="${rtnInfo.placeZipcode}" readonly placeholder="우편번호" style="width: 130px;"/>
                        <br><br>
                        <input type="text" class="form-control input-sm" id="placeBscAddr" name="placeBscAddr" value="${rtnInfo.placeBscAddr}" readonly placeholder="기본주소" style="width: 400px;"/>
                        <br><br>
                        <input type="text" class="form-control input-sm" id="placeDtlAddr" name="placeDtlAddr" value="${rtnInfo.placeDtlAddr}" title="우편번호" placeholder="상세주소 입력" maxlength="50" style="width: 400px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">참석대상<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="ptcptTrgtCntn" name="ptcptTrgtCntn" title="참석대상" rows="5">${rtnInfo.ptcptTrgtCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">교육인원</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="ptcptCnt" name="ptcptCnt" value="${rtnInfo.ptcptCnt}" title="교육인원" maxlength="50" style="width:100px;"/> 명
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">교육시간</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm" id="ptcptHh" name="ptcptHh" title="교육시간" >
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                <c:if test="${cdList.cd ne 'EBC_VISIT_CD04' and fn:contains(cdList, 'EBC_VISIT_CD04')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.ptcptHh eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset style="margin-bottom: 25px;">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사소개서</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="itrdcFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">기본계획<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="row form-inline" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">교육일정<span class="star"> *</span></label>
                            <div class="col-sm-9 ">
                                <div class="input-group col-md-2" style="z-index:0;">
                                    <input type="text" class="form-control datetimepicker_strtDt" id="edctnStrtDt" name="edctnStrtDt" value="${kl:convertDate(rtnInfo.edctnStrtDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="교육시작일자" />
                                    <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                </div>
                                &nbsp;~&nbsp;
                                <div class="input-group col-md-2" style="z-index:0;">
                                    <input type="text" class="form-control datetimepicker_endDt" id="edctnEndDt" name="edctnEndDt" value="${kl:convertDate(rtnInfo.edctnEndDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="교육종료일자" />
                                    <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                            <input type="text" class="form-control input-sm notRequired" id="edctnPlace" name="edctnPlace" value="${rtnInfo.edctnPlace}" title="교육장소" maxlength="50" style="width: 220px;"/>
                        </div>
                        <div class="row vertical" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">협업기관<span class="star"> *</span></label>
                            <div class="col-sm-10 form-inline">
                                <input type="text" class="form-control input-sm" id="cprtnInstt" name="cprtnInstt" value="${rtnInfo.cprtnInstt}" title="협업기관" maxlength="50"/>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">교육계획<span class="star"> *</span></label>
                            <div class="col-sm-10 form-inline">
                                <input type="text" class="form-control input-sm " id="edctnPlanCnt" name="edctnPlanCnt" value="${rtnInfo.edctnPlanCnt}" title="교육계획" maxlength="50" /> 명
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">교육시간<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <div class="row" style="margin-bottom: 20px;">
                                    <label class="col-sm-1 control-label">강사1 교육시간<span class="star"> *</span></label>
                                    <div class="col-sm-9 form-inline">
                                        <input type="text" class="form-control input-sm" id="isttrEdctnTime1" name="isttrEdctnTime1" value="${rtnInfo.isttrEdctnTime1}" title="강사1 교육시간" maxlength="50" /> H
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 20px;">
                                    <label class="col-sm-1 control-label">강사2 교육시간<span class="star"> *</span></label>
                                    <div class="col-sm-9 form-inline">
                                        <input type="text" class="form-control input-sm" id="isttrEdctnTime2" name="isttrEdctnTime2" value="${rtnInfo.isttrEdctnTime2}" title="강사2 교육시간" maxlength="50" /> H
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">교육실적<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">시행여부<span class="star"> *</span></label>
                        <div class="input-group">
                            <select class="form-control input-sm" id="enfrcmntCd" name="enfrcmntCd" title="시행여부" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD02')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">운영방법<span class="star"> *</span></label>
                          ${rtnInfo.edctnMthd}
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">방문교육<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">방문교육회사<span class="star"> *</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control input-sm notRequired" id="vstEdctnCmpn" name="vstEdctnCmpn" value="${rtnInfo.vstEdctnCmpn}" title="방문교육회사" maxlength="50" style="width: 220px;"/>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">방문교육주제<span class="star"> *</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control input-sm notRequired" id="vstEdctnTheme" name="vstEdctnTheme" value="${rtnInfo.vstEdctnTheme}" title="방문교육주제" maxlength="50" style="width: 220px;"/>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">방문교육일<span class="star"> *</span></label>
                        <div class="input-group" style="z-index:0;width: 220px;">
                            <input type="text" class="form-control input-sm datetimepicker_strtDt" id="vstEdctnDt" name="vstEdctnDt" value="${kl:convertDate(rtnInfo.vstEdctnDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="설립일자" />
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">방문교육시간<span class="star"> *</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control input-sm notRequired" id="vstEdctnTime" name="vstEdctnTime" value="${rtnInfo.vstEdctnTime}" title="방문교육시간" maxlength="50" style="width: 220px;"/> H
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">교육 참석율<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <label class="col-sm-1 control-label">신청인원<span class="star"> *</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control input-sm notRequired" id="appctnCnt" name="appctnCnt" value="${rtnInfo.appctnCnt}" title="신청인원" maxlength="50" style="width: 220px;"/> 명
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
            <label class="col-sm-1 control-label">교육 참석인원<span class="star"> *</span></label>
            <div class="col-sm-11">
                <div class="row" style="margin-bottom: 20px;">
                    <c:forEach var="cdList1" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                        <c:if test="${cdList eq 'EBC_VISIT_CD03001'}">
                            <input type="hidden" class="form-control input-sm notRequired" id="rsltTypeCd" name="educntList" value="${cdList1.cd}" title="결과구분코드" maxlength="50" style="width: 220px;"/>
                        </c:if>
                    </c:forEach>
                    <c:forEach var="cdList" items="${oneCdList}" varStatus="status">
                          <label class="col-sm-1 control-label">${cdList.cdNm}</label>
                          <div class="input-group">
                              <c:forEach var="cdList1" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                  <c:if test="${cdList eq 'EBC_VISIT_CD03001'}">
                                   <input type="hidden" class="form-control input-sm notRequired" id="rsltTypeCd1" name="eduCntList" value="${cdList1.cd}" title="결과구분코드" maxlength="50" style="width: 220px;"/>
                                  </c:if>
                              </c:forEach>
                              <input type="hidden" class="form-control input-sm notRequired" id="optnCd1" name="eduCntList" value="${cdList.cd}" title="옵션코드" maxlength="50" style="width: 220px;"/>
                              <input type="text" class="form-control input-sm notRequired" id="rsltVal1" name="eduCntList" value="${rtnInfo.rsltVal}" title="결과값" maxlength="50" style="width: 220px;"/>
                          </div>
                    </c:forEach>
                </div>
            </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">분야별 참석인원<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <c:forEach var="cdList" items="${twoCdList}" varStatus="status">
                            <label class="col-sm-1 control-label">${cdList.cdNm}</label>
                            <div class="input-group">
                                <c:forEach var="cdList1" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                    <c:if test="${cdList eq 'EBC_VISIT_CD03001'}">
                                        <input type="hidden" class="form-control input-sm notRequired" id="rsltTypeCd2" name="fieldCntList" value="${cdList1.cd}" title="결과구분코드" maxlength="50" style="width: 220px;"/>
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" class="form-control input-sm notRequired" id="optnCd2" name="fieldCntList" value="${cdList.cd}" title="옵션코드" maxlength="50" style="width: 220px;"/>
                                <input type="text" class="form-control input-sm notRequired" id="rsltVal2" name="fieldCntList" value="${rtnInfo.rsltVal}" title="결과값" maxlength="50" style="width: 220px;"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">직급별 참석인원<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <c:forEach var="cdList" items="${threeCdList}" varStatus="status">
                            <label class="col-sm-1 control-label">${cdList.cdNm}</label>
                            <div class="input-group">
                                <c:forEach var="cdList1" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                    <c:if test="${cdList eq 'EBC_VISIT_CD03001'}">
                                        <input type="hidden" class="form-control input-sm notRequired" id="rsltTypeCd3" name="roleCntList" value="${cdList1.cd}" title="결과구분코드" maxlength="50" style="width: 220px;"/>
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" class="form-control input-sm notRequired" id="optnCd3" name="roleCntList" value="${cdList.cd}" title="옵션코드" maxlength="50" style="width: 220px;"/>
                                <input type="text" class="form-control input-sm notRequired" id="rsltVal3" name="roleCntList" value="${rtnInfo.rsltVal}" title="결과값" maxlength="50" style="width: 220px;"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">교육만족도<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <div class="row" style="margin-bottom: 20px;">
                        <c:forEach var="cdList" items="${fourCdList}" varStatus="status">
                            <label class="col-sm-1 control-label">${cdList.cdNm}</label>
                            <div class="input-group">
                                <c:forEach var="cdList1" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                    <c:if test="${cdList eq 'EBC_VISIT_CD03001'}">
                                        <input type="hidden" class="form-control input-sm notRequired" id="rsltTypeCd4" name="eduSatisfyList" value="${cdList1.cd}" title="결과구분코드" maxlength="50" style="width: 220px;"/>
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" class="form-control input-sm notRequired" id="optnCd4" name="eduSatisfyList" value="${cdList.cd}" title="옵션코드" maxlength="50" style="width: 220px;"/>
                                <input type="text" class="form-control input-sm notRequired" id="rsltVal4" name="eduSatisfyList" value="${rtnInfo.rsltVal}" title="결과값" maxlength="50" style="width: 220px;"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
            <label class="col-sm-1 control-label">강의교안<span class="star"> *</span></label>
            <div class="col-sm-11">
                <div class="form-group text-sm">
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </div>
            </fieldset>
            <fieldset>
            <label class="col-sm-1 control-label">기타자료<span class="star"> *</span></label>
            <div class="col-sm-11">
                <div class="form-group text-sm">
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="etcMatlsFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </div>
            </fieldset>
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 등록자</th>
                            <td>${ rtnInfo.regName }</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ not empty rtnInfo.modName }">
                                        ${ rtnInfo.modName }
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <th>최종 수정일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ not empty rtnInfo.modDtm }">
                                        ${ kl:convertDate(rtnInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpc/MPCLecturerSrchLayer.jsp"></jsp:include><!--강사검색-->
