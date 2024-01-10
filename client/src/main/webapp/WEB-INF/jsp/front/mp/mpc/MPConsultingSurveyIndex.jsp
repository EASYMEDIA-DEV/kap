<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">컨설팅 만족도 설문 조사 참여하기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box evaluation"><!-- evaluation: 평가하기 대기 페이지 -->
            <div class="cont-for-padding">
                <p class="f-title1">${rtnData.bsnYear} 상주${rtnData.cnstgNm} </p>
                <div class="sort-label-area">
                    <p class="label"><span>${rtnData.cnstgNm}</span></p>
                </div>

                <div class="def-list-w">
                    <div class="def-list">
                        <p class="tit f-head">신청업종/분야</p>
                        <p class="txt f-sub-head">
                        <c:if test="${fn:contains(rtnData.cbsnCd, 'NON')}">비금속분야- ${rtnData.cbsnNm}</p></c:if>
                        <c:if test="${fn:contains(rtnData.cbsnCd, 'METAL')}">금속분야- ${rtnData.cbsnNm}</p></c:if>
                        <c:if test="${fn:contains(rtnData.cbsnCd, 'INDUS')}">기타</p></c:if>

                    </div>
                    <div class="def-list">
                        <p class="tit f-head">위원</p>
                        <p class="txt f-sub-head">홍길동</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">지도기간</p>
                        <p class="txt f-sub-head">2023-01-01 ~ 2023-01-01</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">총 문항수</p>
                        <p class="txt f-sub-head">45문항</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">예상 응답시간</p>
                        <p class="txt f-sub-head">약 10분</p>
                    </div>
                </div>

                <div class="noti-txt-w">
                    <P class="bullet-noti-txt f-caption2">* 귀사의 무궁한 발전을 기원합니다.</P>
                    <P class="bullet-noti-txt f-caption2">* 재단에서는 귀하의 의견을 수렴하여 만족도 높은 컨설팅 사업을 진행 할 수 있도록 노력합니다.</P>
                    <P class="bullet-noti-txt f-caption2">* 귀하께서 기재하신 내용은 외부로 유출되거나 타 용도로 사용하지 않을 것을 약속드립니다.</P>
                </div>
            </div>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small gray-bg" href="javascript:"><span>신청내역 상세</span></a>
                        <a class="btn-solid small black-bg" href="./surveyStep2?detailsKey=${rtnData.cnstgSeq}"><span>참여하기</span></a>
                    </div>
                    <div class="btn-set">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>