package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局

        // 通过ID找到TextView
        TextView helloTextView = findViewById(R.id.text_view_hello_world);

        // 通过资源名称获取字符串资源并设置给TextView
        String greeting = getResources().getString(R.string.hello_andriod);
        helloTextView.setText(greeting);
    }
}