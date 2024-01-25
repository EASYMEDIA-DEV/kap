<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage" data-controller="controller/eb/ebm/EBMEduApplyTrnsfCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input type="hidden" id="detailsKey" name="detailsKey" value="${ rtnData.edctnSeq }" />
        <input type="hidden" id="edctnSeq" name="edctnSeq" value="${ rtnData.edctnSeq }" />
        <input type="hidden" id="episdOrd" name="episdOrd" value="${ rtnData.episdOrd }" />
        <input type="hidden" id="episdYear" name="episdYear" value="${ rtnData.episdYear }" />
        <input type="hidden" id="episdSeq" name="episdSeq" value="${ rtnData.episdSeq }" />
        <input type="hidden" id="ptcptSeq" name="ptcptSeq" value="${ rtnData.ptcptSeq }" />

        <input type="hidden" id="bfrGpcId" name="bfrGpcId" value="${ rtnData.gpcId }" />

        <input type="hidden" id="workBsnmNo" name="workBsnmNo" value="${loginMap.bsnmNo}" />


        <input type="hidden" id="gpcYn" name="gpcYn" value="${rtnData.gpcYn}" />

        <c:set var="gpcYn" value="${rtnData.gpcYn}"/>
        <c:set var="gpcPass" value="Y"/>
        <c:choose>
            <c:when test="${gpcYn eq 'Y'}">
                <c:set var="gpcPass" value="N"/>
            </c:when>
            <c:otherwise>
                <c:set var="gpcPass" value="Y"/>
            </c:otherwise>
        </c:choose>

        <input type="hidden" id="gpcPass" name="gpcPass" value="${gpcPass}" />
        <input type="hidden" id="memSeq" name="memSeq" value="${loginMap.seq}" />
        <input type="hidden" id="memId" name="memId" value="${applicantInfo.id}" /><!--로그인 한 사람의 id -->
        <c:set var="gndr" value="${applicantInfo.gndr eq 1 ? 'M' : 'F'}" />
        <input type="hidden" id="gndr" name="gndr" value="${gndr}" /><!--로그인 한 사람의 성별 -->
        <input type="hidden" id="bsnmNo" name="bsnmNo" value="${loginMap.bsnmNo}" />

        <!--현재 lctrSeq를 기반으로 이전 이후것을 가져온다 -->

        <!-- content 영역 start -->
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육 양도</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

                <div class="right-con-area">
                    <div class="guide-info-area scroll-motion">
                        <div class="for-motion">
                            <div class="divide-box">
                                <p class="exclamation-txt f-body1">교육 양도는 교육 시작 전일까지만 가능합니다. 교육 양도 후 회수는 불가능하며, 새로 신청하여 교육에 참여하실 수 있습니다.</p>
                                <p class="exclamation-txt f-body1">교육 양도는 소속 부품사내 회원에 한해서 양도 가능합니다. 양도 받은 회원 또한 소속 부품사내 회원에게 재양도 하실 수 있습니다.</p>
                            </div>
                        </div>
                    </div>
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">소속 부품사</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>소속 부품사</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>부품사명</th>
                                                    <td>${rtnInfo.cmpnNm}</td><!-- 2023-12-27 class 삭제 -->
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">회원목록</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="info-head">
                                        <p class="article-total-count f-body2">총 <span>1065</span>건</p>
                                        <div class="form-input srch-input">
                                            <input type="text" name="q" id="q" placeholder="성명/아이디/핸드폰번호/이메일 입력"><!-- 2023-12-07 수정 -->
                                            <div class="input-btn-wrap">
                                                <button class="delete-btn" title="지우기" type="button"></button>
                                                <button id="searchBtn" class="srch-btn" title="검색" type="button"></button>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="table-sec" id="listContainer">

                                    </div>

                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count">(9/40)</span></a>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <c:if test="${gpcYn eq 'Y'}">
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
                                                            <div class="for-status-chk"><!-- 조건 충족 시 satisfy 클래스 추가 -->
                                                                <div class="form-group">
                                                                    <div class="form-input">
                                                                        <input type="text" class="notRequired" name="gpcId" id="gpcId" placeholder="GPC 아이디 입력" title="gpcId">
                                                                    </div>
                                                                    <div class="btn-wrap">
                                                                        <button class="btn-solid small gray-bg gpcCheck" type="button"><span>인증</span></button>
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
                        </c:if>

                    </div>
                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap align-right for-motion">
                            <a class="btn-solid small black-bg setTrnsf" href="javascript:"><span>교육 양도하기</span></a>
                        </div>
                    </div>
                </div>




            </div>
        </div>
        <!-- content 영역 end -->


    </form>

    <jsp:include page="/WEB-INF/jsp/front/eb/ebm/EBMEduApplyTrnsfLayer1.jsp"></jsp:include><!-- gpc id인증 팝업1-->
    <jsp:include page="/WEB-INF/jsp/front/eb/ebm/EBMEduApplyTrnsfLayer2.jsp"></jsp:include><!-- gpc id인증 팝업2-->
    <jsp:include page="/WEB-INF/jsp/front/eb/ebm/EBMEduApplyTrnsfLayer3.jsp"></jsp:include><!-- gpc id인증 팝업3-->
    <jsp:include page="/WEB-INF/jsp/front/eb/ebm/EBMEduApplyTrnsfLayer4.jsp"></jsp:include><!-- gpc id인증 팝업4 -->

</div>