<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/cog/COGBoardWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.seq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.fileSeq}" />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="100" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <label class="col-sm-2 control-label">게시기간(일자)</label>
                <div class="col-sm-4">
                    <div class="form-inline">
                        <div class="input-group form-date-group mr-sm">
                            <!--class명을 datetimepicker_strtDt -->
                            <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postStrtDtm" value="${ kl:convertDate(rtnDto.postStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', null) }" title="시작일" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                            <span class="input-group-addon bg-white b0">~</span>
                            <!--class명을 datetimepicker_endDt -->
                            <input type="text" class="form-control notRequired input-sm datetimepicker_endDt <c:if test="${rtnDto.odtmYn eq 'Y'}">notRequired</c:if>" name="postEndDtm" value="${ kl:convertDate(rtnDto.postEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', null) }" title="종료일" readonly="readonly" <c:if test="${rtnDto.odtmYn eq 'Y'}">disabled</c:if> />
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-group" style="padding-bottom:7px">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" class="notRequired" id="odtmYn" name="odtmYn" value="${rtnDto.odtmYn}" title="상시여부" <c:if test="${rtnDto.odtmYn eq 'Y'}">checked</c:if> />
                        <span class="ion-checkmark-round"></span> 상시여부
                    </label>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="cnts" name="cnts" title="내용" data-type="${pageGb}">${rtnDto.cnts}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">PC 첨부파일<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
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
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">사용여부</label>
                    <div class="col-sm-10">
                        <c:set var="mainYn" value="${kl:nvl(rtnDto.mainYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="Y" title="사용여부" <c:if test="${mainYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 사용
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="N" title="사용여부" <c:if test="${mainYn eq 'N'}">checked</c:if> />
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