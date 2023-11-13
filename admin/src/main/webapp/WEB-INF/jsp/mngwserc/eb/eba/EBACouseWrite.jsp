<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/eba/EBACouseWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDto.thnlFileSeq}" />

            <input type="hidden" class="notRequired" id="prntCd" name="prntCd" value="${rtnDto.prntCd}" />



            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm classType" name="cd" id="cd" title="과정분류-대분류">
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
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="nm" name="nm" value="${rtnDto.nm}" title="제목" maxlength="200" placeholder="과정명 입력" />
                    </div>

                    <label class="col-sm-1 control-label">과정요약<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="smmryNm" name="smmryNm" value="${rtnDto.smmryNm}" title="과정요약" maxlength="200" placeholder="과정요약 입력"/>
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
                            <div class="row" style="margin-bottom: 20px;">
                                <c:forEach var="targetList" items="${list.edList}">
                                    <c:choose>
                                        <c:when test="${targetList.dpth eq '2'}">
                                            <label class="col-sm-1 control-label">${targetList.cdNm}<span class="star"> *</span></label>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${targetList.cdNm ne '기타'}">
                                                <label class="checkbox-inline c-checkbox">
                                                    <input type="checkbox" class="checkboxSingle notRequired" data-name="useYnList" value="${targetList.cd}" name="targetCd"  <c:if test="${kl:forEachChk(targetList.cd, rtnTrgtData) eq 'Y'}">checked</c:if> />
                                                    <span class="ion-checkmark-round"></span> ${targetList.cdNm}
                                                </label>
                                            </c:if>
                                            <c:if test="${targetList.cdNm eq '기타' && targetList.cd eq 'ED_TARGET05001'}">
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm notRequired" id="etcNm" name="etcNm" value="" title="기타" maxlength="200" placeholder="기타 입력"/>
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
                    <label class="col-sm-1 control-label">출석</label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm wd-sm" name="cmptnStndCd" id="cmptnStndCd" title="출석">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${studyCdList.CMPTN_STND}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.cmptnStndCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <label class="col-sm-1 control-label">평가</label>
                    <div class="col-sm-1">

                        <select class="form-control input-sm wd-sm" name="cmptnJdgmtCd" id="cmptnJdgmtCd" title="평가">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${studyCdList.CMPTN_JDGMT}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.cmptnJdgmtCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-1">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle jdgmtYn notRequired" value="N" name="jdgmtYn" <c:if test="${rtnDto.jdgmtYn eq 'Y'}">checked</c:if>/>
                        <span class="ion-checkmark-round"></span> 평가없음
                        </label>
                    </div>


                </div>
            </div>

            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">학습 시간<span class="star"> *</span></label>
                <div class="col-sm-11"  style="margin-left: -80px;">
                    <label class="col-sm-1 control-label">학습일</label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm wd-sm" name="stduyDdCd" id="stduyDdCd" title="학습일">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${studyCdList.STDUY_DD}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.stduyDdCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <label class="col-sm-1 control-label">학습시간</label>
                    <div class="col-sm-1">
                        <select class="form-control input-sm wd-sm" name="stduyTimeCd" id="stduyTimeCd" title="학습시간">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${studyCdList.STDUY_TIME}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 준비물<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="stduySuplsNm" name="stduySuplsNm" value="${rtnDto.stduySuplsNm}" title="학습 준비물" maxlength="200" placeholder="학습 준비물 입력" />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">협업기관</label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm notRequired" id="cprtnInsttNm" name="cprtnInsttNm" value="${rtnDto.cprtnInsttNm}" title="협업기관" maxlength="200" placeholder="협업기관 입력" />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea maxlength="30" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="학습내용을 입력하세요" id="stduyCntn" name="stduyCntn" title="학습내용" oninput="cmmCtrl.checkMaxlength(this);">
                            ${rtnDto.stduyCntn}
                        </textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연계학습</label>
                    <div class="col-sm-11">

                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">선수과목</label>
                            <div class="col-sm-10">
                                <div class="row" style="margin-bottom: 20px;">-자동차 품질경영시스템</div>
                                <div class="row" style="margin-bottom: 20px;">-자동차 품질경영시스템</div>
                            </div>
                            <button type="button" class="btn btn-sm btn-success">과정검색</button>
                        </div>

                        <div class="row" style="margin-bottom: 20px;">
                            <label class="col-sm-1 control-label">후속과목</label>
                            <div class="col-sm-10">
                                <div class="row" style="margin-bottom: 20px;">
                                    - [SQ인증] 업종별 기술 이해 <span class="star">[X]</span>
                                    <input type="hidden" class="notRequired" name="EDCTN_REL" id="" value="">
                                    <%--EDCTN_REL--%>
                                </div>
                                <div class="row" style="margin-bottom: 20px;">- 업종별 SQ 평가 대응 실무 [X]</div>
                            </div>
                            <button type="button" class="btn btn-sm btn-success">과정검색</button>
                        </div>


                    </div>
                </div>
            </fieldset>



            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일 이미지</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="PC 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
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