<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebb/EBBPtcptWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
            <input type="hidden" class="notRequired" id="episdOrd" name="episdOrd" value="${rtnDto.episdOrd}" />
            <input type="hidden" class="notRequired" id="episdYear" name="episdYear" value="${rtnDto.episdYear}" />
            <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="stduyMthdCd" name="stduyMthdCd" value="${ rtnDto.stduyMthdCd}" />
            <input type="hidden" id="rcrmtMthdCd" name="rcrmtMthdCd" value="${episdDto.rcrmtMthdCd}" />


            <!-- 과정정보 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-12">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>과정정보</h6>
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

            <!-- 회차정보 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-12">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>회차정보</h6>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.episdOrd}회차</p>
                    </div>
                    <label class="col-sm-1 control-label">업종<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.cbsnCdNm}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">

                        <p class="form-control-static">${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                    </div>

                    <label class="col-sm-1 control-label">교육기간<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <input type="hidden" name="edctnStrtDtm" id="edctnStrtDtm" value="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                        <input type="hidden" name="edctnEndDtm" id="edctnEndDtm" value="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                        <p class="form-control-static">${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">강사<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static">
                            <c:set var="isttrSize" value="${fn:length(isttrList)}" />
                            <c:forEach var="isttrList" items="${isttrList}" varStatus="status">
                                ${isttrList.name} <c:if test="${isttrSize > 1 && status.count < isttrSize}">, </c:if>

                            </c:forEach>
                        </p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">정원<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.fxnumCnt}명</p>
                    </div>
                    <label class="col-sm-1 control-label">모집방식<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.rcrmtMthdCdNm}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">문의담당자<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static">${rtnDto.picNm} / ${rtnDto.picEmail} / ${rtnDto.picTelNo}</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static">${roomDto.nm}</p>
                    </div>
                </div>
            </fieldset>


            <!-- 회원정보 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-11">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>회원정보</h6>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-inverse btn-sm memberSearch">
                            회원 검색
                        </button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">성명<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static memName">-</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static memEmail">-</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static deptDtlNm">-</p>
                    </div>
                    <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <p class="form-control-static pstnCdNm">-</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">핸드폰번호<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static hpNo">-</p>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                    <div class="col-sm-11" style="margin-left: -15px">
                        <p class="form-control-static telNo">-</p>
                    </div>
                </div>
            </fieldset>

            <!-- GPC 아이디 -->
            <fieldset>
                <div class="form-group text-sm">
                    <div class="col-sm-12">
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>GPC 아이디</h6>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">GPC 아이디<span class="star"> *</span></label>
                    <div class="col-sm-11 form-inline" style="margin-left: -15px">
                        <input type="text" class="form-control input-sm notRequired" id="gpcId" name="gpcId" value="" title="GPC 아이디" maxlength="50" placeholder="GPC 아이디" style="max-width: 150px;"/>


                        <button type="button" class="btn btn-inverse btn-sm gpcIdSearch">
                            인증
                        </button>
                        <span id="gpcIdText"></span>

                        <input type="hidden" class="form-control input-sm notRequired" id="memSeq" name="memSeq" value="" title="회원번호"/>
                        <input type="hidden" class="form-control input-sm notRequired" id="ptcptBsnmNo" name="ptcptBsnmNo" value="" title="참여사업자번호"/>
                    </div>

                </div>
            </fieldset>

            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="javascript:history.back();">취소</button>
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

        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include><!--교육장검색-->

