package com.example.databasesample;

import androidx.appcompat.app.AppCompatActivity;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // リストビューにリスナ設定
        ListView view = findViewById(R.id.lvCocktail);
        view.setOnItemClickListener(new ListItemClickListener());
    }


    /**
     * 保存ボタン押下時処理
     * @param view
     */
    public void onSaveButtonClick(View view) {

        // 感想欄取得
        EditText editText = findViewById(R.id.etNote);

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
        }
    }
}