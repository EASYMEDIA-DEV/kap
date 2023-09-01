<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smf/SMFFmlyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="" data-alert-type="pass">
            <input type="hidden" id="menuSeq" name="menuSeq" value=""/>
            <input type="hidden" class="notRequired" id="type" name="type" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="col-sm-6 p0">
                <div class="mb">
                    <button type="button" class="btn btn-sm btn-inverse" id="btnRoot">최상단 생성</button>
                    <button type="button" class="btn btn-sm btn-primary" id="btnRename">사이트명 변경</button>
                    <button type="button" class="btn btn-sm btn-danger"  id="btnRemove">삭제</button>
                </div>
                <div id="divCategoris" style="height:810px; overflow-y:auto;">

                </div>
            </div>
            <div id="info" class="col-sm-6 p0 hidden">
                <div class="p-lg mt-lg">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트명(국문)<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <label class="input-sm" id="menuNm" name="menuNm" title="사이트명(국문)"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트URL(국문)<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm urlChk" id="menuUrl" name="menuUrl" title="사이트URL" maxlength="100" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트명(영문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="enMenuNm" name="enMenuNm" title="사이트명(영문)" maxlength="40" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트URL(영문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired urlChk" id="enMenuUrl" name="enMenuUrl" title="사이트URL(영문)" maxlength="100" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트명(중문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="cnMenuNm" name="cnMenuNm" title="사이트명(중문)" maxlength="40" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">사이트URL(중문)</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired urlChk" id="cnMenuUrl" name="cnMenuUrl" title="사이트URL(중문)" maxlength="100" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="last-child mb0">
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">노출 여부<span class="star text-danger"> *</span></label>
                            <div class="col-sm-10">
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="useYn" value="Y" title="노출 여부" />
                                    <span class="ion-record"></span> 노출
                                </label>
                                <label class="radio-inline c-radio">
                                    <input type="radio" name="useYn" value="N" title="노출 여부" />
                                    <span class="ion-record"></span> 미노출
                                </label>
                            </div>
                        </div>
                    </fieldset>

                    <hr />

                    <div class="clearfix">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-sm btn-success">적용</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="/common/js/lib/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/js/lib/jquery/jquery.hotkeys.js"></script>