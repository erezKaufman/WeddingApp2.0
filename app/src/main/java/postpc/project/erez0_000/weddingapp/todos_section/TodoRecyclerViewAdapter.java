package postpc.project.erez0_000.weddingapp.todos_section;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import postpc.project.erez0_000.weddingapp.R;

import java.util.ArrayList;

public class TodoRecyclerViewAdapter
        extends RecyclerView.Adapter<TodoRecyclerViewAdapter.GroupViewHolder> {

    private ArrayList<String> todoTitleList;
    private CreateOnClickListner listener;

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(postpc.project.erez0_000.weddingapp.R.id.tv_id);

        }
    }

    /**
     * @param createOnClickListner
     */
    public TodoRecyclerViewAdapter(CreateOnClickListner createOnClickListner,
                                   ArrayList<String> todoTitleList) {
        this.todoTitleList = todoTitleList;
        listener = createOnClickListner;
    }


    public void addTodo(String todoTitle) {
        todoTitleList.add(todoTitle);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(postpc.project.erez0_000.weddingapp.R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final String todoTitle = todoTitleList.get(position);
        holder.groupName.setText(todoTitle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openTodoListInActivity(todoTitle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.deleteTodoFromListener(todoTitle);
                return true;
            }
        });


    }

    /**
     * interface method for the TodoRecyclerViewAdapter listener.
     * the method removes the TodoTitle from the arraylist and updates the recyclerView
     *
     * @param todoTitle
     */
    public void deleteTodo(String todoTitle) {

        todoTitleList.remove(todoTitle);
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return todoTitleList.size();
    }


    /**
     * listener to pass activity request from the CategoriesActivity
     */
    public interface CreateOnClickListner {
        void openTodoListInActivity(String todoTitle);

        void deleteTodoFromListener(String todoTitle);
    }
}