Table Clients {
  client_id  integer [primary key]
  client_name  text
}

Table FamilyMembers {
  member_id  integer [primary key]
  name  text
  birth_date  date
  death_date  date
  is_deceased  boolean
  current_residence  text
  client_id  integer [ref: < Clients.client_id]
}

Table Relationships {
  relationship_id  integer [primary key]
  member_id  integer [ref: < FamilyMembers.member_id]
  related_member_id  integer [ref: < FamilyMembers.member_id]
  relation_type  text
}

Table Addresses {
  address_id  integer [primary key]
  city  text
  member_id  integer [ref: < FamilyMembers.member_id]
}

Table Events {
  event_id  integer [primary key]
  event_date  date
  event_type  text
  // member_id  integer [ref: < FamilyMembers.member_id]
}

Table EventAttendee {
  member_id  integer [ref: < FamilyMembers.member_id]
  event_id  integer [ref: < Events.event_id]

}





