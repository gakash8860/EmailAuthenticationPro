package com.example.emailauthenticationpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "Check";
    EditText ename;
    EditText email1;
    EditText epass;
    EditText epass2;
    EditText ephone;
    EditText elast;
    EditText date;
    int month,year,day;
    RadioGroup rgroup;
    CheckBox cb;
    Button b1;
    private FirebaseAuth mAuth;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        date=findViewById(R.id.date);
        mAuth = FirebaseAuth.getInstance();
        ename=findViewById(R.id.tname);
        email1=findViewById(R.id.tmail);
        elast=findViewById(R.id.tlast);
        epass=findViewById(R.id.tpass);
        epass2=findViewById(R.id.tpass2);
        ephone=findViewById(R.id.tphone);
        rgroup=findViewById(R.id.rg);
        cb=findViewById(R.id.checkBox);
        b1=findViewById(R.id.button);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmpty();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmpty();

            }
        });

    }
    public void getDate(){
        final Calendar c = Calendar.getInstance();
        day=c.get(Calendar.DAY_OF_MONTH);
        month=c.get(Calendar.MONTH);
        year=c.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth+ " "+(month+1)+" -"+year);
            }
        },day,month,year);
        dialog.show();
    }


    public void isEmpty(){
        String tmail,password;
        tmail=email1.getText().toString();
        password=epass.getText().toString();
        if (tmail.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Please Fill The Form", Toast.LENGTH_SHORT).show();
        }else {
            signUp(tmail,password);
        }

    }
    public void signUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(MainActivity2.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void updateUI(FirebaseUser fbu) {
        finish();
    }
}