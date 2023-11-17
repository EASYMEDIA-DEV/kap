<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/cb/cba/CBATechGuidanceWriteCtrl">
        <h6 class="mt0">신청부품사 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.vslSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="introFileSeq" name="introFileSeq" value="${rtnDto.introFileSeq}" />
            <input type="hidden" class="notRequired" id="actPlFileSeq" name="actPlFileSeq" value="${rtnDto.actPlFileSeq}" />
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm notRequired" readonly="readonly" name="name" title="신청자 정보">
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-sm btn-info">회원검색</button>
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" name="email" value="${rtnDto.email}" maxlength="50" title="이메일"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer">
                            <c:forEach var="memCd" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(memCd.cd,'CD02')  && memCd.cd ne 'MEM_CD02'}">
                                    <option>${memCd.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm" name="deptDtlNm" value="${rtnDto.email}" maxlength="50" title="부서">
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm listRowSizeContainer" name="pstnCd" title="직급">
                            <c:forEach var="memCd" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(memCd.cd,'CD01') && memCd.cd ne 'MEM_CD01'}">
                                    <option value="${memCd.cd}">${memCd.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <p class="form-control-static">hpNo</p>
                    </div>
                    <label class="col-sm-1 control-label">전화번호</label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control input-sm notRequired"  name="memTelNo" value="" title="전화번호"/>
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
                        <p class="form-control-static">에이테크</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm listRowSizeContainer ctgryCd" name="ctgryCd">
                            <c:forEach var="cpType" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cpType.cd,'COMPANY01') && cpType.cd ne 'COMPANY01'}">
                                    <option value="${cpType.cd}">${cpType.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">규모</label>
                    <div class="col-sm-4">
                        <select class="form-control input-sm listRowSizeContainer" name="sizeCd">
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
                        <input type="text" class="form-control input-sm notRequired" name="rprsntNm" value="" title="대표자명"/>
                    </div>
                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="input-group col-md-2" style="z-index:0;">
                        <input type="text" class="form-control datetimepicker_strtDt"  name="stbsmDt" value="${kl:convertDate(rtnDto.stbsmDt, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '')}" title="설립일자" />
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
                        <input type="text" class="form-control input-sm"  name="telNo" value="" title="전화번호"/>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star">*</span></label>
                    <div class="col-sm-4">
                        <p class="form-control-static">bsnmNo</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">본사 주소<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" readonly="readonly" id="hqZipcode" name="zipcode" value="" title="우편번호"/>
                        <input type="text" class="form-control input-sm" readonly="readonly" id="hqBscAddr" name="bscAddr" value="" title="기본 주소"/>
                        <input type="text" class="form-control input-sm"  id="hqDtlAddr" name="dtlAddr" value="" title="상세 주소" />
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
                        <input type="number" class="form-control input-sm notRequired" name="slsPmt" value="" title="매출액"/>
                    </div>
                    <label class="col-sm-1 control-label">억원</label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm listRowSizeContainer" name="slsYear">
                            <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                    <option value="${yearList.cd}">${yearList.cdNm} </option>
                                    </label>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">년</label>
                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-3">
                        <input type="number" class="form-control input-sm notRequired" title="직원수" name="mpleCnt" value="">
                    </div>
                    <label class="col-sm-1 control-label">명</label>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주생산품</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct1" value="" title="주생산품1"/>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct2" value="" title="주생산품2"/>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="mjrPrdct3" value="" title="주생산품3"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="fiveStar"> <%--구분이 1차일 때--%>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">품질5스타</label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer" name="qlty5StarCd">
                                <c:forEach var="qltyList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(qltyList.cd, 'COMPANY03') && qltyList.cd ne 'COMPANY03'}">
                                        <option>${qltyList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer" name="qlty5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}">${yearList.cd} </option>
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
                            <select class="form-control input-sm listRowSizeContainer" name="pay5StarCd">
                                <c:forEach var="payList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(payList.cd, 'COMPANY03') && payList.cd ne 'COMPANY03'}">
                                        <option value="${payList.cd}">${payList.cdNm} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer" name="pay5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}">${yearList.cd} </option>
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
                            <select class="form-control input-sm listRowSizeContainer" name="tchlg5StarCd">
                                <c:forEach var="tchList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(tchList.cd, 'COMPANY03') && tchList.cd ne 'COMPANY03'}">
                                        <option value="${tchList.cd}">${tchList.cdNm} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control input-sm listRowSizeContainer" name="tchlg5StarYear">
                                <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                        <option value="${yearList.cd}">${yearList.cd} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-1 control-label">년</label>
                    </div>
                </fieldset>
            </fieldset>
            <fieldset class="sqInfo" style="display: none"><%--구분이 2차일 때--%>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">SQ정보</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="nm" value=""/>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="score" value=""/>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer" name="year">
                            <c:forEach var="yearList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                <c:if test="${yearList.cd ne 'CO_YEAR_CD'}">
                                    <option value="${yearList.cd}">${yearList.cdNm} </option>
                                    </label>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control input-sm notRequired"  name="crtfnCmpnNm" value=""/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <span class="dtl-tab" style="float:right"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>상주기술지도 신청정보</h6>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청일자<span class="star"> *</span></label>
                    <div class="input-group col-md-2" style="z-index:0;">
                        <input type="text" class="form-control datetimepicker_strtDt"  name="appctnDt" value="${kl:convertDate(rtnDto.appctnDt, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '')}"title="신청일자" />
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
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-sm btn-info cmpnPlus">업체추가</button>
                            <div class="row tempRow" id="dlvryRow" style="border:solid; border-width:1px; margin-top: 10px;">
                                <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                                <label class="col-sm-3 control-label">
                                    <input type="text" class="form-control input-sm"  name="dlvryCmpnNm" value="" style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                                </label>
                                <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                                <label class="col-sm-3 control-label">
                                    <input type="number" class="form-control input-sm"  name="dlvryRate" value="" style="margin-bottom: 10px; margin-top:10px" title="매출비중"/>
                                </label>
                                <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                                <label class="col-sm-1 control-label closeLabel" style="display: none">
                                    <button type="button" class="close" aria-label="Close">x</button>
                                </label>
                            </div>
                        </div>
                    </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">완성차 의존율<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-sm btn-info dpndnPlus">업체추가</button>
                        <div class="row dpTempRow" id="dpndnRow" style="border:solid; border-width:1px; margin-top: 10px">
                            <label class="col-sm-2 control-label" style="margin-top: 15px;">업체명</label>
                            <label class="col-sm-3 control-label">
                                <input type="text" class="form-control input-sm"  name="dpndnCmpnNm" value="" style="margin-bottom: 10px; margin-top:10px" title="업체명"/>
                            </label>
                            <label class="col-sm-2 control-label" style="margin-top: 15px;">매출비중</label>
                            <label class="col-sm-3 control-label">
                                <input type="number" class="form-control input-sm"  name="dpndnRate" value="" style="margin-bottom: 10px; margin-top:10px" title="매출비중"/>
                            </label>
                            <label class="col-sm-1 control-label" style="margin-top: 15px;">%</label>
                            <label class="col-sm-1 control-label closeLabel" style="display: none">
                                <button type="button" class="close" aria-label="Close">x</button>
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">품질담당인원</label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control input-sm notRequired" name="qltyPicCnt" value=""/>
                    </div>
                    <label class="col-sm-1 control-label">FAX<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control input-sm" name="faxNo" value="" title="FAX"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">지도요청 공장주소<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" readonly="readonly" id="zipcode" name="fctryZipcode" value="" title="우편번호" placeholder="우편번호"/>
                        <input type="text" class="form-control input-sm" readonly="readonly" id="bscAddr" name="fctryBscAddr" value="" title="기본주소" placeholder="기본주소"/>
                        <input type="text" class="form-control input-sm notRequired" id="dtlAddr" name="fctryDtlAddr" value="" title="상세주소" placeholder="상세주소 입력"/>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-sm btn-warning searchPostCode factAddr">우편번호 검색</button>
                    </div>
                    <div class="col-sm-1">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="notRequired addrSame"  name="cmpnAddrSameYn" value="N" title="주소 동일 여부"/>
                            <span class="ion-checkmark-round"></span> 본사와 동일
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">소재지역<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer">
                            <option>선택</option>
                            <option>2차사</option>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control input-sm listRowSizeContainer">
                            <option>선택</option>
                            <option>2차사</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표자 승인여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="rprsntApprvYn" value="Y" checked/>
                            <span class="ion-record"></span> 승인
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="rprsntApprvYn" value="N" />
                            <span class="ion-record"></span> 미승인
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청사유<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="appctnRsnCd" value="N" checked/>
                            <span class="ion-record"></span> 자발적 신청
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="appctnRsnCd" value="N"/>
                            <span class="ion-record"></span> 1차부품사 권유
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="appctnRsnCd" value="N"/>
                            <span class="ion-record"></span> 완성차 권유
                        </label>
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
                                        <input type="radio" name="cnstgCd" value="${cdList.cd}" title="업종"/>
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
                                        <input type="radio" class="notRequired" name="cnstgCd" value="${cdList.cd}" title="업종"/>
                                        <span class="ion-record"></span> ${cdList.cdNm}
                                    </label>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <label class="col-sm-2 control-label">기타</label>
                        </div>
                        <div class="col-sm-5 control-label" style="padding-left: 300px;">
                            <input type="text" class="form-control input-sm notRequired"  name="cnstgCd" value="" title="업종"/>
                        </div>
                    </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청사항<span class="star"> *</span></label>
                    <div>
                        <c:forEach var="cdCtnList" items="${cdDtlList.TEC_GUIDE_APPCTN}" varStatus="status">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="appctnFldCd"  name="appctnFldCd" value="${cdCtnList.cd}" title="신청사항"/>
                                <span class="ion-checkmark-round"></span> ${cdCtnList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사소개서 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone pcThumbFile" data-file-field-nm="introFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="회사소개서 첨부파일">
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
                    <label class="col-sm-1 control-label">개선활동 추진계획서<br> 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone moThumbFile" data-file-field-nm="actPlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="MO 썸네일 이미지">
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
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto}">
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