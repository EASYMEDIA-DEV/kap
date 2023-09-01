<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/bm/bma/BMANewsWriteCtrl">
        <c:choose>
            <c:when test="${empty rtnDto.newsSeq}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
            <c:otherwise>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:otherwise>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnDto.newsSeq ? './api-insert' : './api-update'}">
            <input type="hidden" class="notRequired" id="popupSeq" name="popupSeq" value="${rtnDto.newsSeq}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.newsSeq}" />
            <input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
            <input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" name="thnlFileSeq" value="${rtnDto.thnlFileSeq}" />

            <!-- 삭제 시 시퀀스 -->
            <input type="hidden" class="notRequired" id="delValueList" name="delValueList" value="${rtnDto.newsSeq}"/>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">계열사<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11 form-inline">
                        <select name="affltCd" id="affltCd" title="계열사" class="form-control input-sm">
                            <option value="">선택</option>
                            <c:choose>
                                <c:when test="${rtnData.lnggCd eq 'en'}">
                                    <c:forEach var="list" items="${rtnData.codeList}">
                                        <option value="${list.cd}" <c:if test="${rtnDto.affltCd eq list.cd}">selected</c:if>>${list.enCdNm}</option>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${rtnData.lnggCd eq 'cn'}">
                                    <c:forEach var="list" items="${rtnData.codeList}">
                                        <option value="${list.cd}" <c:if test="${rtnDto.affltCd eq list.cd}">selected</c:if>>${list.cnCdNm}</option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="list" items="${rtnData.codeList}">
                                        <option value="${list.cd}" <c:if test="${rtnDto.affltCd eq list.cd}">selected</c:if>>${list.cdNm}</option>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">게시일<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-inline">
                            <div class="input-group form-date-group mr-sm">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="expsDtm" name="expsDtm" value="<c:if test="${not empty rtnDto.expsDtm}">${kl:convertDate(rtnDto.expsDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '')}</c:if> " title="게시일" readonly="readonly"/>
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
                    <label class="col-sm-1 control-label">제목<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="<c:out value='${rtnDto.titl}' escapeXml="true"/>" maxlength="100" title="제목" placeholder="제목을 입력하세요." />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">태그<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <c:choose>
                            <c:when test="${rtnData.lnggCd eq 'en'}">
                                <c:forEach var="list" items="${rtnDto.tagList}">
                                    <c:if test="${ not empty list.enCdNm }">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" name="affltCdList" title="태그" value="${list.cd}" <c:forEach var="use" items="${rtnDto.useTagList}"> <c:if test="${use.cd eq list.cd}">checked</c:if> </c:forEach>/>
                                            <span class="ion-checkmark-round"></span> ${list.enCdNm}
                                        </label>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:when test="${rtnData.lnggCd eq 'cn'}">
                                <c:forEach var="list" items="${rtnDto.tagList}">
                                    <c:if test="${ not empty list.cnCdNm }">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" name="affltCdList" title="태그" value="${list.cd}" <c:forEach var="use" items="${rtnDto.useTagList}"><c:if test="${use.cd eq list.cd}">checked</c:if> </c:forEach>/>
                                            <span class="ion-checkmark-round"></span> ${list.cnCdNm}
                                        </label>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="list" items="${rtnDto.tagList}">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxSingle" name="affltCdList" title="태그" value="${list.cd}" <c:forEach var="use" items="${rtnDto.useTagList}"> <c:if test="${use.cd eq list.cd}">checked</c:if> </c:forEach>/>
                                        <span class="ion-checkmark-round"></span> ${list.cdNm}
                                    </label>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일이미지</label>
                    <div class="col-sm-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.newsExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone notRequired" data-file-field-nm="thnlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ 277 X 277, 파일(${fileExtns})만 등록 가능합니다. (최대 1개만 등록 가능합니다.)
                        </p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="dextEditor" id="cntn">${rtnDto.cntn}</div>
                        <textarea name="cntn" title="내용" style="display:none;"></textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">메인노출여부<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="mainExpsYn" value="${kl:nvl(rtnDto.mainExpsYn, 'N')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" class="mainExps" name="mainExpsYn" value="Y" <c:if test="${mainExpsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 메인노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="mainExpsYn" value="N" <c:if test="${mainExpsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 메인미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
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
                    <input class="notRequired" type="hidden" value="${strPam}" name="strPam"/>
                    <button type="button" class="btn btn-sm btn-default" id="btnList">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${empty rtnDto.newsSeq}">
                            <button type="submit" id="btnSubmit" class="btn btn-success btn-sm mb-sm">등록</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-sm btn-danger" id="btnOneDelete">삭제</button>
                            <button type="submit" id="btnSubmit" class="btn btn-sm btn-success">수정</button>
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