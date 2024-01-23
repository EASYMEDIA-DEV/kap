<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" /></c:set>

<div class="cont-wrap" data-controller="controller/wb/wbd/WBDSafetyCtrl">
  <form id="frmData" name="frmData" enctype="multipart/form-data">
  <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnData.episdSeq}" />
    <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnUser.seq}" />
    <input type="hidden" class="notRequired" id="appctnBsnmNo" name="appctnBsnmNo" value="${rtnUser.bsnmNo}" />
    <div class="sub-top-vis-area apply-page consult-biz">
      <div class="page-tit-area">
        <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
        <div class="apply-step-w">
          <div class="for-move">
            <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
              <p class="step-num">1</p>
              <p class="step-con">기본정보</p>
            </div>
            <div class="step-list completed">
              <p class="step-num">2</p>
              <p class="step-con">정보입력</p>
            </div>
            <div class="step-list ongoing">
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
      <div class="cont-sec-w">
        <div class="cont-sec no-border scroll-motion">
          <div class="for-motion">
            <div class="status-con-box gray-bg completed">
              <div class="cont-for-padding">
                <p class="f-title1"><span class="color-sky">안전설비구축</span><br>사업신청이 완료되었습니다.</p>
                <div class="def-list-w">
                  <div class="def-list">
                    <p class="tit f-head">신청일시</p>
                    <p class="txt f-sub-head">${today}</p>
                  </div>
                  <div class="def-list">
                    <p class="tit f-head">신청정보</p>
                    <p class="txt f-sub-head">
                      <span class="txt-item">${rtnUser.name}</span>
                      <span class="txt-item">${rtnData.cmpnNm}</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div class="guide-info-area scroll-motion">
              <div class="for-motion">
                <div class="divide-box">
                  <p class="exclamation-txt f-body1">사업 신청 후에는 수정이 불가능하며, 사업 신청 취소 후 다시 접수해야 합니다.</p>
                  <p class="exclamation-txt f-body1">사업 신청 취소는 마이페이지 > 상생 사업 신청내역에서 가능합니다.</p>
                </div>
                <div class="divide-box"></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="page-bot-btn-sec scroll-motion">
        <div class="btn-wrap align-center for-motion">
          <div class="btn-set">
            <a class="btn-solid small black-bg" href="/my-page/coexistence/list"><span>신청내역 보기</span></a>
          </div>
        </div>
      </div>
    </div>
  </div>
  </form>
</div>
<!-- content 영역 end -->