<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbk/WBKManagementCtrl">
    <form id="frmData" name="frmData" enctype="multipart/form-data">
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
<%--        <input type="hidden" class="notRequired" name="episdSeq" value="${episdSeq}" />--%>
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <div class="step-list">
                            <p class="step-num">2</p>
                            <p class="step-con">정보입력</p>
                        </div>
                        <div class="step-list">
                            <p class="step-num">3</p>
                            <p class="step-con">신청완료</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="img-area">
                <div class="gray-bg"></div>
                <div class="graphic-item-w">
                    <div class="item"></div>
                    <div class="item"></div>
                </div>
            </div>
        </div>

        <div class="divide-con-area">
            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            <!--LNB 종료-->
            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="guide-info-area">
                                <div class="divide-box">
                                    <p class="exclamation-txt f-sub-head">회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.<br>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.</p>
                                </div>
                            </div>
                            <div class="sec-tit-area">
                                <p class="f-title3">신청자 기본정보를 확인해주세요</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>신청자</th>
                                                <td>${rtnUser.name}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                        <td>${rtnUser.hpNo}</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${rtnUser.email}</td>
                                            </tr>
                                            <tr>
                                                <th>일반 전화번호</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty rtnUser.memTelNo}">
                                                            ${rtnUser.memTelNo}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>성별</th>
                                                <td>${rtnUser.gender}</td>
                                            </tr>
                                            <tr>
                                                <th>생년월일</th>
                                                <td>${rtnUser.birthdate}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="btn-wrap align-right">
                                        <a class="btn-text-icon black-circle" href="javascript:"><span>신청자 기본정보 수정</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="./content"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="./step2?episdSeq=${episdSeq}"><span>다음</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- content 영역 end -->