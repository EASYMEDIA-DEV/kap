<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smb/SMBMnVslWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.seq}" />
            <!-- 첨부파일 순번 -->
            <c:choose>
                <c:when test="${gubun eq 'pc'}">
                    <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.pcAtchFileSeq}" />
                </c:when>
                <c:when test="${gubun eq 'mobile'}">
                    <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.mblAtchFileSeq}" />
                </c:when>
            </c:choose>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시기간<span class="star"> *</span></label>
                    <div class="col-sm-7">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <!--class명을 datetimepicker_strtDt -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="strtDtm" value="${ kl:convertDate(rtnDto.postStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', null) }" title="시작일" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <!--class명을 datetimepicker_endDt -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_endDt <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="endDtm" value="${ kl:convertDate(rtnDto.postEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', null) }" title="종료일" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="padding-bottom:7px">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="${rtnDto.odtmYn}" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 상시여부
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" maxlength="200" title="제목" placeholder="제목 입력하세요." />
                    </div>
                </div>
            </fieldset>
            <!-- 2023-01-04 메인 배너 3개 띄우기 위한 수정 (jds) s -->
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시위치<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <select class="form-control input-sm" name="positionOrd" id="positionOrd" title="게시위치">
                            <option value="">[선택]</option>
                            <option value="10" <c:if test="${rtnDto.positionOrd eq '10'}">selected</c:if>>상</option>
                            <option value="20" <c:if test="${rtnDto.positionOrd eq '20'}">selected</c:if>>중</option>
                            <option value="30" <c:if test="${rtnDto.positionOrd eq '30'}">selected</c:if>>하</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <!-- 2023-01-04 메인 배너 3개 띄우기 위한 수정 (jds) e -->
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">Main Copy</label>
                    <div class="col-sm-5">
                        <textarea class="form-control input-sm notRequired" id="mnCopy" name="mnCopy" maxlength="500" title="Main Copy" placeholder="Main Copy를 입력하세요." >${rtnDto.mnCopy}</textarea>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">HEX</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" id="mnHexCd" name="mnHexCd" value="${rtnDto.mnHexCd}" maxlength="6" title="HEX" placeholder="HEX 입력하세요." onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/,'');"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">Sub Copy</label>
                    <div class="col-sm-5">
                        <textarea class="form-control input-sm notRequired" id="subCopy" name="subCopy" maxlength="500" title="Sub Copy" placeholder="Sub Copy를 입력하세요.">${rtnDto.subCopy}</textarea>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">HEX</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm notRequired" id="subHexCd" name="subHexCd" value="${rtnDto.subHexCd}" maxlength="6" title="HEX" placeholder="HEX 입력하세요." onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/,'');" />
                    </div>
                </div>
            </fieldset>

            <c:choose>
                <c:when test="${gubun eq 'pc'}">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">PC 첨부파일<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone notRequired" data-file-field-nm="fileSeq" data-file-extn="${imageExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="PC 첨부파일">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080, ${imageExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>
                    </fieldset>
                </c:when>
                <c:when test="${gubun eq 'mobile'}">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">mobile 첨부파일<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone notRequired" data-file-field-nm="fileSeq" data-file-extn="${imageExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="mobile 첨부파일">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 1920 X 1080, ${imageExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
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
                        <input type="text" class="form-control input-sm notRequired" id="linkUrl" name="linkUrl" value="${rtnDto.linkUrl}" maxlength="200" title="링크 URL" placeholder="링크 URL 입력하세요." onkeyup="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/,'');" />
                    </div>
                    <div class="col-sm-2">
                        <c:set var="newWndwYn" value="${kl:nvl(rtnDto.newWndwYn, 'N')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="newWndwYn" value="Y" <c:if test="${newWndwYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 새 창
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="newWndwYn" value="N" <c:if test="${newWndwYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 현재 창
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="mainYn" value="${kl:nvl(rtnDto.mainYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="Y" <c:if test="${mainYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="N" <c:if test="${mainYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnDto}">
                <fieldset></fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regNm} (${rtnDto.regId})</p>
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
                                        ${rtnDto.modNm} (${rtnDto.modId})
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
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
    </div>
</div>