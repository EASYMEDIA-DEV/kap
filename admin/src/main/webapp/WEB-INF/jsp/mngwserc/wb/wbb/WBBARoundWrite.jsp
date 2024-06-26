
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbb/WBBARoundWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>
            <c:if test="${not empty rtnDto.episdSeq }">
                회차 상세/수정
            </c:if>
            <c:if test="${empty rtnDto.episdSeq }">
                회차 등록
            </c:if>
        </h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="bsnCd" name="bsnCd" value="${bsnCd}" />
            <c:if test="${not empty rtnDto.episdSeq}">
                <input type="hidden" class="notRequired" id="bfreYear" value="${rtnInfo.year}" />
                <input type="hidden" class="notRequired" id="bfAccStrtDtm" value="${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" />
                <input type="hidden" class="notRequired" id="bfAccEndDtm" value="${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" />
                <input type="hidden" class="notRequired" id="bfBnsStrtDtm" value="${kl:convertDate(rtnDto.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" />
                <input type="hidden" class="notRequired" id="bfBnsEndDtm" value="${kl:convertDate(rtnDto.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" />
                <input type="hidden" class="notRequired" id="bfreExpsYn" value="${rtnDto.expsYn}"/>
                <input type="hidden" class="notRequired" id="bfreEpisd" value="${rtnDto.episd}" />
            </c:if>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm" id="year" name="year">
                            <c:forEach begin="${todayYear-20}" end="${todayYear+20}" var="result" step="1">
                                <option value="${result}" <c:if test="${rtnDto.year eq result}">selected</c:if><c:if test="${empty rtnDto and todayYear eq result}">selected</c:if>>${result}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm" id="episd" name="episd">
                            <c:forEach var="cdList" items="${classTypeList.ROUND_CD}" varStatus="status">
                                <option value="${cdList.cdNm}" <c:if test="${rtnDto.episd eq cdList.cdNm}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="accsStrtDtm" name="accsStrtDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="접수 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <select class="form-control input-sm" id="accsStrtHour" name="accsStrtHour" title="접수 시작일">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${classTypeList.SYSTEM_HOUR}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                    </c:forEach>
                                </select>


                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="accsEndDtm" name="accsEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="접수 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <select class="form-control input-sm" id="accsEndHour" name="accsEndHour" title="접수 종료일">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${classTypeList.SYSTEM_HOUR}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">사업기간<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="bsnStrtDtm" name="bsnStrtDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="사업기간 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <select class="form-control input-sm" id="bsnStrtHour" name="bsnStrtHour" title="사업기간 시작일">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${classTypeList.SYSTEM_HOUR}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.bsnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                    </c:forEach>
                                </select>

                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="bsnEndDtm" name="bsnEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="사업기간 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <select class="form-control input-sm" id="bsnEndHour" name="bsnEndHour" title="사업기간 종료일">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${classTypeList.SYSTEM_HOUR}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${kl:convertDate(rtnDto.bsnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'HH', '') eq cdList.cd}">selected</c:if> >${cdList.cdNm}시</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnDto.episdSeq}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">진행상태<span class="star"> *</span></label>
                        <div class="col-sm-11">
                                ${ rtnDto.dateState}
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="mainYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출여부"<c:if test="${empty rtnDto}">checked</c:if>  <c:if test="${rtnDto.expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출여부" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnDto.episdSeq}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <fieldset></fieldset>
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
            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto.episdSeq}">
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
