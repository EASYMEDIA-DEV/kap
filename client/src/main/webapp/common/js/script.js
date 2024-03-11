var _this_scroll = 0;       // 스크롤 up & down 체크위한 변수
var _isScrollTop;          // scrollTop 변수
var comeBackElement;
let kvTitleChangeMotion;
var _isPlanMove = false; // 전체 교육 일정 해당 월로 위치 한번만 이동하기 위한 변수
var _flowInterval;

var commonScript = (function(){
  return {
    deviceChk : function(){
      // 디바이스 체크
      function isTouchscreen() { // iPad 접속 OS13부터 Macintosh으로 인식
        return (navigator.maxTouchPoints > 0) ? true : false;
      }

      if(/Android/i.test(navigator.userAgent)) {
        if(isTouchscreen()){
          _device = 'android';
        }else{
          _device = 'pc';
        }
      }

      // 브라우저 체크
      var agent = navigator.userAgent.toLowerCase(),
          name = navigator.appName;

      if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1 || agent.indexOf('edg/') > -1) {
        if(isTouchscreen()){
          _device = 'mobileIe';
        }else{
          _device = 'pcIe';
        }
        _browser = 'ie';
        $("html").addClass("ie");
      }
      else if(agent.indexOf('Chrome') > -1 || window.navigator.userAgent.indexOf('CriOS') > -1) { // Chrome
        if(isTouchscreen()){
          _device = 'mobileChrome';
        }else{
          _device = 'pcChrome';
        }
        _browser = 'chrome';
        $("html").addClass("chrome");
      }
      else if(agent.indexOf('safari') > -1) { // Safari
        if(isTouchscreen()){
          _device = 'mobileSafari';
        }else{
          _device = 'pcSafari';
        }
        _browser = 'safari';
        $("html").addClass("safari");
      }
       else if(agent.indexOf('firefox') > -1) { // Firefox
        _browser = 'firefox';
      }

    },
    init: function(){
      if(window.location.href.indexOf("kap.easymedia.kr") > -1 || window.location.href.indexOf('127.0.0.1') > -1 || window.location.href.indexOf("http://eznet1.easymedia.kr") > -1) {
        if(window.location.href.indexOf("kr/styleguide/") == -1){
          if($("header").html() == ''){
            $("header").empty().load("/html/header.html");
            $("footer").empty().load("/html/footer.html", function(){
              gsap.delayedCall(0.2, function() {
                commonScript.headerFooterFn();
                commonScript.formFn();
                commonScript.commonMotion();
                //commonScript.resizeFn();
                //commonScript.scrollFn();
              });
            });
          }
          if($(".only-vertical-view").html() == '') {
            $(".only-vertical-view").empty().load("/html/vertical-view.html");
            showBack();
          }
        }        
      }
    },
    pageInitFn: function(){
      // 서브 페이지 header 나타나게 (pc, mobile)
      if($("#header").length && !$("#wrap").hasClass("main") && $(window).scrollTop() == 0) {
        setTimeout(() => {
          gsap.to($("#header"), 0.7, {top: 0, ease: Power3});
        }, 200);
      }

      // 로그인 페이지 첫 진입 시 모션
      if($(".member-main").length) {
        setTimeout(() => {
          $(".member-main").addClass("init-active");
        }, 10);
      }

      // 서브 페이지 첫 진입 시 모션
      if($(".sub-top-vis-area").length) {
        setTimeout(() => {
          $(".sub-top-vis-area").addClass("init-active");
          gsap.to($(".sub-top-vis-area:not(.apply-page):has(.img-area) .page-tit-area .page-tit .for-move"), {duration: 1, top: 0, delay: .6, ease: Power3.easeOut});          
          gsap.to($(".sub-top-vis-area:not(:has(.img-area)) .page-tit-area .page-tit .for-move"), {duration: 1, top: 0, ease: Power3.easeOut});          
        }, 10);
      }

      // scrollMotion
      scrollMotionTrigger();

      // 마크업에서 보여주기 위해 적어놨습니다.
      schedulePopupFn();

      // 흐르는 텍스트, 이미지 함수
      flowTxtImgFn();
    },
    commonMotion: function() {
      // header check
      if(!$("#wrap").hasClass("main")){
        if($(window).scrollTop() == 0) {
          $("#header").addClass("hide");
        } 
        $("#header").addClass("up-scroll");
      }


      // (공통) 아코디언 Accordion 스크립트
      if($(".accordion-st").size() > 0) { // 처음 진입 시 오픈
        $(".accordion-st .list-item.active .acco-hide-area").css("display", "block");
      }
      $(".accordion-st .acco-click-area").off().on("click", function(e){
        if($(e.target).parents(".form-checkbox").size() == 0){
          if($(this).parents(".list-item").find(".acco-hide-area").length > 0){
            $(this).parents(".list-item").siblings().removeClass("active").find(".acco-hide-area").stop(true, true).slideUp(300);
            if(!$(this).parents(".list-item").hasClass("active")){
              $(this).parents(".list-item").addClass("active").find(".acco-hide-area").stop(true, true).slideDown(300);
            }else{
              $(this).parents(".list-item").removeClass("active").find(".acco-hide-area").stop(true, true).slideUp(300);
            }
          }
        }
      });


      // tab con 클릭
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
      $(".txt-depth-tab-swiper .txt-tab-btn").off().on("click", function(){
        $(this).addClass("active").siblings().removeClass("active");
      });


      // top버튼 탑버튼
      $(".btn-top").off().on("click", function(){
        gsap.to($("html, body"), {duration: 0.8, scrollTop: 0, ease: Power3.easeOut});
      });

      // mobile 퀵메뉴 오픈
      $(".quick-menu .btn-open-menu").off().on("click", function(){
        if(!$(this).parents(".quick-menu").hasClass("opened")){
          $(this).attr("title","퀵 메뉴 닫기");
          $(this).parents(".quick-menu").addClass("opened");
        }else{
          $(this).attr("title","퀵 메뉴 열기");
          $(this).parents(".quick-menu").removeClass("opened");
        }
      });

      // 퀵, top버튼 position 변경
      quickRePosition();
      setTimeout(() => {
        $(".quick-menu").addClass("show");
      }, 200);



      // [교육사업 > 교육신청] 필터 filter 테스트
      $(".filter-open-btn").off().on("click", function(){
        if(window.innerWidth > 1023){
          if(!$(".filter-popup").hasClass("opened")){
            $(this).attr("title", "필터 닫기");
            $(".info-head .form-select").addClass("disabled");
            $(".filter-popup").addClass("opened").stop(true, true).slideDown(300, function(){
              ScrollTrigger.refresh();
            });
            
          }else{
            $(this).attr("title", "필터 열기");
            $(".info-head .form-select").removeClass("disabled");
            $(".filter-popup").removeClass("opened").stop(true, true).slideUp(300, function(){
              ScrollTrigger.refresh();
            });
          }          
        }else{
          $(this).attr("title", "필터 닫기");
          $(".filter-popup").addClass("opened").show();
          $(".info-head .form-select").addClass("disabled");
          gsap.to($(".filter-popup .for-flex .for-center"), 0.6, {top: 0, ease: Power3});
          $("body").addClass("stop-scroll");
          $(".dimd").css("z-index", 101).stop(true, true).fadeIn(300);
        }
      });
      $(".filter-popup .btn-role-close").off().on("click", function(){
        $(".filter-open-btn").attr("title", "필터 열기");
        $(".info-head .form-select").removeClass("disabled");
        
        $(".filter-popup").removeClass("opened");

        if(window.innerWidth > 1023){
          $(".filter-popup").stop(true, true).slideUp(300);
        }else{
          gsap.to($(".filter-popup .for-flex .for-center"), 0.6, {top: "100%", ease: Power3, onComplete: function(){
            $(".filter-popup").hide();
          }});            
          $("body").removeClass("stop-scroll");
          $(".dimd").css("z-index", 50).stop(true, true).fadeOut(300);
        }
      })

      
      // [컨설팅사업 > 교육사업 소개]] 하단 고정 영역 노출 여부 체크 (footer 하단 여백 변경 목적)
      if($(".accepting-fixed-area").is(":visible")){
        $("#wrap").addClass("has-bot-fix-area")
      }

      // [컨설팅사업 > 교육사업 소개] 하단 고정 영역 position 변경
      acceptingRePosition();

      // [컨설팅사업 > 교육사업 소개] 하단 고정 영역 열기
      var innerShowTl;
      $(".accepting-fixed-area .open-click-area").off().on("click", function(){
        $(".accepting-fixed-area").addClass("opened");
        $(".dimd").css("z-index", 101).stop(true, true).fadeIn(400);
        $(".accepting-fixed-area .hide-area").css({"z-index": 1, "background-color": "#fff"});
        gsap.to((".accepting-fixed-area .open-click-area .tit, .accepting-fixed-area .open-click-area .btn-text-icon.plus"), 0.2, {opacity: 0, ease:Power3.easeOut});
        gsap.to((".accepting-fixed-area .hide-area"), 0.5, {scaleX: 1, scaleY: 1, ease:Power3.easeOut});
        innerShowTl = gsap.to((".accepting-fixed-area .hide-area .inner-con"), 0.5, {opacity: 1, ease:Power3.easeOut, delay: 0.5});
      });
      // [컨설팅사업 > 교육사업 소개] 하단 고정 영역 닫기
      $(".accepting-fixed-area .hide-area .btn-role-close").off().on("click", function(){
        $(".accepting-fixed-area").removeClass("opened");
        $(".dimd").stop(true, true).fadeOut(400, function(){
          $(".dimd").css("z-index", 50);
        });
        gsap.to((".accepting-fixed-area .open-click-area .tit, .accepting-fixed-area .open-click-area .btn-text-icon.plus"), 0.3, {opacity: 1, ease:Power3.easeOut, delay: 0.5});

        let xScale, yScale;
        if(window.innerWidth > 1023){
          xScale = 0.6498;
          yScale = (window.innerWidth * 0.00052 * 72) / $(".accepting-fixed-area .hide-area").outerHeight();
        }else{
          xScale = 1;
          yScale = (window.innerWidth * 0.00256 * 30) / $(".accepting-fixed-area .hide-area").outerHeight();
        }
        gsap.to((".accepting-fixed-area .hide-area"), .5, {scaleX: xScale, scaleY: yScale, ease:Power3.easeOut, onComplete: function(){
          $(".accepting-fixed-area .hide-area").css({"z-index": 0, "background-color": "transparent"});
        }});
        innerShowTl.kill();
        gsap.to((".accepting-fixed-area .hide-area .inner-con"), 0.1, {opacity: 0, ease:Power3.easeOut});
      });


      // [재단정보 > 재단소개 > 연혁], history
      if($(".year-wrap").size() > 0){
        let isHistoryScroll = true;
        let historyHeaderHeight = $("#header").height();
        let yearValue;
        
        // 스크롤 시, 해당 연도 on
        $(".history-area .history-wrap .history-item").each((idx, item) => {
          gsap.to(item, {
            scrollTrigger: {
              trigger: item,
              start: () => `top ${$("#header").height() + 24}`,
              end: 'bottom top',
              // markers: true,
              onEnter: () => {
                yearValue = $(item).data("year");
                if(yearValue && isHistoryScroll) {
                  $(`.history-area .year-wrap .year-box .year-btn[data-year="${yearValue}"]`).siblings().removeClass("on");
                  $(`.history-area .year-wrap .year-box .year-btn[data-year="${yearValue}"]`).addClass("on");
                }
              },
              onLeaveBack: () => {
                yearValue = $(item).data("year");
                if(yearValue && isHistoryScroll) {
                  $(`.history-area .year-wrap .year-box .year-btn[data-year="${yearValue}"]`).prev().addClass("on");
                  
                  if(!(yearValue === 'currentYear')) {
                    $(`.history-area .year-wrap .year-box .year-btn[data-year="${yearValue}"]`).removeClass("on");
                  }
                }
              }
            },
          });
        });

        // 연도 fixed
        let $historyArea = $(".history-area");
        let hisTop = $historyArea.offset().top;
        let hisOffset = $historyArea.offset().top + $historyArea.innerHeight();
        let scrollFixed = $(window).scrollTop() + $(".year-box").innerHeight() + historyHeaderHeight;

        const historyScrollFixedFunc = () => {
          if($(window).scrollTop() > hisTop - historyHeaderHeight - 20) {
            $(".year-box").addClass("fixed");
            $(".year-box").css("top", historyHeaderHeight + 20);
          } else {
            $(".year-box").removeClass("fixed");
            $(".year-box").css("top", 0);
          }
          
          if(hisOffset - 20 < scrollFixed) {
            $(".year-box").addClass("fixed-end");
            $(".year-box").css("top", "unset");
          } else {
            $(".year-box").removeClass("fixed-end");
          }
        }

        historyScrollFixedFunc();

        $(window).scroll(function(){
          hisTop = $historyArea.offset().top;
          hisOffset = $historyArea.offset().top + $historyArea.innerHeight();
          scrollFixed = $(window).scrollTop() + $(".year-box").innerHeight() + historyHeaderHeight;
            
          historyScrollFixedFunc()
        });

        // 연도 클릭 앵커 이동
        $(".history-area .year-wrap .year-box .year-btn").on("click", function() {
          isHistoryScroll = false;
          yearValue = $(this).data("year");
          historyHeaderHeight = $("#header").height();

          $(this).siblings().removeClass("on");
          $(this).addClass("on");
          $("html, body").stop().animate({
            scrollTop : $(`.history-area .history-wrap .history-item[data-year="${yearValue}"]`).offset().top - historyHeaderHeight - 20
          }, {
            duration: 1000
          });

          setTimeout(() => {
            isHistoryScroll = true;
          }, 1000)
          // clearTimeout(historyScroll);
        });
      }


      // [교육사업] 교육신청 anchor click
      $(".anchor-btn-w .anchor-btn").each(function(q){
        $(this).off().on("click", function(){
          var nowIndex = $(".anchor-btn-w .anchor-btn.active").index()
          var targetIndex = $(this).index();

          if(targetIndex < nowIndex){
            TweenMax.to($("html, body"), 0.7, {scrollTop:$(".anchor-con").eq(q).offset().top - $(".anchor-btn-w").height() - (parseInt($(".anchor-con").eq(q).css("margin-top")) / 2) - $("header").height(), ease:Power3.easeOut});
          }else{
            TweenMax.to($("html, body"), 0.7, {scrollTop:$(".anchor-con").eq(q).offset().top - $(".anchor-btn-w").height() - (parseInt($(".anchor-con").eq(q).css("margin-top")) / 2), ease:Power3.easeOut});
          }

          $(".anchor-btn-w .anchor-btn").removeClass("active").eq($(this).index()).addClass("active");
        });
      });

      // [교육사업] 전체일정팝업 뜰 때
      // $(".quick-menu .edu-schedule").off().on("click", function(){
      //   $(".allTrainingSchedulePopup").addClass("open");
      //   schedulePopupFn();
      // })
    },
    formFn: function(){
      // 툴팁
      const tooltipPosCalcu = function(tool) {
        $(tool).css("left", `50%`)

        let cont = $(".cont-sec-w");
        let contW = cont.outerWidth();
        let toolW = tool.outerWidth();
        let contL = cont.offset().left;
        let toolL = tool.offset().left;
        let contR = contL + contW;
        let toolR = toolL + toolW;

        if(contL > toolL) {
          $(tool).css("left", `calc(50% + ${contL - toolL}px)`)
        } else if(contR < toolR) {
          $(tool).css("left", `calc(50% - ${toolR - contR}px)`)
        }
      }
      const tooltipPosMobCalcu = function(tool) {
        $(tool).css("left", 0)

        let cont = $(".cont-sec-w");
        let contW = cont.outerWidth();
        let contL = cont.offset().left;
        let toolL = tool.offset().left;

        $(tool).css({width: contW, left: `${contL - toolL}px`});
      }
      const tooltipTxtChk = function(tool) {
        $(tool).css({width: "auto", "white-space": "nowrap"});

        if($(tool).width() + 1 < $(tool).find(".txt").width()) {
          $(tool).css({width: "100vw", "white-space": "unset"});
        }
        
      }

      let tool;
      $(".tooltip-wrap .tooltip-btn").off().on("click", function(){
        $(this).parents(".tooltip-wrap").addClass("open");
        $(this).parents(".tooltip-wrap").find(".tooltip-box").stop(true, true).fadeIn(200);
        tool = $($(this).parents(".tooltip-wrap").find(".tooltip-box"));
        
        if (window.innerWidth >= 1024) {
          setTimeout(() => {
            let toolMaxWRemCalcu = parseFloat($('html').css('font-size')) * 430;
  
            if($(tool).find(".txt").width() < toolMaxWRemCalcu) {
              $(tool).css({width: "auto", "white-space": "nowrap"});
              tooltipPosCalcu(tool);
            } else {
              tooltipPosCalcu(tool);
            }
          }, 1)
        } else {
          setTimeout(() => {
            tooltipPosMobCalcu(tool);
          }, 1);
        }
      });
      $(".tooltip-wrap .tooltip-box .btn-close").off().on("click", function(){
        $(this).parents(".tooltip-wrap").removeClass("open");
        $(this).parents(".tooltip-box").stop(true, true).fadeOut(200, () => {
          $(this).parents(".tooltip-box").css("left", '');
        });
        
      });

      $(window).resize(function () {
        if (window.innerWidth >= 1024) {
          if($(".tooltip-box:visible").size() >= 1) {
            $(".tooltip-box:visible").each((idx, item) => {
              tooltipPosCalcu($(item));
              tooltipTxtChk($(item));
            })
          }
        } else {
          if($(".tooltip-box:visible").size() >= 1) {
            $(".tooltip-box:visible").each((idx, item) => {
              tooltipPosMobCalcu($(item));
            })
          }
        }
      })

      // 약관 항목 체크 시
      $("input[type='checkbox']").off().change(function(){
        if($(this).parents(".agree-list-w").size() > 0){
          if($(".agree-terms-w .agree-list-w .list-item .acco-click-area input[type='checkbox']:checked").size() == $(".agree-terms-w .agree-list-w .list-item").size()){
            $(this).parents(".agree-terms-w").find(".all-check-area input[type='checkbox']").prop('checked', true);
          }else{
            $(this).parents(".agree-terms-w").find(".all-check-area input[type='checkbox']").prop('checked', false);
          }
        }

        // 전체 약관 동의 체크 시
        if($(this).parents(".all-check-area").size() > 0){
          if($(this).is(":checked")){
            $(this).parents(".agree-terms-w").find(".agree-list-w input[type='checkbox']").prop('checked', true);
          }else{
            $(this).parents(".agree-terms-w").find(".agree-list-w input[type='checkbox']").prop('checked', false);
          }
        }

        // 마케팅 정보 수신동의 체크 시
        if($(this).parents(".marketing").size() > 0){
          if($(this).parents(".included-chk-area").size() > 0){
            if($(this).parents(".list-item.marketing").find(".included-chk-area input[type='checkbox']:checked").size() > 0){
              $(this).parents(".list-item.marketing").find(".acco-click-area input[type='checkbox']").prop('checked', true);
            }else{
              $(this).parents(".list-item.marketing").find(".acco-click-area input[type='checkbox']").prop('checked', false);
              $(this).parents(".agree-terms-w").find(".all-check-area input[type='checkbox']").prop('checked', false);
            }
          }else{
            if($(this).is(":checked")){
              $(this).parents(".list-item.marketing").find(".included-chk-area input[type='checkbox']").prop('checked', true);
            }else{
              $(this).parents(".list-item.marketing").find(".included-chk-area input[type='checkbox']").prop('checked', false);
            }
          }
        }
      });

      // 첫 진입 시 검색 input 텍스트 입력되어 있으면 삭제 버튼 노출
      $(".srch-input").each(function(index, item){
        if(!$(item).find("input[type='text']").is('[readonly]') && $(item).find("input[type='text']").val() !== ""){
          $(item).find("input[type='text']").siblings(".input-btn-wrap").find(".delete-btn").show();
        }
      })

      // 검색 input 텍스트 입력 시 삭제 버튼 노출
      $(".srch-input input[type='text']").on("keyup", function() {
        if(!$(this).is('[readonly]')){
          if($(this).val() !== "") {
            $(this).siblings(".input-btn-wrap").find(".delete-btn").show();
          } else {
            $(this).siblings(".input-btn-wrap").find(".delete-btn").hide();
          }
        }
      });

      // 검색 input 삭제 버튼 클릭 시
      $(".input-btn-wrap .delete-btn").on("click", function(){
        $(this).hide();
        $(this).parents(".input-btn-wrap").siblings("input").val("").focus();
      });


      // form select - 화살표 모션
      $(".form-select select").off().focus(function(){
        $(this).parents(".form-select").addClass("active");
      });

      $(".form-select select").blur(function(){
        $(this).data('isopen', false);
        $(this).parents(".form-select").removeClass("active");
      });

      $(".form-select select").on("change", function(){
        $(this).parents(".form-select").removeClass("active");
      });
        
      $(".form-select select").on("mouseup", function(e) {
        let open = $(this).data("isopen");

        if(open) {
          $(this).parents(".form-select").removeClass("active");
        } else {
          $(this).parents(".form-select").addClass("active");
        }
        
        $(this).data("isopen", !open);
      });

      // 기타 radio 클릭 시 input 활성화
      $("input[type='radio']").off().change(function(){
        if($(this).parents(".etc-option-w").size() > 0){
          if($(this).prop('checked') == true){
            $(this).parents(".etc-option-w").find("input[type='text']").removeAttr('disabled').focus();
          }
        }else{
          $(this).parents(".data-line-w").find(".etc-option-w input[type='text']").attr("disabled", true).val('');
        }
      });

      // 뉴스레터 수신 동의 체크박스
      $(".newsletter-agree input[type='checkbox']").off().change(function(){
        $(this).removeClass("agree");
      });

      // 전체 체크박스
      $(".total-check-w").each(function(){
        $(this).find(".form-checkbox").each(function(){
          $(this).find("input[type='checkbox']").off().on("click", function(){
            if($(this).parent(".form-checkbox").hasClass("total-check")){
              if($(this).is(":checked")){
                $(this).parents(".total-check-w").find(".form-checkbox input[type='checkbox']").prop("checked", true);
              }else{
                $(this).parents(".total-check-w").find(".form-checkbox input[type='checkbox']").prop("checked", false);
              }
            }else{
              if($(this).parents(".total-check-w").find(".form-checkbox").not(".total-check").find("input[type='checkbox']:checked").length == $(this).parents(".total-check-w").find(".form-checkbox input[type='checkbox']").length - 1){
                $(this).parents(".total-check-w").find(".total-check input[type='checkbox']").prop("checked", true);
              }else{
                $(this).parents(".total-check-w").find(".total-check input[type='checkbox']").prop("checked", false)
              }
            }
          })
        })
      });
    },
    headerFooterFn: function(){
      if($("#header").size() != 0){
        let gnbNum = -1;
        let headerHeight;
        let gnbHeight;
        let isFindHeight = false;

        // 헤더 - 호버시
        $("#header nav .gnb").off().on("mouseenter focusin", function(){
          if(!$("#header").hasClass("srch-open")) {
            $(".dimd").stop(true, true).fadeIn(400);
            $("#header").addClass("mouse-hover overflow-hidden");
          }
        });
  
        $("#header nav .gnb").off("mouseleave").on("mouseleave", function(){
          if(!$("#header").hasClass("srch-open")) {
            $("#header").removeClass("mouse-hover overflow-hidden");
            $("#header nav .gnb > li").removeClass("on");
            $("#header nav .gnb > li .two-pack-wrap").css("display","none");
            $("#header nav .gnb > li .two-pack-wrap .two-pack > li").removeClass("on");
            $(".dimd").stop(true, true).fadeOut(400);
            $("#header").css("height", "100rem");
            gnbNum = -1;
            isFindHeight = false;
          } else {
            $("#header nav .gnb > li").removeClass("on");
          }
        });
  
        $("#header nav .gnb > li").off().on("mouseenter focusin", function(){
          if(!$("#header").hasClass("srch-open")) {
            if(!isFindHeight) {
              headerHeight = $("#header").height();
              isFindHeight = true;
            }
            
            if(gnbNum != $(this).index()){
              $("#header nav .gnb > li .two-pack-wrap").stop(true, true).fadeOut(100);
              $("#header nav .gnb > li .two-pack-wrap .two-pack > li").removeClass("on");
              $(this).find(".two-pack-wrap").stop(true, true).fadeIn(400);
              $(this).siblings().removeClass("on");
              $(this).addClass("on");

              gnbHeight = $(this).find(".two-pack-wrap").innerHeight();
              $("#header").css("height", gnbHeight + headerHeight);
            }
  
            gnbNum = $(this).index();
          }
        });
  
        $("#header nav .gnb > li .two-pack-wrap .two-pack > li").off().on("mouseenter focusin", function(){
          $(this).siblings().removeClass("on");
          $(this).addClass("on");
        });

        $("#header nav .gnb > li:last .two-pack > li:last .three-pack > li:last a").off().on("focusout", function(){
          $("#header").removeClass("mouse-hover overflow-hidden");
          $("#header nav .gnb > li").removeClass("on");
          $("#header nav .gnb > li .two-pack-wrap").css("display","none");
          $("#header nav .gnb > li .two-pack-wrap .two-pack > li").removeClass("on");
          $(".dimd").stop(true, true).fadeOut(400);
          $("#header").css("height", "100rem");
          
          gnbNum = -1;
          isFindHeight = false;
        });

        // 유틸 - Hover 팝업
        $("#header .util-area .pop-wrap").off().on("mouseenter focusin", function(){
          if(!$("#header").hasClass("srch-open")) {
            $(this).find(".pop-box").stop(true, true).fadeIn(200);
          }
        });
        $("#header .util-area .pop-wrap").off("mouseleave").on("mouseleave", function(){
          if(!$("#header").hasClass("srch-open")) {
            $(this).find(".pop-box").stop(true, true).fadeOut(200);
          }
        });

        // 유틸 - Hover 팝업, 탭이동 포커스 조정
        $("#header .util-area .pop-wrap .pop-box").each((idx, item) => {
          $($(item).find("a:last")).off().on("keydown", function(e) {
            if ((e.keyCode || e.which) == 9 && !e.shiftKey) {
              $(item).stop(true, true).fadeOut(200);
            }
          });
        })
        $("#header .util-area .pop-wrap .icon-btn").each((idx, item) => {
          $(item).off().on("keydown", function(e) {
            if ((e.keyCode || e.which) == 9 && e.shiftKey) {
              $(item).siblings(".pop-box").stop(true, true).fadeOut(200);
            }
          });
        })

        // 유틸 - 전체메뉴
        let isAllMenuOpen = false;
        let logoTimeFade;
        let rollingInterval;

            // 유틸 - 전체메뉴, (mobile) 공지사항 알림 rolling
        function noticeRollingText(){
          if($(".notice-rolling li").length >= 3) {
            $(".notice-rolling li:nth-child(1)").addClass("current");
            $(".notice-rolling li:nth-child(2)").addClass("next");
            $(".notice-rolling li:nth-child(3)").addClass("prev");

            rollingInterval = window.setInterval(() => {
              $(".notice-rolling .prev").removeClass("prev");

              let current = $(".notice-rolling .current");
              current.removeClass("current");
              current.addClass("prev");

              let next = $(".notice-rolling .next");
              if (next.next().length === 0) {
                $(".notice-rolling ul li:first-child").addClass("next");
              }else{
                next.next().addClass("next");
              }
              next.removeClass("next");
              next.addClass("current");
            }, 3000);
          } else if($(".notice-rolling li").length == 2) {
            $(".notice-rolling li:nth-child(1)").addClass("current");
            $(".notice-rolling li:nth-child(2)").addClass("next");
            
            rollingInterval = window.setInterval(() => {

              let current = $(".notice-rolling .current");
              current.removeClass("current");
              current.addClass("prev");
              
              let next = $(".notice-rolling .next");
              next.removeClass("next");
              next.addClass("current");

              setTimeout(() => {
                let prev = $(".notice-rolling .prev");
                prev.removeClass("prev");
                prev.addClass("next");
              }, 1000); 
            }, 3000);
          }
        }
        
        $("#header .util-area .menu-btn").off().on("click", function(){
          $("#header .util-area .menu-btn").removeClass("init");
          if(!$("#header").hasClass("menu-open")) {
            $("#header .all-menu").show(0, function() {
              $("#header").addClass("menu-open");

              $("#header .all-menu").addClass("scroll");
              $("body").addClass("stop-scroll");
              $("#header .util-area .menu-btn").addClass("active");
              $("#header .util-area .menu-btn > span").text("닫기");
              isAllMenuOpen = true;

              if(!$("#wrap").hasClass("main") && $(window).width() < 1024) {
                logoTimeFade = setTimeout(() => {
                  $("#header h1").stop(true, true).fadeIn(400);
                }, 800)
              }

              // 롤링 텍스트 시작
              noticeRollingText();
            });
          } else {
            $("#header .all-menu").fadeOut(200, function() {
              $("#header").removeClass("menu-open");

              // 롤링 텍스트 정지
              clearInterval(rollingInterval);
              // 롤링 텍스트 정지 시, 초기화
              $(".notice-rolling li").removeClass("current next prev");
            });
            
            $("#header .util-area .menu-btn").removeClass("active");
            $("#header .util-area .menu-btn > span").text("메뉴");
            isAllMenuOpen = false;

            if($(window).width() >= 1024) {
              $("#header .all-menu").removeClass("scroll");
              $("body").removeClass("stop-scroll");

            } else {
              if(!$("#wrap").hasClass("main") && !$("#header").hasClass("srch-open")) {
                clearTimeout(logoTimeFade);
                $("#header h1").stop(true, true).fadeOut(200);
              }
              if(!$("#header").hasClass("srch-open")) {
                $("#header .all-menu").removeClass("scroll");
                $("body").removeClass("stop-scroll");
              }
            }
          }
        });
        $("#header .all-menu .gnb .two-pack > li").off().on("mouseenter focusin", function() {
          $(this).siblings().removeClass("on");
          $(this).addClass("on")
        });
        $("#header .all-menu .gnb > li").off().on("mouseenter focusin", function() {
          if(gnbNum != $(this).index()) {
            $("#header .all-menu .two-pack > li").removeClass("on");
          }
          gnbNum = $(this).index();
        })
        $("#header .all-menu .gnb .two-pack > li").off("mouseleave").on("mouseleave", function(){
          $("#header .all-menu .gnb .two-pack > li").removeClass("on");
        });

        // 유틸 - 전체메뉴, 탭이동 포커스 조정
        let $allMenuLastLi;

        if($(".non-member-apply").size() > 0) { // non-member-apply 있으면
          $allMenuLastLi = $(".non-member-apply");
        } else { // non-member-apply 없으면
          $allMenuLastLi = $($("#header .all-menu .gnb").find(".three-pack > li a").last());
        }

        $($allMenuLastLi).focus().on("keydown", function(e) {
          if ((e.keyCode || e.which) == 9 && !e.shiftKey) {
            e.preventDefault();
            $("#header .util-area .menu-btn").focus();
          }
        });
        $("#header .util-area .menu-btn").on("keydown", function(e) {
          if (isAllMenuOpen && (e.keyCode || e.which) == 9 && e.shiftKey) {
            e.preventDefault();
            $($allMenuLastLi).focus();
          }
        });

        // 유틸 - 전체메뉴, (mobile) 메뉴 아코디언
        $("#header .all-menu .gnb .one-depth").on("click", function() {
          if($(window).width() < 1024) {
            if(!$(this).parents(".gnb > li").hasClass("active")) {
              $(this).parents(".gnb > li").find(".two-pack").stop(true, true).slideDown(400);
              $(this).parents(".gnb > li").addClass("active");
              $(this).parents(".gnb > li").siblings().find(".two-pack").stop(true, true).slideUp(400);
              $(this).parents(".gnb > li").siblings().removeClass("active");
            } else {
              $(this).parents(".gnb > li").find(".two-pack").stop(true, true).slideUp(400);
              $(this).parents(".gnb > li").removeClass("active");
            }
          }
        });


        // 유틸 - 전체검색
        let srchTimeSlide;
        $("#header .util-area .search-btn").on("click", function(){
          if($(window).width() >= 1024) {
            if(!$("#header").hasClass("srch-open")) {
              $("#header").addClass("srch-open");
              srchTimeSlide = setTimeout(() => {
                $("#header .all-srch").stop(true, true).slideDown(400);
              }, 100);
              $(".dimd").stop(true, true).fadeIn(400);
              $("#header .util-area .search-btn > span").text("닫기");
            } else {
              clearTimeout(srchTimeSlide);
              $("#header .all-srch").stop(true, true).slideUp(300, function() {
                $("#header").removeClass("srch-open");
                $(".dimd").stop(true, true).fadeOut(400);
                $("#header .util-area .search-btn > span").text("검색");
                if($(window).scrollTop() > 100){
                  $("#header").removeClass("hide");
                  $("#header").addClass("up-scroll");
                }
              });
            }
          } else {
            if(!$("#header").hasClass("srch-open")) {
              $("#header").addClass("srch-open");
              $("#header .all-srch").stop(true, true).fadeIn(400);
              $("#header .util-area .search-btn > span").text("닫기");
              $("body").addClass("stop-scroll");
              if(!$("#wrap").hasClass("main")) {
                $("#header h1").stop(true, true).fadeIn(400);
              }
            } else {
              $("#header").removeClass("srch-open");
              $("body").removeClass("stop-scroll");
              $("#header .all-srch").stop(true, true).fadeOut(400, function() {
                $("#header .util-area .search-btn > span").text("검색");
              });
              if(!$("#wrap").hasClass("main")) {
                $("#header h1").stop(true, true).fadeOut(400);
              }
            }           
          }
        });
        // 유틸 - 전체메뉴, 탭이동 포커스 조정
        const $allSrchLastLi =  $("#header .all-srch .top-srchs > li:last a");
        
        $($allSrchLastLi).focus().on("keydown", function(e) {
          if ((e.keyCode || e.which) == 9 && !e.shiftKey) {
            e.preventDefault();
            $("#header .util-area .search-btn").focus();
          }
        });
        $("#header .all-srch input").focus().on("keydown", function(e) {
          if ((e.keyCode || e.which) == 9 && e.shiftKey) {
            e.preventDefault();
            $("#header .util-area .search-btn").focus();
          }
        });
        $("#header .util-area .search-btn").on("keydown", function(e) {
          if ($("#header").hasClass("srch-open") && (e.keyCode || e.which) == 9 && !e.shiftKey) {
            e.preventDefault();
            $("#header .all-srch input").focus();
          } else if($("#header").hasClass("srch-open") && (e.keyCode || e.which) == 9 && e.shiftKey) {
            e.preventDefault();
            $($allSrchLastLi).focus();
          }
        });

        // 유틸 - resize 적용
        $(window).resize(function () {
          if (window.innerWidth >= 1024) {
            // 유틸 - 전체 메뉴
            $("#header .all-menu .gnb .two-pack").removeAttr("style");
            $("#header .all-menu .gnb > li").removeClass("active");

            // 유틸 - 전체 검색, 전체 메뉴 스크롤/딤드
            if($("#header").hasClass("srch-open") && $("#header").hasClass("menu-open")) {
              $(".dimd").stop(true, true).show();
              $("body").addClass("stop-scroll");
            } else if($("#header").hasClass("srch-open")) {
              $(".dimd").stop(true, true).show();
              $("body").removeClass("stop-scroll");
            }
          } else {
            // 유틸 - 전체 검색, 전체 메뉴 스크롤/딤드/로고
            if($("#header").hasClass("srch-open") || $("#header").hasClass("menu-open")) {
              $(".dimd").stop(true, true).hide();
              $("body").addClass("stop-scroll");
              $("#header h1").css("display", "block");
            }
          }
        })
      }
    },
    lnbFn: function(){
      var _lnbHeight = $(".lnb-area").height() - parseInt($(".lnb-area .for-motion").css("margin-top")) + 100;

      // load 시 lnb 해당 아코디언 열려있게
      if($(".btn-two-depth.active").length && window.innerWidth > 1023){
        if(!$(".btn-two-depth.active").hasClass("single-menu")){
          $(".btn-two-depth.active").parents(".lnb-list").find(".three-depth-wrap").slideDown(0, function(){
            setTimeout(() => {
              _lnbHeight = $(".lnb-area").outerHeight(true);
              ScrollTrigger.refresh();
            }, 10);
          });
        }
      }


      // lnb title 이동 및 색 변화
      if($(".lnb-area").size() > 0){
        ScrollTrigger.matchMedia({
          "(min-width: 1024px)": function() {
            gsap.to($(".lnb-area"), {
              scrollTrigger: {
                trigger: $(".divide-con-area"),
                // start: "top 70%",
                start: () => "top 85%",
                endTrigger: $(".divide-con-area .right-con-area"),
                end: () => "bottom-=100rem " + _lnbHeight,
                onEnter: function(){
                  gsap.to((".lnb-area .for-motion"), 1, {marginTop:0, opacity: 1, ease:Power3.easeOut});
                },
                invalidateOnRefresh: true,
                scrub: true,
                // markers:true,
              },
            });
          },
        })
      }

      // lnb 스크롤 시 고정
      if($(".lnb-area").size() > 0){
        ScrollTrigger.matchMedia({
          "(min-width: 1024px)": function() {
            gsap.to($(".divide-con-area .right-con-area .cont-sec-w"), {
              scrollTrigger: {
                trigger: $(".lnb-area"),
                start: ()=> "top " + ($("header").height() + 10),
                endTrigger: $(".divide-con-area .right-con-area"),
                end: () => "bottom " + _lnbHeight,
                pin: true,
                pinSpacing: false,
                invalidateOnRefresh: true,
                scrub: true,
                // markers:true,
              },
            });
          },
        })
      }

      // lnb 아코디언 클릭
      $(".lnb-area .btn-two-depth").on("click", function(){
        if(window.innerWidth > 1023){
          if(!$(this).hasClass("single-menu")){
            if(!$(this).hasClass("active")){
              $(this).addClass("active");
              $(this).parents(".lnb-list").find(".three-depth-wrap").stop(true, true).slideDown(300, function(){
                calLnbHeight();
              });
              $(this).parents(".lnb-list").siblings().find(".three-depth-wrap").stop(true, true).slideUp(300);
              $(this).parents(".lnb-list").siblings().find(".btn-two-depth").removeClass("active");
            }else{
              $(this).removeClass("active");
              $(this).parents(".lnb-list").find(".three-depth-wrap").stop(true, true).slideUp(300, function(){
                calLnbHeight();
              });
            }
          }
        }
      });

      function calLnbHeight(){
        _lnbHeight = 0;
        $(".lnb-list").each(function(q){
          _lnbHeight = _lnbHeight + $(".lnb-list").eq(q).outerHeight(true);
        });
        _lnbHeight = _lnbHeight + 140 + "rem";
        ScrollTrigger.refresh();
        if(_isScrollTop != $(window).scrollTop()){
          $(window).scrollTop(_isScrollTop)
        }
      }
    },
    scrollFn: function(){
      var forAnchorGap = 0;

      anchorVisibleFn();

      $(window).on("scroll", function(){
        _isScrollTop =  $(window).scrollTop();
        anchorVisibleFn();
        
        // 스크롤 시, 헤더 up & down
        if(_isScrollTop > _this_scroll) { // down
          if($(window).scrollTop() > 100 && !$("#header").hasClass("srch-open")){
            gsap.to(("#header"), 0.7, {top:"-100rem", ease: Power3.easeOut});
            $(".anchor-btn-w").size() && gsap.to((".anchor-btn-w"), 0.7, {top: 0, ease: Power3.easeOut});
            $(".quick-menu").addClass("scroll-down");
          }

          forAnchorGap = 0;
        }
      
        if(_isScrollTop < _this_scroll) { // up
          if(_isScrollTop > 0 && !$("#header").hasClass("srch-open")){
            $("#header").addClass("up-scroll");
            $("#header").removeClass("hide");
            gsap.to(("#header"), 1, {top: 0, ease: Power3.easeOut});
            $(".anchor-btn-w").size() && gsap.to((".anchor-btn-w"), 1, {top: $("header").height(), ease: Power3.easeOut});
          } else{
            if($("#wrap").hasClass("main")){
              $("#header").removeClass("up-scroll");
            } else {
              $("#header").addClass("hide");
            }
          }

          if($(window).scrollTop() <= 100){
            $(".quick-menu").removeClass("scroll-down");
          }

          forAnchorGap = $("header").height();
        }

        // 페이지 스크롤 길이
        setTimeout(function(){
          $(".scroll-gauge .bar").width((_isScrollTop / (($("#wrap").height() - window.innerHeight)) * 100) + "%");
        }, 40)

        // 퀵, top버튼 position 변경
        quickRePosition();

        // 컨설팅사업 > 신청하기 하단 고정 영역 position 변경
        acceptingRePosition();

        // [교육사업] 앵커

        if($(".anchor-con").size() > 0){
          $(".anchor-con").each(function(q){
            if($(window).scrollTop() > $(this).offset().top - $(".anchor-btn-w").height() - forAnchorGap - 2 - (parseInt($(".anchor-con").eq(1).css("margin-top")) / 2)){
              $(".anchor-btn-w .anchor-btn").removeClass("active").eq(q).addClass("active");
            }
          });
        }

        _this_scroll = _isScrollTop;
      });
    },
    scrollTriggerFn: function(){
      // sub page 상단 이미지 모션
      if($(".sub-top-vis-area.basic-page").length){
        // sub 일반 페이지 상단 비주얼 영역 image 모션
        let kvImageMotion = gsap.timeline({
          scrollTrigger: {
            trigger: $(".sub-top-vis-area.basic-page"),
            start: ()=> "top " + $(".sub-top-vis-area.basic-page").offset().top,
            end:"bottom top",
            toggleClass: {targets: $(".sub-top-vis-area.basic-page"), className: "scroll-down"},
            //markers:true,
          },
        });
        

        // sub 신청하기 페이지 상단 비주얼 영역 graphic 모션
        if($(".sub-top-vis-area.apply-page").length){
          let kvApplyMotion = gsap.timeline({
            scrollTrigger: {
              trigger: $(".sub-top-vis-area.apply-page"),
              start: () => "top " + $(".sub-top-vis-area.apply-page").offset().top,
              end:"top+=40% top",
              scrub: 0.1,
              onUpdate: function (self) {
                gsap.to((".sub-top-vis-area.apply-page .img-area .graphic-item-w .item:nth-child(1)"), 0.5, {yPercent: 10 * self.progress});
                gsap.to((".sub-top-vis-area.apply-page .img-area .graphic-item-w .item:nth-child(2)"), 0.5, {yPercent: 10 * self.progress});
              },
              //markers:true,
            },
          });
        }
      }

      ScrollTrigger.matchMedia({
        "(min-width: 1024px)": function() {
          if($(".sub-top-vis-area.basic-page").length){
            gsap.to(".sub-top-vis-area.basic-page .f-xlarge-title", {
              scrollTrigger: {
                trigger: ".sub-top-vis-area.basic-page .f-xlarge-title",
                start: () => "top " + $(".sub-top-vis-area.basic-page").offset().top,
                endTrigger: ".sub-top-vis-area.basic-page .img-area",
                end:"top top",
                pin: true,
                pinSpacing: false,
                //markers:true,
              },
            });
  
            let kvTitleChangeMotion = gsap.timeline({
              scrollTrigger: {
                trigger: $(".sub-top-vis-area.basic-page .page-tit-area"),
                start: () => "top " + $(".sub-top-vis-area.basic-page").offset().top,
                endTrigger: ".sub-top-vis-area.basic-page .img-area",
                //end:"top-=50% top",
                end:"top top",
                scrub: 1,
                //markers:true,
              },
            });
  
            kvTitleChangeMotion.kill();
            kvTitleChangeMotion.to($(".sub-top-vis-area.basic-page .page-tit-area .f-xlarge-title .for-move"), {marginLeft: "97rem", duration:1, ease: "Power3.ease"}, 0)
                                .to($(".sub-top-vis-area.basic-page .page-tit-area .f-xlarge-title"), {color: "#fff", duration:1, ease: "Power3.ease"}, 0)
          }
        },
        "(max-width: 1023px)": function() {
          if($(".sub-top-vis-area.basic-page").length){
            gsap.to(".sub-top-vis-area.basic-page", {
              scrollTrigger: {
                trigger: ".sub-top-vis-area.basic-page .f-xlarge-title",
                start: () => "top " + $(".sub-top-vis-area.basic-page").offset().top,
                endTrigger: ".sub-top-vis-area.basic-page",
                end:"top-=100rem top",
                pin: true,
                pinSpacing: false,
                //markers:true,
              },
            });
  
            let kvTitleChangeMotion = gsap.timeline({
              scrollTrigger: {
                trigger: $(".sub-top-vis-area.basic-page .page-tit-area"),
                start: () => "top-=1px " + $(".sub-top-vis-area.basic-page").offset().top,
                endTrigger: ".sub-top-vis-area.basic-page .img-area",
                //end:"top-=50% top",
                end:"top top",
                scrub: 1,
                //markers:true,
              },
            });
  
            kvTitleChangeMotion.kill();
            kvTitleChangeMotion.to($(".sub-top-vis-area.basic-page .page-tit-area .f-xlarge-title .for-move"), {duration:1, ease: "Power3.ease"}, 0)
                                .to($(".sub-top-vis-area.basic-page .page-tit-area .f-xlarge-title"), {color: "#fff", duration:1, ease: "Power3.ease"}, 0)
          }
        },
      })      
    },
    popupFn: function(){
      let popIdxNum = 200;
      let dimdIdxNum = 200 - 2;
      // 팝업 떠있을 시
      if($(".layer-popup:visible").size() >= 1){
        $("body").addClass("stop-scroll");
        $(".layer-popup:visible").each(function(q, item){
          $(item).css("z-index", popIdxNum + q);
        });
        $(".dimd").css("z-index", (parseInt($(".layer-popup").eq(-1).css("z-index")) - 1)).show(); 
      }
      
      // (공통) 팝업 닫기 버튼
      $(".layer-popup .btn-role-close").off().on("click", function(){
        if(comeBackElement){
          $(comeBackElement).attr("tabindex", 0).show().focus();
        }

        if($(".layer-popup:visible").size() == 1){ // 팝업이 하나만 떠있을 때
          if($(".accepting-fixed-area").hasClass("opened")) { // [컨설팅 사업] 플로팅 여부
            $(".accepting-fixed-area").css("z-index", "");
          } else {
            $(".dimd").css("z-index", 50).stop(true, true).fadeOut(300);
          }
          $("body").removeClass("stop-scroll");
        }else{ // 팝업이 여러개 떠있을 때
          $(".dimd").css("z-index", `${ dimdIdxNum + ($(".layer-popup:visible").length - 1) }`);
        }

        $(this).parents(".layer-popup").css("z-index", "");
        if(window.innerWidth > 1023){
          $(this).parents(".layer-popup").stop(true, true).fadeOut(300, function(){
            $(this).parents(".layer-popup").removeClass("completed");
          });
        }else{
          if($(this).parents(".layer-popup").hasClass("main-popup")){
            $(this).parents(".layer-popup").stop(true, true).fadeOut(300); // 메인팝업만 fadeOut, 나머진 slide 방식
          }else{
            var targetPopup = $(this).parents(".layer-popup");
            gsap.to($(this).parents(".layer-popup").find(".pop-con-area"), 0.6, {top: "100%", ease: Power3, onComplete: function(){
              $(targetPopup).hide().removeClass("completed");
            }});            
          }
        }

        if($(this).parents(".layer-popup").hasClass("full-popup")){
          $(this).parents(".layer-popup").removeClass("open");
        }
        if($(this).parents(".layer-popup").hasClass("trendPopup")){
          $(this).parents(".layer-popup").find(".swiper-slide").removeClass("mv");
          $(this).parents(".layer-popup").find(".swiper-slide .for-motion").removeClass("init");
          _isTrendChk = false;
        }

        if($(this).parents(".layer-popup").hasClass("estiCertiPop")){
          clearInterval(_flowInterval)
        }
      });

      // (공통) 정보 동의 팝업에서 '동의' 버튼 눌렀을 경우
      $(".layer-popup .btn-agree").on("click", function(){
        if(comeBackElement){
          $(comeBackElement).prop('checked', true).removeAttr('disabled');
          $(comeBackElement).addClass("agree");
        }
      });
    },
    swiperFn: function(){
      // 큰 텍스트 형식 tab swiper
      if($(".txt-major-tab-swiper").size() > 0){
        const txttabSwiper = new Swiper(".txt-major-tab-swiper .swiper-container", {
          slidesPerView: "auto",
          observer: true,
          observeParents: true,
        });
      }

      // 텍스트 형식 tab swiper
      if($(".txt-tab-swiper").size() > 0){
        txtTabSwiperCreate();
      }

      // 라벨 형식 tab swiper
      if($(".txt-depth-tab-swiper").size() > 0) {
        tabmenuSwiperCreate();
      }

      if($(".trend-swiper-area").size() > 0){
        trendSwiperCreate();
      }
      
      // [공통 - 교육/세미나] swiper
      trainingSwperInitFn();


      // [교육사업 > 교육사업소개] swiper
      swiperInitFn();
    },
    resizeFn: function(){
      $(window).resize(function(){
        // 라벨 형식 tab swiper
        if($(".txt-depth-tab-swiper").size() > 0) {
          tabmenuSwiperCreate();
        }

        if($(".trend-swiper-area").size() > 0){
          trendSwiperCreate();
        }

        // 퀵, top버튼 position 변경
        quickRePosition();

        // 컨설팅사업 > 신청하기 하단 고정 영역 position 변경
        acceptingRePosition();

        // [공통 - 교육/세미나] swiper
        trainingSwperInitFn();

        // [교육사업 > 교육사업소개] swiper
        swiperInitFn();

        // 전체교육일정 일정 바 left, width값 계산
        schedulePopupFn();
        
        // [교육사업 > 교육신청] 필터 filter 체크하여 dimd 안보이게
        if(window.innerWidth > 1023){
          $(".layer-popup .pop-con-area").removeAttr("style");

          if($(".filter-popup").length){
            $(".dimd").hide();
            $("body").removeClass("stop-scroll");
          }
        }else{
          if($(".filter-popup").hasClass("opened")){
            gsap.to($(".filter-popup .for-flex .for-center"), 0.6, {top: 0, ease: Power3});
            $(".dimd").show();
            $("body").addClass("stop-scroll");
          }else{
            if($(".filter-popup").length){
              gsap.to($(".filter-popup .for-flex .for-center"), 0.6, {top: "100%", ease: Power3});
              $(".dimd").hide();
              $("body").removeClass("stop-scroll");
            }
          }
        }
      });
    },
  }
})();

