package com.example.student.cmpe137_android_lab1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_NAME = "USER_NAME";
    public static final String PASS_WORD = "PASS_WORD";

    public String userName;
    public String passWord;

    private EditText userNameEditText;
    private EditText passWordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState == null){
            userName = "Enter Username";
            passWord = "Enter Password";
        }
        else
        {
            userName = savedInstanceState.getString(USER_NAME);
            passWord = savedInstanceState.getString(PASS_WORD);
        }

        /* Inputs */
        // username
        userNameEditText = (EditText)findViewById(R.id.usernameEdit);
        // password
        passWordEditText = (EditText)findViewById(R.id.passwordEdit);

//        // Username
//        userNameEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //reset every field
//                userNameEditText.setText(" ");
//            }
//        });
//        View.OnClickListener userButtonListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Hide Keyboard
//                ((InputMethodManager) Objects.requireNonNull(getSystemService(
//                        Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
//                        userNameEditText.getWindowToken(), 0);
//
//            }
//        };
//        // Password
//        passWordEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // reset
//                passWordEditText.setText(" ");
//            }
//        });
//        View.OnClickListener passButtonListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Hide Keyboard
//                ((InputMethodManager) Objects.requireNonNull(getSystemService(
//                        Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
//                        passWordEditText.getWindowToken(), 0);
//
//            }
//        };

        // Sign in button
        Button signOnButton = (Button) findViewById(R.id.sign_in_button);
        signOnButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Sign In Clicked!", Toast.LENGTH_SHORT).show();
                signOn();
            }
        });

        // Cancel Button
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Cancel Clicked!", Toast.LENGTH_SHORT).show();
                cancel();
            }
        });
    }

    private void signOn(){

        userName = userNameEditText.getText().toString();
        passWord = passWordEditText.getText().toString();

        //put checks for 0 or  out of range values
        if(!passWord.equals(" ") && !userName.equals(" ")) {
            if(userName.equals("010641462") && passWord.equals("CMPE#137")) {
                // Landing page
                Intent myIntent = new Intent(LoginActivity.this, LandingActivity.class);
                startActivity(myIntent);
            }
            if (!userName.equals("010641462")){
                fixTheUsername();
            }
            if(!passWord.equals("CMPE#137")){
                fixThePassword();
            }
        }
        else
        {
            errorPopUp();
        }
    }

    // For username
    public void fixTheUsername(){
        //alert
        AlertDialog.Builder builder =
                new AlertDialog.Builder(LoginActivity.this);

        // User did not enter some items
        builder.setTitle(R.string.wrong_user);

        // provide an OK button that dismisses the dialog
        builder.setPositiveButton("OK", null);

        // Set message display
        builder.setMessage(R.string.wrong_user_message);

        // Create an alert dialog
        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    // For password
    public void fixThePassword(){
        //alert
        AlertDialog.Builder builder =
                new AlertDialog.Builder(LoginActivity.this);

        // User did not enter some items
        builder.setTitle(R.string.wrong_pass);

        // provide an OK button that dismisses the dialog
        builder.setPositiveButton("OK", null);

        // Set message display
        builder.setMessage(R.string.wrong_pass_message);

        // Create an alert dialog
        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    // Another helper function for error handling
    public void errorPopUp(){
        //alert
        AlertDialog.Builder builder =
                new AlertDialog.Builder(LoginActivity.this);

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

    private void cancel(){
        //reset every field
        userNameEditText.setText("Enter Username");
        passWordEditText.setText("Enter Password");
    }

    // Menu time!
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu adding items to the action bar if present
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString(USER_NAME, userName);
        outState.putString(PASS_WORD, passWord);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        // Simplified If statement for inflatable
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
