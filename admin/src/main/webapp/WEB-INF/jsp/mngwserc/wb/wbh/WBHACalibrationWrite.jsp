
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbh/WBHACalibrationWriteCtrl controller/co/COFormCtrl">
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${rtnInfo.pageIndex}" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${rtnInfo.pageRowSize}" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${rtnInfo.listRowSize}" />
            <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${userInfo.memSeq}" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="${userInfo.bsnmNo}" />
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnData.appctnSeq}" />
            <input type="hidden" class="notRequired" name="appctnBsnmNo" value="${userInfo.bsnmNo}"/>
            <input type="hidden" class="notRequired" name="bsnCd" value="INQ07008"/>


            <h6 class="mt0"><strong>
                <c:if test="${not empty rtnData.appctnSeq }">
                    신청부품사 상세/수정
                </c:if>
                <c:if test="${empty rtnData.appctnSeq }">
                    관리자 등록
                </c:if>
            </strong></h6><hr>
            <div id="appctnPdfArea1">
                <br>
                <fieldset>
                    <div class="form-group text-sm">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <h6 class="mt0">신청자 정보</h6>
                        </div>
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-5">
                            <p class="control-static"><span class="star"> *</span> 표시는 필수 기재 항목입니다.</p>
                        </div>
                    </div>
                </fieldset>

            <div id="compnayDiv">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-6">
                                <c:choose>
                                    <c:when test="${not empty userInfo.name}">
                                        <input type="text" class="form-control" id="id" value="${userInfo.name}(${userInfo.id})" title="신청자" maxlength="50" disabled/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" id="id" value="" title="신청자" maxlength="50" disabled/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-sm-1">
                                <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <p class="form-control-static" id="email">
                                ${userInfo.email}
                            </p>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-5">
                                <select class="form-control input-sm" id="deptCd" name="deptCd" title="부서">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD020')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.deptCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>

                            </div>
                            <div class="col-sm-4" style="margin-left: -15px">
                                <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="${userInfo.deptDtlNm}" title="부서 상세" maxlength="50"/>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-5" style="margin-left: -15px">
                                <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4" style="margin-left: -15px">
                                <input type="text" class="form-control " id="pstnNm" name="pstnNm" value="${userInfo.pstnNm}" title="직급 상세" maxlength="50"/>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                        <div class="col-sm-5"style="margin-left: -15px">
                            <div class="col-sm-6">
                                <p class="form-control-static" id="hpNo">${userInfo.hpNo}</p>
                            </div>
                        </div>
                        <label class="col-sm-1 control-label">일반 전화번호</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control notRequired" id="telNo" name="telNo" value="${userInfo.telNo}" title="전화번호" maxlength="50"/>
                        </div>
                    </div>
                </fieldset>


                <br>
                <fieldset>
                    <div class="form-group text-sm">
                        <div class="col-sm-5" style="margin-left: -15px">
                            <h6 class="mt0">부품사 정보</h6>
                        </div>
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-5">
                            <p class="control-static"><span class="star"> *</span> 표시는 필수 기재 항목입니다.</p>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static" id="cmpnNm">${userInfo.cmpnNm}</p>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01001')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                        <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01002')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="규모">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'COMPANY020')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">대표자명<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <p class="form-control-static" id="rprsntNm">${userInfo.rprsntNm}</p>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <div class="input-group">
                                    <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                           value="${userInfo.stbsmDt}"
                                           title="설립일자" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                    <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">전화번호<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <input type="text" class="form-control input-sm" id="compTel" name="compTel" value="${userInfo.compTel}" title="전화번호" maxlength="50" placeholder="전화번호 입력"/>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                        <div class="col-sm-5">
                            <p class="form-control-static" id="bsnmNo" title="재직 회사사업자번호">${userInfo.bsnmNo}</p>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">주소<span class="star"> *</span></label>
                        <div class="col-sm-11">
                            <input type="button" class="btn btn-sm btn-primary" id="searchPostCode" value="우편번호 검색"><br>
                            <br>
                            <div style="display: flex; gap: 10px;">
                                <input type="text" class="form-control input-sm" id="zipCode" name="zipcode" value="${userInfo.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                                <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${userInfo.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                            </div>
                            <br>
                            <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${userInfo.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">매출액(연도)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${userInfo.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                            &nbsp;억원&nbsp;
                            <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                    <option value="${cdList.cd}"  <c:if test="${userInfo.slsYear eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                            &nbsp;년&nbsp;
                        </div>

                        <label class="col-sm-1 control-label">직원수</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${userInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">주생산품</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${userInfo.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${userInfo.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                            <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${userInfo.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
                        </div>
                    </div>
                </fieldset>

                <div id="fieldStart">
                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">품질5스타</label>
                            <div class="col-sm-5">
                                <select class="form-control input-sm notRequired" id="qlty5starCd" name="qlty5starCd" title="품질5스타등급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.qlty5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5starYear" title="품질5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.qlty5starYear eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select> 년
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">납입5스타</label>
                            <div class="col-sm-5">
                                <select class="form-control input-sm notRequired" id="pay5starCd" name="pay5starCd" title="납입5스타등급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.pay5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="pay5starYear" name="pay5starYear" title="납입5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.pay5starYear eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select> 년
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">기술5스타</label>
                            <div class="col-sm-5">
                                <select class="form-control input-sm notRequired" id="tchlg5starCd" name="tchlg5starCd" title="기술5스타등급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                        <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                            <option value="${cdList.cd}" <c:if test="${userInfo.tchlg5starCd eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select class="form-control input-sm notRequired" id="tchlg5starYear" name="tchlg5starYear" title="기술5스타연도">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                        <option value="${cdList.cd}" <c:if test="${userInfo.tchlg5starYear eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select> 년
                            </div>
                        </div>
                    </fieldset>
                </div>
                <fieldset id="fieldSQ">
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label">SQ 정보</label>
                        <div class="col-sm-10">
                            <c:forEach var="i" begin="1" end="3" varStatus="status">
                                <div class="col-sm-12" id="sqInfoArea${status.index-1}">
                                    <input type="hidden" class="notRequired" id="cbsnSeq" name="sqInfoList[${status.index-1}].cbsnSeq" value="${userInfo.sqInfoList[status.index-1].cbsnSeq}" />
                                    <input type="text" class="form-control input-sm notRequired" id="nm" name="sqInfoList[${status.index-1}].nm" value="${userInfo.sqInfoList[status.index-1].nm}" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                    <input type="text" class="form-control input-sm notRequired" id="score" name="sqInfoList[${status.index-1}].score" value="${userInfo.sqInfoList[status.index-1].score}" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                    <select class="form-control input-sm notRequired" id="year" name="sqInfoList[${status.index-1}].year" value="${userInfo.sqInfoList[status.index-1].year}" title="평가년도" style="width:auto;">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}"  <c:if test="${userInfo.sqInfoList[status.index-1].year eq cdList.cd}">selected</c:if>>
                                                    ${cdList.cdNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm" name="sqInfoList[${status.index-1}].crtfnCmpnNm" value="${userInfo.sqInfoList[status.index-1].crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </fieldset>
            </div>

            <c:if test="${not empty rtnInfo.appctnSeq}">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 변경이력</h6>
            <fieldset>
                <div class="col-sm-12 p0 mt">
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">이전 신청자</th>
                                <th class="text-center">변경 신청자</th>
                                <th class="text-center">변경일 / 변경자</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="trnfsListContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="trnfsPagingContainer"/>
                    </div>
                </div>
            </fieldset>
            </c:if>
            </div>

            <div id="appctnPdfArea2">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>컨설팅 내역</h6>
            <fieldset>
                <div class="col-sm-12 p0 mt">
                    <div class="table-responsive col-sm-12 p0 m0">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">사업연도</th>
                                <th class="text-center">사업구분</th>
                                <th class="text-center">진행상태</th>
                                <th class="text-center">부품사명</th>
                                <th class="text-center">구분</th>
                                <th class="text-center">규모</th>
                                <th class="text-center">사업자등록번호</th>
                                <th class="text-center">신청분야/업종</th>
                                <th class="text-center">신청 소재지</th>
                                <th class="text-center">담당위원</th>
                                <th class="text-center">신청일</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="conListContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="conPagingContainer"/>
                    </div>
                </div>
            </fieldset>

            <h6 class="mt0 nextHtml"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
            <c:if test="${not empty rtnData}">
                <c:forEach var="item" items="${rtnData.euipmentList}" varStatus="status">
                    <fieldset class="equipment">
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label equipmentNm">대상장비 ${status.index+1}<span class="star"> *</span></label>
                            <div class="col-sm-1">
                                <input type="text" class="form-control tchlgNm" name="euipmentList[${status.index}].tchlgNm" value=${item.tchlgNm} title="대상장비" maxlength="50"/>
                            </div>
                            <label class="col-sm-1 control-label">수량<span class="star"> *</span></label>
                            <div class="col-sm-1">
                                <input type="text" class="form-control numberChk tchlgCnt" name="euipmentList[${status.index}].tchlgCnt" value="${item.tchlgCnt}" title="대상장비 수량" maxlength="50"/>
                            </div>
                            <div class="col-sm-1" style="margin-left: 90px;" >
                                <button type="button" class="btn btn-info insertLow">+</button>
                                <c:if test="${status.index ne 0}">
                                    <button type="button" class="btn btn-info btn-danger deleteLow">-</button>
                                </c:if>
                            </div>
                        </div>
                    </fieldset>
                </c:forEach>
                <fieldset id="admMemo">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">관리자메모</label>
                        <div class="col-sm-11">
                            <textarea class="form-control input-sm notRequired" name="admMemo" rows="10" maxlength="2000" title="관리자메모" placeholder="관리자메모를 입력하세요.">${rtnData.admMemo}</textarea>
                        </div>
                    </div>
                </fieldset>

                <div class="pull-right">
                    <button type="button" class="btn btn-sm btn-default" id="appctnPdfDownload">신청정보 다운로드</button>
                </div>

            </c:if>
            </div>

            <div id="equipmentHtml">
                <fieldset class="equipment">
                    <div class="form-group text-sm form-inline">
                        <label class="col-sm-1 control-label equipmentNm">대상장비 1<span class="star"> *</span></label>
                        <div class="col-sm-1">
                            <input type="text" class="form-control tchlgNm" name="euipmentList[0].tchlgNm" title="대상장비" maxlength="50"/>
                        </div>
                        <label class="col-sm-1 control-label">수량<span class="star"> *</span></label>
                        <div class="col-sm-1">
                            <input type="text" class="form-control numberChk tchlgCnt" name="euipmentList[0].tchlgCnt" title="대상장비 수량" maxlength="50"/>
                        </div>
                        <div class="col-sm-1" style="margin-left: 90px;" >
                            <button type="button" class="btn btn-info insertLow">+</button>
                            <button type="button" class="btn btn-info btn-danger deleteLow">-</button>
                        </div>
                    </div>
                </fieldset>
            </div>
            <c:choose>
                <c:when test="${not empty rtnData}">
                    <div class="panel-group" id="accParent" role="tablist">
                        <div>
                            <h6 class="mt0"><em class="ion-play mr-sm"></em>사업진행 상세</h6>
                            <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em><strong>전체 진행상태 :  ${rtnData.rsumeSttsNm} (${rtnData.mngSttsNm})</strong></h7><hr/>
                            <input type="hidden" id="tabIndex" value="${rtnData.stageOrd}">
                        </div>
                        <div class="panel panel-default" id="tabIndex1">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx1" aria-constrols="addEx1">신청</a>
                            </div>
                            <div id="addEx1" class="panel-collapse collapse <c:if test="${rtnData.stageOrd eq 1}">in</c:if>" role="tabpanel">
                                <div class="panel-body">
                                        <input type="hidden" class="notRequired" name="rsumeSeq" value="${rtnData.applyList[0].rsumeSeq}" />
                                        <input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnData.applyList[0].rsumeOrd}" />
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[0].appctnSttsNm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[0].appctnSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <c:choose>
                                                    <c:when test="${rtnData.applyList[0].mngSttsCd eq 'PRO_TYPE07001_02_004' or rtnData.applyList[0] eq 'PRO_TYPE07001_02_005'
                                                        or rtnData.applyList[0].mngSttsCd eq 'PRO_TYPE07001_02_006'}">
                                                        ${rtnData.applyList[0].mngSttsNm}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm notRequired mngStatus" id="mngStatus1" name="mngSttsCd" value="${item.mngSttsCd}" title="관리자 상태값" style="width:auto;">
                                                            <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE07001_02_')}">
                                                                    <option value="${cdList.cd}"  <c:if test="${rtnData.applyList[0].mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${not empty rtnData.applyList[0].rtrnRsnCntn}">
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${rtnData.applyList[0].rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${item.applyDtl.rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px; display: none;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[0].mngSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <input type="hidden" name="fileCdList" value="ATTACH_FILE_TYPE01"/>
                                                <input type="hidden" class="notRequired" name="fileSeq${rtnData.applyList[0].fileInfoList[0].fileSeq}" value="${rtnData.applyList[0].fileInfoList[0].fileSeq}"/>
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                                <div class="dropzone attachFile" data-file-field-nm="fileSeq${rtnData.applyList[0].fileInfoList[0].fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
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
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">대상장비리스트<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <input type="hidden" name="fileCdList" value="ATTACH_FILE_TYPE11"/>
                                                <input type="hidden" class="notRequired" name="fileSeq${rtnData.applyList[0].fileInfoList[1].fileSeq}" value="${rtnData.applyList[0].fileInfoList[1].fileSeq}"/>
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                                <div class="dropzone attachFile" data-file-field-nm="fileSeq${rtnData.applyList[0].fileInfoList[1].fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="대상장비리스트">
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

                        <div class="panel panel-default" id="tabIndex2">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx2" aria-constrols="addEx2">심사</a>
                            </div>
                            <div id="addEx2" class="panel-collapse collapse <c:if test="${rtnData.stageOrd eq 2}">in</c:if>" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeSeq" value="${rtnData.applyList[1].rsumeSeq}" />
                                    <input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnData.applyList[1].rsumeOrd}" />
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">접수일</label>
                                            <div class="col-sm-6 form-inline">
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="accsDt" value="${rtnData.applyList[1].msEquipmentList[0].accsDt}" title="접수일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                                        <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">검교정비용</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control comma" name="clbtnExpnsPmt" value="${rtnData.applyList[1].msEquipmentList[0].clbtnExpnsPmt}" title="검교정비용" maxlength="50">
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[1].appctnSttsNm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[1].appctnSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">실지급일</label>
                                            <div class="col-sm-6 form-inline">
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="realGiveDt" value="${rtnData.applyList[1].msEquipmentList[0].realGiveDt}" title="실지급일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                                        <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">재단지원금</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control comma" name="fndnSpprtPmt" value="${rtnData.applyList[1].msEquipmentList[0].fndnSpprtPmt}" title="재단지원금" maxlength="50">
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">업체부담금</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control comma" name="entBrdnPmt" value="${rtnData.applyList[1].msEquipmentList[0].entBrdnPmt}" title="업체부담금" maxlength="50">
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <c:choose>
                                                    <c:when test="${rtnData.applyList[1].mngSttsCd eq 'PRO_TYPE07001_02_004' or rtnData.applyList[1] eq 'PRO_TYPE07001_02_005'
                                                        or rtnData.applyList[1].mngSttsCd eq 'PRO_TYPE07001_02_006'}">
                                                        ${rtnData.applyList[1].mngSttsNm}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm notRequired mngStatus" id="mngStatus2" name="mngSttsCd" value="${item.mngSttsCd}" title="관리자 상태값" style="width:auto;">
                                                            <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE07001_04_')}">
                                                                    <option value="${cdList.cd}"  <c:if test="${rtnData.applyList[1].mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${not empty rtnData.applyList[1].rtrnRsnCntn}">
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${rtnData.applyList[1].rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px; display: none;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[1].mngSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-default" id="tabIndex3">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx3" aria-constrols="addEx3">증빙</a>
                            </div>
                            <div id="addEx3" class="panel-collapse collapse <c:if test="${rtnData.stageOrd eq 3}">in</c:if>" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeSeq" value="${rtnData.applyList[2].rsumeSeq}" />
                                    <input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnData.applyList[2].rsumeOrd}" />
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[2].appctnSttsNm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[2].appctnSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <c:choose>
                                                    <c:when test="${rtnData.applyList[2].mngSttsCd eq 'PRO_TYPE07001_06_005' or rtnData.applyList[2].mngSttsCd eq 'PRO_TYPE07001_06_006'}">
                                                        ${rtnData.applyList[2].mngSttsNm}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <select class="form-control input-sm notRequired mngStatus" id="mngStatus3" name="mngSttsCd" value="${rtnData.applyList[2].mngSttsCd}" title="관리자 상태값" style="width:auto;">
                                                            <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}">
                                                                <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE07001_06_')}">
                                                                    <option value="${cdList.cd}"  <c:if test="${rtnData.applyList[2].mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                            ${cdList.cdNm}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${not empty rtnData.applyList[2].rtrnRsnCntn}">
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="${rtnData.applyList[2].rtrnRsnCntn}" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text" class="form-control notRequired" id="rtrnRsnCntn" name="rtrnRsnCntn" value="" title="사유입력" placeholder="사유입력" maxlength="50" style="width: 800px; display: none;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                    ${rtnData.applyList[2].mngSttsChngDtm}
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">영수증</label>
                                            <div class="col-sm-10 col-md-11">
                                                <input type="hidden" name="fileCdList" value="ATTACH_FILE_TYPE14"/>
                                                <input type="hidden" class="notRequired" name="fileSeq${rtnData.applyList[2].fileInfoList[0].fileSeq}" value="${rtnData.applyList[2].fileInfoList[0].fileSeq}"/>
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq${rtnData.applyList[2].fileInfoList[0].fileSeq}" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="영수증">
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
                </c:when>
                <c:otherwise>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">사업신청서<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <input type="hidden" name="fileCdList" value="ATTACH_FILE_TYPE01"/>
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업신청서">
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
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">대상장비리스트<span class="star"> *</span></label>
                            <div class="col-sm-10 col-md-11">
                                <input type="hidden" name="fileCdList" value="ATTACH_FILE_TYPE11"/>
                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                <spring:eval var="atchUploadMaxSize" expression="838860800" />
                                <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="대상장비리스트">
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
                </c:otherwise>
            </c:choose>

            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
        </form>

        <form class="form-horizontal" id="frm" name="frm" method="post">
            <input type="hidden" class="notRequired" name="memSeq" value="${userInfo.memSeq}" />
            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}" />
            <input type="hidden" class="notRequired" name="nextStageNm" value="" />
            <input type="hidden" class="notRequired" name="userLogYn" />
            <input type="hidden" class="notRequired" name="wbbTransDTO.bfreMemSeq" />
            <input type="hidden" class="notRequired" name="wbbTransDTO.aftrMemSeq" />
            <input type="hidden" class="notRequired" name="bsnmNo" value="${userInfo.bsnmNo}">
            <%--<input type="hidden" class="notRequired" name="maxStage" value="${rtnInfo.maxStage}" />--%>
        </form >
        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>

    </div>
</div>

