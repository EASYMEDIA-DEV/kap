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
    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
    </div>
    <div class="divide-con-area" >
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="guide-info-area scroll-motion" data-controller="controller/mp/mpe/MPESqCertiManageBtnCtrl">
                <div class="for-motion">
                    <div class="divide-box">
                        <div class="btn-wrap">
                            <div class="btn-set">
                                <!-- "자격증 신청 반려" 문구가 보일 경우, [자격증 보기] 버튼은 보이지 않고 [자격증 신청] 버튼만 보임 -->
                                <c:if test="${ not empty sqCertiMst}">
                                    <button class="btn-solid small gray-bg" type="button" id="paymentInfoViewPopupBtn"><span>자격증 보기</span></button>
                                </c:if>
                                <c:if test="${ posibleSqCertiCnt eq 1 and loginMap.authCd eq 'CP' and (empty sqCertiMst)}">
                                    <button class="btn-solid small black-bg" type="button" id="paymentInfoManagPopupBtn"><span>자격증 신청</span></button>
                                </c:if>
                            </div>
                        </div>
                        <c:if test="${ posibleSqCertiCnt eq 1 and loginMap.authCd eq 'CP' and ((not empty sqCertiMst and sqCertiMst.issueCd eq 'EBD_SQ_C'))}">
                            <p class="exclamation-txt f-body1">자격증 신청 반려: ${sqCertiMst.rtrnRsn}</p>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpe/MPESqCertiCompleteListCtrl">
                    <form >
                        <input type="hidden" name="pageIndex" value="1" />
                        <input type="hidden" name="pageRowSize" value="10" />
                        <input type="hidden" name="listRowSize" value="10" />
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">수료 교육 이력</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="info-head no-bdr">
                                    <p class="article-total-count f-body2">총 <span id="completeListContainerTotCnt">${ educationCompleteListCnt }</span>개</p>
                                </div>
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table w864"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                            <caption>회원목록</caption>
                                            <colgroup>
                                                <col style="width: 8%;">
                                                <col style="width: 26%;">
                                                <col style="width: 11%;">
                                                <col style="width: 11%;">
                                                <col style="width: 22%;">
                                                <col style="width: 11%;">
                                                <col style="width: 11%;">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>과정</th>
                                                <th>회차</th>
                                                <th>업종</th>
                                                <th>교육기간</th>
                                                <th>신청일</th>
                                                <th>수료일</th>
                                            </tr>
                                            </thead>
                                            <tbody id="completeListContainer">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line " href="javascript:" id="moreCompletePagingContainer"><span>더보기</span><span class="item-count">(0/0)</span></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpe/MPESqRepairListCtrl">
                    <form>
                        <input type="hidden" name="pageIndex" value="1" />
                        <input type="hidden" name="pageRowSize" value="10" />
                        <input type="hidden" name="listRowSize" value="10" />
                        <div class="for-motion">
                            <div class="sec-tit-area">
                                <p class="f-title3">보수 교육 이력</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="info-head no-bdr">
                                    <p class="article-total-count f-body2">총 <span id="repairListContainerTotCnt">0</span>개</p>
                                </div>
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table w864"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                            <caption>회원목록</caption>
                                            <colgroup>
                                                <col style="width: 8%;">
                                                <col style="width: 29%;">
                                                <col style="width: 13%;">
                                                <col style="width: 24%;">
                                                <col style="width: 13%;">
                                                <col style="width: 13%;">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>과정</th>
                                                <th>회차</th>
                                                <th>교육기간</th>
                                                <th>신청일</th>
                                                <th>수료일</th>
                                            </tr>
                                            </thead>
                                            <tbody id="repairListContainer">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="btn-wrap add-load align-center">
                                    <a class="btn-solid small black-line" href="javascript:" id="moreRepairPagingContainer"><span>더보기</span><span class="item-count">(0/0)</span></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->
