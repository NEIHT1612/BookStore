/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bookstore.controller.user;

import com.bookstore.constant.Constant;
import com.bookstore.dal.GenericDAO;
import com.bookstore.dal.impl.BookDAO;
import com.bookstore.dal.impl.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import com.bookstore.entity.PageControl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HomeServlet extends HttpServlet {
    BookDAO bookDAO;
    CategoryDAO categoryDAO;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bookDAO = new BookDAO();
        categoryDAO = new CategoryDAO();
        PageControl pageControl = new PageControl();
        
        //get du lieu tu DB len
        List<Book> listBook = null;
        List<Category> listCategory = categoryDAO.findAll();
        
        //tao ra session
        HttpSession session = request.getSession();
        
        String action;
        try {
            action = request.getParameter("action");
            if(action == null){
                action = "";
            }
        } catch (Exception e) {
            action = "";
        }
        
        switch(action){
            default:
                listBook = pagination(request,response,pageControl);
                break;
        }
        
        //set listBook vao session
        session.setAttribute("listBook", listBook);
        session.setAttribute("listCategory", listCategory);
        request.setAttribute("pageControl", pageControl);
        
        //go to homepage
        request.getRequestDispatcher("views/user/home-page/homePage.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
        
        String url = "home";
        String action = request.getParameter("action");
        switch(action){
            case "search":
                searchByName(request,response);       
                url = "views/user/home-page/homePage.jsp";
                break;
            case "category":
                searchByCategory(request,response);
                url = "views/user/home-page/homePage.jsp";
                break;
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void searchByName(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        
        bookDAO = new BookDAO();
        List<Book> list = bookDAO.getContainsByProperty("name", keyword);
        
        HttpSession session = request.getSession();
        session.setAttribute("listBook", list);
    }

    private void searchByCategory(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        
        bookDAO = new BookDAO();
        
        List<Book> list = bookDAO.findByProperty("category_id", id);
        
        HttpSession session = request.getSession();
        session.setAttribute("listBook", list);
    }

    private List<Book> pagination(HttpServletRequest request, HttpServletResponse response, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        bookDAO = new BookDAO();
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        //tim kiem xem co tong bao nhieu record
        int totalRecord = bookDAO.findTotalRecord();
        //tim kiem xem co tong bao nhieu page
        int totalPage = (totalRecord % Constant.RECORD_PER_PAGE == 0 ?
                (totalRecord / Constant.RECORD_PER_PAGE):
                (totalRecord / Constant.RECORD_PER_PAGE) + 1);
        //set vao pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);
        return bookDAO.findListByPage(page);
    }

}
