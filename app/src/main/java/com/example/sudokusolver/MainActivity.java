package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final Map<String, TextView> textViewMap = new HashMap<>();

    private int[][] savedSudoku = new int[9][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTextViewMap();

        Switch allowGuessSwitch = findViewById(R.id.allowGuess);
        Switch stepByStepExecSwitch = findViewById(R.id.stepByStepExec);

        TextView noteText = findViewById(R.id.textNote);


        View.OnClickListener solveListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[][] sudoku = fetchSudokuData();

                SudokuSolver solver = new SudokuSolver(sudoku,textViewMap, allowGuessSwitch.isChecked(), stepByStepExecSwitch.isChecked(), noteText);
                solver.solveSudoku();
            }
        };
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(solveListner);
        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(solveListner);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedSudoku = fetchSudokuData();
            }
        });
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<9;i++) {
                    for(int j=0; j<9; j++) {
                        TextView textView = textViewMap.get("f"+i+""+j);
                        int data = savedSudoku[i][j];
                        if(data > 0) {
                            textView.setTextColor(Color.BLUE);
                            textView.setText(data+"");
                        } else {
                            textView.setText("");
                            textView.setTextColor(Color.WHITE);
                        }
                    }
                }
                noteText.setText("");
            }
        });

    }

    private int[][] fetchSudokuData() {
        int[][] sudokuData = new int[9][9];
        for(int i=0; i<9;i++) {
            for(int j=0; j<9; j++) {
                TextView textView = textViewMap.get("f"+i+""+j);
                textView.setTextColor(Color.WHITE);
                final CharSequence data = textView.getText();
                if(data != null && data.length()>0) {
                    textView.setTextColor(Color.BLUE);
                    System.out.println("Data is set : "+i+":"+j+"="+data);
                    sudokuData[i][j] = Integer.parseInt(data.toString());
                }
            }
        }
        return sudokuData;
    }

    private void setTextViewMap() {
        textViewMap.put("f00", findViewById(R.id.f00));
        textViewMap.put("f01", findViewById(R.id.f01));
        textViewMap.put("f02", findViewById(R.id.f02));
        textViewMap.put("f03", findViewById(R.id.f03));
        textViewMap.put("f04", findViewById(R.id.f04));
        textViewMap.put("f05", findViewById(R.id.f05));
        textViewMap.put("f06", findViewById(R.id.f06));
        textViewMap.put("f07", findViewById(R.id.f07));
        textViewMap.put("f08", findViewById(R.id.f08));

        textViewMap.put("f10", findViewById(R.id.f10));
        textViewMap.put("f11", findViewById(R.id.f11));
        textViewMap.put("f12", findViewById(R.id.f12));
        textViewMap.put("f13", findViewById(R.id.f13));
        textViewMap.put("f14", findViewById(R.id.f14));
        textViewMap.put("f15", findViewById(R.id.f15));
        textViewMap.put("f16", findViewById(R.id.f16));
        textViewMap.put("f17", findViewById(R.id.f17));
        textViewMap.put("f18", findViewById(R.id.f18));

        textViewMap.put("f20", findViewById(R.id.f20));
        textViewMap.put("f21", findViewById(R.id.f21));
        textViewMap.put("f22", findViewById(R.id.f22));
        textViewMap.put("f23", findViewById(R.id.f23));
        textViewMap.put("f24", findViewById(R.id.f24));
        textViewMap.put("f25", findViewById(R.id.f25));
        textViewMap.put("f26", findViewById(R.id.f26));
        textViewMap.put("f27", findViewById(R.id.f27));
        textViewMap.put("f28", findViewById(R.id.f28));

        textViewMap.put("f30", findViewById(R.id.f30));
        textViewMap.put("f31", findViewById(R.id.f31));
        textViewMap.put("f32", findViewById(R.id.f32));
        textViewMap.put("f33", findViewById(R.id.f33));
        textViewMap.put("f34", findViewById(R.id.f34));
        textViewMap.put("f35", findViewById(R.id.f35));
        textViewMap.put("f36", findViewById(R.id.f36));
        textViewMap.put("f37", findViewById(R.id.f37));
        textViewMap.put("f38", findViewById(R.id.f38));

        textViewMap.put("f40", findViewById(R.id.f40));
        textViewMap.put("f41", findViewById(R.id.f41));
        textViewMap.put("f42", findViewById(R.id.f42));
        textViewMap.put("f43", findViewById(R.id.f43));
        textViewMap.put("f44", findViewById(R.id.f44));
        textViewMap.put("f45", findViewById(R.id.f45));
        textViewMap.put("f46", findViewById(R.id.f46));
        textViewMap.put("f47", findViewById(R.id.f47));
        textViewMap.put("f48", findViewById(R.id.f48));

        textViewMap.put("f50", findViewById(R.id.f50));
        textViewMap.put("f51", findViewById(R.id.f51));
        textViewMap.put("f52", findViewById(R.id.f52));
        textViewMap.put("f53", findViewById(R.id.f53));
        textViewMap.put("f54", findViewById(R.id.f54));
        textViewMap.put("f55", findViewById(R.id.f55));
        textViewMap.put("f56", findViewById(R.id.f56));
        textViewMap.put("f57", findViewById(R.id.f57));
        textViewMap.put("f58", findViewById(R.id.f58));

        textViewMap.put("f60", findViewById(R.id.f60));
        textViewMap.put("f61", findViewById(R.id.f61));
        textViewMap.put("f62", findViewById(R.id.f62));
        textViewMap.put("f63", findViewById(R.id.f63));
        textViewMap.put("f64", findViewById(R.id.f64));
        textViewMap.put("f65", findViewById(R.id.f65));
        textViewMap.put("f66", findViewById(R.id.f66));
        textViewMap.put("f67", findViewById(R.id.f67));
        textViewMap.put("f68", findViewById(R.id.f68));

        textViewMap.put("f70", findViewById(R.id.f70));
        textViewMap.put("f71", findViewById(R.id.f71));
        textViewMap.put("f72", findViewById(R.id.f72));
        textViewMap.put("f73", findViewById(R.id.f73));
        textViewMap.put("f74", findViewById(R.id.f74));
        textViewMap.put("f75", findViewById(R.id.f75));
        textViewMap.put("f76", findViewById(R.id.f76));
        textViewMap.put("f77", findViewById(R.id.f77));
        textViewMap.put("f78", findViewById(R.id.f78));

        textViewMap.put("f80", findViewById(R.id.f80));
        textViewMap.put("f81", findViewById(R.id.f81));
        textViewMap.put("f82", findViewById(R.id.f82));
        textViewMap.put("f83", findViewById(R.id.f83));
        textViewMap.put("f84", findViewById(R.id.f84));
        textViewMap.put("f85", findViewById(R.id.f85));
        textViewMap.put("f86", findViewById(R.id.f86));
        textViewMap.put("f87", findViewById(R.id.f87));
        textViewMap.put("f88", findViewById(R.id.f88));
    }

    private void printSudoku(int[][] sudoku) {
        /*for(int i=0; i<9; i++) {
            for(int j=0; j<9;j++) {
                textViewMap.get("f"+i+""+j).set
            }
        }*/
    }

}