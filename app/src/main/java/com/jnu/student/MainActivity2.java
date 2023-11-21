package com.jnu.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<Book> books = new ArrayList<>();
    BookAdapter bookAdapter;
    ActivityResultLauncher<Intent> addItemLauncher;
    ActivityResultLauncher<Intent> updateItemLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recycle_view_books);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

        bookAdapter = new BookAdapter(books);
        mainRecyclerView.setAdapter(bookAdapter);

        registerForContextMenu(mainRecyclerView);

        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        String name = data.getStringExtra("name"); // 获取返回的数据

                        books.add(new Book(name, R.drawable.default_cover));
                        bookAdapter.notifyItemInserted(books.size());
                        new DataBank().SaveBooks(MainActivity2.this, books);


                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );

        updateItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        int position = data.getIntExtra("position",0);
                        String name = data.getStringExtra("name"); // 获取返回的数据

                        Book book = books.get(position);
                        book.setTitle(name);
                        bookAdapter.notifyItemChanged(position);
                        new DataBank().SaveBooks(MainActivity2.this, books);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 添加新建菜单项
        menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "新建");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理新建菜单项的点击事件
        if (item.getItemId() == Menu.FIRST + 3) {
            Intent newBookIntent = new Intent(MainActivity2.this, BookDetailsActivity.class);
            addItemLauncher.launch(newBookIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = item.getOrder();

        switch (id) {
            case 0:
                Intent intent = new Intent(MainActivity2.this, BookDetailsActivity.class);
                addItemLauncher.launch(intent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    books.remove(position);
                    bookAdapter.notifyItemRemoved(position);
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(MainActivity2.this, "Deletion canceled", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
                break;
            case 2:
                Intent intentUpdate = new Intent(MainActivity2.this, BookDetailsActivity.class);
                Book book = books.get(position);
                intentUpdate.putExtra("name", book.getTitle());
                intentUpdate.putExtra("position", position);
                updateItemLauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }

        if (id == 3) {
            Intent newBookIntent = new Intent(MainActivity2.this, BookDetailsActivity.class);
            addItemLauncher.launch(newBookIntent);
            return true;
        }
        return true;
    }

    public static class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private List<Book> bookList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewName;
            private final ImageView imageViewItem;

            public ViewHolder(View bookView) {
                super(bookView);

                textViewName = bookView.findViewById(R.id.text_view_book_title);
                imageViewItem = bookView.findViewById(R.id.image_view_book_cover);

                bookView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public ImageView getImageViewItem() {
                return imageViewItem;
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }
        }
        public BookAdapter(List<Book> bookList) {
            this.bookList = bookList;
        }

        @NonNull
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

class Book implements Serializable {
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

    public void setTitle(String name) {
        this.name = name;
    }
}









