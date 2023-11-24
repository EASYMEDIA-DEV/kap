
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- Datepicker-->
<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
    <input type="hidden" id="cmssrCbsnCdSe" name="cmssrCbsnCdSe" value=${rtnDtl.cmssrCbsnCd} />
    <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDtl.cmssrPhotoFileSeq}" />
</form>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">아이디<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <p class="col-sm-11 form-control-static">${rtnDtl.id}</p>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">비밀번호<span class="star"> *</span></label>
        <button type="button" class="btn btn-secondary"  id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">이름<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <p class="col-sm-11 form-control-static">${rtnDtl.name}</p>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">위원구분<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
                <select class="form-control input-sm" id="cmssrTypeCd" name="cmssrTypeCd" title="위원구분" style="width:150px; display:inline-block;">
                    <option value="">선택</option>
                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                        <c:if test="${fn:contains(cdList, 'MEM_CD030')}">
                            <c:if test="${fn:contains(cdList.dpth, '3')}">
                                <option value="${cdList.cd}"  ${ rtnDtl.cmssrTypeCd eq cdList.cd ? 'selected' : '' }>
                                        ${cdList.cdNm}
                                </option>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>

        <label class="cmssrCdDiv col-sm-1 control-label">업종/분야<span class="star"> *</span></label>
        <div class="cmssrCdDiv col-sm-5">
            <div class="input-group">
                <select class="form-control input-sm"  id="cmssrCbsnCd" name="cmssrCbsnCd" title="업종분야" style="width:auto; display:inline-block;" >
                    <option value="">선택</option>
                </select>
            </div>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">생년월일<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group" style="z-index:0;width: 220px;">
                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="birth" value="${rtnDtl.birth}" title="생일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();" />
                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse input-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
            </div>
        </div>
        <label class="col-sm-1 control-label">재직여부<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
                <select class="form-control input-sm" id="cmssrWorkCd" name="cmssrWorkCd" title="재직여부" style="width:150px; display:inline-block;">
                    <option value="">선택</option>
                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                        <c:if test="${fn:contains(cdList, 'MEM_CD040')}">
                            <option value="${cdList.cd}" ${ rtnDtl.cmssrWorkCd eq cdList.cd ? 'selected' : '' }>
                                    ${cdList.cdNm}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">입사일<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group" style="z-index:0;width: 220px;">
                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="cmssrMplmnDt"  value="${rtnDtl.cmssrMplmnDt}" title="입사일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();" />
                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse input-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
            </div>
        </div>
        <label class="col-sm-1 control-label">퇴사일</label>
        <div class="col-sm-5">
            <div class="input-group" style="z-index:0;width: 220px;">
                <input type="text" class="notRequired form-control input-sm datetimepicker_strtDt" name="cmssrRsgntDt"  value="${rtnDtl.cmssrRsgntDt}" title="퇴사일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();" />
                <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse input-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
            </div>
        </div>
    </div>
</fieldset>
<fieldset>
<div class="form-group text-sm">
    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
    <div class="col-sm-5">
        <div class="col-sm-3">
            <div class="input-group">
                <input type="text" class="form-control input-sm "id="hpNo"  value="${rtnDtl.hpNo}" title="휴대폰번호" name="hpNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"  />
            </div>
        </div>
    </div>
    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
    <div class="col-sm-5">
        <div class="input-group" style="z-index:0;width: 220px;">
            <input type="text" class="form-control input-sm " id="email" title="이메일" name="email" value="${rtnDtl.email}" maxlength="50"/>
            <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="dupEmail">중복확인</button></span>
        </div>
    </div>
</div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">사진<span class="star"> *</span></label>
        <div class="col-sm-10 col-md-11">
            <spring:eval var="imgExtns" expression="@environment.getProperty('app.file.imgExtns')" />
            <spring:eval var="atchUploadMaxSize" expression="5242880" />
            <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${imgType}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                <div class="dz-default dz-message">
                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                </div>
            </div>
            <p class="text-bold mt">
                ※ 1920 X 1080 / 파일 확장자(${imgType}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
            </p>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">주요경력<span class="star"> *</span></label>
        <div class="col-sm-3">
            <div class="input-group">
                <textarea  type="text" id="cmssrMjrCarerCntn" title="주요경력" name="cmssrMjrCarerCntn">${rtnDtl.cmssrMjrCarerCntn}</textarea>               </div>
        </div>
    </div>
</fieldset>

<fieldset class="last-child">
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">주요경력 화면노출여부<span class="star"> *</span></label>
        <div class="col-sm-5">
            <label class="radio-inline c-radio">
                <input type="radio" name="cmssrMjrCarerExpsYn" value="Y" ${rtnDtl.cmssrMjrCarerExpsYn.toString() == 'Y' ? 'checked' : ''}  />
                <span class="ion-record"></span> 노출
            </label>
            <label class="radio-inline c-radio">
                <input type="radio" name="cmssrMjrCarerExpsYn" value="N"  class="notRequired" ${rtnDtl.cmssrMjrCarerExpsYn.toString() == 'N' ? 'checked' : ''} />
                <span class="ion-record"></span> 미노출
            </label>
        </div>
    </div>
</fieldset>

    <h5>등록/수정이력</h5>
        <table class="table">
            <colgroup>
                <col style="width:10%;">
                <col style="width:40%;">
                <col style="width:10%;">
                <col style="width:40%;">
            </colgroup>
            <tbody>
            <tr>
                <th scope="row" class="bg-gray-lighter">최초 등록자 </th>
                <td>${rtnDtl.regName}(${rtnDtl.regId})</td>
                <th scope="row" class="bg-gray-lighter">최초 등록일시</th>
                <td>${ kl:convertDate(rtnDtl.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">최종 수정자</th>
                <td>${rtnDtl.modName}(${rtnDtl.modId})</td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                <td>${ kl:convertDate(rtnDtl.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            </tbody>
        </table>

