<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

    <!-- 담당자 정보 안내 팝업 -->
    <div class="layer-popup educCenterInfoPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">교육장 안내</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec" style="height: 500px;">
                                <div class="sec-con-area" id="eduRoom" style="height: 500px;">

                                </div>
                            </div>
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>교육장 안내</caption>
                                            <colgroup>
                                                <col style="width: 160rem;">
                                                <col style="width: 480rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>교육장명</th>
                                                <td>자동차부품산업진흥재단 5F 대회의실</td>
                                            </tr>
                                            <tr>
                                                <th>주소</th>
                                                <td>[12345] 서울특별시 용산구 원효로 74 4층 회의실</td>
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




