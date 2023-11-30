var msgCtrl = (function(){

	"use strict";

	var config = {
		confirm : {
			cre : "생성하시겠습니까?",
			ins : "등록하시겠습니까?",
			upd : "수정하시겠습니까?",
			del : "삭제하시겠습니까?",
			sve : "저장하시겠습니까?",
			list : "목록으로 이동 시 입력한 값이 초기화 처리됩니다. \n이동하시겠습니까?",
		},
		success : {
			cre : "생성되었습니다.",
			ins : "등록되었습니다.",
			upd : "수정되었습니다.",
			del : "삭제되었습니다.",
			sve : "저장되었습니다.",
			apl : "적용되었습니다.",
			exm : "제출되었습니다.",
		},
		fail : {
			act : "문제가 발생하여 진행이 중단됩니다. 잠시 후 다시 시도 바랍니다.",
			target : "대상을 선택하세요.",
			notFileRequired: "첨부파일을 등록해주세요.",
			http : {
				status : {
					"C401" : "세션이 종료되었습니다. 다시 로그인 해주세요.",
					"C403" : "권한이 없습니다.", 
					"C406" : "해당 IP로는 접근이 불가합니다.\n관리자에게 문의해주세요."
				}
			},
			del : "삭제할 게시물을 선택하세요.",
		}
	};

	var get_message = function(msgCode){
		return eval("config." + msgCode);
	};

	return {
		getMsg : get_message
	}
}());