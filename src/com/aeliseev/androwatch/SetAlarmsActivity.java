package com.aeliseev.androwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.*;

import java.util.*;

/**
 * Created by AEliseev on 24.02.2014
 */
public class SetAlarmsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarms);

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);

        NumberPicker dnp = (NumberPicker) findViewById(R.id.durationNumberPicker);
        dnp.setMinValue(0);
        dnp.setMaxValue(180);
        dnp.setValue(2);

        NumberPicker inp = (NumberPicker) findViewById(R.id.intervalNumberPicker);
        inp.setMinValue(0);
        inp.setMaxValue(60);
        inp.setValue(1);

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_DISC);
        service.putExtra(PrefsService.ALARM_NUMBER_EXTRA_KEY, 1);
        service.putExtra(SingletonService.EXTRA_CALLBACK_KEY, new ResultReceiver(new Handler()) {

            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Prefs prefs = (Prefs) resultData.getSerializable(PrefsService.PREFS_EXTRA_KEY);

                ((CheckBox) findViewById(R.id.activeCheckBox)).setChecked(prefs.isActive());

                ((CheckBox) findViewById(R.id.mondayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.mon.name()));
                ((CheckBox) findViewById(R.id.tuesdayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.tue.name()));
                ((CheckBox) findViewById(R.id.wednesdayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.wed.name()));
                ((CheckBox) findViewById(R.id.thursdayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.thu.name()));
                ((CheckBox) findViewById(R.id.fridayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.fri.name()));
                ((CheckBox) findViewById(R.id.saturdayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.sat.name()));
                ((CheckBox) findViewById(R.id.sundayCheckBox)).setChecked(prefs.getDaysActive().contains(CalendarHelper.DayOfWeek.sun.name()));

                final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
                tp.setCurrentHour(prefs.getStartHour());
                tp.setCurrentMinute(prefs.getStartMinute());

                ((NumberPicker) findViewById(R.id.durationNumberPicker)).setValue(prefs.getDuration());
                ((NumberPicker) findViewById(R.id.intervalNumberPicker)).setValue(prefs.getInterval());
            }
        });

        startService(service);
    }

    public void savePreferences(View button) {

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.SAVE_PREFS_DISC);

        Prefs prefs = new Prefs();

        final CheckBox activeCheckbox = (CheckBox) findViewById(R.id.activeCheckBox);
        prefs.setActive(activeCheckbox.isChecked());

        final CheckBox mondayCheckbox       = (CheckBox) findViewById(R.id.mondayCheckBox);
        final CheckBox tuesdayCheckbox      = (CheckBox) findViewById(R.id.tuesdayCheckBox);
        final CheckBox wednesdayCheckbox    = (CheckBox) findViewById(R.id.wednesdayCheckBox);
        final CheckBox thursdayCheckbox     = (CheckBox) findViewById(R.id.thursdayCheckBox);
        final CheckBox fridayCheckbox       = (CheckBox) findViewById(R.id.fridayCheckBox);
        final CheckBox saturdayCheckbox     = (CheckBox) findViewById(R.id.saturdayCheckBox);
        final CheckBox sundayCheckbox       = (CheckBox) findViewById(R.id.sundayCheckBox);
        Set<String> daysActive = new HashSet<>();
        if (mondayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.mon.name());
        }
        if (tuesdayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.tue.name());
        }
        if (wednesdayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.wed.name());
        }
        if (thursdayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.thu.name());
        }
        if (fridayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.fri.name());
        }
        if (saturdayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.sat.name());
        }
        if (sundayCheckbox.isChecked()) {
            daysActive.add(CalendarHelper.DayOfWeek.sun.name());
        }
        prefs.setDaysActive(daysActive);

        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        prefs.setStartHour(tp.getCurrentHour());
        prefs.setStartMinute(tp.getCurrentMinute());

        final NumberPicker durationNP = (NumberPicker) findViewById(R.id.durationNumberPicker);
        prefs.setDuration(durationNP.getValue());

        final NumberPicker intervalNP = (NumberPicker) findViewById(R.id.intervalNumberPicker);
        prefs.setInterval(intervalNP.getValue());

        prefs.setAlarmNumber(1);
        service.putExtra(PrefsService.PREFS_EXTRA_KEY, prefs);

        startService(service);

        // close activity
        finish();
    }

    public void cancelPreferences(View button) {
        // close activity
        finish();
    }
}