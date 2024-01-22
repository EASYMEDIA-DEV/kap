<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/wb/wbj/WBJAutoMotiveCtrl">
  <form id="frmData" name="frmData" enctype="multipart/form-data">
    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" class="notRequired" id="firstIndex" name="firstIndex" value="${ rtnData.firstIndex }" />
    <input type="hidden" class="notRequired" id="recordCountPerPage" name="recordCountPerPage" value="${ rtnData.recordCountPerPage }" />
    <input type="hidden" class="notRequired" id="totalCount" name="totalCount" value="${ rtnData.totalCount }" />
    <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${param.episdSeq}" />
    <input type="hidden" class="notRequired" id="bsnmNo" name="bsnmNo" value="${param.bsnmNo}" />
    <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${param.memSeq}" />
    <input type="hidden" class="notRequired" id="name" value="${rtnUser.name}" />
    <input type="hidden" class="notRequired" id="hpNo" value="${rtnUser.hpNo}" />
    <input type="hidden" class="notRequired" id="pstnCdNm" value="${rtnUser.pstnCdNm}" />
    <input type="hidden" class="notRequired" id="deptCdNm" value="${rtnUser.deptNm}" />
    <input type="hidden" class="notRequired" id="cmpnNm" value="${rtnUser.cmpnNm}" />
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
            <p class="f-title3">추천자 정보를 입력해주세요</p>
            <p class="f-caption2 noti-txt">* 추천자 직급 자격: 임원 이상</p>
          </div>
          <div class="sec-con-area">
            <div class="data-enter-form">
              <div class="row">
                <div class="form-checkbox">
                  <input type="checkbox" id="infoSameChk" class="infoSameChk" name="">
                  <label for="infoSameChk">신청자 정보와 동일</label>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">이름</p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="rcmndName" name="rcmndName" class="infoSameChk" placeholder="이름 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">휴대폰번호</p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="rcmndHpNo" name="rcmndHpNo" class="infoSameChk" placeholder="휴대폰번호 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">부품사명</p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="rcmndCmpnNm" name="rcmndCmpnNm" class="infoSameChk" placeholder="부품사명 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">부서</p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="rcmndDeptNm" name="rcmndDeptNm" class="infoSameChk" placeholder="부서 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">직급</p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="rcmndPstnNm" name="rcmndPstnNm" class="infoSameChk" placeholder="직급 입력">
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
            <p class="f-title3">포상대상자 정보를 입력해주세요</p>
            <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
          </div>
          <div class="sec-con-area">
            <div class="data-enter-form">
              <div class="row">
                <div class="th">
                  <p class="title f-head">포상부문<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-select">
                          <select class="form-control srvTypeCd prizeCd" id="optPrize" name="prizeCd">
                            <option value="" selected="">선택</option>
                            <c:forEach var="prizeList" items="${optPrizeList}" varStatus="status">
                                <option value="${prizeList.prizeCd}">${prizeList.prizeCdNm}</option>
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
                  <p class="title f-head">부품사명 검색<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="hidden" class="notRequired" id="newBsnmNo" name="cmpnSeq" value="" />
                          <input type="text" id="bsnmNoNm" placeholder="">
                        </div>
                        <div class="btn-wrap">
                          <button type="button" class="btn-solid small gray-bg bsnmNoBtn" ><span>검색</span></button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">구분<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="ctgryNm" placeholder="" disabled>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">이름<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" name="newName" placeholder="이름 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">부서<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" id="deptNm" name="deptNm" placeholder="부서 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">직급<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-select">
                          <select class="form-control input-sm" id="" name="newPstnCd" title="직급 선택">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                              <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                <option value="${cdList.cd}">${cdList.cdNm}</option>
                              </c:if>
                            </c:forEach>
                          </select>
                        </div>
                        <div class="form-input">
                          <input class="notRequired" type="text" name="newPstnNm" placeholder="직급 입력">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">연령<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <p class="unit-txt">만</p>
                          <input type="number" id="age" name="age" placeholder="연령 입력">
                          <p class="unit-txt">세</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">근속년수<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="number" id="yrssvYearCnt" name="yrssvYearCnt" placeholder="근속년수 입력">
                          <p class="unit-txt">년</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">휴대폰번호<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" class="phoneChk" name="newHpNo" placeholder="휴대폰번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="14">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">회사 전화번호<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group">
                        <div class="form-input">
                          <input type="text" class="phoneChk" name="newCmpnTelNo" placeholder="회사 전화번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="14">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="th">
                  <p class="title f-head">이메일<span class="essential-mark color-sky">*</span></p>
                </div>
                <div class="td">
                  <div class="data-line-w">
                    <div class="data-line">
                      <div class="form-group form-email">
                        <div class="form-input">
                          <input type="text" class="firstEmail" placeholder="이메일 입력">
                        </div>
                        <div class="form-input">
                          <input type="text" class="secondEmail" placeholder="직접입력">
                        </div>
                        <div class="form-select">
                          <select id="emailSelect" title="메일 선택">
                            <option value="">직접입력</option>
                            <option value="naver.com">naver.com</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="nate.com">nate.com</option>
                            <option value="daum.net">daum.net</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <input type="hidden" class="notRequired newEmail" name="newEmail" value="">
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
                            <div class="file-list">
                            </div>
                          </div>
                          <div class="file-btn-area">
                            <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE01" title="첨부파일유형"/>
                            <input type="file" class="searchFile" id="searchFile" name="appctnFileSeq" data-file-field-nm="appctnFileSeq" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip" class="fileInput notRequired"/>
                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                          </div>
                          <div class="btn-wrap btn-down-wrap">
                            <c:forEach var="item" items="${rtnRoundDtl.optnList}" varStatus="status">
                              <a class="btn-text-icon download gray" href="/file/download?fileSeq=${item.fileSeq}&fileOrd=${item.optnOrd}" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
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
                      <!-- 2024-01-02 약관 추가 -->
                      <div class="agree-box">
                        <div class="gray-bg-sec narrow-pad">
                          <div class="paragraphs">
                            <p class="txt f-body1">개인·기업정보의 수집·이용·제공 동의 및 청렴서약서</p>
                          </div>
                          <div class="paragraphs">
                            <p class="txt f-body2">자동차부품산업진흥재단(이하 “재단”이라 한다)이 지원하는 상생지원사업(이하“사업”이라 한다)과 관련하여 재단이 개인 및 기업정보를 수집ㆍ이용하거나 제3자에게 제공하고자 하는 경우에는 「개인정보 보호법」 제15조 제1항 제1호, 제17조 제1항 제1호, 제23조 제1항 제1호, 제24조 제1항 제1호에 따라 본인의 동의를 얻어야 함.</p>
                          </div>
                          <div class="paragraphs">
                            <p class="txt f-body2">
                              1. 수집·이용 목적
                              <br/>재단의 사업 신청 접수 업무 수행
                              <br/>재단의 기업지원 이력관리 업무수행
                            </p>
                            <p class="txt f-body2">
                              2. 수집·이용 항목
                              <br/>기업명, 사업자등록번호, 기업경영규모, 성명(대표자, 책임자, 실무자), 생년월일, 전화번호, 휴대전화번호, 주소, e-mail 등 신청서에 기재되는 개인정보
                            </p>
                            <p class="txt f-body2">
                              3. 효력기간
                              <span class="list">
                                      사업자가 본 동의서를 제출하고, 최종 지원결정 시점 이후 효력 발생
                                      <br/>- 지원이 취소되거나 계약이 거절된 경우 그 시점부터 효력 소멸
                                      <br/>- 동의철회 또는 제공된 목적달성 후에는 지원 사업의 효율적인 운영을 위해 필요한 범위 내에서만 보유 및 이용
                                    </span>
                              <span class="list">
                                      기업정보 수집 시점 : 신청기업의 사업 참여 이전 3개년부터 참여 이후 10년간
                                      <br/>- 보유 정보가 없을 경우 수집하지 않음
                                    </span>
                            </p>
                            <p class="txt f-body2">
                              4. 청렴서약
                              <br/>본인(또는 상생사업 수행기업)은 자동차부품산업진흥재단이 주관하여 시행하는 사업을 수행함에 있어 신의성실의 원칙에 입각한 상호 신뢰를 바탕으로 다음 사항을 준수할 것을 선언합니다.
                              <br/>1. 본 사업의 목표를 효율적으로 달성하기 위해 최선을 다하고, 관련 규정 및 지침이 정하는 절차와 방법에 따라 사업을 성실히 수행하겠습니다.
                              <br/>2. 공정한 사업 수행을 저해할 수 있는 청탁, 알선, 금품이나 향응의 요구 및 제공 등 일체의 부정한 행위를 하지 않겠습니다.
                              <br/>3. 긍지와 자부심을 가지고 사업비를 깨끗하고 투명하게 사용하고 윤리경영에 앞장서 국민으로부터 신뢰받는 청렴한 사업을 수행하겠습니다.
                            </p>
                          </div>
                          <div class="paragraphs">
                            <p class="txt f-body2">※ 본인은 본 동의서 내의 정보의 수집･조회･활용 및 청렴서약에 관한 내용을 충분히 이해하고 동의하였습니다. </p>
                          </div>
                        </div>
                        <div class="form-group align-right">
                          <div class="form-checkbox">
                            <input type="checkbox" id="agreeChk" name="">
                            <label for="agreeChk">약관에 동의합니다.</label>
                          </div>
                        </div>
                      </div>
                      <!-- // 2024-01-02 약관 추가 -->
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
            <a class="btn-solid small gray-bg" id="goHome" href="javascript:"><span>취소</span></a>
          </div>
          <div class="btn-set">
            <a class="btn-solid small black-bg insert" href="javascript:"><span>신청하기</span></a>
          </div>
        </div>
      </div>
    </div>
  </div>
    <jsp:include page="/WEB-INF/jsp/front/wb/wbj/WBJAutoMotiveLayer.jsp"></jsp:include><!-- 부품사 팝업 -->
  </form>
</div>
<!-- content 영역 end -->
