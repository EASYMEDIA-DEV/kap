<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-heading">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
    </div>
    <div class="card-body" data-controller="controller/eb/ebd/EBDSqCertiReqWriteCtrl">
        <form class="form-horizontal" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.examAppctnSeq}" />
            <input type="hidden" class="notRequired" id="gubun" name="gubun" value="${gubun}" />
            <h7 class="text-bold mt0"><em class="ion-android-arrow-dropright mr-sm text-bold"></em>과정정보</h7>
            <hr >
            <fieldset>
                <div class="form-group text-sm ">
                    <div class="col-sm-1 text-right text-bold">
                        과정 분류
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.ctgryCdNm }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        과정명
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.nm }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        학습 방식
                    </div>
                    <div class="col-sm-5">
                        <span>${ rtnDto.stduyMthdCdNm }</span>
                    </div>
                    <div class="col-sm-1 text-right text-bold">
                        학습시간
                    </div>
                    <div class="col-sm-5">
                        <span>${ rtnDto.stduyTimeCdNm }일 / ${rtnDto.stduyDdCdNm}시간 </span>
                    </div>
                </div>
            </fieldset>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>회차정보</h7>
            <hr />
            <fieldset class="">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        회차
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.episdOrd }회차</span>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        접수기간
                    </div>
                    <div class="col-sm-5">
                        <span>${ kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') } ~ ${ kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</span>
                    </div>
                    <div class="col-sm-1 text-right text-bold">
                        교육기간
                    </div>
                    <div class="col-sm-5">
                        <span>${ kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') } ~ ${ kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        강사
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.isttrNm }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        정원
                    </div>
                    <div class="col-sm-5">
                        <span>${ rtnDto.fxnumCnt }명</span>
                    </div>
                    <div class="col-sm-1 text-right text-bold">
                        모집방식
                    </div>
                    <div class="col-sm-5">
                        <span>${ rtnDto.rcrmtMthdCdNm }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset class="">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        문의담당자
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.picNm } / ${ rtnDto.picEmail } / ${ rtnDto.picTelNo }</span>
                    </div>
                </div>
            </fieldset>
            <fieldset class="mb-lg">
                <div class="form-group text-sm">
                    <div class="col-sm-1 text-right text-bold">
                        교육장소
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.placeNm }</span>
                    </div>
                </div>
            </fieldset>
            <!--회원, 회사 수정 시작(회원순번 넘김)-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/COReqMemCmpnWrite.jsp">
                <jsp:param name="memTitle" value="신청자" />
                <jsp:param name="cmpnTitle" value="부품사" />
                <jsp:param name="memSeq" value="${ rtnDto.memSeq }" />
            </jsp:include>
            <!--회원, 회사 수정 종료-->
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>GPC 아이디</h7>
            <hr />
            <fieldset class="mb-lg">
                <div class="form-group text-sm ">
                    <div class="col-sm-1 text-right text-bold ">
                        GPC 아이디
                    </div>
                    <div class="col-sm-11">
                        <span>${ rtnDto.gpcId }</span>
                    </div>
                </div>
            </fieldset>
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>필수과목 수료내역</h7>
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
                                    <c:forEach var="list" items="${rtnPrePrcsList.educationList}" varStatus="status">
                                        <c:if test="${ list.lcnsCnnctCd ne 'LCNS_CNNCT02'}" >
                                            <tr>
                                                <td class="text-center" >${ fn:replace(list.ctgryCdNm, '|', '>') }</td>
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
                                       </c:if>
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
            <h7 class="text-bold"><em class="ion-android-arrow-dropright mr-sm "></em>발급상태</h7>
            <hr />
            <fieldset class="">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold">SQ평가원 구분<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm" name="examCd" ${ kl:decode(rtnDto.issueCd, 'EBD_SQ_I', 'disabled', '') }>
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
                    <label class="col-sm-2 control-label text-bold ">발급상태<span class="star"> *</span></label>
                    <div class="col-sm-2">
                        <select class="form-control input-sm issueCd" name="issueCd" ${ kl:decode(rtnDto.issueCd, 'EBD_SQ_I', 'disabled', '') }>
                            <c:forEach var="cdList" items="${cdDtlList.EBD_SQ}" varStatus="status">
                                <option value="${cdList.cd}" ${ kl:decode(rtnDto.issueCd, cdList.cd, 'selected', '') }>
                                        ${cdList.cdNm}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4 ">※ 발급상태 설정 후 수정이 불가능하오니 주의바랍니다.</div>
                </div>
            </fieldset>
            <fieldset class="last-child rtrnRsnContainer" style="display:none;">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold">반려사유<span class="star"> *</span></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm" name="rtrnRsn" value="${ rtnDto.rtrnRsn }" maxlength="100" title="반려사유" placeholder="반려사유 입력"  ${ kl:decode(rtnDto.issueCd, 'EBD_SQ_R', '', 'readonly') } />
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child jdgmtNoContainer" style="display:${ kl:decode(rtnDto.issueCd, 'EBD_SQ_I', '', 'none') };">
                <div class="form-group text-sm ">
                    <label class="col-sm-2 control-label text-bold">자격증 번호<span class="star"> *</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" name="jdgmtNo" value="${ rtnDto.jdgmtNo }" maxlength="20" title="자격증 번호" placeholder="자격증 번호 입력"  ${ kl:decode(rtnDto.issueCd, 'EBD_SQ_I', 'readonly', '') } />
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default listBtn" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnDto.examAppctnSeq}">
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
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