<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<script>
    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            $(".tabClick:eq(1)").find("a").trigger("click");

        }
    }
</script>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="actionType" value="${not empty rtnDto ? 'update' : 'write'}"/>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebb/EBBEpisdWriteCtrl" data-actionType="${actionType}">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="orgEpisdYear" name="orgEpisdYear" value="${rtnDto.episdYear}" />
            <input type="hidden" class="notRequired" id="orgEpisdOrd" name="orgEpisdOrd" value="${rtnDto.episdOrd}" />
            <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="ctgryCd" name="ctgryCd" value="${rtnDto.ctgryCd}" />

            <input type="hidden" class="notRequired" id="prntCd" name="prntCd" value="${rtnDto.prntCd}" />
            <input type="hidden" class="notRequired" id="copyYn" name="copyYn" value="${rtnDto.copyYn}" />

            <input type="hidden" class="notRequired" id="stduyMthdCd" name="stduyMthdCd" value="${rtnDto.stduyMthdCd}" />
            <input type="hidden" class="notRequired" id="jdgmtYn" name="jdgmtYn" value="${rtnDto.jdgmtYn}" />

            <!-- 첨부파일 순번 -->
            <%--<input type="hidden" class="notRequired" id="edctnNtctnFileSeq" name="edctnNtctnFileSeq" value="${rtnDto.edctnNtctnFileSeq}" />--%>

            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-11">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>과정정보</h6>
                    </div>
                    <!-- 수정시에는 미출력 -->
                    <c:if test="${empty rtnDto}">
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-inverse btn-sm couseSearch">
                                과정검색
                            </button>
                        </div>
                    </c:if>

                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <p class="form-control-static ctgryCdNm">
                            <c:if test="${rtnDto.prntCdNm ne null}">
                                ${rtnDto.prntCdNm} > ${rtnDto.ctgryCdNm}
                            </c:if>
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <p class="form-control-static nm">${rtnDto.nm}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">

                    <label class="col-sm-1 control-label">학습방식<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5 ">
                        <p class="form-control-static stduyMthd">${rtnDto.stduyMthdCdNm}</p>
                    </div>

                    <label class="col-sm-1 control-label">학습시간<span class="star"> *</span></label>
                    <div class="col-sm-5 ">
                        <p class="form-control-static stduyDtm">
                            <c:if test="${rtnDto.stduyDdCdNm ne null}">
                                ${rtnDto.stduyDdCdNm}일/${rtnDto.stduyTimeCdNm} 시간
                            </c:if>
                        </p>
                    </div>

                </div>
            </fieldset>

            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#episdList">회차정보</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>> <a data-toggle="tab" href="#accsList">참여자 목록</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>><a data-toggle="tab" href="#svResult">만족도 결과</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>><a data-toggle="tab" href="#bdget">예산/지출</a></li>
            </ul>

            <div class="tab-content">

                <!-- 회차정보 -->
                <div id="episdList" class="tab-pane fade in active">
                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-12">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>회차</h6>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">연도<span class="star text-danger"> *</span></label>
                            <div class="col-sm-6 form-inline">
                                <select class="form-control input-sm wd-sm" name="episdYear" id="episdYear" title="년도" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>

                                <select class="form-control input-sm wd-sm" name="episdOrd" id="episdOrd" title="회차" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.ROUND_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdOrd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <label class="col-sm-1 control-label">업종<span class="star text-danger"> *</span></label>
                            <div class="col-sm-2">
                                <select class="form-control input-sm wd-sm" name="cbsnCd" id="cbsnCd" title="업종">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.CBSN_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">접수기간</label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="accsStrtDt" id="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="접수 시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsStrtHour" title="접수 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_endDt" name="accsEndDtm" id="accsEndDt" value="${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="접수 종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsEndHour" id="accsEndHour" title="접수 종료시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육기간</label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="edctnStrtDt" id="edctnStrtDt" value="${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="edctnStrtHour" id="edctnStrtHour" title="교육 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_endDt" name="edctnEndDt" id="edctnEndDt" value="${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="edctnEndHour" id="edctnEndHour" title="교육 종료시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">강사<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm eduIsttrSearch">
                                    강사검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">번호</th>
                                        <th class="text-center">이름</th>
                                        <th class="text-center">소속</th>
                                        <th class="text-center">약력(특이사항)</th>
                                        <th class="text-center">삭제</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="isttrContainer">
                                    <tr data-total-count="0" class="notIsttr">
                                        <td colspan="5" class="text-center" <c:if test="${isttrList.size() ne 0}">style="display:none;"</c:if>>
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    <tr class="setIsttr" data-total-count="0" style="display:none;">
                                        <td class="text-center">
                                        </td>
                                        <td class="text-center">
                                        </td>
                                        <td class="text-center">
                                        </td>
                                        <td class="text-center">
                                        </td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-sm btn-danger btnOneTrRemove">삭제</button>
                                        </td>
                                        <input type="hidden" class="notRequired" name="isttrSeq" id="isttrSeq" value="" disabled="true" titlle="강사번호">
                                    </tr>
                                    <c:choose>
                                        <c:when test="${isttrList.size() ne 0}">
                                            <c:forEach var="list" items="${isttrList}" varStatus="status">
                                                <tr>
                                                    <td class="text-center">
                                                        ${isttrList.size() - status.index}
                                                    </td>
                                                    <td class="text-center">
                                                            ${list.name} - 이름
                                                    </td>
                                                    <td class="text-center">
                                                            ${list.ffltnNm} - 소속
                                                    </td>
                                                    <td class="text-center">
                                                            ${list.spclCntn} - 약력
                                                    </td>
                                                    <td class="text-center">
                                                        <button type="button" class="btn btn-sm btn-danger btnOneTrRemove">삭제</button>
                                                    </td>
                                                    <input type="hidden" class="notRequired" name="isttrSeq" value="${list.isttrSeq}" disabled="true" titlle="강사번호">
                                                </tr>
                                            </c:forEach>

                                        </c:when>
                                    </c:choose>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">정원<span class="star text-danger"> *</span></label>
                            <div class="col-sm-2">
                                <label class="input_line form-inline">
                                    <input type="text" class="form-control input-sm numberChk" id="fxnumCnt" name="fxnumCnt" value="${rtnDto.fxnumCnt}" title="정원수" maxlength="50" placeholder="정원수 입력" style="max-width: 150px;"/> 명
                                </label>

                            </div>
                            <div class="col-sm-1">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle notRequired" value="N"  name="fxnumImpsbYn"  <c:if test="${rtnDto.fxnumImpsbYn eq 'N'}">checked</c:if> title="제한없음"/>
                                    <span class="ion-checkmark-round"></span> 제한없음
                                </label>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모집방식<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <c:forEach var="cdList" items="${episdCdList.RCRMT_MTHD}" varStatus="status">
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="rcrmtMthdCd" value="${cdList.cd}" title="모집방식" <c:if test="${rtnDto.rcrmtMthdCd eq cdList.cd}">checked</c:if>/>
                                        <span class="ion-record"></span> ${cdList.cdNm}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">문의담당자<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">이름</th>
                                        <th class="text-center">이메일</th>
                                        <th class="text-center">전화번호</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm" id="picNm" name="picNm" value="${rtnDto.picNm}" title="담당자명" maxlength="50" placeholder="담당자명"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm emailChk" id="picEmail" name="picEmail" value="${rtnDto.picEmail}" title="담당자이메일" maxlength="50" placeholder="담당자이메일"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm mobileNumChk" id="picTelNo" name="picTelNo" value="${rtnDto.picTelNo}" title="담당자전화번호" maxlength="50" placeholder="담당자전화번호"/>
                                    </td>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset class="edctnNtctn">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육안내문</label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="edctnNtctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>

                        <input type="hidden" class="notRequired" id="edctnNtctnFileSeq" name="edctnNtctnFileSeq" value="${rtnDto.edctnNtctnFileSeq}" />
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육장소<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm eduRoomSearch">
                                    교육장 검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">교육장명</th>
                                        <th class="text-center">지역</th>
                                        <th class="text-center">주소</th>
                                        <th class="text-center">대표번호</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody>

                                    <tr data-total-count="0" class="notPlace" <c:if test="${roomDto.nm ne ''}">style="display:none;"</c:if>>
                                        <td colspan="4" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    <tr  class="setPlace" <c:if test="${roomDto.nm eq ''}">style="display: none;"</c:if>>
                                        <td class="text-center">
                                            ${roomDto.nm}
                                        </td>
                                        <td class="text-center">
                                            ${roomDto.rgnsName}
                                        </td>
                                        <td class="text-center">
                                            <c:if test="${not empty roomDto.zipcode}">(${roomDto.zipcode})</c:if> ${roomDto.bscAddr}<c:if test="${not empty roomDto.dtlAddr}">, ${roomDto.dtlAddr}</c:if>
                                        </td>
                                        <td class="text-center">
                                            ${roomDto.rprsntTelNo}
                                        </td>
                                        <input type="hidden" class="notRequired" name="placeSeq" id="placeSeq" value="${rtnDto.placeSeq}" disabled="true" titlle="교육장">
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">협력기관</label>
                            <div class="col-sm-11">
                                <input type="text" class="form-control input-sm notRequired" id="cprtnInsttNm" name="cprtnInsttNm" value="${rtnDto.cprtnInsttNm}" title="협력기관" maxlength="200" placeholder="협력기관 입력" />
                            </div>
                        </div>
                    </fieldset>

                    <!--온라인 강의 시작-->
                    <fieldset class="onlineSet" style="display:none;">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">온라인 강의 목차<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">번호</th>
                                        <th class="text-center">강의명</th>
                                        <th class="text-center">유튜브 URL</th>
                                        <th class="text-center">강의 시간</th>
                                        <th class="text-center">추가/삭제</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="onlineList">
                                    <tr class="examTr" style="display: none;">
                                        <td class="text-center" rowspan="2"></td>
                                        <td class="text-center">
                                            <input type="text" class="form-control input-sm notRequired" name="onlineNm" value="" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                        </td><!--강의명-->
                                        <td class="text-center">
                                            <input type="text" class="form-control input-sm notRequired" name="onlineUrl" value="" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                        </td><!--유튜브 URL-->
                                        <td class="text-center">
                                            <input type="text" class="form-control input-sm numberChk notRequired" name="onlineTime" value="" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;"/>분
                                        </td><!--강의시간-->
                                        <td class="text-center">
                                            <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                            <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                        </td><!--삭제-->
                                    </tr>
                                    <tr class="examTr" style="display: none;">
                                        <td class="text-center" colspan="4">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                ※ 파일확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                            </p>
                                        </td>
                                    </tr>

                                    <c:choose>
                                        <c:when test="${lctrDtoList.size()>0}">
                                            <!-- 온라인강의 목차 출력 s -->
                                            <c:forEach var="lctrDtoList" items="${lctrDtoList}" varStatus="status">
                                                <tr>
                                                    <td class="text-center" rowspan="2">${status.count }</td>
                                                    <td class="text-center">
                                                        <input type="text" class="form-control input-sm notRequired" name="onlineNm" value="${lctrDtoList.nm}" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                                    </td><!--강의명-->
                                                    <td class="text-center">
                                                        <input type="text" class="form-control input-sm notRequired" name="onlineUrl" value="${lctrDtoList.url}" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                                    </td><!--유튜브 URL-->
                                                    <td class="text-center">
                                                        <input type="text" class="form-control input-sm numberChk notRequired" name="onlineTime" value="${lctrDtoList.time}" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;"/>분
                                                    </td><!--강의시간-->
                                                    <td class="text-center">
                                                        <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                                        <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                                    </td><!--삭제-->
                                                </tr>
                                                <tr>
                                                    <td class="text-center" colspan="4">
                                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                        <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq${lctrDtoList.thnlFileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                            <div class="dz-default dz-message">
                                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                            </div>
                                                        </div>
                                                        <p class="text-bold mt">
                                                            ※ 파일확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                        </p>
                                                    </td>
                                                </tr>

                                                <input type="hidden" class="notRequired thnlFileForm" id="thnlFileSeq" name="lctrFileSeq${lctrDtoList.thnlFileSeq}" value="${lctrDtoList.thnlFileSeq}" />
                                            </c:forEach>
                                            <!-- 온라인강의 목차 출력 e -->
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td class="text-center" rowspan="2">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                                                <td class="text-center">
                                                    <input type="text" class="form-control input-sm notRequired" name="onlineNm" value="${list.url}" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                                </td><!--강의명-->
                                                <td class="text-center">
                                                    <input type="text" class="form-control input-sm notRequired" name="onlineUrl" value="${list.url}" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                                </td><!--유튜브 URL-->
                                                <td class="text-center">
                                                    <input type="text" class="form-control input-sm numberChk notRequired" name="onlineTime" value="${list.time}" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;"/>분
                                                </td><!--강의시간-->
                                                <td class="text-center">
                                                    <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                                    <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                                </td><!--삭제-->
                                            </tr>
                                            <tr>
                                                <td class="text-center" colspan="4">
                                                    <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                    <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                    <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                        <div class="dz-default dz-message">
                                                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                        </div>
                                                    </div>
                                                    <p class="text-bold mt">
                                                        ※ 파일확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                    </p>
                                                </td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                    <!--온라인 강의 끝-->

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">만족도조사<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm eduSrvSearch">
                                    설문 검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">설문유형</th>
                                        <th class="text-center">제목</th>
                                        <th class="text-center">설문기간</th>
                                        <th class="text-center"></th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer3">
                                        <tr>
                                            <td class="text-center">${rtnDto.srvNm}</td>
                                            <td class="text-center">${rtnDto.typeNm}</td>
                                            <td class="text-center">
                                                <div class="input-group form-date-group mr-sm">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt" name="srvStrtDtm" id="srvStrtDtm" value="${ kl:convertDate(rtnDto.srvStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문시작일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                        <span class="input-group-addon bg-white b0">~</span>
                                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="srvEndDtm" id="srvEndDtm" value="${ kl:convertDate(rtnDto.srvEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문종료일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>

                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-inverse btn-sm srvReset">
                                                    설문 초기화
                                                </button>
                                            </td>
                                        </tr>
                                    <%--<tr data-total-count="0">
                                        <td colspan="4" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>--%>
                                        <input type="hidden" class="notRequired" name="srvSeq" id="srvSeq" value="${rtnDto.srvSeq}" disabled="true">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>


                    <fieldset class="jdgmtYn" style="display:none;">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">평가<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm eduExamSearch">
                                    평가 검색
                                </button>

                                <label class="checkbox-inline c-checkbox pull-right">
                                    <input type="checkbox" class="checkboxSingle notRequired otsdExamPtcptYn" value="Y"  name="otsdExamPtcptYn"  <c:if test="${rtnDto.otsdExamPtcptYn eq 'Y'}">checked</c:if> title="오프라인평가"/>
                                    <span class="ion-checkmark-round"></span> 오프라인평가
                                </label>

                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center" colspan="4">제목</th>
                                        <th class="text-center" colspan="6">평가기간</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody>
                                    <tr data-total-count="0" class="notExg" <c:if test="${rtnDto.examNm ne ''}">style="display:none;"</c:if>>
                                        <td colspan="1" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    <tr class="setExg" <c:if test="${rtnDto.examNm eq ''}">style="display: none;"</c:if>>
                                        <td class="text-center" colspan="4">${rtnDto.examNm}</td>
                                        <td colspan="6">
                                            <div class="input-group form-date-group mr-sm">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="examStrtDtm" id="examStrtDtm" value="${ kl:convertDate(rtnDto.examStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시험시작일시" readonly="readonly"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                <span class="input-group-addon bg-white b0">~</span>
                                                <input type="text" class="form-control input-sm datetimepicker_endDt" name="examEndDtm" id="examEndDtm" value="${ kl:convertDate(rtnDto.examEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시험종료일시" readonly="readonly"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                            </div>

                                        </td>
                                    </tr>
                                    <input type="hidden" class="notRequired" name="examSeq" id="examSeq" value="${rtnDto.examSeq}" disabled="true" title="평가">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>


                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">수료 자동화 여부<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                    <c:set var="cmptnAutoYn" value="${kl:nvl(rtnDto.cmptnAutoYn, 'Y')}" />
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmptnAutoYn" value="Y" title="수료 자동화 여부" <c:if test="${cmptnAutoYn eq 'Y'}">checked</c:if>/>
                                        <span class="ion-record"></span> 자동
                                    </label>

                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmptnAutoYn" value="N" title="수료 자동화 여부" <c:if test="${cmptnAutoYn eq 'N'}">checked</c:if>/>
                                        <span class="ion-record"></span> 수동
                                    </label>
                            </div>
                        </div>
                    </fieldset>
                </div>

                <!-- 참여자 목록-->
                <div id="accsList" class="tab-pane fade">

                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-11">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>참여자 목록</h6>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset class="ptcptField">
                        <div class="clearfix">
                            <h6 class="pull-left mt0">
                                <em class="ion-play mr-sm"></em>교육 참여자 목록 (총 <span id="ptcptListContainerTotCnt">0</span> 건)
                            </h6>
                            <!-- 현재 페이징 번호 -->
                            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
                            <!-- 페이징 버튼 사이즈 -->
                            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
                            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />

                            <div class="pull-right ml-sm">
                                <select class="form-control input-sm listRowSizeContainer" >
                                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                        <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                    </jsp:include>
                                </select>
                            </div>
                            <div class="pull-right">
                                <button type="button" class="btn btn-default btn-sm mb-sm" id="btnEdctnAtndc">출석부</button>
                                <button type="button" class="btn btn-default btn-sm mb-sm" id="btnAddApp">신청자 등록</button>
                                <button type="button" class="btn btn-info btn-sm mb-sm" id="btnExcel">엑셀 다운로드</button>
                            </div>
                        </div>

                        <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                            <table class="table table-hover table-striped" >
                                <thead>
                                <tr>
                                    <th class="text-center">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxAll notRequired" title="전체선택" name="ptcptChk"/>
                                            <span class="ion-checkmark-round"></span>
                                        </label>
                                    </th>
                                    <th class="text-center">번호</th>
                                    <th class="text-center">아이디</th>
                                    <th class="text-center">이름</th>
                                    <th class="text-center">부품사명</th>
                                    <th class="text-center">구분</th>
                                    <th class="text-center">사업자등록번호</th>
                                    <th class="text-center">휴대폰번호</th>
                                    <th class="text-center">이메일</th>
                                    <th class="text-center">가입일</th>
                                    <th class="text-center">교육신청일</th>
                                    <th class="text-center">교육상태</th>
                                    <th class="text-center">출석</th>
                                    <th class="text-center">평가</th>
                                    <th class="text-center">수료</th>

                                </tr>
                                </thead>

                                <!-- 리스트 목록 결과 -->
                                <tbody id="ptcptListContainer"/>

                            </table>
                            <!-- 페이징 버튼 -->
                            <div id="ptcptPagingContainer"/>
                        </div>
                    </fieldset>




                </div>

                <!-- 만족도 결과 -->
                <div id="svResult" class="tab-pane fade">

                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-11">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>설문정보</h6>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">설문명<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static">
                                    ${rtnDto.srvNm}
                                </p>
                            </div>
                            <label class="col-sm-1 control-label">설문기간<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static">
                                    ${kl:convertDate(rtnDto.srvStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} ~ ${kl:convertDate(rtnDto.srvEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육 참여자<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static">
                                    <c:if test="${rtnDto.edctnMemCnt ne ''}">${rtnDto.edctnMemCnt}명</c:if>
                                </p>
                            </div>
                            <label class="col-sm-1 control-label">설문 참여자<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static">
                                    <c:if test="${rtnDto.srvMemCnt ne ''}">${rtnDto.srvMemCnt}명</c:if>
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">설문 참여율<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <p class="form-control-static">
                                        <c:choose>
                                            <c:when test="${rtnDto.edctnMemCnt ne 0 && rtnDto.srvMemCnt ne 0}">
                                                <fmt:parseNumber var= "cnt" integerOnly= "true" value= "${(rtnDto.srvMemCnt/rtnDto.edctnMemCnt)*100}" />
                                                ${cnt}%
                                            </c:when>
                                            <c:otherwise>
                                                0%
                                            </c:otherwise>
                                        </c:choose>
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="table-responsive ">
                                <table class="table text-sm">
                                    <tbody>
                                        <tr>
                                            <th colspan="9">부서별 인원</th><th colspan="7">직급별 인원</th>
                                        </tr>
                                        <tr>
                                            <th>품질</th>
                                            <th>R&D</th>
                                            <th>생산</th>
                                            <th>구매</th>
                                            <th>경영지원</th>
                                            <th>업체평가</th>
                                            <th>안전</th>
                                            <th>ESG</th>
                                            <th>기타</th>

                                            <th>대표</th>
                                            <th>임원</th>
                                            <th>부장</th>
                                            <th>과장/차장</th>
                                            <th>사원/대리</th>
                                            <th>조장/반창(계장)</th>
                                            <th>기타</th>
                                        </tr>

                                        <c:choose>
                                            <c:when test="${not empty srvRstDtl}">
                                                <fmt:parseNumber var= "tPer1" integerOnly= "true" value= "${(srvRstDtl.t1/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer2" integerOnly= "true" value= "${(srvRstDtl.t2/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer3" integerOnly= "true" value= "${(srvRstDtl.t3/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer4" integerOnly= "true" value= "${(srvRstDtl.t4/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer5" integerOnly= "true" value= "${(srvRstDtl.t5/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer6" integerOnly= "true" value= "${(srvRstDtl.t6/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer7" integerOnly= "true" value= "${(srvRstDtl.t7/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer8" integerOnly= "true" value= "${(srvRstDtl.t8/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "tPer9" integerOnly= "true" value= "${(srvRstDtl.t9/rtnDto.srvMemCnt)*100}" />

                                                <fmt:parseNumber var= "aPer1" integerOnly= "true" value= "${(srvRstDtl.a1/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer2" integerOnly= "true" value= "${(srvRstDtl.a2/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer3" integerOnly= "true" value= "${(srvRstDtl.a3/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer4" integerOnly= "true" value= "${(srvRstDtl.a4/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer5" integerOnly= "true" value= "${(srvRstDtl.a5/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer6" integerOnly= "true" value= "${(srvRstDtl.a6/rtnDto.srvMemCnt)*100}" />
                                                <fmt:parseNumber var= "aPer7" integerOnly= "true" value= "${(srvRstDtl.a7/rtnDto.srvMemCnt)*100}" />
                                                <tr>
                                                    <td class="text-center">${srvRstDtl.t1}명(${tPer1}%)</td>
                                                    <td class="text-center">${srvRstDtl.t2}명(${tPer2}%)</td>
                                                    <td class="text-center">${srvRstDtl.t3}명(${tPer3}%)</td>
                                                    <td class="text-center">${srvRstDtl.t4}명(${tPer4}%)</td>
                                                    <td class="text-center">${srvRstDtl.t5}명(${tPer5}%)</td>
                                                    <td class="text-center">${srvRstDtl.t6}명(${tPer6}%)</td>
                                                    <td class="text-center">${srvRstDtl.t7}명(${tPer7}%)</td>
                                                    <td class="text-center">${srvRstDtl.t8}명(${tPer8}%)</td>
                                                    <td class="text-center">${srvRstDtl.t9}명(${tPer9}%)</td>

                                                    <td class="text-center">${srvRstDtl.a1}명(${aPer1}%)</td>
                                                    <td class="text-center">${srvRstDtl.a2}명(${aPer2}%)</td>
                                                    <td class="text-center">${srvRstDtl.a3}명(${aPer3}%)</td>
                                                    <td class="text-center">${srvRstDtl.a4}명(${aPer4}%)</td>
                                                    <td class="text-center">${srvRstDtl.a5}명(${aPer5}%)</td>
                                                    <td class="text-center">${srvRstDtl.a6}명(${aPer6}%)</td>
                                                    <td class="text-center">${srvRstDtl.a7}명(${aPer7}%)</td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>

                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                    <td class="text-center">0명(0%)</td>
                                                </tr>
                                            </c:otherwise>

                                        </c:choose>

                                    </tbody>
                                </table>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="table-responsive ">
                                <table class="table text-sm">
                                    <tbody>
                                    <tr>
                                        <th>종합평균</th>
                                        <th>전체(공통)</th>
                                        <th>교육환경</th>
                                        <th>교육지원</th>
                                        <th>교육내용</th>
                                        <th>강사평가</th>
                                    </tr>
                                    <tr>
                                        <td class="text-center">5.0</td>
                                        <td class="text-center">5.0</td>
                                        <td class="text-center">5.0</td>
                                        <td class="text-center">5.0</td>
                                        <td class="text-center">5.0</td>
                                        <td class="text-center">5.0</td>

                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                    </fieldset>












                </div>

                <!-- 예산/지출 -->
                <div id="bdget" class="tab-pane fade">

                    <!--공통코드 데이터 + 입력된 데이터-->
                    <div class="bdgetTargetData" style="display:none;" data-type="bdget01">
                        <c:forEach var="cdList" items="${ED_BDGET_CD01}" varStatus="status">

                            <c:set var="tempPmt" value="" />
                            <c:set var="tempClass" value="numberChk" />
                            <c:forEach var="bdgetList" items="${bdgetList}">
                                <c:if test="${tempPmt eq ''}">
                                    <c:if test="${cdList.cd eq bdgetList.cd}">
                                        <c:if test="${cdList.cd eq 'ED_BDGET_CD01011' }">
                                            <c:set var="tempClass" value="" />
                                            <c:set var="tempPmt" value="${bdgetList.etcNm}" />
                                        </c:if>
                                        <c:if test="${cdList.cd ne 'ED_BDGET_CD01011' }">
                                            <c:set var="tempPmt" value="${bdgetList.pmt}" />
                                        </c:if>

                                    </c:if>
                                    <c:if test="${cdList.cd ne bdgetList.cd}">
                                        <c:set var="tempPmt" value="" />
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <input type="text" class="form-control input-sm calPmtForm bdget01 numberChk notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="50" placeholder="${cdList.cdNm} 입력" />
                        </c:forEach>
                    </div>
                    <div class="bdgetTargetData" style="display:none;" data-type="bdget02">
                        <c:forEach var="cdList" items="${ED_BDGET_CD02}" varStatus="status">

                            <c:set var="tempPmt" value="" />
                            <c:forEach var="bdgetList" items="${bdgetList}">
                                <c:if test="${tempPmt eq ''}">
                                    <c:if test="${cdList.cd eq bdgetList.cd}">
                                        <c:if test="${cdList.cd eq 'ED_BDGET_CD02011' }">
                                            <c:set var="tempClass" value="" />
                                            <c:set var="tempPmt" value="${bdgetList.etcNm}" />
                                        </c:if>
                                        <c:if test="${cdList.cd ne 'ED_BDGET_CD02011' }">
                                            <c:set var="tempPmt" value="${bdgetList.pmt}" />
                                        </c:if>

                                    </c:if>
                                    <c:if test="${cdList.cd ne bdgetList.cd}">
                                        <c:set var="tempPmt" value="" />
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <input type="text" class="form-control input-sm calPmtForm bdget02 numberChk notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="50" placeholder="${cdList.cdNm} 입력" />
                        </c:forEach>
                    </div>

                    <div class="copyBdgetForm" style="display: none;">
                            <label class="col-sm-1 control-label">부담금/대관비<span class="star"> *</span></label>
                            <div class="">
                            </div>
                    </div>

                    <fieldset class="copyTitle" style="display: none;">
                        <div class="form-group text-sm">
                            <div class="col-sm-12">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">교육예산<span> (<span class="pmt">10000원</span>)</h6>
                            </div>
                        </div>
                    </fieldset>

                    <!--복사용 끝-->

                    <!-- 협력기관 지출내역 -->

                    <!-- 강사 강의시간 -->
                </div>
            </div>

            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출여부" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출여부" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${fn:replace(strPam, 'copyYn=Y', 'copyYn=N')}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                            <tr>
                                <th>최초 작성자</th>
                                <td>${ rtnDto.regName }</td>
                                <th>최초 작성일</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${ not empty rtnDto.modName }">
                                            ${ rtnDto.modName }
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <th>최종 수정일</th>
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
        </form>




    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/eb/eba/EBACouseSrchLayer.jsp"></jsp:include><!--교육과정검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebf/EBFEduRoomSrchLayer.jsp"></jsp:include><!--교육장검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/ex/exg/EXGExamListSrchLayer.jsp"></jsp:include><!--시험검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpc/MPCLecturerSrchLayer.jsp"></jsp:include><!--강사검색-->
<!--설문검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/sv/sva/SVASurveySrchLayer.jsp">
<jsp:param name="typeCd" value="EDU" />
</jsp:include>

<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebb/EBBAtndcLayer.jsp"></jsp:include><!--출석부 레이어 팝업-->
