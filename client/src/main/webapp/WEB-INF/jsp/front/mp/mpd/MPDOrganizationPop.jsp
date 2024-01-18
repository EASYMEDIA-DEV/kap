<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<!-- 위원 상세 팝업 -->
<div class="layer-popup memberDetailsPopup">
    <div class="for-center">
        <div class="pop-wrap">
            <div class="pop-con-area">
                <div class="tit-area">
                    <p class="f-title1">위원 상세</p>
                </div>
                <div class="con-area">
                    <div class="scroll-area">
                        <div class="p-cont-sec">
                            <div class="sec-con-area">
                                <div class="article-list-w txt-list">
                                    <dl class="list-item">
                                        <dt class="f-body1" id="cmssrType"></dt>
                                        <dd class="f-body2" id="name"></dd>
                                    </dl>
                                    <dl class="list-item">
                                        <dt class="f-body1">이메일</dt>
                                        <dd class="f-body2" id="email"></dd>
                                    </dl>
                                    <dl class="list-item">
                                        <dt class="f-body1">업종/분야</dt>
                                        <dd class="f-body2">
                                            <p class="box-label" id="cmssrCbsnCdNm"><span></span></p>
                                        </dd>
                                    </dl>
                                    <dl class="list-item">
                                        <dt class="f-body1">경력</dt>
                                        <dd class="f-body2" id="cmssrMjrCarerCntn"></dd>
                                    </dl>
                                    <dl class="list-item">
                                        <dt class="f-body1">컨설팅 분야</dt>
                                        <dd class="f-body2" id="cmssrCnstgFldCntn"></dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bot-fix-btn-area">
                    <div class="btn-wrap align-right">
                        <a class="btn-solid small black-bg" href="javascript:" id="goQa"><span>위원 문의</span></a>
                    </div>
                </div>
                <div class="user-opt-area">
                    <button class="btn-close btn-role-close btnClose" title="팝업 닫기" type="button"><span>닫기</span></button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="dimd"></div>