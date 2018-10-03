package postpc.project.erez0_000.weddingapp.todos_section;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import postpc.project.erez0_000.weddingapp.R;

public class DeleteTodoFragment extends DialogFragment implements View.OnClickListener{
    private DeletefragmentListener listener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(postpc.project.erez0_000.weddingapp.R.layout.delete_todo,container, false);
        view.findViewById(postpc.project.erez0_000.weddingapp.R.id.delete_todo_accept).setOnClickListener(this);
        view.findViewById(postpc.project.erez0_000.weddingapp.R.id.delete_todo_reject).setOnClickListener(this);
        return view;
    }

    public static DeleteTodoFragment newInstance() {

        Bundle args = new Bundle();

        DeleteTodoFragment fragment = new DeleteTodoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void setListener(DeletefragmentListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case postpc.project.erez0_000.weddingapp.R.id.delete_todo_reject:
                dismiss();
                break;
            case postpc.project.erez0_000.weddingapp.R.id.delete_todo_accept:
                listener.acceptDelition();
                dismiss();
                break;
        }
    }

    public interface DeletefragmentListener{
        public void acceptDelition();
    }
}
