package com.example.erez0_000.weddingapp.todos_section;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erez0_000.weddingapp.R;

import java.util.List;
import java.util.Map;


public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
//    private List<GroupItem> listGroup;
    private TodoList groupList;
    private int checkedBoxesCount;
    private boolean[] checkedGroup;

    public ExpandableListViewAdapter(Context context, List<String> listGroup, Map<String,
            List<ChildItemSample>> listChild) {
        this.context = context;
//        this.listGroup = listGroup;
//        this.listChild = listChild;
        checkedBoxesCount = 0;
        checkedGroup = new boolean[listGroup.size()];
    }

    @Override
    public int getGroupCount() {
        return groupList.getTodoList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.getGroupItem(groupPosition).getItemList().size();
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
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        GroupItem itemGroup = getGroup(groupPosition);
        GroupViewHolder groupViewHolder;
//        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expanded_list_group, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvGroup = view.findViewById(R.id.tv_group);
            groupViewHolder.cbGroup = view.findViewById(R.id.cb_group);

            groupViewHolder.cbGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    checkedGroup[pos] = !checkedGroup[pos];
//                    listChild.get(listGroup.get(pos))
                    for (ChildItemSample item : groupList.getGroupItem(pos).getItemList()) {
                        item.setChecked(checkedGroup[pos]);
                    }
                    notifyDataSetChanged();
                }
            });
            view.setTag(groupViewHolder);
//        } else {
//            groupViewHolder = (GroupViewHolder) view.getTag();
//        }
        groupViewHolder.tvGroup.setText(String.format("%s (%d)", itemGroup, getChildrenCount(groupPosition)));
        groupViewHolder.cbGroup.setChecked(checkedGroup[groupPosition]);
        groupViewHolder.cbGroup.setTag(groupPosition);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildItemSample expandedListText = getChild(groupPosition, childPosition);
        ChildViewHolder childViewHolder;
        final EditTaskItem editTaskItem;
        GroupItem curGroup = groupList.getGroupItem(groupPosition);
//        if(view == null){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (curGroup.getNumberOfItems() == childPosition){
            view = inflater.inflate(R.layout.add_task_item,null);
            editTaskItem = new EditTaskItem();
            editTaskItem.btchild = view.findViewById(R.id.bt_child);
            editTaskItem.etchild = view.findViewById(R.id.ed_child);
            editTaskItem.btchild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupList.addTaskInTodo(groupPosition,editTaskItem.etString);
                }
            });
            editTaskItem.etchild.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    editTaskItem.etString = s.toString();
                }
            });
        }
        else{
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
                    if (cb.isChecked()) {
                        checkedBoxesCount++;
                        Toast.makeText(context, "Checked value is: " +
                                getChild(groupPosition,childPosition),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        checkedBoxesCount--;
                        if (checkedBoxesCount == 0) {
                            Toast.makeText(context, "nothing checked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "unchecked", Toast.LENGTH_SHORT).show();
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            view.setTag(childViewHolder);
            childViewHolder.cbChild.setChecked(expandedListText.isChecked());
            childViewHolder.tvChild.setText(expandedListText.getName());
        }
//        }else {
//            childViewHolder = (ChildViewHolder)view.getTag();
//        }
        return view;
    }

    public void clearChecks() {
        for (int i = 0; i < checkedGroup.length; i++) checkedGroup[i] = false;
        for (GroupItem item: groupList.getTodoList()) {
            for (ChildItemSample sample : item.getItemList()) {
                sample.setChecked(false);
            }
        }
        checkedBoxesCount = 0;
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder {
        CheckBox cbGroup;
        TextView tvGroup;
    }

    private class ChildViewHolder {
        CheckBox cbChild;
        TextView tvChild;
    }

    private class EditTaskItem{
        EditText etchild;
        Button   btchild;
        String etString;
    }
}