$(window).on("load", function(){
  //commonScript.deviceChk();
  commonScript.headerFooterFn();
  commonScript.init();
  commonScript.pageInitFn();
  commonScript.commonMotion();
  commonScript.formFn();
  commonScript.lnbFn();
  commonScript.scrollFn();
  commonScript.scrollTriggerFn();
  commonScript.swiperFn();
  commonScript.resizeFn();
  commonScript.popupFn();
});
$(document).on("ready", function(){
  // commonScript.headerFooterFn();
});

function scrollMotionTrigger(){
  if($(".scroll-motion").size() > 0){
    $(".scroll-motion:visible").each(function(q){
      let currentEle = $(this);
      gsap.to($(this), {
        onComplete: function () {
          // [마이페이지 > 교육 사업 신청내역], progress
          if($(currentEle).find(".progress-area").length > 0) {
            let progressValue = $(currentEle).find(".progress-area").data("progress");
            $(currentEle).find(".progress-area .progress .progress-bar").css("width", `${progressValue}%`);
            progressCounterNum($(currentEle), progressValue);
          }
        },
        scrollTrigger: {
          trigger: $(this),
          start: () => "top 85%",
          end:"bottom top",
          toggleClass: {targets: $(".scroll-motion:visible").eq(q), className: "active"},
          once: true,
          // markers: true,
        },
      });
    });
  }
}

