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

    if('${data}' != '') {
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
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 1 장 총 칙</p>
                                        <p class="sub-tit f-caption1">제 1 조 (목적)</p>
                                        <p class="txt f-caption2">이 약관은 재)자동차부품산업진흥재단(이하 "재단"이라 합니다.)이 제공하는 재단 사업에 관련된 서비스 (이하 "서비스"라 한다)의 이용 조건 및 절차에 관한 기본적인 사항에 관한 정의를 목적으로 합니다.</p>
                                        <p class="sub-tit f-caption1">제 2 조 (약관의 효력 및 변경)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>이 약관은 서비스를 통하여 이를 공지하거나 전자메일 등의 방법으로 회원에게 통지함으로써 효력이 발생됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 사정상 중요한 사유가 발생될 경우 사전 고지 없이 이 약관의 내용을 변경할 수 있으며, 변경된 약관은 제1항과 같은 방법으로 공지 또는 통지함으로써 효력이 발생됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>회원은 변경된 약관에 동의하지 않을 경우 회원 해지(탈퇴)를 요청할 수 있으며, 변경된 약관의 효력발생일 이후에도 서비스를 계속 사용할 경우 약관의 변경 사항에 동의한 것으로 간주됩니다.</p>
                                        <p class="sub-tit f-caption1">제 3 조 (약관 외 준칙)</p>
                                        <p class="txt f-caption2">본 약관에 명시되지 아니한 사항에 대해서는 전기통신기본법, 전기통신사업법, 정보통신망 이용촉진 등에 관한 법률 및 기타 관련 법령의 규정에 따릅니다.</p>
                                        <p class="sub-tit f-caption1">제 4 조 (용어의 정의)</p>
                                        <p class="txt f-caption2">이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원 : 재단과 서비스 이용 계약을 체결하고 이용자 아이디(ID)를 부여받은 법인 및 개인</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>아이디(ID) : 회원의 식별과 서비스 이용을 위하여 회원이 정하고 재단이 승인하는 문자 또는 숫자의 조합 (이하 "ID"라 합니다)</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>비밀번호 : 회원이 부여받은 ID와 일치되는 회원임을 확인하고, 회원의 비밀보호를 위해 회원 자신이 정한 문자 또는 숫자의 조합</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>해지(탈퇴) : 재단 또는 회원이 서비스 개통 후 이용계약을 해약하는 것</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 2 장 서비스 이용 계약</p>
                                        <p class="sub-tit f-caption1">제 5 조 (이용 계약의 성립)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>서비스 가입 신청 시 본 약관을 읽고 "동의함" 버튼을 클릭하면 이 약관에 동의하는 것으로 간주됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>이용계약은 서비스 이용신청 고객의 이용약관 동의 후 이용신청에 대하여 재단이 승낙함으로써 성립합니다.</p>
                                        <p class="sub-tit f-caption1">제 6 조 (이용 신청)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원에 가입하기 위해서는 재단이 요청하는 소정의 가입신청 양식에서 요구하는 사항을 기록하여 신청하여야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>가입신청 양식에 기재하는 모든 회원 정보는 모두 실제 데이터인 것으로 간주됩니다. 실명이나 실제 정보를 입력하지 않은 사용자는 법적인 보호를 받을 수 없으며, 서비스 이용에 제한을 받을 수 있습니다.</p>
                                        <p class="sub-tit f-caption1">제 7 조 (개인정보의 보호)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>재단은 회원이 가입 시에 제공한 개인정보와 이용자의 서비스 이용중에 발생하는 모든 개인정보를 매우 중요하게 생각하고 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 이용 신청 시 및 서비스 이용중에 회원이 제공하는 정보 등을 통하여 회원에 관한 정보를 수집하며, 회원의 개인정보는 본 이용계약의 이행과 본 이용계약상의 서비스 제공을 위한 목적으로 사용됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>재단은 서비스 제공과 관련하여 취득한 회원의 신상정보를 본인의 승낙없이 제3자에게 누설 또는 배포할 수 없으며 상업적 목적으로 사용할 수 없습니다. 다만, 다음의 각 호에 해당하는 경우에는 그러하지 아니합니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 관계 법령에 의하여 수사상의 목적으로 관계기관으로부터 요청이 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 정보통신윤리위원회의 요청이 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 기타 관계법령에서 정한 절차에 따른 요청이 있는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>제 3 항의 범위 내에서 재단은 업무와 관련하여 회원 전체 또는 일부의 개인정보에 관한 집합적인 통계 자료를 작성하여 이를 사용할 수 있고, 서비스를 통하여 회원의 컴퓨터에 쿠키를 전송할 수 있습니다. 이 경우 회원은 쿠키의 수신을 거부하거나 쿠키의 수신에 대하여 경고하도록 사용하는 컴퓨터의 브라우저의 설정을 변경할 수 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑤</span>재단이 제 3항의 규정에도 불구하고 고지 또는 명시한 범위를 초과하여 회원의 개인정보를 이용하거나 제3자에게 제공하고자 하는 경우에는 제공받는 자, 제공목적 및 제공할 정보의 내용 등을 반드시 당해 회원에게 개별적으로 서면이나 전자우편 등으로 통지하여 동의를 받아야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑥</span>회원은 언제나 이용계약을 해지함으로써 개인정보의 수집 및 이용에 대한 동의 철회를 요구할 수 있으며 재단은 본인 여부를 확인하는 즉시 동의 철회를 위한 필요한 조치를 시행합니다. 해지의 방법은 이 약관에서 별도로 규정한 바에 따릅니다.</p>
                                        <p class="sub-tit f-caption1">제 8 조 (이용 신청의 승낙)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>재단은 제6조에 따른 이용신청 고객에 대하여 아래② , ③의 경우를 예외로 하여 서비스 이용을 승낙합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 아래 사항에 해당하는 경우에 이용신청에 대한 승낙을 제한할 수 있고 그 사유가 해소될 때까지 승낙을 유보할 수 있습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 서비스 관련 설비에 여유가 없는 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 기술상 지장이 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 기타 재단의 사정상 필요하다고 인정되는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>재단은 아래 사항에 해당하는 경우에 이용신청에 대한 승낙을 하지 않을 수 있습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 본인의 실명으로 신청하지 않은 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 다른 사람의 명의를 사용하여 신청한 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 이용자 정보를 허위로 기재하여 신청한 경우</p>
                                        <p class="num-sub-txt f-caption2">4. 사회의 안녕질서 또는 미풍양속을 저해할 목적으로 신청한 경우</p>
                                        <p class="num-sub-txt f-caption2">5. 기타 재단이 정한 이용신청요건이 미비되었을 때</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>제 2항 또는 3항에 의하여 이용신청의 승낙을 유보하거나 승낙하지 아니하는 경우, 재단은 이를 이용 신청자에게 알려야 합니다. 다만, 재단의 귀책사유없이 이용신청자에게 통지할 수 없는 경우는 예외로 합니다.</p>
                                        <p class="sub-tit f-caption1">제 9 조 (회원정보의 조회 및 변경)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원은 서비스 메뉴를 통해 언제든지 본인의 개인정보를 조회하고 수정할 수 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>회원은 이용신청 시 기재한 사항이 변경되었을 경우 서비스 메뉴를 통해 수정을 해야 하며 회원정보를 변경하지 않음으로 인하여 발생되는 문제의 책임은 회원에게 있습니다.</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 3 장 계약 당사자의 의무</p>
                                        <p class="sub-tit f-caption1">제 10 조 (재단의 의무)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>재단은 회원으로부터 제기되는 의견이나 불만이 정당하다고 인정할 경우에는 즉시 처리하여야 합니다. 다만, 즉시 처리가 곤란한 경우는 회원에게 그 사유와 처리일정을 전자메일 또는 전화 등으로 통보하여야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 회원 개인정보 보호와 관련하여 제 7조 제 1,2,3항에 제시된 내용을 지킵니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>재단은 계속적이고 안정적인 서비스의 제공을 위하여 지속적으로 노력하며, 설비에 장애가 생기거나 멸실 된 때에는 지체없이 이를 수리 복구합니다. 다만, 천재지변, 비상사태 또는 그 밖에 부득이한 경우에는 그 서비스를 일시 중단하거나 중지할 수 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>재단은 이용계약의 체결, 계약사항의 변경 및 해지 등 회원과의 계약관련 절차 및 내용 등에 있어 회원에게 편의를 제공하도록 노력합니다.</p>
                                        <p class="sub-tit f-caption1">제 11 조 (회원의 의무)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원은 서비스 이용과 관련하여 다음 각 호에 해당되는 행위를 하여서는 안됩니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 다른 회원의 ID와 비밀번호, 주민등록번호 등을 도용하는 행위</p>
                                        <p class="num-sub-txt f-caption2">2. 본 서비스를 통하여 얻은 정보를 재단의 사전승낙 없이 회원의 이용 이외 목적으로 복제하거나 이를 출판 및 방송 등에 사용하거나 제3자에게 제공하는 행위</p>
                                        <p class="num-sub-txt f-caption2">3. 타인의 특허, 상표, 영업비밀, 저작권 기타 지적재산권을 침해하는 내용을 게시, 전자메일 또는 기타의 방법으로 타인에게 유포하는 행위</p>
                                        <p class="num-sub-txt f-caption2">4. 공공질서 및 미풍양속에 위반되는 저속, 음란한 내용의 정보, 문장, 도형 등을 전송, 게시, 전자메일 또는 기타의 방법으로 타인에게 유포하는 행위</p>
                                        <p class="num-sub-txt f-caption2">5. 모욕적이거나 위협적이어서 타인의 프라이버시를 침해할 수 있는 내용을 전송, 게시, 전자메일 또는 기타의 방법으로 타인에게 유포하는 행위</p>
                                        <p class="num-sub-txt f-caption2">6. 범죄와 결부된다고 객관적으로 판단되는 행위</p>
                                        <p class="num-sub-txt f-caption2">7. 재단의 승인을 받지 않고 다른 사용자의 개인정보를 수집 또는 저장하는 행위</p>
                                        <p class="num-sub-txt f-caption2">8. 기타 관계법령에 위배되는 행위</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>회원은 이 약관에서 규정하는 사항과 서비스 이용안내 또는 주의사항 등 재단이 공지 혹은 통지하는 사항을 준수하여야 하며, 기타 재단의 업무에 방해되는 행위를 하여서는 안됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>회원의 ID와 비밀번호에 관한 모든 관리책임은 회원에게 있습니다. 회원에게 부여된 ID와 비밀번호의 관리 소홀, 부정 사용에 의하여 발생하는 모든 결과에 대한 책임은 회원에게 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>회원은 자신의 ID나 비밀번호가 부정하게 사용되었다는 사실을 발견한 경우에는 즉시 재단에 신고 하여야하며, 신고를 하지 않아 발생하는 모든 결과에 대한 책임은 회원에게 있습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑤</span>회원은 내용별로 재단이 서비스 공지사항에 게시하거나 별도로 공지한 이용제한 사항을 준수하여야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑥</span>회원은 재단의 사전승낙 없이는 서비스를 이용하여 영업활동을 할 수 없으며, 그 영업활동의 결과와 회원이 약관에 위반한 영업활동을 하여 발생한 결과에 대하여 재단은 책임을 지지 않습니다. 회원은 이와 같은 영업활동으로 재단이 손해를 입은 경우 회원은 재단에 대하여 손해배상의무를 집니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑦</span>회원은 재단의 명시적인 동의가 없는 한 서비스의 이용권한, 기타 이용계약상 지위를 타인에게 양도, 증여할 수 없으며, 이를 담보로 제공할 수 없습니다.</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 4 장 서비스 제공 및 이용</p>
                                        <p class="sub-tit f-caption1">제 12 조 (서비스 이용시간)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>서비스의 이용은 재단이 업무상 또는 기술상 특별한 경우를 제외하고는 연중무휴 1일 24시간 동안 가능한 것을 원칙으로 합니다. 다만, 재단의 업무상이나 기술상의 이유로 서비스가 일시 중지되거나, 운영상의 목적으로 재단이 정한 기간에는 서비스가 일시 중지될 수 있습니다. 이런 경우 재단은 사전 또는 사후 이를 공지합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 서비스 내용별로 이용가능 시간을 정할 수 있으며 이 경우 그 내용을 서비스를 통해 사전 공지합니다.</p>
                                        <p class="sub-tit f-caption1">제 13 조 (정보의 제공)</p>
                                        <p class="txt f-caption2">재단은 회원이 서비스 이용 중 필요하다고 인정되는 다양한 정보에 대해서 공지사항이나 전자메일 등의 방법으로 회원에게 제공할 수 있으며, 회원은 원하지 않을 경우 가입신청 메뉴와 회원정보수정 메뉴에서 정보수신거부를 할 수 있습니다. 또한 재단이 제공하는 서비스는 기본적으로 무료입니다.</p>
                                        <p class="sub-tit f-caption1">제 14 조 (회원의 게시물)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>서비스에 게재된 자료에 대한 권리는 다음 각 호와 같습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 게시물에 대한 권리와 책임은 게시자에게 있으며 재단은 게시자의 동의 없이는 이를 서비스 내 게재 외에 영리적 목적으로 사용하지 않습니다. 단, 비영리적인 경우에는 그러하지 아니하며 또한 재단은 서비스내의 게재권을 갖습니다.</p>
                                        <p class="num-sub-txt f-caption2">2. 회원은 서비스를 이용하여 얻은 정보를 가공, 판매하는 행위 등 서비스에 게재된 자료를 상업적으로 사용할 수 없습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 회원이 서비스에 게시, 게재하거나 서비스를 통해 전송한 내용물에 대하여 책임지지 않으며, 아래 사항에 해당하는 경우 사전 통지 후 삭제할 수 있습니다. 다만, 긴급을 요하는 경우에는 임의 조치할 수 있습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 타인을 비방하거나, 프라이버시를 침해하거나, 명예를 손상시키는 내용인 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 서비스의 안정적인 운영에 지장을 주거나 줄 우려가 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 범죄적 행위에 관련된다고 인정되는 내용일 경우</p>
                                        <p class="num-sub-txt f-caption2">4. 재단의 지적재산권, 타인의 지적재산권 등 기타 권리를 침해하는 내용인 경우</p>
                                        <p class="num-sub-txt f-caption2">5. 재단에서 규정한 게시기간을 초과한 경우</p>
                                        <p class="num-sub-txt f-caption2">6. 기타 관계법령에 위반된다고 판단되는 경우</p>
                                        <p class="sub-tit f-caption1">제 15 조 (서비스 내용 변경)</p>
                                        <p class="txt f-caption2">서비스 내용이 추가, 변경 또는 삭제되는 경우에는 필요한 사항을 회원들에게 공지하거나 통보합니다.</p>
                                        <p class="sub-tit f-caption1">제 16 조 (서비스 제공의 제한 및 중지)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>재단은 다음에 해당하는 경우 서비스 제공의 일부 또는 전부를 제한하거나 중지할 수 있습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 서비스용 설비의 보수, 정기점검 또는 공사로 인한 부득이한 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 전기통신사업법에 규정된 기간통신사업자가 전기통신 서비스를 중지한 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 전시, 사변, 천재지변 또는 이에 준하는 국가비상사태가 발생하거나 발생할 우려가 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">4. 설비 장애 또는 이용 폭주 등으로 서비스 이용에 지장이 있는 경우</p>
                                        <p class="num-sub-txt f-caption2">5. 재단이 만든 서비스의 기본 취지에 반하는 게시물(음란, 퇴폐적인 것 등)이 등록되어 대다수 이용자를 건전한 서비스 이용에 지장을 초래하거나, 지장을 줄 우려가 있는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단이 제1항의 규정에 의하여 서비스 제공의 일부 또는 전부를 제한하거나 중지한 때에는 그 사유 및 제한기간 등을 회원에게 알립니다.</p>
                                        <p class="sub-tit f-caption1">제 17 조 (서비스 이용 책임)</p>
                                        <p class="txt f-caption2">회원은 재단에서 권한 있는 사원이 서명한 명시적인 서면에 구체적으로 허용한 경우를 제외하고는 이용하여 불법상품을 판매하는 영업활동을 할 수 없으며 특히 해킹,돈벌기 광고,음란사이트를 통한 상업행위, ㅋ용S/W 불법배포 등을 할 수 없습니다. 이를 어기고 발생한 영업활동의 결과 및 손실, 관계기관에 의한 구속 등 법적 조치 등에 관해서는 재단이 책임을 지지 않습니다.</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 5 장 계약해지 및 이용제한</p>
                                        <p class="sub-tit f-caption1">제 18 조 (계약해지 및 이용제한)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원이 이용계약을 해지하고자 할 때에는 회원 본인이 재단에 해지신청을 하여야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 회원이 아래 사항에 해당하는 행위를 하였을 경우 사전통지 후 계약을 해지하거나 또는 기간을 정하여 서비스 이용을 중지할 수 있습니다. 다만, 긴급을 요하는 경우 임의 조치할 수 있습니다.</p>
                                        <p class="num-sub-txt f-caption2">1. 타인의 서비스 ID 및 비밀번호를 도용한 경우</p>
                                        <p class="num-sub-txt f-caption2">2. 서비스 운영을 고의로 방해한 경우</p>
                                        <p class="num-sub-txt f-caption2">3. 공공질서 및 미풍양속에 저해되는 내용을 고의로 유포시킨 경우</p>
                                        <p class="num-sub-txt f-caption2">4. 회원이 사회적 공익을 저해할 목적으로 서비스 이용을 계획 또는 실행하는 경우</p>
                                        <p class="num-sub-txt f-caption2">5. 타인의 명예를 손상시키거나 불이익을 주는 행위를 한 경우</p>
                                        <p class="num-sub-txt f-caption2">6. 서비스의 안정적 운영을 방해할 목적으로 다량의 정보를 전송하거나 광고성 정보를 전송하는 경우</p>
                                        <p class="num-sub-txt f-caption2">7. 정보통신설비의 오작동이나 정보 등의 파괴를 유발시키는 컴퓨터 바이러스프로그램 등을 유포하는 경우</p>
                                        <p class="num-sub-txt f-caption2">8. 재단, 다른 회원 또는 타인의 지적재산권을 침해하는 경우</p>
                                        <p class="num-sub-txt f-caption2">9. 정보통신윤리위원회 등 외부기관의 시정요구가 있거나 불법선거 운동과 관련하여 선거관리위원회의 유권해석을 받은 경우</p>
                                        <p class="num-sub-txt f-caption2">10. 타인의 개인정보, 이용자ID 및 비밀번호를 부정하게 사용하는 경우</p>
                                        <p class="num-sub-txt f-caption2">11. 재단의 서비스 정보를 이용하여 얻은 정보를 재단의 사전 승낙없이 복제 또는 유통시키거나 상업적으로 이용하는 경우</p>
                                        <p class="num-sub-txt f-caption2">12. 회원이 자신의 홈페이지와 게시판에 음란물을 게재하거나 음란사이트를 링크하는 경우</p>
                                        <p class="num-sub-txt f-caption2">13. 가입한 이름이 실명이 아니거나 같은 사용자가 다른 ID로 이중등록을 한 경우</p>
                                        <p class="num-sub-txt f-caption2">14. 본 약관을 포함하여 기타 재단이 정한 이용조건 및 관계법령을 위반한 경우</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="tit f-caption1">제 6 장 손해배상 및 기타 사항</p>
                                        <p class="sub-tit f-caption1">제 19 조 (손해배상)</p>
                                        <p class="txt f-caption2">재단은 서비스 요금이 무료인 동안의 서비스 이용과 관련하여 회원에게 발생한 어떠한 손해에 관하여도 책임을 지지 않습니다.</p>
                                        <p class="sub-tit f-caption1">제 20 조 (면책조항)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>재단은 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임이 면제됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>재단은 회원의 귀책사유로 인한 서비스 이용의 장애에 대하여 책임을 지지 않습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>재단은 회원이 서비스를 이용하여 기대하는 수익을 상실한 것이나 서비스를 통하여 얻은 자료로 인한 손해에 관하여 책임을 지지 않습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>재단은 회원이 서비스에 게재한 정보, 자료, 사실의 신뢰도, 정확성 등 내용에 관하여는 책임을 지지 않습니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">⑤</span>재단은 서비스 이용과 관련하여 가입자에게 발생한 손해 가운데 가입자의 고의, 과실에 의한 손해에 대하여 책임을 지지 않습니다.</p>
                                        <p class="sub-tit f-caption1">제 21 조 (소송제기)</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>회원이 소송을 제기할 경우는 재단에 귀책 사유가 명백히 인정되는 경우에 한정됩니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>회원이 재단을 상대로 소송을 제기할 경우 모든 소송은 원인 발생일로부터 1년 이내에 제기되어야 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>모든 노력에도 불구하고 소송이 제기될 경우, 재단의 본사 소재지를 관할하는 법원을 전속 관할법원으로 합니다.</p>
                                    </div>
                                    <div class="paragraphs">
                                        <p class="txt f-caption2">
                                            [부칙]
                                            <br/>1. (시행일) 이 약관은 2024년 3월 1일부터 시행합니다.
                                        </p>
                                    </div>
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
                                <!-- 2023-12-29 약관 추가 -->
                                <div class="terms-con">
                                    <div class="paragraphs">
                                        <p class="sub-tit f-caption1">1. 총칙</p>
                                        <p class="txt f-caption2">&lt;자동차부품산업진흥재단&gt;은 (이하 ‘재단’이라 함)는 개인정보 보호를 매우 중요하게 여기고 있으며, ｢개인정보 보호법｣ 제30조에 따라 고객님의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립· 공개합니다.</p>
                                        <p class="sub-tit f-caption1">2. 개인정보 처리 목적, 항목 및 보유기간</p>
                                        <p class="txt f-caption2">재단은 다음의 목적을 위하여 아래와 같이 개인정보 항목을 처리하고 있습니다. 법령에 따른 개인정보 보유 및 이용기간 또는 개인정보 수집 시에 동의 받은 기간 내에서 개인정보를 처리, 보유합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적, 항목이 변경되는 경우에는 관련 법령에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.</p>
                                        <p class="sub-tit f-caption1">3. 수집하는 개인정보의 항목 및 수집방법</p>
                                        <p class="txt f-caption2">
                                            1) 수집하는 개인정보의 항목
                                            <br/>첫째, 재단은 회원가입, 각종 서비스의 제공을 위해 최초 회원가입 당시 아래와 같은 개인정보를 수집하고 있습니다.
                                            <br/>- 수집/이용 목적 : 회원가입을 위한 본인 확인, 회원 자격의 유지 관리, 서비스 부정 이용방지, 민원 처리 및 의견 수렴
                                            <br/>- 수집/이용 항목 : 성명, ID, 성별, 생년월일, 이메일, 휴대전화번호, 소속정보(소속사명, 부서, 직급) 등
                                            <br/>- 보유/이용 기간 : 재단 회원가입후 탈퇴시까지
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 개인정보 수집방법
                                            <br/>홈페이지 회원가입, 서비스 이용, 이벤트 응모, 배송요청 등을 통한 수집, 회원정보수정, 팩스, 전화 등 단, 서비스 과정에서 아래와 같은 정보들이 자동으로 생성되어 수집될 수 있습니다.
                                            <br/>- IP Address, 쿠키, 방문 일시, 서비스 이용 기록, 불량 이용 기록 선택정보를 입력하지 않은 경우에도 서비스 이용 제한은 없으며 이용자의 기본적 인권 침해의 우려가 있는 민감한 개인 정보(인종, 사상 및 신조, 정치적 성향 이나 범죄기록, 의료정보 등)는 수집하지 않습니다.
                                        </p>
                                        <p class="sub-tit f-caption1">4. 개인정보의 처리 목적</p>
                                        <p class="txt f-caption2">재단은 수집한 개인정보를 다음의 목적을 위해 활용합니다.</p>
                                        <p class="txt f-caption2">
                                            1) 서비스 제공
                                            <br/>서비스 제공에 관한 계약 이행 및 콘텐츠 제공, 특정 맞춤 서비스 제공, 교육활용, 이벤트 등으로 인한 물품 배송
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 회원관리
                                            <br/>회원제 서비스 이용에 따른 제한적 본인확인(실명인증, 아이핀), 개인 식별, 불량회원의 부정 이용 방지와 비인가 사용 방지, 가입 의사 확인, 연령확인, 불만처리 등 민원처리, 분쟁조정을 위한 기록보존, 고지사항 전달
                                        </p>
                                        <p class="txt f-caption2">
                                            3) 마케팅 및 광고에 활용
                                            <br/>신규 서비스 개발 및 맞춤 서비스 제공, 통계학적 특성에 따른 서비스 제공 및 광고 게재, 서비스의 유효성 확인, 이벤트 및 광고성 정보 제공 및 참여기회 제공, 접속빈도 파악, 회원의 서비스 이용에 대한 통계
                                        </p>
                                        <p class="sub-tit f-caption1">5. 개인정보의 파기 절차 및 방법</p>
                                        <p class="txt f-caption2">
                                            재단은 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체없이 파기합니다. 파기절차 및 방법은 다음과 같습니다.
                                        </p>
                                        <p class="txt f-caption2">
                                            1) 파기절차
                                            <br/>회원님이 회원가입 등을 위해 입력하신 정보는 목적이 달성된 후 별도의 DB로 옮겨져(종이의 경우 별도의 서류함) 내부 방침 및 기타 관련 법령에 의한 정보보호 사유에 따라(보유 및 이용기간 참조) 일정 기간 저장된 후 파기 되어집니다.
                                            <br/>별도 DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 보유되어지는 이외의 다른 목적으로 이용되지 않습니다.
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 파기방법
                                            <br/>- 전자적 파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.
                                            <br/>- 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.
                                        </p>
                                        <p class="sub-tit f-caption1">6. 개인정보 제공</p>
                                        <p class="txt f-caption2">재단은 이용자의 개인정보를 원칙적으로 외부에 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다.</p>
                                        <p class="txt f-caption2">
                                            - 이용자들이 사전에 동의한 경우
                                            <br/>- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우개인정보 제3자 제공
                                        </p>
                                        <p class="txt f-caption2">재단은 이용자의 개인정보를 원칙적으로 외부에 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다.</p>
                                        <p class="num-txt f-caption2"><span class="num">①</span>정보주체로부터 별도의 동의를 받은 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">②</span>다른 법률에 특별한 규정이 있는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">③</span>정보주체 또는 그 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">④</span>통계작성 및 학술연구 등의 목적을 위하여 필요한 경우로서 특정 개인을 알아볼 수 없는 형태로 개인정보를 제공하는 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">⑤</span>개인정보를 목적 외의 용도로 이용하거나 이를 제3자에게 제공하지 아니하면 다른 법률에서 정하는 소관업무를 수행할 수 없는 경우로서 보호위원회의 심의·의결을 거친 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">⑥</span>조약, 그 밖의 국제협정의 이행을 위하여 외국정부 또는 국제기구에 제공하기 위하여 필요한 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">⑦</span>범죄의 수사와 공소의 제기 및 유지를 위하여 필요한 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">⑧</span>법원의 재판업무 수행을 위하여 필요한 경우</p>
                                        <p class="num-txt f-caption2"><span class="num">⑨</span>형(刑) 및 감호, 보호처분의 집행을 위하여 필요한 경우</p>
                                        <p class="txt f-caption2">그 밖에 개인정보 제3자 제공이 필요한 경우에는 합당한 절차를 통한 이용자의 동의를 얻어 제3자에게 개인정보를 제공할 수 있습니다.</p>
                                        <p class="txt f-caption2">동의를 얻어 개인정보를 제공받는 자와 이용목적은 아래와 같습니다.</p>
                                        <p class="sub-tit f-caption1">7. 개인정보 처리의 위탁</p>
                                        <p class="txt f-caption2">
                                            1) 재단은 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무을 위탁하고 있습니다
                                            <br/>2) 재단은 위탁계약 체결 시 ｢개인정보 보호법｣ 제26조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적 ·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.
                                            <br/>3) 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.
                                            <br/>- 수탁자 : 이지미디어
                                            <br/>- 위탁업무 내용 : 전산 시스템 개발 및 유지보수
                                        </p>
                                        <p class="sub-tit f-caption1">8. 개인정보의 제3자 제공</p>
                                        <p class="txt f-caption2">
                                            1) 재단은 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 ｢개인정보 보호법｣ 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.
                                            <br/>2) 재단은 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.
                                            <br/>- 제공받는 제3자 : GPC(현대차글로벌상생협력센터)
                                            <br/>- 제공 목적 : GPC에서 진행되는 교육과정
                                            <br/>- 제공하는 개인정보 항목 : 성명, 성별, ID, 소속사명, 생년월일, 교육분야
                                            <br/>- 보유/이용기간 : GPC의 개인정보 이용목적 및 보관기관에 따름
                                        </p>
                                        <p class="sub-tit f-caption1">9. 정보주체의 권리·의무 및 그 행사방법에 관한 사항</p>
                                        <p class="txt f-caption2">이용자는 언제든지 재단이 보유하는 개인정보의 이용내역, 수집 · 이용에 대한 동의 내역을 열람하거나 정정할 수 있습니다. 해당 개인정보에 오류로 인해 정정·삭제를 할 필요가 있다고 인정되는 경우에는 재단은 지체 없이 이를 수정합니다. 동의내역의 열람·정정·삭제 요청은 ‘회원정보수정’ 및 가입해지(동의철회)를 위해서는 “회원탈퇴”를 클릭하여 본인 확인 절차를 거치신 후 직접 열람, 정정 또는 탈퇴가 가능합니다. 또는 개인정보보호담당자에게 서면, 전화, 이메일 등으로 연락하시면 신속하게 필요한 조치를 수행합니다. 재단은 대리인이 방문하여 열람 · 증명을 요구하는 경우에는 적법한 위임을 받았는지 확인할 수 있는 위임장과 대리인의 신분증 등을 제출 받아 정확히 대리인 여부를 확인합니다.</p>
                                        <p class="sub-tit f-caption1">10. 회원, 법정대리인의 권리와 의무 및 그 행사방법</p>
                                        <p class="txt f-caption2">
                                            1) 회원은 재단에 대하여 언제든지 개인정보 수집 · 이용 · 제공 등의 동의를 철회(가입해지)할 수 있으며 개인 정보 열람, 정정, 삭제, 처리정지 요구 등의 권리를 행사할 수 있습니다.
                                            <br/>2) 고객 또는 법정대리인은 위와 같은 권리 행사를 온라인에서는 재단 홈페이지에 접속하여 본인 확인 절차를 거친 후 개인정보관리 메뉴에서 하실 수 있고, 서면, 전화 또는 이메일 등을 통하여 고객센터 또는 재단 개인정보보호 책임자 및 담당자에게 연락하는 방법으로 하실 수 있으며, 재단은 이에 대해 지체없이 조치 하겠습니다.
                                            <br/>3) 제1, 2항에 따른 권리 행사는 고객님의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니 다. 이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.
                                            <br/>4) 개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제4항, 제37조제2항에 의하여 회원님의 권리가 제한될 수 있습니다.
                                            <br/>5) 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.
                                            <br/>6) 재단은 정보주체 권리에 따른 열람의 요구, 정정, 삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.
                                            <br/>7) 회원이 개인정보 오류정정을 요구하신 경우, 회사는 오류정정을 완료하기 전까지 당해 개인정보를 이용 · 제공하지 않으며, 이미 제3자에게 당해 개인정보를 제공한 경우에는 제3자에게 지체없이 통지하여 오류 정정 이 이루어지도록 하고 있습니다.
                                            <br/>8) 회원 또는 법정 대리인이 동의철회(가입해지)한 경우, 회사는 지체없이 파기하는 것을 원칙으로 하나 관계 법령에서 의무적으로 보유하도록 한 경우에는 개인정보 처리방침 '개인정보의 보유 및 이용기간'에 따라 처리하고, 반드시 필요한 경우에만 열람 또는 이용이 가능하도록 조치하고 있습니다.
                                        </p>
                                        <p class="sub-tit f-caption1">11. 개인정보의 기술적·관리적 보호조치에 관한 사항</p>
                                        <p class="txt f-caption2">재단은 이용자의 개인정보를 처리함에 있어 개인정보가 분실, 도난, 누출, 변조 또는 훼손되지 않도록 안전성 확보를 위하여 다음과 같은 기술적·관리적 대책을 강구하고 있습니다.</p>
                                        <p class="txt f-caption2">
                                            1) 비밀번호 암호화
                                            <br/>이용자의 비밀번호는 암호화 저장 관리되고 있습니다.
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 해킹 등에 대비한 대책
                                            <br/>재단은 해킹이나 컴퓨터 바이러스 등에 의해 협력업체의 개인정보가 유출되거나 훼손되는 것을 막기 위해 최선을 다하고 있으며 안전한 암호알고리즘을 이용하여 네트워크상 개인정보를 안전하게 전송하는 보안장치(SSL 등)를 적용하고 있습니다. 그리고 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있으며, 기타 시스템 적으로 안전성을 확보하기 위한 가능한 모든 기술적 장치를 갖추려 노력하고 있습니다
                                        </p>
                                        <p class="txt f-caption2">
                                            3) 개인정보처리자의 최소화 운영 및 교육
                                            <br/>재단은 개인정보처리자를 업무 담당자에 한정하여 운영하고 이를 위한 별도의 계정을 부여하고 있습니다. 또한 개인정보보호의 인식 제고를 위하여 정기적인 교육을 수행하고 있습니다.
                                        </p>
                                        <p class="sub-tit f-caption1">12. 회원의 권익침해에 대한 구제방법</p>
                                        <p class="txt f-caption2">고객께서는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다. 아래의 기관은 회사와 별개의 기관으로서, 회사의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다</p>
                                        <p class="txt f-caption2">
                                            1) 개인정보 침해신고센터 (한국인터넷진흥원 운영)
                                            <br/>- 소관업무 : 개인정보 침해사실 신고, 상담 신청
                                            <br/>- 홈페이지 : privacy.kisa.or.kr
                                            <br/>- 전화 : (국번없이) 118
                                        </p>
                                        <p class="txt f-caption2">
                                            2) 개인정보 침해신고센터 (한국인터넷진흥원 운영)
                                            <br/>- 소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정 (민사적 해결)
                                            <br/> - 홈페이지 : www.kopico.go.kr
                                            <br/> - 전화 : (국번없이) 1833-6972
                                        </p>
                                        <p class="txt f-caption2">
                                            3) 대검찰청
                                            <br/> - 홈페이지 : www.spo.go.kr
                                            <br/> - 전화 : (국번없이) 1301
                                        </p>
                                        <p class="txt f-caption2">
                                            3) 경찰청
                                            <br/> - 홈페이지 : ecrm.cyber.go.kr
                                            <br/> - 전화 : (국번없이) 182
                                        </p>
                                        <p class="sub-tit f-caption1">13. 개인정보 보호책임자 및 담당자, 업무처리 부서 구제방법</p>
                                        <p class="txt f-caption2">
                                            1) 재단은 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 고객의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
                                            <br/>- 개인정보보호 책임자 : 재단 오윤석 단장
                                            <br/>- 개인정보보호 담당자 : 재단 경영지원실 한강수 선임 (02-3271-3967, hagas@kapkorea.org)
                                        </p>
                                        <p class="txt f-caption2">2) 회원께서는 재단의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 재단은 회원님의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.</p>
                                        <p class="txt f-caption2">※ 다만 개인정보 보호 관련 문의, 불만 및 피해관련 내용 이외의 내용으로 발송하시는 이메일은 답변 및 처리가 어려우며, 당자의 동의 없이 발송하는 영리목적의 광고성 이메일에 대해서는 「정보통신망 이용촉진 및 정보보호 등에 관한 법률」 제50조부터 제50조의8의 규정에 따라 관계기관에 신고 등의 조치가 이루어 질 수 있습니다.</p>
                                        <p class="sub-tit f-caption1">14. 개인정보 처리방침의 변경에 관한 사항</p>
                                        <p class="txt f-caption2">재단은 본 개인정보처리방침을 변경하는 경우 그 이유 및 변경내용을 홈페이지 첫 화면의 공지사항란 또는 별도의 창을 통하는 등의 방법으로 사전에 공지한 후 변경 및 적용하고 있습니다.</p>
                                        <p class="txt f-caption2">귀하는 개인정보 취급에 대한 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 재단 홈페이지 회원 가입 및 이용이 제한됩니다</p>
                                        <p class="txt f-caption2">상기 안내사항에 모두 동의하고 회원가입 절차를 계속 진행하시려면 [동의 및 가입]을 그렇지 않으면 [동의하지 않음]을 누르세요</p>
                                    </div>
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