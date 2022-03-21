package com.iqbal.karim.ahmed.salik.project.databaseClasses;

import android.content.ContentValues;
import android.database.Cursor;

import com.iqbal.karim.ahmed.salik.project.Commons;

import java.util.ArrayList;
import java.util.List;

public class ItemsTable {
    public static final String TABLE_NAME = "items";

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String REQUESTERID_FIELD = "requesterId";
    public static final String REQUESTERNAME_FIELD = "requesterName";
    public static final String CATEGORY_FIELD = "category";
    public static final String SHARED_FIELD = "shared";
    public static final String GIVENBY_FIELD = "givenBy";

    public static final String CREATE_TABLE_ITEMS = "CREATE TABLE \"items\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT,\n" +
            "\t\"requesterId\"\tINTEGER,\n" +
            "\t\"requesterName\"\tTEXT,\n" +
            "\t\"category\"\tTEXT,\n" +
            "\t\"shared\"\tINTEGER,\n" +
            "\t\"givenBy\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ");";
    public static final String DROP_TABLE_ITEMS = "DROP table if exists" + TABLE_NAME;

    public static boolean insertItem(DatabaseHelperItems db, String name,  int requesterId, String requesterName, String cat){
        ContentValues cv = new ContentValues();

        cv.put(NAME_FIELD, name);
        cv.put(REQUESTERID_FIELD, requesterId);
        cv.put(REQUESTERNAME_FIELD, requesterName);
        cv.put(CATEGORY_FIELD, cat);
        cv.put(SHARED_FIELD, 0);

        boolean res = db.insert(TABLE_NAME, cv);
        return res;
    }

    public static List<Item> getAllItems(DatabaseHelperItems db){

        Cursor cursor = db.getAllRecords(TABLE_NAME, null);
        List<Item> data=new ArrayList<>();
        Item it = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int requesterId = cursor.getInt(2);
            String requesterName = cursor.getString(3);
            String cat = cursor.getString(4);

            int shared = cursor.getInt(5);
            String givenBy = cursor.getString(6);

            it = new Item(id, name, requesterId, requesterName, cat, shared, givenBy);
            data.add(it);
        }
        return data;
    }

    public static List<Item> getRequestItems(DatabaseHelperItems db){

        String where = REQUESTERID_FIELD + " = \"" + Commons.currentUser.getId() + "\"";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        List<Item> data=new ArrayList<>();
        Item it = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int requesterId = cursor.getInt(2);
            String requesterName = cursor.getString(3);
            String cat = cursor.getString(4);

            int shared = cursor.getInt(5);
            String givenBy = cursor.getString(6);

            it = new Item(id, name, requesterId, requesterName, cat, shared, givenBy);
            data.add(it);
        }
        return data;
    }

    public static List<Item> getShareItems(DatabaseHelperItems db){

        String where = REQUESTERID_FIELD + " != \"" + Commons.currentUser.getId() + "\"" +
                " and " + SHARED_FIELD + " = 0";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        List<Item> data=new ArrayList<>();
        Item it = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int requesterId = cursor.getInt(2);
            String requesterName = cursor.getString(3);
            String cat = cursor.getString(4);

            int shared = cursor.getInt(5);
            String givenBy = cursor.getString(6);

            it = new Item(id, name, requesterId, requesterName, cat, shared, givenBy);
            data.add(it);
        }
        return data;
    }

    public static void deleteAll(DatabaseHelperItems db){
        db.deleteAll(TABLE_NAME);
    }

    public static boolean deleteItem(DatabaseHelperItems db, Item item){
        String where = ID_FIELD + " = " + item.getId();
        boolean res = db.delete(TABLE_NAME, where);
        return res;
    }

    public static boolean markShared(DatabaseHelperItems db, Item item){
        String where = ID_FIELD + " = " + item.getId();

        ContentValues cv = new ContentValues();
        cv.put(SHARED_FIELD, 1);
        cv.put(GIVENBY_FIELD, Commons.currentUser.getName());

        boolean res = db.update(TABLE_NAME, cv, where);
        return res;
    }
}
