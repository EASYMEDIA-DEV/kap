<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
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
            <p class="page-tit f-xlarge-title"><span class="for-move">${ not empty pageMenuDto ? pageMenuDto.menuNm : ''}</span></p>
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
                        <!-- @ 인사말 디자인 영역 -->
                        <div class="greeting-wrap">
                            <div class="txt-box">
                                <div class="sec-tit-area mob-layout tabl-layout">
                                    <p class="f-title3">
                                        안녕하십니까?
                                        <br/>자동차부품산업진흥재단을 방문해 주신 여러분을 환영합니다.
                                    </p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="txt-sec">
                                        <div class="paragraph tabl-layout">
                                            <p class="f-sub-head"><span class="mob-layout">한국 자동차산업은 글로벌 생존경쟁이 더욱 치열해지는 상황속에서 자동차 메이커와 부</span><span>품업계의 부단한 노력으로 눈부신 발전을 거듭하며 명실상부한 세계 3위의 자동차강국으로 성장해 왔습니다.</span></p>
                                        </div>
                                        <div class="paragraph tabl-layout">
                                            <p class="f-sub-head">지난 2002년 7월, 자동차부품산업의 국제경쟁력 확보라는 사명을 띠고 비영리 공익법인으로 출범한 자동차부품산업진흥재단은 부품사의 현장애로를 해결해주는 기술지도로 출발, 완성차 임원출신으로 구성된 경영컨설팅, 품질마인드 고양을 위한 품질학교, 이론과 실습이 병행된 기술학교, 임직원 의식혁신을 위한 경영일반 교육의 개설에 이르기까지, 부품업계의 품질 및 기술력 향상과 인재 육성을 위한 다각적인 지원사업을 펼쳐오고 있습니다.</p>
                                        </div>
                                        <div class="paragraph">
                                            <p class="f-sub-head">돌이켜보면, 국내 부품산업은 국가 기간산업으로 자리잡으며 기업규모와 경쟁력 측면에서 선진업체들과 대등한 수준으로 성장해 왔지만, 지속가능한 성장을 위해서는 품질, 기술, 원가 측면에서 경쟁력을 더욱 강화해 나가야 하겠습니다.</p>
                                        </div>
                                        <div class="paragraph">
                                            <p class="f-sub-head">재단은 현장지도, 품질, 기술을 선도하는 종합 컨설팅, 교육 지원기관으로서 내부혁신을 통한 역량 강화와 실효성 높은 지원사업을 전개하여, 자동차부품업계의 경쟁력을 한 단계 레벨업하고, 나아가 한국 자동차산업이 글로벌 선두로 도약하는데 전력을 기울여 나가겠습니다. 여러분의 많은 관심과 아낌없는 조언을 기대해 마지 않습니다.</p>
                                        </div>
                                        <div class="paragraph">
                                            <p class="f-sub-head">감사합니다.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="img-box">
                                <div class="img-test">
                                    <img src="/common/images/img-foundation-greeting-chariman.png" alt="">
                                </div>
                                <p class="name f-body1">자동차부품산업진흥재단 이사장 안정구</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">학력</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="ul-txt-w highlight">
                                    <div class="ul-txt-list">
                                        <dl class="ul-txt has-dot">
                                            <dt>1981년</dt>
                                            <dd>세인트메리스 국제 학교(Toyko) 졸업 (St. Mary’s International School - Toyko)</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>1986년</dt>
                                            <dd>미국 캘리포니아 대학교 버클리 졸업 (전공: 정치경제학) (University of California, Berkeley)</dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">경력</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="ul-txt-w highlight">
                                    <div class="ul-txt-list">
                                        <dl class="ul-txt has-dot">
                                            <dt>1997년 ~ 현재</dt>
                                            <dd>㈜유니크 대표이사/사장</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2016년 ~ 현재</dt>
                                            <dd>한국공학한림원 일반회원</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2020년 ~ 현재</dt>
                                            <dd>한국자동차공학회 부회장/대학생 자작자동차대회 위원장</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2020년 ~ 2022년</dt>
                                            <dd>한국자동차공학회 부회장/재무</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2021년 ~ 2022년</dt>
                                            <dd>현대기아자동차 협력회 부회장</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2023년 ~ 현재</dt>
                                            <dd>현재 현대기아자동차 협력회 감사</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2023년 ~ 현재</dt>
                                            <dd>자동차부품산업진흥재단 이사장</dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">수상</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="ul-txt-w highlight">
                                    <div class="ul-txt-list">
                                        <dl class="ul-txt has-dot">
                                            <dt>2003년</dt>
                                            <dd>발명의 날 산업포장</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2012년</dt>
                                            <dd>산업기술진흥유공자 산업통상자원부장관표창</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2012년</dt>
                                            <dd>대한민국기술대상 산업통상자원부장관표창</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2014년</dt>
                                            <dd>경상남도산업평화상 대상</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2014년</dt>
                                            <dd>무역의 날 1억불 수출의 탑</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2015년</dt>
                                            <dd>상공인의 날 대통령표창</dd>
                                        </dl>
                                        <dl class="ul-txt has-dot">
                                            <dt>2015년</dt>
                                            <dd>지역산업진흥유공 대통령단체표창</dd>
                                        </dl>
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
<!-- content 영역 end -->