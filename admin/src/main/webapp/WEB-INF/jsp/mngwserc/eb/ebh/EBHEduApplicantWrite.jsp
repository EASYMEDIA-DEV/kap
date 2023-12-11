<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-heading">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
    </div>

    <div class="card-body" data-controller="controller/eb/ebh/EBHEduApplicantWriteCtrl">
        <form class="form-horizontal" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.ptcptSeq}" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnDto.memSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="episdOrd" name="episdOrd" value="${rtnDto.episdOrd}" />
            <input type="hidden" class="notRequired" id="episdYear" name="episdYear" value="${rtnDto.episdYear}" />

            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>과정정보</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정 분류<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${rtnDto.stdPrtCateNm} > ${rtnDto.stdCateNm}
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${rtnDto.nm}
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 방식<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.stduyMthdNm}
                    </div>
                    <label class="col-sm-1 control-label">학습 시간<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.stduyDd}일 / ${rtnDto.stduyTime}시간
                    </div>
                </div>
            </fieldset>

            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>회차 정보</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.episdOrd}회차
                    </div>
                    <label class="col-sm-1 control-label">업종<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.cbsnNm}
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                        ~
                        ${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                    </div>
                    <label class="col-sm-1 control-label">교육기간<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                        ~
                        ${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">강사<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        <c:forEach var="insName" items="${rtnDto.insNameList}" varStatus="status">
                            ${ insName }<c:if test="${ not status.last }">, </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">정원<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.fxnumCnt }명
                    </div>
                    <label class="col-sm-1 control-label">모집방식<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.rcrmtMthd}
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">문의담당자<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.picNm } / ${ rtnDto.picEmail } / ${ rtnDto.picTelNo }
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.placeName }
                    </div>
                </div>
            </fieldset>

            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>신청자 정보</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.name }(${ rtnDto.id })
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control input-sm" value="${rtnDto.email}" id="email" name="email" title="이메일" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="deptCd" id="dept" title="부서">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD02')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5 pr0">
                                <input type="text" class="form-control input-sm" value="${rtnDto.deptDtlNm}" id="deptDtlNm" name="deptDtlNm" title="부서 상세" maxlength="50" />
                            </div>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="pstnCd" id="pos" title="직급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD01')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5 pr0" <c:if test="${not rtnDto.pstnNm}">style="display: none"</c:if> id="pstnNmChk">
                                <input type="text" class="form-control input-sm" value="${rtnDto.pstnNm}" id="pstnNm" name="pstnNm" title="직급 상세" maxlength="50" />
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.hpNo }
                    </div>
                    <label class="col-sm-1 control-label">일반 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnDto.telNo}" id="telNo" name="telNo" title="일반 전화번호" maxlength="50" />
                    </div>
                </div>
            </fieldset>

            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>부품사 정보</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.cmpnNm }
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="partsCtgryCd" id="partsCtgry" title="구분">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.partsCtgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="sizeCd" id="size" title="규모">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY02')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.rprsntNm }
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm datetimepicker_input" style="width:100px;" id="stbsmDt" name="stbsmDt" data-name="stbsmDt" value="${not empty rtnDto.stbsmDt ? kl:convertDate(rtnDto.stbsmDt, 'yyyyMMdd', 'yyyy-MM-dd', '') : today}" title="설립일자" readonly onclick="cmmCtrl.initCalendar(this);"/>
                            <span class="input-group-btn" style="z-index:0; width: auto;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnDto.cmpnTelNo}" id="cmpnTelNo" name="cmpnTelNo" title="회사 전화번호" maxlength="50" />
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ kl:bsnmNoConvert(rtnDto.ptcptBsnmNo) }
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnDto.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="우편번호">
                            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDto.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="기본주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="상세주소">
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <input type="text" class="form-control input-sm notRequired" value="${rtnDto.slsPmt}" id="slsPmt" name="slsPmt" title="매출액" maxlength="50" />
                            </div>
                            <div class="col-sm-1 pr0" style="padding-top: 7px">억원</div>
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm notRequired" data-name="slsYear" title="연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnData.slsYear eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 pr0" style="padding-top: 7px">년</div>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mpleCnt}" id="mpleCnt" name="mpleCnt" title="직원수" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct1}" id="mjrPrdct1" name="mjrPrdct1" title="주생산품1" maxlength="50" />
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct2}" id="mjrPrdct2" name="mjrPrdct2" title="주생산품2" maxlength="50" />
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct3}" id="mjrPrdct3" name="mjrPrdct3" title="주생산품3" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">품질5스타</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="qlty5StarCd" id="qlty5StarCd" title="품질5스타 코드">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.qlty5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm notRequired" data-name="qlty5StarYear" title="납입5스타 연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnData.qlty5StarYear eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 pr0" style="padding-top: 7px">년</div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">납입5스타</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="pay5StarCd" id="pay5StarCd" title="납입5스타 코드">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.pay5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm notRequired" data-name="pay5StarYear" title="납입5스타 연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnData.pay5StarYear eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 pr0" style="padding-top: 7px">년</div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">기술5스타</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm" data-name="tchlg5StarCd" id="tchlg5StarCd" title="기술5스타 코드">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.tchlg5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5 pr0">
                                <select class="form-control input-sm notRequired" data-name="tchlg5StarYear" title="기술5스타 연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnData.tchlg5StarYear eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 pr0" style="padding-top: 7px">년</div>
                        </div>
                    </div>
                </div>
            </fieldset>

        </form>
    </div>
</div>