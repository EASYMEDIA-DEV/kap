
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbf/WBFARoundWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="bsnCd" name="bsnCd" value="INQ07006" />
            <input type="hidden" class="notRequired" id="delValueList" name="delValueList" value="${rtnDto.episdSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <select class="form-control input-sm" id="year" name="year">
                            <c:forEach begin="${year}" end="${todayYear+3}" var="result" step="1">
                                <option value="${result}" <c:if test="${rtnDto.year eq result}">selected</c:if><c:if test="${empty rtnDto and todayYear eq result}">selected</c:if>>${result}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-11">
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
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="accsEndDtm" name="accsEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="접수 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
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
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="bsnEndDtm" name="bsnEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="사업기간 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
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
            <c:if test="${not empty rtnDto.detailsKey}">
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
            <fieldset id="rowInsert">
                <c:choose>
                    <c:when test="${empty rtnDto}">
                        <div class="form-inline text-sm give-row">
                            <label class="col-sm-1 control-label">지급차수<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <div class="inline giveText"> 1차수</div>
                                <div class="form-group mr-sm">
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveStrtDtList"
                                               value="${today}"
                                               title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control input-sm datetimepicker_endDt" name="giveEndDtList"
                                               value="${today}"
                                               title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                                <input type="hidden" class="giveRowHid" name="giveOrd" value="1">
                                <button type="button" class="btn btn-info insertLow">+</button>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="list" items="${rtnDto.giveList}" varStatus="status">
                            <div class="form-inline text-sm give-row">
                                <c:if test="${status.index eq 0 }"><label class="col-sm-1 control-label">지급차수<span class="star"> *</span></label></c:if>
                                <c:if test="${status.index ne 0 }"><label class="col-sm-1 control-label"></label></c:if>
                                <div class="col-sm-11">
                                    <c:if test="${status.index ne 0 }"><fieldset></fieldset></c:if>
                                    <div class="inline giveText"> ${status.index +1 }차수</div>
                                    <div class="form-group mr-sm">
                                        <div class="input-group">
                                            <input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveStrtDtList"
                                                   value="<c:if test="${not empty rtnDto}">${kl:convertDate(list.strtDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if>"
                                                   title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                <span class="input-group-addon bg-white b0">~</span>
                                                <input type="text" class="form-control input-sm datetimepicker_endDt" name="giveEndDtList"
                                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(list.endDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if>"
                                                       title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                                <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                        </div>
                                    </div>
                                    <input type="hidden" class="giveRowHid" name="giveOrd" value="${status.index +1}">
                                    <button type="button" class="btn btn-info insertLow">+</button>
                                    <c:if test="${status.index ne 0 }">
                                        <button type="button" class="btn btn-danger deleteLow">-</button>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </fieldset>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>사업유형 관리</h6>
            <fieldset id="business">
                <c:choose>
                    <c:when test="${empty rtnDto.bsinList}">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">사업유형 1<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6"><input type="text" class="form-control input-sm business-txt" data-order="1" value="" title="사업유형 1" maxlength="50" placeholder="사업유형 1"></div>
                                <div class="col-sm-6"><button type="button" class="btn btn-info btnPlusBusi">+</button></div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="list" items="${rtnDto.bsinList}" varStatus="status">
                            <div class="form-group text-sm">
                                <fieldset></fieldset>
                                <label class="col-sm-1 control-label">사업유형 ${status.index+1}<span class="star"> *</span></label>
                                <div class="col-sm-5">
                                    <div class="col-sm-6"><input type="text" class="form-control input-sm business-txt" data-order="${list.optnOrd}" value="${list.optnNm}"  title="사업유형 ${status.index+1}" maxlength="50" placeholder="사업유형 ${status.index+1}"></div>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-info btnPlusBusi">+</button>
                                        <c:if test="${status.index ne 0}"><button type="button" class="btn btn-danger btnDelete">-</button></c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </fieldset>

            <h6 class="mt0"><em class="ion-play mr-sm"></em>과제명 관리</h6>
            <fieldset id="assignment">
                <c:choose>
                    <c:when test="${empty rtnDto.asigtList}">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과제명 1<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6"><input type="text" class="form-control input-sm assignment-txt" data-order="1" value="" title="과제명 1" maxlength="50" placeholder="과제명 1"></div>
                                <div class="col-sm-6"><button type="button" class="btn btn-info btnPlusAssi">+</button></div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="list" items="${rtnDto.asigtList}" varStatus="status">
                            <div class="form-group text-sm">
                                <fieldset></fieldset>
                                <label class="col-sm-1 control-label">과제명 ${status.index+1}<span class="star"> *</span></label>
                                <div class="col-sm-5">
                                    <div class="col-sm-6"><input type="text" class="form-control input-sm assignment-txt" data-order="${list.optnOrd}" value="${list.optnNm}" title="과제명 ${status.index+1}" maxlength="50" placeholder="과제명 ${status.index+1}"></div>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-info btnPlusAssi">+</button>
                                        <c:if test="${status.index ne 0}"><button type="button" class="btn btn-danger btnDelete">-</button></c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </fieldset>

            <c:if test="${not empty rtnDto.detailsKey}">
                <fieldset></fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}</p>
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
                        <label class="col-sm-1 control-label">최종 수정일</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}
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
                        <c:when test="${ not empty rtnDto}">
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
