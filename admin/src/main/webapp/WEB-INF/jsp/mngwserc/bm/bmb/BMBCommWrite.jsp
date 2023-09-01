<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/bm/bmb/BMBCommWriteCtrl">
        <c:choose>
            <c:when test="${empty rtnDto.commSeq}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
            <c:otherwise>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:otherwise>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnDto.commSeq ? './insert' : './update'}">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.commSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="thnlFileSeq" name="thnlFileSeq" value="${rtnDto.thnlFileSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">구분<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="typeCd" value="${kl:nvl(rtnDto.typeCd, 'link')}" />
                        <label class="radio-inline c-radio">
                            <input class="typeCd" type="radio" name="typeCd" value="link" <c:if test="${typeCd eq 'link'}">checked</c:if> />
                            <span class="ion-record"></span> 링크
                        </label>
                        <label class="radio-inline c-radio">
                            <input class="typeCd" type="radio" name="typeCd" value="youtube" <c:if test="${typeCd eq 'youtube'}">checked</c:if> />
                            <span class="ion-record"></span> 유튜브
                        </label>
                        <label class="radio-inline c-radio">
                            <input class="typeCd" type="radio" name="typeCd" value="sns" <c:if test="${typeCd eq 'sns'}">checked</c:if> />
                            <span class="ion-record"></span> SNS
                        </label>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star text-danger"> *</span></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="<c:out value='${rtnDto.titl}' escapeXml="true"/>" title="제목" maxlength="100" placeholder="제목을 입력하세요."/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시일<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="expsDtm" name="expsDtm" value="<c:if test="${not empty rtnDto.expsDtm}">${kl:convertDate(rtnDto.expsDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '')}</c:if> " title="등록일" readonly="readonly"/>
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

            <fieldset id="imgField">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일 이미지<span class="star text-danger"> *</span></label>
                    <div class="col-sm-10">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.commExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone" data-file-field-nm="thnlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 380 X 214, 파일(${fileExtns})만 등록 가능합니다. (최대 1개만 등록 가능합니다.)
                        </p>
                    </div>
                </div>
            </fieldset>
            <!-- 링크 영역 -->
            <fieldset id="linkDiv">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">링크<span class="star text-danger"> *</span></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control input-sm" name="link" id="link" value="<c:if test="${rtnDto.typeCd eq 'link'}">${rtnDto.url}</c:if>" title="링크" maxlength="200" placeholder="https://부터 입력하세요." oninput="this.value=this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣\s]/,'');"/>
                        </div>
                    </div>
                </fieldset>
                <!-- 링크 영역 -->
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">새 창여부<span class="star text-danger"> *</span></label>
                        <div class="col-sm-11">
                            <c:set var="wnppYn" value="${kl:nvl(rtnDto.wnppYn, 'Y')}" />
                            <label class="radio-inline c-radio">
                                <input type="radio" name="wnppYn" value="Y" <c:if test="${wnppYn eq 'Y'}">checked</c:if> />
                                <span class="ion-record"></span> 새창
                            </label>
                            <label class="radio-inline c-radio">
                                <input type="radio" name="wnppYn" value="N" <c:if test="${wnppYn eq 'N'}">checked</c:if> />
                                <span class="ion-record"></span> 본창
                            </label>
                        </div>
                    </div>
                </fieldset>
            </fieldset>
            <!-- 유튜브 영역 -->
            <fieldset id="youtubeDiv" style="display: none">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">유튜브URL<span class="star text-danger"> *</span></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control input-sm" name="url" value="<c:if test="${rtnDto.typeCd eq 'youtube'}">${rtnDto.url}</c:if>" title="유튜브URL" maxlength="200" placeholder="유튜브URL을 입력하세요." oninput="this.value=this.value.replace(/[ㄱ-힣]/,'');"/>
                            <p class="text-bold mt">
                                ※ /watch?v= 뒤에부터 입력해주세요.
                            </p>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">상세내용</label>
                        <div class="col-sm-10">
                            <textarea class="form-control input-sm notRequired" name="cntn" title="상세내용" placeholder="상세내용을 입력하세요." maxlength="2000"><c:if test="${rtnDto.typeCd eq 'youtube'}">${rtnDto.cntn}</c:if></textarea>
                        </div>
                    </div>
                </fieldset>
            </fieldset>
            <!-- SNS 영역 -->
            <fieldset id="snsDiv" style="display: none">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">상세내용<span class="star text-danger"> *</span></label>
                        <div class="col-sm-10 dextEditor" id="dextCntn"><c:if test="${rtnDto.typeCd eq 'sns'}">${rtnDto.cntn}</c:if></div>
                        <textarea class="" name="dextCntn" title="상세내용" style="display:none;"></textarea>
                    </div>
                </fieldset>
            </fieldset>

            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star text-danger"> *</span></label>
                    <div class="col-sm-10">
                        <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <input type="hidden" class="notRequired" name="strPam" value="${strPam}">
                    <button type="button" class="btn btn-sm btn-default" id="btnList">목록</button>
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
                            <td>${ rtnDto.regName } (${ rtnDto.regId })</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ rtnDto.regDtm ne rtnDto.modDtm }">
                                        ${ rtnDto.modName } (${ rtnDto.modId })
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