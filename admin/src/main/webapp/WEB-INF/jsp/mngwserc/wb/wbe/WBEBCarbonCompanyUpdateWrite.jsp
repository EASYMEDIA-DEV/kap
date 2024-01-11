
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnData" value="${rtnData}" />

<c:set var="rtnDtl" value="${rtnData.rsumeDtlList}" />
<c:set var="rtnSpprt" value="${rtnData.spprtList}" />
<c:set var="rtnMem" value="${rtnData.memList[0]}" />
<c:set var="rtnCmssr" value="${rtnData.memList[1]}" />
<c:set var="rtnCompany" value="${rtnData.companyDtl}" />
<c:set var="rtnSQ" value="${rtnCompany.dtlList}" />
<c:set var="rtnPbsn" value="${rtnData.pbsnDtlList}" />
<c:set var="rtnFile" value="${rtnData.fileDtlList}" />

<c:set var="maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbe/WBEBCarbonCompanyUpdateWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청부품사 상세/수정</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >

            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />

            <!-- 이력 현재 페이징 번호 -->
            <input type="hidden" id="trnsfPageIndex" name="trnsfPageIndex" value="${ rtnData.pageIndex }" />
            <!-- 이력 페이징 버튼 사이즈 -->
            <input type="hidden" id="trnsfPageRowSize" name="trnsfPageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="trnsfListRowSize" name="trnsfListRowSize" value="${ rtnData.listRowSize }" />


            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="appctnSeq" name="appctnSeq" value="${rtnData.appctnSeq}" />
            <input type="hidden" id="detailsKey" name="detailsKey" value="${rtnData.appctnSeq}" />
            <input type="hidden" id="rsumeSeq" name="rsumeSeq" value="${rtnDtl[maxRsumeOrd-1].rsumeSeq}" />
            <input type="hidden" id="maxRsumeOrd" name="maxRsumeOrd" value="${rtnData.maxRsumeOrd}" />
            <input type="hidden" id="episdSeq" name="episdSeq" value="${rtnData.episdSeq}"/>

            <%--<h6 class="mt0">회차 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="episdYear" name="year" title="연도">
                                <option value="">선택</option>
                                <c:forEach var="rtnYear" items="${rtnYear.list}" varStatus="status">
                                    <option value="${rtnYear.year}" <c:if test="${rtnData.year eq rtnYear.year }">selected</c:if>>${rtnYear.year}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="episd" name="episd" title="회차">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classCityTypeList.ROUND_CD}" varStatus="status">
                                    <option value="${cdList.cdNm}" <c:if test="${rtnData.episd eq cdList.cdNm}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>--%>
            <div id="appctnPdfArea1">
                <br>
                <fieldset>
                    <div class="form-group text-sm">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <h6 class="mt0">신청자 정보</h6>
                        </div>
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-5">
                            <div class="pull-right">
                                <p class="control-static"><span class="star"> *</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                        </div>
                    </div>
                </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <input type="hidden" id="memSeq" class="notRequired" name="memSeq" value="${rtnMem.memSeq}" />
                            <input type="text" id="mem" class="form-control" value="${rtnMem.name}(${rtnMem.id})" title="신청자" disabled/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <p class="form-control-static" id="email">${rtnMem.email}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm" id="deptCd" name="memList[0].deptCd" title="부서">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD020')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnMem.deptCd eq cdList.cd }">selected</c:if> >${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control" id="deptDtlNm" name="memList[0].deptDtlNm" value="${rtnMem.deptDtlNm}" title="부서명" maxlength="50"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm" id="pstnCd" name="memList[0].pstnCd" title="직급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnMem.pstnCd eq cdList.cd }">selected</c:if> >${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px;">
                            <input type="text" style="display: none;" class="form-control notRequired" id="pstnNm" name="memList[0].pstnCdNm" value="${rtnMem.pstnCdNm}" title="직급명" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static" id="hpNo">${rtnMem.hpNo}</p>
                    </div>

                    <label class="col-sm-1 control-label">일반 전화번호</label>
                    <div class="col-sm-1">
                        <input type="text" id="telNo" class="form-control notRequired" name="memList[0].telNo" value="${rtnMem.telNo}" title="전화번호" maxlength="50"/>
                    </div>
                </div>
            </fieldset>


                <br>
                <fieldset>
                    <div class="form-group text-sm">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <h6 class="mt0">부품사 정보</h6>
                        </div>
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-5">
                            <div class="pull-right">
                                <p class="control-static"><span class="star"> *</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                        </div>
                    </div>
                </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="hidden" id="cmpnNm" value="${rtnCompany.cmpnNm}"/>
                        <p class="form-control-static">${rtnCompany.cmpnNm}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <select class="form-control input-sm" id="ctgryCd" name="companyDtl.ctgryCd" title="구분">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY01001')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnCompany.ctgryCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:if>
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY01002')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnCompany.ctgryCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="befeCtgryCd" class="notRequired" value="${rtnCompany.ctgryCd}"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <select class="form-control input-sm" id="sizeCd" name="companyDtl.sizeCd" title="규모">
                                <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY020')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnCompany.sizeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <p class="form-control-static">${rtnCompany.rprsntNm}</p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="companyDtl.stbsmDt"
                                       value="${kl:convertDate(rtnCompany.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                       title="설립일자" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <input type="text" class="form-control input-sm" id="compTel" name="companyDtl.telNo" value="${rtnCompany.telNo}" title="전화번호" maxlength="50" placeholder="전화번호 입력"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="hidden" id="bsnmNoPut1" name="appctnBsnmNo" title="사업자등록번호" value="${rtnCompany.bsnmNo}" />
                        <input type="hidden" id="bsnmNoPut2" name="companyDtl.bsnmNo" title="사업자등록번호" value="${rtnCompany.bsnmNo}" />
                        <input type="hidden" id="bsnmNo" class="notRequired" value="${rtnCompany.bsnmNo}" />
                        <p class="form-control-static" id="bsnm">${fn:substring(rtnCompany.bsnmNo,0,3) } - ${fn:substring(rtnCompany.bsnmNo,3,5) } - ${fn:substring(rtnCompany.bsnmNo,5, 10) }</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipCode" name="companyDtl.zipcode" value="${rtnCompany.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                            <input type="text" class="form-control input-sm" id="bscAddr" name="companyDtl.bscAddr" value="${rtnCompany.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="companyDtl.dtlAddr" value="${rtnCompany.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm numberChk notRequired" id="slsPmt" name="companyDtl.slsPmt" value="${rtnCompany.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                        &nbsp;억원&nbsp;
                        <select class="form-control input-sm" id="slsYear" name="companyDtl.slsYear" title="매출액 연도">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnCompany.slsYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        &nbsp;년&nbsp;
                    </div>

                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm numberChk notRequired" id="mpleCnt" name="companyDtl.mpleCnt" value="${rtnCompany.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="companyDtl.mjrPrdct1" value="${rtnCompany.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="companyDtl.mjrPrdct2" value="${rtnCompany.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="companyDtl.mjrPrdct3" value="${rtnCompany.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
                    </div>
                </div>
            </fieldset>

            <fieldset class="companyCate1">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">품질5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired starType" id="qlty5starCd" name="companyDtl.qlty5starCd" title="품질">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnCompany.qlty5starCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="qlty5starYear" name="companyDtl.qlty5starYear" title="품질연도">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnCompany.qlty5starYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>
            <fieldset class="companyCate1">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">납입5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired starType" id="pay5starCd" name="companyDtl.pay5starCd" title="납입">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnCompany.pay5starCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="pay5starYear" name="companyDtl.pay5starYear" title="납입연도">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnCompany.pay5starYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>
            <fieldset class="companyCate1">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">기술5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired starType" id="tchlg5starCd" name="companyDtl.tchlg5starCd" title="기술">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnCompany.tchlg5starCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="tchlg5starYear" name="companyDtl.tchlg5starYear" title="기술연도">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnCompany.tchlg5starYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>

            <fieldset class="companyCate2" style="display: none">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">SQ 정보</label>
                    <div class="col-sm-11">
                        <input type="text" id="nm0" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[0].nm" value="${rtnSQ[0].nm}" maxlength="50" title="SQ업종" placeholder="SQ업종"/>
                        <input type="text" id="score0" class="form-control input-sm numberChk notRequired SQ" name="companyDtl.dtlList[0].score" value="${rtnSQ[0].score}" maxlength="50" title="SQ점수" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" id="year0" name="companyDtl.dtlList[0].year">
                            <option value="" <c:if test="${ empty rtnSQ[0].year}">selected</c:if>>선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnSQ[0].year eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm0" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[0].crtfnCmpnNm" value="${rtnSQ[0].crtfnCmpnNm}" maxlength="50" title="SQ인증주관사" placeholder="SQ인중주관사"/>
                        <br><br>
                        <input type="text" id="nm1" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[1].nm" value="${rtnSQ[1].nm}" maxlength="50" title="SQ업종" placeholder="SQ업종"/>
                        <input type="text" id="score1" class="form-control input-sm numberChk notRequired SQ" name="companyDtl.dtlList[1].score" value="${rtnSQ[1].score}" maxlength="50" title="SQ점수" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" id="year1" name="companyDtl.dtlList[1].year">
                            <option value="" <c:if test="${ empty rtnSQ[1].year}">selected</c:if> >선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${ rtnSQ[1].year eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm1" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[1].crtfnCmpnNm" value="${rtnSQ[1].crtfnCmpnNm}" maxlength="50" title="SQ인증주관사" placeholder="SQ인중주관사"/>
                        <br><br>
                        <input type="text" id="nm2" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[2].nm" value="${rtnSQ[2].nm}" maxlength="50" title="SQ업종" placeholder="SQ업종"/>
                        <input type="text" id="score2" class="form-control input-sm numberChk notRequired SQ" name="companyDtl.dtlList[2].score" value="${rtnSQ[2].score}" maxlength="50" title="SQ점수" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" id="year2" name="companyDtl.dtlList[2].year">
                            <option value="" <c:if test="${ empty rtnSQ[2].year}">selected</c:if> >선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnSQ[2].year eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm2" class="form-control input-sm notRequired SQ" name="companyDtl.dtlList[2].crtfnCmpnNm" value="${rtnSQ[2].crtfnCmpnNm}" maxlength="50" title="SQ인증주관사" placeholder="SQ인중주관사"/>
                    </div>
                </div>
            </fieldset>
            </div>
            <h6 class="mt0">신청자 변경 이력</h6>

            <fieldset>
                <div class="col-sm-12 p0 mt">
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
                            <tbody id="trnfsListContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="trnfsPagingContainer"/>
                    </div>
                </div>
            </fieldset>

            <div id="appctnPdfArea2">

            <h6 class="mt0">담당위원 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">담당위원명</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <p class="form-control-static">${rtnCmssr.name}</p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <p class="form-control-static">${rtnCmssr.email}</p>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <p class="form-control-static">${rtnCmssr.hpNo}</p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">업체명</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <p class="form-control-static">${rtnCmssr.name}</p>
                        </div>
                    </div>
                </div>
            </fieldset>



            <h6 class="mt0">사업신청 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">종된사업장번호</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm notRequired" name="sbrdnBsnmNo" value="${rtnData.sbrdnBsnmNo}" title="종된사업장번호" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>


            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구축사업장<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm" id="searchPostCode2" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipcode2" name="pbsnDtlList[0].pbsnZipcode" value="${rtnPbsn[0].pbsnZipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                            <input type="text" class="form-control input-sm" id="bscAddr2" name="pbsnDtlList[0].pbsnBscAddr" value="${rtnPbsn[0].pbsnBscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr2" name="pbsnDtlList[0].pbsnDtlAddr" value="${rtnPbsn[0].pbsnDtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>

            <h6 class="mt0">선급금 해당 여부</h6>


            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부 <span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" class="pmndvPmtYn" name="pmndvPmtYn" value="Y" <c:if test="${rtnData.pmndvPmtYn eq 'Y'}">checked</c:if> title="노출여부"/>
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" class="pmndvPmtYn" name="pmndvPmtYn" value="N" <c:if test="${rtnData.pmndvPmtYn eq 'N'}">checked</c:if> title="노출여부"/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>

            <div class="container-fluid">
                <div class="panel-group" id="aaa1" role="tablist">
                    <!-- 선급금지급 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#aaa1" href="#ab1" aria-constrols="ab1" id="pmndv" >선급금지급</a>
                            <input type="hidden" class="notRequired" name="spprtList[0].appctnSpprtSeq" value="${rtnSpprt[0].appctnSpprtSeq}" />
                            <input type="hidden" class="notRequired" name="spprtList[0].giveType" value="${rtnSpprt[0].giveType}" />
                        </div>
                        <div id="ab1" class="panel-collapse collapse <c:if test="${rtnSpprt[0].mngSttsNm eq '미확인'}">in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">접수일<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="accsDt1" name="spprtList[0].accsDt"
                                                           value="${kl:convertDate(rtnSpprt[0].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="접수일자" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>  />
                                                    <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">은행명<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control koreanChk notRequired" id="bankNm1" name="spprtList[0].bankNm" value="${rtnSpprt[0].bankNm}" title="은행명"  <c:if test="${empty rtnSpprt[0]}">disabled</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">계좌번호<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control numberChk notRequired" id="acntNo1" name="spprtList[0].acntNo" value="${rtnSpprt[0].acntNo}" title="계좌번호" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">예금주<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control notRequired" id="dpsitNm1" name="spprtList[0].dpsitNm" value="${rtnSpprt[0].dpsitNm}" title="예금주" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <p class="form-control-static"><c:if test="${empty rtnSpprt[0]}">접수전</c:if> ${rtnSpprt[0].appctnSttsNm}</p>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <p class="form-control-static">${rtnSpprt[0].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지급일<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="giveDt1" name="spprtList[0].giveDt"
                                                           value="${kl:convertDate(rtnSpprt[0].giveDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="지급일자" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">지급차수<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <select class="form-control input-sm notRequired" id="giveSeq1" name="spprtList[0].giveSeq" title="지급차수" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>>
                                                    <option value="">선택</option>
                                                    <c:forEach var="cdList" items="${rtnData.giveOrdList}" varStatus="status">
                                                        <option value="${status.index +1}" <c:if test="${rtnSpprt[0].giveSeq eq status.index +1 }">selected</c:if> >${status.index +1}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                            <c:choose>
                                                <c:when test="${rtnSpprt[0].mngSttsNm eq '적합'}">
                                                    <p class="form-control-static">적합</p>
                                                    <input type="hidden" name="spprtList[0].mngSttsCd" value="${rtnSpprt[0].mngSttsCd}">
                                                </c:when>
                                                <c:when test="${rtnSpprt[0].mngSttsNm eq '부적합'}">
                                                    <p class="form-control-static">부적합</p>
                                                    <input type="hidden" name="spprtList[0].mngSttsCd" value="${rtnSpprt[0].mngSttsCd}">
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control input-sm spprtMngSttsCd notRequired" id="spprtMngSttsCd1" name="spprtList[0].mngSttsCd" <c:if test="${empty rtnSpprt[0]}">disabled</c:if>>
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE03001_02')}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnSpprt[0].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                                <input type="hidden" class="notRequired" name="spprtList[0].appctnSttsCd" value="${rtnSpprt[0].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시<span class="star"> *</span></label>
                                        <div class="col-sm-10 form-inline" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <p class="form-control-static">${rtnSpprt[0].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="spprtAppctnFileSeq" value="${rtnSpprt[0].spprtAppctnFileSeq}" />
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">협약서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="agrmtFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="협약서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" id="agrmtFileSeq" name="agrmtFileSeq" value="${rtnSpprt[0].agrmtFileSeq}" />
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">보증보험증<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="grnteInsrncFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="보증보험증">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" id="grnteInsrncFileSeq" name="grnteInsrncFileSeq" value="${rtnSpprt[0].grnteInsrncFileSeq}" />

                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container-fluid">
                <div class="panel-group" id="acc" role="tablist">
                    <!-- 지원금지급 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#acc" data-target="#accEx7" aria-constrols="addEx7">
                                지원금지급
                            </a>
                            <input type="hidden" class="notRequired" name="spprtList[1].appctnSpprtSeq" value="${rtnSpprt[1].appctnSpprtSeq}" />
                            <input type="hidden" class="notRequired" name="spprtList[1].giveType" value="${rtnSpprt[1].giveType}" />
                        </div>
                        <div id="accEx7" class="panel-collapse collapse <c:if test="${rtnSpprt[1].mngSttsNm eq '미확인'}">in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">접수일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="accsDt2" name="spprtList[1].accsDt"
                                                           value="${kl:convertDate(rtnSpprt[1].accsDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="접수일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${empty rtnSpprt[1]}">disabled</c:if> />
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">은행명</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control input-sm koreanChk notRequired" id="bankNm2" name="spprtList[1].bankNm" value="${rtnSpprt[1].bankNm}" title="은행명" maxlength="50" placeholder="은행명 입력" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">계좌번호</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control input-sm numberChk notRequired" id="acntNo2" name="spprtList[1].acntNo" value="${rtnSpprt[1].acntNo}" title="계좌번호" maxlength="50" placeholder="계좌번호 입력" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">예금주</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="dpsitNm2" name="spprtList[1].dpsitNm" value="${rtnSpprt[1].dpsitNm}" title="예금주" maxlength="50" placeholder="예금주 입력" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnSpprt[1]}">접수전</c:if>${rtnSpprt[1].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnSpprt[1].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지급일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="giveDt2" name="spprtList[1].giveDt"
                                                           value="${kl:convertDate(rtnSpprt[1].giveDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="지급일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">지급차수<span class="star"> *</span></label>
                                        <div class="col-sm-5" style="margin-left: -15px">
                                            <div class="col-sm-3">
                                                <select class="form-control input-sm notRequired" id="giveSeq2" name="spprtList[1].giveSeq" title="지급차수" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>>
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${rtnData.giveOrdList}" varStatus="status">
                                                            <option value="${status.index +1 }" <c:if test="${rtnSpprt[0].giveSeq eq status.index+1 }">selected</c:if> >${status.index+1}</option>
                                                        </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                            <c:choose>
                                                <c:when test="${rtnSpprt[0].mngSttsNm eq '적합'}">
                                                    <p class="form-control-static">적합</p>
                                                    <input type="hidden" name="spprtList[1].mngSttsCd" value="${rtnSpprt[1].mngSttsCd}">
                                                </c:when>
                                                <c:when test="${rtnSpprt[0].mngSttsNm eq '부적합'}">
                                                    <p class="form-control-static">부적합</p>
                                                    <input type="hidden" name="spprtList[1].mngSttsCd" value="${rtnSpprt[1].mngSttsCd}">
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control input-sm spprtMngSttsCd notRequired" id="spprtMngSttsCd2" name="spprtList[1].mngSttsCd" <c:if test="${empty rtnSpprt[1]}">disabled</c:if>>
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE03002_02')}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnSpprt[1].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                                <input type="hidden" class="notRequired" name="spprtList[1].appctnSttsCd"value="${rtnSpprt[1].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnSpprt[1].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq1" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="spprtAppctnFileSeq1" value="${rtnSpprt[1].spprtAppctnFileSeq}" />
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">거래명세서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="blingFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="거래명세서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" id="blingFileSeq" name="blingFileSeq" value="${rtnSpprt[1].blingFileSeq}" />
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">매출전표<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="slsFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="매출전표">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" id="slsFileSeq" name="slsFileSeq" value="${rtnSpprt[1].slsFileSeq}" />
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">검수확인서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="insptChkFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="검수확인서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" id="insptChkFileSeq" name="insptChkFileSeq" value="${rtnSpprt[1].insptChkFileSeq}" />
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자메모</label>
                                        <div class="col-sm-5">
                                            <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" value="${rtnData.admMemo}" title="관리자메모"  placeholder="관리자 메모를 입력해주세요." maxlength="500">${rtnData.admMemo}</textarea>
                                            <p class="form-control-static">저장 시간 : ${rtnData.modDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pull-right">
                <button type="button" class="btn btn-sm btn-default" id="appctnPdfDownload">신청정보 다운로드</button>
            </div>
        </div>

            <h6 class="mt0">사업진행 상세</h6>
            <hr>
            <h6 class="mt0"><em class="ion-play mr-sm"></em>전체 진행상태 : ${rtnDtl[maxRsumeOrd-1].rsumeSttsNm } (${rtnDtl[maxRsumeOrd-1].mngSttsNm }) </h6>
            <br>

            <div class="container-fluid">
                <div class="panel-group" id="accParent" role="tablist">

                    <!-- 신청 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" href="#accEx1" aria-constrols="addEx1">
                                신청
                            </a>
                        </div>
                        <div id="accEx1" class="panel-collapse collapse<c:if test="${rtnDtl[0].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 1}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[0]}">접수전</c:if>${rtnDtl[0].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[0].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">점검위원</label>
                                        <div class="col-sm-10 form-inline">
                                            <input type="text" id="picName" class="form-control notRequired" value="${rtnCmssr.name}" maxlength="50" title="점검위원" disabled/>
                                            <input type="hidden" class="notRequired" id="picCmssrSeq" name="picCmssrSeq" title="점검위원" value="${rtnPbsn[0].chkCmssrSeq}" />
                                            <input type="hidden" class="notRequired" id="chkCmssrSeq" name="pbsnDtlList[0].chkCmssrSeq" title="점검위원" value="${rtnPbsn[0].chkCmssrSeq}" />
                                            <button type="button" class="btn btn-sm btn-info btnCmtSearch" <c:if test="${rtnData.maxRsumeOrd ne 1}">disabled</c:if> <c:if test="${rtnDtl[0].mngSttsNm eq '미선정' || rtnDtl[0].mngSttsNm eq '선정'}">disabled</c:if>>위원검색</button>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업비</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control notRequired" id="bsnPmt" name="pbsnDtlList[0].bsnPmt" value="${rtnPbsn[0].bsnPmt}" title="사업비" maxlength="50" <c:if test="${rtnData.maxRsumeOrd ne 1}">disabled</c:if> <c:if test="${rtnDtl[0].mngSttsNm eq '미선정' || rtnDtl[0].mngSttsNm eq '선정'}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 상태값</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                                <c:choose>
                                                    <c:when test="${rtnDtl[0].mngSttsNm eq '미선정'}">
                                                        <p class="form-control-static">미선정</p>
                                                        <input type="hidden" name="rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                                                        <input type="hidden" id="mngSttsCd1" name="rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                                                    </c:when>
                                                    <c:when test="${rtnDtl[0].mngSttsNm eq '선정'}">
                                                        <p class="form-control-static">선정</p>
                                                        <input type="hidden" name="rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                                                        <input type="hidden" id="mngSttsCd1" name="rsumeDtlList[0].mngSttsCd" value="${rtnDtl[0].mngSttsCd}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm mngSttsCd notRequired" id="mngSttsCd1" name="rsumeDtlList[0].mngSttsCd" <c:if test="${rtnData.maxRsumeOrd ne 1}">disabled</c:if>>
                                                            <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01001_02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnDtl[0].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <input type="hidden" class="notRequired" value="${rtnDtl[0].appctnSttsCd}" name="rsumeDtlList[0].appctnSttsCd" />
                                            </div>
                                            <div class="col-sm-10" style="margin-left: -15px">
                                                <input type="text" class="form-control rtrnRsnCntn notRequired" id="rtrnRsnCntn1" name="rsumeDtlList[0].rtrnRsnCntn" value="${rtnDtl[0].rtrnRsnCntn}" title="반려사유" placeholder="사유 입력" maxlength="50" disabled/>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[0].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq0" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq0" value="${rtnFile[0].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[0].fileCd" value="ATTACH_FILE_TYPE01" />
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <!-- 사업계획 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx2" aria-constrols="addEx2">
                                사업계획
                            </a>
                        </div>
                        <div id="accEx2" class="panel-collapse collapse<c:if test="${rtnDtl[1].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 2}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">
                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업계획</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="bsnPlanDt" name="pbsnDtlList[1].bsnPlanDt"
                                                           value="${kl:convertDate(rtnPbsn[1].bsnPlanDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="점검일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 2}">disabled</c:if> <c:if test="${rtnDtl[1].mngSttsNm eq '미선정' || rtnDtl[1].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">지원금(①)</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control notRequired" id="spprtPmt" name="pbsnDtlList[1].spprtPmt" value="${rtnPbsn[1].spprtPmt}" title="지원금" maxlength="50" value="" <c:if test="${rtnData.maxRsumeOrd ne 2}">disabled</c:if> <c:if test="${rtnDtl[1].mngSttsNm eq '미선정' || rtnDtl[1].mngSttsNm eq '선정'}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">자부담(②)</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control notRequired" id="phswPmt" name="pbsnDtlList[1].phswPmt" value="${rtnPbsn[1].phswPmt}" title="신청자" maxlength="50" value="" <c:if test="${rtnData.maxRsumeOrd ne 2}">disabled</c:if> <c:if test="${rtnDtl[1].mngSttsNm eq '미선정' || rtnDtl[1].mngSttsNm eq '선정'}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">총금액(①+②)</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control notRequired" id="sum" value="${rtnPbsn[1].ttlPmt}" maxlength="50" disabled/>
                                            <input type="hidden" class="notRequired" id="ttlPmt" name="pbsnDtlList[1].ttlPmt" value="${rtnPbsn[1].ttlPmt}"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[1]}">접수전</c:if>${rtnDtl[1].appctnSttsNm }</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[1].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 상태값</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                                <c:choose>
                                                    <c:when test="${rtnDtl[1].mngSttsNm eq '적합'}">
                                                        <p class="form-control-static">적합</p>
                                                        <input type="hidden" name="rsumeDtlList[1].mngSttsCd" value="${rtnDtl[1].mngSttsCd}">
                                                    </c:when>
                                                    <c:when test="${rtnDtl[1].mngSttsNm eq '부적합'}">
                                                        <p class="form-control-static">부적합</p>
                                                        <input type="hidden" name="rsumeDtlList[1].mngSttsCd" value="${rtnDtl[1].mngSttsCd}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm mngSttsCd notRequired" title="관리자 상태값" id="mngSttsCd2" name="rsumeDtlList[1].mngSttsCd"  <c:if test="${rtnData.maxRsumeOrd ne 2}">disabled</c:if>>
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01002_02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnDtl[1].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <input type="hidden" class="notRequired" name="rsumeDtlList[1].appctnSttsCd"value="${rtnDtl[1].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[1].mngSttsChngDtm }</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업계획서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq1" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업계획서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq1" value="${rtnFile[1].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[1].fileCd" value="ATTACH_FILE_TYPE08" />
                                </fieldset>


                            </div>
                        </div>
                    </div>

                    <!-- 최초점검 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx3" aria-constrols="addEx3">
                                최초점검
                            </a>
                        </div>
                        <div id="accEx3" class="panel-collapse collapse<c:if test="${rtnDtl[2].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 3}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[2]}">접수전</c:if>${rtnDtl[2].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[2].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">점검일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="chkDt" name="pbsnDtlList[2].chkDt"
                                                           value="${kl:convertDate(rtnPbsn[2].chkDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="점검일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 3}">disabled</c:if> <c:if test="${rtnDtl[2].mngSttsNm eq '미선정' || rtnDtl[2].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">평가점수</label>
                                        <div class="col-sm-5 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="examScore" name="pbsnDtlList[2].examScore" value="${rtnPbsn[2].examScore}" title="평가점수" maxlength="50" placeholder="평가점수 입력" <c:if test="${rtnData.maxRsumeOrd ne 3}">disabled</c:if> <c:if test="${rtnDtl[2].mngSttsNm eq '미선정' || rtnDtl[2].mngSttsNm eq '선정'}">disabled</c:if>/>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">결제/발주</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="payDt" name="pbsnDtlList[2].payDt"
                                                           value="${kl:convertDate(rtnPbsn[2].payDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="결제/발주 일자" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 3}">disabled</c:if> <c:if test="${rtnDtl[2].mngSttsNm eq '미선정' || rtnDtl[2].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">설치예정일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="nslltSchdlDt" name="pbsnDtlList[2].nslltSchdlDt"
                                                           value="${kl:convertDate(rtnPbsn[2].nslltSchdlDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="설치예정일자" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 3}">disabled</c:if> <c:if test="${rtnDtl[2].mngSttsNm eq '미선정' || rtnDtl[2].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                                <c:choose>
                                                    <c:when test="${rtnDtl[2].mngSttsNm eq '적합'}">
                                                        <p class="form-control-static">적합</p>
                                                        <input type="hidden" name="rsumeDtlList[2].mngSttsCd" value="${rtnDtl[2].mngSttsCd}">
                                                    </c:when>
                                                    <c:when test="${rtnDtl[2].mngSttsNm eq '부적합'}">
                                                        <p class="form-control-static">부적합</p>
                                                        <input type="hidden" name="rsumeDtlList[2].mngSttsCd" value="${rtnDtl[2].mngSttsCd}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm mngSttsCd notRequired" id="mngSttsCd3" name="rsumeDtlList[2].mngSttsCd" <c:if test="${rtnData.maxRsumeOrd ne 3}">disabled</c:if>>
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01003_02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnDtl[2].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <input type="hidden" class="notRequired" name="rsumeDtlList[2].appctnSttsCd"value="${rtnDtl[2].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[2].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">점검보고서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq2" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="점검보고서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq2" value="${rtnFile[2].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[2].fileCd" value="ATTACH_FILE_TYPE09" />
                                </fieldset>

                            </div>
                        </div>
                    </div>

                    <!-- 완료보고 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx4" aria-constrols="addEx4">
                                완료보고
                            </a>
                        </div>
                        <div id="accEx4" class="panel-collapse collapse<c:if test="${rtnDtl[3].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 4}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">완료보고일</label>
                                        <div class="col-sm-5">
                                        <div class="col-sm-3" style="margin-left: -15px">
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="cmpltnBrfngDt" name="pbsnDtlList[3].cmpltnBrfngDt"
                                                       value="${kl:convertDate(rtnPbsn[3].cmpltnBrfngDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                       title="완료보고일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 4}">disabled</c:if> <c:if test="${rtnDtl[3].mngSttsNm eq '미선정' || rtnDtl[3].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[3]}">접수전</c:if>${rtnDtl[3].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[3].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                                <c:choose>
                                                    <c:when test="${rtnDtl[3].mngSttsNm eq '적합'}">
                                                        <p class="form-control-static">적합</p>
                                                        <input type="hidden" name="rsumeDtlList[3].mngSttsCd" value="${rtnDtl[3].mngSttsCd}">
                                                    </c:when>
                                                    <c:when test="${rtnDtl[3].mngSttsNm eq '부적합'}">
                                                        <p class="form-control-static">부적합</p>
                                                        <input type="hidden" name="rsumeDtlList[3].mngSttsCd" value="${rtnDtl[3].mngSttsCd}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm mngSttsCd notRequired" id="mngSttsCd4" name="rsumeDtlList[3].mngSttsCd" <c:if test="${rtnData.maxRsumeOrd ne 4}">disabled</c:if>>
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01004_02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnDtl[3].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <input type="hidden" class="notRequired" name="rsumeDtlList[3].appctnSttsCd"value="${rtnDtl[3].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[3].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">완료보고서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq3" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="완료보고서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq3" value="${rtnFile[3].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[3].fileCd" value="ATTACH_FILE_TYPE10" />
                                </fieldset>



                            </div>
                        </div>
                    </div>


                    <!-- 최종점검 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx5" aria-constrols="addEx5">
                                최종점검
                            </a>
                        </div>
                        <div id="accEx5" class="panel-collapse collapse<c:if test="${rtnDtl[4].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 5}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[4]}">접수전</c:if>${rtnDtl[4].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[4].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">최종점검일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="lastChkDt1" name="pbsnDtlList[4].lastChkDt"
                                                           value="${kl:convertDate(rtnPbsn[4].lastChkDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="최종점검일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                                    <span class="input-group-btn" style="z-index:0;" <c:if test="${rtnData.maxRsumeOrd ne 5}">disabled</c:if> <c:if test="${rtnDtl[4].mngSttsNm eq '미선정' || rtnDtl[4].mngSttsNm eq '선정'}">disabled</c:if>>
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <div class="col-sm-1" style="margin-left: -15px">
                                                <c:choose>
                                                    <c:when test="${rtnDtl[4].mngSttsNm eq '적합'}">
                                                        <p class="form-control-static">적합</p>
                                                        <input type="hidden" name="rsumeDtlList[4].mngSttsCd" value="${rtnDtl[4].mngSttsCd}">
                                                    </c:when>
                                                    <c:when test="${rtnDtl[4].mngSttsNm eq '부적합'}">
                                                        <p class="form-control-static">부적합</p>
                                                        <input type="hidden" name="rsumeDtlList[4].mngSttsCd" value="${rtnDtl[4].mngSttsCd}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm mngSttsCd notRequired" id="mngSttsCd5" name="rsumeDtlList[4].mngSttsCd" <c:if test="${rtnData.maxRsumeOrd ne 5}">disabled</c:if>>
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01005_02')}">
                                                                    <option value="${cdList.cd}" <c:if test="${rtnDtl[4].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>

                                                <input type="hidden" class="notRequired" name="rsumeDtlList[4].appctnSttsCd"value="${rtnDtl[4].appctnSttsCd}">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[4].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">점검보고서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq4" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="점검보고서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq4" value="${rtnFile[4].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[4].fileCd" value="ATTACH_FILE_TYPE09" />
                                </fieldset>
                            </div>
                        </div>
                    </div>


                    <!-- 검수보고 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx6" aria-constrols="addEx6">
                                검수보고
                            </a>
                        </div>
                        <div id="accEx6" class="panel-collapse collapse<c:if test="${rtnDtl[5].mngSttsNm eq '미확인'}"> in</c:if><c:if test="${rtnData.maxRsumeOrd eq 6}"> in</c:if>" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 상태값</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static"><c:if test="${empty rtnDtl[5]}">접수전</c:if>${rtnDtl[5].appctnSttsNm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[5].appctnSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">최종점검일</label>
                                        <div class="col-sm-5">
                                            <div class="col-sm-3" style="margin-left: -15px">
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" id="lastChkDt2" name="pbsnDtlList[5].lastChkDt"
                                                           value="${kl:convertDate(rtnPbsn[5].lastChkDt , 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                                           title="최종점검일" readonly onclick="cmmCtrl.initCalendar(this);" <c:if test="${rtnData.maxRsumeOrd ne 6}">disabled</c:if> <c:if test="${rtnDtl[5].mngSttsNm eq '미선정' || rtnDtl[5].mngSttsNm eq '선정'}">disabled</c:if>/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                        <label class="col-sm-1 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-10 form-inline">
                                            <c:choose>
                                                <c:when test="${rtnDtl[5].mngSttsNm eq '적합'}">
                                                    <p class="form-control-static">적합</p>
                                                    <input type="hidden" name="rsumeDtlList[5].mngSttsCd" value="${rtnDtl[5].mngSttsCd}">
                                                </c:when>
                                                <c:when test="${rtnDtl[5].mngSttsNm eq '부적합'}">
                                                    <p class="form-control-static">부적합</p>
                                                    <input type="hidden" name="rsumeDtlList[5].mngSttsCd" value="${rtnDtl[5].mngSttsCd}">
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control input-sm mngSttsCd notRequired" id="mngSttsCd6" name="rsumeDtlList[5].mngSttsCd" <c:if test="${rtnData.maxRsumeOrd ne 6}">disabled</c:if>>
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01006_02')}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnDtl[5].mngSttsCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                            <input type="hidden" class="notRequired" name="rsumeDtlList[5].appctnSttsCd"value="${rtnDtl[5].appctnSttsCd}">
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-5 form-inline">
                                            <p class="form-control-static">${rtnDtl[5].mngSttsChngDtm}</p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">점검보고서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq5" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="점검보고서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                    <input type="hidden" class="notRequired" name="fileSeq5" value="${rtnFile[5].fileSeq}" />
                                    <input type="hidden" class="notRequired" name="fileDtlList[5].fileCd" value="ATTACH_FILE_TYPE09" />
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div> <!-- id=accParent -->
            </div>


            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
        </form>
        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>
        <%-- 위원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpd/MPDCmtSrchLayer.jsp"></jsp:include>
    </div>
</div>
