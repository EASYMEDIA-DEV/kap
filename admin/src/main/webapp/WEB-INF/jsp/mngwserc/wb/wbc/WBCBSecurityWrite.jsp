
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbc/WBCBSecurityWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>관리자 등록</h6>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <h6 class="mt0">회차 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="episdYear" name="year" title="연도">
                                <option value="">선택</option>
                                <c:forEach var="rtnYear" items="${rtnYear}" varStatus="status">
                                    <option value="${rtnYear}">${rtnYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="episd" name="episd" title="회차">
                                <option value="">선택</option>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>


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
                            <input type="hidden" id="memSeq" class="notRequired" name="memSeq" value="" />
                            <input type="text" class="form-control" id="mem" value="${rtnDto.nm}" title="신청자" readonly/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <p id="email" class="form-control-static"></p>
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
                                <c:forEach var="cdList" items="${classTypeList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD020')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-4" style="margin-left: -15px">
                            <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="" title="부서명" maxlength="50"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4" style="margin-left: -15px;">
                            <input type="text" style="display:none;" class="form-control notRequired" id="pstnNm" name="pstnNm" value="" title="직급명" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p id="hpNo" class="form-control-static"></p>
                    </div>

                    <label class="col-sm-1 control-label">일반 전화번호</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control telNumber notRequired" id="telNo" name="telNo" value="${rtnDto.nm}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" title="전화번호"/>
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
                        <p id="cmpnNm" class="form-control-static"></p>
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
                                <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY01001')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                    <c:if test="${fn:contains(cdList.cd, 'COMPANY01002')}">
                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="befeCtgryCd" class="notRequired" value=""/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
                            <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="규모">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
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
                        <div class="col-sm-3" style="margin-left: -15px">
                            <p class="form-control-static" id="rprsntNm" ></p>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="col-sm-3" style="margin-left: -15px">
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
                            <input type="text" class="form-control telNumber" id="compTel" name="compTel" value="${rtnDto.smmryNm}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" title="회사 전화번호" placeholder="회사 전화번호 입력"/>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="hidden" id="bsnmNoPut" name="bsnmNo" value="" />
                        <p class="form-control-static" id="bsnmNo"></p>
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
                            <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnDto.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDto.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">매출액(연도)</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm numberChk notRequired" id="slsPmt" name="slsPmt" value="" title="매출액" maxlength="50" placeholder="매출액"/>
                        &nbsp;억원&nbsp;
                        <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.episd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        &nbsp;년&nbsp;
                    </div>

                    <label class="col-sm-1 control-label">직원수</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm numberChk notRequired" id="mpleCnt" name="mpleCnt" value="" title="직원수" maxlength="50" placeholder="직원수 입력"/>
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

            <fieldset class="companyCate1">
                <div class="form-group text-sm form-inline">
                    <label class="col-sm-1 control-label">품질5스타</label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm notRequired starType" id="qlty5starCd" name="qlty5starCd">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.episd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="qlty5starYear" name="qlty5starYear">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.episd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
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
                        <select class="form-control input-sm notRequired starType" id="pay5starCd" name="pay5starYear">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="pay5starYear" name="pay5starYear">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
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
                        <select class="form-control input-sm notRequired starType" id="tchlg5starCd" name="tchlg5starCd">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.COMPANY_TYPE}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'COMPANY03')}">
                                    <option value="${cdList.cd}" >${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm notRequired starType" id="tchlg5starYear" name="tchlg5starYear">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
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
                        <input type="text" id="nm0" class="form-control input-sm notRequired SQ" name="nm" value="" maxlength="50" placeholder="SQ업종"/>
                        <input type="text" id="score0" class="form-control input-sm numberChk notRequired SQ" name="score" value="" maxlength="50" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" name="companyYear">
                            <option value="" >선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm0" class="form-control input-sm notRequired SQ" name="crtfnCmpnNm" value="" maxlength="50" placeholder="SQ인증주관사"/>

                        <br><br>

                        <input type="text" id="nm1" class="form-control input-sm notRequired SQ" name="nm" value="" maxlength="50" placeholder="SQ업종"/>
                        <input type="text" id="score1" class="form-control input-sm numberChk notRequired SQ" name="score" value="" maxlength="50" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" name="companyYear">
                            <option value="" >선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm1" class="form-control input-sm notRequired SQ" name="crtfnCmpnNm" value="" maxlength="50" placeholder="SQ인증주관사"/>

                        <br><br>

                        <input type="text" id="nm2" class="form-control input-sm notRequired SQ" name="nm" value="" maxlength="50" placeholder="SQ업종"/>
                        <input type="text" id="score2" class="form-control input-sm numberChk notRequired SQ" name="score" value="" maxlength="50" placeholder="SQ점수"/>
                        <select class="form-control input-sm notRequired SQ" name="companyYear">
                            <option value="" >선택</option>
                            <c:forEach var="cdList" items="${classCityTypeList.CO_YEAR_CD}" varStatus="status">
                                <option value="${cdList.cd}" >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <input type="text" id="crtfnCmpnNm2" class="form-control input-sm notRequired SQ" name="crtfnCmpnNm" value="" maxlength="50" placeholder="SQ인증주관사"/>
                    </div>
                </div>
            </fieldset>






            <h6 class="mt0">사업신청 정보</h6>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">종된사업장번호</label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm notRequired" id="sbrdnBsnmNo" name="sbrdnBsnmNo" value="" title="종된사업장번호" maxlength="50"/>
                        </div>
                    </div>
                </div>
            </fieldset>


            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구축사업장<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-gray" id="searchPostCode2" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                            <input type="text" class="form-control input-sm" id="zipcode2" name="pbsnZipcode" value="" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                            <input type="text" class="form-control input-sm" id="bscAddr2" name="pbsnbscAddr" value="" readonly placeholder="구축사업장 기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr2" name="pbsnDtlAddr" value="" placeholder="구축사업장 상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="104857600" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
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
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>
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
