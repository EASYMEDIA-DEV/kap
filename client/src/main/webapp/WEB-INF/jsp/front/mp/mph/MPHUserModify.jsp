<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<jsp:include page="/WEB-INF/jsp/front/mp/mph/MPHPartsModifySrchLayer.jsp"  />
<script type="text/javascript">


</script>
<div id="wrap" class="mypage" data-controller="controller/mp/mph/MPHCertificationController " ><!-- 마이페이지 mypage 클래스 추가 -->

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
            <input type="hidden" name="mpaUserDto.memCdOld" id="formMemCdOld" class="notRequired" value="${rtnDtl.memCd}"/>
            <input type="hidden" name="mpaUserDto.fndnNtfyRcvYn" id="formFndnNtfyRcvYn" class="notRequired" value="${rtnDtl.fndnNtfyRcvYn}"/>
            <input type="hidden" name="mpaUserDto.hpNo" id="formHpNo" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.deptCd" id="formDeptCd" class="notRequired deptCd" value="${rtnDtl.deptCd}"/>
            <input type="hidden" name="mpaUserDto.deptDtlNm" id="formDeptDtlNm" class="notRequired deptDtlNm" value="${rtnDtl.deptDtlNm}"/>
            <input type="hidden" name="mpaUserDto.pstnNm" id="formpstnNm" class="notRequired pstnNm" value="${rtnDtl.pstnNm}"/>
            <input type="hidden" name="mpaUserDto.pstnCd" id="formPstnCd" class="notRequired pstnCd" value="${rtnDtl.pstnCd}"/>
            <input type="hidden" name="mpaUserDto.ntfyEmailRcvYn" id="formNtfyEmailRcvYn" class="notRequired"/>
            <input type="hidden" name="mpaUserDto.ntfySmsRcvYn" id="formNtfySmsRcvYn" class="notRequired"/>
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="email-auth" name="email" class="notRequired" />
            <input type="hidden" name="ci" id="ci" value="${rtnDtl.ci}"/>
            <input type="hidden" id="bsnmNos" name="bsnmNo"  class="notRequired" value="${rtnDtl.workBsnmNo}"/>
            <input type="hidden" id="bsnmNosOld" name="bsnmNoOld"  class="notRequired" value="${rtnDtl.workBsnmNo}"/>
            <input type="hidden" id="partTypeChg" name="partTypeChg"  class="notRequired" value="no"/>

            <input type="hidden" id="cmpnNm" name="mpePartsCompanyDTO.cmpnNm"  class="notRequired cmpnNm"/>
            <input type="hidden" id="rprsntNm" name="mpePartsCompanyDTO.rprsntNm"  class="notRequired rprsntNm"/>
            <input type="hidden" id="cmpnTel" name="mpePartsCompanyDTO.telNo"  class="notRequired cmpnTel"/>
            <input type="hidden" id="cmpnZipcode" name="mpePartsCompanyDTO.zipcode"  class="notRequired cmpnZipcode"/>
            <input type="hidden" id="cmpnBscAddr" name="mpePartsCompanyDTO.bscAddr"  class="notRequired cmpnBscAddr"/>
            <input type="hidden" id="cmpnDtlAddr" name="mpePartsCompanyDTO.dtlAddr"  class="notRequired cmpnDtlAddr"/>
            <input type="hidden" id="bsnmChk" name="bsnmChk"  class="notRequired bsnmChk"/>
            <input type="hidden" id="ctgryCd" name="mpePartsCompanyDTO.ctgryCd"  class="notRequired ctgryCd"/>
            <input type="hidden" id="sizeCd" name="mpePartsCompanyDTO.sizeCd"  class="notRequired sizeCd"/>
            <input type="hidden"  name="mpePartsCompanyDTO.stbsmDt" class="notRequired stbsmDt"/>
            <input type="hidden"  name="mpePartsCompanyDTO.slsPmt"  class="notRequired slsPmt"/>
            <input type="hidden"  name="mpePartsCompanyDTO.slsYear"  class="notRequired slsYear"/>
            <input type="hidden" name="mpePartsCompanyDTO.mpleCnt"  class="notRequired mpleCnt"/>
            <input type="hidden"  name="mpePartsCompanyDTO.mjrPrdct1"  class="notRequired mjrPrdct1"/>
            <input type="hidden"  name="mpePartsCompanyDTO.mjrPrdct2"  class="notRequired mjrPrdct2"/>
            <input type="hidden"  name="mpePartsCompanyDTO.mjrPrdct3"  class="notRequired mjrPrdct3"/>
            <input type="hidden"  name="mpePartsCompanyDTO.qlty5StarCd"  class="notRequired qlty5StarCd"/>
            <input type="hidden"  name="mpePartsCompanyDTO.qlty5StarYear" class="notRequired qlty5StarYear"/>
            <input type="hidden"  name="mpePartsCompanyDTO.pay5StarCd"  class="notRequired pay5StarCd"/>
            <input type="hidden" name="mpePartsCompanyDTO.pay5StarYear" class="notRequired pay5StarYear"/>
            <input type="hidden"  name="mpePartsCompanyDTO.tchlg5StarCd"  class="notRequired tchlg5StarCd"/>
            <input type="hidden"  name="mpePartsCompanyDTO.tchlg5StarYear"  class="notRequired tchlg5StarYear"/>
            <input type="hidden"  name="mpePartsCompanyDTO.sqInfoList1" class="notRequired sqInfoList1"/>
            <input type="hidden" name="mpePartsCompanyDTO.sqInfoList2"  class="notRequired sqInfoList2"/>
            <input type="hidden"  name="mpePartsCompanyDTO.sqInfoList3"  class="notRequired sqInfoList3"/>



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
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

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

                                        <a class="btn-text-icon diagonal-arrow" id="myRegister" href="javascript:"><span>기본정보 변경</span></a>
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
                                                                        <input type="text" placeholder="우편번호" readonly value="${rtnDtl.zipcode}" id="zipcodeMod" name="mpaUserDto.zipcode" class="zipcode" title="우편번호">
                                                                    </div>
                                                                    <div class="form-input w-longer">
                                                                        <input type="text" placeholder="기본주소" readonly value="${rtnDtl.bscAddr}" id="bscAddrMod" name="mpaUserDto.bscAddr" class="bscAddr" title="기본주소">
                                                                    </div>
                                                                    <div class="btn-wrap">
                                                                        <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <div class="form-input w-longest">
                                                                        <input type="text" placeholder="상세주소 입력" value="${rtnDtl.dtlAddr}" id="dtlAddrMod" name="mpaUserDto.dtlAddr" class="dtlAddr" title="상세주소">
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
                                        <c:if test="${rtnDtl.memCd eq 'CP' }">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select title="부서" class="notRequired deptCd deptCdOld">
                                                                    <option value="" selected>선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status" >
                                                                        <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnDtl.deptCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="부서 상세 입력"  class="notRequired deptDtlNm" value="${rtnDtl.deptDtlNm}" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select   title="직급" class="notRequired pstnCd pstnCdOld" title="직급">
                                                                    <option value="" selected>선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                        <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnDtl.pstnCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input form-display" style="display:none;">
                                                                <input type="text" placeholder="기타 직급 입력"  class="pstnNm notRequired pstnNmOld" value="${rtnDtl.pstnNm}" >
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-con-area">
                                    <div class="btn-wrap">
                                        <div class="btn-set">
                                            <c:if test="${rtnDtl.memCd eq 'CO' }">
                                            <a class="btn-solid small black-bg" id="btnParts" href="javascript:"><span>부품사회원 전환</span></a>
                                            </c:if>
                                            <c:if test="${rtnDtl.memCd eq 'CP' }">
                                            <a class="btn-solid small gray-bg" id="btnParts" href="javascript:"><span>부품사 정보 변경</span></a>
                                            <a class="btn-solid small black-bg" id="btnPartsChg" href="javascript:"><span>소속부품사(이직) 변경</span></a>
                                            </c:if>

                                        </div>
                                        <div class="btn-set">
                                        </div>
                                    </div>

                                    <div class="noti-txt-w">
                                        <p class="bullet-noti-txt f-caption2">* 부품사 회원으로 전환 시 재단에서 주관하는 다양한 사업 및 교육에 참여하실 수 있습니다.</p>
                                    </div>
                                    <div class="gray-bg-sec middle-pad partDtl" id="partDtl" style="display: none">
                                        <div class="list-txt-w">
                                            <div class="list-txt">
                                                <p class="tit">사업자등록번호</p>
                                                <p class="txt bsnmNoNum" >${kl:bsnmNoConvert(rtnDtl.workBsnmNo)}</p>
                                            </div>
                                            <div class="list-txt">
                                                <p class="tit">부품사명</p>
                                                <p class="txt cmpnNm">${rtnDtl.cmpnNm}</p>
                                            </div>
                                            <div class="list-txt">
                                                <p class="tit">대표자명</p>
                                                <p class="txt rprsntNm">${rtnDtl.rprsntNm}</p>
                                            </div>
                                            <div class="list-txt">
                                                <p class="tit">구분</p>
                                                <p class="txt ctgryNm">${rtnDtl.ctgryCdNm}</p>
                                            </div>
                                            <div class="list-txt">
                                                <p class="tit">주소</p>
                                                <p class="txt addrNm">${rtnDtl.cmpnBscAddr}  ${rtnDtl.cmpnDtlAddr}</p>
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
                                <a class="btn-solid small gray-bg" href="javascript:" id="cancelBtn"><span>취소</span></a>
                            </div>
                            <div class="btn-set">
                                <button class="btn-solid small black-bg" id="btnModify"><span>회원정보 변경</span></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </form>

    </div>
    <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
        <input type="hidden" id="m" name="m" value="service" />
        <input type="hidden" id="token_version_id" name="token_version_id" value=""  class="notRequired"/>
        <input type="hidden" id="enc_data" name="enc_data" class="notRequired" />
        <input type="hidden" id="integrity_value" name="integrity_value" class="notRequired" />
    </form>

    <!-- content 영역 end -->
</div>