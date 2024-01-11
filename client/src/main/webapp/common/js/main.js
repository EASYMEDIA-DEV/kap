var _this_scroll = 0;       // 스크롤 up & down 체크위한 변수
var _isScrollTop;          // scrollTop 변수
var _mainPopupSlideCount = 0;
var mainPopSwiper;

var mainScript = (function(){
  return {
    introFn: function(){
      // main 인트로
      if($(".intro-motion:visible").length){ // 인트로 노출 시
        $(".intro-motion").addClass("visible");
        $(".intro-motion .viedeo-area").show();
        var introVideo = document.getElementById("introVideo");
        
        introVideo.addEventListener('ended', function() {
          $(".intro-motion").addClass("end");
          gsap.to($(".main-kv-sec"), 1, {left: 0, ease: Power3, delay:0.8});
          setTimeout(() => {
            mainScript.mainKVFn();
            mainInitShowMotion();
          }, 2000);
        });
      }else{ // 인트로 미 노출 시
        mainScript.mainKVFn();
        setTimeout(() => {
          mainInitShowMotion();
        }, 200);
      }

      function mainInitShowMotion(){
        if($(window).scrollTop() == 0){
          gsap.to($(".main header"), 0.7, {top: 0, ease: Power3});
        }
        gsap.to($(".main .quick-menu"), 0.7, {marginRight: 0, ease: Power3});
        gsap.to($(".main-kv-sec .roll-swiper-area .swiper-slide:first-child .txt-wrap .motion"), 0.7, {opacity: 1, left: 0, stagger:0.08, ease: Power3});
        gsap.to($(".main-kv-sec .slide-control-area"), 0.7, {opacity: 1, ease: Power3});
        gsap.to($(".main-kv-sec .scroll-down"), 0.7, {opacity: 1, ease: Power3});
        gsap.to($(".biz-line-sec .biz-list"), 0.7, {opacity: 1, top: 0, stagger:0.1, ease: Power3});
      }
    },
    mainKVFn: function(){
      // 메인 kv
      if($(".main-kv-sec").length){        
        $(".slide-control-area .pagination-w .total-page").html($(".main-kv-sec .roll-swiper-area .list").length);
        $(".main-kv-sec").height(window.innerHeight);
        $(".main-kv-sec .roll-swiper-area .list:first-child").addClass("hide-motion");

        var slideNum = 0;        
        var $homeSlider = $('#homeSlider');
        var mc = new Hammer($homeSlider[0]);
        var kvmotion1, kvmotion2, kvmotion3, kvmotion4;
        var isMoving = false;

        function slideMoveFn(){
          isMoving = true;

          if(kvmotion1 != undefined){
            kvmotion1.kill();
            kvmotion2.kill();
            kvmotion3.kill();
            kvmotion4.kill();
          }

          $(".main-kv-sec .roll-swiper-area .list:not(.hide-motion)").css("z-index", 1);
          $(".main-kv-sec .roll-swiper-area .list.hide-motion").css("z-index", 20);

          kvmotion1 = gsap.to($(".main-kv-sec .roll-swiper-area .list:not(.hide-motion)"), 0, {clipPath: "polygon(0 0, 0 100%, 50% 100%, 50% 0, 50% 0, 50% 100%, 100% 100%, 100% 0)"}); // 이미지 사라진거 원복
          kvmotion2 = gsap.to($(".main-kv-sec .roll-swiper-area .list.hide-motion"), 1, {clipPath: "polygon(0% 0%, 0% 100%, 0% 100%, 0% 0%, 50% 0%, 50% 100%, 50% 100%, 50% 0%)", ease: Power3}); // 이미지 사라지게
          kvmotion3 = gsap.to($(".main-kv-sec .roll-swiper-area .list").eq(slideNum).find(".txt-wrap .motion"), 0.7, {left: 0, opacity: 1, stagger:0.08, ease: Power3, delay: 1, onComplete: function(){
            gsap.to($(".main-kv-sec .roll-swiper-area .list:not(.hide-motion) .txt-wrap .motion"), 0, {left: "-100rem", opacity: 0}); // 글씨 없어지게 (reset)
            isMoving = false;
            mainKvTimerMotion.restart();
            $(".main-kv-sec .slide-control-area .move-control .btn-pause").removeClass("stopped");
            
          }}); // 글씨 나타나게
          
          $(".main-kv-sec .roll-swiper-area .list").eq(slideNum).addClass("hide-motion").siblings().removeClass("hide-motion");
          $(".main-kv-sec .roll-swiper-area .list.hide-motion").css("z-index", 19);

          kvmotion4 = gsap.fromTo($(".main-kv-sec .roll-swiper-area .list").eq(slideNum).find(".img-area"), 1, {left: "100rem"}, {left: 0, ease: Power3}); // 이미지가 왼쪽으로 움직이게
        }

        
        mc.get('swipe').set({ direction: Hammer.DIRECTION_HORIZONTAL });

        mc.on("swipeleft swiperight", function(e) {
          if ( Math.abs(e.deltaX) < 30) return;

          if(isMoving == false){
            if ( e.type == "swiperight") {
              if(slideNum > 0){
                slideNum -= 1;
              }else{
                slideNum = $(".main-kv-sec .roll-swiper-area .list").length - 1;
              }
            } else {
              if(slideNum < $(".main-kv-sec .roll-swiper-area .list").length - 1){
                slideNum += 1;
              }else{
                slideNum = 0;
              }
            }
            
            $(".slide-control-area .pagination-w .cur-page").html(slideNum + 1);
            slideMoveFn();
          }
        });

        var mainKvTimerMotion = gsap.to($(".main-kv-sec .slide-control-area .timer-gauge .bar"), 10, {
          width: "100%",
          ease: "none",
          onComplete: function() {
            if(isMoving == false){
              if(slideNum < $(".main-kv-sec .roll-swiper-area .list").length - 1){
                slideNum += 1;
              }else{
                slideNum = 0;
              }
              slideMoveFn();
            }
            
          }
        });

        // 왼쪽 이동 버튼        
        $(".main-kv-sec .slide-control-area .move-control .swiper-button-prev").on("click", function(){
          if(isMoving == false){
            if(slideNum > 0){
              slideNum -= 1;
            }else{
              slideNum = $(".main-kv-sec .roll-swiper-area .list").length - 1;
            }
            $(".slide-control-area .pagination-w .cur-page").html(slideNum + 1);
            slideMoveFn();
          }
        });

        // 오른쪽 이동 버튼
        $(".main-kv-sec .slide-control-area .move-control .swiper-button-next").on("click", function(){
          if(isMoving == false){
            if(slideNum < $(".main-kv-sec .roll-swiper-area .list").length - 1){
              slideNum += 1;
            }else{
              slideNum = 0;
            }
            $(".slide-control-area .pagination-w .cur-page").html(slideNum + 1);
            slideMoveFn();
          }
        });

        // 일시 정지 버튼
        $(".main-kv-sec .slide-control-area .btn-pause").on("click", function(){
          if(!$(this).hasClass("stopped")){
            $(this).addClass("stopped").attr("title","재생");
            mainKvTimerMotion.pause();
          }else{
            $(this).removeClass("stopped").attr("title","일시정지");
            mainKvTimerMotion.play();
          }
        });
      }
    },
    mainPopupFn: function(){
      if($(".main-popup").length){
        var popupTimerMotion = gsap.to($(".main-popup .bot-info-area .slide-control-area .timer-gauge .bar"), 3, {
          width: "100%",
          ease: "none",
          onComplete: function() {
            if(mainPopSwiper != undefined){
              if(window.innerWidth > 1023){
                mainPopSwiper.slideNext();
              }
            }
          }
        });

        mainPopSwiper = new Swiper(".main-popup .swiper-container", {
          observer: true,
          observeParents: true,
          slidesPerView: 1,
          loop : true,
          navigation: {
            nextEl: ".main-popup .swiper-button-next",
            prevEl: ".main-popup .swiper-button-prev",
          },
          pagination: {
            el: ".main-popup .swiper-pagination",
            type: "fraction",
          },
          touchReleaseOnEdges: true,
          on:{
            slideChangeTransitionStart:function(){
              $(".slide-control-area .move-control .btn-pause").removeClass("stopped");
            },
            slideChangeTransitionEnd:function(){
              if(window.innerWidth > 1023){
                popupTimerMotion.restart();
              }
            }
          },
          breakpoints: {
            1023: { //브라우저가 1023보다 클 때
              slidesPerView: 2,  
            },
          },
        });

        // 일시 정지 버튼
        $(".main-popup .bot-info-area .slide-control-area .btn-pause").on("click", function(){
          if(!$(this).hasClass("stopped")){
            $(this).addClass("stopped").attr("title","재생");
            popupTimerMotion.pause();
          }else{
            $(this).removeClass("stopped").attr("title","일시정지");
            popupTimerMotion.play();
          }
        });
      }
    },
    commonFn: function(){
      // 메인팝업 슬라이드 갯수
      _mainPopupSlideCount = $(".main-popup .pop-wrap .swiper-slide").size();

      // main 공통 아코디언
      let lastTarget;

      $(".hover-accordion .acco-list .title-area").on("mouseenter focusin click", function(){
        $(this).parents(".acco-list").addClass("active").siblings().removeClass("active");
        if($(this).parents(".biz-line-sec").size() > 0){
          gsap.to($(this).parents(".acco-list").siblings().find(".title-area"), .5, {opacity:0.5, ease:Power3.easeOut});
          gsap.to($(this), .5, {opacity:1, ease:Power3.easeOut});
        }
      });

      $(".hover-accordion .acco-list").on("mouseleave", function(){
        if(!($(this).parents(".coexisting-swiper-w").size() > 0 && $(this).index() == 0)){
          $(this).removeClass("active");
          if($(this).parents(".biz-line-sec").size() > 0){
            gsap.to($(this).parents(".hover-accordion").find(".acco-list:nth-child(n+2) .title-area"), .5, {opacity:0.5, ease:Power3.easeOut});
          }
        }

        lastTarget = $(this).parents(".hover-accordion");
      });

      $(".hover-accordion").on("mouseleave", function(){
        if($(this).parents(".coexisting-swiper-w").size() > 0){
          $(this).find(".acco-list:first-child").addClass("active");
        }
      });

      document.addEventListener("mousemove",(e) => {        
        if($(e.target).parents(".coexisting-swiper-w").size() > 0){
          if($(e.target).hasClass("acco-list") || $(e.target).hasClass("swiper-slide")){
            $(lastTarget).find(".acco-list:first-child").addClass("active").siblings().removeClass("active");
          }
        }
      })


      // 페이지 첫 진입 시 스크롤 내려가 있으면 quick 디자인 흰색으로
      if($(window).scrollTop() > 100){
        $(".quick-menu").addClass("scroll-down");
      }


      // 사업 영역 tab 클릭
      $(".biz-tab-area .txt-tab-swiper .txt-tab-btn").on("click", function(){
        $(".biz-tab-area .txt-tab-swiper .txt-tab-btn").removeClass("active").eq($(this).index()).addClass("active");
        $(".biz-tab-con-area section").hide().eq($(this).index()).show();
      });

      
      // 뉴스레터 수신 동의 관련
    },
    scrollFn: function(){
      $(window).on("scroll", function(){
      });
    },
    resizeFn: function(){
      $(window).resize(function(){
        // main kv 높이값
        $(".main-kv-sec").height(window.innerHeight);

        // 메인팝업 인디케이터 visible 여부
        mainPopupIndicatorChk();

        // [공통 - 교육/세미나] swiper
        if($(location).attr('host').indexOf("html") > -1 || $(location).attr('host').indexOf("eznet1") > -1){ //  퍼블 worklist에서만 동작하게 예외처리
          mainTrainingInitFn();
        }

        // 상생사업 swiper
        coexistingSwperInitFn();
      }).resize();
    },
    swiperFn: function(){
      // [공통 - 교육/세미나] swiper
      //mainTrainingInitFn();

      if($(location).attr('host').indexOf("html") > -1 || $(location).attr('host').indexOf("eznet1") > -1){ //  퍼블 worklist에서만 동작하게 예외처리
        trainingPCFn();
      }

      
      // 상생사업 swiper
      coexistingSwperInitFn();

      $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").each(function(index, item){
        $(item).css("z-index", $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").length - index);
      });

      var easing = BezierEasing(0.6, 0, 0.3, 1);
      var slideNum = 0;

      if(slideNum == 0){
        $(".coexisting-swiper-w .swiper-button-prev").addClass("swiper-button-disabled");
      }

      $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-button-next").on("click", function(){
        if(window.innerWidth > 1023){
          if(slideNum < $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").length - 1){
            $(".coexisting-swiper-w .swiper-button-prev").removeClass("swiper-button-disabled");
            gsap.to($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".inner-motion"), 0.6, {y: "-100%", opacity: 0, ease: easing});
            gsap.to($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".thumbnail-img"), 0.6, {opacity: 0, ease: easing, onComplete: function(){
              slideNum += 1;
              $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum - 1).hide();
              $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum - 1).find(".biz-list .title-area").hide();
              $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum - 1).find(".biz-list .hide-area").hide();
              gsap.fromTo($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".inner-motion"), 0.6, {y: 100, opacity: 0}, {y: 0, opacity: 1, ease: easing});
              if(slideNum >= $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").length - 1){
                $(".coexisting-swiper-w .swiper-button-next").addClass("swiper-button-disabled");
              }
            }});
          }
        }
      });

      $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-button-prev").off().on("click", function(){
        if(window.innerWidth > 1023){
          if(slideNum > 0){
            $(".coexisting-swiper-w .swiper-button-next").removeClass("swiper-button-disabled");
            $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum - 1).show();
            gsap.to($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".inner-motion"), 0.6, {y: "-100%", opacity: 0, ease: easing});
            gsap.to($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum - 1).find(".thumbnail-img"), 0.6, {opacity: 1, ease: easing, onComplete: function(){
              slideNum -= 1;
              $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".biz-list .title-area").show();
              $(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".biz-list .hide-area").show();
              gsap.fromTo($(".biz-tab-area .coexisting-sec .coexisting-swiper-w .swiper-slide").eq(slideNum).find(".inner-motion"), 0.6, {y: 100, opacity: 0}, {y: 0, opacity: 1, ease: easing});
              if(slideNum == 0){
                $(".coexisting-swiper-w .swiper-button-prev").addClass("swiper-button-disabled");
              }
            }});
          }
        }
      });
    },
  }
})();

