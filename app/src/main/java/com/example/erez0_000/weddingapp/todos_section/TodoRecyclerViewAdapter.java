package com.example.erez0_000.weddingapp.todos_section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.erez0_000.weddingapp.R;
import java.util.ArrayList;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.GroupViewHolder>
        implements View.OnClickListener, View.OnLongClickListener{
    //    private ArrayList<ExpandableListViewAdapter> todoTitleList;
    private ArrayList<TodoTitle> todoTitleList;
    private CreateOnClickListner listner;

    @Override
    public boolean onLongClick(View v) {
        return true;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView groupName;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.tv_id);

        }
    }

    public TodoRecyclerViewAdapter(CreateOnClickListner createOnClickListner){
        todoTitleList = new ArrayList<>();
        listner = createOnClickListner;
    }



    public void addTodo(TodoTitle todoTitle){
        todoTitleList.add(todoTitle);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group_item,parent,false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final TodoTitle todoTitle = todoTitleList.get(position);
        holder.groupName.setText(todoTitle.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.openTodoListInActivity(todoTitle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listner.deleteTodo(todoTitle);
                return true;
            }
        });
    }



    @Override
    public int getItemCount() {
        return todoTitleList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public interface CreateOnClickListner{
        void openTodoListInActivity(TodoTitle todoTitle);
        void deleteTodo(TodoTitle todoTitle);
    }
}