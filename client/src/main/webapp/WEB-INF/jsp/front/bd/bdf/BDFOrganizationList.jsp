<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/bd/bdf/BDFOrganizationListCtrl">

    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-organization.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-organization-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <div class="right-con-area">
            <div class="cont-sec-w">

                <%--<jsp:include page="/WEB-INF/jsp/front/bd/bdf/BDFOrganizationMemberInclude.jsp" />--%>
                <%--CMS 시작--%>
                ${rtnData.cnts}
                <%--CMS 끝--%>

                <div class="cont-sec no-border scroll-motion"><!-- @ s-margin : margin이 기본보다 작은 값일 때 추가 -->
                    <div class="tab-con-w">
                        <div class="tab-btn-area for-motion">
                            <div class="txt-tab-swiper func-tab">
                                <div class="swiper-container">
                                    <div class="swiper-wrapper">
                                        <a class="swiper-slide txt-tab-btn btnFilter active" href="javascript:" data-cmssr-cbsn-cd="MEM_CD03001">
                                            <p class="txt"><span class="menu-name">전문위원</span></p>
                                        </a>
                                        <a class="swiper-slide txt-tab-btn btnFilter" href="javascript:" data-cmssr-cbsn-cd="MEM_CD03003">
                                            <p class="txt"><span class="menu-name">경영전문위원</span></p>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-con-box for-motion">
                            <div class="tab-con">
                                <div class="tab-section">
                                    <div class="tab-btn-area">
                                        <div class="txt-depth-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper">
                                                    <a class="swiper-slide txt-tab-btn btnFilter active" href="javascript:" data-cmssr-cbsn-cd="MEM_CD03001">
                                                        <p class="txt"><span class="menu-name">전체</span></p>
                                                    </a>
                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                        <c:if test="${ fn:contains(cdList.cd, 'MEM_CD03001') or fn:contains(cdList.cd, 'MEM_CD03002') }">
                                                            <a class="swiper-slide txt-tab-btn btnFilter" href="javascript:" data-cmssr-cbsn-cd="${ cdList.cd }">
                                                                <p class="txt"><span class="menu-name">${ cdList.cdNm }</span></p>
                                                            </a>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="section-tit-area">
                                        <p class="tit f-title2"><span class="menu-name">전문위원</span> &#40;<span class="item-count" id="proCnt">${ rtnProData.totalCount }</span>명&#41;</p>
                                    </div>
                                    <div class="tab-con-area">
                                        <div class="article-list-w txt-card-list" id="proList"><!--  txt-card-list: 텍스트 카드일 경우 + bg가 있을 경우 -->
                                            <c:if test="${ not empty rtnProData.list}">
                                                <c:forEach var="list" items="${rtnProData.list}" varStatus="status">
                                                    <a class="list-item btnDetail" href="javascript:" title="팝업 열기" data-mem-seq="${ list.memSeq }">
                                                        <div class="bg">
                                                            <c:choose>
                                                                <c:when test="${ not empty list.webPath }">
                                                                    <img src="${ list.webPath }" alt="">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="/common/images/@img-foundation-group-member.png" alt="">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="txt-box">
                                                            <div class="names">
                                                                <p class="name f-title3">${ list.name }</p>
                                                                <%--<p class="position f-sub-head">전문위원</p>--%>
                                                            </div>
                                                            <div class="labels">
                                                                <p class="box-label"><span>${ list.cmssrCbsnCdNm }</span></p>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <div class="btn-text-icon black-circle"><span>더 알아보기</span></div>
                                                            </div>
                                                        </div>
                                                    </a>
                                                </c:forEach>
                                            </c:if>
                                            <!-- 2024-01-26 추가 -->
                                            <c:if test="${ empty rtnProData.list}">
                                            <div class="no-data-area">
                                                <div class="txt-box">
                                                    <p class="txt f-body1">등록된 데이터가 없습니다.</p>
                                                </div>
                                            </div>
                                            </c:if>
                                            <!-- // 2024-01-26 추가 -->
                                        </div>
                                        <c:if test="${ rtnProData.totalCount > rtnProData.listRowSize }">
                                            <div class="btn-wrap add-load align-center" id="proPage">
                                                <a class="btn-solid small black-line btnMore" href="javascript:" data-cmssr-type-cd="MEM_CD03001" data-cmssr-cbsn-cd="${ rtnProData.cmssrCbsnCd }" data-page-index="${ rtnProData.pageIndex }"><span>더보기</span><span class="item-count">(${rtnProData.pageIndex * rtnProData.listRowSize >= rtnProData.totalCount ? rtnProData.totalCount : rtnProData.pageIndex * rtnProData.listRowSize}/${ rtnProData.totalCount })</span></a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-con">
                                <div class="tab-section">
                                    <div class="tab-btn-area">
                                        <div class="txt-depth-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper">
                                                    <a class="swiper-slide txt-tab-btn btnFilter" href="javascript:" data-cmssr-cbsn-cd="MEM_CD03003">
                                                        <p class="txt"><span class="menu-name">전체</span></p>
                                                    </a>
                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                        <c:if test="${ fn:contains(cdList.cd, 'MEM_CD03003') }">
                                                            <a class="swiper-slide txt-tab-btn btnFilter" href="javascript:" data-cmssr-cbsn-cd="${ cdList.cd }">
                                                                <p class="txt"><span class="menu-name">${ cdList.cdNm }</span></p>
                                                            </a>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="section-tit-area">
                                        <p class="tit f-title2"><span class="menu-name">경영전문위원</span> &#40;<span class="item-count" id="advCnt">${ rtnAdvData.totalCount }</span>명&#41;</p>
                                    </div>
                                    <div class="tab-con-area">
                                        <div class="article-list-w txt-card-list" id="advList"><!--  txt-card-list: 텍스트 카드일 경우 + bg가 있을 경우 -->
                                            <c:if test="${ not empty rtnAdvData.list}">
                                                <c:forEach var="list" items="${rtnAdvData.list}" varStatus="status">
                                                    <a class="list-item btnDetail" href="javascript:" title="팝업 열기" data-mem-seq="${ list.memSeq }">
                                                        <div class="bg">
                                                            <c:choose>
                                                                <c:when test="${ not empty list.webPath }">
                                                                    <img src="${ list.webPath }" alt="">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="/common/images/@img-foundation-group-member.png" alt="">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="txt-box">
                                                            <div class="names">
                                                                <p class="name f-title3">${ list.name }</p>
                                                                <%--<p class="position f-sub-head">경영전문위원</p>--%>
                                                            </div>
                                                            <div class="labels">
                                                                <p class="box-label"><span>${ list.cmssrCbsnCdNm }</span></p>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <div class="btn-text-icon black-circle"><span>더 알아보기</span></div>
                                                            </div>
                                                        </div>
                                                    </a>
                                                </c:forEach>
                                            </c:if>
                                            <!-- 2024-01-26 추가 -->
                                            <c:if test="${ empty rtnAdvData.list}">
                                                <div class="no-data-area">
                                                    <div class="txt-box">
                                                        <p class="txt f-body1">등록된 데이터가 없습니다.</p>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <!-- // 2024-01-26 추가 -->
                                        </div>
                                        <c:if test="${ rtnAdvData.totalCount > rtnAdvData.listRowSize }">
                                            <div class="btn-wrap add-load align-center" id="advPage">
                                                <a class="btn-solid small black-line btnMore" href="javascript:" data-cmssr-type-cd="MEM_CD03003" data-cmssr-cbsn-cd="${ rtnAdvData.cmssrCbsnCd }" data-page-index="${ rtnAdvData.pageIndex }"><span>더보기</span><span class="item-count">(${rtnAdvData.pageIndex * rtnAdvData.listRowSize >= rtnAdvData.totalCount ? rtnAdvData.totalCount : rtnAdvData.pageIndex * rtnAdvData.listRowSize}/${ rtnAdvData.totalCount })</span></a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <jsp:include page="/WEB-INF/jsp/front/bd/bdf/BDFOrganizationPop.jsp" />

</div>