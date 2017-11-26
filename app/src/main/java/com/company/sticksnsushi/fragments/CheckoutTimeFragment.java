package com.company.sticksnsushi.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
    long elapsedDays;
    RadioButton rbDelivery, rbPickup;
    TextView chooseDate, chooseTime, chooseRestaurant;
    boolean deliveryOption = true;
    Button nextScreenButton;


    DatePickerDialog.OnDateSetListener dateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_time, container, false);
        chooseDate = view.findViewById(R.id.dateText);
        chooseTime = view.findViewById(R.id.chooseTime);
        chooseRestaurant = view.findViewById(R.id.chooseRestaurant);
        rbDelivery = view.findViewById(R.id.rB_delivery);
        rbPickup = view.findViewById(R.id.rB_pickup);
        nextScreenButton = view.findViewById(R.id.nextScreenButtom);
        chooseTime.setVisibility(View.INVISIBLE);
        setDelivery();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rbPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickup();
            }
        });

        rbDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelivery();
            }
        });
            chooseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDelivery();
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
            chooseTime.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setDelivery();
                    showTimePopup(v);

                }
            }
            );

            chooseRestaurant.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setPickup();
                    showRestaurantPopup(v);

                }
            }
            );

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

                        getDaysDifference(date2, date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    month = month + 1;
                    String date = dayOfMonth + "/" + month + "/" + year;
                    if (elapsedDays < 0) {
                        Toast.makeText(getActivity(), "Der kan kun bestilles frem i tiden", Toast.LENGTH_LONG).show();
                    } else if (elapsedDays > 14) {
                        Toast.makeText(getActivity(), "Der kan maks bestilles 14 dage frem", Toast.LENGTH_LONG).show();
                    } else if (elapsedDays > -1 && elapsedDays < 15) {
                        chooseDate.setText(date);
                        chooseTime.setVisibility(View.VISIBLE);
                    }
                }
            };
        nextScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft;
                ft = getFragmentManager().beginTransaction();
                // XML files for animation are downloaded from internet
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(R.id.activity_checkout_frame, new InformationFragment());
                ft.commit();

                BottomNavigationItemView time = getActivity().findViewById(R.id.menu_chosen_time);
                time.getItemData().setChecked(false);

                BottomNavigationItemView payment = getActivity().findViewById(R.id.menu_information);
                payment.getItemData().setChecked(true);
            }
        });
    }

    public void showRestaurantPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.pickup_location_popup, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item) {
                chooseRestaurant.setText(item.getTitle());
                return true;
            }
        });


    }



    public void showTimePopup(View v) {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.time_popup, popup.getMenu());
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int hourFixed = hour+2;
            int i = 11;
            int minute = 0;
            int timeIntervalStartMinute = minute;
            while (i < 22){
                int timeIntervalStart = i;
                int timeIntervalEnd = i;
                int timeIntervalEndMinute = timeIntervalStartMinute + 15;
                if(timeIntervalEndMinute == 60){
                    i++;
                    timeIntervalEnd=++timeIntervalEnd;
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

                if(elapsedDays == 0) {
                    if(i < hourFixed){

                    }
                    else if(i > hourFixed){
                        popup.getMenu().add(timeInterval);
                    }
                }
                else if(elapsedDays != 0){
                    popup.getMenu().add(timeInterval);
                }

                timeIntervalStartMinute = timeIntervalStartMinute + 15;
                if(timeIntervalStartMinute == 60){
                    timeIntervalStartMinute = 0;
                }



            }
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                public boolean onMenuItemClick(MenuItem item) {
                    chooseTime.setText(item.getTitle());
                return true;
                }
            });


    }

    public void getDaysDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        elapsedDays = different / daysInMilli;

    }

    public void setDelivery(){
        rbPickup.setChecked(false);
        rbDelivery.setChecked(true);
        rbPickup.setTextColor(Color.parseColor("#666666"));
        chooseRestaurant.setTextColor(Color.parseColor("#666666"));
        chooseDate.setTextColor(Color.parseColor("#ffffff"));
        chooseTime.setTextColor(Color.parseColor("#ffffff"));
        rbDelivery.setTextColor(Color.parseColor("#ffffff"));
        deliveryOption = true;
    }

    public void setPickup(){
        rbPickup.setChecked(true);
        rbDelivery.setChecked(false);
        chooseDate.setTextColor(Color.parseColor("#666666"));
        chooseTime.setTextColor(Color.parseColor("#666666"));
        rbDelivery.setTextColor(Color.parseColor("#666666"));
        rbPickup.setTextColor(Color.parseColor("#ffffff"));
        chooseRestaurant.setTextColor(Color.parseColor("#ffffff"));
        deliveryOption = false;
    }
    }
