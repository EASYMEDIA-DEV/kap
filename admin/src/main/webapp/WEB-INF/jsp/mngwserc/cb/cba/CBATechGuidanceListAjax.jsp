<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.cnstgSeq}" name="delValueList" class="checkboxSingle notRequired" data-edctn_seq="${list.cnstgSeq}" data-rsumeCd="${list.rsumeSttsCd}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>                                           <%--번호--%>
                <td class="text-center">${ list.bsnYear }</td>                                                                                     <%--사업연도--%>
                <td class="text-center">${ list.rsumeSttsNm }</td>                                                                                 <%--진행상태--%>
                <td class="text-center"><a href="javascript:" class="listView" data-details-key="${list.cnstgSeq}">${ list.cmpnNm }</a></td>       <%--부품사명--%>
                <td class="text-center">${kl:bsnmNoConvert(list.appctnBsnmNo)}</td>                                                                <%--사업자등록번호--%>
                <td class="text-center">${ list.ctgryNm }</td>                                                                                     <%--구분--%>
                <td class="text-center">${ list.sizeNm }</td>                                                                                      <%--규모--%>
                <td class="text-center">${ list.slsPmt }</td>                                                                                      <%--매출액(억원)--%>
                <td class="text-center">${ list.mpleCnt }</td>                                                                                     <%--직원수--%>
                <td class="text-center">${ list.cbsnNm }</td>                                                                                      <%--신청업종--%>
                <td class="text-center">${ list.firstRgnsNm } ${ list.scndRgnsNm }</td>                                                            <%--신청소재지--%>
                <td class="text-center">${ empty list.crtfnCmpnNm ? '-' : list.crtfnCmpnNm}</td>                                                   <%--SQ 인증 주관사--%>
                <td class="text-center">${ list.appctnDt }</td>                                                                                    <%--신청일--%>
                <td class="text-center">${ empty list.vstDt ? '-' : list.vstDt }</td>                                                              <%--방문일--%>
                <td class="text-center">${ empty list.cmssrNm ? '-' : list.cmssrNm}</td>                                                           <%--담당위원--%>
                <td class="text-center">${ empty list.vstCnt ? '-' : list.vstCnt}</td>                                                             <%--방문횟수--%>
                <td class="text-center">${ empty list.guideKickfDt ? '-' : list.guideKickfDt }</td>                                                <%--킥오프일--%>
                <td class="text-center">  <%--킥오프자료--%>
                    <c:choose>
                        <c:when test="${empty list.kickfFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.kickfFileSeq}&fileOrd=${list.kickfFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ empty list.guidePscndDt ? '-' : list.guidePscndDt}</td>                                                  <%--렙업일--%>
                <td class="text-center">                                                                                                            <%--렙업자료--%>
                    <c:choose>
                        <c:when test="${empty list.lvlupFileSeq}">
                            -
                        </c:when>
                        <c:otherwise>
                            <a href="/mngwserc/file/view?fileSeq=${list.lvlupFileSeq}&fileOrd=${list.lvlupFileOrd}"><img src="/common/images/fileicon.png"></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ list.regName }(${ list.regId })</td>                                                                       <%--최초 등록자(아이디)--%>
                <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd HH:mm', '-')}</td>                              <%--최초 등록일시--%>
                <td class="text-center">                                                                                                              <%--최종 수정자(아이디)--%>
                    <c:choose>
                        <c:when test="${empty list.modId}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${ list.modName }(${ list.modId })
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ empty kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd HH:mm', '-') ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd HH:mm', '-')}</td>   <%--최종 수정일시--%>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="26" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