function progressCounterNum(progressValue, numValue) {
  let counter = { number: 0 };
  const conuterCont =  $(progressValue).find(".progress-info .progress-num .num");

  gsap.to(counter, {
    duration: 1,
    number: numValue,
    onUpdate() {
      $(conuterCont).html(counter.number.toFixed(0));
    }
  });
}

function quickRePosition(){
  // 퀵, top버튼 footer만나면 position 변경
  if(window.innerWidth > 1023 && $(".quick-menu").length){
    $(".quick-menu").css({"position":"fixed", "bottom": ""});
  }else{
    if($("footer").length){
      var htmlVw;
      if(window.innerWidth <= 600){
        htmlVw = 0.00256;
      }else{
        htmlVw = 0.0013;
      }
      if($(".accepting-fixed-area").is(":visible")){// 신청하기 하단 고정 영역 노출 시
        if($(window).scrollTop() + window.innerHeight + window.innerWidth * htmlVw * 12 >= $("footer").offset().top){ // 12 = 고정영역 고정후 여백(32) - 고정영역 고정전 여백(20)
          $(".quick-menu").css({"position":"absolute", "bottom": $("footer").outerHeight(true) + (window.innerWidth * htmlVw * (32 + 58 + 12))}); // 32(고정영역과 footer 사이 여백) + 58(고정 영역 높이) + 12(고정영역과 quick 사이 여백)
        }else{
          $(".quick-menu").css({"position":"fixed", "bottom": "94rem"});
        }
      }else{// 신청하기 하단 고정 영역 없는 일반 페이지
        if($(window).scrollTop() + window.innerHeight >= $("footer").offset().top){
          $(".quick-menu").css({"position":"absolute", "bottom": $("footer").outerHeight(true) + (window.innerWidth * htmlVw * 16)}); // 16 = footer와 quick 사이 여백
        }else{
          $(".quick-menu").css({"position":"fixed", "bottom": "16rem"});
        }
      }
    }
  }
}

