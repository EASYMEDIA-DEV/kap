<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="csList" value="${rtnDto.list}"/>
<div class="cont-wrap" data-controller="controller/cb/cbb/CBBManageConsultApplicationCtrl">
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
                                    <table class="basic-table">
                                        <caption>신청자 기본 정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>신청자</th>
                                            <td class="appName">${loginMap.name}</td>
                                        </tr>
                                        <tr>
                                            <th>휴대폰번호</th>
                                            <td class="appHpNo">${loginMap.telNo}</td>
                                        </tr>
                                        <tr>
                                            <th>이메일</th>
                                            <td class="appEmail">${loginMap.email}</td>
                                        </tr>
                                        <tr>
                                            <th>일반 전화번호</th>
                                            <td class="appTelNo">${empty loginMap.hpNo ? '-': loginMap.hpNo }</td>
                                        </tr>
                                        <tr>
                                            <th>부서</th>
                                            <td class="appDeptNm">${loginMap.deptNm}</td>
                                        </tr>
                                        <tr>
                                            <th>직급</th>
                                            <td class="appPstnCdNm">${loginMap.pstnCdNm}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="btn-wrap align-right">
                                    <a class="btn-text-icon black-circle" href="javascript:"><span>신청자 기본정보 수정</span></a>
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
                                    <table class="basic-table">
                                        <caption>소속 부품사 기본정보</caption>
                                        <colgroup>
                                            <col style="width: 273rem;">
                                            <col style="width: 820rem;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>사업자등록번호</th>
                                            <td>${loginMap.bsnmNo}</td>
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
                                    <a class="btn-text-icon black-circle" href="javascript:"><span>부품사 기본정보 수정</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">담당임원 정보를 입력해주세요</p>
                            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="form-checkbox">
                                        <input type="checkbox" id="infoSameChk" name="">
                                        <label for="infoSameChk">담당자 정보와 동일</label>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">이름<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" class="picName" name="picName"  placeholder="이름 입력" title="이름">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">휴대폰번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" class="cmssrHpNo" name="cmssrHpNo" placeholder="휴대폰번호 입력" title="휴대폰 번호">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">이메일<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group form-email">
                                                    <input type="hidden" class="picEmail" name="picEmail" value=""/>
                                                    <div class="form-input">
                                                        <input type="text" class="id" name="id" placeholder="이메일 입력" title="이메일">
                                                    </div>
                                                    @
                                                    <div class="form-input">
                                                        <input type="text" name="address" class="address" placeholder="직접입력" title="이메일">
                                                    </div>
                                                    <div class="form-select">
                                                        <select id="addressSelect" title="메일 선택">
                                                            <option value="" selected>직접입력</option>
                                                            <option value="naver.com">naver.com</option>
                                                            <option value="gmail.com">gmail.com</option>
                                                            <option value="nate.com">nate.com</option>
                                                            <option value="daum.net">daum.net</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">회사 전화번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" class="cmssrCmpnTelNo" name="cmssrCmpnTelNo" placeholder="회사 전화번호 입력" required="" title="회사 전화번호">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" class="picDeptNm" name="picDeptNm" placeholder="부서 입력" title="부서">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row cmssrInfo">
                                    <div class="th">
                                        <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" class="picPstnNm" name="picPstnNm" placeholder="직급 입력" title="직급">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-bot-btn-sec scroll-motion">
                <div class="btn-wrap for-motion">
                    <a class="btn-solid small gray-bg" href="./index"><span>취소</span></a>
                    <a class="btn-solid small black-bg consInfoAppl"><span>다음</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
<jsp:include page="/WEB-INF/jsp/front/cb/cbb/CBBManageConsultLayer.jsp"></jsp:include>