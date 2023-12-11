<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String birthdate = request.getParameter("birthdate");
    String gender = request.getParameter("gndr").equals("1")? "남" : "여";
    String genderCd = request.getParameter("gndr");
    String ci = request.getParameter("ci");
    String agree = request.getParameter("agree");
    String trmsAgmntYn = request.getParameter("trmsAgmntYn");
    String psnifAgmntYn = request.getParameter("psnifAgmntYn");
    String psnif3AgmntYn = request.getParameter("psnif3AgmntYn");
    String fndnNtfyRcvYn = request.getParameter("fndnNtfyRcvYn");
    String ntfySmsRcvYn = request.getParameter("ntfySmsRcvYn");
    String ntfyEmailRcvYn = request.getParameter("ntfyEmailRcvYn");
//    String hpNo = request.getParameter("mobile_no");
    //TODO 휴대폰 번호 아직 안넘와서 임시로 세팅
    String hpNo = "010-1234-5678";

%>



<div id="wrap" class="member"  data-controller="controller/mp/MPUserController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
  <form name="formSuccess" id="formSuccess" method="post" action="/member/join-success">
      <input type="hidden" id="regDtm" name="regDtm"  />
      <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

  </form>
    <form name="formUserSubmit" id="formUserSubmit"  method="post"  >
        <input type="hidden" id="name" name="name" value="<%=name%>" />
        <input type="hidden" id="birth" name="birth" value="<%=birthdate%>" />
        <input type="hidden" id="gender" name="gender" value="<%=gender%>" />
        <input type="hidden" id="gndr" name="gndr" value="<%=genderCd%>" />
        <input type="hidden" id="ci" name="ci" value="<%=ci%>" />
<%--        <input type="hidden" id="agree" name="agree" value="<%=agree%>" />--%>
        <input type="hidden" id="trmsAgmntYn" name="trmsAgmntYn" value="<%=trmsAgmntYn%>" />
        <input type="hidden" id="psnifAgmntYn" name="psnifAgmntYn" value="<%=psnifAgmntYn%>" />
        <input type="hidden" id="psnif3AgmntYn" name="psnif3AgmntYn" value="<%=psnif3AgmntYn%>" />
        <input type="hidden" id="fndnNtfyRcvYn" name="fndnNtfyRcvYn" value="<%=fndnNtfyRcvYn%>" />
        <input type="hidden" id="ntfySmsRcvYn" name="ntfySmsRcvYn" value="<%=ntfySmsRcvYn%>" />
        <input type="hidden" id="ntfyEmailRcvYn" name="ntfyEmailRcvYn" value="<%=ntfyEmailRcvYn%>" />
        <input type="hidden" id="hpNo" name="hpNo" value="<%=hpNo%>" />
        <input type="hidden" id="memCd" name="memCd" value="CO" />
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" id="email" name="email" class="notRequired" />
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
                    <div class="step-list completed">
                        <p class="step-num">2</p>
                        <p class="step-con">약관동의</p>
                    </div>
                    <div class="step-list ongoing">
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
                <div class="cont-sec-w">
                    <div class="cont-sec">
                        <div class="sec-tit-area">
                            <p class="f-title3">회원 기본 정보</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="gray-bg-sec middle-pad dark-gray-bg">
                                <div class="list-txt-w">
                                    <div class="list-txt">
                                        <p class="tit">이름</p>
                                        <p class="txt"><%=name%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">생년월일</p>
                                        <p class="txt"><%=birthdate%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">성별</p>
                                        <p class="txt"><%=gender%></p>
                                    </div>
                                    <div class="list-txt">
                                        <p class="tit">휴대폰번호</p>
                                        <p class="txt"><%=hpNo%></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec">
                        <div class="sec-tit-area">
                            <p class="f-title3">회원 정보 입력</p>
                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">아이디<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="for-status-chk for-status-chk-id"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                            <div class="form-group">
                                                <div class="form-input">
                                                    <input type="text" placeholder="아이디 입력" class="idChk" id="id" name="id" title="아이디" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');">
                                                </div>
                                                <div class="btn-wrap">
                                                    <button class="btn-solid small gray-bg" type="button" id="dupId"><span>중복확인</span></button>
                                                </div>
                                            </div>
                                            <p class="satisfy-msg">사용 가능한 아이디입니다.</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">비밀번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="form-group">
                                            <div class="for-status-chk for-status-chk1"><!-- 조건 충족 시 error 클래스 삭제 -->
                                                <div class="form-input w-longer">
                                                    <input type="password" placeholder="비밀번호 입력" class="pwd" id="pwd" name="pwd" title="비밀번호" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');" maxlength="16" autocomplete="off">
                                                </div>
                                                <p class="error-msg">8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.</p>
                                            </div>
                                        </div>
                                        <div class="noti-txt-w">
                                            <p class="bullet-noti-txt f-caption2">* 8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">비밀번호 확인<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="form-group">
                                            <div class="for-status-chk for-status-chk2"><!-- 조건 충족 시 error 클래스 삭제 -->
                                                <div class="form-input w-longer">
                                                    <input type="password" placeholder="비밀번호 확인" class="pwdSetConfirm" id="passwordConfirm" name="passwordConfirm" maxlength="16" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');">
                                                </div>
                                                <p class="error-msg">비밀번호가 일치하지 않습니다.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">이메일<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <div class="form-group form-email">
                                                        <div class="form-input">
                                                            <input  id="email-first" class="emailChks" name="email-first" title="이메일"  type="text" placeholder="이메일 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input id="emailAddr" name="emailAddr" class="emailAddrChks" title="이메일 주소" type="text" placeholder="직접입력" maxlength="256">
                                                        </div>
                                                        <div class="form-select">
                                                            <select id="emailSelect" title="메일 선택" class="notRequired">
                                                                <option value="" selected>직접입력</option>
                                                            </select>
                                                        </div>
                                                        <div class="btn-wrap">
                                                            <button class="btn-solid small gray-bg" type="button" id="emailAuth"><span class="authName">인증</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="inner-line">
                                                    <div class="for-status-chk for-status-chk-email"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                        <div class="form-group">
                                                            <div class="form-input w-longer">
                                                                <input type="text" placeholder="인증번호 입력"  name="emailAuthNum" id="emailAuthNum" oninput="this.value=this.value.replace(/[^0-9]/g, '')" class="notRequired">
                                                                <p class="unit-txt timer" id="timer" >유효시간 <span ></span></p>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-solid small gray-bg" type="button" id="emailAuthChk"><span>인증</span></button>
                                                            </div>
                                                        </div>
                                                        <p class="satisfy-msg">인증되었습니다.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">일반 전화번호</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" class="notRequired" placeholder="일반 전화번호 입력" id="telNo" name="telNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" >
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">주소<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <div class="form-address">
                                                        <div class="form-group">
                                                            <div class="form-input w-shortest">
                                                                <input type="text" placeholder="우편번호" readonly id="zipcode" title="우편번호" name="zipcode">
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="기본주소" readonly id="bscAddr" name="bscAddr" title="기본주소">
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longer">
                                                                <input type="text" placeholder="상세주소 입력" id="dtlAddr" name="dtlAddr" title="상세주소">
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


                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <button class="btn-solid small gray-bg btnBack" ><span>이전</span></button>
                        </div>
                        <div class="btn-set">
                            <button class="btn-solid small black-bg" type="submit"><span>회원가입</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
    </form>
</div>