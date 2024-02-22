/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.constant;

/**
 *
 * @author PC
 */
public class Constant {
    // Private constructor to hide the implicit public one
    private Constant() {
        // Private constructor to prevent instantiation of this class
        throw new AssertionError("Constant class cannot be instantiated.");
    }
    public static final int RECORD_PER_PAGE = 9;
    
    public static final String SESSION_ACCOUNT = "account";
    
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;
}
