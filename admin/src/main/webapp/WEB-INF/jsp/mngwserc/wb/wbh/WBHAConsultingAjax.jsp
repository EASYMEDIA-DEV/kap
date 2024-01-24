<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td><!-- 번호 -->
                <td class="text-center">${ list.bsnYear}</td><!-- 사업년도 -->
                <td class="text-center">${ list.cnstgNm}</td><!-- 사업구분 -->
                <td class="text-center">${ list.rsumeSttsNm}</td><!-- 진행상태 -->
                <td class="text-center">${ list.cmpnNm}</td><!-- 부품사명 -->
                <td class="text-center">${ list.ctgryNm}</td><!-- 구분 -->
                <td class="text-center">${ list.sizeNm}</td><!-- 규모 -->
                <td class="text-center">${ list.appctnBsnmNo}</td><!-- 사업자등록번호 -->
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.appctnFldNm}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${ list.appctnFldNm}
                        </c:otherwise>
                    </c:choose>
                </td><!-- 신청분야/업종 -->
                <td class="text-center">${ list.rgnsName}</td><!-- 신청 소재지 -->
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.cmssrNm}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${ list.cmssrNm}
                        </c:otherwise>
                    </c:choose>
                </td><!-- 담당위원 -->
                <td class="text-center">${ kl:convertDate(list.regDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</td><!-- 신청일 -->
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="13" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

