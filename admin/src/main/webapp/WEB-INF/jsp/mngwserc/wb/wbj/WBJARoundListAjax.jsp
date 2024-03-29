<%@ page import="java.util.Calendar" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <fmt:formatNumber var="prizePmt" value="${list.prizePmt}" pattern="#,###"/>
            <tr data-total-count="${rtnData.totalCount}" >
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.episdSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">
                    <a href="javascript:" class="listView"  data-details-key="${list.episdSeq}">${list.year}</a>
                </td>
                <td class="text-center">${ kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ ${ kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center">${ kl:convertDate(list.bsnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') } ~ ${ kl:convertDate(list.bsnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center">${list.dateState}</td>
                <td class="text-center">${not empty list.nomCnt ? list.nomCnt : "-"}</td>
                <td class="text-center">${not empty list.regCnt ? list.regCnt : "-"}</td>
                <td class="text-center">${prizePmt}</td>
                <td class="text-center">${list.placeNm}</td>
                <td class="text-center">${ list.regName}</td>
                <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${ list.modName }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ kl:decode(list.modDtm, "", "-", kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '')) }</td>
                <td class="text-center">${ list.expsYn eq 'Y' ? '노출' : '미노출' }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="15" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

