<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<script type="text/javascript">
    if('${rtnData}' == '' || '${rtnData}' == null) {
        alert("본인이 일치하지 않습니다.");
        history.back(-1);
    }

</script>

<!-- content 영역 start -->
<div class="cont-wrap" data-controller="controller/co/COLgnCtrl">
    <div class="inner">
        <form name="formPwdChng" id="formPwdChng"  method="post" >
            <input type="hidden" id="name" name="id"  value="${rtnData.id}"/>
            <input type="hidden" id="birthdate" name="memSeq"  value="${rtnData.memSeq}"/>
            <input type="hidden" id="pwdChngType" name="pwdChngType"  value="new"/>
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">비밀번호 찾기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <p class="con-sub-tit f-title2">회원님의 계정 비밀번호를 재설정해주세요.</p>

            <div class="data-enter-form">
                <div class="row">
                    <div class="th">
                        <p class="title f-head">새로운 비밀번호</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="for-status-chk for-status-chk3"><!-- 조건 충족 시 error 클래스 삭제 -->
                                        <div class="form-input w-longer">
                                            <input type="password" placeholder="새로운 비밀번호 입력" class="pwdSet" id="newPassword" name="newPassword" title="신규 비밀번호 확인" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');" maxlength="16" autocomplete="off">
                                        </div>
                                        <p class="error-msg">8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.</p>
                                    </div>
                                </div>
                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 영문/숫자/특수문자 중 3종류 이상을 조합한 8~16자</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="th">
                        <p class="title f-head">비밀번호 확인</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="for-status-chk for-status-chk4"><!-- 조건 충족 시 error 클래스 삭제 -->
                                        <div class="form-input w-longer">
                                            <input type="password" placeholder="비밀번호 확인 입력" class="pwdSetConfirm" id="passwordConfirm" name="passwordConfirm" maxlength="16" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');">
                                        </div>
                                        <p class="error-msg">※ 비밀번호가 일치하지 않습니다.</p>
                                    </div>
                                </div>
                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 영문/숫자/특수문자 중 3종류 이상을 조합한 8~16자</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small gray-bg" href="/login"><span>취소</span></a>
                    </div>
                    <div class="btn-set">
                        <button class="btn-solid small black-bg" type="submit"><span>비밀번호 변경</span></button>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>