$(window).on("load", function(){
  mainScript.introFn();
  mainScript.commonFn();
  mainScript.mainPopupFn();
  mainScript.scrollFn();
  mainScript.resizeFn();
  mainScript.swiperFn();
});

function mainPopupIndicatorChk(){
  if(window.innerWidth > 1023){
    if(_mainPopupSlideCount >= 3){
      $(".main-popup").addClass("has-controller");
    }else{
      $(".main-popup").removeClass("has-controller");
      if(mainPopSwiper != undefined){
        mainPopSwiper.destroy();
        mainPopSwiper = undefined;
      }
    }
  }else{
    if(_mainPopupSlideCount >= 2){
      $(".main-popup").addClass("has-controller");

      if(mainPopSwiper == undefined){
        mainPopSwiper = new Swiper(".main-popup .swiper-container", {
          observer: true,
          observeParents: true,
          slidesPerView: 1,
          loop : true,
          navigation: {
            nextEl: ".main-popup .swiper-button-next",
            prevEl: ".main-popup .swiper-button-prev",
          },
          pagination: {
            el: ".main-popup .swiper-pagination",
            type: "fraction",
          },
          touchReleaseOnEdges: true,
          on:{
            slideChangeTransitionEnd:function(){
              if(window.innerWidth > 1023){
                popupTimerMotion.restart();
              }
            }
          },
          breakpoints: {
            1023: { //브라우저가 1023보다 클 때
              slidesPerView: 2,  
            },
          },
        });
      }
    }else{
      $(".main-popup").removeClass("has-controller");
    }
  }
}


