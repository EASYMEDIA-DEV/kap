var _this_scroll = 0;       // 스크롤 up & down 체크위한 변수
var _isScrollTop;          // scrollTop 변수
var comeBackElement;
let kvTitleChangeMotion;
var _isPlanMove = false; // 전체 교육 일정 해당 월로 위치 한번만 이동하기 위한 변수

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
      $(".accordion-st .acco-click-area").off().on("click", function(e){
        if($(e.target).parents(".form-checkbox").size() == 0){
          //console.log("외부");
          $(this).parents(".list-item").siblings().removeClass("active").find(".acco-hide-area").stop(true, true).slideUp(300);
          if(!$(this).parents(".list-item").hasClass("active")){
            $(this).parents(".list-item").addClass("active").find(".acco-hide-area").stop(true, true).slideDown(300);
          }else{
            $(this).parents(".list-item").removeClass("active").find(".acco-hide-area").stop(true, true).slideUp(300);
          }
        }
      });


      // tab con 클릭
      $(".txt-tab-swiper.func-tab .txt-tab-btn").off().on("click", function(){
        $(this).addClass("active").siblings().removeClass("active");
        // $(this).parents(".tab-con-w").find(".tab-con-area .tab-con").hide().eq($(this).index()).show();
        if($(this).parents(".pop-con-area").size() > 0) {
          $(this).parents(".pop-con-area").find(".tab-con").hide().eq($(this).index()).show();
        } else {
          $(this).parents(".tab-con-w").find(".tab-con").hide().eq($(this).index()).show();
        }
      });
      $(".txt-depth-tab-sweiper .txt-tab-btn").off().on("click", function(){
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
          if(!$(this).hasClass("opened")){
            $(this).addClass("opened").attr("title", "필터 닫기");
            $(".filter-popup").stop(true, true).slideDown(300);
          }else{
            $(this).removeClass("opened").attr("title", "필터 열기");
            $(".filter-popup").stop(true, true).slideUp(300);
          }          
        }else{
          $(this).addClass("opened").attr("title", "필터 닫기");
          $(".filter-popup").stop(true, true).fadeIn(300);
          $("body").addClass("stop-scroll");
          $(".dimd").css("z-index", 101).stop(true, true).fadeIn(300);
        }
      });
      $(".filter-popup .btn-role-close").off().on("click", function(){
        $(".filter-open-btn").removeClass("opened").attr("title", "필터 열기");

        if(window.innerWidth > 1023){
          $(".filter-popup").stop(true, true).slideUp(300);
        }else{
          $(".filter-popup").stop(true, true).fadeOut(300);
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
      $(".accepting-fixed-area .open-click-area").off().on("click", function(){
        $(".accepting-fixed-area").addClass("opened");
        $(".dimd").css("z-index", 101).stop(true, true).fadeIn(400);
        $(".accepting-fixed-area .hide-area").css({"z-index": 1, "background-color": "#fff"});
        gsap.to((".accepting-fixed-area .open-click-area .tit, .accepting-fixed-area .open-click-area .btn-text-icon.plus"), 0.2, {opacity: 0, ease:Power3.easeOut});
        gsap.to((".accepting-fixed-area .hide-area"), 0.5, {scaleX: 1, scaleY: 1, ease:Power3.easeOut});
        gsap.to((".accepting-fixed-area .hide-area .inner-con"), 0.5, {opacity: 1, ease:Power3.easeOut, delay: 0.5});
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
          yScale = (window.innerWidth * 0.00256 * 58) / $(".accepting-fixed-area .hide-area").outerHeight();
        }
        gsap.to((".accepting-fixed-area .hide-area"), .5, {scaleX: xScale, scaleY: yScale, ease:Power3.easeOut, onComplete: function(){
          $(".accepting-fixed-area .hide-area").css({"z-index": 0, "background-color": "transparent"});
        }});
        gsap.to((".accepting-fixed-area .hide-area .inner-con"), 0.1, {opacity: 0, ease:Power3.easeOut});
      });


      // [재단정보 > 재단소개 > 연혁], history
      if($(".year-wrap").size() > 0){
        // $(window).resize(function() {
        //   if($(window).innerWidth() > 1500 && isPc) {
        //     isPc = !isPc;
        //     isPc2 = true;
        //     isMobile = true;
        //     headerH = $($header).innerHeight();
        //   } else if($(window).innerWidth() < 1500 && $(window).innerWidth() > 1024 && isPc2) {
        //     isPc2 = !isPc2;
        //     isPc = true;
        //     isMobile = true;
        //     headerH = $($header).innerHeight();
        //   } else if($(window).innerWidth() < 1024 && isMobile) {
        //     isMobile = !isMobile;
        //     isPc = true;
        //     isPc2 = true;
        //     headerH = $($header).innerHeight();
        //   }
        // }
                // $(window).resize(function() {
        //   historyHeaderHeight = $("#header").height();

        //   console.log($("#header").height());

        //   $(".history-item").each((idx, item) => {
        //       gsap.scrollTrigger.update(item, {
        //           start: `top ${$("#header").height()}`
        //       });
        //   });
        // });
        let isHistoryScroll = true;
        let historyHeaderHeight = $("#header").height();
        let yearValue;
        
        // 스크롤 시, 해당 연도 on
        $(".history-area .history-wrap .history-item").each((idx, item) => {
          gsap.to(item, {
            scrollTrigger: {
              trigger: item,
              start: `top ${$("#header").height() + 24}`,
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
                    console.log("같다");
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
    },
    formFn: function(){
      // 툴팁
      $(".tooltip-wrap .tooltip-btn").off().on("click", function(){
        $(this).parents(".tooltip-wrap").addClass("open");
        $(this).parents(".tooltip-wrap").find(".tooltip-box").stop(true, true).fadeIn(200);
      });
      $(".tooltip-wrap .tooltip-box .btn-close").off().on("click", function(){
        $(this).parents(".tooltip-wrap").removeClass("open");
        $(this).parents(".tooltip-box").stop(true, true).fadeOut(200);
      });

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
      if($(".form-select").size() > 0){
        $(".form-select select").off().focus(function(){
          $(this).parents(".form-select").addClass("active");
        });
  
        $(".form-select select").off().blur(function(){
          $(this).data('isopen', false);
          $(this).parents(".form-select").removeClass("active");
        });
  
        $(".form-select select").off().on("change", function(){
          $(this).parents(".form-select").removeClass("active");
        });
          
        $(".form-select select").off().on("mouseup", function(e) {
          let open = $(this).data("isopen");
    
          if(open) {
            $(this).parents(".form-select").removeClass("active");
          } else {
            $(this).parents(".form-select").addClass("active");
          }
          
          $(this).data("isopen", !open);
        });
      }

      // 기타 radio 클릭 시 input 활성화
      $("input[type='radio']").off().change(function(){
        if($(this).parents(".etc-option-w").size() > 0){
          if($(this).prop('checked') == true){
            console.log("해당 클릭");
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
          // prev 삭제
          $(".notice-rolling .prev").removeClass("prev");

          // current -> prev
          let current = $(".notice-rolling .current");
          current.removeClass("current");
          current.addClass("prev");

          // next -> current
          let next = $(".notice-rolling .next");
          //다음 목록 요소가 있을 때, 없을 때
          if (next.next().length === 0) {
            $(".notice-rolling ul li:first-child").addClass("next");
          }else{
            //목록 처음 요소를 다음 요소로 선택
            next.next().addClass("next");
          }
          next.removeClass("next");
          next.addClass("current");
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
              rollingInterval = window.setInterval(noticeRollingText, 3000);

            });
          } else {
            $("#header .all-menu").fadeOut(200, function() {
              $("#header").removeClass("menu-open");
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

            // 롤링 텍스트 정지
            clearInterval(rollingInterval);
            // 롤링 텍스트 정지 시, 초기화
            $(".notice-rolling li").removeClass("prev current next");
            $(".notice-rolling li:first-child").addClass("current");
            $(".notice-rolling li:nth-child(2)").addClass("next");
            $(".notice-rolling li:last-child").addClass("prev");

            // if($(window).width() >= 1024) {
            //   if(!$("#header").hasClass("srch-open")) {
            //     $("body").removeClass("stop-scroll");
            //   }
            // }
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
        const $allMenuLastLi = $($("#header .all-menu .gnb").find(".three-pack > li a").last());

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
      var _lnbHeight = $(".lnb-area").height();

      // load 시 lnb 해당 아코디언 열려있게
      if($(".btn-two-depth.active").length && window.innerWidth > 1023){
        if(!$(".btn-two-depth.active").hasClass("single-menu")){
          $(".btn-two-depth.active").parents(".lnb-list").find(".three-depth-wrap").slideDown(0, function(){
            setTimeout(() => {
              _lnbHeight = $(".lnb-area").outerHeight(true);
              _lnbHeight = _lnbHeight + 100 + "rem";
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
                start: "top 70%",
                endTrigger: $(".divide-con-area .right-con-area"),
                end: () => "bottom-=100rem " + _lnbHeight,
                onEnter: function(){
                  gsap.to((".lnb-area .for-motion"), 1, {marginTop:0, opacity: 1, ease:Power3.easeOut});
                },
                invalidateOnRefresh: true,
                scrub: true,
                //markers:true,
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
                start: "top 100rem",
                endTrigger: $(".divide-con-area .right-con-area .cont-sec-w"),
                //end:"bottom-=100rem "+ _lnbHeight,
                end: () => "bottom " + _lnbHeight,
                pin: true,
                pinSpacing: false,
                invalidateOnRefresh: true,
                scrub: true,
                // markers:true,
                // onEnter: () => {
                //   if($(".year-wrap").size() > 0) {
                //     $(".year-box").addClass("fixed");
                //   }
                // },
                // onEnterBack: () => {
                //   if($(".year-wrap").size() > 0) {
                //     $(".year-box").addClass("fixed");
                //   }
                // },
                // onLeaveBack: () => {
                //   if($(".year-wrap").size() > 0) {
                //     $(".year-box").removeClass("fixed");
                //   }
                // },
                // onLeave: () => {
                //   if($(".year-wrap").size() > 0) {
                //     $(".year-box").removeClass("fixed");
                //   }
                // }
              },
            });
          },
        })
      }

      // lnb 아코디언 클릭
      $(".lnb-area .btn-two-depth").on("click", function(){
        if(window.innerWidth > 1023){
          console.log("here");
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
      $(window).on("scroll", function(){
        _isScrollTop =  $(window).scrollTop();
        
        // 스크롤 시, 헤더 up & down
        if(_isScrollTop > _this_scroll) { // down
          if($(window).scrollTop() > 100 && !$("#header").hasClass("srch-open")){
            gsap.to(("#header"), 1, {top:"-110rem", ease: Power3.easeOut});
            $(".quick-menu").addClass("scroll-down");
          }
        }
      
        if(_isScrollTop < _this_scroll) { // up
          if(_isScrollTop > 0 && !$("#header").hasClass("srch-open")){
            $("#header").addClass("up-scroll");
            $("#header").removeClass("hide");
            gsap.to(("#header"), 1.3, {top: 0, ease: Power3.easeOut});
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
        }

        // 페이지 스크롤 길이
        $(".scroll-gauge .bar").width((_isScrollTop / (($("#wrap").height() - window.innerHeight)) * 100) + "%");

        // 퀵, top버튼 position 변경
        quickRePosition();

        // 컨설팅사업 > 신청하기 하단 고정 영역 position 변경
        acceptingRePosition();

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
            start: "top " + $(".sub-top-vis-area.basic-page").offset().top,
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
              start: "top " + $(".sub-top-vis-area.apply-page").offset().top,
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
                start: "top " + $(".sub-top-vis-area.basic-page").offset().top,
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
                start: "top " + $(".sub-top-vis-area.basic-page").offset().top,
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
                start: "top " + $(".sub-top-vis-area.basic-page").offset().top,
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
                start: "top " + $(".sub-top-vis-area.basic-page").offset().top,
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
          $(this).parents(".layer-popup").stop(true, true).fadeOut(300);
        }else{
          if($(this).parents(".layer-popup").hasClass("main-popup")){
            $(this).parents(".layer-popup").stop(true, true).fadeOut(300); // 메인팝업만 fadeOut, 나머진 slide 방식
          }else{
            var targetPopup = $(this).parents(".layer-popup");
            gsap.to($(this).parents(".layer-popup").find(".pop-con-area"), 0.6, {top: "100%", ease: Power3, onComplete: function(){
              $(targetPopup).hide();
            }});            
          }
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
      // 텍스트 형식 tab swiper
      if($(".txt-tab-swiper").size() > 0){
        const divideSwiper = new Swiper(".txt-tab-swiper .swiper-container", {
          slidesPerView: "auto",
          observer: true,
          observeParents: true,
        });
      }
      // 라벨 형식 tab swiper
      if($(".txt-depth-tab-sweiper").size() > 0){
        const divideSwiper = new Swiper(".txt-depth-tab-sweiper .swiper-container", {
          slidesPerView: "auto",
          observer: true,
          observeParents: true,
        });
      }

      
      // [공통 - 교육/세미나] swiper
      trainingSwperInitFn();


      // [교육사업 > 교육사업소개] swiper
      swiperInitFn();


      // 컨설팅 사업 > 기술지도 안내 swiper
      if($(".divide-swiper").size() > 0){
        // ❗❗ 2023-11-07 홀딩 상태로 구은지 작업 일시 중단
        // const divideSwiper = new Swiper(".divide-img-swiper .swiper-container", {
        //   loop: true,
        //   autoplay: false,
        //   observer: true,
        //   observeParents: true,
        //   simulateTouch: false,
        //   speed: 1000,
        //   watchSlidesProgress: true,
        //   navigation: {
        //     nextEl: ".divide-img-swiper .swiper-button-next",
        //     prevEl: ".divide-img-swiper .swiper-button-prev",
        //   },
        //   on: {
        //     slideChange: function () {
        //       // active된 스와이퍼 관련 문구 on
        //       let realIdx = this.realIndex;

        //       $(".divide-txt-wrap").find('.txt-box').removeClass('on');
        //       $(".divide-txt-wrap").find('.txt-box').eq(realIdx).addClass('on');

        //       // transitoin 지정
        //       let swipeSpeed = this.params.speed;

        //       if ($(".swiper-slide-active").hasClass("swiper-slide-duplicate")) {
        //         $(this.slides).find(".slide-inner").css("transition", `0ms`);
        //       } else {
        //         $(this.slides).find(".slide-inner").css("transition", `${swipeSpeed}ms`);
        //       }
        //     },
        //     progress: function () {
        //       let movingValue = 0.8;
        //       let innerSlideOffset = this.width * movingValue;
  
        //       $(this.slides).each(function(idx, slideItem) {
        //         let slideProgress = slideItem.progress;
        //         let innerTranslate = slideProgress * innerSlideOffset;
        //         let slides = $(slideItem).find(".slide-inner");
  
        //         gsap.to(slides, 0, {x: innerTranslate});
        //       });
        //     }
        //   }
        // });

        let divideSwiper;

        function divideSwiperCreate() {
          if (window.innerWidth >= 1024) { // 1024px 이상인 경우
            if (divideSwiper) {
              divideSwiper.destroy();
            }
            divideSwiper = new Swiper(".divide-swiper .swiper-container", {
              loop: true,
              effect: "fade",
              speed: 500,
              observer: true,
              observeParents: true,
              watchSlidesProgress: true,
              navigation: {
                nextEl: ".divide-swiper .swiper-button-next",
                prevEl: ".divide-swiper .swiper-button-prev",
              },
            });
          } else { // 1024px 미만인 경우
            if (divideSwiper) {
              divideSwiper.destroy();
            }
            divideSwiper = new Swiper(".divide-swiper .swiper-container", {
              loop: true,
              effect: "slide",
              speed: 500,
              slidesPerView: 1.33,
              spaceBetween: 30,
              observer: true,
              observeParents: true,
              watchSlidesProgress: true,
              navigation: {
                nextEl: ".divide-swiper .swiper-button-next",
                prevEl: ".divide-swiper .swiper-button-prev",
              },
            });
          }
        }

        divideSwiperCreate();
        $(window).resize(function () {
          divideSwiperCreate();
        });
      }
    },
    resizeFn: function(){
      $(window).resize(function(){
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
          if($(".filter-open-btn").length){
            $(".dimd").hide();
            $("body").removeClass("stop-scroll");
          }
        }else{
          if($(".filter-open-btn").hasClass("opened")){
            $(".dimd").show();
            $("body").addClass("stop-scroll");
          }else{
            if($(".filter-open-btn").length){
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
      gsap.to($(this), {
        scrollTrigger: {
          trigger: $(this),
          start: "top 85%",
          end:"bottom top",
          toggleClass: {targets: $(".scroll-motion:visible").eq(q), className: "active"},
          once: true,
          onEnter: () => {
            // [마이페이지 > 교육 사업 신청내역], progress
            if($(".progress").size() > 0) {
              let progressValue = $(".progress .progress-bar").data("progress");
              $(".scroll-motion.active .for-motion .progress-area .progress .progress-bar").css("width", progressValue);
            }
          },
          //markers: true,
        },
      });
    });
  }
}

function quickRePosition(){
  // 퀵, top버튼 footer만나면 position 변경
  if(window.innerWidth > 1023 && $(".quick-menu").length){
    $(".quick-menu").css({"position":"fixed", "bottom": ""});
  }else{
    if($("footer").length){
      if($(".accepting-fixed-area").is(":visible")){// 신청하기 하단 고정 영역 노출 시
        if($(window).scrollTop() + window.innerHeight + window.innerWidth * 0.00256 * 12 >= $("footer").offset().top){ // 12 = 고정영역 고정후 여백(32) - 고정영역 고정전 여백(20)
          $(".quick-menu").css({"position":"absolute", "bottom": $("footer").outerHeight(true) + (window.innerWidth * 0.00256 * (32 + 58 + 12))}); // 32(고정영역과 footer 사이 여백) + 58(고정 영역 높이) + 12(고정영역과 quick 사이 여백)
        }else{
          $(".quick-menu").css({"position":"fixed", "bottom": "94rem"});
        }
      }else{// 신청하기 하단 고정 영역 없는 일반 페이지
        if($(window).scrollTop() + window.innerHeight >= $("footer").offset().top){
          $(".quick-menu").css({"position":"absolute", "bottom": $("footer").outerHeight(true) + (window.innerWidth * 0.00256 * 16)}); // 16 = footer와 quick 사이 여백
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

let popIdxNum = 200;
let dimdIdxNum = 200 - 2;
function openPopup(popName, comebackEl) {
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

  gsap.delayedCall(0.1, function () {
    if(!$(comeBackElement).hasClass("agree")){ // 뉴스레터 수신 동의 구별 위한 if문 추가
      if(window.innerWidth > 1023){
        designatedPopup.stop(true, true).fadeIn(300);
      }else{
        $(designatedPopup).css("display","block");
        setTimeout(() => {
          gsap.to($(designatedPopup).find(".pop-con-area"), 0.6, {top: 0, ease: Power3});
        }, 10);
      }
      $(".dimd").css("z-index", `${ dimdIdxNum + $(".layer-popup:visible").length }`).stop(true, true).fadeIn(300);
      designatedPopup.attr("tabindex", 0).focus();
      $("body").addClass("stop-scroll");
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
  if (window.innerWidth > 1023) {
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
  }else if(window.innerWidth <= 1023) {
    if(!$("#wrap").hasClass("main")){
      $(".training-swiper-area.swiper-role .training-swiper").each(function(index, item){
        if(subTrainingSwiper[index] != undefined){
          console.log(subTrainingSwiper[index]);
          subTrainingSwiper[index].destroy();
          subTrainingSwiper[index] = undefined;
        }
      });
    }
  }
}

// 전체 교육 일정 레이어 팝업 관련 함수
function schedulePopupFn(){
  if($(".total-edu-area").size() > 0){

    var monthOuterWidth = Math.round($(".total-edu-area .edu-plan-area .month-wrap .month").outerWidth(true))
  
    $(".total-edu-area .edu-plan-area .round-period .period").each(function(q){
      $(".period-bar").eq(q).find("span").text($(this).text())
  
      var startPeriod = $(this).text().split("~")[0]
      var endPeriod = $(this).text().split("~")[1]
      var startMonth = parseInt(startPeriod.split(".")[0]) // 시작 달
      var startDate = parseInt(startPeriod.split(".")[1]) // 시작 날짜
      var endMonth = parseInt(endPeriod.split(".")[0]) // 종료 달
      var endDate = parseInt(endPeriod.split(".")[1]) // 종료 날짜
  
      $(".period-bar").eq(q).css({"left":(Math.round((startMonth - 1) * monthOuterWidth)) + ((monthOuterWidth / 31) * (startDate - 1)), "width":((endMonth - startMonth) * monthOuterWidth) + ((endDate - startDate) * (monthOuterWidth / 31))})
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
    
    if(!_isPlanMove){
      _isPlanMove = true;
      $(".total-edu-area .edu-plan-area .month-area .scroll-div").scrollLeft($(".total-edu-area .edu-plan-area .month-area .month-wrap .month").outerWidth(true) * $(".total-edu-area .edu-plan-area .month-area .month-wrap .month.now").index())
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