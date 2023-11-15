<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>


<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpe/MPEPartsCompanyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.bsnmNo}" />

            <c:if test="${ not empty rtnInfo }">
                <ul class="nav nav-tabs" id="myTab" role="tablist">
                    <li class="nav-item active">
                        <a class="nav-link active" id="basicTab" data-toggle="tab" href="#tab1" role="tab" aria-controls="tab1" aria-selected="true">부품사 기본정보
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" id="performTab" data-toggle="tab" href="#tab2" role="tab" aria-controls="tab2" aria-selected="false">부품사 실적정보
                        </a>
                    </li>
                </ul>
            </c:if>

            <div class="tab-content">
                <div class="tab-pane fade show active in" id="<c:if test="${ not empty rtnInfo }">tab1</c:if>" role="tabpanel" aria-labelledby="basicTab">
                    <div class="text-left"><h5>부품사 관리 등록</h5></div>
                    <div class="card">
                        <div class="table-responsive">
                            <table class="table">
                                <tbody>
                                <tr style="display:none;">
                                    <th style="width:10.3%;"></th>
                                    <td style="width:10.3%;"></td>
                                </tr>
                                <tr class="form-inline">
                                    <th>사업자등록번호<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm" id="bsnmNo" name="bsnmNo" value="${rtnDto.bsnmNo}" title="사업자번호" placeholder="사업자등록번호를 입력해주세요." <c:if test="${not empty rtnDto.bsnmNo}">readonly</c:if> style="width: 220px;"/>
                                        <button type="button" class="btn btn-sm" id="checkBtn">인증</button> <span class="input-sm">※ 사업자등록번호 인증 시 부품사/대표자명이 자동으로 입력됩니다.</span>

                                    </td>
                                </tr>
                                <tr>
                                    <th>부품사명<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm" id="cmpnNm" name="cmpnNm" value="${rtnDto.cmpnNm}" readonly title="부품사명" style="width: 200px;"/>
                                    </td>
                                    <th>부품사명(약식)</th>
                                    <td>
                                        <input type="text" class="form-control input-sm notRequired" id="cmpnNfrmlNm" name="cmpnNfrmlNm" value="${rtnDto.cmpnNfrmlNm}" title="부품사명(약식)" placeholder="부품사명(약식)을 입력해주세요."/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>대표자명<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm" id="rprsntNm" name="rprsntNm" value="${rtnDto.rprsntNm}" readonly title="대표자명" style="width: 200px;"/>
                                    </td>
                                    <th>부품사코드<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm notRequired" id="cmpnCd" name="cmpnCd" value="${rtnDto.cmpnCd}" title="부품사코드" placeholder="부품사코드를 입력해주세요."/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>구분<span class="star"> *</span></th>
                                    <td>
                                        <select class="form-control input-sm" id="ctgryCd" name="ctgryCd" title="구분" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'COMPANY01') and cdList.cd ne 'COMPANY01'}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnDto.ctgryCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <th>규모<span class="star"> *</span></th>
                                    <td>
                                        <select class="form-control input-sm" id="sizeCd" name="sizeCd" title="기업규모" style="width:auto; display:inline-block;" <c:if test="${not empty info}">disabled</c:if>>
                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'COMPANY020')}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnDto.sizeCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>전화번호<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm phoneChk" id="telNo" name="telNo" value="${rtnDto.telNo}" title="전화번호" placeholder="전화번호를 입력해주세요." style="width: 200px;"/>
                                    </td>
                                    <th>설립일자<span class="star"> *</span></th>
                                    <td>
                                        <div class="input-group" style="z-index:0;">
                                            <input type="text" class="form-control input-sm datetimepicker_strtDt ${kl:decode(rtnDto.stbsmDt, 'Y', 'notRequired', '')}" id="stbsmDt" name="stbsmDt" value="${kl:convertDate(rtnDto.stbsmDt, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}" readonly="readonly" title="설립일자" />
                                            <span class="input-group-btn" style="z-index:0;">
                                                <button type="button" class="btn btn-inverse input-sm" onclick="jQuery(this).parent().prev().focus();">
                                                    <em class="ion-calendar"></em>
                                                </button>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>본사주소<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnDto.zipcode}" readonly placeholder="우편번호">
                                        <input type="button" class="btn btn-sm" id="searchPostCode" value="우편번호 검색"><br>
                                        <br>
                                        <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDto.bscAddr}" readonly placeholder="기본주소" style="width: 400px;"><br>
                                        <br>
                                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" placeholder="상세주소" placeholder="상세주소를 입력해주세요." style="width: 400px;">
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>매출액</th>
                                    <td>
                                        <input type="text" class="form-control input-sm notRequired" id="slsPmt" name="slsPmt" value="${rtnDto.slsPmt}" title="매출액" placeholder="매출액을 입력해주세요."/> 억 원
                                        <select class="form-control input-sm notRequired" id="slsYear" name="slsYear" title="선택" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                <option value="${cdList.cd}" <c:if test="${rtnDto.slsYear eq cdList.cd}">selected</c:if>>
                                                        ${cdList.cdNm}
                                                </option>
                                            </c:forEach>
                                        </select> 년
                                    </td>
                                    <th>직원수</th>
                                    <td>
                                        <input type="text" class="form-control input-sm notRequired" id="mpleCnt" name="mpleCnt" value="${rtnDto.mpleCnt}" title="직원수" placeholder="직원수를 입력해주세요."/> 명
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>주생산품<span class="star"> *</span></th>
                                    <td>
                                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct1" name="mjrPrdct1" value="${rtnDto.mjrPrdct1}" title="주생산품1" placeholder="주생산품(1)을 입력해주세요." style="width: 200px;"/>
                                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct2" name="mjrPrdct2" value="${rtnDto.mjrPrdct2}" title="주생산품2" placeholder="주생산품(2)을 입력해주세요." style="width: 200px;"/>
                                        <input type="text" class="form-control input-sm notRequired" id="mjrPrdct3" name="mjrPrdct3" value="${rtnDto.mjrPrdct3}" title="주생산품3" placeholder="주생산품(3)을 입력해주세요." style="width: 200px;"/>
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>품질5스타</th>
                                    <td>
                                        <select class="form-control input-sm notRequired" id="qlty5StarCd" name="qlty5StarCd" title="품질5스타등급" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnDto.qlty5StarCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control input-sm notRequired" id="qlty5StarYear" name="qlty5StarYear" title="품질5스타연도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdList}" varStatus="status">
                                                <option value="${cdList}" <c:if test="${rtnDto.qlty5StarYear eq cdList}">selected</c:if>>
                                                        ${cdList}
                                                </option>
                                            </c:forEach>
                                        </select> 년
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>납입5스타</th>
                                    <td>
                                        <select class="form-control input-sm notRequired" id="pay5StarCd" name="pay5StarCd" title="납입5스타등급" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnDto.pay5StarCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control input-sm notRequired" id="pay5StarYear" name="pay5StarYear" title="납입5스타연도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdList}" varStatus="status">
                                                <option value="${cdList}" <c:if test="${rtnDto.pay5StarYear eq cdList}">selected</c:if>>
                                                        ${cdList}
                                                </option>
                                            </c:forEach>
                                        </select> 년
                                    </td>
                                </tr>
                                <tr class="form-inline">
                                    <th>기술5스타</th>
                                    <td>
                                        <select class="form-control input-sm notRequired" id="tchlg5StarCd" name="tchlg5StarCd" title="기술5스타등급" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                                <c:if test="${fn:contains(cdList, 'COMPANY030')}">
                                                    <option value="${cdList.cd}" <c:if test="${rtnDto.tchlg5StarCd eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control input-sm notRequired" id="tchlg5StarYear" name="tchlg5StarYear" title="기술5스타연도" style="width:auto;">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdList}" varStatus="status">
                                                <option value="${cdList}" <c:if test="${rtnDto.tchlg5StarYear eq cdList}">selected</c:if>>
                                                        ${cdList}
                                                </option>
                                            </c:forEach>
                                        </select> 년
                                    </td>
                                </tr>
                                <tr class="form-inline sqInfoArea" <c:if test="${rtnDto.ctgryCd eq null or rtnDto.ctgryCd eq 'COMPANY01001'}">style="display:none;"</c:if>>
                                    <th>SQ 정보</th>
                                    <td>
                                        <c:forEach items="${sqInfoList.list}" var="list" varStatus="status">
                                            <input type="hidden" class="notRequired" id="cbsnSeq${status.count}" name="sqInfoList${status.count}" value="${list.cbsnSeq}"/>
                                            <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="${list.nm}" title="SQ 업종" placeholder="SQ 업종입력"/>
                                            <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="${list.score}" title="점수" placeholder="SQ 점수입력"/>
                                            <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                                <option value="">선택</option>
                                                <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                    <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                            ${cdList.cdNm}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="${list.crtfnCmpnNm}" title="인증주관사명" placeholder="SQ 인증주관사 입력"/>
                                            </br>
                                        </c:forEach>
                                        <c:choose>
                                            <c:when test="${sqInfoListCnt == 0}">
                                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                                    <input type="hidden" class="notRequired" id="cbsnSeq${ status.count}" name="sqInfoList${status.count}" value=""/>
                                                    <input type="text" class="form-control input-sm notRequired" id="nm${status.count}" name="sqInfoList${status.count}" value="" title="SQ 업종" placeholder="SQ 업종입력"/>
                                                    <input type="text" class="form-control input-sm notRequired" id="score${status.count}" name="sqInfoList${status.count}" value="" title="점수" placeholder="SQ 점수입력"/>
                                                    <select class="form-control input-sm notRequired" id="year${status.count}" name="sqInfoList${status.count}" title="평가년도" style="width:auto;">
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                            <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${status.count}" name="sqInfoList${status.count}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력"/>
                                                    </br>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="i" begin="1" end="${ 3 - sqInfoListCnt }" varStatus="status">
                                                    <input type="hidden" class="notRequired" id="cbsnSeq${sqInfoListCnt + 1}" name="sqInfoList${sqInfoListCnt + 1}" value=""/>
                                                    <input type="text" class="form-control input-sm notRequired" id="nm${sqInfoListCnt + 1}" name="sqInfoList${sqInfoListCnt + 1}" value="" title="SQ 업종" placeholder="SQ 업종입력"/>
                                                    <input type="text" class="form-control input-sm notRequired" id="score${sqInfoListCnt + 1}" name="sqInfoList${sqInfoListCnt + 1}" value="" title="점수" placeholder="SQ 점수입력"/>
                                                    <select class="form-control input-sm notRequired" id="year${sqInfoListCnt + 1}" name="sqInfoList${sqInfoListCnt + 1}" title="평가년도" style="width:auto;">
                                                        <option value="">선택</option>
                                                        <c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}">
                                                            <option value="${cdList.cd}" <c:if test="${list.year eq cdList.cd}">selected</c:if>>
                                                                    ${cdList.cdNm}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="text" class="form-control input-sm notRequired" id="crtfnCmpnNm${sqInfoListCnt + 1}" name="sqInfoList${sqInfoListCnt + 1}" value="" title="인증주관사명" placeholder="SQ 인증주관사 입력"/>
                                                    </br>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <th>관리자메모<span class="star"> *</span></th>
                                    <td colspan="12">
                                        <textarea class="form-control input-sm notRequired" id="admMemo" name="admMemo" value="${rtnDto.admMemo}" title="관리자메모" placeholder="관리자 메모를 입력해주세요."></textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            <c:if test="${ not empty rtnInfo }">
                <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="performTab">
test
                </div>
            </c:if>
            </div>
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
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
    </div>
</div>