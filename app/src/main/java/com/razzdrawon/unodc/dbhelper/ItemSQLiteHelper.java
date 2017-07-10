package com.razzdrawon.unodc.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.razzdrawon.unodc.model.Item;

import java.util.List;

/**
 * Created by mapadi3 on 09/07/17.
 */

public class ItemSQLiteHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "itemsManager";

    // Contacts table name
    private static final String TABLE_ITEMS = "items";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_JSONITEM = "json_item";
    private static final String KEY_FLAG_SYNC = "flag_sync";

    public ItemSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_JSONITEM + " TEXT,"
                + KEY_FLAG_SYNC + " INTEGER" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        // Create tables again
        onCreate(db);
    }


//    // Adding new contact
    public void addItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JSONITEM, item); // Contact Name
        values.put(KEY_FLAG_SYNC, 0); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }
//
//    // Getting single contact
//    public Item getContact(int id) {
//
//    }
//
//    // Getting All Contacts
//    public List<Item> getAllItems() {
//
//    }
//
//    // Getting contacts Count
//    public int getItemsCount() {
//
//    }
//
//    // Updating single contact
//    public int updateItem(Item item) {
//
//    }
//
//    // Deleting single contact
//    public void deleteItem(Item item) {
//
//    }

}
