<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="mypage" data-controller="controller/mp/mph/MPHCertification"><!-- 마이페이지 mypage 클래스 추가 -->

    <div class="cont-wrap">
        <form name="formUserSubmit" id="formUserSubmit"  method="post"  >
            <input type="hidden" id="formEmail" name="mpaUserDto.email" class="notRequired" />
            <input type="hidden" id="oldEmailName"  value="${rtnDtl.emailName}" />
            <input type="hidden" id="oldEmailAddr" value="${rtnDtl.emailAddr}" />
            <input type="hidden" id="oldEmailRcv" name="mpaUserDto.oldEmailRcv"  value="${rtnDtl.ntfyEmailRcvYn}"  class="notRequired"/>
            <input type="hidden" id="oldSmsRcv" name="mpaUserDto.oldSmsRcv" value="${rtnDtl.ntfySmsRcvYn}" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.name" id="formName" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.birth" id="formBirth" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.gndr" id="formGndr" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.memCd" id="formMemCd" class="notRequired" value="${rtnDtl.memCd}"/>
            <input type="hidden" name="mpaUserDto.fndnNtfyRcvYn" id="formFndnNtfyRcvYn" class="notRequired" value="${rtnDtl.fndnNtfyRcvYn}"/>
            <input type="hidden" name="mpaUserDto.hpNo" id="formHpNo" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.ntfyEmailRcvYn" id="formNtfyEmailRcvYn" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.ntfySmsRcvYn" id="formNtfySmsRcvYn" class="notRequired"/>
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="email-auth" name="email" class="notRequired" />
            <input type="hidden" name="ci" id="ci" value="${rtnDtl.ci}"/>
            <input type="hidden" id="bsnmNo" name="bsnmNo" value="" class="notRequired"/>



            <!-- content 영역 start -->
        <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">

                <p class="page-tit f-xlarge-title"><span class="for-move">회원정보 수정</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <div class="lnb-area">
                <div class="for-motion">
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>교육/세미나 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>컨설팅 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>상생 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>SQ평가원 자격증</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>근태 체크</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>나의 1:1 문의</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu active" href="javascript:"><span>정보 수정</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>회원탈퇴</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                </div>
            </div>

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <div class="left">
                                    <p class="f-title3">회원 기본 정보</p>
                                    <p class="txt f-body2">회원가입 시 등록하신 가입정보가 변경된 경우 해당 항목의 정보를 수정해주시기 바랍니다.</p>
                                </div>
                                <div class="btn-wrap">
                                    <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
                                        <input type="hidden" id="m" name="m" value="service" />
                                        <input type="hidden" id="token_version_id" name="token_version_id" value=""  class="notRequired"/>
                                        <input type="hidden" id="enc_data" name="enc_data" class="notRequired" />
                                        <input type="hidden" id="integrity_value" name="integrity_value" class="notRequired" />
                                    </form>
                                    <button class="btn-text-icon diagonal-arrow" id="myRegister"><span>기본정보 변경</span></button>
                                </div>
                            </div>
                            <div class="sec-con-area">
                                <div class="gray-bg-sec middle-pad">
                                    <div class="list-txt-w">
                                        <div class="list-txt">
                                            <p class="tit">이름</p>
                                            <p class="txt" id="name">${rtnDtl.name}</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">생년월일</p>
                                            <p class="txt" id="birth">${rtnDtl.birth}</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">성별</p>
                                            <p class="txt" id="gndr">${rtnDtl.gndr =='1' ? '남' : '여'}</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">휴대폰번호</p>
                                            <p class="txt" id="hpNo">${rtnDtl.hpNo}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <div class="left">
                                    <p class="f-title3">회원 상세 정보</p><!-- 2023-12-06 텍스트 수정 -->
                                </div>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">아이디</p>
                                        </div>
                                        <div class="td">
                                            <p class="data-txt">${rtnDtl.id}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">비밀번호</p>
                                        </div>
                                        <div class="td">
                                            <div class="form-group">
                                                <div class="for-status-chk for-status-chk1"><!-- 조건 충족 시 error 클래스 삭제 -->
                                                    <div class="form-input w-longer">
                                                        <input type="password" placeholder="새로운 비밀번호 입력" class="password notRequired" name="mpaUserDto.pwd" title="비밀번호" id="pwd">
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
                                            <p class="title f-head">비밀번호 확인</p>
                                        </div>
                                        <div class="td">
                                            <div class="form-group">
                                                <div class="for-status-chk for-status-chk2"><!-- 조건 충족 시 error 클래스 삭제 -->
                                                    <div class="form-input w-longer">
                                                        <input type="password" placeholder="비밀번호 확인" class="password notRequired" name="passwordConfirm" title="비밀번호 확인" id="pwdConfirm">
                                                    </div>
                                                    <p class="error-msg">비밀번호가 일치하지 않습니다.</p>
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
                                                    <div class="middle-line">
                                                        <div class="form-group form-email">
                                                            <div class="form-input">
                                                                <input type="text" placeholder="이메일 입력" value="${rtnDtl.emailName}" id="emailNameNew" name="emailNameNew" title="이메일">
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="직접입력" value="${rtnDtl.emailAddr}"  id="emailAddrNew" name="emailAddrNew" title="이메일 주소">
                                                            </div>
                                                            <div class="form-select">
                                                                <select id="emailSelect" class="notRequired" title="메일 선택">
                                                                    <option value="" selected>직접입력</option>
                                                                </select>
                                                            </div>
                                                            <div class="btn-wrap newEmailAuth" style="display: none">
                                                                <button class="btn-solid small gray-bg" id="emailAuth" type="button"><span class="authName">인증</span></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-line" id="emailAuthDis" style="display: none">
                                                        <div class="for-status-chk for-status-chk-email"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                            <div class="form-group">
                                                                <div class="form-input w-longer">
                                                                    <input type="text" class="notRequired" placeholder="인증번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')">
                                                                    <p class="unit-txt timer" id="timer">유효시간 <span></span></p>
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
                                                            <input type="text" placeholder="일반 전화번호 입력" oninput="this.value=this.value.replace(/[^0-9]/gi,'');" value="${rtnDtl.telNo}" name="mpaUserDto.telNo" title="전화번호" class="notRequired" id="telNoInput">
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
                                                                <div class="form-input">
                                                                    <input type="text" placeholder="우편번호" readonly value="${rtnDtl.zipcode}" id="zipcode" name="mpaUserDto.zipcode" class="zipcode" title="우편번호">
                                                                </div>
                                                                <div class="form-input w-longer">
                                                                    <input type="text" placeholder="기본주소" readonly value="${rtnDtl.bscAddr}" id="bscAddr" name="mpaUserDto.bscAddr" class="bscAddr" title="기본주소">
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <div class="form-input w-longest">
                                                                    <input type="text" placeholder="상세주소 입력" value="${rtnDtl.dtlAddr}" id="dtlAddr" name="mpaUserDto.dtlAddr" class="dtlAddr" title="상세주소">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">SMS 수신여부<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <div class="form-radio">
                                                            <input type="radio" id="smsReceiveRadio1" name="ntfySmsRcvYn" ${rtnDtl.ntfySmsRcvYn.toString() == 'Y' ? 'checked' : ''} value="Y" class="notRequired" title="SMS">
                                                            <label for="smsReceiveRadio1">수신</label>
                                                        </div>
                                                        <div class="form-radio">
                                                            <input type="radio" id="smsReceiveRadio2" name="ntfySmsRcvYn" ${rtnDtl.ntfySmsRcvYn.toString() == 'N' ? 'checked' : ''} value="N" class="notRequired"  title="SMS">
                                                            <label for="smsReceiveRadio2">수신안함</label>
                                                        </div>
                                                    </div>
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* ${ kl:convertDate(rtnDtl.ntfySmsRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } 변경</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">이메일 수신여부<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <div class="form-radio">
                                                            <input type="radio" id="emailReceiveRadio1" name="ntfyEmailRcvYn" ${rtnDtl.ntfyEmailRcvYn.toString() == 'Y' ? 'checked' : ''} value="Y" class="notRequired"  title="이메일">
                                                            <label for="emailReceiveRadio1">수신</label>
                                                        </div>
                                                        <div class="form-radio">
                                                            <input type="radio" id="emailReceiveRadio2" name="ntfyEmailRcvYn" ${rtnDtl.ntfyEmailRcvYn.toString() == 'N' ? 'checked' : ''} value="N" class="notRequired" title="이메일">
                                                            <label for="emailReceiveRadio2">수신안함</label>
                                                        </div>
                                                    </div>
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* ${ kl:convertDate(rtnDtl.ntfyEmailRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } 변경</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-con-area">
                                <div class="btn-wrap">
                                    <div class="btn-set">
                                        <a class="btn-solid small black-bg" href="javascript:"><span>부품사회원 전환</span></a>
                                    </div>
                                    <div class="btn-set">
                                    </div>
                                </div>

                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 부품사 회원으로 전환 시 재단에서 주관하는 다양한 사업 및 교육에 참여하실 수 있습니다.</p>
                                </div>

                                <div class="gray-bg-sec middle-pad" style="display: none">
                                    <div class="list-txt-w">
                                        <div class="list-txt">
                                            <p class="tit">사업자등록번호</p>
                                            <p class="txt">123-45-67890</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">부품사명</p>
                                            <p class="txt">자동차부품산업진흥재단</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">대표자명</p>
                                            <p class="txt">홍길동</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">구분</p>
                                            <p class="txt">1차</p>
                                        </div>
                                        <div class="list-txt">
                                            <p class="tit">본사주소</p>
                                            <p class="txt">(04365) 서울 용산구 원효로 74 현대자동차원효로사옥 114-38</p>
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
                            <button class="btn-solid small black-bg" type="submit"><span>회원정보 변경</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>

    <!-- content 영역 end -->
</div>