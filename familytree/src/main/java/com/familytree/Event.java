package com.familytree;

import com.familytree.data.entities.FamilyMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * represents an event that is set on a date with attendees
 */
public class Event {
    private int eventId;
    private Date eventDate;
    private String eventType;
    private List<FamilyMember> attendees;
    private List<Integer> attendees2;

    /**
     * Constructor for event including the ID, date, and tyope
     * 
     * @param eventId Identification 
     * @param eventDate date of event
     * @param eventType type of event
     */
    public Event(int eventId, Date eventDate, String eventType) {
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.attendees = new ArrayList<>();
        this.attendees2 = new ArrayList<>();

    }

    
    /** 
     * @param eventId
     */
    // Setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // Getters
    public int getEventId() {
        return eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    /**
     * Gets attendees of event
     * 
     * @return attendees
     */
    public List<FamilyMember> getAttendees() {
        return attendees;
    }

    // Method to add an attendee
    public void addAttendee(FamilyMember attendee) {
        attendees.add(attendee);
    }

    public void addAttendee2(int attendee) {
        attendees2.add(attendee);
    }

    @Override
    public String toString() {
        return "|" +
                "eventId:" + eventId +
                "| eventDate=" + eventDate.toString() +
                "| Event Description:'" + eventType + '\'' +
                "| Attendee Count:" + attendees2.size() +
                '|';
    }
}
