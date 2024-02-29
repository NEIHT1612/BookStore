/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bookstore.controller.user;

import com.bookstore.constant.Constant;
import com.bookstore.dal.impl.AccountDAO;
import com.bookstore.entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class AuthenServlet extends HttpServlet {
    AccountDAO accountDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = null;

        String action = request.getParameter("action") == null ? "login" : request.getParameter("action");
        switch (action) {
            case "login":
                url = "views/user/home-page/login.jsp";
                break;
            case "register":
                url = "views/user/home-page/register.jsp";
                break;
            case "logout":
                logoutDoGet(request,response);
                url = "home";
                break;
            default:
                url = "login";
                break;
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "login" : request.getParameter("action");
        switch (action) {
            case "login":
                loginDoPost(request, response);
                break;
            case "register":
                registerDoPost(request,response);
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void loginDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountDAO = new AccountDAO();
        //get ve cac thong tin
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Account account = Account.builder()
                .username(username)
                .password(password)
                .build();
        //kiem tra xem account co ton tai trong DB
        account = accountDAO.findByUserPass(account);
        if(account == null){
            request.setAttribute("error", "Username or password incorrect !");
            request.getRequestDispatcher("views/user/home-page/login.jsp").forward(request, response);
        }else{
            HttpSession session = request.getSession();
            session.setAttribute(Constant.SESSION_ACCOUNT, account);
            if(account.getRoleId()==1){
                response.sendRedirect("admin/dashboard");
            }else{
            //set vao session account
            
            response.sendRedirect("home");
            }
        }
    }

    private void logoutDoGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constant.SESSION_ACCOUNT);
    }

    private void registerDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountDAO = new AccountDAO();
        
        String username= request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        Account account = Account.builder()
                .username(username)
                .password(password)
                .email(email)
                .roleId(Constant.ROLE_USER)
                .build();
        
        boolean isExist = accountDAO.findByUserPass(username);
        if(!isExist){
            accountDAO.insert(account);
            response.sendRedirect("home");
        }
        else{
            request.setAttribute("error", "Account existed");
            request.getRequestDispatcher("views/user/home-page/register.jsp").forward(request, response);
        }
    }

}
