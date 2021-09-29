package com.hrithik.ccnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hrithik.ccnumber.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.submitPaymentBtn.setOnClickListener(view -> setUpPayment());

    }

    private void setUpPayment() {

        String cardNum = Objects.requireNonNull(b.ccNumberET.getText()).toString();
        String firstName = Objects.requireNonNull(b.firstNameET.getText()).toString();
        String lastName = Objects.requireNonNull(b.lastNameET.getText()).toString();
        String date = Objects.requireNonNull(b.dateET.getText()).toString();
        String cvv = Objects.requireNonNull(b.securityCodeET.getText()).toString();


        if (!new CheckForCreditCardNumber().isCreditCardNumberValid(cardNum)){
            b.ccNumberET.requestFocus();
            b.ccNumberLay.setError("Invalid card number");
            return;
        }else {
            b.ccNumberLay.setErrorEnabled(false);
        }

        if (!isDateCorrect(date)){
            b.dateET.requestFocus();
            b.dateLay.setError( (date.length() == 0) ? "Invalid date" : "Card expired");
            return;
        }else {
            b.dateLay.setErrorEnabled(false);
        }

        // check for security code
        if (cvv.length() == 4){
            if (! new CheckForCreditCardNumber().isCardNumberInAmericanExpressFormat(cardNum)){
                b.securityCodeET.requestFocus();
                b.securityCodeLay.setError("Invalid CVV");
                return;
            }else {
                b.securityCodeLay.setErrorEnabled(false);
            }
        }
        else if (cvv.length() == 3){
            if (new CheckForCreditCardNumber().isCardNumberInAmericanExpressFormat(cardNum)){
                b.securityCodeET.requestFocus();
                b.securityCodeLay.setError("Invalid CVV");
                return;
            }else {
                b.securityCodeLay.setErrorEnabled(false);
            }
        }else {
            b.securityCodeET.requestFocus();
            b.securityCodeLay.setError("Invalid CVV");
            return;
        }


        if (!isNameCorrect(firstName)){
            b.firstNameET.requestFocus();
            b.firstNameLay.setError((firstName.equals("")) ? "First name required" : "Invalid entered first number");
            return;
        }else {
            b.firstNameLay.setErrorEnabled(false);
        }


        if (!isNameCorrect(lastName)){
            b.lastNameET.requestFocus();
            b.lastNameLay.setError( (lastName.equals("")) ? "Last name required" : "Invalid entered last name");
            return;
        }else {
            b.lastNameLay.setErrorEnabled(false);
        }


        new AlertDialog.Builder(this)
                .setTitle("Payment successful")
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();


    }

    // Checking for expiration date entered
    private boolean isDateCorrect(String dateStr) {

        SimpleDateFormat format = new SimpleDateFormat("MM/yy");

        try {
            Date date = format.parse(dateStr);

            if (date != null) {
                long dateMillis = date.getTime();
                long crntMillis = System.currentTimeMillis();

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(dateMillis);
                int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

                c.add(Calendar.DATE,daysInMonth-1);
                dateMillis = c.getTimeInMillis();

                return dateMillis > crntMillis;

            }else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    // checking for name (First & Last) entered
    private boolean isNameCorrect(String name) {
        if (name.equals("")) return false;
        String spaceRemovedStr = name.replaceAll("\\s","");
        return spaceRemovedStr.matches("^[a-zA-Z]*$");
    }

}