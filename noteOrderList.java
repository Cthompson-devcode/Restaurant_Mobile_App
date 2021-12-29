package com.example.donnasdiner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.donnasdiner.Adapter.NotesAdapter;
import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.Notes;
import com.example.donnasdiner.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class noteOrderList extends AppCompatActivity {
    public static final Integer EXTRA_CART_ID =
            1111111111;
    public static final String EXTRA_NOTE_TITLE =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";

    public static final String EXTRA_NOTE_DETAILS =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    private int cartID;
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    private NotesViewModel notesViewModel;

   restaurantRepository restaurantRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_order_list);
        restaurantRepository = new restaurantRepository(getApplication());

        setTitle("List of Notes");

      //  System.out.println("THIS IS MY CARTID Extra =========" + getIntent().getStringExtra(EXTRA_CART_ID) );

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_note);

        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (noteOrderList.this, addEditOrderNote.class);
                intent.putExtra(addEditOrderNote.EXTRA_CART_ID, cartID);

                startActivityForResult(intent, 1 );

            }
        });

        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();

        cartID = intent.getIntExtra(String.valueOf(noteOrderList.EXTRA_CART_ID), -1);
        System.out.println("THIS IS THE COURSEID IN NOTES ACTIVITY=============" + cartID);


        NotesAdapter adapter2 = new NotesAdapter();
        recyclerView.setAdapter(adapter2);


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getLiveCartNotes(cartID).observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> filteredNotes) {

                adapter2.setNotes(filteredNotes);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notesViewModel.delete(adapter2.getNotesAt(viewHolder.getAdapterPosition()));

                Toast.makeText(noteOrderList.this, "Course Deleted! ", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter2.setonItemClickListener(new NotesAdapter.onItemClickListener() {
            @Override

            public void onItemClick(Notes notes) {
                //sending data that was clicked
                Intent intent = new Intent(noteOrderList.this, addEditOrderNote.class);
                intent.putExtra(addEditOrderNote.EXTRA_CART_ID, cartID);
                intent.putExtra(addEditOrderNote.EXTRA_NOTE_TITLE, notes.getTitle());
                intent.putExtra(addEditOrderNote.EXTRA_NOTE_DETAILS, notes.getNoteInfo());
                intent.putExtra(addEditOrderNote.EXTRA_NOTE_ID, notes.getUid());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK)
        {

            assert data != null;
            //int id = data.getIntExtra(NotesActivity.EXTRA_COURSE_ID, -1);
            String notetitle = data.getStringExtra(addEditOrderNote.EXTRA_NOTE_TITLE);
            String noteDetails = data.getStringExtra(addEditOrderNote.EXTRA_NOTE_DETAILS);




//saving user input to database
            Notes notes = new Notes ( cartID, notetitle, noteDetails);
            notesViewModel.insert(notes);


            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(addEditOrderNote.EXTRA_NOTE_ID, -1);
            if ( id == -1)
            {
                Toast.makeText(this, " Cannot Update Note", Toast.LENGTH_SHORT).show();
                return;

            }



            int cartID = data.getIntExtra(addEditOrderNote.EXTRA_CART_ID, -1);
            String notetitle = data.getStringExtra(addEditOrderNote.EXTRA_NOTE_TITLE);
            String noteDetails = data.getStringExtra(addEditOrderNote.EXTRA_NOTE_DETAILS);


//saving user input to database

            Notes notes = new Notes ( cartID, notetitle, noteDetails);
            notes.setUid(id);
            notesViewModel.update(notes);



            Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Note NOT Saved!", Toast.LENGTH_SHORT).show();


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
       // menuInflater.inflate(R.menu.main_menu, menu);
        //menuInflater.inflate(R.menu.share, menu);

        return true;
    }




}