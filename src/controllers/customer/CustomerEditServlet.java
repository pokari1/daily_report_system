package controllers.customer;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import utils.DBUtil;

/**
 * Servlet implementation class CustomerEditServlet
 */
@WebServlet("/customer/edit")
public class CustomerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

     // 該当のIDのメッセージ1件のみをデータベースから取得
        Customer c = em.find(Customer.class, Integer.parseInt(request.getParameter("id")));
        em.close();

     // employee情報とセッションIDをリクエストスコープに登録
        request.setAttribute("customer", c);
        request.setAttribute("_token", request.getSession().getId());

        if(c != null) {
            //// employeeIDをセッションスコープに登録
            request.getSession().setAttribute("customer_id", c.getId());

        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/customer/edit.jsp");
        rd.forward(request, response);
    }
}