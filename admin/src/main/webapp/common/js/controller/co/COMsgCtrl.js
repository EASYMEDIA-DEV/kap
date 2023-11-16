var msgCtrl = (function(){

	"use strict";

	var config = {
		confirm : {
			cre : {
				target : {
					none : "생성하시겠습니까?",
					menu : "메뉴 생성 하시겠습니까?",
					code : "코드 생성 하시겠습니까?",
					site : "사이트생성 하시겠습니까?"
				}
			},
			ins : "등록하시겠습니까?",
			upd : "수정하시겠습니까?",
			del : "삭제하시겠습니까?",
			sve : "저장하시겠습니까?",
			apl : "적용하시겠습니까?",
			rls : "해제하시겠습니까?",
			apv : "승인하시겠습니까?",
			fpv : "거절하시겠습니까?",
			prs : "처리하시겠습니까?",
			list : "목록으로 이동 시 입력한 값이 초기화 처리됩니다. \n이동하시겠습니까?",
			co : {
				coa : {
					pwdInit : "비밀번호를 초기화하시겠습니까?"
				},
				cod : {
					moveMenu : "메뉴를 이동하시겠습니까?"
				},
				cof : {
					moveCode : "코드를 이동하시겠습니까?"
				},
				cog : {
					revert : "해당 게시물을 되돌리기 하시겠습니까?"
				}
			},
			sm : {
				smd : {
					moveSite : "사이트를 이동하시겠습니까?"
				},
				smf : {
					cancel : "사이트생성을 취소하시겠습니까?"
				}
			}
		},
		success : {
			cre : "생성되었습니다.",
			ins : "등록되었습니다.",
			upd : "수정되었습니다.",
			//del : "삭제되었습니다.",
			sve : "저장되었습니다.",
			apl : "적용되었습니다.",
			aplCom : "적용 완료되었습니다.",
			rls : "해제되었습니다.",
			apv : "승인되었습니다.",
			fpv : "거절되었습니다.",
			prs : "처리되었습니다.",
			del : {
				target : {
					none : "삭제되었습니다.",
					account : "계정이 삭제되었습니다.",
					board : "게시물이 삭제되었습니다.",
				}
			},
			co : {
				login :{
					auth:{
						  email : "인증번호가 발송되었습니다."
						, emailNotCofirm : "인증번호가 일치하지 않습니다."
					},
					password : {
						change : "비밀번호 변경이 완료되었습니다."
					}
				},
				coa : {
					pwdInit : "비밀번호가 초기화되었습니다."
				},
				cob : {
					password : {
						change : "비밀번호가 변경되었습니다."
					}
				},
				cod : {
					moveMenu : "메뉴를 이동하였습니다."
				},
				cof : {
					moveCode : "코드를 이동하였습니다."
				},
				cog : {
					revert : "되돌리기가 완료되었습니다."
				}
			},
			sm : {
				smc : {
					del : "게시물이 삭제되었습니다."
				},
				smd : {
					moveSite : "사이트를 이동하였습니다."
				}
			}
		},
		fail : {
			act : "문제가 발생하여 진행이 중단됩니다. 잠시 후 다시 시도 바랍니다.",
			target : "대상을 선택하세요.",
			notUse : "미노출할 게시물을 선택해주세요.",
			notFileRequired: "첨부파일을 등록해주세요.",
			reason : "사유를 입력하세요.",
			notValidUrl: "올바르지 않은 URL입니다. 'javascript:, /, http://, https://'부터 입력이 가능합니다.",
			http : {
				status : {
					"C401" : "세션이 종료되었습니다. 다시 로그인 해주세요.",
					"C403" : "권한이 없습니다.", 
					"C406" : "해당 IP로는 접근이 불가합니다.\n관리자에게 문의해주세요."
				}
			},
			targetBoard : "삭제할 게시물을 선택하세요.",
			del : {
				target : {
					board : "삭제할 게시물을 선택하세요.",
					account : "삭제할 계정을 선택하세요.",
					menu : "삭제하려는 메뉴를 선택하세요.",
					code : "삭제하려는 코드를 선택하세요.",
					site : "삭제하려는 사이트를 선택하세요."
				}
			},
			co : {
				login : {
					notExist : "관리자 정보를 찾을 수 없습니다.",
					block : "해당 계정은 비활성화된 계정입니다.\n관리자에게 문의해주세요.",
					notMatch : "입력한 정보를 다시 확인해주세요.\n비밀번호 5회 이상 오류 시 로그인이 제한됩니다.",
					countExcess : "비밀번호 5회 실패로 로그인 제한되었습니다.\n관리자에게 문의해주세요.",
					password : {
						temporary : "임시 비밀번호로 로그인하여 비밀번호를 재설정 합니다.",
						changeCycle : "비밀번호 최종변경일자가 90일이 경과되어 비밀번호 변경 페이지로 이동합니다.",
					},
					menuInacc : "접근 가능한 메뉴가 없습니다. 관리자에게 문의해주세요.",
					notIp : "해당 IP로는 접근이 불가합니다. 관리자에게 문의해주세요."
				},
				password : {
					notMatch : "비밀번호가 일치하지 않습니다. 확인 후 입력해주세요.",
					confirm : "비밀번호가 일치하지 않습니다.",
					previous : "이전과 동일한 비밀번호는 사용 불가합니다.",
					previous_notMatch : "기존비밀번호가 일치하지 않습니다."
				},
				coa : {
					cantUseId : "사용불가한 아이디 입니다.",
					rsn : "사유를 입력해주세요.",
					notSelectMenu : "왼쪽 트리구조에서 접근가능한 메뉴를 선택해주세요.",
					cantDltAccount : "해당 계정은 삭제가 불가합니다.",
					cantDltAccounts : "삭제가 불가한 계정이 포함되어있습니다."
				},
				cob : {
					exist : "이미 존재하는 메뉴입니다.",
					notDelete : "삭제하려는 메뉴를 선택하세요.",
					notRename : "명칭 변경하려는 메뉴를 선택하세요.",
					notTopNode : "상단 메뉴를 선택하세요.",
					notSubmit : "선택된 메뉴가 없습니다."
				},
				coc : {
					password : {
						//로그인후 임시비밀번호 변경 페이지 경고 문구
						includeId : "아이디와 동일한 값은 입력할 수 없습니다.",
						sameString : "동일한 문자/숫자를 3자 이상 연속으로 사용 불가합니다.",
						contString : "4자리 이상 연속된 문자/숫자는 사용 불가합니다."
					}
				},
				cod : {
					admin : {
						userRoot : "사용자 메뉴는 드라이브당 한 개만 지정할 수 있습니다."
					},
					user : {
						topNode : "지정된 사용자 메뉴가 없습니다. 관리자에게 문의해주세요."
					}
				},
				cof : {
					exist : "이미 존재하는 코드입니다.",
					notDelete : "삭제하려는 코드를 선택하세요.",
					notRename : "명칭 변경하려는 코드를 선택하세요.",
					notTopNode : "상단 코드를 선택하세요.",
					notSubmit : "선택된 코드가 없습니다."
				},
				cog : {
					remove : "배포된 게시물은 삭제가 불가합니다.",
					revert : {
						targetExcess : "되돌리기할 게시물을 1개만 선택해주세요.",
						permission : "배포된 게시물은 되돌리기가 불가합니다."
					},
					cnts : "내용을 입력해주세요."

				}
			},
			bm : {
				bma : {
					mainExps : "메인 노출은 최대 5개까지만 가능합니다."
				},
				bmd : {
					alreadyYear : "이미 등록된 연도가 있습니다."
				}
			},
			sm : {
				sort : {
					notMoveUp : "더 이상 올릴 수 없습니다.",
					notMoveDown : "더 이상 내릴 수 없습니다.",
				},
				smc : {
					target : "삭제할 게시물을 선택하세요.",
					titl : "제목을 입력해주세요.",
					cntn : "상세내용을 입력하세요.",
					image: "이미지를 첨부해주세요.",
					html: "HTML 내용을 작성해주세요.",
					notuse: "선택한 게시물을 미노출로 변경하시겠습니까?"

				},
				smf : {
					notRename : "명칭 변경하려는 사이트를 선택하세요.",
					notDelete : "삭제하려는 사이트를 선택하세요.",
					notTopNode : "상단 사이트를 선택하세요."
				}
			},
			mp : {
				mpa : {
					al_001 : '시작일자가 종료일자보다 이후일 수 없습니다.' ,
					al_002 : '종료일자가 시작일자보다 이전일 수 없습니다.' ,
					al_003 : '엑셀 다운로드 사유를 입력해주세요.' ,
					al_004 : '저장되었습니다.' ,
					al_005 : '비밀번호가 초기화되었습니다.' ,
					al_006 : '이메일을 입력해주세요.' ,
					al_007 : '이미 사용중인 이메일 주소입니다.' ,
					al_008 : '사용가능한 이메일 주소입니다.' ,
					al_009 : '우편번호를 입력해주세요.' ,
					al_010 : '상세주소를 입력해주세요.' ,
					al_011 : '휴대폰번호를 입력해주세요.' ,
					al_012 : '이메일 중복확인을 해주세요.'
				},
				mpe : {
					notSrchPartsCom: "부품사를 선택해주세요.",
					notSrchPartsCom1: "부품사를 1개만 선택해주세요.",
				}
			},

			ex :{
				deleteCheck:"매칭된 게시물은 삭제 할 수 없습니다.",
				deleteExamOptn: "응답내용은 최소 1개 이상 존재해야 하므로, 더 이상 삭제할 수 없습니다.",
				notExmOptn: "정답을 선택해주세요.",
				notScord100: "배점의 합은 100점을 넘을 수 없습니다.",
				notSrchExam: "평가지를 선택해주세요.",
				notSrchExam1: "평가지를 1개만 선택해주세요.",
			}
		}
	};

	var get_message = function(msgCode){
		return eval("config." + msgCode);
	};

	return {
		getMsg : get_message
	}
}());