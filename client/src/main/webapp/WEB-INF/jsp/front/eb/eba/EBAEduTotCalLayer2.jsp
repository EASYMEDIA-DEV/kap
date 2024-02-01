<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<script>


    var formLayerObj = $("form[name='frmEduTotCalSearch']");


    var searchLayer = function (arg){

        var searchForm = {};

        searchForm.ctgryCd = arg;
        cmmCtrl.jsonAjax(function(data){
            var rtn = JSON.parse(data);
            //정원여유

            $(".edctnList").empty();
            var listSize = rtn.list.length;



            if(listSize > 0){
                $.each(rtn.list, function(e){

                    var $this = this;

                    var addClass =  (e==0)? "swiper-slide txt-tab-btn active edctnSearch" : "swiper-slide txt-tab-btn edctnSearch";

                    var temp = "<a class='"+addClass+"' href='javascript:edctnSearch("+$this.edctnSeq+");' data-edctnseq="+$this.edctnSeq+"><p class='txt'><span class='menu-name'>"+$this.nm+"</span></p></a>";

                    $(".edctnList").append(temp);

                });

                $(".edu-info-div").css("display", "");

                $(".listNotEmpty").css("display", "");
                $(".listEmpty").css("display", "none");
                $(".total-edu-area").removeClass("no-data");



                edctnSearch(rtn.list[0].edctnSeq);

            }else{
                $(".edu-info-div").css("display", "none");

                $(".listNotEmpty").css("display", "none");
                $(".listEmpty").css("display", "");
                $(".total-edu-area").addClass("no-data");
            }



        }, "/education/apply/selectEduList", searchForm, "text");

        $(".txt-depth-tab-swiper .txt-tab-btn").off().on("click", function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

        $(".edctnList").find("a:first").trigger("click");

    }

    var searchEpisdLayer = function (edctnSeq){

        var searchForm = {};

        var now = new Date();	// 현재 날짜 및 시간
        var year = now.getFullYear();	// 연도

        searchForm.detailsKey = edctnSeq;
        searchForm.episdYear = year;

        cmmCtrl.jsonAjax(function(data){

            var rtn = JSON.parse(data);

            var rtnDto = rtn.rtnDto;
            var rtnEpisdList = rtn.rtnEpisdList;


            $(".edctnNmLayer").attr("href", "/education/apply/detail?detailsKey="+rtnDto.edctnSeq);
            $(".edctnNmLayer").find("span").text(rtnDto.nm);//제목변경 nm
            $(".stduyMthdLayer").text(rtnDto.stduyMthdCdNm);//교육타입 변경 stduyMthdCdNm
            $(".stduyDdLayer").text(rtnDto.stduyDdCdNm+"일("+rtnDto.stduyTimeCdNm+"시간)");//교육일자, 시간 변경

            var listSize = rtnEpisdList.length;

            if(listSize>0){
                $(".edu-info-div").css("display", "");
                $(".edu-plan-area").find(".listNotEmpty").find(".round-list").empty();
                $(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").empty();

                $(".listNotEmpty").css("display", "");
                $(".listEmpty").css("display", "none");
                $(".total-edu-area").removeClass("no-data");


                $.each(rtnEpisdList, function(){

                    var copyEpisdSample = $(".copyEpisdSample").find(".round-div").clone(true);
                    var copyEpisdSample2 = $(".copyEpisdSample2").find(".period-bar-div").clone(true);

                    var $this = this;

                    var episdOrd = $this.episdOrd;

                    var accsStrtDt = $this.accsStrtDt;
                    var accsEndDt = $this.accsEndDt;
                    var edctnStrtDt = $this.edctnStrtDt;
                    var edctnEndDt = $this.edctnEndDt;

                    var accsStrtYear = $this.accsStrtYear;
                    var accsEndYear = $this.accsEndYear;
                    var edctnStrtYear = $this.edctnStrtYear;
                    var edctnEndYear = $this.edctnEndYear;

                    var episdYear = $this.episdYear;


                    console.log("@@accsStrtDt = " + accsStrtDt);


                    copyEpisdSample.find(".episdOrd").text(episdOrd+"회차");
                    copyEpisdSample.find(".episdAccsDt").text(accsStrtDt + " ~ " + accsEndDt);
                    if(episdYear != accsStrtYear && episdYear != accsEndYear){
                        copyEpisdSample.find(".episdAccsDt").closest("div").addClass("no-this-year");
                    }

                    copyEpisdSample.find(".episdEduDt").text(edctnStrtDt + " ~ " + edctnEndDt);
                    if(episdYear != edctnStrtYear && episdYear != edctnEndYear){
                        copyEpisdSample.find(".episdEduDt").closest("div").addClass("no-this-year");
                    }

                    $(".eduYear").text(episdYear+"년");
                    $(".edu-plan-area").find(".listNotEmpty").find(".round-list").append(copyEpisdSample);

                    $(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").append(copyEpisdSample2);

                });

                //과정에 대한 차수 반복
                $(".edu-plan-area").find(".round-list").find(".copyEpisdSample").css("display","");

                schedulePopupFn();

            }else{
                $(".edu-info-div").css("display", "none");
                $(".listNotEmpty").css("display", "none");
                $(".listEmpty").css("display", "");
                $(".total-edu-area").addClass("no-data");



            }

        }, "/education/apply/detailLayer", searchForm, "text");
    }

    var searchEpisdLayer = function (edctnSeq){
        console.log("@@@B");
        var searchForm = {};

        var now = new Date();	// 현재 날짜 및 시간
        var year = now.getFullYear();	// 연도

        searchForm.detailsKey = edctnSeq;
        searchForm.episdYear = year;

        cmmCtrl.jsonAjax(function(data){

            var rtn = JSON.parse(data);

            var rtnDto = rtn.rtnDto;
            var rtnEpisdList = rtn.rtnEpisdList;


            $(".edctnNmLayer").attr("href", "/education/apply/detail?detailsKey="+rtnDto.edctnSeq);
            $(".edctnNmLayer").find("span").text(rtnDto.nm);//제목변경 nm
            $(".stduyMthdLayer").text(rtnDto.stduyMthdCdNm);//교육타입 변경 stduyMthdCdNm
            $(".stduyDdLayer").text(rtnDto.stduyDdCdNm+"일("+rtnDto.stduyTimeCdNm+"시간)");//교육일자, 시간 변경

            var listSize = rtnEpisdList.length;

            if(listSize>0){
                $(".edu-info-div").css("display", "");
                $(".edu-plan-area").find(".listNotEmpty").find(".round-list").empty();
                $(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").empty();

                $(".listNotEmpty").css("display", "");
                $(".listEmpty").css("display", "none");
                $(".total-edu-area").removeClass("no-data");

                $.each(rtnEpisdList, function(){

                    var copyEpisdSample = $(".copyEpisdSample").find(".round-div").clone(true);
                    var copyEpisdSample2 = $(".copyEpisdSample2").find(".period-bar-div").clone(true);

                    var $this = this;

                    var episdOrd = $this.episdOrd;

                    var accsStrtDt = $this.accsStrtDt;
                    var accsEndDt = $this.accsEndDt;
                    var edctnStrtDt = $this.edctnStrtDt;
                    var edctnEndDt = $this.edctnEndDt;

                    var accsStrtYear = $this.accsStrtYear;
                    var accsEndYear = $this.accsEndYear;
                    var edctnStrtYear = $this.edctnStrtYear;
                    var edctnEndYear = $this.edctnEndYear;
                    var accsStatusOrder = $this.accsStatusOrder;

                    var episdYear = $this.episdYear;

                    copyEpisdSample.find(".episdOrd").text(episdOrd+"회차");
                    copyEpisdSample.find(".episdAccsDt").text(accsStrtDt + " ~ " + accsEndDt);
                    copyEpisdSample.find(".episdAccsDt").attr("data-start-year", accsStrtYear);
                    copyEpisdSample.find(".episdAccsDt").attr("data-end-year", accsEndYear);

                    if(episdYear != accsStrtYear && episdYear != accsEndYear){
                        copyEpisdSample.find(".episdAccsDt").closest("div").addClass("no-this-year");
                    }

                    copyEpisdSample.find(".episdEduDt").text(edctnStrtDt + " ~ " + edctnEndDt);
                    if(episdYear != edctnStrtYear && episdYear != edctnEndYear){
                        copyEpisdSample.find(".episdEduDt").closest("div").addClass("no-this-year");
                    }

                    $(".eduYear").text(episdYear+"년");
                    $(".edu-plan-area").find(".listNotEmpty").find(".round-list").append(copyEpisdSample);

                    $(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").append(copyEpisdSample2);

                });

                //과정에 대한 차수 반복
                $(".edu-plan-area").find(".round-list").find(".copyEpisdSample").css("display","");

                schedulePopupFn();

            }else{
                $(".edu-info-div").css("display", "none");
                $(".listNotEmpty").css("display", "none");
                $(".listEmpty").css("display", "");
                $(".total-edu-area").addClass("no-data");



            }

        }, "/education/apply/detailLayer", searchForm, "text");
    }

    var selectCtgryCdListLayer = function(arg){

        var cdMst= {};
        cdMst.cd = arg;


        cmmCtrl.jsonAjax(function(data){
            callbackAjaxCtgryCdListLayer(data);

            //데이터가 없으면 2뎁스 사용 불가처리 함
            if(arg == "" || arg === undefined){
                $("form[name='frmEduTotCalSearch']").find("#ctgryCd").attr("readonly", true).attr("disabled", true);
            }else{
                $("form[name='frmEduTotCalSearch']").find("#ctgryCd").attr("readonly", false).attr("disabled", false);
            }

        }, '/education/classTypeList', cdMst, "text");
    }

    //과정분류 2뎁스 세팅
    var callbackAjaxCtgryCdListLayer = function(data){

        var detailList = JSON.parse(data);

        $("form[name='frmEduTotCalSearch']").find("#ctgryCd").empty();

        var temp ="";

        var detailLength = detailList.length;

        if(detailLength > 0){


            for(var i =0; i < detailList.length; i++){

                var cd = detailList[i].cd;
                var cdNm = detailList[i].cdNm;


                var addClass =  (i==0)? "swiper-slide active txt-tab-btn searchLayer" : "swiper-slide txt-tab-btn searchLayer";
                temp = temp+"<a class='"+addClass+"' data-ctgrycd="+cd+" href='javascript:'><p class='txt'><span class='menu-name'>"+cdNm+"</span></p></a>";

            }
            $("form[name='frmEduTotCalSearch']").find("#ctgryCd").append(temp);

            $("form[name='frmEduTotCalSearch']").find("#ctgryCd").find("a:first").trigger("click");

            txtTabSwiperCreate();

            $(".txt-major-tab-swiper .txt-tab-btn").off().on("click", function(){
                $(this).addClass("active").siblings().removeClass("active");
            });
            $(".txt-tab-swiper.func-tab .txt-tab-btn").off().on("click", function(){
                $(this).addClass("active").siblings().removeClass("active");

                if($(this).parents(".pop-con-area").size() > 0) {
                    $(this).parents(".pop-con-area").find(".con-area").scrollTop(0); // 탭 클릭시, 맨 상단 스크롤 이동
                    $(this).parents(".pop-con-area").find(".tab-con").hide().eq($(this).index()).show();
                } else {
                    $(this).parents(".tab-con-w").find(".tab-con").hide().eq($(this).index()).show();
                    // 탭 클릭 시, 맨 처음 탭에 active 가도록 초기화
                    if($(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper").size() > 0 && !$(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper .swiper-slide:first-child").hasClass("active")) {
                        $(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper .swiper-slide:first-child").addClass("active").siblings().removeClass("active");;

                        let depthTabSwiper = tabmenuSwipers[$(this).index()];
                        if (depthTabSwiper) {
                            depthTabSwiper.slideTo(0, 0);
                        }
                    }

                }
            });


            $(".edu-info-div").css("display", "");
            $(".listNotEmpty").css("display", "");
            $(".listEmpty").css("display", "none");
            $(".total-edu-area").removeClass("no-data");


            searchLayer(detailList[0].cd);


        }else{
            $(".edu-info-div").css("display", "none");

            $(".listNotEmpty").css("display", "none");
            $(".listEmpty").css("display", "");
            $(".total-edu-area").addClass("no-data");
        }
        //데이터 있음

        //데이터 없음

        $(".searchLayer").click(function(e){

            var ctgryCd = $(this).data("ctgrycd");
            searchLayer(ctgryCd);
        });
    }
    function eduSchedule(e){
        openPopup('allTrainingSchedulePopup', e);

        classTypeLayer('CLASS01');

    }

    //과정 대분류-중분류 호출
    function classTypeLayer(e){
        selectCtgryCdListLayer(e);
    }

    //좌측하단 차수목록 호출
    function edctnSearch(edctnSeq){

        console.log("@@@AA");
        //전체 팝업일정에서 과정명 클릭할때 실행

        searchEpisdLayer(edctnSeq);
    }






</script>


<form class="form-horizontal" name="frmEduTotCalSearch" method="post" action="" data-del-type="none">
    <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
    <!-- 페이징 버튼 사이즈 -->
    <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
    <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" id="episdSeq" name="episdSeq" value="" />

    <c:set var="today" value="<%=new java.util.Date()%>" />
    <c:set var="month"><fmt:formatDate value="${today}" pattern="MM" /></c:set>


    <div class="layer-popup full-popup allTrainingSchedulePopup"><!-- full-popup: 화면 꽉 차는 팝업 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-head">전체교육일정</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="tab-con-w">
                                    <div class="tab-btn-area">
                                        <div class="txt-major-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper">
                                                    <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                                        <a class="swiper-slide txt-tab-btn classTypeLayer <c:if test="${status.index eq 0}">active</c:if>" href="javascript:classTypeLayer('${cdList.cd}');" data-prntCd="${cdList.cd}">
                                                            <p class="txt"><span class="menu-name">${cdList.cdNm}</span></p>
                                                        </a>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="txt-tab-swiper func-tab">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper ctgryCdLayer" id="ctgryCd">

                                                </div>
                                            </div>
                                        </div>

                                        <div class="txt-depth-tab-swiper">
                                            <div class="swiper-container">
                                                <div class="swiper-wrapper edctnList">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="total-edu-area">
                                        <div class="edu-info-div" style="display:none;">
                                            <a class="btn-text-icon black-circle edctnNmLayer" href="javascript:"><span>품질을 알라면 개념을 알아야한다</span></a>
                                            <div class="status-info-w">
                                                <p class="box-label stduyMthdLayer"><span>집체교육</span></p>
                                                <p class="box-label stduyDdLayer"><span>nn일(nn시간)</span></p>
                                            </div>
                                        </div>
                                        <div class="edu-plan-area">
                                            <div class="period-area listNotEmpty">
                                                <div class="period-div">
                                                    <p class="period receive f-caption2">접수기간</p>
                                                    <p class="period education f-caption2">교육기간</p>
                                                </div>
                                                <div class="round-list">

                                                </div>
                                            </div>
                                            <div class="month-area listNotEmpty">
                                                <p class="f-head eduYear">2023년</p>
                                                <div class="scroll-div">
                                                    <div class="scroll-box">

                                                        <%
                                                            Date nowDate = new Date();
                                                            pageContext.setAttribute("nowDate", nowDate);//현재 시간
                                                        %>
                                                        <fmt:formatDate pattern="MM.dd" value="${nowDate}" var="nowDate" />
                                                        <div class="month-wrap" data-date="${nowDate}">
                                                            <!-- 2024-02-01 추가 -->
                                                            <div class="month-chk-w">
                                                                <div class="dot"></div>
                                                                <div class="line"></div>
                                                            </div>
                                                            <!-- // 2024-02-01 추가 -->
                                                            <c:forEach var="forMonth" varStatus="status" begin="1" end="12">
                                                                <p class="month <c:if test="${status.index eq month}">now</c:if>"><span class="f-body2">${forMonth}월</span></p>
                                                            </c:forEach>
                                                        </div>

                                                        <div class="period-bar-list">

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="month-area">
                                                <p class="f-head">2023년</p>
                                                <div class="scroll-div">
                                                    <div class="scroll-box">
                                                        <div class="month-wrap">
                                                            <c:forEach var="forMonth" varStatus="status" begin="1" end="12">
                                                                <p class="month <c:if test="${status.index eq month}">now</c:if>"><span class="f-body2">${forMonth}월</span></p>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="period-bar-list">
                                                            <div class="no-data-area">
                                                                <div class="txt-box">
                                                                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="year-plan-btn">
                                            <a class="btn-text-icon download" href="/file/download?fileSeq=${rtnFormDto.ttlEdctnFileSeq}&fileOrd=${rtnFormDto.fileOrd}"><span>연간 일정 다운로드</span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="copyEpisdSample" style="display:none;">
        <div class="round-div">
            <p class="f-body2 episdOrd">1회차</p>
            <div class="round-period-div">
                <div class="round-period">
                    <p class="tit f-caption2">접수</p>
                    <p class="period f-caption2 episdAccsDt">01.01 ~ 01.04</p>
                </div>
                <div class="round-period">
                    <p class="tit f-caption2">교육</p>
                    <p class="period f-caption2 episdEduDt">03.22 ~ 04.30</p>
                </div>
            </div>
        </div>
    </div>

    <div class="copyEpisdSample2" style="display:none;">
        <div class="period-bar-div">
            <p class="period-bar receive">
                <a href="javascript:"><span></span></a>
            </p>
            <p class="period-bar education">
                <span></span>
            </p>
        </div>
    </div>



    <%--<div class="dimd"></div>--%>

</form>





<%--</div>--%>




