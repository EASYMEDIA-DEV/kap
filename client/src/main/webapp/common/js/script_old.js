var _this_scroll = 0;       // 스크롤 up & down 체크위한 변수
var _isScrollTop;          // scrollTop 변수
var _pageScrollOffset;            // dimd 시 scrollTop 값 기억
var _beforeScrollOffset;
var _device = '';           // 접속 device 체크 위한 변수
var _deviceCondition = '';  // 해상도 따른 device 체크 위한 변수
var _browser = '';          // browser 체크 위한 변수
var _anchorPdNum// 상단 여백 값
var _betweenSpace;
var _thrDepSwiper; // 3depth 스와이퍼
var _filterTop; // 필터 상단 top값
var _barWidthArr = [];
var _newsDetailSwiper; // 코오롱 뉴스 상세 리스트
var _viewConTitHeight; // view 페이지 title 높이값
var _isMotionOnce = false; // resize 안에서 모션 한번만 작동하기 위한 변수
var _mainPopSwiper; // 메인팝업
var _mGnbAccordion; // mobile header accordion 변수
var _headerHeightArr = [];  // header
var _headerMaxHeight = 0;   // header
var _pathLength // top 버튼 stroke-dasharray
var _srchDataSwiper; // 데이터 검색, 선택 초기화 swiper 변수
var _popScrollTop;
var _fooH // 푸터 Height값
var _visualSwiper; // 비주얼 스와이퍼 변수
var _videoTime; // 비디오 영상 시간
var _progressBarMotion;
var _paybarH;
var _fixedBtnH;
var _rightH;
var _isSrchOncePc = false;
var _isSrchOnceMo = false;
/* var window.innerWidth = 0; */

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
      if(window.location.href.indexOf("http://kggroup.easymedia.kr/") > -1 || window.location.href.indexOf('127.0.0.1') > -1 || window.location.href.indexOf("http://eznet1.easymedia.kr") > -1) {
        if(window.location.href.indexOf("kr/styleguide/") == -1){
          if($("header").html() == ''){
            $("header").empty().load("/header.html");
            $("footer").empty().load("/footer.html", function(){
              gsap.delayedCall(0.2, function() {
                commonScript.headerFooterFn();
                commonScript.formChkFn();
                // commonScript.commonFn();
                // popupResize();
                // commonScript.resizeFn();
                // commonScript.scrollFn();
              });
            });
          }
        }        
      }

      // var wrap = document.getElementById("wrap");
      // var scrollBar = window.innerWidth - $(window).width();
      // _winWidth = Math.ceil(wrap.getBoundingClientRect().width) + scrollBar;
    },
    headerFooterFn: function(){
      // pc header gnb
      var gnbNum = -1;
      var headerDelayTime;

      $("header nav .gnb > li").each(function(q){
        $(this).find(".one_depth").on("mouseenter focusin", function(){
          if(window.innerWidth > 1023){
            if(gnbNum != $(this).parents("li").index()){
              $("header nav .two_pack > li").removeClass("on");
            }
            $("header").addClass("mouse_hover gnb_hover drop_down")

            $(".form_select.on").find("select").blur();
            $(".form_select").removeClass("on");
            
            $(this).parents("li").siblings().removeClass("on");
            $(this).parents("li").addClass("on");
            $(this).parents("li").removeClass("opacity")
            $(this).parents("li").siblings().addClass("opacity");
            $(this).parents("li").siblings().find(".two_pack").fadeOut(200)
            $(this).parents("li").find(".two_dep_div li").removeClass("opacity")
            $("header nav .gnb > li").eq($(this).parents("li").index()).find(".two_pack").fadeIn(200);
  
            gnbNum = $(this).parents("li").index();
          }
        });

        $(this).find(".two_pack li").each(function(q){
          $(this).find(".two_depth").on("mouseenter focusin", function(){
            $(this).parents(".two_dep_div").find("li").removeClass("opacity")
            $(this).parents(".two_dep_div").find("li").not($(this).parents("li")).addClass("opacity");
          });
        });

        $(this).find(".one_depth").on("click", function(){
          if(window.innerWidth <= 1023){
            if(!$(this).parents("li").hasClass("on")){
              $(this).parents(".gnb").find("li").removeClass("on")
              $(this).parents(".gnb").find(".two_pack").stop(true, true).slideUp(300);
              $(this).parents("li").removeClass("opacity")
              $(this).parents("li").siblings().addClass("opacity");
              $(this).parents("li").addClass("on");
              $(this).siblings(".two_pack").stop(true, true).slideDown(300);
            }else{
              $(this).parents("li").removeClass("opacity")
              $(this).parents("li").removeClass("on");
              $(this).siblings(".two_pack").stop(true, true).slideUp(300);
            }
            
          }
        });
      });


      $("header nav").on("mouseleave", function(){
        if(window.innerWidth > 1024){
          setTimeout(function(){
            $("header").removeClass("mouse_hover");
          }, 400);
          $("header").find(".two_pack").fadeOut(200)
          $("header").removeClass("drop_down gnb_hover");
          $(".dimd").stop(true, true).fadeOut(400);
          $("header nav .gnb > li").removeClass("on");
          $("header nav .gnb > li").removeClass("opacity");
          $("header nav .gnb > li .two_pack .two_dep_div > li").removeClass("opacity");
          $("header .gnb > li .one_depth").blur();
          clearTimeout(headerDelayTime);
        }
      });

      // 모바일 전체메뉴 열기
      $("header .all_menu_btn").on("click", function(){
        $("body").addClass("stop_scroll")
        $("body, html").on('scroll touchmove mousewheel', function (e) {
          e.preventDefault();
        });
        $("header .header_dimd").fadeIn();
        $("header nav").addClass("on");
      });

      // 모바일 전체메뉴 닫기
      $("header .all_menu_close, .header_dimd").on("click", function(){
        $("body").removeClass("stop_scroll")
          $("body, html").off('scroll touchmove mousewheel');
        $("header .header_dimd").fadeOut();
        $("header nav").removeClass("on");
      });

      // footer family site
      $("footer .inner .flex_box .right .group_link .btn_open").on("click", function(){
        if(!$(this).parents(".group_link").hasClass("on")){
          $(this).parents(".group_link").addClass("on")
          $(this).siblings(".group").stop(true, true).slideDown()
        }else{
          $(this).parents(".group_link").removeClass("on")
          $(this).siblings(".group").stop(true, true).slideUp()
        }
      });
    },
    commonFn: function(){
      if(_device == 'ios'){
        $("body").addClass("ios");
      }

      // 탑버튼
      repositioningTopBtn(); // 스크롤 없는 페이지에서 스크롤 전 초기 위치값 설정

      $(".btn_top").on("click", function(){
        gsap.to($("html, body"), {duration: 0.8, scrollTop: 0, ease: Power3.easeOut});
      });

      // 필터
      $(".filter_area .filter_btn").on("click", function(){
        $(this).parents(".list_area").addClass("high_index");
        if(_deviceCondition == "pc"){
          $(".filter_pop").fadeIn();
        }else if(_deviceCondition == "mobile"){
          
          $(".dimd").fadeIn();
          $(".filter_pop").stop(true, true).slideDown(300, function(){
            $(".filter_pop").innerHeight($(".filter_pop .pop_head").innerHeight() + $(".filter_pop .family_box").innerHeight() + $(".filter_pop .btn_area").outerHeight(true) + 64)
            $("body").addClass("stop_scroll");
            $("body, html").on('scroll touchmove mousewheel', function (e) {
              e.preventDefault();
            });
          });
        }
      });

      $(".filter_area .filter_pop .close_btn").on("click", function(){
        if(_deviceCondition == "pc"){
          $(this).parents(".list_area").removeClass("high_index");
          $(".filter_pop").fadeOut();
        }else if(_deviceCondition == "mobile"){
          $(".filter_pop").stop(true, true).slideUp(300, function(){
            $("body").removeClass("stop_scroll");
            $("body, html").off("scroll touchmove mousewheel");
            $(".dimd").fadeOut();
            $(this).parents(".list_area").removeClass("high_index");
          });
        }
      });

      $(".filter_area .filter_pop .pop_cont .family_box .family").each(function(){
        $(this).on("click", function(){
          if(!$(this).hasClass("on")){
            $(this).addClass("on");
          }else{
            $(this).removeClass("on");
          }
        });
      });

      // 탭 공통 기능
      $(".tab_func_area").each(function(){
        $(this).find(".tab_btn_area .btn").each(function(q){
          $(this).on("click", function(){
            if(!$(this).hasClass("on")) {
              $(this).parents(".tab_func_area").find(".tab_btn_area .btn").removeClass("on");
              $(this).addClass("on");
              $(this).parents(".tab_func_area").find(".tab_con_area > .con").removeClass("on");

              // 전체 탭 없는 경우
              if(!$(".tab_func_area .tab_btn_area .btn").hasClass("total")) {
                $(this).parents(".tab_func_area").find(".tab_con_area > .con").eq(q).addClass("on");
              } else {
                // 전체 탭 있는 경우 ex) CLS-01-005
                if($(this).not(".total").hasClass("on")) {
                  $(this).parents(".tab_func_area").find(".tab_con_area > .con").eq(q-1).addClass("on");
                }
              }
            }

            fixedMobileH();

            dropDownRecall();
          });
        });
      });

      // 앵커 이동 공통 기능
      $(".anchor_func_area").each(function() {
        $(this).find(".anchor_btn_area .anchor_btn").each(function(q) {
          $(this).on("click", function() {
            var offset = $(this).parents(".anchor_func_area").find(".anchor_con").eq(q).offset()
            gsap.to($("html, body"), 0.5, {scrollTop: offset.top - $( ".anchor_btn_area").innerHeight() + 1, ease: Power3.easeOut});

            setTimeout(function(){
              if($(".page_title_area").hasClass("fixed") && !$(".page_title_area").hasClass("up")){
                gsap.to($("html, body"), 0.5, {scrollTop: offset.top - $( ".anchor_btn_area").innerHeight() - $(".page_title_area.fixed").innerHeight() + 1, delay:0.1, ease: Power3.easeOut});
              }
            }, 100)
          });
        });
      });

      // 아코디언
      accorRecall();

      // 곽재선의 창 공유하기
      $(".share_area .share_btn").on("click", function(){
        if(!$(this).parents(".share_area").hasClass("on")){
          $(this).parents(".share_area").addClass("on");
          $(this).parents(".share_area").find(".share_wrap").animate({width: 'toggle'});
        }else{
          $(this).parents(".share_area").removeClass("on");
          $(this).parents(".share_area").find(".share_wrap").animate({width: 'toggle'});
        }
      });
      
      // 곽재선의 창 목록 첫 로딩 시 active 붙게
      $(".gjs_page .onelist_area").addClass("active");

      // 곽재선의 창 하단 리스트 버튼
      if($(".ceo_view_con").size() > 0){
        $(".ceo_view_con .txt_area .list_btn_w .btn_div").each(function(){
        $(this).on("mouseenter", function(){
            if(window.innerWidth > 1023 ){
              $(this).parents(".list_btn").addClass("on");
            }
          });

          $(this).on("mouseleave", function(){
            if(window.innerWidth > 1023 ){
              $(".ceo_view_con .txt_area .list_btn_w .list_btn").removeClass("on");
            }
          });
        });
      }

      //검색창 스크롤 모션
      if($(".list_info_div").size() > 0){
        $(".info_div").addClass("active");
      }
    },
    scrollFn: function(){
      // not IE
      $(window).on("scroll", function(){
        _isScrollTop =  $(window).scrollTop();

        if(_isScrollTop > _this_scroll) { // down
          if(_isScrollTop > 0){
            if($("header").size() > 0){
              $("header").addClass("decrease");
            }
          }

          if(_isScrollTop > window.innerHeight / 2){
            $("header").addClass("hide");
            $("header").removeClass("up_scroll");

            // ESG 경영 tab 관련
            if($(".management_page").size() > 0) {
              if($("header").hasClass("hide")){
                $(".tab_box").removeClass("show");
              }
            }
          }
        }

        if(_isScrollTop < _this_scroll) { // up
          $("header").removeClass("hide");
          $("header").addClass("up_scroll");
        }
        
        _this_scroll = _isScrollTop;

        if(_isScrollTop == 0){
          $("header").removeClass("up_scroll");

          if(!$("header").hasClass("mouse_hover")){
            $("header").removeClass("decrease");
          }
        }

        // 앵커 이동 공통 기능
        if($(".anchor_func_area").size() > 0) {
          if(window.innerWidth <= 1024) {
            $(".anchor_func_area .anchor_con").each(function(q) {
              if(_isScrollTop > $(this).offset().top - $( ".anchor_btn_area").innerHeight() - $(".page_title_area.fixed").innerHeight() - 1) {
                $(".anchor_btn_area .anchor_btn").removeClass("on");
                $(".anchor_btn_area .anchor_btn").eq(q).addClass("on")
              }
              if(_isScrollTop + 100 >= $("#wrap").innerHeight() - $(window).innerHeight()){
                $(".anchor_btn_area .anchor_btn").removeClass("on");
                $(".anchor_btn_area .anchor_btn").eq(-1).addClass("on")
              }
            });
          }
        }

        // 탑버튼
        repositioningTopBtn();
      });
      $(window).scroll();

      // for IE
      $("body").on("scroll", function(){
        
      });

      // scrollMotion
      scrollMotionTrigger();
    },
    swiperFn: function(){
      if($(".main_pop_swiper").size() > 0){
        if($(".main_pop_swiper .swiper-slide").length > 1){
          $(".main_pop_swiper .swiper-container").after($('<div class="swiper-button-prev"></div>'));
          $(".main_pop_swiper .swiper-container").after($('<div class="swiper-button-next"></div>'));
          
          _mainPopSwiper = new Swiper(".main_pop_swiper .swiper-container", {
            observer: true,
            observeParents: true,
            navigation: {
              nextEl: ".main_pop_swiper .swiper-button-next",
              prevEl: ".main_pop_swiper .swiper-button-prev",
            },
            pagination: {
              el: ".main_pop_swiper .swiper-pagination",
              type: "fraction",
            },
            touchReleaseOnEdges: true,
            loop:true,
          });

          $(".main_pop_swiper").hover(function(){
            $(".main_pop_swiper").find(".swiper-button-next, .swiper-button-prev").fadeIn(250);
          }, function(){
            $(".main_pop_swiper").find(".swiper-button-next, .swiper-button-prev").fadeOut(250);
          });
        }else{
          _mainPopSwiper = new Swiper(".main_pop_swiper .swiper-container", {
            observer: true,
            observeParents: true,
            touchReleaseOnEdges: true,
            pagination: {
              el: ".main_pop_swiper .swiper-pagination",
              type: "fraction",
            },
          });
        }
      }

      // 뉴스룸 swiper
      if($(".divide_swiper").size() > 0){
        var imgSwiper = new Swiper(".img_swiper .swiper-container", {
          observer: true,
          observeParents: true,
          watchOverflow : true,
          pagination: {
            el: ".divide_swiper .swiper-pagination",
            type: "fraction",
          },
          // navigation: {
          //   nextEl: ".divide_swiper .swiper-button-next",
          //   prevEl: ".divide_swiper .swiper-button-prev",
          // },
          // loop:true,
          on: {
            slideChange:function(){
              txtSwiper.slideTo(imgSwiper.realIndex)
            }
          }
        });

        var txtSwiper = new Swiper(".txt_swiper .swiper-container", {
          observer: true,
          observeParents: true,
          watchOverflow : true,
          pagination: {
            el: ".divide_swiper .swiper-pagination",
            type: "fraction",
          },
          navigation: {
            nextEl: ".divide_swiper .swiper-button-next",
            prevEl: ".divide_swiper .swiper-button-prev",
          },
          // loop:true,
          on: {
            slideChange:function(){
              imgSwiper.slideTo(txtSwiper.realIndex)
            }
          }
        });
      }

      // 소셜미디어 swiper
      if($(".center_swiper").size() > 0){
        if($(".center_swiper .swiper-slide").size() > 1){
          var centerSwiper = new Swiper(".center_swiper .swiper-container", {
            observer: true,
            observeParents: true,
            watchOverflow : true,
            pagination: {
              el: ".center_swiper .swiper-pagination",
              type: "fraction",
            },
            navigation: {
              nextEl: ".center_swiper .swiper-button-next",
              prevEl: ".center_swiper .swiper-button-prev",
            },
            roundLengths: true,
            loop:true,
            centeredSlides: true,
            slidesPerView: "auto",
            on:{
              slideChangeTransitionStart:function(){
                imgResizingFn()
              }
            }
          });
        }else{
          var centerSwiper = new Swiper(".center_swiper .swiper-container", {
            observer: true,
            observeParents: true,
            watchOverflow : true,
            pagination: {
              el: ".divide_swiper .swiper-pagination",
              type: "fraction",
            },
            navigation: {
              nextEl: ".divide_swiper .swiper-button-next",
              prevEl: ".divide_swiper .swiper-button-prev",
            },
            slidesPerView: "auto",
            centeredSlides: true,
          });
        }
      }
    },
    resizeFn: function(){
      $(window).resize(function(){

        if($(".layer_popup").is(":visible")){
          $(".layer_popup .pop_wrap .pop_cont").css("min-height", window.innerHeight - ($(".layer_popup .pop_wrap").innerHeight() - $(".layer_popup .pop_wrap").height()))
        }

        
        // 탑버튼
        repositioningTopBtn();
        
        // 이미지 리사이징
        imgResizingFn();

        // 플로우 텍스트
        flowTxt();

        // 해상도 따른 pc, mobile 구분
        if(window.innerWidth > 1023 ){ // pc
          _deviceCondition = "pc";

          if($(".form_select_div .list_wrap").is(":visible")){
            $("body").removeClass("stop_scroll")
          }

          // 헤더 pc버전
          $("header").addClass("pc");
          if($("header").hasClass("mobile")){
            $("header .header_dimd").hide()
            $("header nav").removeClass("on");
            $("header .gnb > li").removeClass("on opacity");
            $("header").removeClass("mobile");
            $("header nav .two_pack").removeAttr("style");
          }
          $("header .gnb_w").removeAttr("style");

          if($(".mobile_acco_div").size() > 0) {
            $(".accordion_type").each(function() {
              $(this).find(".list_div").removeClass("on");
              $(this).parents(".mobile_acco_div").addClass("open");
              $(this).find(".hide_con").removeAttr("style");

              if(!$(this).parents(".mobile_acco_div").hasClass("first") && $(".white_inner").size() > 0){
                $(this).parents(".mobile_acco_div").addClass("first");
              }
            });
          }

          // 필터 팝업 리사이징 할 때
          if($(".list_area").hasClass("high_index")){
            $(".dimd").hide();
            $("body").removeClass("stop_scroll");
            $("body, html").off("scroll touchmove mousewheel");
          }

          if($(".main_popup").is(":visible")){
            $(".main_popup .pop_wrap").removeAttr("style")
          }

        }else{ // mobile
          _deviceCondition = "mobile";

          // 헤더 mobile버전
          $("header").addClass("mobile");
          if($("header").hasClass("pc")){
            $("header .gnb > li").removeClass("on opacity");
            $("header").removeClass("pc mouse_hover gnb_hover drop_down");
            $("header nav .two_pack").removeAttr("style");
          }
          $("header .gnb_w").removeAttr("style");

          if($(".form_select_div .list_wrap").is(":visible")){
            $("body").addClass("stop_scroll")
          }

          // 모바일 아코디언
          if($(".mobile_acco_div").size() > 0) {// 모바일 (ex: CLS-01-009)
            $(".accordion_type").each(function() {
              $(this).parents(".mobile_acco_div").removeClass("open");

              if($(".white_inner").size() > 0){
                if($(this).parents(".mobile_acco_div").hasClass("first")){
                  $(this).find(".list_div").find(".hide_con").hide();
                  $(this).parents(".mobile_acco_div").removeClass("first");
                }
              }
            });
          }
          

          // 필터 팝업 리사이징 할 때
          if($(".list_area").hasClass("high_index")){
            $(".dimd").show();
            $("body").addClass("stop_scroll");
            $("body, html").on('scroll touchmove mousewheel', function (e) {
              e.preventDefault();
            });
            
            setTimeout(function(){
              console.log($(".filter_area .filter_pop").height())
              console.log($(".filter_pop .pop_head").innerHeight() + $(".filter_pop .family_box").innerHeight() + $(".filter_pop .btn_area").outerHeight(true) + 64)
              if($(".filter_area .filter_pop").innerHeight() > $(".filter_pop .pop_head").innerHeight() + $(".filter_pop .family_box").innerHeight() + $(".filter_pop .btn_area").outerHeight(true) + 48){
                $(".filter_pop").css("height", "auto")
              }else{
                $(".filter_pop").innerHeight($(".filter_pop .pop_head").innerHeight() + $(".filter_pop .family_box").innerHeight() + $(".filter_pop .btn_area").outerHeight(true) + 48)
              }
            },400)
            
          }

          if($(".main_popup").is(":visible")){
            $(".main_popup .pop_wrap").height($(".main_popup .pop_wrap .pop_cont .swiper-container").height() + $(".main_popup .pop_wrap .pop_cont .bottom_area").innerHeight())
          }
        }
        
        // 팝업 리사이즈
        // popupResize();

        // 콘텐츠 영역 최소높이값 설정
        setTimeout(function(){
          $(".cont_wrap, .cont_inner").css("min-height", window.innerHeight - $("footer").innerHeight());
          repositioningTopBtn();
        }, 100);


        // if($(".layer_popup").is(":visible")){
        //   $("body").addClass("stop_scroll");
        //   $("body, html").on('scroll touchmove mousewheel', function (e) {
        //     e.preventDefault();
        //   });
        //   $("#wrap").addClass("stop_scroll");
        // }
      }).resize();
    },
    formChkFn: function(){
      // select (focus 여부에 따른 화살표 전환 위한 스크립트)
      /*$(".form_select_arr").each(function(q){
        $(this).find("select").focus(function(){
          $(this).parents(".form_select").addClass("on");

          console.log("focus");
        });

        $(this).find("select").blur(function(){
          $(this).data('isopen', false);
          $(this).parents(".form_select").removeClass("on");
          if($(this).parents(".has_error_msg").size() > 0){
            if($(this).val() == ""){
              $(this).parents(".form_select, .has_error_msg").addClass("error");
            }else{
              $(this).parents(".form_select").removeClass("error");
              if($(this).closest(".has_error_msg").find(".error").size() == 0){
                $(this).parents(".has_error_msg").removeClass("error");
              }
            }
          }
        });

        $(this).find("select").on("change", function(){
          $(this).parents(".form_select").removeClass("on");
          if($(this).parents(".has_error_msg").size() > 0){
            if($(this).val() == ""){
              $(this).parents(".form_select, .has_error_msg").addClass("error");
            }else{
              $(this).parents(".form_select").removeClass("error");
              if($(this).closest(".has_error_msg").find(".error").size() == 0){
                $(this).parents(".has_error_msg").removeClass("error");
              }
            }
          }
        });
        
        $(this).find("select").mouseup(function(e) {
          var open = $(this).data("isopen");
    
          if(open) {
            $(this).parents(".form_select").removeClass("on");
            if($(this).parents(".has_error_msg").size() > 0){
              if($(this).val() == ""){
                $(this).parents(".form_select, .has_error_msg").addClass("error");
              }else{
                $(this).parents(".form_select").removeClass("error");
                if($(this).closest(".has_error_msg").find(".error").size() == 0){
                  $(this).parents(".has_error_msg").removeClass("error");
                }
              }
            }
          } else {
            $(this).parents(".form_select").addClass("on");
          }
          $(this).data("isopen", !open);
        });
      });*/

      // input checkbox (label 태그 내부에 텍스가 없을 시)
      $(".form_checkbox").each(function(q){
        if($(this).find("label").html() == ""){
          $(this).addClass("no_txt")
        };
      });

      $(".form_radio").each(function(q){
        if($(this).find("label").html() == ""){
          $(this).addClass("no_txt")
        }
      });

      // 검색 박스 지우기 버튼 노출/미노출
      $(".form_search, .form_input").each(function(q){
        
        $(this).off().on("focusin", function() {
          $(this).addClass("focus");
          if($(this).find("input").val() !== ""){
            $(this).find(".btn_delete").show();

            if($(this).parents(".price_input_wrap").find(".form_input").hasClass("focus")) {
              $(this).parents(".btn").removeClass("on");
            }
          }
        });

        $(this).find("input").off().on("focusout", function(){
          $(this).parents(".form_search, .form_input").removeClass("focus")
          setTimeout(function(){
            $(".form_search, .form_input").eq(q).find(".btn_delete").hide();
          }, 150)
        })

        $(this).find(".btn_delete").off().on("click", function(){
          $(this).hide();
          $(this).parents(".input_btn_wrap").siblings("input").val("").focus();
          if($(".change_input_box").size() > 0){
            $(".change_input_box").find(".s_color_btn").addClass("disabled");
          }
        })

        $(this).find("input").on("keyup", function() {
          if(!$(this).is('[readonly]')){
            if($(this).val() !== "") {
              $(this).closest(".form_search, .form_input").find(".btn_delete").show();
              if($(".filter_btn_list").size() > 0){
                $(this).parents(".filter_btn_list").find(".btn").removeClass("on");
                $(this).parents(".btn").addClass("on");
                $(this).parents(".list_div").find(".txt_box").removeClass("hide");
                
              }
            } else {
              $(this).closest(".form_search, .form_input").find(".btn_delete").hide();
            }
          }
        });
      });

      $(".form_textarea").each(function(){
        $(this).find("textarea").on("focusin", function(){
          $(this).parents(".form_textarea").addClass("focus")
        });

        $(this).find("textarea").on("focusout", function(){
          $(this).parents(".form_textarea").removeClass("focus")
        });
      });

      // 셀렉트 박스 첫번째 값 제외 선택한 경우
      $(".form_select_div").each(function(){
        $(this).find(".btn_open").off("click").on("click", function(){
          if(!$(this).parents(".form_select_div").hasClass("on")){
            _pageScrollOffset = window.pageYOffset;

            $(".form_select_div").find(".list_wrap").stop(true, true).slideUp();
            $(".form_select_div").removeClass("on");
            $(this).parents(".form_select_div").addClass("on");
            $(this).parents(".form_select_div").find(".list_wrap").stop(true, true).slideDown();

            if($(this).parents().hasClass("layer_popup")){
              $(this).parents(".layer_popup").find(".scroll_area").addClass("hidden");
            }
            
            if(_deviceCondition == 'mobile'){
              $("body").addClass("stop_scroll");
              $("body, html").on('scroll touchmove mousewheel', function (e) {
                e.preventDefault();
              });
            }
          }else{
            $(this).parents(".form_select_div").removeClass("on");
            $(this).parents(".form_select_div").find(".list_wrap").stop(true, true).slideUp();

            if($(this).parents().hasClass("layer_popup")){
              $(this).parents(".layer_popup").find(".scroll_area").removeClass("hidden");
            }
            if($(this).parents(".layer_popup").size() <= 0){
              $("body").removeClass("stop_scroll");
              $("body, html").off("scroll touchmove mousewheel");
              $("#wrap").removeClass("stop_scroll");
            }
          }
        });

        $(this).find(".list_wrap .btn_link").each(function(q){
          $(this).off("click").on("click", function(){
            if($(this).parents(".scroll_area").hasClass("hidden")){
              $(this).parents(".scroll_area").removeClass("hidden");
            }
            if($(this).parents(".layer_popup").size() <= 0){
              $("body").removeClass("stop_scroll");
            }
            if($(this).parents(".form_select_div.disabled").size() <= 0){
              if($(this).parents(".discount_area").size() <= 0){
                $(this).parents(".form_select_div").find(".open_area .btn_open").html($(this).html());
                $(this).parents(".form_select_div").find(".select").html($(this).html());

                if(!$(this).hasClass("default")){
                  $(this).parents(".form_select_div").addClass("change");
                  $(this).parents(".list_wrap").find(".btn_link").removeClass("on")
                  $(this).addClass("on")
                }else{
                  $(this).parents(".list_wrap").find(".btn_link").removeClass("on")
                  $(this).parents(".form_select_div").removeClass("change");
                }
              }
              

              $(this).parents(".form_select_div").removeClass("on");
              $(this).parents(".form_select_div").find(".list_wrap").stop(true, true).slideUp();
            }
          });
        });

        $(this).find(".dimd").off("click").on("click", function(){
          if($(this).parents(".layer_popup").size() <= 0){
            $("body").removeClass("stop_scroll");
            $("#wrap").removeClass("stop_scroll");
          }
          if($(this).parents(".scroll_area").hasClass("hidden")){
            $(this).parents(".scroll_area").removeClass("hidden");
          }
          $(this).parents(".form_select_div").removeClass("on");
          $(this).parents(".form_select_div").find(".list_wrap").stop(true, true).slideUp();
        });

        $(this).find(".close").off("click").on("click", function(){
          if($(this).parents(".layer_popup").size() <= 0){
            $("body").removeClass("stop_scroll");
            $("#wrap").removeClass("stop_scroll");
          }

          if($(this).parents().hasClass("layer_popup")){
            $(this).parents(".layer_popup").find(".scroll_area").removeClass("hidden");
          }

          $(this).parents(".form_select_div").removeClass("on");
          
          $(this).parents(".form_select_div").find(".list_wrap").stop(true, true).slideUp();
        });
      });
    },
    scrollTriggerFn:function(){
      // 곽재선의 창 상세
      if($(".ceo_view_con").size() > 0){
        ScrollTrigger.matchMedia({
          "(min-width: 1024px)": function() {
            gsap.to($(".ceo_view_con .img_area"), {
              scrollTrigger: {
                trigger: $(".ceo_view_con .img_area"),
                start: "top top",
                endTrigger: $(".ceo_view_con").find(".inner"),
                end:"bottom bottom",
                pin: true,
                // invalidateOnRefresh: true,
                // markers:true,
                scrub: true,
                onLeave: function(self){
                  gsap.set($(".ceo_view_con .img_area"), {width:100 - (self.progress.toFixed(3) * 60) + "%", ease: Power2.easeOut});
                },
                onEnterBack: function(self){
                  gsap.set($(".ceo_view_con .img_area"), {width:100 - (self.progress.toFixed(3) * 60) + "%", ease: Power2.easeOut});
                }
              },
            });
            
            gsap.to($(".ceo_view_con .img_area"), {
              scrollTrigger: {
                trigger: $(".ceo_view_con .img_area .img_wrap"),
                // start: "top+=100% top",
                start: "top+=90% top",
                // end:"+=85%",
                end:"+=75%",
                scrub: true,
                // markers: true,
                onUpdate: function(self){
                  if(window.innerWidth >= 1024) {
                    gsap.to($(".ceo_view_con .img_area"), 0.5, {width:100 - (self.progress.toFixed(3) * 60) + "%", ease: Power2.easeOut});
                  }
                },
              },
            });
          },
        })
      }

      // 경영철학, 핵심가치 공통 모션
      if($(".move_img_txt").size() > 0) {
        if($(".philo_page").size() > 0){
          gsap.to(".deco_area .tit", {
            yPercent: 30,
            scrollTrigger: {
              trigger: ".deco_area",
              start: 'top-=240px top',
              end: "bottom top",
              scrub: 1,
              // markers: true,
            },
          });
        } else if($(".core_value_page").size() > 0){
          gsap.to(".deco_area .tit", {
            yPercent: -30,
            scrollTrigger: {
              trigger: ".deco_area",
              start: 'top-=240px top',
              end: "bottom top",
              scrub: 1,
              // markers: true,
            },
          });
          ScrollTrigger.matchMedia({
            "(min-width: 1024px)": function() {
              gsap.to(".deco_area > .f_t4", {
                yPercent: -200,
                scrollTrigger: {
                  trigger: ".deco_area",
                  start: 'top-=240px top',
                  end: "bottom top",
                  scrub: 1,
                  // markers: true,
                },
              });
            },
            "(max-width: 1023px)": function() {
              gsap.to(".deco_area > .f_t4", {
                yPercent: -150,
                scrollTrigger: {
                  trigger: ".deco_area",
                  start: 'top-=240px top',
                  end: "bottom top",
                  scrub: 1,
                  // markers: true,
                },
              });
            },
          })
        }
        gsap.to(".deco_area .visual_box", {
          yPercent: -50,
          scrollTrigger: {
            trigger: ".deco_area",
            start: 'top-=240px top',
            end: "bottom top",
            scrub: 1,
            // markers: true,
          },
        });
      }

      // 경영철학 모션
      if($(".philo_page").size() > 0) {
        gsap.to($(".slogan_area .visual_box"), {
          width: "100%",
          scrollTrigger: {
            trigger: ".slogan_area",
            start: "top bottom",
            end:"bottom bottom",
            scrub: 1,
            // markers:true,
          },
        });
      }

      // KG가족 공통 모션
      if($(".kg_family_area").size() > 0){
        $(".kg_family_area .company_div").eq(0).addClass("active");

        const sections = document.querySelectorAll('.company_wrap');
        sections.forEach((section) => {
          const sectionIndex = Array.from(sections).indexOf(section);
          gsap.from(section, {
            ease: 'power3.easeOut',
            scrollTrigger: {
              trigger: section,
              start: 'top-=240px top',
              end: 'bottom center',
              // scrub: true,
              // markers: true,
              // pin:true,
              onEnter: () => {
                gsap.to($(".kg_family_area .company_wrap").eq(sectionIndex).find(".company_div .img"), 1, {width:"100%", ease:Power3.easeOut})
                // gsap.to($(".kg_family_area .company_wrap").eq(sectionIndex).find(".company_div .title"), 1, {opacity:0, ease:Power3.easeOut})
              }
            },
          });
        });
      }


      // 장학재단 & 문화재단 공통 모션
      // if($(".foundation_page").size() > 0){
      //   gsap.to(".foundation_info .img_box", {
      //     width: "100%",
      //     ease: Power2.easeOut,
      //     scrollTrigger: {
      //       trigger: ".foundation_info .img_box",
      //       start: "top-=80% top",
      //       // markers: true,
      //       // scrub: 1,
      //     },
      //   });
      // }


      //ceo 인사말 motion
      if($(".ceo_infor_page").size() > 0) {
        ScrollTrigger.matchMedia({
          "(min-width: 1024px)": function() {
            gsap.to(".page_cont .ig_area", .8, {y: 0});
            // gsap.to(".page_cont .ig_cover", .8, {height: 0});
            // gsap.to(".page_cont .txt_area h3", .8, {opacity: 1, y: 0, delay: .8});
            // gsap.to(".page_cont .txt_area .sub_txt", .8, {opacity: 1, y: 0, delay: 1.6});
            // gsap.to(".page_cont .line", .8, {width: "100%", delay: 1.6});
            gsap.to(".page_cont .ig_area", {
              yPercent: 40,
              scrollTrigger: {
                trigger: ".page_cont .ig_area",
                start: "-10% top",
                end: "70% 40%",
                scrub: 1,
                // markers: true,
              },
            });
          },
          "(max-width: 1023px)": function() {
            gsap.to(".page_cont .ig_area", .8, {y: 0});
            // gsap.to(".page_cont .txt_area h3", .8, {opacity: 1, y: 0});
            // gsap.to(".page_cont .txt_area .sub_txt", .8, {opacity: 1, y: 0, delay: .8});
            // gsap.to(".page_cont .line", .8, {width: "100%", delay: .8});
            // gsap.to(".page_cont .ig_area", .8, {
            //   y: 0,
            //   scrollTrigger: {
            //     trigger: ".page_cont .ig_area",
            //     start: "top bottom",
            //     // markers :true
            //   },
            // })
            // gsap.to(".page_cont .ig_cover", .8, {
            //   height: 0,
            //   scrollTrigger: {
            //     trigger: ".page_cont .ig_area",
            //     start: "top bottom",
            //     // markers: true

            //   },
            // })
            // gsap.to(".page_cont .ig_area", .7, {y: 0, delay: 1.4});
            // gsap.to(".page_cont .ig_cover", .7, {height: 0, delay: 1.4});
          }
        })
      }

      //ci motion
      if($(".ci_page").size() > 0) {
        let pageWidth = $(".cont_wrap").outerWidth();

        gsap.to(".page_cont .left_wall", 1.2, {width: 0});
        gsap.to(".page_cont .right_wall", 1.2, {width: 0});

        if ((pageWidth + 17) >= 1024) {
          gsap.to(".page_cont .top_line", .8, {width: "calc(100% - 220rem)", delay: 1.2});
          gsap.to(".page_cont .middle_line", .8, {width: "calc(100% - 220rem)", delay: 1.2});
          gsap.to(".page_cont .bottom_line", .8, {width: "calc(100% - 220rem)", delay: 1.2});
          gsap.to(".page_cont .left_line", .8, {height: "200rem", delay: 1.2});
          gsap.to(".page_cont .right_line", .8, {height: "200rem", delay: 1.2});
        } else {
          gsap.to(".page_cont .top_line", .8, {width: "100%", delay: 1.2});
          gsap.to(".page_cont .middle_line", .8, {width: "100%", delay: 1.2});
          gsap.to(".page_cont .bottom_line", .8, {width: "100%", delay: 1.2});
          gsap.to(".page_cont .left_line", .8, {height: "calc(100% + 20rem)", delay: 1.2});
          gsap.to(".page_cont .right_line", .8, {height: "calc(100% + 20rem)", delay: 1.2});
        }

        // gsap.to(".page_cont .top_line", .8, {width: 0, delay: 1.6});
        // gsap.to(".page_cont .middle_line", .8, {width: 0, delay: 1.6});
        // gsap.to(".page_cont .bottom_line", .8, {width: 0, delay: 1.6});
        // gsap.to(".page_cont .left_line", .8, {height: 0, delay: 1.6});
        // gsap.to(".page_cont .right_line", .8, {height: 0, delay: 1.6});
        setTimeout(function() {$(".ci_page h3").addClass("active")}, 1600);

        if ((pageWidth + 17) >= 600) {
          $(".slogan_area").addClass("scroll_motion");
          $(".ci_area").addClass("scroll_motion");
          $(".page_cont").removeClass("scroll_motion");
        } else {
          $(".slogan_area").removeClass("scroll_motion");
          $(".ci_area").removeClass("scroll_motion");
          $(".page_cont").addClass("scroll_motion");
        }

        $(window).resize(function() {
          pageWidth = $(".cont_wrap").outerWidth();
          gsap.set(".ci_page .color_area", {y: "80rem",  opacity: 0});
          gsap.set(".ci_page .color_area .color_chip .color_txt", {y: "50rem",  opacity: 0});

          if ((pageWidth + 17) >= 600) {
            $(".slogan_area").addClass("scroll_motion");
            $(".ci_area").addClass("scroll_motion");
            $(".page_cont").removeClass("scroll_motion");
          } else {
            $(".slogan_area").removeClass("scroll_motion");
            $(".ci_area").removeClass("scroll_motion");
            $(".page_cont").addClass("scroll_motion");
          }
          textMotion();
        })

        function textMotion() {
          let textTop = gsap.utils.toArray(".scroll_motion");
          textTop.forEach((textTop, idx) => {
            gsap.to(textTop, .8, {
              y: 0,
              opacity: 1,
              scrollTrigger: {
                trigger: textTop,
                start: "top 80%",
                // markers: true
              },
              onComplete: () => {
                pageWidth = $(".cont_wrap").outerWidth();
                if ((pageWidth + 17) >= 600) {
                  if(idx == 5) {
                    ScrollTrigger.matchMedia({
                      "(min-width: 1024px)": function() {
                        gsap.set(".ci_page .color_area .color_chip .color_txt", {y: "50rem",  opacity: 0})

                        gsap.to(".ci_page .color_area .color_chip.violet", .8, {left: "calc(25% - 41rem)"});
                        gsap.to(".ci_page .color_area .color_chip.magenta", .8, {left: "calc(50% - 81rem)"});
                        gsap.to(".ci_page .color_area .color_chip.orange", .8, {right: "calc(25% - 41rem)"});
                        gsap.to(".ci_page .color_area .color_chip.yellow", .8, {right: 0})
                        gsap.to(".ci_page .color_area .color_chip .color_txt", .8, {y: 0,  opacity: 1, delay: .8})
                      },
                      "(min-width: 600px) and (max-width: 1023px)": function() {
                        gsap.set(".ci_page .color_area .color_chip .color_txt", {y: "50rem",  opacity: 0})

                        gsap.to(".ci_page .color_area .color_chip .color_txt", .8, {y: 0,  opacity: 1})
                      }
                    })
                  }
                } else {
                  if(idx == 4) {
                    ScrollTrigger.matchMedia({
                      "(max-width: 600px)": function() {
                        gsap.to(".ci_page .color_area .color_chip .color_txt", .8, {y: 0,  opacity: 1})
                      }
                    })
                  }
                }
              },
            })
          }) 
        }
        
        textMotion();
      }

      //heritage motion
      if($(".heritage_page").size() > 0) {
        // gsap.to($(".main_visual h3"), .8, {y: 0, opacity: 1, delay: .8})
        // gsap.to($(".main_visual .main_desc_txt"), .8, {y: 0, opacity: 1, delay: 1.6})


        //섹션 처음 움직이는 이미지 모션
        let moveAreas = gsap.utils.toArray(".year_area .img_move_area");
        moveAreas.forEach((moveArea) => {
          ScrollTrigger.matchMedia({
            "(min-width: 1024px)": function() {
              gsap.to($(moveArea).find(".left_txt"), .8, {
                top: "30rem",
                scrollTrigger: {
                  trigger: moveArea,
                  start: "-200% top",
                  end: "+=110%",
                  scrub: 1,
                  // markers: true
                },
              })
              gsap.to($(moveArea).find(".left_ig_wrap"), .8, {
                top: "350rem",
                scrollTrigger: {
                  trigger: moveArea,
                  start: "-200% top",
                  end: "+=280%",
                  scrub: .5,
                  // markers: true
                },
              })
              gsap.to($(moveArea).find(".middle_ig_wrap"), .8, {
                top: "-40rem",
                scrollTrigger: {
                  trigger: moveArea,
                  start: "-200% top",
                  end: "+=110%",
                  scrub: 1,
                  // markers: true
                },
              })
              gsap.to($(moveArea).find(".right_ig_wrap"), .8, {
                bottom: "0rem",
                scrollTrigger: {
                  trigger: moveArea,
                  start: "-200% top",
                  end: "+=180%",
                  scrub: 1,
                  // markers: true
                },
              })
            },
          })
        }) 

        
        //앵커탭 모션 ,년도 ,텍스트 투명도 모션
        // let lastY = 0;
        let windowTop;
        let nextList;
        let numHeight;
        let listOn;
        let YearText;
        let $changeYear;
        let pageHeight; 
        let pageWidth; 
        let lastIndex = $(".year_area").length - 1;
        const $anchorBtn = $(".year_tab a");
        let yearAreas = gsap.utils.toArray(".year_area");
        yearAreas.forEach((yearArea, idx) => {
          $(window).on("scroll", function() {
            windowTop = $(window).scrollTop();
            pageHeight = $(".cont_wrap").outerHeight();
            pageWidth = $(".cont_wrap").outerWidth();
            // console.log(pageWidth + 17);

            if($(yearAreas).eq(idx).find(".img_move_area").offset().top < windowTop) {
              if ((pageWidth + 17) >= 1024) {
                $(yearArea).find(".change_year").addClass("active");
              }
            }

            if(($(".main_visual").offset().top + $(".main_visual").innerHeight() / 2) < windowTop) {
              if ((pageWidth + 17) >= 1024) {
                $(".year_tab").addClass("block");
              }
            } else {
              if ((pageWidth + 17) >= 1024) {
                $(".year_tab").removeClass("block");
              }
            }

            if (pageHeight < windowTop + $(window).innerHeight()) {
              if ((pageWidth + 17) >= 1024) {
                $(".year_tab").removeClass("block");
              }
            }

            if(pageWidth + 17 < 1024) {
              if(idx == 0 && ($(".year_area").eq(0).offset().top) <= windowTop) {
                $(".heritage_page .year_tab").addClass("go_fix");
              } else if (($(".year_area").eq(0).offset().top) > windowTop) {
                $(".heritage_page .year_tab").removeClass("go_fix");
              }
            }

            if($(".year_area").eq(lastIndex).find(".fix_list").offset().top < windowTop) {
              $(".heritage_page .year_tab").removeClass("go_fix");
            }

            // $anchorBtn.removeClass("on");
            if($(window).scrollTop() > $(".year_area").eq(0).offset().top && $(window).scrollTop() <= $(".year_area").eq(1).offset().top) {
              $anchorBtn.removeClass("on");
              $(".year_tab a").eq(0).addClass("on");
            } else if($(window).scrollTop() >  $(".year_area").eq(1).offset().top && $(window).scrollTop() <=  $(".year_area").eq(2).offset().top) {
              $anchorBtn.removeClass("on");
              $(".year_tab a").eq(1).addClass("on");
            } else if($(window).scrollTop() >  $(".year_area").eq(2).offset().top) {
              $anchorBtn.removeClass("on");
              $(".year_tab a").eq(2).addClass("on");
            }
          })
          ScrollTrigger.matchMedia({
            "(max-width: 599px)": function() {
              gsap.set(".middle_ig_wrap",{ y: "40rem", opacity: 0});
              gsap.set(".left_ig_wrap",{ top: "176rem", opacity: 0});
              gsap.set(".right_ig_wrap",{ top: "176rem", opacity: 0});

              gsap.to($(yearArea).find(".img_move_area"), {
                scrollTrigger: {
                  trigger: $(yearArea).find(".img_move_area"),
                  start: "-60% top",
                  end() {
                    return "+=".concat(($(".year_area").eq(idx).find(".fix_list").height()) + ($(".year_area").eq(idx).find(".img_move_area").height() * 1.7));
                  },
                  // markers: true,
                  onEnter: function() {
                    gsap.to($(yearArea).find(".middle_ig_wrap"), .4, {y: 0, opacity: 1});
                    gsap.to($(yearArea).find(".left_ig_wrap"), .4, {top: "136rem", opacity: 1, delay: .4});
                    gsap.to($(yearArea).find(".right_ig_wrap"), .4, {top: "164rem", opacity: 1, delay: .8});
                  },
                  // onEnterBack: function() {
                  // },
                  onUpdate: function() {
                    windowTop = $(window).scrollTop();
                    nextList = $(yearAreas).eq(idx + 1);
                    listOn = $(".year_area").eq(idx).find(".fix_list").children(".year_wrap");

                    const arrY = [];
                    $(listOn).each(function(q){
                      arrY.push(parseInt($(listOn).eq(q).offset().top));
                      // console.log(arrY[0]);
                      if((windowTop + ($("header").height() * 3)) > arrY[q]) {
                        $(listOn).find(".year_num").removeClass("color");
                        $(listOn).find(".txt_item").removeClass("color");
                        $(listOn).eq(q).find(".year_num").addClass("color");
                        $(listOn).eq(q).find(".txt_item").addClass("color");
                      }

                    });

                  },
                },
              })
            },
            "(min-width: 600px) and (max-width: 1023px)": function() {
              gsap.set(".middle_ig_wrap",{ y: "80rem", opacity: 0});
              gsap.set(".left_ig_wrap",{ top: "330rem", opacity: 0});
              gsap.set(".right_ig_wrap",{ top: "370rem", opacity: 0});
              
              gsap.to($(yearArea).find(".img_move_area"), {
                scrollTrigger: {
                  trigger: $(yearArea).find(".img_move_area"),
                  start: "-60% top",
                  end() {
                    return "+=".concat(($(".year_area").eq(idx).find(".fix_list").height()) + ($(".year_area").eq(idx).find(".img_move_area").height() * 1.7));
                  },
                  // markers: true,
                  onEnter: function() {
                    gsap.to($(yearArea).find(".middle_ig_wrap"), .6, {y: 0, opacity: 1});
                    gsap.to($(yearArea).find(".left_ig_wrap"), .6, {top: "250rem", opacity: 1, delay: .6});
                    gsap.to($(yearArea).find(".right_ig_wrap"), .6, {top: "305rem", opacity: 1, delay: 1.2});
                  },
                  // onEnterBack: function() {
                  // },
                  onUpdate: function() {
                    windowTop = $(window).scrollTop();
                    nextList = $(yearAreas).eq(idx + 1);
                    listOn = $(".year_area").eq(idx).find(".fix_list").children(".year_wrap");

                    const arrY = [];
                    $(listOn).each(function(q){
                      arrY.push(parseInt($(listOn).eq(q).offset().top));
                      // console.log(arrY[0]);
                      if((windowTop + ($("header").height() * 4)) > arrY[q]) {
                        $(listOn).find(".year_num").removeClass("color");
                        $(listOn).find(".txt_item").removeClass("color");
                        $(listOn).eq(q).find(".year_num").addClass("color");
                        $(listOn).eq(q).find(".txt_item").addClass("color");
                      }
                    });

                  },
                },
              })
            },
            "(min-width: 1024px)": function() {
              gsap.to($(yearArea).find(".allyear_wrapper .year_num_wrap"), {
                scrollTrigger: {
                  trigger: $(yearArea).find(".allyear_wrapper .year_num_wrap"),
                  start: "-100rem top",
                  end() {
                    return "+=".concat($(".year_area").eq(idx).find(".fix_list").height() - 100);
                  },
                  pin: true,
                  pinSpacing: false,
                  scrub: 1,
                  // markers: true,
                  onLeave: function() {
                    $(yearArea).find(".change_year").addClass("none");
                  },
                  onEnterBack: function() {
                    $(yearArea).find(".change_year").removeClass("none");
                  },
                  onUpdate: function() {
                    windowTop = $(window).scrollTop();
                    nextList = $(yearAreas).eq(idx + 1);
                    numHeight = $(yearAreas).eq(idx).find(".change_year").innerHeight();
                    $changeYear = $(yearAreas).eq(idx).find(".change_year");

                    listOn = $(".year_area").eq(idx).find(".fix_list").children(".year_wrap");

                    const arrY = [];
                    $(listOn).each(function(q){
                      arrY.push(parseInt($(listOn).eq(q).offset().top));
                      // console.log(arrY[0]);
                      if((windowTop + 180) > arrY[q]) {
                        YearText = $(listOn).eq(q).find(".year_num").text();
                        $changeYear.text(YearText);

                        
                        $(listOn).find(".txt_item").removeClass("color");
                        $(listOn).eq(q).find(".txt_item").addClass("color");
                      }
                    });
                  },
                },
              })
            },
          })
        })
        
        $anchorBtn.on("click", function() {
          // $anchorBtn.removeClass("on");
          // $(this).addClass("on");
          gsap.to("html, body", {duration: 0, scrollTop: $(".year_area").eq($(this).index()).offset().top + 1, ease: Power3.easeOut});

          pageWidth = $(".cont_wrap").outerWidth();
          $changeYear = $(".year_area").eq($(this).index()).find(".change_year");
          listOn = $(".year_area").eq($(this).index()).find(".fix_list").children(".year_wrap");
          YearText = $(listOn).eq(0).find(".year_num").text();

          if(pageWidth + 17 >= 1024) {
            $changeYear.text(YearText);
          }
        })

      }

      // ESG 경영
      if($(".management_page").size() > 0) {
        // tab swiper
        // let tabSwiper = new Swiper(".tabs_area .swiper-container", {
        //   slidesPerView: "auto",
        //   observer: true,
        //   observeParents: true,
        // });

        // scroll tab fixed
        $(window).on("scroll", function(){
          let _isScrollTop =  $(window).scrollTop();
          let headerHeight = $("header").innerHeight();
          let tabTop = $(".tabs_area").offset().top;

          if(!$("header").hasClass("hide")){
            $(".tab_box").addClass("show");
          } else {
            $(".tab_box").removeClass("show");
          }
          if(headerHeight + _isScrollTop > tabTop) {
            $(".tab_box").addClass("fixed");
          } else {
            $(".tab_box").removeClass("fixed");
            $(".tab_box").removeClass("show");
          }
        });
      }
      if($(".esg_area").size() > 0) {
        // img box motion
        // ScrollTrigger.create({
        //   trigger: ".esg_area",
        //   start: "top bottom",
        //   // markers: true,
        //   onEnter: () => {
        //     $(".esg_area .img_box").addClass("active");
        //   }
        // })

        // img box change motion
        let esgItemLength = $(".esg_list .item").length - 1;

        gsap.utils.toArray(".esg_list .item").forEach((item, idx) => {
          ScrollTrigger.create({
            trigger: item,
            start: "top 60%",
            end: "bottom 60%",
            toggleClass: "on",
            // markers: true,
            onEnter: () => {
              $(".esg_area .img_box figure").css("z-index", "");
              $(".esg_area .img_box figure").eq(idx).css("z-index", 2);
              if(idx != 0) {
                $(".esg_area .img_box figure").eq(idx-1).css("z-index", 1);
                $(".esg_area .img_box figure").eq(idx).css("height", 0);
                gsap.to($(".esg_area .img_box figure").eq(idx), {
                  height: "100%",
                  duration: .8
                })
              }
            },
            onLeave: () => {
              if(idx == esgItemLength) {
                $(item).addClass("on");
              }
              if(idx == 0) {
                $(item).removeClass("on");
              }
            },
            onEnterBack: () => {
              $(".esg_area .img_box figure").css("z-index", "");
              $(".esg_area .img_box figure").eq(idx).css("z-index", 2);
              $(".esg_area .img_box figure").eq(idx+1).css("z-index", 1);
              if(idx != esgItemLength) {
                $(".esg_area .img_box figure").eq(idx).css("height", 0);
                gsap.to($(".esg_area .img_box figure").eq(idx), {
                  height: "100%",
                  duration: .8
                })
              }
            },
            onLeaveBack: () => {
              if(idx  == 0) {
                $(item).addClass("on");
              }
            }
          }) 
        });

        // img box scroll moving motion
        ScrollTrigger.matchMedia({
          "(min-width: 1025px)": function() {
            gsap.fromTo(".esg_area .img_box", {
              top: "20rem",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            }, {
              top: "calc(100% - 448rem)",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            });
          },
          "(min-width: 601px) and (max-width: 1024px)": function() {
            gsap.fromTo(".esg_area .img_box", {
              top: "60rem",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            }, {
              top: "calc(100% - 408rem)",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            });
          },
          "(max-width: 600px)": function() {
            gsap.fromTo(".esg_area .img_box", {
              top: "60rem",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            }, {
              top: "calc(100% - 243rem)",
              scrollTrigger: {
                trigger: ".esg_area",
                start: 'top center',
                end: "bottom center",
                scrub: 1,
                // markers: true,
              },
            });
          }
        })
        
      }
    },
    popupFn:function(){
      // 팝업 닫기
      
      $(".layer_popup .x_btn").on("click", function(){
        $(this).parents(".layer_popup").removeClass("open");
        $("body").removeClass("stop_scroll");
        $("body, html").off("scroll touchmove mousewheel");
        $("#wrap").removeClass("stop_scroll");
        $("body, html").scrollTop(_pageScrollOffset);
      });
      
    },
    openPopupFn : function(a,b){
      openPopup(a,b);
    },
  }
})();

