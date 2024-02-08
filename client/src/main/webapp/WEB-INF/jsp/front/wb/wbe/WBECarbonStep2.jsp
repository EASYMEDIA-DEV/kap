<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbe/WBECarbonCtrl">
  <form id="frmData" name="frmData" enctype="multipart/form-data">
  <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnData.episdSeq}" />
    <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnUser.seq}" />
    <input type="hidden" class="notRequired" id="appctnBsnmNo" name="appctnBsnmNo" value="${rtnUser.bsnmNo}" />

    <input type="hidden" class="notRequired" id="year" name="year" value="${rtnRoundDtl.year}" />
    <input type="hidden" class="notRequired" id="episd" name="episd" value="${rtnRoundDtl.episd}" />
    <input type="hidden" class="notRequired" id="bsnmNo" name="bsnmNo" value="${rtnUser.bsnmNo}" />

    <input type="hidden" id="msg" value="${msg}">
  <div class="sub-top-vis-area apply-page consult-biz">
    <div class="page-tit-area">
      <p class="page-tit f-xlarge-title"><span class="for-move">사업신청</span></p>
      <div class="apply-step-w">
        <div class="for-move">
          <div class="step-list"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
            <p class="step-num">1</p>
            <p class="step-con">기본정보</p>
          </div>
          <div class="step-list ongoing">
            <p class="step-num">2</p>
            <p class="step-con">정보입력</p>
          </div>
          <div class="step-list ">
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
            <div class="sec-tit-area">
              <p class="f-title3">사업신청 정보를 입력해주세요</p>
              <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
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
                            <input type="text" id="sbrdnBsnmNo" name="sbrdnBsnmNo" placeholder="종된사업장번호 입력" maxlength="4">
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
                    <p class="title f-head">구축사업장 주소<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="middle-line">
                          <div class="form-checkbox">
                            <input type="checkbox" id="sameAsHQChk" name="">
                            <label for="sameAsHQChk">본사와 동일</label>
                          </div>
                        </div>
                        <div class="middle-line">
                          <div class="form-address">
                            <div class="form-group">
                              <div class="form-input">
                                <input type="text" id="zipCode" name="pbsnDtlList[0].pbsnZipcode" placeholder="우편번호" value="" readonly="">
                                <input type="hidden" id="oriZipCode" class="notRequired" value="${rtnData.zipcode}">
                              </div>
                              <div class="form-input w-longer">
                                <input type="text" id="bscAddr" name="pbsnDtlList[0].pbsnBscAddr" placeholder="주소" value="" readonly="">
                                <input type="hidden" id="oriBscAddr" class="notRequired" value="${rtnData.bscAddr}">
                              </div>
                              <div class="btn-wrap">
                                <button class="btn-solid small gray-bg" id="searchPostCode" type="button"><span>우편번호 찾기</span></button>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="form-input w-longest">
                                <input type="text" id="dtlAddr" name="pbsnDtlList[0].pbsnDtlAddr" placeholder="상세주소 입력" value="" maxlength="100">
                                <input type="hidden" id="oriDtlAddr" class="notRequired" value="${rtnData.dtlAddr}">
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
        </div>
        <div class="cont-sec no-border scroll-motion">
          <div class="for-motion">
            <div class="sec-tit-area">
              <p class="f-title3">사업신청을 위한 첨부파일을 등록해주세요</p>
              <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
            </div>
            <div class="sec-con-area">
              <div class="data-enter-form">
                <div class="row">
                  <div class="th">
                    <p class="title f-head">첨부파일<span class="essential-mark color-sky">*</span></p>
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
                            <input type="file" id="searchFile" name="atchFile" data-max-size="51943040" data-accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip" class="fileInput notRequired"/>
                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                          </div>
                          <div class="btn-wrap btn-down-wrap">
                            <c:forEach var="item" items="${rtnRoundDtl.smjList}" varStatus="status">
                              <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${item.crbnEmsnsFileSeq}&fileOrd=${item.fileOrd}" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
                              <input type="hidden" class="optnFile" value="${item.crbnEmsnsFileSeq}">
                            </c:forEach>
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
                      <div class="agree-box">
                        <div class="gray-bg-sec narrow-pad">
                          <div class="paragraphs">
                            <p class="txt f-body1">개인정보의 수집·이용에 대한 동의[필수]</p>
                            <p class="txt f-body2">
                              1. 개인정보의 수집·이용 목적
                              <br/>부품사를 대상으로 하는 부품사 탄소저감 설비 구축 지원과 관련한 안내 및 기타 정보 제공
                            </p>
                            <p class="txt f-body2">
                              2. 수집하는 개인정보 항목
                              <br/>부품사 탄소저감 구축 사업을 신청하는 부품사의 부품사 주소, 담당자 성명, 소속회사, 전화번호, 이메일주소
                            </p>
                            <p class="txt f-body2">
                              3. 개인정보 보유 및 이용기간
                              <br/>개인정보 수집·이용 동의시점으로부터 탄소저감 구축사업 종료시점까지(단, 동의 철회 시 즉시 파기)
                            </p>
                            <p class="txt f-body2">
                              4. 동의를 거부할 권리 및 동의 거부시 불이익 사항
                              <br/>귀하는 개인정보 수집·이동 동의를 거부할 권리가 있으며 동의 거부시에는 탄소저감 구축에 제한이 있을 수 있습니다.
                            </p>
                          </div>
                          <div class="paragraphs">
                            <p class="txt f-body1">개인정보의 제3자 제공에 대한 동의[필수]</p>
                            <p class="txt f-body2">
                              1. 개인정보를 제공받는자
                              <br/>자동차부품산업진흥재단, 탄소저감 설비 공급사, 탄소저감 설비 설치사, 기부금 출연사
                            </p>
                            <p class="txt f-body2">
                              2. 개인정보를 제공받는 자의 개인정보 이용 목적
                              <br/>부품사 지원공고문 발송, 현장 점검, 탄소저감 설비 설치 등 지원사업을 위한 제반 업무
                            </p>
                            <p class="txt f-body2">
                              3. 제공하는 개인정보 항목
                              <br/>부품사 탄소저감 구축 사업을 신청하는 부품사의 부품사 주소, 담당자 성명, 소속회사, 휴대전화번호(연락처), 이메일주소
                            </p>
                            <p class="txt f-body2">
                              4. 개인정보를 제공받는 자의 개인정보 보유 및 이용기간
                              <br/>개인정보 수집·이용 동의시점으로부터 탄소저감 구축사업 종료시점까지(단, 동의 철회 시 즉시 파기)
                            </p>
                            <p class="txt f-body2">
                              5. 동의를 거부할 권리 및 동의 거부시 불이익 사항
                              <br/>귀하는 개인정보 수집·이동 동의를 거부할 권리가 있으며 동의 거부시에는 탄소저감 구축에 제한이 있을 수 있습니다.
                            </p>
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
      </div>

      <div class="page-bot-btn-sec scroll-motion">
        <div class="btn-wrap for-motion">
          <div class="btn-set">
            <a class="btn-solid small gray-bg" href="javascript:"><span>취소</span></a>
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