<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/ex/exg/EXGExamWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle} 기본정보</h7>
        <hr >
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.examSeq}" />
            <input type="hidden" class="notRequired" id="posbChg" name="posbChg" value="${ kl:decode(rtnDto.posbChg, false, 'false', 'true') }" />
            <input type="hidden" class="notRequired" id="gubun" name="gubun" value="${gubun}" />


            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm classType" name="cd" id="cd" title="과정분류-대분류${rtnDto.prntCd}">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.prntCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm" name="ctgryCd" id="ctgryCd" title="권한" data-ctgrycd="${rtnDto.ctgryCd}">
                                <option value="">선택</option>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="nm" name="nm" value="${rtnDto.nm}" title="제목" maxlength="50" placeholder="과정명 입력" />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm " id="titl" name="titl" value="${rtnDto.titl}" maxlength="100" title="제목" placeholder="제목 입력하세요." />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">시험개요<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control ckeditorRequired" id="smmryCntn" name="smmryCntn" title="시험개요" data-type="${pageGb}" >${rtnDto.smmryCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child ">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
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
            <fieldset class="last-child mb0">
                <div class="form-group text-sm ">
                    <div class="col-sm-12 card bg-info">
                        <div class="col-sm-12 card-body text-center ">
                            <em class="ion-alert-circled mr-sm"></em>노출여부를 ‘미노출’로 설정 시 이미 진행 중이거나 또는 매핑된 교육/컨설팅/상생사업 이력이 있어도 사용자에게 미표시 됩니다.
                        </div>
                    </div>
                </div>
            </fieldset>
            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle} 문항</h7>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm examListContainer" data-controller="controller/ex/exg/EXGExamMakeCtrl" style="display:none;">
                    <c:choose>
                        <c:when test="${ rtnDto.examSeq != null }">
                            <c:forEach var="qstnList" items="${rtnDto.exExamQstnDtlList}" varStatus="qstnStatus">
                                <div class="col-sm-12 examList mt-sm pl0 pr0">
                                    <hr>
                                    <label class="col-sm-1 control-label examQstnNm">질문 ${qstnList.qstnOrd+1}</label>
                                    <input type="hidden" class="notRequired" name="qstnSeq" value="${qstnList.qstnSeq}" />
                                    <div class="col-sm-11 pl0 pr0">
                                        <div class="col-sm-12">
                                            <div>
                                                <label class="col-sm-2 control-label">문항유형<span class="star"> *</span></label>
                                                <div class="col-sm-3">
                                                    <select class="form-control input-sm srvTypeCd" name="srvTypeCd" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }>
                                                        <c:forEach var="cdList" items="${cdDtlList.EXG}" varStatus="status">
                                                            <option value="${cdList.cd}" ${ kl:decode(qstnList.srvTypeCd, cdList.cd, 'selected', '') }>
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-sm-2 pl0">
                                                    <button type="button" class="btn btn-sm btn-inverse btnExamWrite" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }>문항추가</button>
                                                </div>
                                                <c:if test="${ qstnStatus.index != 0}">
                                                    <div class="col-sm-5 text-right">
                                                        <button type="button" class="btn btn-sm btn-danger btnExamDelete" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }>문항삭제</button>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-sm-12 mt-sm">
                                            <div>
                                                <label class="col-sm-2 control-label">질문<span class="star"> *</span></label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control input-sm" name="qstnNm" value="${ qstnList.qstnNm }" maxlength="50" title="질문" placeholder="질문을 입력하세요." ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') } />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-12 mt-sm exmplContainer ">
                                            <c:if test="${  qstnList.exExamExmplDtlList != null && qstnList.exExamExmplDtlList.size() > 0 }">
                                                <label class="col-sm-2 control-label">응답<span class="star"> *</span></label>
                                                <div class="col-sm-10 exmplOptnContainer pl0 pr0">
                                                    <c:forEach var="exmplList" items="${qstnList.exExamExmplDtlList}" varStatus="exmplStatus">
                                                        <div class="col-sm-12 exmplOptnContainerList pl0 pr0 mb-sm">
                                                            <div class="col-sm-9 ">
                                                                <input type="text" class="form-control input-sm" value="${ exmplList.exmplNm }" name="exmplNm" maxlength="50" title="응답내용" placeholder="응답내용을 입력하세요." ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }/>
                                                            </div>
                                                            <div class="col-sm-1">
                                                                <c:choose>
                                                                    <c:when test="${ qstnList.srvTypeCd eq 'EXG_B' }">
                                                                        <label class="checkbox c-checkbox checkbox-inline pl0">
                                                                            <input type="checkbox" name="checkbox"  class="notRequired" value="" title="선택"  ${ kl:decode(exmplList.canswYn, 'Y', 'checked', '') } ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }/>
                                                                            <span class="ion-checkmark-round"></span>
                                                                        </label>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <label class="radio c-radio " >
                                                                            <input type="radio" class="notRequired" name="radio${qstnList.qstnOrd}"  value="Y"  title="선택" ${ kl:decode(exmplList.canswYn, 'Y', 'checked', '') } ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }/>
                                                                            <span class="ion-record"></span>
                                                                        </label>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <div class="col-sm-2">
                                                                <div class="pull-right">
                                                                    <button type="button" class="btn btn-sm btn-inverse btnAddOptn" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }><em class="ion-android-add"></em></button>
                                                                    <button type="button" class="btn btn-sm btn-danger btnDeleteOptn" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') }><em class="ion-android-remove"></em></button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="col-sm-12 mt0">
                                            <div>
                                                <label class="col-sm-2 control-label">배점<span class="star"> *</span></label>
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control input-sm numberChk" name="scord" value="${ qstnList.scord }" maxlength="2" title="배점" placeholder="배점 입력" ${ kl:decode(rtnDto.posbChg, false, 'disabled', '') } />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                    <div class="examHtmlTemplage">
                        <div class="col-sm-12 examList mt-sm pl0 pr0">
                            <hr>
                            <label class="col-sm-1 control-label examQstnNm">질문 </label>
                            <div class="col-sm-11 pl0 pr0">
                                <div class="col-sm-12">
                                    <div>
                                        <label class="col-sm-2 control-label">문항유형<span class="star"> *</span></label>
                                        <div class="col-sm-3">
                                            <select class="form-control input-sm srvTypeCd" name="srvTypeCd">
                                                <c:forEach var="cdList" items="${cdDtlList.EXG}" varStatus="status">
                                                    <option value="${cdList.cd}">
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-sm-2 pl0">
                                            <button type="button" class="btn btn-sm btn-inverse btnExamWrite">문항추가</button>
                                        </div>
                                        <div class="col-sm-5 text-right">
                                            <button type="button" class="btn btn-sm btn-danger btnExamDelete">문항삭제</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 mt-sm">
                                    <div>
                                        <label class="col-sm-2 control-label">질문<span class="star"> *</span></label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control input-sm" name="qstnNm" value="" maxlength="50" title="질문" placeholder="질문을 입력하세요." />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 mt-sm exmplContainer ">
                                    <label class="col-sm-2 control-label">응답<span class="star"> *</span></label>
                                    <div class="col-sm-10 exmplOptnContainer pl0 pr0">
                                        <div class="col-sm-12 exmplOptnContainerList pl0 pr0 mb-sm">
                                            <div class="col-sm-9 ">
                                                <input type="text" class="form-control input-sm" value="" name="exmplNm" maxlength="50" title="응답내용" placeholder="응답내용을 입력하세요." />
                                            </div>
                                            <div class="col-sm-1">
                                                <label class="checkbox c-checkbox checkbox-inline pl0" style="display:none;">
                                                    <input type="checkbox" name="checkbox"  class="notRequired" value="" title="선택" />
                                                    <span class="ion-checkmark-round"></span>
                                                </label>
                                                <label class="radio c-radio " >
                                                    <input type="radio" name="radio" class="notRequired"  value="Y"  title="선택" />
                                                    <span class="ion-record"></span>
                                                </label>
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
                                <div class="col-sm-12 mt0">
                                    <div>
                                        <label class="col-sm-2 control-label">배점<span class="star"> *</span></label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control input-sm numberChk" name="scord" value="" maxlength="2" title="배점" placeholder="배점 입력" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnDto.examSeq}">
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
                        <c:when test="${ not empty rtnDto.examSeq}">
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