<!-- 지급정보관리 팝업 -->
<c:if test="${loginMap.authCd eq 'CP' and empty sqCertiMst}">
    <div class="layer-popup paymentInfoManagPopup" data-controller="controller/mp/mpe/MPESqCertiAppliyCtrl"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <form style="height:100%">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="for-center" >
                <div class="pop-wrap">
                    <div class="pop-con-area">
                        <div class="tit-area">
                            <p class="f-title1">SQ평가원 자격증 신청</p>
                        </div>
                        <div class="con-area">
                            <div class="scroll-area">
                                <div class="p-info-sec">
                                    <p class="f-sub-head">SQ평가원 자격증은 담당자가 SQ평가원 양성과정 및 업종별 기술이해, SQ품질지도 과정에 대한 내용을 확인 후 발급 처리되므로, 업무일 기준 최대 5일까지 소요될 수 있으니, 이 점 양해 바랍니다.</p>
                                </div>
                                <div class="p-cont-sec">
                                    <div class="noti-txt-w t-align-right">
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="sec-tit-area">
                                        <p class="f-head">신청자 증명사진<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="sec-con-area">
                                        <div class="data-enter-form">
                                            <div class="row">
                                                <!-- <div class="th">
                                                  <p class="title f-body1">신청자 증명사진<span class="essential-mark color-sky">*</span></p>
                                                </div> -->
                                                <div class="td">
                                                    <div class="data-line-w">
                                                        <div class="data-line">
                                                            <div class="inner-line">
                                                                <div class="form-group">
                                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                                    </div>
                                                                    <div class="file-btn-area">
                                                                        <input type="file"  id="idntfnPhotoFileSeq" title="신청자 증명사진" name="idntfnPhotoFileSeq" data-max-size="${atchUploadMaxSize}" data-accept="${imageExtns}">
                                                                        <label class="btn-solid gray-bg" for="idntfnPhotoFileSeq">파일 찾기</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="inner-line">
                                                                <div class="noti-txt-w">
                                                                    <p class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(${imageExtns}) / 용량(최대${atchUploadMaxSize / 1024 / 1024}MB) / 최대개수(1개)</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div  id="paymentInfoManagPopupFrm">

                                </div>
                            </div>
                        </div>
                        <div class="bot-fix-btn-area">
                            <div class="btn-wrap align-right">
                                <button class="btn-solid small black-bg " type="button" id="submitBtn"><span>자격증 신청</span></button>
                            </div>
                        </div>
                        <div class="user-opt-area">
                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</c:if>
