<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <input type="hidden" name="totalCount" id="totalCount" value="${rtnData.totalCount}"/>
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">

            <c:choose>
                <c:when test="${list.eduGubun eq 'E'}">

                    <!--교육일수 차이 계산-->
                    <c:set var="dayVal" value=""/>
                    <c:choose>
                        <c:when test="${not empty list.edctnStrtDtm && not empty list.edctnEndDtm}">
                            <c:set var="strtDt" value="${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                            <c:set var="endDt" value="${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }"/>
                            <c:set var="dayVal" value="${kl:getDaysDiff(strtDt, endDt) + 1}"/>
                        </c:when>
                    </c:choose>


                    <div class="training-confirm episdDtl" data-episdseq="${list.episdSeq}" data-edctnseq="${list.edctnSeq}" data-episdyear="${list.episdYear}" data-episdord="${list.episdOrd}" data-ptcptSeq="${list.ptcptSeq}" data-trnsfYn="${list.trnsfYn}">
                        <div class="top-info">
                            <div class="training-view-page">
                                <div class="training-list">
                                    <div class="img-area">
                                        <c:if test="${not empty list.webPath}">
                                            <img src="${list.webPath}" alt="${list.fileDsc}">
                                        </c:if>
                                    </div>
                                    <div class="txt-area">
                                        <div class="top-line">
                                            <div class="sort-label-area">
                                                <p class="label"><span>${list.prntCdNm}</span></p>
                                                <p class="label"><span>${list.ctgryCdNm}</span></p>
                                            </div>
                                            <p class="training-name f-title3">
                                                <a href="javascript:">${list.nm}</a>
                                            </p>
                                        </div>
                                        <div class="group">
                                            <p class="f-head">${list.episdOrd}회차</p>
                                            <!-- 2024-05-17 추가 s -->
                                            <c:if test="${not empty list.cbsnCdNm}">
                                                <p class="box-label bigger"><span>${list.cbsnCdNm}</span></p>
                                            </c:if>
                                            <!-- 2024-05-17 추가 e -->
                                            <div class="status-info-w">
                                                <c:choose>
                                                    <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '교육대기' or list.eduStat eq '신청대기' or list.eduStat eq '협의중')}">
                                                        <p class="box-label bigger waiting">
                                                            <span>${list.eduStat eq '신청대기' ? '선발대기' : list.eduStat}</span>
                                                        </p>
                                                    </c:when>
                                                    <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '신청' or list.eduStat eq '신청취소' or list.eduStat eq '교육양도')}">
                                                        <p class="box-label bigger">
                                                            <span>${list.eduStat}</span>
                                                        </p>
                                                    </c:when>
                                                    <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '미선발' or list.eduStat eq '교육취소')}">
                                                        <p class="box-label bigger arr">
                                                            <span>${list.eduStat}</span>
                                                        </p>
                                                    </c:when>
                                                    <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '교육중')}">
                                                        <p class="box-label bigger accepting">
                                                            <span>${list.eduStat}</span>
                                                        </p>
                                                    </c:when>
                                                    <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '교육종료' or list.eduStat eq '교육완료')}">
                                                        <p class="box-label bigger complete">
                                                            <span>${list.eduStat}</span>
                                                        </p>
                                                    </c:when>
                                                    <c:when test="${list.trnsfYn eq 'Y'}"> <%-- 이관 된 경우 --%>
                                                        <p class="box-label bigger waiting">
                                                            <span>교육양도</span>
                                                        </p>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bot-info">
                            <div class="index-list-w">
                                <div class="list-item">
                                    <div class="cont">
                                        <div class="cont-area">
                                            <div class="info-list-w ">
                                                <div class="info-list">
                                                    <p class="tit f-caption2">학습방식</p>
                                                    <p class="txt f-body2">${list.stduyMthdCdNm}</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">신청일시</p>
                                                    <p class="txt f-body2">${ empty list.ptcptDtm ? '-' : kl:convertDate(list.ptcptDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">모집방식</p>
                                                    <p class="txt f-body2">${list.rcrmtMthdCdNm}</p>
                                                </div>
                                                <div class="info-list">
                                                    <c:choose>
                                                        <c:when test="${list.rcrmtMthdCd eq 'RCRMT_MTHD02'}">
                                                            <p class="tit f-caption2">선발여부</p>
                                                            <p class="txt f-body2">${list.sttsCdNm}</p>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">학습시간</p>
                                                    <p class="txt f-body2">${list.stduyDdCdNm}일/${list.stduyTimeCdNm}시간</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육장소</p>
                                                    <p class="txt f-body2">
                                                        <c:choose>
                                                            <c:when test="${list.stduyMthdCd eq 'STDUY_MTHD02'}">
                                                                온라인
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${list.placeNm}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육기간</p>
                                                    <p class="txt f-body2">
                                                            ${ empty list.edctnStrtDtm ? '-' : kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }
                                                        ~
                                                            ${ empty list.edctnEndDtm ? '-' : kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }(${dayVal}일간)
                                                    </p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">수료여부</p>
                                                    <%-- 2024-11-26 수료 방식 개편 s --%>
                                                    <p class="txt f-body2">
                                                        <c:choose>
                                                            <c:when test="${list.cmptnYn eq 'S'}">
                                                                이수
                                                            </c:when>
                                                            <c:when test="${list.cmptnYn eq 'Y'}">
                                                                수료
                                                            </c:when>
                                                            <c:when test="${list.cmptnYn eq 'U'}">
                                                                불참
                                                            </c:when>
                                                            <c:when test="${list.cmptnYn eq 'E'}">
                                                                중도퇴소
                                                            </c:when>
                                                            <c:otherwise>
                                                                미수료
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                    <%-- 2024-11-26 수료 방식 개편 e --%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="training-confirm visitEduDtl" data-edctnseq="${list.edctnSeq}" data-trnsfYn="${list.trnsfYn}">
                        <div class="top-info">
                            <div class="training-view-page">
                                <div class="training-list">
                                    <div class="txt-area">
                                        <div class="top-line">
                                            <div class="sort-label-area">
                                                <p class="label"><span>${list.ctgryCdNm}</span></p>
                                            </div>
                                            <p class="training-name f-title3"><a href="javascript:">${list.nm}</a></p>
                                        </div>
                                        <div class="status-info-w">

                                            <c:choose>
                                                <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '교육대기' or list.eduStat eq '신청대기' or list.eduStat eq '협의중')}">
                                                    <p class="box-label bigger waiting">
                                                        <span>${list.eduStat}</span>
                                                    </p>
                                                </c:when>
                                                <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '신청' or list.eduStat eq '신청취소' or list.eduStat eq '교육양도')}">
                                                    <p class="box-label bigger">
                                                        <span>${list.eduStat}</span>
                                                    </p>
                                                </c:when>
                                                <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '미선발' or list.eduStat eq '교육취소')}">
                                                    <p class="box-label bigger arr">
                                                        <span>${list.eduStat}</span>
                                                    </p>
                                                </c:when>
                                                <c:when test="${list.trnsfYn eq 'N' and (list.eduStat eq '교육종료' or list.eduStat eq '교육완료')}">
                                                    <p class="box-label bigger complete">
                                                        <span>${list.eduStat}</span>
                                                    </p>
                                                </c:when>
                                                <c:when test="${list.trnsfYn eq 'Y'}"> <%-- 이관 된 경우 --%>
                                                    <p class="box-label bigger waiting">
                                                        <span>이관</span>
                                                    </p>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bot-info">
                            <div class="index-list-w">
                                <div class="list-item">
                                    <div class="cont">
                                        <div class="cont-area">
                                            <div class="info-list-w ">
                                                <div class="info-list">
                                                    <p class="tit f-caption2">신청일시</p>
                                                    <p class="txt f-body2">${ empty list.ptcptDtm ? '-' : kl:convertDate(list.ptcptDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-') }</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육인원</p>
                                                    <p class="txt f-body2">
                                                        <c:choose>
                                                            <c:when test="${list.ptcptCnt < 10}">
                                                                0${list.ptcptCnt}명
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${list.ptcptCnt}명
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육희망일</p>
                                                    <p class="txt f-body2">${ empty list.hopeDt ? '-' : kl:convertDate(list.hopeDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '-') }</p>
                                                </div>
                                                <div class="info-list">
                                                    <p class="tit f-caption2">교육시간</p>
                                                    <p class="txt f-body2">${list.stduyTimeCdNm}시간</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>


            </c:choose>


        </c:forEach>




    </c:when>
    <c:otherwise>
        <input type="hidden" name="totalCount" id="totalCount" value="0"/>

        <div class="no-data-area has-border">
            <div class="txt-box">
                <p class="txt f-body1">현재 학습중인 과정이 없습니다.</p>
            </div>
        </div>

    </c:otherwise>
</c:choose>