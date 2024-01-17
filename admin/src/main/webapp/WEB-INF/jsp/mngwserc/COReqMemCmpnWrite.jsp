<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div data-controller="controller/COReqMemCmpnWrite" data-mem-seq="${param.memSeq}">
    <input type="hidden" class="notRequired" name="memMemSeq" value="${param.memSeq}" />
    <h7 class="mt0 text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>${param.memTitle} 정보</h7>
    <hr >
    <fieldset class="mt0">
        <div class="form-group text-sm ">
            <label class="col-sm-1 control-label text-bold">신청자(아이디)</label>
            <div class="col-sm-5 memName">
            </div>
            <label class="col-sm-1 control-label text-bold">이메일<span class="star"> *</span></label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm" name="memEmail" value="" maxlength="100" title="신청자 이메일" placeholder=""  />
            </div>
        </div>
    </fieldset>
    <fieldset>
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">부서<span class="star"> *</span></label>
            <div class="col-sm-2">
                <select class="form-control input-sm" name="memDeptCd" title="신청자 부서">

                </select>
            </div>
            <div class="col-sm-3">
                <input type="text" class="form-control input-sm" name="memDeptDtlNm" value="" maxlength="100" title="상세 부서" placeholder=""  />
            </div>
            <label class="col-sm-1 control-label text-bold">직급<span class="star"> *</span></label>
            <div class="col-sm-2">
                <select class="form-control input-sm memPstnCd" name="memPstnCd" title="신청자 직급">

                </select>
            </div>
            <div class="col-sm-2 memPstnCdEtc" style="display:none;">
                <input type="text" class="form-control input-sm notRequired" name="memPstnNm" value="" maxlength="100" title="기타" placeholder=""  />
            </div>
        </div>
    </fieldset>
    <fieldset class="mb-lg">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">휴대폰번호</label>
            <div class="col-sm-5 memHpNo">
            </div>
            <label class="col-sm-1 control-label text-bold">전화번호</label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm phoneChk notRequired" name="memTelNo" value="" maxlength="13" title="전화번호" placeholder=""  />
            </div>
        </div>
    </fieldset>
    <h7 class="mt0 text-bold cmpnContainer"><em class="ion-android-arrow-dropright mr-sm"></em>${param.cmpnTitle} 정보</h7>
    <hr class="cmpnContainer"/>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">부품사명<span class="star"> *</span></label>
            <div class="col-sm-11 cmpnNm">
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">구분<span class="star"> *</span></label>
            <div class="col-sm-5">
                <select class="form-control input-sm" name="cmpnCtgryCd" title="부품사 구분">

                </select>
            </div>
            <label class="col-sm-1 control-label text-bold">규모<span class="star"> *</span></label>
            <div class="col-sm-5">
                <select class="form-control input-sm" name="cmpnSizeCd" title="부품사 규모">

                </select>
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">대표자명<span class="star"> *</span></label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm" name="cmpnRprsntNm" value="" maxlength="20" title="부품사 대표자명" placeholder=""  />
            </div>
            <label class="col-sm-1 control-label text-bold">설립일자<span class="star"> *</span></label>
            <div class="col-sm-2">
                <div class="input-group">
                    <input type="text" class="form-control input-sm datetimepicker_strtDt" name="cmpnStbsmDt" value="" title="설립일자" readonly onclick="cmmCtrl.initCalendar(this);"/>
                    <span class="input-group-btn" style="z-index:0;">
                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                            <em class="ion-calendar"></em>
                        </button>
					</span>
                </div>
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">전화번호<span class="star"> *</span></label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm phoneChk" name="cmpnTelNo" value="" maxlength="11" title="부품사 전화번호" placeholder=""  />
            </div>
            <label class="col-sm-1 control-label text-bold">사업자등록번호<span class="star"> *</span></label>
            <div class="col-sm-5 cmpnBsnmNo">
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">본사주소<span class="star"> *</span></label>
            <div class="col-sm-11 pl0">
                <div class="col-sm-4">
                    <div class="input-group">
                        <input type="text" class="form-control input-sm" id="cmpnContainerCmpnZipcode" name="cmpnZipcode" value="" maxlength="10"  title="우편번호" readonly />
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-inverse btn-sm cmpnZipcodeSrchBtn">
                                우편번호 검색
                            </button>
                        </span>
                    </div>
                </div>
                <div class="col-sm-9 mt-sm">
                    <input type="text" class="form-control input-sm" id="cmpnContainerCmpnBscAddr" name="cmpnBscAddr" value="" maxlength="100" title="기본 주소" placeholder=""  />
                </div>
                <div class="col-sm-9 mt-sm">
                    <input type="text" class="form-control input-sm" id="cmpnContainerCmpnDtlAddr" name="cmpnDtlAddr" value="" maxlength="100" title="상세 주소" placeholder=""  />
                </div>
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer">
        <div class="form-group text-sm">
            <label class="col-sm-1 control-label text-bold">매출액(연도)</label>
            <div class="col-sm-2">
                <div class="input-group">
                    <input type="text" class="form-control input-sm notRequired" name="cmpnSlsPmt" value="" maxlength="10"  title="매출액"  />
                    <span class="input-group-addon input-sm bg-white" style="border: 0">억원</span>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="input-group">
                    <select class="form-control input-sm notRequired" name="cmpnSlsYear" title="년도">

                    </select>
                    <span class="input-group-addon input-sm bg-white" style="border: 0">년</span>
                </div>
            </div>
            <label class="col-sm-1 control-label text-bold">직원수</label>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm notRequired" name="cmpnMpleCnt" value="" maxlength="10" title="직원수" placeholder=""  />
            </div>
        </div>
    </fieldset>
    <fieldset class="cmpnContainer mb-lg">
        <div class="form-group text-sm">
            <div class="col-sm-1 text-right text-bold">
                주생산품
            </div>
            <div class="col-sm-3">
                <input type="text" class="form-control input-sm notRequired" name="cmpnMjrPrdct1" value="" maxlength="50" title="주생산품" placeholder="주생산품(1)을 입력해주세요."  />
            </div>
            <div class="col-sm-4">
                <input type="text" class="form-control input-sm notRequired" name="cmpnMjrPrdct2" value="" maxlength="50" title="주생산품" placeholder="주생산품(2)을 입력해주세요."  />
            </div>
            <div class="col-sm-4">
                <input type="text" class="form-control input-sm notRequired" name="cmpnMjrPrdct3" value="" maxlength="50" title="주생산품" placeholder="주생산품(3)을 입력해주세요."  />
            </div>
        </div>
    </fieldset>