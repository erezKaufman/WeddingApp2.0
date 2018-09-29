package com.example.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;

public class CategoriesActivity extends AppCompatActivity
        implements TodoRecyclerViewAdapter.CreateOnClickListner,
        View.OnClickListener,
        DeleteTodoFragment.DeletefragmentListener{
    private RecyclerView gRecyclerView;
    private TodoRecyclerViewAdapter gviewAdapter;
    private Button addTodo;
    private EditText taskText;
    private ArrayList<TodoList> listOfTodos;


    private static final String ED_TASK_BACK = "ED_TASK_BACK";
    private static final String ED_TASK_ITEM = "TASK_ITEM";
    private static final int RC_EXPANDABLE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        // hold the User information
//        User currentUser = Database.curUser;

        // fill the recyclerView with the todos from the user
//        if (currentUser != null){
//            listOfTodos = currentUser.getTodoArray();
//
//        }else{
        listOfTodos = new ArrayList<>();
//        }
        // START editing View
        taskText = findViewById(R.id.ed_main);
        taskText.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        addTodo = findViewById(R.id.bt_main);
        addTodo.setOnClickListener(this);
        gRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // END editing View
        initRecyclerView();

    }

    /**
     * the method initialize the recyclerView for the categories - which each holds the TodoList
     */
    private void initRecyclerView() {
        ArrayList<String> titleList = new ArrayList<>();
        for (TodoList mTodo: listOfTodos){
            titleList.add(mTodo.getTodoName());
        }
        gRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gviewAdapter = new TodoRecyclerViewAdapter(this,titleList);

        gRecyclerView.setAdapter(gviewAdapter);
    }

    /**
     * interface method for the TodoRecyclerViewAdapter listener.
     * the method takes the cell that the user chose and open it in a new activity
     * @param todoTitle the title of the todolist
     */
    @Override
    public void openTodoListInActivity(String todoTitle) {

        TodoList listToSend = searchAndReturnGroup(todoTitle);


        Intent i = new Intent(this, EXpandableActivity.class);
        i.putExtra(ED_TASK_ITEM, (Serializable) listToSend);
        startActivityForResult(i, RC_EXPANDABLE);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

    }

    /**
     *
     * @param todoTitle
     */
    @Override
    public void deleteTodoFromListener(final String todoTitle) {
        FragmentManager ft = getSupportFragmentManager();
        DeleteTodoFragment appointmentFrag = DeleteTodoFragment.newInstance();
        appointmentFrag.setListener(new DeleteTodoFragment.DeletefragmentListener() {
            /**
             * implementing listener's method - after
             */
            @Override
            public void acceptDelition() {
                gviewAdapter.deleteTodo(todoTitle);
                Toast.makeText(CategoriesActivity.this,"הרשימה נמחקה בהצלחה",Toast.LENGTH_LONG).show();

            }
        });
        appointmentFrag.show(ft ,null);



    }


    @Override
    public void onClick(View v) {
        if (taskText.getText().toString().isEmpty()) {
            return;
        }
        gviewAdapter.addTodo(taskText.getText().toString());
        taskText.getText().clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_EXPANDABLE) {

            if (resultCode == Activity.RESULT_OK) {
                TodoList result = (TodoList) data.getSerializableExtra(ED_TASK_BACK);
                searchAndReplaceGroup(result);
                // TODO: 29/09/2018  here add todolist to user, or in searchAndReplace
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }

    private TodoList searchAndReturnGroup(String res) {
        for (TodoList item : listOfTodos) {
            if (item.getTodoName().equals(res)) {
                return item;
            }

        }
        TodoList returnedList = new TodoList(res);
        listOfTodos.add(returnedList);
        return returnedList;
    }

    private void searchAndReplaceGroup(TodoList res) {
        for (TodoList item : listOfTodos) {
            if (item.getTodoName().equals(res.getTodoName())) {
                listOfTodos.remove(item);
                listOfTodos.add(res);
                return;
            }
        }
    }

    @Override
    public void acceptDelition() {

    }
}
