package controllers.customer;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class CustomerIndexServlet
 */
@WebServlet("/customer/index")
public class CustomerIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManagerの生成
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }

        //最大件数と開始位置を指定してをgetAllEmployees取得
        List<Customer> customer = em.createNamedQuery("getAllCustomer", Customer.class)
                //何件目からデータを取得するか
                 .setFirstResult(15 * (page - 1))
                 //データの最大取得件数
                 .setMaxResults(15)
                 //複数のデータが結果として取得
                 .getResultList();

        long customer_count = (long)em.createNamedQuery("getCustomerCount", Long.class)
                //1件だけ取得
               .getSingleResult();

       em.close();

        //JSPにデータを送信　
        request.setAttribute("customer", customer);
        request.setAttribute("customer_count", customer_count);
        request.setAttribute("page", page);

    // フラッシュメッセージがセッションスコープにセットされていたら
    if(request.getSession().getAttribute("flush") != null) {
     // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存し、セッションスコープからは削除する
        request.setAttribute("flush", request.getSession().getAttribute("flush"));
        request.getSession().removeAttribute("flush");
    }

    //クライアントからリクエストを受信し、、ビューとして /WEB-INF/views/customer/index.jspを呼び出す
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/customer/index.jsp");
    rd.forward(request, response);
}
}