<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebb/EBBEpisdWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnYear" name="edctnYear" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnOrd" name="edctnOrd" value="${rtnDto.episdOrd}" />

            <input type="hidden" class="notRequired" id="prntCd" name="prntCd" value="${rtnDto.prntCd}" />
            <input type="hidden" class="notRequired" id="copyYn" name="copyYn" value="${rtnDto.copyYn}" />

            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="edctnNtctnFileSeq" name="edctnNtctnFileSeq" value="${rtnDto.edctnNtctnFileSeq}" />


            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-11">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>과정정보</h6>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-inverse btn-sm couseSearch">
                            과정검색
                        </button>
                    </div>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                    <div class="col-sm-11">
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
                    <div class="col-sm-11">
                        <p class="form-control-static nm">${rtnDto.nm}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학습방식<span class="star text-danger"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static stduyMthd">${rtnDto.stduyMthdCdNm}</p>
                    </div>

                    <label class="col-sm-1 control-label">학습시간<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static stduyDtm">
                            <c:if test="${rtnDto.stduyDdCdNm ne null}">
                                ${rtnDto.stduyDdCdNm}일/${rtnDto.stduyTimeCdNm} 시간
                            </c:if>

                        </p>

                    </div>
                </div>
            </fieldset>

            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#episdList">회차정보</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#accsList">참여자 목록</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#svResult">만족도 결과</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#bdget">예산/지출</a></li>
            </ul>

            <div class="tab-content">

                <!-- 회차정보 -->
                <div id="episdList" class="tab-pane fade in active">
                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-12">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>회차</h6>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">회차<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <select class="form-control input-sm wd-sm classType" name="episdOrd" id="episdOrd" title="회차">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.ROUND_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdOrd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">접수기간</label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsStrtHour" title="접수 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach varStatus="status" begin="1" end="23">
                                                <option value="${status.count}">${status.count}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_endDt" name="accsEndDtm" value="${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsEndHour" title="접수 종료시간">
                                            <option value="">선택</option>
                                            <c:forEach varStatus="status" begin="1" end="23">
                                                <option value="${status.count}">${status.count}시</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육기간</label>
                            <div class="col-sm-10">
                                <div class="form-inline">
                                    <div class="input-group form-date-group mr-sm">
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시작일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsStrtHour" title="접수 시작시간">
                                            <option value="">선택</option>
                                            <c:forEach varStatus="status" begin="1" end="23">
                                                <option value="${status.count}">${status.count}시</option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-addon bg-white b0">~</span>
                                        <input type="text" class="form-control notRequired input-sm datetimepicker_endDt" name="accsEndDtm" value="${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }" title="종료일시" readonly="readonly"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                <em class="ion-calendar"></em>
                                            </button>
                                        </span>
                                        <select class="form-control input-sm wd-sm classType" name="accsStrtHour" id="accsEndHour" title="접수 종료시간">
                                            <option value="">선택</option>
                                            <c:forEach varStatus="status" begin="1" end="23">
                                                <option value="${status.count}">${status.count}시</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">강사<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm">
                                    강사검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">번호</th>
                                        <th class="text-center">이름</th>
                                        <th class="text-center">소속</th>
                                        <th class="text-center">약력(특이사항)</th>
                                        <th class="text-center">삭제</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer">

                                    <tr data-total-count="0">
                                        <td colspan="5" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">정원<span class="star text-danger"> *</span></label>
                            <div class="col-sm-2">
                                <label class="input_line">
                                    <input type="text" class="form-control input-sm" id="fxnumCnt" name="fxnumCnt" value="${rtnDto.fxnumCnt}" title="정원수" maxlength="50" placeholder="정원수 입력" style="max-width: 150px;"/>
                                    <span>명</span>
                                </label>

                            </div>
                            <div class="col-sm-1">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle notRequired" data-name="fxnumImpsbYn" value="${targetList.cd}" name="targetCd"  <c:if test="${rtnDto.fxnumImpsbYn eq 'N'}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 제한없음
                                </label>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">모집방식<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <c:forEach var="cdList" items="${episdCdList.RCRMT_MTHD}" varStatus="status">
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="rcrmtMthdCd" value="${cdList.cd}" title="모집방식" <c:if test="${rtnDto.rcrmtMthdCd eq cdList.cd}">checked</c:if>/>
                                        <span class="ion-record"></span> ${cdList.cdNm}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">온라인 강의 목차<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <table class="table table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th class="text-center">번호</th>
                                            <th class="text-center">강의명</th>
                                            <th class="text-center">유튜브 URL</th>
                                            <th class="text-center">강의 시간</th>
                                            <th class="text-center">추가/삭제</th>
                                        </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer4">
                                        <tr>
                                            <td class="text-center" rowspan="2">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                                            <td class="text-center">
                                                    <input type="text" class="form-control input-sm" id="nm" name="nm" value="${list.url}" title="강의명" maxlength="50" placeholder="강의명" style="max-width: 300px;"/>
                                            </td><!--강의명-->
                                            <td class="text-center">
                                                    <input type="text" class="form-control input-sm" id="url" name="url" value="${list.url}" title="유튜브 URL" maxlength="50" placeholder="유튜브 URL" style="max-width: 300px;"/>
                                            </td><!--유튜브 URL-->
                                            <td class="text-center">
                                                <input type="text" class="form-control input-sm" id="time" name="time" value="${list.time}" title="강의시간" maxlength="50" placeholder="강의시간" style="max-width: 80px;"/>분
                                            </td><!--강의시간-->
                                            <td class="text-center">
                                                <button type="button" class="btn btn-inverse btn-sm add">추가</button>
                                                <button type="button" class="btn btn-inverse btn-sm remove" data-isttr_seq="${list.lctrSeq}">삭제</button>
                                            </td><!--삭제-->
                                        </tr>
                                       <tr>
                                            <td class="text-center" colspan="4">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="edctnNtctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="썸네일이미지">
                                                    <div class="dz-default dz-message">
                                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                    </div>
                                                </div>
                                                <p class="text-bold mt">
                                                    ※ 파일확장자 jpg, jpeg, png 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                </p>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">문의담당자<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">이름</th>
                                        <th class="text-center">이메일</th>
                                        <th class="text-center">전화번호</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm" id="picNm" name="picNm" value="${rtnDto.picNm}" title="담당자명" maxlength="50" placeholder="담당자명"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm" id="picEmail" name="picEmail" value="${rtnDto.picEmail}" title="담당자이메일" maxlength="50" placeholder="담당자이메일"/>
                                    </td>
                                    <td class="text-center">
                                        <input type="text" class="form-control input-sm" id="picTelNo" name="picTelNo" value="${rtnDto.picTelNo}" title="담당자전화번호" maxlength="50" placeholder="담당자전화번호"/>
                                    </td>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육안내문</label>
                            <div class="col-sm-10 col-md-11">
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="edctnNtctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="썸네일이미지">
                                    <div class="dz-default dz-message">
                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                    </div>
                                </div>
                                <p class="text-bold mt">
                                    ※ 파일확장자 pdf, ppt, pptx, doc, docx, xls, xlsx 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육장소<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm">
                                    교육장 검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">교육장명</th>
                                        <th class="text-center">지역</th>
                                        <th class="text-center">주소</th>
                                        <th class="text-center">대표번호</th>
                                        <th class="text-center"></th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer3">

                                    <tr data-total-count="0">
                                        <td colspan="5" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>



                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">만족도조사<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm">
                                    설문 검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">설문유형</th>
                                        <th class="text-center">제목</th>
                                        <th class="text-center">설문기간</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer3">
                                        <tr>
                                            <td class="text-center">교육 만족도 조사</td>
                                            <td class="text-center">교육만족도조사 품질아카데미_1</td>
                                            <td class="text-center">
                                                <div class="input-group form-date-group mr-sm">
                                                    <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시작일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                        <span class="input-group-addon bg-white b0">~</span>
                                                        <input type="text" class="form-control notRequired input-sm datetimepicker_strtDt" name="accsStrtDt" value="${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }" title="시작일시" readonly="readonly"/>
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>

                                            </td>
                                        </tr>
                                    <%--<tr data-total-count="0">
                                        <td colspan="4" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>--%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>


                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">평가<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                <button type="button" class="btn btn-inverse btn-sm">
                                    평가 검색
                                </button>
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="text-center">제목</th>
                                    </tr>
                                    </thead>
                                    <!-- 리스트 목록 결과 -->
                                    <tbody id="listContainer3">
                                    <tr>
                                        교육 평가_품질아카데미_1
                                    </tr>

                                    <tr data-total-count="0">
                                        <td colspan="1" class="text-center">
                                            검색결과가 없습니다.<br>
                                            (등록된 데이터가 없습니다.)
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>


                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">수료 자동화 여부<span class="star text-danger"> *</span></label>
                            <div class="col-sm-11">
                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmptnAutoYn" value="Y" title="수료 자동화 여부" <c:if test="${rtnDto.cmptnAutoYn eq 'Y'}">checked</c:if>/>
                                        <span class="ion-record"></span> 자동
                                    </label>

                                    <label class="radio-inline c-radio">
                                        <input type="radio" name="cmptnAutoYn" value="N" title="수료 자동화 여부" <c:if test="${rtnDto.cmptnAutoYn eq 'N'}">checked</c:if>/>
                                        <span class="ion-record"></span> 수동
                                    </label>
                            </div>
                        </div>
                    </fieldset>
                </div>

                <!-- 참여자 목록-->
                <div id="accsList" class="tab-pane fade">
                </div>

                <!-- 만족도 결과 -->
                <div id="svResult" class="tab-pane fade">
                </div>

                <!-- 예산/지출 -->
                <div id="bdget" class="tab-pane fade">
                </div>
            </div>

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


<jsp:include page="/WEB-INF/jsp/mngwserc/eb/eba/EBACouseSrchLayer.jsp"></jsp:include>