function acceptingRePosition(){
  // [컨설팅사업 > 하단 접수중 고정영역] footer만나면 position 변경
  if(window.innerWidth > 1023){ // pc
    if($(".accepting-fixed-area").length && $("footer").length){
      if($(window).scrollTop() + window.innerHeight + (window.innerWidth * 0.00052 * 24) >= $("footer").offset().top){ // 24 = 고정영역 고정후 여백(64) - 고정영역 고정전 여백(40)
        $(".accepting-fixed-area").addClass("not-fix");
      }else{
        $(".accepting-fixed-area").removeClass("not-fix");
      }
    }
  }else{ // mobile
    if($(".accepting-fixed-area").length && $("footer").length){
      if($(window).scrollTop() + window.innerHeight + (window.innerWidth * 0.00256 * 12) >= $("footer").offset().top){ // 12 = 고정영역 고정후 여백(32) - 고정영역 고정전 여백(20)
        $(".accepting-fixed-area").addClass("not-fix");
      }else{
        $(".accepting-fixed-area").removeClass("not-fix");
      }
    }
  }
}

// 탭메뉴
let tabmenuSwipers = [];
function tabmenuSwiperCreate() {
  $(".txt-depth-tab-swiper").each(function (idx, item) {
    const currentSwiper = tabmenuSwipers[idx];

    if (window.innerWidth < 1023) {
      if (!currentSwiper) {
        const newSwiper = new Swiper($(item).find(".swiper-container")[0], {
          slidesPerView: "auto",
          observer: true,
          observeParents: true,
        });
        
        tabmenuSwipers[idx] = newSwiper;
        newSwiper.slideTo($(item).find('.swiper-slide.active').index(), 0);
      }
    } else {
      if(!(currentSwiper === undefined)) {
        if (currentSwiper) {
          currentSwiper.destroy();
          tabmenuSwipers[idx] = undefined;
        }
      }
    }
  });
}

