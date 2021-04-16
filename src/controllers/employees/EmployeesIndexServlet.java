package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManagerの生成
        EntityManager em = DBUtil.createEntityManager();

        //開くページ数を取得
        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }


        //最大件数と開始位置を指定してをgetAllCustomer取得

        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                //何件目からデータを取得するか
                 .setFirstResult(15 * (page - 1))
                 //データの最大取得件数
                 .setMaxResults(15)
                 //複数のデータが結果として取得
                 .getResultList();

        //getCustomersCountを実行しオブジェクトとしてリストへ格納
        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                //1件だけ取得
               .getSingleResult();

       em.close();

       //JSPにデータを送信　
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);


        //フラッシュメッセージ
        // フラッシュメッセージがセッションスコープにセットされていたら
        if(request.getSession().getAttribute("flush") != null) {
         // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存し、セッションスコープからは削除する
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //クライアントからリクエストを受信し、、ビューとして /WEB-INF/views/employees/index.jspを呼び出す
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}