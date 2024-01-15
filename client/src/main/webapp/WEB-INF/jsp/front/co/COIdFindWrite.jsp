<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="member">
<div class="cont-wrap" data-controller="controller/co/COLgnCtrl">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">아이디 찾기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <p class="con-sub-tit f-title2">회원님이 원하시는 방법을 선택하여 아이디를 확인해보세요.</p>

            <div class="tab-con-w">
                <div class="tab-btn-area">
                    <div class="txt-tab-swiper func-tab">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <a class="swiper-slide txt-tab-btn active" href="javascript:">
                                    <p class="txt"><span class="menu-name">휴대폰</span></p>
                                </a>
                                <a class="swiper-slide txt-tab-btn" href="javascript:">
                                    <p class="txt"><span class="menu-name">이메일</span></p>
                                </a>
                                <a class="swiper-slide txt-tab-btn" href="javascript:">
                                    <p class="txt"><span class="menu-name">본인인증</span></p>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-con-area">
                    <form id="frmIdFind" name="frmIdFind" method="post" action="./id-find-res">
                        <div class="tab-con">
                            <input type="hidden" class="notRequired" id="csrfKey2" name="${_csrf.parameterName}" value="${_csrf.token}" />

                        <input type='hidden' value='1' name='types' readonly>
                        <input type="hidden" name="birthdate" value="" id="birthdate-id" class="notRequired" />
                        <div class="data-enter-form">
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">이름</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group">
                                                <div class="form-input">
                                                    <input type="text" id="name" name="name" title="이름" class="nameChk" placeholder="이름 입력">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">생년월일</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group form-birth">
                                                <div class="form-input">
                                                    <input type="text" id="year-id" name="year" title="년도" class="onlyNumber" placeholder="YYYY" value="" maxlength="4">
                                                </div>
                                                <div class="form-input">
                                                    <input type="text" id="month-id" name="month" title="월" class="onlyNumber" placeholder="MM" value="" maxlength="2">
                                                </div>
                                                <div class="form-input">
                                                    <input type="text" id="day-id" name="day" title="일" class="onlyNumber" placeholder="DD" value="" maxlength="2">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">휴대폰번호</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group">
                                                <div class="form-input w-longer">
                                                    <input class="hpNo" id="hpNo" name="mobileno" title="휴대폰 번호" type="text" placeholder="휴대폰번호 입력" maxlength="13">
                                                </div>
                                            </div>
                                            <div class="noti-txt-w">
                                                <p class="bullet-noti-txt f-caption2">* 휴대폰번호를 바꾸신 회원님께서는 과거 휴대폰번호를 입력하여 주시기를 바랍니다.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="page-bot-btn-sec">
                            <div class="btn-wrap align-center">
                                <button class="btn-solid small black-bg" type="submit"><span>아이디 찾기</span></button>
                            </div>
                        </div>

                    </div>
                    </form>

                    <div class="tab-con">
                        <form id="frmIdFind-email" name="frmIdFind-email" method="post" action="./id-find-res">
                            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                            <div class="data-enter-form">
                            <input type="hidden" name="birthdate" value="" id="birthdate-email" class="notRequired"/>
                            <input type="hidden" name="birthdate" value="" id="email" class="notRequired"/>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">이름</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group">
                                                <div class="form-input">
                                                    <input type="text" id="name-email" name="name" title="이름" class="nameChk" placeholder="이름 입력">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">생년월일</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group form-birth">
                                                <div class="form-input">
                                                    <input type="text" id="year-email" name="year" title="년도" class="onlyNumber" placeholder="YYYY" value="" maxlength="4">
                                                </div>
                                                <div class="form-input">
                                                    <input type="text" id="month-email" name="month" title="월" class="onlyNumber" placeholder="MM" value="" maxlength="2">
                                                </div>
                                                <div class="form-input">
                                                    <input type="text" id="day-email" name="day" title="일" class="onlyNumber" placeholder="DD" value="" maxlength="2">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-head">이메일</p>
                                </div>
                                <div class="td">
                                    <div class="data-line-w">
                                        <div class="data-line">
                                            <div class="form-group form-email">
                                                <div class="form-input">
                                                    <input id="email-first" name="email" title="이메일" class="emailChks" type="text" placeholder="이메일 입력" >
                                                </div>
                                                <div class="form-input">
                                                    <input id="emailAddr" name="emailAddr" title="이메일 주소" type="text" placeholder="직접입력" maxlength="256">
                                                </div>
                                                <div class="form-select">
                                                    <select id="emailSelect" class="notRequired" title="메일 선택">
                                                        <option value="" selected>직접입력</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="page-bot-btn-sec">
                            <div class="btn-wrap align-center">
                                <button class="btn-solid small black-bg" type="submit"><span>아이디 찾기</span></button>
                            </div>
                        </div>
                        </form>
                    </div>
                    <div class="tab-con">
                        <div class="gray-bg-sec middle-pad dark-gray-bg t-align-center t-align-center">
                            <div class="phone-certi">
                                <p class="title f-head">휴대폰 본인인증</p>
                                <p class="txt">회원님 명의의 휴대폰을 통해 <br>인증 받으실 수 있습니다.</p>
                            </div>
                        </div>

                        <div class="noti-txt-w">
                            <p class="bullet-noti-txt f-caption2">* 본인 인증 시 입력하시는 정보는 공인된 인증기관에서 직접 수집하며, 인증 이외의 용도로 이용되지 않습니다.</p>
                        </div>

                        <div class="page-bot-btn-sec">
                            <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
                                <input type="hidden" id="m" name="m" value="service" />
                                <input type="hidden" id="token_version_id" name="token_version_id" value="" />
                                <input type="hidden" id="enc_data" name="enc_data" />
                                <input type="hidden" id="integrity_value" name="integrity_value" />
                            </form>
                            <div class="btn-wrap align-center">
                                <button class="btn-solid small black-bg" id="myRegister" ><span>인증하기</span></button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</div>