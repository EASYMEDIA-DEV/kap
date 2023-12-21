
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbb/WBBBCompanyWriteCtrl controller/co/COFormCtrl">
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <c:if test="${not empty rtnInfo.appctnSeq}">
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="${rtnInfo.pageIndex}" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="${rtnInfo.pageRowSize}" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="${rtnInfo.listRowSize}" />
            </c:if>
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${userInfo.memSeq}" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="${userInfo.bsnmNo}" />
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}" />

            <c:if test="${empty rtnInfo.appctnSeq}">
                <h6 class="mt0"><strong>관리자 등록</strong></h6><hr>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>회차 정보</h6>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                    <option value="">연도 전체</option>
                                    <c:forEach var="yearList" items="${rtnYear}" varStatus="status">
                                        <option value="${yearList.year}" <c:if test="${rtnInfo.year eq yearList.year}">selected</c:if>>${yearList.year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="optEpisd" name="episdSeq" title="회차">
                                <option value="">회차 전체</option>
                                <c:forEach var="rtnEpisd" items="${rtnEpisdList}" varStatus="status">
                                    <option value="${rtnEpisd.episdSeq}" <c:if test="${rtnInfo.episdSeq eq rtnEpisd.episdSeq}">selected</c:if>>${rtnEpisd.episd}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </fieldset>
            </c:if>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>

            <div id="compnayDiv">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-6">
                                <c:choose>
                                    <c:when test="${not empty userInfo.name}">
                                        <input type="text" class="form-control" id="id" value="${userInfo.name}(${userInfo.id})" title="신청자" maxlength="50" disabled/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" id="id" value="" title="신청자" maxlength="50" disabled/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-sm-1">
                                <button type="button" id="btnPartUserModal" class="btn btn-sm btn-info">회원검색</button>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <p class="form-control-static" id="email">
                                ${userInfo.email}
                            </p>
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
                                            <option value="${cdList.cd}" <c:if test="${userInfo.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>

                            </div>
                            <div class="col-sm-4" style="margin-left: -15px">
                                <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="${userInfo.deptDtlNm}" title="부서 상세" maxlength="50"/>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">직급</label>
                        <div class="col-sm-5">
                            <div class="col-sm-5" style="margin-left: -15px">
                                <select class="form-control input-sm notRequired" id="pstnCd" name="pstnCd" title="직급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4" style="margin-left: -15px">
                                <input type="text" class="form-control " id="pstnNm" name="pstnNm" value="${userInfo.pstnNm}" title="직급 상세" maxlength="50"/>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                        <div class="col-sm-5"style="margin-left: -15px">
                            <div class="col-sm-6">
                                <p class="form-control-static" id="hpNo">${userInfo.hpNo}</p>
                            </div>
                        </div>
                        <label class="col-sm-1 control-label">일반 전화번호</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control notRequired" id="telNo" name="telNo" value="${userInfo.telNo}" title="전화번호" maxlength="50"/>
                        </div>
                    </div>
                </fieldset>


                <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static" id="cmpnNm">${userInfo.cmpnNm}</p>
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
                                            <c:if test="${cdList.cd eq 'COMPANY01001' or cdList.cd eq 'COMPANY01002'}" >
                                                <option value="${cdList.cd}" <c:if test="${userInfo.ctgryCd eq cdList.cd}">selected</c:if>>
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
                                            <option value="${cdList.cd}" <c:if test="${userInfo.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
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
                                <p class="form-control-static" id="rprsntNm">${userInfo.rprsntNm}</p>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <div class="input-group">
                                    <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                           value="${userInfo.stbsmDt}"
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
                                <input type="text" class="form-control input-sm" id="compTel" name="compTel" value="${userInfo.compTel}" title="전화번호" maxlength="50" placeholder="전화번호 입력"/>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static" id="bsnmNo" title="재직 회사사업자번호">${userInfo.bsnmNo}</p>
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
                                <input type="text" class="form-control input-sm" id="zipCode" name="zipcode" value="${userInfo.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                                <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${userInfo.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                            </div>
                            <br>
                            <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${userInfo.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">매출액(연도)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${userInfo.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                            &nbsp;억원&nbsp;
                            <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <option value="${cdList.cd}"  <c:if test="${userInfo.slsYear eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                            &nbsp;년&nbsp;
                        </div>

                        <label class="col-sm-1 control-label">직원수</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${userInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">주생산품</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${userInfo.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${userInfo.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${userInfo.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
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
                                            <option value="${cdList.cd}" <c:if test="${userInfo.qlty5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5starYear" title="품질5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.qlty5starYear eq cdList.cd}">selected</c:if>>
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
                                            <option value="${cdList.cd}" <c:if test="${userInfo.pay5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="pay5starYear" name="pay5starYear" title="납입5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.pay5starYear eq cdList.cd}">selected</c:if>>
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
                                            <option value="${cdList.cd}" <c:if test="${userInfo.tchlg5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="tchlg5starYear" name="tchlg5starYear" title="기술5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.tchlg5starYear eq cdList.cd}">selected</c:if>>
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
                                    <input type="hidden" class="notRequired" id="cbsnSeq" name="sqInfoList[${status.index-1}].cbsnSeq" value="${userInfo.sqInfoList[status.index-1].cbsnSeq}" />
                                    <input type="text" class="form-control input-sm notRequired" id="nm" name="sqInfoList[${status.index-1}].nm" value="${userInfo.sqInfoList[status.index-1].nm}" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                    <input type="text" class="form-control input-sm notRequired" id="score" name="sqInfoList[${status.index-1}].score" value="${userInfo.sqInfoList[status.index-1].score}" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                    <select class="form-control input-sm notRequired" id="year" name="sqInfoList[${status.index-1}].year" value="${userInfo.sqInfoList[status.index-1].year}" title="평가년도" style="width:auto;">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}"  <c:if test="${userInfo.sqInfoList[status.index-1].year eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm" name="sqInfoList[${status.index-1}].crtfnCmpnNm" value="${userInfo.sqInfoList[status.index-1].crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </fieldset>
            </div>
            <c:if test="${not empty rtnInfo.appctnSeq}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 변경이력</h6>
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
                <h6 class="mt0"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
                <fieldset id="admMemo">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">관리자메모</label>
                        <div class="col-sm-11">
                            <textarea class="form-control input-sm notRequired" name="admMemo" rows="10" maxlength="2000" title="관리자메모" placeholder="관리자메모를 입력하세요.">${rtnInfo.admMemo}</textarea>
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <c:if test="${not empty optionList}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
                <c:forEach var="optnList" items="${optionList}" varStatus="status">
                    <fieldset>
                        <input type="hidden" name="optnSeq" value="${optnList.optnSeq}"/>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">${optnList.optnNm}<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="" />
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="${optnList.optnNm}">
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
                </c:forEach>
            </c:if>

            <hr />
            <c:if test="${not empty rtnInfo}">
                <div class="panel-group" id="accParent" role="tablist">
                    <div>
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>사업진행 상세</h6>
                        <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em><strong>전체 진행상태 : ${rtnInfo.rsumeSttsNm} (${rtnInfo.mngSttsNm})</strong></h7><hr/>
                        <input type="hidden" id="tabIndex" value="${rtnInfo.stageOrd}">
                    </div>
                    <c:forEach var="item" items="${rtnInfo.applyList}" varStatus="status">
                        <div class="panel panel-default" id="tabIndex${status.index+1}">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx${status.index}" aria-constrols="addEx${status.index}">${item.stageNm}</a>
                            </div>
                            <div id="addEx${status.index}" class="panel-collapse collapse <c:if test="${rtnInfo.stageOrd eq item.applyDtl.rsumeOrd}">in</c:if>" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeSeq" value="${item.applyDtl.rsumeSeq}" />
                                    <input type="hidden" class="notRequired" name="rsumeOrd" value="${item.applyDtl.rsumeOrd}" />
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태 값</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${item.applyDtl.appctnSttsNm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${item.applyDtl.appctnSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <c:choose>
                                                    <c:when test="${item.applyDtl.mngSttsCd eq 'PRO_TYPE04_1_4' or item.applyDtl.mngSttsCd eq 'PRO_TYPE04_1_8'
                                                    or item.applyDtl.mngSttsCd eq 'PRO_TYPE04_1_5' or item.applyDtl.mngSttsCd eq 'PRO_TYPE04_1_6' or item.applyDtl.mngSttsCd eq 'PRO_TYPE04_1_7'}">
                                                        ${item.applyDtl.mngSttsNm}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm notRequired mngStatus" name="mngSttsCd" value="${item.mngSttsCd}" title="관리자 상태값" style="width:auto;">
                                                            <option value="">선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE04_1_')}">
                                                                    <option value="${cdList.cd}"  <c:if test="${item.applyDtl.mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${not empty item.applyDtl.rtrnRsnCntn}">
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${item.applyDtl.rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${item.applyDtl.rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px; display: none;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${item.applyDtl.mngSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>

                                    <c:choose>
                                        <c:when test="${not empty item.applyOptnList}">
                                            <c:forEach var="itemOptn" items="${item.applyOptnList}" varStatus="status1">
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <input type="hidden" name="sbmsnSeqList" value="${itemOptn.sbmsnSeq}"/>
                                                        <input type="hidden" name="optnSeq" value="${itemOptn.optnSeq}"/>
                                                        <label class="col-sm-1 control-label">${itemOptn.optnNm}<span class="star"> *</span></label>
                                                        <div class="col-sm-10 col-md-11">
                                                            <input type="hidden" class="notRequired" id="fileSeq${itemOptn.fileSeq}" name="fileSeq${itemOptn.fileSeq}" value="${itemOptn.fileSeq}" />
                                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                            <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq${itemOptn.fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="${itemOptn.optnNm}">
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
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="itemOptn" items="${item.applyTempOptnList}" varStatus="status1">
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <input type="hidden" name="optnSeq" value="${itemOptn.optnSeq}"/>
                                                        <label class="col-sm-1 control-label">${itemOptn.optnNm}<span class="star"> *</span></label>
                                                        <div class="col-sm-10 col-md-11">
                                                            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${itemOptn.fileSeq}" />
                                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                            <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="${itemOptn.optnNm}">
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
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div> <!-- id=accParent -->
            </c:if>


            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
        </form>

        <form class="form-horizontal" id="frm" name="frm" method="post">
            <input type="hidden" class="notRequired" name="memSeq" value="${userInfo.memSeq}" />
            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}" />
            <input type="hidden" class="notRequired" name="nextStageNm" value="" />
            <input type="hidden" class="notRequired" name="userLogYn" />
            <input type="hidden" class="notRequired" name="wbbTransDTO.bfreMemSeq" />
            <input type="hidden" class="notRequired" name="wbbTransDTO.aftrMemSeq" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="${userInfo.bsnmNo}">
            <input type="hidden" class="notRequired" name="maxStage" value="${rtnInfo.maxStage}" />
        </form >
        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/wb/WBFBPartUserModal.jsp" />

    </div>
</div>

