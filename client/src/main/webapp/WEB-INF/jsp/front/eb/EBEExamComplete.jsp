<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">평가하기</span>
                </p>
            </div>
        </div>

        <div class="status-con-box completed"><!-- complete: 완료 페이지 -->
            <div class="cont-for-padding">
                <p class="f-title1"><span class="color-sky">${ rtnData.nm }</span><br> 평가가 완료되었습니다</p>
                <p class="detail-txt">평가에 참여해 주셔서 감사합니다. 주관식 문항이 있는 경우 담당자가 수기로 확인해야 하므로, 평가점수는 추후 확인 가능합니다.</p>
            </div>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small black-bg" href="/my-page/education/list"><span>신청내역 상세</span></a>
                    </div>
                    <div class="btn-set">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->