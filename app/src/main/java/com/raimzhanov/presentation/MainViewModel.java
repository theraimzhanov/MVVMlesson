package com.raimzhanov.presentation;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raimzhanov.mvvmlesson.data.local.AppDataBase;
import com.raimzhanov.mvvmlesson.data.remote.RetrofitBuilder;
import com.raimzhanov.mvvmlesson.pojo.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private static AppDataBase db;
    private LiveData<List<Person>> persons;
    private  MutableLiveData<Throwable> errors;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = AppDataBase.getInstance(application);
        persons = db.dao().getAllPersons();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }
public void clearErrors(){
        errors.setValue(null);
}


    private void insertPersons(List<Person> people){
     new InsertPersonTask().execute(people);
    }

    private static class InsertPersonTask extends AsyncTask<List<Person>,Void,Void>{

        @Override
        protected Void doInBackground(List<Person>... lists) {
            if (lists != null && lists.length>0){
                db.dao().insertPersons(lists[0]);
            }
            return null;
        }
    }

    private void deleteAll(){
 new DeleteAllTask().execute();
    }
    private static class DeleteAllTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            db.dao().clearDb();
            return null;
        }
    }

    public void getData(){
        RetrofitBuilder.getService().getAllPerson().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                deleteAll();
                insertPersons(response.body());
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                   errors.setValue(t);
            }
        });
    }
}
