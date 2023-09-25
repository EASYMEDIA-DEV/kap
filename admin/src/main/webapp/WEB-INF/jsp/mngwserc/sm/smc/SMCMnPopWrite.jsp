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
    <div class="card-body" data-controller="controller/sm/smc/SMCPopWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.seq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.fileSeq}" />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시기간(일시)</label>
                    <div class="col-sm-11">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <!--class명을 datetimepicker_strtDtm -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_strtDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="strtDtm" value="<c:if test="${rtnDto.odtmYn ne 'Y'}">${ kl:convertDate(rtnDto.strtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</c:if>" title="시작일시"  <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <!--class명을 datetimepicker_endDtm -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_endDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="endDtm" value="<c:if test="${rtnDto.odtmYn ne 'Y'}">${ kl:convertDate(rtnDto.endDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</c:if>" title="종료일시"  <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if>/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                            </div>
                            <div class="form-group" style="padding-bottom:7px;">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 상시여부
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="100" />
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분</label>
                    <div class="col-sm-11 typeCd">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="typeCd" value="image" <c:if test="${rtnDto.typeCd eq 'image' or rtnDto.typeCd eq null}">checked</c:if> />
                            <span class="ion-record"></span> 이미지
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="typeCd" value="html" <c:if test="${rtnDto.typeCd eq 'html'}">checked</c:if> />
                            <span class="ion-record"></span> HTML
                        </label>
                    </div>
                </div>
            </fieldset>
            <c:choose>
                <c:when test="${gubun eq 'pc'}">
                    <fieldset id="imageArea" <c:if test="${rtnDto.typeCd eq 'html'}">style="display:none;"</c:if><c:if test="${rtnDto.typeCd eq 'image' or rtnDto.typeCd eq null}">style="display:block;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC 이미지<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="PC 이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080, ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="htmlArea" <c:if test="${rtnDto.typeCd eq 'image' or rtnDto.typeCd eq null}">style="display:none;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC HTML<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control notRequired" id="cnts" name="cnts" title="내용" data-type="${pageGb}">${rtnDto.cnts}</textarea>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="linkUrlContainer" style="display:${ not empty rtnDto and rtnDto.typeCd eq 'html' ? 'none;' : 'block;'}">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">링크 URL</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control input-sm notRequired" id="linkUrl" name="linkUrl" value="${rtnDto.linkUrl}" maxlength="1000" title="링크 URL" placeholder="링크 URL 입력하세요." />
                            </div>
                        </div>
                    </fieldset>
                </c:when>
                <c:when test="${gubun eq 'mobile' or gubun eq null}">
                    <fieldset id="imageArea" <c:if test="${rtnDto.typeCd eq 'html'}">style="display:none;"</c:if><c:if test="${rtnDto.typeCd eq 'image' or rtnDto.typeCd eq null}">style="display:block;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모바일 이미지<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="모바일 이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080, ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="htmlArea" <c:if test="${rtnDto.typeCd eq 'image' or rtnDto.typeCd eq null}">style="display:none;"</c:if>>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모바일 HTML<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <textarea class="form-control notRequired" id="cnts" name="cnts" title="내용" data-type="${pageGb}">${rtnDto.cnts}</textarea>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="linkUrlContainer" style="display:${ not empty rtnDto and rtnDto.typeCd eq 'html' ? 'none;' : 'block;'}">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">링크 URL</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control input-sm notRequired" id="linkUrl" name="linkUrl" value="${rtnDto.linkUrl}" maxlength="1000" title="링크 URL" placeholder="링크 URL 입력하세요." />
                            </div>

                        </div>
                    </fieldset>
                </c:when>
            </c:choose>

            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출 여부</label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="useYn" value="Y" title="노출 여부" <c:if test="${rtnDto.useYn eq 'Y' or rtnDto.useYn eq null}">checked</c:if>/>
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="useYn" value="N" title="노출 여부" <c:if test="${rtnDto.useYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list'">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success" >수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
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
                            <th>최초 작성자</th>
                            <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ rtnDto.regDtm ne rtnDto.modDtm }">
                                        ${ rtnDto.modName }(${ rtnDto.modId })
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <th>최종 수정일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ rtnDto.regDtm ne rtnDto.modDtm }">
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