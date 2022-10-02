package com.example.sudokusolver;

import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SudokuSolver {

    private static final int size = 9;

    private int[][] sudoku = new int[size][size];

    Map<String, TextView> textViewMap;

    private final Map<Integer, List<Integer>> possibleNumbersMap = new HashMap<>();

	private boolean allowGuessing = false;

	private boolean stepByStepExec = false;

	private TextView noteText;

    public SudokuSolver(int[][] sudoku, Map<String, TextView> textViewMap, boolean allowGuessing, boolean stepByStepExec, TextView noteText) {
        this.sudoku = sudoku;
        this.textViewMap = textViewMap;
		this.allowGuessing = allowGuessing;
		this.stepByStepExec = stepByStepExec;
		this.noteText = noteText;
    }



   /* public static void main(String[] args) {
        SudokuSolver obj = new SudokuSolver();
        obj.printSudoku();
        System.out.println("=======================Resolve=============");
        obj.solveSudoku();
        obj.printSudoku();

    }*/


    public void solveSudoku() {
		int count = 1;
		//boolean guessValue = false;
		int retryWithoutUpdateCount = 0;
		boolean foundUpdate = false;
		do {
			printSudoku();
			//guessValue = false;
			System.out.println("Count : " + count+++" : "+possibleNumbersMap);
			String insertedData = "";
			foundUpdate = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					List<Integer> possibility = new ArrayList<>();
					int possibilityIndex = generateIndex(i, j);
					if(count == 16 && i==1 && j == 5) {
						printSudoku();
						System.out.println("break");
					}
					if (sudoku[i][j] == 0) {
						String msg = "";
						for (int num = 1; num < 10; num++) {
							if (validateNumber(num, i, j)) {
								possibility.add(num);
							}
						}

						if (possibility.size() > 1) {
							msg = validateNumberPossibility(possibility, i, j);
						} else {
							msg = "Single possibility found.";
						}
						if (possibility.size() == 1) {
							foundUpdate = true;
							sudoku[i][j] = possibility.get(0);
							textViewMap.get("f"+i+""+j).setText(sudoku[i][j]+"");
							possibleNumbersMap.remove(possibilityIndex);
							insertedData = insertedData + i + ":" + j + "=" + possibility.get(0) + ", ";
							noteText.setText(msg);
							if(stepByStepExec) {
								textViewMap.get("f"+i+""+j).setTextColor(Color.GREEN);
								return;
							}
						} else {
							if(possibility.isEmpty()) {
								String errorMsg = "Error : "+i+":"+j+":"+possibleNumbersMap;
								System.out.println(errorMsg);
								noteText.setText(errorMsg);
								return;
							}
							possibleNumbersMap.put(possibilityIndex, possibility);
						}
					}
				}
			}
			if(!insertedData.isEmpty()) {
				System.out.println("InsertedData : "+insertedData);
			}
			if(foundUpdate) {
				retryWithoutUpdateCount = 0;
			} else {
				retryWithoutUpdateCount++;
			}
			if (!foundUpdate && allowGuessing) {
				System.out.println("------------Update not found : ");
				guessOneValue();
				//guessValue = true;
				foundUpdate = true;
				retryWithoutUpdateCount = 0;
			}

		} while ((foundUpdate || retryWithoutUpdateCount < 2) && !possibleNumbersMap.isEmpty());
		if(possibleNumbersMap.isEmpty()) {
			noteText.setText("Solved");
		} else {
			noteText.setText("Not solved : "+possibleNumbersMap);
		}
		System.out.println(possibleNumbersMap);
	}

	private void guessOneValue() {
		int minPossibility = 9;
		int minPossibilityI=0;
		int minPossibilityJ=0;
		int minPossibilityGuessIndex = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (sudoku[i][j] == 0) {
					int guessIndex = generateIndex(i, j);
					List<Integer> list = possibleNumbersMap.get(guessIndex);
					if (list != null) {
						if(minPossibility>list.size()) {
							minPossibilityI=i;
							minPossibilityJ=j;
							minPossibilityGuessIndex=guessIndex;
							minPossibility=list.size();
						}
					}

				}
			}
		}
		
		sudoku[minPossibilityI][minPossibilityJ] = possibleNumbersMap.get(minPossibilityGuessIndex).get(0);
		TextView textView = textViewMap.get("f"+minPossibilityI+""+minPossibilityJ);
		textView.setText(sudoku[minPossibilityI][minPossibilityJ]+"");
		textView.setTextColor(Color.YELLOW);
		System.out.println(" value Guess " + minPossibilityI + ":" + minPossibilityJ + "=" + sudoku[minPossibilityI][minPossibilityJ]);
		removeGuessValueFromOthersPossibility(minPossibilityI, minPossibilityJ, sudoku[minPossibilityI][minPossibilityJ]);
		possibleNumbersMap.remove(minPossibilityGuessIndex);
		return;
	}
	
	private void removeGuessValueFromOthersPossibility(int raw, int col, int guessValue) {
		for(int i=0;i<size;i++) {
			
			//Check raw
			List<Integer> possibilityInRaw = possibleNumbersMap.get(generateIndex(i, col));
			if(possibilityInRaw!=null && possibilityInRaw.contains(guessValue)) {
				possibilityInRaw.remove(possibilityInRaw.indexOf(guessValue));
			}
			
			//Check col
			List<Integer> possibilityInCol = possibleNumbersMap.get(generateIndex(raw, i));
			if(possibilityInCol!=null && possibilityInCol.contains(guessValue)) {
				possibilityInCol.remove(possibilityInCol.indexOf(guessValue));
			}
		}
		
		//Check Box
		int rawBoxIndex = (raw / 3) * 3;
		int colBoxIndex = (col / 3) * 3;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				List<Integer> possibilityInBox = possibleNumbersMap.get(generateIndex(rawBoxIndex + i, colBoxIndex + j));
				if(possibilityInBox!=null && possibilityInBox.contains(guessValue)) {
					possibilityInBox.remove(possibilityInBox.indexOf(guessValue));
				}
			}

		}
	}

	private int generateIndex(int raw, int col) {
		return (raw * 10) + col;
	}

	private boolean validateNumber(int number, int raw, int col) {
		boolean isValid = true;
		for (int i = 0; i < size; i++) {
			if (sudoku[i][col] == number || sudoku[raw][i] == number) {
				isValid = false;
				break;
			}
		}
		if (isValid) {
			int rawBoxIndex = (raw / 3) * 3;
			int colBoxIndex = (col / 3) * 3;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (sudoku[rawBoxIndex + i][colBoxIndex + j] == number) {
						isValid = false;
						break;
					}
				}

			}
		}
		return isValid;
	}

	private String validateNumberPossibility(List<Integer> possibleNumbers, int raw, int col) {

    	String msg = "";
		// Check raw wise
		List<Integer> uniquePossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		for (int i = 0; i < size; i++) {
			if (uniquePossibleNumbers.isEmpty()) {
				break;
			}
			if (sudoku[i][col] == 0 && i != raw) {
				List<Integer> list = possibleNumbersMap.get(generateIndex(i, col));
				if (list == null) {
					return "Raw wise Possibility is not set for raw : "+i+", col : "+col;
				}
				msg+="\n others raw : "+i+", col : "+col+" :: "+list;
				for (Integer possibleNum : uniquePossibleNumbers) {
					if (list.contains(possibleNum)) {
						uniquePossibleNumbers.remove(uniquePossibleNumbers.indexOf(possibleNum));
					}
				}
			}
		}
		if (uniquePossibleNumbers.size() == 1) {
			msg += "\nPossibleNumbers : "+possibleNumbers;
			possibleNumbers.clear();
			possibleNumbers.add(uniquePossibleNumbers.get(0));
			return "Raw wise Unique possibility found : "+msg;
		}
		msg = "";
		// Check columns wise
		uniquePossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		for (int i = 0; i < size; i++) {
			if (uniquePossibleNumbers.isEmpty()) {
				break;
			}

			if (sudoku[raw][i] == 0 && i != col) {
				List<Integer> list = possibleNumbersMap.get(generateIndex(raw, i));
				if (list == null) {
					return "Column wise Possibility is not set for raw : "+raw+", col : "+i;
				}
				msg+="\n others raw : "+i+", col : "+col+" :: "+list;
				for (Integer possibleNum : uniquePossibleNumbers) {
					if (list.contains(possibleNum)) {
						uniquePossibleNumbers.remove(uniquePossibleNumbers.indexOf(possibleNum));
					}
				}
			}
		}
		if (uniquePossibleNumbers.size() == 1) {
			msg += "\nPossibleNumbers : "+possibleNumbers;
			possibleNumbers.clear();
			possibleNumbers.add(uniquePossibleNumbers.get(0));
			return "Column wise Unique possibility found : "+msg;
		}

		msg = "";

		// Check Block wise
		uniquePossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		int rawBoxIndex = (raw / 3) * 3;
		int colBoxIndex = (col / 3) * 3;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (uniquePossibleNumbers.isEmpty()) {
					break;
				}
				if ((rawBoxIndex + i) != raw || (colBoxIndex + j) != col) {
					List<Integer> list = possibleNumbersMap.get(generateIndex(rawBoxIndex + i, colBoxIndex + j));
					if (list == null) {
						return "Block wise Possibility is not set for raw : "+(rawBoxIndex+i)+", col : "+(colBoxIndex+j);
					}
					msg+="\n others raw : "+(rawBoxIndex+i)+", col : "+(colBoxIndex+j)+" :: "+list;
					for (Integer possibleNum : uniquePossibleNumbers) {
						msg += "\nPossibleNumbers : "+possibleNumbers;
						if (list.contains(possibleNum)) {
							uniquePossibleNumbers.remove(uniquePossibleNumbers.indexOf(possibleNum));
						}
					}
				}
			}

		}
		if (uniquePossibleNumbers.size() == 1) {
			possibleNumbers.clear();
			possibleNumbers.add(uniquePossibleNumbers.get(0));
			return "Box wise unique Possibility found : "+msg;
		}
		return "";
	}

	private void printSudoku() {
		boolean isResolved = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int data = sudoku[i][j];
				if (data == 0) {
					isResolved = false;
				}
				System.out.print(data + " ");
			}
			System.out.println();
		}
	}
}
