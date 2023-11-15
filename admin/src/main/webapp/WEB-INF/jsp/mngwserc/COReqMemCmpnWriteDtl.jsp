<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div data-controller="controller/EXGExamWriteCtrl">
    <h7 class="mt0 text-bold"><em class="ion-android-arrow-dropright mr-sm"></em>신청자 정보</h7>
    <hr >
    <fieldset>
        <div class="form-group text-sm ">
            <div class="col-sm-1 text-right text-bold">
                신청자(아이디)
            </div>
            <div class="col-sm-5">
                <span>${ rtnDto.ctgryCdNm }</span>
            </div>
            <div class="col-sm-1 text-right text-bold">
                이메일<span class="star"> *</span>
            </div>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm" name="qstnNm" value="" maxlength="50" title="이메일" placeholder=""  />
            </div>
        </div>
    </fieldset>
    <fieldset>
        <div class="form-group text-sm">
            <div class="col-sm-1 text-right text-bold">
                부서<span class="star"> *</span>
            </div>
            <div class="col-sm-5">

            </div>
            <div class="col-sm-1 text-right text-bold">
                직급<span class="star"> *</span>
            </div>
            <div class="col-sm-5">
                <input type="text" class="form-control input-sm" name="qstnNm" value="" maxlength="50" title="이메일" placeholder=""  />
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
    <h7><em class="ion-android-arrow-dropright mr-sm"></em>회차정보</h7>
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
    <h7><em class="ion-android-arrow-dropright mr-sm"></em>GPC 아이디</h7>
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
    <h7><em class="ion-android-arrow-dropright mr-sm"></em>선수과목 수료내역</h7>
    <hr />
    <fieldset class="mb-lg">

    </fieldset>
    <h7><em class="ion-android-arrow-dropright mr-sm"></em>발급상태</h7>
    <hr />
    <fieldset class="">
        <div class="form-group text-sm ">
            <div class="col-sm-2 text-right text-bold ">
                SQ평가원 구분<span class="star"> *</span>
            </div>
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
            <div class="col-sm-2 text-right text-bold ">
                발급상태<span class="star"> *</span>
            </div>
            <div class="col-sm-2">
                <select class="form-control input-sm" name="issueCd">
                    <c:forEach var="cdList" items="${cdDtlList.EBD_SQ}" varStatus="status">
                        <option value="${cdList.cd}" ${ kl:decode(rtnDto.issueCd, cdList.cd, 'selected', '') }>
                                ${cdList.cdNm}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </fieldset>
    <fieldset class="">
        <div class="form-group text-sm ">
            <div class="col-sm-2 text-right text-bold ">
                반려사유<span class="star"> *</span>
            </div>
            <div class="col-sm-10">
                <input type="text" class="form-control input-sm" name="qstnNm" value="${ rtnDto.rtrnRsn }" maxlength="100" title="반려사유" placeholder="반려사유 입력"  />
            </div>
        </div>
    </fieldset>
</div>