<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage evaluation" data-controller="controller/eb/ebm/EBMEduApplyOnlineStep2Ctrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" id="detailsKey" name="detailsKey" value="${ rtnData.edctnSeq }" />
        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${ rtnData.edctnSeq }" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="${ rtnData.episdOrd }" />
        <input type="hidden" id="episdYear" name="episdYear" value="${ rtnData.episdYear }" />
        <input type="hidden" id="episdSeq" name="episdSeq" value="${ rtnData.episdSeq }" />
        <input type="hidden" id="ptcptSeq" name="ptcptSeq" value="${ rtnData.ptcptSeq }" />

        <input type="hidden" id="nowLctrSeq" name="nowLctrSeq" value="${nowLctrSeq}" />


        <!--현재 lctrSeq를 기반으로 이전 이후것을 가져온다 -->



        <c:set var="lctrList" value="${lctrList.list}"/>

        <c:set var="previousIndexSeq" value=""/>
        <c:set var="previousItem" value=""/>
        <c:set var="nextIndexSeq" value=""/>
        <c:set var="nextItem" value=""/>


        <c:forEach var="list" items="${lctrList}" varStatus="loopStatus">

            <c:set var="currentIndexSeq" value="${loopStatus.index}" />

            <!-- 앞의 항목 -->
            <c:if test="${loopStatus.index > 0}">
                <c:set var="previousIndexSeq" value="${loopStatus.index - 1}" />

                <c:if test="${nowLctrSeq eq list.lctrSeq}">
                    <c:set var="previousItem" value="${lctrList[previousIndexSeq]}" />
                </c:if>
                <!-- 이제 previousIndexSeq, previousItem을 사용할 수 있습니다 -->
            </c:if>

            <!-- 현재 항목 -->
            <!-- currentIndexSeq, item을 사용할 수 있습니다 -->

            <!-- 뒤의 항목 -->
            <c:if test="${loopStatus.index < fn:length(lctrList) - 1}">
               <c:set var="nextIndexSeq" value="${loopStatus.index + 1}" />
               <c:if test="${nowLctrSeq eq list.lctrSeq}">
                    <c:set var="nextItem" value="${lctrList[nextIndexSeq]}" />
                </c:if>
                <!-- 이제 nextIndexSeq, nextItem을 사용할 수 있습니다 -->
            </c:if>
        </c:forEach>


        <!-- content 영역 start -->
        <div class="cont-wrap">
            <div class="inner">
                <div class="sub-top-vis-area">
                    <div class="page-tit-area t-align-center">
                        <p class="page-tit f-large-title">
                            <span class="for-move">온라인 교육</span>
                        </p>
                    </div>
                </div>

                <div class="inner-con-box">
                    <div class="evaluation-category">
                        <div class="left">
                            <p class="subject-tit f-title3">${rtnData.nm}</p>
                            <div class="sort-label-area">
                                <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                            </div>
                        </div>
                        <div class="right">
                            <div class="status-info-w">
                                <p class="box-label bigger"><span>${rtnData.episdYear}년 ${rtnData.episdOrd}차</span></p>
                                <p class="box-label bigger"><span>${not empty rtnData.cbsnCdNm ? rtnData.cbsnCdNm : '-'}</span></p>
                                <p class="box-label bigger"><span>${rtnData.isttrGroupName}</span></p>
                                <p class="box-label bigger"><span>
                                    ${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                    ~
                                    ${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                </span></p>
                            </div>
                        </div>
                    </div>


                    <div id="viewContainer">

                    </div>


                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>