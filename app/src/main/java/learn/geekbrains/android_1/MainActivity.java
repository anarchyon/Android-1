package learn.geekbrains.android_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private static final boolean CALC_MODE = true;
    private static final boolean TEXT_MODE = false;

    private EditText editTextLeft, editTextRight;
    private CheckBox checkBoxConcat, checkBoxInvert;
    private RadioButton radioButtonSum, radioButtonDiff;
    private ToggleButton toggleButtonMode;
    private Button buttonExecute;
    private TextView textViewResult;
    private SwitchMaterial switchMaterialCalendar;
    private CalendarView calendarView;

    private boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode = TEXT_MODE;

        findView();
        calendarView.setVisibility(View.INVISIBLE);

        toggleButtonMode.setOnCheckedChangeListener((button, b) -> changeMode());
        switchMaterialCalendar.setOnCheckedChangeListener(
                (button, b) -> changeCalendarVisibility(b));
        buttonExecute.setOnClickListener(button -> execute());
    }

    private void findView() {
        editTextLeft = findViewById(R.id.editTextLeft);
        editTextRight = findViewById(R.id.editTextRight);
        checkBoxConcat = findViewById(R.id.checkbox_concat);
        checkBoxInvert = findViewById(R.id.checkbox_invert);
        radioButtonSum = findViewById(R.id.radio_sum);
        radioButtonDiff = findViewById(R.id.radio_diff);
        toggleButtonMode = findViewById(R.id.toggle_mode);
        buttonExecute = findViewById(R.id.button_execute);
        textViewResult = findViewById(R.id.text_result);
        switchMaterialCalendar = findViewById(R.id.switch_calendar);
        calendarView = findViewById(R.id.calendar);
    }

    private void changeMode() {
        checkBoxConcat.setEnabled(!checkBoxConcat.isEnabled());
        checkBoxInvert.setEnabled(!checkBoxInvert.isEnabled());
        radioButtonSum.setEnabled(!radioButtonSum.isEnabled());
        radioButtonDiff.setEnabled(!radioButtonDiff.isEnabled());
        mode = !mode;
        editTextLeft.setText("");
        editTextRight.setText("");
        textViewResult.setText("");
        if (mode == CALC_MODE) {
            editTextLeft.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextRight.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (mode == TEXT_MODE) {
            editTextLeft.setInputType(InputType.TYPE_CLASS_TEXT);
            editTextRight.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    private void changeCalendarVisibility(boolean state) {
        if (state) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.INVISIBLE);
        }
    }

    private void execute() {
        if (mode == TEXT_MODE) {
            StringBuilder text = new StringBuilder(editTextLeft.getText());
            if (checkBoxConcat.isChecked()) {
                text.append(editTextRight.getText());
            }
            if (checkBoxInvert.isChecked()) {
                text.reverse();
            }
            textViewResult.setText(text);
        } else if (mode == CALC_MODE) {
            double result = 0;
            double num1 = 0;
            double num2 = 0;
            String text1 = String.valueOf(editTextLeft.getText());
            String text2 = String.valueOf(editTextRight.getText());
            if (!text1.equals("")) num1 = Integer.parseInt(text1);
            if (!text2.equals("")) num2 = Integer.parseInt(text2);
            if (radioButtonSum.isChecked()) {
                result = num1 + num2;
            } else if (radioButtonDiff.isChecked()) {
                result = num1 - num2;
            }
            textViewResult.setText(String.valueOf(result));
        }
    }
}