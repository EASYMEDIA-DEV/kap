<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="t-align-center">
                    <div class="form-radio">
                        <input type="radio" class="memRadio" id="memListeRadio${status.count}" name="memListeRadioSet" value="${list.memSeq}" data-gndr="${list.gndr}" data-id="${list.id}">
                        <label for="memListeRadio${status.count}"></label>
                    </div>
                </td>

                <td class="t-align-center">${kl:nameMasking(list.name)}</td>
                <td class="t-align-center">${kl:idMasking(list.id)}</td>
                <td class="t-align-center">${kl:phoneMasking(list.hpNo)}</td>
                <td class="t-align-center">${ kl:emailMasking(list.email) }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>
        <tr data-total-count="0">
            <td colspan="4" class="text-center">
                <div class="txt-box">
                    <p class="txt f-body1">조회된 데이터가 없습니다.111</p>
                </div>
            </td>
        </tr>
    </c:otherwise>
</c:choose>

