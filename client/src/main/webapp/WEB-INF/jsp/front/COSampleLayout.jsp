<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
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
            <p class="page-tit f-xlarge-title"><span class="for-move">공지사항</span></p>
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
            DATA
        </div>
    </div>
</div>
<!-- content 영역 end -->