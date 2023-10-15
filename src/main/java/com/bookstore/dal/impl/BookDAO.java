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
        for (Book book : new BookDAO().findListByPage(2)) {
            System.out.println(book);
        }
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

}
