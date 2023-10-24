define(["ezCtrl", "controller/co/COBDashBoardCtrl"], function(ezCtrl, menuCtrl) {

	"use strict";
	
	// set controller name
	var exports = {
		controller : "controller/co/cob/COBDashBoardCtrl"
	};
	
	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);
	
	// create object function
			
	// set model
	ctrl.model = {
		id : {
			// do something...
		},
		classname : {
			// do something...
		},
		immediately : function() {

		}
	};
	
	// execute model
	ctrl.exec();
	
	return ctrl;
});