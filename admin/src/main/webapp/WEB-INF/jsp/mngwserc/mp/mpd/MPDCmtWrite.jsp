<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpd/MPDCmtWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="lgnSsnId" value="${rtnData.lgnSsnId}">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" class="notRequired" value="${ rtnData.pageIndex == null ? 1 : rtnData.pageIndex}" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" class="notRequired" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" class="notRequired" value="${ rtnData.listRowSize }" />
            <input type="hidden" id="memCd" name="memCd" value="${rtnDtl.memCd}" class="notRequired"/>
            <div class="tab-content">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">아이디<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input style="width:225px" type="text" class="form-control input-sm " id="id" title="아이디" name="id" placeholder="아이디 입력" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');" />
                                <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="dupId" style="margin-left:0.8rem;">중복확인</button></span>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">비밀번호<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input style="width:225px" type="password"   class="form-control input-sm " id="pwd" title="비밀번호" name="pwd" maxlength="16" placeholder="비밀번호 확인 입력" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
                            </div>
                            ※ 8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.
                        </div>
                    </div>

                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">비밀번호 확인<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <input style="width:225px" type="password" class="form-control input-sm " id="pwdCon" title="비밀번호 확인" name="pwdCon" maxlength="16" placeholder="비밀번호 확인 입력" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <input style="width:225px" type="text" class="form-control input-sm " id="name" title="이름" name="name" placeholder="이름 입력" oninput="this.value=this.value.replace(/[^\wㄱ-힣]/g,'');"/>
                            </div>
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
                                                <option value="${cdList.cd}">
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
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="birth" title="생일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();"/>
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
                                            <option value="${cdList.cd}">
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
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="cmssrMplmnDt"  title="입사일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();" />
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
                                <input type="text" class="notRequired form-control input-sm datetimepicker_strtDt" name="cmssrRsgntDt" value="" title="퇴사일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();" />
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
                                <input style="width:225px" type="text" class="form-control input-sm "id="hpNo" title="휴대폰번호" placeholder="휴대폰번호 입력" name="hpNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"  />
                            </div>
                    </div>
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <div class="input-group" style="z-index:0;width: 220px;">
                            <input style="width:225px" type="text" class="form-control input-sm " id="email" title="이메일" name="email" placeholder="이메일 입력" maxlength="50" oninput="this.value=this.value.replace(/[ㄱ-힣]/g, '')"/>
                            <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="dupEmail" style="margin-left:0.8rem;">중복확인</button></span>
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
                            <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${imgType}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지" title="사진" name="dropzone">
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
                            <textarea class="form-control input-sm " id="cmssrMjrCarerCntn" rows="5" name="cmssrMjrCarerCntn" maxlength="500" title="주요경력" placeholder="주요경력 입력" ></textarea>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">컨설팅분야</label>
                        <div class="col-sm-5">
                            <textarea class="form-control input-sm notRequired" id="cmssrCnstgFldCntn" rows="5" name="cmssrCnstgFldCntn" maxlength="500" title="컨설팅분야" placeholder="컨설팅분야 입력" ></textarea>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="last-child">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">주요경력/컨설팅분야 화면노출여부<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="cmssrMjrCarerExpsYn" value="Y" checked  />
                                <span class="ion-record"></span> 노출
                            </label>
                            <label class="radio-inline c-radio">
                                <input type="radio" name="cmssrMjrCarerExpsYn" value="N"  class="notRequired"/>
                                <span class="ion-record"></span> 미노출
                            </label>
                        </div>
                    </div>
                </fieldset>
            </div>
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
                </div>
                <div style="float:right">
                    <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                </div>
        </form>
    </div>


</div>

