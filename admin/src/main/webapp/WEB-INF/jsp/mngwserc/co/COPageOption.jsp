<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<option value="10" ${ kl:decode(param.listRowSize, 10, 'selected', '') }>10건씩 보기</option>
<option value="30" ${ kl:decode(param.listRowSize, 30, 'selected', '') }>30건씩 보기</option>
<option value="50" ${ kl:decode(param.listRowSize, 50, 'selected', '') }>50건씩 보기</option>
<option value="50" ${ kl:decode(param.listRowSize, 100, 'selected', '') }>100건씩 보기</option>