package com.fabio.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fabio.congregationreport.db.GenericDAO;
import com.fabio.congregationreport.models.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublisherDAO extends GenericDAO<Publisher> {
    public PublisherDAO(Context context) {
        super(context, Publisher.class);
    }

    public Publisher getPublisher(String email) {
        // Publicadores
        Publisher publisher = new Publisher();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "email";
        filtros.put(tipoColuna, email);
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

    public List<Publisher> getPublishers() {
        List<Publisher> groups = new ArrayList<>();

        String[] orderes = new String[1];
        orderes[1] = "name ASC";

        Cursor cursor = findAll(orderes);

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Publisher publisher = readRow(cursor);
            groups.add(publisher);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return groups;
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
        //this.close();
        publisher.setId(Integer.parseInt(result.toString()));
        return publisher;
    }

    public ContentValues getValues(Publisher publisher) {
        ContentValues values = new ContentValues();
        try {
            values.put("name", publisher.getName());
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
            values.put("phones", publisher.getPhones());
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
            values.put("notes", publisher.getNotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("gender", publisher.getGender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("group", publisher.getGroup());
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
                publisher.setPhones(
                        cursor.getString(cursor.getColumnIndex("phones"))
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
                publisher.setNotes(
                        cursor.getString(cursor.getColumnIndex("notes"))
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
                publisher.setGroup(
                        cursor.getString(cursor.getColumnIndex("group"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setFamilyHead(cursor.getInt(cursor.getColumnIndex("family_head")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setPublisher(cursor.getInt(cursor.getColumnIndex("publisher")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setRegularPioneer(cursor.getInt(cursor.getColumnIndex("regular_pioneer")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSpecialPioneer(cursor.getInt(cursor.getColumnIndex("special_pioneer")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setMissionary(cursor.getInt(cursor.getColumnIndex("missionary")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setDeaf(cursor.getInt(cursor.getColumnIndex("deaf")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setElder(cursor.getInt(cursor.getColumnIndex("elder")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupGroup(cursor.getInt(cursor.getColumnIndex("sup_group")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setSupGroup(cursor.getInt(cursor.getColumnIndex("sup_group")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setServantMinisterial(cursor.getInt(cursor.getColumnIndex("servant_ministerial")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setServantMinisterial(cursor.getInt(cursor.getColumnIndex("servant_ministerial")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setGroupHelp(cursor.getInt(cursor.getColumnIndex("group_help")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                publisher.setAnointed(cursor.getInt(cursor.getColumnIndex("anointed")) > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return publisher;
    }
}
