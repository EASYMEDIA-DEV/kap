<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbj/WBJRoundWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle} 기본정보</h7>
        <hr >
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.episdSeq}" />
            <input type="hidden" class="notRequired" id="bsnCd" name="bsnCd" value="INQ07010" />
            <input type="hidden" class="notRequired" id="episd" name="episd" value="1" />
            <input type="hidden" class="notRequired" id="bfreYear" value="${rtnInfo.year}" />
            <input type="hidden" class="notRequired" id="bfrePlaceSeq" value="${rtnInfo.placeSeq}"/>
            <input type="hidden" class="notRequired" id="bfreExpsYn" value="${rtnInfo.expsYn}"/>
            <input type="hidden" class="notRequired" id="bfreAddNtfyCntn" value="${rtnDto.addNtfyCntn}"/>
            <input type="hidden" class="notRequired" id="bfreBsnStrtDtm" value="${kl:convertDate(rtnInfo.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"/>
            <input type="hidden" class="notRequired" id="bfreBsnEndDtm" value="${kl:convertDate(rtnInfo.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"/>
            <input type="hidden" class="notRequired" id="bfreAccsStrtDtm" value="${kl:convertDate(rtnInfo.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"/>
            <input type="hidden" class="notRequired" id="bfreAccsEndDtm" value="${kl:convertDate(rtnInfo.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}"/>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <select class="form-control input-sm yearCnt" id="year" name="year">
                            <c:forEach begin="${year}" end="${todayYear+3}" var="result" step="1">
                                <option value="${result}" <c:if test="${rtnInfo.year eq result}">selected</c:if><c:if test="${empty rtnInfo and todayYear eq result}">selected</c:if>>${result}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">사업기간<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="bsnStrtDtm" name="bsnStrtDtm"
                                       value="<c:if test="${not empty rtnInfo}">${kl:convertDate(rtnInfo.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnInfo}">${today}</c:if>"
                                       title="사업기간 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="bsnEndDtm" name="bsnEndDtm"
                                       value="<c:if test="${not empty rtnInfo}">${kl:convertDate(rtnInfo.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnInfo}">${today}</c:if>"
                                       title="사업기간 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
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
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm datetimepicker_strtDt" id="accsStrtDtm" name="accsStrtDtm"
                                       value="<c:if test="${not empty rtnInfo}">${kl:convertDate(rtnInfo.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnInfo}">${today}</c:if>"
                                       title="접수 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="accsEndDtm" name="accsEndDtm"
                                       value="<c:if test="${not empty rtnInfo}">${kl:convertDate(rtnInfo.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnInfo}">${today}</c:if>"
                                       title="접수 종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>
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
                <c:choose>
                    <c:when test="${ rtnInfo.episdSeq != null }">
                    <div class="form-group text-sm examListContainer">
                        <c:forEach var="prizeList" items="${rtnInfo.prizeList}" varStatus="qstnStatus">
                            <div class="col-sm-12 examList mt-sm pl0 pr0">
                                <label class="col-sm-1 control-label examQstnNm"> ${qstnStatus.index > 0 ? '' : '포상종류/포상금 <span class="star"> *</span>'} </label>
                                <input type="hidden" class="notRequired" name="prizeSeq" title="포상순번" value="${prizeList.prizeSeq}" />
                                <div class="col-sm-11 pl0 pr0 prizeText">
                                    <div class="col-sm-12">
                                        <div>
                                            <label class="col-sm-1 control-label">훈격<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm srvTypeCd mrtsCd" name="mrtsCd" title="훈격" >
                                                    <option >훈격선택</option>
                                                    <c:forEach var="cdList" items="${cdList.MNGCNSLT_DIS}" varStatus="status">
                                                        <option value="${cdList.cd}" ${ kl:decode(prizeList.mrtsCd, cdList.cd, 'selected', '') }>${cdList.cdNm}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label class="col-sm-1 control-label">포상부문<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm srvTypeCd prizeCd" name="prizeCd" title="포상부문" >
                                                    <option >포상부문 선택</option>
                                                    <c:forEach var="cdList" items="${cdList.MNGCNSLT_REW}" varStatus="status">
                                                        <option value="${cdList.cd}" ${ kl:decode(prizeList.prizeCd, cdList.cd, 'selected', '') }>${cdList.cdNm}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label class="col-sm-1 control-label">포상금<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <input type="text" name="prizePmt" title="포상금"  value="${prizeList.prizePmt}">만원
                                            </div>
                                            <div class="col-sm-2 pl0">
                                                <button type="button" class="btn btn-sm btn-inverse btnExamWrite">포상종류 추가</button>
                                                <button type="button" class="btn btn-sm btn-danger btnExamDelete">포상종류 삭제</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    </c:when>
                    <c:otherwise>
                    <div class="form-group text-sm examListContainer">
                        <div class="col-sm-12 examList mt-sm pl0 pr0 examHtmlTemplage">
                            <label class="col-sm-1 control-label examQstnNm">포상종류/포상금 <span class="star"> *</span></label>
                            <div class="col-sm-11 pl0 pr0 prizeText">
                                <div class="col-sm-12">
                                    <div>
                                        <label class="col-sm-1 control-label">훈격<span class="star"> *</span></label>
                                        <div class="col-sm-2">
                                            <select class="form-control input-sm srvTypeCd mrtsCd" name="mrtsCd" title="훈격" >
                                                <option >훈격선택</option>
                                                <c:forEach var="cdList" items="${cdList.MNGCNSLT_DIS}" varStatus="status">
                                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label">포상부문<span class="star"> *</span></label>
                                        <div class="col-sm-2">
                                            <select class="form-control input-sm srvTypeCd prizeCd" name="prizeCd" title="포상부문" >
                                                <option >포상부문 선택</option>
                                                <c:forEach var="cdList" items="${cdList.MNGCNSLT_REW}" varStatus="status">
                                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label">포상금<span class="star"> *</span></label>
                                        <div class="col-sm-2">
                                            <input type="text" name="prizePmt" title="포상금" >만원
                                        </div>
                                        <div class="col-sm-2 pl0">
                                            <button type="button" class="btn btn-sm btn-inverse btnExamWrite">포상종류 추가</button>
                                            <button type="button" class="btn btn-sm btn-danger btnExamDelete">포상종류 삭제</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:otherwise>
                </c:choose>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm" >
                    <label class="col-sm-1 control-label">장소</label>
                    <input type="hidden" name="placeSeq" class="notRequired" id="placeSeq" title="장소" value="${rtnInfo.placeSeq}"/>
                    <button type="button" class="btn btn-inverse btn-sm mb-sm room" >장소 검색</button>
                </div>
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th class="text-center">장소명</th>
                        <th class="text-center">지역</th>
                        <th class="text-center">주소</th>
                        <th class="text-center">대표번호</th>
                    </tr>
                    </thead>
                    <!-- 리스트 목록 결과 -->
                    <tbody id="listContainer">
                    <c:choose>
                        <c:when test="${ rtnInfo.episdSeq != null }">
                            <c:forEach var="roomList" items="${rtnInfo.roomList}" varStatus="qstnStatus">
                                <tr data-total-count="0">
                                    <td class="text-center">${roomList.nm}</td>
                                    <td class="text-center">${roomList.rgnsName}</td>
                                    <td class="text-center">
                                        <c:if test="${not empty roomList.zipcode}">(${roomList.zipcode})</c:if> ${roomList.bscAddr}<c:if test="${not empty roomList.dtlAddr}">, ${roomList.dtlAddr}</c:if>
                                    </td>
                                    <td class="text-center">${roomList.rprsntTelNo}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr data-total-count="0">
                                <td colspan="4" class="text-center">
                                    검색결과가 없습니다.<br>
                                    (등록된 데이터가 없습니다.)
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label notRequired">추가공지사항</label>
                    <div class="col-sm-11 notRequired">
                    <textarea maxlength="30" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="추가공지사항을 입력하세요" id="addNtfyCntn" name="addNtfyCntn" oninput="cmmCtrl.checkMaxlength(this);">
                        ${rtnDto.addNtfyCntn}
                    </textarea>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnInfo.detailsKey}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">진행상태<span class="star"> *</span></label>
                        <div class="col-sm-11">${ rtnInfo.dateState}</div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출여부"  <c:if test="${empty rtnInfo}">checked</c:if> <c:if test="${rtnInfo.expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출여부" <c:if test="${rtnInfo.expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <%--노출여부 e--%>

            <c:if test="${not empty rtnInfo.episdSeq}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnInfo.regName} (${rtnInfo.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}</p>
                        </div>
                    </div>
                </fieldset>
                <c:set var="modFlag" value="${not empty rtnInfo.modDtm && (rtnInfo.regDtm ne rtnInfo.modDtm)}" />
                <fieldset class="last-child">
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최종 수정자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${rtnInfo.modName} (${rtnInfo.modId})
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최종 수정일</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}
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
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
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

<!-- 교육  매핑 -->
<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebf/EBFEduRoomSrchLayer.jsp"></jsp:include>