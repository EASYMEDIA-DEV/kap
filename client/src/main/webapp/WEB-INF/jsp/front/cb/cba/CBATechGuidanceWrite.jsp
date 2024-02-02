<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
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
<c:set var="csList" value="${rtnDto.list}"/>
<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="date"><fmt:formatDate value="${today}" pattern="yyyy-MM-dd hh:mm:ss" /></c:set>
<%
  String bsnmNo = request.getParameter("bsnmNo");
  String sizeCd = request.getParameter("sizeCd");
%>
<div class="cont-wrap" data-controller="controller/cb/cba/CBATechGuidanceWriteCtrl">

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
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <form id="frmData" name="frmData" method="post" enctype="multipart/form-data">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="bsnmNo" name="bsnmNo" value="<%=bsnmNo%>">
            <input type="hidden" class="sizeCd" name="sizeCd" value="<%=sizeCd%>">
            <input type="hidden" class="ctgryCd" name="ctgryCd" value="">
            <input type="hidden" class="appctnDt" name="appctnDt" value="${date}">
            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">상주기술지도 신청정보를 입력해주세요.</p>
                                <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">거래처별 매출비중<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w" id="dlvryTempDiv">
                                                <div class="data-inner-line tempRow" id="dlvryRow">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" class="dlvryCmpnNm" name="dlvryCmpnNm" maxlength="50" placeholder="업체명 입력">
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="number" class="dlvryRate" name="dlvryRate" placeholder="매출비중(%) 입력" maxlength="3">
                                                        </div>
                                                        <!-- 업체 추가 버튼은 마지막 추가된 리스트에 표시됩니다 -->
                                                        <div class="btn-wrap">
                                                            <button class="btn-text-icon delete closeLabel" type="button" style="display: none"><span>삭제</span></button>
                                                            <button class="btn-solid small gray-bg btn-add-line cmpnPlus" type="button"><span>업체 추가</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">완성차 의존율<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w" id="dpTempDiv">
                                                <div class="data-inner-line dpTempRow" id="dpndnRow">
                                                    <div class="form-group">
                                                        <div class="form-input w-longer">
                                                            <input type="text" class="dpndnCmpnNm" name="dpndnCmpnNm" maxlength="50" placeholder="업체명 입력" >
                                                        </div>
                                                        <div class="form-input">
                                                            <input type="number" class="dpndnRate" placeholder="의존율(%) 입력" name="dpndnRate" maxlength="3">
                                                        </div>
                                                        <!-- 업체 추가 버튼은 마지막 추가된 리스트에 표시됩니다 -->
                                                        <div class="btn-wrap">
                                                            <button class="btn-text-icon delete closeLabel" type="button" style="display: none"><span>삭제</span></button>
                                                            <button class="btn-solid small gray-bg btn-add-line dpndnPlus" type="button"><span>업체 추가</span></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">품질담당 인원<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="number" class="qltyPicCnt" name="qltyPicCnt" maxlength="50" placeholder="인원수 입력">
                                                            <p class="unit-txt">명</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">FAX</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input">
                                                            <input type="text" class="notRequired telRex" name="faxNo" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="13" placeholder="FAX 입력">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">지도요청 공장주소<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="middle-line">
                                                        <div class="form-checkbox">
                                                            <input type="checkbox" id="cmpnAddrSameYn" name="cmpnAddrSameYn" value="N">
                                                            <label for="cmpnAddrSameYn">본사와 동일</label>
                                                        </div>
                                                    </div>
                                                    <div class="middle-line">
                                                        <div class="form-address">
                                                            <div class="form-group">
                                                                <div class="form-input">
                                                                    <input type="text" class="addr" placeholder="우편번호" id="hqZipcode" name="fctryZipcode" value="" readonly maxlength="50">
                                                                </div>
                                                                <div class="form-input w-longer">
                                                                    <input type="text" class="addr" placeholder="주소" id="hqBscAddr" name="fctryBscAddr" value="" readonly maxlength="50">
                                                                </div>
                                                                <div class="btn-wrap">
                                                                    <button class="btn-solid small gray-bg searchPostCode" id="hqAddr" type="button"><span>우편번호 찾기</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <div class="form-input w-longest">
                                                                    <input type="text" class="addr" placeholder="상세주소 입력" id="hqDtlAddr" name="fctryDtlAddr" value="" maxlength="50">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">소재지역<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-select">
                                                            <select class="mainAddr" id="mainAddr" name="firstRgnsCd" title="소재지 선택">
                                                                <option>선택</option>
                                                                <c:forEach var="addrList" items="${cdDtlList.ADDR_CD}" varStatus="status">
                                                                    <c:if test="${fn:contains(addrList.cd,'MAIN')}">
                                                                        <option value="${addrList.cd}">${addrList.cdNm} </option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="form-select">
                                                            <select class="subAddr" id="subAddr" name="scndRgnsCd" title="소재지 선택">
                                                                <option>선택</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">대표자 승인여부<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <div class="form-radio">
                                                            <input type="radio" id="radio1" name="rprsntApprvYn" value="Y">
                                                            <label for="radio1">승인</label>
                                                        </div>
                                                        <div class="form-radio">
                                                            <input type="radio" id="radio2" name="rprsntApprvYn" value="N">
                                                            <label for="radio2">미승인</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청사유<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group">
                                                        <c:forEach var="cdList" items="${cdDtlList.APPCTN_RSN_CD}" varStatus="status">
                                                            <div class="form-radio">
                                                                <input type="radio" id="${cdList.cd}" value="${cdList.cd}" title="신청사유" name="appctnRsnCd">
                                                                <label for="${cdList.cd}">${cdList.cdNm}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">업종<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <p class="data-title f-body1">금속분야</p>
                                                    <div class="opt-group">
                                                        <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList.cd,'METAL') && cdList.cd ne 'TEC_GUIDE_METAL'}">
                                                                <div class="form-radio">
                                                                    <input type="radio" id="${cdList.cd}" class="cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                                                    <label for="${cdList.cd}">${cdList.cdNm}</label>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">비금속분야</p>
                                                    <div class="opt-group">
                                                        <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                                                            <c:if test="${fn:contains(cdList.cd,'NON') && cdList.cd ne 'TEC_GUIDE_NON'}">
                                                                <div class="form-radio">
                                                                    <input type="radio" id="${cdList.cd}" class="cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                                                    <label for="${cdList.cd}">${cdList.cdNm}</label>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">기타</p>
                                                    <div class="etc-option-w">
                                                        <div class="form-group">
                                                            <c:forEach var="cdList" items="${cdDtlList.TEC_GUIDE_INDUS}" varStatus="status">
                                                                <c:if test="${!fn:contains(cdList.cd,'NON') && !fn:contains(cdList.cd,'META')}">
                                                                    <div class="form-radio">
                                                                        <input type="radio" id="${cdList.cd}" class="cbsnCd" name="cbsnCd" value="${cdList.cd}" title="업종" <c:if test="${rtnDto.cbsnCd eq cdList.cd}">checked</c:if>/>
                                                                        <label for="${cdList.cd}">업종 직접 입력</label>
                                                                    </div>
                                                                </c:if>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="form-input w-longest">
                                                                <input type="text" class="notRequired" name="etcNm" placeholder="업종 입력" title="업종" disabled maxlength="50">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">신청사항<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="opt-group total-check-w"><!-- 전체 체크박스 있는 경우 total-check-w class 추가 -->
                                                        <c:forEach var="appctnCdList" items="${cdDtlList.TEC_GUIDE_APPCTN}" varStatus="status">
                                                            <c:if test="${appctnCdList.cd eq 'TEC_GUIDE_APPCTN00'}">
                                                                <div class="form-checkbox total-check">
                                                                    <input type="checkbox" id="requestChk1" value="${appctnCdList.cd}" name="appctnTypeCd">
                                                                    <label for="requestChk1">전체</label>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${appctnCdList.cd ne 'TEC_GUIDE_APPCTN00'}">
                                                                <div class="form-checkbox">
                                                                   <input type="checkbox" id="${appctnCdList.cd}" value="${appctnCdList.cd}" name="appctnTypeCd">
                                                                   <label for="${appctnCdList.cd}">${appctnCdList.cdNm}</label>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">첨부파일<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="noti-txt-w">
                                                        <p class="f-body2">상주기술지도 신청 시 회사소개서(자율 양식)와 개선활동 추진계획서가 필수로 첨부되어야 합니다.</p>
                                                        <P class="bullet-noti-txt f-caption2">* 개선활동 추진계획서는 아래 [양식 다운로드] 후 양식에 맞게 작성 후 첨부 부탁드립니다.</P>
                                                        <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <p class="data-title f-body1">회사소개서</p>
                                                    <div class="form-group">
                                                        <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <div class="file-list" style="display: none;">
                                                                <p class="file-name"><span class="name"></span><span class="unit"></span></p>
                                                                <button class="btn-delete filedelete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" class="searchFile" name="atchFile" id="searchFile" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="data-line">
                                                    <div class="inner-line">
                                                        <p class="data-title f-body1">개선활동 추진계획서</p>
                                                        <div class="form-group">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                <div class="file-list" style="display: none;">
                                                                    <p class="file-name"><span class="name"></span><span class="unit"></span></p>
                                                                    <button class="btn-delete filedelete" title="파일 삭제하기" type="button"></button>
                                                                </div>
                                                            </div>
                                                            <div class="file-btn-area">
                                                                <input type="file" class="searchFile" name="atchFile1" id="searchFile1" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip">
                                                                <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                                                            </div>
                                                            <div class="btn-wrap btn-down-wrap">
                                                                <a class="btn-text-icon download" href="/file/download?fileSeq=${fileData.tchgdFileSeq}&fileOrd=${fileData.tchgdFileOrd}" download><span>양식 다운로드</span></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">이용 약관<span class="essential-mark color-sky">*</span></p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="agree-box">
                                                    <div class="gray-bg-sec narrow-pad">
                                                        <p class="f-body2">재단의 기술지도는 전액 무상으로 지원되는 사업으로서, 지도 활동을 통해 달성한 유·무형의 성과는 전적으로 지도업체에 귀속되나, 재단의 설립취지인 자동차부품산업 발전을 위해 그 성과를 활용(지도 우수기업 선정 및 정부 등 유공자 포상 추천, 동종업계 수평전개를 위한 개선사례 교육/발표/현장공개 등)할 수 있으며, 구체적인 내용은 재단과 지도업체의 협의를 통해 진행됩니다.</p>
                                                    </div>
                                                    <div class="form-group align-right">
                                                        <div class="form-checkbox">
                                                            <input type="checkbox" id="agreeChk" name="termsYn" value="N">
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
                </div>
                <div class="page-bot-btn-sec scroll-motion">
                    <div class="btn-wrap for-motion">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg cancelApply" href="javascript:"><span>취소</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg submit" href="javascript:"><span>신청하기</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- content 영역 end -->