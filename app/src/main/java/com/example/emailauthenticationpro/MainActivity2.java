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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "Check";
    EditText ename;
    EditText email1;
    EditText epass;
    EditText epass2;
    EditText ephone;
    EditText elast;
    TextView dob;
    int month,year,day;
    RadioGroup rgroup;
    String name,lastname,phone,br = null;
    String tmail,password;
    CheckBox cb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button b1,b2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dob=findViewById(R.id.dob);
        mAuth = FirebaseAuth.getInstance();
        b2=findViewById(R.id.button2);
        ename=findViewById(R.id.tname);
        email1=findViewById(R.id.tmail);
        elast=findViewById(R.id.tlast);
        epass=findViewById(R.id.tpass);
        epass2=findViewById(R.id.tpass2);
        ephone=findViewById(R.id.tphone);
        rgroup=findViewById(R.id.rg);
        cb=findViewById(R.id.checkBox);
        b1=findViewById(R.id.button);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signUp(tmail,password);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
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
                dob.setText("Date of Birth : "+dayOfMonth+ "-"+(month+1)+" -"+year);
            }
        },day,month,year);
        dialog.show();
    }


    public void isEmpty(){

        name=ename.getText().toString();
        tmail=email1.getText().toString();
        password=epass.getText().toString();
        lastname=elast.getText().toString();
        phone=ephone.getText().toString();
        if (cb.isChecked()){
            RadioButton rb =findViewById(rgroup.getCheckedRadioButtonId());
            if (rb!=null){
                br=rb.getText().toString();
                if (tmail.isEmpty()||password.isEmpty()||name.isEmpty()||lastname.isEmpty()||phone.isEmpty()||br.isEmpty()){
                    Toast.makeText(this, "Please Fill The Form", Toast.LENGTH_SHORT).show();
                }else {

                    addData();

                }

            }
            else {
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please Select Terms & Conditions", Toast.LENGTH_SHORT).show();
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
    public void addData(){
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("lastname", lastname);
        user.put("email", tmail);
        user.put("password",password);
        user.put("gender",br);


// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(MainActivity2.this, "Data is Inserted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(MainActivity2.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}