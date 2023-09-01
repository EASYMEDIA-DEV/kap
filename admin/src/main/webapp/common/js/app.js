/*!
 *
 * Centric - Bootstrap Admin Template
 *
 * Version: 1.5
 * Author: @themicon_co
 * Website: http://themicon.co
 * License: https://wrapbootstrap.com/help/licenses
 *
 */

// APP START
// -----------------------------------

(function() {
    "use strict";

    $(initHeader);

    function initHeader()
    {
        // Search modal
        var modalSearch = jQuery(".modal-search");

        jQuery("#header-search").on("click", function(e) {
            e.preventDefault();

            modalSearch.on("show.bs.modal", function() {
                // Add class for white backdrop
                jQuery("body").addClass("modal-backdrop-soft");
            }).on("hidden.bs.modal", function() {
                // Remove class for white backdrop (if not will affect future modals)
                jQuery("body").removeClass("modal-backdrop-soft");
            }).on("shown.bs.modal", function() {
                // Auto focus the search input
                jQuery(".header-input-search").focus();
            }).modal();
        });

        // Settings modal
        var modalSettings = jQuery(".modal-settings");

        jQuery("#header-settings").on("click", function() {
            modalSettings.on('show.bs.modal', function() {
                // Add class for soft backdrop
                jQuery("body").addClass("modal-backdrop-soft");
            }).on('hidden.bs.modal', function() {
                // Remove class for soft backdrop (if not will affect future modals)
                jQuery("body").removeClass("modal-backdrop-soft");
            }).modal();
        });
    }
})();

(function() {
	"use strict";

    $(initSettings);

    function initSettings()
    {
        // Themes setup
        var themes = [
            "theme-1",
            "theme-2",
            "theme-3",
            "theme-4",
            "theme-5",
            "theme-6",
            "theme-7",
            "theme-8",
            "theme-9"
        ];

        var body = jQuery("body");
        var header = jQuery(".layout-container > header");
        var sidebar = jQuery(".layout-container > aside");
        var brand = sidebar.find(".sidebar-header");
        var content = jQuery(".layout-container > main");

        // Handler for themes preview
        jQuery("input[name='setting-theme']:radio").change(function() {
        	//쿠키에서 가져오기
            var index = this.value;

            if (themes[index])
            {
                body.removeClass(themeClassname);
                body.addClass(themes[index]);
                index = parseInt(index) + 1;
                $.cookie("theme", index, {expires:365, path:"/"})
            }
        });

        //현재 테마의 데이터와 저장된 테마가 다르다면 테마를 입힌다.
        var themeIndex = jQuery("body").data("theme");

        themeIndex = parseInt(themeIndex) - 1;

        jQuery("input:radio[name=setting-theme]:input[value='" + themeIndex + "']").attr("checked", true);

        // Regular expression for the pattern bg-* to find the background class
        function themeClassname(index, css)
        {
            var cmatch = css.match(/(^|\s)theme-\S+/g);

            return (cmatch || []).join(' ');
        }

        // Handler for menu links
        jQuery("input[name='headerMenulink']:radio").change(function() {
            var menulinks = jQuery(".menu-link");
            // remove allowed classses
            menulinks.removeClass("menu-link-slide menu-link-arrow menu-link-close");
            // Add selected
            menulinks.addClass(this.value);
        });

        // Handlers for layout variations
        // var lContainer = $('.layout-container');
        jQuery("#sidebar-showheader").change(function() {
            brand[this.checked ? "show" : "hide"]();
            $.cookie("sidebarShowheader", this.checked ? "checked" : "unchedked", {expires:365, path:"/"});
        });

        var sidebarToolbar = jQuery("#sidebar-toolbar");

        jQuery("#sidebar-showtoolbar").change(function() {
            sidebarToolbar[this.checked ? "show" : "hide"]();
            $.cookie("sidebarShowtoolbar", this.checked ? "checked" : "unchedked", {expires:365, path:"/"});
        });

        jQuery("#sidebar-offcanvas").change(function() {
            body[this.checked ? "addClass" : "removeClass"]("sidebar-offcanvas");
            $.cookie("sidebarOffcanvas", this.checked ? "checked" : "unchedked", {expires:365, path:"/"});
        });
    }
})();

