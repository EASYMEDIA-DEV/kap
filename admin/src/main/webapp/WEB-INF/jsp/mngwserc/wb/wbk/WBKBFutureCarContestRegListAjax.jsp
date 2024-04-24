<%@ page import="java.util.Calendar" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.appctnSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.appctnSeq}">${list.year}</a></td>
                <td class="text-center">${ list.name }</td>
                <td class="text-center">${ list.ptcptTypeNm}</td>
                <td class="text-center">${ list.themeCdNm }</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ list.wdcrmCd eq 'WBK_AWD01'}">대상</c:when>
                        <c:when test="${ list.wdcrmCd eq 'WBK_AWD02'}">최우수상</c:when>
                        <c:when test="${ list.wdcrmCd eq 'WBK_AWD03'}">우수상</c:when>
                        <c:when test="${ list.wdcrmCd eq 'WBK_AWD04'}">장려상</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ kl:decode(list.docResultNm, "", "접수완료" , list.docResultNm) }</td>
                <td class="text-center">${ kl:decode(list.FResultNm, "", "접수전" , list.FResultNm) }</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${list.LResultCd eq 'WBKB_REG_LRT001'}">수상</c:when>
                        <c:when test="${list.LResultCd eq 'WBKB_REG_LRT002'}">통과</c:when>
                        <c:when test="${list.LResultCd eq 'WBKB_REG_LRT003'}">탈락</c:when>
                        <c:when test="${list.LResultCd eq 'WBKB_REG_LRT004'}">사용자취소 </c:when>
                        <c:otherwise>접수전</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ list.hpNo }</td>
                <td class="text-center">${ list.email }</td>
                <%--<td class="text-center">${ list.regId}</td>
                <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }</td>--%>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${ list.modId }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ kl:decode(list.modDtm, "", "-", kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '')) }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="14" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

