package com.familytree.data.entities;

import com.familytree.FamilyDatabase;
import com.familytree.Relationship;

import java.awt.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public class FamilyMember {
    private int id;
    public boolean added = false;
    private String name;
    private Date birthDate;
    private Date deathDate;
    private boolean isDeceased;
    private Address address;
    private ArrayList<Integer> parents = new ArrayList<Integer>();
    private ArrayList<Integer> children = new ArrayList<Integer>();
    private int spouse = -1;
    private int stackLayer = -1;

    private int clientId;

    /** 
     * @return int
     */
    public int getSpouse() {
        System.out.println("spouse:" + spouse);
        return spouse;
    }

    public ArrayList<Integer> getParents() {
        return parents;
    }

    public void setParents(ArrayList<Integer> parents) {
        this.parents = parents;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Integer> children) {
        this.children = children;
    }

    public int getStackLayer() {
        return stackLayer;
    }

    public void setStackLayer(int stackLayer) {
        this.stackLayer = stackLayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void addParent(Integer id) {
        if (parents.contains(-5000)) {
            this.parents = new ArrayList<Integer>();
        }
        this.parents.add(id);
    }

    public void addChildren(Integer id) {
        if (added == false) {

            this.children = new ArrayList<Integer>();
            this.added = true;
        }
        this.children.add(id);
    }

    public void setSpouse(int spouse) {
        this.spouse = spouse;
    }

    public FamilyMember(int id, String name, Date birthDate, Date deathDate, boolean isDeceased,
                        String currentResidence) {
        this.parents.add(-5000);
        this.children.add(-5000);

        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.isDeceased = isDeceased;
    }

    public FamilyMember(String name, Date birthDate, Date deathDate, boolean isDeceased) {
        this.parents.add(-5000);
        this.children.add(-5000);

        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.isDeceased = isDeceased;
    }

    @Override
    public String toString() {
        String formattedId = String.format("%-10s", id);
        String formattedAdded = String.format("%-10s", added);
        String formattedName = String.format("%-20s", name);
        String formattedBirthDate = String.format("%-12s", birthDate);
        String formattedDeathDate = String.format("%-12s", deathDate);
        String formattedIsDeceased = String.format("%-12s", isDeceased);
        String formattedParents = String.format("%-20s", parents);
        String formattedChildren = String.format("%-20s", children);
        String formattedSpouse = String.format("%-20s", spouse);
        String formattedStackLayer = String.format("%-10s", stackLayer);
    
        return " [id=" + formattedId +  "| name=" + formattedName + "| birthDate="
                + formattedBirthDate + "| deathDate=" + formattedDeathDate + "| isDeceased=" + formattedIsDeceased
                + "| parents=" + formattedParents + "| children="
                + formattedChildren + "| spouse=" + formattedSpouse + "| stackLayer=" + formattedStackLayer + "]";
    }
    

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public void setDeceased(boolean deceased) {
        isDeceased = deceased;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public boolean isDeceased() {
        return isDeceased;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void processRelationships(Connection connection) {
        ArrayList<Relationship> relationships = (ArrayList<Relationship>) FamilyDatabase
                .findRelationshipsByName(this.getName(), connection);
        if (!relationships.isEmpty()) {
            for (Relationship relationship : relationships) {
                switch (relationship.getType()) {

                    case "marriedto":
                        if (this.spouse == -1) {
                            handleMarriedTo(relationship);
                        }
                        break;
                    case "parentof":
                        if (this.parents.contains(-5000)) {
                            handleParentOf(relationship);


                        }
                        break;
                    // Add more cases for other relationship types as needed
                    default:
                        System.out.println("Unknown relationship type: " + relationship.getType());
                }
            }
        }

    }

    private void handleMarriedTo(Relationship relationship) {

        int memberId1 = relationship.getMember2();
        int memberId2 = relationship.getMember1();
        this.spouse = memberId1;
        System.out.println("Handling 'marriedto' relationship between members " + memberId1 + " and " + memberId2);
        // Add your logic for handling 'marriedto' relationship here
    }

    private void handleParentOf(Relationship relationship) {
        int childId = relationship.getMember2();
        int parentId = relationship.getMember1();
        this.parents.add(parentId);
        System.out.println("Handling 'parentof' relationship: parent " + parentId + " child " + childId);
        // Add your logic for handling 'parentof' relationship here
    }

}
