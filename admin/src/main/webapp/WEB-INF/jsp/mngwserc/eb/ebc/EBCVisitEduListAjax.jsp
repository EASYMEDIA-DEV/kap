<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${list.edctnSttsName eq 'ASIS_CH001'}" >
                            미완료(주제불일치)
                        </c:when>
                        <c:otherwise>
                            ${list.edctnSttsName}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.vstSeq}" data-mem-seq="${list.memSeq}" data-vst-rslt-seq="${list.vstRsltSeq}" data-appctn-bsnm-no="${list.appctnBsnmNo}">
                            ${list.cmpnNm}
                    </a>
                </td>
                <td class="text-center">${kl:bsnmNoConvert(list.appctnBsnmNo)}</td>
                <td class="text-center">${list.ctgryName}</td>
                <td class="text-center">${list.sizeName}</td>
                <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}</td>
                <td class="text-center">${list.appctnFldName}</td>
                <td class="text-center">${kl:convertDate(list.hopeDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</td>
                <td class="text-center">${list.edctnPlaceAddr}</td>
                <td class="text-center">${list.ptcptCnt}</td>
                <td class="text-center">${list.ptcptHhNum}</td>
                <td class="text-center">${list.name}<br>(${kl:idMasking(list.id)})</td>
                <td class="text-center">${kl:phoneMasking(list.hpNo)}</td>
                <td class="text-center">${kl:emailMasking(list.email)}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.rsltEndYnNm }">
                            ${list.rsltEndYnNm}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.cnfrmdTheme }">
                            ${list.cnfrmdTheme}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.edctnStrtDtm and not empty list.edctnEndDtm}">
                            ${kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} ~ ${kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.cmptnCnt }">
                            ${list.cmptnCnt}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.modId }">
                            ${list.modName}(${list.modId})
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.modDtm }">
                            ${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="23" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

