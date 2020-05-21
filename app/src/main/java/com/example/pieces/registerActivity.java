package com.example.pieces;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.pieces.R.id.choice1;

public class registerActivity  extends AppCompatActivity {

	EditText username, password, email, confirmpass, choice1, choice2, choice3;
	Button register;
	Context context;
	private FirebaseAuth mAuth;
	DatabaseReference db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		context = this;
		mAuth = FirebaseAuth.getInstance();
		db = FirebaseDatabase.getInstance().getReference().child("Users");

		username = findViewById(R.id.lytRegister_username);
		password = findViewById(R.id.password );
		email = findViewById(R.id.lytRegister_email);
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
					registerUser(userString, emailString, passString);
				} else {
					Toast.makeText(context, "Confirm password does not match password", Toast.LENGTH_SHORT)
									.show();
				}
			}
		});
	}

	private void registerUser(final String userName, final String emailString, String passString) {
		mAuth.createUserWithEmailAndPassword(emailString, passString)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if(task.isSuccessful()){
									Log.d("tag_reg","Registration successful");
									final AlertDialog.Builder builder = new AlertDialog.Builder(context);
									LayoutInflater inflater = getLayoutInflater();
									final View view = inflater.inflate(R.layout.userdialog,null);
									builder.setView(view);
									builder.setTitle("Fandom");
									builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											choice1 = view.findViewById(R.id.choice1);
											choice2 = view.findViewById(R.id.choice2);
											choice3 = view.findViewById(R.id.choice3);

											String anime1 = choice1.getText().toString();
											String anime2 = choice2.getText().toString();
											String anime3 = choice3.getText().toString();

											FirebaseUser currentUser = mAuth.getCurrentUser();
											final DatabaseReference userDb = db.child(currentUser.getUid());
											userDb.child("Username").setValue(userName);
											userDb.child("Email").setValue(emailString);
											userDb.child("Pick1").setValue(anime1);
											userDb.child("Pick2").setValue(anime2);
											userDb.child("Pick3").setValue(anime3);
											startActivity(new Intent(context, MainActivity.class));
										}
									});
									builder.show();
								}else{
									Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
							}
			}
		});
	}
}
