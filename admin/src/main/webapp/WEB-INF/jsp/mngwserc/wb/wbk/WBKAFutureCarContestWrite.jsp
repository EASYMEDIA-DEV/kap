<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />

<c:set var="date" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="todayYear"><fmt:formatDate value="${date}" pattern="yyyy" /></c:set>

<c:set var="year" value="${ not empty rtnYear.year ? rtnYear.year : todayYear}" />

<div class="container-fluid">
    <div class="card-body" data-controller="controller/wb/wbk/WBKAFutureCarContestWirteCtrl ">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
        <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>${pageTitle} 기본정보</h7>
        <hr >
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.episdSeq}" />
            <input type="hidden" class="notRequired" id="bsnCd" name="bsnCd" value="INQ07011" />
            <input type="hidden" class="notRequired" id="episd" name="episd" value="1" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <select class="form-control input-sm" id="year" name="year">
                            <c:forEach begin="${year}" end="${todayYear+3}" var="result" step="1">
                                <option value="${result}" <c:if test="${rtnDto.year eq result}">selected</c:if><c:if test="${empty rtnDto and todayYear eq result}">selected</c:if>>${result}</option>
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
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.bsnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="사업기간 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                    <em class="ion-calendar"></em>
                                </button>
                            </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="bsnEndDtm" name="bsnEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.bsnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
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
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
                                       title="접수 시작일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                                <span class="input-group-btn" style="z-index:0;">
                                                            <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                                                <em class="ion-calendar"></em>
                                                            </button>
                                                        </span>
                                <span class="input-group-addon bg-white b0">~</span>
                                <input type="text" class="form-control input-sm datetimepicker_endDt" id="accsEndDtm" name="accsEndDtm"
                                       value="<c:if test="${not empty rtnDto}">${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</c:if><c:if test="${empty rtnDto}">${today}</c:if>"
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
            <fieldset id="rowInsert">
                <div class="form-group text-sm examListContainer" data-controller="controller/wb/wbk/WBKAFutureCarContestWirteCtrl" style="display:none;">
                    <c:choose>
                        <c:when test="${ rtnDto != null }">
                            <c:forEach var="prizeList" items="${rtnDto.prizeList}" varStatus="qstnStatus">
                                <div class="col-sm-12 examList mt-sm pl0 pr0">
                                    <label class="col-sm-1 control-label examQstnNm">시상종류/참여구분 <span class="star"> *</span></label>
                                    <input type="hidden" class="notRequired" name="prizeSeq" value="${prizeList.prizeSeq}" />
                                    <div class="col-sm-11 pl0 pr0 prizeText">
                                        <div class="col-sm-12">
                                            <div>
                                                <label class="col-sm-1 control-label">시상<span class="star"> *</span></label>
                                                <div class="col-sm-2">
                                                    <select class="form-control input-sm srvTypeCd wdcrmCd" name="wdcrmCd">
                                                        <option >시상선택</option>
                                                        <c:forEach var="cdDtlList" items="${cdDtlList.WBK_AWD}" varStatus="status">
                                                            <option value="${cdDtlList.cd}" ${ kl:decode(prizeList.wdcrmCd, cdDtlList.cd, 'selected', '') }>${cdDtlList.cdNm}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <label class="col-sm-1 control-label">참여구분<span class="star"> *</span></label>
                                                <div class="col-sm-2">
                                                    <select class="form-control input-sm srvTypeCd ptcptType" name="ptcptType">
                                                        <option >참여부문 선택</option>
                                                        <c:forEach var="cdDtlList" items="${cdDtlList.WBK_PTN}" varStatus="status">
                                                            <option value="${cdDtlList.cd}" ${ kl:decode(prizeList.ptcptType, cdDtlList.cd, 'selected', '') }>${cdDtlList.cdNm}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <label class="col-sm-1 control-label">시상금<span class="star"> *</span></label>
                                                <div class="col-sm-2">
                                                    <input type="text" name="prizePmt" value="${prizeList.prizePmt}">만원
                                                </div>
                                                <div class="col-sm-2 pl0">
                                                    <button type="button" class="btn btn-sm btn-inverse btnExamWrite">시상종류 추가</button>
                                                    <button type="button" class="btn btn-sm btn-danger btnExamDelete">시상종류 삭제</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                        <div class="examHtmlTemplage">
                            <div class="col-sm-12 examList mt-sm pl0 pr0">
                                <label class="col-sm-1 control-label examQstnNm"></label>
                                <input type="hidden" class="notRequired" name="prizeSeq" value="${prizeList.prizeSeq}" />
                                <div class="col-sm-11 pl0 pr0 prizeText">
                                    <div class="col-sm-12">
                                        <div>
                                            <label class="col-sm-1 control-label">시상<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm srvTypeCd wdcrmCd" name="wdcrmCd">
                                                    <option >시상선택</option>
                                                    <c:forEach var="cdDtlList" items="${cdDtlList.WBK_AWD}" varStatus="status">
                                                        <option value="${cdDtlList.cd}">${cdDtlList.cdNm}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label class="col-sm-1 control-label">참여구분<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <select class="form-control input-sm srvTypeCd ptcptType" name="ptcptType">
                                                    <option >잠여구분 선택</option>
                                                    <c:forEach var="cdDtlList" items="${cdDtlList.WBK_PTN}" varStatus="status">
                                                        <option value="${cdDtlList.cd}">${cdDtlList.cdNm}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label class="col-sm-1 control-label">시상금<span class="star"> *</span></label>
                                            <div class="col-sm-2">
                                                <input type="text" name="prizePmt">만원
                                            </div>
                                            <div class="col-sm-2 pl0">
                                                <button type="button" class="btn btn-sm btn-inverse btnExamWrite">시상종류 추가</button>
                                                <button type="button" class="btn btn-sm btn-danger btnExamDelete">시상종류 삭제</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">장소<span class="star"> *</span></label>
                    <input type="hidden" name="placeSeq" id="placeSeq" value="${rtnInfo.placeSeq}"/>
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" onclick="cmmCtrl.getEduRoomLayerPop(function(data){
                      console.log(data);
                     })">장소 검색</button>
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
                            <c:forEach var="placeList" items="${rtnInfo.placeList}" varStatus="qstnStatus">
                                <tr data-total-count="0">
                                    <td class="text-center">${placeList.nm}</td>
                                    <td class="text-center">${placeList.rgnsName}</td>
                                    <td class="text-center">
                                        <c:if test="${not empty placeList.zipcode}">(${placeList.zipcode})</c:if> ${placeList.bscAddr}<c:if test="${not empty roomList.dtlAddr}">, ${roomList.dtlAddr}</c:if>
                                    </td>
                                    <td class="text-center">${placeList.rprsntTelNo}</td>
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
                    <label class="col-sm-1 control-label">추가공지사항</label>
                    <div class="col-sm-11">
                    <textarea maxlength="30" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;" rows="10" placeholder="추가공지사항을 입력하세요" id="addNtfyCntn" name="addNtfyCntn" title="추가공지사항" oninput="cmmCtrl.checkMaxlength(this);">
                        ${rtnDto.addNtfyCntn}
                    </textarea>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty rtnDto.detailsKey}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">진행상태<span class="star"> *</span></label>
                        <div class="col-sm-11">
                                ${ rtnDto.dateState}
                        </div>
                    </div>
                </fieldset>
            </c:if>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="mainYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출여부"<c:if test="${empty rtnDto}">checked</c:if>  <c:if test="${rtnDto.expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출여부" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <%--노출여부 e--%>

            <c:if test="${not empty rtnDto.detailsKey}">
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">최초 등록자</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">${rtnDto.regName} (${rtnDto.regId})</p>
                        </div>
                        <div class="col-sm-1"></div>
                        <label class="col-sm-1 control-label">최초 등록일</label>
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
                        <label class="col-sm-1 control-label">최종 수정일</label>
                        <div class="col-sm-4">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${modFlag}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', '')}
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

