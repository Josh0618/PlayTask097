package com.jnu.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView mainRecyclerView = findViewById(R.id.recycle_view_books);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

        BookAdapter bookAdapter = new BookAdapter(books);
        mainRecyclerView.setAdapter(bookAdapter);

    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private List<Book> bookList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewName;
            private final ImageView imageViewItem;

            public ViewHolder(View bookView) {
                super(bookView);

                textViewName = bookView.findViewById(R.id.text_view_book_title);
                imageViewItem = bookView.findViewById(R.id.image_view_book_cover);
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public ImageView getImageViewItem() {
                return imageViewItem;
            }
        }
        public BookAdapter(List<Book> bookList) {
            this.bookList = bookList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewName().setText(bookList.get(position).getTitle());
            viewHolder.getImageViewItem().setImageResource(bookList.get(position).getCoverResourceId());
        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

    }
}

class Book {
    public int getCoverResourceId() {
        return imageResourceId;
    }

    private int imageResourceId;

    public String getTitle() {
        return name;
    }

    private String name;

    public Book(String name_, int imageResourceId_) {
        this.name=name_;
        this.imageResourceId =imageResourceId_;
    }
}









