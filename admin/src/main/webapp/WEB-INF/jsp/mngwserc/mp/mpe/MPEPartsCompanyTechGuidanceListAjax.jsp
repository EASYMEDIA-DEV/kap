<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty techGuidanceList.list}">
        <c:forEach var="list" items="${techGuidanceList.list}" varStatus="status">
            <tr>
                <td class="text-center">${techGuidanceList.totalCount - techGuidanceList.firstIndex - status.index}</td>
                <td class="text-center">${list.cbsnNm}</td>
                <td class="text-center">${kl:emptyHypen(list.cmssrNm)}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${list.rsumeSttsNm eq '지도완료'}">
                            ${ kl:convertDate(list.guideBgnDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '') } ~ ${ kl:convertDate(list.guidePscndDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '') }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.initVstFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.initVstFileSeq}&fileOrd=${list.initVstFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.kickfFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.kickfFileSeq}&fileOrd=${list.kickfFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.lvlupFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.lvlupFileSeq}&fileOrd=${list.lvlupFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.itrdcFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.itrdcFileSeq}&fileOrd=${list.itrdcFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${empty list.fltyImpvmRate or list.fltyImpvmRate eq 0}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${list.fltyImpvmRate} %
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="10" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
