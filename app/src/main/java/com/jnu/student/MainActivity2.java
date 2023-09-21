package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局

        // 通过ID找到TextView
        TextView helloTextView = findViewById(R.id.text_view_hello_world);

        // 通过资源名称获取字符串资源并设置给TextView
        String greeting = getResources().getString(R.string.hello_andriod);
        helloTextView.setText(greeting);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }
    public void swapText(View view) {
        String text1 = textView.getText().toString();
        String text2 = textView2.getText().toString();

        textView.setText(text2);
        textView2.setText(text1);

        // 显示Toast
        Toast.makeText(this, "交换成功", Toast.LENGTH_SHORT).show();

        // 显示AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("交换成功")
                .setMessage("文本已成功交换")
                .setPositiveButton("确定", null)
                .show();
    }
}