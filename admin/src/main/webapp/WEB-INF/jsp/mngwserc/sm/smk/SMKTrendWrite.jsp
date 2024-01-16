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
    <div class="card-body" data-controller="controller/sm/smk/SMKTrendWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.trndSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="thnlFileSeq" name="thnlFileSeq" value="${rtnInfo.thnlFileSeq}" />
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">카테고리<span class="star"> *</span></label>
                    <div class="col-sm-11 typeCd">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="typeCd" value="TYPE01" <c:if test="${rtnDto.typeCd eq 'TYPE01' or rtnDto.typeCd eq null}">checked</c:if> />
                            <span class="ion-record"></span> 교육사업
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="typeCd" value="TYPE02" <c:if test="${rtnDto.typeCd eq 'TYPE02'}">checked</c:if> />
                            <span class="ion-record"></span> 컨설팅사업
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="typeCd" value="TYPE03" <c:if test="${rtnDto.typeCd eq 'TYPE03'}">checked</c:if> />
                            <span class="ion-record"></span> 상생사업
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="200" placeholder="제목을 입력해주세요"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일 이미지<span class="star"> *</span></label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="5242880" />
                        <div class="dropzone attachFile" data-file-field-nm="thnlFileSeq" data-file-extn="${imgExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 1920 X 1080 / 파일 확장자(${fileExtns}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="urlUrlContainer">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">링크 URL</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm notRequired" id="urlUrl" name="urlUrl" value="${rtnDto.urlUrl}" maxlength="1000" title="링크 URL" placeholder="클릭 시 링크될 URL을 입력하세요" />
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'Y' or rtnDto.expsYn eq null}">checked</c:if>/>
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                    <c:if test="${ not empty rtnInfo }">
                        <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                    </c:if>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                           <button type="submit" class="btn btn-sm btn-success" >저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 등록자</th>
                            <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                            <th>최초 등록일시</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
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
            </c:if>
        </form>
    </div>
</div>