package com.example.badhabits;

import java.time.LocalDate;

public class BadHabitModel {
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    int userId;
    String habit;
    LocalDate date;

    public BadHabitModel(int userId, String habit, LocalDate date) {
        this.userId = userId;
        this.habit = habit;
        this.date = date;
    }
}