(function() {
    "use strict";

    $(sidebarNav);

    function sidebarNav()
    {
        var $sidebarNav = jQuery(".sidebar-nav");
        var $sidebarContent = jQuery(".sidebar-content");

        activate($sidebarNav);

        $sidebarNav.on("click", function(event) {
            var item = getItemElement(event);

            // check click is on a tag
            if (!item)
            {
            	return;
            }

            var ele = jQuery(item), liparent = ele.parent()[0];
            var lis = ele.parent().parent().children(); // markup: ul > li > a
            // remove .active from childs
            lis.find("li").removeClass("active");

            // remove .active from siblings ()
            $.each(lis, function(idx, li) {
                if (li !== liparent)
                {
                	jQuery(li).removeClass("active");
                }
            });

            var next = ele.next();

            if (next.length && next[0].tagName === "UL")
            {
            	ele.parent().toggleClass("active");
                event.preventDefault();
            }
        });

        // find the a element in click context
        // doesn't check deeply, asumens two levels only
        function getItemElement(event)
        {
            var element = event.target, parent = element.parentNode;

            if (element.tagName.toLowerCase() === "a")
            {
            	return element;
            }

            if (parent.tagName.toLowerCase() === "a")
            {
            	return parent;
            }

            if (parent.parentNode.tagName.toLowerCase() === "a")
            {
            	return parent.parentNode;
            }
        }

        function activate(sidebar)
        {
            sidebar.find("a").each(function() {
                var href = $(this).attr("href").replace("#", "");
                var path = href.slice(0, href.lastIndexOf("/"));

                if (href !== "" && window.location.href.indexOf(path) >= 0)
                {
                    var item = jQuery(this).parents("li").addClass("active");
                    // Animate scrolling to focus active item
                    // $sidebarContent.animate({
                    //     scrollTop: $sidebarContent.scrollTop() + item.position().top
                    // }, 1200);
                    return false; // exit foreach
                }
            });
        }

        var layoutContainer = jQuery(".layout-container");
        var $body = jQuery("body");

        // Handler to toggle sidebar visibility on mobile
        jQuery("#sidebar-toggler").click(function(e) {
            e.preventDefault();
            layoutContainer.toggleClass("sidebar-visible");
            // toggle icon state
            jQuery(this).parent().toggleClass("active");
        });

        // Close sidebar when click on backdrop
        jQuery(".sidebar-layout-obfuscator").click(function(e) {
            e.preventDefault();
            layoutContainer.removeClass("sidebar-visible");
            // restore icon
            jQuery("#sidebar-toggler").parent().removeClass("active");
        });

        // Handler to toggle sidebar visibility on desktop
        jQuery("#offcanvas-toggler").click(function(e) {
            e.preventDefault();
            $body.toggleClass("offcanvas-visible");
            // toggle icon state
            jQuery(this).parent().toggleClass("active");
        });

        // remove desktop offcanvas when app changes to mobile
        // so when it returns, the sidebar is shown again
        window.addEventListener("resize", function() {
            if (window.innerWidth < 768)
            {
                $body.removeClass("offcanvas-visible");
                jQuery("#offcanvas-toggler").parent().addClass("active");
            }
        });
    }
})();

