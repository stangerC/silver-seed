package com.silver.seed.query.showcase.servlet;

import com.silver.seed.query.Column;
import com.silver.seed.query.entity.TableMeta;
import com.silver.seed.query.entity.ViewType;
import com.silver.seed.query.showcase.entity.list.JqGridMeta;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Liaojian
 */
public class TableTemplateServlet extends HttpServlet {

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
        JqGridMeta jqGridMeta = new JqGridMeta();
        
        Column column = new Column();
        column.setName("name");
        column.setIndex("name");
        column.setWidth(300);        
        jqGridMeta.addColumn(column);
        
        column = new Column();
        column.setName("phone");
        column.setIndex("phone");
        column.setWidth(300);
        jqGridMeta.addColumn(column);
        
        jqGridMeta.setCaption("Customer Info");
        jqGridMeta.setDataType("json");
        jqGridMeta.setUrl("DataServlet");
        jqGridMeta.setMtype("POST");
                
        request.setAttribute("jqGridMeta", jqGridMeta);
        
        TableMeta<JqGridMeta> tableMeta = new TableMeta<JqGridMeta>(null, ViewType.JqGrid);        
        tableMeta.setTitle("Customer Table");        
        tableMeta.setViewMeta(jqGridMeta);
        request.setAttribute("tableMeta", tableMeta);
        
        request.getRequestDispatcher("table-template.jsp").forward(request, response);
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
