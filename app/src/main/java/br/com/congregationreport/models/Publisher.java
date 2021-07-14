package br.com.congregationreport.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.congregationreport.R;
import br.com.congregationreport.util.UtilConstants;

public class Publisher {

    private Integer id;
    private String name;
    private String lastName;
    private String gender;
    private String birth;
    private String baptism;
    private String cellPhone;
    private String email;
    private String city;
    private String neighborhood;
    private String address;
    private String number;
    private String gps;
    private String groupCongregation;
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
    private boolean coordinator;
    private boolean secretary;
    private boolean supService;
    private String userName;
    private String password;
    private String notes;
    private String contactName1;
    private String contactNote1;
    private boolean jehovahsWitness1;
    private String contactPhone1;
    private String contactAddress1;
    private String contactName2;
    private String contactNote2;
    private boolean jehovahsWitness2;
    private String contactPhone2;
    private String contactAddress2;
    private boolean changedCongregation;


    public static String TABLE_NAME = "publisher";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " name text,"
            + " last_name text,"
            + " gender text,"
            + " birth text,"
            + " baptism text,"
            + " cell_phone text,"
            + " email text,"
            + " city text,"
            + " neighborhood text,"
            + " address text,"
            + " number text,"
            + " gps text,"
            + " family_head text,"
            + " group_congregation text,"
            + " publisher boolean,"
            + " regular_pioneer boolean,"
            + " special_pioneer boolean,"
            + " missionary boolean ,"
            + " deaf boolean,"
            + " elder boolean,"
            + " sup_group boolean,"
            + " servant_ministerial boolean,"
            + " group_help boolean,"
            + " anointed boolean,"
            + " coordinator boolean,"
            + " secretary boolean,"
            + " sup_service boolean,"
            + " user_name text,"
            + " password text,"
            + " notes text,"
            + " contact_name1 text,"
            + " contact_note1 text,"
            + " contact_is_jehovahs_witness1 boolean,"
            + " contact_phone1 text,"
            + " contact_address1 text,"
            + " contact_name2 text,"
            + " contact_note2 text,"
            + " contact_is_jehovahs_witness2 boolean,"
            + " contact_phone2 text,"
            + " contact_address2 text,"
            + " changed_congregation boolean"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Publisher() {
    }

