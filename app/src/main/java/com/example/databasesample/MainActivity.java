package com.example.databasesample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 選択されたカクテルの主キー
    private int coctailId = -1;

    // 選択されたカクテル名
    private String coctailName = "";

    // DBヘルパー
    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // リストビューにリスナ設定
        ListView view = findViewById(R.id.lvCocktail);
        view.setOnItemClickListener(new ListItemClickListener());

        helper = new DataBaseHelper(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        // ヘルパーオブジェクトの解放
        helper.close();
        super.onDestroy();
    }

    /**
     * 保存ボタン押下時処理
     * @param view
     */
    public void onSaveButtonClick(View view) {

        // 感想欄取得
        EditText editText = findViewById(R.id.etNote);
        String note = String.valueOf(editText.getText());

        // ヘルパーオブジェクトからDB接続オブジェクトを取得
        SQLiteDatabase db = helper.getWritableDatabase();

        // リストで選択されたカクテルのメモデータを削除し、新しい値で登録を実行
        String deleteSQL = "DELETE FROM cocktailmemos WHERE _id = ?";

        // プリペアードステートメント取得
        SQLiteStatement statement = db.compileStatement(deleteSQL);

        // 変数のバインド
        statement.bindLong(1, coctailId);

        // SQL実行
        statement.executeUpdateDelete();


        // インサート用SQL作成
        String insertSQL = "INSERT INTO cocktailmemos (_id, name, note) VALUES(?, ?, ?)";
        SQLiteStatement insertStatement = db.compileStatement(insertSQL);
        insertStatement.bindLong(1, coctailId);
        insertStatement.bindString(2, coctailName);
        insertStatement.bindString(3, note);
        insertStatement.executeInsert();

        // 感想欄の入力値を消去
        editText.setText("");

        // カクテル名を「未選択」に変更
        TextView textView = findViewById(R.id.tvCoctailName);
        textView.setText(getString(R.string.tv_name));

        // 「保存」ボタンを押下不可に変更
        Button button = findViewById(R.id.btnSave);
        button.setEnabled(false);
    }

    /**
     * イベントリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // タップされた行番号を取得
            coctailId = position;

            // タップされた行データを取得
            coctailName = (String) parent.getItemAtPosition(coctailId);

            // カクテル名を表示するTextViewにカクテル名を表示
            TextView textView = findViewById(R.id.tvCoctailName);
            textView.setText(coctailName);

            // 保存ボタンをタップ可能に設定
            Button button = findViewById(R.id.btnSave);
            button.setEnabled(true);

            SQLiteDatabase db = helper.getReadableDatabase();

            String sql = "SELECT * FROM cocktailmemos WHERE _id = " + coctailId;

            // SELECT文の実行
            Cursor cursor = db.rawQuery(sql, null);

            String note = "";

            // SQl実行の戻り値（Cursor）をループしてDB内のデータ取得
            while (cursor.moveToNext()) {
                // 欲しいカラム（note）のインデックス値を取得
                int idxNote = cursor.getColumnIndex("note");
                // カラムのインデックス値を指定して欲しいデータを取得
                note = cursor.getString(idxNote);
            }

            EditText editText = findViewById(R.id.etNote);
            editText.setText(note);
        }
    }
}