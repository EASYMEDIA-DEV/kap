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
            <input type="hidden" id="detailsKey" name="detailsKey" value="${rtnDto.ptcptSeq}" />
            <input type="hidden" id="memSeq" name="memSeq" value="${rtnDto.memSeq}" />
            <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}" />
            <input type="hidden" id="episdOrd" name="episdOrd" value="${rtnDto.episdOrd}" />
            <input type="hidden" id="episdYear" name="episdYear" value="${kl:convertDate(rtnDto.episdYear, 'yyyy-MM-dd', 'yyyy', '-')}" />
            <%--<c:if test="${ fn:contains(rtnDto.sttsCd, '04') }">
                <input type="hidden" class="notRequired" id="stts" name="stts" value="N" />
            </c:if>--%>
            <c:if test="${ not fn:contains(rtnDto.sttsCd, '04') }">
                <input type="hidden" class="notRequired" id="sttsCd" name="sttsCd" value="${rtnDto.sttsCd}" />
            </c:if>
            <input type="hidden" id="ptcptBsnmNo" name="ptcptBsnmNo" value="${rtnDto.ptcptBsnmNo}" />

            <br />
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>과정정보</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정 분류<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${rtnDto.stdPrtCateNm} > ${rtnDto.stdCateNm}
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${rtnDto.stdName}
                    </div>
                </div>
            </fieldset>
            <fieldset>
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
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>회차 정보</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${rtnDto.episdOrd}회차
                    </div>
                    <label class="col-sm-1 control-label">업종</label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${not empty rtnDto.cbsnNm ? rtnDto.cbsnNm : '-'}
                    </div>
                </div>
            </fieldset>
            <fieldset>
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
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">강사<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        <c:forEach var="insName" items="${rtnDto.insNameList}" varStatus="status">
                            ${ insName }<c:if test="${ not status.last }">, </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
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
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">문의담당자<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.picNm } / ${ rtnDto.picEmail } / ${ rtnDto.picTelNo }
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.placeName }
                    </div>
                </div>
            </fieldset>

            <br />
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>신청자 정보</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.name }(${ rtnDto.id })
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
<%--                        <input type="text" class="form-control input-sm" value="${rtnDto.email}" id="email" name="email" title="이메일" maxlength="50" />--%>
                        ${rtnDto.email}
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm" data-name="deptCd" id="deptCd" name="deptCd" title="부서" style="width: 200px;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'MEM_CD02') and fn:length(cdList.cd) > 8}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control input-sm" value="${rtnDto.deptDtlNm}" id="deptDtlNm" name="deptDtlNm" title="부서 상세" maxlength="50" style="width: 200px;" />
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm" data-name="pstnCd" id="pstnCd" name="pstnCd" title="직급" style="width: 200px;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'MEM_CD01') and fn:length(cdList.cd) > 8}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.pstnNm}" id="pstnNm" name="pstnNm" title="직급 상세" maxlength="50" style="width: 200px; display: none;" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-4" style="padding-top: 7px">
                        ${ rtnDto.hpNo }
                    </div>
                    <label class="col-sm-1 control-label">일반 전화번호</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.telNo}" id="telNo" name="telNo" title="일반 전화번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="11" placeholder="일반 전화번호 입력" style="width: 200px;" />
                    </div>
                </div>
            </fieldset>

            <br />
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>부품사 정보</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-5" style="padding-top: 7px">
                        ${ kl:bsnmNoConvert(rtnDto.ptcptBsnmNo) }
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5" style="padding-top: 7px">
                        ${ rtnDto.cmpnNm }
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${ rtnDto.rprsntNm }" id="rprsntNm" name="rprsntNm" title="대표자명" maxlength="50" placeholder="대표자명 입력" style="width: 200px;" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm" data-name="partsCtgryCd" name="partsCtgryCd" id="partsCtgryCd" title="구분" style="width: 130px;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.partsCtgryCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm" data-name="sizeCd" id="sizeCd" name="sizeCd" title="규모" style="width: 130px;">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY02')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnDto.cmpnTelNo}" id="cmpnTelNo" name="cmpnTelNo" title="회사 전화번호" maxlength="50" placeholder="회사 전화번호 입력" style="width: 130px;" />
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-3">
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

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <div>
                            <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색">
                        </div>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnDto.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="우편번호">
                            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDto.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="기본주소" style="width: 400px;">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" placeholder="상세주소 입력" maxlength="50" title="상세주소" style="width: 400px;">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.slsPmt}" id="slsPmt" name="slsPmt" title="매출액" maxlength="50" placeholder="매출액 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" style="width: 150px;" />
                        억원
                        <select class="form-control input-sm notRequired" data-name="slsYear" name="slsYear" title="연도">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.slsYear, 'yyyy-MM-dd', 'yyyy', '-') eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mpleCnt}" id="mpleCnt" name="mpleCnt" title="직원수" placeholder="직원수 입력" maxlength="50" />
                        명
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct1}" id="mjrPrdct1" name="mjrPrdct1" title="주생산품1" maxlength="50" placeholder="주생산품(1) 입력" />
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct2}" id="mjrPrdct2" name="mjrPrdct2" title="주생산품2" maxlength="50" placeholder="주생산품(2) 입력" />
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct3}" id="mjrPrdct3" name="mjrPrdct3" title="주생산품3" maxlength="50" placeholder="주생산품(2) 입력" />
                    </div>
                    <%--<div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct1}" id="mjrPrdct1" name="mjrPrdct1" title="주생산품1" maxlength="50" />
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct2}" id="mjrPrdct2" name="mjrPrdct2" title="주생산품2" maxlength="50" />
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.mjrPrdct3}" id="mjrPrdct3" name="mjrPrdct3" title="주생산품3" maxlength="50" />
                    </div>--%>
                </div>
            </fieldset>
            <fieldset class="mb-lg starInfo" <c:if test="${ rtnDto.partsCtgryCd eq 'COMPANY01002'}">style="display: none"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">품질5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" data-name="qlty5StarCd" name="qlty5StarCd" title="품질5스타 코드">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.qlty5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" data-name="qlty5StarYear" name="qlty5StarYear" title="품질5스타 연도" style="width: 100px;">
                            <option value="">연도선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnData.qlty5StarYear, 'yyyy-MM-dd', 'yyyy', '-') eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg starInfo" <c:if test="${ rtnDto.partsCtgryCd eq 'COMPANY01002'}">style="display: none"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">납입5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" data-name="pay5StarCd" name="pay5StarCd" title="납입5스타 코드">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.pay5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" data-name="pay5StarYear" name="pay5StarYear" title="납입5스타 연도" style="width: 100px;">
                            <option value="">연도선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnData.pay5StarYear, 'yyyy-MM-dd', 'yyyy', '-') eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg starInfo" <c:if test="${ rtnDto.partsCtgryCd eq 'COMPANY01002'}">style="display: none"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">기술5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired" data-name="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타 코드">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.tchlg5StarCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired" data-name="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타 연도" style="width: 100px;">
                            <option value="">연도선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnData.tchlg5StarYear, 'yyyy-MM-dd', 'yyyy', '-') eq cdList.cd}">selected</c:if>>${cdList.cd}</option>
                            </c:forEach>
                        </select>
                        년
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg sqInfo" <c:if test="${ rtnDto.partsCtgryCd eq 'COMPANY01001'}">style="display: none"</c:if>>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">SQ 정보</label>
                    <div class="col-sm-5">
                    <c:forEach var="sqData" varStatus="status" begin="0" end="2">
                        <input type="hidden" class="notRequired" name="sqInfoList${ status.count }" value="${rtnDto.sqList[status.index].cbsnSeq}" />
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.sqList[status.index].nm}" name="sqInfoList${ status.count }" title="SQ 업종명" maxlength="50" placeholder="SQ업종" />
                        <input type="text" class="form-control input-sm notRequired sqScore" value="${rtnDto.sqList[status.index].score}" name="sqInfoList${ status.count }" title="SQ 점수" maxlength="50" placeholder="SQ점수" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
                        <select class="form-control input-sm notRequired" data-name="sqInfoList${ status.count }" name="sqInfoList${ status.count }" title="SQ 평가년도">
                            <option value="">연도선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.sqList[status.index].year eq cdList.cd}">selected</c:if>>${cdList.cd}년</option>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.sqList[status.index].crtfnCmpnNm}" name="sqInfoList${ status.count }" title="SQ 인증 주관사명" maxlength="50" placeholder="SQ인증주관사" />
                        <c:if test="${ not status.last }"><br /></c:if>
                    </c:forEach>
                    </div>
                </div>
            </fieldset>

            <br />
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>GPC 아이디</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 아이디<span class="star"> *</span></label>
                    <div class="col-sm-9" style="padding-top: 7px">
                        ${ rtnDto.gpcId }
                    </div>
                </div>
            </fieldset>

            <br />
            <br />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>선발상태</h7>
            <hr />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">선발상태<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm" data-name="sttsCd" name="sttsCd" title="선발상태" <c:if test="${ not (fn:contains(rtnDto.sttsCd, '04')) }">disabled</c:if>>
                            <%--<option value="">선택</option>--%>
                            <c:forEach var="cdList" items="${cdDtlList.EDU_STTS_CD}" varStatus="status">
                                <c:choose>
                                    <c:when test="${ fn:contains(cdList.cd, '01') }">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>선발</option>
                                    </c:when>
                                    <c:when test="${ fn:contains(cdList.cd, '04') }">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>선발대기</option>
                                    </c:when>
                                    <c:when test="${ fn:contains(cdList.cd, '05') }">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>미선발</option>
                                    </c:when>
                                    <c:when test="${ not (fn:contains(rtnDto.sttsCd, '04')) }">
                                        <c:choose>
                                            <c:when test="${ fn:contains(cdList.cd, '02') }">
                                                <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>신청취소</option>
                                            </c:when>
                                            <c:when test="${ fn:contains(cdList.cd, '03') }">
                                                <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>신청양도</option>
                                            </c:when>
                                            <c:when test="${ fn:contains(cdList.cd, '06') }">
                                                <option value="${cdList.cd}" <c:if test="${rtnDto.sttsCd eq cdList.cd}">selected</c:if>>교육취소</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="" selected>알 수 없음</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="" selected>알 수 없음</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-5" style="padding-top: 7px">
                        ※ 선발여부 설정 후 수정이 불가능하오니 주의바랍니다
                    </div>
                </div>
            </fieldset>

            <br />
            <br />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>

            <br />
            <br />
            <br />
            <br />

            <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
            <div class="table-responsive ">
                <table class="table text-sm">
                    <colgroup>
                        <col style="width:15%;">
                        <col style="width:35%;">
                        <col style="width:15%;">
                        <col style="width:35%;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>최종 수정자</th>
                            <td>${not empty rtnDto.modName ? rtnDto.modName += '(' += rtnDto.modId += ')' : '-'}</td>
                            <th>최종 수정일시</th>
                            <td>${not empty rtnDto.modDtm ? kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') : '-'}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </form>
    </div>
</div>