$(window).on("load", function(){
  commonScript.deviceChk();
  commonScript.init();
  // commonScript.headerFooterFn();
  gsap.delayedCall(0.2, function() {
    commonScript.swiperFn();
    commonScript.resizeFn();
    commonScript.commonFn();
    commonScript.formChkFn(); // form 관련 스크립트
    commonScript.scrollTriggerFn(); // 스크롤 트리거 모션
    commonScript.scrollFn();
    commonScript.popupFn();
  });
  
});

function openPopupFn(target, subName){
  var designatedPopup = $(subName) || $(".subName");
  
  _pageScrollOffset = window.pageYOffset;
  $("body").addClass("stop_scroll");
  $("body, html").on('scroll touchmove mousewheel', function (e) {
    e.preventDefault();
  });
  
  gsap.delayedCall(0.1, function () {
    designatedPopup.addClass("open");
    designatedPopup.attr("tabindex", 0).focus();
    // popupResize();

    designatedPopup.find("input[type=text]").eq(0).focus();
    $("body").addClass("stop_scroll");
  });

  designatedPopup.find(".x_btn").on("click", function () {
    $("body, html").scrollTop(_pageScrollOffset);
    designatedPopup.removeClass("open");
  });      
}

function imgResizingFn(){
  var imgNum;
  $(".img_resize_w").each(function(){
    if($(this).find("img").size() >= 2){
      if(window.innerWidth > 768){
        imgNum = 0;
      }else{
        imgNum = 1;
      }
    }else{
      imgNum = 0;
    }

    if($(this).find("img").get(imgNum).naturalWidth / $(this).find("img").get(imgNum).naturalHeight <= $(this).width() / $(this).innerHeight()){
      $(this).addClass("reverse");
    }else{
      $(this).removeClass("reverse");
    }
  });
}

