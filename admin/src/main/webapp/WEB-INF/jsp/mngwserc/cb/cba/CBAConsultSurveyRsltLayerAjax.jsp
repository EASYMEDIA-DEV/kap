<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>    <%--번호--%>
                <td class="text-center">${ list.bsnYear }</td>                                              <%--사업연도--%>
                <td class="text-center">${ list.cmpnCnt }</td>                                              <%--부품사명--%>
                <td class="text-center">${ list.srvCmpltCmpnCnt }</td>                                      <%--담당위원--%>
                <td class="text-center">${ list.rglrCmpnCnt }</td>                                          <%--참여자--%>
                <td class="text-center">${ list.shrtCmpnCnt }</td>                                          <%--신청업종/분야--%>
                <td class="text-center">${ list.cmssrCnt }</td>                                             <%--지도구분--%>
                <td class="text-center">${ list.ttlAvgScore }</td>                                          <%--상태--%>
                <td class="text-center">${ list.rglrAvgScore }</td>                                         <%--랩업일--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--총점(100)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--지도실적(50)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--의사소통(5)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--기획력(10)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--실행력(15)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--마인드(5)--%>
                <td class="text-center">${ list.shrtAvgScore }</td>                                         <%--전문지식(15)--%>
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