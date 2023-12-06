<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<script type="text/javascript">
    if("${rtnData.id}" == null || "${rtnData.id}" == "") {
        alert("존재하지 않는 계정입니다.");
        history.back(-1);
    }


</script>

<div class="cont-wrap" data-controller="controller/co/COLgnCtrl">
    <form id="frmEmail" name="frmEmail" method="post" action="">
    <input type="hidden" name="id" value="${rtnData.id}" />
    <input type="hidden" name="email" value="${rtnData.email}" />
    <input type="hidden" name="name" value="${rtnData.name}" />


    </form>
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">아이디 찾기 결과 안내</span>
                </p>
            </div>
        </div>
        <div class="inner-con-box">
            <p class="con-sub-tit f-title2">입력하신 정보에 대한 아이디 찾기 결과입니다.</p>

            <div class="gray-bg-sec dark-gray-bg t-align-center result">
                <p class="f-title3">${ empty rtnData.id ? '해당하는 아이디가 없습니다.' : kl:idMasking(rtnData.id) }</p>
            </div>
            <c:choose>
                <c:when test="${ not empty rtnData.id }">
                    <div class="noti-txt-w">
                        <p class="bullet-noti-txt f-caption2">* 개인정보 보호를 위해 아이디 뒷자리는 ***로 표시 합니다.</p>
                        <p class="bullet-noti-txt f-caption2">* 전체 아이디 확인은 이메일로 받기 버튼을 통해 회원가입 시 입력한 이메일로 발송 가능합니다</p>
                    </div>
                    <div class="btn-wrap align-left">
                        <div class="btn-set">
                            <button class="btn-text-icon black-circle" id="goToPwd"><span>비밀번호 찾기</span></button>
                            <button class="btn-text-icon black-circle" id="btnEmail"><span>이메일로 받기</span></button>
                        </div>
                    </div>
                </c:when>
            </c:choose>



            <div class="page-bot-btn-sec">
                <div class="btn-wrap align-center">
                    <div class="btn-set">
                        <a class="btn-solid small black-bg" href="/login"><span>로그인</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>