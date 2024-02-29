<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

    <div >
        <div class="sec-tit-area">
            <p class="f-head">신청자 기본정보</p>
        </div>
        <div class="sec-con-area">
            <div class="table-sec">
                <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                    <table class="basic-table"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                        <caption>신청자 기본정보</caption>
                        <colgroup>
                            <col style="width: 26%;">
                            <col style="width: 74%;">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>부품사명</th>
                            <td>${ loginMap.cmpnNm }</td>
                        </tr>
                        <tr>
                            <th>성명</th>
                            <td>${ loginMap.name }</td>
                        </tr>
                        <tr>
                            <th>부서</th>
                            <td>${ loginMap.deptNm }</td>
                        </tr>
                        <tr>
                            <th>핸드폰 번호</th>
                            <td>${ loginMap.hpNo }</td>
                        </tr>
                        <tr>
                            <th>이메일</th>
                            <td>${ loginMap.email }</td>
                        </tr>
                        <tr>
                            <th>직급</th>
                            <td>${ loginMap.pstnCd eq 'ED_TARGET04007' ? loginMap.pstnNm: loginMap.pstnCdNm }</td>
                        </tr>
                        <tr>
                            <th>회사 전화번호</th>
                            <td>${ loginMap.telNo }</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-wrap align-right">
                    <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/certification"><span>신청자 기본정보 수정</span></a>
                </div>
            </div>
        </div>
    </div>
    <div class="p-cont-sec">
        <div class="sec-tit-area">
            <p class="f-head">수료 과정정보</p>
        </div>
        <div class="sec-con-area">
            <div class="article-list-w box-list">
                <c:choose>
                    <c:when test="${ not empty rtnData.educationList}">
                        <c:forEach var="list" items="${rtnData.educationList}" varStatus="status">
                            <div class="list-item">
                                <div class="left">
                                    <div class="sort-label-area">
                                        <c:set var="ctgryCdNmList" value="${ fn:split(list.ctgryCdNm, '|')}" />
                                        <c:forEach var="cdNm" items="${ctgryCdNmList}" varStatus="status">
                                            <p class="label"><span>${ cdNm }</span></p>
                                        </c:forEach>
                                    </div>
                                    <p class="tit f-sub-head">
                                            ${ list.nm }
                                    </p>
                                </div>
                                <div class="right">
                                    <dl>
                                        <dt class="f-body2">회차</dt>
                                        <dd class="f-body2">${ list.episdYear }년/${ list.episdOrd }회차</dd>
                                    </dl>
                                    <dl>
                                        <dt class="f-body2">업종</dt>
                                        <dd class="f-body2">${ kl:nvl(list.cbsnCdNm, '-') }</dd>
                                    </dl>
                                    <dl>
                                        <dt class="f-body2">수료일시</dt>
                                        <dd class="f-body2">${ kl:convertDate(list.cmptnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</dd>
                                    </dl>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="list-item">
                            <div class="left">
                                <p class="tit f-sub-head">
                                    조회된 데이터가 없습니다.
                                </p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

