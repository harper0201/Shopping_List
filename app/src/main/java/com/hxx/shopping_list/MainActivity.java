package com.hxx.shopping_list;

import static android.view.KeyEvent.ACTION_DOWN;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView MyRecyclerview;
    private RecyclerView.Adapter Myadapter;
    private RecyclerView.LayoutManager Mylayoutmanager;
    private ArrayList<item>List;
    private Button add;
    private EditText input;
    private Button email;
    private EditText email_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create a list
        List = new ArrayList<>();
        //initialize the variables
        BuildRecycleView();
        add = findViewById(R.id.add);
        input = findViewById(R.id.new_item);
        //add on click listener to our add button
        input.setOnKeyListener(new MyKeyListener());
        add.setOnClickListener(new MyOnClikerListener());

        email = findViewById(R.id.email);
        email_id = findViewById(R.id.email_id);
        email.setOnClickListener(new MySecondOnClikerListener());

        //creating a method to create item touch helper method for adding swipe to delete functionality.
        //we are specifying drag direction and position to right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // get the position and exchange date
                int from_position = viewHolder.getAbsoluteAdapterPosition();
                int end_position = target.getAbsoluteAdapterPosition();
                Collections.swap(List, from_position, end_position);
                Myadapter.notifyItemMoved(from_position,end_position);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when swipe our item to right direction.
                // get the item at a particular position.
                item delete_item = List.get(viewHolder.getAbsoluteAdapterPosition());
                int remove_position = viewHolder.getAbsoluteAdapterPosition();
                //remove item from our array list.
                List.remove(remove_position);
                //notify item is removed from adapter
                Myadapter.notifyItemRemoved(remove_position);
                Snackbar.make(MyRecyclerview, delete_item.getText(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add on click listener to our action of snack bar.
                        // add our item to array list with a position.
                        List.add(remove_position, delete_item);
                        //notify item is added to our adapter class.
                        Myadapter.notifyItemInserted(remove_position);
                    }
                }).show();
            }
            //add this to our recycler view.
        }).attachToRecyclerView(MyRecyclerview);

    }

    // press enter key to add a new item
    class MyKeyListener implements View.OnKeyListener{
        @Override
        public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
            // press down enter equals to click add button
            if(keyEvent.getAction() == ACTION_DOWN && keycode == KeyEvent.KEYCODE_ENTER){
                add.callOnClick();
                return true;
            }
            return false;
        }
    }

    //click to add a new item
    class MyOnClikerListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(input.getText().length()!=0){
                //if input from user is not empty, add a new item at particular position and notify the adapter
                int current_position = Myadapter.getItemCount();
                List.add(current_position,new item(input.getText().toString()));
                Myadapter.notifyItemInserted(current_position);
                //clear the textview field
                input.getText().clear();
            }
            else{
                // A alert pops up for empty input.
                Toast.makeText(MainActivity.this,"empty input",Toast.LENGTH_LONG).show();
            }
            }
    }

    //click to send email
    class MySecondOnClikerListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // get the email address of user
            String receiver = email_id.getText().toString();
            if(receiver.length()!=0){
                // sned email using intent
                Intent send = new Intent(Intent.ACTION_SEND);
                // set the datatype of email
                send.setType("text/plain");
                // set the receiver of email
                send.putExtra(Intent.EXTRA_EMAIL,new String[] {receiver});
                // get the time and set the subject of email
                Date currentTime = Calendar.getInstance().getTime();
                String subject = "Shopping List" + "(" + currentTime.toString() + ")";
                send.putExtra(Intent.EXTRA_SUBJECT,subject);
                // get the list and store the data in a string
                String result = List.get(0).getText()+"\n";
                for(int j = 1; j < List.size(); j++){
                    result = result + List.get(j).getText()+"\n";
                }
                // send email
                send.putExtra(Intent.EXTRA_TEXT, result);
                // start activity of sending email and do some error checking
                try{
                    startActivity(Intent.createChooser(send,"Choose an Email client"));
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(MainActivity.this,"There is no email client installed",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(MainActivity.this,"Empty input!!",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void BuildRecycleView(){
        MyRecyclerview = findViewById(R.id.recyclerview);
        MyRecyclerview.setHasFixedSize(true);
        Mylayoutmanager = new LinearLayoutManager(this);
        //initializing our adapter class with our array list
        Myadapter = new Adapter(List);
        //set layout manager for our recycler view.
        MyRecyclerview.setLayoutManager(Mylayoutmanager);
        //set adapter to our recycler view.
        MyRecyclerview.setAdapter(Myadapter);
    }

}