package com.example.myjavafx;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
public class Admin extends User implements Authentication, Serializable {
    private static final long serialVersionUID = 7L;
    private String role, workingHours;

    public Admin(){}

    public Admin(String userName,String password,String dateOfBirth,String role,String workingHours){
        super(userName,password,dateOfBirth);
        this.role = role;
        this.workingHours = workingHours;
    }

    public void setRole(String role){
        this.role = role;
    }
    public void setWorkingHours(String workingHours){
        this.workingHours = workingHours;
    }
    public String getRole(){
        return role;
    }
    public String getWorkingHours(){
        return workingHours;
    }

   public void displayCustomerInfo(Database database) {
    boolean foundCustomer = false;

    List<User> users = database.getUsers();

    for (User user : users) {
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            System.out.println("Customer Name: " + customer.getUserName());
            System.out.println("Customer Address: " + customer.getAddress());
            System.out.println("Customer Balance: " + customer.getBalance());
            System.out.println("Customer Gender: " + customer.getGender());
            System.out.println("Customer Interests: " + String.join(", ", customer.getinterests()));
            System.out.println();

            foundCustomer = true;
        }
    }

    if (!foundCustomer) {
        System.out.println("No Customers Found");
    }
}


    @Override
    public void displayMenu() {
        System.out.println("\nAdmin Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. View Customers");
            System.out.println("4. Add Product");
            System.out.println("5. Remove Product");
            System.out.println("6. View Products");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

    }

    @Override
    public void displayProfile() {
     System.out.println("Admin Profile");
     System.out.println("UserName: "+getUserName());
     System.out.println("Date Of Birth: "+getDateOfBirth());
     System.out.println("Role: "+getRole());
     System.out.println("Working Hours: " +getWorkingHours());
    }
    @Override
    public void login(String userName, String password,Database database) {
        if(database.checkUser(userName,password)){
            System.out.println("Login Successful");
            System.out.println("Welcome "+userName);
            System.out.println("You are logged in as an Admin");
        }
        else{
           System.out.println("Invalid Credentials");
        }
    }


    @Override
    public boolean deleteAccount(Database database) {
        return database.removeUser(getUserName());
    }
    @Override
    public void updateProfile(String field, String newValue, Database database) {
        switch (field) {
            case "username" -> setUserName(newValue);
            case "date of birth" -> setDateOfBirth(newValue);
            case "role" -> setRole(newValue);
            case "working hours" -> setWorkingHours(newValue);
            default -> System.out.println("Invalid Field Chosen...");
        }
        User ExistingAdmin = database.getUserByUsername(getUserName());
        if(ExistingAdmin!=null && ExistingAdmin instanceof Admin){
            Admin admin = (Admin) ExistingAdmin;
            admin.setUserName(getUserName());
            admin.setDateOfBirth(getDateOfBirth());
            admin.setRole(getRole());
            admin.setWorkingHours(getWorkingHours());
            database.saveUsers();
            System.out.println("Profile Updated...");
        }
        else{
            System.out.println("Error Updating Profile...[Admin Not Found In Database]");
        }

    }




    @Override
    public void logout() {
        System.out.println("Admin: "+getUserName()+ "Logged out");
    }
    @Override
    public void signup(Scanner scanner, Database database) {
        System.out.println("Enter your username: ");
        String Uname = scanner.nextLine().trim();
        System.out.println("Enter your password: ");
        String pass = scanner.nextLine().trim();
        System.out.println("Enter your date of birth (DD-MM-YYYY): ");
        String dob = scanner.nextLine().trim();
        System.out.println("Enter your role: ");
        String Role = scanner.nextLine().trim();
        System.out.println("Enter your working hours: ");
        String WH = scanner.nextLine().trim();

        Admin newAdmin= new Admin(Uname,pass,dob,Role,WH);
        database.addUser(newAdmin);

    }


}
