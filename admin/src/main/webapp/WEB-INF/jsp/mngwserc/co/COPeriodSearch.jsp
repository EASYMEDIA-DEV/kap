<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<c:set var="startId" value="strtDt" />
<c:set var="endId" value="endDt" />

<c:if test="${fn:length(param.startId) > 0 }">
	<c:set var="startId" value="${param.startId}" />
</c:if>
<c:if test="${fn:length(param.endId) > 0 }">
	<c:set var="endId" value="${param.endId}" />
</c:if>

<fieldset>
	<div class="form-inline text-sm">
		<label class="col-sm-1 control-label">${param.srchText}</label>
		<div class="col-sm-11">

			<c:if test="${fn:length(param.srchOption) > 0 }">
				<div class="form-group mr-sm">
					<select class="form-control input-sm" data-name="srchDate">
						<c:forTokens var="item" items="${param.srchOption}" delims="," varStatus="status">
							<option value="${status.count}" <c:if test="${rtnData.srchDate eq status.count}">selected</c:if>>${item}</option>
						</c:forTokens>
					</select>
				</div>
			</c:if>

			<div class="form-group mr-sm">
				<div class="input-group">
					<input type="text" class="form-control input-sm datetimepicker_strtDt" id="${startId}" data-name="${startId}" value="${today}" title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
					<span class="input-group-btn" style="z-index:0;">
					<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
						<em class="ion-calendar"></em>
					</button>
					</span>
					<span class="input-group-addon bg-white b0">~</span>
					<input type="text" class="form-control input-sm datetimepicker_endDt" id="${endId}" data-name="${endId}" value="${today}" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
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
