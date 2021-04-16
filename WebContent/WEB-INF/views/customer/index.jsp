<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <%--フラッシュメッセージ --%>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>


        <h2>顧客　一覧</h2>
        <table id="customer_list">
            <tbody>
                <tr>
                    <th class="customer_name">会社名</th>
                    <th class="customer_address">住所</th>
                    <th class="customer_charge">担当者</th>
                    <th class="customer_action">操作</th>
                </tr>
                <c:forEach var="customer" items="${customer}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="customer_name"><c:out value="${customer.name}" /></td>
                        <td class="customer_address"><c:out value="${customer.address}" /></td>
                        <td class="customer_charge"><c:out value="${customer.charge}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${customer.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/customer/show?id=${customer.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%--件数表示 --%>
        <div id="pagination">
            （全 ${customer_count} 件）<br />
            <%--繰り返し　 --%>
            <c:forEach var="i" begin="1" end="${((customer_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/customer/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
         </div>

          <p><a href="<c:url value='/customer/new' />">新規従業員の登録</a></p>

    </c:param>
</c:import>