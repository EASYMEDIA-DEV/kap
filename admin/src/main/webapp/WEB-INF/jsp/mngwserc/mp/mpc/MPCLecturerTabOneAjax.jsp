<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
    <fieldset class="mt-sm">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label">이름<span class="star text-danger"> *</span></label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm" id="name" name="name" value="${rtnDtl.name}" title="이름" placeholder="이름 입력" style="width: 200px;"/>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <label class="col-sm-1 control-label">소속<span class="star"> *</span></label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="ffltnNm" name="ffltnNm" value="${rtnDtl.ffltnNm}" title="소속" placeholder="소속 입력" style="width: 220px;"/>
        </div>
    </fieldset>
    <fieldset>
        <label class="col-sm-1 control-label">부서</label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="deptNm" name="deptNm" value="${rtnDtl.deptNm}" title="부서" placeholder="부서 입력" style="width: 220px;"/>
        </div>
    </fieldset>
    <fieldset>
        <label class="col-sm-1 control-label">직급</label>
        <div class="col-sm-5">
            <input type="text" class="form-control input-sm notRequired" id="pstnNm" name="pstnNm" value="${rtnDtl.pstnNm}" title="직급" placeholder="직급 입력" style="width: 220px;"/>
        </div>
    </fieldset>
    <fieldset class="form-inline">
        <label class="col-sm-1 control-label">관련사업</label>
        <div class="col-sm-5">
            <select class="form-control" id="mngBsnCd" name="mngBsnCd" title="1차 선택" style="width:auto;">
                <option>1차 선택</option>
            </select>
            <%--<select class="form-control" id="mngBsnCd" name="mngBsnCd" title="2차 선택" style="width:auto;">
                <option>2차 선택</option>
            </select>        </div>--%>
    </fieldset>
<fieldset>
    <label class="col-sm-1 control-label">전화번호</label>
    <div class="col-sm-5">
        <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnDtl.telNo}" title="전화번호" placeholder="전화번호 입력"  oninput="this.value=this.value.replace(/[^0-9]/g, '')" style="width: 220px;"/>
    </div>
</fieldset>
<fieldset>
    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
    <div class="col-sm-5">
        <input type="text" class="form-control input-sm notRequired" id="hpNo" name="hpNo" value="${rtnDtl.hpNo}" title="휴대폰번호" placeholder="휴대폰번호 입력" style="width: 220px;"/>
    </div>
</fieldset>
<fieldset class="form-inline">
    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
    <div class="col-sm-5">
        <input type="text" class="form-control input-sm"  title="이메일" id="email" value="${rtnDtl.email}" name="email" maxlength="50" placeholder="이메일 주소 입력"/>
        <button type="button" class="btn btn-sm" id="dupEmail" >중복확인</button>
    </div>
</fieldset>
<fieldset>
    <label class="col-sm-1 control-label">특이사항</label>
    <div class="col-sm-5">
        <textarea class="form-control input-sm notRequired" id="spclCntn" name="spclCntn" value="${rtnDtl.spclCntn}" title="특이사항" placeholder="특이사항 입력"></textarea>
    </div>
</fieldset>