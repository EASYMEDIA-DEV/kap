<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>


<div id="wrap" class="mypage" data-controller="controller/mp/mpi/MPIUserWthdrwController "><!-- 마이페이지 mypage 클래스 추가 -->
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">회원탈퇴</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            </div>

            <div class="right-con-area">
                <form name="formWthdrwSuccess" id="formWthdrwSuccess" method="post" action="/my-page/member/wthdrw/wthdrw-success">
                    <input type="hidden" id="regDtm" name="regDtm"  />
                    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                </form>
                <form name="formUserWthdrwSubmit" id="formUserWthdrwSubmit"  method="post"  >
                    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">지금까지 자동차부품산업진흥재단 서비스를 이용해주셔서 감사합니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec">
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="sub-title">아래의 사항을 반드시 확인하시고 회원탈퇴를 신청해주십시오.</p>
                                            <div class="ul-txt-w">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt has-dot">회원탈퇴를 신청하시면 해당 아이디는 즉시 탈퇴 처리되며, 이후 동일 아이디로는 재가입이 불가능합니다.</p>
                                                    <p class="ul-txt has-dot">탈퇴 시 회원님의 개인정보는 영구 삭제됩니다.</p>
                                                    <p class="ul-txt has-dot">삭제 시 작성하신 게시물은 본인을 확인할 수 없으므로 삭제처리가 원천적으로 불가능합니다. 회원님이 작성하신 게시물의 삭제를 원하시면 회원탈퇴 전 먼저 삭제해주시기 바랍니다.</p>
                                                    <p class="ul-txt has-dot">탈퇴한 뒤 데이터를 복구할 수 없으니 신중히 진행해주십시오.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">회원탈퇴 사유 선택</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <div class="opt-group verticality"><!-- verticality : 아래로 떨어지는 -->
                                                            <c:forEach var="cdList" items="${cdDtlList.MEM_WTHDRW}" varStatus="status">
                                                            <div class="form-radio">
                                                                <input type="radio" id="reasonsRadio${status.index}" name="wthdrwRsnCd" value="${cdList.cd}" class="wthdrwRsnCd" title="탈퇴 사유">
                                                                <label for="reasonsRadio${status.index}">${cdList.cdNm}</label>
                                                            </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                    <div class="inner-line">
                                                        <div class="etc-option-w">
                                                            <div class="form-group">
                                                                <div class="form-input w-longest">
                                                                    <input type="text" placeholder="기타 사유 입력" id="wthdrwRsnEtcNm" name="wthdrwRsnEtcNm" class="notRequired" disabled>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <button class="btn-solid small black-bg" type="submit"><span>회원탈퇴</span></button>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->


</div>