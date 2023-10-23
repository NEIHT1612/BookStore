/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.dal.impl;

import com.bookstore.dal.GenericDAO;
import com.bookstore.entity.Account;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author PC
 */
public class AccountDAO extends GenericDAO<Account> {

    @Override
    public List<Account> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(Account t) {
        return insertGenericDAO(t);
    }

    public Account findByUserPass(Account account) {
        String sql = "select * from Account\n"
                + "where username = ? and password = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("username", account.getUsername());
        parameterMap.put("password", account.getPassword());
        List<Account> list = queryGenericDAO(Account.class, sql, parameterMap);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean findByUserPass(String username) {
        String sql = "select * from Account\n"
                + "where username = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("username", username);
        List<Account> list = queryGenericDAO(Account.class, sql, parameterMap);
        return !list.isEmpty();
    }

}
