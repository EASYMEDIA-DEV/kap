<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<% pageContext.setAttribute("newLine", "\n"); %>
<div class="cont-wrap" data-controller="controller/co/COFormCtrl controller/mp/mpg/MPGMyQaViewCtrl">

    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <c:set var="codeName" value="" />
        <c:set var="statusType" value="" />
        <c:choose>
            <c:when test="${ rtnData.rsumeCd eq 'SYN' }">
                <c:set var="codeName" value="접수대기" />
                <c:set var="statusType" value="" />
            </c:when>
            <c:when test="${ rtnData.rsumeCd eq 'SYNACK' }">
                <c:set var="codeName" value="접수완료" />
                <c:set var="statusType" value="waiting" />
            </c:when>
            <c:when test="${ rtnData.rsumeCd eq 'ACK' }">
                <c:set var="codeName" value="답변완료" />
                <c:set var="statusType" value="complete" />
            </c:when>
        </c:choose>
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="answer-sec">
                            <div class="question-wrap">
                                <div class="title-area">
                                    <div class="title f-title3"><p class="box-label bigger ${ statusType }"><span>${ codeName }</span></p>${ rtnData.titl }</div>
                                    <p class="date f-body2">${ kl:convertDate(rtnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</p>
                                </div>
                                <div class="body-area">
                                    <div class="txt-wrap f-sub-head">
                                        ${fn:replace(rtnData.cntn, newLine, '<br>')}
                                    </div>
                                    <c:if test="${ not empty rtnData.fileList }">
                                    <!-- @ 파일이 없으면 주석처리 -->
                                    <div class="attatched-file-area">
                                        <c:forEach var="file" items="${rtnData.fileList}" varStatus="status">
                                            <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${ file.fileSeq }&fileOrd=${ status.index }" title="파일 다운로드" download><span>${ file.orgnFileNm }</span></a>
                                        </c:forEach>
                                    </div>
                                    </c:if>
                                </div>
                            </div>
<%--                            <c:if test="${ not empty rtnData.rplyFileSeq }">--%>
                                <c:if test="${ rtnData.rsumeCd eq 'ACK'  }">
                            <div class="answer-wrap">
                                <div class="title-area">
                                    <div class="title f-title3">답변</div>
                                    <p class="date f-body2">${ kl:convertDate(rtnData.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</p>
                                </div>
                                <div class="body-area">
                                    <p class="f-sub-head">
                                        ${ rtnData.rplyCntn }
                                    </p>
                                    <c:if test="${ not empty rtnData.rplyFileList }">
                                    <!-- @ 파일이 없으면 주석처리 -->
                                    <div class="attatched-file-area">
                                        <c:forEach var="rplyFile" items="${rtnData.rplyFileList}" varStatus="status">
                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${ rplyFile.fileSeq }&fileOrd=${ status.index }" title="파일 다운로드" download><span>${ rplyFile.orgnFileNm }</span></a>
                                        </c:forEach>
                                    </div>
                                    </c:if>
                                </div>
                            </div>
                            </c:if>
                            <!-- 2023-12-12 삭제
                            <div class="page-move">
                              // @ 게시글이 있을 경우 has-article 클래스 추가
                              <a class="next-btn f-body2" href="javascript:"><span class="status">다음</span><span class="tit">다음글이 없습니다.</span></a>
                              <a class="has-article prev-btn f-body2" href="javascript:"><span class="status">이전</span><span class="tit">2023년 하반기 자문위원 채용 공고</span></a>
                            </div>
                            // 2023-12-12 삭제 -->
                            <div class="btn-wrap align-center add-load">
                                <a class="btn-solid small black-bg" href="./list?${strPam}"><span>목록</span></a><!-- 2023-12-06 텍스트 수정 -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>