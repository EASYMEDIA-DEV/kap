<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
	<c:when test="${rtnData.pageIndex eq 1}">
        <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
        <input type="hidden" id="totalCount" name="totalCount" value="${ rtnData.totalCount }" />
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <div class="list-item" data-total-count="${rtnData.totalCount}" data-details-key="${list.faqSeq}">
                <a class="acco-click-area" href="javascript:">
                    <div class="sub-info-wrap">
                        <span class="f-body2">${list.ctgryName}</span>
                    </div>
                    <div class="txt-box">
                        <p class="tit f-title3">${list.titl}</p>
                    </div>
                </a>
                <div class="acco-hide-area">
                    <p class="txt f-sub-head">
                            ${list.cntn}
                    </p>
                    <c:if test="${not empty list.fileSeq}">
                        <div class="attatched-file-area">
                            <c:forEach var="fileList" items="${fileList}" varStatus="status">
                                <c:if test="${not empty fileList.fileSeq}">
                                    <c:if test="${list.faqSeq eq fileList.faqSeq}">
                                        <a class="btn-text-icon download-bg" href="/file/download?fileSeq=${fileList.fileSeq}&fileOrd=${fileList.fileOrd}" title="파일 다운로드" download><span>${fileList.fileName}</span></a>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="no-data-area">
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>
