<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<script>
    window.onpageshow = function(event) {
        var reloadYn = sessionStorage.getItem("reloadYn");//저장후 리로드 여부를 체크하여 리로드시 탭 변경 해줌
        if(!(reloadYn === undefined) && reloadYn == "Y"){

            var activeIndex = (sessionStorage.getItem("activeIndex")!="" && !(sessionStorage.getItem("activeIndex") === undefined) )  ? sessionStorage.getItem("activeIndex") : 0;

            $(".tabClick:eq("+activeIndex+")").find("a").trigger("click");
            sessionStorage.removeItem("reloadYn");//새로고침할때마다  하면안되니까 세션 삭제해줌
            sessionStorage.removeItem("activeIndex");
        }
        /*if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            $(".tabClick:eq(1)").find("a").trigger("click");

        }*/
    }
</script>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="modifyYn" value="Y" />

<%
    Date now = new Date();
    pageContext.setAttribute("now", now);
%>

<fmt:formatDate pattern="yyyy-MM-dd" value="${now}" var="nowDate"/>
<c:set var="edctnStrtDt" value="${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }"/>
<c:set var="edctnEndDt" value="${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }"/>





<!-- 신청자 발생시  modifyYn : N으로 변경되고 N일경우 온라인교육 수정이 불가하다 -->
<!-- 추가로 이 경우에는 강사, 만족도조사, 평가, 노출여부만 수정 가능하다. -->
<!--교육이 시작했을경우 노출여부만 수정 가능하다.-->

<c:choose>
    <c:when test="${not empty rtnDto && rtnDto.accsCnt ne 0}">
        <c:set var="modifyYn" value="N" />
    </c:when>
    <c:otherwise>
        <c:set var="modifyYn" value="Y" />
    </c:otherwise>
</c:choose>

<!--교육중이면 modifyYn도 N으로 강제변경한다 이 상태는 가장 제약이 심한 상태값임-->
<c:set var="eduIng" value="N" />
<c:if test="${edctnStrtDt le nowDate && nowDate le edctnEndDt}">
    <c:set var="eduIng" value="Y" />
    <c:set var="modifyYn" value="N" />
</c:if>


<c:set var="actionType" value=""/>
<!-- actionType은 기본적으로 수정일경우 update이지만 복사버튼을 통해서 들어온경우 write로 변경해줌  -->