<!-- SQ평가원 자격증 팝업 -->
<c:if test="${ not empty sqCertiMst }">
    <div class="layer-popup estiCertiPop paymentInfoViewPopupFrm ${ (sqCertiMst.issueCd eq 'EBD_SQ_R' or sqCertiMst.useYn eq 'N' or sqCertiMst.expiration) ? 'unavailable' : ''}" data-controller="controller/mp/mpe/MPESqCertiAppliyCtrl"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <form style="height:100%">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="for-center">
                <div class="pop-wrap">
                    <div class="pop-con-area">
                        <div class="flow-area">
                            <div class="flow-wrap">
                                <div class="flow-list"><p class="img"><img src="/common/images/img-logo-certificate-prevention-of-theft.svg" alt=""></p></div>
                            </div>
                        </div>
                        <div class="tit-area">
                            <p class="f-title1">SQ평가원 자격증</p>
                        </div>
                        <div class="con-area">
                            <div class="scroll-area">
                                <div class="p-cont-sec">
                                    <div class="cont-sec-w">
                                        <!-- 사용불가일 때 추가되는 영역 -->
                                        <c:choose>
                                            <c:when test="${ sqCertiMst.useYn eq 'N' or sqCertiMst.issueCd eq 'EBD_SQ_R' or sqCertiMst.expiration }">
                                                <c:choose>
                                                    <c:when test="${ sqCertiMst.useYn eq 'N' }">
                                                        <div class="no-data-area">
                                                            <div class="txt-box">
                                                                <p class="txt f-body1">자격증 사용이 중지되었습니다.</p>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${ sqCertiMst.issueCd eq 'EBD_SQ_R' }">
                                                        <div class="no-data-area certificate"><!-- certificate 클래스 추가 시, 자격증 아이콘 -->
                                                            <div class="txt-box">
                                                                <p class="txt f-body1">자격증 발급 예정입니다.</p>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${ sqCertiMst.expiration }">
                                                        <div class="no-data-area">
                                                            <div class="txt-box">
                                                                <p class="txt f-body1">자격증 유효기간이 만료되었습니다.</p>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                                <div class="divide-area">
                                                    <div class="txt-con">
                                                        <div class="type-div">
                                                            <p class="f-title3">SQ평가원</p>
                                                            <p class="f-sub-head">${ sqCertiMst.examCdNm }</p>
                                                        </div>
                                                        <ul class="article-list-w txt-list">
                                                            <li class="list-item">
                                                                <div class="txt-box">
                                                                    <div class="names">
                                                                        <p class="name f-head">${ sqCertiMst.name }</p>
                                                                    </div>
                                                                    <div class="infos">
                                                                        <div class="info">
                                                                            <span></span>
                                                                        </div>
                                                                        <div class="info">
                                                                            <span></span>
                                                                        </div>
                                                                        <div class="info">
                                                                            <span></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <div class="img-con">
                                                        <div class="img"><img src="${sqCertiMst.idntfnPhotoFileSeqUrl}" alt="이미지" ></div>
                                                        <p class="modify-info f-caption2">최근 수정일시<span class="date-time f-caption1">${ kl:convertDate(sqCertiMst.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</span></p>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="divide-area">
                                                    <div class="txt-con">
                                                        <div class="type-div">
                                                            <p class="f-title3">SQ평가원</p>
                                                            <p class="f-sub-head">${ sqCertiMst.examCdNm }</p>
                                                        </div>
                                                        <ul class="article-list-w txt-list">
                                                            <li class="list-item">
                                                                <div class="txt-box">
                                                                    <div class="names">
                                                                        <p class="name f-head">${ sqCertiMst.name }</p>
                                                                    </div>
                                                                    <div class="infos">
                                                                        <dl class="info">
                                                                            <dt class="f-body2">자격증 번호</dt>
                                                                            <dd class="f-body2">${ empty sqCertiMst.jdgmtNo ? '-' : sqCertiMst.jdgmtNo }</dd>
                                                                        </dl>
                                                                        <dl class="info">
                                                                            <dt class="f-body2">생년월일</dt>
                                                                            <dd class="f-body2">${ empty sqCertiMst.birth ? '-' : kl:convertDate(sqCertiMst.birth, 'yyyy-MM-dd', 'yyyy.MM.dd', '') }</dd>
                                                                        </dl>
                                                                        <dl class="info">
                                                                            <dt class="f-body2">소속</dt>
                                                                            <dd class="f-body2">${ empty sqCertiMst.cmpnNm ? '-' : sqCertiMst.cmpnNm }</dd>
                                                                        </dl>
                                                                        <dl class="info">
                                                                            <dt class="f-body2">업종</dt>
                                                                            <dd class="f-body2">
                                                                                <c:set var="cbsnCdNm" value="" />
                                                                                <c:choose>
                                                                                    <c:when test="${ not empty rtnCompletePrcsList}">
                                                                                        <c:forEach var="list" items="${rtnCompletePrcsList.educationList}" varStatus="status">
                                                                                            <c:if test="${ list.lcnsCnnctCd ne 'LCNS_CNNCT02'}" >
                                                                                                <c:choose>
                                                                                                    <c:when test="${ empty cbsnCdNm }">
                                                                                                        <c:set var="cbsnCdNm" value="${ list.cbsnCdNm }" />
                                                                                                    </c:when>
                                                                                                    <c:otherwise>
                                                                                                        <c:set var="cbsnCdNm">
                                                                                                            ${ cbsnCdNm}, ${list.cbsnCdNm}
                                                                                                        </c:set>
                                                                                                    </c:otherwise>
                                                                                                </c:choose>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                    </c:when>
                                                                                </c:choose>
                                                                                    ${ empty cbsnCdNm ? '-' :  cbsnCdNm}
                                                                            </dd>
                                                                        </dl>
                                                                        <dl class="info">
                                                                            <dt class="f-body2">최초취득일</dt>
                                                                            <dd class="f-body2">${ empty sqCertiMst.acqsnDtm ? '-' : kl:convertDate(sqCertiMst.acqsnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</dd>
                                                                        </dl>
                                                                        <dl class="info">
                                                                            <dt class="f-body2">유효기간</dt>
                                                                            <dd class="f-body2">${ kl:convertDate(sqCertiMst.validStrtDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '') } ~ ${ kl:convertDate(sqCertiMst.validEndDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '') }</dd>
                                                                        </dl>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <div class="img-con">
                                                        <div class="img"><img src="${sqCertiMst.idntfnPhotoFileSeqUrl}" alt="이미지" class="idntfnPhotoFileSeqImage"></div>
                                                        <div class="btn-wrap">
                                                            <div class="file-btn-area">
                                                                <c:set var="fileOrd" value="${ not empty sqCertiMst and not empty sqCertiMst.fileList and fn:length(sqCertiMst.fileList) > 0 ? sqCertiMst.fileList[0].fileOrd:  ''}" />
                                                                <input type="file" id="idntfnPhotoFileSeq" name="idntfnPhotoFileSeq"
                                                                                                           data-max-size="${atchUploadMaxSize}"
                                                                                                           data-accept="${imageExtns}"
                                                                                                           data-value="${sqCertiMst.idntfnPhotoFileSeq}"
                                                                                                           data-ord="${fileOrd}"
                                                                                                            title="자격증 사진">
                                                                <label class="btn-solid gray-bg" for="idntfnPhotoFileSeq">수정</label>
                                                            </div>
                                                            <button class="btn-solid small black-bg" type="submit"><span>변경 저장</span></button>
                                                        </div>
                                                        <p class="modify-info f-caption2">최근 수정일시<span class="date-time f-caption1">${ kl:convertDate(sqCertiMst.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</span></p>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- // 사용불가일 때 추가되는 영역 -->

                                        <div class="cont-sec s-margin">
                                            <div class="gray-bg-sec narrow-pad">
                                                <div class="con-list-box">
                                                    <p class="f-caption1">유의 사항</p>
                                                    <div class="ul-txt-w">
                                                        <div class="ul-txt-list">
                                                            <p class="ul-txt has-star f-caption2">본 자격증은 자동차부품산업진흥재단에서 발급한 SQ평가원에 대한 자격을 의미합니다.</p>
                                                            <p class="ul-txt has-star f-caption2">본 자격증에 대한 명의 도용 시 ‘사문서 위조 및 위조사문서 등의 행사‘ (형법 제231조, 현업234조)에 근거하여 처벌될 수 있습니다.</p>
                                                            <p class="ul-txt has-star f-caption2">본 자격증에 대한 유효기간 만료 시 ‘SQ평가원보수’ 과정을 수료할 경우 자동 갱신되므로, 유효기간 만료 전 교육과정을 수료하시기 바랍니다.</p>
                                                            <p class="ul-txt has-star f-caption2">본 자격증에 명시된 업종의 경우 ‘업종별기술이해＇교육과정 중 수료한 과목의 업종명이 표시됩니다.</p>
                                                            <p class="ul-txt has-star f-caption2">본 자격증에 명시된 소속(부품사)에 대한 변경이 필요할 경우 고객센터로 문의바랍니다.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="seal-div">
                                            <p class="f-head">
                                                <span class="txt">자동차부품산업진흥재단 이사장 안정구</span>
                                                <span class="img"><img src="/common/images/img-chariman-seal.png" alt=""></span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="user-opt-area">
                            <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</c:if>