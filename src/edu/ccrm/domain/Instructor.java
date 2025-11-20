package edu.ccrm.domain;
public class Instructor extends Person {
    private String department;
    public Instructor(String id, String fullName, String email, String department) {
        super(id, fullName, email);
        this.department = department;
    }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    @Override
    public void printProfile() {
        System.out.println("Instructor Profile: " + this);
    }
    @Override
    public String toString() {
        return "Instructor[ID=" + getId() +
                ", Name=" + getFullName() +
                ", Email=" + getEmail() +
                ", Dept=" + department +
                "]";
    }
}
