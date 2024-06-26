<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/bd/bda/BDANoticeWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} ${ not empty rtnDto ? '상세/수정' : '등록'}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.ntfySeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.fileSeq}" />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="100" placeholder="제목을 입력하세요." />
                    </div>
                    <div class="col-sm-2">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="notRequired" id="ntfyYn" name="ntfyYn" value="Y" title="중요공지여부" <c:if test="${rtnDto.ntfyYn eq 'Y'}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 중요공지 설정
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset id="ntfyYnDate" <c:if test="${rtnDto.ntfyYn ne 'Y'}">style="display: none"</c:if>>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">중요공지<br />노출기간<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <div class="input-group">
                                <%--<input type="text" class="form-control input-sm datetimepicker_strtDt" style="width:100px" id="expsStrtDtm" data-name="expsStrtDtm" value="${kl:convertDate(kl:addDay(today, '-365'), 'yyyyMMdd', 'yyyy-MM-dd', '')}" title="시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>--%>
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" style="width:100px" id="expsStrtDtm" name="expsStrtDtm" data-name="expsStrtDtm" value="${not empty rtnDto.expsStrtDtm ? kl:convertDate(rtnDto.expsStrtDtm, 'yyyyMMdd', 'yyyy-MM-dd', '') : today}" title="중요공지 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <%--<input type="text" class="form-control input-sm datetimepicker_endDt" style="width:100px" id="expsEndDtm" data-name="expsEndDtm" value="${today}" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>--%>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" style="width:100px" id="expsEndDtm" name="expsEndDtm" data-name="expsEndDtm" value="${not empty rtnDto.expsEndDtm ? kl:convertDate(rtnDto.expsEndDtm, 'yyyyMMdd', 'yyyy-MM-dd', '') : kl:convertDate(kl:addDay(today, '+7'), 'yyyyMMdd', 'yyyy-MM-dd', '')}" title="중요공지 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                            </div>
                            <div class="form-group" style="padding-bottom:7px">
                                <label class="checkbox-inline c-checkbox mr-sm" style="margin-left: 30px !important; margin-right: 32px !important;">
                                    <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="중요공지 상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 상시
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="cntn" name="cntn" title="내용" data-type="${pageGb}" maxlength="500">${rtnDto.cntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">첨부파일</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <%--<spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />--%>
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
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
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                    <c:if test="${ not empty rtnDto }">
                        <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                    </c:if>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
            <c:if test="${ not empty rtnDto }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                            <tr>
                                <th>최초 등록자</th>
                                <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                                <th>최초 등록일시</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>${ empty rtnDto.modName ? '-' : rtnDto.modName += '(' += rtnDto.modId += ')' }</td>
                                <th>최종 수정일시</th>
                                <td>${ kl:decode(rtnDto.modDtm, "", "-", kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>