// [퀵메뉴 > TREND]
let trendSwiper = undefined;
let _isTrendChk = false;

function trendSwiperCreate() {
  if (window.innerWidth >= 1024 && !trendSwiper) {
    if(_isTrendChk) {
      $(".trend-swiper .swiper-slide").addClass("mv");
      $(".trend-swiper .swiper-slide .for-motion").addClass("init");
    }
    trendSwiper = new Swiper('.trend-swiper', {
      observer: true,
      observeParents: true,
      slidesPerView: 'auto',
      navigation: {
        nextEl: ".trend-swiper-area .swiper-button-next",
        prevEl: ".trend-swiper-area .swiper-button-prev",
      },
      speed : 800,
    });
  } else if (window.innerWidth < 1024 && trendSwiper) {
    _isTrendChk = true;
    trendSwiper.destroy();
    trendSwiper = undefined;
  }
}

function trendMobMotion() {
  if($(".trend-swiper").size() > 0){
    ScrollTrigger.matchMedia({
      "(max-width: 1023px)": function() {
        $(".trend-swiper .swiper-slide").removeClass("mv");
        $(".trend-swiper .swiper-slide").each(function(q){
          ScrollTrigger.create({
            trigger: $(this),
            start: "top 70%",
            end:"bottom top",
            scroller: ".trendPopup .con-area",
            // markers: true,
            onEnter: () => {
              if(!$(this).hasClass("mv")) {
                $(this).addClass("mv");
              }
            }
          });
        });
      }
    });
  }
}

