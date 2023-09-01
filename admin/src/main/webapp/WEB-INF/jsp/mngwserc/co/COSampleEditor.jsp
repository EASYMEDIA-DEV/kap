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
            <input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
            <input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">내용</label>
                    <div class="col-sm-10">
                        <!--
                             1. class명이 dextEditor인 div안에 에디터 삽입
                             2. div의 id와 textarea의 name값이 일치해야 form전송시 해당 데이터로 넘어간다-->
                        <div class="dextEditor" id="cntn">${rtnData.cntn}</div>
                        <textarea class="notRequired" name="cntn" title="내용" style="display:none;"></textarea>
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