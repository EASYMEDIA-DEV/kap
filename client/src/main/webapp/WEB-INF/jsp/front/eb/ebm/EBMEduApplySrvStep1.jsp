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
                            <span class="for-move">교육 만족도 설문 조사 참여하기</span>
                        </p>
                    </div>
                </div>

                <div class="inner-con-box evaluation"><!-- evaluation: 평가하기 대기 페이지 -->
                    <div class="cont-for-padding">
                        <p class="f-title1">${rtnData.nm}</p>
                        <div class="sort-label-area">
                            <p class="label"><span>${rtnData.prntCdNm}</span></p>
                            <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                        </div>

                        <div class="def-list-w">
                            <div class="def-list">
                                <p class="tit f-head">회차</p>
                                <p class="txt f-sub-head">${rtnData.episdYear}년 ${rtnData.episdOrd}차</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">업종</p>
                                <p class="txt f-sub-head">${rtnData.cbsnCdNm}</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">강사</p>
                                <p class="txt f-sub-head">${rtnData.isttrGroupName}</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">총 문항수</p>
                                <p class="txt f-sub-head">45문항</p>
                            </div>
                            <div class="def-list">
                                <p class="tit f-head">예상 응답시간</p>
                                <p class="txt f-sub-head">약 10분</p>
                            </div>
                        </div>

                        <div class="noti-txt-w">
                            <P class="bullet-noti-txt f-caption2">* 귀사의 무궁한 발전을 기원합니다.</P>
                            <P class="bullet-noti-txt f-caption2">* 재단에서는 귀하의 의견을 수렴하여 만족도 높을 교육/세미나를 진행 할 수 있도록 노력합니다.</P>
                            <P class="bullet-noti-txt f-caption2">* 귀하께서 기재하신 내용은 외부로 유출되거나 타 용도로 사용하지 않을 것을 약속드립니다.</P>
                        </div>
                    </div>

                    <div class="page-bot-btn-sec">
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg" href="javascript:"><span>신청내역 상세</span></a>
                                <a class="btn-solid small black-bg" href="./srvStep2?detailsKey=${rtnData.edctnSeq}&episdYear=${rtnData.episdYear}&episdOrd=${rtnData.episdOrd}"><span>참여하기</span></a>
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