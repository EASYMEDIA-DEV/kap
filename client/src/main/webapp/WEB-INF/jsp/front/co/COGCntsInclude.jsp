<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ pageMenuDto.userUrl eq '/education/intro/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-educational-work-intro.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-educational-work-intro-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
    <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/greeting/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-greeting.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-greeting-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
    <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/summary/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-outline.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-outline-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
    <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/history/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-history.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-history-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
    <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/ethic/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-management.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-management-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
    <c:when test="${ pageMenuDto.userUrl eq '/foundation/intro/pr/content' }">
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-promotion.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-promotion-mobile.jpg" alt="">
            </div>
        </div>
    </c:when>
</c:choose>