<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>
        <div class="training-swiper-area view01">
            <div class="swiper-container training-swiper">
                <div class="swiper-wrapper marquee_wrapper1" >
                    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                            <!--
                              접수중: accepting
                              접수대기: waiting
                              접수마감: end
                            -->
                            <div class="img-area">
                                <img src="${list.webPath}" alt="">
                            </div>
                            <div class="txt-area">
                                <!--비회원 구분자를 사용하여 영역 표시-->
                                <c:if test="${1 ne 1}">
                                    <div class="top-label-area">
                                        <p class="box-label bigger available"><span>비회원 신청 가능</span></p>
                                    </div>
                                </c:if>
                                <div class="sort-label-area">
                                    <p class="label"><span>${list.prntCdNm}</span></p>
                                    <p class="label"><span>${list.ctgryCdNm}</span></p>
                                </div>
                                <p class="training-name">${list.nm}</p>
                                <div class="date-info-w">
                                    <div class="list">
                                        <p class="txt">교육기간</p>
                                        <p class="date">
                                            <c:choose>
                                                <c:when test="${empty list.edctnStrtDtm}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                    ~
                                                    ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                </c:otherwise>
                                            </c:choose>
                                            (${list.stduyDdCdNm}일간)
                                        </p>
                                    </div>
                                    <div class="list">
                                        <p class="txt">${list.stduyMthdCdNm}</p>
                                        <p class="date"><span class="item">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</span>
                                            <span class="item">
                                    <c:if test="${list.fxnumImpsbYn eq 'Y'}">
                                        정원${list.fxnumCnt}명(${list.rcrmtMthdCdNm})
                                    </c:if>
                                    <c:if test="${list.fxnumImpsbYn eq 'N'}">
                                        정원제한 없음
                                    </c:if>
                                </span>
                                        </p>
                                    </div>
                                </div>
                                <div class="status-info-w">
                                    <c:choose>
                                        <c:when test="${empty list.edctnStrtDtm}">
                                            <c:if test="${list.stduyMthdCd ne 'STDUY_MTHD02'}"><p class="box-label bigger"><span>-</span></p></c:if>
                                            <c:if test="${list.stduyMthdCd eq 'STDUY_MTHD02'}"><p class="box-label bigger"><span>온라인</span></p></c:if>
                                            <p class="box-label bigger waiting"><span>-</span></p>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${list.stduyMthdCd ne 'STDUY_MTHD02'}"><p class="box-label bigger"><span>${list.placeNm}</span></p></c:if>
                                            <c:if test="${list.stduyMthdCd eq 'STDUY_MTHD02'}"><p class="box-label bigger"><span>온라인</span></p></c:if>
                                            <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <!-- hover 시 노출되는 영역 -->
                            <div class="hover-area">
                                <div class="for-position">
                                    <div class="sort-label-area">
                                        <p class="label"><span>${list.prntCdNm}</span></p>
                                        <p class="label"><span>${list.ctgryCdNm}</span></p>
                                    </div>
                                    <p class="training-name">${list.nm}</p>
                                    <div class="date-info-w">
                                        <div class="list">
                                            <div class="tit">접수기간</div>
                                            <div class="txt">
                                                <c:choose>
                                                    <c:when test="${empty list.edctnStrtDtm}">
                                                        -
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${ empty list.accsStrtDtm ? '-' : kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                        ~
                                                        ${ empty list.accsEndDtm ? '-' : kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="list">
                                            <div class="tit">교육기간</div>
                                            <div class="txt">
                                                <c:choose>
                                                    <c:when test="${empty list.edctnStrtDtm}">
                                                        -
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                        ~
                                                        ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
                                                    </c:otherwise>
                                                </c:choose>
                                                (${list.stduyDdCdNm}일간)
                                            </div>
                                        </div>
                                        <div class="list ">
                                            <div class="tit">${list.stduyMthdCdNm}</div>
                                            <div class="txt status-txt">${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)</div>
                                        </div>
                                    </div>
                                    <div class="status-info-w">
                                        <c:choose>
                                            <c:when test="${empty list.edctnStrtDtm}">
                                                <c:if test="${list.stduyMthdCd ne 'STDUY_MTHD02'}"><p class="box-label bigger"><span>-</span></p></c:if>
                                                <c:if test="${list.stduyMthdCd eq 'STDUY_MTHD02'}"><p class="box-label bigger"><span>온라인</span></p></c:if>
                                                <p class="box-label bigger waiting"><span>-</span></p>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${list.stduyMthdCd ne 'STDUY_MTHD02'}"><p class="box-label bigger"><span>${list.placeNm}</span></p></c:if>
                                                <c:if test="${list.stduyMthdCd eq 'STDUY_MTHD02'}"><p class="box-label bigger"><span>온라인</span></p></c:if>
                                                <p class="box-label bigger waiting"><span>${list.accsStatusNm}</span></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="btn-wrap">
                                        <c:if test="${list.accsStatusOrder eq 3}">
                                            <div class="btn-solid small black-bg" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                        </c:if>
                                        <c:if test="${list.accsStatusOrder ne 3}">
                                            <div class="btn-solid small black-bg episdDtl" data-edctnSeq="${list.edctnSeq}"><span>더 알아보기</span></div>
                                        </c:if>

                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>


    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>
        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
            <div class="txt-box">
                <p class="txt f-body1">등록된 회차가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>