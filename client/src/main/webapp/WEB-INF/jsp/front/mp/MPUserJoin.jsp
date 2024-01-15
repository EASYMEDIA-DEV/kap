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
    String memCd = request.getParameter("param1");
    String hpNo = request.getParameter("mobileno");
    

    String bsnmNo = request.getParameter("bsnmNo") ;       //사업자등록번호
    String bsnmNm = request.getParameter("cmpn_nm_new");  //부품사명
    String rprsntNm = request.getParameter("rprsnt_nm"); //대표자명
    String ctgryCd = request.getParameter("ctgryCd");   //구분코드
    String sizeCd = request.getParameter("sizeCd");   //규모코드
    String stbsmDt = request.getParameter("stbsmDt");   //설립일자
    String cmpnTel = request.getParameter("telNo");   //전화번호
    String cmpnZipcode = request.getParameter("zipcode");   //우편번호
    String cmpnBscAddr = request.getParameter("bscAddr");   //기본주소
    String cmpnDtlAddr = request.getParameter("dtlAddr");   //상세주소
    String slsPmt = request.getParameter("slsPmt");   //매출액
    String slsYear = request.getParameter("slsYear");   //매출연도
    String mpleCnt = request.getParameter("mpleCnt");   //직원수
    String mjrPrdct1 = request.getParameter("mjrPrdct1");   //주상품1
    String mjrPrdct2 = request.getParameter("mjrPrdct2");   //주상품2
    String mjrPrdct3 = request.getParameter("mjrPrdct3");   //주상품3
    String qlty5StarCd = request.getParameter("qlty5StarCd");   //품질5스타코드
    String qlty5StarYear = request.getParameter("qlty5StarYear");   //품질5스타년도
    String pay5StarCd = request.getParameter("pay5StarCd");   //납입5스타코드
    String pay5StarYear = request.getParameter("pay5StarYear");   //납입5스타년도
    String tchlg5StarCd = request.getParameter("tchlg5StarCd");   //기술5스타코드
    String tchlg5StarYear = request.getParameter("tchlg5StarYear");   //기술5스타년도
    String sqInfoList1 = request.getParameter("sqInfoList1");   //sq 리스트1
    String sqInfoList2 = request.getParameter("sqInfoList2");   //sq 리스트2
    String sqInfoList3 = request.getParameter("sqInfoList3");   //sq 리스트3
    String bsnmChk = request.getParameter("bsnmChk");


%>

<%!
    // 함수 정의
    public String nullChk(String str) {
        if(str == null || str.equals("null")) {
            return "";
        }
        return str;
    }
%>

