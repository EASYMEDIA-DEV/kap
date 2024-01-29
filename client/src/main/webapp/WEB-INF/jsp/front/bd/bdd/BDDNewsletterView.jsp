<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<div id="wrap" data-controller="controller/bd/bdd/BDDNewsletterViewCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="post" >
        <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.nwsltSeq}" />
        <div class="cont-wrap">
            <div class="inner">
                <div class="cont-sec-w">
                    <div class="cont-sec">
                        <div class="view-sec">
                            <div class="title-area">
                                <p class="title f-title1">${rtnDto.titl}</p>
                                <p class="date f-body1">${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p>
                            </div>
                            <div class="body-area">
                                <div class="txt-wrap f-body2">
                                    ${rtnDto.cntn}
                                </div>
                                <!-- @ 파일이 없으면 주석처리 -->
                                <c:choose>
                                    <c:when test="${not empty fileList.list}">
                                        <div class="attatched-file-area">
                                            <c:forEach var="fileList" items="${fileList.list}">
                                                <a class="btn-text-icon download-bg download" href="/file/download?fileSeq=${fileList.fileSeq}&fileOrd=${fileList.fileOrd}" title="파일 다운로드" download><span>${fileList.fileName}</span></a>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="page-move">
                                <c:choose>
                                    <c:when test="${not empty nextPrevInfo.nextSeq}">
                                        <a class="has-article next-btn f-body2" href="javascript:" id="nextBtn" data-next-seq="${nextPrevInfo.nextSeq}"><span class="status">다음</span><span class="tit">${nextPrevInfo.nextTitl}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="next-btn f-body2" href="javascript:"><span class="status">다음</span><span class="tit">다음글이 없습니다.</span></a>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty nextPrevInfo.prevSeq}">
                                        <a class="has-article prev-btn f-body2" href="javascript:" id="prevBtn" data-prev-seq="${nextPrevInfo.prevSeq}"><span class="status">이전</span><span class="tit">${nextPrevInfo.prevTitl}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="prev-btn f-body2" href="javascript:"><span class="status">이전</span><span class="tit">이전글이 없습니다.</span></a>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="btn-wrap align-center add-load">
                                <a class="btn-solid small black-bg" id="listBtn" href="javascript:" data-str-pam="${strPam}"><span>목록</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>