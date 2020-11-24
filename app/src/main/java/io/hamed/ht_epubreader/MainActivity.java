package io.hamed.ht_epubreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.hamed.ht_epubreader.adapter.BookAdapter;
import io.hamed.ht_epubreader.adapter.SubBookAdapter;
import io.hamed.ht_epubreader.bottomsheet.ToolsBottomSheet;
import io.hamed.ht_epubreader.view.MyRecycler;
import io.hamed.htepubreadr.component.EpubReaderComponent;
import io.hamed.htepubreadr.entity.BookEntity;
import io.hamed.htepubreadr.entity.FontEntity;
import io.hamed.htepubreadr.entity.SubBookEntity;
import io.hamed.ht_epubreader.bottomsheet.OnChangeFontFamily;
import io.hamed.ht_epubreader.bottomsheet.OnChangeFontSize;
import io.hamed.htepubreadr.ui.view.OnHrefClickListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "@MY_APP";
    private MyRecycler rvBook;
    private DrawerLayout drawer;
    private View drawerLayout;
    private Toolbar toolbar;
    private RecyclerView rvSubList;
    private TextView tvBookName;
    private TextView tvBookAuthor;
    private ImageView ivBook;

    private List<FontEntity> listFont = new ArrayList<>();
    private EpubReaderComponent epubReader;
    private BookAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));

        downloadFile("https://hamedtaherpour.github.io/sample-assets/epub/book-8.epub");

        listFont.add(new FontEntity("https://hamedtaherpour.github.io/sample-assets/font/Acme.css", "Acme"));
        listFont.add(new FontEntity("https://hamedtaherpour.github.io/sample-assets/font/IndieFlower.css", "IndieFlower"));
        listFont.add(new FontEntity("https://hamedtaherpour.github.io/sample-assets/font/SansitaSwashed.css", "SansitaSwashed"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                toggleDrawer();
                break;
            case R.id.btn_setting:
                openToolsMenu();
                break;
        }
    }

    private void bindView() {
        rvBook = findViewById(R.id.rv_book);
        drawer = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.l_drawer);
        rvSubList = findViewById(R.id.rv);
        toolbar = findViewById(R.id.tool_bar);
        ivBook = findViewById(R.id.iv_book);
        tvBookName = findViewById(R.id.tv_book_name);
        tvBookAuthor = findViewById(R.id.tv_book_author);

        findViewById(R.id.btn_menu).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
    }

    private void onBookReady(String filePath) {
        try {
            epubReader = new EpubReaderComponent(filePath);
            BookEntity bookEntity = epubReader.make(this);
            setUpBookAdapter(bookEntity.getPagePathList());
            setUpBookInfo(bookEntity.getName(), bookEntity.getAuthor(), bookEntity.getCoverImage());
            setUpBookSubList(bookEntity.getSubBookHref());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openToolsMenu() {
        ToolsBottomSheet sheet = new ToolsBottomSheet();
        sheet.setFontSize(adapter.getFontSize());
        sheet.setAllFontFamily(listFont);
        sheet.setOnChangeFontFamily(new OnChangeFontFamily() {
            @Override
            public void onChange(int position) {
                adapter.setFontEntity(listFont.get(position));
                adapter.notifyDataSetChanged();
            }
        });
        sheet.setOnChangeFontSize(new OnChangeFontSize() {
            @Override
            public void onChangeSize(int size) {
                adapter.setFontSize(size);
                adapter.notifyDataSetChanged();
            }
        });
        sheet.show(getSupportFragmentManager(), sheet.getTag());
    }

    private void setUpBookAdapter(List<String> list) {
        adapter = new BookAdapter(list, epubReader.getAbsolutePath());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvBook.setLayoutManager(layoutManager);
        rvBook.setAdapter(adapter);
        adapter.setFontSize(30);
        adapter.setOnHrefClickListener(new OnHrefClickListener() {
            @Override
            public void onClick(String href) {
                gotoPageByHref(href);
            }
        });
        new LinearSnapHelper().attachToRecyclerView(rvBook);
    }

    private void setUpBookInfo(String name, String author, String filePathImgCover) {
        tvBookName.setText(name);
        tvBookAuthor.setText(author);
        if (filePathImgCover != null)
            ivBook.setImageBitmap(fileToBitmap(new File(filePathImgCover)));
    }

    private void setUpBookSubList(List<SubBookEntity> list) {
        SubBookAdapter adapter = new SubBookAdapter();
        adapter.setOnItemClickListener(new SubBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SubBookEntity entity, int position) {
                gotoPageByHref(entity.getHref());
                drawer.closeDrawer(drawerLayout);
            }
        });
        rvSubList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvSubList.setAdapter(adapter);
        adapter.submitList(list);
    }

    public void gotoPageByHref(String href) {
        int position = epubReader.getPagePositionByHref(href);
        if (position != EpubReaderComponent.PAGE_NOT_FOUND)
            rvBook.scrollToPosition(position);
    }

    private void downloadFile(String url) {
        String fileName = StringUtils.getName(url);
        Ion.with(getApplicationContext())
                .load(url)
                .write(new File(getFileDirectoryDownloads(), fileName))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        if (e == null)
                            onBookReady(file.getPath());
                        else {
                            Toast.makeText(getApplicationContext(), "download failed", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "download failed --> " + e.toString());
                        }
                    }
                });
    }

    private void toggleDrawer() {
        if (drawer.isDrawerOpen(drawerLayout))
            drawer.closeDrawer(drawerLayout);
        else {
            drawer.openDrawer(drawerLayout);
        }
    }

    private File getFileDirectoryDownloads() {
        final File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myDirectory = new File(directory, "book");
        if (!myDirectory.exists())
            myDirectory.mkdir();
        return myDirectory;
    }

    private Bitmap fileToBitmap(File file) {
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(drawerLayout))
            drawer.closeDrawer(drawerLayout);
        else
            super.onBackPressed();
    }

}