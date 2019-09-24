package com.example.kintairecorder.service;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kintairecorder.R;
import com.example.kintairecorder.command.KintaiRecorderRegistCommand;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * KintaiRecorderのMainActivity。
 * 画面表示や処理の制御を行う。
 */
public class KintaiRecorderService extends AppCompatActivity {

    /**
     * onCreateメソッドの実装。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ボタン等
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 記録ボタン制御
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Command実行
                KintaiRecorderRegistCommand command = new KintaiRecorderRegistCommand(getApplicationContext());
                command.execute();

                // insert結果取得
                int result = command.getResult();
                // Snackbar表示
                showResult(view, result);

            }
        });
    }

    /**
     * DB登録結果を元にSnackbarの制御を行う。
     * @param view
     * @param result
     */
    private void showResult(View view, int result) {
        if (result == 0) {
            // 挿入成功
            Snackbar.make(view, (R.string.recorded_1), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        } else if (result == 1) {
            // 一意制約違反
            Snackbar.make(view, (R.string.recorded_2), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
