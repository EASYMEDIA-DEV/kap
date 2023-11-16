<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-heading">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
    </div>
    <div class="card-body" data-controller="controller/eb/ebg/EBGSqCertiConfrimWriteCtrl">
        <form class="form-horizontal" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.examAppctnSeq}" />
            <input type="hidden" class="notRequired" id="gubun" name="gubun" value="${gubun}" />


            <!--회원, 회사 수정 시작(회원순번 넘김)-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/COReqMemCmpnWrite.jsp">
                <jsp:param name="memTitle" value="회원" />
                <jsp:param name="cmpnTitle" value="부품사" />
                <jsp:param name="memSeq" value="${ rtnDto.memSeq }" />
            </jsp:include>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>선수과목 수료내역</h7>
            <hr />
            <fieldset class="mb-lg">
                <!--VUE 영역 시작 -->
                <div class="col-sm-12 p0 m0 table-responsive" >
                    <table class="table table-hover table-striped text-sm ">
                        <thead>
                        <tr>
                            <th class="text-center">과정분류</th>
                            <th class="text-center">과정명</th>
                            <th class="text-center">학습방식</th>
                            <th class="text-center">학습시간</th>
                            <th class="text-center">년도</th>
                            <th class="text-center">회차</th>
                            <th class="text-center">업종</th>
                            <th class="text-center">교육일자</th>
                            <th class="text-center">수료일자</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody>
                        <c:choose>
                            <c:when test="${ not empty rtnPrePrcsList}">
                                <c:forEach var="list" items="${rtnPrePrcsList}" varStatus="status">
                                    <tr>
                                        <td class="text-center" >${ list.ctgryCdNm }</td>
                                        <td class="text-center" >${ list.nm }</td>
                                        <td class="text-center" >${ list.stduyMthdCdNm }</td>
                                        <td class="text-center" >${ list.stduyDdCdNm }일/${list.stduyTimeCdNm}시간</td>
                                        <td class="text-center" >${ list.episdYear }</td>
                                        <td class="text-center" >${ list.episdOrd }</td>
                                        <td class="text-center" >${ list.cbsnCdNm }</td>
                                        <td class="text-center" >
                                                ${ kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } ~ ${ kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                        </td>
                                        <td class="text-center" >
                                            <c:choose>
                                                <c:when test="${ list.cmptnYn eq 'Y' }">
                                                    ${ kl:convertDate(list.cmptnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                                </c:when>
                                                <c:otherwise>
                                                    -
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr data-total-count="0">
                                    <td colspan="8" class="text-center">
                                        등록된 데이터가 없습니다.
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </fieldset>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>SQ평가원 자격증 취득정보</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="col-sm-12 p0 m0 table-responsive" >
                    <table class="table table-hover table-striped text-sm ">
                        <thead>
                            <tr>
                                <th class="text-center">부품사명</th>
                                <th class="text-center">과정분류</th>
                                <th class="text-center">과정명</th>
                                <th class="text-center">학습방식</th>
                                <th class="text-center">학습시간</th>
                                <th class="text-center">년도</th>
                                <th class="text-center">회차</th>
                                <th class="text-center">업종</th>
                                <th class="text-center">교육일자</th>
                                <th class="text-center">취득일자</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="text-center" >${ rtnDataSqReqData.ctgryCdNm }</td>
                                <td class="text-center" >${ rtnDataSqReqData.ctgryCdNm }</td>
                                <td class="text-center" >${ rtnDataSqReqData.nm }</td>
                                <td class="text-center" >${ rtnDataSqReqData.stduyMthdCdNm }</td>
                                <td class="text-center" >${ rtnDataSqReqData.stduyDdCdNm }일/${rtnDataSqReqData.stduyTimeCdNm}시간</td>
                                <td class="text-center" >${ rtnDataSqReqData.episdYear }</td>
                                <td class="text-center" >${ rtnDataSqReqData.episdOrd }</td>
                                <td class="text-center" >${ rtnDataSqReqData.cbsnCdNm }</td>
                                <td class="text-center" >
                                    ${ kl:convertDate(rtnDataSqReqData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } ~ ${ kl:convertDate(rtnDataSqReqData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                </td>
                                <td class="text-center" >
                                    <c:choose>
                                        <c:when test="${ empty rtnDataSqReqData.acqsnDtm }">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${ kl:convertDate(rtnDataSqReqData.acqsnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>SQ평가원 보수정보(갱신)</h7>
            <hr />
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>SQ평가원 자격증 정보</h7>
            <hr />
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold">SQ평가원 구분</label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm" name="examCd">
                            <c:forEach var="cdList" items="${cdDtlList.EBD_SQ_TP}" varStatus="status">
                                <option value="${cdList.cd}" ${ kl:decode(rtnDto.examCd, cdList.cd, 'selected', '') }>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold ">자격증번호</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" name="jdgmtNo" value="${ rtnDto.jdgmtNo }" maxlength="50" title="자격증번호" placeholder="자격증번호"  />
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm">
                    <label class="col-sm-2 control-label">자격증사진</label>
                    <div class="col-sm-10">
                        <spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <input type="hidden" class="notRequired" id="idntfnPhotoFileSeq" name="idntfnPhotoFileSeq" value="${rtnDto.idntfnPhotoFileSeq}" />
                        <div class="dropzone attachFile" data-file-field-nm="idntfnPhotoFileSeq" data-file-extn="${imageExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="자격증사진">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ OOO X OOO / 파일 확장자(${imageExtns}) / 최대 용량(<fmt:formatNumber value="${5242880 / 1024 / 1024}" maxFractionDigits="1" />MB) / 최대 개수 (1개)
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold ">부품사명<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="hidden" class="notRequired" id="bsnm_no" name="bsnm_no" value="${rtnDto.bsnmNo}" />
                            <input type="text" class="form-control input-sm " value="${ rtnDto.cmpnNm }" title="부품사명" readonly onclick="cmmCtrl.initCalendar(this);"/>
                            <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                    부품사 검색
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold ">업종</label>
                    <div class="col-sm-10">

                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold ">자격증 최초 취득일</label>
                    <div class="col-sm-4">
                        ${ kl:convertDate(rtnDto.acqsnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }
                    </div>
                    <label class="col-sm-2 control-label text-bold ">자격증 유효기간</label>
                    <div class="col-sm-4">
                        ${ kl:convertDate(rtnDto.validStrtDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '') } ~ ${ kl:convertDate(rtnDto.validEndDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '') }
                    </div>
                </div>
            </fieldset>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>수정이력</h7>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default listBtn" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto.examAppctnSeq}">
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
