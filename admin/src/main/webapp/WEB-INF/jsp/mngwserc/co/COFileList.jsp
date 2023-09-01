<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:if test="${not empty rtnList}">
    <c:forEach var="list" items="${rtnList}" varStatus="status">
        <div class="dz-preview dz-complete ${fn:startsWith(list.webPath, '/image/') ? 'dz-image-preview' : 'dz-file-preview'}">
            <div class="dz-image">
                <img <c:if test="${fn:startsWith(list.webPath, '/image/')}">src="${list.webPath}" alt="${list.fileDsc}"</c:if> />
            </div>
            <div class="dz-details">
                <div class="dz-size">
                    <span>
                        <c:choose>
                            <c:when test="${list.fileSize lt 1000000}">
                                <strong><fmt:formatNumber type="number" value="${list.fileSize / 1024}" maxFractionDigits="1" /></strong> KB
                            </c:when>
                            <c:otherwise>
                                <strong><fmt:formatNumber type="number" value="${list.fileSize / 1024 / 1024}" maxFractionDigits="1" /></strong> MB
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="dz-filename">
                    <a href="/mngwserc/file/view?fileSeq=${list.fileSeq}&fileOrd=${list.fileOrd}">
                        <span>${list.orgnFileNm}</span>
                    </a>
                </div>
            </div>
            <!-- 조회 파일 순번 -->
            <input type="hidden" class="notRequired" name="fileSeq" value="${list.fileSeq}" />
            <!-- 조회 파일 정렬 -->
            <input type="hidden" class="notRequired" name="fileOrd" value="${list.fileOrd}" />
            <!-- 조회 파일 이름 -->
            <input type="text" class="form-control input-sm notRequired" name="fileDsc" value="${list.fileDsc}" title="대체 텍스트" placeholder="대체 텍스트" maxlength="100" />
            <a href="javascript:" class="dz-remove dropzone_remove">Remove file</a>
        </div>
    </c:forEach>
</c:if>