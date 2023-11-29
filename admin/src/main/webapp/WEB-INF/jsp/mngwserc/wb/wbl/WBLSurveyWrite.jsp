<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbl/WBLSurveyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.cxstnSrvSeq}" />


            <c:if test="${ not empty rtnEpisdSurveyData.list}">
                <c:forEach var="list" items="${rtnEpisdSurveyData.list}" varStatus="status">
                    <input type="hidden" name="episdSurveyYearEpisd" value="${fn:substring(list.year,0,4)}${list.episd}" data-srv-seq="${list.srvSeq}" data-titl="${list.titl}" data-year="${fn:substring(list.year,0,4)}" data-episd="${list.episd}" data-cxstn-cmpn-episd-seq="${list.cxstnCmpnEpisdSeq}">
                </c:forEach>
            </c:if>

            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>회차정보</h6>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">년도/회차<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="year" value="${fn:substring(rtnDto.year,0,4)}">
                                <input type="hidden" name="episd" value="${rtnDto.episd}">
                                <p class="form-control-static">${fn:substring(rtnDto.year,0,4)}년 ${rtnDto.episd}회차</p>
                            </c:when>
                            <c:otherwise>
                                <select class="form-control input-sm episdSurveySelect episdSurveyYear" name="year" title="년도">
                                    <option value="">선택</option>
                                </select>
                                <select class="form-control input-sm episdSurveySelect" name="episd" title="회차">
                                    <option value="">선택</option>
                                </select>
                            </c:otherwise>
                        </c:choose>

                        <input type="hidden" name="episdSurveySrvSeq" value="${rtnDto.srvSeq}">
                        <input type="hidden" name="cxstnCmpnEpisdSeq" value="value="${rtnDto.cxstnCmpnEpisdSeq}">
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">설문지</label>
                    <div class="col-sm-5 ">
                        <p class="form-control-static episdSurveyText">${rtnDto.titl}</p>
                    </div>
                </div>
            </fieldset>


            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>대상 업체 정보</h6>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">1차 부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="partCmpnNm1" value="${rtnDto.partCmpnNm1}"/>
                                <p class="form-control-static">${rtnDto.partCmpnNm1}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm" id="partCmpnNm1" name="partCmpnNm1"  maxlength="200" title="1차부품사명" placeholder="부품사명 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label class="col-sm-1 control-label">1차 부품사코드<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="partCmpnNm1" value="${rtnDto.partCmpnCd1}"/>
                                <p class="form-control-static">${rtnDto.partCmpnCd1}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm" id="partCmpnCd1" name="partCmpnCd1" maxlength="200" title="1차부품사명" placeholder="부품사명 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>

            <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>수행 업체 정보</h6>

            <fieldset>
                <div class="form-group /-inline">
                    <label class="col-sm-1 control-label">2차 부품사명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="partCmpnNm2" value="${rtnDto.partCmpnNm2}"/>
                                <p class="form-control-static">${rtnDto.partCmpnNm2}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm" id="partCmpnNm2" name="partCmpnNm2" maxlength="200" title="2차부품사명" placeholder="부품사명 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label class="col-sm-1 control-label">2차 부품사코드<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="partCmpnCd2" value="${rtnDto.partCmpnCd2}"/>
                                <p class="form-control-static">${rtnDto.partCmpnCd2}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm" id="partCmpnCd2" name="partCmpnCd2" maxlength="200" title="2차부품사코드" placeholder="코드 입력"  style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
            <c:if test="${rtnDto.cxstnSrvSeq != null}">
                <fieldset>
                    <div class="form-group form-inline">
                        <label class="col-sm-1 control-label">인증번호<span class="star"> *</span></label>
                        <div class="col-sm-5">
                             <p class="form-control-static">${rtnDto.crtfnNo}</p>
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="rprsntNm" value="${rtnDto.rprsntNm}"/>
                                <p class="form-control-static">${rtnDto.rprsntNm}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm nameChk" id="rprsntNm" name="rprsntNm"  maxlength="200" title="대표자명" placeholder="대표자명 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label class="col-sm-1 control-label">사업자등록번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="bsnmRegNo" value="${rtnDto.bsnmRegNo}"/>
                                <p class="form-control-static">${rtnDto.bsnmRegNo}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm numberChk" id="bsnmRegNo" name="bsnmRegNo" maxlength="10" title="사업자등록번호" placeholder="사업자등록번호 입력"  style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">담당자명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="picNm" value="${rtnDto.picNm}"/>
                                <p class="form-control-static">${rtnDto.picNm}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm nameChk" id="picNm" name="picNm" value="" maxlength="200" title="담당자명" placeholder="담당자명 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="telNo" value="${rtnDto.telNo}"/>
                                <p class="form-control-static">${rtnDto.telNo}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm phoneChk" id="telNo" name="telNo" value="" maxlength="200" title="전화번호" placeholder="전화번호 입력"  style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group form-inline">
                    <label class="col-sm-1 control-label">이메일 주소<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <c:choose>
                            <c:when test="${rtnDto.cxstnSrvSeq != null}">
                                <input type="hidden" name="email" value="${rtnDto.email}"/>
                                <p class="form-control-static">${rtnDto.email}</p>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control input-sm emailChk" id="email" name="email" value="" maxlength="200" title="이메일 주소" placeholder="이메일 주소 입력" style="width:100%"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${rtnDto.cxstnSrvSeq != null}">
                        <label class="col-sm-1 control-label">참여여부<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static">${ rtnDto.ptcptCd eq 'E' ? '대기' : rtnDto.ptcptCd eq 'N' ? '미참여' : '참여' }
                            </p>
                            <c:if test="${rtnDto.ptcptCd eq 'Y'}"> <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnReset">초기화</button></c:if>
                        </div>

                    </c:if>
                </div>
            </fieldset>

            <c:if test="${rtnDto.cxstnSrvSeq != null}">
                <fieldset>
                    <div class="form-group form-inline">
                        <label class="col-sm-1 control-label">점수<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static">${rtnDto.score}</p>
                        </div>
                    </div>
                </fieldset>
            </c:if>

            <c:if test="${rtnSurveyData != null}">
                <h6 class="ml mb-xl"><em class="ion-play mr-sm"></em>설문정보</h6>
                <c:forEach var="qstnList" items="${rtnSurveyData.svSurveyQstnDtlList}" varStatus="qstnStatus">
                    <c:if test="${cd ne qstnList.cd}">
                        <h6 class="ml mb-xl ${fn:substring(qstnList.cd,0,3)}"><em class="ion-android-checkbox-blank mr-sm"></em>${qstnList.cdNm}</h6>
                    </c:if>
                    <c:if test="${qstnList.cd ne 'CON01' && qstnList.cd ne 'CON02'}">
                        <c:set var="rowspan" value="${qstnList.exmplCnt+3}" />
                        <fieldset style="<c:if test="${qstnList.ctgryCd eq null}">display:none;</c:if>" class="surveyList ${qstnList.cd}" data-survey-type="${qstnList.cd}" >
                            <input type="hidden" name="dpth" value="${qstnList.dpth}">
                            <div class="form-group text-sm">
                                <table class="table">
                                    <tr>
                                        <th rowspan="${rowspan}" class="col-md-1 ${qstnList.cd}questionTxt">질문1</th>
                                        <th class="col-md-1">설문유형<span class="star"> *</span></th>
                                        <td class="form-inline col-md-8" >
                                            ${qstnList.srvTypeNm}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>질문<span class="star"> *</span></th>
                                        <td>${qstnList.qstnNm}</td>
                                        <td></td>
                                    </tr>
                                    <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                        <tr class="answerForm">
                                            <th>응답<span class="star"> *</span></th>
                                            <td class="form-inline">
                                            <c:choose>
                                                <c:when test="${qstnList.srvTypeCd eq 'QST03' || qstnList.srvTypeCd eq 'QST04'}">
                                                    ${exmplList.winAnswerText}
                                                </c:when>
                                                <c:otherwise>
                                                    ${exmplList.exmplNm} <c:if test="${exmplList.winAnswer > 0}"><em class="ion-checkmark" style="font-size:25px;"></em></c:if></td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </fieldset>
                    </c:if>
                    <c:set var="cd" value="${ qstnList.cd}" />
                </c:forEach>
            </c:if>


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
<%--                            <button type="submit" class="btn btn-sm btn-success">수정</button>--%>
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