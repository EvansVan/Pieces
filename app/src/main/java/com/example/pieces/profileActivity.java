package com.example.pieces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
	Button edit;
	private FirebaseAuth mAuth;
	DatabaseReference db;
	private String currentUser;
	Context context;


	@Override
	protected void onStart() {
		super.onStart();
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

		mAuth = FirebaseAuth.getInstance();
		currentUser = mAuth.getCurrentUser().getUid();
		db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
	}

	private void saveClicked(View view) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = getLayoutInflater();
		final View view1 = inflater.inflate(R.layout.edituser, null);
		builder.setView(view);
		builder.setTitle("Update User");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				choice1 = view1.findViewById(R.id.editPick1);
				choice2 = view1.findViewById(R.id.editPick2);
				choice3 = view1.findViewById(R.id.editPick3);
				newUser = view1.findViewById(R.id.lytEdit_username);
				newEmail = view1.findViewById(R.id.lytEdit_email);

				String updateUser = newUser.getText().toString();
				String updateEmail = newEmail.getText().toString();
				String anime1 = choice1.getText().toString();
				String anime2 = choice2.getText().toString();
				String anime3 = choice3.getText().toString();

				FirebaseUser currentUser = mAuth.getCurrentUser();
				final DatabaseReference userDb = db.child(currentUser.getUid());
				userDb.child("Username").setValue(updateUser);
				userDb.child("Email").setValue(updateEmail);
				userDb.child("Pick1").setValue(anime1);
				userDb.child("Pick2").setValue(anime2);
				userDb.child("Pick3").setValue(anime3);
				startActivity(new Intent(context, profileActivity.class));
			}
		});
		builder.show();
	}
}
