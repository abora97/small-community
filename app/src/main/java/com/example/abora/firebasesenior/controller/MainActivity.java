package com.example.abora.firebasesenior.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abora.firebasesenior.Model.Status;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.adapter.StatusAdapter;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;
import com.example.abora.firebasesenior.callback.OnFirebaseDataListener;
import com.example.abora.firebasesenior.callback.OnItemLongClickListner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnItemLongClickListner {

    @BindView(R.id.my_recycler)
    RecyclerView myRecycler;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.add_fab_bu)
    FloatingActionButton addFabBu;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private RecyclerView.LayoutManager layoutManager;
    private StatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        addFabBu.setOnClickListener(this);
        swipeLayout.setOnRefreshListener(this);

        layoutManager = new LinearLayoutManager(this);

        myRecycler.setLayoutManager(layoutManager);
        adapter = new StatusAdapter(MainActivity.this, new ArrayList<Status>(), this);
        myRecycler.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        getAllitems();

    }

    private void getAllitems() {
        Networking.getAllStatus(new OnFirebaseDataListener() {
            @Override
            public void onSucess(List<Status> list) {
                adapter.update(list);
                progressBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onErro(String errorMag) {
                Toast.makeText(MainActivity.this, errorMag, Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fab_bu:
                startActivity(new Intent(MainActivity.this, DetailActitity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        getAllitems();
    }

    @Override
    public void onLongClisck(final Status status) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Deleting !!")
                .setMessage("Are You Sure !!")
                .setCancelable(false)
                .setPositiveButton("OK !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePost(status);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void deletePost(Status status) {
        Networking.deletePost(status, new OnFireBaseOperationListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
                getAllitems();
            }

            @Override
            public void onFailed(String errorMassege) {
                Toast.makeText(MainActivity.this, errorMassege, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.test_menu:
               startActivity(new Intent(MainActivity.this,ChatActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}