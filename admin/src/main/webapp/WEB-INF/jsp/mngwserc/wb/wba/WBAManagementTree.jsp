<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="modal fade wbaTreeLayer" tabindex="-1" role="dialog" data-controller="controller/wb/wba/WBAManagementTreeCtrl">
    <input type="hidden" id="menuType">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" > 사용자 메뉴 선택
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="frmData" name="frmData" method="post" action="" data-alert-type="pass">
                    <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class="col-sm-12 p0">
                        <div id="divCategoris" style="height:800px; overflow-y:auto;">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer row">
                <div class="text-right" style="margin-right: 20px;">
                    <button type="button" class="btn btn-success down mt menuChoice">선택</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/common/js/lib/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/js/lib/jquery/jquery.hotkeys.js"></script>