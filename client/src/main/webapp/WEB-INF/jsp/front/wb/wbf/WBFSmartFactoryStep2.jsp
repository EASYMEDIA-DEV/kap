<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include/el.jspf" %>
<div class="cont-wrap" data-controller="controller/wb/wbf/WBFSmartFactoryCtrl">
    <form id="frmData" name="frmData" enctype="multipart/form-data">
        <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnData.episdSeq}" />
        <input type="hidden" id="msg" value="${msg}">
        <div class="sub-top-vis-area apply-page consult-biz">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
                <div class="apply-step-w">
                    <div class="for-move">
                        <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                            <p class="step-num">1</p>
                            <p class="step-con">기본정보</p>
                        </div>
                        <div class="step-list ongoing">
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
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp"/>
            <!--LNB 종료-->
            <div class="right-con-area">
                <div class="cont-sec-w">

                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">사업신청 정보를 입력해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.
                                </p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">종된사업장번호</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="text" id="sbrdnBsnmNo" name="sbrdnBsnmNo" class="numberChk notRequired" placeholder="종된사업장번호 입력" maxlength="4">
                                                        </div>
                                                    </div>
                                                    <div class="noti-txt-w">
                                                        <p class="bullet-noti-txt f-caption2">* 종된사업장별로 사업 신청이 가능합니다.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">과제명<span class="essential-mark color-sky">*</span>
                                            </p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="taskCd" name="taskCd" title="과제명">
                                                                <option value="">선택</option>
                                                                <c:forEach var="list" items="${rtnData.asigtList}" varStatus="status">
                                                                    <option value="${list.bsnOptnSeq}">${list.optnNm}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">사업유형<span class="essential-mark color-sky">*</span>
                                            </p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select id="bsnTypeCd" name="bsnTypeCd" title="사업유형">
                                                                <option value="">선택</option>
                                                                <c:forEach var="list" items="${rtnData.bsinList}" varStatus="status">
                                                                    <option value="${list.bsnOptnSeq}">${list.optnNm}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">스마트화 <br class="only-pc">현재 수준<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="form-control input-sm" id="smtfnPrsntCd" name="smtfnPrsntCd" title="스마트화 현재 수준">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                                                    <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">스마트화 <br class="only-pc">목표 수준<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="form-control input-sm " id="smtfnTrgtCd" name="smtfnTrgtCd">
                                                                <option value="">선택</option>
                                                                <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                                                    <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                                        <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
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

                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">사업신청을 위한 첨부파일을 등록해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.
                                </p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일<span class="essential-mark color-sky">*</span>
                                            </p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <p class="f-body2">사업 신청 시 구비서류가 필수로 첨부되어야 합니다.</p>
                                                        <p class="bullet-noti-txt f-caption2">※ 아래 [양식 다운로드] 후 양식에 맞게 작성 후 첨부 부탁드립니다.</p>
                                                        <p class="bullet-noti-txt f-caption2">※ 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</p>
                                                    </div>
                                                </div>


                                                <div class="data-line">
                                                    <p class="data-title f-body1">사업신청서</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input
                                                                type="file" id="searchFile1" class="searchFile" name="atchFile1"
                                                                accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip"
                                                                class="fileInput notRequired"
                                                            />
                                                            <label class="btn-solid gray-bg btnFile" for="searchFile1">파일 찾기</label>
                                                        </div>
                                                        <div class="btn-wrap btn-down-wrap">
                                                            <a class="btn-text-icon download btnDownload" data-file-seq="${rtnRoundForm.smrtFctryAppctnFileSeq}" href="javascript:void(0);" title="양식 다운로드" download=""><span>양식 다운로드</span></a>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="data-line">
                                                    <p class="data-title f-body1">보안서약서</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input
                                                                type="file" id="searchFile2" class="searchFile" name="atchFile2"
                                                                accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip"
                                                                class="fileInput notRequired"
                                                            />
                                                            <label class="btn-solid gray-bg btnFile" for="searchFile2">파일 찾기</label>
                                                        </div>
                                                        <div class="btn-wrap btn-down-wrap">
                                                            <a class="btn-text-icon download btnDownload" data-file-seq="${rtnRoundForm.smrtFctryScrtyFileSeq}" href="javascript:void(0);" title="양식 다운로드" download=""><span>양식 다운로드</span></a>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="data-line">
                                                    <p class="data-title f-body1">중소기업확인서</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input
                                                                type="file" id="searchFile3" class="searchFile" name="atchFile3"
                                                                accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip"
                                                                class="fileInput notRequired"
                                                            />
                                                            <label class="btn-solid gray-bg btnFile" for="searchFile3">파일 찾기</label>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="data-line">
                                                    <p class="data-title f-body1">사업자등록증</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input
                                                                type="file" id="searchFile4" class="searchFile" name="atchFile4"
                                                                accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip"
                                                                class="fileInput notRequired"
                                                            />
                                                            <label class="btn-solid gray-bg btnFile" for="searchFile4">파일 찾기</label>
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

                    <%-- 이용 약관 Start --%>
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">사업신청을 위한 약관을 확인해주세요</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">이용 약관<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <!-- 2023-12-28 이용약관 수정 -->
                                                <div class="agree-box">
                                                    <div class="gray-bg-sec narrow-pad">
                                                        <div class="scroll-div">
                                                            <div class="paragraphs">
                                                                <p class="txt f-body2">현대자동차, 기아, 현대모비스는 2022년도 현대차그룹 스마트공장 구축지원사업 추진을 위한 협력사 지원과 관련하여 개인정보보호법 제15조 제1항 제1호, 제17조 제1항 제1호, 제24조 제1항 제1호에 따라 아래와 같이 개인(신용)정보 수집·이용에 관하여 귀하의 개인정보 수집·이용 및 제3자 제공에 대한 동의를 얻고자 합니다.</p>
                                                            </div>
                                                            <div class="paragraphs">
                                                                <p class="txt f-body2">1. 개인정보 수집∙이용에 대한 동의</p>
                                                                <div class="table-box">
                                                                    <table class="basic-table">
                                                                        <caption>개인정보 수집∙이용에 대한 동의</caption>
                                                                        <colgroup>
                                                                            <col style="width: 25%;">
                                                                            <col style="width: 75%;">
                                                                        </colgroup>
                                                                        <tbody>
                                                                        <tr>
                                                                            <th>수집 및 이용항목</th>
                                                                            <td>성명, 직위, 연락처 (회사번호, 휴대폰번호, 이메일)</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th>수집·이용목적</th>
                                                                            <td>
                                                                                1. 2022년도 현대차그룹 스마트공장 구축지원 사업 협력사 식별 및 지원대상 확인, 사업 참여 및 지원 연락
                                                                                <br/>2. 자동차부품산업진흥재단 연계사업 안내
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th>이용기간 및 보유기간</th>
                                                                            <td>사업 종료 후 6개월까지 이용, 차후 이력관리를 위해 3년 보관가능</td>
                                                                        </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <p class="txt f-body2">2. 개인정보의 제3자 제공∙위탁에 대한 동의</p>
                                                                <div class="table-box">
                                                                    <table class="basic-table">
                                                                        <caption>개인정보의 제3자 제공∙위탁에 대한 동의</caption>
                                                                        <colgroup>
                                                                            <col style="width: 25%;">
                                                                            <col style="width: 75%;">
                                                                        </colgroup>
                                                                        <tbody>
                                                                        <tr>
                                                                            <th rowspan="2">제3자</th>
                                                                            <td>
                                                                                중소벤처기업부
                                                                                중소기업기술정보진흥원
                                                                                스마트제조혁신추진단
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                공정거래위원회
                                                                                동반성장위원회
                                                                                중소벤처기업부
                                                                                산업통상자원부
                                                                                대중소기업농어업협력재단
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th rowspan="2">제공하는 개인정보의 항목</th>
                                                                            <td>성명, 직위, 연락처 (회사번호, 휴대폰번호, e메일)</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>기업명, 기업규모, 사업자등록번호, 지원실적 등</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th rowspan="2">개인정보 목적</th>
                                                                            <td>2022년도 스마트공장 구축지원사업 수행 및 지원</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>정부기관 업무협조 동반성장지수 평가</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th rowspan="2">개인정보를 제공받는 자의 개인정보 이용기간 및 보유기간</th>
                                                                            <td>사업 종료 후 6개월까지 이용, 차후 이력관리를 위해 3년 보관가능</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>정부 실적제출 및 평가 등 제공 및 이용목적 달성 후 즉시 파기</td>
                                                                        </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <p class="txt f-body2">
                                                                    3. 기타 사항
                                                                    <br/>① 본인이 동의를 거부하는 경우에는 현대차 및 계열사의 협력사 여부 등 지원대상 여부를 확인할 수 없어 동 사업의 참여 및 지원이 불가합니다.
                                                                    <br/>② 본인이 서명날인한 동의서의 복사본 또는 스캔본은 다양한 자료수집의 편의를 위해서 원본과 동일하게 유효하다는 것을 인정합니다.
                                                                    <br/>③ 본인은 위 1~3항에 대한 개인정보의 수집·제공·이용에 대한 동의를 거부할 권리가 있다는 사실 및 동의거부시 제한사항이 있다는 사실을 충분히 인지하고, 그 불이익에 대한 책임은 본인에게 있음을 확인합니다.
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group align-right">
                                                        <div class="form-checkbox">
                                                            <input type="checkbox" id="agreeChk" name="">
                                                            <label for="agreeChk">약관에 동의합니다.</label>
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
                    <%-- 이용 약관 End --%>

                </div>

                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg btnCancle" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg insert" href="javascript:"><span>신청하기</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- content 영역 end -->