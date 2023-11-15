package com.jnu.student;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity {

    public List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();

        // 添加书籍到 bookList
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        // 在这里添加更多的书籍...

        return bookList;
    }
}