<div id="wrap" class="member"  data-controller="controller/mp/MPUserController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
  <form name="formSuccess" id="formSuccess" method="post" action="/member/join-success">
      <input type="hidden" id="joinId" name="id"  />
      <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

  </form>
    <form name="formUserSubmit" id="formUserSubmit"  method="post"  >
        <input type="hidden" id="name" name="mpaUserDto.name" value="<%=name%>" />
        <input type="hidden" id="name" name="name" value="<%=name%>" />
        <input type="hidden" id="birth" name="mpaUserDto.birth" value="<%=birthdate%>" />
        <input type="hidden" id="gender" name="mpaUserDto.gender" value="<%=gender%>" />
        <input type="hidden" id="gndr" name="mpaUserDto.gndr" value="<%=genderCd%>" />
        <input type="hidden" id="ci" name="mpaUserDto.ci" value="<%=ci%>" />
        <input type="hidden" id="trmsAgmntYn" name="mpaUserDto.trmsAgmntYn" value="<%=trmsAgmntYn%>" />
        <input type="hidden" id="psnifAgmntYn" name="mpaUserDto.psnifAgmntYn" value="<%=psnifAgmntYn%>" />
        <input type="hidden" id="psnif3AgmntYn" name="mpaUserDto.psnif3AgmntYn" value="<%=psnif3AgmntYn%>" />
        <input type="hidden" id="fndnNtfyRcvYn" name="mpaUserDto.fndnNtfyRcvYn" value="<%=fndnNtfyRcvYn%>" />
        <input type="hidden" id="ntfySmsRcvYn" name="mpaUserDto.ntfySmsRcvYn" value="<%=ntfySmsRcvYn%>" />
        <input type="hidden" id="ntfyEmailRcvYn" name="mpaUserDto.ntfyEmailRcvYn" value="<%=ntfyEmailRcvYn%>" />
        <input type="hidden" id="hpNo" name="mpaUserDto.hpNo" value="<%=hpNo%>" />
        <input type="hidden" id="memCd" name="mpaUserDto.memCd" value="<%=memCd%>" />
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" id="email" name="mpaUserDto.email" class="notRequired" />
        <input type="hidden" id="email-auth" name="email" class="notRequired" />

        <!-- 부품사 관련 -->
        <input type="hidden" id="bsnmNo" name="bsnmNo" value="<%=nullChk(bsnmNo)%>" class="notRequired"/>
        <input type="hidden" id="cmpnNm" name="mpePartsCompanyDTO.cmpnNm" value="<%=nullChk(bsnmNm)%>" class="notRequired"/>
        <input type="hidden" id="rprsntNm" name="mpePartsCompanyDTO.rprsntNm" value="<%=nullChk(rprsntNm)%>" class="notRequired"/>
        <input type="hidden" id="cmpnTel" name="mpePartsCompanyDTO.telNo" value="<%=nullChk(cmpnTel)%>" class="notRequired"/>
        <input type="hidden" id="cmpnZipcode" name="mpePartsCompanyDTO.zipcode" value="<%=nullChk(cmpnZipcode)%>" class="notRequired"/>
        <input type="hidden" id="cmpnBscAddr" name="mpePartsCompanyDTO.bscAddr" value="<%=nullChk(cmpnBscAddr)%>" class="notRequired"/>
        <input type="hidden" id="cmpnDtlAddr" name="mpePartsCompanyDTO.dtlAddr" value="<%=nullChk(cmpnDtlAddr)%>" class="notRequired"/>
        <input type="hidden" id="bsnmChk" name="bsnmChk" value="<%=nullChk(bsnmChk)%>" class="notRequired"/>
        <input type="hidden" id="ctgryCd" name="mpePartsCompanyDTO.ctgryCd" value="<%=nullChk(ctgryCd)%>" class="notRequired"/>
        <input type="hidden" id="sizeCd" name="mpePartsCompanyDTO.sizeCd" value="<%=nullChk(sizeCd)%>" class="notRequired"/>
        <input type="hidden" id="stbsmDt" name="mpePartsCompanyDTO.stbsmDt" value="<%=nullChk(stbsmDt)%>" class="notRequired"/>
        <input type="hidden" id="slsPmt" name="mpePartsCompanyDTO.slsPmt" value="<%=nullChk(slsPmt)%>" class="notRequired"/>
        <input type="hidden" id="slsYear" name="mpePartsCompanyDTO.slsYear" value="<%=nullChk(slsYear)%>" class="notRequired"/>
        <input type="hidden" id="mpleCnt" name="mpePartsCompanyDTO.mpleCnt" value="<%=nullChk(mpleCnt)%>" class="notRequired"/>
        <input type="hidden" id="mjrPrdct1" name="mpePartsCompanyDTO.mjrPrdct1" value="<%=nullChk(mjrPrdct1)%>" class="notRequired"/>
        <input type="hidden" id="mjrPrdct2" name="mpePartsCompanyDTO.mjrPrdct2" value="<%=nullChk(mjrPrdct2)%>" class="notRequired"/>
        <input type="hidden" id="mjrPrdct3" name="mpePartsCompanyDTO.mjrPrdct3" value="<%=nullChk(mjrPrdct3)%>" class="notRequired"/>
        <input type="hidden" id="qlty5StarCd" name="mpePartsCompanyDTO.qlty5StarCd" value="<%=nullChk(qlty5StarCd)%>" class="notRequired"/>
        <input type="hidden" id="qlty5StarYear" name="mpePartsCompanyDTO.qlty5StarYear" value="<%=nullChk(qlty5StarYear)%>" class="notRequired"/>
        <input type="hidden" id="pay5StarCd" name="mpePartsCompanyDTO.pay5StarCd" value="<%=nullChk(pay5StarCd)%>" class="notRequired"/>
        <input type="hidden" id="pay5StarYear" name="mpePartsCompanyDTO.pay5StarYear" value="<%=nullChk(pay5StarYear)%>" class="notRequired"/>
        <input type="hidden" id="tchlg5StarCd" name="mpePartsCompanyDTO.tchlg5StarCd" value="<%=nullChk(tchlg5StarCd)%>" class="notRequired"/>
        <input type="hidden" id="tchlg5StarYear" name="mpePartsCompanyDTO.tchlg5StarYear" value="<%=nullChk(tchlg5StarYear)%>" class="notRequired"/>
        <input type="hidden" id="sqInfoList1" name="mpePartsCompanyDTO.sqInfoList1" value="<%=nullChk(sqInfoList1)%>" class="notRequired"/>
        <input type="hidden" id="sqInfoList2" name="mpePartsCompanyDTO.sqInfoList2" value="<%=nullChk(sqInfoList2)%>" class="notRequired"/>
        <input type="hidden" id="sqInfoList3" name="mpePartsCompanyDTO.sqInfoList3" value="<%=nullChk(sqInfoList3)%>" class="notRequired"/>
        <!-- 부품사 관련 끝-->
        <c:set var="memCd" value="<%= memCd %>" />
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
                    <c:if test="${memCd eq 'CO'}">
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
                    </c:if>
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
                                                    <input type="text" placeholder="아이디 입력"  id="id" name="mpaUserDto.id" title="아이디" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');">
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
                                                    <input type="password" placeholder="비밀번호 입력" class="pwd" id="pwd" name="mpaUserDto.pwd" title="비밀번호" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');" maxlength="16" autocomplete="off">
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
                                                    <input type="password" placeholder="비밀번호 확인" title="비밀번호 확인" class="pwdSetConfirm" id="passwordConfirm" name="passwordConfirm" maxlength="16" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');">
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
                                                                <input type="text" placeholder="인증번호 입력"  name="emailAuthNum" id="emailAuthNum" oninput="this.value=this.value.replace(/[^0-9]/g, '')" class="notRequired" maxlength="6">
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
                                                        <input type="text" class="notRequired" placeholder="일반 전화번호 입력" id="telNo" name="mpaUserDto.telNo" oninput="this.value=this.value.replace(/[^0-9]/g, '')" >
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
                                                                <input type="text" placeholder="우편번호" readonly id="zipcode" title="우편번호" name="mpaUserDto.zipcode" >
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="기본주소" readonly id="bscAddr" name="mpaUserDto.bscAddr" title="기본주소" >
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-solid small gray-bg" type="button" id="searchPostCode"><span>우편번호 찾기</span></button>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longer">
                                                                <input type="text" placeholder="상세주소 입력" id="dtlAddr" name="mpaUserDto.dtlAddr" title="상세주소" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${memCd eq 'CP'}">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select id="deptCd" name="mpaUserDto.deptCd" title="부서" class="notRequired">
                                                            <option value="" selected>선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status" >
                                                                <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                                                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-input">
                                                        <input type="text" id="deptDtlNm" title="부서상세" name="mpaUserDto.deptDtlNm" placeholder="부서 상세 입력" class="notRequired" >
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${memCd eq 'CP'}">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-select">
                                                        <select id="pstnCd" name="mpaUserDto.pstnCd" title="직급" class="notRequired" title="직급" >
                                                            <option value="" selected>선택</option>
                                                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                                                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-input pstnNmDis" style="display: none;" >
                                                        <input type="text" placeholder="기타 직급 입력" class="pstnNm notRequired" title="기타" name="mpaUserDto.pstnNm">
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


                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg btnBack" href="javascript:"><span>이전</span></a>
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