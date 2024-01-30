<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>


        <c:set var="floatingYn" value="N"/>
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <c:if test="${list.accsStatusOrder eq 1 && floatingYn eq 'N'}">
            <div>
                <input type="hidden" name="floatingEpisdSeq" id="floatingEpisdSeq" value="${list.episdSeq}"/>
                <input type="hidden" name="floatingEpisdOrd" id="floatingEpisdOrd" value="${list.episdOrd}"/>
                <input type="hidden" name="floatingAccsStrtDtm" id="floatingAccsStrtDtm" value="${kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')} ~ ${kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}"/>
                <input type="hidden" name="floatingEdctnStrtDtm" id="floatingEdctnStrtDtm" value="${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } ~ ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } (${list.stduyDdCdNm}일간)"/>
                <input type="hidden" name="floatingIsttrGroupName" id="floatingIsttrGroupName" value="${list.isttrGroupName}"/>
                <c:if test="${list.fxnumImpsbYn eq 'Y'}">
                    <input type="hidden" name="floatingFxnumImpsb" id="floatingFxnumImpsb" value="${list.fxnumCnt}명"/>
                </c:if>
                <c:if test="${list.fxnumImpsbYn eq 'N'}">
                    <input type="hidden" name="floatingFxnumImpsb" id="floatingFxnumImpsb" value="제한없음"/>
                </c:if>
                <input type="hidden" name="floatingRcrmtMthdCdNm" id="floatingRcrmtMthdCdNm" value="(${list.rcrmtMthdCdNm})"/>
                <c:choose>
                    <c:when test="${list.stduyMthdCd eq 'STDUY_MTHD02'}">
                        <input type="hidden" name="floatingPlaceNm" id="floatingPlaceNm" value="온라인"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="floatingPlaceNm" id="floatingPlaceNm" value="${list.placeNm}"/>
                    </c:otherwise>
                </c:choose>
                <input type="hidden" name="floatingStduyMthdCdNm" id="floatingStduyMthdCdNm" value="${list.stduyMthdCdNm}"/>
                <input type="hidden" name="floatingStduyMthdCd" id="floatingStduyMthdCd" value="${list.stduyMthdCd}"/>
                <input type="hidden" name="floatingStduyDdCdNm" id="floatingStduyDdCdNm" value="${list.stduyDdCdNm}일(${list.stduyTimeCdNm}시간)"/>
                <input type="hidden" name="floatingedctnNtctnFileSeq" id="floatingedctnNtctnFileSeq" value="${list.edctnNtctnFileSeq}"/>
                <input type="hidden" name="floatingedctnNtctnFileNm" id="floatingedctnNtctnFileNm" value="${list.edctnNtctnFileNm}"/>

                <c:set var="floatingYn" value="Y"/>
            </div>

            </c:if>
        </c:forEach>


        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <c:set var="accsStatusOrderClass" value=""/>
            <c:choose>
                <c:when test="${list.accsStatusOrder eq 1}">
                    <c:set var="accsStatusOrderClass" value="available accepting"/>
                </c:when>
                <c:when test="${list.accsStatusOrder eq 1}">
                    <c:set var="accsStatusOrderClass" value="available"/>
                </c:when>
                <c:otherwise>
                    <c:set var="accsStatusOrderClass" value=""/>
                </c:otherwise>
            </c:choose>
            <div class="list-item available ${accsStatusOrderClass}" data-episdSeq="${list.episdSeq}" data-episdYear="${list.episdYear}" data-episdOrd="${list.episdOrd}"><!-- available: 신청 가능한 회차 --><!-- accepting: 접수중 -->
                <c:if test="${list.accsStatusOrder < 3}"><!-- 접수대기와 접수중만 출력-->
                    <p class="available-label">
                        <span>신청 가능한 회차</span>
                    </p>
                </c:if>
                <div class="cont">
                    <div class="top-area">
                        <div class="left">
                            <div class="group">
                                <p class="index-num f-title3">${list.episdOrd}회차 ${accsStatusOrder}</p>
                                <div class="status-info-w">
                                    <c:if test="${not empty episdDto.cbsnCdNm}">
                                        <p class="box-label bigger"><span>${list.cbsnCdNm}</span></p>
                                    </c:if>

                                    <c:set var="accsStatusOrderClass" value=""/>

                                    <c:choose>
                                        <c:when test="${list.accsStatusOrder eq 1}">
                                            <c:set var="accsStatusOrderClass" value="accepting"/>
                                        </c:when>
                                        <c:when test="${list.accsStatusOrder eq 2}">
                                            <c:set var="accsStatusOrderClass" value="waiting"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="accsStatusOrderClass" value="end"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <p class="box-label bigger ${accsStatusOrderClass}"><span>${list.accsStatusNm}</span></p>
                                    <!--
                                      접수중: accepting
                                      접수대기: waiting
                                      접수마감: end
                                    -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cont-area">
                        <div class="btn-wrap">
                            <button class="btn-text-icon black-arrow popupPicPrevSet" type="button" data-picNm="${list.picNm}" data-picEmail="${list.picEmail}" data-picTelNo="${list.picTelNo}"><span>회차 담당자 문의</span></button>

                            <c:if test="${list.stduyMthdCd ne 'STDUY_MTHD01'}">
                                <button class="btn-text-icon black-arrow popupLctrPrevSet" type="button" data-episdYear="${list.episdYear}" data-episdOrd="${list.episdOrd}"><span>온라인 강의목차</span></button>
                            </c:if>

                        </div>
                        <div class="info-list-w">
                            <div class="info-list">
                                <p class="tit f-caption2">강사</p>
                                <p class="txt f-body2">
                                    ${list.isttrGroupName}
                                </p>
                            </div>
                            <div class="info-list">
                                <p class="tit f-caption2">정원</p>
                                <p class="txt f-body2">
                                    <c:if test="${list.fxnumImpsbYn eq 'Y'}">
                                        ${list.fxnumCnt}명
                                    </c:if>
                                    <c:if test="${list.fxnumImpsbYn eq 'N'}">
                                        제한없음
                                    </c:if>
                                    (${list.rcrmtMthdCdNm})
                                </p>
                            </div>
                            <div class="info-list">
                                <p class="tit f-caption2">교육장소</p>
                                <p class="txt f-body1">
                                    <c:choose>
                                        <c:when test="${list.stduyMthdCd eq 'STDUY_MTHD02'}">
                                            온라인
                                        </c:when>
                                        <c:otherwise>
                                            <a href="javascript:" class="mapBtn" data-mapchk="N" data-zipcode="${list.zipcode}" data-nm="${list.placeNm}" data-rprsntTelNo="${list.rprsntTelNo}" data-bscAddr="${list.bscAddr}" data-dtlAddr="${list.dtlAddr}" title="교육장 안내 팝업 열기">${list.placeNm}</a>
                                        </c:otherwise>
                                    </c:choose>

                                </p>
                            </div>
                            <div class="info-list">
                                <p class="tit f-caption2">접수기간</p>
                                <p class="txt f-body2">${kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')} ~ ${kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</p>
                            </div>
                            <div class="info-list">
                                <p class="tit f-caption2">교육기간</p>
                                <p class="txt f-body2">${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } ~ ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') } (${list.stduyDdCdNm}일간)</p>
                            </div>
                        </div>
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <c:if test="${not empty list.edctnNtctnFileSeq}">
                                    <%--<a class="btn-text-icon download" href="/file/view?fileSeq=${list.edctnNtctnFileSeq}&fileOrd=${list.fileOrd}"><span>안내문</span></a>--%>
                                    <a class="btn-text-icon download" href="/file/download?fileSeq=${list.edctnNtctnFileSeq}&fileOrd=${list.fileOrd}"><span>안내문</span></a>
                                </c:if>
                            </div>
                            <div class="btn-set">
                                <c:choose>
                                    <c:when test="${list.accsStatusOrder eq 1}"><!--접수중-->
                                        <a class="btn-solid small black-bg applyStep" href="javascript:" ><span>신청하기</span></a>
                                    </c:when>
                                    <c:when test="${list.accsStatusOrder eq 2}"><!--접수대기-->
                                        <a class="btn-solid small black-bg disabled" href="javascript:" ><span>접수대기</span></a>
                                    </c:when>
                                    <c:when test="${list.accsStatusOrder eq 3}"><!--마감-->
                                        <a class="btn-solid small black-bg disabled" href="javascript:" ><span>마감</span></a>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>

                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>

    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>
        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
            <div class="txt-box">
                <p class="txt f-body1">등록된 회차가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>