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
                    <a href="javascript:" class="listView" data-details-key="${list.vstSeq}" data-mem-seq="${list.memSeq}" data-vst-rslt-seq="${list.vstRsltSeq}" data-appctn-bsnm-no="${list.appctnBsnmNo}">
                            ${list.cmpnNm}
                    </a>
                </td>
                <td class="text-center">${list.appctnBsnmNo}</td>
                <td class="text-center">${list.ctgryName}</td>
                <td class="text-center">${list.sizeName}</td>
                <td class="text-center">${kl:convertDate(list.visitRegDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}</td>
                <td class="text-center">${list.appctnFldName}</td>
                <td class="text-center">${kl:convertDate(list.hopeDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</td>
                <td class="text-center">${list.ptcptCnt}</td>
                <td class="text-center">${list.ptcptHhNum}</td>
                <td class="text-center">${list.name}<br>(${kl:idMasking(list.id)})</td>
                <td class="text-center">${list.hpNo}</td>
                <td class="text-center">${list.email}</td>
                <td class="text-center">${list.edctnSttsName}</td>
                <td class="text-center">${list.cnfrmdTheme}</td>
                <td class="text-center">${kl:convertDate(list.edctnDtm, 'yyyy-MM-dd', 'yyyy.MM.dd', '')}</td>
                <td class="text-center">${list.cmptnCnt}</td>
                <td class="text-center">${list.modName}(${kl:idMasking(list.modId)})</td>
                <td class="text-center">${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}</td>
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

