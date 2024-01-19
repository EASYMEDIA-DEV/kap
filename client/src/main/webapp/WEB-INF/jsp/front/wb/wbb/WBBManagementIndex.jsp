<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbb/WBBManagementCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <form class="form-horizontal" id="frmData" name="frmData" method="post" action="./step1">
        <input type="hidden" id="episdSeq" name="episdSeq"/>
        <input type="hidden" id="firstIndex" name="firstIndex" value="${ rtnData.firstIndex }" />
        <input type="hidden" id="recordCountPerPage" name="recordCountPerPage" value="${ rtnData.recordCountPerPage }" />
        <input type="hidden" id="ordFlag" name="ordFlag" value="1" />
    </form>
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <c:choose>
                    <c:when test="${rtnData.bsnCd eq 'futureTech'}">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-san-com-future.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-san-com-future-mobile.jpg" alt="">
                    </c:when>
                    <c:otherwise>
                        <img class="only-pc" src="/common/images/img-sub-top-visual-san-com-business.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-san-com-business-mobile.jpg" alt="">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <%-- 상생사업 CMS 영역 Start--%>
                ${rtnCms.cnts}
                <%-- 상생사업 CMS 영역 End--%>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area non-block"><!-- non-block : pc/mobile 둘다 flex -->
                            <p class="f-title3">회차정보</p>
                            <p class="article-total-count f-body2">총 <span id="totalCnt">${rtnData.totalCount}</span>개</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="index-list-w divide">
                                <c:choose>
                                    <c:when test="${not empty rtnData.list}">
                                        <c:forEach var="item" items="${rtnData.list}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${item.dateOrd eq 1}">
                                                    <c:set var="classTag" value="accepting"/>
                                                    <c:set var="applyButton" value="<a class='btn-solid small black-bg apply' href='javascript:' data-episd='${item.episdSeq}'><span>신청하기</span></a>"/>
                                                </c:when>
                                                <c:when test="${item.dateOrd eq 2}">
                                                    <c:set var="classTag" value="waiting"/>
                                                    <c:set var="applyButton" value="<a class='btn-solid small black-bg disabled' href='javascript:'><span>${item.acctStatus}</span></a>"/>
                                                    </c:when>
                                                    <c:when test="${item.dateOrd eq 3}">
                                                        <c:set var="classTag" value="end"/>
                                                        <c:set var="applyButton" value="<a class='btn-solid small black-bg disabled' href='javascript:'><span>${item.acctStatus}</span></a>"/>
                                                    </c:when>
                                                </c:choose>
                                                <div class="list-item <c:if test="${item.dateOrd eq 1}">available accepting</c:if>"><!-- available: 신청 가능한 회차 --><!-- accepting: 접수중 -->
                                                    <p class="available-label">
                                                        <span>신청 가능한 회차</span>
                                                    </p>
                                                    <div class="cont">
                                                        <button class="top-area" type="button">
                                                            <div class="left">
                                                                <div class="group">
                                                                    <p class="index-num f-title3">${item.year} ${item.episd}차</p>
                                                                    <div class="status-info-w">
                                                                        <p class="box-label bigger ${classTag}"><span>${item.acctStatus}</span></p>
                                                                        <c:if test="${item.dateOrd eq 1}">
                                                                            <p class="box-label bigger"><span>D-${item.dday}</span></p>
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </button>
                                                        <div class="cont-area">
                                                            <div class="info-list-w">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">접수기간</p>
                                                                    <p class="txt f-body2">${item.accsStrtDtm} ~ <br class="only-pc"/>${item.accsEndDtm}</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">사업기간</p>
                                                                    <p class="txt f-body2">${kl:convertDate(item.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')} ~ ${kl:convertDate(item.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                                                                </div>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <div class="btn-set">
                                                                        ${applyButton}
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <%--신청 가능한 사업이 없는 경우--%>
                                        <div class="no-data-area has-border">
                                            <div class="txt-box">
                                                <p class="txt f-body1">현재 신청 가능한 사업이 없습니다.</p>
                                            </div>
                                        </div>
                                        <%--신청 가능한 사업이 없는 경우--%>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${rtnData.totalCount > 3}">
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line addMore" href="javascript:"><span>더보기</span>
                                        <c:choose>
                                            <c:when test="${rtnData.totalCount > 3}">
                                                <c:set var="curruntCnt" value="3" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="curruntCnt" value="${rtnData.totalCount % 3}" />
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="item-count">
                                            (${curruntCnt}/${rtnData.totalCount})
                                        </span>
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">Q&A 이용안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-inquiry-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">사업 신청 관련 도움을 드리기 위해 Q&A 게시판을 운영하고 있습니다.<br/>질문을 남겨주시면 빠른 시일 내에 답변드리겠습니다.</p>
                                    </div>
                                    <div class="btn-wrap">
                                        <c:choose>
                                            <c:when test="${rtnData.bsnCd eq 'futureTech'}">
                                                <a class="btn-solid small white-bg" href="/foundation/cs/qa/index?inqFir=INQ07&inqSec=미래차다각화"><span>신청 문의하기</span></a>
                                            </c:when>
                                            <c:when test="${rtnData.bsnCd eq 'support'}">
                                                <a class="btn-solid small white-bg" href="/foundation/cs/qa/index?inqFir=INQ07&inqSec=사업재편지원"><span>신청 문의하기</span></a>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                                <p class="noti-txt f-caption2">* 다만 문의 사항이 많아 답변이 다소 늦어질 수 있으니 양해 바랍니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty rtnRoundDtl}">
                <!-- 미래차다각화 사업 접수 하단 플로팅 영역 -->
                <div class="accepting-fixed-area">
                    <div class="for-position">
                        <button class="open-click-area" type="button">
                            <p class="tit"><span>${rtnRoundDtl.year} ${rtnRoundDtl.episd}차 미래차 다각화 사업 접수중</span></p>
                            <div class="btn-text-icon plus"><span>더보기</span></div>
                        </button>

                        <div class="hide-area">
                            <div class="inner-con">
                                <div class="tit-area">
                                    <p class="f-title1">${rtnRoundDtl.year} ${rtnRoundDtl.episd}차 미래차 다각화 사업 접수중</p>
                                </div>
                                <div class="con-area">
                                    <div class="scroll-area">
                                        <div class="info-line-list-w">
                                            <div class="list">
                                                <p class="tit">접수기간</p>
                                                <p class="txt">${rtnRoundDtl.accsStrtDtm} ~ ${rtnRoundDtl.accsEndDtm}</p>
                                            </div>
                                            <div class="list">
                                                <p class="tit">사업기간</p>
                                                <p class="txt">${kl:convertDate(rtnRoundDtl.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')} - ${kl:convertDate(rtnRoundDtl.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="btn-wrap">
                                    <c:if test="${not empty rtnRoundDtl.optnList}">
                                        <div class="btn-set">
                                            <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                                                <a class="btn-solid small gray-bg has-icon download" href="/file/download?fileSeq=${item.fileSeq}&fileOrd=0" download="" title="양식 다운로드"><span>${item.optnNm} 다운로드</span></a>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                    <div class="btn-set">
                                        <a class="btn-solid small black-bg apply" href="javascript:" data-episd='${rtnRoundDtl.episdSeq}'><span>신청하기</span></a>
                                    </div>
                                </div>
                                <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty rtnRoundDtl.optnList}">
                <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                    <input type="hidden" class="optnFile" value="${item.fileSeq}">
                </c:forEach>
             </c:if>
        </div>
    </div>
</div>
<!-- content 영역 end -->
