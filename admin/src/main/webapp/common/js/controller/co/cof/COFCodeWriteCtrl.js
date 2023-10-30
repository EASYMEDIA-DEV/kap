define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/cof/COFCodeWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);
	
	// create object
	var $formObj = jQuery("#frmData");
	
	ctrl.ctgrInfo = {
		"id" : "0", 
		"topNode" : "0", 
		"tree" : "divCategoris", 
		"select" : "categoryArea", 
		"menuType" : "folder",
		"level" : "",
	};

	ctrl.jstreeInfo = {
		"plugins" : [
	        "themes","json_data","ui","crrm","cookies","search","types","hotkeys","dnd"
		],
		"themes" : {
			"icons" : true
		},
		"json_data" : {
			"ajax" : {
				"url" : "/mngwserc/co/cof/select",
				"cdSeq" : 0,
				"dpth" : 0,
				"method" :"POST",
				"data" : function (n) {
					return {
						"cdSeq" : n.attr ? n.attr("id").replace("node_", "") : parseInt(ctrl.ctgrInfo.topNode),
						"dpth" : n.attr ? n.attr("level") : 0,
						"Ran" : Math.random(),
						"_csrf" : $("#csrfKey").val(),
					};
				}
			}
		},
		"types" : {
			"max_depth" : 5,
			"max_children" : 0,
			"valid_children" : [ "drive", "folder", "notfolder" ],
			"types" : {
				"drive" : {
					"valid_children" : [ "folder", "notfolder" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/root.png"
					},
					"start_drag" : true,
					"move_node" : true,
					"delete_node" : true,
					"remove" : true
				},
				"folder" : {
					"valid_children" : [ "folder", "notfolder" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/folder.png"
					}
				},
				"notfolder" : {
					"valid_children" : [ "folder", "notfolder" ],
					"icon" : {
						"image" : "/common/js/lib/jstree/hold.png"
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
	ctrl.setJstree = function(tCtgrInfo, tUrl)
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
		}

		if (typeof tUrl != "undefined")
		{
			ctrl.jstreeInfo.json_data.url = tUrl;
		}

		var trgtObj = jQuery("#" + ctrl.ctgrInfo.tree);

		trgtObj.bind("before.jstree", function (event, data)
		{

		});

		trgtObj.jstree(ctrl.jstreeInfo).bind("open_node.jstree", function(event, data)
		{
			jQuery(this).find("li[status='N']").each(function(q){
				jQuery(this).attr("rel", "notfolder");
			});
		});
		
		/* 코드 선택 */
		trgtObj.bind("select_node.jstree", function(event, data)
		{
			var prevSeq = jQuery("#cdSeq").val();
        	var slctSeq = data.rslt.obj.attr("id").replace("node_", "");
        	
        	if (prevSeq != slctSeq)
        	{
        		$formObj[0].reset();
        		jQuery.ajax({
        			url : "/mngwserc/co/cof/details",
					type : "get",
					data : 
					{
						"cdSeq" : slctSeq,
						"Ran" : Math.random()
					},
					dataType : "json",
					success : function(r) 
					{
						var rtnData = r;
					    if (typeof rtnData != "undefined") 
						{
							jQuery("#cdSeq").val(rtnData.cdSeq);
							jQuery("#type").val(rtnData.cd ? "update" : "insert");
							jQuery("#cdId").val(rtnData.cdId);
							jQuery("#cd").val(rtnData.cd);
							jQuery("#pCdNm").html(rtnData.cdNm);
							jQuery("input[name='useYn']:input[value='" + rtnData.useYn + "']").prop("checked", true);
							
							if (rtnData.dpth == 1)
							{
								if (rtnData.cdId)
								{
									jQuery("#cdId").prop("readonly", true);
								}
								else
								{
									jQuery("#cdId").prop("readonly", false);
								}
								
								jQuery("#cdId").closest("fieldset").addClass("last-child");
								
								jQuery(".codeInfArea").addClass("hidden");
								jQuery("#cd").addClass("notRequired");
							}
							else
							{
								jQuery("#cdId").prop("readonly", true);
								jQuery("#cdId").closest("fieldset").removeClass("last-child");
								
								jQuery(".codeInfArea").removeClass("hidden");
								jQuery("#cd").removeClass("notRequired");

							}
						}
						else 
						{
							alert(msgCtrl.getMsg("fail.act"));
						}
					},
					error : function(xhr, ajaxSettings, thrownError) 
					{
						console.log(xhr)
						cmmCtrl.errorAjax(xhr);
						jQuery.jstree.rollback(data.rlbk);
					}
				});	
        	}
		});

        /* 코드 생성 */
        trgtObj.bind("create.jstree", function(event, data)
        {
        	if (confirm(msgCtrl.getMsg("confirm.cre.target.code")))
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
					url : "/mngwserc/co/cof/insert",
					type : "post",
					data :
					{
						"parntSeq" : parseInt(parntSeq),
						"pstn" : parseInt(data.rslt.position),
						"cdNm" : data.rslt.name,
						"useYn" : "Y",
						"_csrf" : jQuery("#csrfKey").val()
					},
					success : function(r)
					{
						console.log(r);
						alert(msgCtrl.getMsg("success.cre"));
						window.location.reload();
					},
					error : function(xhr, ajaxSettings, thrownError)
					{
						console.log(xhr);
						cmmCtrl.errorAjax(xhr);
						jQuery.jstree.rollback(data.rlbk);
					}
				});
			}
			else
			{
				jQuery.jstree.rollback(data.rlbk);
			}
		});

        /* 코드명 변경 */
        trgtObj.bind("rename.jstree", function (event, data)
        {

        	if (confirm(msgCtrl.getMsg("confirm.upd")))
			{
				jQuery.ajax({
					url : "/mngwserc/co/cof/name-update",
					type : "post",
					data :
					{
						"cdSeq" : data.rslt.obj.attr("id").replace("node_", ""),
						"cdNm" : data.rslt.new_name,
						"_csrf" : jQuery("#csrfKey").val()
					},
					dataType : "json",
					success : function(r)
					{
						if (r > 0)
						{
							alert(msgCtrl.getMsg("success.upd"));
							jQuery("#pCdNm").html(data.rslt.new_name);
						}
					},
					error : function(xhr, ajaxSettings, thrownError)
					{
						cmmCtrl.errorAjax(xhr);
						jQuery.jstree.rollback(data.rlbk);
					}
				});
			}
			else
			{
				jQuery.jstree.rollback(data.rlbk);
			}
		});

        /* 코드 삭제 */
        trgtObj.bind("remove.jstree", function(event, data)
        {
        	if (data.rslt.obj != undefined)
        	{
				var isOk = false
				var totCnt = data.rslt.obj.length;
				data.rslt.obj.each(function(index){
					jQuery.ajax({
						url : "/mngwserc/co/cof/delete",
						type : "post",
						data :
						{
							"cdSeq" : this.id.replace("node_", ""),
							"_csrf" : jQuery("#csrfKey").val()
						},
						dataType : "json",
						async : false,
						success : function(r)
						{
							if (r > 0)
							{
								isOk = true;
							}
						},
						error : function(xhr, ajaxSettings, thrownError)
						{
							isOk = false;
							cmmCtrl.errorAjax(xhr);
							jQuery.jstree.rollback(data.rlbk);
							return false;
						}
					});

					if(index == totCnt -1){
						if(isOk){
							alert(msgCtrl.getMsg("success.del.target.none"));
						}
					}
				});

			}
			else
			{
				alert(msgCtrl.getMsg("fail.co.cof.notDelete"));
			}
		});
        
        /* 코드 이동 */
		trgtObj.bind("move_node.jstree", function(event, data)
		{
			if (data.rslt.np.attr("id") !== data.rslt.op.attr("id"))
			{
				alert("이동할 수 없습니다.");
				jQuery.jstree.rollback(data.rlbk);
			}
			else
			{
				if (confirm(msgCtrl.getMsg("confirm.co.cof.moveCode")))
				{
					data.rslt.o.each(function(i){
						jQuery.ajax({
							url : "/mngwserc/co/cof/pstn-update",
							type : "post",
							data :
							{
								"cdSeq" : jQuery(this).attr("id").replace("node_", ""),
								"refSeq" : data.rslt.cr === -1 ? ctrl.ctgrInfo.topNode : data.rslt.np.attr("id").replace("node_", ""),
								"pstn" : data.rslt.cp + i,
								"isCopy" : data.rslt.cy ? 1 : 0,
								"_csrf" : jQuery("#csrfKey").val()
							},
							dataType : "json",
							async : false,
							success : function(r)
							{
								if (r > 0)
								{
									alert(msgCtrl.getMsg("success.co.cof.moveCode"));
								}
							},
							error : function(xhr, ajaxSettings, thrownError)
							{
								cmmCtrl.errorAjax(xhr);
								jQuery.jstree.rollback(data.rlbk);
							}
						});
					});
				}
				else
				{
					jQuery.jstree.rollback(data.rlbk);
				}
			}
	    });

		/* 코드 loaded */
	    trgtObj.bind("loaded.jstree", function(event, data)
	    {
	    	jQuery(this).jstree("open_all");

	    	jQuery(this).find("li[status='N']").each(function(q){
				jQuery(this).attr("rel", "notfolder");
			});
        });

	    /* 코드 refresh */
		trgtObj.bind("refresh.jstree", function(event, data)
		{
			$(this).find("li[status='N']").each(function(q){
				$(this).attr("rel", "notfolder");
			});
	    });
	};

	// set model
	ctrl.model = {
		id : {
			// do something...
			btnRoot : {
				event : {
					click : function() {
						$("#" + ctrl.ctgrInfo.tree).jstree("create", -1, "last", { "attr" : { "rel" : "drive" } }, false, false);
					}
				}
			},
			btnChild : {
				event : {
					click : function() {
						var arrSelected = $("#" + ctrl.ctgrInfo.tree).jstree('get_selected');

						// 선택한 코드값이 없거나 상위 코드가 아닐 경우 상단 코드 선택하라는 alert 띄움
						if(arrSelected.size() == 0 || arrSelected.attr("level") != "1") {
							alert(msgCtrl.getMsg("fail.co.cof.notTopNode"));
						} else{
							$("#" + ctrl.ctgrInfo.tree).jstree("create", null, "last", { "attr" : { "rel" : ctrl.ctgrInfo.menuType } });
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
							alert(msgCtrl.getMsg("fail.co.cof.notRename"));
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
							alert(msgCtrl.getMsg("fail.co.cof.notDelete"));
						}
					}
				}
			}
		},
		classname : {
			// do something...
		},
		immediately : function() {
			$formObj.validation({
				after: function (){
					var arrSelected = $("#" + ctrl.ctgrInfo.tree).jstree('get_selected');
					if(arrSelected.size() == 0)
					{
						alert(msgCtrl.getMsg("fail.co.cof.notSubmit"));
						return false;
					}
					return true;
				},
				async : {
					use : true,
					func : function(){
						jQuery.ajax({
							url : "/mngwserc/co/cof/info-update",
							type : "post",
							data : $formObj.serializeArray(),
							dataType : "json",
							success : function(r)
							{
								if (r > 0)
								{
									alert(msgCtrl.getMsg("success.aplCom"));
								}
								else
								{
									alert(msgCtrl.getMsg("fail.co.cof.exist"));
								}
								
								window.location.reload();
							},
							error : function(xhr, ajaxSettings, thrownError)
							{
								cmmCtrl.errorAjax(xhr);
							}
						});	
					}
				}
			});
			
			ctrl.setJstree({ "topNode" : "0", "d" : "2" });
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});