// 상생사업 swiper
var mainCoexistingSwiper;
function coexistingSwperInitFn(){  
  if (window.innerWidth <= 1023 && mainCoexistingSwiper == undefined) {
    if($(".coexisting-swiper-w").size() > 0){
      mainCoexistingSwiper = new Swiper(".coexisting-swiper-w .swiper-container", {
        loop: true,
        spaceBetween: 1,
        slidesPerView: "auto",
        observer: true,
        observeParents: true,
        autoHeight: true,
        pagination: {
          el: ".coexisting-swiper-w .swiper-pagination",
          type: "bullets",
        },
        navigation: {
          nextEl: ".coexisting-swiper-w .swiper-button-next",
          prevEl: ".coexisting-swiper-w .swiper-button-prev",
        }
      });
    }
  }else if(window.innerWidth > 1023 && mainCoexistingSwiper != undefined) {
    mainCoexistingSwiper.destroy();
    mainCoexistingSwiper = undefined;
  }
}


function horizontalLoop(items, config) {
  items = gsap.utils.toArray(items);
  config = config || {};
  let onChange = config.onChange,
  lastIndex = 0,
  curIndex = 0,
  tl = gsap.timeline({repeat: config.repeat, onUpdate: onChange && function() {
      let i = tl.closestIndex()
      if (lastIndex !== i) {
          lastIndex = i;
          curIndex = i;
          onChange(items[i], i);
      }
  },
  paused: config.paused, defaults: {ease: "none"}, onReverseComplete: () => tl.totalTime(tl.rawTime() + tl.duration() * 10)}),
  length = items.length,
  startX = items[0].offsetLeft,
  times = [],
  widths = [],
  spaceBefore = [],
  xPercents = [],
  center = config.center,
  pixelsPerSecond = (config.speed || 1) * 50,
  snap = config.snap === false ? v => v : gsap.utils.snap(config.snap || 1),
  timeOffset = 0,
  container = center === true ? items[0].parentNode : gsap.utils.toArray(center)[0] || items[0].parentNode,
  totalWidth,
  getTotalWidth = () => items[length-1].offsetLeft + xPercents[length-1] / 100 * widths[length-1] - startX + spaceBefore[0] + items[length-1].offsetWidth * gsap.getProperty(items[length-1], "scaleX") + (parseFloat(config.paddingRight) || 0),

  

  populateWidths = () => {
      let b1 = container.getBoundingClientRect(), b2;
      items.forEach((el, i) => {
          widths[i] = parseFloat(gsap.getProperty(el, "width", "px"));
          xPercents[i] = snap(parseFloat(gsap.getProperty(el, "x", "px")) / widths[i] * 100 + gsap.getProperty(el, "xPercent"));
          b2 = el.getBoundingClientRect();
          spaceBefore[i] = b2.left - (i ? b1.right : b1.left);
          b1 = b2;
      });
      gsap.set(items, {
          xPercent: i => xPercents[i]
      });
      totalWidth = getTotalWidth();
  },

  timeWrap,

  populateOffsets = () => {
      timeOffset = center ? tl.duration() * (container.offsetWidth / 2) / totalWidth : 0;
      center && times.forEach((t, i) => {
          times[i] = timeWrap(tl.labels["label" + i] + tl.duration() * widths[i] / 2 / totalWidth - timeOffset);
      });
  },

  getClosest = (values, value, wrap) => {
      let i = values.length,
      closest = 1e10,
      index = 0, d;
      while (i--) {
          d = Math.abs(values[i] - value);
          if (d > wrap / 2) {
              d = wrap - d;
          }
          if (d < closest) {
              closest = d;
              index = i;
          }
      }
      return index;
  },

  populateTimeline = () => {
      let i, item, curX, distanceToStart, distanceToLoop;
      tl.clear();
      for (i = 0; i < length; i++) {
          item = items[i];
          curX = xPercents[i] / 100 * widths[i];
          distanceToStart = item.offsetLeft + curX - startX + spaceBefore[0];
          distanceToLoop = distanceToStart + widths[i] * gsap.getProperty(item, "scaleX");
          tl.to(item, {xPercent: snap((curX - distanceToLoop) / widths[i] * 100), duration: distanceToLoop / pixelsPerSecond}, 0)
          .fromTo(item, {xPercent: snap((curX - distanceToLoop + totalWidth) / widths[i] * 100)}, {xPercent: xPercents[i], duration: (curX - distanceToLoop + totalWidth - curX) / pixelsPerSecond, immediateRender: false}, distanceToLoop / pixelsPerSecond)
          .add("label" + i, distanceToStart / pixelsPerSecond);
          times[i] = distanceToStart / pixelsPerSecond;
      }

      timeWrap = gsap.utils.wrap(0, tl.duration());
  },

  refresh = (deep) => {
    if(window.innerWidth > 1023){
      let progress = tl.progress();
      tl.progress(0, true);
      populateWidths();
      deep && populateTimeline();
      populateOffsets();
      deep && tl.draggable ? tl.time(times[curIndex], true) : tl.progress(progress, true);
    }
  },

  

  proxy;
  gsap.set(items, {x: 0});
  populateWidths();
  populateTimeline();
  populateOffsets();

  window.addEventListener("resize", () => {
    if(window.innerWidth > 1023){
      refresh(true);
    }
  });

  function toIndex(index, vars) {
    if(window.innerWidth > 1023){
      vars = vars || {};
      (Math.abs(index - curIndex) > length / 2) && (index += index > curIndex ? -length : length);
      let newIndex = gsap.utils.wrap(0, length, index),
      time = times[newIndex];
      if (time > tl.time() !== index > curIndex) {
          time += tl.duration() * (index > curIndex ? 1 : -1);
      }
      if (time < 0 || time > tl.duration()) {
          vars.modifiers = {time: timeWrap};
      }
      curIndex = newIndex;
      vars.overwrite = true;
      gsap.killTweensOf(proxy);
      return tl.tweenTo(time, vars);
    }
  }

  var moveItem;

  if ( length < 5 ){
      moveItem = 1;
  }else if ( length > 4 && length < 6 ){
      moveItem = 2;
  }else if ( length > 5 && length < 8 ){
      moveItem = 3;
  }else if ( length > 7 ){
      moveItem = 4;
  }

  tl.next = vars => {
    if(window.innerWidth > 1023){
      toIndex(curIndex+moveItem, vars);
      function nextPlay() {
          tl.play();
      }
      setTimeout(nextPlay, 1000);
    }
  }

  tl.previous = vars => {
    if(window.innerWidth > 1023){
      toIndex(curIndex-moveItem, vars);
      function previousPlay() {
          tl.play();
      }
      setTimeout(previousPlay, 1000);
    }
  }

  tl.current = () => curIndex;
  tl.toIndex = (index, vars) => toIndex(index, vars);
  tl.closestIndex = setCurrent => {
      let index = getClosest(times, tl.time(), tl.duration());
      setCurrent && (curIndex = index);
      return index;
  };

  tl.times = times;
  tl.progress(1, true).progress(0, true); // pre-render for performance

  if (config.reversed) {
      tl.vars.onReverseComplete();
      tl.reverse();
  }

  if (config.draggable && typeof(Draggable) === "function") {
    if(window.innerWidth > 1023){
      proxy = document.createElement("div")
      let wrap = gsap.utils.wrap(0, 1),
      ratio, startProgress, draggable, dragSnap,
      align = () => tl.progress(wrap(startProgress + (draggable.startX - draggable.x) * ratio)),
      syncIndex = () => tl.closestIndex(true);
      typeof(InertiaPlugin) === "undefined" && console.warn("InertiaPlugin required for momentum-based scrolling and snapping. https://greensock.com/club");

      draggable = Draggable.create(proxy, {
          trigger: items[0].parentNode,
          type: "x",
          onPressInit() {
              gsap.killTweensOf(tl);
              startProgress = tl.progress();
              refresh();
              ratio = 1 / totalWidth;
              gsap.set(proxy, {x: startProgress / -ratio})
          },
          onDrag: align,
          onThrowUpdate: align,
          inertia: true,
          snap: value => {
              let time = -(value * ratio) * tl.duration(),
              wrappedTime = timeWrap(time),
              snapTime = times[getClosest(times, wrappedTime, tl.duration())],
              dif = snapTime - wrappedTime;
              Math.abs(dif) > tl.duration() / 2 && (dif += dif < 0 ? tl.duration() : -tl.duration());
              return (time + dif) / tl.duration() / -ratio;
          },
          onRelease: syncIndex,
          onThrowComplete: syncIndex
      })[0];

      tl.draggable = draggable;
    }
  }

  tl.closestIndex(true);
  onChange && onChange(items[curIndex], curIndex);

  $('.marquee_wrapper1').on('mouseover',()=>{
    if(window.innerWidth > 1023){
      tl.pause();
    }
  })
  $('.marquee_wrapper1').on('mouseleave',()=>{
    if(window.innerWidth > 1023){
      tl.play();
    }
  })

  return tl;
}

