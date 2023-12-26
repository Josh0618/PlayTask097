package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewRewardActivity extends AppCompatActivity {
    private EditText editTextItemName;
    private EditText editTextItemPoint;
    private Spinner spinnerType;
    private final static String[] typeArray = {"单次","不限"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reward);

        // 初始化新任务信息的EditText
        editTextItemName = findViewById(R.id.edit_text_reward_title);
        editTextItemPoint = findViewById(R.id.edittext_point);
        // 下拉栏
        spinnerType = findViewById(R.id.spinner_type);

        // 设置添加按钮的点击事件
        Button addButton = findViewById(R.id.button_item_details_ok);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个包含新任务信息的Intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", editTextItemName.getText().toString());
                resultIntent.putExtra("point", Integer.parseInt(editTextItemPoint.getText().toString()));
                resultIntent.putExtra("type", spinnerType.getSelectedItemPosition());

                // 设置结果码为RESULT_OK，表示成功添加
                setResult(Activity.RESULT_OK, resultIntent);

                // 结束当前Activity，返回到上一个Activity
                finish();
            }
        });

        // 标签页左边的返回按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 声明数组适配器
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this,R.layout.item_select,typeArray);
        spinnerType.setAdapter(startAdapter);
        // 设置默认显示第一项
        spinnerType.setSelection(0);
    }

}
