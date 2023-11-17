<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <button type="button" class="btn btn-inverse btn-sm mb-sm" onclick="cmmCtrl.getEduRoomLayerPop(function(data){
      console.log(data);
     })">검색레이어</button>
</div>
<!-- 교육  매핑 -->
<jsp:include page="/WEB-INF/jsp/mngwserc/eb/ebf/EBFEduRoomSrchLayer.jsp"></jsp:include>