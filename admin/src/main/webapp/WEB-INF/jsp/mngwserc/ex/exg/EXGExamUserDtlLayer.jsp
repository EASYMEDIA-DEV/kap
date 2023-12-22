<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="modal fade " id="exgExamUserDtlLayer" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-xlg modal-center" role="document"  data-controller="controller/ex/exg/EXGExamUserDtlCtrl">
        <div class="modal-content">
            <form class="form-horizontal" >
                <div class="modal-header">
                    <h5 class="modal-title" >▣ 평가 상세
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </h5>
                </div>
                <div class="modal-body" id="exgExamUserDtlLayerContainer">
                </div>
                <div class="modal-footer row">
                    <div class="text-center">
                        <button type="submit" class="btn btn-success down mt ">선택</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div><!-- 평가 응답 -->