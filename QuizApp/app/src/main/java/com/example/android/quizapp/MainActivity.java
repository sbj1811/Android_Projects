package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean checkBox1_status;
    boolean checkBox2_status;
    boolean checkBox3_status;
    boolean checkBox4_status;
    boolean checkBox5_status;
    boolean checkBox6_status;
    boolean checkBox7_status;
    boolean checkBox8_status;
    boolean checkBox9_status;
    boolean radioButton1_status;
    boolean radioButton2_status;
    boolean radioButton3_status;
    boolean radioButton4_status;
    boolean radioButton5_status;
    int correct_hit = 0;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    EditText nameEditText;
    int object_null = 0;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Checkbox Click
     *
     * @param view
     */

    public void itemClicked1(View view) {
        checkBox1 = (CheckBox) view;
        checkBox1_status = checkBox1.isChecked();
    }

    public void itemClicked2(View view) {
        checkBox2 = (CheckBox) view;
        checkBox2_status = checkBox2.isChecked();
    }

    public void itemClicked3(View view) {
        checkBox3 = (CheckBox) view;
        checkBox3_status = checkBox3.isChecked();
    }

    public void itemClicked4(View view) {
        checkBox4 = (CheckBox) view;
        checkBox4_status = checkBox4.isChecked();
    }

    public void itemClicked5(View view) {
        checkBox5 = (CheckBox) view;
        checkBox5_status = checkBox5.isChecked();
    }

    public void itemClicked6(View view) {
        checkBox6 = (CheckBox) view;
        checkBox6_status = checkBox6.isChecked();
    }

    public void itemClicked7(View view) {
        checkBox7 = (CheckBox) view;
        checkBox7_status = checkBox7.isChecked();
    }

    public void itemClicked8(View view) {
        checkBox8 = (CheckBox) view;
        checkBox8_status = checkBox8.isChecked();
    }

    public void itemClicked9(View view) {
        checkBox9 = (CheckBox) view;
        checkBox9_status = checkBox9.isChecked();
    }

    /**
     * Radiobutton Click
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup_1);

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_button_1:
                if (checked) {
                    correct_hit += 1;
                    Log.i("QUESTION 4", "correct_hit:" + correct_hit);
                }
                break;
            case R.id.radio_button_2:
                if (checked) {
                    correct_hit -= 1;
                }
                break;
            case R.id.radio_button_3:
                if (checked) {
                    correct_hit -= 1;
                }
                break;
        }
    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup_2);

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_button_4:
                if (checked) {
                    correct_hit += 1;
                    Log.i("QUESTION 5", "correct_hit:" + correct_hit);
                }
                break;
            case R.id.radio_button_5:
                if (checked) {
                    correct_hit -= 1;
                }
                break;
        }
    }

    /**
     * Check Quiz entries
     */

    public void submitQuiz(View view) {
        nameEditText = (EditText) findViewById(R.id.name_field);
        name = nameEditText.getText().toString();
        Log.i("NAME", "name:" + name + "0000");
        if (checkBox1_status == true && checkBox2_status == false && checkBox3_status == false && checkBox4_status == false) {
            correct_hit += 1;
            Log.i("QUESTION 1", "correct_hit:" + correct_hit);
        }
        if (name.equals("Andy Rubin") || name.equals("Andrew Rubin")) {
            correct_hit += 1;
            Log.i("QUESTION 2", "correct_hit:" + correct_hit);
        }
        if (checkBox5_status == true && checkBox6_status == false && checkBox7_status == true && checkBox8_status == false && checkBox9_status == true) {
            correct_hit += 1;
            Log.i("QUESTION 3", "correct_hit:" + correct_hit);
        }
        if (correct_hit == 5) {
            if (correct_hit > 5){
                return;
            }
            Log.i("FINAL", "correct_hit:" + correct_hit);
            Toast.makeText(this, "Great Job ! You got all answers correct", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (correct_hit > 5){
                return;
            }
            Log.i("FINAL", "correct_hit:" + correct_hit);
            Toast.makeText(this, ("You got " + correct_hit + " out of 5 answers correct, Try Again"), Toast.LENGTH_SHORT).show();
            resetQuiz(view);
            return;
        }
    }

    /**
     * Resets checkbox if checked
     * @param checkBox
     */
    public void resetCheckbox(CheckBox checkBox){
        object_null += 1;
        if(checkBox == null){
            Log.i("FINAL", "Object: "+object_null+" null");
        }else if (checkBox.isChecked()){
            checkBox.toggle();
        }
    }

    /**
     * Reset Quiz
     */

    public void resetQuiz(View view) {
        correct_hit = 0;
        checkBox1_status = false;
        checkBox2_status = false;
        checkBox3_status = false;
        checkBox4_status = false;
        checkBox5_status = false;
        checkBox6_status = false;
        checkBox7_status = false;
        checkBox8_status = false;
        checkBox9_status = false;
        radioButton1_status = false;
        radioButton2_status = false;
        radioButton3_status = false;
        radioButton4_status = false;
        radioButton5_status = false;
        nameEditText.setText(null);
        resetCheckbox(checkBox1);
        resetCheckbox(checkBox2);
        resetCheckbox(checkBox3);
        resetCheckbox(checkBox4);
        resetCheckbox(checkBox5);
        resetCheckbox(checkBox6);
        resetCheckbox(checkBox7);
        resetCheckbox(checkBox8);
        resetCheckbox(checkBox9);
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        return;
    }

}
