/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.dal.impl;

import com.bookstore.constant.Constant;
import com.bookstore.dal.GenericDAO;
import com.bookstore.entity.Book;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class BookDAO extends GenericDAO<Book> {

    @Override
    public List<Book> findAll() {
        return queryGenericDAO(Book.class);
    }

    public List<Book> findBookByPrice() {
        return queryGenericDAO(Book.class, "select * from Book where price > 80000", parameterMap);
    }

    public static void main(String[] args) {
        for (Book book : new BookDAO().findAll()) {
            System.out.println(book);
        }
        System.out.println(new BookDAO().findTotalRecordByCategory("1"));
        System.out.println(new BookDAO().findTotalRecordByKeyWord("m"));
    }

    public List<Book> getContainsByProperty(String property, String keyword) {
        String sql = "select * from Book where " + property + " like ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        return queryGenericDAO(Book.class, sql, parameterMap);
    }

    public List<Book> findByProperty(String property, String id) {
        String sql = "select * from Book where " + property + " = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("category_id", id);
        return queryGenericDAO(Book.class, sql, parameterMap);
    }

    public List<Book> findListByPage(int page) {
        String sql = "select * from Book\n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("offset", (page - 1) * Constant.RECORD_PER_PAGE);
        parameterMap.put("fetch next", Constant.RECORD_PER_PAGE);
        return queryGenericDAO(Book.class, sql, parameterMap);
    }

    public int findTotalRecord() {
        return findTotalRecordGenericDAO(Book.class);
    }

    public int findTotalRecordByCategory(String categoryId) {
        String sql = "select count(*) \n"
                + "from Book\n"
                + "where category_id = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("category_id", categoryId);
        return findTotalRecordGenericDAO(Book.class, sql, parameterMap);
    }

    public List<Book> findByCategoryAndPage(String categoryId, int page) {
        String sql = "select * from Book\n"
                + "where category_id = ?\n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("category_id", categoryId);
        parameterMap.put("offset", (page - 1) * Constant.RECORD_PER_PAGE);
        parameterMap.put("fetch next", Constant.RECORD_PER_PAGE);
        return queryGenericDAO(Book.class, sql, parameterMap);
    }

    public int findTotalRecordByKeyWord(String keyword) {
        String sql = "select count(*)\n"
                + "from Book\n"
                + "where name like ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        return findTotalRecordGenericDAO(Book.class, sql, parameterMap);
    }

    public List<Book> findByKeyWordAndPage(String keyword, int page) {
        String sql = "select * from Book\n"
                + "where name like ?\n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        parameterMap.put("offset", (page - 1) * Constant.RECORD_PER_PAGE);
        parameterMap.put("fetch next", Constant.RECORD_PER_PAGE);
        return queryGenericDAO(Book.class, sql, parameterMap);
    }

    @Override
    public int insert(Book book) {
        return insertGenericDAO(book);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM [dbo].[Book]\n"
                + "      WHERE id = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("id", id);
        deleteGenericDAO(sql, parameterMap);
    }

    public void updateBook(Book book) {
        String sql = "UPDATE [dbo].[Book]\n"
                + "   SET [name] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[author] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[category_id] = ?\n"
                + " WHERE id = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", book.getName());
        parameterMap.put("image", book.getImage());
        parameterMap.put("quantity", book.getQuantity());
        parameterMap.put("author", book.getAuthor());
        parameterMap.put("price", book.getPrice());
        parameterMap.put("description", book.getDescription());
        parameterMap.put("category_id", book.getCategoryid());
        parameterMap.put("id", book.getId());
        updateGenericDAO(sql, parameterMap);
    }

}
