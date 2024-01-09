<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <div class="training-confirm">
                <div class="top-info">
                    <div class="training-view-page">
                        <div class="training-list">
                            <div class="img-area">
                                <img src="${list.webPath}" alt="">
                            </div>
                            <div class="txt-area">
                                <div class="top-line">
                                    <div class="sort-label-area">

                                        <p class="label"><span>${list.prntCdNm}</span></p>
                                        <p class="label"><span>${list.ctgryCdNm}</span></p>
                                    </div>
                                    <p class="training-name f-title1">${list.nm}</p>
                                </div>
                                <div class="group">
                                    <p class="index-num f-head">${list.episdOrd}회차</p>
                                    <div class="status-info-w">
                                        <p class="box-label bigger accepting"><span>${list.eduStat}</span></p>
                                    </div>
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
                                    <div class="info-list-w ">
                                        <div class="info-list">
                                            <p class="tit f-caption2">학습방식</p>
                                            <p class="txt f-body2">${list.stduyMthdCdNm}</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">신청일시</p>
                                            <p class="txt f-body2">  ${ empty list.ptcptDtm ? '-' : kl:convertDate(list.ptcptDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">모집방식</p>
                                            <p class="txt f-body2">${list.rcrmtMthdCdNm}</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">선발여부</p>
                                            <p class="txt f-body2">${list.sttsCdNm}</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">학습시간</p>
                                            <p class="txt f-body2">${list.stduyDdCdNm}일/${list.stduyTimeCdNm}시간</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">교육장소</p>
                                            <p class="txt f-body2">

                                                        <c:choose>
                                                            <c:when test="${list.stduyMthdCd eq 'STDUY_MTHD02'}">
                                                                온라인
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${list.placeNm}
                                                            </c:otherwise>
                                                        </c:choose>
                                            </p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">교육기간</p>
                                            <p class="txt f-body2">${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                                ~
                                                    ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }(${list.stduyDdCdNm}일간)</p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">수료여부</p>
                                            <p class="txt f-body2">
                                                <c:choose>
                                                    <c:when test="${list.cmptnYn eq 'Y'}">
                                                        수료
                                                    </c:when>
                                                    <c:otherwise>
                                                        미수료
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>




    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>

        <div class="no-data-area has-border">
            <div class="txt-box">
                <p class="txt f-body1">현재 학습중인 과정이 없습니다.</p>
            </div>
        </div>

    </c:otherwise>
</c:choose>