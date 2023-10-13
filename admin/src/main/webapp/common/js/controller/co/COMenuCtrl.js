define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COMenuCtrl"
	};
	var currentPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/"));
	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);
	// create object function
	ctrl.ctgrInfo = {
		"id" : "0",
		"topNode" : "0",
		"tree" : "divCategoris",
		"select" : "categoryArea",
		"menuType" : "folder",
		"level" : "",
		"uid" : "",
		"did" : "",
		"isMenu" : ""
	};

	ctrl.jstreeInfo = {
		"plugins" : [
			"themes","json_data","ui","cookies","search","types","hotkeys","dnd","crrm"
		],
		"themes" : {
			"icons" : true
		},
		"json_data" : {
			"ajax" : {
				"url" : currentPath + "/select",
				"method" :"POST",
				"menuSeq" : 0,
				"dpth" : 0,
				"admSeq" : ctrl.ctgrInfo.uid,
				"roleCd" : ctrl.ctgrInfo.did,
				"isMenu" : ctrl.ctgrInfo.isMenu,
				"data" : function (n) {
					return {
						"menuSeq" : n.attr ? n.attr("id").replace("node_", "") : parseInt(ctrl.ctgrInfo.topNode),
						"dpth" : n.attr ? n.attr("level") : 0,
						"admSeq" : ctrl.ctgrInfo.uid,
						"roleCd" : ctrl.ctgrInfo.did,
						"isMenu" : ctrl.ctgrInfo.isMenu,
						"Ran" : Math.random(),
						"_csrf" : $("#csrfKey").val(),
					};
				}
			}
		},
		"types" : {
			"max_depth" : 5,
			"max_children" : 0,
			"valid_children" : [ "drive", "folder", "notfolder", "cms", "etc" ],
			"types" : {
				"drive" : {
					"valid_children" : [ "folder", "notfolder", "cms", "etc" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/root.png"
					},
					"start_drag" : false,
					"move_node" : false,
					"delete_node" : true,
					"remove" : false
				},
				"folder" : {
					"valid_children" : [ "folder", "notfolder", "cms", "etc" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/folder.png"
					}
				},
				"notfolder" : {
					"valid_children" : [ "folder", "notfolder", "cms", "etc" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/hold.png"
					}
				},
				"cms" : {
					"valid_children" : [ "folder", "notfolder", "cms", "etc" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/cms.png"
					}
				},
				"etc" : {
					"valid_children" : [ "folder", "notfolder", "cms", "etc" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/etc.png"
					}
				}
			}
		},
		"ui" : {

		},
		"core": {

		}
		,"crrm": {
			"move" : {
				"check_move" :
					function(tree)
					{
						if(tree.np.attr("id") === tree.op.attr("id"))
						{
							return {
								after : true,
								before : false,
								inside : false
							}
						}
						else
						{
							return false;
						}
				}
			}
		}
	};

	// create function
	ctrl.setJstree = function (isCheckbox, isEvent, tCtgrInfo, tUrl)
	{
		if (typeof tCtgrInfo != "undefined")
		{
			if (typeof tCtgrInfo.d != "undefined")
			{
				ctrl.jstreeInfo.types.max_depth = tCtgrInfo.d;
			}

			if (typeof tCtgrInfo.id != "undefined")
			{
				ctrl.ctgrInfo.id = tCtgrInfo.id;
			}

			if (typeof tCtgrInfo.topNode != "undefined")
			{
				ctrl.ctgrInfo.topNode = tCtgrInfo.topNode;
			}

			if (typeof tCtgrInfo.tree != "undefined")
			{
				ctrl.ctgrInfo.tree = tCtgrInfo.tree;
			}

			if (typeof tCtgrInfo.select != "undefined")
			{
				ctrl.ctgrInfo.select = tCtgrInfo.select;
			}

			if (typeof tCtgrInfo.menuType != "undefined")
			{
				ctrl.ctgrInfo.menuType = tCtgrInfo.menuType;
			}

			if (typeof tCtgrInfo.level != "undefined")
			{
				ctrl.ctgrInfo.level = tCtgrInfo.level;
			}

			if (typeof tCtgrInfo.uid != "undefined")
			{
				ctrl.ctgrInfo.uid = tCtgrInfo.uid;
			}

			if (typeof tCtgrInfo.did != "undefined")
			{
				ctrl.ctgrInfo.did = tCtgrInfo.did;
			}

			if (typeof tCtgrInfo.isMenu != "undefined")
			{
				ctrl.ctgrInfo.isMenu = tCtgrInfo.isMenu;
			}
		}

		if (typeof tUrl != "undefined")
		{
			ctrl.jstreeInfo.json_data.ajax.url = tUrl;
		}

		var trgtObj = jQuery("#" + ctrl.ctgrInfo.tree);

		trgtObj.bind("before.jstree", function(event, data)
		{

		});

		trgtObj.jstree(ctrl.jstreeInfo).bind("open_node.jstree", function(event, data)
		{
			if (isCheckbox)
			{
				jQuery(this).find("li").each(function(q){
					if (typeof jQuery(this).attr("checkrole") != "undefined")
					{
						jQuery(this).removeClass("jstree-checked");
						jQuery(this).children("a").children(".jstree-checkbox").remove().addClass("jstree-icon");
						jQuery(this).children("a").css("color", "#36912C");
					}

					if (jQuery(this).attr("status") == "N")
					{
						jQuery(this).children("a").css("color", "#FF3366");
					}
				});
			}
			else
			{
				jQuery(this).find("li[status='N']").each(function(q){
					jQuery(this).attr("rel", "notfolder");
				});
			}
		});

		if (isEvent)
		{
			var $formObj = jQuery("#frmData");

			/* 메뉴 선택 */
			trgtObj.bind("select_node.jstree", function(event, data)
			{
				var prevSeq = jQuery("#menuSeq").val();
				var slctSeq = data.rslt.obj.attr("id").replace("node_", "");

				if (prevSeq != slctSeq)
				{
					$formObj[0].reset();

					jQuery.ajax({
						url : currentPath + "/details",
						type : "post",
						data :
							{
								"menuSeq" : slctSeq,
								"Ran" : Math.random(),
								"_csrf" : jQuery("#csrfKey").val()
							},
						dataType : "json",
						success : function(data)
						{
							var rtnData = data.rtnData;
							if (typeof rtnData != "undefined")
							{
								var menuType = rtnData.menuType;

								jQuery("#menuSeq").val(rtnData.menuSeq);
								jQuery("#pMenuSeq").text(rtnData.menuSeq);
								jQuery("#pMenuNm").html(rtnData.menuNm);
								jQuery("#subMenuNm").val(rtnData.subMenuNm);
								jQuery("input[name='menuType']:input[value='" + rtnData.menuType + "']").prop("checked", true);
								jQuery("#admUrl").val(rtnData.admUrl);
								jQuery("#userUrl").val(rtnData.userUrl);
								jQuery("input[name='wnppYn']:input[value='" + rtnData.wnppYn + "']").prop("checked", true);
								jQuery("input[name='useYn']:input[value='" + rtnData.useYn + "']").prop("checked", true);
								jQuery("#seoKwrd").val(jQuery.getXssVal(rtnData.seoKwrd));
								jQuery("#seoCntn").val(jQuery.getXssVal(rtnData.seoCntn));

								if (menuType == "drive")
								{
									jQuery("#menuTypeArea").addClass("hidden");
								}
								else
								{
									jQuery("#menuTypeArea").removeClass("hidden");
								}

								if (menuType == "cms")
								{
									var replaceStr = "/contentsid/" + rtnData.menuSeq + "/list";

									jQuery("#userUrl").val(rtnData.userUrl.replace(replaceStr, ""));
									jQuery("#spanSuffix").text(replaceStr);
								}
								else
								{
									jQuery("#spanSuffix").text("");
								}
								//GNB노출여부는 2뎊스까지만
								if($("#gnbYnArea").size() > 0){
									if(parseInt(rtnData.dpth) > 5)
									{
										$("#gnbYnArea").addClass("hidden").find(":radio[name='gnbYn'][value='N']").prop("checked", true).addClass("notRequired");
									}
									else
									{
										$("#gnbYnArea").removeClass("hidden").find(":radio[name='gnbYn'][value='"+rtnData.gnbYn+"']").prop("checked", true).removeClass("notRequired");
									}
								}
							}
							else
							{
								alert(msgCtrl.getMsg("fail.act"));
							}
						},
						error : function(xhr, ajaxSettings, thrownError)
						{
							if(xhr.status == 401){
								cmmCtrl.errorAjax(xhr, xhr.status, xhr);
							}else{
								alert(msgCtrl.getMsg("fail.act"));
								jQuery.jstree.rollback(data.rlbk);
							}
						}
					});
				}
			});

			/* 메뉴 생성 */
			trgtObj.bind("create.jstree", function(event, data)
			{
				if (confirm(msgCtrl.getMsg("confirm.cre.target.menu")))
				{
					var dataRsltParent = data.rslt.parent;
					var parntSeq;

					if (dataRsltParent == -1)
					{
						parntSeq = ctrl.ctgrInfo.topNode;
					}
					else
					{
						parntSeq = data.rslt.parent.attr("id").replace("node_", "");
					}
					jQuery.ajax({
						url : currentPath + "/insert",
						type : "post",
						data :
							{
								"parntSeq" : parntSeq,
								"pstn" : data.rslt.position,
								"menuNm" : data.rslt.name,
								"menuType" : data.args[2].attr.rel,
								"gnbYn" : "Y",
								"wnppYn" : "N",
								"useYn" : "Y",
								"_csrf" : jQuery("#csrfKey").val()
							},
						dataType : "json",
						success : function(r)
						{
							alert(msgCtrl.getMsg("success.cre"));
							window.location.reload();
							return false;
						},
						error : function(xhr, ajaxSettings, thrownError)
						{
							if(xhr.status == 401){
								cmmCtrl.errorAjax(xhr, xhr.status, xhr);
							}else {
								alert(msgCtrl.getMsg("fail.act"));
								window.location.reload();
							}
							return false;
						}
					});
				}
				else
				{

					jQuery.jstree.rollback(data.rlbk);
				}
			});

			/* 메뉴명 변경 */
			trgtObj.bind("rename.jstree", function(event, data)
			{
				if (confirm(msgCtrl.getMsg("confirm.upd")))
				{
					jQuery.ajax({
						url : currentPath + "/name-update",
						type : "post",
						data :
							{
								"menuSeq" : data.rslt.obj.attr("id").replace("node_", ""),
								"menuNm" :  $.trim(data.rslt.new_name),
								"_csrf" : jQuery("#csrfKey").val()
							},
						dataType : "json",
						success : function(r)
						{
							if (r.actCnt > 0)
							{
								alert(msgCtrl.getMsg("success.upd"));
								jQuery("#pMenuNm").text($.trim(data.rslt.new_name));
							}
						},
						error : function(xhr, ajaxSettings, thrownError)
						{
							if(xhr.status == 401){
								cmmCtrl.errorAjax(xhr, xhr.status, xhr);
							}else {
								alert(msgCtrl.getMsg("fail.act"));
								jQuery.jstree.rollback(data.rlbk);
							}
						}
					});
				}
				else
				{
					jQuery.jstree.rollback(data.rlbk);
				}
			});

			/* 메뉴 삭제 */
			trgtObj.bind("remove.jstree", function(event, data)
			{
				if (data.rslt.obj != undefined)
				{
					var isOk = false;
					var totCnt = data.rslt.obj.length;

					data.rslt.obj.each(function(index){
						jQuery.ajax({
							url : currentPath + "/delete",
							type : "post",
							data :
								{
									"menuSeq" : this.id.replace("node_", ""),
									"_csrf" : jQuery("#csrfKey").val()
								},
							dataType : "json",
							async : false,
							success : function(r)
							{
								if (r.actCnt > 0)
								{
									isOk = true;
								}
							},
							error : function(xhr, ajaxSettings, thrownError)
							{
								isOk = false;
								if(xhr.status != 401){
									alert(msgCtrl.getMsg("fail.act"));
									jQuery.jstree.rollback(data.rlbk);
									return false;
								}
							}
						});

						if(index == totCnt -1){
							if(isOk){
								alert(msgCtrl.getMsg("success.del.target.none"));
							}
						}
					});
				}
			});

			/* 메뉴 이동 */
			trgtObj.bind("move_node.jstree", function(event, data)
			{
				if (data.rslt.np.attr("id") !== data.rslt.op.attr("id"))
				{
					alert("이동할 수 없습니다.");
					jQuery.jstree.rollback(data.rlbk);
				}
				else
				{
					if (confirm(msgCtrl.getMsg("confirm.co.cod.moveMenu")))
					{
						data.rslt.o.each(function(i){
							jQuery.ajax({
								url : currentPath + "/pstn-update",
								type : "post",
								data :
									{
										"menuSeq" : jQuery(this).attr("id").replace("node_", ""),
										"refSeq" : data.rslt.cr === -1 ? ctrl.ctgrInfo.topNode : data.rslt.np.attr("id").replace("node_", ""),
										"pstn" : data.rslt.cp + i,
										"isCopy" : data.rslt.cy ? 1 : 0,
										"_csrf" : jQuery("#csrfKey").val()
									},
								dataType : "json",
								async : false,
								success : function(r)
								{
									if (r.actCnt > 0)
									{
										alert(msgCtrl.getMsg("success.co.cod.moveMenu"));
										window.location.reload();
										return false;
									}
								},
								error : function(xhr, ajaxSettings, thrownError)
								{
									if(xhr.status == 401){
										cmmCtrl.errorAjax(xhr, xhr.status, xhr);
									}else {
										alert(msgCtrl.getMsg("fail.act"));
										window.location.reload();
									}
									return false;
								}
							});
						});
					}
					else
					{
						jQuery.jstree.rollback(data.rlbk);
						//trgtObj.jstree(true).refresh();
						//window.location.reload();
					}
				}
			});

			$formObj.validation({
				after : function(){
					var admUrl = "", userUrl = jQuery("#userUrl").val();

					// URL 유효성 체크
					if(!cmmCtrl.checkUrl(jQuery("#userUrl"))) {
						return false;
					}

					if (userUrl)
					{
						if (userUrl.indexOf("http://") < 0 && userUrl.indexOf("https://") < 0)
						{
							if (userUrl.indexOf("/") != 0)
							{
								userUrl = "/" + userUrl;
							}

							if (userUrl.indexOf("/mngwserc/") != 0)
							{
								admUrl = "/mngwserc" + userUrl;
							}
							else
							{
								admUrl = userUrl;
							}

							var trgtLen = admUrl.length - 1;

							if (admUrl.lastIndexOf("/") == trgtLen)
							{
								admUrl.substr(0, trgtLen);
							}
						}
						else
						{
							admUrl = userUrl;
						}
					}

					if (jQuery("input[name='menuType']:checked").val() == "cms")
					{
						admUrl += "/contentsid/" + jQuery("#menuSeq").val() + "/list";
					}
					jQuery("#admUrl").val(admUrl);

					return true;
				},
				async : {
					use : true,
					func : function(){
						jQuery.ajax({
							url : currentPath + "/info-update",
							type : "post",
							data : $formObj.serializeArray(),
							dataType : "json",
							success : function(r)
							{
								if (r.actCnt > 0)
								{
									alert(msgCtrl.getMsg("success.aplCom"));
									window.location.reload();
								}
							},
							error : function(xhr, ajaxSettings, thrownError)
							{
								if(xhr.status == 401){
									cmmCtrl.errorAjax(xhr, xhr.status, xhr);
								}else {
									alert(msgCtrl.getMsg("fail.act"));
								}
							}
						});
					}
				}
			});
		}

		/* 메뉴 loaded */
		trgtObj.bind("loaded.jstree", function(event, data)
		{
			jQuery(this).jstree("open_all");

			if (isCheckbox)
			{
				jQuery(this).find("li").each(function (q){
					if (typeof jQuery(this).attr("checkrole") != "undefined")
					{
						jQuery(this).removeClass("jstree-checked");
						jQuery(this).children("a").children(".jstree-checkbox").remove().addClass("jstree-icon");
						jQuery(this).children("a").css("color", "#36912C");
					}

					if (jQuery(this).attr("status") == "N")
					{
						jQuery(this).children("a").css("color", "#FF3366");
					}
				});
			}
			else
			{
				jQuery(this).find("li[status='N']").each(function(q){
					jQuery(this).attr("rel", "notfolder");
				});
			}

			if (ctrl.ctgrInfo.topNode == 1)
			{
				if (jQuery("#authCd").val() == "99")
				{
					jQuery("#divCategoris").jstree("uncheck_all");
					jQuery("#divCategoris").find(".jstree-checkbox").prop("disabled", true);
				}else{
					jQuery("#divCategoris").find(".jstree-checkbox").prop("disabled", false);
				}

				jQuery("#authCd").on("change", function(){
					if (jQuery(this).val() == "99")
					{
						jQuery("#divCategoris").jstree("uncheck_all");
						jQuery("#divCategoris").find(".jstree-checkbox").prop("disabled", true);
					}
					else
					{
						jQuery("#divCategoris").find(".jstree-checkbox").prop("disabled", false);
					}
				});
			}
		});

		/* 메뉴 refresh */
		trgtObj.bind("refresh.jstree", function(event, data)
		{
			if (isCheckbox)
			{
				jQuery(this).find("li").each(function(q){
					if (typeof jQuery(this).attr("checkrole") != "undefined")
					{
						jQuery(this).removeClass("jstree-checked");
						jQuery(this).children("a").children(".jstree-checkbox").remove().addClass("jstree-icon");
						jQuery(this).children("a").css("color", "#36912C");
					}

					if (jQuery(this).attr("status") == "N")
					{
						jQuery(this).children("a").css("color", "#FF3366");
					}
				});
			}
			else
			{
				jQuery(this).find("li[status='N']").each(function(q) {
					jQuery(this).attr("rel", "notfolder");
				});
			}
		});
	};

	ctrl.setJstreeChecked = function()
	{
		var $jstree_checked = "", $jstree_undetermined = "";
		var nodeid = "";
		var tmp1 = "", tmp2 = "";

		$jstree_checked = jQuery(".jstree-checked");
		$jstree_undetermined = jQuery(".jstree-undetermined");

		$jstree_checked.each(function(key, value) {
			nodeid = jQuery(this).attr("id");
			tmp1 += nodeid.replace("node_", "") + ",";
		});

		$jstree_undetermined.each(function(key, value) {
			nodeid = jQuery(this).attr("id");
			tmp2 += nodeid.replace("node_", "") + ",";
		});

		jQuery("input[name='mChecked']").val(tmp1);
		jQuery("input[name='mUndetermined']").val(tmp2);

		return true;
	};

	// set model
	ctrl.model = {
		id : {
			// do something...
			btnDrive : {
				event : {
					click : function() {
						//drive 생성
						jQuery("#" + ctrl.ctgrInfo.tree).jstree("create", -1, "last", { "attr" : { "rel" : "drive" } }, false, false);
					}
				}
			},
			btnRoot : {
				event : {
					click : function() {
						//foler 생성
						ctrl.ctgrInfo.menuType = "folder"
						jQuery("#" + ctrl.ctgrInfo.tree).jstree("create", -1, "last", { "attr" : { "rel" : ctrl.ctgrInfo.menuType } }, false, false);
					}
				}
			},
			//하위 생성
			btnChild : {
				event : {
					click : function() {
						var arrSelected = $("#" + ctrl.ctgrInfo.tree).jstree('get_selected');
						if(ctrl.ctgrInfo.menuType == "drive"){
							//하위 생성은 drive를 생성할 수 없다.
							ctrl.ctgrInfo.menuType = "folder";
						}
						if(arrSelected.size() > 0){
							jQuery("#" + ctrl.ctgrInfo.tree).jstree("create", null, "last", { "attr" : { "rel" : ctrl.ctgrInfo.menuType } });
						} else{
							alert(msgCtrl.getMsg("fail.co.cob.notTopNode"));
						}
					}
				}
			},
			btnRename : {
				event : {
					click : function() {
						var arrSelected = $("#" + ctrl.ctgrInfo.tree).jstree('get_selected');
						if(arrSelected.size() > 0){
							$("#" + ctrl.ctgrInfo.tree).jstree("rename");
						}else{
							alert(msgCtrl.getMsg("fail.co.cob.notRename"));
						}
					}
				}
			},
			btnRemove : {
				event : {
					click : function() {
						var arrSelected = $("#" + ctrl.ctgrInfo.tree).jstree('get_selected');
						if(arrSelected.size() > 0){
							if (confirm(msgCtrl.getMsg("confirm.del"))) {
								$("#" + ctrl.ctgrInfo.tree).jstree("remove", null, "last", {"attr": {"rel": ctrl.ctgrInfo.menuType}});
							}
						}else{
							alert(msgCtrl.getMsg("fail.co.cob.notDelete"));
						}
					}
				}
			}
		},
		classname : {
			// do something...
			rboxmenuType : {
				event : {
					change : function() {
						var menuSeq = jQuery("#menuSeq").val();
						var menuType  = jQuery(this).val();

						if (menuSeq && menuType == "cms")
						{
							jQuery("#spanSuffix").text("/contentsid/" + menuSeq + "/list");
						}
						else
						{
							jQuery("#spanSuffix").text("");
						}
					}
				}
			}
		},
		immediately : function() {
			// do something...
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});