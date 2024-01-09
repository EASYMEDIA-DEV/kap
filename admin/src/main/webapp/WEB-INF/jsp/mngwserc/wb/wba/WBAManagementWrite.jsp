<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid" data-controller="controller/co/COFormCtrl controller/wb/wba/WBAManagementWriteCtrl">
    <div class="card-body">
        <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>
            <c:if test="${not empty rtnData.bsnCd }">
                사업생성관리 상세/수정
            </c:if>
            <c:if test="${empty rtnData.bsnCd }">
                사업생성관리 등록
            </c:if>
        </h7>
        <hr >
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.bsnCd}" />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">사업코드<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <select class="form-control input-sm" id="bsnCd" name="bsnCd" title="사업코드" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                            <c:forEach var="cdList" items="${cdDtlList.BM_CODE}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.bsnCd eq cdList.cd}">selected</c:if>>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
           <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">사용자 메뉴<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" id="userMenuName" value="${rtnDto.userMenuName}" readonly title="사용자 메뉴" style="width: 350px;"/>
                        <input type="hidden" class="notRequired" name="userMenuSeq"  id="userMenuSeq" value="${rtnDto.userMenuSeq}"/>
                    </div>
                    <div class="col-sm-8">
                        <button type="button" class="btn btn-info btn-sm mb-sm" id="userMenu">사용자 메뉴선택</button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">관리자 메뉴(회차관리)<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" id="admEpisdMenuName" value="<c:if test="${not empty rtnDto}">${rtnDto.admEpisdMenuParntName}(${rtnDto.admEpisdMenuName})</c:if>" readonly title="관리자 메뉴(회차관리)" readonly style="width: 350px;"/>
                        <input type="hidden" class="notRequired" name="admEpisdMenuSeq" id="admEpisdMenuSeq" value="${rtnDto.admEpisdMenuSeq}"/>
                    </div>
                    <div class="col-sm-8">
                        <button type="button" class="btn btn-info btn-sm mb-sm" id="adminMenu">관리자 메뉴선택</button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">관리자 메뉴(신청부품사관리)<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control input-sm" id="admAppctnMenuName" value="<c:if test="${not empty rtnDto}">${rtnDto.admAppctnMenuParntName}(${rtnDto.admAppctnMenuName})</c:if>" readonly title="관리자 메뉴(신청부품사관리)" readonly style="width: 350px;"/>
                        <input type="hidden" class="form-control input-sm" name="admAppctnMenuSeq" id="admAppctnMenuSeq" value="${rtnDto.admAppctnMenuSeq}"/>
                    </div>
                    <div class="col-sm-8">
                        <button type="button" class="btn btn-info btn-sm mb-sm" id="adminMenuCompany">관리자 메뉴선택</button>
                    </div>
                </div>
            </fieldset>

            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>
                <c:if test="${not empty rtnData.bsnCd }">
                    사업단계 상세/수정
                </c:if>
                <c:if test="${empty rtnData.bsnCd }">
                    사업단계 등록
                </c:if>
            </h7>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm stepListContainer" data-controller="controller/wb/wba/WBAStepMakeCtrl" style="display:none;">
                    <c:choose>
                        <c:when test="${rtnDto.bsnCd != null}">
                            <c:forEach var="stepList" items="${rtnDto.managementDtlList}" varStatus="stepStatus">
                                <div class="col-sm-12 stepList mt-sm pl0 pr0">
                                    <hr>
                                    <label class="col-sm-1 control-label stepNm">단계 ${stepStatus.index + 1}</label>
                                    <div class="col-sm-11 pl0 pr0 stepFileList">
                                        <div class="col-sm-12">
                                            <div>
                                                <label class="col-sm-2 control-label">단계명<span class="star"> *</span></label>
                                                <c:choose>
                                                    <c:when test="${stepStatus.index == 0}">
                                                        <div class="col-sm-3 stepName">
                                                            신청
                                                            <input type="hidden" class="notRequired" name="stageNm" value="신청"/>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-sm-3 stepName">
                                                            <input type="text" class="notRequired" name="stageNm" value="${stepList.stageNm}"/>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="col-sm-2 pl0">
                                                    <button type="button" class="btn btn-sm btn-inverse btnStepWrite">단계추가</button>
                                                    &nbsp;&nbsp;&nbsp;&nbsp&nbsp;<input type="checkbox" class="fileBtn notRequired" data-filedSize="1" name="fileYn" value="Y" ${ kl:decode(stepList.fileYn, 'N', 'checked', '') } /> 첨부파일 미포함
                                                </div>
                                                <c:if test="${stepStatus.index ne 0}">
                                                    <div class="col-sm-5 text-right">
                                                        <button type="button" class="btn btn-sm btn-danger btnStepDelete">단계삭제</button>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="fileDiv">
                                            <c:choose>
                                                <c:when test="${not empty stepList.managementOptnList}">
                                                    <c:forEach var="optnList" items="${stepList.managementOptnList}" varStatus="optnStatus">
                                                        <div class="fileListDepth">
                                                            <div>
                                                                <div class="col-sm-12 mt-sm stepOptnName">
                                                                    <label class="col-sm-2 control-label">첨부파일명</label>
                                                                    <div class="col-sm-10 fileNameContainer pl0 pr0">
                                                                        <div class="col-sm-12 fileNameContainerList pl0 pr0 mb-sm">
                                                                            <div class="col-sm-10">
                                                                                <input type="text" class="form-control input-sm notRequired" name="optnNm" value="${optnList.optnNm}" maxlength="50" title="첨부파일명" placeholder="첨부파일명을 입력하세요." />
                                                                            </div>
                                                                            <div class="col-sm-2">
                                                                                <div class="pull-right">
                                                                                    <button type="button" class="btn btn-sm btn-inverse btnAddOptn"><em class="ion-android-add"></em></button>
                                                                                    <button type="button" class="btn btn-sm btn-danger btnDeleteOptn"><em class="ion-android-remove"></em></button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div>
                                                                <div class="col-sm-12 mt-sm optnFile">
                                                                    <label class="col-sm-2 control-label">양식관리</label>
                                                                    <div class="col-sm-10 pl0 pr0">
                                                                        <div class="col-sm-12 pl0 pr0 mb-sm">
                                                                            <input type="hidden" class="notRequired" id="fileSeq${optnList.fileSeq}" name="fileSeq${optnList.fileSeq}" value="${optnList.fileSeq}" />
                                                                            <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                                            <spring:eval var="atchUploadMaxSize" expression="419430400" />
                                                                            <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq${optnList.fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="양식관리 첨부파일">
                                                                                <div class="dz-default dz-message">
                                                                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                                                </div>
                                                                            </div>
                                                                            <p class="text-bold mt">
                                                                                ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="fileListDepth">
                                                        <div>
                                                            <div class="col-sm-12 mt-sm stepOptnName">
                                                                <label class="col-sm-2 control-label">첨부파일명</label>
                                                                <div class="col-sm-10 fileNameContainer pl0 pr0">
                                                                    <div class="col-sm-12 fileNameContainerList pl0 pr0 mb-sm">
                                                                        <div class="col-sm-10">
                                                                            <input type="text" class="form-control input-sm notRequired" name="optnNm" readonly maxlength="50" title="첨부파일명" placeholder="첨부파일명을 입력하세요." />
                                                                        </div>
                                                                        <div class="col-sm-2">
                                                                            <div class="pull-right">
                                                                                <button type="button" class="btn btn-sm btn-inverse btnAddOptn"><em class="ion-android-add"></em></button>
                                                                                <button type="button" class="btn btn-sm btn-danger btnDeleteOptn"><em class="ion-android-remove"></em></button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div>
                                                            <div class="col-sm-12 mt-sm optnFile">
                                                                <label class="col-sm-2 control-label">양식관리</label>
                                                                <div class="col-sm-10 pl0 pr0">
                                                                    <div class="col-sm-12 pl0 pr0 mb-sm">
                                                                        <input type="hidden" class="notRequired" id="fileSeq${optnList.fileSeq}" name="fileSeq${optnList.fileSeq}" value="${optnList.fileSeq}" />
                                                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                                        <spring:eval var="atchUploadMaxSize" expression="419430400" />
                                                                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq${optnList.fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="양식관리 첨부파일">
                                                                            <div class="dz-default dz-message">
                                                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                                            </div>
                                                                        </div>
                                                                        <p class="text-bold mt">
                                                                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                    <div class="stepInitHtml">
                        <div class="col-sm-12 stepList mt-sm pl0 pr0">
                            <hr>
                            <label class="col-sm-1 control-label stepNm">단계 </label>
                            <div class="col-sm-11 pl0 pr0 stepFileList">
                                <div class="col-sm-12">
                                    <div>
                                        <label class="col-sm-2 control-label">단계명<span class="star"> *</span></label>
                                        <div class="col-sm-3 stepName">
                                            신청
                                            <input type="hidden" class="notRequired" name="stageNm" value="신청"/>
                                        </div>
                                        <div class="col-sm-2 pl0">
                                            <button type="button" class="btn btn-sm btn-inverse btnStepWrite">단계추가</button>
                                            &nbsp;&nbsp;&nbsp;&nbsp&nbsp;<input type="checkbox" class="fileBtn notRequired" data-filedSize="1" name="fileYn" value="Y"/> 첨부파일 미포함
                                        </div>
                                        <div class="col-sm-5 text-right">
                                            <button type="button" class="btn btn-sm btn-danger btnStepDelete">단계삭제</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="fileDiv">
                                    <div class="fileListDepth">
                                        <div id="stepOptnNames">
                                            <div class="col-sm-12 mt-sm stepOptnName">
                                                <label class="col-sm-2 control-label">첨부파일명</label>
                                                <div class="col-sm-10 fileNameContainer pl0 pr0">
                                                    <div class="col-sm-12 fileNameContainerList pl0 pr0 mb-sm">
                                                        <div class="col-sm-10">
                                                            <input type="text" class="form-control input-sm notRequired" name="optnNm" value="" maxlength="50" title="첨부파일명" placeholder="첨부파일명을 입력하세요." />
                                                        </div>
                                                        <div class="col-sm-2">
                                                            <div class="pull-right">
                                                                <button type="button" class="btn btn-sm btn-inverse btnAddOptn"><em class="ion-android-add"></em></button>
                                                                <button type="button" class="btn btn-sm btn-danger btnDeleteOptn"><em class="ion-android-remove"></em></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="optnFiles">
                                            <div class="col-sm-12 mt-sm optnFile">
                                                <label class="col-sm-2 control-label">양식관리</label>
                                                <div class="col-sm-10 pl0 pr0">
                                                    <div class="col-sm-12 pl0 pr0 mb-sm">
                                                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                        <spring:eval var="atchUploadMaxSize" expression="419430400" />
                                                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="양식관리 첨부파일">
                                                            <div class="dz-default dz-message">
                                                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                            </div>
                                                        </div>
                                                        <p class="text-bold mt">
                                                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </fieldset>
        <c:if test="${not empty rtnDto.bsnCd}">
            <fieldset></fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">최초 등록자</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">최초 등록일</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">${kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}</p>
                    </div>
                </div>
            </fieldset>
            <c:set var="modFlag" value="${not empty rtnDto.modDtm && (rtnDto.regDtm ne rtnDto.modDtm)}" />
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">최종 수정자</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">
                            <c:choose>
                                <c:when test="${modFlag}">
                                    ${rtnDto.modName} (${rtnDto.modId})
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-1 control-label">최종 수정일</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">
                            <c:choose>
                                <c:when test="${modFlag}">
                                    ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </fieldset>
        </c:if>
        <hr />
        <div class="clearfix">
            <div class="pull-left">
                <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
            </div>
            <div class="pull-right">
                <c:choose>
                    <c:when test="${ not empty rtnDto.bsnCd}">
                        <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                        <button type="submit" class="btn btn-sm btn-success">수정</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-sm btn-success">등록</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/wb/wba/WBAManagementTree.jsp"></jsp:include>