<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>

<script type="text/javascript">

    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            $("#allAgreeChk").prop("checked",false)
            $("#agreeChk1").prop("checked",false);
            $("#agreeChk2").prop("checked",false);
            $("#agreeChk3").prop("checked",false);
            $("#agreeChk4").prop("checked",false);
            $("#marketingChk1").prop("checked",false);
            $("#marketingChk2").prop("checked",false);
        }
    }

    if('${data}' != 0) {
        alert("이미 가입된 회원입니다. 로그인 페이지로 이동합니다.");
        location.href = "/login";
    }
</script>



<div id="wrap" class="member" data-controller="controller/mp/MPUserController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
    <form name="formNextOne" id="formNextOne"  method="get"  >
            <input type="hidden" id="name" name="name" value="${verificationData.name}">
            <input type="hidden" id="birthdate" name="birthdate" value="${verificationData.birthdate}">
            <input type="hidden" id="mobileno" name="mobileno" value="${verificationData.mobileno}">
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
                                    ${rtnInfo.tmncsCntn}
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
                                <!-- 2023-12-29 약관 추가 -->
                                <div class="terms-con">
                                    ${rtnInfo.psnifCntn}
                                </div>
                                <!-- // 2023-12-29 약관 추가 -->
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
                                <!-- 2023-12-29 약관 추가 -->
                                <div class="terms-con">
                                    ${rtnInfo.offerCntn}
                                    <div class="paragraphs">
                                        <p class="txt f-caption2">&lt;자동차부품산업진흥재단&gt;은 고객 동의를 받아 다음과 같이 개인정보를 제3자에게 제공하고자 합니다.</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="txt f-caption2">
                                            1) 재단은 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 ｢개인정보 보호법｣ 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 재단은 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.
                                            <span class="list">
                            - 제공받는 제3자 : GPC(현대차글로벌상생협력센터)
                            <br/>- 제공 목적 : GPC에서 진행되는 교육과정
                            <br/>- 제공하는 개인정보 항목 : 성명, 성별, ID, 소속사명, 생년월일, 교육분야
                            <br/>- 보유/이용기간 : GPC의 개인정보 이용목적 및 보관기관에 따름
                          </span>
                                            <span class="list">
                            - 제공받는 제3자 : 고용노동부
                            <br/>- 제공 목적 : 산업전환 공동훈련 과정
                            <br/>- 제공하는 개인정보 항목 : 성명, 휴대전화번호
                            <br/>- 보유/이용기간 : 직업능력 개발정보망(HRD-net) 개인정보파일 운영 목적 및 보관기간에 따름
                          </span>
                                        </p>
                                        <p class="txt f-caption2">귀하는 개인정보 제3자 제공에 대한 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 재단 홈페이지 회원 가입 및 이용이 제한됩니다</p>
                                        <p class="txt f-caption2">상기 안내사항에 모두 동의하고 회원가입 절차를 계속 진행하시려면 [동의 및 가입]을 그렇지 않으면 [동의하지 않음]을 누르세요</p>
                                    </div>
                                </div>
                                <!-- // 2023-12-29 약관 추가 -->
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
                                    ${rtnInfo.mktRcvAgmntCntn}
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