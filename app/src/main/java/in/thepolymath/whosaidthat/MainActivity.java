package in.thepolymath.whosaidthat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fill2DZeros();
    }

    String shareMessage;
    boolean shareScore;
    int totalScore = 0, totalAnswered = 0, i = 0, j = 0, a9 = 0, b9 = 0, a10 = 0, b10 = 0;
    //a and b (9 and 10) are used in questions with checkboxes to calculate the score when both correct options are chosen
    int answered[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, score[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int size = score.length;
    int[][] error = new int[10][4];

    /**
     * This method is used to check each option for errors
     */
    public void colorErrorsRed() {
        for (i = 0; i < size; i++) {
            for (j = 0; j < 4; j++) {
                if (error[i][j] == 1) {
                    String resID = "q" + (i + 1) + "_" + (j + 1);
                    Context context = getApplicationContext();
                    int newId = context.getResources().getIdentifier(resID, "id", context.getApplicationInfo().packageName);
                    if (i < 5) {
                        RadioButton wrongAnswer = (RadioButton) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#F44336"));
                    } else if (i > 7 && i < size) {
                        CheckBox wrongAnswer = (CheckBox) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#F44336"));
                    } else {
                        resID = "ans" + (i + 1);
                        newId = context.getResources().getIdentifier(resID, "id", context.getApplicationInfo().packageName);
                        EditText wrongAnswer = (EditText) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#F44336"));
                    }
                }
            }
        }
    }

    /**
     * This method is used to mark each option from red to black
     */
    public void resetToBlack() {
        for (i = 0; i < size; i++) {
            for (j = 0; j < 4; j++) {
                if (error[i][j] == 1) {
                    String resID = "q" + (i + 1) + "_" + (j + 1);
                    Context context = getApplicationContext();
                    int newId = context.getResources().getIdentifier(resID, "id", context.getApplicationInfo().packageName);
                    if (i < 5) {
                        RadioButton wrongAnswer = (RadioButton) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#000000"));
                    } else if (i > 7 && i < size) {
                        CheckBox wrongAnswer = (CheckBox) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#000000"));
                    } else {
                        resID = "ans" + (i + 1);
                        newId = context.getResources().getIdentifier(resID, "id", context.getApplicationInfo().packageName);
                        EditText wrongAnswer = (EditText) findViewById(newId);
                        wrongAnswer.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    /**
     * This method is used to reset everything
     */
    public void reset(View view) {
        resetToBlack();
        fill2DZeros();
        arrayReset();
        a9 = a10 = b9 = b10 = i = j = totalAnswered = totalScore = 0;
        RadioGroup rG1 = (RadioGroup) findViewById(R.id.gr1);
        rG1.clearCheck();
        RadioGroup rG2 = (RadioGroup) findViewById(R.id.gr2);
        rG2.clearCheck();
        RadioGroup rG3 = (RadioGroup) findViewById(R.id.gr3);
        rG3.clearCheck();
        RadioGroup rG4 = (RadioGroup) findViewById(R.id.gr4);
        rG4.clearCheck();
        RadioGroup rG5 = (RadioGroup) findViewById(R.id.gr5);
        rG5.clearCheck();
        EditText e = (EditText) findViewById(R.id.name);
        e.getText().clear();
        EditText ans6 = (EditText) findViewById(R.id.ans6);
        ans6.getText().clear();
        EditText ans7 = (EditText) findViewById(R.id.ans7);
        ans7.getText().clear();
        EditText ans8 = (EditText) findViewById(R.id.ans8);
        ans8.getText().clear();
        CheckBox cb = (CheckBox) findViewById(R.id.sharescore);
        cb.setChecked(false);
        CheckBox cB91 = (CheckBox) findViewById(R.id.q9_1);
        cB91.setChecked(false);
        CheckBox cB92 = (CheckBox) findViewById(R.id.q9_2);
        cB92.setChecked(false);
        CheckBox cB93 = (CheckBox) findViewById(R.id.q9_3);
        cB93.setChecked(false);
        CheckBox cB94 = (CheckBox) findViewById(R.id.q9_4);
        cB94.setChecked(false);
        CheckBox cB101 = (CheckBox) findViewById(R.id.q10_1);
        cB101.setChecked(false);
        CheckBox cB102 = (CheckBox) findViewById(R.id.q10_2);
        cB102.setChecked(false);
        CheckBox cB103 = (CheckBox) findViewById(R.id.q10_3);
        cB103.setChecked(false);
        CheckBox cB104 = (CheckBox) findViewById(R.id.q10_4);
        cB104.setChecked(false);
        Button result = (Button) findViewById(R.id.result);
        result.setEnabled(true);
    }

    /**
     * This method populates the error array with 0s
     */
    public void fill2DZeros() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                error[i][j] = 0;
            }
        }
    }

    /**
     * This method re-initializes the two 1D arrays (score and answered)
     */
    public void arrayReset() {
        for (i = 0; i < size; i++) {
            score[i] = 0;
            answered[i] = 0;
        }
    }


    /**
     * This method gets the name from the EditText box and stores it in the variable
     */
    public String getName() {
        EditText enterName = (EditText) findViewById(R.id.name);
        return enterName.getText().toString();
    }

    /**
     * This method checks and sets the boolean value for Share Score
     */
    public void scoreShare() {
        CheckBox share = (CheckBox) findViewById(R.id.sharescore);
        shareScore = share.isChecked();
        if (shareScore) {
            createShareMessage(totalScore);
            shareMessage(shareMessage);
        }
    }

    /**
     * This method is called to create a message for sharing via Intents
     */
    public String createShareMessage(int score) {
        shareMessage = getName() + " just played Who Said That? and scored";
        shareMessage = shareMessage + " " + score + " points!";
        return shareMessage;
    }

    /**
     * This method shares the message created using createShareMessage
     */
    public void shareMessage(String message) {
        Intent finalMessage = new Intent();
        finalMessage.setAction(Intent.ACTION_SEND);
        finalMessage.putExtra(Intent.EXTRA_TEXT, message);
        finalMessage.setType("text/plain");
        startActivity(finalMessage);
    }

    /**
     * This method sums the array answered and stores it in totalAnswered
     *
     * @param a is an array which has counts for number of questions answered
     */
    public int calcAnsweredCount(int a[]) {
        for (i = 0; i < size; i++) {
            totalAnswered += a[i];
        }
        return totalAnswered;
    }

    /**
     * This method sums the score array and returns the totalScore
     *
     * @param a is an array which has scores for each question answered
     */
    public int calcScore(int a[]) {
        for (i = 0; i < size; i++) {
            totalScore += a[i];
        }
        return totalScore;
    }

    /**
     * This method calculates and displays the score
     */
    public void calcResult(View view) {
        forQ6();
        forQ7();
        forQ8(); //checking and scoring for textBased questions
        calcAnsweredCount(answered);
        if (totalAnswered < size) {
            Toast.makeText(getApplicationContext(), "You forgot to answer one or more questions.", Toast.LENGTH_SHORT).show();
            totalAnswered = 0;
            totalScore = 0;
        } else if (totalAnswered == 10) {
            calcScore(score);
            colorErrorsRed();
            String scoreMessage = "Your score is " + totalScore + ".";
            if (totalScore <= 3) {
                scoreMessage = scoreMessage + " Try harder! Tap reset to play again.";
            } else if (totalScore <= 7) {
                scoreMessage = scoreMessage + " Good job! Tap reset to play again.";
            } else
                scoreMessage = scoreMessage + " Awesome. You're amazing! `Tap reset to play again.";
            Toast.makeText(getApplicationContext(), scoreMessage, Toast.LENGTH_SHORT).show();
            scoreShare();
            totalScore = 0;
            totalAnswered = 0;
            Button result = (Button) findViewById(R.id.result);
            result.setEnabled(false);
        } else {
            Toast.makeText(getApplicationContext(), "Something seems to be wrong.", Toast.LENGTH_SHORT).show();
            totalAnswered = 0;
            totalScore = 0;
        }
    }

    /**
     * This method calculates the score for q1
     *
     * @param view this takes in the RadioButton views created in the xml layout, referred to by the ids
     */
    public void forQ1(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) answered[0] = 1;
        switch (view.getId()) {
            case R.id.q1_1:
                if (checked) {
                    error[0][0] = 0;
                    score[0] = 1;
                }
                break;
            case R.id.q1_2:
                if (checked) {
                    error[0][1] = 1;
                    score[0] = 0;
                }
                break;
            case R.id.q1_3:
                if (checked) {
                    error[0][2] = 1;
                    score[0] = 0;
                }
                break;
            case R.id.q1_4:
                if (checked) {
                    error[0][3] = 1;
                    score[0] = 0;
                }
                break;
        }
    }

    /**
     * This method calculates the score for q2
     *
     * @param view this takes in the RadioButton views created in the xml layout, referred to by the ids
     */
    public void forQ2(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) answered[1] = 1;
        switch (view.getId()) {
            case R.id.q2_1:
                if (checked) {
                    error[1][0] = 1;
                    score[1] = 0;
                }
                break;
            case R.id.q2_2:
                if (checked) {
                    error[1][1] = 1;
                    score[1] = 0;
                }
                break;
            case R.id.q2_3:
                if (checked)
                    score[1] = 1;
                break;
            case R.id.q2_4:
                if (checked) {
                    error[1][2] = 1;
                    score[1] = 0;
                }
                break;
        }
    }

    /**
     * This method calculates the score for q3
     *
     * @param view this takes in the RadioButton views created in the xml layout, referred to by the ids
     */
    public void forQ3(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) answered[2] = 1;
        switch (view.getId()) {
            case R.id.q3_1:
                if (checked) {
                    error[2][0] = 1;
                    score[2] = 0;
                }
                break;
            case R.id.q3_2:
                if (checked)
                    score[2] = 1;
                break;
            case R.id.q3_3:
                if (checked) {
                    error[2][2] = 1;
                    score[2] = 0;
                }
                break;
            case R.id.q3_4:
                if (checked) {
                    error[2][2] = 1;
                    score[2] = 0;
                }
                break;
        }
    }

    /**
     * This method calculates the score for q4
     *
     * @param view this takes in the RadioButton views created in the xml layout, referred to by the ids
     */
    public void forQ4(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) answered[3] = 1;
        switch (view.getId()) {
            case R.id.q4_1:
                if (checked) {
                    error[3][0] = 1;
                    score[3] = 0;
                }
                break;
            case R.id.q4_2:
                if (checked) {
                    error[3][1] = 1;
                    score[3] = 0;
                }
                break;
            case R.id.q4_3:
                if (checked) {
                    error[3][2] = 1;
                    score[3] = 0;
                }
                break;
            case R.id.q4_4:
                if (checked)
                    score[3] = 1;
                break;
        }
    }

    /**
     * This method calculates the score for q5
     *
     * @param view this takes in the RadioButton views created in the xml layout, referred to by the ids
     */
    public void forQ5(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) answered[4] = 1;
        switch (view.getId()) {
            case R.id.q5_1:
                if (checked) {
                    error[4][0] = 1;
                    score[4] = 0;
                }
                break;
            case R.id.q5_2:
                if (checked) {
                    error[4][1] = 1;
                    score[4] = 0;
                }
                break;
            case R.id.q5_3:
                if (checked) {
                    error[4][2] = 1;
                    score[4] = 0;
                }
                break;
            case R.id.q5_4:
                if (checked)
                    score[4] = 1;
                break;
        }
    }

    /**
     * This method calculates the score for q6
     */
    public void forQ6() {
        EditText ans6 = (EditText) findViewById(R.id.ans6);
        String x = ans6.getText().toString();
        if (!TextUtils.isEmpty(x)) {
            answered[5] = 1;
        }
        if (x.equalsIgnoreCase("Leo Tolstoy")) {
            score[5] = 1;
        } else {
            for (i = 0; i < 4; i++) {
                error[5][i] = 1;
            }
        }
    }

    /**
     * This method calculates the score for q7
     */
    public void forQ7() {
        EditText ans7 = (EditText) findViewById(R.id.ans7);
        String x = ans7.getText().toString();
        if (!TextUtils.isEmpty(x)) {
            answered[6] = 1;
        }
        if (x.equalsIgnoreCase("Henry Ford")) {
            score[6] = 1;
        } else {
            for (i = 0; i < 4; i++) {
                error[6][i] = 1;
            }
        }
    }

    /**
     * This method calculates the score for q8
     */
    public void forQ8() {
        EditText ans8 = (EditText) findViewById(R.id.ans8);
        String x = ans8.getText().toString();
        if (!TextUtils.isEmpty(x)) {
            answered[7] = 1;
        }
        if (x.equalsIgnoreCase("Socrates")) {
            score[7] = 1;
        } else {
            for (i = 0; i < 4; i++) {
                error[7][i] = 1;
            }
        }
    }

    /**
     * This method calculates the score for q9
     */
    public void forQ9(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            answered[8] = 1;
        }
        switch (view.getId()) {
            case R.id.q9_1:
                if (checked) {
                    a9 = 1;
                }
                break;
            case R.id.q9_2:
                if (checked) {
                    error[8][1] = 1;
                } else {
                    break;
                }
            case R.id.q9_3:
                if (checked) {
                    error[8][2] = 1;
                } else {
                    break;
                }
            case R.id.q9_4:
                if (checked) {
                    b9 = 1;
                } else {
                    break;
                }
        }

        if (a9 == 1 && b9 == 1) {
            score[8] = 1;
        }
    }

    /**
     * This method calculates the score for q10
     */
    public void forQ10(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            answered[9] = 1;
        }
        switch (view.getId()) {
            case R.id.q10_1:
                if (checked) {
                    error[9][0] = 1;
                } else
                    break;
            case R.id.q10_2:
                if (checked) {
                    error[9][1] = 1;
                } else {
                    break;
                }
            case R.id.q10_3:
                if (checked) {
                    a10 = 1;
                } else {
                    break;
                }
            case R.id.q10_4:
                if (checked) {
                    b10 = 1;
                } else {
                    break;
                }
        }

        if (a10 == 1 && b10 == 1) {
            score[9] = 1;
        }
    }
}

