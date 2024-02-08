<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnData" value="${rtnInfo.rtnData}" />
<c:set var="roomDto" value="${rtnInfo.roomDto}" />
<c:set var="isttrList" value="${rtnInfo.isttrList}" />
<c:set var="rtnTrgtData" value="${rtnInfo.rtnTrgtData}" />
<c:set var="count" value="0" />

<c:set var="accsStatusClass" value="" />
<c:set var="accsStatusText" value="" />
<c:choose>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수대기') }">
        <c:set var="accsStatusClass" value="waiting" />
        <c:set var="accsStatusText" value="접수대기" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '접수중') }">
        <c:set var="accsStatusClass" value="accepting" />
        <c:set var="accsStatusText" value="신청하기" />
    </c:when>
    <c:when test="${ fn:contains(rtnData.accsStatusNm, '마감') }">
        <c:set var="accsStatusClass" value="end" />
        <c:set var="accsStatusText" value="마감" />
    </c:when>
</c:choose>

<c:set var="parseEdctnStrtDtm" value="${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />
<c:set var="parseEdctnEndDtm" value="${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-') }" />

<div data-controller="controller/eb/ebi/EBINonMemberDetailCtrl">
    <form name="frmGo" method="GET" action="" data-del-type="none">
        <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.edctnSeq}" />
    </form>

        <!-- content 영역 start -->
        <div class="cont-wrap"><!-- 2024-01-04 no-mgt 클래스 삭제 -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육신청</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
                <!--LNB 종료-->

                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
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
                                                <p class="training-name f-title1">${ rtnData.nm }</p>
                                                <p class="training-explain-txt">${ rtnData.smmryNm }</p>
                                            </div>
                                            <div class="class-property-w">
                                                <div class="property-list non-member"><!-- non-member: 비회원 가능 -->
                                                    <p class="txt">
                                                        <span>비회원 신청 가능</span>
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
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="roundSection">
                            <div class="for-motion">
                                <div class="sec-tit-area non-block"><!-- non-block : pc/mobile 둘다 flex -->
                                    <p class="f-title3">과정정보</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="index-list-w">
                                        <div class="list-item ${accsStatusClass}"><!-- available: 신청 가능한 회차 --><!-- accepting: 접수중 -->
                                            <div class="cont">
                                                <!-- 2024-01-23 button 태그에서 div로 수정 -->
                                                <div class="top-area">
                                                    <div class="left">
                                                        <div class="group">
                                                            <div class="status-info-w">
                                                                <p class="box-label bigger ${accsStatusClass}"><span>${ rtnData.accsStatusNm }</span></p>
                                                                <!--
                                                                  접수중: accepting
                                                                  접수대기: waiting
                                                                  접수마감: end
                                                                -->
                                                            </div>
                                                            <p class="index-num f-head">${ rtnData.nm }</p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- //2024-01-23 button 태그에서 div로 수정 -->
                                                <div class="cont-area">
                                                    <div class="info-list-w">
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
                                                            <p class="txt f-body1"><a href="javascript:" title="교육장 안내 팝업 열기" id="mapBtn" data-mapchk="N" data-nm="${ roomDto.nm }" data-rprsnt-tel-no="${ roomDto.rprsntTelNo }" data-zipcode="${ roomDto.zipcode }" data-bsc-addr="${ roomDto.bscAddr }" data-dtl-addr="${ roomDto.dtlAddr }">${ roomDto.nm }</a></p>
                                                        </div>
                                                        <div class="info-list">
                                                            <p class="tit f-caption2">접수기간</p>
                                                            <p class="txt f-body2">${ kl:convertDate(rtnData.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ ${ kl:convertDate(rtnData.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</p>
                                                        </div>
                                                        <div class="info-list">
                                                            <p class="tit f-caption2">교육기간</p>
                                                            <p class="txt f-body2">${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ ${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } (${ kl:getDaysDiff(parseEdctnStrtDtm, parseEdctnEndDtm) +1 }일간)</p>
                                                        </div>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-set v-align-center">
                                                            <c:if test="${ not empty rtnData.edctnNtctnFileSeq }">
                                                            <button class="btn-text-icon download" data-file="/file/download?fileSeq=${ rtnData.edctnNtctnFileSeq }&fileOrd=0"><span>안내문</span></button>
                                                            </c:if>
                                                            <button class="btn-text-icon black-arrow" type="button" id="btnPicLayer" data-pic-nm="${ rtnData.picNm }" data-pic-email="${ rtnData.picEmail }" data-pic-tel-no="${ kl:hpNum(rtnData.picTelNo) }"><span>담당자 문의</span></button>
                                                        </div>
                                                        <div class="btn-set">
                                                            <a class="btn-solid small ${ fn:contains(rtnData.accsStatusNm, '접수중') ? 'black-bg goStep' : 'gray-bg disabled' }" href="javascript:" data-url="./step1"><span>${ accsStatusText }</span></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="outlineSection">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">과정소개</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="txt-sec">
                                        <div class="ul-txt-w highlight">
                                            <div class="ul-txt-list">
                                                <p class="ul-txt">${ rtnData.itrdcCntn }</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec anchor-con scroll-motion" id="goalSection">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습목표</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="txt-sec">
                                        <div class="ul-txt-w highlight">
                                            <div class="ul-txt-list">
                                                <%--<p class="ul-txt has-dot">${ rtnData.stduyTrgtCntn }</p>--%>
                                                <p class="ul-txt">${ rtnData.stduyTrgtCntn }</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습대상</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll">
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>회사</th>
                                                    <td>
                                                        <c:forEach var="target" items="${ rtnTrgtData }" varStatus="status">
                                                            <c:if test="${ fn:contains(target.targetCd, 'ED_TARGET01') }">
                                                                <c:if test="${ count ne 0 }">,&nbsp;</c:if>${ target.targetCdNm }
                                                                <c:set var="count" value="${ count + 1}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:set var="count" value="0" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>업종</th>
                                                    <td>
                                                        <c:forEach var="target" items="${ rtnTrgtData }" varStatus="status">
                                                            <c:if test="${ fn:contains(target.targetCd, 'ED_TARGET02') }">
                                                                <c:if test="${ count ne 0 }">,&nbsp;</c:if>${ target.targetCdNm }
                                                                <c:set var="count" value="${ count + 1}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:set var="count" value="0" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>직무</th>
                                                    <td>
                                                        <c:forEach var="target" items="${ rtnTrgtData }" varStatus="status">
                                                            <c:if test="${ fn:contains(target.targetCd, 'ED_TARGET03') }">
                                                                <c:if test="${ count ne 0 }">,&nbsp;</c:if>${ target.targetCdNm }
                                                                <c:set var="count" value="${ count + 1}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:set var="count" value="0" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>직급</th>
                                                    <td>
                                                        <c:forEach var="target" items="${ rtnTrgtData }" varStatus="status">
                                                            <c:if test="${ fn:contains(target.targetCd, 'ED_TARGET04') }">
                                                                <c:if test="${ count ne 0 }">,&nbsp;</c:if>${ target.targetCdNm }
                                                                <c:set var="count" value="${ count + 1}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:set var="count" value="0" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>기타</th>
                                                    <td>
                                                        <c:forEach var="target" items="${ rtnTrgtData }" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${ fn:contains(target.targetCd, 'ED_TARGET05') }">
                                                                    ${ target.etcNm }
                                                                    <c:set var="count" value="${ count + 1}" />
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>
                                                        <c:if test="${ count eq 0 }">
                                                            -
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습 기본정보</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="table-sec">
                                        <div class="table-box need-scroll">
                                            <table class="basic-table">
                                                <caption>신청자 기본 정보</caption>
                                                <colgroup>
                                                    <col style="width: 273rem;">
                                                    <col style="width: 820rem;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>학습방식</th>
                                                    <td>집체교육</td>
                                                </tr>
                                                <!-- 2024-01-04 삭제 -->
                                                <!-- <tr>
                                                  <th>수료기준</th>
                                                  <td>출석 80% 이상, 평가 90점 이상</td>
                                                </tr> -->
                                                <!-- // 2024-01-04 삭제 -->
                                                <tr>
                                                    <th>학습시간</th>
                                                    <td>${ rtnData.stduyDdCdNm }일 / ${ rtnData.stduyTimeCdNm }시간</td>
                                                </tr>
                                                <tr>
                                                    <th>학습 준비물</th>
                                                    <td>${ rtnData.stduySuplsNm }</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cont-sec scroll-motion">
                            <div class="for-motion">
                                <div class="sec-tit-area">
                                    <p class="f-title3">학습내용</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="curriculum-div">
                                        <c:choose>
                                            <c:when test="${ device eq 'P' }">
                                                ${ rtnData.pcStduyCntn }
                                            </c:when>
                                            <c:when test="${ device eq 'M' }">
                                                ${ rtnData.mblStduyCntn }
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="page-bot-btn-sec scroll-motion">
                        <div class="btn-wrap align-center for-motion">
                            <a class="btn-solid small black-bg" href="/education/apply/list"><span>목록</span></a><!-- 2024-01-25 버튼명 변경 -->
                        </div>
                    </div>

                    <!-- 접수중 하단 플로팅 영역 -->
                    <c:if test="${ fn:contains(rtnData.accsStatusNm, '접수중') }">
                    <div class="accepting-fixed-area">
                        <div class="for-position">
                            <button class="open-click-area" type="button">
                                <p class="tit"><span class="status">${ rtnData.accsStatusNm }</span></p>
                                <div class="btn-text-icon plus"><span>더보기</span></div>
                            </button>

                            <div class="hide-area">
                                <div class="inner-con">
                                    <div class="tit-area">
                                        <p class="f-title1">${ rtnData.nm }</p>
                                    </div>
                                    <div class="con-area">
                                        <div class="scroll-area">
                                            <div class="info-line-list-w">
                                                <div class="list">
                                                    <p class="tit">접수일자</p>
                                                    <p class="txt">${ kl:convertDate(rtnData.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ ${ kl:convertDate(rtnData.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">교육일자</p>
                                                    <p class="txt">${ kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } ~ ${ kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } (${ kl:getDaysDiff(parseEdctnStrtDtm, parseEdctnEndDtm) +1 }일간)</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">강사</p>
                                                    <p class="txt">
                                                        <!-- <span class="img"><img src="/common/images/img-instructor-profile-01.jpg" alt=""></span> -->
                                                        <c:forEach var="isttr" items="${isttrList}" varStatus="status">
                                                        <span><c:if test="${ not status.first }">,&nbsp;</c:if>${ isttr.name }</span>
                                                        </c:forEach>
                                                    </p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">정원</p>
                                                    <p class="txt">${ rtnData.fxnumImpsbYn eq 'N' ? '제한없음' : rtnData.fxnumCnt +='명' }</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">교육장소</p>
                                                    <p class="txt">${ roomDto.nm }</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">학습방식</p>
                                                    <p class="txt">집체교육</p>
                                                </div>
                                                <div class="list">
                                                    <p class="tit">학습시간</p>
                                                    <p class="txt">${ rtnData.stduyDdCdNm }일 / ${ rtnData.stduyTimeCdNm }시간</p>
                                                </div>
                                                <!-- 2024-01-24 첨부파일 영역 추가 -->
                                                <c:if test="${ not empty rtnData.edctnNtctnFileSeq }">
                                                <div class="list">
                                                    <p class="tit">첨부파일</p>
                                                    <div class="txt">
                                                        <div class="btn-wrap">
                                                            <a class="btn-text-icon download" href="javascript:" data-file="/file/download?fileSeq=${ rtnData.edctnNtctnFileSeq }&fileOrd=0"><span>${ rtnData.orgnFileNm }</span></a>
                                                        </div>
                                                    </div>
                                                </div>
                                                </c:if>
                                                <!-- // 2024-01-24 첨부파일 영역 추가 -->
                                            </div>
                                        </div>
                                    </div>

                                    <div class="btn-wrap">
                                        <div class="btn-set">
                                            <!-- 2024-01-24 버튼 삭제-->
                                            <!-- <a class="btn-solid small gray-bg has-icon tel" href="javascript:" title="회차 담당자 문의하기"><span>회차 담당자 문의</span></a>
                                            <a class="btn-solid small gray-bg has-icon download" href="javascript:" download title="안내문 다운로드"><span>안내문 다운로드</span></a> -->
                                            <!-- // 2024-01-24 버튼 삭제-->
                                        </div>
                                        <div class="btn-set">
                                            <a class="btn-solid small black-bg goStep" href="javascript:" data-url="./step1"><span>신청하기</span></a>
                                        </div>
                                    </div>

                                    <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>

                    <!-- 앵커 영역 -->
                    <!-- 2023-12-27 비회원일 경우 메뉴명 수정 -->
                    <div class="anchor-btn-w">
                        <button class="anchor-btn active" href="javascript:">
                            <span>과정정보</span>
                        </button>
                        <button class="anchor-btn" href="javascript:">
                            <span>과정소개</span>
                        </button>
                        <button class="anchor-btn" href="javascript:">
                            <span>학습정보</span>
                        </button>
                    </div>
                    <!-- //2023-12-27 비회원일 경우 메뉴명 수정 -->
                </div>
            </div>
        </div>
        <!-- content 영역 end -->



    <jsp:include page="/WEB-INF/jsp/front/eb/ebi/EBIPicLayer.jsp"></jsp:include><!-- 문의 담당자 팝업 -->
    <jsp:include page="/WEB-INF/jsp/front/eb/ebi/EBIEduRoomLayer.jsp"></jsp:include><!-- 교육장 팝업 -->


</div>




