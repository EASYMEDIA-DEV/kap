define(["ezCtrl", "controller/co/COMenuCtrl"], function(ezCtrl, menuCtrl) {

	"use strict";
	
	// set controller name
	var exports = {
		controller : "controller/co/cob/COBUserMenuWriteCtrl"
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
			menuCtrl.setJstree(false, true, { "topNode" : ctrl.obj.data("pageNo"), "d" : "5", "isMenu" :"Y" });
		}
	};
	
	// execute model
	ctrl.exec();
	
	return ctrl;
});