<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${not empty rtnInfo ? rtnInfo : rtnData}"/>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/sm/smb/SMBMainVslWriteCtrl">
        <c:choose>
            <c:when test="${empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
            <c:otherwise>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:otherwise>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnData.detailsKey ? './insert' : './update'}">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />

            <input type="hidden" class="notRequired" name="pcFileSeq" value="${rtnDto.pcFileSeq}" />
            <input type="hidden" class="notRequired" name="mblFileSeq" value="${rtnDto.mblFileSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시기간<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <input type="text" class="form-control input-sm datetimepicker_strtDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postStrtDtm"
                                       value="${kl:convertDate(rtnDto.postStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}" title="게시기간" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postEndDtm"
                                       value="${kl:convertDate(rtnDto.postEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}" title="게시기간" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                            </div>
                            <div class="form-group" style="padding-bottom:7px">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 상시
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">타이틀<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" maxlength="50" title="타이틀" placeholder="타이틀을 입력하세요." />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">PC 비쥬얼<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <spring:eval var="pcFileExtns" expression="@environment.getProperty('app.file.mnVslPcExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone" data-file-field-nm="pcFileSeq" data-file-extn="${pcFileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="PC 비쥬얼">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 1920 X 1080, 파일(${pcFileExtns})만 등록 가능합니다. (최대 1개만 등록 가능합니다.)
                        </p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">MO 비쥬얼<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <spring:eval var="mblFileExtns" expression="@environment.getProperty('app.file.mnVslMblExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone" data-file-field-nm="mblFileSeq" data-file-extn="${mblFileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="MO 비쥬얼">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 1920 X 1080, 파일(${mblFileExtns})만 등록 가능합니다. (최대 1개만 등록 가능합니다.)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">링크</label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm notRequired" id="url" name="url" value="${rtnDto.url}" maxlength="200" title="링크" placeholder="이동할 링크를 입력하세요." oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣\s]/,'');"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">새 창여부</label>
                    <div class="col-sm-11">
                        <c:set var="wnppYn" value="${kl:nvl(rtnDto.wnppYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" class="notRequired" name="wnppYn" value="Y" <c:if test="${wnppYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 새 창
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" class="notRequired" name="wnppYn" value="N" <c:if test="${wnppYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 본 창
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <input type="hidden" class="notRequired" value="${strPam}" name="strPam"/>
                    <button type="button" class="btn btn-sm btn-default" id="btnList">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btnOneDelete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${not empty rtnInfo}">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 작성자</th>
                            <td>${rtnDto.regName} (${rtnDto.regId})</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnDto.regDtm ne rtnDto.modDtm}">
                                        ${rtnDto.modName} (${rtnDto.modId})
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <th>최종 수정일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnDto.regDtm ne rtnDto.modDtm}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
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