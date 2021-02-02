package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.CongReport;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublisherDAO extends GenericDAO<Publisher> {
    public PublisherDAO(Context context) {
        super(context, Publisher.class);
    }


    public Publisher getPublisher(Integer id) {
        // Publicadores
        Publisher publisher = new Publisher();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "id";
        filtros.put(tipoColuna, id.toString());
        Cursor cursor = findFilterByEq(filtros, "name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            publisher = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return publisher;
    }

    public Publisher findPublisherByUser(String userName) {
        // Publicadores
        Publisher publisher = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "user_name";
        filtros.put(tipoColuna, userName);
        Cursor cursor = findFilterByEq(filtros, "name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            publisher = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return publisher;
    }

    public List<Publisher> findPublisherByGroup(String group) {
        // Publicadores
        List<Publisher> publishers = new ArrayList<>();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "group_congregation";
        filtros.put(tipoColuna, group);
        Cursor cursor = findFilterByEq(filtros, "name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Publisher publisher = readRow(cursor);
            publishers.add(publisher);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return publishers;
    }

    public List<Publisher> findPublishers() {
        List<Publisher> publishers = new ArrayList<>();

        Cursor cursor = findAll("name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Publisher publisher = readRow(cursor);
            publishers.add(publisher);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return publishers;
    }

    public List<Publisher> findPublishersByType(Context context, int type) {
        List<Publisher> publishers = new ArrayList<>();
        String query = "";

        try {
            switch (type) {
                case CongReport.PUBLISHER:
                    query = Util.parseInputStreamToString(context.getAssets().open("find_only_publishers.sql"));
                    break;
                case CongReport.AUXILIARY:
                    query = Util.parseInputStreamToString(context.getAssets().open("find_only_auxiliary.sql"));
                    break;
                case CongReport.DEAF:
                    query = Util.parseInputStreamToString(context.getAssets().open("find_only_deaf.sql"));
                    break;
                case CongReport.REGULAR:
                    query = Util.parseInputStreamToString(context.getAssets().open("find_only_regular.sql"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cursor cursor = findFilterWhere(query);

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Publisher publisher = readRow(cursor);
            publishers.add(publisher);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return publishers;
    }

    public Publisher save(Publisher publisher) {
        // Salva
        //this.open();
        Long result = getDb().insert(Publisher.TABLE_NAME, null, getValues(publisher));
        if (result == -1) {
            return null;
        }
        //this.close();
        publisher.setId(Integer.parseInt(result.toString()));
        return publisher;
    }


    public Publisher update(Publisher publisher) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Publisher.TABLE_NAME,
                getValues(publisher),
                KEY_ROWID + "=" + publisher.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        return publisher;
    }

    private ContentValues getValues(Publisher publisher) {
        ContentValues values = new ContentValues();
        try {
            values.put("name", publisher.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("last_name", publisher.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("gender", publisher.getGender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("birth", publisher.getBirth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("baptism", publisher.getBaptism());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("cell_phone", publisher.getCellPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("email", publisher.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("city", publisher.getCity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("neighborhood", publisher.getNeighborhood());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("address", publisher.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("number", publisher.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("gps", publisher.getGps());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("group_congregation", publisher.getGroupCongregation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("family_head", publisher.isFamilyHead());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("publisher", publisher.isPublisher());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("regular_pioneer", publisher.isRegularPioneer());
        } catch (Exception e) {
            values.put("regular_pioneer", false);
            e.printStackTrace();
        }
        try {
            values.put("special_pioneer", publisher.isSpecialPioneer());
        } catch (Exception e) {
            values.put("special_pioneer", false);
            e.printStackTrace();
        }
        try {
            values.put("missionary", publisher.isMissionary());
        } catch (Exception e) {
            values.put("missionary", false);
            e.printStackTrace();
        }
        try {
            values.put("deaf", publisher.isDeaf());
        } catch (Exception e) {
            values.put("deaf", false);
            e.printStackTrace();
        }
        try {
            values.put("elder", publisher.isElder());
        } catch (Exception e) {
            values.put("elder", false);
            e.printStackTrace();
        }
        try {
            values.put("sup_group", publisher.isSupGroup());
        } catch (Exception e) {
            values.put("sup_group", false);
            e.printStackTrace();
        }
        try {
            values.put("servant_ministerial", publisher.isServantMinisterial());
        } catch (Exception e) {
            values.put("servant_ministerial", false);
            e.printStackTrace();
        }
        try {
            values.put("group_help", publisher.isGroupHelp());
        } catch (Exception e) {
            values.put("group_help", false);
            e.printStackTrace();
        }
        try {
            values.put("anointed", publisher.isAnointed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("coordinator", publisher.isCoordinator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("secretary", publisher.isSecretary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("sup_service", publisher.isCoordinator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("user_name", publisher.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("password", publisher.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("notes", publisher.getNotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Contact Data 1
        try {
            values.put("contact_name1", publisher.getContactName1());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_note1", publisher.getContactNote1());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_is_jehovahs_witness1", publisher.isJehovahsWitness1());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_phone1", publisher.getContactPhone1());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_address1", publisher.getContactAddress1());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Contact Data 2
        try {
            values.put("contact_name2", publisher.getContactName2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_note2", publisher.getContactNote2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_is_jehovahs_witness2", publisher.isJehovahsWitness2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_phone2", publisher.getContactPhone2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("contact_address2", publisher.getContactAddress2());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }

    private Publisher readRow(Cursor cursor) {
        Publisher publisher = new Publisher();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            publisher.setId(cursor.getInt(cursor.getColumnIndex("id")));
            publisher.setName(
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            try {
                publisher.setLastName(
                        cursor.getString(cursor.getColumnIndex("last_name"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGender(
                        cursor.getString(cursor.getColumnIndex("gender"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setBirth(
                        cursor.getString(cursor.getColumnIndex("birth"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setBaptism(
                        cursor.getString(cursor.getColumnIndex("baptism"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setCellPhone(
                        cursor.getString(cursor.getColumnIndex("cell_phone"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setEmail(
                        cursor.getString(cursor.getColumnIndex("email"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setCity(
                        cursor.getString(cursor.getColumnIndex("city"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNeighborhood(
                        cursor.getString(cursor.getColumnIndex("neighborhood"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setAddress(
                        cursor.getString(cursor.getColumnIndex("address"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNumber(
                        cursor.getString(cursor.getColumnIndex("number"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGps(
                        cursor.getString(cursor.getColumnIndex("gps"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroupCongregation(
                        cursor.getString(cursor.getColumnIndex("group_congregation"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setFamilyHead(cursor.getInt(cursor.getColumnIndex("family_head")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPublisher(cursor.getInt(cursor.getColumnIndex("publisher")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setRegularPioneer(cursor.getInt(cursor.getColumnIndex("regular_pioneer")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSpecialPioneer(cursor.getInt(cursor.getColumnIndex("special_pioneer")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setMissionary(cursor.getInt(cursor.getColumnIndex("missionary")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setDeaf(cursor.getInt(cursor.getColumnIndex("deaf")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setElder(cursor.getInt(cursor.getColumnIndex("elder")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupGroup(cursor.getInt(cursor.getColumnIndex("sup_group")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupGroup(cursor.getInt(cursor.getColumnIndex("sup_group")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setServantMinisterial(cursor.getInt(cursor.getColumnIndex("servant_ministerial")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setServantMinisterial(cursor.getInt(cursor.getColumnIndex("servant_ministerial")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroupHelp(cursor.getInt(cursor.getColumnIndex("group_help")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setAnointed(cursor.getInt(cursor.getColumnIndex("anointed")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setCoordinator(cursor.getInt(cursor.getColumnIndex("coordinator")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSecretary(cursor.getInt(cursor.getColumnIndex("secretary")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupService(cursor.getInt(cursor.getColumnIndex("sup_service")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setUserName(
                        cursor.getString(cursor.getColumnIndex("user_name"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPassword(
                        cursor.getString(cursor.getColumnIndex("password"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setNotes(
                        cursor.getString(cursor.getColumnIndex("notes"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Contact Data 1
            try {
                publisher.setContactName1(
                        cursor.getString(cursor.getColumnIndex("contact_name1"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactNote1(
                        cursor.getString(cursor.getColumnIndex("contact_note1"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setJehovahsWitness1(
                        cursor.getInt(cursor.getColumnIndex("contact_is_jehovahs_witness1")) == 1
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactPhone1(
                        cursor.getString(cursor.getColumnIndex("contact_phone1"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactAddress1(
                        cursor.getString(cursor.getColumnIndex("contact_address1"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Contact Data 2
            try {
                publisher.setContactName2(
                        cursor.getString(cursor.getColumnIndex("contact_name2"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactNote2(
                        cursor.getString(cursor.getColumnIndex("contact_note2"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setJehovahsWitness2(
                        cursor.getInt(cursor.getColumnIndex("contact_is_jehovahs_witness2")) == 1
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactPhone2(
                        cursor.getString(cursor.getColumnIndex("contact_phone2"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setContactAddress2(
                        cursor.getString(cursor.getColumnIndex("contact_address2"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return publisher;
    }
}
