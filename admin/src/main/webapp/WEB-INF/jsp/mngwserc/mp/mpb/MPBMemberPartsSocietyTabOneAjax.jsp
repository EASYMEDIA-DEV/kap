<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
    <input type="hidden" id="memCd" name="memCd" value="${rtnDtl.memCd}" class="notRequired"/>
    <input type="hidden" id="fndnNtfyRcvYn" name="fndnNtfyRcvYn" value=${rtnDtl.fndnNtfyRcvYn} />
    <input type="hidden" id="oldEmailRcv" name="oldEmailRcv"  value="${rtnDtl.ntfyEmailRcvYn}"  class="notRequired"/>
    <input type="hidden" id="oldSmsRcv" name="oldSmsRcv" value="${rtnDtl.ntfySmsRcvYn}" class="notRequired"/>
    <input type="hidden" id="oldWorkBsnmNo" name="oldWorkBsnmNo" value="${rtnDtl.workBsnmNo}" class="notRequired"/>

    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
    <input type="hidden" id="m" name="m" value="service" class="notRequired" />
    <input type="hidden" id="token_version_id" name="token_version_id" value="" class="notRequired"/>
    <input type="hidden" id="enc_data" name="enc_data" class="notRequired" />
    <input type="hidden" id="integrity_value" name="integrity_value" class="notRequired" />
</form>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">이름<span class="star text-danger"> *</span></label>
            <p class="col-sm-5 form-control-static">${rtnDtl.name}</p>

        <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="ctgry_cd" title="부품사명" name="ctgry_cd" readonly  value="${rtnDtl.cmpnNm}" />
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
        <div class="col-sm-3">
            <div class="input-group">
                <input type="text" class="form-control input-sm " id="workBsnmNo" name="workBsnmNo" value="${rtnDtl.workBsnmNo}" title="사업자등록번호" maxlength="10" oninput="this.value=this.value.replace(/[^\x00-\x7F]/g, '')"/>
                <span class="input-group-btn"><button type="button" style="margin-left: 1rem;" class="btn btn-default btn-sm" id="btnBsnmNo">인증</button></span>
            </div>
        </div>

        <label class="col-sm-3 control-label">구분<span class="star"> *</span></label>
        <div class="col-sm-5">
            <p class="col-sm-11 form-control-static" id="ctgryCdNm">${rtnDtl.ctgryCdNm}</p>
        </div>
    </div>
</fieldset>

<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">부서<span class="star text-danger"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
            <select class="form-control input-sm" id="deptCd" name="deptCd" title="부서" style="width:150px; display:inline-block;">
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                        <option value="${cdList.cd}"  ${rtnDtl.deptCd == cdList.cd ? 'selected' : ''}>${cdList.cdNm}</option>
                    </c:if>
                </c:forEach>
            </select>
                <input type="text" style="margin-left: 1rem; width:150px;" class="form-control input-sm notRequired" id="deptDtlNm" title="부서상세" name="deptDtlNm"  value="${rtnDtl.deptDtlNm}" />
            </div>
        </div>

        <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
            <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급" style="width:auto; display:inline-block;" >
                <option value="">선택</option>
                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                    <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                        <option value="${cdList.cd}"  ${rtnDtl.pstnCd == cdList.cd ? 'selected' : ''}>${cdList.cdNm}</option>
                    </c:if>
                </c:forEach>
            </select>
                    <input type="text" style="margin-left: 1rem; width:150px;" class="form-control input-sm notRequired pstnNm" id="pstnNm" title="기타" name="pstnNm"  value="${rtnDtl.pstnNm}" />
            </div>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">아이디<span class="star text-danger"> *</span></label>
            <p class="col-sm-5 form-control-static">${rtnDtl.id}</p>

    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">비밀번호</label>
        <span class="input-group-btn" ><button type="button" style="margin-left: 1rem" class="btn btn-default btn-sm" id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button></span>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm form-inline">
        <label class="col-sm-1 control-label">주소<span class="star"> *</span></label>
        <div class="col-sm-5">
            <div class="input-group">
                <input type="text" class="form-control input-sm" id="zipcode" title="우편번호" name="zipcode" readonly  value="${rtnDtl.zipcode}"/>
                <span class="input-group-btn" ><button type="button" style="margin-left: 1rem ; margin-top:-0.8rem;" class="btn btn-default btn-sm" id="searchPostCode" >우편번호 검색</button></span>
                <br>
            </div>
            <div class="input-group">
            <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDtl.bscAddr}" readonly placeholder="기본주소" style="width: 400px;"/><br>
            <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDtl.dtlAddr}" title="우편번호" placeholder="상세주소 입력" maxlength="50" style="width: 400px; margin-top:0.5rem;"/>
            </div>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">일반 전화번호</label>
        <div class="col-sm-3">
            <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnDtl.telNo}"  oninput="this.value=this.value.replace(/[^0-9]/g, '')" />
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
        <div class="col-sm-3">
            <div class="input-group">

                <input type="text" class="form-control input-sm " id="email" name="email" value="${rtnDtl.email}" title="이메일" maxlength="50" oninput="this.value=this.value.replace(/[^\x00-\x7F]/g, '')"/>
                <span class="input-group-btn"><button style="margin-left: 1rem;" type="button" class="btn btn-default btn-sm" id="dupEmail">중복확인</button></span>
            </div>
        </div>
    </div>
</fieldset>
<fieldset>
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">생년월일</label>
        <p class="col-sm-5 form-control-static">${ rtnDtl.birth}</p>
        <label class="col-sm-1 control-label">성별</label>
        <p class="col-sm-5 form-control-static">${rtnDtl.gndr == '1' ? '남' : '여'}</p>
    </div>
</fieldset>
<fieldset class="last-child">
    <div class="form-group text-sm">
        <label class="col-sm-1 control-label">SMS 수신여부<span class="star"> *</span></label>
        <div class="col-sm-5">
            <c:set var="useYn" value="${rtnDtl.ntfySmsRcvYn.toString()}" />
            <label class="radio-inline c-radio">
                <input type="radio" name="ntfySmsRcvYn" value="Y" title="수신"  ${rtnDtl.ntfySmsRcvYn.toString() == 'Y' ? 'checked' : ''}  />
                <span class="ion-record"></span> 수신
            </label>
            <label class="radio-inline c-radio">
                <input type="radio" name="ntfySmsRcvYn" value="N" title="수신"  ${rtnDtl.ntfySmsRcvYn.toString() == 'N' ? 'checked' : ''}  />
                <span class="ion-record"></span> 수신 안함
            </label>
            ${ kl:convertDate(rtnDtl.ntfySmsRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} 변경

        </div>
        <label class="col-sm-1 control-label">이메일 수신여부<span class="star"> *</span></label>
        <div class="col-sm-5">
            <c:set var="useYn" value="${rtnDtl.ntfyEmailRcvYn.toString()}" />
            <label class="radio-inline c-radio">
                <input type="radio" name="ntfyEmailRcvYn" value="Y" title="수신" ${rtnDtl.ntfyEmailRcvYn.toString() == 'Y' ? 'checked' : ''} />
                <span class="ion-record"></span> 수신
            </label>
            <label class="radio-inline c-radio">
                <input type="radio" name="ntfyEmailRcvYn" value="N" title="수신" ${rtnDtl.ntfyEmailRcvYn.toString() == 'N' ? 'checked' : ''} />
                <span class="ion-record"></span> 수신 안함
            </label>
            ${ kl:convertDate(rtnDtl.ntfyEmailRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} 변경

        </div>
    </div>
</fieldset>



