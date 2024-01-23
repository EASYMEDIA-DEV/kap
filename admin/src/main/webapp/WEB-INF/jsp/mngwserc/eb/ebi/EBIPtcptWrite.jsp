<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebi/EBIPtcptWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 신청자 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="ptcptSeq" name="ptcptSeq" value="${rtnDto.ptcptSeq}" />


            <!-- 과정정보 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-12">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>비회원 교육정보</h6>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <p class="form-control-static ctgryCdNm">
                            <c:if test="${rtnDto.prntCdNm ne null}">
                                ${rtnDto.prntCdNm} > ${rtnDto.ctgryCdNm}
                            </c:if>
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-4" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.nm}</p>
                    </div>
                    <label class="col-sm-1 control-label">과정요약<span class="star"> *</span></label>
                    <div class="col-sm-4" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.smmryNm}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                    <div class="col-sm-4" style="margin-left: -15px">
                        <p class="form-control-static">${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                    </div>
                    <label class="col-sm-1 control-label">교육기간<span class="star"> *</span></label>
                    <div class="col-sm-4" style="margin-left: -15px">
                        <input type="hidden" name="edctnStrtDtm" id="edctnStrtDtm" value="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                        <input type="hidden" name="edctnEndDtm" id="edctnEndDtm" value="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                        <p class="form-control-static">${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">강사<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <p class="form-control-static">
                            <c:set var="isttrSize" value="${fn:length(isttrList)}" />
                            <c:forEach var="isttrList" items="${isttrList}" varStatus="status">
                                ${isttrList.name} <c:if test="${isttrSize > 1 && status.count < isttrSize}">, </c:if>
                            </c:forEach>
                        </p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">정원<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <c:if test="${rtnDto.fxnumImpsbYn eq 'Y'}">
                            <p class="form-control-static">${rtnDto.fxnumCnt}명</p>
                        </c:if>
                        <c:if test="${rtnDto.fxnumImpsbYn eq 'N'}">
                            <p class="form-control-static">제한없음</p>
                        </c:if>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">문의담당자<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.picNm} / ${rtnDto.picEmail} / ${rtnDto.picTelNo}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <p class="form-control-static">${roomDto.nm}</p>
                    </div>
                </div>
            </fieldset>


            <!-- 회원정보 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-9">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-2" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm" id="ptcptBsnmNo" name="ptcptBsnmNo" value="${rtnDto.ptcptBsnmNo}" title="사업자등록번호" maxlength="10" />
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-inverse btn-sm btnCmpnChk">인증</button>
                    </div>
                    <div class="col-sm-2 satisfy" style="display: none; color: red;">
                        <p class="satisfy-msg mt-sm pl0 ml0">인증되었습니다.</p>
                    </div>
                    <input type="hidden" class="notRequired" id="authCheck" name="authCheck" value="" disabled />
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm notRequired" id="ptcptCmpnNm" name="ptcptCmpnNm" value="${rtnDto.ptcptCmpnNm}" title="부품사명" maxlength="50" readonly />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">성명<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm" id="name" name="name" value="${rtnDto.name}" title="성명" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm emailChk" id="email" name="email" value="${rtnDto.email}" title="이메일" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-2" style="margin-left: -15px">
                        <select class="form-control input-sm" data-name="deptCd" id="deptCd" name="deptCd" title="부서">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'MEM_CD02') and fn:length(cdList.cd) > 8}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm" value="${rtnDto.deptDtlNm}" id="deptDtlNm" name="deptDtlNm" title="부서 상세" maxlength="50" />
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-2" style="margin-left: -15px">
                        <select class="form-control input-sm" data-name="pstnCd" id="pstnCd" name="pstnCd" title="직급">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                <c:if test="${fn:contains(cdList.cd, 'MEM_CD01') and fn:length(cdList.cd) > 8}">
                                    <option value="${cdList.cd}" <c:if test="${rtnData.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2" id="pstnNmChk" style="display: none; margin-left: -15px;">
                        <input type="text" class="form-control input-sm notRequired" value="${rtnDto.pstnNm}" id="pstnNm" name="pstnNm" title="직급 상세" maxlength="50" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">핸드폰번호<span class="star"> *</span></label>
                    <div class="col-sm-9" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm" value="${rtnDto.hpNo}" id="hpNo" name="hpNo" title="핸드폰번호" maxlength="50" />
                    </div>
                </div>
            </fieldset>

            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="javascript:history.back();">취소</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>

        </form>
    </div>
</div>

