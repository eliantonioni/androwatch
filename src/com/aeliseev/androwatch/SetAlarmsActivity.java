package com.aeliseev.androwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
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

        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SeekBar vsb = (SeekBar) findViewById(R.id.volumeSeekBar);
        vsb.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        ((CheckBox) findViewById(R.id.systemVolumeCheckBox))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.volumeSeekBar).setEnabled(!isChecked);
            }
        });

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_DISC);
        service.putExtra(PrefsService.ALARM_NUMBER_EXTRA_KEY, 1);
        service.putExtra(SingletonService.EXTRA_CALLBACK_KEY, new ResultReceiver(new Handler()) {

            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Prefs prefs = (Prefs) resultData.getSerializable(PrefsService.PREFS_EXTRA_KEY);

                ((ToggleButton) findViewById(R.id.activeToggleButton)).setChecked(prefs.isActive());

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

                ((SeekBar) findViewById(R.id.volumeSeekBar)).setProgress(prefs.getVolume());
                ((CheckBox) findViewById(R.id.systemVolumeCheckBox)).setChecked(prefs.isSystemVolume());
            }
        });

        startService(service);
    }

    public void savePreferences(View button) {

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.SAVE_PREFS_DISC);

        Prefs prefs = new Prefs();

        final ToggleButton activeCheckbox = (ToggleButton) findViewById(R.id.activeToggleButton);
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

    public void setActive(View button) {

        final ToggleButton activeCheckbox = (ToggleButton) findViewById(R.id.activeToggleButton);

        findViewById(R.id.mondayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.tuesdayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.wednesdayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.thursdayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.fridayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.saturdayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.sundayCheckBox).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.timePicker).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.durationNumberPicker).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.intervalNumberPicker).setEnabled(activeCheckbox.isChecked());

        boolean sv = ((CheckBox) findViewById(R.id.systemVolumeCheckBox)).isChecked();
        findViewById(R.id.volumeSeekBar).setEnabled(activeCheckbox.isChecked() && !sv);
        findViewById(R.id.systemVolumeCheckBox).setEnabled(activeCheckbox.isChecked());
    }
}