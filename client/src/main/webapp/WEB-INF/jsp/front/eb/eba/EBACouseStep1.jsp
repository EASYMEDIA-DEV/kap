<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" data-controller="controller/eb/eba/EBACouseStepCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnData.edctnSeq}" />

        <input type="hidden" id="episdYear" name="episdYear" value="${episdDto.episdYear}" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="${episdDto.episdOrd}" />
        <input type="hidden" id="episdSeq" name="episdSeq" value="${episdDto.episdSeq}" />

        <input type="hidden" id="stduyMthdCd" name="stduyMthdCd" value="${episdDto.stduyMthdCd}" />
        <input type="hidden" id="ptcptBsnmNo" name="ptcptBsnmNo" value="${loginMap.bsnmNo}" />
        <input type="hidden" id="rcrmtMthdCd" name="rcrmtMthdCd" value="${episdDto.rcrmtMthdCd}" />



        <input type="hidden" id="memSeq" name="memSeq" value="${loginMap.seq}" />
        <input type="hidden" id="bsnmNo" name="bsnmNo" value="${loginMap.bsnmNo}" />



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
                                                        <img src="${rtnData.webPath}" alt="">
                                                    </div>
                                                    <div class="txt-area">
                                                        <div class="top-line">
                                                            <div class="sort-label-area">
                                                                <p class="label"><span>${rtnData.prntCdNm}</span></p>
                                                                <p class="label"><span>${rtnData.ctgryCdNm}</span></p>
                                                            </div>
                                                            <p class="training-name f-title1">${rtnData.nm}</p>
                                                            <p class="training-explain-txt">${rtnData.smmryNm}</p>
                                                        </div>
                                                        <div class="class-property-w">
                                                            <c:if test="${rtnData.stduyMthdCd eq 'STDUY_MTHD01'}">
                                                                <div class="property-list offline"><!-- offline: 집체교육 -->
                                                                    <p class="txt">
                                                                        <span>${rtnData.stduyMthdCdNm}</span>
                                                                    </p>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${rtnData.stduyMthdCd eq 'STDUY_MTHD02'}">
                                                                <div class="property-list online"><!-- online: 온라인교육 -->
                                                                    <p class="txt">
                                                                        <span>${rtnData.stduyMthdCdNm}</span>
                                                                    </p>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${rtnData.stduyMthdCd eq 'STDUY_MTHD03'}">
                                                                <div class="property-list online"><!-- online: 온라인교육 -->
                                                                    <p class="txt">
                                                                        <span>${rtnData.stduyMthdCdNm}</span>
                                                                    </p>
                                                                </div>
                                                            </c:if>

                                                            <div class="property-list time"><!-- time: 학습시간 -->
                                                                <p class="txt">
                                                                    <span>${rtnData.stduyDdCdNm}일(${rtnData.stduyTimeCdNm}시간)</span>
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
                                                        <div class="top-area">
                                                            <div class="left">
                                                                <div class="group">
                                                                    <p class="index-num f-title3">${episdDto.episdOrd}회차</p>
                                                                    <c:if test="${not empty episdDto.cbsnCdNm}">
                                                                        <div class="status-info-w">
                                                                            <p class="box-label bigger"><span>${episdDto.cbsnCdNm}</span></p>
                                                                        </div>
                                                                    </c:if>

                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <div class="btn-set">
                                                                        <c:if test="${not empty list.edctnNtctnFileSeq}">
                                                                            <a class="btn-text-icon download" href="/file/view?fileSeq=${episdDto.edctnNtctnFileSeq}&fileOrd=${episdDto.fileOrd}"><span>안내문</span></a>
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="cont-area">
                                                            <div class="info-list-w">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">강사</p>
                                                                    <p class="txt f-body2">${episdDto.isttrGroupName}</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">정원</p>
                                                                    <p class="txt f-body2">
                                                                        <c:if test="${episdDto.fxnumImpsbYn eq 'Y'}">
                                                                            ${episdDto.fxnumCnt}명
                                                                        </c:if>
                                                                        <c:if test="${episdDto.fxnumImpsbYn eq 'N'}">
                                                                            제한없음
                                                                        </c:if>
                                                                        (${episdDto.rcrmtMthdCdNm})
                                                                    </p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육장소</p>
                                                                    <p class="txt f-body2">

                                                                        <c:choose>
                                                                            <c:when test="${episdDto.stduyMthdCd eq 'STDUY_MTHD02'}">
                                                                                온라인
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="javascript:" class="mapBtn" data-mapchk="N" data-zipcode="${episdDto.zipcode}" data-nm="${episdDto.placeNm}" data-rprsntTelNo="${episdDto.rprsntTelNo}" data-bscAddr="${episdDto.bscAddr}" data-dtlAddr="${episdDto.dtlAddr}" title="교육장 안내 팝업 열기">${episdDto.placeNm}</a>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">접수일자</p>
                                                                    <p class="txt f-body2">${kl:convertDate(episdDto.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')} ~ ${kl:convertDate(episdDto.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육일자</p>
                                                                    <p class="txt f-body2">${ empty episdDto.edctnStrtDtm ? '-' : kl:convertDate(episdDto.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } ~ ${ empty episdDto.edctnEndDtm ? '-' : kl:convertDate(episdDto.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } (${episdDto.stduyDdCdNm}일간)</p>
                                                                    <input type="hidden" name="edctnStrtDtm" id="edctnStrtDtm" value="${kl:convertDate(episdDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                                                                    <input type="hidden" name="edctnEndDtm" id="edctnEndDtm" value="${kl:convertDate(episdDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                                                                </div>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-text-icon black-arrow popupPicPrevSet" type="button" data-picNm="${episdDto.picNm}" data-picEmail="${episdDto.picEmail}" data-picTelNo="${episdDto.picTelNo}"><span>회차 담당자 문의</span></button>
                                                                <button class="btn-text-icon black-arrow popupLctrPrevSet" type="button" data-episdYear="${episdDto.episdYear}" data-episdOrd="${episdDto.episdOrd}"><span>온라인 강의목차</span></button>
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
                                <div class="guide-info-area">
                                    <div class="divide-box">
                                        <p class="exclamation-txt f-sub-head">회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.<br>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.</p>
                                    </div>
                                    <div class="divide-box">
                                        <div class="btn-wrap">
                                            <a class="btn-text-icon download" href="javascript:" title="신청서 작성예시 파일 다운로드" download=""><span>신청서 작성예시</span></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="sec-tit-area">
                                    <p class="f-title3">신청자 기본정보를 확인해주세요</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>신청자</th>
                                                    <td>${applicantInfo.name}</td>
                                                </tr>
                                                <!-- 2024-01-03 추가 -->
                                                <tr>
                                                    <th>성별</th>
                                                    <td>
                                                        <c:if test="${applicantInfo.gndr eq '1'}">
                                                            남
                                                        </c:if>
                                                        <c:if test="${applicantInfo.gndr ne '1'}">
                                                            여
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <!-- // 2024-01-03 추가 -->
                                                <tr>
                                                    <th>휴대폰번호</th>
                                                    <td>${applicantInfo.hpNo}</td>
                                                </tr>
                                                <tr>
                                                    <th>이메일</th>
                                                    <td>${applicantInfo.email}</td>
                                                </tr>
                                                <tr>
                                                    <th>전화번호</th>
                                                    <td>${applicantInfo.telNo}</td>
                                                </tr>
                                                <tr>
                                                    <th>부서</th>
                                                    <td>${applicantInfo.deptCdNm}(${applicantInfo.deptDtlNm})</td>
                                                </tr>
                                                <tr>
                                                    <th>직급</th>
                                                    <td>${applicantInfo.pstnNm}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-wrap align-right">
                                            <a class="btn-text-icon black-circle" href="javascript:"><span>회원 기본정보 수정</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">부품사 기본정보를 확인해주세요</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>소속 부품사 기본정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>

                                                <tr>
                                                    <th>부품사명</th>
                                                    <td>${rtnInfo.cmpnNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>구분</th>
                                                    <td>${rtnInfo.ctgryNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>대표자명</th>
                                                    <td>${rtnInfo.rprsntNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>설립일자</th>
                                                    <td>${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</td>
                                                </tr>
                                                <tr>
                                                    <th>전화번호</th>
                                                    <td>${rtnInfo.telNo}</td>
                                                </tr>
                                                <tr>
                                                    <th>사업자등록번호</th>
                                                    <td>${kl:bsnmNoConvert(rtnInfo.bsnmNo)}</td>
                                                </tr>
                                                <tr>
                                                    <th>본사주소</th>
                                                    <td>(${rtnInfo.zipcode}) ${rtnInfo.bscAddr} ${rtnInfo.dtlAddr}</td>
                                                </tr>
                                                <tr>
                                                    <th>규모</th>
                                                    <td>${rtnInfo.sizeNm}</td>
                                                </tr>
                                                <tr>
                                                    <th>매출액</th>
                                                    <td>${rtnInfo.slsPmt}억 원(${rtnInfo.slsYear}년)</td>
                                                </tr>
                                                <tr>
                                                    <th>직원수</th>
                                                    <td>${rtnInfo.mpleCnt}명</td>
                                                </tr>
                                                <tr>
                                                    <th>주생산품</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty rtnInfo.mjrPrdct1}">
                                                                ① ${rtnInfo.mjrPrdct1}
                                                            </c:when>
                                                            <c:when test="${not empty rtnInfo.mjrPrdct2}">
                                                                ② ${rtnInfo.mjrPrdct2}
                                                            </c:when>
                                                            <c:when test="${not empty rtnInfo.mjrPrdct3}">
                                                                ③ ${rtnInfo.mjrPrdct3}
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <c:choose>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">
                                                <tr>
                                                    <th>SQ정보</th>
                                                    <td>
                                                        <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">
                                                            <p>${status.count}. ${list.nm} / ${list.score} / ${list.year} 년 / ${list.crtfnCmpnNm}</p>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                                </c:when>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01001'}">
                                                <tr>
                                                    <th>품질5스타</th>
                                                    <td>${rtnInfo.qlty5StarCdNm} / ${rtnInfo.qlty5StarYear}년</td>
                                                </tr>
                                                <tr>
                                                    <th>납입5스타</th>
                                                    <td>${rtnInfo.pay5StarCdNm} / ${rtnInfo.pay5StarYear}년</td>
                                                </tr>
                                                <tr>
                                                    <th>기술5스타</th>
                                                    <td>${rtnInfo.tchlg5StarCdNm} / ${rtnInfo.tchlg5StarYear}년</td>
                                                </tr>
                                                </c:when>
                                                </c:choose>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-wrap align-right">
                                            <a class="btn-text-icon black-circle" href="javascript:"><span>부품사 기본정보 수정</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">GPC 아이디 인증</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">GPC 아이디</p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="for-status-chk satisfy"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                            <div class="form-group">
                                                                <div class="form-input">
                                                                    <input type="text" placeholder="GPC 아이디 입력" required="">
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <button class="btn-solid small gray-bg" type="button"><span>인증</span></button>
                                                                </div>
                                                            </div>
                                                            <p class="satisfy-msg">아이디가 인증되었습니다.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-sec">
                                        <p class="exclamation-txt f-body1">GPC 교육의 경우 GPC 홈페이지의 KAP 아이디와 로그인한 아이디가 동일해야 신청 가능합니다.</p>
                                        <p class="exclamation-txt f-body1">GPC(글로벌상생협력센터) 아이디의 경우 본인의 계정만 사용 가능하며, 타인의 아이디는 사용이 불가능합니다.</p>
                                    </div>
                                    <div class="btn-sec">
                                        <div class="btn-wrap">
                                            <button class="btn-text-icon black-arrow" type="button"><span>GPC 회원가입</span></button>
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
                                                <input type="checkbox" id="agreeChk" name="">
                                                <label for="agreeChk">약관에 동의합니다.</label>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 2024-01-02 약관 추가 -->
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
                                <a class="btn-solid small black-bg setPtcptInfo" href="javascript:"><span>신청하기</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content 영역 end -->


    </form>

    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAPicLayer.jsp"></jsp:include><!-- 문의 담당자 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduLctrLayer.jsp"></jsp:include><!-- 온라인 강의 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduRoomLayer.jsp"></jsp:include><!-- 교육장 팝업 -->

</div>