function trendPcMotion() {
  const trendSlides = $(".trend-swiper .swiper-slide");
  console.log(trendSlides);
  let motionSetTime;

  gsap.fromTo(trendSlides, {x: "100vw"}, {
    x: 0,
    duration: 1.6,
    delay: 0.8,
    ease: "power4.out",
    stagger: {
      amount: 2,
      onStart: function () {
        $(this.targets()).addClass("mv");
      },
    },
  })
}

let popIdxNum = 200;
let dimdIdxNum = 200 - 2;
function openPopup(popName, comebackEl, reloadChk) {
  comeBackElement = comebackEl;
  var designatedPopup = $("."+popName) || $("#"+popName);
  
  if($(".layer-popup:visible").size() >= 1) {
    $(".layer-popup:visible").each(function(q, item){
      $(designatedPopup).css("z-index", popIdxNum + (q+1));
    });
  } else {
    $(designatedPopup).css("z-index", popIdxNum);
  }

  if($(".accepting-fixed-area").hasClass("opened")) {
    $(".accepting-fixed-area").css("z-index", dimdIdxNum);
  }
  

  // 모달 오픈 사용자 이벤트 발생
  var event = $.Event("onModalOpen");
  designatedPopup.trigger(event);

  gsap.delayedCall(0.1, function () {
    if(!$(comeBackElement).hasClass("agree")){ // 뉴스레터 수신 동의 구별 위한 if문 추가
      if(window.innerWidth > 1023){
        designatedPopup.stop(true, true).fadeIn(300, function(){
          designatedPopup.addClass("completed");
          if($(designatedPopup).hasClass("trendPopup")) {
            trendPcMotion()
            trendSwiper.slideTo(0);
            trendMobMotion();
          }
          // fadein 완료 후 reloadChk 값이 Y 이면 data값 변경 후 트리커 클릭 처리
          if(reloadChk == "Y"){
            $(comebackEl.target).data("mapchk", "Y").trigger("click");
          }
        })
      }else{
        $(designatedPopup).css("display","block");
        if($(designatedPopup).hasClass("trendPopup")) {
          $(".trendPopup .con-area").scrollTop(0);
          _isTrendChk = true;
        }
        setTimeout(() => {
          gsap.to($(designatedPopup).find(".pop-con-area"), 0.6, {top: 0, ease: Power3, onComplete: function(){
            designatedPopup.addClass("completed");
          }});
          $(designatedPopup).hasClass("trendPopup") && trendMobMotion();
        }, 10);
      }

      if($(designatedPopup).hasClass("full-popup")){ // full-popup 팝업만 2분할 모션
        designatedPopup.stop(true, true).show(0, function(){
          $(designatedPopup).addClass("open");
        })
      }

      
      $(".dimd").css("z-index", `${ dimdIdxNum + $(".layer-popup:visible").length }`).stop(true, true).fadeIn(300);
      designatedPopup.attr("tabindex", 0).focus();
      $("body").addClass("stop-scroll");
      // 모달 오픈 사용자 이벤트 발생
      event = $.Event("onModalOpenComplete");
      designatedPopup.trigger(event);
    }
  });

}





