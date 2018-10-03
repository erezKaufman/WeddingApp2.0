package postpc.project.erez0_000.weddingapp.todos_section;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import postpc.project.erez0_000.weddingapp.R;

/**
 * Created by erez0_000 on 28/04/2018.
 */

public class AddSubTaskFragment extends DialogFragment implements View.OnClickListener{

    EditText editText;
    SubTaskDialogFragmentListner listner;
    View layout;
    Button send_button ;
    int groupPosition;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(postpc.project.erez0_000.weddingapp.R.layout.add_subtask_fragment,container, false);
        layout = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.subtask_fragment);
        editText = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.sub_task_editText);
        send_button = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.sub_task_btn);
        send_button.setOnClickListener(this);

        return view;

    }

    public void setListner(SubTaskDialogFragmentListner dlgFrag)
    {
        listner = dlgFrag;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    @Override
    public void onClick(View view) {
        listner.onDialogAddClick(editText.getText().toString(),groupPosition);
        dismiss();
    }

    public interface SubTaskDialogFragmentListner
    {
        void onDialogAddClick(String message, int groupPos);
    }
}
