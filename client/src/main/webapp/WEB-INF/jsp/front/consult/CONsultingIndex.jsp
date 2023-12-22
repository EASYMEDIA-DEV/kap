<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<div class="cont-wrap" data-controller="controller/consult/CONsultingIndexController">
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
            <p class="page-tit f-xlarge-title"><span class="for-move">기술지도</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-notice.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-notice-mobile.jpg" alt="">
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
                                    <p class="f-sub-head">재단은 부품사의 품질기술력 향상을 위해 기초기술이 취약한 중소 부품사들을 대상으로 금속 7개업종(Al주조, 단조, 용접, 열처리, 절삭가공, 도금, 프레스)과 비금속 5개업종(사출, 고무, 도장, 전기전자, IT System)의 전문기술 인력을 확보하여 부품사의 품질 ,기술, IT, 안전등 제조현장의 다양한 문제를 해결해주는 기술지도 사업을 2003년부터 운영해 오고 있습니다.</p>
                                </div>
                                <div class="paragraph">
                                    <p class="f-sub-head">기술지도사업을 이끄는 기술봉사단은 분야별로 장기간의 실무 경험과 이론을 겸비한 전문가로 구성, 부품사 현장을 방문하여 무상으로 품질과 기술을 지도함으로써 실질적인 품질안정은 물론 기술력 향상, 안전한 제조현장 구축을 도모하고 있습니다.</p>
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
                                    <p class="f-sub-head">제조현장의 품질 및 기술 애로사항에 대한 업종별 기술인력(전문위원)의 현장 맞춤형 혁신활동 지원</p>
                                </div>
                                <div class="img-area">
                                    <p class="img">
                                        <img src="/common/images/img-consulting-business-purpose.jpg" alt="">
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">대상부품사</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="paragraph">
                                    <p class="f-head">자동차 부품 1,2,3차 협력사</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">지도분야</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <p class="sub-title">금속업종</p>
                                <div class="category-list-w">
                                    <div class="category-list">
                                        <span>AL주조</span>
                                    </div>
                                    <div class="category-list">
                                        <span>용접</span>
                                    </div>
                                    <div class="category-list">
                                        <span>단조</span>
                                    </div>
                                    <div class="category-list">
                                        <span>프레스</span>
                                    </div>
                                    <div class="category-list">
                                        <span>열처리</span>
                                    </div>
                                    <div class="category-list">
                                        <span>절삭가공</span>
                                    </div>
                                    <div class="category-list">
                                        <span>도금</span>
                                    </div>
                                </div>
                            </div>
                            <div class="graphic-sec">
                                <p class="sub-title">비금속업종</p>
                                <div class="category-list-w">
                                    <div class="category-list">
                                        <span>사출</span>
                                    </div>
                                    <div class="category-list">
                                        <span>고무</span>
                                    </div>
                                    <div class="category-list">
                                        <span>도장</span>
                                    </div>
                                    <div class="category-list">
                                        <span>전기전자</span>
                                    </div>
                                    <div class="category-list">
                                        <span>IT System</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">지도방법</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="paragraph">
                                    <p class="f-head">6 ~ 9개월 지도업체 주 1회 방문 지도 ( 평균 24MD )</p>
                                </div>
                                <div class="ul-txt-w highlight">
                                    <div class="ul-txt-list">
                                        <p class="ul-txt has-dot">그룹지도 可 (완성차 및 1차사 요청), 융복합 지도 可 (타 업종, 경영컨설팅)</p>
                                        <p class="ul-txt has-dot">IT System 분야 : 2 ~ 6개월 (월 1 ~ 2회 방문 / 평균 8 ~ 10MD)</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">지도내용</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="gray-bg-sec">
                                <div class="con-list-box-w">
                                    <div class="con-list-box">
                                        <p class="sub-title">불량감소</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">작업자 및 관리자 대상 의식교육 (품질, 기술, 원가, 안전 등) 실시</p>
                                                <p class="ul-txt has-dot">공정불량 감소를 위한 상세 진단 및 지도</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="sub-title">공정개선</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">3정 5행 관련 개선안 도출 및 해당 업체와 합동 개선활동 추진</p>
                                                <p class="ul-txt has-dot">현장 LAY-OUT 개선 및 물류 효율화 방안 제시</p>
                                                <p class="ul-txt has-dot">품질문제, 불량률 축소를 위한 현장 개선활동 지도, SQ등급 향상 등</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="sub-title">생산성 향상 &middot; 원가절감</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">재료비 LOSS 감소, C/T 단축 방안 및 작업인원 축소 방안 제시 및 실행</p>
                                                <p class="ul-txt has-dot">5스타 레벨업, SQ 레벨업을 위한 품질시스템 지도</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="con-list-box">
                                        <p class="sub-title">전산시스템 및 스마트공장 구축 지원</p>
                                        <div class="ul-txt-w">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt has-dot">부문별 전산화, 현장 자동화, 단계별 스마트공장 설계·구축·운영 컨설팅</p>
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
                            <p class="f-title3">상주기술지도 신청 안내</p>
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
                                            <p class="step-con">상주기술지도 부품사선정</p>
                                        </div>
                                    </div>
                                </div>

                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-apply-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">상주기술지도 신청은 온라인으로만 접수받으며, 절차에 따라 항목을 입력하시면 신청이 완료됩니다. <br>이후 담당자가 연락하여, 초도 방문 후 상주기술지도 부품사를 선정하게 됩니다.</p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg techApplication" data-authCd="${loginMap.authCd}" <c:if test="${empty loginMap}">href="/login"</c:if>><span>신청하기</span></a>
                                    </div>
                                </div>

                                <div class="divide-flex-box download">
                                    <div class="left">
                                        <p class="f-body2">상주기술지도 신청 시 회사소개서(자율 양식)와 개선활동 추진계획서가 필수로 첨부되어야 합니다.</p>
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
                            <p class="f-title3">기술지도 Q&A 이용안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-inquiry-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">기술지도 신청 관련 도움을 드리기 위해 Q&A 게시판을 운영하고 있습니다. <br>질문을 남겨주시면 빠른 시일 내에 답변드리겠습니다.</p>
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
                            <p class="f-title3"><span class="menu-name">기술지도 전문위원 소개</span> &#40;<span class="item-count">${rtnDto.totalCount}</span>명&#41;</p>
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
                        <p class="tit">2023  상주기술지도 <span class="status">접수중</span></p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1">2023 상주기술지도 접수중 (상시접수중)</p>
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
<jsp:include page="/WEB-INF/jsp/front/consult/CONsultingLayer.jsp"></jsp:include>