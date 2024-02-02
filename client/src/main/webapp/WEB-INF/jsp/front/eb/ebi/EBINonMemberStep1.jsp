<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnData" value="${rtnInfo.rtnData}" />
<c:set var="roomDto" value="${rtnInfo.roomDto}" />
<c:set var="isttrList" value="${rtnInfo.isttrList}" />

<c:set var="accsStatusClass" value="" />
<c:choose>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수대기') }">
        <c:set var="accsStatusClass" value="waiting" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수중') }">
        <c:set var="accsStatusClass" value="accepting" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '마감') }">
        <c:set var="accsStatusClass" value="end" />
    </c:when>
</c:choose>

<c:set var="edctnStrtDtm" value="${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />
<c:set var="edctnEndDtm" value="${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />

<div data-controller="controller/eb/ebi/EBINonMemberStepCtrl">
    <form name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.edctnSeq}" />
        <input type="hidden" class="notRequired" id="email" name="email" value="" />
        <input type="hidden" class="notRequired" id="applyDateTime" name="applyDateTime" value="${rtnData.applyDateTime}" />


        <!-- content 영역 start -->
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <!--
              교육 사업: edu-biz
              컨실팅 사업: consult-biz
              상생 사업: coexisting-biz
            -->
            <div class="sub-top-vis-area apply-page consult-biz">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육신청</span></p>
                    <div class="apply-step-w">
                        <div class="for-move">
                            <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                                <p class="step-num">1</p>
                                <p class="step-con">기본정보</p>
                            </div>
                            <div class="step-list ">
                                <p class="step-num">2</p>
                                <p class="step-con">신청완료</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="img-area">
                    <div class="gray-bg"></div>
                    <div class="graphic-item-w">
                        <div class="item"></div>
                        <div class="item"></div>
                    </div>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">과정 정보를 확인해주세요</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="training-confirm">
                                        <div class="top-info">
                                            <div class="training-view-page">
                                                <div class="training-list">
                                                    <div class="img-area">
                                                        <img src="${ not empty rtnData.webPath ? rtnData.webPath : '/common/images/img-main-training-offline-01.jpg' }" alt="">
                                                    </div>
                                                    <div class="txt-area">
                                                        <div class="top-line">
                                                            <div class="sort-label-area">
                                                                <p class="label"><span>${ rtnData.prntCdNm }</span></p>
                                                                <p class="label"><span>${ rtnData.ctgryCdNm }</span></p>
                                                            </div>
                                                            <p class="training-name f-title3">${ rtnData.nm }</p><!-- 2024-01-19 폰트 클래스 변경 -->
                                                            <p class="training-explain-txt">${ rtnData.smmryNm }</p>
                                                        </div>
                                                        <div class="class-property-w">
                                                            <div class="property-list non-member"><!-- non-member: 비회원 가능 --><!-- 2024-01-29 non-member 클래스 변경 -->
                                                                <p class="txt">
                                                                    <span>비회원 신청 가능</span><!-- 2024-01-29 문구 변경 -->
                                                                </p>
                                                            </div>
                                                            <div class="property-list offline"><!-- offline: 집체교육 -->
                                                                <p class="txt">
                                                                    <span>집체교육</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list time"><!-- time: 학습시간 -->
                                                                <p class="txt">
                                                                    <span>${ rtnData.stduyDdCdNm }일(${ rtnData.stduyTimeCdNm }시간)</span>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="bot-info">
                                            <div class="index-list-w">
                                                <div class="list-item">
                                                    <div class="cont">
                                                        <div class="cont-area">
                                                            <div class="info-list-w">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">강사</p>
                                                                    <c:forEach var="isttr" items="${isttrList}" varStatus="status">
                                                                        <p class="txt f-body2">${ isttr.name }</p>
                                                                    </c:forEach>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">정원</p>
                                                                    <p class="txt f-body2">${ rtnData.fxnumImpsbYn eq 'N' ? '제한없음' : rtnData.fxnumCnt +='명' }(선착순 마감)</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육장소</p>
                                                                    <p class="txt f-body2"><a href="javascript:" title="교육장 안내 팝업 열기" id="mapBtn" data-mapchk="N" data-nm="${ roomDto.nm }" data-rprsnt-tel-no="${ roomDto.rprsntTelNo }" data-zipcode="${ roomDto.zipcode }" data-bsc-addr="${ roomDto.bscAddr }" data-dtl-addr="${ roomDto.dtlAddr }">${ roomDto.nm }</a></p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육일자</p>
                                                                    <p class="txt f-body2">${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ <br/>${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } (${ kl:getDaysDiff(edctnStrtDtm, edctnEndDtm) +1 }일간)</p>
                                                                </div>
                                                            </div>
                                                            <!-- 2024-01-05 구조 변경 및 클래스 삭제 -->
                                                            <div class="btn-wrap">
                                                                <c:if test="${ not empty rtnData.edctnNtctnFileSeq }">
                                                                <a class="btn-text-icon download" href="/file/download?fileSeq=${ rtnData.edctnNtctnFileSeq }&fileOrd=0" download=""><span>안내문</span></a>
                                                                </c:if>
                                                                <button class="btn-text-icon black-arrow" type="button" id="btnPicLayer" data-pic-nm="${ rtnData.picNm }" data-pic-email="${ rtnData.picEmail }" data-pic-tel-no="${ rtnData.picTelNo }"><span>담당자 문의</span></button>
                                                            </div>
                                                            <!-- // 2024-01-05 구조 변경 및 클래스 삭제 -->
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
                                <div class="sec-tit-area">
                                    <p class="f-title3">신청자 정보를 입력해주세요.
                                    </p>
                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">사업자등록번호<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="for-status-chk"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                            <div class="form-group">
                                                                <div class="form-input">
                                                                    <input type="text" placeholder="숫자만 입력해 주세요" id="ptcptBsnmNo" name="ptcptBsnmNo" value="${rtnDto.ptcptBsnmNo}" title="사업자등록번호" maxlength="10">
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <button class="btn-solid small gray-bg" id="btnCmpnChk" type="button"><span>인증</span></button>
                                                                </div>
                                                            </div>
                                                            <p class="satisfy-msg">인증되었습니다.</p>
                                                            <input type="hidden" class="notRequired" id="authCheck" name="authCheck" value="" disabled />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">구분<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="text" placeholder="인증된 업체명 (인증 시 자동 입력)" id="ptcptCmpnNm" name="ptcptCmpnNm" title="부품사명" maxlength="50" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">이름<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="text" placeholder="이름 입력" id="name" name="name" title="이름" maxlength="50" oninput="this.value = this.value.replace(/[^a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]/g, '');">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select data-name="deptCd" id="deptCd" name="deptCd" title="부서">
                                                                    <option value="" selected="">선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD02') and fn:length(cdList.cd) > 8}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnData.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" placeholder="부서 상세 입력" id="deptDtlNm" name="deptDtlNm" title="부서 상세" maxlength="50">
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
                                                                <select data-name="pstnCd" id="pstnCd" name="pstnCd" title="직급">
                                                                    <option value="" selected="">선택</option>
                                                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD01') and fn:length(cdList.cd) > 8}">
                                                                            <option value="${cdList.cd}" <c:if test="${rtnData.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-input" id="pstnNmChk" style="display: none;">
                                                                <input type="text" placeholder="직급 상세 입력" id="pstnNm" name="pstnNm" title="직급 상세" maxlength="50">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">휴대폰번호<span class="essential-mark color-sky">*</span></p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-input">
                                                                <input type="text" placeholder="휴대폰번호 입력" id="hpNo" name="hpNo" title="휴대폰번호" maxlength="13">
                                                            </div>
                                                        </div>
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
                                                        <div class="form-group form-email">
                                                            <div class="form-input">
                                                                <input type="text" name="emailSrt" id="emailSrt" title="이메일" placeholder="이메일 입력" maxlength="50">
                                                            </div>
                                                            <div class="form-input">
                                                                <input type="text" name="emailEnd" id="emailEnd" title="이메일" placeholder="직접입력" maxlength="50">
                                                            </div>
                                                            <div class="form-select">
                                                                <select id="emailEndSelect" title="메일 선택">
                                                                    <option value="" selected>직접입력</option>
                                                                    <option value="naver.com">naver.com</option>
                                                                    <option value="gmail.com">gmail.com</option>
                                                                    <option value="nate.com">nate.com</option>
                                                                    <option value="daum.net">daum.net</option>
                                                                </select>
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
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">개인정보 사용동의</p>
                                </div>
                                <div class="sec-con-area">
                                    <!-- 2024-01-02 약관 추가 -->
                                    <div class="agree-box">
                                        <div class="gray-bg-sec narrow-pad">
                                            <div class="paragraphs">
                                                <p class="txt f-body2">
                                                    재단법인 '자동차부품산업진흥재단'(이하 '재단')은 고객님의 개인정보를 중요시하며,
                                                    [개인정보보호법]에 관한 법률을 준수하고 있습니다.
                                                    재단은 개인정보처리방침을 통하여 고객님께서 제공하시는 개인정보가 어떠한 용도와 방식으로
                                                    이용되고 있으며, 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.
                                                    재단은 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.
                                                    귀하의 개인정보는 교육 신청시 제3자 제공 동의를 선택한 경우 「개인정보보호법」 에 따라 교육신청 등록, 숙박 배정 등을 위하여 귀하의 개인정보를 다음과 같이 제3자에게 제공하고자 합니다.
                                                </p>
                                            </div>
                                            <div class="paragraphs">
                                                <p class="txt f-body2">
                                                    1. 개인정보의 제공
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
                                                <p class="txt f-body2">
                                                    2. 제공받는자의 개인정보 수집 이용
                                                    <br/>① 개인식별정보(성명, 성별 등)를 활용한 교육장 출결 등 사용
                                                    <br/>② 해당 교육 신청자 정보 등록
                                                    <br/>③ GPC內 숙박 호실 배정, 식사 인원 배정 등
                                                </p>
                                                <p class="txt f-body2">
                                                    3. 제공할 개인정보의 항목
                                                    <br>- 개인식별정보(성명, 성별, 회사명)
                                                </p>
                                                <p class="txt f-body2">
                                                    4. 제공받는자의 개인정보 이용
                                                    <br/>교육신청 등을 위해 입력하신 정보는 목적(교육신청)이 달성된 후 별도의 DB로 옮겨져 법률에 의한 경우가 아니고서는 보유되어지는 이외의 다른 목적으로 이용되지 않습니다.
                                                </p>
                                                <p class="txt f-body2">
                                                    5. 개인정보의 제3자 제공 동의
                                                    <br/>위 개인정보 수집 및 제3자 제공 동의에 대하여 거부할 권리가 있으나, 동의 거부시 교육신청 등 이용이 불가합니다.
                                                </p>
                                            </div>
                                        </div>
                                        <div class="form-group align-right">
                                            <div class="form-checkbox">
                                                <input type="checkbox" id="agreeChk" name="agreeChk" value="Y">
                                                <label for="agreeChk">약관에 동의합니다.</label>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 2024-01-02 약관 추가 -->
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap for-motion">
                            <div class="btn-set">
                                <a class="btn-solid small gray-bg" href="javascript:" data-url="./detail?detailsKey=${ rtnData.edctnSeq }" id="btnCancel"><span>취소</span></a>
                            </div>
                            <div class="btn-set">
                                <a class="btn-solid small black-bg" href="javascript:" id="btnApply"><span>신청하기</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>

    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAPicLayer.jsp"></jsp:include><!-- 문의 담당자 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduRoomLayer.jsp"></jsp:include><!-- 교육장 팝업 -->

    <%--<form name="frmGo" id="frmGo" method="post" action="">
        <input type="hidden" class="notRequired" id="regDtm" name="regDtm" value="" />
        <input type="hidden" class="notRequired" id="prntCdNm" name="prntCdNm" value="${ rtnData.prntCdNm }" />
        <input type="hidden" class="notRequired" id="ctgryCdNm" name="ctgryCdNm" value="${ rtnData.ctgryCdNm }" />
        <input type="hidden" class="notRequired" id="nm" name="nm" value="${ rtnData.nm }" />
        <input type="hidden" class="notRequired" id="smmryNm" name="smmryNm" value="${ rtnData.smmryNm }" />
        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnData.edctnSeq}" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>--%>

</div>




<script>
    window.onpageshow = function (event) {
        // console.log(event);
        if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            location.href = "/";
            alert("정상적인 접근이 아닙니다.");
        }
    }

    /*history.pushState(null, '', location.href);
    window.onpopstate = function (event) {
        console.log(event);
        history.go(1);
        this.handleGoback();
        // 뒤로가기 버튼이 눌렸을 때 실행되는 코드
        alert("뒤로가기 버튼이 클릭되었습니다.");

        // 여기에 추가적인 동작을 원하는 경우 작성
        // 예를 들어 특정 페이지로 이동하거나 다른 로직 수행 가능
    }*/
</script>