function scrollMotionTrigger(){
  if($(".scroll_motion").size() > 0){
    $(".scroll_motion:visible").each(function(q){
      gsap.to($(this), {
        scrollTrigger: {
          trigger: $(this),
          start: "top 75%",
          end:"bottom top",
          toggleClass: {targets: $(".scroll_motion:visible").eq(q), className: "active"},
          once: true,
          // markers: true,
        },
      });
    });
  }
}

// function popupResize(){
//   $(".layer_popup:visible").css("top","0px") // 스크롤 없는 팝업 내에서 생기는 ios 노치영역 배경색 오류 해결 위해 적음

//   var fixedBtn = 0;
//   if($(".fixed_out_btn").size() > 0){
//     fixedBtn = $(".layer_popup:visible").find(".fixed_out_btn").outerHeight(true)
//   }

//   if(_deviceCondition == 'pc'){
//     $(".layer_popup:visible").find(".pop_wrap, .pop_cont").css({"height":"", "transform":"translate(0,0)"});
    
//     $(".layer_popup:visible").find(".pop_cont").height($(".layer_popup:visible").find(".pop_wrap").height() - $(".layer_popup:visible").find(".pop_head").innerHeight())
//     $(".layer_popup:visible").find(".pop_wrap").height(Math.ceil($(".layer_popup:visible").find(".pop_cont").height() + $(".layer_popup:visible").find(".pop_head").innerHeight())); // 소수점 버림


