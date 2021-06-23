package com.example.databasesample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // データベースファイル名の定数フィールド
    private static final String DATABASE_NAME = "cocktailmemo.db";

    // バージョン情報の定数フィールド
    private static final int DATABASE_VERSION = 1;

    // コンストラクタ
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        onCreateはコンストラクタで指定したDBが存在しない場合のみ実行される。
        そのためここにCREATE文を記述する。
        */

        // テーブル作成用SQL作成
        StringBuffer sb = new StringBuffer();
        sb.append("Create table cocktailmemos (");
        sb.append(" _id INTEGER PRIMARY KEY,");     // Androidは「_id」を自動的に主キーと認識する仕様
        sb.append(" name TEXT,");
        sb.append(" note TEXT");
        sb.append(")");
        String sql = sb.toString();

        // SQL実行
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        * バージョンの変更があった場合に
        * Alter文等を実行する。
        */
    }
}

