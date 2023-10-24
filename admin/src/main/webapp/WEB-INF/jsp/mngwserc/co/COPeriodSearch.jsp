<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<fieldset>
	<div class="form-inline text-sm">
		<label class="col-sm-1 control-label">${param.srchText}</label>
		<div class="col-sm-11">
			<c:if test="${param.periodType ne 'notSelect'}">
				<div class="form-group mr-sm">
					<select class="form-control input-sm" data-name="srchDate">
						<c:choose>
							<c:when test="${param.periodType eq 'bmb'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>게시일</option>
							</c:when>
							<c:when test="${param.periodType eq 'bmc'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>게시일</option>
							</c:when>
							<c:when test="${param.periodType eq 'coa'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>기간</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>등록일</option>
							</c:when>
							<c:when test="${param.periodType eq 'coc'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>최종접속일</option>
							</c:when>
							<c:when test="${param.periodType eq 'csa'}">
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>등록일</option>
							</c:when>
							<c:when test="${param.periodType eq 'mba'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>가입일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>최종 접속일</option>
							</c:when>
							<c:when test="${param.periodType eq 'mbc'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>탈퇴일</option>
							</c:when>
							<c:when test="${param.periodType eq 'mbd'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>구매일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>배송일</option>
							</c:when>
							<c:when test="${param.periodType eq 'mbe'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>판매일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>등록일</option>
							</c:when>
							<c:when test="${param.periodType eq 'mbf'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>가입일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>승인일</option>
							</c:when>
							<c:when test="${param.periodType eq 'sma'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>게시기간</option>
							</c:when>
							<c:when test="${param.periodType eq 'cea'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>일정</option>
							</c:when>
							<c:when test="${param.periodType eq 'ceb'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>희망일</option>
								<option value="3" <c:if test="${rtnData.srchDate eq '3'}">selected</c:if>>일정</option>
							</c:when>
							<c:when test="${param.periodType eq 'cec'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>희망일</option>
							</c:when>
							<c:when test="${param.periodType eq 'cpb'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>발송일</option>
							</c:when>
							<c:when test="${param.periodType eq 'cpbp'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>사용일</option>
							</c:when>
							<c:when test="${param.periodType eq 'smc'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>수정기간</option>
							</c:when>
							<c:when test="${param.periodType eq 'bma'}">
								<option value="1" <c:if test="${rtnData.srchDate eq '1'}">selected</c:if>>등록일</option>
								<option value="2" <c:if test="${rtnData.srchDate eq '2'}">selected</c:if>>게시일</option>
							</c:when>

						</c:choose>
					</select>
				</div>
			</c:if>
			<div class="form-group mr-sm">
				<c:choose>
					<c:when test="${param.periodType eq 'smc'}">
						<div class="input-group">
							<input type="text" class="form-control input-sm datetimepicker_strtDt" id="dStrDt" data-name="dStrDt" value="" title="등록/수정시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
							<span class="input-group-addon bg-white b0">~</span>
							<input type="text" class="form-control input-sm datetimepicker_endDt" id="dEndDt" data-name="dEndDt" value="" title="등록/수정종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
						</div>
					</c:when>
					<c:when test="${param.periodType ne 'smc' and param.useOdtmYn eq 'Y'}">
						<div class="input-group">
							<input type="text" class="form-control input-sm datetimepicker_strtDt" id="strtDt" data-name="strtDt" value="${rtnData.strtDt}" title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
							<span class="input-group-addon bg-white b0">~</span>
							<input type="text" class="form-control input-sm datetimepicker_endDt" id="endDt" data-name="endDt" value="${rtnData.endDt}" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
						</div>
						<div class="form-group" style="padding-bottom:7px">
							<label class="checkbox-inline c-checkbox mr-sm" style="margin-left: 30px !important; margin-right: 32px !important;">
								<input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시여부" <c:if test="${rtnData.odtmYn eq 'Y'}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 상시
							</label>
						</div>
					</c:when>
					<c:otherwise>
						<div class="input-group">
							<input type="text" class="form-control input-sm datetimepicker_strtDt" id="strtDt" data-name="strtDt" value="${rtnData.strtDt}" title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
							<span class="input-group-addon bg-white b0">~</span>
							<input type="text" class="form-control input-sm datetimepicker_endDt" id="endDt" data-name="endDt" value="${rtnData.endDt}" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
							<span class="input-group-btn" style="z-index:0;">
								<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
									<em class="ion-calendar"></em>
								</button>
							</span>
						</div>
					</c:otherwise>
				</c:choose>
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
