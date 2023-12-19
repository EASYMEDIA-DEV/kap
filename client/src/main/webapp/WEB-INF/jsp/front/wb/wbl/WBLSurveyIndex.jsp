<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- content 영역 start -->
<div class="cont-wrap">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title">
                <span class="for-move">상생협력</span><br />
                <span class="for-move">체감도 조사</span>
            </p>
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
                        <div class="sec-con-area">
                            <div class="paragraph-sec">
                                <div class="paragraph">
                                    <p class="f-body2">자동차부품산업진흥재단에서는 1차 부품사와 2차 부품사간의 상생협력 강화를 위해 상 생협력5스타 평가의 ‘2차사 상생협력 체감도 조사’를 진행합니다.</p>
                                </div>
                                <div class="paragraph">
                                    <p class="f-body2">재단은 공정하고 신뢰성 있는 체감도 조사를 위해 해당 기업에서 응답한 내용에 대해서는 상생5스타 평가 외에는 절대 사용하지 않고, 철저한 보안을 유지하여 불이익을 당하지 않도록 만전을 기하고 있으니 안심하고 설문에 사실대로 응답하여 주시기 바랍니다.</p>
                                </div>
                                <p class="noti-txt f-caption2">* 본 조사 자료는 통계작성 목적으로만 사용되며, 개인, 법인 또는 단체 등의 정보는 통계법 제33조(비밀의 보호)에 의해 철저하게 보호됨을 알려 드립니다.</p>
                            </div>
                            <div class="inner-con-box gray-bg">
                                <p class="con-sub-tit f-title2">SMS 또는 E-mail로 발송된 인증번호를 입력해주세요.</p>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">인증번호</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="password" placeholder="인증번호 입력">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="page-bot-btn-sec">
                                    <div class="btn-wrap align-center">
                                        <div class="btn-set">
                                            <a class="btn-solid small black-bg" href="javascript:"><span>인증하기</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->