<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpe/MPEPartsCompanyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.bsnmNo}" />
            <input type="hidden" class="notRequired" id="eduListContainerTotCnt" name="eduListContainerTotCnt" value="" />

            <c:if test="${not empty rtnInfo.bsnmNo}">
                <ul class="nav nav-tabs" id="myTabs">
                    <li class="active tabClick"><a data-toggle="tab" href="#dtl">부품사 기본정보</a></li>
                    <li class="tabClick"><a data-toggle="tab" href="#edu">부품사 실적정보</a></li>
                    <span class="dtl-tab" style="margin-left:55%"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                </ul>
            </c:if>
            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                        <div class="text-left mb-xl"><h5>부품사 관리 상세/수정</h5></div>
                        <fieldset>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control input-sm" id="bsnmNo" name="bsnmNo" value="${rtnInfo.bsnmNo}" title="사업자번호등록번호" maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g, '')" placeholder="사업자등록번호 입력" <c:if test="${not empty rtnInfo.bsnmNo or not empty rtnInfo }">readonly</c:if> style="width: 220px;"/>
                                    <button type="button" class="btn btn-default btn-sm" id="btnBsnmNo">인증</button> <span>※ 사업자등록번호 인증 시 중복 여부를 확인 합니다.</span>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">부품사명<span class="star text-danger"> *</span></label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm" id="cmpnNm" name="cmpnNm" value="${rtnInfo.cmpnNm}" title="부품사명" maxlength="50" style="width: 200px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">대표자명<span class="star text-danger"> *</span></label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm notRequired" id="rprsntNm" name="rprsntNm" value="${rtnInfo.rprsntNm}" title="대표자명" style="width: 200px;" placeholder="대표자명 입력" maxlength="50" oninput="this.value=this.value.replace(/[^a-zA-Zㄱ-힣]/g, '')"/>
                                </div>

                                <label class="col-sm-1 control-label">부품사코드</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm notRequired" id="cmpnCd" name="cmpnCd" value="${rtnInfo.cmpnCd}" title="부품사코드" maxlength="20" placeholder="부품사코드 입력" style="width: 220px;"  oninput="this.value=this.value.replace(/[ㄱ-힣]/g, '')"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">구분<span class="star text-danger"> *</span></label>
                                <div class="col-sm-5">
                                    <div class="col-sm-3" style="margin-left: -15px">
                                        <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분" <c:if test="${not empty info}">disabled</c:if>>
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
                                <label class="col-sm-1 control-label">회사 전화번호<span class="star text-danger"> *</span></label>
                                <div class="col-sm-5">
                                    <div class="col-sm-3" style="margin-left: -15px">
                                        <input type="text" class="form-control input-sm phoneChk" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" maxlength="50" placeholder="회사 전화번호 입력"/>
                                    </div>
                                </div>
                                <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                                <div class="col-sm-2 mr-sm" style="width:11%">
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                               value="${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"
                                               readonly title="설립일자" />
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
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                                <div class="col-sm-11">
                                    <input type="button" class="btn btn-sm btn-gray" id="searchPostCode" value="우편번호 검색"><br>
                                    <br>
                                    <div style="display: flex; gap: 10px;">
                                        <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnInfo.zipcode}" readonly placeholder="우편번호" style="width: 95px;" maxlength="50" title="우편번호"/>
                                        <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" maxlength="50" title="기본주소"/>
                                    </div>
                                    <br>
                                    <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnInfo.dtlAddr}" title="상세주소" placeholder="상세주소 입력" maxlength="50" style="width: 400px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">매출액(연도)</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm numberChk notRequired" id="slsPmt" name="slsPmt" value="${rtnInfo.slsPmt}" title="매출액" placeholder="매출액 입력" maxlength="50" oninput="this.value=this.value.replace(/[^0-9]/g, '')"/> 억원
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
                                    <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')"/> 명
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">주생산품</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품1" maxlength="50" placeholder="주생산품(1) 입력"/>
                                    <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품2" maxlength="50" placeholder="주생산품(2) 입력"/>
                                    <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품3" maxlength="50" placeholder="주생산품(3) 입력"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset class="qlty5StarArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01002' or rtnInfo.ctgryCd eq 'COMPANY01003' or rtnInfo.ctgryCd eq 'COMPANY01004'}">style="display:none;"</c:if>>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">품질5스타</label>
                                <div class="col-sm-5">
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and empty rtnInfo.qlty5StarCd
                                                                            and not empty rtnInfo.qlty5StarYear
                                     ? '' : 'notRequired'} firstSt" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" style="width:auto;">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and not empty rtnInfo.qlty5StarCd
                                                                            and  empty rtnInfo.qlty5StarYear
                                     ? '' : 'notRequired'} firstSt" id="qlty5StarYear" name="qlty5StarYear" title="품질5스타연도" style="width: 100px;">
                                        <option value="">연도 선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarYear eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>  년
                                </div>
                            </div>
                        </fieldset>
                        <fieldset class="pay5StarArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01002' or rtnInfo.ctgryCd eq 'COMPANY01003' or rtnInfo.ctgryCd eq 'COMPANY01004'}">style="display:none;"</c:if>>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">납입5스타</label>
                                <div class="col-sm-5">
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and empty rtnInfo.pay5StarCd
                                                                            and not empty rtnInfo.pay5StarYear
                                     ? '' : 'notRequired'}  firstSt" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and not empty rtnInfo.pay5StarCd
                                                                            and  empty rtnInfo.pay5StarYear
                                     ? '' : 'notRequired'}  firstSt" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" style="width: 100px;">
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
                        <fieldset class="tchlg5StarArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01002' or rtnInfo.ctgryCd eq 'COMPANY01003' or rtnInfo.ctgryCd eq 'COMPANY01004'}">style="display:none;"</c:if>>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">기술5스타</label>
                                <div class="col-sm-5">
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and empty rtnInfo.tchlg5StarCd
                                                                            and not empty rtnInfo.tchlg5StarYear
                                     ? '' : 'notRequired'}  firstSt" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01001'
                                                                            and not empty rtnInfo.tchlg5StarCd
                                                                            and  empty rtnInfo.tchlg5StarYear
                                     ? '' : 'notRequired'} firstSt" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" style="width:100px;">
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
                        <fieldset class="sqInfoArea" <c:if test="${rtnInfo.ctgryCd eq null or rtnInfo.ctgryCd eq 'COMPANY01001' or rtnInfo.ctgryCd eq 'COMPANY01003' or rtnInfo.ctgryCd eq 'COMPANY01004'}">style="display:none;"</c:if>>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">SQ 정보</label>
                                <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-11" style="padding-top: 10px;padding-bottom: 10px;">
                                        <input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value="${list.cbsnSeq}"/>
                                        <input type="text" class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01002'
                                                                            and empty list.nm
                                                                            and( not empty list.score
                                                                            or not empty list.year
                                                                            or not empty list.crtfnCmpnNm)
                                     ? '' : 'notRequired'}  secondNd" id="nm${status.count}" name="sqInfoList${status.count}" value="${list.nm}" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                        <input type="text" class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01002'
                                                                            and empty list.score
                                                                            and( not empty list.nm
                                                                            or not empty list.year
                                                                            or not empty list.crtfnCmpnNm)
                                     ? '' : 'notRequired'} secondNd" id="score${status.count}" name="sqInfoList${status.count}" value="${list.score}" title="SQ 점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                        <select class="form-control  input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01002'
                                                                            and empty list.year
                                                                            and( not empty list.nm
                                                                            or not empty list.score
                                                                            or not empty list.crtfnCmpnNm)
                                     ? '' : 'notRequired'} secondNd" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm ${ rtnInfo.ctgryCd eq 'COMPANY01002'
                                                                            and empty list.crtfnCmpnNm
                                                                            and( not empty list.nm
                                                                            or not empty list.year
                                                                            or not empty list.score)
                                     ? '' : 'notRequired'} secondNd" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="${list.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                    </div>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${sqInfoListCnt == 0}">
                                        <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                            <label class="col-sm-1 control-label"></label>
                                            <div class="col-sm-11" style="padding-top: 10px;padding-bottom: 10px;">
                                                <input type="hidden" class="notRequired" id="cbsnSeq${ status.count}" name="sqInfoList${status.count}" value=""/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="nm${status.count}" name="sqInfoList${status.count}" value="" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="score${status.count}" name="sqInfoList${status.count}" value="" title="점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                                <select class="form-control input-sm notRequired secondNd" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도">
                                                    <option value="">선택</option>
                                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                        <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                ${cdList.cdNm}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="" title="인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${sqInfoListCnt != 3 and sqInfoListCnt > 0}">
                                        <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                            <label class="col-sm-1 control-label"></label>
                                            <div class="col-sm-11" style="padding-top: 10px;padding-bottom: 10px;">
                                                <input type="hidden" class="notRequired" id="cbsnSeq${status.count + 1}" name="sqInfoList${status.count + 1}" value=""/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="nm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="SQ 업종" placeholder="SQ업종" maxlength="50"/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="score${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="SQ 점수" placeholder="SQ점수" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                                <select class="form-control input-sm notRequired secondNd" id="year${status.count + 1}" name="sqInfoList${status.count + 1}" title="평가년도">
                                                    <option value="">선택</option>
                                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                        <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                ${cdList.cdNm}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="crtfnCmpnNm${status.count + 1}" name="sqInfoList${status.count + 1}" value="" title="SQ 인증주관사명" placeholder="SQ인증주관사" maxlength="50"/>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${sqInfoListCnt == 3}">
                                        <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                            <label class="col-sm-1 control-label"></label>
                                            <div class="col-sm-11" style="padding-top: 10px;padding-bottom: 10px;">
                                                <input type="hidden" class="notRequired" id="cbsnSeq${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value=""/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="nm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="score${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="점수" placeholder="SQ 점수입력" maxlength="50" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                                                <select class="form-control input-sm notRequired secondNd" id="year${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" title="평가년도">
                                                    <option value="">SQ 평가년도 선택</option>
                                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                        <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                ${cdList.cdNm}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <input type="text" class="form-control input-sm notRequired secondNd" id="crtfnCmpnNm${sqInfoListCnt}" name="sqInfoList${sqInfoListCnt}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">관리자메모</label>
                                <div class="col-sm-5">
                                    <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" title="관리자메모" placeholder="관리자 메모 입력" maxlength="500">${rtnInfo.admMemo}</textarea>
                                </div>
                            </div>
                        </fieldset>
                        <div class="clearfix">
                            <div class="pull-left">
                                <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                            </div>
                            <div style="float:right">
                                <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                            </div>
                        </div>
                        <hr />
                        <c:if test="${ not empty rtnInfo }">
                            <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                            <div class="table-responsive">
                                <table class="table text-sm">
                                    <tbody>
                                    <tr>
                                        <th>최초 등록자</th>
                                        <td>
                                            ${ empty rtnDto.regUserName ? rtnDto.regName : rtnDto.regUserName }(${ rtnDto.regId })
                                        </td>
                                        <th>최초 등록일시</th>
                                        <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                                    </tr>
                                    <tr>
                                        <th>최종 수정자</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ not empty rtnDto.modId }">
                                                    ${ empty rtnDto.modUserName ? rtnDto.modName : rtnDto.modUserName }(${ rtnDto.modId })
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <th>최종 수정일시</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ not empty rtnDto.modDtm }">
                                                    ${ kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div id="edu" class="tab-pane fade">
                    <h6 class="mt-lg">종합현황</h6>
                    <div class="table-responsive">
                        <table class="table">
                            <colgroup>
                                <col style="width:20%;">
                                <col style="width:20%;">
                                <col style="width:20%;">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th class="bg-gray-lighter">교육사업</th>
                                <th class="bg-gray-lighter">컨설팅사업</th>
                                <th class="bg-gray-lighter">상생사업</th>
                            </tr>
                            <tr>
                                <td class="text-center">${eduCnt}</td>
                                <td class="text-center">${consultCnt}</td>
                                <td class="text-center">${winBusinessCnt}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            교육참여
                        </h6>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">구분</th>
                                <th class="text-center">누계</th>
                                <th class="text-center">2024년</th>
                                <th class="text-center">2023년</th>
                                <th class="text-center">2022년</th>
                                <th class="text-center">2021년</th>
                                <th class="text-center">2020년</th>
                                <th class="text-center">2019년 이전</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="eduStatisticsListContainer"/>
                        </table>
                    </div>

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            기술지도
                        </h6>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">업종</th>
                                <th class="text-center">지도위원</th>
                                <th class="text-center">지도기간</th>
                                <th class="text-center">초도방문자료</th>
                                <th class="text-center">킥오프자료</th>
                                <th class="text-center">랩업자료</th>
                                <th class="text-center">회사소개자료</th>
                                <th class="text-center">공정개선율</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="techGuidanceListContainer"/>
                        </table>
                    </div>
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            경영컨설팅
                        </h6>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">업종</th>
                                <th class="text-center">지도위원</th>
                                <th class="text-center">지도기간</th>
                                <th class="text-center">초도방문자료</th>
                                <th class="text-center">킥오프자료</th>
                                <th class="text-center">랩업자료</th>
                                <th class="text-center">회사소개자료</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="manageConsultContainer"/>
                        </table>
                    </div>
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            자금지원
                        </h6>
                        <h6 class="pull-right mt0">
                            단위: 원
                        </h6>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">구분</th>
                                <th class="text-center">추천위원</th>
                                <th class="text-center">재단지원금</th>
                                <th class="text-center">총투자비</th>
                                <th class="text-center">실지급일</th>
                                <th class="text-center">장비명</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="fundingListContainer"/>
                        </table>
                    </div>
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            자동차부품산업대상
                        </h6>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">년도</th>
                                <th class="text-center">지원결과</th>
                                <th class="text-center">훈격</th>
                                <th class="text-center">포상부문</th>
                                <th class="text-center">포상금액(만원)</th>
                                <th class="text-center">부품사명</th>
                                <th class="text-center">수상자명</th>
                                <th class="text-center">직급</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="carTargetListContainer"/>
                        </table>
                    </div>
                </div>
            </div>
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