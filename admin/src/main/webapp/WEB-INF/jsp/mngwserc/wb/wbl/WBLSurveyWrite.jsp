<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbl/WBLSurveyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.cxstnSrvSeq}" />

            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>회차정보</h6>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">년도/회차<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm" name="year" title="년도">
                            <option value="">선택</option>
                            <c:set var="startYear" value="2023"/>
                            <c:forEach begin="0" end="10" var="year" step="1">
                                <option value="${startYear+year}" <c:if test="${rtnDto.year eq startYear+year}">selected</c:if>>${startYear+year}년</option>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm" name="episd" title="회차">
                            <option value="">선택</option>
                            <c:forEach begin="1" end="10" var="episd" step="1">
                                <option value="${episd}" <c:if test="${rtnDto.episd eq episd}">selected</c:if>>${episd}차</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>

            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>대상 업체 정보</h6>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">1차 부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="partCmpnNm1" name="partCmpnNm1" value="${rtnDto.partCmpnNm1}" maxlength="200" title="1차부품사명" placeholder="부품사명 입력" style="width:100%"/>
                    </div>
                    <label class="col-sm-1 control-label">1차 부품사코드<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="partCmpnCd1" name="partCmpnCd1" value="${rtnDto.partCmpnCd1}" maxlength="200" title="1차부품사코드" placeholder="코드 입력"  style="width:100%"/>
                    </div>
                </div>
            </fieldset>

            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>수행 업체 정보</h6>

            <fieldset>
                <div class="form-group /-inline">
                    <label class="col-sm-1 control-label">2차 부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="partCmpnNm2" name="partCmpnNm2" value="${rtnDto.partCmpnNm2}" maxlength="200" title="2차부품사명" placeholder="부품사명 입력" style="width:100%"/>
                    </div>
                    <label class="col-sm-1 control-label">2차 부품사코드<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="partCmpnCd2" name="partCmpnCd2" value="${rtnDto.partCmpnCd2}" maxlength="200" title="2차부품사코드" placeholder="코드 입력"  style="width:100%"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="rprsntNm" name="rprsntNm" value="" maxlength="200" title="대표자명" placeholder="대표자명 입력" style="width:100%"/>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="bsnmRegNo" name="bsnmRegNo" value="" maxlength="200" title="사업자등록번호" placeholder="사업자등록번호 입력"  style="width:100%"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">담당자명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="picNm" name="picNm" value="" maxlength="200" title="담당자명" placeholder="담당자명 입력" style="width:100%"/>
                    </div>
                    <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="telNo" name="telNo" value="" maxlength="200" title="전화번호" placeholder="전화번호 입력"  style="width:100%"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">이메일 주소<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="email" name="email" value="" maxlength="200" title="이메일 주소" placeholder="이메일 주소 입력" style="width:100%"/>
                    </div>
                </div>
            </fieldset>


            <c:if test="${not empty rtnDto}">
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
                        <c:when test="${ not empty rtnDto}">
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