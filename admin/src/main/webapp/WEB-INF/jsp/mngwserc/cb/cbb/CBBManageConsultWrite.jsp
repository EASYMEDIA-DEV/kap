<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoList" value="${rtnDto.sqInfoList}"/>
<c:set var="sqInfoList1" value="${rtnDto.sqInfoList1}"/>
<c:set var="sqInfoList2" value="${rtnDto.sqInfoList2}"/>
<c:set var="dpndCmpnList" value="${rtnDto.dpndCmpnList}"/>
<c:set var="dlvryCmpnList" value="${rtnDto.dlvryCmpnList}"/>
<c:set var="appctnTypeList" value="${rtnDto.appctnTypeList}"/>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/cb/cba/CBATechGuidanceWriteCtrl">
        <h6 class="mt0">신청부품사 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.cnstgSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="itrdcFileSeq" name="itrdcFileSeq" value="${rtnDto.itrdcFileSeq}" />
            <input type="hidden" class="notRequired" id="impvmFileSeq" name="impvmFileSeq" value="${rtnDto.impvmFileSeq}" />
            <c:forEach var="rsumeList" items="${rtnDto.rsumeList}" varStatus="status">
            <input type="hidden" class="notRequired" id="initVstFileSeq" name="initVstFileSeq" value="${rsumeList.initVstFileSeq}" />
            <input type="hidden" class="notRequired" id="kickfFileSeq" name="kickfFileSeq" value="${rsumeList.kickfFileSeq}" />
            <input type="hidden" class="notRequired" id="lvlupFileSeq" name="lvlupFileSeq" value="${rsumeList.lvlupFileSeq}" />
            <input type="hidden" class="notRequired" id="etcFileSeq" name="etcFileSeq" value="${rsumeList.etcFileSeq}" />
            </c:forEach>
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnDto.memSeq}" />
            <input type="hidden" class="notRequired" id="bfreMemSeq" name="bfreMemSeq" value="${rtnDto.memSeq}" />
            <input type="hidden" class="notRequired" id="memId" name="id" value="${rtnDto.id}" />
            <input type="hidden" class="notRequired" id="name" name="name" value="${rtnDto.name}" />
            <input type="hidden" class="notRequired" id="cmpnNfrmlNm" name="cmpnNfrmlNm" value="${rtnDto.cmpnNfrmlNm}" />
            <input type="hidden" class="notRequired" id="cmpnCd" name="cmpnCd" value="${rtnDto.cmpnCd}" />
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" readonly="readonly" name="memInfo" value="${rtnDto.name}(${rtnDto.id})" title="신청자 정보">
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="hidden" class="notRequired" name="email" id="email" value="${rtnDto.email}"/>
                        <p class="form-control-static" name="email" id="emailTxt">${rtnDto.email}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer" name="deptCd">
                            <c:forEach var="memCd" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(memCd.cd,'CD02')  && memCd.cd ne 'MEM_CD02'}">
                                    <option value="${memCd.cd}"<c:if test="${rtnDto.deptCd eq memCd.cdNm}">selected</c:if>>${memCd.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" name="deptDtlNm" value="${rtnDto.deptDtlNm}" maxlength="50" title="부서">
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer" id="pstnCdSelect" name="pstnCd" title="직급">
                            <c:forEach var="memCd" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(memCd.cd,'CD01') && memCd.cd ne 'MEM_CD01'}">
                                    <option value="${memCd.cd}" <c:if test="${rtnDto.pstnCd eq memCd.cd}">selected</c:if>>${memCd.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2 pstnCdInput" <c:if test="${rtnDto.pstnCd ne 'MEM_CD01007'}">style="display: none"</c:if>>
                        <input type="text" class="form-control input-sm notRequired"  name="pstnNm" value="${rtnDto.pstnNm}" title="직급 기타명"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <p class="form-control-static" name="hpNo">${rtnDto.hpNo}</p>
                    </div>
                    <label class="col-sm-1 control-label">전화번호</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="telNo" value="${rtnDto.telNo}" title="전화번호"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="hidden" class="notRequired" id="cmpnNm" name="cmpnNm" value="${rtnDto.cmpnNm}" />
                        <p class="form-control-static" name="cmpnNm" id="cmpnNmText">${rtnDto.cmpnNm}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm listRowSizeContainer ctgryCd" name="ctgryCd" id="ctgryCdSelect">
                            <c:forEach var="cpType" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cpType.cd,'COMPANY01') && fn:length(cpType.cd) eq 12}">
                                    <option value="${cpType.cd}" <c:if test="${rtnDto.ctgryCd eq cpType.cd}">selected</c:if>>${cpType.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">규모</label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm listRowSizeContainer" name="sizeCd" id="sizeCdSelect">
                            <c:forEach var="cpSize" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cpSize.cd,'COMPANY02') && cpSize.cd ne 'COMPANY02'}">
                                    <option value="${cpSize.cd}">${cpSize.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="hidden" class="notRequired" name="rprsntNm" id="rprsntNm" value="${rtnDto.rprsntNm}" title="대표자명"/>
                        <p class="form-control-static" name="rprsntNm" id="rprsntNmTxt">${rtnDto.rprsntNm}</p>
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="input-group col-md-2" style="z-index:0;">
                        <input type="text" class="form-control datetimepicker_strtDt"  name="stbsmDt" value="${kl:convertDate(rtnDto.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="설립일자" />
                        <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" name="cmpnTelNo" value="${rtnDto.cmpnTelNo}" title="전화번호"/>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star">*</span></label>
                    <div class="col-sm-4">
                        <input type="hidden" class="notRequired" id="bsnmNo" name="bsnmNo" value="${rtnDto.bsnmNo}"/>
                        <p class="form-control-static" name="bsnmNo" id="bsnmNoText">${rtnDto.bsnmNo}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사 주소<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" readonly="readonly" id="hqZipcode" name="zipcode" value="${rtnDto.zipcode}" title="우편번호"/>
                        <input type="text" class="form-control input-sm" readonly="readonly" id="hqBscAddr" name="bscAddr" value="${rtnDto.bscAddr}" title="기본 주소"/>
                        <input type="text" class="form-control input-sm"  id="hqDtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" title="상세 주소" />
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-sm btn-warning searchPostCode" id="hqAddr">우편번호 검색</button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">매출액/연도</label>
                    <div class="col-sm-1">
                        <input type="number" class="form-control input-sm notRequired" name="slsPmt" value="${rtnDto.slsPmt}" title="매출액"/>
                    </div>
                    <label class="col-sm-1 control-label">억원</label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm listRowSizeContainer" name="slsYear">
                            <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                    <option value="${yearList.cd}" <c:if test="${rtnDto.slsYear eq yearList.cd}">selected</c:if>>${yearList.cdNm} </option>
                                    </label>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">년</label>
                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-3">
                        <input type="number" class="form-control input-sm notRequired" title="직원수" name="mpleCnt" value="${rtnDto.mpleCnt}">
                    </div>
                    <label class="col-sm-1 control-label">명</label>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct1" value="${rtnDto.mjrPrdct1}" title="주생산품1"/>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct2" value="${rtnDto.mjrPrdct2}" title="주생산품2"/>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct3" value="${rtnDto.mjrPrdct3}" title="주생산품3"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="fiveStar" <c:if test="${rtnDto.ctgryCd eq 'COMPANY01002'}">style="display: none"</c:if>> <%--구분이 1차일 때--%>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">품질5스타</label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="qlty5StarCd" id="qlty5StarCd">
                                <c:forEach var="qltyList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(qltyList.cd, 'COMPANY03') && qltyList.cd ne 'COMPANY03'}">
                                        <option value="${qltyList.cd}" <c:if test="${rtnDto.qlty5StarCd eq qltyList.cd}">selected</c:if>>${qltyList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="qlty5StarYear" id="qlty5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}" <c:if test="${rtnDto.qlty5StarYear eq yearList.cd}">selected</c:if>>${yearList.cd} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-1 control-label">년</label>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">납입5스타</label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="pay5StarCd" id="pay5StarCd">
                                <c:forEach var="payList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(payList.cd, 'COMPANY03') && payList.cd ne 'COMPANY03'}">
                                        <option value="${payList.cd}" <c:if test="${rtnDto.pay5StarCd eq payList.cd}">selected</c:if>>${payList.cdNm} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="pay5StarYear" id="pay5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}" <c:if test="${rtnDto.pay5StarYear eq yearList.cd}">selected</c:if>>${yearList.cd} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-1 control-label">년</label>
                    </div>
                </fieldset>
                <fieldset style="padding-bottom: 0px;margin-bottom: 0px;">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">기술5스타</label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="tchlg5StarCd" id="tchlg5StarCd">
                                <c:forEach var="tchList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(tchList.cd, 'COMPANY03') && tchList.cd ne 'COMPANY03'}">
                                        <option value="${tchList.cd}"<c:if test="${rtnDto.tchlg5StarCd eq tchList.cd}">selected</c:if>>${tchList.cdNm} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer notRequired" name="tchlg5StarYear" id="tchlg5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}" <c:if test="${rtnDto.tchlg5StarYear eq yearList.cd}">selected</c:if>>${yearList.cd} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-1 control-label">년</label>
                    </div>
                </fieldset>
            </fieldset>
            <fieldset class="sqInfo" <c:if test="${rtnDto.ctgryCd eq 'COMPANY01001'}">style="display: none"</c:if>><%--구분이 2차일 때--%>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">SQ정보</label>
                    <div class="col-sm-11">
                        <div class="col-sm-2">
                            <input type="hidden" class="form-control input-sm notRequired"  name="cbsnSeq" id="cbsnSeq" value="${sqInfoList[4]}"/>
                            <input type="text" class="form-control input-sm notRequired"  name="nm" id="nm" value="${sqInfoList[0]}"/>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm notRequired"  name="score" id="score" value="${sqInfoList[1]}"/>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer" name="year" id="yearSelect">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}"<c:if test="${sqInfoList[2] eq yearList.cd}">selected</c:if>>${yearList.cdNm} </option>
                                        </label>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm notRequired"  name="crtfnCmpnNm" id="crtfnCmpnNm" value="${sqInfoList[3]}"/>
                        </div>
                    </div>
                    <c:forEach var="i" begin="1" end="2">
                        <label class="col-sm-1 control-label"></label>
                        <c:choose>
                            <c:when test="${i eq 1}">
                                <div class="col-sm-11">
                                    <div class="col-sm-2">
                                        <input type="hidden" class="form-control input-sm notRequired"  name="cbsnSeq" id="cbsnSeq${i}" value="${sqInfoList1[4]}"/>
                                        <input type="text" class="form-control input-sm notRequired"  name="nm" id="nm${i}" value="${sqInfoList1[0]}"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control input-sm notRequired"  name="score" id="score${i}" value="${sqInfoList1[1]}"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control input-sm listRowSizeContainer" name="year" id="yearSelect${i}">
                                            <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                                <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                                    <option value="${yearList.cd}" <c:if test="${sqInfoList1[2] eq yearList.cd}">selected</c:if>>${yearList.cdNm} </option>
                                                    </label>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control input-sm notRequired"  name="crtfnCmpnNm" id="crtfnCmpnNm${i}" value="${sqInfoList1[3]}"/>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-sm-11">
                                    <div class="col-sm-2">
                                        <input type="hidden" class="form-control input-sm notRequired"  name="cbsnSeq" id="cbsnSeq${i}" value="${sqInfoList2[4]}"/>
                                        <input type="text" class="form-control input-sm notRequired"  name="nm" id="nm${i}" value="${sqInfoList2[0]}"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control input-sm notRequired"  name="score" id="score${i}" value="${sqInfoList2[1]}"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control input-sm listRowSizeContainer" name="year" id="yearSelect${i}">
                                            <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                                <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                                    <option value="${yearList.cd}" <c:if test="${sqInfoList2[2] eq yearList.cd}">selected</c:if>>${yearList.cdNm} </option>
                                                    </label>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control input-sm notRequired"  name="crtfnCmpnNm" id="crtfnCmpnNm${i}" value="${sqInfoList2[3]}"/>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </fieldset>
            <fieldset>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>담당인원 정보</h6>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                </div>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                    <label class="col-sm-1 control-label">직급</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                </div>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired"  name="score"/>
                    </div>
                </div>
            </fieldset>

            <c:choose>
                <c:when test="${not empty rtnDto}">
                <fieldset class="ptcptField">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>신청자 변경 이력
                            <input type="hidden" id="trnsfListContainerTotCnt" value="0">
                        </h6>
                        <!-- 현재 페이징 번호 -->
                        <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
                        <!-- 페이징 버튼 사이즈 -->
                        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
                    </div>
                    <div class="pull-right ml-sm">
                        <select class="form-control input-sm listRowSizeContainer" >
                            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
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
                </c:when>
            </c:choose>
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>상주기술지도 신청정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청일자<span class="star"> *</span></label>
                    <div class="input-group col-md-2" style="z-index:0;">
                        <input type="text" class="form-control datetimepicker_strtDt"  name="appctnDt" value="${kl:convertDate(rtnDto.appctnDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"title="신청일자" />
                        <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">거래처별 매출비중<span class="star"> *</span></label>
                    <div class="col-sm-4" id="dlvryTempDiv">
                        <button type="button" class="btn btn-sm btn-info cmpnPlus">업체추가</button>
                        <c:choose>
                            <c:when test="${not empty dlvryCmpnList}">
                                <c:forEach var="dlvryCmpnList" items="${dlvryCmpnList}" varStatus="status">
                                    <div class="row tempRow" id="dlvryRow" style="border:solid; border-width:1px; margin-top: 10px;">
                                        <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                                        <label class="col-sm-3 control-label">
                                            <input type="text" class="form-control input-sm" data-name="dlvryCmpnNmList" name="dlvryCmpnNm" value="${dlvryCmpnList.dlvryCmpnNm}" data-dlvryCmpnSeq="${dlvryCmpnList.cmpnDlvrySeq}" style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                                        </label>
                                        <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                                        <label class="col-sm-3 control-label">
                                            <input type="number" class="form-control input-sm" data-name="dlvryRateList" name="dlvryRate" value="${dlvryCmpnList.dlvryRate}" style="margin-bottom: 10px; margin-top:10px" title="매출비중" maxlength="3"/>
                                        </label>
                                        <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                                        <label class="col-sm-1 control-label closeLabel" <c:if test="${empty dlvryCmpnList}">style="display: none"</c:if>>
                                            <button type="button" class="close" aria-label="Close">x</button>
                                        </label>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="row tempRow" id="dlvryRow" style="border:solid; border-width:1px; margin-top: 10px;">
                                    <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                                    <label class="col-sm-3 control-label">
                                        <input type="text" class="form-control input-sm" data-name="dlvryCmpnNmList" name="dlvryCmpnNm" value="" data-dlvryCmpnSeq="" style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                                    </label>
                                    <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                                    <label class="col-sm-3 control-label">
                                        <input type="number" class="form-control input-sm" data-name="dlvryRateList" name="dlvryRate" value="" style="margin-bottom: 10px; margin-top:10px" title="매출비중" maxlength="3"/>
                                    </label>
                                    <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                                    <label class="col-sm-1 control-label closeLabel" style="display: none">
                                        <button type="button" class="close" aria-label="Close">x</button>
                                    </label>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">완성차 의존율<span class="star"> *</span></label>
                    <div class="col-sm-4" id="dpTempDiv">
                        <button type="button" class="btn btn-sm btn-info dpndnPlus">업체추가</button>
                        <c:choose>
                            <c:when test="${not empty dpndCmpnList}">
                                <c:forEach var="dpndCmpnList" items="${dpndCmpnList}" varStatus="status">
                                    <div class="row dpTempRow" id="dpndnRow" style="border:solid; border-width:1px; margin-top: 10px">
                                        <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                                        <label class="col-sm-3 control-label">
                                            <input type="text" class="form-control input-sm" data-name="dpndnCmpnNmList" name="dpndnCmpnNm" value="${dpndCmpnList.dpndnCmpnNm}" data-dpndnSeq="${dpndCmpnList.dpndnSeq}"style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                                        </label>
                                        <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                                        <label class="col-sm-3 control-label">
                                            <input type="number" class="form-control input-sm" data-name="dpndnRateList" name="dpndnRate" value="${dpndCmpnList.dpndnRate}" style="margin-bottom: 10px; margin-top:10px" title="매출비중" maxlength="3"/>
                                        </label>
                                        <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                                        <label class="col-sm-1 control-label closeLabel" <c:if test="${empty dpndnRateList}">style="display: none"</c:if>>
                                            <button type="button" class="close" aria-label="Close">x</button>
                                        </label>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="row dpTempRow" id="dpndnRow" style="border:solid; border-width:1px; margin-top: 10px">
                                    <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                                    <label class="col-sm-3 control-label">
                                        <input type="text" class="form-control input-sm" data-name="dpndnCmpnNmList" name="dpndnCmpnNm" value="" data-dpndnSeq=""style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                                    </label>
                                    <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                                    <label class="col-sm-3 control-label">
                                        <input type="number" class="form-control input-sm" data-name="dpndnRateList" name="dpndnRate" value="" style="margin-bottom: 10px; margin-top:10px" title="매출비중" maxlength="3"/>
                                    </label>
                                    <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                                    <label class="col-sm-1 control-label closeLabel" style="display: none">
                                        <button type="button" class="close" aria-label="Close">x</button>
                                    </label>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">품질담당인원</label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control input-sm notRequired" name="qltyPicCnt" value="${rtnDto.qltyPicCnt}"/>
                    </div>
                    <label class="col-sm-1 control-label">FAX<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control input-sm" name="faxNo" value="${rtnDto.faxNo}" title="FAX"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">지도요청 공장주소<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" readonly="readonly" id="zipcode" name="fctryZipcode" value="${rtnDto.fctryZipcode}" title="우편번호" placeholder="우편번호"/>
                        <input type="text" class="form-control input-sm" readonly="readonly" id="bscAddr" name="fctryBscAddr" value="${rtnDto.fctryBscAddr}" title="기본주소" placeholder="기본주소"/>
                        <input type="text" class="form-control input-sm notRequired" id="dtlAddr" name="fctryDtlAddr" value="${rtnDto.fctryDtlAddr}" title="상세주소" placeholder="상세주소 입력"/>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-sm btn-warning searchPostCode factAddr">우편번호 검색</button>
                    </div>
                    <div class="col-sm-1">
                        <label class="checkbox-inline c-checkbox">
                            <c:set var="rprsntApprvYn" value="${kl:nvl(rtnDto.rprsntApprvYn, 'N')}" />
                            <input type="checkbox" class="notRequired addrSame"  name="cmpnAddrSameYn" value="${rprsntApprvYn}"<c:if test="${rprsntApprvYn eq 'Y'}">checked</c:if> title="주소 동일 여부"/>
                            <span class="ion-checkmark-round"></span> 본사와 동일
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">소재지역<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer mainAddr" id="mainAddr" name="firstRgnsCd" title="소재지역">
                            <c:forEach var="addrList" items="${cdDtlList.ADDR_CD}" varStatus="status">
                                <c:if test="${fn:contains(addrList.cd,'MAIN')}">
                                    <option value="${addrList.cd}" <c:if test="${rtnDto.firstRgnsCd eq addrList.cd}">selected</c:if>>${addrList.cdNm} </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer subAddr" id="subAddr" name="scndRgnsCd" data-subAddr="${rtnDto.scndRgnsCd}" title="소재지역">
                            <option value="">전체</option>
                            <c:forEach var="addrList" items="${cdDtlList.ADDR_CD}" varStatus="status">
                                <c:if test="${!fn:contains(addrList.cd,'MAIN') && fn:contains(addrList.cd,'CD01')}">
                                    <option value="${addrList.cd}" <c:if test="${rtnDto.scndRgnsCd eq addrList.cd}">selected</c:if>>${addrList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자 승인여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="rprsntApprvYn" value="${rtnDto.rprsntApprvYn}"/>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="rprsntApprvYn" value="Y" <c:if test="${rprsntApprvYn eq 'Y'}">checked</c:if>/>
                            <span class="ion-record"></span> 승인
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="rprsntApprvYn" value="N" <c:if test="${rprsntApprvYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미승인
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청사유<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:forEach var="cdList" items="${cdDtlList.APPCTN_RSN_CD}" varStatus="status">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="appctnRsnCd" value="${cdList.cd}" title="신청사유" <c:if test="${rtnDto.appctnRsnCd eq cdList.cd}">checked</c:if>/>
                                <span class="ion-record"></span> ${cdList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">업종<span class="star"> *</span></label>
                    <div class="row">
                        <label class="col-sm-1 control-label">금속분야</label>
                    </div>
                    <div style="padding-left: 300px;">
                        <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                            <c:if test="${fn:contains(cdList.cd,'METAL') && cdList.cd ne 'TEC_GUIDE_METAL'}">
                                <label class="radio-inline c-radio">
                                    <input type="radio" class="cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                    <span class="ion-record"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="row">
                        <label class="col-sm-2 control-label">비금속분야</label>
                    </div>
                    <div style="padding-left: 300px;">
                        <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                            <c:if test="${fn:contains(cdList.cd,'NON') && cdList.cd ne 'TEC_GUIDE_NON'}">
                                <label class="radio-inline c-radio">
                                    <input type="radio" class="notRequired cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                    <span class="ion-record"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="row">
                        <label class="col-sm-2 control-label">기타</label>
                    </div>
                    <div style="padding-left: 300px;">
                        <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                            <c:if test="${!fn:contains(cdList.cd,'NON') && !fn:contains(cdList.cd,'META')}">
                                <label class="radio-inline c-radio">
                                    <input type="radio" class="notRequired cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                    <span class="ion-record"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                        <input type="text" class="form-control input-sm notRequired" name="etcNm" value="${rtnDto.etcNm}" style="width: 322px; margin-top: 10px;" title="업종" disabled/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm appctnTypeCd">
                    <c:forEach var="appctnList" items="${appctnTypeList}" varStatus="status">
                        <input type="hidden" class="apTypeCd" value="${appctnList.appctnTypeCd}">
                    </c:forEach>
                    <label class="col-sm-1 control-label">신청사항<span class="star"> *</span></label>
                    <c:forEach var="appctnCdList" items="${cdDtlList.TEC_GUIDE_APPCTN}" varStatus="status">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" name="appctnTypeCd" value="${appctnCdList.cd}" title="신청사항" <%--<c:if test="${eq appctnCdList.cd}">checked</c:if>--%>/>
                            <span class="ion-checkmark-round"></span>${appctnCdList.cdNm}
                        </label>
                    </c:forEach>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 소개서 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="itrdcFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">개선활동 추진계획서 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="impvmFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            <c:choose>
            <c:when test="${not empty rtnDto}">
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">관리자 메모</label>
                    <div class="col-sm-11">
                        <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" title="관리자메모" placeholder="관리자 메모 입력" maxlength="500">${rtnDto.admMemo}</textarea>
                        <span style="float: right">저장 시간 ${rtnDto.modDtm}.</span>
                    </div>
                </div>
            </fieldset>
            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#techGuidance">사업진행 상세</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#svResult">만족도 결과</a></li>
            </ul>
            <div class="tab-content">
                <!-- 진행 정보 -->
                <div id="episdList" class="tab-pane fade in active">
                    <fieldset>
                        <h6 class="mt0"><em class="ion-play mr-sm"></em> 진행상태</h6>
                    </fieldset>
                    <c:choose>
                        <c:when test="${empty rtnDto.rsumeList}">
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">사전심사결과</label>
                                    <div class="col-sm-11">
                                        <div class="col-sm-2">
                                            <select class="form-control input-sm listRowSizeContainer notRequired" id="bfJdgmtRslt" name="bfreJdgmtRsltCd" title="사전심사결과">
                                                <option value="">선택</option>
                                                <c:forEach var="bfJdgmtRsltList" items="${cdDtlList.BF_JDGMT_RSLT}" varStatus="status">
                                                    <option value="${bfJdgmtRsltList.cd}">${bfJdgmtRsltList.cdNm} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-11" style="padding-left: 30px;">
                                        <textarea class="form-control input-sm notRequired" id="bfreJdgmtRsltCntn" name="bfreJdgmtRsltCntn" title="사전심사결과 의견" placeholder="사전심사결과 의견 입력" maxlength="500"></textarea>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label" style="z-index:0;">방문자</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control input-sm notRequired"  name="vstrNm" id="vstrNm" value="" placeholder="방문자 입력"/>
                                    </div>
                                    <label class="col-sm-1 control-label">면담자</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control input-sm notRequired"  name="ntrvrNm" id="ntrvrNm" value="" placeholder="면담자 입력"/>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">초도방문일</label>
                                    <div class="input-group col-md-2" style="z-index:0;">
                                        <input type="text" class="form-control datetimepicker_strtDt notRequired" name="vstDt" value="" title="초도방문일"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">초도방문결과</label>
                                    <div class="col-sm-11">
                                        <div class="col-sm-2">
                                            <select class="form-control input-sm listRowSizeContainer initVstRsltCd notRequired" id="initVstRsltCd" name="initVstRsltCd" title="초도방문결과">
                                                <option value="">선택</option>
                                                <c:forEach var="bfJdgmtRsltList" items="${cdDtlList.BF_JDGMT_RSLT}" varStatus="status">
                                                    <option value="${bfJdgmtRsltList.cd}" >${bfJdgmtRsltList.cdNm} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6 rsltCntn" style="display: none; padding-left: 30px;">
                                        <textarea class="form-control input-sm notRequired" id="initVstRsltCntn" name="initVstRsltCntn" value="" placeholder="탈락사유 입력" maxlength="500"></textarea>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">초도방문의견</label>
                                    <div class="col-sm-6" style="padding-left: 30px;">
                                        <textarea class="form-control input-sm notRequired" id="initVstOpnnCntn" name="initVstOpnnCntn" placeholder="초도방문의견 입력" maxlength="500"></textarea>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">담당위원</label>
                                    <div class="col-sm-3">
                                        <input type="hidden" class="notRequired" name="cmssrSeq" value="">
                                        <input type="text" class="form-control input-sm notRequired" readonly="readonly" name="cmssrName" value="">
                                    </div>
                                    <div class="col-sm-1">
                                        <button type="button" class="btn btn-sm btn-info btnCmtSearch">위원검색</button>
                                    </div>
                                    <label class="col-sm-1 control-label">지도구분</label>
                                    <div class="col-sm-4">
                                        <select class="form-control input-sm listRowSizeContainer notRequired" name="guideTypeCd" id="guideTypeCd">
                                            <option value="">선택</option>
                                            <c:forEach var="guideTypeCd" items="${cdDtlList.GUIDE_TYPE_CD}" varStatus="status">
                                                <option value="${guideTypeCd.cd}" >${guideTypeCd.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">지도현황</label>
                                    <div class="col-sm-2">
                                        <select class="form-control input-sm listRowSizeContainer notRequired" name="guidePscndCd" id="guidePscndCd">
                                            <option value="">선택</option>
                                            <c:forEach var="guidePscnd" items="${cdDtlList.GUIDE_PSCND}" varStatus="status">
                                                <option value="${guidePscnd.cd}">${guidePscnd.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group col-md-2" style="z-index:0;">
                                        <input type="text" class="form-control datetimepicker_strtDt notRequired"  name="guidePscndDt" value="" title="초도방문일" />
                                        <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">지도착수일</label>
                                    <div class="col-sm-5">
                                        <div class="input-group" style="z-index:0;width: 220px;">
                                            <input type="text" class="form-control datetimepicker_strtDt notRequired" name="guideBgnDt" value="" title="지도착수일" />
                                            <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                        </div>
                                    </div>
                                    <label class="col-sm-1 control-label">킥오프일</label>
                                    <div class="col-sm-5">
                                        <div class="input-group" style="z-index:0;width: 220px;">
                                            <input type="text" class="form-control datetimepicker_strtDt notRequired"  name="guideKickfDt" value="" title="킥오프일" />
                                            <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">연기/취소 사유</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control input-sm notRequired" id="xtnsnCnclRsn" name="xtnsnCnclRsn" placeholder="연기/취소 사유" maxlength="500"></textarea>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">공정불량개선율</label>
                                    <div class="col-sm-2">
                                        <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmBfreRate" value="" title="불량개션이전율"/>
                                    </div><label class="inline-form control-label">ppm</label>
                                    <div class="col-sm-2">
                                        <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmAftrRate" value="" title="불량개션이후율"/>
                                    </div><label class="inline-form control-label">ppm</label>
                                    <div class="col-sm-2">
                                        <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmRate" value="" title="불량개션율"/>
                                    </div><label class="inline-form control-label">%</label>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">초도방문자료 첨부파일</label>
                                    <div class="col-sm-10 col-md-11">
                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                        <div class="dropzone attachFile notRequired" data-file-field-nm="initVstFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                                            <div class="dz-default dz-message">
                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                            </div>
                                        </div>
                                        <p class="text-bold mt">
                                            ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                        </p>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">킥오프자료 첨부파일</label>
                                    <div class="col-sm-10 col-md-11">
                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                        <div class="dropzone attachFile notRequired" data-file-field-nm="kickfFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                                            <div class="dz-default dz-message">
                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                            </div>
                                        </div>
                                        <p class="text-bold mt">
                                            ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                        </p>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">랩업자료 첨부파일</label>
                                    <div class="col-sm-10 col-md-11">
                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                        <div class="dropzone attachFile notRequired" data-file-field-nm="lvlupFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                                            <div class="dz-default dz-message">
                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                            </div>
                                        </div>
                                        <p class="text-bold mt">
                                            ※ 파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                        </p>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">기타사업자료 첨부파일</label>
                                    <div class="col-sm-10 col-md-11">
                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                        <div class="dropzone attachFile notRequired" data-file-field-nm="etcFileSeq" data-file-extn="${fileExtns}" data-max-file-size="104857600" data-max-file-cnt="1" data-title="첨부파일">
                                            <div class="dz-default dz-message">
                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                            </div>
                                        </div>
                                        <p class="text-bold mt">
                                            ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${104857600 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                        </p>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">만족도조사<span class="star text-danger"> *</span></label>
                                    <div class="col-sm-11">
                                        <button type="button" class="btn btn-inverse btn-sm srvSearch">
                                            설문 검색
                                        </button>
                                        <table class="table table-hover table-striped">
                                            <thead>
                                            <tr>
                                                <th class="text-center">설문유형</th>
                                                <th class="text-center">제목</th>
                                                <th class="text-center">설문기간</th>
                                                <th class="text-center"></th>
                                            </tr>
                                            </thead>
                                            <!-- 리스트 목록 결과 -->
                                            <tbody id="listContainer3">
                                            <tr>
                                                <td class="text-center"></td>
                                                <td class="text-center"></td>
                                                <td class="text-center">
                                                    <div class="input-group form-date-group mr-sm">
                                                        <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="srvStrtDtm" id="srvStrtDtm" title="설문시작일시" readonly="readonly"/>
                                                        <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                        <span class="input-group-addon bg-white b0">~</span>
                                                        <input type="text" class="form-control input-sm datetimepicker_endDt notRequired" name="srvEndDtm" id="srvEndDtm" title="설문종료일시" readonly="readonly"/>
                                                        <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                    </div>

                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-inverse btn-sm srvReset">
                                                        설문 초기화
                                                    </button>
                                                </td>
                                            </tr>
                                                <%--<tr data-total-count="0">
                                                    <td colspan="4" class="text-center">
                                                        검색결과가 없습니다.<br>
                                                        (등록된 데이터가 없습니다.)
                                                    </td>
                                                </tr>--%>
                                            <input type="hidden" class="notRequired" name="srvSeq" id="srvSeq" disabled="true">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </fieldset>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="rsumeList" items="${rtnDto.rsumeList}" varStatus="status">
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사전심사결과</label>
                                        <div class="col-sm-11">
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm listRowSizeContainer notRequired" id="bfJdgmtRslt" name="bfreJdgmtRsltCd" title="사전심사결과">
                                                    <option value="">선택</option>
                                                    <c:forEach var="bfJdgmtRsltList" items="${cdDtlList.BF_JDGMT_RSLT}" varStatus="status">
                                                        <option value="${bfJdgmtRsltList.cd}" <c:if test="${rsumeList.bfreJdgmtRsltCd eq bfJdgmtRsltList.cd}">selected</c:if>>${bfJdgmtRsltList.cdNm} </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <label class="col-sm-1 control-label"></label>
                                        <div class="col-sm-11" style="padding-left: 30px;">
                                                    <textarea class="form-control input-sm notRequired" id="bfreJdgmtRsltCntn" name="bfreJdgmtRsltCntn" title="사전심사결과 의견" placeholder="사전심사결과 의견 입력" maxlength="500">
                                                            ${rsumeList.bfreJdgmtRsltCntn}
                                                    </textarea>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label" style="z-index:0;">방문자</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control input-sm notRequired"  name="vstrNm" id="vstrNm" value="${rsumeList.vstrNm}" placeholder="방문자 입력"/>
                                        </div>
                                        <label class="col-sm-1 control-label">면담자</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control input-sm notRequired"  name="ntrvrNm" id="ntrvrNm" value="${rsumeList.ntrvrNm}" placeholder="면담자 입력"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">초도방문일</label>
                                        <div class="input-group col-md-2" style="z-index:0;">
                                            <input type="text" class="form-control datetimepicker_strtDt notRequired" name="vstDt" value="${kl:convertDate(rsumeList.vstDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="초도방문일" value="${rsumeList.vstDt}"/>
                                            <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">초도방문결과</label>
                                        <div class="col-sm-11">
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm listRowSizeContainer initVstRsltCd notRequired" id="initVstRsltCd" name="initVstRsltCd" title="초도방문결과">
                                                    <option value="">선택</option>
                                                    <c:forEach var="bfJdgmtRsltList" items="${cdDtlList.BF_JDGMT_RSLT}" varStatus="status">
                                                        <option value="${bfJdgmtRsltList.cd}" <c:if test="${rsumeList.initVstRsltCd eq bfJdgmtRsltList.cd}">selected</c:if>>${bfJdgmtRsltList.cdNm} </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <label class="col-sm-1 control-label"></label>
                                        <div class="col-sm-6 rsltCntn" style="display: none; padding-left: 30px;">
                                            <textarea class="form-control input-sm notRequired" id="initVstRsltCntn" name="initVstRsltCntn" placeholder="탈락사유 입력" maxlength="500">${rsumeList.initVstRsltCntn}</textarea>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">초도방문의견</label>
                                        <div class="col-sm-6" style="padding-left: 30px;">
                                            <textarea class="form-control input-sm notRequired" id="initVstOpnnCntn" name="initVstOpnnCntn" placeholder="초도방문의견 입력" maxlength="500">${rsumeList.initVstOpnnCntn}</textarea>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">담당위원</label>
                                        <div class="col-sm-3">
                                            <input type="hidden" class="notRequired" name="cmssrSeq" value="${rsumeList.cmssrSeq}">
                                            <input type="text" class="form-control input-sm notRequired" readonly="readonly" name="cmssrName" value="${rsumeList.cmssrName}">
                                        </div>
                                        <div class="col-sm-1">
                                            <button type="button" class="btn btn-sm btn-info btnCmtSearch">위원검색</button>
                                        </div>
                                        <label class="col-sm-1 control-label">지도구분</label>
                                        <div class="col-sm-4">
                                            <select class="form-control input-sm listRowSizeContainer notRequired" name="guideTypeCd" id="guideTypeCd">
                                                <option value="">선택</option>
                                                <c:forEach var="guideTypeCd" items="${cdDtlList.GUIDE_TYPE_CD}" varStatus="status">
                                                    <option value="${guideTypeCd.cd}" <c:if test="${rsumeList.guideTypeCd eq guideTypeCd.cd }">selected</c:if>>${guideTypeCd.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지도현황</label>
                                        <div class="col-sm-2">
                                            <select class="form-control input-sm listRowSizeContainer notRequired" name="guidePscndCd" id="guidePscndCd">
                                                <option value="">선택</option>
                                                <c:forEach var="guidePscnd" items="${cdDtlList.GUIDE_PSCND}" varStatus="status">
                                                    <option value="${guidePscnd.cd}" <c:if test="${rsumeList.guidePscndCd}">selected</c:if>>${guidePscnd.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="input-group col-md-2" style="z-index:0;">
                                            <input type="text" class="form-control datetimepicker_strtDt notRequired"  name="guidePscndDt" value="${kl:convertDate(rsumeList.guidePscndDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="초도방문일" />
                                            <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지도착수일</label>
                                        <div class="col-sm-5">
                                            <div class="input-group" style="z-index:0;width: 220px;">
                                                <input type="text" class="form-control datetimepicker_strtDt notRequired" name="guideBgnDt" value="${kl:convertDate(rsumeList.guideBgnDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="지도착수일" />
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                            </div>
                                        </div>
                                        <label class="col-sm-1 control-label">킥오프일</label>
                                        <div class="col-sm-5">
                                            <div class="input-group" style="z-index:0;width: 220px;">
                                                <input type="text" class="form-control datetimepicker_strtDt notRequired"  name="guideKickfDt" value="${kl:convertDate(rsumeList.guideKickfDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" title="킥오프일" />
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">연기/취소 사유</label>
                                        <div class="col-sm-5">
                                            <textarea class="form-control input-sm notRequired" id="xtnsnCnclRsn" name="xtnsnCnclRsn" placeholder="연기/취소 사유" maxlength="500">${rsumeList.xtnsnCnclRsn}</textarea>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">공정불량개선율</label>
                                        <div class="col-sm-2">
                                            <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmBfreRate" value="${rsumeList.fltyImpvmBfreRate}" title="불량개션이전율"/>
                                        </div><label class="col-sm-1 control-label">ppm</label>
                                        <div class="col-sm-2">
                                            <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmAftrRate" value="${rsumeList.fltyImpvmAftrRate}" title="불량개션이후율"/>
                                        </div><label class="col-sm-1 control-label">ppm</label>
                                        <div class="col-sm-2">
                                            <input type="number" class="form-control input-sm notRequired"  name="fltyImpvmRate" value="${rsumeList.fltyImpvmRate}" title="불량개션율"/>
                                        </div><label class="col-sm-1 control-label">%</label>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">초도방문자료 첨부파일</label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="initVstFileSeq" data-file-extn="${fileExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="첨부파일">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">킥오프자료 첨부파일</label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="kickfFileSeq" data-file-extn="${fileExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="첨부파일">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">랩업자료 첨부파일</label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="lvlupFileSeq" data-file-extn="${fileExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="첨부파일">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">기타사업자료 첨부파일</label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="etcFileSeq" data-file-extn="${fileExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="첨부파일">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※  파일 확장자(${fileExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">만족도조사<span class="star text-danger"> *</span></label>
                                        <div class="col-sm-11">
                                            <button type="button" class="btn btn-inverse btn-sm srvSearch">
                                                설문 검색
                                            </button>
                                            <table class="table table-hover table-striped">
                                                <thead>
                                                <tr>
                                                    <th class="text-center">설문유형</th>
                                                    <th class="text-center">제목</th>
                                                    <th class="text-center">설문기간</th>
                                                    <th class="text-center"></th>
                                                </tr>
                                                </thead>
                                                <!-- 리스트 목록 결과 -->
                                                <tbody id="listContainer3">
                                                <tr>
                                                    <td class="text-center">${rsumeList.srvNm}</td>
                                                    <td class="text-center">${rsumeList.typeNm}</td>
                                                    <td class="text-center">
                                                        <div class="input-group form-date-group mr-sm">
                                                            <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="srvStrtDtm" id="srvStrtDtm" value="${ kl:convertDate(rsumeList.srvStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문시작일시" readonly="readonly"/>
                                                            <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                            <span class="input-group-addon bg-white b0">~</span>
                                                            <input type="text" class="form-control input-sm datetimepicker_endDt notRequired" name="srvEndDtm" id="srvEndDtm" value="${ kl:convertDate(rsumeList.srvEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문종료일시" readonly="readonly"/>
                                                            <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                        </div>

                                                    </td>
                                                    <td>
                                                        <button type="button" class="btn btn-inverse btn-sm srvReset">
                                                            설문 초기화
                                                        </button>
                                                    </td>
                                                </tr>
                                                    <%--<tr data-total-count="0">
                                                        <td colspan="4" class="text-center">
                                                            검색결과가 없습니다.<br>
                                                            (등록된 데이터가 없습니다.)
                                                        </td>
                                                    </tr>--%>
                                                <input type="hidden" class="notRequired" name="srvSeq" id="srvSeq" value="${rsumeList.srvSeq}">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </fieldset>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <h5>등록/수정이력</h5>
                    <table class="table">
                        <colgroup>
                            <col style="width:10%;">
                            <col style="width:40%;">
                            <col style="width:10%;">
                            <col style="width:40%;">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">최초 등록자 </th>
                            <td>${rtnDto.regName}(${rtnDto.regId})</td>
                            <th scope="row" class="bg-gray-lighter">최초 등록일시</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">최종 수정자</th>
                            <td>${rtnDto.modName}(${rtnDto.modId})</td>
                            <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                            <td>${ kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- 만족도 결과 -->
                <div id="svResult" class="tab-pane fade">
                </div>
            </div>
    </div>
    </c:when>
    </c:choose>
    <div class="clearfix">
        <div class="pull-left">
            <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
        </div>
        <div class="pull-right">
            <c:choose>
                <c:when test="${not empty rtnDto}">
                    <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                    <button type="submit" class="btn btn-sm btn-success">수정</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-sm btn-success">등록</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    </form>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/mngwserc/sv/sva/SVASurveySrchLayer.jsp">
    <jsp:param name="typeCd" value="CON" />
</jsp:include>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpd/MPDCmtSrchLayer.jsp"></jsp:include>

<!--설문검색-->
