package com.example.desafiofluxitmvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.desafiofluxitmvvm.models.RamdomUserResponse;
import com.example.desafiofluxitmvvm.viewmodel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PersonViewModel.showError, AdapterUser.UserClick, InfiniteScrollListener.OnLoadMoreListener {

    private PersonViewModel model;
    private int page = 0;
    private InfiniteScrollListener infiniteScrollListener;
    private String seed;
    private LinearLayoutManager linearLayoutManager;
    private List<RamdomUserResponse> responseListUpdate = new ArrayList<>();
    private AdapterUser adapterUser;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        showProgressView();
        linearLayoutManager = new LinearLayoutManager(this);

        model = ViewModelProviders.of(this).get(PersonViewModel.class);
        model.setShowError(this);

        callApi();

        infiniteScrollListener = new InfiniteScrollListener(linearLayoutManager, this);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            responseListUpdate.clear();
            callApi();
        });

        recyclerView.addOnScrollListener(infiniteScrollListener);
    }

        private void netxPage() {
            page++;
            model.getNextPage(20, seed, page).observe(MainActivity.this, resultResponse -> {
                adapterUser.removeNull();
                responseListUpdate.addAll(resultResponse.getRamdomUserResponses());
                adapterUser.addData(responseListUpdate);
                infiniteScrollListener.setLoaded();
            });
        }

        private void callApi () {
            model.getPersons().observe(this, results -> {
                seed = results.getInfo().getSeed();
                hideProgressView();
                responseListUpdate = results.getRamdomUserResponses();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, "Exito", Toast.LENGTH_SHORT).show();
                initRecyclerview(responseListUpdate);
            });
        }

        private void initRecyclerview (List < RamdomUserResponse > response) {
            adapterUser = new AdapterUser(response, this, this);
            recyclerView.setHasFixedSize(true);
            hideProgressView();
            runLayoutAnimation(recyclerView);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterUser);
        }


        private void runLayoutAnimation ( final RecyclerView recyclerView){
            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            recyclerView.setLayoutAnimation(controller);
            adapterUser.notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


        @Override
        public void error (String error){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }

        void showProgressView () {
            progressBar.setVisibility(View.VISIBLE);
        }

        void hideProgressView () {
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick (RamdomUserResponse response){
            double lat = Double.parseDouble(response.getLocation().getCoordinates().getLatitude());
            double longitude = Double.parseDouble(response.getLocation().getCoordinates().getLongitude());
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble(MapActivity.KEY_LAT, lat);
            bundle.putDouble(MapActivity.KEY_LONG, longitude);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    @Override
    public void onLoadMore() {
        adapterUser.addNullData();
        netxPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterUser != null){
                    adapterUser.getFilter().filter(newText);
                }
                return true;
            }
        });
        return  true;
    }
}

