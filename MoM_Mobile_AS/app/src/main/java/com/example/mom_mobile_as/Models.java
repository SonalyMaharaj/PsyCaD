package com.example.mom_mobile_as;

public class Models {


    //Nested Class, StudentModel
    public static class StudentModel {
        int StudentNumber=0;
        String StudentName="";
        String StudentSurname="";
        String StudentEmail="";
        String StudentDOB="";
        String StudentPassword;
        char StudentGender;
        String StudentQualification;

        //CONTRUCTORS

        public StudentModel(int studentNumber, String studentName, String studentSurname, String studentEmail, String studentDOB) {
            StudentNumber = studentNumber;
            StudentName = studentName;
            StudentSurname = studentSurname;
            StudentEmail = studentEmail;
            StudentDOB = studentDOB;
        }

        public StudentModel(String studentName, String studentSurname, String studentEmail, String studentDOB, String studentPassword, char studentGender, String studentQualification) {
            StudentName = studentName;
            StudentSurname = studentSurname;
            StudentEmail = studentEmail;
            StudentDOB = studentDOB;
            StudentPassword = studentPassword;
            StudentGender = studentGender;
            StudentQualification = studentQualification;
        }

        public StudentModel(){

        } //default constractor


        //GETTERS AND SETTERS
        public int getStudentNumber() {
            return StudentNumber;
        }

        public void setStudentNumber(int studentNumber) {
            StudentNumber = studentNumber;
        }

        public String getStudentName() {
            return StudentName;
        }

        public void setStudentName(String studentName) {
            StudentName = studentName;
        }

        public String getStudentSurname() {
            return StudentSurname;
        }

        public void setStudentSurname(String studentSurname) {
            StudentSurname = studentSurname;
        }

        public String getStudentEmail() {
            return StudentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            StudentEmail = studentEmail;
        }

        public String getStudentDOB() {
            return StudentDOB;
        }

        public void setStudentDOB(String studentDOB) {
            StudentDOB = studentDOB;
        }

        public String getStudentPassword() {
            return StudentPassword;
        }

        public void setStudentPassword(String studentPassword) {
            StudentPassword = studentPassword;
        }

        public char getStudentGender() {
            return StudentGender;
        }

        public void setStudentGender(char studentGender) {
            StudentGender = studentGender;
        }

        public String getStudentQualification() {
            return StudentQualification;
        }

        public void setStudentQualification(String studentQualification) {
            StudentQualification = studentQualification;
        }

    }




}