(function() {
    "use strict";

    $(formAdvanced);

    function formAdvanced()
    {
    	jQuery.datetimepicker.setLocale("ko");

		// Intup Start -----
		$.each(jQuery(".datetimepicker_input"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y-m-d",
				defaultDate : new Date(jQuery("body").data("curtDt")),
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
				onSelectDate : function(selectedDate, selectedObj) {
					selectedObj.blur();
				}
			});
		});
		// ----- Input End


		// 게시기간(일자) Start -----
		$.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y-m-d",
				defaultDate : new Date(jQuery("body").data("curtDt")),
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
                todayButton: false,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDt   = selectedDate;
					var endDtObj = selectedObj.closest("fieldset").find(".datetimepicker_endDt");
					var endDt	 = new Date(endDtObj.val());

					if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
					{
						endDtObj.val(selectedObj.val());
					}
					
					endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

					selectedObj.blur();
				}
			});
		});

		$.each(jQuery(".datetimepicker_endDt"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y-m-d",
				defaultDate : new Date(jQuery("body").data("curtDt")),
				defaultTime : "00:00",
				scrollMonth : false,
				scrollTime : false,
                todayButton: false,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDtObj = selectedObj.closest("fieldset").find(".datetimepicker_strtDt");
					var strtDt    = new Date(strtDtObj.val());
					var endDt     = selectedDate;

					if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
					{
						strtDtObj.val(selectedObj.val());
					}

					//strtDtObj.datetimepicker("setOptions", { maxDate : endDt });

					selectedObj.blur();
				}
			});
		});

		$.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
			var trgtVal = $.trim(jQuery(obj).val());

			if (trgtVal)
			{
				jQuery(obj).closest("fieldset").find(".datetimepicker_endDt").datetimepicker("setOptions", { minDate : new Date(trgtVal) });
			}
		});
		// ----- 게시기간(일자) End
		// 게시기간(일시) Start -----
		$.each(jQuery(".datetimepicker_strtDtm"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : true,
				format : "Y-m-d H:i",
				defaultDate : new Date(jQuery("body").data("curtDt")),
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
                todayButton: false,
				step : 10,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDtm   = selectedDate;
					var endDtmObj = selectedObj.closest("fieldset").find(".datetimepicker_endDtm");
					var endDtm	  = new Date(endDtmObj.val().replace(/-/gi, "/"));

                    // end mintime, default 변경
                    jQuery(".datetimepicker_endDtm").datetimepicker({ defaultTime : $.hiDate(new Date(strtDtm.getTime()))});
                    endDtmObj.datetimepicker("setOptions", { minTime : strtDtm.getTime() + (60 * 10 * 1000) });

					if (strtDtm.getTime() / (1000 * 3600 * 24) > endDtm.getTime() / (1000 * 3600 * 24))
					{
						endDtmObj.val($.date(new Date(strtDtm.getTime()))+" 23:50");
					}
					
					endDtmObj.datetimepicker("setOptions", { minDate : strtDtm, value : endDtmObj.val(), defaultTime : "23:50" });

					selectedObj.blur();
				},
				onSelectTime : function(selectedTime, selectedObj) {
					var strtDtm   = selectedTime;
					var endDtmObj = selectedObj.closest("fieldset").find(".datetimepicker_endDtm");
					var endDtm	  = new Date(endDtmObj.val().replace(/-/gi, "/"));

					if (strtDtm.getTime() / (3600 * 24) > endDtm.getTime() / (3600 * 24))
					{
						endDtmObj.val(selectedObj.val());
					}

					endDtmObj.datetimepicker("setOptions", { minDate : strtDtm, value : endDtmObj.val() });

                    // end mintime, default 변경
                    jQuery(".datetimepicker_endDtm").datetimepicker({ defaultTime : $.hiDate(new Date(strtDtm.getTime()))});
                    endDtmObj.datetimepicker("setOptions", { minTime : strtDtm.getTime() + (60 * 10 * 1000) });

					selectedObj.blur();
				}
			});
		});

		$.each(jQuery(".datetimepicker_endDtm"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : true,
				format : "Y-m-d H:i",
				defaultDate : new Date(jQuery("body").data("curtDt")),
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
                todayButton: false,
				step : 10,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDtmObj = selectedObj.closest("fieldset").find(".datetimepicker_strtDtm");
					var strtDtm    = new Date(strtDtmObj.val().replace(/-/gi, "/"));
					var endDtm     = selectedDate;

					if (strtDtm.getTime() / (1000 * 3600 * 24) > endDtm.getTime() / (1000 * 3600 * 24))
					{
						strtDtmObj.val(selectedObj.val());
					}

                    var endData = new Date(endDtm.getTime());

                    if(!(endData.getFullYear() == strtDtm.getFullYear() && endData.getMonth() == strtDtm.getMonth() && endData.getDate() == strtDtm.getDate())){
                        jQuery(".datetimepicker_endDtm").datetimepicker("setOptions", { minTime : "00:00", defaultTime : "00:00"});
                    }else{
                        jQuery(".datetimepicker_endDtm").datetimepicker("setOptions", { minTime : strtDtm.getTime() + (60 * 10 * 1000), defaultTime : $.hiDate(new Date(strtDtm.getTime()))});
                    }

					//strtDtmObj.datetimepicker("setOptions", { maxDate : endDtm });

					selectedObj.blur();
				},
				onSelectTime : function(selectedTime, selectedObj) {
					var strtDtmObj = selectedObj.closest("fieldset").find(".datetimepicker_strtDtm");
					var strtDtm    = new Date(strtDtmObj.val().replace(/-/gi, "/"));
					var endDtm     = selectedTime;
					
					if (strtDtm.getTime() / (3600 * 24) > endDtm.getTime() / (3600 * 24))
					{
						strtDtmObj.val(selectedObj.val());
					}

					//strtDtmObj.datetimepicker("setOptions", { maxDate : endDtm });

					selectedObj.blur();
				}
			});
		});

		$.each(jQuery(".datetimepicker_strtDtm"), function(i, obj){
			var trgtVal = $.trim(jQuery(obj).val());

			if (trgtVal)
			{
				jQuery(obj).closest("fieldset").find(".datetimepicker_endDtm").datetimepicker("setOptions", { minDate : new Date(trgtVal) });
			}
		});

        $.hiDate = function(d) {
            var h = d.getHours();
            var i = d.getMinutes()+10;

            if(i==60){
                h+=1;
                i=0;
            }
            if(h==24){
                h=0;
            }
            if (h < 10) {
                h = "0" + h;
            }
            if (i % 60 < 10) {
                i = "0" + i;
            }
            var date = h + ":" + i;

            return date;
        };

        $.date = function(d) {
            var y = d.getFullYear();
            var m = d.getMonth()+1;
            var day = d.getDate();

            if (m < 10) {
                m = "0" + m;
            }
            if (day < 10) {
                day = "0" + day;
            }
            return y + "-" + m + "-" + day;
        };
    }
})();