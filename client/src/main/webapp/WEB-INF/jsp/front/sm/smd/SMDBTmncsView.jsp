<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/sm/SMDBTmncsController">
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title">
                        <span class="for-move">이용약관</span>
                    </p>
                </div>
            </div>

            <!-- @ 개인정보처리방침, 이용약관: policy-page 클래스 사용 -->
            <div class="policy-page">
                ${rtnData.cntn}
            </div>
        </div>
    </div>
</div>
