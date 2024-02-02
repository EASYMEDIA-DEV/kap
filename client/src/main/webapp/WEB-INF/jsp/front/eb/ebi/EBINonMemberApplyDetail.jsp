<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnData" value="${rtnInfo.rtnData}" />
<c:set var="roomDto" value="${rtnInfo.roomDto}" />
<c:set var="isttrList" value="${rtnInfo.isttrList}" />
<c:set var="rtnTrgtData" value="${rtnInfo.rtnTrgtData}" />
<c:set var="count" value="0" />

<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<c:set var="accsStatusClass" value="" />
<c:choose>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수대기') }">
        <c:set var="accsStatusClass" value="waiting" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수중') }">
        <c:set var="accsStatusClass" value="accepting" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '마감') }">
        <c:set var="accsStatusClass" value="end" />
    </c:when>
</c:choose>

<c:set var="studyStatusClass" value="" />
<c:choose>
    <c:when test="${ fn:contains(list.sttsCd, 'CD02') }">
        <c:set var="studyStatusClass" value="" />
    </c:when>
    <c:when test="${ fn:contains(list.edctnStatusNm, '교육대기') }">
        <c:set var="studyStatusClass" value="waiting" />
    </c:when>
    <c:when test="${ fn:contains(list.edctnStatusNm, '교육중') }">
        <c:set var="studyStatusClass" value="accepting" />
    </c:when>
    <c:when test="${ fn:contains(list.edctnStatusNm, '교육마감') }">
        <c:set var="studyStatusClass" value="end" />
    </c:when>
</c:choose>

<c:set var="edctnStrtDtm" value="${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />
<c:set var="edctnEndDtm" value="${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />

<div data-controller="controller/eb/ebi/EBINonMemberApplyDetailCtrl">
    <form name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" class="notRequired" id="email" name="email" value="${ rtnDto.email }" />
        <input type="hidden" class="notRequired" id="name" name="name" value="${ rtnDto.name }" />
        <input type="hidden" class="notRequired" id="hpNo" name="hpNo" value="${ rtnDto.hpNo }" />
    </form>

        <!-- content 영역 start -->
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">비회원 신청내역 조회</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
                <!--LNB 종료-->

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
                                                        <img src="${ not empty rtnData.webPath ? rtnData.webPath : '/common/images/img-main-training-offline-01.jpg' }" alt="">
                                                    </div>
                                                    <div class="txt-area">
                                                        <div class="top-line">
                                                            <div class="sort-label-area">
                                                                <p class="label"><span>${ rtnData.prntCdNm }</span></p>
                                                                <p class="label"><span>${ rtnData.ctgryCdNm }</span></p>
                                                            </div>
                                                            <p class="training-name f-title3">${ rtnData.nm }</p><!-- 2024-01-19 폰트 클래스 변경 -->
                                                            <p class="training-explain-txt">${ rtnData.smmryNm }</p>
                                                        </div>
                                                        <div class="class-property-w ">
                                                            <div class="property-list non-member"><!-- non-member: 비회원 가능 -->
                                                                <p class="txt">
                                                                    <span>비회원 신청 가능</span><!-- 2024-01-29 문구 변경 -->
                                                                </p>
                                                            </div>
                                                            <div class="property-list offline"><!-- offline: 집체교육 -->
                                                                <p class="txt">
                                                                    <span>집체교육</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list time"><!-- time: 학습시간 -->
                                                                <p class="txt">
                                                                    <span>${ rtnData.stduyDdCdNm }일(${ rtnData.stduyTimeCdNm }시간)</span>
                                                                </p>
                                                            </div>
                                                            <div class="property-list education"><!-- education: 교육상태 -->
                                                                <p class="txt">
                                                                    <span>${ fn:contains(ptcptData.sttsCd, 'CD02') ? '신청취소' : list.edctnStatusNm }</span>
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
                                                        <div class="cont-area">
                                                            <div class="info-list-w"><!-- 2023-12-18 w-small 클래스 삭제 -->
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">강사</p>
                                                                    <c:forEach var="isttr" items="${isttrList}" varStatus="status">
                                                                        <p class="txt f-body2">${ isttr.name }</p>
                                                                    </c:forEach>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">정원</p>
                                                                    <p class="txt f-body2">${ rtnData.fxnumImpsbYn eq 'N' ? '제한없음' : rtnData.fxnumCnt +='명' }(선착순 마감)</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육장소</p>
                                                                    <p class="txt f-body2"><a href="javascript:" title="교육장 안내 팝업 열기" id="mapBtn" data-mapchk="N" data-nm="${ roomDto.nm }" data-rprsnt-tel-no="${ roomDto.rprsntTelNo }" data-zipcode="${ roomDto.zipcode }" data-bsc-addr="${ roomDto.bscAddr }" data-dtl-addr="${ roomDto.dtlAddr }">${ roomDto.nm }</a></p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">교육일자</p>
                                                                    <p class="txt f-body2">${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ <br class="only-pc"/>${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } (${ kl:getDaysDiff(edctnStrtDtm, edctnEndDtm) +1 }일간)</p>
                                                                </div>
                                                                <div class="info-list">
                                                                    <p class="tit f-caption2">신청일시</p>
                                                                    <p class="txt f-body2">${ kl:convertDate(ptcptData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</p>
                                                                </div>
                                                            </div>
                                                            <!-- 2024-01-05 구조 변경 및 클래스 삭제 -->
                                                            <div class="btn-wrap">
                                                                <c:if test="${ not empty rtnData.edctnNtctnFileSeq }">
                                                                    <a class="btn-text-icon download" href="javascript:" data-file="/file/download?fileSeq=${ rtnData.edctnNtctnFileSeq }&fileOrd=0"><span>안내문</span></a>
                                                                </c:if>
                                                                <button class="btn-text-icon black-arrow" type="button" id="btnPicLayer" data-pic-nm="${ rtnData.picNm }" data-pic-email="${ rtnData.picEmail }" data-pic-tel-no="${ rtnData.picTelNo }"><span>담당자 문의</span></button>
                                                            </div>
                                                            <!-- // 2024-01-05 구조 변경 및 클래스 삭제 -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${ kl:getDaysDiff(today, kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-'))  > 0 and not fn:contains(ptcptData.sttsCd, 'CD02')}">
                                    <div class="btn-sec">
                                        <div class="btn-wrap align-right">
                                            <button class="btn-solid small gray-bg icon apply-cancel" type="button" id="btnCancel" data-edctn-Seq="${rtnData.edctnSeq}" data-ptcpt-seq="${ptcptData.ptcptSeq}"><span>신청취소</span></button>
                                        </div>
                                    </div>
                                    </c:if>
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
                                                <tr>
                                                    <th>사업자등록번호</th>
                                                    <td>${ kl:bsnmNoConvert(ptcptData.ptcptBsnmNo) }</td>
                                                </tr>
                                                <tr>
                                                    <th>업체명</th>
                                                    <td>${ ptcptData.ptcptCmpnNm }</td>
                                                </tr>
                                                <tr>
                                                    <th>성명</th>
                                                    <td>${ ptcptData.name }</td>
                                                </tr>
                                                <tr>
                                                    <th>부서</th>
                                                    <td>${ ptcptData.deptCdNm } (${ ptcptData.deptDtlNm })</td>
                                                </tr>
                                                <tr>
                                                    <th>직급</th>
                                                    <td>${ ptcptData.pstnCdNm } <c:if test="${ fn:contains(ptcptData.pstnCd, 'MEM_CD01007') }">(${ ptcptData.pstnNm })</c:if></td>
                                                </tr>
                                                <tr>
                                                    <th>핸드폰 번호</th>
                                                    <td>${ kl:hpNum(ptcptData.hpNo) }</td>
                                                </tr>
                                                <tr>
                                                    <th>이메일</th>
                                                    <td>${ ptcptData.email }</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-wrap align-right">
                                            <a class="btn-text-icon black-circle goList" href="javascript:" data-url="./list"><span>목록</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- content 영역 end -->



    <jsp:include page="/WEB-INF/jsp/front/eb/ebi/EBIPicLayer.jsp"></jsp:include><!-- 문의 담당자 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/ebi/EBIEduRoomLayer.jsp"></jsp:include><!-- 교육장 팝업 -->


</div>




