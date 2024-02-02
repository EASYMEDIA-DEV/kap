<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage evaluation" data-controller="controller/eb/ebm/EBMEduApplyDtlCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />




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

                <div class="status-con-box completed"><!-- complete: 완료 페이지 -->
                    <div class="cont-for-padding">
                        <p class="f-title1"><span class="color-sky">알아야 할 품질 기초</span><br> 온라인 강의 수강이 완료되었습니다</p>
                        <p class="detail-txt">온라인 강의 수강에 참여해 주셔서 감사합니다. 강의 유형에 따라 교육/세미나 만족도 조사 또는 평가가 있을 예정이오니 많은 참여 바랍니다.</p>
                    </div>

                    <div class="page-bot-btn-sec">
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <a class="btn-solid small black-bg" href="/my-page/edu-apply/detail?detailsKey=${rtnData.edctnSeq}&episdYear=${rtnData.episdYear}&episdOrd=${rtnData.episdOrd}&ptcptSeq=${rtnData.ptcptSeq}"><span>신청내역 상세</span></a>
                            </div>
                            <div class="btn-set">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>