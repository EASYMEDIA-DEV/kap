<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyyMMdd" /></c:set>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.ptcptSeq}" name="delValueList" class="checkboxSingle notRequired" data-ptcpt-seq="${list.ptcptSeq}" data-stts-cd="${ list.sttsCd }"/><%--체크박스--%>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ list.sttsName }</td>
                <td class="text-center">${ list.stdPrtCateNm } > ${ list.stdCateNm }</td>
                <td class="text-center" >
                    <a href="javascript:" class="listView"  data-details-key="${list.ptcptSeq}">
                            ${ list.stdName }
                    </a>
                </td>
                <td class="text-center">${ list.stduyMthdNm }</td>
                <td class="text-center">${ list.stduyDd }일/${ list.stduyTime }시간</td>
                <td class="text-center">${ kl:convertDate(list.episdYear, 'yyyy', 'yyyy', '-') }</td>
                <td class="text-center">${ list.episdOrd }회차</td>
                <td class="text-center">${ list.cbsnNm }</td>
                <td class="text-center">${ list.cmpnNm }</td>
                <td class="text-center">${ kl:bsnmNoConvert(list.ptcptBsnmNo) }</td>
                <td class="text-center">${ list.cmpnCateNm }</td>
                <td class="text-center">${ list.sizeNm }</td>
                <td class="text-center">${ list.addr }</td>
                <td class="text-center">${ kl:nameMasking(list.name) }<br/>(${ kl:idMasking(list.id) }}</td>
                <td class="text-center">${ list.gpcId }</td>
                <%--<td class="text-center">${ kl:phoneMasking(list.hpNo) }</td>--%>
                <td class="text-center">${ kl:phoneMasking(list.hpNo) }</td>
                <%--<td class="text-center">${ kl:emailMasking(list.email) }</td>--%>
                <td class="text-center">${ kl:emailMasking(list.email) }</td>
                <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>
                <td class="text-center">${ empty list.modName ? '-' : list.modName += '<br/>(' += list.modId += ')' }</td>
                <td class="text-center">${ empty list.modDtm ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="22" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

