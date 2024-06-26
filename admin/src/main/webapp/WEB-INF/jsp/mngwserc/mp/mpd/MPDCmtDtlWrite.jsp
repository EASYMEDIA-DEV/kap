<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpd/MPDCmtDtlWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>

        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="lgnSsnId" value="${rtnData.lgnSsnId}">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />

            <h6 class="mt-lg"> 위원 기본 정보 </h6>

            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:10%;">
                        <col style="width:40%;">
                        <col style="width:10%;">
                        <col style="width:40%;">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">이름</th>
                        <td>${rtnInfo.name} </td>
                        <th scope="row" class="bg-gray-lighter">위원구분(업종/분야)</th>
                        <td>${rtnInfo.cmssrTypeCdNm}(${rtnInfo.cmssrCbsnCdNm})</td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">휴대폰번호</th>
                        <td>${rtnInfo.hpNo}</td>
                        <th scope="row" class="bg-gray-lighter">이메일</th>
                        <td>${rtnInfo.email}</td>
                    </tr>
                    <tr>
                        <th scope="row" class="bg-gray-lighter">최초 등록일시</th>
                        <td> ${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                        <th scope="row" class="bg-gray-lighter">재직여부</th>
                        <td>${rtnInfo.cmssrWorkCdNm}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#dtl">위원 상세정보</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#cun">컨설팅 사업 현황</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#san">상생 사업 현황</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#ken">근태 현황</a></li>
                <span class="dtl-tab" style="margin-left:50%"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
            </ul>

            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                    </div>
                </div>
                <div id="cun" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            컨설팅 사업 현황 (총 <span id="listContainerTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">사업연도</th>
                            <th class="text-center">사업구분</th>
                            <th class="text-center">진행상태</th>
                            <th class="text-center">부품사명</th>
                            <th class="text-center">사업자등록번호</th>
                            <th class="text-center">구분</th>
                            <th class="text-center">규모</th>
                            <th class="text-center">신청분야/업종</th>
                            <th class="text-center">신청 소재지</th>
                            <th class="text-center">개선율(%)</th>
                            <th class="text-center">지도착수일</th>
                            <th class="text-center">지도완료일</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                            <tbody id="listContainer"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainer"></div>
                </div>
                <div id="san" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            상생 사업 현황 (총 <span id="listContainerSanTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">사업연도</th>
                            <th class="text-center">회차</th>
                            <th class="text-center">사업구분</th>
                            <th class="text-center">사업명</th>
                            <th class="text-center">진행상태</th>
                            <th class="text-center">부품사명</th>
                            <th class="text-center">사업자등록번호</th>
                            <th class="text-center">구분</th>
                            <th class="text-center">규모</th>
                            <th class="text-center">신청일</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="listContainerSan"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainerSan"></div>
                </div>
                <div id="ken" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            근태 현황 (총 <span id="listContainerKenTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                        <div class="pull-right ml-sm input-group">
                            <input style="width:60%; float:right;" type="text" class="monthpicker monthInit form-control input-sm"  name="monthpicker" readonly/>
                            <span  style="width:20%;" class="input-group-btn" style="z-index:0;">
                                        <button style="margin-bottom:2.5rem ; padding-bottom:0.7rem" type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                        </div>
                    </div>

                <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">근태옵션</th>
                            <th class="text-center">지도부품사1</th>
                            <th class="text-center">소재지역1</th>
                            <th class="text-center">지도부품사2</th>
                            <th class="text-center">소재지역2</th>
                            <th class="text-center">기타출장</th>
                            <th class="text-center">기타</th>
                            <th class="text-center">근태체크일시</th>
                        </tr>
                        </thead>

                        <!-- 리스트 목록 결과 -->
                        <tbody id="listContainerKen"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainerKen"></div>
                </div>
            </div>
            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <div style="float:right">
                    <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                </div>
            </div>
        </form>
    </div>

    <div class="dtl-tab">
        <h5>등록/수정이력</h5>
        <table class="table">
            <colgroup>
                <col style="width:10%;">
                <col style="width:40%;">
                <col style="width:10%;">
                <col style="width:40%;">
            </colgroup>
            <tbody>
            <tr>
                <th scope="row" class="bg-gray-lighter">최초 등록자 </th>
                <td>${rtnInfo.regName}(${rtnInfo.regId})</td>
                <th scope="row" class="bg-gray-lighter">최초 등록일시</th>
                <td>${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">최종 수정자</th>
                <td>${kl:emptyHypen(rtnInfo.modName)}${rtnInfo.modId == null ? '' : '('+=rtnInfo.modId+=')'} </td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                <td>${ kl:emptyHypen(kl:convertDate(rtnInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-'))}</td>
            </tr>
            </tbody>
        </table>
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
