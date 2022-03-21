package com.iqbal.karim.ahmed.salik.project.databaseClasses;

import android.content.ContentValues;
import android.database.Cursor;

import com.iqbal.karim.ahmed.salik.project.Commons;

public class UsersTable {
    public static final String TABLE_NAME = "users";

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String EMAIL_FIELD = "email";
    public static final String PASSWORD_FIELD = "password";
    public static final String IMAGE_FIELD = "imageId";

    public static final String CREATE_TABLE = "CREATE TABLE \"users\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"password\"\tTEXT NOT NULL,\n" +
            "\t\"email\"\tTEXT NOT NULL UNIQUE,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ");";
    public static final String DROP_TABLE = "DROP table if exists" + TABLE_NAME;

    public static boolean insertUser(DatabaseHelper db, String name, String password, String email, int imageId){
        ContentValues cv = new ContentValues();

        cv.put(NAME_FIELD, name);
        cv.put(EMAIL_FIELD, email);
        cv.put(PASSWORD_FIELD, password);
        cv.put(IMAGE_FIELD, imageId);

        boolean res = db.insertUser(TABLE_NAME, cv);
        return res;
    }

    public static User authUser(DatabaseHelper db, String email, String password){
//        String where = EMAIL_FIELD + " = " + email + "and " + PASSWORD_FIELD + " = " + password ;
        String where = EMAIL_FIELD + " = \"" + email + "\" and " + PASSWORD_FIELD + " = \"" + password + "\"";
        Cursor cursor = db.authUser(TABLE_NAME, null, where);
        User u = null;

        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            int id = cursor.getInt(1);
            String pass = cursor.getString(2);
            String currEmail = cursor.getString(3);
            int imgId = cursor.getInt(4);
            u = new User(id, name, password, email, imgId);
        }
        return u;
    }

    public static boolean updateUser(DatabaseHelper db, String name, String email, int img){
        String where = ID_FIELD + " = \"" + Commons.currentUser.getId() + "\"";

        ContentValues cv = new ContentValues();
        cv.put(NAME_FIELD, name);
        cv.put(EMAIL_FIELD, email);
        cv.put(IMAGE_FIELD, img);

        boolean res = db.update(TABLE_NAME, cv, where);
        return res;
    }

    public static boolean updateUserPass(DatabaseHelper db, String pass){
        String where = ID_FIELD + " = \"" + Commons.currentUser.getId() + "\"";

        ContentValues cv = new ContentValues();
        cv.put(PASSWORD_FIELD, pass);

        boolean res = db.update(TABLE_NAME, cv, where);
        return res;
    }
}
