/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bookstore.controller.admin;

import com.bookstore.dal.impl.BookDAO;
import com.bookstore.dal.impl.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author PC
 */
@MultipartConfig
public class DashboardServlet extends HttpServlet {

    BookDAO bookDAO;
    CategoryDAO categoryDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bookDAO = new BookDAO();
        categoryDAO = new CategoryDAO();

        List<Book> listBooks = bookDAO.findAll();
        List<Category> listCategory = categoryDAO.findAll();

        HttpSession session = request.getSession();
        session.setAttribute("listBook", listBooks);
        session.setAttribute("listCategories", listCategory);
        
        //chuyen qua dashboard.jsp
        try {
            request.getRequestDispatcher("../views/admin/dashboard/dashboard.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            // Handle ServletException or IOException
            e.printStackTrace(); // or log the exception, or perform appropriate error handling
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null
                ? "defaultFindAll"
                : request.getParameter("action");
        switch (action) {
            case "add":
                addBook(request);
                break;
            case "delete":
                delete(request);
                break;
            case "edit":
                edit(request);
                break;
            default:
                throw new AssertionError();
        }
        response.sendRedirect("dashboard");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addBook(HttpServletRequest request) {
        //tao doi tuong DAO
        bookDAO = new BookDAO();

        //get information
        //get name
        String name = request.getParameter("name");
        //get author
        String author = request.getParameter("author");
        //get price
        int price = Integer.parseInt(request.getParameter("price"));
        //get quantity
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //get description
        String description = request.getParameter("description");
        //get categoryId
        int categoryId = Integer.parseInt(request.getParameter("category"));
        
        String imagePath = null;
        try {
            Part part = request.getPart("image");
            
            //duong dan luu anh 
            String path = request.getServletContext().getRealPath("/images");
            File dir = new File(path);
            //ko co duong dan => tu tao ra
            if(!dir.exists()){
                dir.mkdirs();
            }
            
            File image = new File(dir, part.getSubmittedFileName());
            part.write(image.getAbsolutePath());
            imagePath = "/BookStore-FA23/images/"+image.getName();
        } catch (Exception e) {

        }
            //tao doi tuong Book
            Book book = Book.builder()
                    .name(name)
                    .author(author)
                    .price(price)
                    .quantity(quantity)
                    .description(description)
                    .categoryid(categoryId)
                    .image(imagePath)
                    .build();

            bookDAO.insert(book);
    }

    private void delete(HttpServletRequest request) {
        //tao ra doi tuong DAO
        bookDAO = new BookDAO();
        //get id to delete
        int id = Integer.parseInt(request.getParameter("id"));
        //delete by id
        bookDAO.deleteById(id);
    }

    private void edit(HttpServletRequest request) {
        Book book = new Book();
        //get information
        //get id
        String id = request.getParameter("id");
        //get name
        String name = request.getParameter("name");
        //get author
        String author = request.getParameter("author");
        //get price
        int price = Integer.parseInt(request.getParameter("price"));
        //get quantity
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //get description
        String description = request.getParameter("description");
        //get category Id
        int categoryId = Integer.parseInt(request.getParameter("category"));

        String imagePath = null;
        //get image
        try {

            Part part = request.getPart("image");
            if (part == null || part.getSize() <= 0) {
                // Sử dụng ảnh hiện tại và cập nhật đường dẫn (imagePath)
                String currentImage = request.getParameter("currentImage");
                book.setImage(currentImage);
            } else {
                try {
                    String path = request.getServletContext().getRealPath("/images");
                    File dir = new File(path);
                    //ko có đường dẫn => tự tạo ra
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File image = new File(dir, part.getSubmittedFileName());
                    part.write(image.getAbsolutePath());
                    imagePath = "/BookStore-FA23/images/" + image.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //setter parameter
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setDescription(description);
        book.setCategoryid(categoryId);
        book.setImage(imagePath);

        //tao doi tuong
        BookDAO dao = new BookDAO();
        dao.updateBook(book);
    }

}
