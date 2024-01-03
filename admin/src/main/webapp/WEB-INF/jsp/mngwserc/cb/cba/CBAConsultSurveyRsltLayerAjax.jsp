<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>    <%--번호--%>
                <td class="text-center">${ list.bsnYear }</td>                                              <%--사업연도--%>
                <td class="text-center">${ list.cmpnCnt }</td>                                              <%--대상부품사 개수--%>
                <td class="text-center">${ list.srvCmpltCmpnCnt }</td>                                      <%--설문완료부품사 개수--%>
                <td class="text-center">${ list.rglrCmpnCnt }</td>                                          <%--정규부품사 개수--%>
                <td class="text-center">${ list.shrtCmpnCnt }</td>                                          <%--단기부품사 개수--%>
                <td class="text-center">${ list.cmssrCnt }</td>                                             <%--담당위원 명수--%>
                <td class="text-center">${ list.ttlAvgScore }</td>                                          <%--총합점수평균--%>
                <td class="text-center">${ list.rglrAvgScore }</td>                                         <%--정규점수평균--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--단기점수평균--%>
                <td class="text-center btnExcelDown" data-bsnyear="${ list.bsnYear }"><button type="button" class="btn btn-inverse btn-sm mb-sm btnExcelDown">엑셀다운로드</button></td>                                                     <%--엑셀다운로드--%>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="26" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>