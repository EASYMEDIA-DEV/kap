<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

    <div class="card-body" data-controller="controller/co/COGBoardWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <%--<input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${info.admSeq}" />--%>
            <input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
            <input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="p0">
                <div class="p-lg">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">이름<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="name" name="name" value="${info.name}" title="이름" maxlength="20" />
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">아이디<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                  <%-- <spring:eval var="id2" expression="@environment.getProperty('app.site.name')"></spring:eval> id = ${id2}--%> <!-- 이렇게 작성하면 값을 가져옴 -->
                                    <input type="text" class="form-control input-sm notRequired " id="id" name="id" value="${info.id}" title="아이디" maxlength="20" autocomplete="off"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">비밀번호<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control input-sm notRequired" id="password" name="password" title="비밀번호" maxlength="20" autocomplete="off" />
                                <span class="help-block mt-sm mb0">※ 영문/숫자/특수문자 중 3종류 이상을 조합한 8~20자 (입력가능 특수문자  !  @  #  ^ & * )</span>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">비밀번호 확인<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control input-sm notRequired" id="pwdCnfrm" name="pwdCnfrm" title="비밀번호 확인" maxlength="20" autocomplete="off" />
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">이메일<span class="star"> *</span></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control input-sm notRequired emailChk" id="email" name="email" value="${info.email}" title="이메일" maxlength="50" />
                                    <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="btnEmailOvrlChk">중복확인</button></span>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">연락처</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired mobileNumChk" id="hpNo" name="hpNo" value="${info.hpNo}" title="연락처" maxlength="20" />
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label">내용</label>
                            <div class="col-sm-10">
                                <!--
                                     1. class명이 dextEditor인 div안에 에디터 삽입
                                     2. div의 id와 textarea의 name값이 일치해야 form전송시 해당 데이터로 넘어간다-->
                                <div class="dextEditor" id="cntn">${info.cntn}</div>
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
                </div>
            </div>
        </form>
    </div>
</div>