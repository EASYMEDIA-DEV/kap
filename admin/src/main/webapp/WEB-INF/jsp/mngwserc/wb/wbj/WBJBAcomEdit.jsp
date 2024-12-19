
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbj/WBJBAcomEditCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청부품사 상세/수정</h6><hr>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <div id="basicData">
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="${rtnInfo.pageIndex}" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="${rtnInfo.pageRowSize}" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="${rtnInfo.listRowSize}" />
                <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnInfo.memSeq}" />
                <input type="hidden" class="notRequired" name="id" value="${rtnInfo.id}" />
                <input type="hidden" class="notRequired" id="bsnmNo" name="bsnmNo" value="${rtnInfo.bsnmNo}" />
                <input type="hidden" class="notRequired" name="appctnSeq" value="${rtnInfo.detailsKey}" />
                <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}" />
                <input type="hidden" class="notRequired" name="userLogYn" value=""/>
                <input type="hidden" class="notRequired" name="bfreMemSeq" value="${rtnInfo.memSeq}" />
                <input type="hidden" class="notRequired" name="aftrMemSeq" value=""/>
                <input type="hidden" class="notRequired" id="name" value="${rtnInfo.name}" />

                <%--<h6 class="mt0"><em class="ion-play mr-sm"></em>회차 정보</h6>

                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-6" style="margin-left: -15px">
                                <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                    <option value="">연도 전체</option>
                                    <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                        <option value="${optYear}" <c:if test="${kl:convertDate(rtnInfo.year, 'yyyy-mm-dd', 'yyyy', '') eq optYear}">selected</c:if>>${optYear}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <select class="form-control input-sm" id="optEpisd" name="episd" title="회차">
                                <option value="">회차 전체</option>
                                <c:forEach var="cdList" items="${cdDtlList.ROUND_CD}" varStatus="status">
                                    <option value="${cdList.cd}" <c:if test="${rtnInfo.episd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </fieldset>--%>
                <div id="appctnPdfArea1" >
                    <fieldset>
                        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청자 정보</h6>
                        <div style="float:right"><button type="button" class="btn btn-sm btn-info appctnPdfDownload" data-html2canvas-ignore="true">신청정보다운</button></div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="nameAndId" value="${rtnInfo.name}(${rtnInfo.id})" title="신청자" maxlength="50" readonly/>
                                </div>
                                <div class="col-sm-1">
                                    <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색</button>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                            <div class="col-sm-2">
                                <p class="form-control-static" id="email" value="${rtnInfo.email}">${rtnInfo.email}</p>
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
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.deptCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">
                                    <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm" value="${rtnInfo.deptDtlNm}" title="부서 상세" maxlength="50"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-5" style="margin-left: -15px">
                                    <select class="form-control input-sm" id="pstnCd" name="pstnCd" title="직급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">
                                    <input type="text" class="form-control notRequired" style="display:none;" id="pstnNm" name="pstnNm" value="${rtnInfo.pstnNm}" title="직급 상세" maxlength="50"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                            <div class="col-sm-5"style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <input type="text" style="border:none; background-color: #FFFFFF" class="form-control notRequired" disabled id="hpNo" value="${rtnInfo.hpNo}"/>
                                </div>
                            </div>
                            <label class="col-sm-1 control-label">일반 전화번호</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control telNumber notRequired" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"/>
                            </div>
                        </div>
                    </fieldset>


                    <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <input type="text" style="border:none; background-color: #FFFFFF" class="form-control notRequired" disabled id="cmpnNm" value="${rtnInfo.cmpnNm}"/>
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
                                            <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01') and cdList.cd ne 'COMPANY01003' and cdList.cd ne 'COMPANY01004'}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="befeCtgryCd" class="notRequired" value="${rtnInfo.ctgryCd}"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="규모">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'COMPANY020')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
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
                                    <p class="form-control-static" id="rprsntNm">${rtnInfo.rprsntNm}</p>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">설립일자<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt" id="stbsmDt" name="stbsmDt"
                                               value="${rtnInfo.stbsmDt}"
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
                                    <input type="text" class="form-control telNumber input-sm" id="compTel" name="compTel" value="${rtnInfo.compTel}" title="회사 전화번호"oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" placeholder="회사 전화번호 입력"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static" id="changeBsnmNo" title="재직 회사사업자번호">${rtnInfo.bsnmNo}</p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">본사주소<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <input type="button" class="btn btn-sm btn-gray" id="searchPostCode" value="우편번호 검색"><br>
                                <br>
                                <div style="display: flex; gap: 10px;">
                                    <input type="text" class="form-control input-sm" id="zipcode" name="zipCode" value="${rtnInfo.zipCode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                                    <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                                </div>
                                <br>
                                <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnInfo.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">매출액(연도)</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${rtnInfo.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                                &nbsp;억원&nbsp;
                                <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnInfo.slsYear eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                                &nbsp;년&nbsp;
                            </div>

                            <label class="col-sm-1 control-label">직원수</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">주생산품</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품(1)" maxlength="50" placeholder="주생산품(1)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품(2)" maxlength="50" placeholder="주생산품(2)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품(3)" maxlength="50" placeholder="주생산품(3)을 입력해주세요."/>
                            </div>
                        </div>
                    </fieldset>

                    <div id="fieldStart">
                        <fieldset>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">품질5스타</label>
                                <div class="col-sm-5">
                                    <select class="form-control input-sm notRequired" id="qlty5starCd" name="qlty5StarCd" title="품질5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="qlty5starYear" name="qlty5StarYear" title="품질5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.qlty5StarYear eq cdList.cd}">selected</c:if>>
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
                                    <select class="form-control input-sm notRequired" id="pay5starCd" name="pay5StarCd" title="납입5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="pay5starYear" name="pay5StarYear" title="납입5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.pay5StarYear eq cdList.cd}">selected</c:if>>
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
                                    <select class="form-control input-sm notRequired" id="tchlg5starCd" name="tchlg5StarCd" title="기술5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="tchlg5starYear" name="tchlg5StarYear" title="기술5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.tchlg5StarYear eq cdList.cd}">selected</c:if>>
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
                                <c:forEach var="i" items="${rtnDataCompDetail.list}" varStatus="status">
                                    <div class="col-sm-12" id="sqInfoArea${status.index}">
                                        <input type="hidden" class="notRequired" id="cbsnSeq" name="sqInfoList[${status.index}].cbsnSeq" value="${i.cbsnSeq}" />
                                        <input type="text" class="form-control input-sm notRequired" id="nm" name="sqInfoList[${status.index}].nm" value="${i.nm}" title="SQ 업종" placeholder="SQ 업종입력" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score" name="sqInfoList[${status.index}].score" value="${i.score}" title="점수" placeholder="SQ 점수입력" maxlength="50"/>
                                        <select class="form-control input-sm notRequired" id="year" name="sqInfoList[${status.index}].year" title="평가년도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${i.year eq cdList.cd}">selected</c:if>>  ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm" name="sqInfoList[${status.index}].crtfnCmpnNm" value="${i.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>
                </div>

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
                                <tbody id="changeList"/>
                            </table>
                            <!-- 페이징 버튼 -->
                            <div id="changePaging"/>
                        </div>
                    </div>
                </fieldset>
            </div>
            <div id="appctnPdfArea2" >
                <h6 class="mt0"><em class="ion-play mr-sm"></em>자동차부품산업대상 신청정보</h6>
                <h7 class="mt0">추천자 정보</h7>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm same" id="rcmndName" name="rcmndName" value="${rtnInfo.rcmndName}" title="이름"  placeholder="이름 입력">
                        </div>
                        <div class="pull-right">
                            <label class="checkbox-inline c-checkbox" style="margin-left: 30px !important; margin-right: 32px !important;">
                                <input type="checkbox" class="notRequired" id="same" name="same" value="" title=""/>
                                <span class="ion-checkmark-round"></span> 신청자 정보와 동일
                            </label>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control phoneChk input-sm same" id="rcmndHpNo" name="rcmndHpNo" value="${rtnInfo.rcmndHpNo}" title="휴대폰번호"  placeholder="휴대폰번호 입력"
                                   oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="14">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm same" id="rcmndCmpnNm" name="rcmndCmpnNm" value="${rtnInfo.rcmndCmpnNm}" title="부품사명" placeholder="부품사명 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm same" id="rcmndDeptNm" name="rcmndDeptNm" value="${rtnInfo.rcmndDeptNm}" title="부서" placeholder="부서 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm same" id="rcmndPstnNm" name="rcmndPstnNm" value="${rtnInfo.rcmndPstnNm}" title="직급"  placeholder="직급 입력">
                        </div>
                    </div>
                </fieldset>

                <h7 class="mt0">포상대상자정보</h7>

                <fieldset>
                    <label class="col-sm-1 control-label">포상부문<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-3">
                            <input type="hidden" value="${rtnInfo.prizeCd}" id="userPirzeCd" name="userPirzeCd" />
                            <select class="form-control srvTypeCd prizeCd" id="optPrize" name="prizeCd">
                                <c:forEach var="cdList" items="${rtnInfo.prizeDtoList}" varStatus="status">
                                    <option value="${cdList.prizeCd}" <c:if test="${rtnInfo.prizeCd eq cdList.prizeCd}">selected</c:if>>${cdList.prizeCdNm}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부품사명 검색<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="hidden" title="부품사" id="newBsnmNo" name="cmpnSeq" value="${rtnInfo.cmpnSeq}" />
                            <input type="text" class="form-control input-sm notRequired" id="bsnmNoNm" disabled value="${ rtnInfo.bsnmNoNm }" title="부품사명"/>
                        </div>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-inverse btn-sm bsnmNoBtn">
                                부품사 검색
                            </button>
                        </span>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">구분<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <c:choose>
                                <c:when test="${rtnInfo.newCtgryCd == 'COMPANY01002'}">
                                    <input type="text" class="form-control input-sm" id="ctgryNm" value="2차" title="구분" maxlength="4" disabled>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="form-control input-sm" id="ctgryNm" value="1차" title="구분" maxlength="4" disabled>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm" name="newName" value="${rtnInfo.newName}" title="이름" maxlength="4" placeholder="이름 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">부서<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm" id="deptNm" name="deptNm" value="${rtnInfo.deptNm}" title="부서" maxlength="4" placeholder="부서 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">직급<span class="star"> *</span></label>
                        <div class="col-sm-5" style="margin-left: -15px">
                            <div class="col-sm-3">
                                <select class="form-control input-sm notRequired" id="newPstnCd" name="newPstnCd" title="직급">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                            <option value="${cdList.cd}" <c:if test="${rtnInfo.newPstnCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4" style="margin-left: -15px">
                                <input type="text" class="form-control notRequired" style="display:none;" value="${rtnInfo.newPstnNm}" id="newPstnNm" name="newPstnNm" title="직급 상세" maxlength="50"/>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">연령<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm" id="age" name="age" value="${rtnInfo.age}" title="연령"  placeholder="연령 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">근속년수<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm" id="yrssvYearCnt" name="yrssvYearCnt" value="${rtnInfo.yrssvYearCnt}" title="근속년수" placeholder="근속년수 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control phoneChk input-sm" name="newHpNo" value="${rtnInfo.newHpNo}" title="휴대폰번호" placeholder="휴대폰번호 입력" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="14">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">회사 전화번호<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control phoneChk input-sm" name="newCmpnTelNo" value="${rtnInfo.newCmpnTelNo}" title="회사 전화번호" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="11" placeholder="회사전화번호 입력">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control input-sm" name="newEmail" value="${rtnInfo.newEmail}" title="이메일" placeholder="이메일 입력">
                        </div>
                    </div>
                </fieldset>
            </div>
            <div id="frmDataRsumeTask">
                <div class="container-fluid">
                    <div class="panel-group" id="accParent" role="tablist">
                        <div>
                            <div class="text-left mb-xl"><h5>사업진행 상세</h5></div>
                            <hr>
                            <h6 class="mt0"><em class="ion-play mr-sm"></em>
                                <c:choose>
                                    <c:when test="${empty rtnAppctnRsume.applyList}">
                                        <c:choose>
                                            <c:when test="${rtnInfo.appctnSttsCd ne 'PRO_TYPE05001_01_003'}">
                                                <c:choose>
                                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_001'}">
                                                        전체 진행상태 : 1차 심사(접수완료)
                                                    </c:when>
                                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_002'}">
                                                        전체 진행상태 : 1차 심사(탈락)
                                                    </c:when>
                                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_004'}">
                                                        전체 진행상태 : 1차 심사(사용자취소)
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                전체 진행상태 : 1차 심사(통과)
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="item" items="${rtnAppctnRsume.applyList}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_001'}">
                                                    전체 진행상태 : 최종 심사(결과대기)
                                                </c:when>
                                                <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_002'}">
                                                    전체 진행상태 : 최종 심사(사용자취소)
                                                </c:when>
                                                <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_003'}">
                                                    전체 진행상태 : 최종 심사(탈락)
                                                </c:when>
                                                <c:otherwise>
                                                    전체 진행상태 : 최종 심사(수상)
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </h6>
                        </div>

                        <!-- 영역 1 1차 심사-->
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE05001">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx1" aria-constrols="addEx1">
                                    1차 심사
                                </a>
                            </div>
                            <div id="addEx1" class="panel-collapse collapse <c:if test="${empty rtnAppctnRsume.applyList}">in</c:if>" role="tabpanel">
                                <div class="panel-body">
                                    <input type="hidden" class="notRequired" name="rsumeSeq" value="${rtnInfo.rsumeSeq}" />
                                    <input type="hidden" class="notRequired" name="rsumeOrd" value="${rtnInfo.rsumeOrd}" />
                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">
                                                    <input type="hidden" id="appctnSttsCd" name="appctnSttsCd" value="">
                                                    <c:choose>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_001'}">
                                                            접수완료
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_002'}">
                                                            탈락
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE05001_01_004'}">
                                                            사용자취소
                                                        </c:when>
                                                        <c:otherwise>
                                                            통과
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static" >
                                                    ${ kl:convertDate(rtnInfo.appctnSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt1">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-3 form-inline">
                                                <select class="form-control input-sm" id="mngSttsCd" name="mngSttsCd" title="관리자 상태값">
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE05001_02')}">
                                                            <c:if test="${cdList.cd ne 'PRO_TYPE05001_02'}">
                                                                <option value="${cdList.cd}" <c:if test="${rtnInfo.mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:if>
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
                                                <p class="form-control-static" >
                                                    ${ kl:convertDate(rtnInfo.mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                </p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">신청서<span class="star"> *</span></label>
                                            <input type="hidden" class="notRequired" name="fileCd" value="ATTACH_FILE_TYPE01" title="첨부파일유형"/>
                                            <div class="col-sm-10 col-md-11">
                                                <input type="hidden" class="notRequired" name="appctnFileSeq" value="${rtnInfo.appctnFileSeq}">
                                                <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                                                <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                                                <div class="dropzone attachFile notRequired" data-file-field-nm="appctnFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="신청서">
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
                        <%--영역 2 최종심사--%>

                        <div class="panel panel-default" data-sttsCd="PRO_TYPE05002">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx2" aria-constrols="addEx2">
                                    최종 심사
                                </a>
                            </div>
                            <c:choose>
                                <c:when test="${empty rtnAppctnRsume.applyList}">
                                    <div id="addEx2" class="panel-collapse collapse" role="tabpanel">
                                        <div class="panel-body">
                                            <h6 class="mt0">신청자</h6>
                                            <fieldset>
                                                <div class="form-group text-sm">
                                                    <label class="col-sm-2 control-label">신청자 상태값</label>
                                                    <div class="col-sm-6 form-inline">
                                                        <p class="form-control-static">

                                                        </p>
                                                    </div>
                                                </div>
                                            </fieldset>
                                            <fieldset>
                                                <div class="form-group text-sm">
                                                    <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                                    <div class="col-sm-6 form-inline">
                                                        <p class="form-control-static" >
                                                                ${ kl:convertDate(item.appctnSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                        </p>
                                                    </div>
                                                </div>
                                            </fieldset>

                                            <h6 class="mt1">관리자</h6>
                                            <fieldset>
                                                <div class="form-group text-sm">
                                                    <label class="col-sm-2 control-label">훈격</label>
                                                    <div class="col-sm-3 form-inline">
                                                        <select class="form-control input-sm mrtsCd notRequired" title="훈격">
                                                            <option value="" >선택</option>

                                                        </select>
                                                    </div>
                                                </div>
                                            </fieldset>
                                            <fieldset>
                                                <div class="form-group text-sm">
                                                    <label class="col-sm-2 control-label">관리자 상태값</label>
                                                    <div class="col-sm-3 form-inline">
                                                        <select class="form-control notRequired" title="관리자 상태값">
                                                        </select>
                                                    </div>
                                                </div>
                                            </fieldset>

                                            <fieldset>
                                                <div class="form-group text-sm">
                                                    <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                                    <div class="col-sm-6 form-inline">
                                                        <p class="form-control-static" ></p>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="item" items="${rtnAppctnRsume.applyList}" varStatus="status">
                                        <div id="addEx2" class="panel-collapse collapse in" role="tabpanel">
                                            <div class="panel-body">
                                                <input type="hidden" class="notRequired" name="finalRsumeSeq" value="${item.rsumeSeq}" />
                                                <input type="hidden" class="notRequired" name="rsumeOrd" value="2" />
                                                <h6 class="mt0">신청자</h6>
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <label class="col-sm-2 control-label">신청자 상태값</label>
                                                        <input type="hidden" class="notRequired" id="finalAppctnSttsCd" name="finalAppctnSttsCd" value="">
                                                        <div class="col-sm-6 form-inline">
                                                            <p class="form-control-static">
                                                                <c:choose>
                                                                    <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_001'}">
                                                                        결과대기
                                                                    </c:when>
                                                                    <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_002'}">
                                                                        사용자취소
                                                                    </c:when>
                                                                    <c:when test="${item.appctnSttsCd eq 'PRO_TYPE05002_01_003'}">
                                                                        탈락
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        수상
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <label class="col-sm-2 control-label">신청자 최종 수정일시</label>
                                                        <div class="col-sm-6 form-inline">
                                                            <p class="form-control-static" >
                                                                    ${ kl:convertDate(item.appctnSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </p>
                                                        </div>
                                                    </div>
                                                </fieldset>

                                                <h6 class="mt1">관리자</h6>
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <label class="col-sm-2 control-label">훈격</label>
                                                        <div class="col-sm-3 form-inline">
                                                            <select class="form-control input-sm mrtsCd notRequired" id="optMrtsCd" name="mrtsCd" title="훈격">
                                                                <option >선택</option>
                                                                <c:forEach var="cdList" items="${rtnInfo.mrtsList}" varStatus="status">
                                                                    <option value="${cdList.mrtsCd}" <c:if test="${rtnInfo.mrtsCd eq cdList.mrtsCd}">selected</c:if>>${cdList.mrtsCdNm}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                                <fieldset>
                                                    <div class="form-group text-sm">
                                                        <label class="col-sm-2 control-label">관리자 상태값</label>
                                                        <div class="col-sm-3 form-inline">
                                                            <select class="form-control notRequired" id="finalMngSttsCd" name="finalMngSttsCd" title="관리자 상태값">
                                                                <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}" varStatus="status">
                                                                    <c:if test="${fn:contains(cdList, 'PRO_TYPE05002_02')}">
                                                                        <c:if test="${cdList.cd ne 'PRO_TYPE05002_02'}">
                                                                            <option value="${cdList.cd}" <c:if test="${item.mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                                    ${cdList.cdNm}
                                                                            </option>
                                                                        </c:if>
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
                                                            <p class="form-control-static" >
                                                                    ${ kl:convertDate(item.mngSttsChngDtm , 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                                            </p>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <%--2차 영역 e--%>
                    </div>
                </div>
            </div>


            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                    <%--<button type="button" id="btnTest" class="btn btn-sm btn-success">저장</button>--%>
                </div>
            </div>
        </form>

        <form class="form-horizontal" id="frm" name="frm" method="post">
            <input type="hidden" class="notRequired" name="memSeq" value="${rtnInfo.memSeq}" />
            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}" />
            <input type="hidden" class="notRequired" name="nextStageNm" value="" />
        </form >

        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpe/MPEPartsCompanySrchLayer.jsp"></jsp:include>