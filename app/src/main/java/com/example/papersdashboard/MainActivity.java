package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//    //initialize drawer
//    DrawerLayout drawerLayout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnLogin;
//    TextView textView;
    EditText user,pass;
//    private View Teacher_drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        textView= (TextView)findViewById(R.id.tv_forgotPassword);


//        //assign variables
//        drawerLayout= findViewById(R.id.Teacher_drawer_layout);
        // for activity testing (directly opening activity)
//        Intent intent = new Intent(MainActivity.this, TeacherCourses.class);
//        startActivity(intent);

        user=findViewById(R.id.et_user);
        pass=findViewById(R.id.et_password);
        btnLogin= (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em=user.getText().toString();
                String pas=pass.getText().toString();

                if(em.isEmpty())
                    user.setError("Please Enter Email");
                if(pas.isEmpty())
                    pass.setError("Please Enter Password");

                Call<Login> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .loginrecord(em,pas);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                            if(response.isSuccessful()) {
                                Login res = response.body();
                                int id= res.getMemberid();

                                Toast.makeText(MainActivity.this, ""+id, Toast.LENGTH_SHORT).show();

                                memberId(id);



                                Toast.makeText(MainActivity.this, "SuccessFully Login " + res, Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(MainActivity.this, "Invalid Crediential", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Failed",t.getMessage());
                    }
                });
            }
        });


    }

    private void memberId(int  id) {

        Call<MembersClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMemberType(id);
        call.enqueue(new Callback<MembersClass>() {
            @Override
            public void onResponse(Call<MembersClass> call, Response<MembersClass> response) {
                if(response.isSuccessful()) {
                    MembersClass res = response.body();

                    String mType= res.getMembertype();

                    if(res.getMembertype().equals("Professor")) {
                        Intent intent = new Intent(MainActivity.this, Teacher_Dashboard.class);
                        editor.putString("role", res.getMembertype());
                        editor.commit();
                        startActivity(intent);
                    }
                    else if(res.getMembertype().equals("Admin")) {
                        Intent intent = new Intent(MainActivity.this, admin_dashboard.class);
                        editor.putString("role", res.getMembertype());
                        editor.commit();
                        startActivity(intent);
                    }
                    else if(res.getMembertype().equals("Director")) {
                        Intent intent = new Intent(MainActivity.this, director_dashboard.class);
                        editor.putString("role", res.getMembertype());
                        editor.commit();
                        startActivity(intent);
                    }



                    Toast.makeText(MainActivity.this, "SuccessFull " + mType, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Toast.makeText(MainActivity.this, "Invalid Crediential"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MembersClass> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });


    }


    //CLICK MENU
//    public void ClickMenu(View view){
//        // open drawer
//        drawerLayout.openDrawer(Teacher_drawer_layout);
//    }
//    public static void openDrawer(DrawerLayout drawerLayout){
//        //open drawer layout
//        drawerLayout.openDrawer(GravityCompat.START);
//    }
//
}


class Login
{
    private int     userid;
    private int     memberid;
    public String  username;
    private String  password;

    public Login(int userid, int memberid, String username, String password) {
        this.userid = userid;
        this.memberid = memberid;
        this.username = username;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}