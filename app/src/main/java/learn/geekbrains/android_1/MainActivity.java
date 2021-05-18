package learn.geekbrains.android_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private static final int CALC_MODE = 1;
    private static final int TEXT_MODE = 0;

    private EditText editTextLeft, editTextRight;
    private CheckBox checkBoxConcat, checkBoxInvert;
    private RadioButton radioButtonSum, radioButtonDiff;
    private ToggleButton toggleButtonMode;
    private Button buttonExecute;
    private TextView textViewResult;
    private SwitchMaterial switchMaterialCalendar;
    private CalendarView calendarView;

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode = TEXT_MODE;

        init();
        calendarView.setVisibility(View.INVISIBLE);

        toggleButtonMode.setOnCheckedChangeListener((button, b) -> changeMode());
        switchMaterialCalendar.setOnCheckedChangeListener(
                (button, b) -> changeCalendarVisibility(b));
        buttonExecute.setOnClickListener(button -> executeOperationWithInputData());
    }

    private void init() {
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
        if (toggleButtonMode.isChecked()) mode = CALC_MODE;
        else mode = TEXT_MODE;
        switch (mode) {
            case TEXT_MODE:
                checkBoxConcat.setEnabled(true);
                checkBoxInvert.setEnabled(true);
                radioButtonSum.setEnabled(false);
                radioButtonDiff.setEnabled(false);
                editTextLeft.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextRight.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case CALC_MODE:
                checkBoxConcat.setEnabled(false);
                checkBoxInvert.setEnabled(false);
                radioButtonSum.setEnabled(true);
                radioButtonDiff.setEnabled(true);
                editTextLeft.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextRight.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
        editTextLeft.setText("");
        editTextRight.setText("");
        textViewResult.setText("");
    }

    private void changeCalendarVisibility(boolean state) {
        if (state) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.INVISIBLE);
        }
    }

    private void executeOperationWithInputData() {
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
            try {
                if (!text1.isEmpty()) num1 = Integer.parseInt(text1);
                if (!text2.isEmpty()) num2 = Integer.parseInt(text2);
            } catch (NumberFormatException e) {
                Log.d("Input", "Error input");
            }
            if (radioButtonSum.isChecked()) {
                result = num1 + num2;
            } else if (radioButtonDiff.isChecked()) {
                result = num1 - num2;
            }
            textViewResult.setText(String.valueOf(result));
        }
    }
}