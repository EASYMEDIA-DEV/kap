define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/CODashBoardCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	var $formObj = jQuery("#frmData");
	// set model
	ctrl.model = {
		id : {
			// do something...
		},
		classname : {
			moreLink : {
				event : {
					click : function(){
						var driveMenuSeq = $(".actions:eq(0) select option:eq(1)").val();
						var moreLink = $(this).data("link");

						var dashMst = {};
						dashMst.driveMenuSeq = driveMenuSeq;
						dashMst.link = moreLink;
							cmmCtrl.jsonAjax(function(data){
								var rtn = JSON.parse(data);
								location.href="/mngwserc"+rtn.moveLink;
							}, './dashboardAuth', dashMst, "text")
						}
					}
				}
			},
		immediately : function() {

		}
	};

	ctrl.exec();

	return ctrl;
});