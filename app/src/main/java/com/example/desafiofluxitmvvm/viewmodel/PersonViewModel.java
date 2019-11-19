package com.example.desafiofluxitmvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.desafiofluxitmvvm.api.Api;
import com.example.desafiofluxitmvvm.models.ResultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonViewModel extends ViewModel {
    private showError showError;
    private MutableLiveData<ResultResponse> personList;
    private MutableLiveData<ResultResponse> personListUpdate;


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.SERVICE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    public void setShowError(PersonViewModel.showError showError) {
        this.showError = showError;
    }

    public LiveData<ResultResponse> getPersons() {
        if (personList == null ) {
            personList = new MutableLiveData<>();
            loadRamdomUser();
        }else {
            loadRamdomUser();
        }
        return personList;
    }

    public LiveData<ResultResponse> getNextPage( Integer number, String seed, int page) {
        if (personListUpdate == null) {
            personListUpdate = new MutableLiveData<>();
            loadNetxPage(number,seed, page);
            } else
            loadNetxPage(number,seed, page);
        return personListUpdate;

    }


    private void loadRamdomUser() {
        Call<ResultResponse> call = api.getRandomUsers(20);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful())
                    personList.setValue(response.body());
                else
                    showError.error("Ocurrio un Error");
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
    }

    private void loadNetxPage(Integer number , String seed, int page ) {
        Call<ResultResponse> call = api.getNetPage(number,seed, page);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                personListUpdate.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

            }
        });
    }

    public interface showError {
        void error(String error);
    }
}