function horizontalLoop2(items, config) {
  items = gsap.utils.toArray(items);
  config = config || {};
  let onChange = config.onChange,
  lastIndex = 0,
  curIndex = 0,
  tl = gsap.timeline({repeat: config.repeat, onUpdate: onChange && function() {
      let i = tl.closestIndex()
      if (lastIndex !== i) {
          lastIndex = i;
          curIndex = i;
          onChange(items[i], i);
      }
  },

  paused: config.paused, defaults: {ease: "none"}, onReverseComplete: () => tl.totalTime(tl.rawTime() + tl.duration() * 10)}),
  length = items.length,
  startX = items[0].offsetLeft,
  times = [],
  widths = [],
  spaceBefore = [],
  xPercents = [],
  center = config.center,
  pixelsPerSecond = (config.speed || 1) * 50,
  snap = config.snap === false ? v => v : gsap.utils.snap(config.snap || 1),
  timeOffset = 0,
  container = center === true ? items[0].parentNode : gsap.utils.toArray(center)[0] || items[0].parentNode,
  totalWidth,
  getTotalWidth = () => items[length-1].offsetLeft + xPercents[length-1] / 100 * widths[length-1] - startX + spaceBefore[0] + items[length-1].offsetWidth * gsap.getProperty(items[length-1], "scaleX") + (parseFloat(config.paddingRight) || 0),

  populateWidths = () => {
      let b1 = container.getBoundingClientRect(), b2;
      items.forEach((el, i) => {
          widths[i] = parseFloat(gsap.getProperty(el, "width", "px"));
          xPercents[i] = snap(parseFloat(gsap.getProperty(el, "x", "px")) / widths[i] * 100 + gsap.getProperty(el, "xPercent"));
          b2 = el.getBoundingClientRect();
          spaceBefore[i] = b2.left - (i ? b1.right : b1.left);
          b1 = b2;
      });
      gsap.set(items, {
          xPercent: i => xPercents[i]
      });
      totalWidth = getTotalWidth();
  },

  timeWrap,

  populateOffsets = () => {
      timeOffset = center ? tl.duration() * (container.offsetWidth / 2) / totalWidth : 0;
      center && times.forEach((t, i) => {
          times[i] = timeWrap(tl.labels["label" + i] + tl.duration() * widths[i] / 2 / totalWidth - timeOffset);
      });
  },

  getClosest = (values, value, wrap) => {
      let i = values.length,
      closest = 1e10,
      index = 0, d;
      while (i--) {
          d = Math.abs(values[i] - value);
          if (d > wrap / 2) {
              d = wrap - d;
          }
          if (d < closest) {
              closest = d;
              index = i;
          }
      }
      return index;
  },

  populateTimeline = () => {
      let i, item, curX, distanceToStart, distanceToLoop;
      tl.clear();
      for (i = 0; i < length; i++) {
          item = items[i];
          curX = xPercents[i] / 100 * widths[i];
          distanceToStart = item.offsetLeft + curX - startX + spaceBefore[0];
          distanceToLoop = distanceToStart + widths[i] * gsap.getProperty(item, "scaleX");
          tl.to(item, {xPercent: snap((curX - distanceToLoop) / widths[i] * 100), duration: distanceToLoop / pixelsPerSecond}, 0)
          .fromTo(item, {xPercent: snap((curX - distanceToLoop + totalWidth) / widths[i] * 100)}, {xPercent: xPercents[i], duration: (curX - distanceToLoop + totalWidth - curX) / pixelsPerSecond, immediateRender: false}, distanceToLoop / pixelsPerSecond)
          .add("label" + i, distanceToStart / pixelsPerSecond);
          times[i] = distanceToStart / pixelsPerSecond;
      }

      timeWrap = gsap.utils.wrap(0, tl.duration());
  },

  refresh = (deep) => {
      let progress = tl.progress();
      tl.progress(0, true);
      populateWidths();
      deep && populateTimeline();
      populateOffsets();
      deep && tl.draggable ? tl.time(times[curIndex], true) : tl.progress(progress, true);
  },

  proxy;
  gsap.set(items, {x: 0});
  populateWidths();
  populateTimeline();
  populateOffsets();

  window.addEventListener("resize", () => refresh(true));

  function toIndex(index, vars) {
      vars = vars || {};
      (Math.abs(index - curIndex) > length / 2) && (index += index > curIndex ? -length : length);
      let newIndex = gsap.utils.wrap(0, length, index),
      time = times[newIndex];
      if (time > tl.time() !== index > curIndex) {
          time += tl.duration() * (index > curIndex ? 1 : -1);
      }
      if (time < 0 || time > tl.duration()) {
          vars.modifiers = {time: timeWrap};
      }
      curIndex = newIndex;
      vars.overwrite = true;
      gsap.killTweensOf(proxy);
      return tl.tweenTo(time, vars);
  }

  var moveItem;

  if ( length < 5 ){
      moveItem = 1;
  }else if ( length > 4 && length < 6 ){
      moveItem = 2;
  }else if ( length > 5 && length < 8 ){
      moveItem = 3;
  }else if ( length > 7 ){
      moveItem = 4;
  }

  tl.next = vars => {
      toIndex(curIndex+moveItem, vars);
      function nextPlay() {
          tl.play();
      }
      setTimeout(nextPlay, 1000);
  }

  tl.previous = vars => {
      toIndex(curIndex-moveItem, vars);
      function previousPlay() {
          tl.play();
      }
      setTimeout(previousPlay, 1000);
  }

  tl.current = () => curIndex;
  tl.toIndex = (index, vars) => toIndex(index, vars);
  tl.closestIndex = setCurrent => {
      let index = getClosest(times, tl.time(), tl.duration());
      setCurrent && (curIndex = index);
      return index;
  };

  tl.times = times;
  tl.progress(1, true).progress(0, true); // pre-render for performance

  if (config.reversed) {
      tl.vars.onReverseComplete();
      tl.reverse();
  }

  if (config.draggable && typeof(Draggable) === "function") {
      proxy = document.createElement("div")
      let wrap = gsap.utils.wrap(0, 1),
      ratio, startProgress, draggable, dragSnap,
      align = () => tl.progress(wrap(startProgress + (draggable.startX - draggable.x) * ratio)),
      syncIndex = () => tl.closestIndex(true);
      typeof(InertiaPlugin) === "undefined" && console.warn("InertiaPlugin required for momentum-based scrolling and snapping. https://greensock.com/club");

      draggable = Draggable.create(proxy, {
          trigger: items[0].parentNode,
          type: "x",
          onPressInit() {
              gsap.killTweensOf(tl);
              startProgress = tl.progress();
              refresh();
              ratio = 1 / totalWidth;
              gsap.set(proxy, {x: startProgress / -ratio})
          },
          onDrag: align,
          onThrowUpdate: align,
          inertia: true,
          snap: value => {
              let time = -(value * ratio) * tl.duration(),
              wrappedTime = timeWrap(time),
              snapTime = times[getClosest(times, wrappedTime, tl.duration())],
              dif = snapTime - wrappedTime;
              Math.abs(dif) > tl.duration() / 2 && (dif += dif < 0 ? tl.duration() : -tl.duration());
              return (time + dif) / tl.duration() / -ratio;
          },
          onRelease: syncIndex,
          onThrowComplete: syncIndex
      })[0];

      tl.draggable = draggable;
  }

  tl.closestIndex(true);
  onChange && onChange(items[curIndex], curIndex);

  $('.marquee_wrapper2').on('mouseover',()=>{
    tl.pause();
  })
  $('.marquee_wrapper2').on('mouseleave',()=>{
      tl.play();
  })

  return tl;
}


