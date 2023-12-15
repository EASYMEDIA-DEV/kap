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
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.year }</td>
                <td class="text-center">${list.rsumeSttsNm}</td>
                <td class="text-center">${list.mngSttsNm}</td>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.appctnSeq}" data-appctn-seq="${list.appctnSeq}" data-bsnm-no="${list.bsnmNo}" data-mem-seq="${list.memSeq}" >${list.cmpnNm }</a></td>

                <td class="text-center">${list.bsnmNo }</td>
                <td class="text-center">${list.ctgryNm}</td>
                <td class="text-center">${list.sizeNm}</td>
                <td class="text-center">${kl:nameMasking(list.name)}<br>(${kl:idMasking(list.id)})</td>
                <td class="text-center">${kl:phoneMasking(list.hpNo)}</td>
                <td class="text-center">${kl:emailMasking(list.email)}</td>

                <td class="text-center">${list.slsPmt}</td>
                <td class="text-center">${list.picCmssrNm}</td>


                <td class="text-center">
                    <c:choose>
                        <c:when test="${list.tchlgOrdMax eq 1}">
                            ${ list.tchlgNm }
                        </c:when>
                        <c:otherwise>
                            ${ list.tchlgNm } 외 ${ list.tchlgOrdMax -1 }
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${list.tchlgCntSum}</td>

                <td class="text-center">${list.nvstmPmt}</td> 투자금액
                <td class="text-center">${list.fndnSpprtPmt}</td> 재단 지원금
                <td class="text-center">${list.realGiveDt}</td> 실 지급일
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.regDtm}">
                            ${ list.regDtm }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.appctnSttsChngDtm}">
                            ${ list.appctnSttsChngDtm }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modId}">
                            ${kl:nameMasking(list.modNm)}<br>(${kl:idMasking(list.modId)})
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${list.modDtm }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="20" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

