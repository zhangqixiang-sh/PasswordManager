package com.zqx.mypwd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zqx.mypwd.model.bean.AccountBean;
import com.zqx.mypwd.util.SqlEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */
public class AccountDao {
    public static final  String C_SERVER   = "c_server";
    public static final  String C_NAME     = "c_name";
    public static final  String C_PWD      = "c_pwd";
    public static final  String TABLE_NAME = "accounts";
    public static final  String C_ID       = "_id";
    private static final int    VERSION    = 1;

    public static void saveAccount(Context context, AccountBean bean) {
        DbOpener opener = new DbOpener(context.getApplicationContext());
        SQLiteDatabase db = opener.getWritableDatabase();
        String sql = "insert into " + TABLE_NAME + " (" +
                C_SERVER + ", " +
                C_NAME + ", " +
                C_PWD + ") values (?,?,?)";



        String[] args = new String[]{bean.server, bean.name, bean.pwd};
        db.execSQL(sql, args);
        opener.close();
        db.close();
    }

    public static int deleteAccount(Context context, AccountBean bean) {
        String[] param = new String[]{bean.id + ""};
        DbOpener opener = new DbOpener(context.getApplicationContext());
        SQLiteDatabase db = opener.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "_id =?", param);
        db.close();
        opener.close();
        return i;
    }

    public static void updateAccount(Context context, AccountBean bean) {
        DbOpener opener = new DbOpener(context);
        SQLiteDatabase db = opener.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_SERVER, bean.server);
        values.put(C_NAME, bean.name);
        values.put(C_PWD, bean.pwd);
        db.update(TABLE_NAME, values, "_id=?", new String[]{bean.id + ""});
        db.close();
        opener.close();
    }


    public static List<AccountBean> getAccountsLike(Context context, String serverName) {
        DbOpener opener = new DbOpener(context.getApplicationContext());
        SQLiteDatabase db = opener.getWritableDatabase();

        String sql = SqlEncoder.begin()
                .select("*")
                .from(TABLE_NAME)
                .where(C_SERVER)
                .like()
                .toString();

        String[] args = new String[]{"%" + serverName + "%"};
        List<AccountBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String server = cursor.getString(1);
                String name = cursor.getString(2);
                String pwd = cursor.getString(3);
                list.add(new AccountBean(id, server, name, pwd));
            }
            cursor.close();
        }
        opener.close();
        db.close();
        return list;
    }

    public static List<AccountBean> getAllAccounts(Context context) {
        DbOpener opener = new DbOpener(context.getApplicationContext());
        SQLiteDatabase db = opener.getWritableDatabase();
        String sql = "select * from " + TABLE_NAME;
        List<AccountBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AccountBean bean = new AccountBean();
                bean.id = cursor.getInt(cursor.getColumnIndex(C_ID));
                bean.server = cursor.getString(cursor.getColumnIndex(C_SERVER));
                bean.name = cursor.getString(cursor.getColumnIndex(C_NAME));
                bean.pwd = cursor.getString(cursor.getColumnIndex(C_PWD));
                list.add(bean);
            }
            cursor.close();
        }
        opener.close();
        db.close();
        return list;
    }

    private static class DbOpener extends SQLiteOpenHelper {


        DbOpener(Context context) {
            super(context, TABLE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table " + TABLE_NAME +
                    " (_id integer primary key, " +
                    C_SERVER + " varchar(30), " +
                    C_NAME + " varchar(30)," +
                    C_PWD + " varchar(30)) ";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
