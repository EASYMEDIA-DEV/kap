<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/co/COFormCtrl controller/mp/mpg/MPGMyQaListCtrl">

    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">자동차부품산업진흥재단에 궁금한점을 문의해보세요.</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="guide-step-w has-border"><!-- has-border: 테두리 있을 때 -->
                                    <div class="step-list">
                                        <p class="step-num">STEP 01</p>
                                        <div class="step-info">
                                            <p class="step-con">문의등록</p>
                                            <p class="box-label bigger"><span>접수대기</span></p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 02</p>
                                        <div class="step-info">
                                            <p class="step-con">담당자 확인</p>
                                            <p class="box-label bigger waiting"><span>접수완료</span></p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 03</p>
                                        <div class="step-info">
                                            <p class="step-con">담당자 답변 등록</p>
                                            <p class="box-label bigger complete"><span>답변완료</span></p>
                                        </div>
                                    </div>
                                </div>

                                <div class="divide-flex-box v-align-center">
                                    <div class="left">
                                        <p class="icon-txt tel">
                                            <span>대표전화</span>
                                            <a href="tel:02-3271-2965">02-3271-2965</a>
                                        </p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small black-bg" href="/foundation/cs/qa/index"><span> 1:1 문의하기</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-con-area">
                            <div class="info-head">
                                <p class="article-total-count f-body2">총 <span>${rtnData.totalCount}</span>건</p>
                                <form class="form-horizontal" name="frmSearch" method="get" action="">
                                    <!-- 현재 페이징 번호 -->
                                    <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
                                    <!-- 페이징 버튼 사이즈 -->
<%--                                    <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />--%>
                                    <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
                                    <!-- CSRF KEY -->
                                    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <!-- 상세로 이동시 시퀀스 -->
                                    <input type="hidden" id="detailsKey" name="detailsKey" value="" />
                                    <input type="hidden" id="memSeq" name="memSeq" value="" />
                                    <input type="hidden" id="rsumeCd" name="rsumeCd" value="" />

                                    <div class="form-input srch-input">
                                        <input type="text" name="searchText" id="searchText" placeholder="검색어를 입력해 주세요." value="${rtnData.searchText}">
                                        <div class="input-btn-wrap">
                                            <button class="delete-btn" title="지우기" type="button"></button>
                                            <button class="srch-btn" id="btnSearch" title="검색" type="button"></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <c:choose>
                                <c:when test="${ not empty rtnData.list }">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>나의 1:1 문의 목록</caption>
                                                <colgroup>
                                                    <col style="width: 80rem;">
                                                    <col style="width: 200rem;">
                                                    <col style="width: 380rem;">
                                                    <col style="width: 144rem;">
                                                    <col style="width: 144rem;">
                                                    <col style="width: 144rem;">
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th>번호</th>
                                                    <th>문의유형</th>
                                                    <th>제목</th>
                                                    <th>등록일</th>
                                                    <th>진행상태</th>
                                                    <th>답변등록일</th>
                                                </tr>
                                                </thead>
                                                <tbody id="dataList">
                                                <c:set var="codeName" value="" />
                                                <c:set var="statusType" value="" />
                                                <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${ list.rsumeCd eq 'SYN' }">
                                                            <c:set var="codeName" value="접수대기" />
                                                            <c:set var="statusType" value="" />
                                                        </c:when>
                                                        <c:when test="${ list.rsumeCd eq 'SYNACK' }">
                                                            <c:set var="codeName" value="접수완료" />
                                                            <c:set var="statusType" value="waiting" />
                                                        </c:when>
                                                        <c:when test="${ list.rsumeCd eq 'ACK' }">
                                                            <c:set var="codeName" value="답변완료" />
                                                            <c:set var="statusType" value="complete" />
                                                        </c:when>
                                                    </c:choose>
                                                    <tr>
                                                        <td class="t-align-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                                                        <td class="t-align-center">${ list.parntCtgryNm } > ${ list.ctgryNm }</td>
                                                        <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동" class="listView"  data-details-key="${list.qaSeq}" data-mem-seq="${list.memSeq}" data-rsume-cd="${list.rsumeCd}">${ list.titl }</a></p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                        <td class="t-align-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                                                        <td class="t-align-center"><p class="box-label bigger ${ statusType }"><span>${ codeName }</span></p></td>
                                                        <td class="t-align-center">${ kl:decode(list.modDtm, "", "-", kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <c:if test="${ rtnData.pageIndex * rtnData.listRowSize < rtnData.totalCount }">
                                    <div class="btn-wrap add-load align-center" id="divMore">
                                        <a class="btn-solid small black-line" href="javascript:" id="btnMore" data-page-index="${rtnData.pageIndex + 1}"><span>더보기</span><span class="item-count" id="pagingVal">(${rtnData.pageIndex * rtnData.listRowSize > rtnData.totalCount ? rtnData.totalCount : rtnData.pageIndex * rtnData.listRowSize}/${rtnData.totalCount})</span></a>
                                    </div>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <div class="table-sec">
                                        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
                                            <div class="txt-box">
                                                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>