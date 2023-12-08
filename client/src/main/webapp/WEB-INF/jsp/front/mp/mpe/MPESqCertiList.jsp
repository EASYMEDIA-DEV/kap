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
            <div class="guide-info-area scroll-motion">
                <div class="for-motion">
                    <div class="btn-wrap align-right">
                        <div class="btn-set">
                            <c:if test="${ posibleSqCertiCnt eq 1}">
                                <button class="btn-solid small black-bg" type="button" onclick="openPopup('paymentInfoManagPopup')"><span>SQ평가원 자격증 신청</span></button>
                            </c:if>
                            <c:if test="${ not empty sqCerti}">
                                <button class="btn-solid small gray-bg" type="button"><span>SQ평가원 자격증 보기</span></button>
                            </c:if>
                        </div>
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
                                    <a class="btn-solid small black-line" href="javascript:" id="moreCompletePagingContainer"><span>더보기</span><span class="item-count">(0/0)</span></a>
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
<div class="layer-popup paymentInfoManagPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
    <div class="for-center">
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
                                                                <input type="file" id="searchFile">
                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="inner-line">
                                                        <div class="noti-txt-w">
                                                            <p class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="p-cont-sec">
                            <div class="sec-tit-area">
                                <p class="f-head">신청자 기본정보</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="table-sec">
                                    <div class="table-box"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <table class="basic-table"><!-- mobile에서 기본 width 130%, 값 지정 필요할 경우 .w-가로값 클래스 추가  -->
                                            <caption>신청자 기본정보</caption>
                                            <colgroup>
                                                <col style="width: 26%;">
                                                <col style="width: 74%;">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>부품사명</th>
                                                <td>${ loginMap.cmpnNm }</td>
                                            </tr>
                                            <tr>
                                                <th>성명</th>
                                                <td>${ loginMap.name }</td>
                                            </tr>
                                            <tr>
                                                <th>부서</th>
                                                <td>${ loginMap.deptNm }</td>
                                            </tr>
                                            <tr>
                                                <th>핸드폰 번호</th>
                                                <td>${ loginMap.hpNo }</td>
                                            </tr>
                                            <tr>
                                                <th>이메일</th>
                                                <td>${ loginMap.email }</td>
                                            </tr>
                                            <tr>
                                                <th>직급</th>
                                                <td>${ loginMap.pstnCd eq 'ED_TARGET04007' ? loginMap.pstnNm: loginMap.pstnCdNm }</td>
                                            </tr>
                                            <tr>
                                                <th>회사 전화번호</th>
                                                <td>${ loginMap.telNo }</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="btn-wrap align-right">
                                        <a class="btn-text-icon black-circle" href="/my-page/member/certification"><span>신청자 기본정보 수정</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="p-cont-sec">
                            <div class="sec-tit-area">
                                <p class="f-head">수료 과정정보</p>
                            </div>
                            <div class="sec-con-area">
                                <div class="article-list-w box-list">
                                    <div class="list-item">
                                        <div class="left">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>품질학교</span></p>
                                            </div>
                                            <p class="tit f-sub-head">
                                                SQ평가원양성-기초
                                            </p>
                                        </div>
                                        <div class="right">
                                            <dl>
                                                <dt class="f-body2">회차</dt>
                                                <dd class="f-body2">2023년/8회차</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">업종</dt>
                                                <dd class="f-body2">절삭가공</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">수료일시</dt>
                                                <dd class="f-body2">2023.01.01 10:00</dd>
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="list-item">
                                        <div class="left">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>품질학교</span></p>
                                            </div>
                                            <p class="tit f-sub-head">
                                                SQ평가원양성-기초
                                            </p>
                                        </div>
                                        <div class="right">
                                            <dl>
                                                <dt class="f-body2">회차</dt>
                                                <dd class="f-body2">2023년/8회차</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">업종</dt>
                                                <dd class="f-body2">절삭가공</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">수료일시</dt>
                                                <dd class="f-body2">2023.01.01 10:00</dd>
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="list-item">
                                        <div class="left">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>품질학교</span></p>
                                            </div>
                                            <p class="tit f-sub-head">
                                                SQ평가원양성-기초
                                            </p>
                                        </div>
                                        <div class="right">
                                            <dl>
                                                <dt class="f-body2">회차</dt>
                                                <dd class="f-body2">2023년/8회차</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">업종</dt>
                                                <dd class="f-body2">절삭가공</dd>
                                            </dl>
                                            <dl>
                                                <dt class="f-body2">수료일시</dt>
                                                <dd class="f-body2">2023.01.01 10:00</dd>
                                            </dl>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bot-fix-btn-area">
                    <div class="btn-wrap align-right">
                        <button class="btn-solid small black-bg btn-role-close btn-agree" type="button"><span>자격증 신청</span></button>
                    </div>
                </div>
                <div class="user-opt-area">
                    <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                </div>
            </div>
        </div>
    </div>
</div>