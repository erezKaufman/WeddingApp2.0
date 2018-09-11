package com.example.erez0_000.weddingapp.todos_section;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.todos_section.ChildItemSample;
import com.example.erez0_000.weddingapp.todos_section.ExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testActivity extends AppCompatActivity {

    Button clearChecks;
    ExpandableListView mExpandableListView;
    ExpandableListViewAdapter mExpandableListAdapter;
    int mLastExpandedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        clearChecks = (Button)findViewById(R.id.btnClearChecks);
        List<String> listTitle = genGroupList();

        // TODO START those lines are important
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
        // TODO END those lines are important
        clearChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandableListAdapter.clearChecks();
            }
        });
    }

    private List<String> genGroupList(){
        List<String> listGroup = new ArrayList<>();
        for(int i=1; i<10; i++){
            listGroup.add("Group: " + i);
        }
        return listGroup;
    }

    private Map<String, List<ChildItemSample>> genChildList(List<String> header){
        Map<String, List<ChildItemSample>> listChild = new HashMap<>();
        for(int i=0; i<header.size(); i++){
            List<ChildItemSample> testDataList = new ArrayList<>();
            int a = (int)(Math.random() * 8);
            for(int j=0; j<a; j++){
                ChildItemSample testItem = new ChildItemSample("Child " + (j + 1));
                testDataList.add(testItem);
            }
            listChild.put(header.get(i), testDataList);
        }
        return  listChild;
    }

}