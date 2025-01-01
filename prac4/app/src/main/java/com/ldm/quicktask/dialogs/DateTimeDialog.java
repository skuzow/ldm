package com.ldm.quicktask.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import com.ldm.quicktask.R;

import java.util.Calendar;
import java.util.Objects;

public class DateTimeDialog {

    public static void editDate(Context context, TextView dateDisplay, int[] date) {
        final Calendar c = Calendar.getInstance();
        date[0] = c.get(Calendar.DAY_OF_MONTH);
        date[1] = c.get(Calendar.MONTH);
        date[2] = c.get(Calendar.YEAR);

        ContextThemeWrapper newContext = new ContextThemeWrapper(context, R.style.Theme_QuickTask_DatePickerDialog);
        DatePickerDialog dialog = new DatePickerDialog(newContext,
                (view1, year, month, dayOfMonth) -> {
                    date[0] = dayOfMonth;
                    date[1] = month;
                    date[2] = year;
                    syncDisplayDate(dateDisplay, date);
                },
                date[2], date[1], date[0]
        );

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public static void syncDisplayDate(TextView dateDisplay, int[] date) {
        if (date[1] < 9) dateDisplay.setText(date[0] + "/0" + (date[1] + 1) + "/" + date[2]);
        else dateDisplay.setText(date[0] + "/" + (date[1] + 1) + "/" + date[2]);
    }

    public static void editTime(Context context, TextView hourDisplay, int[] time) {
        final Calendar c = Calendar.getInstance();
        time[0] = c.get(Calendar.HOUR_OF_DAY);
        time[1] = c.get(Calendar.MINUTE);

        ContextThemeWrapper newContext = new ContextThemeWrapper(context, R.style.Theme_QuickTask_Dialog);
        TimePickerDialog tmd = new TimePickerDialog(newContext,
                (view1, hourOfDay, minute) -> {
                    time[0] = hourOfDay;
                    time[1] = minute;

                    syncDisplayTime(hourDisplay, time);
                },
                time[0], time[1], true
        );

        tmd.show();
    }

    @SuppressLint("SetTextI18n")
    public static void syncDisplayTime(TextView hourDisplay, int[] time) {
        if (time[1] < 10) hourDisplay.setText(time[0] + ":0" + time[1]);
        else hourDisplay.setText(time[0] + ":" + time[1]);
    }
}
