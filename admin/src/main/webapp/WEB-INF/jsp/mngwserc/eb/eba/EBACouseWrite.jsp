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

            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="thnlFileSeq" name="thnlFileSeq" value="${rtnDto.thnlFileSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류${rtnDto.prntCd}<span class="star"> *</span></label>
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
                    <label class="col-sm-1 control-label">자격증연계<span class="star"> *</span></label>
                    <div class="col-sm-2" style="margin-left: -15px">
                        <div class="col-sm-1">
                            <select class="form-control input-sm wd-sm" name="lcnsCnnctCd" id="lcnsCnnctCd" title="자격증연계">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${studyCdList.LCNS_CNNCT}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.lcnsCnnctCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
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
                        <textarea class="form-control input-sm" id="itrdcCntn" name="itrdcCntn" rows="10" maxlength="200" title="과정소개" placeholder="과정 소개를 입력하세요.">${rtnDto.itrdcCntn}</textarea>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습 목표<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control input-sm" id="stduyTrgtCntn" name="stduyTrgtCntn" rows="10" maxlength="200" title="학습목표" placeholder="학습목표를 입력하세요.">${rtnDto.stduyTrgtCntn}</textarea>

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
                    <div class="col-sm-4 ">
                        <div class="pull-left">
                            <select class="form-control input-sm wd-sm" name="cmptnJdgmtCd" id="cmptnJdgmtCd" title="평가">
                                <option value="">선택</option>
                                <c:forEach var="cdList" items="${studyCdList.CMPTN_JDGMT}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnDto.cmptnJdgmtCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
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
                                <option value="${cdList.cd}" <c:if test="${rtnDto.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}일</option>
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
                            <label class="col-sm-1 control-label">필수과목</label>
                            <div class="col-sm-10 relForm3">
                                <c:forEach var="relList" items="${relList}" varStatus="status">
                                    <c:if test="${relList.cnnctCd eq 'EDCTN_REL03'}">
                                        <div class="row" style="margin-bottom: 20px;">
                                            -${relList.cnnctNm}
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
                                            -${relList.cnnctNm}
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
                                            -${relList.cnnctNm}
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
                            <button type="button" class="btn btn-sm btn-danger btnDeleteOptn"><em class="ion-android-remove"></em></button>
                            </div>
                        </div>



                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">썸네일 이미지</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="5242880" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="thnlFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="썸네일이미지">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
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
            <c:if test="${ not empty rtnDto }">
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

            <!--VUE 영역 시작 -->
            <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                <table class="table table-hover table-striped" >
                    <thead>
                    <tr>
                        <th class="text-center" rowspan="2">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
                                <span class="ion-checkmark-round"></span>
                            </label>
                        </th>
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
            <!--리스트 종료 -->
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/eb/eba/EBACouseSrchLayer.jsp"></jsp:include>