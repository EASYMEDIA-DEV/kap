<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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

            <div class="tab-content">

            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:10%;">
                        <col style="width:40%;">
                        <col style="width:10%;">
                        <col style="width:40%;">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">아이디<span style="color:red">*</span></th>
                        <td>
                            <input type="text" class="form-control input-sm" id="id" title="아이디" name="id" />
                            <button type="button" class="btn btn-secondary" id="dupId" >중복확인</button>
                        </td>

                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">비밀번호<span style="color:red">*</span></th>
                        <td>
                            <input type="password" class="form-control input-sm" id="pwd" title="비밀번호" name="pwd" />
                            ※ 8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.

                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">비밀번호 확인<span style="color:red">*</span></th>
                        <td>
                            <input type="password" class="form-control input-sm" id="pwdCon" title="비밀번호" name="pwdCon" />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">이름<span style="color:red">*</span></th>
                        <td>
                            <input type="text" class="form-control input-sm" id="name" title="이름" name="name" />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">위원구분<span style="color:red">*</span></th>
                        <td>
                            <select class="form-control input-sm" id="cmssrTypeCd" name="cmssrTypeCd" title="위원구분" >
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
                        </td>
                        <th scope="row" class="cmssrCdDiv bg-gray-lighter">업종/분야<span style="color:red">*</span></th>
                        <td class="cmssrCdDiv">
                            <select class="form-control input-sm notRequired" id="cmssrCbsnCd" name="cmssrCbsnCd" title="업종분야" >
                                <option value="">선택</option>
                            </select>
                        </td>
                        </div>
                    </form>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">생년월일<span style="color:red">*</span></th>
                        <td>
                                <div class="input-group">
                                    <input type="text" class="form-control input-sm datetimepicker_strtDt" name="birth" value="" title="생일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();"/>
                                    <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                </div>
                        </td>
                        <th scope="row" class="bg-gray-lighter">재직여부<span style="color:red">*</span></th>
                        <td>
                            <select class="form-control input-sm" id="cmssrWorkCd" name="cmssrWorkCd" title="재직여부" >
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'MEM_CD040')}">
                                        <option value="${cdList.cd}">
                                                ${cdList.cdNm}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">입사일<span style="color:red">*</span></th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" name="cmssrMplmnDt" value="" title="입사일"  readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();"/>
                                <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                            </div>
                        </td>
                        <th scope="row" class="bg-gray-lighter">퇴사일</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="notRequired form-control input-sm datetimepicker_strtDt " name="cmssrRsgntDt" value="" title="퇴사일" readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();"/>
                                <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">휴대폰번호<span style="color:red">*</span></th>
                        <td>
                            <input type="text" class="form-control input-sm" id="hpNo" title="휴대폰번호" name="hpNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"/>
                        </td>
                        <th scope="row" class="bg-gray-lighter">이메일<span style="color:red">*</span></th>
                        <td>
                            <input type="text" class="form-control input-sm" id="email" title="이메일" name="email" />
                            <button type="button" class="btn btn-secondary" id="dupEmail" >중복확인</button>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">사진<span style="color:red">*</span></th>
                        <td colspan="3">
                            <div class="form-group text-sm">
                                <div class="col-sm-10 col-md-11">
                                    <spring:eval var="imgExtns" expression="@environment.getProperty('app.file.imgExtns')" />
                                    <spring:eval var="atchUploadMaxSize" expression="5242880" />
                                    <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${imgType}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                                        <div class="dz-default dz-message">
                                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                        </div>
                                    </div>
                                    <p class="text-bold mt">
                                        ※ 1920 X 1080 / 파일 확장자(${imgType}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
                                    </p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">주요경력<span style="color:red">*</span></th>
                        <td colspan="3">
                            <textarea style="width: -webkit-fill-available" type="text" id="cmssrMjrCarerCntn" title="주요경력" name="cmssrMjrCarerCntn"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">주요경력 화면노출여부<span style="color:red">*</span></th>
                        <td>
                            <div class="form-group text-sm">
                                <div class="col-sm-10">
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmssrMjrCarerExpsYn"  value="Y" checked  />
                                        <span class="ion-record"></span> 노출
                                    </label>
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmssrMjrCarerExpsYn" class="notRequired" value="N" />
                                        <span class="ion-record"></span> 미노출
                                    </label>
                                </div>
                            </div>
                        </td>

                    </tr>

                    </tbody>
                </table>
            </div>

            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
                </div>
                <div style="float:right">
                    <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                </div>
            </div>
        </form>
    </div>
</div>
