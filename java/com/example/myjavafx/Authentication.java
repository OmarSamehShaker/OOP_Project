package com.example.myjavafx;
public interface  Authentication {

    public void login(String username , String password , Database database);
    public void logout();
    //public void ResetPass();
    //public void ChangePass();
    
    
}
