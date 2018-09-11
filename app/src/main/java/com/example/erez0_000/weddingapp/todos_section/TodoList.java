package com.example.erez0_000.weddingapp.todos_section;

import java.util.List;

public class TodoList {

    private List<GroupItem> todoList;

    public TodoList(){

    }

    public TodoList(List<GroupItem> todoList){
        this.todoList = todoList;
    }

    public void addTaskInTodo(int groupIndex, String taskName){
//        for (GroupItem group :todoList){
//            if (group.getGroupName().equals(todoName) ){
//                group.addItem(new ChildItemSample(taskName));
//            }
//        }
        todoList.get(groupIndex).addItem(new ChildItemSample(taskName));
    }
    public void addTodo(String todoName ){
        todoList.add(new GroupItem(todoName));
    }


    public GroupItem getGroupItem(int groupPosition){
        return todoList.get(groupPosition);
    }
    public List<GroupItem> getTodoList() {
        return todoList;
    }
}


