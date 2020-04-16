package com.example.pieces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity  extends AppCompatActivity {

	EditText username, password, email, confirmpass;
	Button register;
	Context context;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		context = this;
		mAuth = FirebaseAuth.getInstance();

		username = findViewById(R.id.username);
		password = findViewById(R.id.password );
		email = findViewById(R.id.email);
		confirmpass = findViewById(R.id.confirm_password);
		register = findViewById(R.id.register);

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String emailString = email.getText().toString();
				String userString = username.getText().toString();
				String passString = password.getText().toString();
				String confirmString = confirmpass.getText().toString();

				if(TextUtils.isEmpty(emailString) ||
								TextUtils.isEmpty(userString) ||
								TextUtils.isEmpty(passString) ||
								TextUtils.isEmpty(confirmString)) {
					Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
									.show();
				} else if(passString.equals(confirmString)) {
					registerUser(emailString, passString);
				} else {
					Toast.makeText(context, "Confirm password does not match password", Toast.LENGTH_SHORT)
									.show();
				}
			}
		});
	}

	private void registerUser(String emailString, String passString) {
		mAuth.createUserWithEmailAndPassword(emailString, passString)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if(task.isSuccessful()){
									Log.d("tag_reg","Registration successful");
									FirebaseUser currrentUser = mAuth.getCurrentUser();
									startActivity(new Intent(context,MainActivity.class));
									finish();
								}else{
									Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
							}
			}
		});
	}
}
