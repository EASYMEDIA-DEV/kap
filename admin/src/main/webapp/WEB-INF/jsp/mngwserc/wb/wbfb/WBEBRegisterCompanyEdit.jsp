
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbf/WBFBRegisterCompanyEditCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청부품사 상세/수정</h6><hr>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="" />
            <input type="hidden" class="notRequired" name="id" value="" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="" />
            <input type="hidden" class="notRequired" name="episdSeq" value="" />

            <h6 class="mt0"><em class="ion-play mr-sm"></em>회차 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                <option value="">연도 전체</option>
                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                    <option value="${optYear}">${optYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm" id="optEpisd" name="episd" title="회차">
                            <option value="">회차 전체</option>
                        </select>
                    </div>
                </div>
            </fieldset>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="id" value="" title="신청자" maxlength="50" disabled/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" id="btnPartUserModal" class="btn btn-sm btn-info">회원검색</button>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <p class="form-control-static" id="email"></p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-5">
                            <select class="form-control input-sm" id="deptCd" name="deptCd" title="부서">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD020')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="" title="부서 상세" maxlength="50"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">직급</label>
                    <div class="col-sm-5">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <select class="form-control input-sm notRequired" id="pstnCd" name="pstnCd" title="직급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control " id="pstnNm" name="pstnNm" value="" title="직급 상세" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5"style="margin-left: -15px">
                        <div class="col-sm-6">
                            <p class="form-control-static" id="hpNo"></p>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">일반 전화번호</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control notRequired" id="telNo" name="telNo" value="" title="전화번호" maxlength="50"/>
                    </div>
                </div>
            </fieldset>


            <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="cmpnNm"></p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분">
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
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="규모">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY020')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
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
                        <div class="col-sm-6" style="margin-left: -15px">
                            <p class="form-control-static" id="rprsntNm"></p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                       value=""
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
                    <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <input type="text" class="form-control input-sm" id="compTel" name="compTel" value="" title="전화번호" maxlength="50" placeholder="전화번호 입력"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="bsnmNo" title="재직 회사사업자번호"></p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주소<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-primary" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipCode" name="zipCode" value="" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="" title="매출액" maxlength="50" placeholder="매출액"/>
                        &nbsp;억원&nbsp;
                        <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        &nbsp;년&nbsp;
                    </div>

                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
                    </div>
                </div>
            </fieldset>

            <div id="fieldStart">
                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">품질5스타</label>
                        <div class="col-sm-5">
                            <select class="form-control input-sm notRequired" id="qlty5starCd" name="qlty5starCd" title="품질5스타등급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}" >
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5starYear" title="품질5스타연도">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}" >
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select> 년
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">납입5스타</label>
                        <div class="col-sm-5">
                            <select class="form-control input-sm notRequired" id="pay5starCd" name="pay5starCd" title="납입5스타등급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}" >
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="pay5starYear" name="pay5starYear" title="납입5스타연도">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}" >
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select> 년
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">기술5스타</label>
                        <div class="col-sm-5">
                            <select class="form-control input-sm notRequired" id="tchlg5starCd" name="tchlg5starCd" title="기술5스타등급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}">
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="tchlg5starYear" name="tchlg5starYear" title="기술5스타연도">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}">
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select> 년
                        </div>
                    </div>
                </fieldset>
            </div>

            <fieldset id="fieldSQ">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">SQ 정보</label>
                    <div class="col-sm-10">
                        <c:forEach var="i" begin="1" end="3" varStatus="status">
                            <div class="col-sm-12" id="sqInfoArea${status.index-1}">
                                <input type="hidden" class="notRequired" id="cbsnSeq" name="sqInfoList[${status.index-1}].cbsnSeq" value="" />
                                <input type="text" class="form-control input-sm notRequired" id="nm" name="sqInfoList[${status.index-1}].nm" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                <input type="text" class="form-control input-sm notRequired" id="score" name="sqInfoList[${status.index-1}].score" value="" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                <select class="form-control input-sm notRequired" id="year" name="sqInfoList[${status.index-1}].year" title="평가년도" style="width:auto;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" >
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm" name="sqInfoList[${status.index-1}].crtfnCmpnNm" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>담당위원 정보</h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">담당위원명</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <p class="form-control-static" ></p>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" id="btnPartCsModal" class="btn btn-sm btn-info">검색</button>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-2">
                        <p class="form-control-static" ></p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <p class="form-control-static" ></p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">업체명</label>
                    <div class="col-sm-2">
                        <p class="form-control-static" ></p>
                    </div>
                </div>
            </fieldset>
            <br>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">종된사업장번호</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired" id="sbrdnBsnmNo" name="sbrdnBsnmNo" value="" title="종된사업장번호" maxlength="4" placeholder="종된사업장번호">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">과제명<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <div class="col-sm-6" style="margin-left: -15px">
                        <select class="form-control input-sm" id="optAsigt" name="taskCd" title="과제명">
                            <option value="">선택</option>
                        </select>
                    </div>
                </div>

                <label class="col-sm-1 control-label">사업 유형<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <div class="col-sm-6" style="margin-left: -15px">
                        <select class="form-control input-sm " id="optBsin" name="bsnTypeCd" title="사업 유형">
                            <option value="">선택</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-1 control-label">스마트화 현재 수준<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <div class="col-sm-6" style="margin-left: -15px">
                        <select class="form-control input-sm" name="smtfnPrsntCd" title="스마트화 현재 수준">
                            <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <label class="col-sm-1 control-label">스마트화 목표 수준<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <div class="col-sm-6" style="margin-left: -15px">
                        <select class="form-control input-sm " name="smtfnTrgtCd">
                            <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <br>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>선급금 해당 여부</h6>
            <fieldset>
                <label class="col-sm-1 control-label">선급금 해당 여부<span class="star"> *</span></label>
                <div class="col-sm-5">
                    <div class="col-sm-3">
                        <input type="radio" id="applicable" name="drone" value="Y" />
                        <label for="applicable">해당</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="radio" id="notApplicable" name="drone" value="N" />
                        <label for="notApplicable">미해당</label>
                    </div>
                </div>
            </fieldset>

            <div class="container-fluid">
                <div class="panel-group" id="accParent" role="tablist">
                    <%-- 영역 1 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" href="#accEx1" aria-constrols="addEx1">신청</a>
                        </div>
                        <div id="accEx1" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">
                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">접수일</label>
                                        <div class="col-sm-6 form-inline">
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="accsDt" name="accsDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">정부지원금(①)</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="gvmntSpprtPmt" name="gvmntSpprtPmt" value="" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">대기업출연금(②)</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="mjcmnAprncPmt" name="mjcmnAprncPmt" value="" title="정부지원금" maxlength="50" placeholder="대기업출연금" >
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">총금액(①+②)</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="ttlPmt" name="ttlPmt" value="" title="정부지원금" maxlength="50" placeholder="총금액" disabled>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">은행명</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="bankNm" name="bankNm" value="" title="은행명" maxlength="50" placeholder="은행명" >
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">계좌번호</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="acntNo" name="acntNo" value="" title="계좌번호" maxlength="50" placeholder="계좌번호" >
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">예금주</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="dpsitNm" name="dpsitNm" value="" title="예금주" maxlength="50" placeholder="예금주" >
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" id="appctnSttsCd"></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" id="appctnSttsChngDtm"></p>
                                        </div>
                                    </div>
                                </fieldset>


                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <label class="col-sm-2 control-label">지급일</label>
                                    <div class="col-sm-6 form-inline">
                                        <div class="input-group">
                                            <input type="text" class="form-control input-sm datetimepicker_strtDt" id="giveDt" name="giveDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                            <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급차수</label>
                                        <div class="col-sm-6 form-inline">
                                            <select class="form-control input-sm notRequired" name="giveSeq">
                                                <c:forEach var="cdList" items="${classTypeList.STTS_CD}" varStatus="status">
                                                    <option value="${cdList.cdNm}" <c:if test="${rtnDto.episd eq cdList.cdNm}">selected</c:if> >${cdList.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" id="mngSttsCd"></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" id="mngSttsChngDtm"></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="acntFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="계좌이체약정서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="bnkbkFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="통장사본">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">관리자메모</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" value="" title="관리자메모" placeholder="관리자 메모 입력" maxlength="500"></textarea>
                                        </div>
                                    </div>
                                </fieldset>
                                <div class="pull-right">
                                    <button type="button" class="btn btn-sm btn-primary">신청정보 다운로드</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="text-left mb-xl"><h5>부품사 관리 등록</h5></div>
                    <hr>
                    <h6 class="mt0"><em class="ion-play mr-sm"></em>전체 진행상태 : 잔급지급(지급완료)</h6>

                    <!-- 영역 2 -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx2" aria-constrols="addEx2">신청</a>
                        </div>
                        <div id="accEx2" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt1">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">출연기업</label>
                                        <div class="col-sm-6 form-inline">
                                            <select class="form-control input-sm notRequired" name="aprncCmpnCd" title="출연기업">
                                                <c:forEach var="cdList" items="${classTypeList.STTS_CD}" varStatus="status">
                                                    <option value="${cdList.cdNm}" <c:if test="${rtnDto.episd eq cdList.cdNm}">selected</c:if> >${cdList.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">보안서약서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="보안서약서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">중소기업확인서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="중소기업확인서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">사업자등록증<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업자등록증">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </div>
                    </div>

                    <%-- 영역 3--%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx3" aria-constrols="addEx3">사업계획</a>
                        </div>
                        <div id="accEx3" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">공급기업명</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="offerCmpnNm" name="offerCmpnNm" value="" title="공급기업명" maxlength="50" placeholder="공급기업명" disabled>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">공급기업 사업자등록번호</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" id="offerBsnmNo" name="offerBsnmNo" value="" title="공급기업 사업자등록번호" maxlength="50" placeholder="공급기업 사업자등록번호">
                                            </div>
                                            <div class="col-sm-1">
                                                <button type="button" id="" class="btn btn-sm btn-info">검색</button>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">공급기업 담당자명</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" id="offerPicNm" name="offerPicNm" value="" title="공급기업 담당자명" maxlength="50" placeholder="공급기업 담당자명">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">공급기업 담당자 휴대폰</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" id="offerPicHpNo" name="offerPicHpNo" value="" title="공급기업 담당자 휴대폰" maxlength="50" placeholder="공급기업 담당자 휴대폰">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">공급기업 담당자 이메일</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <p class="form-control-static" ></p>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">총 사업비</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <p class="form-control-static" ></p>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">과제번호</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="taskNo" name="taskNo" value="" title="과제번호" maxlength="50" placeholder="과제번호">
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </div>
                    </div>

                    <%-- 영역 4 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx4" aria-constrols="accEx4">최초점검</a>
                        </div>
                        <div id="accEx4" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검위원</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" id="chkCmssrSeq" name="chkCmssrSeq" value="" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                            </div>
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-sm btn-info">검색</button>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검계획일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="chkPlanDt" name="chkPlanDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검실시일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="chkImplmnDt" name="chkImplmnDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">평점</label>
                                        <div class="col-sm-6 form-inline">
                                            <input type="text" class="form-control input-sm notRequired" id="examScore" name="examScore" value="" title="평점" maxlength="50" placeholder="평점">
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <%-- 영역 5 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx5" aria-constrols="accEx5">중간점검</a>
                        </div>
                        <div id="accEx5" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검위원</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="chkCmssrSeq" value="" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                            </div>
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-sm btn-info">검색</button>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검계획일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="chkPlanDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검실시일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="chkImplmnDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <%-- 영역 6 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx6" aria-constrols="accEx6">원가계산</a>
                        </div>
                        <div id="accEx6" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">원가계산기관</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="chkCmssrSeq" value="" title="원가계산기관" maxlength="50" placeholder="원가계산기관">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">의뢰일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="rqstDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">회신일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="rplyDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">정부지원금(①)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="gvmntSpprtPmt" value="" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">대기업출연금(②)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="mjcmnAprncPmt" value="" title="대기업출연금" maxlength="50" placeholder="대기업출연금">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">도입기업부담금(③)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="ntdcnCmpnBrdnPmt" value="" title="도입기업부담금" maxlength="50" placeholder="도입기업부담금">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">총금액(①+②+③)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="ttlPmt" value="" title="총금액" maxlength="50" placeholder="총금액" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </div>
                    </div>

                    <%-- 영역 7 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx7" aria-constrols="accEx7">협약</a>
                        </div>
                        <div id="accEx7" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">협약일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="agrmtDt" value="" title="협약일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">기간</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="agrmtTermDt" value="" title="기간" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <%-- 영역 8 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx8" aria-constrols="accEx8">지원금지급</a>
                        </div>
                        <div id="accEx8" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">접수일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="accsDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">정부지원금(①)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="gvmntSpprtPmt" value="" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">대기업출연금액(②)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="mjcmnAprncPmt" value="" title="정부지원금" maxlength="50" placeholder="대기업출연금액">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">총금액(①+②)</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="mjcmnAprncPmt" value="" title="총금액" maxlength="50" placeholder="총금액" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">은행명</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="bankNm" value="" title="은행명" maxlength="50" placeholder="은행명">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">계좌번호</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="acntNo" value="" title="계좌번호" maxlength="50" placeholder="계좌번호">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">예금주</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="dpsitNm" value="" title="예금주" maxlength="50" placeholder="예금주">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">사용자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveDt" value="" title="지급일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급차수</label>
                                        <div class="col-sm-6 form-inline" >
                                            <select class="form-control input-sm" id="giveSeq" name="giveSeq" title="지급차수">
                                                <option value="">연도 전체</option>
                                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                                    <option value="${optYear}">${optYear}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">계좌이체약정서<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="계좌이체약정서">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">통장사본<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="통장사본">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <%-- 영역 9 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx9" aria-constrols="accEx9">기술임치</a>
                        </div>
                        <div id="accEx9" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">접수일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">수수료</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="" value="" title="수수료" maxlength="50" placeholder="수수료">
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveDt" value="" title="지급일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급차수</label>
                                        <div class="col-sm-6 form-inline">
                                            <select class="form-control input-sm" title="지급차수">
                                                <option value="">연도 전체</option>
                                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                                    <option value="${optYear}">${optYear}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">지급차수</label>
                                        <div class="col-sm-6 form-inline">
                                            <select class="form-control input-sm" title="지급차수">
                                                <option value="">연도 전체</option>
                                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                                    <option value="${optYear}">${optYear}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
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
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">기술임치증<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="tchlgLseFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="기술임치증">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">임치료 납입 영수증<span class="star"> *</span></label>
                                        <div class="col-sm-10 col-md-11">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="lsePayFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="임치료 납입 영수증">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <%-- 영역 10 --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab">
                            <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx10" aria-constrols="accEx10">최종점검</a>
                        </div>
                        <div id="accEx10" class="panel-collapse collapse in" role="tabpanel">
                            <div class="panel-body">

                                <h6 class="mt0">신청자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>

                                <h6 class="mt0">관리자</h6>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검위원</label>
                                        <div class="col-sm-6" >
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <input type="text" class="form-control input-sm notRequired" name="chkCmssrSeq" value="" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                            </div>
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-sm btn-info">검색</button>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검계획일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="chkPlanDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">점검실시일</label>
                                        <div class="col-sm-6 form-inline" >
                                            <div class="input-group">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="chkImplmnDt" value="" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">스마트화 수준확인</label>
                                        <div class="col-sm-6 form-inline">
                                            <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                                <option value="">연도 전체</option>
                                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                                    <option value="${optYear}">${optYear}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                        <div class="col-sm-6 form-inline">
                                            <p class="form-control-static" ></p>
                                        </div>
                                    </div>
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
                    <c:choose>
                        <c:when test="${ not empty rtnDto}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </form>

        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/wb/WBFBPartUserModal.jsp" />

    </div>
</div>

