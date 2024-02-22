<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="studyStatusClass" value="" />

<c:choose>
    <c:when test="${ not empty rtnData.list }">
        <c:forEach var="list" items="${ rtnData.list }" varStatus="status">
            <c:choose>
                <c:when test="${ fn:contains(list.sttsCd, 'CD02') }">
                    <c:set var="studyStatusClass" value="" />
                </c:when>
                <c:when test="${ fn:contains(list.edctnStatusNm, '교육대기') }">
                    <c:set var="studyStatusClass" value="waiting" />
                </c:when>
                <c:when test="${ fn:contains(list.edctnStatusNm, '교육중') }">
                    <c:set var="studyStatusClass" value="accepting" />
                </c:when>
                <c:when test="${ fn:contains(list.edctnStatusNm, '교육종료') }">
                    <c:set var="studyStatusClass" value="end" />
                </c:when>
            </c:choose>

            <c:set var="edctnStrtDtm" value="${ kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />
            <c:set var="edctnEndDtm" value="${ kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />

            <div class="training-list-w">
                <div class="training-confirm">
                    <div class="top-info">
                        <div class="training-view-page">
                            <div class="training-list">
                                <div class="img-area">
                                    <img src="${ not empty list.webPath ? list.webPath : '/common/images/img-main-training-offline-01.jpg' }" alt="">
                                </div>
                                <div class="txt-area">
                                    <div class="top-line">
                                        <div class="sort-label-area">
                                            <p class="label"><span>${ list.prntCdNm }</span></p>
                                            <p class="label"><span>${ list.ctgryCdNm }</span></p>
                                        </div>
                                        <p class="training-name f-title3"><a href="javascript:" class="listView" data-en-edctn-seq="${list.enEdctnSeq}" data-en-ptcpt-seq="${list.enPtcptSeq}" data-details-key="${list.edctnSeq}" data-ptcpt-seq="${list.ptcptSeq}">${ list.nm }</a></p><!-- 2024-01-19 폰트 클래스 변경 --><!-- 2024-01-26 a태그 추가 -->
                                    </div>
                                    <div class="status-info-w">
                                        <p class="box-label bigger available"><span>비회원 신청 가능</span></p>
                                        <p class="box-label bigger ${ studyStatusClass }"><span>${ fn:contains(list.sttsCd, 'CD02') ? '신청취소' : list.edctnStatusNm }</span></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-info">
                        <div class="index-list-w">
                            <div class="list-item">
                                <div class="cont">
                                    <div class="cont-area">
                                        <div class="info-list-w"><!-- 2023-12-18 w-small 클래스 삭제 -->
                                            <div class="info-list">
                                                <p class="tit f-caption2">학습방식</p>
                                                <p class="txt f-body2">집체교육</p>
                                            </div>
                                            <div class="info-list">
                                                <p class="tit f-caption2">신청일시</p>
                                                <p class="txt f-body2">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</p>
                                            </div>
                                            <div class="info-list">
                                                <p class="tit f-caption2">학습시간</p>
                                                <p class="txt f-body2">${ list.stduyDdCdNm }일/${ list.stduyTimeCdNm }시간</p>
                                            </div>
                                            <div class="info-list">
                                                <p class="tit f-caption2">교육장소</p>
                                                <p class="txt f-body2">${ list.placeNm }</p>
                                            </div>
                                            <div class="info-list">
                                                <p class="tit f-caption2">교육기간</p>
                                                <p class="txt f-body2">${ kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ <br class="only-pc"/>${ kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } (${ kl:getDaysDiff(edctnStrtDtm, edctnEndDtm) +1 }일간)</p><!-- 2023-12-19 only-pc 클래스 추가 -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${rtnData.pageIndex * rtnData.listRowSize <= rtnData.totalCount}">
            <div class="btn-wrap add-load align-center">
                <a class="btn-solid small black-line" href="javascript:" id="btnMore" data-total-count="${rtnData.totalCount}"><span>더보기</span><span class="item-count">(${rtnData.pageIndex * rtnData.listRowSize >= rtnData.totalCount ? rtnData.totalCount : rtnData.pageIndex * rtnData.listRowSize}/${ rtnData.totalCount })</span></a>
            </div>
        </c:if>
    </c:when>
    <c:otherwise>
        <div class="index-list-w">
            <div class="no-data-area has-border">
                <div class="txt-box">
                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>