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
            <input type="hidden" class="notRequired" id="pcThumbFileSeq" name="pcThumbFileSeq" value="${rtnDto.pcThumbFileSeq}" />
            <input type="hidden" class="notRequired" id="moThumbFileSeq" name="moThumbFileSeq" value="${rtnDto.moThumbFileSeq}" />
            <c:if test="${typeCd eq '30'}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                        <div class="col-sm-10">
                            <select class="form-control input-sm" id="faqType" name="faqType" title="FAQ구분" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${cdDtlList.FAQ_TYPE_CD}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.faqType eq cdList.cd}">selected</c:if>>
                                            ${cdList.cdNm}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="200" placeholder="제목을 입력하세요." />
                    </div>
                    <c:if test="${typeCd eq '10'}">
                        <div class="col-sm-2">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="notRequired" id="topYn" name="topYn" value="Y" title="중요공지여부" <c:if test="${rtnDto.topYn eq 'Y'}">checked</c:if> />
                                <span class="ion-checkmark-round"></span> 중요공지 설정
                            </label>
                        </div>
                    </c:if>
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
                    <label class="col-sm-1 control-label">첨부파일</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <c:if test="${typeCd eq '20'}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">PC 썸네일</br> 이미지</label>
                        <div class="col-sm-10 col-md-11">
                            <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                            <spring:eval var="atchUploadMaxSize" expression="5242880" />
                            <div class="dropzone pcThumbFile" data-file-field-nm="pcThumbFileSeq" data-file-extn="${imageExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="PC 썸네일 이미지">
                                <div class="dz-default dz-message">
                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                </div>
                            </div>
                            <p class="text-bold mt">
                                ※ ${imageExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                            </p>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">MO 썸네일</br> 이미지</label>
                        <div class="col-sm-10 col-md-11">
                            <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                            <spring:eval var="atchUploadMaxSize" expression="5242880" />
                            <div class="dropzone moThumbFile" data-file-field-nm="moThumbFileSeq" data-file-extn="${imageExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="MO 썸네일 이미지">
                                <div class="dz-default dz-message">
                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                </div>
                            </div>
                            <p class="text-bold mt">
                                ※ ${imageExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                            </p>
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="mainYn" value="${kl:nvl(rtnDto.mainYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="Y" title="노출여부" <c:if test="${mainYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainYn" value="N" title="노출여부" <c:if test="${mainYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
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
                                <td>${ rtnDto.regName }</td>
                                <th>최초 작성일</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${ not empty rtnDto.modName }">
                                            ${ rtnDto.modName }
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <th>최종 수정일</th>
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