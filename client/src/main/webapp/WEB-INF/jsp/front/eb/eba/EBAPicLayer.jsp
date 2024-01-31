<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<%--<div id="wrap" data-controller="controller/eb/eba/EBACouseDtlCtrl">--%>

    <!-- 회차 담당자 정보 안내 팝업 -->
    <div class="layer-popup eduPersonInfoPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">회차 담당자 정보 안내</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-info-sec">
                                <p class="f-sub-head">해당 회차에 궁금하신 점이 있으신 경우 아래 담당자에게 문의바랍니다.</p>
                            </div>
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <div class="p-list-box-w">
                                        <div class="p-list-box">
                                            <p class="f-body1">평일 09:00 ~ 17:00</p>
                                        </div>
                                        <div class="p-list-box">
                                            <p class="f-body1">점심시간 11:30 ~ 12:30 / 토, 일요일 및 공휴일 휴무</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="p-cont-sec">
                                <div class="sec-tit-area">
                                    <p class="f-head">회차 담당자</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>회차 담당자 정보 안내</caption>
                                            <colgroup>
                                                <col style="width: 160rem;">
                                                <col style="width: 480rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>이름</th>
                                                <td>홍길동</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>honggil@kapkorea.org</td>
                                            </tr>
                                            <tr>
                                                <th>전화번호</th>
                                                <td>02-1234-5678</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="dimd"></div>--%>

<%--</div>--%>




