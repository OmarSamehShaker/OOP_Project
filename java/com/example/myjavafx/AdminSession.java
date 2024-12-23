package com.example.myjavafx;

public class AdminSession {
    private static Admin currentAdmin;

    private AdminSession() {
        // Private constructor to prevent instantiation
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }
}
