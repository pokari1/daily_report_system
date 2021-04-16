package controllers.customer;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import utils.DBUtil;

/**
 * Servlet implementation class CustomerCreateServlet
 */
@WebServlet("/customer/create")
public class CustomerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        //CSRF対策
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Customer c = new Customer();


            String name = request.getParameter("name");
            c.setName(name);

            String address = request.getParameter("address");
            c.setAddress(address);

            String charge = request.getParameter("charge");
            c.setCharge(charge);

            //現在日時の情報を持つ日付型のオブジェクトを取得
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            c.setCreated_at(currentTime);
            c.setUpdated_at(currentTime);
            c.setDelete_flag(0);

            // データベースに保存
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/customer/index");
        }
    }

}

