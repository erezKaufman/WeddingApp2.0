package com.example.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.erez0_000.weddingapp.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter
        implements AddSubTaskFragment.SubTaskDialogFragmentListner {

    private Activity activity;
    private TodoList groupList;
    //    private TodoList groupList;
    private int checkedBoxesCount;

    public ExpandableListViewAdapter(Activity activity,TodoList groupItems) {
        groupList = groupItems;
        this.activity = activity;
        checkedBoxesCount = 0;

    }

    public TodoList getGroupList() {
        return groupList;
    }

    @Override
    public int getGroupCount() {
        return groupList.getTodoList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.getTodoList().get(groupPosition).getItemList().size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return groupList.getGroupItem(groupPosition);
    }

    @Override
    public ChildItemSample getChild(int groupPosition, int childPosition) {
        return groupList.getGroupItem(groupPosition).getItemList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        final GroupItem itemGroup = getGroup(groupPosition);
        final GroupViewHolder groupViewHolder;
//        if (view == null) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.expanded_list_group, null);
        groupViewHolder = new GroupViewHolder();
        groupViewHolder.tvGroup = view.findViewById(R.id.tv_group);
        groupViewHolder.cbGroup = view.findViewById(R.id.cb_group);
        groupViewHolder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemGroup.isChecked()){

                    itemGroup.setChecked(false);
                    groupViewHolder.cbGroup.setChecked(false);
                    for (ChildItemSample child : itemGroup.getItemList()){
                        child.setChecked(false);
                    }
                }else {
                    itemGroup.setChecked(true);
                    groupViewHolder.cbGroup.setChecked(false);
                    for (ChildItemSample child : itemGroup.getItemList()){
                        child.setChecked(true);
                    }
                }
                notifyDataSetChanged();
            }
        });
        groupViewHolder.imbGroup = view.findViewById(R.id.imageButton);
        groupViewHolder.imbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateSubTaskFrag(v,groupPosition);
            }
        });
        view.setTag(groupViewHolder);
//        } else {
//            groupViewHolder = (GroupViewHolder) view.getTag();
//        }
        groupViewHolder.tvGroup.setText(String.format("%s (%d)", itemGroup.getGroupName(),
                getChildrenCount(groupPosition)));
        groupViewHolder.cbGroup.setChecked(itemGroup.isChecked());
        groupViewHolder.cbGroup.setTag(groupPosition);
        return view;
    }

    public void openCreateSubTaskFrag(View view, int groupPosition) {
        FragmentManager ft = ((FragmentActivity)activity).getSupportFragmentManager();
        AddSubTaskFragment myDialogFrag = new AddSubTaskFragment();
        myDialogFrag.setListner(this);
        myDialogFrag.setGroupPosition(groupPosition);
        myDialogFrag.show(ft ,null);

        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildItemSample expandedListText = getChild(groupPosition, childPosition);
        ChildViewHolder childViewHolder;
//        final EditTaskItem editTaskItem;
        final GroupItem curGroup = groupList.getGroupItem(groupPosition);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.expanded_list_item, null);
        childViewHolder = new ChildViewHolder();
        childViewHolder.tvChild = view.findViewById(R.id.tv_child);
        childViewHolder.cbChild = view.findViewById(R.id.cb_child);
        childViewHolder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                ChildItemSample selectedItem = getChild(groupPosition,childPosition);
                selectedItem.setChecked(cb.isChecked());
                int counter = 0;
                for (int i = 0;i<getChildrenCount(groupPosition);i++){
                    if (curGroup.getItemList().get(i).isChecked()){
                        counter++;
                    }
                }

                if (counter == getChildrenCount(groupPosition)) {
                    curGroup.setChecked(true);
                }
                else{
                    curGroup.setChecked(false);
                }

                if (cb.isChecked()) {
                    checkedBoxesCount++;

                    Toast.makeText(activity, "Checked value is: " +
                                    getChild(groupPosition,childPosition),
                            Toast.LENGTH_SHORT).show();
                } else {
                    checkedBoxesCount--;
                    if (checkedBoxesCount == 0) {
                        Toast.makeText(activity, "nothing checked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();
            }
        });

        view.setTag(childViewHolder);
        childViewHolder.cbChild.setChecked(expandedListText.isChecked());
        childViewHolder.tvChild.setText(expandedListText.getName());
//        }
//        }else {
//            childViewHolder = (ChildViewHolder)view.getTag();
//        }
        return view;
    }

//    public void clearChecks() {
//        for (int i = 0; i < checkedGroup.length; i++) checkedGroup[i] = false;
//        for (GroupItem item: groupList.getTodoList()) {
//            for (ChildItemSample sample : item.getItemList()) {
//                sample.setChecked(false);
//            }
//        }
//        checkedBoxesCount = 0;
//        notifyDataSetChanged();
//    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onDialogAddClick(String subTask,int groupPos) {
        if (subTask.isEmpty()){
            return;
        }
        groupList.getGroupItem(groupPos).addItem(new ChildItemSample(subTask));
        notifyDataSetChanged();
        notifyDataSetInvalidated();


    }

    private class GroupViewHolder {
        CheckBox cbGroup;
        TextView tvGroup;
        ImageButton imbGroup;
    }

    private class ChildViewHolder {
        CheckBox cbChild;
        TextView tvChild;
    }

//    private class EditTaskItem{
//        EditText etchild;
//        Button   btchild;
//        String etString;
//    }
}