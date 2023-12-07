<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="member member-main" data-controller="controller/co/COLgnCtrl"><!-- 로그인, 회원가입 페이지 member 클래스 추가 --><!-- 각 인트로 화면은 member-main 클래스 추가 -->
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-large-title">
                        <span class="for-move">로그인</span>
                    </p>
                    <p class="page-txt">
                        <span class="for-move">자동차부품산업진흥재단 사이트 방문을 환영합니다.</span>
                    </p>
                </div>
            </div>

            <div class="member-half-box">
                <div class="left page-init-motion">
                    <form id="frmLogin" name="frmLogin" method="post" action="">
                    <div class="data-enter-form verticality"><!-- verticality: 제목, 내용이 상하 배치 -->
                        <div class="row">
                            <div class="th">
                                <p class="title f-head">아이디</p>
                            </div>
                            <div class="td">
                                <div class="data-line-w">
                                    <div class="data-line">
                                        <div class="form-group">
                                            <div class="form-input id">
                                                <input type="text" placeholder="아이디를 입력해주세요." class="idChk" id="id" name="id" title="아이디" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="th">
                                <p class="title f-head">비밀번호</p>
                            </div>
                            <div class="td">
                                <div class="data-line-w">
                                    <div class="data-line">
                                        <div class="form-group">
                                            <div class="form-input password">
                                                <input type="password" placeholder="비밀번호를 입력해주세요." id="password" name="password" maxlength="16" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-checkbox">
                        <input type="checkbox" id="savdIdChk" class="notRequired" name="idRemember">
                        <label for="savdIdChk">아이디 저장</label>
                    </div>
                    <div class="btn-wrap">
                        <button class="btn-solid small black-bg" type="submit"><span>로그인</span></button>
                    </div>
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <a class="btn-text-icon gray-txt" href="/id-find"><span>아이디 찾기</span></a>
                            <a class="btn-text-icon gray-txt" href="/pwd-find"><span>비밀번호 찾기</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-text-icon" href="javascript:"><span>회원가입</span></a>
                        </div>
                    </div>
                </form>
                </div>
                <div class="right">
                    <div class="img-area">
                        <div class="img">
                            <img src="/common/images/img-login-main.jpg" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
</div>
