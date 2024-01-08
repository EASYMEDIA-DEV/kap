<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<div class="cont-wrap" data-controller="controller/cb/cbb/CBBManageConsultIndexCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-consulting-guide.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-consulting-guide-mobile.jpg" alt="">
            </div>
        </div>
    </div>
    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">사업개요</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="paragraph">
                                    <p class="f-sub-head">재단은 2007년 자동차메이커의 연구개발 생산기술, 품질, 해외마케팅 등 다양한 분야의 임원 출신으로 구성된 협력 부품사 지원단을 발족, 회사 전반의 경영자문과 현장 맞춤형 컨설팅을 무상으로 제공하여 부품사의 관리시스템 체계화와 글로벌 경쟁력 향상을 적극 지원하고 있습니다.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">사업목적</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="paragraph">
                                    <p class="f-sub-head">자동차 메이커 및 유관기관 임원 출신으로 구성된 자문위원이 전문 경력과 노하우를 전수하기 위하여 부품사를 방문하여 경영전반에 걸쳐 맞춤형 컨설팅 무상으로 제공</p>
                                </div>
                                <div class="img-area">
                                    <p class="img">
                                        <img src="/common/images/img-managementconsulting-business-purpose.jpg" alt="">
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">컨설팅 개요</p>
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
                                            <th>지원대상</th>
                                            <td>한국자동차부품사</td>
                                        </tr>
                                        <tr>
                                            <th>활동기간</th>
                                            <td>3~6개월 (필요 시 연장 가능)</td>
                                        </tr>
                                        <tr>
                                            <th>비용(무상)</th>
                                            <td>
                                                <p>국내 : 일체 무상지원 (교통비, 숙박비 등 재단 부담)</p>
                                                <p>해외 : 항공료 및 현지 체제비용 업체부담</p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>자문위원 인원</th>
                                            <td>11명</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">컨설팅 분야</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="gray-bg-sec-w">
                                <div class="gray-bg-sec">
                                    <p class="f-title3">경영자문</p>
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="f-body1">종합 경영컨설팅</p>
                                            <div class="ul-txt-w">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt">분야별 자문활동</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="con-list-box">
                                            <p class="f-body1">자문업체 협력사 One-Point 자문</p>
                                            <div class="ul-txt-w">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt">연구개발, 생산, 품질, 원가, 물류 등</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="gray-bg-sec">
                                    <p class="f-title3">경영진단</p>
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="f-body1">회사 전반적인 부문 진단 희망 시</p>
                                            <div class="ul-txt-w">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt">사업분야별 경영진단팀 구성<br/>1개월 진단</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="gray-bg-sec">
                                    <p class="f-title3">교육 세미나</p>
                                    <div class="con-list-box-w">
                                        <div class="con-list-box">
                                            <p class="f-body1">맞춤형 교육·세미나</p>
                                            <div class="ul-txt-w">
                                                <div class="ul-txt-list">
                                                    <p class="ul-txt">부품사 현지 방문<br/>(품질, 생산기술, 연구개발 등)</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">컨설팅 세부 내용</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="gray-bg-sec">
                                <div class="con-list-box-w">
                                    <div class="con-list-box">
                                        <p class="f-head">연구개발</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">미래차 사업 확대, 기술개발 로드맵 수립, 부품·신차개발 프로세스 정립, 연구소 운영</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="f-head">생산</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">공장운영, 생산(생산성 향상)설비 관리·개선, 공장라인 운영 적정성 관리, 부품 재고 관리</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="f-head">생산기술</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">공정자동화, 6M 표준화, 수익성 개선, 공장 레이아웃·불량분석·설비 개선</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="f-head">품질</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">품질경영시스템구축, 품질 불량 개선, 품질5스타 및 SQ 평가·인증</p>
                                                <p class="ul-txt has-dot">자동차부품 Recall(제작 결함) 대응</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="f-head">원가(재경)</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">손익분석 관련 업무지원(제품, 공단단위별), 원가 계산, 재경 업무(회계·세무·재무)</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="f-head">기획/전략</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">경영관리 체계 정립, 리더십·직원역량개발, 미래비전 및 중장기 전략 수립</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">경영컨설팅 신청 안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="guide-step-w">
                                    <div class="step-list">
                                        <p class="step-num">STEP 01</p>
                                        <div class="step-info">
                                            <p class="step-con">온라인 신청</p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 02</p>
                                        <div class="step-info">
                                            <p class="step-con">담당자연락</p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 03</p>
                                        <div class="step-info">
                                            <p class="step-con">초도방문</p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 04</p>
                                        <div class="step-info">
                                            <p class="step-con">상주경영컨설팅 부품사선정</p>
                                        </div>
                                    </div>
                                </div>

                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-apply-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">
                                            상주경영컨설팅 신청은 온라인으로만 접수받으며, 절차에 따라 항목을 입력하시면 신청이 완료됩니다.
                                            <br/>이후 담당자가 연락하여, 초도 방문 후 상주경영컨설팅 부품사를 선정하게 됩니다.
                                        </p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg techApplication" data-authCd="${loginMap.authCd}" <c:if test="${empty loginMap}">href="/login"</c:if>><span>신청하기</span></a>
                                    </div>
                                </div>

                                <div class="divide-flex-box download">
                                    <div class="left">
                                        <p class="f-body2">상주경영컨설팅 신청 시 회사소개서(자율 양식)와 개선활동 추진계획서가 필수로 첨부되어야 합니다.</p>
                                        <p class="f-caption2">* 개선활동 추진계획서는 다운로드 후 양식에 맞게 작성 후 첨부 부탁드립니다.</p>
                                    </div>
                                    <div class="right has-button">
                                        <div class="btn-wrap">
                                            <a class="btn-text-icon download" href="javascript:" download><span>개선활동 추진계획서 다운로드</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">경영컨설팅 Q&A 이용안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-inquiry-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">
                                            경영컨설팅 관련 도움을 드리기 위해 Q&A 게시판을 운영하고 있습니다.
                                            <br/>질문을 남겨주시면 빠른 시일 내에 답변드리겠습니다.
                                        </p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg" href="javascript:"><span>신청 문의하기</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3"><span class="menu-name">경영컨설팅 자문위원 소개</span> &#40;<span class="item-count">${rtnDto.totalCount}</span>명&#41;</p>
                        </div>
                        <div class="sec-con-area popUp">
                            <div class="list-sec">
                                <div class="article-list-w txt-card-list" id="infoCard"><!--  txt-card-list: 텍스트 카드일 경우 + bg가 있을 경우 -->
                                    <c:forEach var="csList" items="${rtnDto.list}" varStatus="status">
                                        <a class="list-item open" title="팝업 열기">
                                            <div class="bg">
                                                <img src="/common/images/@img-foundation-group-member.png" alt="">
                                            </div>
                                            <input type="hidden" class="memSeq" value="${csList.memSeq}">
                                            <div class="txt-box">
                                                <div class="names">
                                                    <p class="name f-title3">${csList.name}</p>
                                                    <p class="position f-sub-head">전문위원</p>
                                                </div>
                                                <div class="labels">
                                                    <p class="box-label cmssrCbsnCdNm"><span>${csList.cmssrCbsnCdNm}</span></p>
                                                </div>
                                                <div class="btn-wrap">
                                                    <div class="btn-text-icon black-circle"><span>더 알아보기</span></div>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                                <div class="btn-wrap align-center moreBtn">
                                    <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count cntText"></span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!-- 접수중 하단 플로팅 영역 -->
            <div class="accepting-fixed-area">
                <div class="for-position">
                    <button class="open-click-area" type="button">
                        <p class="tit">2023 상주경영컨설팅 <span class="status">접수중</span></p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1">2023 상주경영컨설팅 접수중 (상시접수중)</p>
                            </div>
                            <!-- <div class="con-area">
                              <div class="scroll-area">
                              </div>
                            </div> -->

                            <div class="btn-wrap">
                                <div class="btn-set">
                                    <a class="btn-solid small gray-bg has-icon download" href="javascript:" download title="개선활동 추진계획서 다운로드"><span>개선활동 추진계획서 다운로드</span></a>
                                </div>
                                <div class="btn-set">
                                    <a class="btn-solid small black-bg" href="javascript:"><span>신청하기</span></a>
                                </div>
                            </div>

                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
<jsp:include page="/WEB-INF/jsp/front/cb/cbb/CBBManageConsultLayer.jsp"></jsp:include>