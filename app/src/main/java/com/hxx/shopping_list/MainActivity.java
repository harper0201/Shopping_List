package com.hxx.shopping_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView MyRecyclerview;
    private RecyclerView.Adapter Myadapter;
    private RecyclerView.LayoutManager Mylayoutmanager;
    private ArrayList<item>List;
    private Button add;
    private EditText input;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List = new ArrayList<>();
        BuildRecycleView();
        add = findViewById(R.id.add);
        input = findViewById(R.id.new_item);
        add.setOnClickListener(new MyOnClikerListener());
    }

    class MyOnClikerListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(input.getText().toString().isEmpty() == false){
                int current_position = Myadapter.getItemCount();
                List.add(current_position,new item(input.getText().toString()));
                input.setText(" ");
                Myadapter.notifyDataSetChanged();
            }
        }
    }


    public void BuildRecycleView(){
        MyRecyclerview = findViewById(R.id.recyclerview);
        MyRecyclerview.setHasFixedSize(true);
        Mylayoutmanager = new LinearLayoutManager(this);
        Myadapter = new Adapter(List);
        MyRecyclerview.setLayoutManager(Mylayoutmanager);
        MyRecyclerview.setAdapter(Myadapter);
    }
}