    public Publisher(String name) {
        this.id = 0;
        this.name = name;
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
                publisher.setGender(jsonObject.getString("gender"));
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
                String email = jsonObject.getString("email");
                if (!email.isEmpty()) {
                    publisher.setEmail(jsonObject.getString("email"));
                }
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
                publisher.setGps(jsonObject.getString("gps"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroupCongregation(jsonObject.getString("group"));
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

            try {
                publisher.setCoordinator(jsonObject.getBoolean("coordinator"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publisher.setSecretary(jsonObject.getBoolean("secretary"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publisher.setSupService(jsonObject.getBoolean("sup_service"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setUserName(jsonObject.getString("user_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPassword(jsonObject.getString("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNotes(jsonObject.getString("notes"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Contact Data 1
            try {
                publisher.setContactName1(jsonObject.getString("contact_name1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactNote1(jsonObject.getString("contact_note1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setJehovahsWitness1(jsonObject.getBoolean("contact_is_jehovahs_witness1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactPhone1(jsonObject.getString("contact_phone1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactAddress1(jsonObject.getString("contact_address1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Contact Data 2
            try {
                publisher.setContactName2(jsonObject.getString("contact_name2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactNote2(jsonObject.getString("contact_note2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setJehovahsWitness2(jsonObject.getBoolean("contact_is_jehovahs_witness2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactPhone2(jsonObject.getString("contact_phone2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactAddress2(jsonObject.getString("contact_address2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setChangedCongregation(jsonObject.getBoolean("changed_congregation"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return publisher;
    }

    public static JSONObject getJson(Publisher publisher) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", publisher.getId());
            object.put("name", publisher.getName());
            object.put("last_name", publisher.getLastName());
            object.put("gender", publisher.getGender());
            object.put("birth", publisher.getBirth());
            object.put("baptism", publisher.getBaptism());
            object.put("cell_phone", publisher.getCellPhone());
            object.put("email", publisher.getEmail());
            object.put("city", publisher.getCity());
            object.put("neighborhood", publisher.getNeighborhood());
            object.put("address", publisher.getAddress());
            object.put("number", publisher.getNumber());
            object.put("gps", publisher.getGps());
            object.put("group", publisher.getGroupCongregation());
            object.put("family_head", publisher.isFamilyHead());
            object.put("publisher", publisher.isPublisher());
            object.put("regular_pioneer", publisher.isRegularPioneer());
            object.put("special_pioneer", publisher.isSpecialPioneer());
            object.put("missionary", publisher.isMissionary());
            object.put("deaf", publisher.isDeaf());
            object.put("elder", publisher.isElder());
            object.put("sup_group", publisher.isSupGroup());
            object.put("servant_ministerial", publisher.isServantMinisterial());
            object.put("group_help", publisher.isGroupHelp());
            object.put("anointed", publisher.isAnointed());
            object.put("coordinator", publisher.isCoordinator());
            object.put("secretary", publisher.isCoordinator());
            object.put("sup_service", publisher.isSupService());
            object.put("user_name", publisher.getUserName());
            object.put("password", publisher.getPassword());
            object.put("notes", publisher.getNotes());
            object.put("contact_name1", publisher.getContactName1());
            object.put("contact_note1", publisher.getContactNote1());
            object.put("contact_is_jehovahs_witness1", publisher.isJehovahsWitness1());
            object.put("contact_phone1", publisher.getContactPhone1());
            object.put("contact_address1", publisher.getContactAddress1());
            object.put("contact_name2", publisher.getContactName2());
            object.put("contact_note2", publisher.getContactNote2());
            object.put("contact_is_jehovahs_witness2", publisher.isJehovahsWitness2());
            object.put("contact_phone2", publisher.getContactPhone2());
            object.put("contact_address2", publisher.getContactAddress2());
            object.put("changed_congregation", publisher.isChangedCongregation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Publisher getUpdate(Publisher publisher, Publisher newDataPublisher) {
        try {
            publisher.setName(newDataPublisher.getName());
            publisher.setLastName(newDataPublisher.getLastName());
            publisher.setGender(newDataPublisher.getGender());
            publisher.setBirth(newDataPublisher.getBirth());
            publisher.setBaptism(newDataPublisher.getBaptism());
            publisher.setCellPhone(newDataPublisher.getCellPhone());
            publisher.setEmail(newDataPublisher.getEmail());
            publisher.setCity(newDataPublisher.getCity());
            publisher.setNeighborhood(newDataPublisher.getNeighborhood());
            publisher.setAddress(newDataPublisher.getAddress());
            publisher.setNumber(newDataPublisher.getNumber());
            publisher.setGps(newDataPublisher.getGps());
            publisher.setNotes(newDataPublisher.getNotes());
            publisher.setGroupCongregation(newDataPublisher.getGroupCongregation());
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
            publisher.setCoordinator(newDataPublisher.isCoordinator());
            publisher.setSecretary(newDataPublisher.isSecretary());
            publisher.setSupService(newDataPublisher.isSupService());
            publisher.setUserName(newDataPublisher.getUserName());
            publisher.setPassword(newDataPublisher.getPassword());
            publisher.setNotes(newDataPublisher.getNotes());
            publisher.setChangedCongregation(newDataPublisher.isChangedCongregation());
            // Contact Data 1
            publisher.setContactName1(newDataPublisher.getContactName1());
            publisher.setContactNote1(newDataPublisher.getContactNote1());
            publisher.setJehovahsWitness1(newDataPublisher.isJehovahsWitness1());
            publisher.setContactPhone1(newDataPublisher.getContactPhone1());
            publisher.setContactAddress1(newDataPublisher.getContactAddress1());
            // Contact Data 2
            publisher.setContactName2(newDataPublisher.getContactName2());
            publisher.setContactNote2(newDataPublisher.getContactNote2());
            publisher.setJehovahsWitness2(newDataPublisher.isJehovahsWitness2());
            publisher.setContactPhone2(newDataPublisher.getContactPhone2());
            publisher.setContactAddress2(newDataPublisher.getContactAddress2());
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

    public String getCustomName(Context context) {
        String customName = this.getName();
        if (this.getGender().equals(context.getResources().getString(R.string.female))) {
            customName = this.getName();
        } else if (this.isServantMinisterial()) {
            customName = this.getName() + " (" + context.getResources().getString(R.string.servant) + ")";
        } else if (this.isElder()) {
            customName = this.getName() + " (" + context.getResources().getString(R.string.elder) + ")";
        }
        return customName;
    }

    public String getBottomName(Context context) {
        String bottomName = "";
        if (this.getGender().equals(UtilConstants.Man)) {
            if (this.isRegularPioneer()) {
                bottomName = context.getResources().getString(R.string.regular_pioneer);
            } else if (this.isSpecialPioneer()) {
                bottomName = context.getResources().getString(R.string.special_pioneer);
            } else if (this.isMissionary()) {
                bottomName = context.getResources().getString(R.string.missionary);
            } else {
                bottomName = context.getResources().getString(R.string.publisher);
            }
        } else {
            if (this.isRegularPioneer()) {
                bottomName = context.getResources().getString(R.string.regular_pioneer_w);
            } else if (this.isSpecialPioneer()) {
                bottomName = context.getResources().getString(R.string.special_pioneer_);
            } else if (this.isMissionary()) {
                bottomName = context.getResources().getString(R.string.missionary_w);
            } else {
                bottomName = context.getResources().getString(R.string.publisher_w);
            }
        }
        return bottomName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getGroupCongregation() {
        return groupCongregation;
    }

    public void setGroupCongregation(String groupCongregation) {
        this.groupCongregation = groupCongregation;
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

    public boolean isCoordinator() {
        return coordinator;
    }

    public void setCoordinator(boolean coordinator) {
        this.coordinator = coordinator;
    }

    public boolean isSecretary() {
        return secretary;
    }

    public void setSecretary(boolean secretary) {
        this.secretary = secretary;
    }

    public boolean isSupService() {
        return supService;
    }

    public void setSupService(boolean supService) {
        this.supService = supService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPublisherBaptized() {
        return publisher && (this.baptism != null && !this.baptism.trim().isEmpty());
    }

    public boolean isElderOrServant() {
        return this.elder || this.servantMinisterial;
    }


    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public String getContactNote1() {
        return contactNote1;
    }

    public void setContactNote1(String contactNote1) {
        this.contactNote1 = contactNote1;
    }


    public String getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactAddress1() {
        return contactAddress1;
    }

    public void setContactAddress1(String contactAddress1) {
        this.contactAddress1 = contactAddress1;
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2;
    }

    public String getContactNote2() {
        return contactNote2;
    }

    public void setContactNote2(String contactNote2) {
        this.contactNote2 = contactNote2;
    }


    public String getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactAddress2() {
        return contactAddress2;
    }

    public void setContactAddress2(String contactAddress2) {
        this.contactAddress2 = contactAddress2;
    }

    public boolean isJehovahsWitness1() {
        return jehovahsWitness1;
    }

    public void setJehovahsWitness1(boolean jehovahsWitness1) {
        this.jehovahsWitness1 = jehovahsWitness1;
    }

    public boolean isJehovahsWitness2() {
        return jehovahsWitness2;
    }

    public void setJehovahsWitness2(boolean jehovahsWitness2) {
        this.jehovahsWitness2 = jehovahsWitness2;
    }

    public String getFullName() {
        String s = this.lastName == null ? "" : " " + this.lastName;
        return this.name + s;
    }

    public boolean isChangedCongregation() {
        return changedCongregation;
    }

    public void setChangedCongregation(boolean changedCongregation) {
        this.changedCongregation = changedCongregation;
    }
}
