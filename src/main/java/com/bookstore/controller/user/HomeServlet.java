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
    private static final String LIST_BOOK_ATTRIBUTE = "listBook";
    private static final BookDAO bookDAO = new BookDAO();
    private static final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //tao SESSION
        HttpSession session = request.getSession();
        //tao DAO
        
        //tao doi tuong pageControl
        PageControl pageControl = new PageControl();
        List<Category> listCategory = categoryDAO.findAll();

        //phan trang
        List<Book> listBook = pagination(request, pageControl);

        //set listBook vao session
        session.setAttribute(LIST_BOOK_ATTRIBUTE, listBook);
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

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void searchByName(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");

        
        List<Book> list = bookDAO.getContainsByProperty("name", keyword);

        HttpSession session = request.getSession();
        session.setAttribute(LIST_BOOK_ATTRIBUTE, list);
    }

//    private void searchByCategory(HttpServletRequest request, HttpServletResponse response) {
//        String id = request.getParameter("id");
//
//        bookDAO = new BookDAO();
//
//        List<Book> list = bookDAO.findByProperty("category_id", id);
//
//        HttpSession session = request.getSession();
//        session.setAttribute(LIST_BOOK_ATTRIBUTE, list);
//    }

    private List<Book> pagination(HttpServletRequest request, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<Book> listBook = null;
        //get action hien tai muon lam gi
        //tim kiem co bao nhieu record va listBookByPage
        String action = request.getParameter("action") == null
                ? "defaultFindAll"
                : request.getParameter("action");
        switch (action) {
            case "search":
                //phan trang dua tren search
                //get ve keyword
                String keyword = request.getParameter("keyword");
                //tinh totalRecord
                totalRecord = bookDAO.findTotalRecordByKeyWord(keyword);
                //tim ve list dua tren keyword va page
                listBook = bookDAO.findByKeyWordAndPage(keyword, page);
                pageControl.setUrlPattern("home?action=search&keyword="+keyword);
                break;
            case "category":
                //phan trang dua tren categoryId
                //get ve categoryId
                String categoryId = request.getParameter("categoryId");
                //tinh totalRecord
                totalRecord = bookDAO.findTotalRecordByCategory(categoryId);
                //tim ve list dua tren category va page
                listBook = bookDAO.findByCategoryAndPage(categoryId, page);
                pageControl.setUrlPattern("home?action=category&categoryId="+categoryId);
                break;
            default:
                //phan trang o trang home
                //tim ve totalRecord
                totalRecord = bookDAO.findTotalRecord();
                //tim ve danh sach cac quyen sach o trang chi dinh
                listBook = bookDAO.findListByPage(page);
                pageControl.setUrlPattern("home?");
        }

        //tim kiem xem tong co bao nhieu page
        int totalPage = (totalRecord % Constant.RECORD_PER_PAGE == 0
                ? (totalRecord / Constant.RECORD_PER_PAGE)
                : (totalRecord / Constant.RECORD_PER_PAGE) + 1);
        //set gia tri vao pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listBook;
    }

}
