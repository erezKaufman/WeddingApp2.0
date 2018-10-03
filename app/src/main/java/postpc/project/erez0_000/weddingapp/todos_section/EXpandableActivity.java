package postpc.project.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import postpc.project.erez0_000.weddingapp.R;

public class EXpandableActivity extends AppCompatActivity implements View.OnClickListener
{

    ExpandableListView mExpandableListView;
    ExpandableListViewAdapter mExpandableListAdapter;
    TodoList taskList;
    int mLastExpandedPosition = -1;
    private EditText insertTodo;
    private Button addTodo;
    private String text,taskBusinessText;

    private static final String ED_TASK_ITEM = "TASK_ITEM";
    private static final String ED_TASK_BACK = "ED_TASK_BACK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(postpc.project.erez0_000.weddingapp.R.layout.activity_expandable);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent i = getIntent();
        taskList= (TodoList) i.getSerializableExtra(ED_TASK_ITEM);



        mExpandableListView = (ExpandableListView) findViewById(postpc.project.erez0_000.weddingapp.R.id.expandedListView);
        mExpandableListAdapter = new ExpandableListViewAdapter(this,taskList);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = mExpandableListView.getExpandableListPosition(position);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);


                /*  if group item clicked */
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {

                    mExpandableListAdapter.deleteGrouplist(groupPosition);
                }

                /*  if child item clicked */
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

                    mExpandableListAdapter.deleteitem(groupPosition, childPosition);
                }


                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(mLastExpandedPosition != -1 && (mLastExpandedPosition != groupPosition)){
                    mExpandableListView.collapseGroup(mLastExpandedPosition);
                }
                mLastExpandedPosition = groupPosition;
            }
        });


        insertTodo = findViewById(postpc.project.erez0_000.weddingapp.R.id.et_expandable);

        addTodo = findViewById(postpc.project.erez0_000.weddingapp.R.id.bt_expandable);
        addTodo.setOnClickListener(this);

        taskBusinessText= i.getExtras().getString("task to write");
        if (taskBusinessText != null){
            insertTodo.setText(taskBusinessText);
        }
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
        overridePendingTransition  (postpc.project.erez0_000.weddingapp.R.anim.right_slide_in, postpc.project.erez0_000.weddingapp.R.anim.right_slide_out);
    }
}
