package com.example.gursimransingh.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       String tabName = uri.getLastPathSegment();
        return sqLiteDatabase.delete(tabName,selection,null);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

       String tabName = uri.getLastPathSegment();
        long id = sqLiteDatabase.insert(tabName,null,values);

        Uri tempUri = Uri.parse("dummyUri/"+id);
        return tempUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new DBHelper(getContext(),Util.DB_NAME,null,Util.DB_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

     String tabName = uri.getLastPathSegment();
       return sqLiteDatabase.query(tabName,projection,null,null,null,null,null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tabName = uri.getLastPathSegment();
        return sqLiteDatabase.update(tabName,values,selection,null);
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Util.CREATE_TAB_QUERY);
            //CREATE_TAB_QUERY2,3,4,//....for more tables
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//It runs when DBversion changes!!
        }
    }
}
