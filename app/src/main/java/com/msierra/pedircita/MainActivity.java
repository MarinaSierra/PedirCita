package com.msierra.pedircita;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DatePicker calendario;
    TimePicker fecha;
    EditText dni;
    Button reservar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get layout elements
        calendario = findViewById(R.id.calendar);
        fecha = findViewById(R.id.time);
        fecha.setIs24HourView(true);
        dni = findViewById(R.id.dni);
        reservar = findViewById(R.id.reserve);

        reservar.setOnClickListener(view -> {
            int day = calendario.getDayOfMonth();
            int month = calendario.getMonth();
            int year = calendario.getYear();
            int hour = fecha.getHour();
            int minute = fecha.getMinute();

            if (isDateOk(day, month, year)) {
                if (isTimeOk(hour, minute)) {
                    if (isDNIOk(dni.getText().toString())) {

                        Toast.makeText(this, "Su cita ha sido reservada", Toast.LENGTH_SHORT).show();
                    } else {
                        // muestra mensaje de dni no valido
                        Toast.makeText(this, "DNI no válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // muestra mensaje de dia no valido (solo de L-V)
                    Toast.makeText(this, "Hora no válida", Toast.LENGTH_SHORT).show();
                }
            } else {
                // muestra mensaje de dia no valido (solo de L-V)
                Toast.makeText(this, "Día de la semana no válido", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isDateOk(int day, int month, int year) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        return (dayOfWeek > 0 && dayOfWeek < 6);
    }


    private boolean isTimeOk(int hour, int minute) {
        boolean valid = false;

        if (hour >= 9 && hour <= 14) {
            if (hour == 14) {
                if (minute == 0) {
                    valid = true;
                }
            } else {
                valid = true;
            }
        }

        return valid;
    }


    private boolean isDNIOk(String dni) {
        boolean correctNumbers = false;
        boolean correctLetters;
        String dniNumeros = dni.substring(0, 7);

        for (int i = 0; i < dniNumeros.length(); i++) {
            int numero = Integer.parseInt(String.valueOf(dniNumeros.charAt(i)));

            correctNumbers= isNumber(numero);
        }

        char dniLetra = dni.charAt(8);

        correctLetters= isLetter(dniLetra);
        return correctNumbers && correctLetters;
    }

    private boolean isNumber (int n){
        return n >= 0 && n <= 9;
    }

    private boolean isLetter (char c){
        return c>=65 && c<=90;
    }
}