<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbj/WBJAcomCompanyWriteCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>관리자 등록</h6><hr>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="" />
            <input type="hidden" class="notRequired" name="id" value="" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="" />
            <input type="hidden" class="notRequired" name="episdSeq" value="" />
            <input type="hidden" class="notRequired" name="hpNo" value="" />
            <input type="hidden" class="notRequired" id="name" value="" />

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
                        <div class="col-sm-3">
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

                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
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
                            <input type="text" class="form-control " id="pstnNm" name="pstnNm" value="" title="직급 상세" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" style="border:none; background-color: #FFFFFF" class="form-control" disabled id="hpNo" maxlength="50"/>
                    </div>
                    <div class="col-sm-3"></div>
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
                        <input type="text" style="border:none; background: #FFFFFF;" class="form-control notRequired" disabled id="cmpnNm" maxlength="50"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
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
                            <input type="hidden" id="befeCtgryCd" class="notRequired" value="${rtnInfo.ctgryCd}"/>
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
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <input type="text" class="form-control input-sm" id="compTel" name="compTel" value="" title="전화번호" maxlength="50" placeholder="전화번호 입력"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="workBsnmNo" title="재직 회사사업자번호"></p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사 주소<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-primary" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipCode" name="zipCode" value=""  readonly placeholder="우편번호" style="width: 95px;" title="주소">
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
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
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
                            <select class="form-control input-sm notRequired" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" style="width:auto;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5StarYear" title="품질5스타연도">
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
                            <select class="form-control input-sm notRequired" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급" style="width: 100px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.pay5StarCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" style="width: 100px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select> 년
                        </div>
                    </div>
                </fieldset>
                <fieldset class="tchlgCd">
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">기술5스타</label>
                        <div class="col-sm-5">
                            <select class="form-control input-sm notRequired" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급" style="width:100px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm notRequired" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" style="width:100px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                    <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
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
            <h7 class="mt0">추천자 정보</h7>
            <fieldset>
                <div class="pull-right">
                    <label class="checkbox-inline c-checkbox" style="margin-left: 30px !important; margin-right: 32px !important;">
                        <input type="checkbox" class="notRequired" id="same" name="same" value="" title=""/>
                        <span class="ion-checkmark-round"></span> 신청자 정보와 동일
                    </label>
                </div>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이름</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm same notRequired"  id="rcmndName" name="rcmndName" value="" title="이름"  placeholder="이름 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm same notRequired" id="rcmndHpNo" name="rcmndHpNo" value="" title="휴대폰번호"  placeholder="휴대폰번호 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm same notRequired" id="rcmndCmpnNm" name="rcmndCmpnNm" value="" title="부품사명" placeholder="부품사명 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm same notRequired" id="rcmndDeptNm" name="rcmndDeptNm" value="" title="부서" placeholder="부서 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">직급</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm same notRequired" id="rcmndPstnNm" name="rcmndPstnNm" value="" title="직급"  placeholder="직급 입력">
                    </div>
                </div>
            </fieldset>

            <h7 class="mt0">포상대상자정보</h7>

            <fieldset>
                <label class="col-sm-1 control-label">포상부문<span class="star"> *</span></label>
                <div class="col-sm-5" style="margin-left: -15px">
                    <div class="col-sm-3">
                        <select class="form-control srvTypeCd prizeCd" id="optPrize" name="prizeCd">
                            <option value="" >선택</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명 검색<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="hidden" class="notRequired" id="bsnmNo" name="cmpnSeq" value="${rtnDto.cmpnSeq}" />
                        <input type="text" class="form-control input-sm notRequired" id="bsnmNoNm" disabled value="${ rtnDto.bsnmNoNm }" title="부품사명"/>
                    </div>
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-inverse btn-sm bsnmNoBtn">
                            부품사 검색
                        </button>
                    </span>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" id="ctgryNm" value="" title="구분" maxlength="4" disabled>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" id="" name="newName" value="" title="이름" maxlength="4" placeholder="이름 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" id="deptNm" name="deptNm" value="" title="부서" maxlength="4" placeholder="부서 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm notRequired" name="newPstnCd" title="직급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control" name="newPstnNm" title="직급 상세" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연령<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" id="age" name="age" value="" title="연령"  placeholder="연령 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">근속년수<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" id="yrssvYearCnt" name="yrssvYearCnt" value="" title="근속년수" placeholder="근속년수 입력">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" name="newHpNo" value="" title="휴대폰번호" placeholder="휴대폰번호 입력">
                    </div>
                </div>
            </fieldset><fieldset>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                <div class="col-sm-2">
                    <input type="text" class="form-control input-sm" name="newCmpnTelNo" value="" title="회사 전화번호"  placeholder="회사전화번호 입력">
                </div>
            </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm"  name="newEmail" value="" title="이메일" placeholder="이메일 입력">
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청서</label>
                    <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE01" title="첨부파일유형"/>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="appctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="신청서">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ jpg,jpeg,gif,png,bmp,mp4,webm,wmv,avi,mkv,pdf,ppt,pptx,xls,xlsx,doc,docx,dox,hwp,hwpx,txt,zip  파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
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
        <jsp:include page="/WEB-INF/jsp/mngwserc/wb/WBFBPartUserModal.jsp" />
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpe/MPEPartsCompanySrchLayer.jsp"></jsp:include>
