<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnInfo" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebc/EBCVisitEduWriteCtrl">
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.vstSeq}" />
            <input type="hidden" class="notRequired" id="vstSeq" name="vstSeq" value="${rtnInfo.vstSeq}" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnInfo.memSeq}" />
            <input type="hidden" class="notRequired" id="bfreMemSeq" name="bfreMemSeq" value="${rtnInfo.memSeq}" />
            <input type="hidden" class="notRequired" id="vstRsltSeq" name="vstRsltSeq" value="${rtnInfo.vstRsltSeq}" />
            <input type="hidden" class="notRequired" id="itrdcFileSeq" name="itrdcFileSeq" value="${rtnInfo.itrdcFileSeq}" />
            <input type="hidden" class="notRequired" id="lctrFileSeq" name="lctrFileSeq" value="${rtnInfo.lctrFileSeq}" />
            <input type="hidden" class="notRequired" id="etcMatlsFileSeq" name="etcMatlsFileSeq" value="${rtnInfo.etcMatlsFileSeq}" />
            <input type="hidden" class="notRequired" id="selectCtgryCd" name="selectCtgryCd" value="${rtnInfo.selectCtgryCd}" />
            <input type="hidden" class="notRequired" id="edctnPlaceAddr" name="edctnPlaceAddr" value="${rtnInfo.edctnPlaceAddr}"/>
            <input type="hidden" id="bsnmNo" name="bsnmNo" value="${rtnInfo.appctnBsnmNo}"/>
            <input type="hidden" id="memId" name="memId" value="${rtnInfo.id}"/>

            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm" readonly="readonly" name="memInfo" value="${rtnInfo.name}(${rtnInfo.id})" title="신청자 정보">
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-sm btn-info bsnmNoBtn">회원검색</button>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <p id="emailTxt" class="form-control-static">${rtnInfo.email}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm" id="deptCd" name="deptCd" title="부서" >
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'MEM_CD02') and cdList.cd ne 'MEM_CD02'}">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.deptCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control input-sm" id="deptDtlNm" name="deptDtlNm" value="${rtnInfo.deptDtlNm}" title="부서상세명" maxlength="50"/>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm" id="pstnCdSelect" name="pstnCd" title="직급" >
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'MEM_CD01') and cdList.cd ne 'MEM_CD01'}">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.pstnCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4 pstnCdInput" style="margin-left: -15px; <c:if test="${rtnInfo.pstnCd ne 'MEM_CD01007'}">display: none;</c:if>">
                            <input type="text" class="form-control input-sm notRequired" id="deptCdNm" name="deptCdNm" value="${rtnInfo.deptCdNm}" title="직급" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static">${rtnInfo.hpNo}</p>
                    </div>
                    <label class="col-sm-1 control-label" style="margin-left: -14px;">일반 전화번호</label>
                    <div class="col-sm-1">
                        <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="11"/>
                    </div>
                </div>
            </fieldset>
            <br><br>
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <input type="hidden" id="appctnBsnmNo" name="appctnBsnmNo" value="${rtnInfo.appctnBsnmNo}"/>
                        <p class="form-control-static" name="cmpnNm" id="cmpnNm">${rtnInfo.cmpnNm}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
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
                    </div>
                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
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
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <p class="form-control-static">${rtnInfo.rprsntNm}</p>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
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
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <input type="text" class="form-control input-sm" id="cmpnTelNo" name="cmpnTelNo" value="${rtnInfo.cmpnTelNo}" title="회사 전화번호" maxlength="11" placeholder="회사 전화번호 입력" style="width: 200px;"/>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static">${kl:bsnmNoConvert(rtnInfo.appctnBsnmNo)}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-gray" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnInfo.zipcode}" readonly placeholder="우편번호" maxlength="50" style="width: 95px;"/>
                            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" maxlength="50" style="width: 295px;"/>
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnInfo.dtlAddr}" title="우편번호" placeholder="상세주소 입력" maxlength="50" style="width: 400px;"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${rtnInfo.slsPmt}" title="매출액" placeholder="매출액 입력" maxlength="50" style="width: 220px;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/> 억원
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
                        <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
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
                    <div class="col-sm-5" style="margin-left: -15px;">
                        <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">${sqInfoList.sqInfoList}
                            <div class="col-sm-11 sqInfoList" style="padding-top: 10px;padding-bottom: 10px;">
                                <input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value="${list.cbsnSeq}"/>
                                <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="${list.nm}" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="${list.score}" title="점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="${list.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                            </div>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${sqInfoListCnt == 0}">
                                <c:forEach var="i" begin="1" end="${3 - sqInfoListCnt}" varStatus="status">
                                    <div class="col-sm-11 sqInfoList" style="padding-top: 10px;padding-bottom: 10px;"><input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value=""/>
                                        <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="" title="점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                        <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:when test="${sqInfoListCnt != 3 and sqInfoListCnt > 0}">
                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                    <div class="col-sm-11 sqInfoList" style="padding-top: 10px;padding-bottom: 10px;"><input type="hidden" class="notRequired" id="cbsnSeq${status.count + 1}" name="sqInfoList${status.count + 1}" value=""/>
                                        <input type="text" class="form-control input-sm notRequired" id="nm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                        <select class="form-control input-sm notRequired" id="year${status.count + 1}" name="sqInfoList${status.count + 1}" title="평가년도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:when test="${sqInfoListCnt == 3}">
                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                    <div class="col-sm-11 sqInfoList" style="padding-top: 10px;padding-bottom: 10px;"><input type="hidden" class="notRequired" id="cbsnSeq${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value=""/>
                                        <input type="text" class="form-control input-sm notRequired" id="nm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                        <select class="form-control input-sm notRequired" id="year${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" title="평가년도" style="width:auto;">
                                            <option value="">SQ 평가년도 선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
            <br><br>
            <fieldset class="trsfField">
                <div class="clearfix">
                    <h6 class="pull-left mt0">
                        <em class="ion-play mr-sm"></em>신청자 변경 이력
                        <input type="hidden" id="trsfListContainerTotCnt" value="0">
                    </h6>
                    <!-- 현재 페이징 번호 -->
                    <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnInfo.pageIndex }" />
                    <!-- 페이징 버튼 사이즈 -->
                    <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnInfo.pageRowSize }" />
                    <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnInfo.listRowSize }" />

                    <div class="pull-right ml-sm" data-html2canvas-ignore="true">
                        <select class="form-control input-sm listRowSizeContainer" >
                            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                <jsp:param name="listRowSize" value="${ rtnInfo.listRowSize }" />
                            </jsp:include>
                        </select>
                    </div>
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">이전 신청자</th>
                                <th class="text-center">변경 신청자</th>
                                <th class="text-center">변경일 / 변경자</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="trsfListContainer"/>

                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="trsfPagingContainer"/>
                    </div>
            </fieldset>
            <fieldset>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청내용</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청사유<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="appctnRsn" name="appctnRsn" title="신청사유" rows="5" maxlength="500">${rtnInfo.appctnRsn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">신청분야<span class="star"> *</span></label>
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
                    <label class="col-sm-1 control-label">신청주제<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control" id="appctnThemeCntn" name="appctnThemeCntn" title="신청주제" rows="5" maxlength="500">${rtnInfo.appctnThemeCntn}</textarea>
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
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-gray" id="searchEduPlacePostCode" value="우편번호 찾기">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle notRequired" id="samePlaceBtn" name="samePlace" />
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
                        <textarea class="form-control" id="ptcptTrgtCntn" name="ptcptTrgtCntn" title="참석대상" rows="5" maxlength="500">${rtnInfo.ptcptTrgtCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">교육인원<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="ptcptCnt" name="ptcptCnt" value="${rtnInfo.ptcptCnt}" title="교육인원" maxlength="50" style="width:100px;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/> 명
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">교육시간<span class="star"> *</span></label>
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
                        </select> 시간
                    </div>
                </div>
            </fieldset>
            <fieldset style="margin-bottom: 25px;" class="itrdcFile">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사소개서<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone attachFile" data-file-field-nm="itrdcFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="회사소개서">
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
            <br><br>
            <fieldset>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>교육실적</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">개요</label>
                    <div class="col-sm-11">
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">실적마감여부</label>
                                <div class="col-sm-11">
                                    <c:set var="rsltEndYn" value="${kl:nvl(rtnInfo.rsltEndYn, 'N')}" />
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="rsltEndYn" value="N" title="실적마감여부" <c:if test="${rsltEndYn eq 'N'}">checked</c:if> />
                                        <span class="ion-record"></span> 미마감
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="rsltEndYn" value="Y" title="실적마감여부" <c:if test="${rsltEndYn eq 'Y'}">checked</c:if> />
                                        <span class="ion-record"></span> 마감
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="rsltEndYn" value="C" title="실적마감여부" <c:if test="${rsltEndYn eq 'C'}">checked</c:if> />
                                        <span class="ion-record"></span> 교육취소
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">교육상태</label>
                                <div class="col-sm-10 form-inline">
                                    <div class="input-group">
                                        <select class="form-control input-sm" id="edctnSttsCd" name="edctnSttsCd" title="교육상태" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                                            <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD02') and cdList.cd ne 'EBC_VISIT_CD02'}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.edctnSttsCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">확정주제</label>
                                <div class="col-sm-10 form-inline">
                                    <input type="text" class="form-control input-sm notRequired" id="cnfrmdTheme" name="cnfrmdTheme" value="${rtnInfo.cnfrmdTheme}" title="확정주제" maxlength="50" style="width: 220px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">년도</label>
                                <div class="col-sm-10 form-inline">
                                    <input type="text" class="form-control input-sm notRequired" id="edctnYear" name="edctnYear" value="${rtnInfo.edctnYear}" title="교육년도" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 220px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row form-inline">
                                <label class="col-sm-1 control-label">교육기간</label>
                                <div class="col-sm-10">
                                    <div class="form-inline">
                                        <div class="input-group form-date-group mr-sm">
                                            <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" id="edctnStrtDtm" name="edctnStrtDtm" value="${kl:convertDate(rtnInfo.edctnStrtDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy-MM-dd', '')}" title="교육시작일"/>
                                            <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            <select class="form-control input-sm wd-sm notRequired" name="edctnStrtHour" id="edctnStrtHour" title="교육 시작시간" style="margin-left: 5px;">
                                                <option value="">선택</option>
                                                <c:forEach var="cdList" items="${cdDtlList.SYSTEM_HOUR}" varStatus="status">
                                                    <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnInfo.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                                </c:forEach>
                                            </select>
                                            <span class="input-group-addon bg-white b0">~</span>
                                            <input type="text" class="form-control notRequired input-sm datetimepicker_endDt" id="edctnEndDtm" name="edctnEndDtm" value="${kl:convertDate(rtnInfo.edctnEndDtm, 'yyyy-MM-dd hh:mm:ss', 'yyyy-MM-dd', '')}" title="교육종료일"/>
                                            <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            <select class="form-control input-sm wd-sm notRequired" name="edctnEndHour" id="edctnEndHour" title="교육 종료시간" style="margin-left: 5px;">
                                                <option value="">선택</option>
                                                <c:forEach var="cdList" items="${cdDtlList.SYSTEM_HOUR}" varStatus="status">
                                                    <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnInfo.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">교육장소</label>
                                <div class="col-sm-10 form-inline">
                                    <input type="text" class="form-control input-sm notRequired" id="edctnPlace" name="edctnPlace" value="${rtnInfo.edctnPlace}" title="교육장소" maxlength="50" style="width: 220px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">강사</label>
                                <div class="col-sm-11">
                                    <button type="button" class="btn btn-inverse btn-sm eduIsttrSearch">
                                        강사검색
                                    </button>
                                    <table class="table table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th class="text-center">번호</th>
                                            <th class="text-center">이름</th>
                                            <th class="text-center">소속</th>
                                            <th class="text-center">약력(특이사항)</th>
                                            <th class="text-center">삭제</th>
                                        </tr>
                                        </thead>
                                        <!-- 리스트 목록 결과 -->
                                        <tbody id="isttrContainer">
                                        <tr data-total-count="0" class="notIsttr">
                                            <td colspan="5" class="text-center" <c:if test="${isttrList.size() ne 0}">style="display:none;"</c:if>>
                                                검색결과가 없습니다.<br>
                                                (등록된 데이터가 없습니다.)
                                            </td>
                                        </tr>
                                        <tr class="setIsttr" data-total-count="0" style="display:none;">
                                            <td class="text-center">
                                            </td>
                                            <td class="text-center">
                                            </td>
                                            <td class="text-center">
                                            </td>
                                            <td class="text-center">
                                            </td>
                                            <td class="text-center">
                                                <button type="button" class="btn btn-sm btn-danger btnOneTrRemove">삭제</button>
                                            </td>
                                            <input type="hidden" class="notRequired" name="isttrSeq" id="isttrSeq" value="" disabled="true" titlle="강사번호">
                                        </tr>
                                        <c:choose>
                                            <c:when test="${isttrList.size() ne 0}">
                                                <c:forEach var="list" items="${isttrList}" varStatus="status">
                                                    <tr>
                                                        <td class="text-center">
                                                                ${isttrList.size() - status.index}
                                                        </td>
                                                        <td class="text-center">
                                                                ${list.name}
                                                        </td>
                                                        <td class="text-center">
                                                                ${list.ffltnNm}
                                                        </td>
                                                        <td class="text-center">
                                                            <c:choose>
                                                                <c:when test="${ not empty list.spclCntn}">
                                                                    ${list.spclCntn}
                                                                </c:when>
                                                                <c:otherwise>-</c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-sm btn-danger btnOneTrRemove">삭제</button>
                                                        </td>
                                                        <input type="hidden" class="notRequired" name="isttrSeq" value="${list.isttrSeq}" disabled="true" titlle="강사번호">
                                                    </tr>
                                                </c:forEach>

                                            </c:when>
                                        </c:choose>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">수료인원(명)</label>
                                <div class="col-sm-10 form-inline">
                                    <fmt:formatNumber var="formatPtcptRate" value="${rtnInfo.ptcptRate * 100}" pattern="##0" />
                                    <input type="text" class="form-control input-sm notRequired" id="cmptnCnt" name="cmptnCnt" value="${rtnInfo.cmptnCnt}" title="수료인원" maxlength="3" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 220px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="row">
                                <label class="col-sm-1 control-label">참석률(%)</label>
                                <div class="col-sm-10 form-inline">
                                    <input type="text" class="form-control input-sm notRequired" id="ptcptRate" name="ptcptRate" value="${not empty rtnInfo.ptcptRate ? formatPtcptRate : ''}" title="참석률(%)" maxlength="3" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 220px;"/>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </fieldset>

            <label class="col-sm-1 control-label">회사별<br>수료 인원(명)</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${eduCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>

                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03001">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>

            <label class="col-sm-1 control-label">분야별<br>수료 인원(명)</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${fieldCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>

                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03002">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>

            <label class="col-sm-1 control-label">직급별<br>수료 인원(명)</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${positionCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>

                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03003">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>

            <label class="col-sm-1 control-label">출석/평가</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${attendCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>
                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03004">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>

            <label class="col-sm-1 control-label">만족도</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${satisfactionCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>
                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03005">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>


            <label class="col-sm-1 control-label">교육시간</label>
            <fieldset>
                <div class="form-group text-sm">
                    <c:forEach var="cdList" items="${eduTimeCdList}" varStatus="status">
                        <c:forEach var="resultOpList" items="${resultOpList}">
                            <c:if test="${cdList.cd eq resultOpList.optnCd}">
                                <c:set var="tempPmt" value="${resultOpList.rsltVal}" />
                            </c:if>
                        </c:forEach>
                        <label class="col-sm-1 control-label mt-sm">${cdList.cdNm}</label>
                        <div class="col-sm-2 cnt-list" data-rslt-type-cd="EBC_VISIT_CD03006">
                            <input type="text" class="form-control input-sm mt-sm notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="3" placeholder="${cdList.cdNm} 입력" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                        </div>
                    </c:forEach>
                </div>
            </fieldset>

            <fieldset class="lctrFile">
                <label class="col-sm-1 control-label">강의교안</label>
                <div class="col-sm-11">
                    <div class="form-group text-sm">
                        <div class="col-sm-10 col-md-11">
                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                            <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="강의교안">
                                <div class="dz-default dz-message">
                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                </div>
                            </div>
                            <p class="text-bold mt">
                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                            </p>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="etcMatlsFile">
                <label class="col-sm-1 control-label">기타자료</label>
                <div class="col-sm-11">
                    <div class="form-group text-sm">
                        <div class="col-sm-10 col-md-11">
                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                            <div class="dropzone attachFile notRequired" data-file-field-nm="etcMatlsFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="기타자료">
                                <div class="dz-default dz-message">
                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                </div>
                            </div>
                            <p class="text-bold mt">
                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                            </p>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
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
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ not empty rtnInfo.modName }">
                                        ${ rtnInfo.modName }(${ rtnInfo.modId })
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
<script type="text/javascript">
    $(document).ready(function() {
        var modiYn = "N";

        $(document).on('input', 'select, input:text, textarea', function() {
            if (modiYn === "N" && $(this).val()) {
                modiYn = "Y";
            }
        });

        $(document).on('change', 'input:checkbox', function() {
            if (modiYn === "N" && $(this).is(":checked")) {
                modiYn = "Y";
            }
        });

        // 수정 페이지의 경우 modiYn을 "Y"로 설정
        if ($("#detailsKey").val() && $("#detailsKey").val() !== undefined) {
            modiYn = "Y";
        }

        history.pushState(null, null, '');
        window.addEventListener('popstate', browserPopstateHandler);

        function browserPopstateHandler(event) {

            if (modiYn === "Y") {
                var confirmed = confirm(msgCtrl.getMsg("confirm.list"));

                // browserPopstateHandler가 재발생할 수 있도록 브라우저 상태 다시 설정하기
                history.pushState(null, null, window.location.pathname + window.location.search);

                if (confirmed) {
                    var strPam = $("#btnList").data("strPam");
                    location.href = "./list?" + strPam;
                }
            } else {
                var strPam = $("#btnList").data("strPam");
                location.href = "./list?" + strPam;
            }
        }

        // 페이지 이동 시 이벤트 핸들러 등록 해제
        $(window).on('beforeunload', function() {
        });
    });

</script>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include><!--회원검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpc/MPCLecturerSrchLayer.jsp"></jsp:include><!--강사검색-->
