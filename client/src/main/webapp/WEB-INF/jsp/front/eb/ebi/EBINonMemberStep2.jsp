<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div>

    <!-- content 영역 start -->
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
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">교육신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <div class="step-list ongoing">
                            <p class="step-num">2</p>
                            <p class="step-con">신청완료</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="img-area">
                <div class="gray-bg"></div>
                <div class="graphic-item-w">
                    <div class="item"></div>
                    <div class="item"></div>
                </div>
            </div>
        </div>

        <div class="divide-con-area">

            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="status-con-box gray-bg completed">
                                <div class="cont-for-padding">
                                    <div class="sort-label-area"><!-- 2024-01-04 division-line 클래스 삭제 -->
                                        <p class="label"><span>${ rtnData.prntCdNm }</span></p>
                                        <p class="label"><span>${ rtnData.ctgryCdNm }</span></p>
                                    </div>
                                    <p class="f-title1"><span class="color-sky">${ rtnData.nm }</span><br> 교육과정 신청이 완료되었습니다.</p>
                                    <div class="def-list-w">
                                        <div class="def-list">
                                            <p class="tit f-head">신청일시</p>
                                            <p class="txt f-sub-head">${ kl:convertDate(ptcptData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="guide-info-area scroll-motion active">
                                <div class="for-motion">
                                    <div class="divide-box">
                                        <p class="exclamation-txt f-body1">교육 신청 후에는 수정이 불가능하며, 교육 신청 취소 후 다시 접수해야 합니다.</p>
                                        <p class="exclamation-txt f-body1">교육 신청 취소는 교육 목록 > 비회원 신청 내역 조회에서 가능합니다.</p>
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
                            <a class="btn-solid small gray-bg" href="./auth"><span>신청내역 보기</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="./detail?detailsKey=${rtnData.edctnSeq}"><span>확인</span></a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- content 영역 end -->

</div>
