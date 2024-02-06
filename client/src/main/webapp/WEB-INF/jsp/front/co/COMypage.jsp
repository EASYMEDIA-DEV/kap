<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage" data-controller="controller/co/COMypageCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />




        <!-- content 영역 start -->
        <div class="cont-wrap">

            <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">MY</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="menu-head">
                                    <div class="log-menu">
                                        <p class="user-tit f-title1">
                                            반갑습니다. <span>${loginMap.name}</span> 님
                                            <br/>현재 학습중인 과정이 <span class="userEduCnt">0개</span> 있습니다.
                                        </p>
                                        <div class="pc btn-wrap">
                                            <a class="btn-text-icon black-arrow" href="/my-page/member/intrduction/certification" title="링크 이동"><span>정보수정</span></a>
                                        </div>
                                    </div>
                                    <div class="loginfo-wrap">
                                        <div class="loginfo-box">
                                            <p class="info-tit f-title3">사업 신청내역</p>
                                            <span class="f-caption2">최근 1년 기준</span>
                                            <div class="info-cont">
                                                <ul class="counts">
                                                    <li class="count">
                                                        <span class="f-sub-head">교육사업</span>
                                                        <a class="f-title1" href="/my-page/edu-apply/list">${eduYearCnt}</a>
                                                    </li>
                                                    <li class="count">
                                                        <span class="f-sub-head">컨설팅사업</span>
                                                        <a class="f-title1" href="/my-page/consulting/list">${coeYearCnt}</a>
                                                    </li>
                                                    <li class="count">
                                                        <span class="f-sub-head">상생사업</span>
                                                        <a class="f-title1" href="/my-page/coexistence/list">${consultingYearCnt}</a>
                                                    </li>
                                                </ul>
                                                <div class="pc btn-wrap">
                                                    <div class="btn-set">
                                                        <a class="btn-solid small white-bg" href="/my-page/edu-apply/list?crtfctYn=Y"><span>증명서 발급</span></a>
                                                        <a class="btn-solid small white-bg" href="/foundation/cs/qa/index"><span>1:1 문의</span></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mob btn-wrap">
                                            <div class="btn-set">
                                                <a class="btn-solid small gray-bg" href="javascript:"><span>증명서 발급</span></a>
                                                <a class="btn-solid small gray-bg" href="/foundation/cs/qa/index"><span>1:1 문의</span></a>
                                            </div>
                                        </div>
                                        <p class="last-date f-caption2"><span>최근 로그인 일시</span><span class="date">${ empty loginMap.regDtm ? '-' : kl:convertDate(loginMap.lastLgnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</span></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습중인 과정 (<span class="item-count">0</span>개)</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="trainings-list-w" id="listContainer">
                                        <!-- @ 데이터 있을 경우 노출되는 영역 -->

                                        <!-- @ 데이터 없을 경우 노출되는 영역 -->

                                    </div>

                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">나의 1:1 문의</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <p class="info-txt f-caption2">최근 3개월 기준</p>
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>경영공시</caption>
                                                <colgroup>
                                                    <col style="width: 200rem;">
                                                    <col style="width: 332rem;">
                                                    <col style="width: 200rem;">
                                                    <col style="width: 160rem;">
                                                    <col style="width: 200rem;">
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th>문의유형</th>
                                                    <th>제목</th>
                                                    <th>등록일</th>
                                                    <th>진행상태</th>
                                                    <th>답변등록일</th>
                                                </tr>
                                                </thead>
                                                <!--  -->
                                                <tbody>
                                                <c:set var="codeName" value="" />
                                                <c:set var="statusType" value="" />
                                                <c:forEach var="qaList" items="${myQaData.list}" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${ qaList.rsumeCd eq 'SYN' }">
                                                            <c:set var="codeName" value="접수대기" />
                                                            <c:set var="statusType" value="" />
                                                        </c:when>
                                                        <c:when test="${ qaList.rsumeCd eq 'SYNACK' }">
                                                            <c:set var="codeName" value="접수완료" />
                                                            <c:set var="statusType" value="waiting" />
                                                        </c:when>
                                                        <c:when test="${ qaList.rsumeCd eq 'ACK' }">
                                                            <c:set var="codeName" value="답변완료" />
                                                            <c:set var="statusType" value="complete" />
                                                        </c:when>
                                                    </c:choose>
                                                    <tr>
                                                        <td class="t-align-center">${ qaList.parntCtgryNm } > ${ qaList.ctgryNm }</td>
                                                        <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동" class="qaDtl"  data-details-key="${qaList.qaSeq}" data-mem-seq="${qaList.memSeq}" data-rsume-cd="${qaList.rsumeCd}">${ qaList.titl }</a></p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                        <td class="t-align-center">${ kl:convertDate(qaList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
                                                        <td class="t-align-center"><p class="box-label bigger ${ statusType }"><span>${ codeName }</span></p></td>
                                                        <td class="t-align-center">${ kl:decode(qaList.modDtm, "", "-", kl:convertDate(qaList.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')) }</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-wrap align-right">
                                            <a class="btn-text-icon black-circle" href="/my-page/member/qa/list"><span>나의 1:1문의 바로가기</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>




            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>