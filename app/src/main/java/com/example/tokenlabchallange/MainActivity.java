package com.example.tokenlabchallange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tokenlabchallange.Class.MoviePresenter;
import com.example.tokenlabchallange.Inteface.VoidListener;

public class MainActivity extends AppCompatActivity implements VoidListener {

    MoviePresenter presenter;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.listLoading);
        presenter = new MoviePresenter(this,(RecyclerView) findViewById(R.id.movieListContainer),this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        presenter.ItemSelected(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CallBack() {
        loading.setVisibility(View.GONE);
    }
}
