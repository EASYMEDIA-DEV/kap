<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbg/WBGAExamCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <c:set var="now" value="<%=new java.util.Date()%>" />
    <c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
    <input type="hidden" id="pmt" value="${rtnRoundDtl.stndSlsPmt}" />
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">시험계측장비</span></p>
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
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">Q&A 이용안내</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="box-btn-area">
                                    <div class="bg-area">
                                        <div class="img" style="background-image: url('/common/images/img-inquiry-btn-bg.jpg');"></div>
                                    </div>
                                    <div class="txt-area">
                                        <p class="txt f-head">사업 신청 관련 도움을 드리기 위해 Q&A 게시판을 운영하고 있습니다.<br/>질문을 남겨주시면 빠른 시일 내에 답변드리겠습니다.</p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small white-bg" href="/foundation/cs/qa/index?inqFir=INQ07&inqSec=시험계측장비"><span>신청 문의하기</span></a>
                                    </div>
                                </div>
                                <p class="noti-txt f-caption2">* 다만 문의 사항이 많아 답변이 다소 늦어질 수 있으니 양해 바랍니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 접수중 하단 플로팅 영역 -->
            <div class="accepting-fixed-area">
                <div class="for-position">
                    <button class="open-click-area" type="button">
                        <p class="tit">${sysYear} 시험계측장비 사업 접수중 (상시접수중)</p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1">${sysYear} 시험계측장비 사업 접수중 (상시접수중)</p>
                            </div>

                            <div class="con-area">
                                <div class="scroll-area">
                                    <div class="info-line-list-w">
                                        <div class="list">
                                            <p class="tit">첨부파일</p>
                                            <div class="txt">
                                                <!-- 2024-02-13 첨부파일전체다운로드 추가로 인한 마크업 변경 -->
                                                <div class="btn-wrap">
                                                    <div class="btn-set">
                                                        <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${rtnRoundDtl.fileSeq}&fileOrd=${rtnRoundDtl.fileOrd}" download><span>${rtnRoundDtl.examFileNm}</span></a>
                                                    </div>
                                                </div>
                                                <!-- // 2024-02-13 첨부파일전체다운로드 추가로 인한 마크업 변경 -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="btn-wrap">
                                <div class="btn-set">
                                    <%--<a class="btn-solid small gray-bg has-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${rtnRoundDtl.fileSeq}&fileOrd=${rtnRoundDtl.fileOrd}" download title="양식 다운로드"><span>양식 다운로드</span></a>--%>
                                </div>
                                <div class="btn-set">
                                    <a class="btn-solid small black-bg apply" href="javascript:"><span>신청하기</span></a>
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