//   }else if( _deviceCondition == "mobile"){
//     $(".layer_popup:visible").find(".pop_wrap, .pop_cont").css({"transform":"translate(0,0)"});
//     $(".layer_popup:visible").find(".pop_wrap.full").css("height", window.innerHeight) // 소수점 버림
//     $(".layer_popup:visible").find(".pop_cont").height($(".layer_popup:visible").find(".pop_wrap").height() - $(".layer_popup:visible").find(".pop_head").innerHeight() + 1)

//     if($(".fixed_bot_btn").size() > 0){
//     }else if($(".fixed_out_btn").size() > 0){
//       $(".layer_popup:visible").find(".for_padding").css("height","auto");
//       $(".layer_popup:visible").find(".for_padding").innerHeight($(".layer_popup:visible").find(".pop_cont").height() - fixedBtn);
//     }
//   }

//   $(".layer_popup:visible").find(".pop_wrap").css({"margin-left": $(".layer_popup:visible").find(".pop_wrap").width()*-0.5, "margin-top": $(".layer_popup:visible").find(".pop_wrap").height()*-0.5}); // 중앙정렬

  
// }

function repositioningTopBtn(){
  if($(".btn_top").length > 0) {
    if($(window).scrollTop() > window.innerHeight / 4){
      $(".btn_top").fadeIn();
    }else{
      $(".btn_top").fadeOut()
    }

    // var safetyChar = getComputedStyle(document.documentElement).getPropertyValue("--sab")
    // var safetyNum = parseInt(safetyChar.split("p"));

    if($(window).scrollTop() + window.innerHeight > $("footer").offset().top) {
      // 푸터에 붙었을 때,
      if(window.innerWidth > 1023){
        $(".btn_top").css("bottom", $(window).scrollTop() + window.innerHeight - $("footer").offset().top + 20);
      }else{
        $(".btn_top").css("bottom", $(window).scrollTop() + window.innerHeight - $("footer").offset().top + 10);
      }
    }else {
      // 스크롤 할 때,
      $(".btn_top").removeClass("no_fixed");
      
      if(window.innerWidth > 1023){
        $(".btn_top").css("bottom", 20);
      }else{
        $(".btn_top").css("bottom", 10);
      }
    }
  }
}

