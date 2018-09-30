package com.example.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.erez0_000.weddingapp.R;

public class EXpandableActivity extends AppCompatActivity implements View.OnClickListener
{

    ExpandableListView mExpandableListView;
    ExpandableListViewAdapter mExpandableListAdapter;
    TodoList taskList;
    int mLastExpandedPosition = -1;
    private EditText insertTodo;
    private Button addTodo;
    private String text;

    private static final int RC_EXPANDABLE = 9001;
    private static final String ED_TASK_ITEM = "TASK_ITEM";
    private static final String ED_TASK_BACK = "ED_TASK_BACK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        Intent i = getIntent();
        taskList= (TodoList) i.getSerializableExtra(ED_TASK_ITEM);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandedListView);
        mExpandableListAdapter = new ExpandableListViewAdapter(this,taskList);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(mLastExpandedPosition != -1 && (mLastExpandedPosition != groupPosition)){
                    mExpandableListView.collapseGroup(mLastExpandedPosition);
                }
                mLastExpandedPosition = groupPosition;
            }
        });


        insertTodo = findViewById(R.id.et_expandable);
//        insertTodo.addTextChangedListener(this);

        addTodo = findViewById(R.id.bt_expandable);
        addTodo.setOnClickListener(this);


        // todo create get serializible of todolist and fill it in the todos here

    }


    @Override
    public void onClick(View v) {
        if (insertTodo.getText().toString()
                .isEmpty()){
            return;
        }

//        mExpandableListAdapter.getGroupList().get(0).addItem(new ChildItemSample(text));
        mExpandableListAdapter.getGroupList().getTodoList().add(new GroupItem(insertTodo.getText().toString()));
        mExpandableListAdapter.notifyDataSetChanged();
        mExpandableListAdapter.notifyDataSetInvalidated();
        insertTodo.getText().clear();

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra(ED_TASK_BACK,taskList);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }
}
