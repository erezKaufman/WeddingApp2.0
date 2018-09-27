package com.example.erez0_000.weddingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class SetAppointmentFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener{
    private Button gotoTodos, addEventToCalendar, addToCalcChart;
    private TextView title,mail;
    private ListView phonelist,maillist;
    long[] phoneNumber;
    private Businesses curBusiness;
    private String mailStr,chosenDate;
    private boolean isWinter;
    private final String min_price = "Min_Price";
    private final String max_price= "Max_Price";
    public static SetAppointmentFragment newInstance() {

        Bundle args = new Bundle();

        SetAppointmentFragment fragment = new SetAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_appointment_frag,container, false);

        // set listeners to buttons
        view.findViewById(R.id.gotoTodos).setOnClickListener(this);
        view.findViewById(R.id.createCalendarEvent).setOnClickListener(this);
        view.findViewById(R.id.addValueToChart).setOnClickListener(this);
        phonelist =(ListView)view.findViewById(R.id.listViewPhone);
        mail =(TextView)view.findViewById(R.id.mailInfo);
        mail.setText(curBusiness.getMail());
        view.findViewById(R.id.accept).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
//        ArrayAdapter<Integer> arrayAdapter =
//                new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, phoneNumber);
        return view;
    }

    public void setBusinessContact(Businesses currentBusiness,String date,boolean isWinter){

        curBusiness= currentBusiness ;
        chosenDate = date;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotoTodos:
                Intent i = new Intent(getActivity(), CategoriesActivity.class);
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
                getView().findViewById(R.id.add_amount_linealayout).setVisibility(getView().GONE);
                Toast.makeText(getActivity(), "העסק התווסף לרשימת בעלי העסקים שבחרתם עבור החתונה שלכם.\n שקלול המחיר התווסף גם הוא לחישוב הכולל ", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void openPayChart(View v) {
        // TODO need to know how to get user's chart and add it to the calculations
        int min_val = 0;
        int max_val = 0;
        if (isWinter){
            Map<String,Integer> curprices = curBusiness.getWinter_price();
            min_val = curprices.get(min_price);
            max_val = curprices.get(max_price);
        }else{
            Map<String,Integer> curprices = curBusiness.getSummer_price();
            min_val = curprices.get(min_price);
            max_val = curprices.get(max_price);
        }

        IntTuple intTuple = intaddToChart(min_val,max_val);
        TextView lastAmmount = getView().findViewById(R.id.lastAmmount);
        lastAmmount.setText("0"); // TODO: 27/09/2018 add user last ammount here
        TextView newAmmount = getView().findViewById(R.id.curAmmount);
        newAmmount.setText(String.format("בין %s לבין%s",intTuple.min,intTuple.max));
        getView().findViewById(R.id.add_amount_linealayout).setVisibility(getView().VISIBLE);
//        v.findViewById(R.id.add_amount_linealayout).setVisibility(v.VISIBLE);
    }

    public void acceptAmmount(View v){

    }

    /**
     * the method should contact the User current prices, add to them the price of the new business
     * and
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
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "פגישה עם "+curBusiness.getName());
        startActivity   (intent);
    }
    private static class IntTuple{
        int min;
        int max;
    }

}

