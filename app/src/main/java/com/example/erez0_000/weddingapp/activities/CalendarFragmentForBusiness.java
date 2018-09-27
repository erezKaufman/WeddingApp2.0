package com.example.erez0_000.weddingapp.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.erez0_000.weddingapp.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragmentForBusiness extends Fragment implements View.OnClickListener {

    CalendarView calendarView;
    long[] epochDates;
    ClendarDialogFragmentListner listner;
    TextView curDate;
    Button setDateBtn;
    CaldroidFragment caldroidFragment;
    final int winderBegins = 4;
    final int winterEnds =9;
    final int startMonth = 1;
    final int endMonth = 30;
    boolean isWinter;
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // fill the view with the fragment layout
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        isWinter = false;
        // START find id of objects in the layout
        curDate = view.findViewById(R.id.show_date);

        setDateBtn = view.findViewById(R.id.set_date_btn);
        setDateBtn.setOnClickListener(this);
        setDateBtn.setEnabled(false);
        // END find id of objects in the layout

        // START Define the caldroid calendar
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();
        // END Define the caldroid calendar
        updateOcupiedDatesBackground(epochDates);

        // set listner for the caldroid when clicked on specific date
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                boolean isOccupied = false;
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Date cuTime = Calendar.getInstance().getTime();
                cal1.setTime(date);
                for (long curdate : epochDates) {
                    cal2.setTime(run(curdate));
                    boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

                    if (sameDay && cuTime.compareTo(cal2.getTime())<=0) {
                        isOccupied = true;
                        break;
                    }
                }
                if (isOccupied) {
                    setDateBtn.setEnabled(false);
                    curDate.setText("התאריך תפוס, אנא בחר תאריך אחר");
                } else {
                    isWinter =  (cal1.get(Calendar.MONTH) >= winderBegins)&&
                            (cal1.get(Calendar.MONTH) <= winterEnds);
                    // show date
                    String day          = (String) DateFormat.format("dd",   date); // 20
                    String monthNumber  = (String) DateFormat.format("MM",   date); // 06
                    String year         = (String) DateFormat.format("yyyy", date); // 2013
                    curDate.setText(day+"/"+monthNumber+"/"+year);
                    setDateBtn.setEnabled(true);
                }
            }
        });


        return view;

    }

    public Date run(long time){
        return  new Date(time * 1000);
    }
    public void setListner(CalendarFragmentForBusiness.ClendarDialogFragmentListner dlgFrag,
                           long[] dateList) {
        epochDates = dateList;
        listner = dlgFrag;

    }

    public void updateOcupiedDatesBackground(long[] datelist){
        ColorDrawable redBackground = new ColorDrawable(0xFFFF6666);
        Date cuTime = Calendar.getInstance().getTime();
        for (long date: datelist){
            Date newDate = run(date);
            if (cuTime.compareTo(newDate)<=0){
                caldroidFragment.setBackgroundDrawableForDate(redBackground, newDate);
            }

        }
        caldroidFragment.refreshView();

    }

    @Override
    public void onClick(View v) {

        listner.onDateClick(curDate.toString(),isWinter);

    }

    public static CalendarFragmentForBusiness newInstance() {

        CalendarFragmentForBusiness calendarFrag = new CalendarFragmentForBusiness();
        Bundle args = new Bundle();
        calendarFrag.setArguments(args);
        return calendarFrag;
    }


    public interface ClendarDialogFragmentListner {
        void onDateClick(String message, boolean isWinter);
    }
}

