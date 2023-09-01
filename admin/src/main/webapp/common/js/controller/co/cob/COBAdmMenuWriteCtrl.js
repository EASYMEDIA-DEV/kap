define(["ezCtrl", "controller/co/COMenuCtrl"], function(ezCtrl, menuCtrl) {

	"use strict";
	
	// set controller name
	var exports = {
		controller : "controller/co/cob/COBAdmMenuWriteCtrl"
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
			// do something...
			menuCtrl.setJstree(false, true, { "topNode" : "1", "d" : "5", "isMenu" : "N" });
		}
	};
	
	// execute model
	ctrl.exec();
	
	return ctrl;
});