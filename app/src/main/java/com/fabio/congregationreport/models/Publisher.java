package com.fabio.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Publisher {
    private Integer id;
    private String name;
    private String lastName;
    private String birth;
    private String baptism;
    private String cellPhone;
    private String phones;
    private String email;
    private String city;
    private String neighborhood;
    private String address;
    private String number;
    private String notes;
    private String gender;
    private String group;
    private boolean familyHead;
    private boolean publisher;
    private boolean regularPioneer;
    private boolean specialPioneer;
    private boolean missionary;
    private boolean deaf;
    private boolean elder;
    private boolean supGroup;
    private boolean servantMinisterial;
    private boolean groupHelp;
    private boolean anointed;
    public static String TABLE_NAME = "publisher";

    public Publisher() {
    }

    public static Publisher convertJson(JSONObject jsonObject) {
        Publisher publisher = new Publisher();
        try {
            publisher.setId(jsonObject.getInt("id"));
            try {
                publisher.setName(jsonObject.getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setLastName(jsonObject.getString("last_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setBirth(jsonObject.getString("birth"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setBaptism(jsonObject.getString("baptism"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setCellPhone(jsonObject.getString("cell_phone"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPhones(jsonObject.getString("phones"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setEmail(jsonObject.getString("email"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setCity(jsonObject.getString("city"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNeighborhood(jsonObject.getString("neighborhood"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setAddress(jsonObject.getString("address"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNumber(jsonObject.getString("number"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNotes(jsonObject.getString("notes"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGender(jsonObject.getString("gender"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroup(jsonObject.getString("group"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setFamilyHead(jsonObject.getBoolean("family_head"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPublisher(jsonObject.getBoolean("publisher"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setRegularPioneer(jsonObject.getBoolean("regular_pioneer"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSpecialPioneer(jsonObject.getBoolean("special_pioneer"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setMissionary(jsonObject.getBoolean("missionary"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setDeaf(jsonObject.getBoolean("deaf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setElder(jsonObject.getBoolean("elder"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupGroup(jsonObject.getBoolean("sup_group"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setServantMinisterial(jsonObject.getBoolean("servant_ministerial"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroupHelp(jsonObject.getBoolean("group_help"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setAnointed(jsonObject.getBoolean("anointed"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return publisher;
    }

    public static Publisher getUpdatePublisher(Publisher publisher, Publisher newDataPublisher) {
        try {
            publisher.setName(newDataPublisher.getName());
            publisher.setLastName(newDataPublisher.getLastName());
            publisher.setBirth(newDataPublisher.getBirth());
            publisher.setBaptism(newDataPublisher.getBaptism());
            publisher.setCellPhone(newDataPublisher.getCellPhone());
            publisher.setPhones(newDataPublisher.getPhones());
            publisher.setEmail(newDataPublisher.getEmail());
            publisher.setCity(newDataPublisher.getCity());
            publisher.setNeighborhood(newDataPublisher.getNeighborhood());
            publisher.setAddress(newDataPublisher.getAddress());
            publisher.setNumber(newDataPublisher.getNumber());
            publisher.setNotes(newDataPublisher.getNotes());
            publisher.setGender(newDataPublisher.getGender());
            publisher.setGroup(newDataPublisher.getGroup());
            publisher.setFamilyHead(newDataPublisher.isFamilyHead());
            publisher.setPublisher(newDataPublisher.isPublisher());
            publisher.setRegularPioneer(newDataPublisher.isRegularPioneer());
            publisher.setSpecialPioneer(newDataPublisher.isSpecialPioneer());
            publisher.setMissionary(newDataPublisher.isMissionary());
            publisher.setDeaf(newDataPublisher.isDeaf());
            publisher.setElder(newDataPublisher.isElder());
            publisher.setSupGroup(newDataPublisher.isSupGroup());
            publisher.setServantMinisterial(newDataPublisher.isServantMinisterial());
            publisher.setGroupHelp(newDataPublisher.isGroupHelp());
            publisher.setAnointed(newDataPublisher.isAnointed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publisher;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBaptism() {
        return baptism;
    }

    public void setBaptism(String baptism) {
        this.baptism = baptism;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isFamilyHead() {
        return familyHead;
    }

    public void setFamilyHead(boolean familyHead) {
        this.familyHead = familyHead;
    }

    public boolean isPublisher() {
        return publisher;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public boolean isRegularPioneer() {
        return regularPioneer;
    }

    public void setRegularPioneer(boolean regularPioneer) {
        this.regularPioneer = regularPioneer;
    }

    public boolean isSpecialPioneer() {
        return specialPioneer;
    }

    public void setSpecialPioneer(boolean specialPioneer) {
        this.specialPioneer = specialPioneer;
    }

    public boolean isMissionary() {
        return missionary;
    }

    public void setMissionary(boolean missionary) {
        this.missionary = missionary;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public void setDeaf(boolean deaf) {
        this.deaf = deaf;
    }

    public boolean isElder() {
        return elder;
    }

    public void setElder(boolean elder) {
        this.elder = elder;
    }

    public boolean isSupGroup() {
        return supGroup;
    }

    public void setSupGroup(boolean supGroup) {
        this.supGroup = supGroup;
    }

    public boolean isServantMinisterial() {
        return servantMinisterial;
    }

    public void setServantMinisterial(boolean servantMinisterial) {
        this.servantMinisterial = servantMinisterial;
    }

    public boolean isGroupHelp() {
        return groupHelp;
    }

    public void setGroupHelp(boolean groupHelp) {
        this.groupHelp = groupHelp;
    }

    public boolean isAnointed() {
        return anointed;
    }

    public void setAnointed(boolean anointed) {
        this.anointed = anointed;
    }
}
