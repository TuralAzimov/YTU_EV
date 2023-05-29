package com.example.ytu_ev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextInputEditText editTextName,editTextSurname,editTextEducationInfo,editTextMailAddress,editTextPhoneNumber;
    RadioButton room,roommate;
    Button buttonProfile;
    Button buttonExit;
    SeekBar distance,duration;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void updateUser(String studentId, String first_name, String last_name , String EducationInfo,
                           String situation , String distance , String duration, String contact_mail, String phone_number){
        databaseReference.child("users").child(studentId).child("İsim").setValue(first_name);
        databaseReference.child("users").child(studentId).child("Soyisim").setValue(last_name);
        databaseReference.child("users").child(studentId).child("Bölüm/Sınıf").setValue(EducationInfo);
        databaseReference.child("users").child(studentId).child("Durum").setValue(situation);
        databaseReference.child("users").child(studentId).child("İstenen Maksimum Uzaklık").setValue(distance);
        databaseReference.child("users").child(studentId).child("Süre").setValue(duration);
        databaseReference.child("users").child(studentId).child("İletisim maili").setValue(contact_mail);
        databaseReference.child("users").child(studentId).child("Telefon numarası").setValue(phone_number);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextEducationInfo = findViewById(R.id.education_info);
        editTextMailAddress = findViewById(R.id.contact_mail);
        editTextPhoneNumber = findViewById(R.id.phone);
        room = findViewById(R.id.room);
        roommate = findViewById(R.id.roommate);

        buttonProfile = findViewById(R.id.btn_profile);
        distance = findViewById(R.id.seekbar);
        duration = findViewById(R.id.seekbar2);
        buttonExit = findViewById(R.id.btn_logout);


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,surname,educationInfo,contactMail,phone,state,userId;
                name = String.valueOf(editTextName.getText());
                surname = String.valueOf(editTextSurname.getText());
                educationInfo = String.valueOf(editTextEducationInfo.getText());
                contactMail= String.valueOf(editTextMailAddress.getText());
                phone = String.valueOf(editTextPhoneNumber.getText());

                boolean isRoomChecked = room.isChecked();
                boolean isRoommateChecked = roommate.isChecked();

                if(isRoomChecked){
                    state = "Oda arıyor";
                }
                else if(isRoommateChecked){
                    state = "Oda arkadaşı arıyor";
                }
                else state = "Aramıyor";

                String distanceTxt = String.valueOf(distance.getProgress());
                String durationTxt = String.valueOf(duration.getProgress());


                userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                updateUser(userId,name,surname,educationInfo,state,distanceTxt,durationTxt,contactMail,phone);

            }
        });
    }
}