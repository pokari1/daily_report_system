<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
<c:param name="content">
 <h2>顧客　新規登録ページ</h2>

 <%--入力された情報をPOSTで送る　送り先指定 --%>
<form method="POST" action="<c:url value='/customer/create' />">
<%--フォームを反映 --%>
<c:import url="_form.jsp" />
</form>

<p><a href="<c:url value='/customer/index' />">一覧に戻る</a></p>
</c:param>
</c:import>