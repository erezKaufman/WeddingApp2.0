package com.example.erez0_000.weddingapp.todos_section;

import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {

    private String groupName;
    private List<ChildItemSample> itemList;
    private EditText newItemText;
    private Button addItemButton;
    private int numberOfItems =  0 ;
    public GroupItem(){

    }

    public GroupItem(String groupName){
        this.groupName = groupName;
        itemList = new ArrayList<>();
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public GroupItem(String groupName, List<ChildItemSample> itemList){
        this.groupName = groupName;
        this.itemList = itemList;
    }

    public List<ChildItemSample> getItemList() {
        return itemList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void addItem(ChildItemSample newItem){
        numberOfItems +=1;
        itemList.add(newItem);
    }
    public void deleteItem(ChildItemSample itemTodelete){
        numberOfItems -=1;
        itemList.remove(itemTodelete);
    }


}
