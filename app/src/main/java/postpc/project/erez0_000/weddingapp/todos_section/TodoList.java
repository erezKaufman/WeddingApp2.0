package postpc.project.erez0_000.weddingapp.todos_section;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TodoList  implements Serializable{
    @SerializedName("todoList")
    private ArrayList<GroupItem> todoList;
    @SerializedName("todoName")
    private String todoName;
    public TodoList(){

    }

    public TodoList(ArrayList<GroupItem> todoList){
        this.todoList = todoList;
    }

    public TodoList(String name){
        this.todoList = new ArrayList<>();
        todoName = name;
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

    public String getTodoName() {
        return todoName;
    }
}


