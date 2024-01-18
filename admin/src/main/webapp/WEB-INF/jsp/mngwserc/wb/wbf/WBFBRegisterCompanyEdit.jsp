<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="date" value="<%=new java.util.Date( )%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbf/WBFBRegisterCompanyEditCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청부품사 상세/수정</h6><hr>

        <%-- target Set - 신청자 정보 관련 DTO --%>
        <c:set var="registerDtl" value="${rtnBasicData.registerDtl}"/>
        <c:set var="spprtDtl" value="${rtnRegisterData.spprtDtl}"/>
        <c:set var="rsumeTaskDtl" value="${rtnRegisterData.rsumeTaskDtl}"/>
        <c:set var="rsumeLeng" value="${fn:length(rtnRegisterData.rsumeTaskDtl)-1}"/>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <div id="basicData">
                <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" class="notRequired" name="beforeMemSeq" value="${registerDtl.memSeq}" />
                <input type="hidden" class="notRequired" name="beforeId" value="${registerDtl.id}" />
                <%-- 현재 신청자 id --%>
                <input type="hidden" class="notRequired" name="memSeq" value="${registerDtl.memSeq}" />
                <input type="hidden" class="notRequired" name="id" value="${registerDtl.id}" />
                <%-- 변형 전 사업자번호 --%>
                <input type="hidden" class="notRequired" id="bsnmNo" name="bsnmNo" value="${registerDtl.bsnmNo}" />
                <input type="hidden" class="notRequired" name="appctnSeq" value="${rtnBasicData.appctnSeq}" />

                <h6 class="mt0"><em class="ion-play mr-sm"></em>회차 정보</h6>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                    <option value="">연도 전체</option>
                                    <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                        <option value="${optYear}" <c:if test="${registerDtl.year eq optYear}">selected</c:if> >
                                                ${optYear}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="optEpisd" name="episd" title="회차">
                                <option value="">회차 전체</option>
                                <c:forEach var="optEpisd" items="${rtnBasicData.episdList}" varStatus="status" >
                                    <option value="${optEpisd}" <c:if test="${registerDtl.episd eq optEpisd}">selected</c:if> >
                                            ${optEpisd}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </fieldset>

                <div id="appctnPdfArea1">
                    <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="nameAndId" value="${registerDtl.name}(${registerDtl.id})" title="신청자" maxlength="50" disabled/>
                                </div>
                                <div class="col-sm-1">
                                    <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                            <div class="col-sm-2">
                                <p class="form-control-static" id="email">${registerDtl.email}</p>
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
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.deptCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">
                                    <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="${registerDtl.deptDtlNm}" title="부서 상세" maxlength="50"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-5" style="margin-left: -15px">
                                    <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.pstnCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">

                                    <input type="text" class="form-control notRequired" style="display:none;"  id="pstnNm" name="pstnNm" value="${registerDtl.pstnNm}" title="직급 상세" maxlength="50"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                            <div class="col-sm-5"style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="hpNo">${registerDtl.hpNo}</p>
                                </div>
                            </div>
                            <label class="col-sm-1 control-label">일반 전화번호</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control notRequired telNumber" id="telNo" name="telNo" value="${registerDtl.telNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" title="일반 전화번호" placeholder="전화번호 입력"/>
                            </div>
                        </div>
                    </fieldset>


                    <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static" id="cmpnNm">${registerDtl.cmpnNm}</p>
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
                                            <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
                                                <c:if test="${cdList.cd eq 'COMPANY01001' or cdList.cd eq 'COMPANY01002'}" >
                                                    <option value="${cdList.cd}" <c:if test="${registerDtl.ctgryCd eq cdList.cd}">selected</c:if> >
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
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
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.sizeCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
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
                                    <p class="form-control-static" id="rprsntNm">${registerDtl.rprsntNm}</p>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                               value="${registerDtl.stbsmDt}"
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
                            <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <input type="text" class="form-control input-sm telNumber" id="compTel" name="compTel" value="${registerDtl.compTel}" title="회사 전화번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" placeholder="전화번호 입력"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static" id="changeBsnmNo" title="재직 회사사업자번호">
                                    ${fn:substring(registerDtl.bsnmNo, 0, 3)}
                                    -
                                    ${fn:substring(registerDtl.bsnmNo, 3, 5)}
                                    -
                                    ${fn:substring(registerDtl.bsnmNo, 5, -1)}
                                </p>
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
                                    <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${registerDtl.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                                    <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${registerDtl.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                                </div>
                                <br>
                                <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${registerDtl.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">매출액(연도)</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${registerDtl.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                                &nbsp;억원&nbsp;
                                <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${registerDtl.slsYear eq cdList.cd}">selected</c:if>>
                                                ${cdList.cdNm}
                                        </option>
                                    </c:forEach>
                                </select>
                                &nbsp;년&nbsp;
                            </div>

                            <label class="col-sm-1 control-label">직원수</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${registerDtl.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">주생산품</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${registerDtl.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${registerDtl.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${registerDtl.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
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
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.qlty5starCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5starYear" title="품질5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${registerDtl.qlty5starYear eq cdList.cd}">selected</c:if> >
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
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.pay5starCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="pay5starYear" name="pay5starYear" title="납입5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${registerDtl.pay5starYear eq cdList.cd}">selected</c:if> >
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
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.tchlg5starCd eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="tchlg5starYear" name="tchlg5starYear" title="기술5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${registerDtl.tchlg5starYear eq cdList.cd}">selected</c:if> >
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
                                    <div class="col-sm-12 sqInfoArea" id="sqInfoArea${status.index-1}">
                                        <input type="hidden" class="notRequired" id="cbsnSeq" name="sqInfoList[${status.index-1}].cbsnSeq" value="${rtnBasicData.sqInfoList[status.index-1].cbsnSeq}" />
                                        <input type="text" class="form-control input-sm notRequired" id="nm" name="sqInfoList[${status.index-1}].nm" value="${rtnBasicData.sqInfoList[status.index-1].nm}" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score" name="sqInfoList[${status.index-1}].score" value="${rtnBasicData.sqInfoList[status.index-1].score}" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                        <select class="form-control input-sm notRequired" id="year" name="sqInfoList[${status.index-1}].year" title="평가년도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${rtnBasicData.sqInfoList[status.index-1].year eq cdList.cd}">selected</c:if> >
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm" name="sqInfoList[${status.index-1}].crtfnCmpnNm" value="${rtnBasicData.sqInfoList[status.index-1].crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>
                </div>

                <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 변경 이력</h6>
                <fieldset id="trnsfField">
                    <div class="clearfix">
                        <!-- 현재 페이징 번호 -->
                        <input type="hidden" id="pageIndex" name="pageIndex" value="${ registerDtl.pageIndex }" />
                        <!-- 페이징 버튼 사이즈 -->
                        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ registerDtl.pageRowSize }" />
                        <input type="hidden" id="listRowSize" name="listRowSize" value="${ registerDtl.listRowSize }" />

                        <div class="pull-right ml-sm" data-html2canvas-ignore="true">
                            <select class="form-control input-sm trsfListRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnDto.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
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
                            <tbody id="trsfListContainer"/>

                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="trsfPagingContainer"/>
                    </div>
                </fieldset>

                <div id="appctnPdfArea2">
                    <h6 class="mt0"><em class="ion-play mr-sm"></em>담당위원 정보</h6>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">담당위원명</label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <p class="form-control-static" >
                                        <c:if test="${empty registerDtl.picCmssrNm}">-</c:if>
                                        <c:if test="${not empty registerDtl.picCmssrNm}">${registerDtl.picCmssrNm}</c:if>
                                    </p>
                                </div>
                            </div>
                            <label class="col-sm-1 control-label">이메일</label>
                            <div class="col-sm-2">
                                <p class="form-control-static" >
                                    <c:if test="${empty registerDtl.picCmssrEmail}">-</c:if>
                                    <c:if test="${not empty registerDtl.picCmssrEmail}">${registerDtl.picCmssrEmail}</c:if>
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">휴대폰번호</label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <p class="form-control-static" >
                                        <c:if test="${empty registerDtl.picCmssrHpNo}">-</c:if>
                                        <c:if test="${not empty registerDtl.picCmssrHpNo}">${registerDtl.picCmssrHpNo}</c:if>
                                    </p>
                                </div>
                            </div>
                            <label class="col-sm-1 control-label">업체명</label>
                            <div class="col-sm-2">
                                <p class="form-control-static">
                                    <c:if test="${empty registerDtl.picCmssrCmpnNm}">-</c:if>
                                    <c:if test="${not empty registerDtl.picCmssrCmpnNm}">${registerDtl.picCmssrCmpnNm}</c:if>
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <br>

                    <h6 class="mt0"><em class="ion-play mr-sm"></em>사업신청 정보</h6>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">종된사업장번호</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control input-sm notRequired" id="sbrdnBsnmNo" name="sbrdnBsnmNo" value="${registerDtl.sbrdnBsnmNo}" title="종된사업장번호" maxlength="4" placeholder="종된사업장번호">
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과제명<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm" id="optAsigt" name="taskCd" title="과제명">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${rtnBasicData.roundMstDTO.asigtList}" varStatus="status">
                                            <option value="${cdList.bsnOptnSeq}" <c:if test="${registerDtl.taskCd eq cdList.bsnOptnSeq}" >selected</c:if>>
                                                    ${cdList.optnNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">사업 유형<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm " id="optBsin" name="bsnTypeCd" title="사업 유형">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${rtnBasicData.roundMstDTO.bsinList}" varStatus="status">
                                            <option value="${cdList.bsnOptnSeq}" <c:if test="${registerDtl.bsnTypeCd eq cdList.bsnOptnSeq}" >selected</c:if>>
                                                    ${cdList.optnNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">스마트화 현재 수준<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm" name="smtfnPrsntCd" title="스마트화 현재 수준">
                                        <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.smtfnPrsntCd eq cdList.cd}" >selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">스마트화 목표 수준<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm " name="smtfnTrgtCd">
                                        <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                <option value="${cdList.cd}" <c:if test="${registerDtl.smtfnTrgtCd eq cdList.cd}" >selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <br>

                    <h6 class="mt0"><em class="ion-play mr-sm"></em>지급관리</h6>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">선급금 해당 여부<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-3">
                                    <input type="radio" id="applicable" class="pmndvPmtYn" name="pmndvPmtYn" value="Y" <c:if test="${registerDtl.pmndvPmtYn eq 'Y'}" >checked</c:if>/>
                                    <label for="applicable">해당</label>
                                </div>
                                <div class="col-sm-3">
                                    <input type="radio" id="notApplicable" class="pmndvPmtYn" name="pmndvPmtYn" value="N" <c:if test="${registerDtl.pmndvPmtYn eq 'N' or empty registerDtl.pmndvPmtYn }" >checked</c:if>/>
                                    <label for="notApplicable">미해당</label>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div id="frmDataSpprt">
                <div class="container-fluid">
                    <div class="panel-group" id="accRoot" role="tablist">
                        <%-- 영역 1 --%>
                        <div class="panel panel-default" id="pmndvPmtView" data-sttsCd="PRO_TYPE03001">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accRoot" href="#addLow1" aria-constrols="addLow1">선급금지급</a>
                            </div>
                            <div id="addLow1" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="spprtDtlList[0].appctnSpprtSeq" value="${spprtDtl[0].appctnSpprtSeq}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">접수일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[0].accsDt" value="${empty spprtDtl[0].accsDt ? today : spprtDtl[0].accsDt}" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">정부지원금(①)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="spprtDtlList[0].gvmntSpprtPmt" value="<fmt:formatNumber value='${spprtDtl[0].gvmntSpprtPmt}' pattern='#,###'/>" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">대기업출연금액(②)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="spprtDtlList[0].mjcmnAprncPmt" value="<fmt:formatNumber value='${spprtDtl[0].mjcmnAprncPmt}' pattern='#,###'/>" title="대기업출연금액" maxlength="50" placeholder="대기업출연금액">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">총금액(①+②)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma notRequired ttlPmt" name="spprtDtlList[0].ttlPmt" value="<fmt:formatNumber value='${spprtDtl[0].ttlPmt}' pattern='#,###'/>" title="총금액" maxlength="50" placeholder="총금액" readonly style="background: #eee">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">은행명</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="spprtDtlList[0].bankNm" value="${spprtDtl[0].bankNm}" title="은행명" maxlength="50" placeholder="은행명">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">계좌번호</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control numberChk input-sm notRequired" name="spprtDtlList[0].acntNo" value="${spprtDtl[0].acntNo}" title="계좌번호" maxlength="50" placeholder="계좌번호">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">예금주</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="spprtDtlList[0].dpsitNm" value="${spprtDtl[0].dpsitNm}" title="예금주" maxlength="50" placeholder="예금주">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="spprtDtlList[0].appctnSttsCd" value="${spprtDtl[0].appctnSttsCd}">
                                                <p class="form-control-static">${spprtDtl[0].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${spprtDtl[0].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[0].giveDt" value="${empty spprtDtl[0].giveDt ? today : spprtDtl[0].giveDt}" title="지급일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급차수</label>
                                            <div class="col-sm-6 form-inline" >
                                                <select class="form-control input-sm notRequired" name="spprtDtlList[0].giveSeq" title="지급차수">
                                                    <option value="">지급차수</option>
                                                    <c:forEach var="orderList" items="${rtnBasicData.orderList}" varStatus="status">
                                                        <option value="${orderList.giveSeq}" <c:if test="${spprtDtl[0].giveSeq eq orderList.giveSeq}" >selected</c:if> >
                                                                ${orderList.giveOrd} 차수
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
                                                <select class="form-control input-sm notRequired mngSttsCd" name="spprtDtlList[0].mngSttsCd" title="관리자 상태값/결과">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE03001_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${spprtDtl[0].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${spprtDtl[0].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile1" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                    <input type="hidden" class="notRequired fileInfo" data-hidden='fileInfo' name="spprtAppctnFile1" value="${spprtDtl[0].spprtAppctnFileSeq}">
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
                                            <label class="col-sm-1 control-label">계좌이체약정서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile2" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="계좌이체약정서">
                                                    <input type="hidden" class="notRequired fileInfo" data-hidden='fileInfo' name="spprtAppctnFile2" value="${spprtDtl[0].acntFileSeq}">
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
                                            <label class="col-sm-1 control-label">통장사본<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile3" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="통장사본">
                                                    <input type="hidden" class="notRequired fileInfo" data-hidden='fileInfo' name="spprtAppctnFile3" value="${spprtDtl[0].bnkbkFileSeq}">
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

                        <%-- 영역 2 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE03002">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accRoot" data-target="#addLow2" aria-constrols="addLow2">지원금지급</a>
                            </div>
                            <div id="addLow2" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="spprtDtlList[1].appctnSpprtSeq" value="${spprtDtl[1].appctnSpprtSeq}">
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">접수일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[1].accsDt" value="${empty spprtDtl[1].accsDt ? today : spprtDtl[1].accsDt}" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">정부지원금(①)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="spprtDtlList[1].gvmntSpprtPmt" value="<fmt:formatNumber value='${spprtDtl[1].gvmntSpprtPmt}' pattern='#,###'/>" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">대기업출연금액(②)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="spprtDtlList[1].mjcmnAprncPmt" value="<fmt:formatNumber value='${spprtDtl[1].mjcmnAprncPmt}' pattern='#,###'/>" title="정부지원금" maxlength="50" placeholder="대기업출연금액">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">총금액(①+②)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma notRequired ttlPmt" name="spprtDtlList[1].ttlPmt" value="<fmt:formatNumber value='${spprtDtl[1].ttlPmt}' pattern='#,###'/>" title="총금액" maxlength="50" placeholder="총금액" readonly style="background: #eee">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">은행명</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="spprtDtlList[1].bankNm" value="${spprtDtl[1].bankNm}" title="은행명" maxlength="50" placeholder="은행명">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">계좌번호</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm numberChk notRequired" name="spprtDtlList[1].acntNo" value="${spprtDtl[1].acntNo}" title="계좌번호" maxlength="50" placeholder="계좌번호">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">예금주</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="spprtDtlList[1].dpsitNm" value="${spprtDtl[1].dpsitNm}" title="예금주" maxlength="50" placeholder="예금주">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="spprtDtlList[1].appctnSttsCd" value="${spprtDtl[1].appctnSttsCd}">
                                                <p class="form-control-static">${spprtDtl[1].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">사용자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${spprtDtl[1].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[1].giveDt" value="${empty spprtDtl[1].giveDt ? today : spprtDtl[1].giveDt}" title="지급일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급차수</label>
                                            <div class="col-sm-6 form-inline" >
                                                <select class="form-control input-sm notRequired" id="giveSeq" name="spprtDtlList[1].giveSeq" title="지급차수">
                                                    <option value="">지급차수</option>
                                                    <c:forEach var="orderList" items="${rtnBasicData.orderList}" varStatus="status">
                                                        <option value="${orderList.giveSeq}" <c:if test="${spprtDtl[1].giveSeq eq orderList.giveSeq}" >selected</c:if> >
                                                                ${orderList.giveOrd} 차수
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
                                                <select class="form-control input-sm notRequired mngSttsCd" name="spprtDtlList[1].mngSttsCd" title="관리자 상태값/결과">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE03002_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${spprtDtl[1].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${spprtDtl[1].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile4" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                    <input type="hidden" class="notRequired fileInfo" data-hidden='fileInfo' name="spprtAppctnFile4" value="${spprtDtl[1].spprtAppctnFileSeq}">
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
                                            <label class="col-sm-1 control-label">계좌이체약정서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile5" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="계좌이체약정서">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' name="spprtAppctnFile5" value="${spprtDtl[1].acntFileSeq}">
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
                                            <label class="col-sm-1 control-label">통장사본<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile6" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="통장사본">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' name="spprtAppctnFile6" value="${spprtDtl[1].bnkbkFileSeq}">
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

                        <%-- 영역 3 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE03003">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accRoot" data-target="#addLow3" aria-constrols="addLow3">기술임치</a>
                            </div>
                            <div id="addLow3" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="spprtDtlList[2].appctnSpprtSeq" value="${spprtDtl[2].appctnSpprtSeq}">
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">접수일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[2].accsDt" value="${empty spprtDtl[2].accsDt ? today : spprtDtl[2].accsDt}" title="접수일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">수수료</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma notRequired" name="spprtDtlList[2].cmssnPmt" value="<fmt:formatNumber value='${spprtDtl[2].cmssnPmt}' pattern='#,###'/>" title="수수료" maxlength="50" placeholder="수수료">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="spprtDtlList[2].appctnSttsCd" value="${spprtDtl[2].appctnSttsCd}">
                                                <p class="form-control-static">${spprtDtl[2].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${spprtDtl[2].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="spprtDtlList[2].giveDt" value="${empty spprtDtl[2].giveDt ? today : spprtDtl[2].giveDt}" title="지급일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">지급차수</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired" name="spprtDtlList[2].giveSeq" title="지급차수">
                                                    <option value="">지급차수</option>

                                                    <c:forEach var="orderList" items="${rtnBasicData.orderList}" varStatus="status">
                                                        <option value="${orderList.giveSeq}" <c:if test="${spprtDtl[2].giveSeq eq orderList.giveSeq}" >selected</c:if> >
                                                                ${orderList.giveOrd} 차수
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
                                                <select class="form-control input-sm notRequired mngSttsCd" name="spprtDtlList[2].mngSttsCd" title="관리자 상태값/결과">

                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE03003_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${spprtDtl[2].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${spprtDtl[2].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">지원금신청서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile7" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="지원금신청서">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' name="spprtAppctnFile7" value="${spprtDtl[2].spprtAppctnFileSeq}">
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
                                            <label class="col-sm-1 control-label">기술임치증<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile8" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="기술임치증">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' name="spprtAppctnFile8" value="${spprtDtl[2].tchlgLseFileSeq}">
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
                                            <label class="col-sm-1 control-label">임치료 납입 영수증<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="spprtAppctnFile9" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="임치료 납입 영수증">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' name="spprtAppctnFile9" value="${spprtDtl[2].lsePayFileSeq}">
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
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">관리자메모</label>
                            <div class="col-sm-11">
                                <textarea class="form-control input-sm notRequired" name="admMemo" rows="10" maxlength="2000" title="관리자메모" placeholder="관리자메모를 입력하세요.">${registerDtl.admMemo}</textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="pull-right">
                        <button type="button" class="btn btn-sm btn-default" id="appctnPdfDownload">신청정보 다운로드</button>
                    </div>
                </div>
            </div>

            <div id="frmDataRsumeTask">
                <div class="container-fluid">
                    <div class="panel-group" id="accParent" role="tablist">
                        <div>
                            <div class="text-left mb-xl"><h5>부품사 관리 등록</h5></div>
                            <hr>
                            <h6 class="mt0"><em class="ion-play mr-sm"></em>전체 진행상태 : ${rsumeTaskDtl[rsumeLeng].rsumeSttsCdNm} (${rsumeTaskDtl[rsumeLeng].mngSttsCdNm})</h6>
                        </div>

                        <!-- 영역 1 -->
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02001">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx1" aria-constrols="addEx1">신청</a>
                            </div>
                            <div id="addEx1" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[0].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[0].rsumeOrd}">
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[0].appctnSttsCd}">
                                                <p class="form-control-static">${rsumeTaskDtl[0].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[0].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt1">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">출연기업</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired" name="rsumeTaskDtl.aprncCmpnCd" title="출연기업">
                                                    <option value="">선택</option>
                                                    <c:forEach var="cdList" items="${cdDtlList.APRNC_CMPN_CD}" varStatus="status">
                                                        <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[0].aprncCmpnCd eq cdList.cd}">selected</c:if> >
                                                                ${cdList.cdNm}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" id="mngCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02001_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[0].mngSttsCd eq cdList.cd}">selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                                <input type="text" class="form-control input-sm notRequired rtrnRsnCntn" name="rsumeTaskDtl.rtrnRsnCntn" value="${rsumeTaskDtl[0].rtrnRsnCntn}" title="반려사유내용" maxlength="50" placeholder="반려사유내용">
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[0].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <%-- 파일 검색 --%>
                                    <c:set var="rsumeFileList" value="${rsumeTaskDtl[0].appctnFileInfo}" />
                                    <c:set var="rsumeTaskFile1" value=""/>
                                    <c:set var="rsumeTaskFile2" value=""/>
                                    <c:set var="rsumeTaskFile3" value=""/>
                                    <c:set var="rsumeTaskFile4" value=""/>
                                    <c:forEach var="list" items="${rsumeFileList}">
                                        <c:if test="${list.fileCd eq 'ATTACH_FILE_TYPE01'}" ><c:set var="rsumeTaskFile1" value="${list.fileSeq}"/></c:if>
                                        <c:if test="${list.fileCd eq 'ATTACH_FILE_TYPE02'}" ><c:set var="rsumeTaskFile2" value="${list.fileSeq}"/></c:if>
                                        <c:if test="${list.fileCd eq 'ATTACH_FILE_TYPE03'}" ><c:set var="rsumeTaskFile3" value="${list.fileSeq}"/></c:if>
                                        <c:if test="${list.fileCd eq 'ATTACH_FILE_TYPE04'}" ><c:set var="rsumeTaskFile4" value="${list.fileSeq}"/></c:if>
                                    </c:forEach>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">신청서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile1" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="신청서">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[0].type" value="ATTACH_FILE_TYPE01">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[0].seqNm" value="rsumeTaskFile1">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' data-fileSeq="1" data-fileType='ATTACH_FILE_TYPE01' name="rsumeTaskFile1" value="${rsumeTaskFile1}">
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
                                            <label class="col-sm-1 control-label">보안서약서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile2" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="보안서약서">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[1].type" value="ATTACH_FILE_TYPE02">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[1].seqNm" value="rsumeTaskFile2">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' data-fileSeq="2" data-fileType='ATTACH_FILE_TYPE02' name="rsumeTaskFile2" value="${rsumeTaskFile2}">
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
                                            <label class="col-sm-1 control-label">중소기업확인서<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile3" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="중소기업확인서">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[2].type" value="ATTACH_FILE_TYPE03">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[2].seqNm" value="rsumeTaskFile3">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' data-fileSeq="3" data-fileType='ATTACH_FILE_TYPE03' name="rsumeTaskFile3" value="${rsumeTaskFile3}">
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
                                            <label class="col-sm-1 control-label">사업자등록증<span class="star"> *</span></label>
                                            <div class="col-sm-10 col-md-11">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="rsumeTaskFile4" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="사업자등록증">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[3].type" value="ATTACH_FILE_TYPE04">
                                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnFileInfo[3].seqNm" value="rsumeTaskFile4">
                                                    <input type="hidden" class="notRequired" data-hidden='fileInfo' data-fileSeq="4" data-fileType='ATTACH_FILE_TYPE04' name="rsumeTaskFile4" value="${rsumeTaskFile4}">
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

                        <%-- 영역 2 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02002">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx2" aria-constrols="addEx2">사업계획</a>
                            </div>
                            <div id="addEx2" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[1].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[1].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">공급기업명</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control input-sm notRequired" id="offerCmpnNm" name="rsumeTaskDtl.offerCmpnNm" value="${rsumeTaskDtl[1].offerCmpnNm}" title="공급기업명" maxlength="50" placeholder="공급기업명" readonly>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">공급기업 사업자등록번호</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" id="offerBsnmNo" name="rsumeTaskDtl.offerBsnmNo" value="${rsumeTaskDtl[1].offerBsnmNo}" title="공급기업 사업자등록번호" maxlength="50" placeholder="공급기업 사업자등록번호">
                                                </div>
                                                <div class="col-sm-1">
                                                    <button type="button" id="bsnmNoAuth" class="btn btn-sm btn-info">인증</button>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">공급기업 담당자명</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="rsumeTaskDtl.offerPicNm" value="${rsumeTaskDtl[1].offerPicNm}" title="공급기업 담당자명" maxlength="50" placeholder="공급기업 담당자명">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">공급기업 담당자 휴대폰</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm mobileChk notRequired" name="rsumeTaskDtl.offerPicHpNo" value="${rsumeTaskDtl[1].offerPicHpNo}" title="공급기업 담당자 휴대폰" maxlength="50" placeholder="공급기업 담당자 휴대폰">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">공급기업 담당자 이메일</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm emailChk notRequired" name="rsumeTaskDtl.offerPicEmail" value="${rsumeTaskDtl[1].offerPicEmail}" title="공급기업 담당자 이메일" maxlength="50" placeholder="공급기업 담당자 이메일">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">총 사업비</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma notRequired" name="rsumeTaskDtl.ttlBsnPmt" value="${rsumeTaskDtl[1].ttlBsnPmt}" title="총 사업비" maxlength="50" placeholder="총 사업비">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[1].appctnSttsCd}">
                                                <p class="form-control-static" >${rsumeTaskDtl[1].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[1].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">과제번호</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control input-sm notRequired" name="rsumeTaskDtl.taskNo" value="${rsumeTaskDtl[1].taskNo}" title="과제번호" maxlength="50" placeholder="과제번호">
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02002_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[1].mngSttsCd eq cdList.cdNm}">selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[1].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                </div>
                            </div>
                        </div>

                        <%-- 영역 3 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02003">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx3" aria-constrols="accEx3">최초점검</a>
                            </div>
                            <div id="accEx3" class="panel-collapse collapse " role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[2].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[2].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[2].appctnSttsCd}">
                                                <p class="form-control-static">${rsumeTaskDtl[2].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[2].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검위원</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="hidden" class="notRequired chkCmssrSeq" name="rsumeTaskDtl.chkCmssrSeq" value="${rsumeTaskDtl[2].chkCmssrSeq}">
                                                    <input type="text" class="form-control input-sm notRequired" value="${rsumeTaskDtl[2].chkCmssrNm}" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                                </div>
                                                <div class="col-sm-1">
                                                    <button type="button" class="btn btn-sm btn-info btnCmtSearch">위원검색</button>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검계획일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkPlanDt" value="${empty rsumeTaskDtl[2].chkPlanDt ? today : rsumeTaskDtl[2].chkPlanDt}" title="점검계획일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검실시일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkImplmnDt" value="${empty rsumeTaskDtl[2].chkImplmnDt ? today : rsumeTaskDtl[2].chkImplmnDt}" title="점검실시일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">평점</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="text" class="form-control numberChk input-sm notRequired" name="rsumeTaskDtl.examScore" value="${rsumeTaskDtl[2].examScore}" title="평점" maxlength="50" placeholder="평점">
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02003_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[2].mngSttsCd eq cdList.cd}">selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[2].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <%-- 영역 4 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02004">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx4" aria-constrols="accEx4">중간점검</a>
                            </div>
                            <div id="accEx4" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[3].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[3].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[3].appctnSttsCd}">
                                                <p class="form-control-static" >${rsumeTaskDtl[3].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[3].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검위원</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="hidden" class="notRequired chkCmssrSeq" name="rsumeTaskDtl.chkCmssrSeq" value="${rsumeTaskDtl[3].chkCmssrSeq}" />
                                                    <input type="text" class="form-control input-sm notRequired" value="${rsumeTaskDtl[3].chkCmssrNm}" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                                </div>
                                                <div class="col-sm-1">
                                                    <button type="button" class="btn btn-sm btn-info btnCmtSearch">위원검색</button>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검계획일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkPlanDt" value="${empty rsumeTaskDtl[3].chkPlanDt ? today : rsumeTaskDtl[3].chkPlanDt}" title="점검계획일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검실시일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkImplmnDt" value="${empty rsumeTaskDtl[3].chkImplmnDt ? today : rsumeTaskDtl[3].chkImplmnDt}" title="점검실시일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                    <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                        <em class="ion-calendar"></em>
                                                    </button>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02004_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[3].mngSttsCd eq cdList.cdNm}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[3].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <%-- 영역 5 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02005">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx5" aria-constrols="accEx5">원가계산</a>
                            </div>
                            <div id="accEx5" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[4].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[4].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[4].appctnSttsCd}">
                                                <p class="form-control-static">${rsumeTaskDtl[4].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[4].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">원가계산기관</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm notRequired" name="rsumeTaskDtl.prmcstClcltnInstt" value="${rsumeTaskDtl[4].prmcstClcltnInstt}" title="원가계산기관" maxlength="50" placeholder="원가계산기관">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">의뢰일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.rqstDt" value="${empty rsumeTaskDtl[4].rqstDt ? today : rsumeTaskDtl[4].rqstDt}" title="의뢰일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">회신일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.rplyDt" value="${empty rsumeTaskDtl[4].rplyDt ? today : rsumeTaskDtl[4].rplyDt}" title="회신일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">정부지원금(①)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="rsumeTaskDtl.gvmntSpprtPmt" value="<fmt:formatNumber value='${rsumeTaskDtl[4].gvmntSpprtPmt}' pattern='#,###'/>" title="정부지원금" maxlength="50" placeholder="정부지원금">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">대기업출연금(②)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="rsumeTaskDtl.mjcmnAprncPmt" value="<fmt:formatNumber value='${rsumeTaskDtl[4].mjcmnAprncPmt}' pattern='#,###'/>" title="대기업출연금" maxlength="50" placeholder="대기업출연금">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">도입기업부담금(③)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma priceVal notRequired" name="rsumeTaskDtl.ntdcnCmpnBrdnPmt" value="<fmt:formatNumber value='${rsumeTaskDtl[4].ntdcnCmpnBrdnPmt}' pattern='#,###'/>" title="도입기업부담금" maxlength="50" placeholder="도입기업부담금">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">총금액(①+②+③)</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="text" class="form-control input-sm comma notRequired ttlPmt" name="rsumeTaskDtl.ttlPmt" value="<fmt:formatNumber value='${rsumeTaskDtl[4].ttlPmt}' pattern='#,###'/>" title="총금액" maxlength="50" placeholder="총금액" readonly style="background: #eee">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02005_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[4].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[4].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                </div>
                            </div>
                        </div>

                        <%-- 영역 6 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02006">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx6" aria-constrols="accEx6">협약</a>
                            </div>
                            <div id="accEx6" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[5].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[5].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[5].appctnSttsCd}">
                                                <p class="form-control-static">${rsumeTaskDtl[5].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[5].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">협약일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.agrmtDt" value="${empty rsumeTaskDtl[5].agrmtDt ? today : rsumeTaskDtl[5].agrmtDt}"  title="협약일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">기간</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.agrmtTermDt" value="${empty rsumeTaskDtl[5].agrmtTermDt ? today : rsumeTaskDtl[5].agrmtTermDt}" title="기간" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02006_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[5].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[5].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <%-- 영역 7 --%>
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE02007">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx7" aria-constrols="accEx7">최종점검</a>
                            </div>
                            <div id="accEx7" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeSeq" value="${rsumeTaskDtl[6].rsumeSeq}">
                                    <input type="hidden" class="notRequired" name="rsumeTaskDtl.rsumeOrd" value="${rsumeTaskDtl[6].rsumeOrd}">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <input type="hidden" class="notRequired" name="rsumeTaskDtl.appctnSttsCd" value="${rsumeTaskDtl[6].appctnSttsCd}">
                                                <p class="form-control-static" >${rsumeTaskDtl[6].appctnSttsCdNm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rsumeTaskDtl[6].appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt0">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검위원</label>
                                            <div class="col-sm-6" >
                                                <div class="col-sm-6" style="margin-left: -15px">
                                                    <input type="hidden" class="notRequired chkCmssrSeq" name="rsumeTaskDtl.chkCmssrSeq" value="${rsumeTaskDtl[6].chkCmssrSeq}" />
                                                    <input type="text" class="form-control input-sm notRequired" value="${rsumeTaskDtl[6].chkCmssrNm}" title="점검위원" maxlength="50" placeholder="점검위원" disabled>
                                                </div>
                                                <div class="col-sm-1">
                                                    <button type="button" class="btn btn-sm btn-info btnCmtSearch">위원검색</button>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검계획일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkPlanDt" value="${empty rsumeTaskDtl[6].chkPlanDt ? today : rsumeTaskDtl[6].chkPlanDt}" title="점검계획일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">점검실시일</label>
                                            <div class="col-sm-6 form-inline" >
                                                <div class="input-group">
                                                    <input type="text" class="form-control input-sm datetimepicker_strtDt notRequired" name="rsumeTaskDtl.chkImplmnDt" value="${empty rsumeTaskDtl[6].chkImplmnDt ? today : rsumeTaskDtl[6].chkImplmnDt}" title="점검실시일" readonly="" onclick="cmmCtrl.initCalendar(this);">
                                                    <span class="input-group-btn" style="z-index:0;">
                                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                            <em class="ion-calendar"></em>
                                                        </button>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">스마트화 수준확인</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm" name="rsumeTaskDtl.smtfnPrsntCd notRequired" title="스마트화 현재 수준">
                                                    <c:forEach var="cdList" items="${cdDtlList.BGN_REG_INF}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList.cd, 'BGN_REG_INF020')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[6].smtfnPrsntCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값/결과</label>
                                            <div class="col-sm-6 form-inline">
                                                <select class="form-control input-sm notRequired mngSttsCd" name="rsumeTaskDtl.mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE02007_02_')}">
                                                            <option value="${cdList.cd}" <c:if test="${rsumeTaskDtl[6].mngSttsCd eq cdList.cd}" >selected</c:if> >
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >${rsumeTaskDtl[6].mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
        </form>


        <form id="sendForm" style="display: none">
            <input type="hidden" class="notRequired" name="nowRsumeTaskCd" value="${rsumeTaskDtl[rsumeLeng].rsumeSttsCd}" />
        </form>

        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>
        <%-- 위원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpd/MPDCmtSrchLayer.jsp"></jsp:include>

    </div>
</div>

