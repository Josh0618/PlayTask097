package com.jnu.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewMissionActivity extends AppCompatActivity {

    private EditText editTextItemName;
    private EditText editTextItemPoint;
    private EditText editTextItemTimes;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mission);

        editTextItemName= findViewById(R.id.edit_text_mission_title);
        editTextItemPoint= findViewById(R.id.edittext_point);
        editTextItemTimes= findViewById(R.id.edittext_times);

        Button buttonOk = findViewById(R.id.button_item_details_ok);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个包含新任务信息的Intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", editTextItemName.getText().toString());
                resultIntent.putExtra("point", Integer.parseInt(editTextItemPoint.getText().toString()));
                if (!(editTextItemTimes.getText().toString().isEmpty())){
                    resultIntent.putExtra("times", Integer.parseInt(editTextItemTimes.getText().toString()));
                }

                // 设置结果码为RESULT_OK，表示成功添加
                setResult(Activity.RESULT_OK, resultIntent);

                // 结束当前Activity，返回到上一个Activity
                finish();

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
