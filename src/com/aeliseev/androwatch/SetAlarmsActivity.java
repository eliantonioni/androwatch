package com.aeliseev.androwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.*;

/**
 * Created by AEliseev on 24.02.2014
 */
public class SetAlarmsActivity extends Activity {

    private static final int CHOOSE_RINGTONE_SUB_ACTIVITY_CODE = 10;
    private static final String SILENT_RINGTONE_TEXT =  "Не задано";

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

        String[] data = {"Мужской русский", "Женский русский"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.voiceSpinner);
        spinner.setAdapter(adapter);

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_DISC);
        service.putExtra(PrefsService.PREFS_EXTRA_KEY, new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER));

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

                ((Spinner) findViewById(R.id.voiceSpinner)).setSelection(prefs.getVoiceNumber());

                ((TextView) findViewById(R.id.ringtoneURIText)).setText(prefs.getRingtoneURI());
                ((TextView) findViewById(R.id.ringtoneTitle)).setText(
                        getAlarmNameByURI(Uri.parse(prefs.getRingtoneURI()))
                );

                setActive(null);
            }
        });

        startService(service);
    }

    public void savePreferences(View button) {

        Intent service = new Intent(getApplicationContext(), PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.SAVE_PREFS_DISC);

        Prefs prefs = new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER);

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

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        prefs.setStartHour(tp.getCurrentHour());
        prefs.setStartMinute(tp.getCurrentMinute());

        NumberPicker durationNP = (NumberPicker) findViewById(R.id.durationNumberPicker);
        prefs.setDuration(durationNP.getValue());

        NumberPicker intervalNP = (NumberPicker) findViewById(R.id.intervalNumberPicker);
        prefs.setInterval(intervalNP.getValue());

        SeekBar sb = (SeekBar) findViewById(R.id.volumeSeekBar);
        prefs.setVolume(sb.getProgress());

        CheckBox svCB = (CheckBox) findViewById(R.id.systemVolumeCheckBox);
        prefs.setSystemVolume(svCB.isChecked());

        Spinner vS = (Spinner) findViewById(R.id.voiceSpinner);
        prefs.setVoiceNumber(vS.getSelectedItemPosition());

        TextView ringtoneURITV = (TextView) findViewById(R.id.ringtoneURIText);
        prefs.setRingtoneURI(ringtoneURITV.getText() != null ? ringtoneURITV.getText().toString() : "");

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
        findViewById(R.id.voiceSpinner).setEnabled(activeCheckbox.isChecked());
        findViewById(R.id.ringtoneButton).setEnabled(activeCheckbox.isChecked());

        boolean sv = ((CheckBox) findViewById(R.id.systemVolumeCheckBox)).isChecked();
        findViewById(R.id.volumeSeekBar).setEnabled(activeCheckbox.isChecked() && !sv);
        findViewById(R.id.systemVolumeCheckBox).setEnabled(activeCheckbox.isChecked());
    }

    public void chooseAlarmFile(View button) {

        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        startActivityForResult(intent, CHOOSE_RINGTONE_SUB_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == CHOOSE_RINGTONE_SUB_ACTIVITY_CODE) {

            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {

                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Ringtone URI=" + uri);
                ((TextView) findViewById(R.id.ringtoneTitle)).setText(getAlarmNameByURI(uri));
                ((TextView) findViewById(R.id.ringtoneURIText)).setText(uri.toString());
            }
            else { // no sounds ringtone

                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Silent ringtone");
                ((TextView) findViewById(R.id.ringtoneTitle)).setText(SILENT_RINGTONE_TEXT);
                ((TextView) findViewById(R.id.ringtoneURIText)).setText("");
            }
        }
    }

    private String getAlarmNameByURI(Uri uri) {

        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext(), i++) {

            if (uri != null && uri.equals(manager.getRingtoneUri(i))) {
                return cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            }
        }

        return SILENT_RINGTONE_TEXT;
    }
}