<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebb/EBBEpisdWriteCtrl" data-actionType="${actionType}">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} ${not empty rtnDto ? '상세/수정' : '등록'}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <input type="hidden" class="notRequired" id="modifyYn" name="modifyYn" value="${modifyYn}" />
            <input type="hidden" class="notRequired" id="eduIng" name="eduIng" value="${eduIng}" />

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




            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}"/>
            <input type="hidden" class="notRequired" id="orgEpisdYear" name="orgEpisdYear" value="${rtnDto.episdYear}" />
            <input type="hidden" class="notRequired" id="orgEpisdOrd" name="orgEpisdOrd" value="${rtnDto.episdOrd}" />
            <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}"/>
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

            <ul class="nav nav-tabs" id="myTabs" data-action-type="${actionType}"><%-- 2024.05.09 data 속성 추가 --%>
                <li class="active tabClick"><a data-toggle="tab" href="#episdList">회차정보</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>> <a data-toggle="tab" href="#accsList">참여자 목록</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>><a data-toggle="tab" href="#svResult">만족도 결과</a></li>
                <li class="tabClick" <c:if test="${actionType ne 'update'}">style="display:none"</c:if>><a data-toggle="tab" href="#bdget">예산/지출</a></li>

                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto && rtnDto.copyYn eq 'N'}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_end_edu">강제종강</button>
                        </c:when>
                    </c:choose>
                </div>


            </ul>

            <div class="tab-content">

                <!-- 회차정보 -->

                <div id="prevEpisd" <c:if test="${ actionType eq 'update'}">style="display:none;"</c:if> >
                    <table class="table table-hover table-striped">
                        <tr>
                            <td colspan="12"  rowspan="4" class="text-center">과정 선택후 등록 가능합니다.</td>
                        </tr>
                    </table>
                </div>

                <div id="episdList" class="tab-pane fade in active" style="flex-wrap:wrap;<c:if test="${actionType ne 'update'}">display:none;</c:if> ">
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

                                <%
                                    Date nowDate = new Date();
                                    pageContext.setAttribute("nowDate", nowDate);//현재 시간
                                %>
                                <fmt:formatDate pattern="yyyy" value="${nowDate}" var="nowDate" />

                                <c:set var="beforeYear" value="${nowDate-20}"/>
                                <c:set var="nowYear" value="${nowDate}"/>
                                <c:set var="afterYear" value="${nowDate+20}"/>
                                <select class="form-control input-sm wd-sm notRequired" name="episdYear" id="episdYear" title="년도" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="year" begin="${beforeYear}" end="${afterYear}">
                                        <c:choose>
                                            <c:when test="${not empty rtnDto.episdYear}">
                                                <option value="${year}" <c:if test="${rtnDto.episdYear eq year}">selected</c:if>>${year}년</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${year}" <c:if test="${year eq nowYear}">selected</c:if>>${year}년</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>

                                <select class="form-control input-sm wd-sm notRequired" name="episdOrd" id="episdOrd" title="회차" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.ROUND_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdOrd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <label class="col-sm-1 control-label">업종</label>
                            <div class="col-sm-2">
                                <select class="form-control input-sm wd-sm notRequired" name="cbsnCd" id="cbsnCd" title="업종">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.CBSN_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                    </fieldset>

                    <fieldset class="eduDt">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">접수기간<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="accsStrtDt" id="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="접수 시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsStrtHour" title="접수 시작시간" style="margin-left:5px;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0"> ~ </span>
                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="accsEndDtm" id="accsEndDt" value="${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="접수 종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsEndHour" id="accsEndHour" title="접수 종료시간" style="margin-left:5px;">
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
                    <fieldset class="eduDt">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육기간<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="edctnStrtDt" id="edctnStrtDt" value="${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="교육 시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <div>
                                            <select class="form-control input-sm wd-sm classType form-inline" name="edctnStrtHour" id="edctnStrtHour" title="교육 시작시간" style="margin-left:5px;">
                                                <option value="">선택</option>
                                                <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                    <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control input-sm wd-sm classType form-inline" name="edctnStrtMin" id="edctnStrtMin" title="교육 시작분" style="margin-left:5px;">
                                                <option value="">선택</option>
                                                <option value="00" <c:if test="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'mm', '') eq '00'}">selected</c:if> >00분</option>
                                                <option value="30" <c:if test="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'mm', '') eq '30'}">selected</c:if> >30분</option>
                                            </select>
                                        </div>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="edctnEndDt" id="edctnEndDt" value="${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="교육 종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <div>
                                            <select class="form-control input-sm wd-sm classType" name="edctnEndHour" id="edctnEndHour" title="교육 종료시간" style="margin-left:5px;">
                                                <option value="">선택</option>
                                                <c:forEach var="cdList" items="${episdCdList.SYSTEM_HOUR}" varStatus="status">
                                                    <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control input-sm wd-sm classType" name="edctnEndMin" id="edctnEndMin" title="교육 종료분" style="margin-left:5px;">
                                                <option value="">선택</option>
                                                <option value="00" <c:if test="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'mm', '') eq '00'}">selected</c:if> >00분</option>
                                                <option value="30" <c:if test="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'mm', '') eq '30'}">selected</c:if> >30분</option>
                                            </select>
                                        </div>
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
                                    <input type="text" class="form-control input-sm numberChk notRequired" id="fxnumCnt" name="fxnumCnt" value="${rtnDto.fxnumCnt}" title="정원수" maxlength="50" placeholder="정원수 입력" style="max-width: 150px;"/> 명
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
                                        <input type="text" class="form-control input-sm koreanCustromChk notRequired" id="picNm" name="picNm" value="${rtnDto.picNm}" title="담당자명" maxlength="50" placeholder="담당자명"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm notRequired" id="picEmail" name="picEmail" value="${rtnDto.picEmail}" title="담당자이메일" maxlength="50" placeholder="담당자이메일"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm phoneChk notRequired" id="picTelNo" name="picTelNo" value="${rtnDto.picTelNo}" title="담당자전화번호" maxlength="13" placeholder="담당자전화번호"/>
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
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtnsPdf')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="edctnNtctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 파일확장자 ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>

                        <input type="hidden" class="notRequired" id="edctnNtctnFileSeq" name="edctnNtctnFileSeq" value="${rtnDto.edctnNtctnFileSeq}" />
                    </fieldset>

                    <fieldset class="eduRoom">
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
                            <div class="col-sm-11 form-inline">
                                <input type="text" class="form-control input-sm notRequired" id="cprtnInsttNm" value="${rtnDto.cprtnInsttNm}" title="협력기관" maxlength="50" placeholder="협력기관 입력"  readonly style="width: 250px;" />
                                <input type="hidden" class="form-control input-sm notRequired" name="cprtnInsttSeq" id="cprtnInsttSeq" value="${rtnDto.cprtnInsttSeq}" title="협력기관" maxlength="50" placeholder="협력기관 입력" />
                                <button type="button" class="btn btn-inverse btn-sm cprtnInsttSearch">
                                    협력기관 검색
                                </button>
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
                                        <td class="text-center" rowspan="2">1</td>
                                        <td class="text-center">
                                            <input type="text" class="form-control input-sm notRequired" name="onlineNm" value="" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                        </td><!--강의명-->
                                        <td class="text-center">
                                            <input type="text" class="form-control input-sm notRequired" name="onlineUrl" value="" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                        </td><!--유튜브 URL-->
                                        <td class="text-center">
                                            <label class="input_line form-inline">
                                            <input type="text" class="form-contol input-sm numberChk notRequired" name="onlineTime" value="" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;margin-right: 10px;"/>분
                                            </label>
                                        </td><!--강의시간-->
                                        <td class="text-center">
                                            <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                            <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                        </td><!--삭제-->
                                    </tr>
                                    <tr class="examTr" style="display: none;">
                                        <td class="text-center" colspan="4">
                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                            <spring:eval var="eduThumSize" expression="@environment.getProperty('app.file.eduThumSize')" />
                                            <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                            <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                <div class="dz-default dz-message">
                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                </div>
                                            </div>
                                            <p class="text-bold mt">
                                                <%--※ 파일확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)--%>
                                                    ※ ${eduThumSize} / 파일 확장자(jpg,jpeg,png) / 최대 용량(5MB) / 최대 개수 (1개)
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
                                                        <label class="input_line form-inline">
                                                            <input type="text" class="form-control input-sm numberChk notRequired" name="onlineTime" value="${lctrDtoList.time}" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;margin-right: 10px;"/>분
                                                        </label>
                                                    </td><!--강의시간-->
                                                    <td class="text-center">
                                                        <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                                        <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                                    </td><!--삭제-->
                                                </tr>
                                                <tr>
                                                    <td class="text-center" colspan="4">
                                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                                        <spring:eval var="eduThumSize" expression="@environment.getProperty('app.file.eduThumSize')" />
                                                        <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                                        <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq${lctrDtoList.thnlFileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                            <div class="dz-default dz-message">
                                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                            </div>
                                                        </div>
                                                        <p class="text-bold mt">
                                                            ※ ${eduThumSize} / 파일 확장자(jpg,jpeg,png) / 최대 용량(5MB) / 최대 개수 (1개)
                                                        </p>
                                                    </td>
                                                </tr>

                                                <input type="hidden" class="notRequired thnlFileForm" id="thnlFileSeq" name="lctrFileSeq${lctrDtoList.thnlFileSeq}" value="${lctrDtoList.thnlFileSeq}" />
                                            </c:forEach>
                                            <!-- 온라인강의 목차 출력 e -->
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td class="text-center" rowspan="2">${ rtnData.totalCount - rtnData.firstIndex - status.index + 1}</td>
                                                <td class="text-center">
                                                    <input type="text" class="form-control input-sm notRequired" name="onlineNm" value="${list.url}" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                                </td><!--강의명-->
                                                <td class="text-center">
                                                    <input type="text" class="form-control input-sm notRequired" name="onlineUrl" value="${list.url}" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                                </td><!--유튜브 URL-->
                                                <td class="text-center">
                                                    <label class="input_line form-inline">
                                                        <input type="text" class="form-control input-sm numberChk notRequired" name="onlineTime" value="${list.time}" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;margin-right: 10px;"/>분
                                                    </label>

                                                </td><!--강의시간-->
                                                <td class="text-center">
                                                    <button type="button" class="btn btn-inverse btn-sm btnAdd">추가</button>
                                                    <button type="button" class="btn btn-inverse btn-sm btnRemove">삭제</button>
                                                </td><!--삭제-->
                                            </tr>
                                            <tr>
                                                <td class="text-center" colspan="4">
                                                    <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                                    <spring:eval var="eduThumSize" expression="@environment.getProperty('app.file.eduThumSize')" />
                                                    <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                                    <div class="dropzone attachFile notRequired" data-file-field-nm="lctrFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                        <div class="dz-default dz-message">
                                                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                        </div>
                                                    </div>
                                                    <p class="text-bold mt">
                                                        ※ ${eduThumSize} / 파일 확장자(jpg,jpeg,png) / 최대 용량(5MB) / 최대 개수 (1개)
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
                                <%--<span class="dtl-tab" style="margin-left:10px; color:red; font-size: 13px;"><strong>만족도조사는 필수 항목입니다.</strong></span>--%>
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
                                                    <input type="hidden" class="notRequired" name="srvSeq" id="srvSeq" value="${rtnDto.srvSeq}">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="srvStrtDtm" id="srvStrtDtm" value="${ kl:convertDate(rtnDto.srvStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문시작일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                        <span class="input-group-addon bg-white b0">~</span>
                                                        <input type="text" class="form-control input-sm datetimepicker_endDt notRequired" name="srvEndDtm" id="srvEndDtm" value="${ kl:convertDate(rtnDto.srvEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="설문종료일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>

                                            </td>
                                            <td>
                                                <c:if test="${rtnDto.srvSeq != null}">
                                                <button type="button" class="btn btn-inverse btn-sm srvReset">
                                                    설문 초기화
                                                </button>
                                                </c:if>
                                            </td>
                                        </tr>
                                    <%--<tr data-total-count="0">
                                        <td colspan="4" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>--%>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>


                    <fieldset class="jdgmtYn" style="display:none;">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">평가 <span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm eduExamSearch">
                                    평가 검색
                                </button>
                                <%--<span class="dtl-tab" style="margin-left:10px; color:red; font-size: 13px;"><strong>평가는 필수 항목입니다.</strong></span>--%>
                                <label class="checkbox-inline c-checkbox pull-right">
                                    <input type="checkbox" class="checkboxSingle notRequired otsdExamPtcptYn" value="Y"  name="otsdExamPtcptYn"  <c:if test="${rtnDto.otsdExamPtcptYn eq 'Y'}">checked</c:if> title="오프라인평가"/>
                                    <span class="ion-checkmark-round"></span> 오프라인평가
                                </label>

                                <table class="table table-striped">

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
                                        <td class="text-center examNmForm" colspan="4" style="min-width: 200px;">${rtnDto.examNm}</td>
                                        <td colspan="6">
                                            <input type="hidden" class="notRequired" name="examSeq" id="examSeq" value="${rtnDto.examSeq}" title="평가">
                                            <div class="input-group form-date-group mr-sm">
                                                <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="examStrtDtm" id="examStrtDtm" value="${ kl:convertDate(rtnDto.examStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시험시작일시" readonly="readonly"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                <span class="input-group-addon bg-white b0">~</span>
                                                <input type="text" class="form-control input-sm datetimepicker_endDt notRequired" name="examEndDtm" id="examEndDtm" value="${ kl:convertDate(rtnDto.examEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시험종료일시" readonly="readonly"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                            </div>

                                        </td>
                                    </tr>

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
                                        <input type="radio" name="cmptnAutoYn" value="Y" class="cmptnAutoYn" title="수료 자동화 여부" <c:if test="${cmptnAutoYn eq 'Y'}">checked</c:if>/>
                                        <span class="ion-record"></span> 자동
                                    </label>

                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmptnAutoYn" value="N" class="cmptnAutoYn" title="수료 자동화 여부" <c:if test="${cmptnAutoYn eq 'N'}">checked</c:if>/>
                                        <span class="ion-record"></span> 수동
                                    </label>
                            </div>
                        </div>
                    </fieldset>
                    <c:if test="${not empty rtnDto.episdSeq}">
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">QR코드<span class="star text-danger"> *</span></label>
                                <div class="col-sm-11">
                                    <button type="button" class="btn btn-default btn-sm mb-sm" id="btnQrDownload">QR 이미지 다운로드</button>
                                </div>
                            </div>
                        </fieldset>
                    </c:if>

                    <fieldset class="last-child">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">사용여부<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="expsYn" value="Y" title="사용여부" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                                    <span class="ion-record"></span> 사용<%-- 2024-05-17 텍스트 수정--%>
                                </label>
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="expsYn" value="N" title="사용여부" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                                    <span class="ion-record"></span> 미사용<%-- 2024-05-17 텍스트 수정--%>
                                </label>
                            </div>
                        </div>
                    </fieldset>


                    <c:if test="${actionType eq 'update'}">
                        <fieldset class="last-child">
                            <div class="form-group text-sm">

                                <label class="col-sm-1 control-label">교육완료여부<span class="star"> *</span></label>
                                <div class="col-sm-11">
                                    <div class="row">
                                        <c:set var="edctnCmpltnYn" value="${kl:nvl(rtnDto.edctnCmpltnYn, 'N')}" />
                                        <label class="radio-inline c-radio">
                                            <input type="radio" name="edctnCmpltnYn" value="Y" title="교육완료여부" <c:if test="${edctnCmpltnYn eq 'Y'}">checked</c:if> />
                                            <span class="ion-record"></span> 완료
                                        </label>
                                        <label class="radio-inline c-radio">
                                            <input type="radio" name="edctnCmpltnYn" value="N" title="교육완료여부" <c:if test="${edctnCmpltnYn eq 'N'}">checked</c:if> />
                                            <span class="ion-record"></span> 미완료
                                        </label>
                                    </div>
                                    <div class="row">
                                        <p class="text-bold mt">
                                            ※ GPC 교육의 경우 교육완료 처리 시 해당 차수정보와 참석자 정보, 설문 응답결과에 대해 GPC와의 I/F가 진행됩니다.
                                        </p>
                                    </div>
                                </div>


                            </div>
                        </fieldset>
                    </c:if>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">GPC 전송여부<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <div class="row">
                                    <c:set var="episdGpcYn" value="${kl:nvl(rtnDto.episdGpcYn, 'N')}" />
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="episdGpcYn" value="Y" title="gpc여부" <c:if test="${episdGpcYn eq 'Y'}">checked</c:if> />
                                        <span class="ion-record"></span> 전송
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="episdGpcYn" value="N" title="gpc여부" <c:if test="${episdGpcYn eq 'N'}">checked</c:if> />
                                        <span class="ion-record"></span> 미전송
                                    </label>
                                </div>
                                <div class="row">
                                    <p class="text-bold mt">
                                        ※ GPC 인터페이스 전송 대상 차수는 '전송'으로 체크해 주시기 바랍니다.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </fieldset>






                </div>

                <!-- 참여자 목록-->
                <div id="accsList" class="tab-pane fade" style="flex-wrap:wrap;">

                    <fieldset class="ptcptField">
                        <div class="clearfix">
                            <h6 class="pull-left mt0">
                                <em class="ion-play mr-sm"></em>참여자 목록 (총 <span id="ptcptListContainerTotCnt">0</span> 건 / 신청 <span id="ptcptListContainerTypeACnt">0</span>건 / 취소 <span id="ptcptListContainerTypeBCnt">0</span> 건 / 양도 <span id="ptcptListContainerTypeCCnt">0</span> 건)
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

                                <c:if test="${rtnData.stduyMthdCd ne 'STDUY_MTHD02'}">
                                    <button type="button" class="btn btn-default btn-sm mb-sm" id="btnEdctnAtndc">출석부</button>
                                </c:if>

                                <button type="button" class="btn btn-default btn-sm mb-sm" id="btnAddApp">신청자 등록</button>
                                <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcel">엑셀다운로드</button>
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
                                    <th class="text-center">부서</th>
                                    <th class="text-center">직급</th>
                                    <th class="text-center">휴대폰번호</th>
                                    <th class="text-center">이메일</th>
                                    <%--<th class="text-center">가입일</th>--%>
                                    <th class="text-center">교육신청일</th>
                                    <th class="text-center">교육상태</th>
                                    <th class="text-center">출석/수강</th>
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

                    <div class="clearfix">
                        <div class="pull-left">
                            <button type="button" class="btn btn-info btn-default" id="changeEpisd">차수 변경</button>
                            <button type="button" class="btn btn-info btn-default" id="sendEduMail">메일 발송</button>
                        </div>
                    </div>


                </div>

                <!-- 만족도 결과 -->
                <div id="svResult" class="tab-pane fade" style="flex-wrap:wrap;">

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
                                        ${not empty rtnDto.edctnMemCnt ? rtnDto.edctnMemCnt : 0}명
                                </p>
                            </div>
                            <label class="col-sm-1 control-label">설문 참여자<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static">
                                    ${not empty rtnDto.srvMemCnt ? rtnDto.srvMemCnt : 0}명
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
                                            <th colspan="7">부서별 인원</th><th colspan="7">직급별 인원</th>
                                        </tr>
                                        <tr>
                                            <th>품질</th>
                                            <th>R&D</th>
                                            <th>생산</th>
                                            <th>구매</th>
                                            <th>경영지원</th>
                                            <th>업체평가</th>
                                            <th>기타</th>

                                            <th>대표</th>
                                            <th>임원</th>
                                            <th>부장</th>
                                            <th>과장/차장</th>
                                            <th>사원/대리</th>
                                            <th>조장/반장(계장)</th>
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
                                                <fmt:parseNumber var= "tPer7" integerOnly= "true" value= "${((srvRstDtl.t7 + srvRstDtl.t8 + srvRstDtl.t9) /rtnDto.srvMemCnt)*100}" />

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
                                                    <td class="text-center">${srvRstDtl.t7 + srvRstDtl.t8 + srvRstDtl.t9}명(${tPer7}%)</td>

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
                                                </tr>
                                            </c:otherwise>

                                        </c:choose>

                                    </tbody>
                                </table>
                        </div>
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
                                        <td class="text-center">${srvScoreDtl.vrllAvrg}</td>
                                        <td class="text-center">${srvScoreDtl.ttlCoScore}</td>
                                        <td class="text-center">${srvScoreDtl.edctnEnvrnmtScore}</td>
                                        <td class="text-center">${srvScoreDtl.edctnSpprtScore}</td>
                                        <td class="text-center">${srvScoreDtl.edctnCntnScore}</td>
                                        <td class="text-center">${srvScoreDtl.isttrScore}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                    <c:if test="${rtnSurveyData != null}">
                        <c:forEach var="qstnList" items="${rtnSurveyData.svSurveyQstnDtlList}" varStatus="qstnStatus">
                            <c:if test="${cd ne qstnList.cd}">
                                <h6 class="ml mb-xl ${fn:substring(qstnList.cd,0,3)}"><em class="ion-android-checkbox-blank mr-sm"></em>${qstnList.cdNm}</h6>
                            </c:if>
                            <c:if test="${qstnList.cd ne 'CON01' && qstnList.cd ne 'CON02'}">
                                <c:set var="rowspan" value="${qstnList.exmplCnt+3}" />
                                <fieldset style="<c:if test="${qstnList.ctgryCd eq null}">display:none;</c:if>" class="surveyList ${qstnList.cd}" data-survey-type="${qstnList.cd}" >
                                    <input type="hidden" name="dpth" value="${qstnList.dpth}">
                                    <div class="form-group text-sm">
                                        <table class="table">
                                            <tr>
                                                <th rowspan="3" class="col-md-1 ${qstnList.cd}questionTxt">질문1</th>
                                                <th class="col-md-1">설문유형<span class="star"> *</span></th>
                                                <td class="form-inline col-md-8" >
                                                        ${qstnList.srvTypeNm}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>질문<span class="star"> *</span></th>
                                                <td> <c:if test="${qstnList.cd eq 'EDU05'}"><strong>${qstnList.isttrName}</strong></c:if> ${qstnList.qstnNm}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <th>응답<span class="star"> *</span></th>
                                                <td>
                                                    <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                        <c:choose>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST03' || qstnList.srvTypeCd eq 'QST04'}">
                                                                <c:forTokens var="item" items="${exmplList.winAnswerText}" delims="," varStatus="status">
                                                                   - ${item} <br>
                                                                </c:forTokens>
                                                            </c:when>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">
                                                                - ${exmplList.exmplOrd} (${exmplList.winAnswer}명)
                                                                <input type="hidden" name="qstnCd" value="${qstnList.cd}">
                                                                <input type="hidden" name="qstnCdScore" value="${exmplList.exmplOrd}">
                                                                <input type="hidden" name="qstnCdCount" value="${exmplList.winAnswer}">
                                                                <br>
                                                            </c:when>
                                                            <c:otherwise>
                                                                - ${exmplList.exmplNm} (${exmplList.winAnswer}명) <br>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </table>
                                    </div>
                                </fieldset>
                            </c:if>
                            <c:set var="cd" value="${ qstnList.cd}" />
                        </c:forEach>
                    </c:if>











                </div>

                <!-- 예산/지출 -->
                <div id="bdget" class="tab-pane fade" style="flex-wrap:wrap;">
                    <div class="bdgetForm0" style="width:100%;order:0">
                        <fieldset>
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
                            <input type="text" class="form-control input-sm calPmtForm bdget01 comma notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="50" placeholder="${cdList.cdNm} 입력" />
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
                            <input type="text" class="form-control input-sm calPmtForm bdget02 comma notRequired" name="${cdList.cd}" value="${tempPmt}" title="${cdList.cdNm}" maxlength="50" placeholder="${cdList.cdNm} 입력" />
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
                                <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">교육예산(<span class="pmt">10000원</span>)</span></h6>
                            </div>
                        </div>
                    </fieldset>

                    <!--복사용 끝-->

                    <!-- 협력기관 지출내역 -->
                    <div class="bdgetForm3" style="width:100%;order:3">
                        <fieldset>
                            <div class="form-group text-sm">
                                <div class="col-sm-12">
                                    <h6 class="mt0"><em class="ion-play mr-sm"></em><span class="title">협력기관 지출내역(합계 : <span>0</span>원)</span></h6>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">협력기관</label>
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
                                                <label class="col-sm-1 control-label">지출금액</label>
                                                <div class="col-sm-4 form-inline">
                                                    <input type="text" class="form-control input-sm notRequired comma" id="expnsPmt" name="expnsPmt" value="${rtnDto.expnsPmt}" title="지출금액" maxlength="50" placeholder="지출금액" style=width:250px;"/>
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

            <c:if test="${not empty rtnDto and rtnDto.copyYn eq 'N'}">
                <h5 class="ml mb-xl"><em class="ion-play mr-sm"></em>등록/수정이력</h5>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</p>
                        </div>
                    </div>
                </fieldset>
                <c:set var="modFlag" value="${not empty rtnDto.modDtm && (rtnDto.regDtm ne rtnDto.modDtm)}" />
                <fieldset class="last-child">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최종 수정자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${rtnDto.modName} (${rtnDto.modId})
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최종 수정일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </fieldset>
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
                        <button type="button" class="btn btn-primary down mt">다운로드</button>
                    </div>
                </div>
            </div>
        </div>
    </div>















</div>



<!--설문검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/sv/sva/SVASurveySrchLayer.jsp">
    <jsp:param name="typeCd" value="EDU" />
</jsp:include>
<jsp:include page="/WEB-INF/jsp/mngwserc/eb/eba/EBACouseSrchLayer.jsp"></jsp:include><!--교육과정검색-->

<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebf/EBFEduRoomSrchLayer.jsp"></jsp:include><!--교육장검색-->



<jsp:include page="/WEB-INF/jsp/mngwserc/ex/exg/EXGExamListSrchLayer.jsp"></jsp:include><!--시험검색-->
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpc/MPCLecturerSrchLayer.jsp"></jsp:include><!--강사검색-->

<c:if test="${actionType eq 'update'}">
    <jsp:include page="/WEB-INF/jsp/mngwserc/ex/exg/EXGExamUserDtlLayer.jsp"></jsp:include><!--교육 참여자 평가 응답 상세-->
    <jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebb/EBBAtndcLayer.jsp"></jsp:include><!--출석부 레이어 팝업-->
    <jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebb/EBBMemAtndcLayer.jsp"></jsp:include><!--출석부 레이어 팝업 - 개인별 -->
    <jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebb/EBBChangeEpisdLayer.jsp"></jsp:include><!-- 차수변경 레이어팝업-->
    <jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebb/EBBInformMailLayer.jsp"></jsp:include><!-- 메일발송 레이어팝업-->
</c:if>








