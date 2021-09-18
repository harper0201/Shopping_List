package com.hxx.shopping_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                item delete_item = List.get(viewHolder.getAbsoluteAdapterPosition());
                int remove_position = viewHolder.getAbsoluteAdapterPosition();
                List.remove(remove_position);
                Myadapter.notifyItemRemoved(remove_position);
                Snackbar.make(MyRecyclerview, delete_item.getText(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List.add(remove_position, delete_item);
                        Myadapter.notifyItemInserted(remove_position);
                    }
                }).show();
            }

        }).attachToRecyclerView(MyRecyclerview);
    }

    class MyOnClikerListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(input.getText().length()!=0){
                int current_position = Myadapter.getItemCount();
                List.add(current_position,new item(input.getText().toString()));
                input.getText().clear();
                Myadapter.notifyItemInserted(current_position);
            }
            else{
                Toast.makeText(MainActivity.this,"empty input",Toast.LENGTH_LONG).show();
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