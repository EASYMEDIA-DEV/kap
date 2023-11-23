<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smj/SMJFormWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.stySeq}" />
            <input type="hidden" class="notRequired" id="typeCd" name="typeCd" value="${rtnInfo.typeCd}" />
            <input type="hidden" class="notRequired" id="ttlEdctnFileSeq" name="ttlEdctnFileSeq" value="${rtnDto.ttlEdctnFileSeq}" />

            <ul class="nav nav-tabs" id="myTabs">
                <li class="tabClick"><a data-toggle="tab" id="consultTab">컨설팅</a></li>
                <li class="tabClick"><a data-toggle="tab" id="winBusinessTab">상생</a></li>
                <li class="active tabClick"><a data-toggle="tab" id="educationTab">교육</a></li>
            </ul>
            </br>
            <fieldset style="margin-bottom: 25px;">
                <div class="form-group text-sm">
                    <h6 class="mt0" style="margin-left:1%"><em class="ion-play mr-sm"></em>전체교육일정</h6>
                    <label class="col-sm-1 control-label">전체 교육 일정</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="52428800" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="ttlEdctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="전체교육일정">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <div class="clearfix">
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success" >저장</button>
                </div>
            </div>
            <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
            <div class="table-responsive ">
                <table class="table text-sm">
                    <tbody>
                    <tr>
                        <th>최종 수정자</th>
                        <td>
                            <c:choose>
                                <c:when test="${ not empty rtnDto.modName }">
                                    ${ rtnDto.modName }(${ rtnDto.modId })
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <th>최종 수정일시</th>
                        <td>
                            <c:choose>
                                <c:when test="${ not empty rtnDto.modDtm }">
                                    ${ kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </form>
    </div>
</div>