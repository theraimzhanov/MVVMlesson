package com.raimzhanov.mvvmlesson.data.remote;


import com.raimzhanov.mvvmlesson.pojo.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("/people")
    Call<List<Person>> getAllPerson();

}
