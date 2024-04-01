package com.familytree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventPopupMenu extends JPopupMenu {
    private int eventId;
    private Connection connection;
    private JLabel dateLabel;
    private JLabel typeLabel;
    private JTextArea attendeesArea;
    private JButton addAttendeeButton;

    public EventPopupMenu(int eventId, Connection connection) {
        this.eventId = eventId;
        this.connection = connection;

        dateLabel = new JLabel();
        typeLabel = new JLabel();
        attendeesArea = new JTextArea();
        addAttendeeButton = new JButton("Add Attendee");

        // Populate data
        loadData();

        // Add components to the popup menu
        add(dateLabel);
        add(typeLabel);
        add(new JScrollPane(attendeesArea));
        add(addAttendeeButton);

        // Set action listener for add attendee button
        addAttendeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAttendee();
            }
        });
    }

    private void loadData() {
        try {
            // Fetch data from the database based on the event id
            String query = "SELECT event_date, event_type FROM Event WHERE event_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, eventId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String date = resultSet.getString("event_date");
                    String type = resultSet.getString("event_type");
                    dateLabel.setText("Date: " + date);
                    typeLabel.setText("Type: " + type);
                }
            }

            // Fetch attendees
            query = "SELECT fm.name, fm.birth_date FROM EventAttendee ea JOIN FamilyMembers fm ON ea.member_id = fm.member_id WHERE ea.event_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, eventId);
                ResultSet resultSet = statement.executeQuery();
                StringBuilder attendees = new StringBuilder();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String birthDate = resultSet.getString("birth_date");
                    attendees.append(name).append(" (").append(birthDate).append(")\n");
                }
                attendeesArea.setText(attendees.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAttendee() {
        try {
            // Fetch all family members to populate the dropdown menu
            String query = "SELECT member_id, name, birth_date FROM FamilyMembers";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                // Create a dropdown menu with family member names and birthdates
                JPopupMenu menu = new JPopupMenu();
                while (resultSet.next()) {
                    int memberId = resultSet.getInt("member_id");
                    String name = resultSet.getString("name");
                    String birthDate = resultSet.getString("birth_date");
                    JMenuItem menuItem = new JMenuItem(name + " (" + birthDate + ")");
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Add the selected family member as an attendee to the event
                            try {
                                String insertQuery = "INSERT INTO EventAttendee (event_id, member_id) VALUES (?, ?)";
                                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                    insertStatement.setInt(1, eventId);
                                    insertStatement.setInt(2, memberId);
                                    insertStatement.executeUpdate();
                                }
                                // Reload data after adding attendee
                                loadData();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    menu.add(menuItem);
                }
                // Display the dropdown menu
                menu.show(addAttendeeButton, 0, addAttendeeButton.getHeight());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}