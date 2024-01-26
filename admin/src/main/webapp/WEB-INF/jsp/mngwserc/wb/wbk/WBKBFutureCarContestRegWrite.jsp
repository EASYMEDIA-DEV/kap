<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="partList" value="${ not empty rtnInfo.partList ? rtnInfo.partList : rtnData}" />
<c:set var="rsumeList" value="${ not empty rtnInfo.rsumeList ? rtnInfo.rsumeList : rtnData}" />
<c:set var="fileDtlList" value="${ not empty rtnInfo.fileDtlList ? rtnInfo.fileDtlList : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />
<%--심사 진행 스텝 set--%>
<c:set var="lastStep" value="1" />
<c:forEach var="rsumeList2" items="${rsumeList}" varStatus="status">
    <c:set var="lastStep" value="${rsumeList2.rsumeOrd}" />
</c:forEach>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbk/WBKBFutureCarContestRegPartCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}등록</h6>


        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.appctnSeq}" />
            <input type="hidden" class="notRequired" id="bsnCd" name="bsnCd" value="INQ07011" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="" />
            <input type="hidden" class="notRequired" name="id" value="" />
            <input type="hidden" class="notRequired" id="rsumeSeq" name="rsumeSeq" value="${rtnDto.rsumeSeq}" />
            <!-- 미래차공모전 회차 1 고정 -->
            <input type="hidden" class="notRequired" id="episd" name="episd" value="1" />
            <input type="hidden" class="notRequired" id= "episdSeq" name="episdSeq" value="" />
            <!-- 첨부파일 순번 -->
            <%--<input type="hidden" class="notRequired" id="atchFileSeq" name="atchFileSeq" value="${rtnDto.atchFileSeq}" />--%>
            <!-- 미래차 신청팀 참여 구분 -->
            <input type="hidden" class="notRequired" id="ptcptType" name="ptcptType" value="" />
            <!-- 서류 심사 진행상태 구분 -->
            <input type="hidden" class="notRequired" id="rsumeOrd" name="rsumeOrd" value="${rtnDto.rsumeOrd}" />
            <input type="hidden" class="notRequired" id="partResume" name="appctnSttsCd" value="${rtnDto.appctnSttsCd}" />
            <input type="hidden" class="notRequired" id="hghstWinerYn" name="hghstWinerYn" value="${rtnDto.hghstWinerYn}" />
            <input type="hidden" class="notRequired" id="wdcrmCd" name="wdcrmCd" value="" />


            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle}회차 정보</h7>
            <hr >


            <fieldset class="lastStep" data-laststep="${lastStep}">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                <option value="">연도 전체</option>
                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                    <option value="${optYear}" <c:if test="${rtnDto.year eq optYear}">selected</c:if> >${optYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>

            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle}신청자 정보</h7>
            <hr >

            <fieldset>
                <div class="form-group text-sm">
                   <label class="col-sm-1 control-label">신청자<span class="star"> *</span></label>
                       <div class="col-sm-5" style="margin-left: -15px">
                           <div class="col-sm-6">
                               <input type="hidden" class="form-control" id="name" name="name" value="<c:if test="${not empty rtnDto}">${rtnDto.id}</c:if><c:if test="${empty rtnDto}"></c:if>" title="신청자"/>
                               <input type="text" class="form-control" id="id" name="id" value="<c:if test="${not empty rtnDto}">${rtnDto.id}</c:if><c:if test="${empty rtnDto}"></c:if>" title="신청자" maxlength="50" disabled/>
                           </div>
                           <c:if test="${empty rtnDto}">
                           <div class="col-sm-1">
                               <button type="button" id="btnPartUserModal" class="btn btn-sm btn-info">회원검색</button>
                           </div>
                           </c:if>
                       </div>
                    <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="hpNo" name="hpNo" value="<c:if test="${not empty rtnDto}">${rtnDto.hpNo}</c:if>">${rtnDto.hpNo}</p>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="email" name="email" value="<c:if test="${not empty rtnDto}">${rtnDto.email}</c:if>">${rtnDto.email}</p>
                    </div>
                    <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="telNo" name="telNo" value="<c:if test="${not empty rtnDto}">${rtnDto.telNo}</c:if><c:if test="${empty rtnDto}"></c:if>" title="전화번호" maxlength="50"/>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">성별<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="gndr" name="gndㅅr" value="<c:if test="${not empty rtnDto}">
                        ${rtnDto.gndr == 1 ? 'A' : (rtnDto.gndr == 2 ? '여자' : '')}
                        </c:if>">${rtnDto.gndr == 1 ? '남자' : (rtnDto.gndr == 2 ? '여자' : '')}</p>
                    </div>
                    <label class="col-sm-1 control-label">생년월일<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <p class="form-control-static" id="birth" name="birth" value="<c:if test="${not empty rtnDto}">${rtnDto.birth}</c:if>">${rtnDto.birth}</p>
                    </div>
                </div>
            </fieldset>


            <div class="row">
                <div class="col-md-6">
                    <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle}팀장 정보</h7>
                </div>
                <div class="col-md-6 text-right">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" id="regUserSame" value="" name="regUserSame" class="checkboxSingle notRequired" data-auth-cd="${list.authCd}"/>
                        <span class="ion-checkmark-round"></span>신청자 정보와 동일
                    </label>
                </div>
            </div>
            <hr >
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label alterSameRegUserInfo">이름<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm alterSameRegUserInfo nameChk" id="rdName" name="rdName" value="${rtnDto.rdName}" maxlength="200" title="팀장명" placeholder="이름" style="width:100%"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label alterSameRegUserInfo">휴대폰번호<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm alterSameRegUserInfo mobileChk" id="rdHpNo" name="rdHpNo" value="${rtnDto.rdHpNo}" maxlength="200" title="휴대번호" placeholder="휴대번호 입력" style="width:100%"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm alterSameRegUserInfo emailChk" id="rdEmail" name="rdEmail" value="${rtnDto.rdEmail}" maxlength="200" title="이메일 주소" placeholder="이메일 주소" style="width:100%"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학교<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="RdSchlNm" name="rdSchlNm" value="${rtnDto.rdSchlNm}" maxlength="200" title="학교명" placeholder="학교 입력" style="width:100%"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">학년<span class="star"> *</span></label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" id="RdGrd" name="rdGrd" value="${rtnDto.rdGrd}" maxlength="200" title="학년" placeholder="학년 입력" style="width:100%"/>
                    </div>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" name="rdGrdCd">
                                <c:forEach items="${grdCd}" var="grdCd" step="1" >
                                    <option value="${grdCd.cd}" <c:if test="${rtnDto.rdGrdCd eq grdCd.cd}">selected</c:if>>${grdCd.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주제<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:forEach var="wbkbRegCtg" items="${wbkbRegCtg}" varStatus="status">
                            <label class="radio-inline c-radio">
                                <input type="radio" class="rboxmenuType" data-name="themeCdList" name="themeCd" title="주제" value="${wbkbRegCtg.cd}" <c:if test="${wbkbRegCtg.cd eq rtnDto.themeCd}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${wbkbRegCtg.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">세부 내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                    <textarea maxlength="30" class="col-sm-12 pv ckeditorRequired" style="resize: vertical;" rows="10" placeholder="추가공지사항을 입력하세요" id="dtlCntn" name="dtlCntn" title="세부내용" oninput="cmmCtrl.checkMaxlength(this);">${rtnDto.dtlCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <%--//--%>

            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle}팀원 정보</h7>
            <fieldset id="rowInsert">
                <%--<div class="form-group text-sm examListContainer " style="display:none;">--%>
                <div class="form-group text-sm examListContainer" data-controller="controller/wb/wbk/WBKBFutureCarContestRegWriteCtrl" style="display:none;">
                    <div class="text-right">
                        <button type="button" class="btn btn-sm btn-inverse btnExamWrite">팀원정보 추가</button>
                            <%--<button type="button" class="btn btn-sm btn-danger btnExamDelete">팀원정보 삭제</button>--%>
                    </div>
                    <hr >
                    <c:choose>
                        <c:when test="${ partList != null }">
                            <c:forEach var="partList" items="${partList}" varStatus="qstnStatus">
                                <div class="col-sm-12 examList mt-sm pl0 pr0">
                                    <div class="col-sm-11 pl0 pr0" id="partInfo1">
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label ">이름<span class="star"> *</span></label>
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm nameChk" name="partName" value="${partList.name}" maxlength="200" title="이름 입력" placeholder="이름 입력" style="width:100%"/>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label ">휴대폰번호<span class="star"> *</span></label>
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm phoneChk" name="partHpNo" value="${partList.hpNo}" maxlength="200" title="휴대폰번호 입력" placeholder="휴대폰번호 입력" style="width:100%"/>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label ">이메일<span class="star"> *</span></label>
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm emailChk" name="partEmail" value="${partList.email}" maxlength="200" title="이메일 입력" placeholder="이메일 입력" style="width:100%"/>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label">학교<span class="star"> *</span></label>
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm" name="partSchlNm" value="${partList.schlNm}" maxlength="200" title="학교 입력" placeholder="학교 입력" style="width:100%"/>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label">학년<span class="star"> *</span></label>
                                                <div class="col-sm-5">
                                                    <input type="text" class="form-control input-sm" name="partGrd" value="${partList.grd}" maxlength="200" title="학년 입력" placeholder="학년 입력" style="width:100%"/>
                                                </div>
                                                <div class="col-sm-5" style="margin-left: -15px">
                                                    <div class="col-sm-6" style="margin-left: -15px">
                                                        <select class="form-control input-sm" name="partGrdCd">
                                                            <c:forEach items="${grdCd}" var="result" step="1">
                                                                <option value="${result.cd}" <c:if test="${partList.grdCd eq result.cd}">selected</c:if>>${result.cdNm}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <div class="text-right">
                                                <%--<button type="button" class="btn btn-sm btn-inverse btnExamWrite">팀원정보 추가</button>--%>
                                            <button type="button" class="btn btn-sm btn-danger btnExamDelete">팀원정보 삭제</button>
                                        </div>
                                        <br>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                        <div class="examHtmlTemplage">
                            <div class="col-sm-12 examList mt-sm pl0 pr0">
                                <div class="col-sm-11 pl0 pr0" id="partInfo">
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label ">이름<span class="star"> *</span></label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control input-sm nameChk" name="partName" value="" maxlength="200" title="이름 입력" placeholder="이름 입력" style="width:100%"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label ">휴대폰번호<span class="star"> *</span></label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control input-sm phoneChk" name="partHpNo" value="" maxlength="200" title="휴대폰번호 입력" placeholder="휴대폰번호 입력" style="width:100%"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label ">이메일<span class="star"> *</span></label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control input-sm emailChk" name="partEmail" value="" maxlength="200" title="이메일 입력" placeholder="이메일 입력" style="width:100%"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">학교<span class="star"> *</span></label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control input-sm" name="partSchlNm" value="" maxlength="200" title="학교 입력" placeholder="학교 입력" style="width:100%"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-1 control-label">학년<span class="star"> *</span></label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control input-sm" name="partGrd" value="" maxlength="200" title="이메일 주소" placeholder="이메일 주소 입력" style="width:100%"/>
                                        </div>
                                        <div class="col-sm-5" style="margin-left: -15px">
                                            <div class="col-sm-6" style="margin-left: -15px">
                                                <select class="form-control input-sm" name="partGrdCd">
                                                    <c:forEach items="${grdCd}" var="result" step="1">
                                                        <option value="${result.cd}" <c:if test="${rtnDto.partGrdCd eq result}">selected</c:if>>${result.cdNm}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                                <div class="text-right">
                                    <%--<button type="button" class="btn btn-sm btn-inverse btnExamWrite">팀원정보 추가</button>--%>
                                    <button type="button" class="btn btn-sm btn-danger btnExamDelete">팀원정보 삭제</button>
                                </div>
                                <br>
                            </div>
                            </div>
                        </div>
                    </div>
                </fieldset>

            <c:if test="${rtnDto != null}">
            <h7 class="mt0" style="margin-left:1%" ><em class="ion-play mr-sm"></em>
                전체 진행상태 : <c:choose>
                                    <c:when test="${lastStep eq 1}">서류 심사</c:when>
                                    <c:when test="${lastStep eq 2}">1차 심사</c:when>
                                    <c:otherwise>최종 심사</c:otherwise>
                              </c:choose>
            </h7>
            <%-- 서류 심사 --%>
            <div id="frmDataSpprt" data-controller="controller/wb/wbk/WBKBFutureCarContestRegRsumeCtrl">
                <div class="container-fluid">
                    <div class="panel-group" id="accRoot" role="tablist">
                        <%-- 영역 1 --%>
                        <div class="panel panel-default" id="pmndvPmtView" data-sttsCd="TYPE03001">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accRoot" href="#addLow1" aria-constrols="addLow1">서류 심사</a>
                            </div>
                            <div id="addLow1" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">사용자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" id="userCancel">
                                                    <c:choose>
                                                            <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                                            <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                                            <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                                            <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                                            <c:when test="${rsumeList[0].appctnSttsCd eq 'WBKB_REG_FRT004'}">사용자취소</c:when>
                                                            <c:otherwise>접수완료</c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">
                                                    <c:choose>
                                                        <c:when test="${rsumeList[0].appctnSttsChngDtm != null}">
                                                            ${kl:convertDate(rsumeList[0].appctnSttsChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired sttsChange" id="mngSttsCd1" title="관리자 상태값/결과">
                                                    <option value="" >미확인</option>
                                                    <c:forEach var="cdList" items="${adminStateCd}" varStatus="status">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeList[0].mngSttsCd eq cdList.cd}">selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">
                                                    <c:choose>
                                                        <c:when test="${rsumeList[0].mngSttsChngDtm != null}">
                                                            ${ kl:convertDate(rsumeList[0].mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset style="margin-bottom: 25px;">
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">신청서</label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                            <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE10" title="첨부파일유형"/>
                                            <%--<input type="hidden" class="notRequired" id="" name="appctnFileSeq" value="${rtnDto.fileSeq}" title="첨부파일유형"/>--%>
                                            <input type="hidden" class="notRequired" id="" name="appctnFileSeq" value="${fileDtlList[0].fileSeq}" title="첨부파일유형"/>
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="appctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
                                                    <div class="dz-default dz-message">
                                                        <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                    </div>
                                                </div>
                                                <p class="text-bold mt">
                                                    ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <%-- 1차 심사 --%>
                            <div class="panel panel-default" id="pmndvPmtView" data-sttsCd="TYPE03002">
                                <div class="panel-heading" role="tab">
                                    <a role="button" data-toggle="collapse" data-parent="#accRoot" href="#addLow2" aria-constrols="addLow2">1차 심사</a>
                                </div>
                                <div id="addLow2" class="panel-collapse collapse" role="tabpanel">
                                    <div class="panel-body">
                                        <h6 class="mt0">신청자</h6>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">사용자 상태값</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                                            <c:when test="${rsumeList[1].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                                            <c:otherwise>접수전</c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[1].mngSttsChngDtm != null}">
                                                                ${ kl:convertDate(rsumeList[1].mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>

                                        <h6 class="mt0">관리자</h6>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                                <div class="col-sm-6 form-inline">
                                                    <select class="form-control input-sm notRequired sttsChange" id="mngSttsCd2" title="관리자 상태값/결과">
                                                        <option value="" >미확인</option>
                                                        <c:forEach var="cdList" items="${adminStateCd}" varStatus="status">
                                                            <option value="${cdList.cd}"  <c:if test="${rsumeList[1].mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>

                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[1].mngSttsChngDtm != null}">
                                                                ${ kl:convertDate(rsumeList[1].mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>

                                        <c:set var="rsumeListSize" value="${fn:length(rtnDto.rsumeList)}" />

                                        <fieldset style="margin-bottom: 25px;">
                                            <div class="form-group text-sm">
                                                <c:set var="rsumeListSize" value="${fn:length(rtnDto.rsumeList)}" />
                                                <label class="col-sm-1 control-label">논문${rsumeListSize}</label>
                                                <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE11" title="첨부파일유형"/>
                                                <input type="hidden" class="notRequired" name="firstFileSeq" value="${fileDtlList[1].fileSeq}" title="파일 시퀀스"/>
                                                <div class="col-sm-10 col-md-11">
                                                    <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                    <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                    <div class="dropzone attachFile notRequired" data-file-field-nm="firstFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
                                                        <div class="dz-default dz-message">
                                                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                        </div>
                                                    </div>
                                                    <p class="text-bold mt">
                                                        ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        <%-- 최종 심사 --%>
                            <div class="panel panel-default" id="pmndvPmtView" data-sttsCd="TYPE03003">
                                <div class="panel-heading" role="tab">
                                    <a role="button" data-toggle="collapse" data-parent="#accRoot" href="#addLow3" aria-constrols="addLow3">최종 심사</a>
                                </div>
                                <div id="addLow3" class="panel-collapse collapse" role="tabpanel">
                                    <div class="panel-body">
                                        <h6 class="mt0">신청자</h6>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">사용자 상태값</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT001'}">접수완료</c:when>
                                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT002'}">통과</c:when>
                                                            <c:when test="${rsumeList[2].appctnSttsCd eq 'WBKB_REG_FRT003'}">탈락</c:when>
                                                            <c:otherwise>접수전</c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[2].mngSttsChngDtm != null}">
                                                                ${ kl:convertDate(rsumeList[2].mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>

                                        <h6 class="mt0">관리자</h6>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">시상부문<span class="star rqWdcrmCd" style="display: none"> *</span></label>
                                                <div class="col-sm-6 form-inline">
                                                    <select class="form-control input-sm notRequired wdcrmCdBtn" name="wdcrmCd" title="시상부문" disabled="disabled">
                                                        <option value="" >선택</option>
                                                        <c:forEach var="cdList" items="${cdDtlList.WBK_AWD}" varStatus="status">
                                                            <option value="${cdList.cd}" <c:if test="${rtnDto.wdcrmCd eq cdList.cd}">selected</c:if>>
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                                <div class="col-sm-6 form-inline">
                                                    <select class="form-control input-sm notRequired sttsChange" id="mngSttsCd3" title="관리자 상태값/결과">
                                                        <option value="" >미확인</option>
                                                        <c:forEach var="cdList" items="${adminStateCd}" varStatus="status">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeList[2].mngSttsCd eq cdList.cd}">selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <div class="form-group text-sm">
                                                <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                                <div class="col-sm-6 form-inline">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${rsumeList[2].mngSttsChngDtm != null}">
                                                                ${ kl:convertDate(rsumeList[2].mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset style="margin-bottom: 25px;">
                                            <div class="form-group text-sm">
                                                <label class="col-sm-1 control-label">PT자료</label>
                                                <div class="col-sm-10 col-md-11">
                                                <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE12" title="첨부파일유형"/>
                                                <input type="hidden" class="notRequired" name="lastFileSeq" value="${fileDtlList[2].fileSeq}" title="첨부파일유형"/>
                                                    <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                    <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                    <div class="dropzone attachFile notRequired" data-file-field-nm="lastFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
                                                        <div class="dz-default dz-message">
                                                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                                        </div>
                                                    </div>
                                                    <p class="text-bold mt">
                                                        ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                                                    </p>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
            </c:if>

            <c:if test="${rtnDto == null}">
            <fieldset style="margin-bottom: 25px;">
            <h7 class="mt0" style="margin-left:1%"><em class="ion-play mr-sm"></em>사업신청 정보</h7>
            <div class="form-group text-sm">
                <label class="col-sm-1 control-label">신청서</label>
                <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE10" title="첨부파일유형"/>
                <input type="hidden" class="notRequired"  name="appctnFileSeq" value="${rtnDto.fileSeq}" title="첨부파일유형"/>
                <div class="col-sm-10 col-md-11">
                    <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                    <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                    <div class="dropzone attachFile notRequired" data-file-field-nm="appctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
                        <div class="dz-default dz-message">
                            <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                        </div>
                    </div>
                    <p class="text-bold mt">
                        ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024 / 8}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
                    </p>
                </div>
            </div>
            </fieldset>
            </c:if>

            <c:if test="${not empty rtnDto.detailsKey}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}</p>
                        </div>
                    </div>
                </fieldset>
                <c:set var="modFlag" value="${not empty rtnDto.modDtm && (rtnDto.regDtm ne rtnDto.modDtm)}" />
                <fieldset class="last-child">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최종 수정자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${rtnDto.modName} (${rtnDto.modId})
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최종 수정일시</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>

        <%-- 아코디언 update Form --%>
        <c:if test="${ not empty rtnInfo}">
            <form class="form-horizontal" id="rsumeFrm" name="rsumeFrm" method="post" type="hidden" >
                <input type="hidden" class="notRequired" name="atchFileSeq" value="${rtnDto.atchFileSeq}" />
                <%--<input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnDto.rsumeOrd != null ? rtnDto.rsumeOrd + 1 : ''}" />--%>
                <input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnDto.rsumeOrd}" />
                <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" class="notRequired" name="detailsKey" value="${rtnDto.appctnSeq}" />
                <input type="hidden" class="notRequired" name="appctnSeq" value="${rtnDto.appctnSeq}" />
                <input type="hidden" class="notRequired" name="rsumeSeq" value="${rtnDto.rsumeSeq}" />
                <input type="hidden" class="notRequired" name="appctnSttsCd" value="" />
                <input type="hidden" class="notRequired" name="mngSttsCd" value="" />
                <%--<input type="hidden" class="notRequired"  name="fileSeq" value="${rtnDto.fileSeq}" title="첨부파일유형"/>--%>
                <input type="hidden" class="notRequired" id="fisrtFileSeq" name="fisrtFileSeq" value="${rtnDto.fisrtFileSeq}" />
                <input type="hidden" class="notRequired" id="lastFileSeq" name="lastFileSeq" value="${rtnDto.lastFileSeq}" />
                <input type="hidden" class="notRequired" id="lastFileSeq" name="lastFileSeq" value="${rtnDto.lastFileSeq}" />


            </form>
        </c:if>
        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/wb/WBFBPartUserModal.jsp">
            <jsp:param name="modalType" value="FutureCar" />
        </jsp:include>
    </div>
</div>

