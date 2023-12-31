<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" data-controller="controller/co/COMypageCtrl">
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
                                            반갑습니다. <span>홍길동</span> 님
                                            <br/>현재 학습중인 과정이 <span>1개</span> 있습니다.
                                        </p>
                                        <div class="pc btn-wrap">
                                            <a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>정보수정</span></a>
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
                                                        <a class="f-title1" href="javascript:">5</a>
                                                    </li>
                                                    <li class="count">
                                                        <span class="f-sub-head">컨설팅사업</span>
                                                        <a class="f-title1" href="javascript:">21</a>
                                                    </li>
                                                    <li class="count">
                                                        <span class="f-sub-head">상생사업</span>
                                                        <a class="f-title1" href="javascript:">0</a>
                                                    </li>
                                                </ul>
                                                <div class="pc btn-wrap">
                                                    <div class="btn-set">
                                                        <a class="btn-solid small white-bg" href="javascript:"><span>증명서 발급</span></a>
                                                        <a class="btn-solid small white-bg" href="javascript:"><span>1:1 문의</span></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mob btn-wrap">
                                            <div class="btn-set">
                                                <a class="btn-solid small gray-bg" href="javascript:"><span>증명서 발급</span></a>
                                                <a class="btn-solid small gray-bg" href="javascript:"><span>1:1 문의</span></a>
                                            </div>
                                        </div>
                                        <p class="last-date f-caption2"><span>최근 로그인 일시</span><span class="date">2023.01.01 10:00</span></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습중인 과정 (<span class="item-count">1</span>개)</p>
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
                                                <tr>
                                                    <td class="t-align-center">일반 > 회원가입</td>
                                                    <td><p class="txt-ellipsis">로그인,</p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                    <td class="t-align-center"><p class="box-label bigger waiting"><span>접수대기</span></p></td>
                                                    <td class="t-align-center">-</td>
                                                </tr>
                                                <tr>
                                                    <td class="t-align-center">일반 > 회원가입</td>
                                                    <td><p class="txt-ellipsis">로그인, 회원가입, 회원탈퇴는 어떻게 하나요? 로그인, 회원가입, 회원탈퇴는 어떻게 하나요? 로그인, 회원가입, 회원탈퇴는 어떻게 하나요? 로그인, 회원가입, 회원탈퇴는 어떻게 하나요?</p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                    <td class="t-align-center"><p class="box-label bigger complete"><span>접수완료</span></p></td>
                                                    <td class="t-align-center">-</td>
                                                </tr>
                                                <tr>
                                                    <td class="t-align-center">일반 > 회원가입</td>
                                                    <td><p class="txt-ellipsis">로그인, 회원가입, 회원탈퇴는 어떻게 하나요? 로그인, 회원가입, 회원탈퇴는 어떻게 하나요?</p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                    <td class="t-align-center"><p class="box-label bigger"><span>답변완료</span></p></td>
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                </tr>
                                                <tr>
                                                    <td class="t-align-center">일반 > 회원가입</td>
                                                    <td><p class="txt-ellipsis">로그인, 회원가입, 회원탈퇴는 어떻게 하나요? 로그인, 회원가입, 회원탈퇴는 어떻게 하나요?</p></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                    <td class="t-align-center"><p class="box-label bigger"><span>답변완료</span></p></td>
                                                    <td class="t-align-center">2023.09.25 13:00</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-wrap align-right">
                                            <a class="btn-text-icon black-circle" href="javascript:"><span>나의 1:1문의 바로가기</span></a>
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