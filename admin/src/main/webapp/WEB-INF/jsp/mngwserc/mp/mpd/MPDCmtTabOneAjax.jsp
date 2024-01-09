
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- Datepicker-->
<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
    <input type="hidden" id="cmssrCbsnCdSe" class="notRequired" name="cmssrCbsnCdSe" value=${rtnDtl.cmssrCbsnCd} />
    <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDtl.cmssrPhotoFileSeq}" />
    <input type="hidden" id="memCd" name="memCd" value="${rtnDtl.memCd}" class="notRequired"/>

</form>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">아이디<span class="star text-danger"> *</span></label>
            <p class="col-sm-5 form-control-static">${rtnDtl.id}</p>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">비밀번호</label>
        <span class="input-group-btn" ><button type="button" style="margin-left: 1.3rem" class="btn btn-default btn-sm" id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button></span>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">이름<span class="star text-danger"> *</span></label>
            <p class="col-sm-5 form-control-static">${rtnDtl.name}</p>
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
                <select class="form-control input-sm notRequired"  id="cmssrCbsnCd" name="cmssrCbsnCd" title="업종분야" style="width:auto; display:inline-block;" >
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
            <div class="input-group">
                <input type="text" class="form-control input-sm "id="hpNo"  value="${rtnDtl.hpNo}" title="휴대폰번호" name="hpNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"  placeholder="휴대폰번호 입력"/>
            </div>
    </div>
    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
    <div class="col-sm-5">
        <div class="input-group" style="z-index:0;width: 220px;">
            <input type="text" class="form-control input-sm " id="email" title="이메일" name="email" value="${rtnDtl.email}" maxlength="50" oninput="this.value=this.value.replace(/[ㄱ-힣]/g, '')" placeholder="이메일 입력"/>
            <span class="input-group-btn"><button type="button" style="margin-left: 1rem;" class="btn btn-default btn-sm" id="dupEmail">중복확인</button></span>
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
                ※ 337 X 386 / 파일 확장자(${imgType}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
            </p>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">주요경력<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
                <textarea class="form-control input-sm" style="resize: none; border-radius:0.4rem;" rows="1" cols="20" type="text" id="cmssrMjrCarerCntn" title="주요경력" name="cmssrMjrCarerCntn" placeholder="주요경력 입력">${rtnDtl.cmssrMjrCarerCntn}</textarea>
            </div>
        </div>
    </div>

</fieldset>


<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">컨설팅분야</label>
        <div class="col-sm-3">
            <div class="input-group">
                <textarea  class="form-control input-sm notRequired" style="resize: none; border-radius:0.4rem ;" type="text" rows="1" cols="20" id="cmssrCnstgFldCntn" title="컨설팅분야" name="cmssrCnstgFldCntn"  placeholder="컨설팅분야 입력">${rtnDtl.cmssrCnstgFldCntn}</textarea>               </div>
        </div>
    </div>
</fieldset>

<fieldset class="last-child">
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">주요경력/컨설팅분야 화면노출여부<span class="star"> *</span></label>
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
