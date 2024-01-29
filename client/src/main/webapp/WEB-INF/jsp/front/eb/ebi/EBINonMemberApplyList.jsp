<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="studyStatusClass" value="" />

<div class="cont-wrap" data-controller="controller/co/COFormCtrl controller/eb/ebi/EBINonMemberApplyListCtrl">

    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">비회원 신청내역 조회</span></p>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-con-area" id="listContainer">
                            <div class="info-head no-bdr">
                                <p class="article-total-count f-body2">총 <span>${ rtnDto.totalCount }</span>건</p>
                                <form name="frmSearch" method="post" action="" data-del-type="none">
                                    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <input type="hidden" class="notRequired" id="email" name="email" value="${ rtnDto.email }" />
                                    <input type="hidden" class="notRequired" id="name" name="name" value="${ rtnDto.name }" />
                                    <input type="hidden" class="notRequired" id="hpNo" name="hpNo" value="${ rtnDto.hpNo }" />
                                    <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="" />
                                    <input type="hidden" class="notRequired" id="ptcptSeq" name="ptcptSeq" value="" />
                                    <input type="hidden" class="notRequired" id="pageIndex" name="pageIndex" value="${ rtnDto.pageIndex }" />
                                    <div class="sort-select">
                                        <div class="form-select txt-select">
                                            <select id="ordData" name="ordData" title="정렬 바꾸기">
                                                <option value="1" ${ rtnDto.ordData eq '1' ? 'selected' : rtnDto.ordData eq '' ? 'selected' : '' }>접수일자순</option>
                                                <option value="2" ${ rtnDto.ordData eq '2' ? 'selected' : '' }>교육일자순</option>
                                                <option value="3" ${ rtnDto.ordData eq '3' ? 'selected' : '' }>학습시간 짧은순</option>
                                                <option value="4" ${ rtnDto.ordData eq '4' ? 'selected' : '' }>학습시간 긴 순</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>