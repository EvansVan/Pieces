package com.example.pieces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginActivity extends AppCompatActivity {

	Context context;
	EditText uname, password;
	TextView reg;
	Button loginBtn;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mAuth = FirebaseAuth.getInstance();
		context = this;

		uname = findViewById(R.id.uname);
		reg = findViewById(R.id.signUp);
		password = findViewById(R.id.password);
		loginBtn = findViewById(R.id.loginBtn);

		//Clickable text that send the user to the register activity
		String text = "Not a registered user?? Click here to register";
		SpannableString ss = new SpannableString(text);
		final SharedPreferences preferences = getSharedPreferences( "user_login",MODE_PRIVATE);
		ClickableSpan clickableSpan1 = new ClickableSpan() {
			@Override
			public void onClick(@NonNull View widget) {
				startActivity(new Intent(context,registerActivity.class));
			}
		};
		ss.setSpan(clickableSpan1,24,45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		reg.setText(ss);
		reg.setMovementMethod(LinkMovementMethod.getInstance());

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String passString = password.getText().toString();
				String userString = uname.getText().toString();
				if (passString.length()<=6) {
					Toast.makeText(context, "User password is to short " + userString, Toast.LENGTH_SHORT)
									.show();
				} else if(passString.isEmpty() || userString.isEmpty()){
					Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
									.show();
				} else {
					loginUser(userString, passString);
				}
			}
		});
	}

	//login method to validate user
	private void loginUser(String userString, String passString) {
		mAuth.signInWithEmailAndPassword(userString, passString)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if(task.isSuccessful()){
									Log.d("tag_login","Login successful");
									startActivity(new Intent(context,MainActivity.class));
									finish();
								} else{
									Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
								}
							}
						});
	}

}