var boxListSwiper;
function swiperInitFn(){
  // [교육사업 > 교육사업소개] swiper
  if (window.innerWidth > 1023 && boxListSwiper == undefined) {
    if($(".box-list-swiper").size() > 0){
      boxListSwiper = new Swiper(".box-list-swiper .swiper-container", {
        slidesPerView: "auto",
        observer: true,
        observeParents: true,
        navigation: {
          nextEl: ".box-list-swiper .swiper-button-next",
          prevEl: ".box-list-swiper .swiper-button-prev",
        },
        //spaceBetween: 40,
      });
    }
  }else if(window.innerWidth <= 1023 && boxListSwiper != undefined) {
    boxListSwiper.destroy();
    boxListSwiper = undefined;
  }
}

var subTrainingSwiper = [];
function trainingSwperInitFn(){
  // [공통 - 교육/세미나] swiper
  // if (window.innerWidth > 1023) {
    if(!$("#wrap").hasClass("main")){
      if($(".training-swiper-area").size() > 0){
        $(".training-swiper-area.swiper-role .training-swiper").each(function(index, item){
          if(subTrainingSwiper[index] == undefined){
            $(this).addClass('swiper' + index);
            $(this).parents(".training-swiper-area").addClass('training-swiper-area' + index);
            subTrainingSwiper[index] = new Swiper(".training-swiper.swiper" + index, {
              observer: true,
              observeParents: true,
              pagination: {
                el: ".training-swiper-area .swiper-pagination",
                type: "bullets",
              },
              slidesPerView: 'auto',
              navigation: {
                nextEl: ".training-swiper-area.training-swiper-area" + index + " .swiper-button-next",
                prevEl: ".training-swiper-area.training-swiper-area" + index + " .swiper-button-prev",
              }
            });
          }
        });
      }
    }
  // }
  // else if(window.innerWidth <= 1023) {
  //   if(!$("#wrap").hasClass("main")){
  //     $(".training-swiper-area.swiper-role .training-swiper").each(function(index, item){
  //       if(subTrainingSwiper[index] != undefined){
  //         console.log(subTrainingSwiper[index]);
  //         subTrainingSwiper[index].destroy();
  //         subTrainingSwiper[index] = undefined;
  //       }
  //     });
  //   }
  // }
}

// 전체 교육 일정 팝업 내 스와이퍼 함수 재호출
function txtTabSwiperCreate(){
  const divideSwiper = new Swiper(".txt-tab-swiper .swiper-container", {
    slidesPerView: "auto",
    observer: true,
    observeParents: true,
  });
}



