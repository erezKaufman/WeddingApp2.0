package com.example.erez0_000.weddingapp.businessPage;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erez0_000.weddingapp.Login_pages.BusinessesInChart;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class SetAppointmentFragment extends DialogFragment implements View.OnClickListener {
    private TextView mail;
    private ListView phonelist;
    private Businesses curBusiness;
    private String chosenDate;
    private boolean isWinter;
    private final String min_price = "Min_Price";
    private final String max_price = "Max_Price";
    int newMinAmmount, newMaxAmmount;
    private ProgressDialog mprogressDialog;
    private  Button gotoTodos, addValueToChart, createCalendarEvent;


    public static SetAppointmentFragment newInstance() {

        Bundle args = new Bundle();

        SetAppointmentFragment fragment = new SetAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_appointment_frag, container, false);

        // set listeners to buttons
        gotoTodos = view.findViewById(R.id.gotoTodos);
        gotoTodos.setOnClickListener(this);
        addValueToChart = view.findViewById(R.id.addValueToChart);
        addValueToChart.setOnClickListener(this);
        if (User.thisUser == null) {
            gotoTodos.setVisibility(View.GONE);
            addValueToChart.setVisibility(View.GONE);
        }
        createCalendarEvent = view.findViewById(R.id.createCalendarEvent);
        createCalendarEvent.setOnClickListener(this);

        phonelist = (ListView) view.findViewById(R.id.listViewPhone);
        mail = (TextView) view.findViewById(R.id.mailInfo);
        mail.setText(curBusiness.getMail());
        view.findViewById(R.id.accept).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.phone_number_layout, curBusiness.getPhone());

        phonelist.setAdapter(arrayAdapter);

        phonelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + curBusiness.getPhone()[position]));
                SetAppointmentFragment.this.startActivity(intent);
            }
        });

        showManuel();
        return view;
    }

    public void setBusinessContact(Businesses currentBusiness, String date, boolean isWinter) {

        curBusiness = currentBusiness;
        chosenDate = date;
        this.isWinter = isWinter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoTodos:
                Intent i = new Intent(getActivity(), CategoriesActivity.class);

                i.putExtra("task name", String.format("לדבר עם %s לגבי %s",
                        curBusiness.getName(),curBusiness.getBusiness_type()));
                startActivity(i);
                break;
            case R.id.createCalendarEvent:
                createEvent();
                break;
            case R.id.addValueToChart:
                openPayChart(v);
                break;
            case R.id.cancel:
                getView().findViewById(R.id.add_amount_linealayout).setVisibility(getView().GONE);
                break;
            case R.id.accept:
                // TODO: 27/09/2018 update the user chart
                acceptAndUpdateAmmount();
                getView().findViewById(R.id.add_amount_linealayout).setVisibility(getView().GONE);
                break;
        }
    }

    private void openPayChart(View v) {
        User curUser = User.thisUser;
        if ((curUser.getBusinessInChart() != null) && isBusinessAlreadyInChart(curUser.getBusinessInChart(), curBusiness)) {
            Toast.makeText(getContext(), "לא ניתן להזמין את אותו עסק מספר פעמים", Toast.LENGTH_SHORT).show();
            return;
        }
        int min_val = 0;
        int max_val = 0;
        Map<String, Integer> curprices;
        if (isWinter) {
            curprices = curBusiness.getWinter_price();
        } else {
            curprices = curBusiness.getSummer_price();
        }

        min_val = curprices.get(min_price);
        max_val = curprices.get(max_price);

        IntTuple intTuple = intaddToChart(min_val, max_val);
        TextView lastAmmount = getView().findViewById(R.id.lastAmmount);
        lastAmmount.setText(String.format("בין %s לבין%s",
                curUser.getMinCurrentDestinedAmmount(), curUser.getMaxCurrentDestinedAmmount()));
        TextView newAmmount = getView().findViewById(R.id.curAmmount);
        newMinAmmount = curUser.getMinCurrentDestinedAmmount() + intTuple.min;
        newMaxAmmount = curUser.getMaxCurrentDestinedAmmount() + intTuple.max;
        newAmmount.setText(String.format("בין %s לבין%s", newMinAmmount, newMaxAmmount));
        getView().findViewById(R.id.add_amount_linealayout).setVisibility(getView().VISIBLE);
//        v.findViewById(R.id.add_amount_linealayout).setVisibility(v.VISIBLE);
    }

    public void acceptAndUpdateAmmount() {
        showProgressDialog();
        User.thisUser.setMaxCurrentDestinedAmmount(newMaxAmmount);
        User.thisUser.setMinCurrentDestinedAmmount(newMinAmmount);
        if (isWinter) {
            User.thisUser.addBusinessToChart(curBusiness,
                    curBusiness.getWinter_price().get(min_price),
                    curBusiness.getWinter_price().get(max_price));
        } else {
            User.thisUser.addBusinessToChart(curBusiness,
                    curBusiness.getSummer_price().get(min_price),
                    curBusiness.getSummer_price().get(max_price));
        }
        Database.getInstance().updateUser(User.thisUser, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                hideProgressDialog();
                Toast.makeText(getActivity(), R.string.Toast_added_business_to_chart, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(), "קרתה תקלה בעידכון השינויים, אנא נסו שנית", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * the method should contact the User current prices, add to them the price of the new business
     * and
     *
     * @param min_val the minimum value of the current business for the season
     * @param max_val the maximum value of the current business for the season
     * @return
     */
    private IntTuple intaddToChart(int min_val, int max_val) {
        // TODO: 27/09/2018 ADD USER HERE!!!!
        IntTuple returnTuple = new IntTuple();
        returnTuple.max = max_val;
        returnTuple.min = min_val;
        return returnTuple;
        //
    }

    private void createEvent() {
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(curFormater.parse(chosenDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, curBusiness.getAddress());
        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
        intent.putExtra("title", "פגישה עם " + curBusiness.getName());
        startActivity(intent);
    }

    public boolean isBusinessAlreadyInChart(ArrayList<BusinessesInChart> chartList, Businesses curBusiness) {
        for (BusinessesInChart bus : chartList) {
            if (bus.getCurBusiness().getName().equals(curBusiness.getName())) {
                return true;
            }
        }
        return false;
    }

    private static class IntTuple {
        int min;
        int max;
    }

    private void hideProgressDialog() {
        if (mprogressDialog != null && mprogressDialog.isShowing()) {
            mprogressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mprogressDialog == null) {
            mprogressDialog = new ProgressDialog(getContext());
            mprogressDialog.setCancelable(false);
            mprogressDialog.setMessage("מוסיף נתונים לגבי העסק...");
        }
        mprogressDialog.show();
    }


    private void showManuel() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), User.thisUser.getUsername()+"SETAPPOINTEMNT");


        sequence.setConfig(config);

        sequence.addSequenceItem(addValueToChart,
                "זהו כפתור גישה למידע על העסק", "קיבלתי");
        sequence.addSequenceItem(gotoTodos,
                "בכפתור זה תוכל לגשת ללוח השנה של בעל העסק ולראות תאריכים זמינים, ליצור קשר, ולקבוע פגישה", "קיבלתי");
        sequence.addSequenceItem(createCalendarEvent,
                "זהו כפתור גישה למידע על העסק", "קיבלתי");

        sequence.start();

    }

}

