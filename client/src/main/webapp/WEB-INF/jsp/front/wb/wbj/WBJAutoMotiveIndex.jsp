<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbj/WBJAutoMotiveCtrl">
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
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-san-reward-car.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-san-reward-car-mobile.jpg" alt="">
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
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-apply-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">자동차부품산업대상 신청은 온라인으로 접수 받으며, 신청절차에 따라서 해당 항목을 입력하시고 부문별 신청서를 첨부해주시면 됩니다. <br />신청 후 1차 심의, 2차 현장실사, 3차 심의를 거쳐 시상자를 선정하게됩니다.</p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg apply" href="javascript:" data-episd='${rtnData.episdSeq}'><span>신청하기</span></a>
                                    </div>
                                </div>

                                <div class="divide-flex-box download">
                                    <div class="left">
                                        <p class="f-body2">자동차부품산업대상 신청 시 부문별 신청서가 필수로 첨부되어야합니다.</p>
                                        <p class="f-caption2">* [양식 다운로드] 후 양식에 맞게 작성 후 첨부 부탁드립니다.</p>
                                    </div>
                                    <div class="right has-button">
                                        <div class="btn-wrap">
                                        <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                                            <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${item.fileSeq}&fileOrd=${item.optnOrd}" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
                                        </c:forEach>
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
                            <p class="f-title3">자동차부품산업대상 Q&A 이용안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-inquiry-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">자동차부품산업대상 관련 궁금한 점을 상시 질문할 수 있도록 Q&A게시판을 운영하고 있습니다. <br />게시판에 질문을 남겨주시면 관리자에게 전달되며 답변을 얻을 수 있습니다.</p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg" href="/foundation/cs/qa/index?inqFir=INQ07&inqSec=자동차부품산업대상"><span>신청 문의하기</span></a>
                                    </div>
                                </div>
                                <p class="noti-txt f-caption2">* 다만 문의 사항이 많아 답변이 다소 늦어질 수 있으니 양해 바랍니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">추가공지사항</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="txt-sec">
                                <div class="paragraph">
                                    <p class="f-sub-head">${rtnData.addNtfyCntn}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <c:if test="${not empty rtnData}">
            <!-- 자동차 부품 산업대상 사업 접수 하단 플로팅 영역 -->
            <div class="accepting-fixed-area">
                <div class="for-position">
                    <button class="open-click-area" type="button">
                        <p class="tit"><span>${rtnData.year} 자동차부품산업대상 접수중</span></p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1">${rtnData.year} 자동차사부품산업대상 접수중</p>
                            </div>
                            <div class="con-area">
                                <div class="scroll-area">
                                    <div class="info-line-list-w">
                                        <div class="list">
                                            <p class="tit">접수기간</p>
                                            <p class="txt">${rtnData.accsStrtDtm} ~ ${rtnData.accsEndDtm}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="btn-wrap">
                                <div class="btn-set">
                                    <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                                        <a class="btn-solid small gray-bg has-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${item.fileSeq}&fileOrd=${item.optnOrd}" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
                                    </c:forEach>
                                </div>
                                <div class="btn-set">
                                    <a class="btn-solid small black-bg apply" href="javascript:" data-episd='${rtnData.episdSeq}'><span>신청하기</span></a>
                                </div>
                            </div>
                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty rtnRoundDtl.optnList}">
                <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                    <input type="hidden" class="optnFile" value="${item.fileSeq}">
                </c:forEach>
            </c:if>
        </c:if>
    </div>
</div>
</div>
<!-- content 영역 end -->