var wrapper, wrapper2;
var boxes, boxes2;
var loop, loop2;
var activeElement, activeElement2;

function trainingPCFn() {
  // 교육 리스트  
  wrapper, wrapper2 = undefined;
  boxes, boxes2 = undefined;
  loop, loop2 = undefined;
  activeElement, activeElement2 = undefined;

  wrapper = document.querySelector(".marquee_wrapper1");
  boxes = gsap.utils.toArray(".marquee_item1");

  activeElement;
  loop = horizontalLoop(boxes, {
    repeat: 99999999,
    paused: false,
    draggable: true,
    center: false,
    onChange: (element, index) => {
        activeElement && activeElement.classList.remove("-active");
        element.classList.add("-active");
        activeElement = element;
    }
  });

  document.querySelector(".btn_next").addEventListener("click", () => loop.next({duration: 0.4, ease: "power1.inOut"}));
  document.querySelector(".btn_prev").addEventListener("click", () => loop.previous({duration: 0.4, ease: "power1.inOut"}));



  wrapper2 = document.querySelector(".marquee_wrapper2");
  boxes2 = gsap.utils.toArray(".marquee_item2");

  activeElement2;
  loop2 = horizontalLoop2(boxes2, {
      repeat: 99999999,
      paused: false,
      draggable: true,
      center: false,
      onChange: (element, index) => {
          activeElement2 && activeElement2.classList.remove("-active");
          element.classList.add("-active");
          activeElement2 = element;
      }
  });
  
  document.querySelector(".btn_next2").addEventListener("click", () => loop2.next({duration: 0.4, ease: "power1.inOut"}));
  document.querySelector(".btn_prev2").addEventListener("click", () => loop2.previous({duration: 0.4, ease: "power1.inOut"}));


  //loop.restart();
  //loop2.restart();
}


