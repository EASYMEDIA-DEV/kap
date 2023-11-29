<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.cxstnSrvSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>

                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center" > ${fn:substring(list.year,0,4)}</td>
                <td class="text-center" > ${list.episd}</td>
                <td class="text-center" > ${list.partCmpnNm1}</td>
                <td class="text-center" > ${list.partCmpnCd1}</td>
                <td class="text-center" >
                    <a href="javascript:" class="listView"  data-details-key="${list.cxstnSrvSeq}">
                            ${list.partCmpnNm2}
                    </a>
                </td>
                <td class="text-center"> ${list.partCmpnCd2}</td>
                <td class="text-center"> ${ list.ptcptCd eq 'E' ? '대기' : list.ptcptCd eq 'N' ? '미참여' : '참여' }</td>
                <td class="text-center"></td>
                <td class="text-center"></td>
                <td class="text-center"></td>
                <td class="text-center"></td>
                <td class="text-center">${ kl:convertDate(list.ptcptCmpltnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } </td>
                <td class="text-center" >${ list.regName }</td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" >${ list.modName }</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${ kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="18" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

