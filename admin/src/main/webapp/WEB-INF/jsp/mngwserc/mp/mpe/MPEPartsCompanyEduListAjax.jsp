<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty eduList}">
        <tr>
            <th scope="row">품질아카데미</th>
            <c:forEach  var="list" items="${eduList.list}" varStatus="status">
                <td class="text-center">${list.edu1}</td>
            </c:forEach>
        </tr>
        <tr>
            <th scope="row">제조/경영혁신</th>
            <c:forEach  var="list" items="${eduList.list}" varStatus="status">
                <td class="text-center">${list.edu2}</td>
            </c:forEach>
        </tr>
        <tr>
            <th scope="row">세미나</th>
            <c:forEach  var="list" items="${eduList.list}" varStatus="status">
                <td class="text-center">${list.edu3}</td>
            </c:forEach>
        </tr>
        <tr>
            <th scope="row">방문교육</th>
            <c:forEach  var="list" items="${eduList.list}" varStatus="status">
                <td class="text-center">${list.edu4}</td>
            </c:forEach>
        </tr>
        <tr>
            <th scope="row">소계</th>
            <c:forEach  var="list" items="${eduList.list}" varStatus="status">
                <td class="text-center">${list.sumedu}</td>
            </c:forEach>
        </tr>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="6" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
