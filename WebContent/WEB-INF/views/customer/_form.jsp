<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--フラッシュメッセージ --%>
    <c:if test="${errors != null}">
        <div id="flush_error">
            入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
            </c:forEach>
    </div>
    </c:if>

    <label for="name">会社名</label><br />
    <input type="text" name="name" value="${customer.name}" />
    <br /><br />

    <label for="address">住所</label><br />
    <input type="text" name="address" value="${customer.address}" />
    <br /><br />


    <label for="charge">担当者</label><br />
    <input type="text" name="charge" value="${customer.charge}" />
    <br /><br />


        <%--フォームから非表示データで数値を送る --%>
    <input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>