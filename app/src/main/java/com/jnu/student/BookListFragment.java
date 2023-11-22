package com.jnu.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BookListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Book> books = new ArrayList<>();
    private BookAdapter bookAdapter;
    ActivityResultLauncher<Intent> addItemLauncher;
    ActivityResultLauncher<Intent> updateItemLauncher;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recycle_view_books);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        books = new DataBank().LoadBooks(requireActivity());
        if(0 == books.size()) {
            books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
            books.add(new Book("创新工程实践", R.drawable.book_no_name));
            books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        }

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

                        new DataBank().SaveBooks(requireActivity(), books);

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

                        new DataBank().SaveBooks(requireActivity(), books);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
        return rootView;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = item.getOrder();

        switch (id) {
            case 0:
                Intent intent = new Intent(requireActivity(), BookDetailsActivity.class);
                addItemLauncher.launch(intent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        books.remove(position);
                        bookAdapter.notifyItemRemoved(position);

                        new DataBank().SaveBooks(requireActivity(), books);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
            case 2:
                Intent intentUpdate = new Intent(requireActivity(), BookDetailsActivity.class);
                Book book = books.get(position);
                intentUpdate.putExtra("name", book.getTitle());
                intentUpdate.putExtra("position", position);
                updateItemLauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private ArrayList<Book> bookArrayList;

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
        public BookAdapter(ArrayList<Book> books) {
            this.bookArrayList = books;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewName().setText(books.get(position).getTitle());
            viewHolder.getImageViewItem().setImageResource(books.get(position).getCoverResourceId());
        }

        @Override
        public int getItemCount() {
            return bookArrayList.size();
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