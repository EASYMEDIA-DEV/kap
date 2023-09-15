<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COSampleWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.fileSeq}" />
            <%--<input type="hidden" class="notRequired" id="cntn" name="cntn"/>--%>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="100" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">이름<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" id="name" name="name" value="${rtnDto.name}" title="이름" maxlength="20" />
                    </div>
                    <label class="col-sm-2 control-label">ID<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <!-- ID는 class명을 idChk(jquery.ez.valiation-1.3.6.js처리) -->
                        <input type="text" class="form-control input-sm idChk" id="id" name="id" value="${rtnDto.id}" title="아이디" maxlength="20" autocomplete="off"/>
                    </div>
                </div>
            </fieldset>
            <!--
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">비밀번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                       비밀번호는 class명을 passChk(jquery.ez.valiation-1.3.6.js처리)
                        <input type="password" class="form-control input-sm passChk" id="password" name="password" title="비밀번호" maxlength="20" autocomplete="off" />
                        <span class="help-block mt-sm mb0">※ 영문/숫자/특수문자 중 3종류 이상을 조합한 6~12자</span>
                    </div>
                    <label class="col-sm-2 control-label">비밀번호 확인<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        비밀번호 확인은 class명을 passEqual(jquery.ez.valiation-1.3.6.js처리)
                        <input type="password" class="form-control input-sm passEqual" id="pwdCnfrm" name="pwdCnfrm" title="비밀번호 확인" maxlength="20" autocomplete="off" />
                    </div>
                </div>
            </fieldset>
            -->
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <!-- 이메일은 class명을 emailChk(jquery.ez.valiation-1.3.6.js처리) -->
                            <input type="text" class="form-control input-sm emailChk" id="email" name="email" value="${rtnDto.email}" title="이메일" maxlength="50" />
                            <span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="btnEmailOvrlChk">중복확인</button></span>
                        </div>
                    </div>
                    <label class="col-sm-2 control-label">연락처</label>
                    <div class="col-sm-4">
                        <!-- 휴대전화 또는 집전화번호는 class명을 phoneChk(jquery.ez.valiation-1.3.6.js처리) -->
                        <input type="text" class="form-control input-sm notRequired phoneChk" id="telNo" name="telNo" value="${rtnDto.telNo}" title="연락처" maxlength="20" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">게시기간(일시)</label>
                    <div class="col-sm-4">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <!--class명을 datetimepicker_strtDtm -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_strtDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postStrtDtm" value="${ kl:convertDate(rtnDto.postStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }" title="시작일시" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <!--class명을 datetimepicker_endDtm -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_endDtm <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postEndDtm" value="${ kl:convertDate(rtnDto.postEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }" title="종료일시" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if>/>
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                            </div>
                            <div class="form-group" style="padding-bottom:7px">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="Y" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 상시여부
                                </label>
                            </div>
                        </div>
                    </div>
                    <label class="col-sm-2 control-label">게시기간(일자)</label>
                    <div class="col-sm-4">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <!--class명을 datetimepicker_strtDt -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt notRequired" name="strtDt" value="${rtnDto.strtDt}" title="시작일" readonly="readonly" />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <!--class명을 datetimepicker_endDt -->
                                <input type="text" class="form-control notRequired input-sm datetimepicker_endDt notRequired" name="endDt" value="${rtnDto.endDt}" title="종료일" readonly="readonly" />
                                <span class="input-group-btn" style="z-index:0;">
                                    <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                        <em class="ion-calendar"></em>
                                    </button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="cntn" name="cntn" title="내용" data-type="${pageGb}">${info.cntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">자바스크립트</label>
                    <div class="col-sm-10 col-md-11">
                        <textarea class="form-control notRequired" id="jsCntn" name="jsCntn" title="자바스크립트" rows="10">${info.jsCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">PC 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 1920 X 1080, ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">모바일 첨부파일</label>
                    <div class="col-sm-10">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.documentExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone notRequired" data-file-field-nm="mblFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="오바일 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 1920 X 1080, ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">사용여부</label>
                    <div class="col-sm-10">
                        <c:set var="useYn" value="${kl:nvl(rtnDto.useYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="useYn" value="Y" title="사용여부" <c:if test="${useYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 사용
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="useYn" value="N" title="사용여부" <c:if test="${useYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미사용
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                            <tr>
                                <th>최초 작성자</th>
                                <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                                <th>최초 작성일</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${ rtnDto.regDtm ne rtnDto.modDtm }">
                                            ${ rtnDto.modName }(${ rtnDto.modId })
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <th>최종 수정일</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${ rtnDto.regDtm ne rtnDto.modDtm }">
                                            ${ kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>