function flowTxt(){
  var infiMoveWidth = $(".divide_swiper .flow_txt_w .flow_txt").width() + 160;

  $(".divide_swiper .flow_txt_w .flow_txt").each(function(q){
    $(".divide_swiper .flow_txt_w .flow_txt").eq(q).css("left", infiMoveWidth * q);

    gsap.to($(".divide_swiper .flow_txt_w .flow_txt").eq(q), Math.floor(infiMoveWidth * 0.015), {"left": infiMoveWidth * (q - 1), ease: "none", repeat:-1, onComplete: function(){
      $(".divide_swiper .flow_txt_w .flow_txt").eq(q).css("left", infiMoveWidth);
    }});
  })
  
}  

function accorRecall(){
  if($(".accordion_type").size() > 0) {
    $(".accordion_type").each(function() {
      $(this).find(".list_div .list").off().on("click", function() {
        if($(this).parents(".accordion_type").hasClass("one_open")){// 한개만 열려야될때
          if(!$(this).parent().hasClass("on")) {
            $(this).parents(".accordion_type").find(".list_div").removeClass("on");
            $(this).parent().addClass("on");
            $(this).parents(".accordion_type").find(".list_div .hide_con").stop(true, true).slideUp();
            $(this).siblings(".hide_con").stop(true, true).slideDown();
          }else{
            $(this).parent().removeClass("on");
            $(this).siblings(".hide_con").stop(true, true).slideUp();
          }
        }else{
          $(this).parent().toggleClass("on");
          
          if($(this).parent().hasClass("on")) {
            $(this).siblings(".hide_con").stop(true, true).slideDown();
          } else {
            $(this).siblings(".hide_con").stop(true, true).slideUp();
          }
        } 
      });
    });
  }
}

var lazyLoading = () => {
  var imgs = document.querySelectorAll('.lazy');

  var observerCallback = (entries, observer) => {
    entries.forEach(({ isIntersecting, intersectionRatio, target }) => {
      if (isIntersecting && intersectionRatio > 0) {
        target.src = target.dataset.src;
        target.classList.remove("lazy");
        observer.unobserve(target);
      }
    });
  };

  var io = new IntersectionObserver(observerCallback);
  imgs.forEach((img) => io.observe(img));
};