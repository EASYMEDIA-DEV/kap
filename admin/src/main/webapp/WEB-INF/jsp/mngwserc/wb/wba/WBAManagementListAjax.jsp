<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
	<c:when test="${ not empty rtnData.list}">
		<c:forEach var="list" items="${rtnData.list}" varStatus="status">
			<tr data-total-count="${rtnData.totalCount}">
				<td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
				<td class="text-center">
					<a href="javascript:" class="listView" data-details-key="${list.bsnCd}">
							${list.manageName}
					</a>
				</td>
				<td class="text-center">${list.regName}(${list.regId})</td>
				<td class="text-center">${ kl:convertDate( list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</td>
				<td class="text-center">${list.modName}(${list.modId})</td>
				<td class="text-center">${ kl:convertDate( list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr data-total-count="0">
			<td colspan="16" class="text-center">
				검색결과가 없습니다.<br>
				(등록된 데이터가 없습니다.)
			</td>
		</tr>
	</c:otherwise>
</c:choose>

