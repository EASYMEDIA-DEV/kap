<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/eba/EBACouseWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="prntCd" name="prntCd" value="${rtnDto.prntCd}" />
            <input type="hidden" class="notRequired" id="copyYn" name="copyYn" value="${rtnDto.copyYn}" />
            <input type="hidden" class="notRequired" id="couseEpisdYn" name="couseEpisdYn" value="Y" />

            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex}" />

            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="thnlFileSeq" name="thnlFileSeq" value="${rtnDto.thnlFileSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-6 form-inline">
                        <select class="form-control input-sm wd-sm classType" name="cd" id="cd" title="과정분류-대분류">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.prntCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                        <select class="form-control input-sm wd-sm" name="ctgryCd" id="ctgryCd" title="과정분류-중분류" data-ctgrycd="${rtnDto.ctgryCd}" readonly="true" disabled style="width: 110px;">
                            <option value="">선택</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">자격증연계<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm" name="lcnsCnnctCd" id="lcnsCnnctCd" title="자격증연계">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${studyCdList.LCNS_CNNCT}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.lcnsCnnctCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <label class="col-sm-1 control-label" style="margin-left: 15px">GPC 교육여부<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm" name="gpcYn" id="gpcYn" title="GPC 교육여부">
                                <option value="">선택</option>
                                <option value="Y" <c:if test="${rtnDto.gpcYn eq 'Y'}">selected</c:if>>해당</option>
                                <option value="N" <c:if test="${rtnDto.gpcYn eq 'N'}">selected</c:if>>미해당</option>
                            </select>
                        </div>
                    </div>

                </div>

            </fieldset>

            <c:set var="gpcYn" value="${ not empty rtnDto.gpcYn ? rtnDto.gpcYn : 'N'}"/>

            <fieldset class="gpcYn" <c:if test="${gpcYn eq 'N'}">style="display: none;"</c:if>>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 교육유형<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm wd-sm" name="gpcEdctnType" id="gpcEdctnType" title="GPC 교육유형">
                            <option value="">선택</option>
                            <option value="T" <c:if test="${rtnDto.gpcEdctnType eq 'T'}">selected</c:if> >집체교육</option>
                            <option value="UB" <c:if test="${rtnDto.gpcEdctnType eq 'UB'}">selected</c:if> >비대면B타입</option>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">GPC 레벨<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm wd-sm" name="gpcLvl" id="gpcLvl" title="GPC 레벨">
                            <option value="">선택</option>
                            <option value="LVEL_CD01" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD01'}">selected</c:if> >실무자</option>
                            <option value="LVEL_CD02" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD02'}">selected</c:if> >관리자</option>
                            <option value="LVEL_CD03" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD03'}">selected</c:if> >리더</option>
                            <option value="LVEL_CD04" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD04'}">selected</c:if> >실무자/관리자</option>
                            <option value="LVEL_CD05" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD05'}">selected</c:if> >관리자/리더</option>
                            <option value="LVEL_CD06" <c:if test="${rtnDto.gpcLvl eq 'LVEL_CD06'}">selected</c:if> >공통</option>
                        </select>
                    </div>

                </div>
            </fieldset>

            <fieldset class="gpcYn" <c:if test="${gpcYn eq 'N'}">style="display: none;"</c:if>>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 카테고리<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm wd-sm" name="gpcParntCtgry" id="gpcParntCtgry" title="GPC 카테고리">
                            <option value="">선택</option>
                            <option value="HMGRT_CD" <c:if test="${rtnDto.gpcParntCtgry eq 'HMGRT_CD'}">selected</c:if>>HMG핵심과제</option>
                            <option value="GOLCAP_CD" <c:if test="${rtnDto.gpcParntCtgry eq 'GOLCAP_CD'}">selected</c:if>>글로벌역량</option>
                            <option value="LEAD_CD" <c:if test="${rtnDto.gpcParntCtgry eq 'LEAD_CD'}">selected</c:if>>리더쉽</option>
                            <option value="HMGSPEC_CD" <c:if test="${rtnDto.gpcParntCtgry eq 'HMGSPEC_CD'}">selected</c:if>>HMG특화</option>
                            <option value="NORJB_CD" <c:if test="${rtnDto.gpcParntCtgry eq 'NORJB_CD'}">selected</c:if>>일반직무</option>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">GPC 트랙</label>
                    <div class="col-sm-5">

                        <div id="selectGroup0" style="display:none;">
                            <option value="">선택</option>
                        </div>

                        <div id="selectGroup1" style="display:none;">
                            <option value="">선택</option>
                            <option value="HMGRT_CD01" <c:if test="${rtnDto.gpcCtgry eq 'HMGRT_CD01'}">selected</c:if>>HMG핵심과제</option>
                            <option value="HMGRT_CD02" <c:if test="${rtnDto.gpcCtgry eq 'HMGRT_CD02'}">selected</c:if>>미래기술트렌드</option>
                        </div>
                        <div id="selectGroup2" style="display:none;">
                            <option value="">선택</option>
                            <option value="GOLCAP_CD01" <c:if test="${rtnDto.gpcCtgry eq 'GOLCAP_CD01'}">selected</c:if>>해외현지인육성</option>
                            <option value="GOLCAP_CD02" <c:if test="${rtnDto.gpcCtgry eq 'GOLCAP_CD02'}">selected</c:if>>글로벌리더육성</option>
                            <option value="GOLCAP_CD03" <c:if test="${rtnDto.gpcCtgry eq 'GOLCAP_CD03'}">selected</c:if>>지역전문가육성</option>
                        </div>
                        <div id="selectGroup3" style="display:none;">
                            <option value="">선택</option>
                            <option value="LEAD_CD01" <c:if test="${rtnDto.gpcCtgry eq 'LEAD_CD01'}">selected</c:if>>Basic</option>
                            <option value="LEAD_CD02" <c:if test="${rtnDto.gpcCtgry eq 'LEAD_CD02'}">selected</c:if>>Biz</option>
                            <option value="LEAD_CD03" <c:if test="${rtnDto.gpcCtgry eq 'LEAD_CD03'}">selected</c:if>>People</option>
                        </div>
                        <div id="selectGroup4" style="display:none;">
                            <option value="">선택</option>
                            <option value="HMGSPEC_CD01" <c:if test="${rtnDto.gpcCtgry eq 'HMGSPEC_CD01'}">selected</c:if>>공통분야</option>
                            <option value="HMGSPEC_CD02" <c:if test="${rtnDto.gpcCtgry eq 'HMGSPEC_CD02'}">selected</c:if>>구매분야</option>
                            <option value="HMGSPEC_CD03" <c:if test="${rtnDto.gpcCtgry eq 'HMGSPEC_CD03'}">selected</c:if>>품질분야</option>
                            <option value="HMGSPEC_CD04" <c:if test="${rtnDto.gpcCtgry eq 'HMGSPEC_CD04'}">selected</c:if>>생기분야</option>
                            <option value="HMGSPEC_CD05" <c:if test="${rtnDto.gpcCtgry eq 'HMGSPEC_CD05'}">selected</c:if>>R&D분야</option>
                        </div>

                        <div id="selectGroup5" style="display:none;">
                            <option value="">선택</option>
                            <option value="NORJB_CD01" <c:if test="${rtnDto.gpcCtgry eq 'NORJB_CD01'}">selected</c:if>>인적자원관리</option>
                            <option value="NORJB_CD02" <c:if test="${rtnDto.gpcCtgry eq 'NORJB_CD02'}">selected</c:if>>환경/안전</option>
                            <option value="NORJB_CD03" <c:if test="${rtnDto.gpcCtgry eq 'NORJB_CD03'}">selected</c:if>>자동차입문</option>
                            <option value="NORJB_CD04" <c:if test="${rtnDto.gpcCtgry eq 'NORJB_CD04'}">selected</c:if>>공급망관리</option>
                            <option value="NORJB_CD05" <c:if test="${rtnDto.gpcCtgry eq 'NORJB_CD05'}">selected</c:if>>비지니스스킬</option>
                        </div>

                        <select class="form-control input-sm wd-sm" name="gpcCtgry" id="gpcCtgry" title="GPC 트랙" disabled>
                            <option value="">선택</option>
                        </select>
                    </div>

                </div>
            </fieldset>

            <fieldset class="gpcYn" <c:if test="${gpcYn eq 'N'}">style="display: none;"</c:if>>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 환급과정<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <select class="form-control input-sm wd-sm" name="gpcRfnPrcs" id="gpcRfnPrcs" title="GPC 환급과정">
                            <option value="">선택</option>
                            <option value="N" <c:if test="${rtnDto.gpcRfnPrcs eq 'N'}">selected</c:if>>비환급</option>
                            <option value="R" <c:if test="${rtnDto.gpcRfnPrcs eq 'R'}">selected</c:if>>환급</option>
                        </select>
                    </div>
                    <label class="col-sm-1 control-label">GPC 강사 ID</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm notRequired numberChk" id="gpcIsttrId" name="gpcIsttrId" value="${rtnDto.gpcIsttrId}" title="GPC 강사 ID" maxlength="10" placeholder="GPC 강사 ID 입력" style="max-width: 150px;"/>
                    </div>
                </div>
            </fieldset>

            <fieldset class="gpcYn" <c:if test="${gpcYn eq 'N'}">style="display: none;"</c:if>>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 정원수<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="input_line form-inline">
                            <input type="text" class="form-control input-sm numberChk" id="gpcFxnumCnt" name="gpcFxnumCnt" value="${rtnDto.gpcFxnumCnt}" title="GPC 정원수" maxlength="50" placeholder="정원수 입력" style="max-width: 150px;"/> 명
                        </label>

                    </div>
                </div>
            </fieldset>





            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="nm" name="nm" value="${rtnDto.nm}" title="제목" maxlength="50" placeholder="과정명 입력" />
                    </div>
                    <label class="col-sm-1 control-label">과정요약<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="smmryNm" name="smmryNm" value="${rtnDto.smmryNm}" title="과정요약" maxlength="50" placeholder="과정요약 입력"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정소개<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control input-sm" id="itrdcCntn" name="itrdcCntn" rows="10" maxlength="500" title="과정소개" placeholder="과정 소개를 입력하세요.">${rtnDto.itrdcCntn}</textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 목표<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control input-sm" id="stduyTrgtCntn" name="stduyTrgtCntn" rows="10" maxlength="500" title="학습목표" placeholder="학습목표를 입력하세요.">${rtnDto.stduyTrgtCntn}</textarea>

                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 대상<span class="star"> *</span></label>
                    <div class="col-sm-11">

                        <c:forEach var="list" items="${edTarget}">
                            <div class="row edTargetRow" style="margin-bottom: 20px;">
                                <c:forEach var="targetList" items="${list.edList}">
                                    <c:choose>
                                        <c:when test="${targetList.dpth eq '2'}">
                                            <c:set var="etcYn" value="Y"/>
                                            <c:if test="${targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                <c:set var="etcYn" value="N"/>
                                            </c:if>
                                            <c:if test="${targetList.cdNm eq '기타' && targetList.cd eq 'ED_TARGET05001'}">
                                                <c:set var="etcYn" value="Y"/>
                                            </c:if>
                                            <label class="col-sm-1 control-label" <c:if test="${etcYn eq 'Y'}">style="margin-left: -10px;"</c:if> data-cdnm="${targetList.cdNm}">${targetList.cdNm}
                                                <c:if test="${etcYn eq 'N'}">
                                                <span class="star"> *</span>
                                                </c:if>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${targetList.cdNm ne '기타' && targetList.cd ne 'ED_TARGET05001'}">
                                                <label class="checkbox-inline c-checkbox">
                                                    <c:set var="tempChk" value="N" />
                                                    <c:forEach var="rtnTrgtDataList" items="${rtnTrgtData}">
                                                        <c:if test="${tempChk eq 'N'}">
                                                            <c:if test="${rtnTrgtDataList.targetCd eq targetList.cd}">
                                                                <c:set var="tempChk" value="Y" />
                                                            </c:if>
                                                            <c:if test="${rtnTrgtDataList.targetCd ne targetList.cd}">
                                                                <c:set var="tempChk" value="N" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <%--<input type="checkbox" class="checkboxSingle notRequired" data-name="useYnList" value="${targetList.cd}" name="targetCd"  <c:if test="${kl:forEachChk(targetList.cd, rtnTrgtData) eq 'Y'}">checked</c:if> />--%>
                                                    <input type="checkbox" class="checkboxSingle notRequired" data-name="useYnList" value="${targetList.cd}" name="targetCd"  <c:if test="${tempChk eq 'Y'}">checked</c:if> />
                                                    <span class="ion-checkmark-round"></span> ${targetList.cdNm}
                                                </label>
                                            </c:if>
                                            <c:if test="${targetList.cdNm eq '기타' && targetList.cd eq 'ED_TARGET05001'}">
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm notRequired" id="etcNm" name="etcNm" value="${rtnTrgtData[rtnTrgtData.size()-1].etcNm}" title="기타" maxlength="200" placeholder="미입력 시 사용자 사이트에 하이픈(-)으로 표시"/>
                                                </div>
                                            </c:if>

                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>

                        </c:forEach>
                    </div>
                </div>
            </fieldset>

            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">학습 방식<span class="star"> *</span></label>
                <div class="col-sm-11">
                    <c:forEach var="cdList" items="${studyCdList.STDUY_MTHD}" varStatus="status">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="stduyMthdCd" value="${cdList.cd}" title="학습방식" <c:if test="${rtnDto.stduyMthdCd eq cdList.cd}">checked</c:if>/>
                            <span class="ion-record"></span> ${cdList.cdNm}
                        </label>
                    </c:forEach>

                </div>
            </div>

            <div class="form-group text-sm">

                <label class="col-sm-1 control-label">수료 기준<span class="star"> *</span></label>
                <div class="col-sm-11" style="margin-left: -80px;">
                        <label class="col-sm-1 control-label">출석/수강</label>
                        <div class="col-sm-1">
                            <label class="input_line form-inline">
                                <select class="form-control input-sm wd-sm" name="cmptnStndCd" id="cmptnStndCd" title="출석/수강" style="margin-right: 5px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${studyCdList.CMPTN_STND}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.cmptnStndCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>%
                            </label>

                        </div>

                    <label class="col-sm-1 control-label">평가</label>
                    <div class="col-sm-4 ">
                        <div class="pull-left">
                            <label class="input_line form-inline">
                                <select class="form-control input-sm wd-sm" name="cmptnJdgmtCd" id="cmptnJdgmtCd" title="평가" style="margin-right: 5px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${studyCdList.CMPTN_JDGMT}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.cmptnJdgmtCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>점
                            </label>

                        </div>
                        <div class="pull-left ml-lg">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="checkboxSingle jdgmtYn notRequired" value="N" name="jdgmtYn" <c:if test="${rtnDto.jdgmtYn eq 'N'}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> 평가없음
                            </label>
                        </div>

                    </div>

                </div>
            </div>

            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">학습 시간<span class="star"> *</span></label>
                <div class="col-sm-11"  style="margin-left: -80px;">
                    <label class="col-sm-1 control-label">학습일</label>
                    <div class="col-sm-1">
                        <label class="input_line form-inline">
                            <select class="form-control input-sm wd-sm" name="stduyDdCd" id="stduyDdCd" title="학습일" style="margin-right: 5px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${studyCdList.STDUY_DD}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.stduyDdCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>일
                        </label>
                    </div>

                    <label class="col-sm-1 control-label">학습시간</label>
                    <div class="col-sm-2">
                        <label class="input_line form-inline">
                            <select class="form-control input-sm wd-sm" name="stduyTimeCd" id="stduyTimeCd" title="학습시간" style="margin-right: 5px;">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${studyCdList.STDUY_TIME}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select> 시간
                        </label>
                    </div>
                </div>
            </div>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 준비물<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="stduySuplsNm" name="stduySuplsNm" value="${rtnDto.stduySuplsNm}" title="학습 준비물" maxlength="50" placeholder="학습 준비물 입력" />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">PC 학습내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea maxlength="500" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="학습내용을 입력하세요" id="pcStduyCntn" name="pcStduyCntn" title="PC 학습내용" oninput="cmmCtrl.checkMaxlength(this);">
                            ${rtnDto.pcStduyCntn}
                        </textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">모바일 학습내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea maxlength="500" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="학습내용을 입력하세요" id="mblStduyCntn" name="mblStduyCntn" title="모바일 학습내용" oninput="cmmCtrl.checkMaxlength(this);">
                            ${rtnDto.mblStduyCntn}
                        </textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset class="relField">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연계학습</label>
                    <div class="col-sm-11">


                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">필수과정</label>
                            <div class="col-sm-10 relForm3">
                                <c:forEach var="relList" items="${relList}" varStatus="status">
                                    <c:if test="${relList.cnnctCd eq 'EDCTN_REL03'}">
                                        <div class="row" style="margin-bottom: 20px;">
                                            ▣ ${relList.cnnctNm}
                                            <input type="hidden" class="notRequired cloneHidden" name="edctnRel3" value="${relList.cnnctEdctnSeq}">
                                            <button type="button" class="btn btn-sm btn-danger btnDeleteOptn" onclick='$(this).closest(".row").remove();'><em class="ion-android-remove"></em></button>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <button type="button" class="btn btn-sm btn-success couseSearch" data-rel-status="3">과정검색</button>
                        </div>

                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">선수과정</label>
                            <div class="col-sm-10 relForm1">
                                <c:forEach var="relList" items="${relList}" varStatus="status">
                                    <c:if test="${relList.cnnctCd eq 'EDCTN_REL01'}">
                                        <div class="row" style="margin-bottom: 20px;">
                                            ▣ ${relList.cnnctNm}
                                            <input type="hidden" class="notRequired cloneHidden" name="edctnRel1" value="${relList.cnnctEdctnSeq}">
                                            <button type="button" class="btn btn-sm btn-danger btnDeleteOptn" onclick='$(this).closest(".row").remove();'><em class="ion-android-remove"></em></button>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <button type="button" class="btn btn-sm btn-success couseSearch" data-rel-status="1">과정검색</button>
                        </div>

                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">후속과정</label>
                            <div class="col-sm-10 relForm2">
                                <c:forEach var="relList" items="${relList}" varStatus="status">
                                    <c:if test="${relList.cnnctCd eq 'EDCTN_REL02'}">
                                        <div class="row" style="margin-bottom: 20px;">
                                            ▣ ${relList.cnnctNm}
                                            <input type="hidden" class="notRequired cloneHidden" name="edctnRel2" value="${relList.cnnctEdctnSeq}">
                                            <button type="button" class="btn btn-sm btn-danger btnDeleteOptn" onclick='$(this).closest(".row").remove();'><em class="ion-android-remove"></em></button>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <button type="button" class="btn btn-sm btn-success couseSearch" data-rel-status="2">과정검색</button>
                        </div>

                        <div class="exmplContainer relForm" style="display:none;">
                            <div class="row" style="margin-bottom: 20px;">
                            <span class="nm">

                            </span>
                            <input type="hidden" class="notRequired cloneHidden" name="" id="" value="">
                            <button type="button" class="btn btn-sm btn-danger btnDeleteOptn" onclick='$(this).closest(".row").remove();'><em class="ion-android-remove"></em></button>
                            </div>
                        </div>



                    </div>
                </div>
            </fieldset>

            <fieldset id="thnlZone">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일 이미지</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                        <spring:eval var="eduThumSize" expression="@environment.getProperty('app.file.eduThumSize')" />
                        <spring:eval var="atchUploadMaxSize" expression="5242880" />

                        <div class="dropzone attachFile notRequired" data-file-field-nm="thnlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${eduThumSize} / 파일 확장자(jpg,jpeg,png) / 최대 용량(5MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>

            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출여부" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출여부" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${fn:replace(strPam, 'copyYn=Y', 'copyYn=N')}">목록</button>
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
            <c:if test="${ not empty rtnDto && copyYn eq 'N' }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                            <tr>
                                <th>최초 작성자</th>
                                <td>${rtnDto.regName}(${rtnDto.regId})</td>
                                <th>최초 작성일</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${ not empty rtnDto.modName }">
                                            ${ rtnDto.modName }(${rtnDto.modId})
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

            <!--VUE 영역 시작 -->
            <c:if test="${ not empty rtnDto && copyYn eq 'N' }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>회차정보</h6>
                <div class="table-responsive col-sm-12 p0 m0" id="vueList" <c:if test="${empty rtnDto}">style="display:none;"</c:if>  >
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center" rowspan="2">번호</th>
                            <th class="text-center" rowspan="2">과정분류</th>
                            <th class="text-center" rowspan="2">과정명</th>
                            <th class="text-center" rowspan="2">학습방식</th>
                            <th class="text-center" rowspan="2">학습시간</th>

                            <th class="text-center" rowspan="2">년도</th>
                            <th class="text-center" rowspan="2">회차</th>
                            <th class="text-center" rowspan="2">접수기간</th>
                            <th class="text-center" rowspan="2">접수상태</th>
                            <th class="text-center" rowspan="2">교육기간</th>
                            <th class="text-center" rowspan="2">실적마감여부</th>
                            <th class="text-center" rowspan="2">교육상태</th>
                            <th class="text-center" colspan="2">강사</th>

                            <th class="text-center" rowspan="2">정원</th>
                            <th class="text-center" rowspan="2">신청자</th>
                            <th class="text-center" rowspan="2">모집 방식</th>
                            <th class="text-center" colspan="3">문의담당자</th>
                            <th class="text-center" rowspan="2">교육장소</th>


                            <th class="text-center" rowspan="2">최초 등록자</th>
                            <th class="text-center" rowspan="2">최초 등록일시</th>
                            <th class="text-center" rowspan="2">최종 수정자</th>
                            <th class="text-center" rowspan="2">최종 수정일시</th>
                            <th class="text-center" rowspan="2">노출여부</th>
                        </tr>
                        <tr>
                            <th class="text-center">이름</th>
                            <th class="text-center">소속</th>

                            <th class="text-center">이름</th>
                            <th class="text-center">이메일</th>
                            <th class="text-center">전화번호</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="listContainer"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainer"/>
                </div>
            </c:if>

            <!--리스트 종료 -->
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/eb/eba/EBACouseSrchLayer.jsp"></jsp:include>