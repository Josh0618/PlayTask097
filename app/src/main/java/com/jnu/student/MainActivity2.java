package com.jnu.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Book> books;
    ActivityResultLauncher<Intent> launcher;

    public static final int MENU_ADD = 1;
    public static final int MENU_EDIT = 2;
    public static final int MENU_DELETE = 3;


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

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // 处理返回的数据
                        if (data != null) {
                            // 从返回的 Intent 中提取数据，这里假设您返回的数据为 Book 对象
                            Book newBook = (Book) data.getSerializableExtra("newBook");

                            // 将新的 Book 对象添加到 RecyclerView 的数据源中
                            // 这里需要您实现相应的添加逻辑，比如更新适配器数据并刷新 RecyclerView
                            // adapter.addBook(newBook);
                            // adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    // 模拟书籍列表数据
    public List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        return bookList;
    }

    // 在主 Activity 中的某个方法中，比如在创建ContextMenu时，启动另一个 Activity
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.recycle_view_books) {
            menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "Add");
            menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, "Edit");
            menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ADD:
                // 添加逻辑，启动另一个 Activity
                Intent intent = new Intent(this, BookDetailsActivity.class);
                launcher.launch(intent);
                return true;
            case MENU_EDIT:
                // 编辑逻辑
                return true;
            case MENU_DELETE:
                // 删除逻辑
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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



