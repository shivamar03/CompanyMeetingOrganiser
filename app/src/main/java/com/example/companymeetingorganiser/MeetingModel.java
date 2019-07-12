package com.example.companymeetingorganiser;

public class MeetingModel {

    private String sTime, eTime, description, participants;

    public String getStartTime() {
        return sTime;
    }

    public void setStartTime(String sTime) {
        this.sTime = sTime;
    }

    public String getEndTime() {
        return eTime;
    }

    public void setEndTime(String eTime) {
        this.eTime = eTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
