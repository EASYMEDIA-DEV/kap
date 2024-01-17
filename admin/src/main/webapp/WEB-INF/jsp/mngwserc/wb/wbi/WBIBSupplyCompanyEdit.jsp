<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include/el.jspf" %>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbi/WBIBSupplyCompanyEditCtrl controller/co/COFormCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청부품사 상세/수정</h6>
        <hr>

        <form class="form-horizontal" id="frmData" name="frmData" method="post">
            <div id="basicData">
                <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>

                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="${rtnInfo.pageIndex}"/>
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="${rtnInfo.pageRowSize}"/>
                <input type="hidden" id="listRowSize" name="listRowSize" value="${rtnInfo.listRowSize}"/>
                <input type="hidden" class="notRequired" id="memSeq" name="memSeq" value="${rtnInfo.memSeq}"/>
                <input type="hidden" class="notRequired" id="id" name="id" value="${rtnInfo.id}"/>
                <input type="hidden" class="notRequired" name="bsnmNo" value="${rtnInfo.bsnmNo}"/>
                <input type="hidden" class="notRequired" name="appctnSeq" value="${rtnInfo.detailsKey}"/>
                <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.detailsKey}"/>
                <input type="hidden" class="notRequired" name="userLogYn" value=""/>
                <input type="hidden" class="notRequired" name="bfreMemSeq" value="${rtnInfo.memSeq}"/>
                <input type="hidden" class="notRequired" name="aftrMemSeq"/>

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
                <div id="appctnPdfArea1">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">신청자(아이디)<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="nameAndId" value="${rtnInfo.name}(${rtnInfo.id})"
                                           title="신청자" maxlength="50" disabled/>
                                </div>
                                <div class="col-sm-1">
                                    <button type="button" class="btn btn-sm btn-info btnPartUserModal">회원검색
                                    </button>
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
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.deptCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">
                                    <input type="text" class="form-control " id="deptDtlNm" name="deptDtlNm"
                                           value="${rtnInfo.deptDtlNm}" title="부서 상세" maxlength="50"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">직급</label>
                            <div class="col-sm-5">
                                <div class="col-sm-5" style="margin-left: -15px">
                                    <select class="form-control input-sm notRequired" id="pstnCd" name="pstnCd"
                                            title="직급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'MEM_CD010')}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.pstnCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4" style="margin-left: -15px">
                                    <input type="text" class="form-control notRequired" id="pstnNm" name="pstnNm"
                                           value="${rtnInfo.pstnNm}" title="직급 상세" maxlength="50"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="hpNo" value="${rtnInfo.hpNo}">${rtnInfo.hpNo}</p>
                                </div>
                            </div>
                            <label class="col-sm-1 control-label">일반 전화번호</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control telNumber notRequired" id="telNo" name="telNo" value="${rtnInfo.telNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" title="전화번호"/>
                            </div>
                        </div>
                    </fieldset>


                    <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사 정보</h6>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">부품사명<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <input type="text" style="border:none; background-color: #FFFFFF"
                                       class="form-control notRequired" disabled id="cmpnNm" value="${rtnInfo.cmpnNm}"/>
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
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.ctgryCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="befeCtgryCd" class="notRequired"
                                           value="${rtnInfo.ctgryCd}"/>
                                </div>
                            </div>

                            <label class="col-sm-1 control-label">규모<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <div class="col-sm-6" style="margin-left: -15px">
                                    <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="규모">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList.cd, 'COMPANY020')}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.sizeCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
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
                                        <input type="text" class="form-control input-sm datetimepicker_strtDt"
                                               id="stbsmDt" name="stbsmDt"
                                               value="${rtnInfo.stbsmDt}"
                                               title="설립일자" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                        <span class="input-group-btn" style="z-index:0;">
                                            <button type="button" class="btn btn-inverse btn-sm"
                                                    onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
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
                                    <input type="text" class="form-control telNumber notRequired" id="compTel" name="compTel" value="${rtnInfo.telNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13" title="전화번호" placeholder="전화번호 입력"/>

                                </div>
                            </div>

                            <label class="col-sm-1 control-label">사업자등록<br/>번호<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static" id="bsnmNo" title="재직 회사사업자번호">${rtnInfo.bsnmNo}</p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">본사 주소<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <input type="button" class="btn btn-sm btn-primary" id="searchPostCode" value="우편번호 검색"><br>
                                <br>
                                <div style="display: flex; gap: 10px;">
                                    <input type="text" class="form-control input-sm" id="zipcode" name="zipCode"
                                           value="${rtnInfo.zipCode}" readonly placeholder="우편번호" style="width: 95px;"
                                           title="주소">
                                    <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr"
                                           value="${rtnInfo.bscAddr}" readonly placeholder="기본주소" style="width: 295px;"
                                           title="주소">
                                </div>
                                <br>
                                <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr"
                                       value="${rtnInfo.dtlAddr}" placeholder="상세주소" style="width: 400px;"
                                       maxlength="50" title="주소">
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">매출액(연도)</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt"
                                       value="${rtnInfo.slsPmt}" title="매출액" maxlength="50" placeholder="매출액"/>
                                &nbsp;억원&nbsp;
                                <select class="form-control input-sm notRequired" id="slsYear" name="slsYear">
                                    <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}"
                                                <c:if test="${rtnInfo.slsYear eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                                &nbsp;년&nbsp;
                            </div>

                            <label class="col-sm-1 control-label">직원수</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt"
                                       value="${rtnInfo.mpleCnt}" title="직원수" maxlength="50" placeholder="직원수 입력"/>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm form-inline">
                            <label class="col-sm-1 control-label">주생산품</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1"
                                       name="mjrPrdct1" value="${rtnInfo.mjrPrdct1}" title="주생산품(1)" maxlength="50"
                                       placeholder="주생산품(1)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2"
                                       name="mjrPrdct2" value="${rtnInfo.mjrPrdct2}" title="주생산품(2)" maxlength="50"
                                       placeholder="주생산품(2)을 입력해주세요."/>
                                <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3"
                                       name="mjrPrdct3" value="${rtnInfo.mjrPrdct3}" title="주생산품(3)" maxlength="50"
                                       placeholder="주생산품(3)을 입력해주세요."/>
                            </div>
                        </div>
                    </fieldset>

                    <div id="fieldStart">
                        <fieldset>
                            <div class="form-group text-sm form-inline">
                                <label class="col-sm-1 control-label">품질5스타</label>
                                <div class="col-sm-5">
                                    <select class="form-control input-sm notRequired" id="qlty5starCd"
                                            name="qlty5starCd" title="품질5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="qlty5starYear"
                                            name="qlty5starYear" title="품질5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}"
                                                    <c:if test="${rtnInfo.qlty5StarYear eq cdList.cd}">selected</c:if>>
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
                                    <select class="form-control input-sm notRequired" id="pay5starCd" name="pay5starCd"
                                            title="납입5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.pay5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="pay5starYear"
                                            name="pay5starYear" title="납입5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}"
                                                    <c:if test="${rtnInfo.pay5StarYear eq cdList.cd}">selected</c:if>>
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
                                    <select class="form-control input-sm notRequired" id="tchlg5starCd"
                                            name="tchlg5starCd" title="기술5스타등급">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                            <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${rtnInfo.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <select class="form-control input-sm notRequired" id="tchlg5starYear"
                                            name="tchlg5starYear" title="기술5스타연도">
                                        <option value="">선택</option>
                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                            <option value="${cdList.cd}"
                                                    <c:if test="${rtnInfo.tchlg5StarYear eq cdList.cd}">selected</c:if>>
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
                                        <input type="hidden" class="notRequired" id="cbsnSeq"
                                               name="sqInfoList[${status.index}].cbsnSeq" value="${i.cbsnSeq}"/>
                                        <input type="text" class="form-control input-sm notRequired" id="nm"
                                               name="sqInfoList[${status.index}].nm" value="${i.nm}" title="SQ 업종"
                                               placeholder="SQ 업종입력" maxlength="50"/>
                                        <input type="text" class="form-control input-sm notRequired" id="score"
                                               name="sqInfoList[${status.index}].score" value="${i.score}" title="점수"
                                               placeholder="SQ 점수입력" maxlength="50"/>
                                        <select class="form-control input-sm notRequired" id="year"
                                                name="sqInfoList[${status.index}].year" title="평가년도"
                                                style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}"
                                                        <c:if test="${i.year eq cdList.cd}">selected</c:if>> ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm"
                                               name="sqInfoList[${status.index}].crtfnCmpnNm" value="${i.crtfnCmpnNm}"
                                               title="인증주관사명" placeholder="SQ 인증주관사 입력" maxlength="50"/>
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
                            <table class="table table-hover table-striped">
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
                <div id="appctnPdfArea2">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">관리자메모</label>
                            <div class="col-sm-11">
                                <textarea class="form-control input-sm notRequired" name="admMemo" rows="10"
                                          maxlength="2000" title="관리자메모"
                                          placeholder="관리자메모를 입력하세요.">${rtnInfo.admMemo}</textarea>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>


            <div id="frmDataRsumeTask">
                <div class="container-fluid">
                    <div class="panel-group" id="accParent" role="tablist">
                        <div class="pull-right">
                            <button type="button" class="btn btn-sm btn-default appctnPdfDownload"
                                    data-html2canvas-ignore="true">신청정보 다운로드
                            </button>
                        </div>
                        <div>
                            <div class="text-left mb-xl"><h5>사업진행 상세</h5>
                            </div>
                            <hr>
                            <h6 class="mt0"><em class="ion-play mr-sm"></em>
                                <c:choose>
                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_001'}">
                                        전체 진행상태 : 신청(접수완료)
                                    </c:when>
                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_002'}">
                                        전체 진행상태 : 신청(보완요청)
                                    </c:when>
                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_003'}">
                                        전체 진행상태 : 신청(보완완료)
                                    </c:when>
                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_004'}">
                                        전체 진행상태 : 신청(사용자취소)
                                    </c:when>
                                    <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_005'}">
                                        전체 진행상태 : 신청(미선정)
                                    </c:when>
                                    <c:otherwise>
                                        전체 진행상태 : 신청(선정)
                                    </c:otherwise>
                                </c:choose>
                            </h6>
                        </div>

                        <!-- 영역 1 -->
                        <div class="panel panel-default" data-sttsCd="PRO_TYPE06001">
                            <div class="panel-heading" role="tab">
                                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#addEx1"
                                   aria-constrols="addEx1">
                                    신청
                                </a>
                            </div>
                            <div id="addEx1" class="panel-collapse collapse" role="tabpanel">
                                <div class="panel-body">

                                    <h6 class="mt0">신청자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">신청자 상태값</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">
                                                    <input type="hidden" id="appctnSttsCd" name="appctnSttsCd" value="">
                                                    <c:choose>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_001'}">
                                                            접수완료
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_002'}">
                                                            보완요청
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_003'}">
                                                            보완완료
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_004'}">
                                                            사용자취소
                                                        </c:when>
                                                        <c:when test="${rtnInfo.appctnSttsCd eq 'PRO_TYPE06001_01_005'}">
                                                            미선정
                                                        </c:when>
                                                        <c:otherwise>
                                                            선정
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
                                                <p class="form-control-static">${rtnInfo.appctnSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <h6 class="mt1">관리자</h6>
                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 상태값</label>
                                            <div class="col-sm-3 form-inline">
                                                <select class="form-control input-sm" id="mngSttsCd" name="mngSttsCd"
                                                        title="관리자 상태값">
                                                    <option value="">선택</option>
                                                    <c:forEach var="cdList" items="${cdDtlList.PRO_TYPE}"
                                                               varStatus="status">
                                                        <c:if test="${fn:contains(cdList, 'PRO_TYPE06001_02')}">
                                                            <c:if test="${cdList.cd ne 'PRO_TYPE06001_02'}">
                                                                <option value="${cdList.cd}"
                                                                        <c:if test="${rtnInfo.mngSttsCd eq cdList.cd}">selected</c:if>>
                                                                        ${cdList.cdNm}
                                                                </option>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="col-sm-3 form-inline">
                                                <input class="form-control notRequired input-sm" type="text"
                                                       id="rtrnRsnCntn" name="rtrnRsnCntn"
                                                       value="${rtnInfo.rtrnRsnCntn}" placeholder="사유입력"
                                                       style="width: 100%">
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-2 control-label">관리자 최종 수정일시</label>
                                            <div class="col-sm-6 form-inline">
                                                <p class="form-control-static">${rtnInfo.mngSttsChngDtm}</p>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <div class="form-group text-sm">
                                            <label class="col-sm-1 control-label">신청서</label>
                                            <input type="hidden" class="notRequired" name="fileCd"
                                                   value="ATTACH_FILE_TYPE01" title="첨부파일유형"/>
                                            <div class="col-sm-10 col-md-11">
                                                <input type="hidden" class="notRequired" name="fileSeq" value="${rtnInfo.appctnSeq}">
                                                <spring:eval var="fileExtns"
                                                             expression="@environment.getProperty('app.file.fileExtns')"/>
                                                <spring:eval var="atchUploadMaxSize"
                                                             expression="@environment.getProperty('app.file.max-size')"/>
                                                <div class="dropzone attachFile notRequired"
                                                     data-file-field-nm="fileSeq" data-file-extn="${fileExtns}"
                                                     data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1"
                                                     data-title="신청서">
                                                    <div class="dz-default dz-message">
                                                        <span><em class="ion-upload text-info icon-2x"></em><br/>파일을 드래그&드랍 또는 선택해주세요</span>
                                                    </div>
                                                </div>
                                                <p class="text-bold mt">
                                                    ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber
                                                        value="${atchUploadMaxSize / 1024 / 1024 / 8}"
                                                        maxFractionDigits="1"/>MB 이하, 최대 1개 파일 등록 가능)
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

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록
                    </button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                    <%--<button type="button" id="btnTest" class="btn btn-sm btn-success">저장</button>--%>
                </div>
            </div>
        </form>

        <form class="form-horizontal" id="frm" name="frm" method="post">
            <input type="hidden" class="notRequired" name="memSeq" value="${rtnInfo.memSeq}"/>
            <input type="hidden" class="notRequired" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" class="notRequired" name="detailsKey" value="${rtnInfo.appctnSeq}"/>
            <input type="hidden" class="notRequired" name="nextStageNm" value=""/>
            <input type="hidden" class="notRequired" name="bsnmNo" value="${userInfo.bsnmNo}">
        </form>


        <%-- 부품사 회원 검색 모달 --%>
        <jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpb/MPBMemberPartsSocietySrchLayer.jsp"></jsp:include>

    </div>
</div>

