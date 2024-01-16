<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smb/SMBMnVslWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} ${ not empty rtnDto ? '상세/수정' : '등록'}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.vslSeq}" />
            <input type="hidden" class="notRequired" id="mdCd" name="mdCd" value="${mdCd}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="imgFileSeq" name="imgFileSeq" value="${rtnDto.imgFileSeq}" />
            <input type="hidden" class="notRequired" id="videoFileSeq" name="videoFileSeq" value="${rtnDto.videoFileSeq}" />
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
                                <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="${not empty rtnDto.odtmYn ? rtnDto.odtmYn : 'N'}" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
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
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" maxlength="200" title="제목" placeholder="제목을 입력해주세요." />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">Main Copy</label>
                    <div class="col-sm-5">
                        <textarea class="form-control input-sm notRequired" id="mnCopy" name="mnCopy" maxlength="500" title="Main Copy" placeholder="메인 카피를 입력하세요." >${rtnDto.mnCopy}</textarea>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">HEX</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" id="mnHexCd" name="mnHexCd" value="${rtnDto.mnHexCd}" maxlength="6" title="HEX" placeholder="#123456" onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,'');"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">Sub Copy</label>
                    <div class="col-sm-5">
                        <textarea class="form-control input-sm notRequired" id="subCopy" name="subCopy" maxlength="500" title="Sub Copy" placeholder="서브 카피를 입력하세요.">${rtnDto.subCopy}</textarea>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">HEX</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" id="subHexCd" name="subHexCd" value="${rtnDto.subHexCd}" maxlength="6" title="HEX" placeholder="#123456" onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,'');" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="tagCd" value="${kl:nvl(rtnDto.tagCd, 'image')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="tagCd" class="tagCd" value="image" <c:if test="${tagCd eq 'image'}">checked</c:if> />
                            <span class="ion-record"></span> 이미지
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="tagCd" class="tagCd" value="video" <c:if test="${tagCd eq 'video'}">checked</c:if> />
                            <span class="ion-record"></span> 동영상
                        </label>
                    </div>
                </div>
            </fieldset>
            <c:choose>
            <c:when test="${mdCd eq 'pc'}">
            <fieldset class="pcImage">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                        <div class="dropzone attachFile" data-file-field-nm="imgFileSeq" data-file-extn="${imageExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ OOO X OOO / 파일 확장자(${imageExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="pcVideo" style="display: none">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <div class="dropzone attachFile" data-file-field-nm="videoFileSeq" data-file-extn="mp4" data-max-file-size="20971520" data-max-file-cnt="1" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ OOO X OOO / 파일 확장자(mp4) / 최대 용량(<fmt:formatNumber value="${20971520 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            </c:when>
                <c:when test="${mdCd eq 'mobile'}">
                    <fieldset class="mobileImg">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">첨부파일<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                <div class="dropzone attachFile" data-file-field-nm="imgFileSeq" data-file-extn="${imageExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="PC 첨부파일">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ OOO X OOO / 파일 확장자(${imageExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="mobileVideo" style="display: none">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">첨부파일<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <div class="dropzone attachFile" data-file-field-nm="videoFileSeq" data-file-extn="mp4" data-max-file-size="20971520" data-max-file-cnt="1" data-title="PC 첨부파일">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ OOO X OOO / 파일 확장자(mp4) / 최대 용량(<fmt:formatNumber value="${20971520 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                </c:when>
            </c:choose>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">링크 URL</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm notRequired" id="urlUrl" name="urlUrl" value="${rtnDto.urlUrl}" maxlength="200" title="링크 URL" placeholder="링크 URL 입력하세요." onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,'');" />
                    </div>
                    <div class="col-sm-2">
                        <c:set var="wnppYn" value="${kl:nvl(rtnDto.wnppYn, 'N')}" />
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
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
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
                            <th>최초 작성자</th>
                            <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>${ empty rtnDto.modName ? '-' : rtnDto.modName += '(' += rtnDto.modId += ')' }</td>
                            <th>최종 수정일</th>
                            <td>${ kl:decode(rtnDto.modDtm, "", "-", kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>