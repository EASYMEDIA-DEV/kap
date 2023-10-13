<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COSampleWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" name="pcSmplAtchFileSeq" value="" />
            <input type="hidden" class="notRequired" name="pcMblAtchFileSeq" value="" />
            <!-- 등록 가능한 파일 확장자 -->
            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
            <!-- 등록 가능한 파일 크기 -->
            <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">PC 첨부파일(여러개)<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <div class="dropzone " data-file-field-nm="pcSmplAtchFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-titl="PC 이미지/영상">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">모바일 첨부파일(여러개)<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <div class="dropzone " data-file-field-nm="pcMblAtchFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="PC 이미지/영상">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">
                        등록
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>