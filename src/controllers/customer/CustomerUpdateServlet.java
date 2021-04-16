package controllers.customer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import models.validators.CustomerValidator;
import utils.DBUtil;
/**
 * Servlet implementation class CustomerUpdateServlet
 */
@WebServlet("/customer/update")
public class CustomerUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //CSRF対策
    String _token = request.getParameter("_token");
    if(_token != null && _token.equals(request.getSession().getId())) {
        EntityManager em = DBUtil.createEntityManager();

        // セッションスコープからメッセージのIDを取得して
        // 該当のIDのメッセージ1件のみをデータベースから取得
        Customer c = em.find(Customer.class, (Integer)(request.getSession().getAttribute("customer_id")));


        c.setName(request.getParameter("name"));
        c.setAddress(request.getParameter("address"));
        c.setCharge(request.getParameter("charge"));

        c.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        List<String> errors = CustomerValidator.validate(c);
        if(errors.size() > 0) {
            em.close();

            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("report", c);
            request.setAttribute("errors", errors);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/customer/edit.jsp");
            rd.forward(request, response);
        } else {
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "更新が完了しました。");

            request.getSession().removeAttribute("customer_id");

            response.sendRedirect(request.getContextPath() + "/customer/index");
        }
    }
}

}