<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
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
                ${rtnCms.cnts}

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
                                        <a class="btn-solid small white-bg <c:if test="${empty loginMap}">techApplication</c:if>"
                                            <c:choose>
                                                <c:when test="${not empty loginMap}">
                                                    href="./application"
                                                </c:when>
                                                <c:otherwise>
                                                    href="javascript:"
                                                </c:otherwise>
                                            </c:choose>><span>신청하기</span></a>
                                    </div>
                                </div>

                                <div class="divide-flex-box download">
                                    <div class="left">
                                        <p class="f-body2">상주경영컨설팅 신청 시 회사소개서(자율 양식)와 개선활동 추진계획서가 필수로 첨부되어야 합니다.</p>
                                        <p class="f-caption2">* 개선활동 추진계획서는 다운로드 후 양식에 맞게 작성 후 첨부 부탁드립니다.</p>
                                    </div>
                                    <div class="right has-button">
                                        <div class="btn-wrap">
                                            <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${fileData.mngmntFileSeq}&fileOrd=${fileData.mngmntFileOrd}" download><span>개선활동 추진계획서 다운로드</span></a>
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
                                        <a class="btn-solid small white-bg" href="javascript:" id="goContact" data-seq="${loginMap.seq}"><span>신청 문의하기</span></a>
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
                                        <a class="list-item popOpen" href="javascript:" title="팝업 열기">
                                            <div class="bg">
                                                <img src="${csList.webPath}" alt="">
                                            </div>
                                            <input type="hidden" class="memSeq" value="${csList.memSeq}">
                                            <div class="txt-box">
                                                <div class="names">
                                                    <p class="name f-title3">${csList.name}</p>
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
                        <p class="tit"><c:out value="${sysYear}" /> 상주경영컨설팅 <span class="status">접수중</span></p>
                        <div class="btn-text-icon plus"><span>더보기</span></div>
                    </button>

                    <div class="hide-area">
                        <div class="inner-con">
                            <div class="tit-area">
                                <p class="f-title1"><c:out value="${sysYear}" /> 상주경영컨설팅 접수중 (상시접수중)</p>
                            </div>
                            <!-- 2024-01-24 첨부파일 영역 추가 -->
                            <div class="con-area">
                                <div class="scroll-area">
                                    <div class="info-line-list-w">
                                        <div class="list">
                                            <p class="tit">첨부파일</p>
                                            <div class="txt">
                                                <div class="btn-wrap">
                                                    <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${fileData.mngmntFileSeq}&fileOrd=${fileData.mngmntFileOrd}" download><span>개선활동 추진계획서 다운로드</span></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- // 2024-01-24 첨부파일 영역 추가 -->

                            <div class="btn-wrap">
                                <div class="btn-set">
                                    <!-- <a class="btn-solid small gray-bg has-icon download" href="javascript:" download title="개선활동 추진계획서 다운로드"><span>개선활동 추진계획서 다운로드</span></a> --><!-- 2024-01-24 다운로드 버튼 삭제-->
                                </div>
                                <div class="btn-set">
                                    <a class="btn-solid small black-bg <c:if test="${empty loginMap}">techApplication</c:if>"
                                        <c:choose>
                                            <c:when test="${not empty loginMap}">
                                                href="./application"
                                            </c:when>
                                            <c:otherwise>
                                                href="javascript:"
                                            </c:otherwise>
                                        </c:choose>><span>신청하기</span></a>
                                </div>
                            </div>

                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
    <div class="layer-popup memberDetailsPopup">
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">위원 상세</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <div class="article-list-w txt-list">
                                        <dl class="list-item">
                                            <dt class="f-body1">전문위원</dt>
                                            <dd class="f-body2 cmssrName"></dd>
                                        </dl>
                                        <dl class="list-item">
                                            <dt class="f-body1">이메일</dt>
                                            <dd class="f-body2 email"></dd>
                                        </dl>
                                        <dl class="list-item">
                                            <dt class="f-body1">업종/분야</dt>
                                            <dd class="f-body2">
                                                <p class="box-label cmssrCbsnCd"><span></span></p>
                                            </dd>
                                        </dl>
                                        <dl class="list-item">
                                            <dt class="f-body1">경력</dt>
                                            <dd class="f-body2 cmssrMjrCarerCntn">
                                                <p></p>
                                            </dd>
                                        </dl>
                                        <dl class="list-item">
                                            <dt class="f-body1">컨설팅 분야</dt>
                                            <dd class="f-body2 cmssrCnstgFldCntn">
                                                <p></p>
                                                <p></p>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap align-right">
                            <a class="btn-solid small black-bg" href="javascript:" data-seq="${loginMap.seq}" id="goQa"><span>위원 문의</span></a>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>