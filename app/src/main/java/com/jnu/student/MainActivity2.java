package com.jnu.student;

import static com.jnu.student.R.id.recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 设置布局管理器，例如 LinearLayoutManager

        BookListMainActivity bookListMainActivity = new BookListMainActivity();
        List<Book> bookList = bookListMainActivity.getListBooks();

        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }
    /*public void swapText(View view) {
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
    }*/
}
