<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion" data-controller="controller/mp/mpb/MPBAcomWriteCtrl">
    <div class="cont-sec no-border scroll-motion">
        <div class="for-motion">
            <div class="sec-tit-area">
                <p class="f-title3">사업신청 정보</p>
            </div>
            <div class="sec-con-area">
                <div class="gray-bg-sec">
                    <div class="data-view-w">
                        <p class="data-view-tit f-head">추천자 정보</p>
                        <div class="data-view-form">
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">추천자</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.rcmndName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">휴대폰번호</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.rcmndHpNo}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">부품사명</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.rcmndCmpnNm}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">부서</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.rcmndDeptNm}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">직급</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.rcmndPstnNm}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="data-view-w">
                        <p class="data-view-tit f-head">포상대상자 정보</p>
                        <div class="data-view-form">
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">포상부문</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">
                                        ${rtnData.ctgryNm}
                                        <c:forEach var="cdList" items="${rtnData.prizeList}" varStatus="status">
                                            <c:if test="${rtnData.prizeCd eq cdList.prizeCd}">
                                                ${cdList.prizeCdNm}
                                            </c:if>>
                                        </c:forEach>
                                    </p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">부품사명</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${ rtnData.bsnmNoNm }</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">구분</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">
                                        <c:choose>
                                            <c:when test="${rtnData.newCtgryCd == 'COMPANY01002'}">
                                                2차
                                            </c:when>
                                            <c:otherwise>
                                                1차
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">이름</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.newName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">부서</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.deptNm}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">직급</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.newPstnCd}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">연령</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.age}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">근속년수</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.yrssvYearCnt}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">휴대폰번호</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.newHpNo}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">회사 전화번호</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.newCmpnTelNo}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="th">
                                    <p class="title f-body2">이메일</p>
                                </div>
                                <div class="td">
                                    <p class="txt f-body1">${rtnData.newEmail}</p>
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
                <p class="f-title3">사업진행상황</p>
            </div>
            <div class="sec-con-area">
                <div class="article-sec">
                    <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                        <div class="list-item active"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                            <form name="frmData" id="frmData">
                                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" class="notRequired" name="bsnCd" value="${rtnData.bsnCd}" />
                                <input type="hidden" class="notRequired" name="finalRsumeSeq" value="${rtnData.rsumeSeq}" />
                                <input type="hidden" class="notRequired" name="rsumeOrd" value="2" />
                                <a class="acco-click-area" href="javascript:">
                                    <div class="txt-box">
                                        <p class="tit f-head">1차 심사</p>
                                    </div>
                                    <p class="box-label bigger arr">
                                        <span>
                                        <input type="hidden" id="finalAppctnSttsCd" name="finalAppctnSttsCd" value="">
                                            <c:choose>
                                                <c:when test="${rtnData.appctnSttsCd eq 'PRO_TYPE05001_01_001'}">
                                                    접수완료
                                                </c:when>
                                                <c:when test="${rtnData.appctnSttsCd eq 'PRO_TYPE05001_01_002'}">
                                                    탈락
                                                </c:when>
                                                <c:otherwise>
                                                    통과
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>
                                </a>
                                <div class="acco-hide-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                        <div class="form-group">
                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                <c:forEach var="itemOptn" items="${rtnData.optFileList}" varStatus="status1">
                                                                    ${rtnData.optFileList}
                                                                    <p class="empty-txt">
                                                                        <a href="/file/download?fileSeq=${rtnData.appctnFileSeq}&fileOrd=${rtnData.fileSeq}" download="" title="파일 다운로드"></a>
                                                                    </p>
                                                                </c:forEach>
                                                            </div>
                                                            <!-- 2024-01-03 추가 -->
                                                            <div class="file-prev-area">
                                                                <a href="/file/download?fileSeq=${rtnData.appctnFileSeq}&fileOrd=${rtnData.fileSeq}-1" download="" title="파일 다운로드"></a>
                                                            </div>
                                                            <!-- 2024-01-03 추가 -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
