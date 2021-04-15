<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- JSTLのうちのコア機能を c という名前で利用できるようにする設定↑例　c: --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- JSTLのうちI18nの機能を fmt という名前で利用できるようにする設定↑例　fmt:  --%>

<%-- JSPのテンプレートファイルを取り込む↓　url=指定 --%>
<c:import url="../layout/app.jsp">
    <%--name属性がcontent パラメータ指定--%>
    <c:param name="content">
    <%--  フラッシュメッセージを出す--%>
        <%--単一の条件分岐　flushが空出ない場合--%>
        <c:if test="${flush != null}">
            <%--この文章のid属性＝flush_success --%>
            <div id="flush_success">
                <%-- "変数"を出力 --%>
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日報管理システムへようこそ</h2>
        <h3>【自分の日報　一覧】</h3>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <%--繰り返し　カウンタ変数report items＝reports配列　varStatusループ状態を格納するオブジェクト名指定  --%>
                <c:forEach var="report" items="${reports}" varStatus="status">
                    <%--statusループのインデックス番号（1から開始） --%>
                    <tr class="row${status.count % 2}">
                        <%--　report_name欄に?report.employee.name?変数表示　 --%>
                        <td class="report_name"><c:out value="${report.employee.name}" /></td>
                        <%-- report_date欄に？report.report_date変数表示 patternで出力するフォーマット指定--%>
                        <td class="report_date"><fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                        <%-- レポートタイトル表示--%>
                        <td class="report_title">${report.title}</td>
                        <%-- 詳細を見る表示　選択したreport.idのshowページへリンク--%>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

<%--件数表示 --%>
        <div id="pagination">
            （全 ${reports_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/reports/new' />">新規日報の登録</a></p>
    </c:param>
</c:import>