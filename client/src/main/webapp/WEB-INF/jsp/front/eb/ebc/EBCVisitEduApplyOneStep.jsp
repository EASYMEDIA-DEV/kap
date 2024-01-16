<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/eb/EBCVisitEduCtrl">
    <div class="cont-wrap">
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">방문교육신청</span></p>
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
                        <a class="btn-two-depth single-menu" href="javascript:"><span>교육사업 소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>교육신청</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu active" href="javascript:"><span>방문교육</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                </div>
            </div>

            <div class="right-con-area">
                <div class="guide-info-area scroll-motion">
                    <div class="for-motion">
                        <div class="divide-box">
                            <p class="exclamation-txt f-sub-head">
                                회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.
                                <br/>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.
                            </p>
                        </div>
                        <div class="divide-box">
                            <div class="btn-wrap">
                                <a class="btn-text-icon download" href="javascript:" title="신청서 작성예시 파일 다운로드" download=""><span>신청서 작성예시</span></a>
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
                                        <table class="basic-table">
                                            <caption>신청자 기본 정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>신청자</th>
                                                <td>${applicantInfo.name}</td>
                                            </tr>
                                            <tr>
                                                <th>휴대폰번호</th>
                                                <td>${kl:emptyHypen(applicantInfo.hpNo)}</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${kl:emptyHypen(applicantInfo.email)}</td>
                                            </tr>
                                            <tr>
                                                <th>일반 전화번호</th>
                                                <td>${kl:emptyHypen(applicantInfo.telNo)}</td>
                                            </tr>
                                            <tr>
                                                <th>부서</th>
                                                <td>${applicantInfo.deptCdNm}(${applicantInfo.deptDtlNm})</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>${applicantInfo.pstnNm}</td>
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
                    </div>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">소속 부품사 기본정보를 확인해주세요</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table">
                                            <caption>소속 부품사 기본정보</caption>
                                            <colgroup>
                                                <col style="width: 273rem;">
                                                <col style="width: 820rem;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>사업자등록번호</th>
                                                <td>${kl:bsnmNoConvert(rtnInfo.bsnmNo)}</td>
                                            </tr>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${rtnInfo.cmpnNm}</td>
                                            </tr>
                                            <tr>
                                                <th>대표자명</th>
                                                <td>${rtnInfo.rprsntNm}</td>
                                            </tr>
                                            <tr>
                                                <th>구분</th>
                                                <td>${rtnInfo.ctgryNm}</td>
                                            </tr>
                                            <tr>
                                                <th>규모</th>
                                                <td>${rtnInfo.sizeNm}</td>
                                            </tr>
                                            <tr>
                                                <th>설립일자</th>
                                                <td>${kl:convertDate(rtnInfo.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${kl:emptyHypen(rtnInfo.telNo)}</td>
                                            </tr>

                                            <tr>
                                                <th>본사주소</th>
                                                <td>(${rtnInfo.zipcode}) ${rtnInfo.bscAddr} ${rtnInfo.dtlAddr}</td>
                                            </tr>

                                            <tr>
                                                <th>매출액</th>
                                                <td>${rtnInfo.slsPmt}억 원(${rtnInfo.slsYear}년)</td>
                                            </tr>
                                            <tr>
                                                <th>직원수</th>
                                                <td>${rtnInfo.mpleCnt}명</td>
                                            </tr>
                                            <tr>
                                                <th>주생산품</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty rtnInfo.mjrPrdct1}">
                                                            ① ${rtnInfo.mjrPrdct1}
                                                        </c:when>
                                                        <c:when test="${not empty rtnInfo.mjrPrdct2}">
                                                            ② ${rtnInfo.mjrPrdct2}
                                                        </c:when>
                                                        <c:when test="${not empty rtnInfo.mjrPrdct3}">
                                                            ③ ${rtnInfo.mjrPrdct3}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <c:choose>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01002'}">
                                                    <tr>
                                                        <th>SQ정보</th>
                                                        <td>
                                                            <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">
                                                                <p>${status.count}. ${list.nm} / ${list.score} / ${list.year} 년 / ${list.crtfnCmpnNm}</p>
                                                            </c:forEach>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:when test="${rtnInfo.ctgryCd eq 'COMPANY01001'}">
                                                    <tr>
                                                        <th>품질5스타</th>
                                                        <td>${rtnInfo.qlty5StarCdNm} / ${rtnInfo.qlty5StarYear}년</td>
                                                    </tr>
                                                    <tr>
                                                        <th>납입5스타</th>
                                                        <td>${rtnInfo.pay5StarCdNm} / ${rtnInfo.pay5StarYear}년</td>
                                                    </tr>
                                                    <tr>
                                                        <th>기술5스타</th>
                                                        <td>${rtnInfo.tchlg5StarCdNm} / ${rtnInfo.tchlg5StarYear}년</td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="btn-wrap align-right">
                                        <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/certification"><span>부품사 기본정보 수정</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="/education/visit/apply/two-step"><span>다음</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
