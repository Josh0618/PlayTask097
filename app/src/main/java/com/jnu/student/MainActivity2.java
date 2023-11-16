package com.jnu.student;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Book> books;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取书籍列表
        books = getListBooks();

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recycle_view_books);
        RecyclerViewBookAdapter adapter = new RecyclerViewBookAdapter(books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // 模拟书籍列表数据
    public List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        return bookList;
    }
}

class Book {
    private String title;
    private int coverResourceId;

    public Book(String title, int coverResourceId) {
        this.title = title;
        this.coverResourceId = coverResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }
}

class RecyclerViewBookAdapter extends RecyclerView.Adapter<RecyclerViewBookAdapter.BookViewHolder> {

    private List<Book> bookList;

    public RecyclerViewBookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookCover.setImageResource(book.getCoverResourceId());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.image_view_book_cover);
            bookTitle = itemView.findViewById(R.id.text_view_book_title);
        }
    }
}



