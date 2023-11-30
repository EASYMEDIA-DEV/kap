<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.vstSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.vstSeq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">교육결과값</td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.vstSeq}">
                            ${list.cmpnNm}
                    </a>
                </td>
                <td class="text-center">${list.ctgryName}</td>
                <td class="text-center">${list.cmpnSizeName}</td>
                <td class="text-center">${list.appctnBsnmNo}</td>
                <td class="text-center">${list.bscAddr}</td>
                <td class="text-center">${list.slsPmt}</td>
                <td class="text-center">${list.mpleCnt}</td>
                <td class="text-center">${list.firstRgnsName} ${list.scndRgnsName}</td>
                <td class="text-center">교육상태값</td>
                <td class="text-center">${list.appctnCnt}</td>
                <td class="text-center">${list.ptcptHhNum}</td>
                <td class="text-center">${list.hopeDt}</td>
                <td class="text-center">${list.edctnStrtDt}</td>
                <td class="text-center">${list.edctnEndDt}</td>
                <td class="text-center">${list.ptcptCnt}</td>
                <td class="text-center">${list.memName}(${list.memId})</td>
                <td class="text-center">${list.memHpNo}</td>
                <td class="text-center">${list.memEmail}</td>
                <td class="text-center">${list.regDtm}</td>
                <td class="text-center">${list.modName}(${list.modId})</td>
                <td class="text-center">${list.modDtm}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="24" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

