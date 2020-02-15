package com.example.kintairecorder.service;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kintairecorder.R;
import com.example.kintairecorder.command.KintaiRecorderInsertCommand;
import com.example.kintairecorder.command.KintaiRecorderSelectCommand;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * KintaiRecorderのMainActivity。
 * 画面表示や処理の制御を行う。
 */
public class KintaiRecorderService extends AppCompatActivity {

    // 処理状態コード
    private int stateCode;

    // 0(初期表示未済)で初期化
    {
        stateCode = 0;
    }

    /**
     * onCreateメソッドの実装。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Context
        final Context context = getApplicationContext();

        // ボタン等
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初回起動の場合、DBデータ取得を行って表示する
        if (stateCode == 0) {
            // メイン画面更新処理
            KintaiRecorderSelectCommand selectCommand = new KintaiRecorderSelectCommand(context, 0);
            selectCommand.execute();

            // 画面更新用文字列(DB取得結果)の取得
            String resultStr = selectCommand.getReturnStr();

            // 文字列の表示
            TextView textView = findViewById(R.id.record_data);
            textView.setText(resultStr);

            // 初期表示済み(登録未済)に更新
            stateCode = 1;
        }

        // 記録ボタン制御
        FloatingActionButton recordButton = findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 本日登録済み状態なら、DB取得せずSnackbarを表示して終了
                if (stateCode == 2) {
                    showRegistResult(view, stateCode);
                    return;
                }

                // InsertCommand実行
                KintaiRecorderInsertCommand insertCommand = new KintaiRecorderInsertCommand(context);
                insertCommand.execute();

                // メイン画面更新処理
                KintaiRecorderSelectCommand selectCommand = new KintaiRecorderSelectCommand(context, 0);
                selectCommand.execute();

                // 画面更新用文字列(DB取得結果)の取得
                String resultStr = selectCommand.getReturnStr();

                // 文字列の表示
                TextView textView = findViewById(R.id.record_data);
                textView.setText(resultStr);

                // insert結果取得
                int result = insertCommand.getResult();

                // Snackbar表示
                showRegistResult(view, result);

            }
        });
    }

    /**
     * DB登録結果を元にSnackbarの制御を行う。
     * @param view
     * @param result
     */
    private void showRegistResult(View view, int result) {
        if (result == 1) {
            // 挿入成功
            Snackbar.make(view, (R.string.recorded_1), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        } else if (result == 2) {
            // 一意制約違反
            Snackbar.make(view, (R.string.recorded_2), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            // 処理状態コードを本日登録済みに更新
            stateCode = 2;
        } else {
            // その他エラー
            Snackbar.make(view, (R.string.record_failed), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