var mainTrainingSwiper = [];
function mainTrainingInitFn() {
  // [공통 - 교육/세미나] swiper
  if (window.innerWidth <= 1023) { // mobile
    if($("#wrap").hasClass("main")){
      if($(".training-swiper-area").size() > 0){
        if(loop != undefined){
          loop.pause();
          loop2.pause();
        }

        $(".training-swiper-area").addClass("is-mobile");

        $(".training-swiper-area .training-swiper").each(function(index, item){
          if(mainTrainingSwiper[index] == undefined){
            $(this).addClass('swiper' + index);
            $(this).parents(".training-swiper-area").addClass('training-swiper-area' + index);
            mainTrainingSwiper[index] = new Swiper(".training-swiper.swiper" + index, {
              observer: true,
              observeParents: true,
              loop: true,
              /*pagination: {
                el: ".training-swiper-area.training-swiper-area" + index + " .swiper-pagination",
                type: "progressbar",
              },*/
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
  }else if(window.innerWidth > 1023) { // pc
    if($("#wrap").hasClass("main")){
      $(".training-swiper-area").removeClass("is-mobile");
      //$(".training-swiper-area .swiper-slide").css("transform", "translate(0, 0)");

      $(".training-swiper-area .training-swiper").each(function(index, item){
        if(mainTrainingSwiper[index] != undefined){
          mainTrainingSwiper[index].destroy();
          mainTrainingSwiper[index] = undefined;
        }
      });
    }
  }
}