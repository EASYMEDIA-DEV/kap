<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smc/SMCMnPopWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.popupSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.fileSeq}" />
            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">게시기간<span class="star"> *</span></label>
                    <div class="col-sm-9 ">
                        <div class="input-group col-md-2" style="z-index:0;">
                            <input type="text" class="form-control datetimepicker_strtDt ${kl:decode(rtnDto.odtmYn, 'Y', 'notRequired', '')}" id="expsStrtDtm" name="expsStrtDtm" value="${kl:convertDate(rtnDto.expsStrtDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '')}" ${kl:decode(rtnDto.odtmYn, 'Y', 'disabled', '')} readonly="readonly" title="시작일" />
                            <span class="input-group-btn" style="z-index:0;">
					<button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
						<em class="ion-calendar"></em>
					</button>
				</span>
                        </div>
                        <select class="form-control" id="ptupStrtHh" name="ptupStrtHh" title="시작일(시)">
                            <c:set var="ptupStrtHh" value="${kl:convertDate(rtnDto.expsStrtDtm, 'yyyy-MM-dd HH:mm', 'HH', '')}" />
                            <c:forEach var="strtHh" begin="0" end="23" step="1">
                                <c:choose>
                                    <c:when test="${strtHh lt 10}">
                                        <option <c:if test="${ptupStrtHh eq strtHh}">selected</c:if>>0${strtHh}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option <c:if test="${ptupStrtHh eq strtHh}">selected</c:if>>${strtHh}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        &nbsp;시&nbsp;
                        <select class="form-control" id="ptupStrtMi" name="ptupStrtMi" title="시작일(분)">
                            <c:set var="ptupStrtMi" value="${kl:convertDate(rtnDto.expsStrtDtm, 'yyyy-MM-dd HH:mm', 'mm', '')}" />
                            <c:forEach var="strtMi" begin="0" end="50" step="10">
                                <c:choose>
                                    <c:when test="${strtMi lt 10}">
                                        <option <c:if test="${ptupStrtMi eq strtMi}">selected</c:if>>0${strtMi}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option <c:if test="${ptupStrtMi eq strtMi}">selected</c:if>>${strtMi}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        &nbsp;분&nbsp;
                        &nbsp;~&nbsp;
                        <div class="input-group col-md-2" style="z-index:0;">
                            <input type="text" class="form-control datetimepicker_endDt ${kl:decode(rtnDto.odtmYn, 'Y', 'notRequired', '')}" id="expsEndDtm" name="expsEndDtm" value="${kl:convertDate(rtnDto.expsEndDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '')}" ${kl:decode(rtnDto.odtmYn, 'Y', 'disabled', '')} readonly="readonly" title="종료일" />
                            <span class="input-group-btn" style="z-index:0;">
					<button type="button" class="btn btn-inverse" onclick="jQuery(this).parent().prev().focus();">
						<em class="ion-calendar"></em>
					</button>
				</span>
                        </div>
                        <select class="form-control" id="ptupEndHh" name="ptupEndHh" title="종료일(시)">
                            <c:set var="ptupEndHh" value="${kl:convertDate(rtnDto.expsEndDtm, 'yyyy-MM-dd HH:mm', 'HH', '')}" />
                            <c:forEach var="endHh" begin="0" end="23" step="1">
                                <c:choose>
                                    <c:when test="${endHh lt 10}">
                                        <option <c:if test="${ptupEndHh eq endHh}">selected</c:if>>0${endHh}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option <c:if test="${ptupEndHh eq endHh}">selected</c:if>>${endHh}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        &nbsp;시&nbsp;
                        <select class="form-control" id="ptupEndMi" name="ptupEndMi" title="종료일(분)">
                            <c:set var="ptupEndMi" value="${kl:convertDate(rtnDto.expsEndDtm, 'yyyy-MM-dd HH:mm', 'mm', '')}" />
                            <c:forEach var="endMi" begin="0" end="50" step="10">
                                <c:choose>
                                    <c:when test="${endMi lt 10}">
                                        <option <c:if test="${ptupEndMi eq endMi}">selected</c:if>>0${endMi}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option <c:if test="${ptupEndMi eq endMi}">selected</c:if>>${endMi}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        &nbsp;분&nbsp;
                        <div class="form-group" style="padding-bottom:7px">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                                <span class="ion-checkmark-round"></span> 상시
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="200" placeholder="제목을 입력해주세요."/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-11 tagCd">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="tagCd" value="img" <c:if test="${rtnDto.tagCd eq 'img' or rtnDto.tagCd eq null}">checked</c:if> />
                            <span class="ion-record"></span> 이미지
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="tagCd" value="html" <c:if test="${rtnDto.tagCd eq 'html'}">checked</c:if> />
                            <span class="ion-record"></span> HTML
                        </label>
                    </div>
                </div>
            </fieldset>
            <c:choose>
                <c:when test="${mdCd eq 'pc'}">
                    <fieldset id="imgArea" <c:if test="${rtnDto.tagCd eq 'html'}">style="display:none;"</c:if><c:if test="${rtnDto.tagCd eq 'img' or rtnDto.tagCd eq null}">style="display:block;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC 이미지<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="imgExtns" expression="@environment.getProperty('app.file.imgExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${imgExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080 / 파일 확장자(${imgExtns}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="htmlArea" <c:if test="${rtnDto.tagCd eq 'img' or rtnDto.tagCd eq null}">style="display:none;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC HTML<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control notRequired <c:if test="${rtnDto.tagCd eq 'html'}">ckeditorRequired</c:if>" id="cntn" name="cntn" title="내용" data-type="${pageGb}">${rtnDto.cntn}</textarea>
                            </div>
                        </div>
                    </fieldset>
                </c:when>
                <c:when test="${mdCd eq 'mbl' or mdCd eq null}">
                    <fieldset id="imgArea" <c:if test="${rtnDto.tagCd eq 'html'}">style="display:none;"</c:if><c:if test="${rtnDto.tagCd eq 'img' or rtnDto.tagCd eq null}">style="display:block;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모바일 이미지<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imgExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080 / 파일 확장자(${imgExtns}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}"/>MB 이하) / 최대 개수 (1개)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="htmlArea" <c:if test="${rtnDto.tagCd eq 'img' or rtnDto.tagCd eq null}">style="display:none;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모바일 HTML<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control notRequired" id="cntn" name="cntn" title="내용" data-type="${pageGb}">${rtnDto.cntn}</textarea>
                            </div>
                        </div>
                    </fieldset>
                </c:when>
            </c:choose>
            <fieldset class="urlUrlContainer">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">링크 URL</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm notRequired" id="urlUrl" name="urlUrl" value="${rtnDto.urlUrl}" maxlength="1000" title="링크 URL" placeholder="링크 URL 입력하세요." />
                    </div>
                    <div class="col-sm-2">
                        <c:set var="wnppYn" value="${kl:nvl(rtnDto.wnppYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="wnppYn" value="Y" <c:if test="${wnppYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 새 창
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="wnppYn" value="N" <c:if test="${wnppYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 현재 창
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'Y' or rtnDto.expsYn eq null}">checked</c:if>/>
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success" >저장</button>
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
                            <th>최초 등록자</th>
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