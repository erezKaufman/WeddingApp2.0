package com.example.erez0_000.weddingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.erez0_000.weddingapp.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

public class CalendarFragmentForrBusiness extends Fragment implements View.OnClickListener {

    CalendarView calendarView;
    int[] epochDates;
    ClendarDialogFragmentListner listner;
    TextView curDate;
    Button setDateBtn;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // fill the view with the fragment layout
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        // START find id of objects in the layout
        curDate = view.findViewById(R.id.show_date);

        setDateBtn = view.findViewById(R.id.set_date_btn);
        setDateBtn.setOnClickListener(this);
        setDateBtn.setEnabled(false);
        // END find id of objects in the layout

        // START Define the caldroid calendar
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();
        // END Define the caldroid calendar


        // set listner for the caldroid when clicked on specific date
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                boolean isOccupied = false;
                for (int curdate : epochDates) {
                    if (date.getTime() == curdate) {
                        isOccupied = true;
                        break;
                    }
                }
                if (isOccupied) {
                    setDateBtn.setEnabled(false);
                    curDate.setText("התאריך תפוס, אנא בחר תאריך אחר");
                } else {
                    curDate.setText(date.toString());
                    setDateBtn.setEnabled(true);
                }
            }
        });

        return view;

    }

    public void setListner(CalendarFragmentForrBusiness.ClendarDialogFragmentListner dlgFrag,
                           int[] dateList) {
        epochDates = dateList;
        listner = dlgFrag;

    }



    @Override
    public void onClick(View v) {
        listner.onDateClick(curDate.toString());
    }

    public static Fragment newInstance() {
        CalendarFragmentForrBusiness calendarFrag = new CalendarFragmentForrBusiness();
        Bundle args = new Bundle();
        calendarFrag.setArguments(args);
        return calendarFrag;
    }


    public interface ClendarDialogFragmentListner {
        void onDateClick(String message);
    }
}

