<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area apply-page consult-biz">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ not empty pageMenuDto ? pageMenuDto.menuNm : ''}</span></p>
            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">기본정보</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">2</p>
                        <p class="step-con">정보입력</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">3</p>
                        <p class="step-con">신청완료</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="img-area">
            <div class="gray-bg"></div>
            <div class="graphic-item-w">
                <div class="item"></div>
                <div class="item"></div>
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <div class="lnb-area">
            <div class="for-motion">
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu" href="javascript:"><span>컨설팅사업 소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu active" href="javascript:"><span>기술지도</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu" href="javascript:"><span>경영컨설팅</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
            </div>
        </div>

        <div class="right-con-area">
            <div class="guide-info-area scroll-motion">
                <div class="for-motion">
                    <div class="divide-box">
                        <p class="page-guide-txt">회원가입시 등록된 업체 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다. <br>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.</p>
                    </div>
                    <div class="divide-box">
                        <div class="btn-wrap">
                            <a class="btn-text-icon download" href="javascript:" title="신청서 작성예시 파일 다운로드" download><span>신청서 작성예시</span></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">신청자 기본정보를 확인해주세요</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="table-sec">
                                <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                    <table class="basic-table"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                        <!--
                                          @ 폰트 기본 좌측 정렬
                                            .txt-c : 폰트 가운데 정렬
                                            .txt-r : 폰트 우측 정렬
                                          @ border-right 기본
                                            .bdl : border-left 생성
                                            .bdr : border-right 생성
                                            .bdln : border-left 삭제
                                            .bdrn : border-right 삭제
                                        -->
                                        <caption>신청자 기본 정보</caption>
                                        <colgroup>
                                            <col style="width: 25%;">
                                            <col style="width: 75%;">
                                        </colgroup>
                                        <tbody>
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
                                            <td>abc@easymedia.net</td>
                                        </tr>
                                        <tr>
                                            <th>전화번호</th>
                                            <td>-</td>
                                        </tr>
                                        <tr>
                                            <th>부서</th>
                                            <td>품질(품질기술지원팀)</td>
                                        </tr>
                                        <tr>
                                            <th>직급</th>
                                            <td>과장</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="javascript:"><span>신청인 추가</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">소속 부품사 기본정보를 확인해주세요</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="table-sec">
                                <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                    <table class="basic-table"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                        <!--
                                          @ 폰트 기본 좌측 정렬
                                            .txt-c : 폰트 가운데 정렬
                                            .txt-r : 폰트 우측 정렬
                                          @ border-right 기본
                                            .bdl : border-left 생성
                                            .bdr : border-right 생성
                                            .bdln : border-left 삭제
                                            .bdrn : border-right 삭제
                                        -->
                                        <caption>소속 부품사 기본정보</caption>
                                        <colgroup>
                                            <col style="width: 25%;">
                                            <col style="width: 75%;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>사업자등록번호</th>
                                            <td>123-45-67890</td>
                                        </tr>
                                        <tr>
                                            <th>부품사명</th>
                                            <td>에이테크</td>
                                        </tr>
                                        <tr>
                                            <th>대표자명</th>
                                            <td>홍상직</td>
                                        </tr>
                                        <tr>
                                            <th>부품사명(약식)</th>
                                            <td>-</td>
                                        </tr>
                                        <tr>
                                            <th>구분</th>
                                            <td>1차</td>
                                        </tr>
                                        <tr>
                                            <th>규모</th>
                                            <td>중소기업</td>
                                        </tr>
                                        <tr>
                                            <th>설립일자</th>
                                            <td>2002-07-15</td>
                                        </tr>
                                        <tr>
                                            <th>전화번호</th>
                                            <td>031-1234-5678</td>
                                        </tr>
                                        <tr>
                                            <th>본사주소</th>
                                            <td>12345 서울시 구로구 디지털로32길 28</td>
                                        </tr>
                                        <tr>
                                            <th>매출액</th>
                                            <td>100억 원(2022년)</td>
                                        </tr>
                                        <tr>
                                            <th>직원수</th>
                                            <td>100명</td>
                                        </tr>
                                        <tr>
                                            <th>주생산품</th>
                                            <td>① 알루미늄 실린더 ② 섀시</td>
                                        </tr>
                                        <tr>
                                            <th>품질5스타</th>
                                            <td>★★★★★ / 2022년</td>
                                        </tr>
                                        <tr>
                                            <th>납입5스타</th>
                                            <td>★★★ / 2018년</td>
                                        </tr>
                                        <tr>
                                            <th>기술5스타</th>
                                            <td>-</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="javascript:"><span>업체 기본정보 수정하기</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion">
                    <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
                    <a class="btn-solid small black-bg" href="javascript:"><span>다음</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->