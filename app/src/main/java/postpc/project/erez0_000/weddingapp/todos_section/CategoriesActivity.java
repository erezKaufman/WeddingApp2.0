package postpc.project.erez0_000.weddingapp.todos_section;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.db_classes.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class CategoriesActivity extends AppCompatActivity
        implements TodoRecyclerViewAdapter.CreateOnClickListner,
        View.OnClickListener,
        DeleteTodoFragment.DeletefragmentListener {
    private RecyclerView gRecyclerView;
    private TodoRecyclerViewAdapter gviewAdapter;
    private Button addTodo;
    private EditText taskText;
    private ArrayList<TodoList> listOfTodos;
    private String businessTypeFromSetAppointment, taskBusinessText;

    private final static String CATEGORYTEXTINFO = "זהו עמוד המטלות שלך, הוא מכיל רשימה בנויה מראש של הצעות שלנו לקראת תכנון החתונה שלך! המטלות יעזרו לך החל מלחשוב על מספר מוזמנים ועד למצוא פעילויות מעניינות להפעלת ילדים באירוע. שימוש נכון במטלות ותכנון זמנים לקראת החתונה יכול להקל משמעותית על הלחץ המלווה את רוב הזוגות בזמנים אלו";
    private static final String ED_TASK_BACK = "ED_TASK_BACK";
    private static final String ED_TASK_ITEM = "TASK_ITEM";
    private static final int RC_EXPANDABLE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(postpc.project.erez0_000.weddingapp.R.layout.activity_categories);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // hold the User information
        User currentUser = User.thisUser;

        // fill the recyclerView with the todos from the user
        listOfTodos = currentUser.getTodoArray();

        // START editing View
        taskText = findViewById(postpc.project.erez0_000.weddingapp.R.id.ed_main);
        taskText.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        addTodo = findViewById(postpc.project.erez0_000.weddingapp.R.id.bt_main);
        addTodo.setOnClickListener(this);
        gRecyclerView = (RecyclerView) findViewById(postpc.project.erez0_000.weddingapp.R.id.recyclerView);
        Intent i = getIntent();
        taskBusinessText = i.getExtras().getString("task name");
        if (!taskBusinessText.equals("NULL") ){
            CreateTaskFromBusinessActivity();
        }
        initRecyclerView();
//        showManuel();
        // END editing View


    }

    private void CreateTaskFromBusinessActivity() {

        if (taskBusinessText != null){
            TodoList listToSend = searchAndReturnGroup("המטלות שלי");
            Intent newIntent = new Intent(this, EXpandableActivity.class);
            newIntent.putExtra(ED_TASK_ITEM, (Serializable) listToSend);
            newIntent.putExtra("task to write",taskBusinessText);
            startActivityForResult(newIntent, RC_EXPANDABLE);
            overridePendingTransition(postpc.project.erez0_000.weddingapp.R.anim.right_slide_in, postpc.project.erez0_000.weddingapp.R.anim.right_slide_out);
        }
    }

    /**
     * the method initialize the recyclerView for the categories - which each holds the TodoList
     */
    private void initRecyclerView() {
        ArrayList<String> titleList = new ArrayList<>();
        for (TodoList mTodo : listOfTodos) {
            titleList.add(mTodo.getTodoName());
        }
        gRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gviewAdapter = new TodoRecyclerViewAdapter(this, titleList);

        gRecyclerView.setAdapter(gviewAdapter);
    }

    /**
     * interface method for the TodoRecyclerViewAdapter listener.
     * the method takes the cell that the user chose and open it in a new activity
     *
     * @param todoTitle the title of the todolist
     */
    @Override
    public void openTodoListInActivity(String todoTitle) {

        TodoList listToSend = searchAndReturnGroup(todoTitle);


        Intent i = new Intent(this, EXpandableActivity.class);
        i.putExtra(ED_TASK_ITEM, (Serializable) listToSend);
        startActivityForResult(i, RC_EXPANDABLE);
        overridePendingTransition(postpc.project.erez0_000.weddingapp.R.anim.right_slide_in, postpc.project.erez0_000.weddingapp.R.anim.right_slide_out);

    }

    /**
     * @param todoTitle
     */
    @Override
    public void deleteTodoFromListener(final String todoTitle) {
        FragmentManager ft = getFragmentManager();
        DeleteTodoFragment appointmentFrag = DeleteTodoFragment.newInstance();
        appointmentFrag.setListener(new DeleteTodoFragment.DeletefragmentListener() {
            /**
             * implementing listener's method - after
             */
            @Override
            public void acceptDelition() {
                gviewAdapter.deleteTodo(todoTitle);
                Toast.makeText(CategoriesActivity.this, "הרשימה נמחקה בהצלחה", Toast.LENGTH_LONG).show();

            }
        });
        appointmentFrag.show(ft, null);


    }


    @Override
    public void onClick(View v) {
        if (taskText.getText().toString().isEmpty()) {
            return;
        }
        gviewAdapter.addTodo(taskText.getText().toString());
        taskText.getText().clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_EXPANDABLE) {

            if (resultCode == Activity.RESULT_OK) {
                TodoList result = (TodoList) data.getSerializableExtra(ED_TASK_BACK);
                searchAndReplaceGroup(result);
                // TODO: 29/09/2018  here add todolist to user, or in searchAndReplace
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }

    private TodoList searchAndReturnGroup(String res) {
        for (TodoList item : listOfTodos) {
            if (item.getTodoName().equals(res)) {
                return item;
            }

        }
        TodoList returnedList = new TodoList(res);
        listOfTodos.add(returnedList);
        return returnedList;
    }

    private void searchAndReplaceGroup(TodoList res) {
        int pos = 0;
        for (TodoList item : listOfTodos) {
            if (item.getTodoName().equals(res.getTodoName())) {
                listOfTodos.remove(item);
                listOfTodos.add(res);
                User.thisUser.getTodoArray().remove(res);
                User.thisUser.getTodoArray().add(res);

                Database.getInstance().updateUser(User.thisUser, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(CategoriesActivity.this, "שינויים עודכנו", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CategoriesActivity.this, "קרתה תקלה בעידכון השינויים, אנא נסו שנית", Toast.LENGTH_SHORT).show();
                    }
                });

                return;
            }
            pos += 1;
        }
    }

    @Override
    public void acceptDelition() {

    }

    /**
     * the method opens the guide animation in the user's first run in the page
     */
    private void showManuel() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, User.thisUser+"Categories");


        sequence.setConfig(config);

        sequence.addSequenceItem(new View(this),
                CATEGORYTEXTINFO, "קיבלתי");
        sequence.addSequenceItem(addTodo,
                "לאחר כתיבה של נושא למטלה, פשוט לחץ על כפתור ההוספה ורשימת המטלות תיווצר", "קיבלתי");

        sequence.start();

    }
}
