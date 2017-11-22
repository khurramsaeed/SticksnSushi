package com.company.sticksnsushi.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.company.sticksnsushi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Khurram Saeed Malik on 20/11/2017.
 */

public class CheckoutTimeFragment extends Fragment {
    TextView displayDate, chooseTime;


    DatePickerDialog.OnDateSetListener dateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_time, container, false);
        displayDate = view.findViewById(R.id.dateText);
        chooseTime = view.findViewById(R.id.chooseTime);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                            year, month, day);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
            }

        }
        );
        chooseTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                    showPopUp(v);
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: " + year + "/" + month + "/" + dayOfMonth);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
                try {
                    Date date1 = simpleDateFormat.parse(dayOfMonth + "/" + month + "/" + year);
                    String currenDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR);
                    Date date2 = simpleDateFormat.parse(currenDate);

                    printDifference(date2, date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                displayDate.setText(date);
            }
        };
    }

        public void showPopUp(View v) {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.time_popup, popup.getMenu());
            int i = 11;
            int minute = 0;
            int timeIntervalStartMinute = minute;
            while (i < 22){
                int timeIntervalStart = i;
                int timeIntervalEnd = i;
                int timeIntervalEndMinute = timeIntervalStartMinute + 15;
                if(timeIntervalEndMinute == 60){
                    i++;
                    timeIntervalEnd=i++;
                    timeIntervalEndMinute=0;
                }
                String timeIntervalStartMinuteString = Integer.toString(timeIntervalStartMinute);
                if (timeIntervalStartMinuteString.equals("0")){
                    timeIntervalStartMinuteString = "00";
                }
                String timeIntervalEndMinuteString = Integer.toString(timeIntervalEndMinute);
                if (timeIntervalEndMinuteString.equals("0")){
                    timeIntervalEndMinuteString = "00";
                }
                String timeInterval = timeIntervalStart+":"+timeIntervalStartMinuteString + " - " + timeIntervalEnd
                + ":" + timeIntervalEndMinuteString;
                popup.getMenu().add(timeInterval);
                timeIntervalStartMinute = timeIntervalStartMinute + 15;
                if(timeIntervalStartMinute == 60){
                    timeIntervalStartMinute = 0;
                }



            }
            popup.show();

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

}
