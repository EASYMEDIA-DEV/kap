<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/eb/ebf/EBFEduRoomWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} ${ not empty rtnDto ? '상세/수정' : '등록'}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.placeSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육장명<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" id="nm" name="nm" value="${rtnDto.nm}" title="교육장명" maxlength="50" placeholder="교육장명 입력" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">지역<span class="star"> *</span></label>
                    <div class="col-sm-3">
                        <select class="form-control input-sm" name="rgnsCd" id="rgnsCd" title="지역">
                            <option value="">선택</option>
                            <c:forEach var="cdList" items="${cdDtlList.ED_CITY_CODE}" varStatus="status">
                                <option value="${cdList.cd}" <c:if test="${rtnDto.rgnsCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주소<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="button" class="btn btn-sm btn-gray" id="searchPostCode" value="우편번호 검색"><br>
                        <br>
                        <div style="display: flex; gap: 10px;">
                        <input type="text" class="form-control input-sm" id="zipcode" name="zipcode" value="${rtnDto.zipcode}" readonly placeholder="우편번호" style="width: 95px;" title="주소">
                        <input type="text" class="form-control input-sm" id="bscAddr" name="bscAddr" value="${rtnDto.bscAddr}" readonly placeholder="기본주소" style="width: 295px;" title="주소">
                        </div>
                        <br>
                        <input type="text" class="form-control input-sm" id="dtlAddr" name="dtlAddr" value="${rtnDto.dtlAddr}" placeholder="상세주소" style="width: 400px;" maxlength="50" title="주소">
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">대표번호<span class="star"> *</span></label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control input-sm" id="rprsntTelNo" name="rprsntTelNo" value="${rtnDto.rprsntTelNo}" title="대표번호" maxlength="13" placeholder="대표번호 입력" />
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <colgroup>
                            <col style="width:15%;">
                            <col style="width:35%;">
                            <col style="width:15%;">
                            <col style="width:35%;">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>최초 등록자</th>
                                <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                                <th>최초 등록일시</th>
                                <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                            </tr>
                            <tr>
                                <th>최종 수정자</th>
                                <td>${not empty rtnDto.modName ? rtnDto.modName += '(' += rtnDto.modId += ')' : '-'}</td>
                                <th>최종 수정일시</th>
                                <td>${not empty rtnDto.modDtm ? kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') : '-'}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        var modiYn = "N";

        $(document).on('input', 'select, input:text, textarea', function() {
            if (modiYn === "N" && $(this).val()) {
                modiYn = "Y";
            }
        });

        $(document).on('change', 'input:checkbox', function() {
            if (modiYn === "N" && $(this).is(":checked")) {
                modiYn = "Y";
            }
        });

        // 수정 페이지의 경우 modiYn을 "Y"로 설정
        if ($("#detailsKey").val() && $("#detailsKey").val() !== undefined) {
            modiYn = "Y";
        }

        history.pushState(null, null, '');
        window.addEventListener('popstate', browserPopstateHandler);

        function browserPopstateHandler(event) {

            if (modiYn === "Y") {
                var confirmed = confirm(msgCtrl.getMsg("confirm.list"));

                // browserPopstateHandler가 재발생할 수 있도록 브라우저 상태 다시 설정하기
                history.pushState(null, null, window.location.pathname + window.location.search);

                if (confirmed) {
                    var strPam = $("#btnList").data("strPam");
                    location.href = "./list?" + strPam;
                }
            } else {
                var strPam = $("#btnList").data("strPam");
                location.href = "./list?" + strPam;
            }
        }

        // 페이지 이동 시 이벤트 핸들러 등록 해제
        $(window).on('beforeunload', function() {
        });
    });

</script>