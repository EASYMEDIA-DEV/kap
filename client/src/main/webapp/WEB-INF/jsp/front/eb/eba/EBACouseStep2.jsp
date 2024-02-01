<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" data-controller="controller/eb/eba/EBACouseStep2Ctrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" id="episdYear" name="episdYear" value="${rtnPtcptDto.episdYear}" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="${rtnPtcptDto.episdOrd}" />
        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnPtcptDto.edctnSeq}" />
        <input type="hidden" id="ptcptSeq" name="ptcptSeq" value="${rtnPtcptDto.ptcptSeq}" />

        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <!--
              교육 사업: edu-biz
              컨실팅 사업: consult-biz
              상생 사업: coexisting-biz
            -->
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-notice.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-notice-mobile.jpg" alt="">
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

                <!--LNB 종료-->
                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="status-con-box gray-bg completed">
                                    <div class="cont-for-padding">
                                        <div class="sort-label-area"><!-- 2024-01-04 division-line 클래스 삭제 -->
                                            <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                            <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                                        </div>
                                        <p class="f-title1"><span class="color-sky">${rtnData.nm}</span><br> 교육과정 신청이 완료되었습니다.</p>
                                        <div class="def-list-w">
                                            <div class="def-list">
                                                <p class="tit f-head">신청일시</p>
                                                <p class="txt f-sub-head">${kl:convertDate(rtnPtcptDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="guide-info-area scroll-motion active">
                                    <div class="for-motion">
                                        <div class="divide-box">
                                            <p class="exclamation-txt f-body1">교육 신청 후에는 수정이 불가능하며, 교육 신청 취소 후 다시 접수해야 합니다.</p>
                                            <p class="exclamation-txt f-body1">교육 신청 취소는 마이페이지 > 교육/세미나 신청내역에서 가능합니다.</p>
                                        </div>
                                        <div class="divide-box"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="page-bot-btn-sec">
                        <div class="btn-wrap for-motion">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg applyInfo" href="javascript:"><span>신청내역 보기</span></a>
                            </div>
                            <div class="btn-set">
                                <a class="btn-solid small black-bg" href="/education/apply/detail?detailsKey=${rtnData.edctnSeq}"><span>확인</span></a>
                            </div>
                        </div>
                    </div>
                </div>



            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>




