<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="mypage" data-controller="controller/mp/mpc/MPConsultingListCtrl"><!-- 마이페이지 mypage 클래스 추가 -->
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
            </div>
        </div>

        <div class="divide-con-area">

            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-con-area">
                                <div class="info-head">
                                    <div class="left">
                                        <p class="article-total-count f-body2">총 <span>1065</span>건</p>
                                        <div class="sort-select">
                                            <div class="form-select txt-select">
                                                <select id="ordFlag" name="ordFlag" title="정렬 바꾸기">
                                                    <option value="1" selected="">업데이트순</option>
                                                    <option value="2">신청일자순</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="right">
                                        <a class="filter-open-btn" href="javascript:" title="필터 열기">
                                            <span>필터<!--<b class="filter-count">(7)</b>--></span><!-- 2024-01-09 filter-conut 삭제 -->
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
                                                            <p class="title f-head">사업구분</p>
                                                        </div>
                                                        <div class="td">
                                                            <div class="data-line-w">
                                                                <div class="data-line">
                                                                    <div class="opt-group total-check-w">
                                                                        <div class="form-checkbox total-check">
                                                                            <input type="checkbox" id="businessChk1" name="businessChk" checked>
                                                                            <label for="businessChk1">전체</label>
                                                                        </div>
                                                                        <div class="form-checkbox">
                                                                            <input type="checkbox" id="businessChk2" name="statusChk" value="CONSULT_GB01" checked>
                                                                            <label for="businessChk2">기술지도</label>
                                                                        </div>
                                                                        <div class="form-checkbox">
                                                                            <input type="checkbox" id="businessChk3" name="statusChk" value="CONSULT_GB02" checked>
                                                                            <label for="businessChk3">경영컨설팅</label>
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
                                                                        <input type="text" name="q" id="q" placeholder="사업명 입력">
                                                                        <div class="input-btn-wrap">
                                                                            <button class="delete-btn" title="지우기" type="button"></button>
                                                                            <button class="srch-btn searchBtn" title="검색" type="button"></button>
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
                                                                    <p class="data-title f-body2">신청일자</p>
                                                                    <div class="form-group form-calendar">

                                                                        <div class="form-input">

                                                                            <input type="text" class="datetimepicker_strtDt" style="width:100px" id="strtDt" data-name="strtDt" value="${today}" title="시작일"/>
                                                                            <span class="input-group-btn" style="z-index:0;">
                                                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                                    <em class="ion-calendar"></em>
                                                                                </button>
                                                                            </span>
                                                                        </div>
                                                                        <div class="form-input calendar">
                                                                            <%--<input type="text" name="endDt" id="endDt" placeholder="2023.01.01">--%>
                                                                            <input type="text" class="datetimepicker_endDt" style="width:100px" id="endDt" data-name="endDt" value="${today}" title="종료일"/>
                                                                            <span class="input-group-btn" style="z-index:0;">
                                                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                                    <em class="ion-calendar"></em>
                                                                                </button>
                                                                            </span>
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
                                                    <button class="btn-solid small gray-bg" id="btnRefresh" type="button"><span>필터 초기화</span></button>
                                                    <button class="btn-solid small black-bg" id="btnSearch" type="button"><span>적용</span></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="trainings-list-w" id="listContainer">

                                </div>

                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
    </form>
</div>