<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/eb/EBCVisitEduCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="post" >
        <input type="hidden" class="notRequired" id="memCd" name="memCd" value="${rtnInfo.memCd}" />

        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">방문교육</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-educational-visiting.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-educational-visiting-mobile.jpg" alt="">
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <div class="lnb-area">
                    <div class="for-motion">
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu" href="javascript:"><span>교육사업 소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu" href="javascript:"><span>교육신청</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                        <div class="lnb-list">
                            <a class="btn-two-depth single-menu active" href="javascript:"><span>방문교육</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                        </div>
                    </div>
                </div>

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
                                            <p class="f-sub-head">부품사에 더 많은 교육의 기회를 제공하기 위해, 재단의 각 분야 전문가들이 부품사 현장으로 직접 찾아가 맞춤 교육을 제공합니다.</p>
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
                                            <p class="f-sub-head">회사 내부 이슈로 임직원에 대한 집중 교육이 필요하거나, 시간과 거리의 제약으로 외부 교육 참여가 어려운 경우, 품질/기술/경영 전문가의 현장 방문 교육을 신청할 수 있습니다.</p>
                                        </div>
                                        <div class="paragraph">
                                            <p class="f-sub-head">신청은 홈페이지를 통해 가능하며, 방문교육 담당자가 교육 주제/교육 대상/교육 시간/교육 장소를 고려하여 신청자와 협의 후 최적의 강사를 배정합니다.</p>
                                        </div>
                                        <div class="paragraph">
                                            <p class="f-sub-head">오랜 기간 현업에서 경험을 쌓은 품질/기술/경영 전문위원들이 부품사 현황에 맞는 맞춤형 교육을 제공한다는 점에서, 효과성과 효율성 높은 교육 혜택을 받을 수 있습니다.</p>
                                        </div>
                                    </div>
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>사업목적</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th>구분</th>
                                                    <th>내용</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <th>대상</th>
                                                    <td>자동차 부품사(참석인원 20인 이상)</td>
                                                </tr>
                                                <tr>
                                                    <th>교육시간</th>
                                                    <td>2~6시간(부품사에서 요청하는 교육시간 반영)</td>
                                                </tr>
                                                <tr>
                                                    <th>업무진행</th>
                                                    <td>신청 → 협의 → 강사배정 → 교육준비 → 교육실시</td>
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
                                    <p class="f-title3">교육내용</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>교육내용</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th>분야</th>
                                                    <th>내용</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <th>품질</th>
                                                    <td>
                                                        <p>[품질 기초] 품질경영시스템, QC7 tools, 3정 5행, 공정 관리 등</p>
                                                        <p>[품질 향상] 5스타 레벨업, 내부 심사원, 코어툴, 신차부품개발프로세스, FMEA, SPC, MSA</p>
                                                        <p>[SQ 평가 관련] 업종별 SQ 이해, SQ 평가 대응</p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>기술</th>
                                                    <td>
                                                        <p>[업종별 기술 이해] 용접, 사출, 도금, 도장, 프레스금형, 전기전자, 가공, 열처리, AL주조, 단조, 고무</p>
                                                        <p>[IT/전산 관련] 현장 자동화, 스마트 공장 구축</p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>경영</th>
                                                    <td>
                                                        <p>ESG 역량 향상 및 평가 대응</p>
                                                        <p>생산 관리자의 역량</p>
                                                        <p>경영진단, 사업계획 수립, 해외 수출 및 진출 등</p>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- 방문교육 신청 하단 플로팅 영역 -->
                    <div class="accepting-fixed-area">
                        <div class="for-position">
                            <button class="open-click-area" type="button">
                                <p class="tit">방문교육 신청하기</span></p>
                                <div class="btn-text-icon plus"><span>더보기</span></div>
                            </button>

                            <div class="hide-area">
                                <div class="inner-con">
                                    <div class="tit-area">
                                        <p class="f-title1">방문교육 신청하기</p>
                                    </div>
                                    <!-- <div class="con-area">
                                      <div class="scroll-area">
                                      </div>
                                    </div> -->

                                    <div class="btn-wrap align-right">
                                        <a class="btn-solid small black-bg" href="javascript:" id="applyBtn"><span>신청하기</span></a>
                                    </div>

                                    <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
