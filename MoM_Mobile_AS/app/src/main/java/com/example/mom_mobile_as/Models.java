package com.example.mom_mobile_as;

import java.io.Serializable;

public class Models {


    public static class OTP{
        String generatedOTP;

        public String getGeneratedOTP() {
            return generatedOTP;
        }

        public void setGeneratedOTP(String generatedOTP) {
            this.generatedOTP = generatedOTP;
        }
    }
    //Nested Class, StudentModel
    public static class StudentModel implements Serializable {
        int StudentNumber=0;
        String StudentName="";
        String StudentSurname="";
        String StudentEmail="";
        String StudentDOB="";
        String StudentPassword;
        char StudentGender;
        String StudentQualification;
        String Campus;
        //CONSTRUCTORS
        public StudentModel(int studentNumber, String studentName, String studentSurname, String studentEmail, String studentDOB,String Campus) {
            this.StudentNumber = studentNumber;
            this.StudentName = studentName;
            this.StudentSurname = studentSurname;
            this.StudentEmail = studentEmail;
            this.StudentDOB = studentDOB;
            this.Campus=Campus;
        }

        public StudentModel(String studentName, String studentSurname, String studentEmail, String studentDOB, String studentPassword, char studentGender, String studentQualification) {
            this.StudentName = studentName;
            this.StudentSurname = studentSurname;
            this.StudentEmail = studentEmail;
            this.StudentDOB = studentDOB;
            this.StudentPassword = studentPassword;
            this.StudentGender = studentGender;
            this.StudentQualification = studentQualification;
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

        public String getCampus() {
            return Campus;
        }

        public void setCampus(String campus) {
            Campus = campus;
        }

        public String getStudentGender() {
            return String.valueOf(StudentGender);
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

    //CREATE A Medicine Model
    public static class Medicine{
        private int MedicineID;
        private String MedicineName;
        private String MedicineCategory;
        private String NameOfDoctor;
        private int StudentNumber;

        public Medicine(int medicineID, String medicineName, String medicineCategory, String nameOfDoctor, int studentNumber) {
            MedicineID = medicineID;
            MedicineName = medicineName;
            MedicineCategory = medicineCategory;
            NameOfDoctor = nameOfDoctor;
            StudentNumber = studentNumber;
        }

        public  Medicine(){

        }

        public int getMedicineID() {
            return MedicineID;
        }

        public void setMedicineID(int medicineID) {
            MedicineID = medicineID;
        }

        public String getMedicineName() {
            return MedicineName;
        }

        public void setMedicineName(String medicineName) {
            MedicineName = medicineName;
        }

        public String getMedicineCategory() {
            return MedicineCategory;
        }

        public void setMedicineCategory(String medicineCategory) {
            MedicineCategory = medicineCategory;
        }

        public String getNameOfDoctor() {
            return NameOfDoctor;
        }

        public void setNameOfDoctor(String nameOfDoctor) {
            NameOfDoctor = nameOfDoctor;
        }

        public int getStudentNumber() {
            return StudentNumber;
        }

        public void setStudentNumber(int studentNumber) {
            StudentNumber = studentNumber;
        }
    }

    //TODO: CREATE A MOOD MODEL
    public static class MoodModel{
        private int MoodId;
        private String MoodName;
        private String MoodDate;
        private String MoodTime;
        private Integer MoodIntegerImage;
        private String Path;

        public MoodModel(int moodId, String moodName, String moodDate, Integer moodIntegerImage, String imagePath) {
            MoodId = moodId;
            MoodName = moodName;
            MoodDate = moodDate;
            MoodIntegerImage = moodIntegerImage;
            Path = imagePath;
        }

        public MoodModel(String moodName, String moodDate, String moodtime,Integer moodIntegerImage) {
            MoodName = moodName;
            MoodDate = moodDate;
            MoodTime=moodtime;
            MoodIntegerImage = moodIntegerImage;
        }

        public String getMoodTime() {
            return MoodTime;
        }

        public void setMoodTime(String moodTime) {
            MoodTime = moodTime;
        }

        public String getPath() {
            return Path;
        }

        public void setPath(String path) {
            Path = path;
        }

        //DEFAULT CONSTRUCTOR
        public  MoodModel(){

        }

        public int getMoodId() {
            return MoodId;
        }

        public void setMoodId(int moodId) {
            MoodId = moodId;
        }

        public String getMoodName() {
            return MoodName;
        }

        public void setMoodName(String moodName) {
            MoodName = moodName;
        }

        public String getMoodDate() {
            return MoodDate;
        }

        public void setMoodDate(String moodDate) {
            MoodDate = moodDate;
        }

        public Integer getMoodIntegerImage() {
            return MoodIntegerImage;
        }

        public void setMoodIntegerImage(Integer moodIntegerImage) {
            MoodIntegerImage = moodIntegerImage;
        }

        public String getImagePath() {
            return Path;
        }

        public void setImagePath(String imagePath) {
            Path = imagePath;
        }
    }


    //TODO: CREATE A CALL LOG MODEL
    public static class CallLogModel{

        private int CallID;
        private String TelHolder;
        private String TelNumber;
        private String CallDate;
        private String CallTime;

        //DEFAULT CONSTRUCTOR
        public CallLogModel() {

        }

        public CallLogModel(int callID, String telHolder, String telNumber, String callDate, String callTime) {
            CallID = callID;
            TelHolder = telHolder;
            TelNumber = telNumber;
            CallDate = callDate;
            CallTime = callTime;
        }

        public int getCallID() {
            return CallID;
        }

        public void setCallID(int callID) {
            CallID = callID;
        }

        public String getTelHolder() {
            return TelHolder;
        }

        public void setTelHolder(String telHolder) {
            TelHolder = telHolder;
        }

        public String getTelNumber() {
            return TelNumber;
        }

        public void setTelNumber(String telNumber) {
            TelNumber = telNumber;
        }

        public String getCallDate() {
            return CallDate;
        }

        public void setCallDate(String callDate) {
            CallDate = callDate;
        }

        public String getCallTime() {
            return CallTime;
        }

        public void setCallTime(String callTime) {
            CallTime = callTime;
        }
    }

    public static class PsychologistModel{
        private int PsychologistID;
        private String PsychologistName;
        private String PsychologistSurname;
        private String Campus;

        public PsychologistModel(){

        }

        public PsychologistModel(int psychologistID, String psychologistName, String psychologistSurname,String campus) {
            PsychologistID = psychologistID;
            PsychologistName = psychologistName;
            PsychologistSurname = psychologistSurname;
            Campus=campus;
        }

        public int getPsychologistID() {
            return PsychologistID;
        }

        public void setPsychologistID(int psychologistID) {
            PsychologistID = psychologistID;
        }

        public String getPsychologistName() {
            return PsychologistName;
        }

        public void setPsychologistName(String psychologistName) {
            PsychologistName = psychologistName;
        }

        public String getPsychologistSurname() {
            return PsychologistSurname;
        }

        public void setPsychologistSurname(String psychologistSurname) {
            PsychologistSurname = psychologistSurname;
        }

        public String getCampus() {
            return Campus;
        }

        public void setCampus(String campus) {
            Campus = campus;
        }

        @Override
        public String toString(){
            return PsychologistName+" "+PsychologistSurname;
        }
    }

}
