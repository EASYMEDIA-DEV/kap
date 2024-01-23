<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbk/WBKManagementCtrl">
    <!--
  신청 페이지: apply-page 클래스 추가
  그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <form class="form-horizontal" id="frmData" name="frmData" method="post" action="./step1">
        <input type="hidden" id="episdSeq" name="episdSeq"/>
        <input type="hidden" id="firstIndex" name="firstIndex" value="${ rtnData.firstIndex }" />
        <input type="hidden" id="recordCountPerPage" name="recordCountPerPage" value="${ rtnData.recordCountPerPage }" />
        <input type="hidden" id="ordFlag" name="ordFlag" value="1" />
    </form>
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">미래차공모전</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-san-reward-futurecar.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-san-reward-futurecar-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
            <%-- 상생사업 CMS 영역 Start--%>
            ${rtnCms.cnts}
            <%-- 상생사업 CMS 영역 End--%>



            <c:if test="${not empty rtnRoundDtl}">
                <!-- 미래차 공모전 사업 접수 하단 플로팅 영역 -->
                <div class="accepting-fixed-area">
                    <div class="for-position">
                        <button class="open-click-area" type="button">
                            <p class="tit"><span>${rtnRoundDtl.year} 미래차공모전 접수중</span></p>
                            <div class="btn-text-icon plus"><span>더보기</span></div>
                        </button>

                        <div class="hide-area">
                            <div class="inner-con">
                                <div class="tit-area">
                                    <p class="f-title1">${rtnRoundDtl.year} 미래차공모전 접수중</p>
                                </div>
                                <div class="con-area">
                                    <div class="scroll-area">
                                        <div class="info-line-list-w">
                                            <div class="list">
                                                <p class="tit">접수기간</p>
                                                <p class="txt">${rtnRoundDtl.accsStrtDtm} ~ ${rtnRoundDtl.accsEndDtm}</p>
                                            </div>
                                            <div class="list">
                                                <p class="tit">사업기간</p>
                                                <p class="txt">${kl:convertDate(rtnRoundDtl.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy.MM.dd', '')} - ${kl:convertDate(rtnRoundDtl.bsnEndDtm, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="btn-wrap">
                                    <c:if test="${not empty rtnRoundDtl.fileSeq}">
                                        <div class="btn-set">
                                            <a class="btn-solid small gray-bg has-icon download" href="/file/download?fileSeq=${rtnRoundDtl.fileSeq}&fileOrd=1" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
                                        </div>
                                    </c:if>
                                    <div class="btn-set">
                                        <a class="btn-solid small black-bg apply" href="javascript:" data-episd='${rtnRoundDtl.episdSeq}'><span>신청하기</span></a>
                                    </div>
                                </div>
                                <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                            </div>
                        </div>
                    </div>
                </div>

            </c:if>

                <c:if test="${not empty rtnRoundDtl.fileSeq}">
                    <input type="hidden" class="optnFile" value="${item.fileSeq}">
                </c:if>
            </div>
        </div>
    </div>
</div>
    <!-- content 영역 end -->