package com.silver.seed.query.showcase.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.silver.seed.core.paging.Paging;
import com.silver.seed.query.entity.jqgrid.JqGridData;
import com.silver.seed.query.showcase.entity.Customer;
import com.silver.seed.query.showcase.repository.CustomerRepository;
import com.silver.seed.query.showcase.service.CustomerService;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.data.domain.Page;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Liaojian
 */
public class DataServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        Paging paging = new Paging();
        
        Map<String, String[]> parameters =  request.getParameterMap();
        Set<String> keySet = parameters.keySet();
        for(String key : keySet) {            
            String value = request.getParameter(key);
            System.out.println(String.format("key:[%s];value:[%s]", key, value));
            if("rows".equals(key)) {
                paging.setPageSize(Integer.parseInt(value));
            }
            if("page".equals(key)) {
                paging.setPageNumber(Integer.parseInt(value));
            }
        }
        
        try {            
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
                    request.getServletContext());
            
            String[] names = ctx.getBeanDefinitionNames();            
            CustomerService customerService = (CustomerService)ctx.getBean("customerService");                                                                              
            
            Page<Customer> page = customerService.getAll(paging);
            JqGridData data = new JqGridData(page, new String[]{"name", "phone"}, JqGridData.INCLUDES);  
            
//            List<Customer> customers = page.getContent();            
//            JqGridData data = new JqGridData(customers, new String[]{"name", "phone"}, JqGridData.INCLUDES, 10, 1);                        
            
            data.toJsonString();
            
            System.out.println(data.toJsonString());    
            out.println(data.toJsonString());                        
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
