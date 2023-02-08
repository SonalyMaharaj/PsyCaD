package com.example.mom_mobile_as;

import java.io.Serializable;

public class DiaryModel implements Serializable {

    private int DiaryId;
    private String DiaryDate;
    private String DiaryTitle;
    private String DiaryDescription;
    private String FlaggedWords;
    private String StudentNumber;

    public DiaryModel() {
        DiaryId=0;
        DiaryDate="NONE";
        DiaryTitle="NONE";
        DiaryDescription="NONE";
        FlaggedWords="NONE";
        StudentNumber="NONE";
    }

    public int getDiaryId() {
        return DiaryId;
    }

    public void setDiaryId(int diaryId) {
        DiaryId = diaryId;
    }

    public String getDiaryDate() {
        return DiaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        DiaryDate = diaryDate;
    }

    public String getDiaryTitle() {
        return DiaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        DiaryTitle = diaryTitle;
    }

    public String getDiaryDescription() {
        return DiaryDescription;
    }

    public void setDiaryDescription(String diaryDescription) {
        DiaryDescription = diaryDescription;
    }

    public String getFlaggedWords() {
        return FlaggedWords;
    }

    public void setFlaggedWords(String flaggedWords) {
        FlaggedWords = flaggedWords;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }
}
