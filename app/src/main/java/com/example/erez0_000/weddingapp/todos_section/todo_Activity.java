package com.example.erez0_000.weddingapp.todos_section;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.erez0_000.weddingapp.R;

public class todo_Activity extends AppCompatActivity {

    Button clearChecks;
    ExpandableListView mExpandableListView;
    ExpandableListViewAdapter mExpandableListAdapter;
    int mLastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_);
        clearChecks = (Button)findViewById(R.id.btnClearChecks);

        //  START those lines are important
        mExpandableListAdapter = new ExpandableListViewAdapter(this, listTitle, genChildList(listTitle));
        mExpandableListView = (ExpandableListView)findViewById(R.id.expandedListView);
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
        //  END those lines are important
    }
}
