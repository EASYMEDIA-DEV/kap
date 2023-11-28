<%@ page import="java.util.Calendar" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="startId" value="strtDt" />
<c:set var="endId" value="endDt" />

<c:if test="${fn:length(param.startId) > 0 }">
	<c:set var="startId" value="${param.startId}" />
</c:if>
<c:if test="${fn:length(param.endId) > 0 }">
	<c:set var="endId" value="${param.endId}" />
</c:if>
<c:set var="srchDate" value="${ rtnData['srchDate'] == null ? param.srchDate : rtnData['srchDate'] }" />

<fieldset>
	<div class="form-inline text-sm">
		<label class="col-sm-1 control-label">${param.srchText}</label>
		<div class="col-sm-11">

			<c:if test="${fn:length(param.srchOption) > 0 }">
				<div class="form-group mr-sm">
					<select class="form-control input-sm" data-srch-date="srchDate" id="srchDate" name="srchDate">
						<c:forTokens var="item" items="${param.srchOption}" delims="," varStatus="status">
							<option value="${status.count}" <c:if test="${srchDate eq status.count}">selected</c:if>>${item}</option>
						</c:forTokens>
					</select>
				</div>
			</c:if>
			<c:if test="${param.selPer eq 'select'}">
				<div class="col-sm-1">
					<div class="row">
						<div class="col-sm-3 pr0">
							<select class="form-control input-sm" data-name="date">
								<option value="1" selected>가입일</option>
								<option value="2" >수정일</option>
							</select>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${param.selPer eq 'wthdrw'}">
				<div class="col-sm-1">
					<div class="row">
						<div class="col-sm-3 pr0">
							<select class="form-control input-sm" data-name="date">
								<option value="1" selected>탈퇴일</option>
							</select>
						</div>
					</div>
				</div>
			</c:if>
			<div class="form-group mr-sm">
				<c:if test="${param.srchType eq 'wbea'}">
					<select class="form-control input-sm" data-name="carbonDate">
						<option value="1" <c:if test="${rtnData.carbonDate eq '1'}">selected</c:if>>접수기간</option>
						<option value="2" <c:if test="${rtnData.carbonDate eq '2'}">selected</c:if>>사업기간</option>
					</select>
				</c:if>
				<c:if test="${param.srchType eq 'wbeb'}">
					<select class="form-control input-sm" data-name="carbonDate">
						<option value="1" <c:if test="${rtnData.carbonDate eq '1'}">selected</c:if>>신청일</option>
						<option value="2" <c:if test="${rtnData.carbonDate eq '2'}">selected</c:if>>사업기간</option>
					</select>
				</c:if>
				<div class="input-group">
					<input type="text" class="form-control input-sm datetimepicker_strtDt" style="width:100px" id="${startId}" data-name="${startId}" value="${kl:convertDate(kl:addDay(today, '-365'), 'yyyyMMdd', 'yyyy-MM-dd', '')}" title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
					<span class="input-group-btn" style="z-index:0;">
					<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
						<em class="ion-calendar"></em>
					</button>
					</span>
					<span class="input-group-addon bg-white b0">~</span>
					<input type="text" class="form-control input-sm datetimepicker_endDt" style="width:100px" id="${endId}" data-name="${endId}" value="${today}" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
					<span class="input-group-btn" style="z-index:0;">
						<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
							<em class="ion-calendar"></em>
						</button>
					</span>
				</div>
				<c:if test="${param.useOdtmYn eq 'Y'}">
					<div class="form-group" style="padding-bottom:7px">
						<label class="checkbox-inline c-checkbox mr-sm" style="margin-left: 30px !important; margin-right: 32px !important;">
							<input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시여부" <c:if test="${rtnData.odtmYn eq 'Y'}">checked</c:if> />
							<span class="ion-checkmark-round"></span> 상시
						</label>
					</div>
				</c:if>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-default btn-sm" onclick="cmmCtrl.setPeriod(this)">직접입력</button>
				<button type="button" class="btn btn-default btn-sm wd-xxs" onclick="cmmCtrl.setPeriod(this, 'd', 6)">1주일</button>
				<button type="button" class="btn btn-default btn-sm wd-xxs" onclick="cmmCtrl.setPeriod(this, 'm', 1)">1개월</button>
				<button type="button" class="btn btn-default btn-sm wd-xxs" onclick="cmmCtrl.setPeriod(this, 'm', 3)">3개월</button>
				<button type="button" class="btn btn-default btn-sm wd-xxs" onclick="cmmCtrl.setPeriod(this, 'm', 6)">6개월</button>
				<button type="button" class="btn btn-default btn-sm wd-xxs" onclick="cmmCtrl.setPeriod(this, 'y', 1)">1년</button>
			</div>
		</div>
	</div>
</fieldset>
