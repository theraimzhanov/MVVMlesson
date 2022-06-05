package com.raimzhanov.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.raimzhanov.mvvmlesson.R;
import com.raimzhanov.mvvmlesson.pojo.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getData();
        viewModel.getPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                

                Log.d("TAG", "onChanged: "+people.size());
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null){
                Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                viewModel.clearErrors();
            }
            }
        });
    }
}