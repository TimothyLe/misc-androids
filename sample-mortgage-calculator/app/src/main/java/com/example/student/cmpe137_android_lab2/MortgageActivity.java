package com.example.student.cmpe137_android_lab2;

import static java.lang.Math.toIntExact;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class MortgageActivity extends AppCompatActivity {

    public static final String HOME_VALUE = "HOME_VALUE";
    public static final String DOWN_PAYMENT = "DOWN_PAYMENT";
    public static final String INTEREST_RATE = "INTEREST_RATE";
    public static final String TERMS = "TERMS";
    public static final String PROPERTY_TAX_RATE = "PROPERTY_TAX_RATE";

    public double homeValue;
    public double downPayment;
    public double interestRate;
    // Has to 15,20,25,30,40
    public int terms;
    public double propertyTaxRate;

    // Inputs
    private EditText homeValueEditText;
    private EditText downPaymentEditText;
    private EditText interestRateEditText;
    private EditText termsEditText;
    private EditText propertyTaxRateEditText;
    // Outputs
    private EditText monthlyPaymentAmountEditText;
    private EditText totalInterestPaidEditText;
    private EditText totalPropertyTaxPaidEditText;
    private EditText payOffDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        if(savedInstanceState == null){
            homeValue = 0.0;
            downPayment = 0.0;
            interestRate = 0.0;
            terms = 0;
            propertyTaxRate = 0.0;
        }
        else
        {
            homeValue = savedInstanceState.getDouble(HOME_VALUE);
            downPayment = savedInstanceState.getDouble(DOWN_PAYMENT);
            interestRate = savedInstanceState.getDouble(INTEREST_RATE);
            terms = savedInstanceState.getInt(TERMS);
            propertyTaxRate = savedInstanceState.getDouble(PROPERTY_TAX_RATE);
        }

        /* Inputs */
        // Home value
        homeValueEditText = (EditText)findViewById(R.id.homeValueEdit);
        // Down Payment
        downPaymentEditText = (EditText)findViewById(R.id.downPaymentEdit);
        // Interest Rate
        interestRateEditText = (EditText)findViewById(R.id.interestRateEdit);
        // Terms
        termsEditText = (EditText)findViewById(R.id.termsEdit);
        // Property Tax Rate
        propertyTaxRateEditText = (EditText)findViewById(R.id.propertyTaxRateEdit);

        /* Outputs */
        // Monthly Payment Amount
        monthlyPaymentAmountEditText =(EditText)findViewById(R.id.monthlyPaymentEdit);
        // Total Interest Paid
        totalInterestPaidEditText = (EditText)findViewById(R.id.totalInterestPaidEdit);
        // Total Property Tax Paid
        totalPropertyTaxPaidEditText = (EditText)findViewById(R.id.totalPropertyTaxPaidEdit);
        // Pay Off Date
        payOffDateEditText = (EditText) findViewById(R.id.payOffDateEdit);

        Button calculateButton = (Button)findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(calculateButtonListener);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonListener);

    }

    public OnClickListener calculateButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Hide Keyboard
            ((InputMethodManager) Objects.requireNonNull(getSystemService(
                    Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
                    homeValueEditText.getWindowToken(), 0);
            calculate();
        }
    };

    private void calculate(){

        homeValue = Double.parseDouble(homeValueEditText.getText().toString());
        downPayment = Double.parseDouble(downPaymentEditText.getText().toString());
        interestRate = Double.parseDouble(interestRateEditText.getText().toString());
        terms = Integer.parseInt(termsEditText.getText().toString());

        //put checks for 0 or  out of range values
        if(homeValue != 0.0 && downPayment != 0.0 && interestRate != 0.0 && terms !=0) {
            if (terms == 15 || terms == 20 || terms == 25 || terms == 30 || terms == 40){
                validTermCalculate();
            } else {
                fixTheTerms();
            }
        }
        else
        {
            errorPopUp();
        }
    }

    // Helper function for my boy
    public void validTermCalculate(){
        //calculate monthly and total payment
        double monthlyInterestRate = 0.0, monthlyPropertyTaxRate = 0.0, loan = 0.0, monthlyPaymentAmount = 0.0,
                totalInterestPaid = 0.0, monthlyPropertyTaxPaid = 0.0,
                totalPropertyTaxPaid = 0.0;
        int months = 0, payOffDate = 0;

        monthlyInterestRate = interestRate / (12 * 100);
        monthlyPropertyTaxRate = propertyTaxRate / (12 * 100);
        months = terms * 12;
        loan = homeValue - downPayment;

        // Interest section
        monthlyPaymentAmount = ((loan * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -months)));
        monthlyPaymentAmountEditText.setText(String.format("%.02f", monthlyPaymentAmount));

        totalInterestPaid = monthlyPaymentAmount * months;
        totalInterestPaidEditText.setText(String.format("%.02f", totalInterestPaid));

        // Property tax section
        monthlyPropertyTaxPaid = ((loan * monthlyPropertyTaxRate) / (1 - Math.pow(1 + monthlyPropertyTaxRate, -months)));
        totalPropertyTaxPaid = monthlyPropertyTaxPaid * months;
        totalPropertyTaxPaidEditText.setText(String.format("%.02f", totalPropertyTaxPaid));

        double payOff = (loan - (totalInterestPaid + totalPropertyTaxPaid)) / months;
        payOffDate =  (int) payOff;
        payOffDateEditText.setText(String.format("%i", payOffDate)); // Int for months
    }

    // Another helper function for error handling
    public void errorPopUp(){
        //alert
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MortgageActivity.this);

        // User did not enter some items
        builder.setTitle(R.string.missing_entries);

        // provide an OK button that dismisses the dialog
        builder.setPositiveButton("OK", null);

        // Set message display
        builder.setMessage(R.string.provide_entries);

        // Create an alert dialog
        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    // Fix the terms entry
    public void fixTheTerms(){
        //alert
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MortgageActivity.this);

        // User did not enter some items
        builder.setTitle(R.string.incorrect_term);

        // provide an OK button that dismisses the dialog
        builder.setPositiveButton("OK", null);

        // Set message display
        builder.setMessage(R.string.provide_term);

        // Create an alert dialog
        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    public OnClickListener cancelButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Hide Keyboard
            ((InputMethodManager) Objects.requireNonNull(getSystemService(
                    Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
                    homeValueEditText.getWindowToken(), 0);
            reset();
        }
    };


    private void reset(){
        //reset every field
        homeValueEditText.setText("0.0");
        downPaymentEditText.setText("0.0");
        interestRateEditText.setText("0.0");
        termsEditText.setText("0");
        propertyTaxRateEditText.setText("0.0");

        monthlyPaymentAmountEditText.setText("0.0");
        totalInterestPaidEditText.setText("0.0");
        totalPropertyTaxPaidEditText.setText("0.0");
        payOffDateEditText.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu adding items to the action bar if present
        getMenuInflater().inflate(R.menu.mortgage_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putDouble(HOME_VALUE, homeValue);
        outState.putDouble(DOWN_PAYMENT, downPayment);
        outState.putDouble(INTEREST_RATE, interestRate);
        outState.putInt(TERMS, terms);
        outState.putDouble(PROPERTY_TAX_RATE, propertyTaxRate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        // Simplified If statement for inflatable
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
