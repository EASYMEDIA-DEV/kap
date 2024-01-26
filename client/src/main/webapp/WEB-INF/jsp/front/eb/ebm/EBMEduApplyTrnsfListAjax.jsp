<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>

        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
            <table class="basic-table w864"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                <caption>회원목록</caption>
                <colgroup>
                    <col style="width: 84rem;">
                    <col style="width: 180rem;">
                    <col style="width: 324rem;">
                    <col style="width: 180rem;">
                    <col style="width: 324rem;">
                </colgroup>
                <thead>
                <tr>
                    <th></th>
                    <th>성명</th>
                    <th>아이디</th>
                    <th>핸드폰번호</th>
                    <th>이메일</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                        <tr data-total-count="${rtnData.totalCount}">
                            <td class="t-align-center">
                                <div class="form-radio">
                                    <input type="radio" class="notRequired memRadio" id="memListeRadio${status.count}" name="memListeRadioSet" value="${list.memSeq}" data-gndr="${list.gndr}" data-id="${list.id}">
                                    <label for="memListeRadio${status.count}"></label>
                                </div>
                            </td>

                            <td class="t-align-center">${kl:nameMasking(list.name)}</td>
                            <td class="t-align-center">${kl:idMasking(list.id)}</td>
                            <td class="t-align-center">${kl:phoneMasking(list.hpNo)}</td>
                            <td class="t-align-center">${ kl:emailMasking(list.email) }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>



    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>
        <div class="no-data-area has-border">
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>

