<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.appctnSeq}" name="delValueList" data-delete-Yn="${list.deleteYn}" class="checkboxSingle notRequired"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.year}</td>
                <td class="text-center">${list.episd}</td>
                <td class="text-center">${list.rsumeSttsCd}</td>
                <td class="text-center">${list.mngSttsCd}</td>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.appctnSeq}" data-bsnm-no="${list.bsnmNo}" data-mem-Seq="${list.memSeq}">${list.cmpnNm}</a></td>
                <td class="text-center">${list.ctgryCdNm}</td>
                <td class="text-center">${list.sizeCdNm}</td>
                <td class="text-center">${list.bsnmNo}</td>
                <td class="text-center">${kl:nameMasking(list.name)}(${kl:idMasking(list.id)})</td>
                <td class="text-center">${kl:phoneMasking(list.hpNo)}</td>
                <td class="text-center">${kl:emailMasking(list.email)}</td>
                <td class="text-center">${not empty list.mngSttsChngDtm ? list.mngSttsChngDtm : "-"}</td>
                <td class="text-center">${not empty list.appctnSttsChngDtm ? list.appctnSttsChngDtm : "-"}</td>
                <td class="text-center">${kl:nameMasking(list.modName)}<br>(${kl:idMasking(list.modId)})</td>
                <td class="text-center">${not empty list.modDtm ? list.modDtm : "-"}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="18" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

