
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbf/WBFBRegisterCompanyWriteCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>관리자 등록</h6><hr>

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
                            <input type="text" class="form-control" id="nameAndId" value="" title="신청자" maxlength="50" disabled/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
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
                            <input type="text" class="form-control notRequired" id="deptDtlNm" name="deptDtlNm" value="" title="부서 상세" maxlength="50"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">직급</label>
                    <div class="col-sm-5">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control notRequired" style="display:none;" id="pstnNm" name="pstnNm" value="" title="직급 상세" maxlength="50"/>
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
                        <input type="text" class="form-control notRequired telNumber" id="telNo" name="telNo" value="" title="전화번호" placeholder="전화번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"/>
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
                                        <c:if test="${cdList.cd eq 'COMPANY01001' or cdList.cd eq 'COMPANY01002'}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
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
                            <input type="text" class="form-control input-sm telNumber" id="compTel" name="compTel" value="" title="전화번호" placeholder="전화번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="changeBsnmNo" title="재직 회사사업자번호"></p>
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
                            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="" readonly placeholder="우편번호" style="width: 95px;" title="주소">
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

            <h6 class="mt0"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">종된사업장번호</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired numberChk" id="sbrdnBsnmNo" name="sbrdnBsnmNo" value="" title="종된사업장번호" maxlength="4" placeholder="종된사업장번호">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
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
                </div>
            </fieldset>
            <fieldset>
                    <div class="form-group text-sm">
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
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청서<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile1" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="신청서">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[0].type" value="ATTACH_FILE_TYPE01">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[0].seqNm" value="rsumeTaskFile1">
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
                        <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile2" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="보안서약서">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[1].type" value="ATTACH_FILE_TYPE02">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[1].seqNm" value="rsumeTaskFile2">
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
                        <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile3" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="중소기업확인서">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[2].type" value="ATTACH_FILE_TYPE03">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[2].seqNm" value="rsumeTaskFile3">
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
                        <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile4" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업자등록증">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[3].type" value="ATTACH_FILE_TYPE04">
                            <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[3].seqNm" value="rsumeTaskFile4">
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

    </div>
</div>

