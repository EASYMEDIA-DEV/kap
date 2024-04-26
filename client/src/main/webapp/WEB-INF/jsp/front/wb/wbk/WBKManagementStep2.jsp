<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap" data-controller="controller/wb/wbk/WBKManagementCtrl controller/wb/wbk/WBKPartWriteCtrl controller/co/COFormCtrl" >
  <form id="frmData" name="frmData" enctype="multipart/form-data">
  <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <input type="hidden" class="notRequired" name="episdSeq" value="${rtnData.episdSeq}" />
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
          <div class="for-motion" >
            <div class="sec-tit-area">
              <p class="f-title3">신청 팀장 정보 및 신청정보를 입력해주세요</p>
              <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
            </div>
            <div class="sec-con-area">
              <div class="data-enter-form">
                <div class="row">
                  <div class="form-checkbox">
                    <input type="checkbox" id="infoSameChk" name="">
                    <label for="infoSameChk">신청자 정보와 동일</label>
                    <%-- 신청자 정보 --%>
                    <input type="hidden" id="sameNm" value="${rtnUser.name}"/>
                    <input type="hidden" id="sameHpNo" value="${rtnUser.hpNo}"/>
                    <input type="hidden" id="sameEmail" value="${rtnUser.email}"/>
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
                            <input type="text" class="infoSame" id="rdName" name="rdName" placeholder="이름 입력" title="이름">
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
                            <input type="text" class="infoSame phoneCheck" id="rdHpNo" name="rdHpNo" placeholder="휴대폰번호 입력">
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
                        <%-- 등록시 팀장 이메일 --%>
                        <input type="hidden" name="rdEmail" value="">
                        <div class="form-group form-email">
                          <div class="form-input">
                            <input type="text" class="infoSame" id="rdEmailId" placeholder="이메일 입력" value="">
                          </div>
                          <div class="form-input">
                            <input type="text" class="infoSame" id="rdEmaildomain" placeholder="직접입력" value="">
                          </div>
                          <div class="form-select">
                            <select id="emailDomain" title="메일 선택">
                              <option value="default" selected>직접입력</option>
                              <option value="">easymedia.net</option>
                              <option value="">option2</option>
                              <option value="">option3</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="th">
                    <p class="title f-head">학교<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="form-group">
                          <div class="form-input">
                            <input type="text" id="RdSchlNm" name="rdSchlNm" placeholder="학교 입력">
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="th">
                    <p class="title f-head">학년<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="form-group">
                          <div class="form-input">
                            <input type="text" class="numberChk" id="RdGrd" name="rdGrd" placeholder="학년 입력(숫자만 기입)">
                          </div>
                          <div class="form-select">
                            <select name="rdGrdCd" title="졸업여부 선택">
                              <c:forEach items="${grdCd}" var="grdCd" step="1" >
                                <option value="${grdCd.cd}">${grdCd.cdNm}</option>
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
                    <p class="title f-head">참여구분<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="form-select">
                          <select class="ptcptType" name="ptcptType" title="참여구분 선택">
                            <option value="" selected="">선택</option>
                            <c:forEach var="cdDtlList" items="${cdDtlList.WBK_PTN}" varStatus="status">
                              <option value="${cdDtlList.cd}">${cdDtlList.cdNm}</option>
                            </c:forEach>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="th">
                    <p class="title f-head">주제<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="opt-group">
                          <c:forEach var="wbkbRegCtg" items="${wbkbRegCtg}" varStatus="status">
                            <div class="form-radio">
                              <input type="radio" id="themeCd${status.index}" name="themeCd" title="주제" value="${wbkbRegCtg.cd}" />
                              <label for="themeCd${status.index}">${wbkbRegCtg.cdNm}</label>
                            </div>
                          </c:forEach>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="th">
                    <p class="title f-head">세부 내용<span class="essential-mark color-sky">*</span></p>
                  </div>
                  <div class="td">
                    <div class="data-line-w">
                      <div class="data-line">
                        <div class="form-group">
                          <div class="form-textarea">
                            <textarea name="dtlCntn" class="ckeditorRequired" id="dtlCntn" cols="" rows="" placeholder="공모 주제 세부내용 입력"></textarea>
                            <div class="check-byte">
                              <p class="txt"><span class="current-byte">0</span>자</p>
                              <p class="txt"><span class="max-byte">500</span>자</p>
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
        <div class="cont-sec no-border scroll-motion partForm">
          <div class="for-motion initPartHtml">
            <div class="sec-tit-area">
              <p class="f-head">팀원 정보</p>
              <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
            </div>
            <div class="sec-con-area">
              <!-- 팀원 정보 추가가 될 때 생기는 영역 -->
              <div class="data-enter-form add-data addPart">
                <div class="data-line-w Participant">
                  <div class="data-line">
                    <p class="f-sub-head partFormNm">팀원 1</p>
                    <div class="add-data-form">
                      <div class="btn-wrap align-right">
                        <button class="btn-text-icon delete deleteBtn" href="javascript:"><span>삭제</span></button>
                      </div>
                      <div class="data-enter-form">
                        <div class="row">
                          <div class="th">
                            <p class="title f-head">이름<span class="essential-mark color-sky">*</span></p>
                          </div>
                          <div class="td">
                            <div class="data-line-w">
                              <div class="data-line">
                                <div class="form-group">
                                  <div class="form-input">
                                    <input type="text" class="name" name="partList[0].name" placeholder="이름 입력">
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
                                    <input type="text" class="hpNo phoneCheck" name="partList[0].hpNo" placeholder="휴대폰번호 입력">
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
                                <input type="hidden" class="email" name="partList[0].email" value="">
                                <%-- 등록시 팀원 이메일 --%>
                                <div class="form-group form-email">
                                  <div class="form-input">
                                    <input type="text" class="partEmailId" placeholder="이메일 입력">
                                  </div>
                                  <div class="form-input">
                                    <input type="text" class="partEmailDomain" placeholder="직접입력">
                                  </div>
                                  <div class="form-select">
                                    <select class="partDomainChk" title="메일 선택">
                                      <option value="default" selected>직접입력</option>
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
                        </div>
                        <div class="row">
                          <div class="th">
                            <p class="title f-head">학교<span class="essential-mark color-sky">*</span></p>
                          </div>
                          <div class="td">
                            <div class="data-line-w">
                              <div class="data-line">
                                <div class="form-group">
                                  <div class="form-input">
                                    <input type="text" class="schlNm" name="partList[0].schlNm" placeholder="학교 입력">
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="th">
                            <p class="title f-head">학년<span class="essential-mark color-sky">*</span></p>
                          </div>
                          <div class="td">
                            <div class="data-line-w">
                              <div class="data-line">
                                <div class="form-group">
                                  <div class="form-input">
                                    <input type="text" class="grd numberChk" name="partList[0].grd" placeholder="학년 입력(숫자만 기입)">
                                  </div>
                                  <div class="form-select">
                                    <select class="grdCd" name="partList[0].grdCd" title="졸업여부 선택">
                                      <c:forEach items="${grdCd}" var="grdCd" step="1" >
                                        <option value="${grdCd.cd}">${grdCd.cdNm}</option>
                                      </c:forEach>
                                    </select>
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
                <p class="noti-txt f-caption2 partNoti">* 팀원은 최대 2명까지 신청가능합니다.</p>
              </div>
              <!-- // 팀원 정보 추가가 될 때 생기는 영역 -->
              <div class="btn-wrap align-center">
                <a class="btn-solid small gray-bg addBtn" href="javascript:"><span>팀원 정보 추가</span></a>
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
                        <p class="data-title f-body1">신청서</p>
                        <div class="form-group">
                          <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                            <div class="file-list">
                              <p class="empty-txt">선택된 파일 없음</p>
                              <button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button>
                            </div>
                          </div>
                          <div class="file-btn-area">
                            <%--<input type="file" id="searchFile">
                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>--%>
                            <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE10" title="첨부파일유형"/>
                            <input type="file" class="searchFile" id="searchFile1" name="atchFile1" accept="jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip" class="fileInput notRequired"/>
                            <label class="btn-solid gray-bg" for="searchFile1">파일 찾기</label>
                          </div>
                          <div class="btn-wrap btn-down-wrap">
                            <a class="btn-text-icon download fileDown" href="javascript:" data-url="/file/download?fileSeq=${rtnData.fileSeq}&fileOrd=0" download="" title="양식 다운로드"><span>양식 다운로드</span></a>
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
                            <p class="txt f-body2">다음은 자동차부품산업진흥재단 주관 ‘미래자동차산업 아이디어 공모전’ 개최 관련, 아래와 같이 귀하의 개인정보를 수집·이용하기 위해 『개인정보보호법』 제 15조, 제 17조 및 제 22조에 따른 관련 사항과 유의사항을 안내드립니다.</p>
                          </div>
                          <div class="paragraphs">
                            <p class="txt f-body2">
                              1. 개인정보의 수집 및 이용목적
                              <br>공모 내용에 대한 개인 및 단체 식별과 공모전 신청 요건심사, 평가 후 평가결과 알림 및 수상자 선정
                            </p>
                            <p class="txt f-body2">
                              2. 수집하는 개인정보 항목
                              <br>성명, 휴대폰번호, 소속, 이메일 등
                            </p>
                            <p class="txt f-body2">
                              3. 개인정보의 보유 및 이용기간 동의일로부터 해지요청일 까지
                              <br>(단, 별도의 해지요청이 없을 경우 공모전 종료로부터 3개월 후 삭제)
                            </p>
                            <p class="txt f-body2">
                              4. 개인정보 수집‧이용 동의 거부 및 삭제요청
                              <br>위 개인정보 수집‧이용 동의에 거부할 수 있습니다. 다만 개인정보 수집에 대해 거부할 경우 공모건에 대한 식별 불가로 인해 심사가 불가하며 평가결과에 대한 알림이 불가능함을 안내드립니다.
                            </p>
                            <p class="txt f-body2">
                              5. 유의사항
                              <br>1) 접수된 응모작은 공모전 주최자가 입상한 응모작을 이용하는 것에 허락한 것으로 간주하여 최종 입상작은발표일로부터 2년간 비영리 공익목적으로 활용할 수 있음
                              <br/>- 응모작은 일체 저작권을 침해하지 않아야 하며, 관련 분쟁에 대한 책임은 응모자에게 있음(전문 표절검증 시스템을 통해 표절여부 검증 실시)
                              <br/>2) 타 공모전 수상작 표절, 참가신청서 허위정보 기재 등 중대한 결격사유 발생 시 수상 취소 및 상금 환수 조치함
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