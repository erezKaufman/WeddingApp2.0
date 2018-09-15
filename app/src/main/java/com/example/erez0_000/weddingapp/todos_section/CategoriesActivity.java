package com.example.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.erez0_000.weddingapp.R;

public class CategoriesActivity extends AppCompatActivity
        implements TodoRecyclerViewAdapter.CreateOnClickListner,
        View.OnClickListener
        {
    private RecyclerView gRecyclerView;
    private TodoRecyclerViewAdapter gviewAdapter;
    private Button addTodo;
    private EditText taskText;
//    private String text;

    private static final String ED_TASK_BACK = "ED_TASK_BACK";
    private static final String ED_TASK_ITEM = "TASK_ITEM";
    private static final int RC_EXPANDABLE = 9001;

    private ArrayList<TodoList> listOfTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initRecyclerView();
        listOfTodos = new ArrayList<>();
        taskText = findViewById(R.id.ed_main);
//        taskText.addTextChangedListener(this);

        addTodo = findViewById(R.id.bt_main);
        addTodo.setOnClickListener(this);


    }

    private void initRecyclerView() {
        gRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gviewAdapter = new TodoRecyclerViewAdapter(this, this);

        gRecyclerView.setAdapter(gviewAdapter);
    }

    @Override
    public void openTodoListInActivity(TodoTitle todoTitle) {

        TodoList listToSend = searchAndReturnGroup(todoTitle.getTitle());


        Intent i = new Intent(this, EXpandableActivity.class);
        i.putExtra(ED_TASK_ITEM, (Serializable) listToSend);
        startActivityForResult(i, RC_EXPANDABLE);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

    }

    @Override
    public void deleteTodo(TodoTitle todoTitle) {

    }

//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        text = s.toString();
//    }

    @Override
    public void onClick(View v) {
        if (taskText.getText().toString().isEmpty()) {
            return;
        }
        gviewAdapter.addTodo(new TodoTitle(taskText.getText().toString(), this));
        taskText.getText().clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_EXPANDABLE) {

            if (resultCode == Activity.RESULT_OK) {
                TodoList result = (TodoList) data.getSerializableExtra(ED_TASK_BACK);
                searchAndReplaceGroup(result);

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
}
