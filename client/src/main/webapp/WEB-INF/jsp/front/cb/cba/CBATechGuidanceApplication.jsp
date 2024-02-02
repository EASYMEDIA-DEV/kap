<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<script>
    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            // 이벤트 추가하는 곳
            alert("정상적인 접근이 아닙니다");
            location.href = "/";
        }
    }
</script>
<div class="cont-wrap" data-controller="controller/cb/cba/CBATechGuidanceApplicationCtrl">
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
            <p class="page-tit f-xlarge-title"><span class="for-move">기술지도신청</span></p>
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
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="guide-info-area scroll-motion">
                <div class="for-motion">
                    <div class="divide-box">
                        <p class="exclamation-txt f-sub-head">회원가입시 등록된 부품사 및 정보를 기본으로 신청합니다. 변경 사항이 있으면 수정 후 신청 바랍니다.<br>정확한 신청을 위해 각 항목을 확인하시고 현재 정보를 정확히 입력해 주십시오.</p>
                    </div>
                    <%--<div class="divide-box">
                        <div class="btn-wrap">
                            <a class="btn-text-icon download" href="javascript:" title="신청서 작성예시 파일 다운로드" download><span>신청서 작성예시</span></a>
                        </div>
                    </div>--%>
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
                                        <input type="hidden" class="memSeq" value="${loginMap.seq}">
                                        <caption>신청자 기본 정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>신청자</th>
                                            <td>${loginMap.name}</td>
                                        </tr>
                                        <tr>
                                            <th>휴대폰번호</th>
                                            <td>${loginMap.hpNo}</td>
                                        </tr>
                                        <tr>
                                            <th>이메일</th>
                                            <td>${loginMap.email}</td>
                                        </tr>
                                        <tr>
                                            <th>일반 전화번호</th>
                                            <td>${empty loginMap.memTelNo ? '-': loginMap.memTelNo }</td>
                                        </tr>
                                        <tr>
                                            <th>부서</th>
                                            <td class="deptCd"></td>
                                        </tr>
                                        <tr>
                                            <th>직급</th>
                                            <td>${loginMap.pstnCdNm}<c:if test="${loginMap.pstnCd eq 'MEM_CD01007'}">(${loginMap.pstnNm})</c:if></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/modify-page"><span>신청자 기본정보 수정</span></a>
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
                                    <input type="hidden" id="bsnmNo" value="${loginMap.bsnmNo}" >
                                    <input type="hidden" id="sizeCd" value="" >
                                    <table class="basic-table">
                                        <caption>소속 부품사 기본정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>사업자등록번호</th>
                                            <td>${kl:bsnmNoConvert(loginMap.bsnmNo)}</td>
                                        </tr>
                                        <tr>
                                            <th>부품사명</th>
                                            <td id="cmpnNm"></td>
                                        </tr>
                                        <tr>
                                            <th>대표자명</th>
                                            <td id="rprsntNm"></td>
                                        </tr>
                                        <tr>
                                            <th>구분</th>
                                            <td id="ctgryNm"></td>
                                        </tr>
                                        <tr>
                                            <th>규모</th>
                                            <td id="sizeNm"></td>
                                        </tr>
                                        <tr>
                                            <th>설립일자</th>
                                            <td id="stbsmDt"></td>
                                        </tr>
                                        <tr>
                                            <th>회사 전화번호</th>
                                            <td id="telNo"></td>
                                        </tr>
                                        <tr>
                                            <th>본사주소</th>
                                            <td id="dtlAddr"></td>
                                        </tr>
                                        <tr>
                                            <th>매출액</th>
                                            <td id="slsPmt"></td>
                                        </tr>
                                        <tr>
                                            <th>직원수</th>
                                            <td id="mpleCnt"></td>
                                        </tr>
                                        <tr>
                                            <th>주생산품</th>
                                            <td id="mjrPrdct"></td>
                                        </tr>
                                        <tr class="fiveStar" style="display: none">
                                            <th>품질5스타</th>
                                            <td id="qlty5Star"></td>
                                        </tr>
                                        <tr class="fiveStar" style="display: none">
                                            <th>납입5스타</th>
                                            <td id="pay5Star"></td>
                                        </tr>
                                        <tr class="fiveStar" style="display: none">
                                            <th>기술5스타</th>
                                            <td id="techlg5Star"></td>
                                        </tr>
                                        <tr class="sqInfo" style="display: none">
                                            <th>SQ정보</th>
                                            <td id="sqInfo">
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="/my-page/member/intrduction/modify-page"><span>부품사 기본정보 수정</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion">
                    <a class="btn-solid small gray-bg cancelApply" href="javascript:"><span>취소</span></a>
                    <a class="btn-solid small black-bg consInfoApply" href="javascript:"><span>다음</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->