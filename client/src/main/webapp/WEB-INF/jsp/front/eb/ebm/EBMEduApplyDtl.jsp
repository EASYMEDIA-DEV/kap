<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage" data-controller="controller/eb/ebm/EBMEduApplyDtlCtrl">
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
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육 사업 신청내역</span></p>
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
                                    <div class="training-confirm">
                                        <div class="top-info">
                                            <div class="training-view-page">
                                                <div class="training-list">
                                                    <div class="img-area">
                                                        <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                                    </div>
                                                    <div class="txt-area">
                                                        <div class="top-line">
                                                            <div class="sort-label-area">
                                                                <p class="label"><span>품질아카데미</span></p>
                                                                <p class="label"><span>품질학교</span></p>
                                                            </div>
                                                            <p class="training-name f-title1">꼭 알아야 할 품질 기초 두줄로 떨어질 때</p>
                                                            <p class="training-explain-txt">품질 전문가로서의 첫 시작을 위한 교육!</p>
                                                        </div>
                                                        <div class="class-property-w ">
                                                            <div class="property-list offline"><!-- offline: 집체교육 -->
                                                                <p class="txt">
                                                                    <span>집체교육</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list online"><!-- online: 온라인교육 -->
                                                                <p class="txt">
                                                                    <span>온라인교육</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list time"><!-- time: 학습시간 -->
                                                                <p class="txt">
                                                                    <span>2일(14시간)</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list completion"><!-- completion: 수료여부 -->
                                                                <p class="txt">
                                                                    <span>수료</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list education"><!-- education: 교육상태 -->
                                                                <p class="txt">
                                                                    <span>교육 중</span>
                                                                </p>
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
                                                        <div class="top-area">
                                                            <div class="left">
                                                                <div class="group">
                                                                    <p class="index-num f-title3">8회차</p>
                                                                    <div class="status-info-w">
                                                                        <p class="box-label bigger"><span>사출</span></p>
                                                                    </div>
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <div class="btn-set">
                                                                        <a class="btn-text-icon download" href="javascript:" download=""><span>안내문</span></a>
                                                                        <a class="btn-text-icon download" href="javascript:" download=""><span>안내문</span></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="cont-area">
                                                            <div class="info-list-w ">
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">강사</p>
                                                                    <p class="txt f-body2">홍길동, 홍길남</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">정원</p>
                                                                    <p class="txt f-body2">30명(모집 후 선발)</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육장소</p>
                                                                    <p class="txt f-body2"><a href="javascript:" title="교육장 안내 팝업 열기">글로벌상생협력센터<br class="only-pc" />(GPC)(경주)</a></p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육일자</p>
                                                                    <p class="txt f-body2">2023.10.26 10:00 ~ 2023.10.27 11:00(1일간)</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">신청일시</p>
                                                                    <p class="txt f-body2">2023.10.26 10:00</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">선발여부</p>
                                                                    <p class="txt f-body2">선발대기</p>
                                                                </div>
                                                            </div>
                                                            <div class="btn-wrap">
                                                                <button class="btn-text-icon black-arrow" type="button"><span>회차 담당자 문의</span></button>
                                                                <button class="btn-text-icon black-arrow" type="button"><span>온라인 강의목차</span></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="btn-sec">
                                        <div class="btn-wrap align-right">
                                            <div class="btn-set">
                                                <button class="btn-solid small gray-bg icon btn-print" type="button" onclick="printFn()"><span>수료증 출력</span></button>
                                                <button class="btn-solid small gray-bg icon transfer" type="button"><span>교육양도</span></button>
                                                <button class="btn-solid small gray-bg icon taking" type="button"><span>수강하기</span></button>
                                                <button class="btn-solid small gray-bg icon checkout" type="button"><span>퇴실하기</span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">교육 만족도 설문 조사</p>
                                </div>


                                <div class="sec-con-area ">
                                    <div class="graphic-sec">
                                        <div class="box-btn-area">
                                            <div class="bg-area">
                                                <div class="img" style="background-image: url('/common/images/img-assessment-btn-bg.jpg');"></div>
                                            </div>
                                            <div class="txt-area">
                                                <p class="txt f-head">
                                                    수강하신 교육 및 세미나에 대한 만족도와 소감을 남겨주세요.
                                                    <br/>소중한 의견을 반영하여 더 나은 교육을 만들겠습니다.
                                                </p>
                                            </div>
                                            <div class="btn-wrap">
                                                <a class="btn-solid small white-bg srvStart" href="javascript:" data-episdseq="${rtnData.episdSeq}" data-edctnseq="${rtnData.edctnSeq}" data-episdyear="${rtnData.episdYear}" data-episdord="${rtnData.episdOrd}" data-srvseq="${rtnData.srvSeq}"><span>참여하기</span></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">신청자 기본정보</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <!-- 2023-12-07 수정 -->
                                                <tr>
                                                    <th>신청자</th>
                                                    <td>홍길동</td>
                                                </tr>
                                                <tr>
                                                    <th>휴대폰번호</th>
                                                    <td>010-1234-5678</td>
                                                </tr>
                                                <tr>
                                                    <th>이메일</th>
                                                    <td>honggil@kapkorea.org</td>
                                                </tr>
                                                <tr>
                                                    <th>일반 전화번호</th>
                                                    <td>02-1234-5678</td>
                                                </tr>
                                                <tr>
                                                    <th>부서</th>
                                                    <td>기획그룹</td>
                                                </tr>
                                                <tr>
                                                    <th>직급</th>
                                                    <td>과장</td>
                                                </tr>
                                                <tr>
                                                    <th>성별</th>
                                                    <td>남</td>
                                                </tr>
                                                <!-- // 2023-12-07 수정 -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">소속 부품사 기본정보</p><!-- 2023-12-07 텍스트 수정 -->
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>소속 부품사 기본정보</caption><!-- 2023-12-07 텍스트 수정 -->
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <!-- 2023-12-07 수정 -->
                                                <tr>
                                                    <th>사업자등록번호</th>
                                                    <td>123-45-67890</td>
                                                </tr>
                                                <tr>
                                                    <th>부품사명</th>
                                                    <td>㈜만도</td>
                                                </tr>
                                                <tr>
                                                    <th>대표자명</th>
                                                    <td>김대표</td>
                                                </tr>
                                                <tr>
                                                    <th>구분</th>
                                                    <td>2차</td>
                                                </tr>
                                                <tr>
                                                    <th>규모</th>
                                                    <td>중소기업</td>
                                                </tr>
                                                <tr>
                                                    <th>설립일자</th>
                                                    <td>1952.03.04</td>
                                                </tr>
                                                <tr>
                                                    <th>회사 전화번호</th>
                                                    <td>02-1234-5678</td>
                                                </tr>
                                                <tr>
                                                    <th>본사주소</th>
                                                    <td>(04365) 서울 용산구 원효로 74 현대자동차원효로 사옥 5층</td>
                                                </tr>
                                                <tr>
                                                    <th>매출액</th>
                                                    <td>50억/2014년</td>
                                                </tr>
                                                <tr>
                                                    <th>직원수</th>
                                                    <td>100명</td>
                                                </tr>
                                                <tr>
                                                    <th>주생산품</th>
                                                    <td>① 제동품 ②조향품 ③현가품</td>
                                                </tr>
                                                <tr>
                                                    <th>품질5스타</th>
                                                    <td>★★★★★ / 2022년</td>
                                                </tr>
                                                <tr>
                                                    <th>납입5스타</th>
                                                    <td>★★★ / 2016년</td>
                                                </tr>
                                                <tr>
                                                    <th>기술5스타</th>
                                                    <td>-</td>
                                                </tr>
                                                <tr>
                                                    <th>SQ정보</th>
                                                    <td>
                                                        <p>1. SQ1 / 2000 / 2018 년 / 와이즈랩</p>
                                                        <p>2. SQ3 / 2000 / 2018 년 / 와이즈랩</p>
                                                        <p>3. SQ2 / 2000 / 2018 년 / 와이즈랩</p>
                                                    </td>
                                                </tr>
                                                <!-- // 2023-12-07 수정 -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">GPC 아이디</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>GPC 아이디</th>
                                                    <td>Abcdcs1234</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">출석내역</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="progress-sec">
                                        <!-- @ data-progress에 수치 값 입력 (0~100) -->
                                        <div class="progress-area" data-progress="66">
                                            <div class="progress">
                                                <div class="progress-bar"></div>
                                            </div>
                                            <div class="progress-info">
                                                <p class="txt f-sub-head">출석률</p>
                                                <p class="progress-num f-head color-sky">
                                                    <span class="num">0</span>
                                                    <span>%</span>
                                                </p>
                                            </div>
                                        </div>
                                        <ul class="progress-list">
                                            <li class="list-item">
                                                <div class="txt-area">
                                                    <p class="day f-head">4일차</p>
                                                    <p class="date f-caption2">2023.02.01</p>
                                                </div>
                                                <div class="status-area">
                                                    <dl class="status to">
                                                        <dt class="dt f-caption1">출석</dt>
                                                        <dd class="dd f-body1">-</dd>
                                                    </dl>
                                                    <dl class="status off">
                                                        <dt class="dt f-caption1">퇴실</dt>
                                                        <dd class="dd f-body1">-</dd>
                                                    </dl>
                                                </div>
                                            </li>
                                            <li class="list-item">
                                                <div class="txt-area">
                                                    <p class="day f-head">3일차</p>
                                                    <p class="date f-caption2">2023.02.01</p>
                                                </div>
                                                <div class="status-area">
                                                    <dl class="status to">
                                                        <dt class="dt f-caption1">출석</dt>
                                                        <dd class="dd f-body1">08:30</dd>
                                                    </dl>
                                                    <dl class="status off">
                                                        <dt class="dt f-caption1">퇴실</dt>
                                                        <dd class="dd f-body1">-</dd>
                                                    </dl>
                                                </div>
                                            </li>
                                            <li class="list-item">
                                                <div class="txt-area">
                                                    <p class="day f-head">2일차</p>
                                                    <p class="date f-caption2">2023.02.01</p>
                                                </div>
                                                <div class="status-area">
                                                    <dl class="status to">
                                                        <dt class="dt f-caption1">출석</dt>
                                                        <dd class="dd f-body1">08:30</dd>
                                                    </dl>
                                                    <dl class="status off">
                                                        <dt class="dt f-caption1">퇴실</dt>
                                                        <dd class="dd f-body1">15:30</dd>
                                                    </dl>
                                                </div>
                                            </li>
                                            <li class="list-item">
                                                <div class="txt-area">
                                                    <p class="day f-head">1일차</p>
                                                    <p class="date f-caption2">2023.02.01</p>
                                                </div>
                                                <div class="status-area">
                                                    <dl class="status to">
                                                        <dt class="dt f-caption1">출석</dt>
                                                        <dd class="dd f-body1">08:30</dd>
                                                    </dl>
                                                    <dl class="status off">
                                                        <dt class="dt f-caption1">퇴실</dt>
                                                        <dd class="dd f-body1">
                                                            13:30
                                                            <div class="tooltip-wrap">
                                                                <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                                                <div class="tooltip-box">
                                                                    <p class="txt f-caption2">두통으로 인한 조기 퇴실</p>
                                                                    <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                                                </div>
                                                            </div>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">교육 만족도 설문 조사 참여내역</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="gray-bg-sec">
                                        <div class="con-list-box-w">
                                            <div class="con-list-box">
                                                <p class="f-head">교육 만족도 설문 조사</p>
                                                <div class="ul-txt-w info">
                                                    <div class="ul-txt-list">
                                                        <div class="ul-txt">
                                                            <!-- <dl><dt class="f-caption2">평가점수</dt><dd class="f-caption1">95점</dd></dl> -->
                                                            <dl><dt class="f-caption2">등록일시</dt><dd class="f-caption1">-</dd></dl>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="status-circle"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                            <p class="txt f-body1">미참여</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">수료 평가 참여내역</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="gray-bg-sec">
                                        <div class="con-list-box-w">
                                            <div class="con-list-box">
                                                <p class="f-head">평가</p>
                                                <div class="ul-txt-w info">
                                                    <div class="ul-txt-list">
                                                        <div class="ul-txt">
                                                            <dl><dt class="f-caption2">평가점수</dt><dd class="f-caption1">95점</dd></dl>
                                                            <dl><dt class="f-caption2">등록일시</dt><dd class="f-caption1">2023.01.01 10:30</dd></dl>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="status-circle on"><!-- @ 기본: 미참여, on 클래스: 참여 -->
                                            <p class="txt f-body1">참여완료</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap for-motion align-right">
                            <a class="btn-solid small black-bg" href="javascript:"><span>목록</span></a>
                        </div>
                    </div>
                </div>




            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>