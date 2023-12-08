<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<script type="text/javascript">
    if('${data}' != '') {
        alert("이미 가입된 회원입니다. 로그인 페이지로 이동합니다.");
        location.href = "/login";
    }
</script>
<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
    if (request.getProtocol().equals("HTTP/1.1"))
        response.setHeader("Cache-Control", "no-cache");
%>


<div id="wrap" class="member" data-controller="controller/mp/MPUserController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
    <form name="formNextOne" id="formNextOne"  method="post"  >
            <input type="hidden" id="name" name="name" value="${verificationData.name}">
            <input type="hidden" id="birthdate" name="birthdate" value="${verificationData.birthdate}">
            <input type="hidden" id="mobile_no" name="mobile_no" value="${verificationData.mobile_no}">
            <input type="hidden" id="ci" name="ci" value="${verificationData.ci}">
            <input type="hidden" id="param1" name="param1" value="${verificationData.param1}">
            <input type="hidden" id="gndr" name="gndr" value="${verificationData.gndr}">
            <input type="hidden" id="agree" name="agree">
            <input type="hidden" id="trmsAgmntYn" name="trmsAgmntYn" >
            <input type="hidden" id="psnifAgmntYn" name="psnifAgmntYn" >
            <input type="hidden" id="psnif3AgmntYn" name="psnif3AgmntYn" >
            <input type="hidden" id="fndnNtfyRcvYn" name="fndnNtfyRcvYn" >
            <input type="hidden" id="ntfySmsRcvYn" name="ntfySmsRcvYn" >
            <input type="hidden" id="ntfyEmailRcvYn" name="ntfyEmailRcvYn" >
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area t-align-center">
                    <p class="page-tit f-large-title">
                        <span class="for-move">회원가입</span>
                    </p>
                </div>
            </div>

            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">본인인증</p>
                    </div>
                    <div class="step-list ongoing">
                        <p class="step-num">2</p>
                        <p class="step-con">약관동의</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">3</p>
                        <p class="step-con">회원정보 입력</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">4</p>
                        <p class="step-con">가입완료</p>
                    </div>
                </div>
            </div>

            <div class="inner-con-box">
                <!-- 약관 영역 -->
                <div class="noti-txt-w">
                    <p class="bullet-noti-txt f-caption2">* 이용약관 및 개인정보 수집·이용·제공 동의 내용을 확인하였으며, 아래 내용에 동의합니다.</p>
                </div>

                <div class="agree-terms-w">
                    <div class="all-check-area">
                        <div class="form-checkbox">
                            <input type="checkbox" id="allAgreeChk" name="">
                            <label for="allAgreeChk">전체 약관 동의</label>
                        </div>
                    </div>
                    <div class="agree-list-w accordion-st">
                        <div class="list-item active">
                            <a class="acco-click-area" href="javascript:">
                                <div class="form-checkbox">
                                    <input type="checkbox" id="agreeChk1" name="">
                                    <label for="agreeChk1">이용약관 동의 <span class="color-sky">(필수)</span></label>
                                </div>
                            </a>
                            <div class="acco-hide-area">
                                <div class="terms-con">
                                    이용약관 내용이 등록될 예정입니다.
                                </div>
                            </div>
                        </div>
                        <div class="list-item">
                            <a class="acco-click-area" href="javascript:">
                                <div class="form-checkbox">
                                    <input type="checkbox" id="agreeChk2" name="">
                                    <label for="agreeChk2">개인정보처리방침 동의 <span class="color-sky">(필수)</span></label>
                                </div>
                            </a>
                            <div class="acco-hide-area">
                                <div class="terms-con">
                                    개인정보처리방침 내용이 등록될 예정입니다.
                                </div>
                            </div>
                        </div>
                        <div class="list-item">
                            <a class="acco-click-area" href="javascript:">
                                <div class="form-checkbox">
                                    <input type="checkbox" id="agreeChk3" name="">
                                    <label for="agreeChk3">제 3자 개인정보 제공 동의 <span class="color-gray">(선택)</span></label>
                                </div>
                            </a>
                            <div class="acco-hide-area">
                                <div class="terms-con">
                                    제 3자 개인정보 제공 동의 내용이 등록될 예정입니다.
                                </div>
                            </div>
                        </div>
                        <div class="list-item marketing"><!-- marketing -->
                            <a class="acco-click-area" href="javascript:">
                                <div class="form-checkbox">
                                    <input type="checkbox" id="agreeChk4" name="">
                                    <label for="agreeChk4">마케팅 정보 수신 동의 <span class="color-gray">(선택)</span></label>
                                </div>
                            </a>
                            <div class="acco-hide-area">
                                <div class="terms-con">
                                    마케팅 정보 수신 동의 내용이 등록될 예정입니다.
                                </div>
                            </div>
                            <div class="included-chk-area">
                                <div class="form-group">
                                    <div class="form-checkbox">
                                        <input type="checkbox" id="marketingChk1" name="">
                                        <label for="marketingChk1">이메일</label>
                                    </div>
                                    <div class="form-checkbox">
                                        <input type="checkbox" id="marketingChk2" name="">
                                        <label for="marketingChk2">SMS</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <button class="btn-solid small gray-bg btnBack"><span>이전</span></button>
                        </div>
                        <div class="btn-set">
                            <button class="btn-solid small black-bg" id="nextPageOne" ><span>다음</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>