
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
<c:set var="actionType" value=""/>
<!-- actionType은 기본적으로 수정일경우 update이지만 복사버튼을 통해서 들어온경우 write로 변경해줌  -->


<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebi/EBINonMemberWriteCtrl" data-actionType="${actionType}">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} <c:choose><c:when test="${not empty rtnDto and rtnDto.copyYn eq'N'}">상세/수정</c:when><c:otherwise>등록</c:otherwise></c:choose></h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <c:choose>
                <c:when test="${not empty rtnDto}">
                    <c:choose>
                        <c:when test="${rtnDto.copyYn eq'Y'}">
                            <c:set var="actionType" value="write"/>
                            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="" />
                        </c:when>
                        <c:when test="${rtnDto.copyYn eq'N'}">
                            <c:set var="actionType" value="update"/>
                            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:set var="actionType" value="write"/>
                    <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
                </c:otherwise>
            </c:choose>




            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />

            <input type="hidden" class="notRequired" id="prntCd" name="prntCd" value="${rtnDto.prntCd}" />
            <input type="hidden" class="notRequired" id="copyYn" name="copyYn" value="${rtnDto.copyYn}" />

            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#edctnList">교육정보</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>> <a data-toggle="tab" href="#accsList">신청자정보</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>><a data-toggle="tab" href="#bdget">예산/지출</a></li>
            </ul>

            <div class="tab-content">

                <!--  교육정보 -->
                <div id="edctnList" class="tab-pane fade in active" style="flex-wrap:wrap;">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-5 pr0">
                                        <select class="form-control input-sm wd-sm classType" name="cd" id="cd" title="과정분류-대분류">
                                            <option value="-99">선택</option>
                                            <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${rtnDto.prntCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-5 pr0">
                                        <select class="form-control input-sm wd-sm" name="ctgryCd" id="ctgryCd" title="과정분류-소분류" data-ctgrycd="${rtnDto.ctgryCd}">
                                            <option value="">선택</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="row">
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control input-sm" id="nm" name="nm" value="${rtnDto.nm}" title="과정명" maxlength="50" placeholder="과정명 입력" />
                            </div>
                            <label class="col-sm-1 control-label">과정요약<span class="star"> *</span></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control input-sm" id="smmryNm" name="smmryNm" value="${rtnDto.smmryNm}" title="과정요약" maxlength="50" placeholder="과정요약 입력"/>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="accsStrtDt" id="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="접수 시작일" readonly="readonly"/>
                                        <span class="input-group-btn pr-sm" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm" name="accsStrtHour" id="accsStrtHour" title="접수 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="accsEndDtm" id="accsEndDt" value="${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="접수 종료일" readonly="readonly"/>
                                        <span class="input-group-btn pr-sm" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm" name="accsEndHour" id="accsEndHour" title="접수 종료시간">
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
                            <label class="col-sm-1 control-label">교육기간<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="edctnStrtDt" id="edctnStrtDt" value="${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="교육 시작일" readonly="readonly"/>
                                        <span class="input-group-btn pr-sm" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm" name="edctnStrtHour" id="edctnStrtHour" title="교육 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="edctnEndDt" id="edctnEndDt" value="${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="교육 종료일" readonly="readonly"/>
                                        <span class="input-group-btn pr-sm" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm" name="edctnEndHour" id="edctnEndHour" title="교육 종료시간">
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
                                        <tr data-total-count="${ fn:length(isttrList) }" class="notIsttr">
                                            <td colspan="5" class="text-center" <c:if test="${fn:length(isttrList) ne 0}">style="display:none;"</c:if>>
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
                                            <c:when test="${fn:length(isttrList) ne 0}">
                                                <c:forEach var="list" items="${isttrList}" varStatus="status">
                                                    <tr>
                                                        <td class="text-center">
                                                            ${fn:length(isttrList) - status.index}
                                                        </td>
                                                        <td class="text-center">
                                                                ${list.name}
                                                        </td>
                                                        <td class="text-center">
                                                                ${list.ffltnNm}
                                                        </td>
                                                        <td class="text-center">
                                                                ${list.spclCntn}
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
                                    <input type="text" class="form-control input-sm numberChk ${kl:decode(rtnDto.fxnumImpsbYn, 'N', 'notRequired', '')}" id="fxnumCnt" name="fxnumCnt" value="${rtnDto.fxnumCnt}" title="정원수" maxlength="50" placeholder="정원수 입력" style="max-width: 150px;"<c:if test="${rtnDto.fxnumImpsbYn eq 'N'}">readonly</c:if>/> 명
                                </label>
                            </div>
                            <div class="col-sm-1">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle notRequired" value="N" id="fxnumImpsbYn" name="fxnumImpsbYn" title="제한없음"  <c:if test="${rtnDto.fxnumImpsbYn eq 'N'}">checked</c:if>/>
                                    <span class="ion-checkmark-round"></span> 제한없음
                                </label>
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
                                        <input type="text" class="form-control input-sm emailChk" id="picEmail" name="picEmail" value="${rtnDto.picEmail}" title="담당자 이메일" maxlength="50" placeholder="담당자 이메일"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm mobileChk mobileNumChk" id="picTelNo" name="picTelNo" value="${kl:hpNum(rtnDto.picTelNo)}" title="담당자 전화번호" maxlength="13" placeholder="담당자 전화번호"/>
                                    </td>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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
                                    <tr data-total-count="0 ${roomDto}" class="notPlace" <c:if test="${not empty roomDto}">style="display:none;"</c:if>>
                                        <td colspan="4" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    <tr class="setPlace" <c:if test="${empty roomDto}">style="display: none;"</c:if>>
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
                            <label class="col-sm-1 control-label">과정소개<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control input-sm" id="itrdcCntn" name="itrdcCntn" rows="10" maxlength="500" title="과정 소개" placeholder="과정 소개 입력">${rtnDto.itrdcCntn}</textarea>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습 목표<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control input-sm" id="stduyTrgtCntn" name="stduyTrgtCntn" rows="10" maxlength="500" title="학습 목표" placeholder="학습 목표 입력">${rtnDto.stduyTrgtCntn}</textarea>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습 대상<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <c:forEach var="list" items="${edTarget}">
                                    <div class="row" style="margin-bottom: 20px;">
                                        <c:forEach var="targetList" items="${list.edList}">
                                            <c:choose>
                                                <c:when test="${targetList.dpth eq '2' && targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                    <label class="col-sm-1 control-label">${targetList.cdNm}<span class="star"> *</span></label>
                                                    <c:set var="prtName" value="${targetList.cdNm}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                        <label class="checkbox-inline c-checkbox">
                                                            <c:set var="tempChk" value="N" />
                                                            <c:forEach var="rtnTrgtDataList" items="${rtnTrgtData}">
                                                                <c:if test="${tempChk eq 'N'}">
                                                                    <c:if test="${rtnTrgtDataList.targetCd eq targetList.cd}">
                                                                        <c:set var="tempChk" value="Y" />
                                                                    </c:if>
                                                                    <c:if test="${rtnTrgtDataList.targetCd ne targetList.cd}">
                                                                        <c:set var="tempChk" value="N" />
                                                                    </c:if>
                                                                </c:if>
                                                            </c:forEach>
                                                            <input type="checkbox" class="checkboxSingle notRequired" value="${targetList.cd}" name="targetCd" title="학습 대상 - ${prtName}"  <c:if test="${tempChk eq 'Y'}">checked</c:if> />
                                                            <span class="ion-checkmark-round"></span> ${targetList.cdNm}
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${targetList.cdNm eq '기타' && targetList.cd eq 'ED_TARGET05001'}">
                                                        <label class="col-sm-1 control-label">${targetList.cdNm}&nbsp;&nbsp;</label>
                                                        <div class="col-sm-5 ml0 pl0">
                                                            <input type="text" class="form-control input-sm notRequired" id="etcNm" name="etcNm" value="${rtnTrgtData[rtnTrgtData.size()-1].etcNm}" title="기타" maxlength="50" placeholder="미입력 시 사용자 사이트에 하이픈(-)으로 표시"/>
                                                        </div>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습 시간<span class="star"> *</span></label>
                            <div class="col-sm-11" style="margin-left: -70px;">
                                <label class="col-sm-1 control-label">학습일</label>
                                <div class="col-sm-1 mr-xl">
                                    <select class="form-control input-sm wd-sm" name="stduyDdCd" id="stduyDdCd" title="학습일">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${studyCdList.STDUY_DD}" varStatus="status">
                                            <option value="${cdList.cd}" <c:if test="${rtnDto.stduyDdCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}일</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <label class="col-sm-1 control-label ml-sm">학습시간</label>
                                <div class="col-sm-1">
                                    <select class="form-control input-sm wd-sm" name="stduyTimeCd" id="stduyTimeCd" title="학습시간">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${studyCdList.STDUY_TIME}" varStatus="status">
                                            <option value="${cdList.cd}" <c:if test="${rtnDto.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}시간</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습준비물<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <input type="text" class="form-control input-sm" id="stduySuplsNm" name="stduySuplsNm" value="${rtnDto.stduySuplsNm}" title="학습준비물" maxlength="50" placeholder="학습준비물 입력" />
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">협력기관</label>
                            <div class="col-sm-11 form-inline">
                                <input type="text" class="form-control input-sm notRequired" id="cprtnInsttNm" value="${rtnDto.cprtnInsttNm}" title="협력기관" maxlength="50" placeholder="협력기관 입력"  readonly style="width: 250px;" />
                                <input type="hidden" class="form-control input-sm notRequired" name="cprtnInsttSeq" id="cprtnInsttSeq" value="${rtnDto.cprtnInsttSeq}" title="협력기관" maxlength="50" placeholder="협력기관 입력" />
                                <button type="button" class="btn btn-inverse btn-sm cprtnInsttSearch">
                                    협력기관 검색
                                </button>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC 학습내용<span class="star"> *</span></label>
                            <div class="col-sm-11">
                        <textarea maxlength="500" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="PC 학습내용을 입력하세요" id="pcStduyCntn" name="pcStduyCntn" title="PC 학습내용" oninput="cmmCtrl.checkMaxlength(this);">
                            ${rtnDto.pcStduyCntn}
                        </textarea>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모바일 학습내용<span class="star"> *</span></label>
                            <div class="col-sm-11">
                        <textarea maxlength="500" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="모바일 학습내용을 입력하세요" id="mblStduyCntn" name="mblStduyCntn" title="모바일 학습내용" oninput="cmmCtrl.checkMaxlength(this);">
                            ${rtnDto.mblStduyCntn}
                        </textarea>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset class="edctnNtctn">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육안내문</label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="104857600" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="edctnNtctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="교육안내문">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 파일 확장자 pdf, ppt, pptx, doc, docx, xls, xlsx, hwp, hwpx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>

                        <input type="hidden" class="notRequired" id="edctnNtctnFileSeq" name="edctnNtctnFileSeq" value="${rtnDto.edctnNtctnFileSeq}" />
                    </fieldset>

                    <fieldset class="thnl">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">썸네일 이미지</label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="104857600" />
                                <div class="dropzone pcThumbFile notRequired" data-file-field-nm="thnlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 파일 확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>

                        <input type="hidden" class="notRequired" id="thnlFileSeq" name="thnlFileSeq" value="${rtnDto.thnlFileSeq}" />
                    </fieldset>

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
                </div>

                <!-- 신청자 목록-->
                <div id="accsList" class="tab-pane fade" style="flex-wrap:wrap;">

                    <%--<fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-11">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 목록</h6>
                            </div>
                        </div>
                    </fieldset>--%>

                    <fieldset class="ptcptField">
                        <div class="clearfix">
                            <h6 class="pull-left mt0">
                                <em class="ion-play mr-sm"></em>신청자 목록 (총 <span id="ptcptListContainerTotCnt">0</span> 건)
                            </h6>
                            <!-- 현재 페이징 번호 -->
                            <input type="hidden" id="pageIndex" name="pageIndex" class="notRequired" value="${ rtnData.pageIndex }" />
                            <!-- 페이징 버튼 사이즈 -->
                            <input type="hidden" id="pageRowSize" name="pageRowSize" class="notRequired" value="${ rtnData.pageRowSize }" />
                            <input type="hidden" id="listRowSize" name="listRowSize" class="notRequired" value="${ rtnData.listRowSize }" />

                            <div class="pull-right ml-sm">
                                <select class="form-control input-sm listRowSizeContainer" >
                                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                        <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                    </jsp:include>
                                </select>
                            </div>
                            <div class="pull-right">
                                <%--<button type="button" class="btn btn-default btn-sm mb-sm" id="btnEdctnAtndc">출석부</button>--%>
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
                                    <th class="text-center">이름</th>
                                    <th class="text-center">부품사명</th>
                                    <th class="text-center">사업자등록번호</th>
                                    <th class="text-center">부서</th>
                                    <th class="text-center">직급</th>
                                    <th class="text-center">휴대폰번호</th>
                                    <th class="text-center">이메일</th>
                                    <th class="text-center">신청일시</th>
                                    <th class="text-center">신청취소</th>
                                </tr>
                                </thead>

                                <!-- 리스트 목록 결과 -->
                                <tbody id="ptcptListContainer"/>

                            </table>
                            <!-- 페이징 버튼 -->
                            <div id="ptcptPagingContainer"/>
                        </div>
                    </fieldset>

                    <div class="clearfix">
                        <div class="pull-left">
                            <button type="button" class="btn btn-info btn-default" id="cancelSelectEdctn">신청취소</button>
                        </div>
                    </div>
                </div>

                <!-- 예산/지출 -->
                <div id="bdget" class="tab-pane fade" style="flex-wrap:wrap;">
                    <div class="bdgetForm0" style="width:100%;order:0">
                        <fieldset>
                            <div class="form-group text-sm">
                                <div class="col-sm-11">
                                    <h6 class="mt0"><em class="ion-play mr-sm"></em>실적 마감 여부</h6>
                                </div>
                            </div>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">마감여부<span class="star text-danger"> *</span></label>
                                <div class="col-sm-11">
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="bdgetExpnsYn" value="Y" title="예산지출교육여부" <c:if test="${rtnDto.bdgetExpnsYn eq 'Y'}">checked</c:if>/>
                                        <span class="ion-record"></span> 마감
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="bdgetExpnsYn" value="N" title="예산지출교육여부" <c:if test="${rtnDto.bdgetExpnsYn eq 'N' or empty rtnDto}">checked</c:if>/>
                                        <span class="ion-record"></span> 미마감
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="bdgetExpnsYn" value="C" title="예산지출교육여부" <c:if test="${rtnDto.bdgetExpnsYn eq 'C'}">checked</c:if>/>
                                        <span class="ion-record"></span> 교육취소
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                    </div>


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
                                <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">교육예산</span></h6>
                            </div>
                        </div>
                    </fieldset>

                    <!--복사용 끝-->

                    <!-- 협력기관 지출내역 -->
                    <div class="bdgetForm3" style="width:100%;order:3">
                        <fieldset>
                            <div class="form-group text-sm">
                                <div class="col-sm-12">
                                    <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">협력기관 지출내역 (합계 : <span>0</span>원)</span></h6>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">협력기관<span class="star"> *</span></label>
                                <div class="col-sm-11">
                                    <div class="row" style="margin-bottom: 20px;">
                                        <div class="form-group text-sm">
                                            <div class="col-sm-5 form-inline">
                                                <input type="text" class="form-control input-sm notRequired" id="expnsCprtnInsttNm" name="expnsCprtnInsttNm" value="${rtnDto.expnsCprtnInsttNm}" readonly title="지출협업기관명" maxlength="50" placeholder="지출협업기관명" style=width:250px;"/>
                                                <input type="hidden" class="form-control input-sm notRequired" name="expnsCprtnInsttSeq" id="expnsCprtnInsttSeq" value="${rtnDto.expnsCprtnInsttSeq}" title="지출협력기관" maxlength="50" />
                                                <button type="button" class="btn btn-inverse btn-sm expnsCprtnInsttSearch">
                                                    협력기관 검색
                                                </button>
                                            </div>
                                            <div class="col-sm-6">
                                                <label class="col-sm-1 control-label">지출금액<span class="star"> *</span></label>
                                                <div class="col-sm-4 form-inline">
                                                    <input type="text" class="form-control input-sm numberChk notRequired" id="expnsPmt" name="expnsPmt" value="${rtnDto.expnsPmt}" title="지출금액" maxlength="50" placeholder="지출금액" style=width:250px;"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                    </div>

                    <!--강사시간용 데이터 세팅-->
                    <c:set var="ED_BDGET_CD03001" value="" />
                    <c:set var="ED_BDGET_CD03002" value="" />
                    <c:set var="ED_BDGET_CD03003" value="" />
                    <c:set var="ED_BDGET_CD03004" value="" />
                    <c:set var="ED_BDGET_CD03005" value="" />
                    <c:set var="ED_BDGET_CD03006" value="" />
                    <c:forEach var="bdgetList" items="${bdgetList}">
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03001' }"><c:set var="ED_BDGET_CD03001" value="${bdgetList.pmt}" /></c:if>
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03002' }"><c:set var="ED_BDGET_CD03002" value="${bdgetList.pmt}" /></c:if>
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03003' }"><c:set var="ED_BDGET_CD03003" value="${bdgetList.pmt}" /></c:if>
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03004' }"><c:set var="ED_BDGET_CD03004" value="${bdgetList.pmt}" /></c:if>
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03005' }"><c:set var="ED_BDGET_CD03005" value="${bdgetList.pmt}" /></c:if>
                        <c:if test="${bdgetList.cd eq 'ED_BDGET_CD03006' }"><c:set var="ED_BDGET_CD03006" value="${bdgetList.pmt}" /></c:if>
                    </c:forEach>
                    <!-- 강사 강의시간 -->
                    <div class="bdgetForm4" style="width:100%;order:4">
                        <fieldset>
                            <div class="form-group text-sm">
                                <div class="col-sm-12">
                                    <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">강사 강의시간</span></h6>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">강사 강의시간</label>
                                <div class="col-sm-11">
                                    <div class="row" style="margin-bottom: 20px;">
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">강사1</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03001" value="${ED_BDGET_CD03001}" title="강사1" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                            <label class="col-sm-1 control-label">강사2</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03002" value="${ED_BDGET_CD03002}" title="강사2" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                            <label class="col-sm-1 control-label">강사3</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03003" value="${ED_BDGET_CD03003}" title="강사3" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row" style="margin-bottom: 20px;">
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">강사4</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03004" value="${ED_BDGET_CD03004}" title="강사4" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                            <label class="col-sm-1 control-label">강사5</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03005" value="${ED_BDGET_CD03005}" title="강사5" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                            <label class="col-sm-1 control-label">강사6</label>
                                            <div class="col-sm-3 form-inline">
                                                <input type="text" class="form-control input-sm numberChk notRequired" name="ED_BDGET_CD03006" value="${ED_BDGET_CD03006}" title="강사6" maxlength="50" placeholder="강사 시간 입력" style="max-width: 150px;"/> H
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>

                </div>
            </div>

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
            <c:if test="${ not empty rtnDto and rtnDto.copyYn == 'N' }">
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
            </c:if>
        </form>
    </div>

    <!-- 사유 레이어 팝업(Modal) -->
    <div class="modal fade excel-down" tabindex="-1" role="dialog" >
        <div class="modal-dialog modal-lg modal-center" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" >▣ 엑셀 다운로드
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </h5>
                </div>
                <div class="modal-body">
                    <div class="form-group ">
                        <p><em class="ion-play mr-sm"></em>사유입력</p>
                        <div class="col-sm-12">
                            <textarea maxlength="30" class="col-sm-12 pv" style="resize: vertical;" rows="10" placeholder="사유를 입력하세요." id="rsn" title="사유" oninput="cmmCtrl.checkMaxlength(this);"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer row">
                    <div class="text-center">
                        <button type="button" class="btn btn-primary down mt">저장</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpc/MPCLecturerSrchLayer.jsp"></jsp:include><!--강사검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebf/EBFEduRoomSrchLayer.jsp"></jsp:include><!--교육장검색-->
