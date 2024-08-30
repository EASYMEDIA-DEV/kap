<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/sv/sva/SVASurveyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.srvSeq}" />
            <input type="hidden" class="notRequired" id="posbChg" name="posbChg" value="${rtnDto.posbChg}" />

            <h5 class="ml mb-xl"><em class="ion-play mr-sm"></em>설문 기본정보</h5>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">설문유형<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="hidden" name="preTypeCd" class="notRequired">
                        <c:forEach var="cdList" items="${cdOncDtlList.SURVEY_TYPE}" varStatus="status">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="typeCd" class="typeCd" value="${cdList.cd}" <c:if test="${rtnDto.typeCd eq cdList.cd}">checked</c:if> />
                                <span class="ion-record"></span> ${cdList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" maxlength="50" title="제목" placeholder="제목을 입력해주세요." />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group form-inline text-sm">
                    <label class="col-sm-1 control-label">예상 응답시간<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <select class="form-control input-sm wd-sm" name="rspnMm" id="rspnMm" title="예상 응답시간">
                            <option value="5" <c:if test="${rtnDto.rspnMm eq '5'}">selected</c:if>>5</option>
                            <option value="10" <c:if test="${rtnDto.rspnMm eq '10'}">selected</c:if>>10</option>
                            <option value="15" <c:if test="${rtnDto.rspnMm eq '15'}">selected</c:if>>15</option>
                            <option value="20" <c:if test="${rtnDto.rspnMm eq '20'}">selected</c:if>>20</option>
                            <option value="25" <c:if test="${rtnDto.rspnMm eq '25'}">selected</c:if>>25</option>
                            <option value="30" <c:if test="${rtnDto.rspnMm eq '30'}">selected</c:if>>30</option>
                            <option value="35" <c:if test="${rtnDto.rspnMm eq '35'}">selected</c:if>>35</option>
                            <option value="40" <c:if test="${rtnDto.rspnMm eq '40'}">selected</c:if>>40</option>
                            <option value="45" <c:if test="${rtnDto.rspnMm eq '45'}">selected</c:if>>45</option>
                            <option value="50" <c:if test="${rtnDto.rspnMm eq '50'}">selected</c:if>>50</option>
                            <option value="55" <c:if test="${rtnDto.rspnMm eq '55'}">selected</c:if>>55</option>
                            <option value="60" <c:if test="${rtnDto.rspnMm eq '60'}">selected</c:if>>60</option>
                        </select>
                        &nbsp;분&nbsp;
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">설문개요<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="hidden" name="hiddenTextArea" title="설문개요">
                        <textarea class="form-control notRequired ckeditorRequired" id="cntn" name="cntn" title="설문개요">${rtnDto.cntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" <c:if test="${rtnDto.expsYn eq 'Y' || rtnDto.expsYn eq null}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-body text-center">
                            <p class="card-text"><em class="ion-android-warning" style="font-size:25px;"></em> 노출여부를 ‘미노출’로 설정 시 이미 진행 중이거나 또는 매핑된 교육/컨설팅/상생사업 이력이 있어도 사용자에게 미표시 됩니다</p>
                        </div>
                    </div>
                </div>
            </fieldset>
                 <h5 class="ml mb-xl" ><em class="ion-play mr-sm"></em>설문 문항(총 <span id="totCnt">1</span>개)</h5>

            <c:choose>
                <c:when test="${ rtnDto.srvSeq != null }">
                    <c:forEach var="qstnList" items="${rtnDto.svSurveyQstnDtlList}" varStatus="qstnStatus">
                        <c:if test="${cd ne qstnList.cd}">
                            <h6 class="ml mb-xl ${fn:substring(qstnList.cd,0,3)}" style="<c:if test="${fn:substring(qstnList.cd,0,3) ne rtnDto.typeCd}">display:none;</c:if>"><em class="ion-android-checkbox-blank mr-sm"></em>${qstnList.cdNm}(총 <span id="${qstnList.cd}Cnt">1</span>개)</h6>
                        </c:if>
                        <c:if test="${qstnList.cd ne 'CON01' && qstnList.cd ne 'CON02'}">
                            <c:set var="rowspan" value="${qstnList.exmplCnt+3}" />
                            <fieldset style="<c:if test="${qstnList.ctgryCd eq null}">display:none;</c:if>" class="surveyList ${qstnList.cd}" data-survey-type="${qstnList.cd}" >
                                <input type="hidden" name="qstn_ord" value="0">
                                <input type="hidden" name="parnt_qstn_ord" value="0">
                                <input type="hidden" name="dpth" value="${qstnList.dpth}">
                                <div class="form-group text-sm">
                                    <table class="table">
                                        <tr>
                                            <th rowspan="${rowspan}" class="col-md-1 ${qstnList.cd}questionTxt">질문1</th>
                                            <th class="col-md-1">설문유형<span class="star"> *</span></th>
                                            <td class="form-inline col-md-8" >
                                                <input type="hidden" name="preQuestionType" class="notRequired">
                                                <select class="form-control input-sm wd-sm questionType" name="srv_type_cd" title="설문유형">
                                                    <c:forEach var="cdList" items="${cdOncDtlList.QUESTION_TYPE}" varStatus="status">
                                                        <label class="radio-inline c-radio">
                                                            <option value="${cdList.cd}" <c:if test="${qstnList.srvTypeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                        </label>
                                                    </c:forEach>
                                                </select>
                                                <button type="button" class="btn btn-sm btn-inverse addQuestion" >문항추가</button>
                                                <button type="button" class="btn btn-sm btn-info addSubQuestion" <c:if test="${qstnList.dpth eq 2}">style="display:none;"</c:if>>하위문항추가</button>
                                                <label class="checkbox-inline c-checkbox">
                                                    <input type="checkbox" class="notRequired" name="ncs_yn" value="Y" <c:if test="${qstnList.ncsYn eq 'Y'}">checked</c:if>  /><span class="ion-checkmark-round"></span> 필수응답
                                                </label>
                                                <c:if test="${fn:contains(qstnList.cd, 'WIN')}">
                                                    <%-- 2024-07-12 추가개발 ppt 3 추가, 2024-08-06 수정 --%>
                                                    <label class="checkbox-inline c-checkbox">
                                                        <input type="checkbox" class="notRequired nonApplicableYn" name="nonApplicableYn" value="Y" <c:if test="${qstnList.nonApplicableYn eq 'Y'}">checked</c:if>  /><span class="ion-checkmark-round"></span> 해당사항 없음
                                                    </label>
                                                    <%-- 2024-08-06 추가 --%>
                                                    <label class="checkbox-inline c-checkbox">
                                                        <input type="checkbox" class="notRequired scoreExclusionYn" name="scoreExclusionYn" value="Y" <c:if test="${qstnList.scoreExclusionYn eq 'Y'}">checked</c:if>  /><span class="ion-checkmark-round"></span> 점수 미반영
                                                    </label>
                                                </c:if>
                                            </td>
                                            <td class="col-md-1">
                                                <button type="button" class="btn btn-sm btn-danger delQuestion" <c:if test="${qstnList.qstnGrpOrd eq 1}">style="display:none;"</c:if>>문항삭제</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>질문<span class="star"> *</span></th>
                                            <td ><input type="text" class="form-control input-sm <c:if test="${qstnList.dpth eq 0}">notRequired</c:if>" name="qstn_nm" value="${qstnList.qstnNm}" maxlength="150" title="질문" placeholder="질문을 입력해주세요." /></td> <%-- 2024-04-03 글자 수 제한 수정 --%>
                                            <td></td>
                                        </tr>
                                        <c:choose>
                                            <c:when test="${qstnList.svSurveyExmplDtlList.size() > 0}">
                                                <c:forEach var="exmplList" items="${qstnList.svSurveyExmplDtlList}" varStatus="exmplStatus">
                                                    <tr class="answerForm" <c:if test="${qstnList.srvTypeCd eq 'QST03' || qstnList.srvTypeCd eq 'QST04'}">style="display: none;" </c:if>>
                                                        <c:choose>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">
                                                                <c:choose>
                                                                    <c:when test="${exmplStatus.first}">
                                                                        <th>응답(min)<span class="star"> *</span></th>
                                                                    </c:when>
                                                                    <c:when test="${exmplStatus.last}">
                                                                        <th>응답(max)<span class="star"> *</span></th>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <th>응답<span class="star                                                                                                                                                                                                                                                     "> *</span></th>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <th>응답<span class="star"> *</span></th>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    <td class="form-inline">
                                                        <c:choose>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST01'}">
                                                                <input type="text" class="form-control input-sm answer" name="exmpl_nm" value="${exmplList.exmplNm}" <c:if test="${exmplList.otherYn eq 'Y'}">readonly</c:if> maxlength="50" title="응답" placeholder="응답내용을 입력해주세요." style="width:80%"/>
                                                                <input type="text" class="form-control input-sm notRequired subNumber surveyNextNumChk"  name="next_no" value="${exmplList.nextNo}" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%" <c:if test="${qstnList.qstnCnt eq 0}">disabled</c:if>/>
                                                            </c:when>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST02'}">
                                                                <input type="text" class="form-control input-sm answer" name="exmpl_nm" value="${exmplList.exmplNm}" <c:if test="${exmplList.otherYn eq 'Y'}">readonly</c:if>  maxlength="50" title="응답" placeholder="응답내용을 입력해주세요." style="width:100%"/>
                                                                <input type="text" class="form-control input-sm notRequired subNumber surveyNextNumChk"  name="next_no" value="${exmplList.nextNo}" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%;display:none;" disabled/>
                                                            </c:when>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST03' || qstnList.srvTypeCd eq 'QST04'}">
                                                                <input type="text" class="form-control input-sm notRequired answer" name="exmpl_nm" value="${exmplList.exmplNm}" maxlength="50" title="응답" placeholder="응답내용을 입력해주세요." style="width:100%"/>
                                                                <input type="text" class="form-control input-sm notRequired subNumber surveyNextNumChk"  name="next_no" value="${exmplList.nextNo}" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%;display:none;" disabled/>
                                                            </c:when>
                                                            <c:when test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">
                                                                <input type="text" class="form-control input-sm answer <c:if test="${!(exmplStatus.first || exmplStatus.last)}">notRequired</c:if>" name="exmpl_nm" value="${exmplList.exmplNm}" maxlength="50" title="라벨" placeholder="라벨명을 입력해주세요." style="width:80%" <c:if test="${!(exmplStatus.first || exmplStatus.last)}">disabled</c:if>/>
                                                                <input type="text" class="form-control input-sm notRequired subNumber surveyNextNumChk"  name="next_no" value="${exmplList.nextNo}" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%;" <c:if test="${qstnList.qstnCnt eq 0}">disabled</c:if>/>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <%-- 2024-08-23 추가개발 s --%>
                                                    <c:if test="${(qstnList.srvTypeCd eq 'QST01' or qstnList.srvTypeCd eq 'QST02') and fn:contains(qstnList.cd, 'WIN')}">
                                                        <td class="otherYnLabel">
                                                            <label class="checkbox-inline c-checkbox">
                                                                <input type="checkbox" class="notRequired otherYn" name="otherYn" value="Y" <c:if test="${exmplList.otherYn eq 'Y'}">checked</c:if> /><span class="ion-checkmark-round"></span> 기타
                                                            </label>
                                                        </td>
                                                    </c:if>
                                                    <%-- 2024-08-23 추가개발 e --%>
                                                    <td>
                                                        <button type="button" class="btn btn-sm btn-inverse addAnswer" <c:if test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">style="display:none;"</c:if>>+</button>
                                                        <button type="button" class="btn btn-sm btn-danger delAnswer" <c:if test="${qstnList.srvTypeCd eq 'QST05' || qstnList.srvTypeCd eq 'QST06' || qstnList.srvTypeCd eq 'QST07'}">style="display:none;"</c:if>>-</button>
                                                    </td>
                                                </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="answerForm">
                                                    <th>응답<span class="star"> *</span></th>
                                                    <td class="form-inline">
                                                        <input type="text" class="form-control input-sm <c:if test="${qstnList.dpth eq 0}">notRequired</c:if> answer" name="exmpl_nm" value="" maxlength="50" title="응답" placeholder="응답내용을 입력해주세요." style="width:80%"/>
                                                        <input type="text" class="form-control input-sm notRequired subNumber"  name="next_no" value="" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%" disabled/>
                                                    </td>
                                                    <%-- 2024-08-23 추가개발 s --%>
                                                    <c:if test="${fn:contains(qstnList.cd, 'WIN')}">
                                                        <td class="otherYnLabel">
                                                            <label class="checkbox-inline c-checkbox">
                                                                <input type="checkbox" class="notRequired otherYn" name="otherYn" value="Y" /><span class="ion-checkmark-round"></span> 기타
                                                            </label>
                                                        </td>
                                                    </c:if>
                                                    <%-- 2024-08-23 추가개발 e --%>
                                                    <td>
                                                        <button type="button" class="btn btn-sm btn-inverse addAnswer" >+</button>
                                                        <button type="button" class="btn btn-sm btn-danger delAnswer" >-</button>
                                                    </td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </table>
                                </div>
                            </fieldset>
                        </c:if>
                        <c:set var="cd" value="${ qstnList.cd}" />
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="cdTwoList" items="${cdTwoDtlList.SURVEY_TYPE}" varStatus="status">
                        <h6 class="ml mb-xl ${fn:substring(cdTwoList.cd,0,3)}" style="display:none;" ><em class="ion-android-checkbox-blank mr-sm"></em>${cdTwoList.cdNm}(총 <span id="${cdTwoList.cd}Cnt">1</span>개)</h6>
                        <c:if test="${cdTwoList.cd ne 'CON01' && cdTwoList.cd ne 'CON02'}">
                            <fieldset style="display:none;" class="surveyList ${cdTwoList.cd}" data-survey-type="${cdTwoList.cd}" >
                                <input type="hidden" name="qstn_ord" value="0">
                                <input type="hidden" name="parnt_qstn_ord" value="0">
                                <input type="hidden" name="dpth" value="1">
                                <div class="form-group text-sm">
                                    <table class="table">
                                        <tr>
                                            <th rowspan="3" class="col-md-1 ${cdTwoList.cd}questionTxt">질문1</th>
                                            <th class="col-md-1">설문유형<span class="star"> *</span></th>
                                            <td class="form-inline col-md-8" >
                                                <input type="hidden" name="preQuestionType" class="notRequired">
                                                <select class="form-control input-sm wd-sm questionType" name="srv_type_cd" title="설문유형">
                                                    <c:forEach var="cdList" items="${cdOncDtlList.QUESTION_TYPE}" varStatus="status">
                                                        <label class="radio-inline c-radio">
                                                            <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                        </label>
                                                    </c:forEach>
                                                </select>
                                                <button type="button" class="btn btn-sm btn-inverse addQuestion" >문항추가</button>
                                                <button type="button" class="btn btn-sm btn-info addSubQuestion" >하위문항추가</button>
                                                <label class="checkbox-inline c-checkbox">
                                                    <input type="checkbox" class="notRequired" name="ncs_yn" value="Y" /><span class="ion-checkmark-round"></span> 필수응답
                                                </label>
                                                <c:if test="${fn:contains(cdTwoList.cd, 'WIN')}">
                                                    <%-- 2024-07-12 추가개발 ppt 3 추가, 2024-08-06 수정 --%>
                                                    <label class="checkbox-inline c-checkbox">
                                                        <input type="checkbox" class="notRequired nonApplicableYn" name="nonApplicableYn" value="Y" /><span class="ion-checkmark-round"></span> 해당사항 없음
                                                    </label>
                                                    <%-- 2024-08-06 추가 --%>
                                                    <label class="checkbox-inline c-checkbox">
                                                        <input type="checkbox" class="notRequired scoreExclusionYn" name="scoreExclusionYn" value="Y" /><span class="ion-checkmark-round"></span> 점수미반영
                                                    </label>
                                                </c:if>
                                            </td>
                                            <td class="col-md-1">
                                                <button type="button" class="btn btn-sm btn-danger delQuestion" style="display:none;">문항삭제</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>질문<span class="star"> *</span></th>
                                            <td ><input type="text" class="form-control input-sm" name="qstn_nm" value="" maxlength="150" title="질문" placeholder="질문을 입력해주세요." /></td> <%-- 2024-04-03 글자 수 제한 수정 --%>
                                            <td></td>
                                        </tr>
                                        <tr class="answerForm">
                                            <th>응답<span class="star"> *</span></th>
                                            <td class="form-inline">
                                                <input type="text" class="form-control input-sm answer" name="exmpl_nm" value="" maxlength="50" title="응답" placeholder="응답내용을 입력해주세요." style="width:80%"/>
                                                <input type="text" class="form-control input-sm notRequired subNumber surveyNextNumChk"  name="next_no" value="" maxlength="50" title="하위번호" placeholder="하위번호" style="width:19%" disabled/>
                                            </td>
                                            <%-- 2024-08-23 추가개발 s --%>
                                            <c:if test="${fn:contains(cdTwoList.cd, 'WIN')}">
                                                <td class="otherYnLabel">
                                                    <label class="checkbox-inline c-checkbox">
                                                        <input type="checkbox" class="notRequired otherYn" name="otherYn" value="Y" /><span class="ion-checkmark-round"></span> 기타
                                                    </label>
                                                </td>
                                            </c:if>
                                            <%-- 2024-08-23 추가개발 e --%>
                                            <td>
                                                <button type="button" class="btn btn-sm btn-inverse addAnswer" >+</button>
                                                <button type="button" class="btn btn-sm btn-danger delAnswer" >-</button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </fieldset>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>


            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="if(confirm('목록으로 이동 시 입력한 값이 초기화 처리됩니다. 이동하시겠습니까?')){location.href='./list?${strPam}'}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto}">
                            <c:if test="${rtnDto.posbChg ne 'false'}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            </c:if>
                            <button type="button" class="btn btn-sm btn-success" id="btnSubmit">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-sm btn-success" id="btnSubmit">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${not empty rtnDto}">
                <h5 class="ml mb-xl"><em class="ion-play mr-sm"></em>등록/수정이력</h5>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</p>
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
                        <label class="col-sm-1 control-label">최종 수정일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
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
        </form>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        var modiYn = "N";

        $(document).on('input', 'select, input:text, textarea', function() {
            if (modiYn === "N" && $(this).val()) {
                modiYn = "Y";
            }
        });

        $(document).on('change', 'input:checkbox', function() {
            if (modiYn === "N" && $(this).is(":checked")) {
                modiYn = "Y";
            }
        });

        // 수정 페이지의 경우 modiYn을 "Y"로 설정
        if ($("#detailsKey").val() && $("#detailsKey").val() !== undefined) {
            modiYn = "Y";
        }

        history.pushState(null, null, '');
        window.addEventListener('popstate', browserPopstateHandler);

        function browserPopstateHandler(event) {

            if (modiYn === "Y") {
                var confirmed = confirm(msgCtrl.getMsg("confirm.list"));

                // browserPopstateHandler가 재발생할 수 있도록 브라우저 상태 다시 설정하기
                history.pushState(null, null, window.location.pathname + window.location.search);

                if (confirmed) {
                    var strPam = $("#btnList").data("strPam");
                    location.href = "./list?" + strPam;
                }
            } else {
                var strPam = $("#btnList").data("strPam");
                location.href = "./list?" + strPam;
            }
        }

        // 페이지 이동 시 이벤트 핸들러 등록 해제
        $(window).on('beforeunload', function() {
        });
    });

</script>