// 전체 교육 일정 레이어 팝업 관련 함수
function schedulePopupFn(){
  if($(".total-edu-area").size() > 0){

    var monthOuterWidth = Math.round($(".total-edu-area .edu-plan-area .month-wrap .month").outerWidth(true))
    var thisYear = $(".total-edu-area .edu-plan-area .month-area .f-head").text().split("년")[0];
    var thisDate = $(".total-edu-area .edu-plan-area .month-wrap").data("date")
    var thisDateString = String(thisDate).split(".")[1]
    
    $(".month-chk-w").css("left", ((monthOuterWidth * ($(".total-edu-area .edu-plan-area .month-area .month-wrap .month.now").text().split("월")[0])) - $(".total-edu-area .edu-plan-area .month-area .month-wrap .month.now").width()) + ((monthOuterWidth / 31) * parseInt(thisDateString)))


    $(".total-edu-area .edu-plan-area .round-period .period").each(function(q){
      if(!$(this).closest(".round-period").hasClass("no-this-year")){
        $(".period-bar").eq(q).find("span").text($(this).text())

        var startPeriod = $(this).text().split("~")[0]
        var endPeriod = $(this).text().split("~")[1]
        var startMonth = parseInt(startPeriod.split(".")[0]) // 시작 달
        var startDate = parseInt(startPeriod.split(".")[1]) // 시작 날짜
        var endMonth = parseInt(endPeriod.split(".")[0]) // 종료 달
        var endDate = parseInt(endPeriod.split(".")[1]) // 종료 날짜

        if($(this).data("start-year") < thisYear){
          $(".period-bar").eq(q).css({"left":0, "width":Math.round((monthOuterWidth / 31) * endDate)})
        }else if($(this).data("end-year") > thisYear){
          $(".period-bar").eq(q).css({"left":(Math.round((startMonth - 1) * monthOuterWidth)) + ((monthOuterWidth / 31) * (startDate - 1)), "width":Math.round(((monthOuterWidth / 31) * 31) - (monthOuterWidth / 31) * (startDate - 1))})
        }else{
          $(".period-bar").eq(q).css({"left":(Math.round((startMonth - 1) * monthOuterWidth)) + ((monthOuterWidth / 31) * (startDate - 1)), "width":((endMonth - startMonth) * monthOuterWidth) + ((endDate - startDate) * (monthOuterWidth / 31))})
        }
        
      }else{
        $(".period-bar").eq(q).css("width", "0")
      }
    });

    if(window.innerWidth > 1023){
      Draggable.create(".scroll-div", {
        type: "scroll-x",
        cursor:"auto",
        inertia: true,
        throwResistance: 3000,
      });
    }else{
      $(".total-edu-area .edu-plan-area .month-area .scroll-div > div").removeAttr("style")
    }
    
    setTimeout(function(){
      $(".total-edu-area .edu-plan-area .month-area .scroll-div").scrollLeft(($(".total-edu-area .edu-plan-area .month-area .month-wrap .month").outerWidth(true) * $(".total-edu-area .edu-plan-area .month-area .month-wrap .month.now").index()) - $(".total-edu-area .edu-plan-area .month-area .month-wrap .month").outerWidth(true))
    }, 100)
    
  }
}

// 흐르는 텍스트 함수
function flowTxtImgFn(){
  if($(".flow-wrap").size() > 0){
    if($(".flow-wrap").parents().hasClass("layer-popup")){
      if($(".flow-wrap").parents(".layer-popup").is(":visible")){
        let fontWidthSum = $(".flow-wrap .flow-list").outerWidth(true);

        while(fontWidthSum < window.innerWidth + $(".flow-wrap .flow-list").eq(0).outerWidth(true)){
          const repeatObject = document.querySelector(".flow-wrap .flow-list");
          const newNode = repeatObject.cloneNode(true);
          repeatObject.after(newNode);
          fontWidthSum = fontWidthSum + $(".flow-wrap .flow-list").eq(-1).outerWidth(true);
        };
      
        _flowInterval = setInterval(function() {
          if(parseInt($(".flow-wrap").css("left").split("p")[0]) < -$(".flow-wrap .flow-list").eq(0).outerWidth(true)) {
            $(".flow-wrap").css("left", 0);
          }
          $(".flow-wrap").css("left", parseInt($(".flow-wrap").css("left").split("p")[0]) - 2);
        }, 20);
      }
    }
  }
}


// 교육사업 anchor visible 함수
function anchorVisibleFn() {
  if($(".anchor-btn-w").length){
    if($(window).scrollTop() > $("#roundSection").offset().top - $(".anchor-btn-w").height()){
      $(".anchor-btn-w").addClass("show");
    }else{
      $(".anchor-btn-w").removeClass("show");
    }
  }
}


// 프린트 관련 함수
function printFn(){
  // method 1 (새 윈도우 창 열어서 프린트 후 닫기)
  var popUrl = "FO-PC-MYP-02-013.html";
  var popOption = "top=10, left=10, width=1080, height=1528, scrollbars=no, status=no, menubar=no, toolbars=no, resizable=no";
  var myWindow = window.open(popUrl, popOption);
  myWindow.document.close();
  myWindow.focus();
  
  myWindow.onafterprint = function () { //프린터 출력 후 이벤트
    myWindow.close();
  }

  myWindow.print();








  // method 2 (기존 바닥페이지에서 내용 교체로 구현)
  /*var initBody = document.body.innerHTML; //body영역 저장

  const newDiv = document.createElement('div');
  document.body.appendChild(newDiv);
  $("body > div:last-child").attr('id', 'for-print-area');
  $("#for-print-area").load("/html/mypage/FO-PC-MYP-02-013.html", function(){
    window.print();
    $("#for-print-area").css("border","5px solid yellow");
  });


  window.onbeforeprint = function () { //프린터 출력 전 이벤트
    document.body.innerHTML = document.getElementById('for-print-area').innerHTML;
  }
  
  
	window.onafterprint = function () { //프린터 출력 후 이벤트
    document.body.innerHTML = initBody;
    ScrollTrigger.refresh();
	}

  return false;*/	
}




// 세로 최적화 안내
function showBack(focusEle) {	// 화면 각도 바뀔때 기종, 키패드 올라왔는지 아닌지 체크 후  only-vertical-view 노출,미노출 체크 
	var agent = navigator.userAgent.toLowerCase();
		
	var mobileArr = new Array("iPhone", "iPod", "Android");
	var mobileNum;
	for(var txt in mobileArr){
		if(navigator.userAgent.match(mobileArr[txt]) != null){
			mobileNum = txt;
			break;
		}
	}

  if(agent.indexOf('nexus 5 build/mra58n') > -1 || agent.indexOf('Windows') > -1){
  }else{
    if(mobileNum > 1){ // ios 외 다른 기종 				
      if (window.matchMedia('(orientation: portrait)').matches || window.innerWidth <= window.innerHeight) {
        // 세로 모드 (평소 사용하는 각도)
        if(typeof focusEle != "undefined"){// 키패드 올라왔을 때
          $("body").addClass("vertical");
          $(".only-vertical-view").addClass("on");
        }else{
          $("body").removeClass("vertical");
          $(".only-vertical-view").removeClass("on");
        }		
      }else if(window.matchMedia("(orientation: landscape)").matches || window.innerWidth > window.innerHeight){
        var widthChk = window.innerWidth;
        var heightChk = window.innerHeight;
        var aspectRatio = widthChk / heightChk
        var landscapeAspectRatioThreshold = 1.2;

        if (aspectRatio > landscapeAspectRatioThreshold) {
          // 가로 모드
          $("body").addClass("landscape");
          $(".only-vertical-view").addClass("on");
        } else {
          // 세로 모드
          $("body").removeClass("landscape");
          $(".only-vertical-view").removeClass("on");
        }
      }
    }else{	// ios 
      if(agent.indexOf("version") != -1){
        if(window.matchMedia("(orientation: portrait)").matches){
          // 세로 모드 (평소 사용하는 각도)
          $("body").removeClass("vertical");
          $(".only-vertical-view").removeClass("on");
        }else if(window.matchMedia("(orientation: landscape)").matches){
          if(window.innerHeight < 512){
            // 가로 모드 (동영상 볼때 사용하는 각도)
            $("body").addClass("vertical");
            $(".only-vertical-view").addClass("on");
          }
        }
      }else{			
        if(window.matchMedia("(orientation: portrait)").matches){
          // 세로 모드 (평소 사용하는 각도)
          $("body").removeClass("vertical");
          $(".only-vertical-view").removeClass("on");
        }else if(window.matchMedia("(orientation: landscape)").matches){
          if(window.innerHeight < 512){
            // 가로 모드 (동영상 볼때 사용하는 각도)
            $("body").addClass("vertical");
            $(".only-vertical-view").addClass("on");
          }
        }
      }
    }
  }
}

$(window).on("orientationchange", function(event){
  $(window).on("resize", function() {
    var focusEle = document.activeElement.name;
    $("input:focus, textarea:focus").blur();
    setTimeout(function(){
      showBack(focusEle);
    },280)
  });
});