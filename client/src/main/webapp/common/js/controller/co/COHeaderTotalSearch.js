define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COHeaderTotalSearch"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);
	// create object
	var $formObj = ctrl.obj.find("form").eq(0);
	// set model
	ctrl.model = {
		id : {
			// do something...
			totalSearchButton : {
				event : {
					click : function() {
						$formObj.submit();
					}
				}
			},
		},
		classname : {
			// do something...
			frontTotalKeyword : {
				event : {
					click : function() {
						location.href="/search?q=" + $(this).text();
					}
				}
			},
		},
		immediately : function() {
			// 유효성 검사
			$formObj.validation({
				before: function(){

				},
				after : function() {
					var isValid = true
					return isValid;
				},
				customfunc : function(obj, tagid, okval, msg){

				},
				async : {
					use : true,
					func : function (){
						location.href="/search?q=" + $formObj.find("input[name=q]").val();
					}
				},
				msg : {
					confirm : {
						init : ""
					}
				}
			});
		}
	};
	// execute model
	ctrl.exec();
	return ctrl;
});