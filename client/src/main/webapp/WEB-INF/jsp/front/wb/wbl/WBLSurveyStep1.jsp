<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- content 영역 start -->
<div class="cont-wrap">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">상생협력 체감도 조사</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box evaluation" data-controller="controller/wb/wbl/WBLSurveyStep1Ctrl"><!-- evaluation: 평가하기 대기 페이지 -->
            <div class="cont-for-padding">
                <p class="f-title1">상생협력 체감도 조사</p>

                <div class="def-list-w">
                    <div class="def-list">
                        <p class="tit f-head">회차</p>
                        <p class="txt f-sub-head">${fn:substring(rtnData.list[0].year,0,4)}년 ${rtnData.list[0].episd}차</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">총 문항수</p>
                        <p class="txt f-sub-head">${rtnData.list[0].qstnCnt}문항</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">예상 응답시간</p>
                        <p class="txt f-sub-head">약 ${rtnData.list[0].rspnMm}분</p>
                    </div>
                </div>

                <div class="noti-txt-w">
                    ${rtnData.list[0].cntn}
<%--                    <P class="bullet-noti-txt f-caption2">* 귀사의 무궁한 발전을 기원합니다.</P>--%>
<%--                    <P class="bullet-noti-txt f-caption2">* 재단에서는 귀하의 의견을 수렴하여 만족도 높은 상생협력을 진행 할 수 있도록 노력합니다.</P>--%>
<%--                    <P class="bullet-noti-txt f-caption2">* 귀하께서 기재하신 내용은 외부로 유출되거나 타 용도로 사용하지 않을 것을 약속드립니다.</P>--%>
                </div>
            </div>
            <div class="cont-line-sec">
                <div class="sec-tit-area">
                    <p class="f-title3">평가대상</p>
                </div>
                <div class="sec-con-area">
                    <ul class="article-list-w index-list">
                        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                            <li class="list-item">
                                <div class="txt-area">
                                    <p class="num f-sub-head">${status.count}</p>
                                    <div class="training">
                                        <div class="dl">
                                            <div class="dt w-longer f-body2">대상업체</div>
                                            <div class="dd f-body2">${list.partCmpnNm1} (${list.partCmpnCd1})</div> <%-- 2024-07-09 추가개발 ppt 12 수정 --%>
                                        </div>
                                        <div class="dl">
                                            <div class="dt w-longer f-body2">수행 부품사</div>
                                            <div class="dd f-body2">${list.partCmpnNm2} (${list.partCmpnCd2})</div> <%-- 2024-07-09 추가개발 ppt 12 수정 --%>
                                        </div>
                                    </div>
                                </div>
                                <div class="btn-area">
                                    <div class="btn-wrap">
                                        <c:choose>
                                            <c:when test="${list.ptcptCd eq 'E'}">
                                                <button class="btn-text-icon x-circle noSurvey" data-cxstn-srv-seq="${list.cxstnSrvSeq}"><span>미참여</span></button>
                                                <a class="btn-text-icon black-circle goSurvey" href="javascript:" data-cxstn-srv-seq="${list.cxstnSrvSeq}"><span>참여하기</span></a>
                                            </c:when>
                                            <c:when test="${list.ptcptCd eq 'N'}">
                                                <div class="status-circle no"><!-- @ 기본: 미참여, on 클래스: 참여, no:미참여완료 -->
                                                    <p class="txt f-body1">미참여완료</p>
                                                </div>
                                            </c:when>
                                            <c:when test="${list.ptcptCd eq 'Y'}">
                                                <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                                    <p class="txt f-body1">참여완료</p>
                                                </div>
                                            </c:when>
                                        </c:choose>

                                    </div>
                                </div>
                            </li>
                        </c:forEach>
<%--
                        <li class="list-item">
                            <div class="txt-area">
                                <p class="num f-sub-head">2</p>
                                <div class="training">
                                    <div class="dl">
                                        <div class="dt w-longer f-body2">대상업체</div>
                                        <div class="dd f-body2">KOMOS</div>
                                    </div>
                                    <div class="dl">
                                        <div class="dt w-longer f-body2">수행 부품사</div>
                                        <div class="dd f-body2">동일전공</div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-area">
                                <div class="btn-wrap">
                                    <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                        <p class="txt f-body1">참여완료</p>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-item">
                            <div class="txt-area">
                                <p class="num f-sub-head">2</p>
                                <div class="training">
                                    <div class="dl">
                                        <div class="dt w-longer f-body2">대상업체</div>
                                        <div class="dd f-body2">KOMOS</div>
                                    </div>
                                    <div class="dl">
                                        <div class="dt w-longer f-body2">수행 부품사</div>
                                        <div class="dd f-body2">동일전공</div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-area">
                                <div class="btn-wrap">
                                    <div class="status-circle no"><!-- @ 기본: 미참여, on 클래스: 참여, no:미참여완료 -->
                                        <p class="txt f-body1">미참여완료</p>
                                    </div>
                                </div>
                            </div>
                        </li>--%>
                    </ul>

<%--
                    <div class="btn-wrap add-load align-center">
                        <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                    </div>
--%>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->