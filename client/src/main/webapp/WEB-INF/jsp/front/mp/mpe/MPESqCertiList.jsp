<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
    </div>
    <div class="divide-con-area" >
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="guide-info-area scroll-motion" data-controller="controller/mp/mpe/MPESqCertiManageBtnCtrl">
                <div class="for-motion">
                    <div class="btn-wrap align-right">
                        <div class="btn-set">
                            <c:if test="${ posibleSqCertiCnt eq 1 and loginMap.authCd eq 'CP' and (empty sqCerti)}">
                                <button class="btn-solid small black-bg" type="button" id="paymentInfoManagPopupBtn"><span>SQ평가원 자격증 신청</span></button>
                            </c:if>
                            <c:if test="${ not empty sqCerti and sqCerti.useYn eq 'Y' and sqCerti.issueCd ne 'EBD_SQ_R'}">
                                <button class="btn-solid small gray-bg" type="button"><span>SQ평가원 자격증 보기</span></button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpe/MPESqCertiCompleteListCtrl">
                    <form >
                        <input type="hidden" name="pageIndex" value="1" />
                        <input type="hidden" name="pageRowSize" value="10" />
                        <input type="hidden" name="listRowSize" value="10" />
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">수료 교육 이력</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="info-head no-bdr">
                                    <p class="article-total-count f-body2">총 <span id="completeListContainerTotCnt">${ educationCompleteListCnt }</span>개</p>
                                </div>
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table w864"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                            <caption>회원목록</caption>
                                            <colgroup>
                                                <col style="width: 8%;">
                                                <col style="width: 26%;">
                                                <col style="width: 11%;">
                                                <col style="width: 11%;">
                                                <col style="width: 22%;">
                                                <col style="width: 11%;">
                                                <col style="width: 11%;">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>과정</th>
                                                <th>회차</th>
                                                <th>업종</th>
                                                <th>교육기간</th>
                                                <th>신청일</th>
                                                <th>수료일</th>
                                            </tr>
                                            </thead>
                                            <tbody id="completeListContainer">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line " href="javascript:" id="moreCompletePagingContainer"><span>더보기</span><span class="item-count">(0/0)</span></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpe/MPESqRepairListCtrl">
                    <form>
                        <input type="hidden" name="pageIndex" value="1" />
                        <input type="hidden" name="pageRowSize" value="10" />
                        <input type="hidden" name="listRowSize" value="10" />
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">보수 교육 이력</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="info-head no-bdr">
                                    <p class="article-total-count f-body2">총 <span id="repairListContainerTotCnt">0</span>개</p>
                                </div>
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table w864"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                            <caption>회원목록</caption>
                                            <colgroup>
                                                <col style="width: 8%;">
                                                <col style="width: 29%;">
                                                <col style="width: 13%;">
                                                <col style="width: 24%;">
                                                <col style="width: 13%;">
                                                <col style="width: 13%;">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>과정</th>
                                                <th>회차</th>
                                                <th>교육기간</th>
                                                <th>신청일</th>
                                                <th>수료일</th>
                                            </tr>
                                            </thead>
                                            <tbody id="repairListContainer">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line" href="javascript:" id="moreRepairPagingContainer"><span>더보기</span><span class="item-count">(0/0)</span></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
<!-- 지급정보관리 팝업 -->
<c:if test="${loginMap.authCd eq 'CP'}">
    <div class="layer-popup paymentInfoManagPopup" data-controller="controller/mp/mpe/MPESqCertiAppliyCtrl"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <form style="height:100%">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="for-center" >
                <div class="pop-wrap">
                    <div class="pop-con-area">
                        <div class="tit-area">
                            <p class="f-title1">SQ평가원 자격증 신청</p>
                        </div>
                        <div class="con-area" id="paymentInfoManagPopupFrm">
                        </div>
                        <div class="bot-fix-btn-area">
                            <div class="btn-wrap align-right">
                                <button class="btn-solid small black-bg " type="button" id="submitBtn"><span>자격증 신청</span></button>
                            </div>
                        </div>
                        <div class="user-opt-area">
                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</c:if>