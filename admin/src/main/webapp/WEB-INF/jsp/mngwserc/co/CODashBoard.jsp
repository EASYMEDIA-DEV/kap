<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/cof/COFCodeWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="" data-alert-type="pass">
            <input type="hidden" class="notRequired" id="cdSeq" name="cdSeq" value="" />
            <input type="hidden" class="notRequired" id="type" name="type" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="col-sm-6 p0">
                <div class="mb">
                    <button type="button" class="btn btn-sm btn-inverse" id="btnRoot">최상위 생성</button>
                    <button type="button" class="btn btn-sm btn-success" id="btnChild">하위 생성</button>
                    <button type="button" class="btn btn-sm btn-primary" id="btnRename">코드명 변경</button>
                    <button type="button" class="btn btn-sm btn-danger"  id="btnRemove">삭제</button>
                </div>
                <div id="divCategoris" style="height:610px; overflow-y:auto;"></div>
            </div>
            <div class="col-sm-6 p0">
                <div class="p-lg mt-lg">
                    <fieldset class="last-child">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">그룹코드</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="cdId" name="cdId" title="그룹코드" maxlength="20" />
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="codeInfArea hidden">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">코드<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm" id="cd" name="cd" title="코드" maxlength="20" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/g,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="codeInfArea hidden">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">코드명(국문)</label>
                            <div class="col-sm-10">
                                <p class="form-control-static" id="pCdNm"></p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="codeInfArea hidden">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">코드명(영문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="enCdNm" name="enCdNm" title="코드명(영문)" maxlength="20" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/g,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="codeInfArea hidden">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">코드명(중문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="cnCdNm" name="cnCdNm" title="코드명(중문)" maxlength="20" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/g,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="last-child codeInfArea hidden">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">노출 여부</label>
                            <div class="col-sm-10">
                                <label class="radio-inline c-radio">
                                    <input type="radio" class="notRequired" name="useYn" value="Y" title="노출 여부" />
                                    <span class="ion-record"></span> 노출
                                </label>
                                <label class="radio-inline c-radio">
                                    <input type="radio" class="notRequired" name="useYn" value="N" title="노출 여부" />
                                    <span class="ion-record"></span> 미노출
                                </label>
                            </div>
                        </div>
                    </fieldset>
                    <hr />
                    <div class="clearfix">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-sm btn-success" >적용</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
