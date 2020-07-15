package com.example.pieces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {

	ImageView profileImg;
	EditText choice1,choice2, choice3, newUser, newEmail;
	TextView Username, Email , Pick1, Pick2, Pick3;
	Button edit, delete;
	private FirebaseAuth mAuth;
	FirebaseUser userDeets;
	DatabaseReference db;
	private String currentUser;
	Context context;


	@Override
	protected void onStart() {
		super.onStart();

		//populate the text views with database values when the activity starts
		db.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					String userName = dataSnapshot.child("Username").getValue().toString();
					String email = dataSnapshot.child("Email").getValue().toString();
					String pick1 = dataSnapshot.child("Pick1").getValue().toString();
					String pick2 = dataSnapshot.child("Pick2").getValue().toString();
					String pick3 = dataSnapshot.child("Pick3").getValue().toString();

					Username.setText(userName);
					Email.setText(email);
					Pick1.setText(pick1);
					Pick2.setText(pick2);
					Pick3.setText(pick3);
				}
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		context = this;
		profileImg = findViewById(R.id.profileImg);
		Username = findViewById(R.id.lytProfile_username);
		Email = findViewById(R.id.lytProfile_email);
		Pick1 = findViewById(R.id.pick1);
		Pick2 = findViewById(R.id.pick2);
		Pick3 = findViewById(R.id.pick3);
		edit = findViewById(R.id.edit);
		delete = findViewById(R.id.delete);

		mAuth = FirebaseAuth.getInstance();
		userDeets = mAuth.getCurrentUser();
		currentUser = mAuth.getCurrentUser().getUid();
		db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userDeets.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						Toast.makeText(context,"account deleted",Toast.LENGTH_LONG).show();
						startActivity(new Intent(context, loginActivity.class));
					}
				});
			}
		});

		edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(context);
				LayoutInflater inflater = getLayoutInflater();
				final View editView = inflater.inflate(R.layout.edituser, null);
				builder.setView(editView);
				builder.setTitle("Update User");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						choice1 = editView.findViewById(R.id.editPick1);
						choice2 = editView.findViewById(R.id.editPick2);
						choice3 = editView.findViewById(R.id.editPick3);
						newUser = editView.findViewById(R.id.lytEdit_username);
						newEmail = editView.findViewById(R.id.lytEdit_email);

						String updateUser = newUser.getText().toString();
						String updateEmail = newEmail.getText().toString();
						String anime1 = choice1.getText().toString();
						String anime2 = choice2.getText().toString();
						String anime3 = choice3.getText().toString();

						if(TextUtils.isEmpty(updateUser) ||
										TextUtils.isEmpty(updateEmail) ||
										TextUtils.isEmpty(anime1) ||
										TextUtils.isEmpty(anime2) ||
										TextUtils.isEmpty(anime3)) {
							Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
											.show();
						} else {
							db.child("Username").setValue(updateUser);
							db.child("Email").setValue(updateEmail);
							db.child("Pick1").setValue(anime1);
							db.child("Pick2").setValue(anime2);
							db.child("Pick3").setValue(anime3);
							startActivity(new Intent(context, profileActivity.class));
					}
				}
								});
			builder.show();
			}
		});
	}
}

