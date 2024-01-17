<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/mp/mpb/MPBCoexistenceCtrl">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <form class="form-horizontal" id="frmData" name="frmData">
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" id="firstIndex" name="firstIndex" value="${ rtnData.firstIndex }" />
        <input type="hidden" id="recordCountPerPage" name="recordCountPerPage" value="${ rtnData.recordCountPerPage }" />
    </form>
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-con-area">
                            <div class="info-head">
                                <div class="left">
                                    <p class="article-total-count f-body2">총 <span id="totalCnt">${rtnData.totalCount}</span>건</p>
                                    <div class="sort-select">
                                        <div class="form-select txt-select">
                                            <select id="" name="" title="정렬 바꾸기">
                                                <option value="" selected="">업데이트순</option>
                                                <option value="">신청일자순</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="right">
                                    <a class="filter-open-btn" href="javascript:" title="필터 열기">
                                        <span>필터</span>
                                    </a>
                                </div>
                            </div>

                            <div class="filter-popup">
                                <div class="for-flex">
                                    <div class="for-center">
                                        <div class="filter-con-area">
                                            <div class="data-enter-form">
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head">사업상태</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <div class="opt-group total-check-w">
                                                                    <div class="form-checkbox total-check">
                                                                        <input type="checkbox" id="businessStatusChk1" name="businessStatusChk" checked>
                                                                        <label for="businessStatusChk1">전체</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessStatusChk2" name="businessStatusChk" checked>
                                                                        <label for="businessStatusChk2">진행중</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessStatusChk3" name="businessStatusChk" checked>
                                                                        <label for="businessStatusChk3">완료</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessStatusChk4" name="businessStatusChk" checked>
                                                                        <label for="businessStatusChk4">미선정</label>
                                                                    </div>
                                                                    <div class="form-checkbox">
                                                                        <input type="checkbox" id="businessStatusChk5" name="businessStatusChk" checked>
                                                                        <label for="businessStatusChk5">취소</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head">키워드</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <div class="form-input srch-input w-longer">
                                                                    <input type="text" placeholder="사업명 입력">
                                                                    <div class="input-btn-wrap">
                                                                        <button class="delete-btn" title="지우기" type="button"></button>
                                                                        <button class="srch-btn" title="검색"></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="th">
                                                        <p class="title f-head">기간검색</p>
                                                    </div>
                                                    <div class="td">
                                                        <div class="data-line-w">
                                                            <div class="data-line">
                                                                <div class="middle-line">
                                                                    <div class="opt-group">
                                                                        <div class="form-radio">
                                                                            <input type="radio" id="searchPeriodRadio1" name="searchPeriodRadio" checked>
                                                                            <label for="searchPeriodRadio1">사업기간</label>
                                                                        </div>
                                                                        <div class="form-radio">
                                                                            <input type="radio" id="searchPeriodRadio2" name="searchPeriodRadio">
                                                                            <label for="searchPeriodRadio2">신청일시</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="middle-line">
                                                                    <div class="form-group form-calendar">
                                                                        <div class="form-input">
                                                                            <input type="text" class="datetimepicker_strtDt" onclick="cmmCtrl.initCalendar(this);" placeholder="2023.01.01">
                                                                        </div>
                                                                        <div class="form-input calendar">
                                                                            <input type="text" class="datetimepicker_endDt" onclick="cmmCtrl.initCalendar(this);"  placeholder="2023.01.01">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btn-wrap">
                                            <div class="btn-set">
                                                <button class="btn-solid small gray-bg btn-role-close" type="button"><span>닫기</span><!--ㅠ-->
                                                </button></div>
                                            <div class="btn-set">
                                                <button class="btn-solid small gray-bg"><span>필터 초기화</span></button>
                                                <button class="btn-solid small black-bg"><span>적용</span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="trainings-list-w">
                                <c:choose>
                                    <c:when test="${not empty rtnData}">
                                        <c:forEach var="item" items="${rtnData.list}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${item.rsumeSttsCdNm eq '미선정' || item.rsumeSttsCdNm eq '보완요청' || item.rsumeSttsCdNm eq '사용자취소'
                                                || item.rsumeSttsCdNm eq '부적합' || item.rsumeSttsCdNm eq '탈락'}">
                                                    <c:set var="classType" value="arr" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="classType" value="accepting" />
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="training-confirm moveView" data-bsn-cd="${item.bsnCd}" data-appctn-seq="${item.appctnSeq}">
                                                <div class="top-info">
                                                    <div class="training-view-page">
                                                        <div class="training-list">
                                                            <div class="txt-area">
                                                                <div class="top-line">
                                                                    <div class="sort-label-area">
                                                                        <p class="label">
                                                                            <span>
                                                                                <c:choose>
                                                                                    <c:when test="${not empty item.title}">${item.title}</c:when>
                                                                                    <c:otherwise>경쟁력향상지원</c:otherwise>
                                                                                </c:choose>
                                                                            </span>
                                                                        </p>
                                                                    </div>
                                                                    <p class="training-name f-title3">${item.year} ${item.episd}차 ${item.bsnNm}
                                                                    </p>
                                                                </div>
                                                                <div class="group">
                                                                    <p class="index-num f-head">${item.rsumeSttsCdNm}</p>
                                                                    <div class="status-info-w">
                                                                        <p class="box-label bigger ${classType}"><span>${item.appctnSttsCd}</span></p>
                                                                    </div>
                                                                    <c:if test="${not empty item.admMemo}">
                                                                        <div class="tooltip-wrap">
                                                                            <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                                                            <div class="tooltip-box">
                                                                                <p class="txt">${item.admMemo}</p>
                                                                                <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
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
                                                                            <p class="tit f-caption2">신청일자</p>
                                                                            <p class="txt f-body2">${ kl:convertDate(item.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }</p>
                                                                        </div>
                                                                        <div class="info-list">
                                                                            <div class="tit f-caption2">사업기간</div>
                                                                            <p class="txt f-body2">${ kl:convertDate(item.bsnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') } ~ <br class="only-pc"/>${ kl:convertDate(item.bsnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }</p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
                                            <div class="txt-box">
                                                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${rtnData.totalCount > 10}">
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line addMore" href="javascript:"><span>더보기</span>
                                        <span class="item-count">
                                            <c:choose>
                                                <c:when test="${rtnData.totalCount > 10}">
                                                    <c:set var="curruntCnt" value="10" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="curruntCnt" value="${rtnData.totalCount % 10}" />
                                                </c:otherwise>
                                            </c:choose>
                                            (${curruntCnt}/${rtnData.totalCount})
                                        </span>
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->