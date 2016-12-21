package com.example.oktaysadoglu.markdata.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.activities.MainActivity;

/**
 * Created by oktaysadoglu on 21/12/2016.
 */

public class SettingsFragment extends Fragment {

    private View settingsFragmentView;

    private NumberPicker numberPicker;

    private Button changeTextSizeButton;

    private Button dialogOkayButton,dialogCancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setSettingsFragmentView(inflater.inflate(R.layout.fragment_settings,container,false));

        bindViews();

        configureChangeTextSizeButton();

        return getSettingsFragmentView();
    }

    private void bindViews(){

        setChangeTextSizeButton((Button) getSettingsFragmentView().findViewById(R.id.fragment_settings_change_text_size_button));

    }

    private void configureChangeTextSizeButton(){

        getChangeTextSizeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogToChangeTextSize();
            }
        });

    }

    private void createDialogToChangeTextSize(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_change_text_size);
        dialog.setTitle(getString(R.string.settings_fragment_dialog_title));

        bindDialogComponents(dialog);

        dialog.show();

    }

    private void bindDialogComponents(Dialog dialog){

        setNumberPicker((NumberPicker) dialog.findViewById(R.id.dialog_nubmer_picker_to_determine_text_size));

        setDialogCancelButton((Button) dialog.findViewById(R.id.dialog_change_text_size_cancel_button));

        setDialogOkayButton((Button) dialog.findViewById(R.id.dialog_change_text_size_okay_button));

        configureDialogButtons(dialog);

        configureNumberPicker();

    }

    private void configureNumberPicker(){

        getNumberPicker().setMaxValue(36);
        getNumberPicker().setMinValue(16);
        getNumberPicker().setValue(MainActivity.news_text_size);

    }

    private void configureDialogButtons(final Dialog dialog){

        getDialogOkayButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.news_text_size = getNumberPicker().getValue();
                dialog.dismiss();
            }
        });

        getDialogCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public NumberPicker getNumberPicker() {
        return numberPicker;
    }

    public void setNumberPicker(NumberPicker numberPicker) {
        this.numberPicker = numberPicker;
    }

    public View getSettingsFragmentView() {
        return settingsFragmentView;
    }

    public void setSettingsFragmentView(View settingsFragmentView) {
        this.settingsFragmentView = settingsFragmentView;
    }

    public Button getChangeTextSizeButton() {
        return changeTextSizeButton;
    }

    public void setChangeTextSizeButton(Button changeTextSizeButton) {
        this.changeTextSizeButton = changeTextSizeButton;
    }

    public Button getDialogOkayButton() {
        return dialogOkayButton;
    }

    public void setDialogOkayButton(Button dialogOkayButton) {
        this.dialogOkayButton = dialogOkayButton;
    }

    public Button getDialogCancelButton() {
        return dialogCancelButton;
    }

    public void setDialogCancelButton(Button dialogCancelButton) {
        this.dialogCancelButton = dialogCancelButton;
    }
}
