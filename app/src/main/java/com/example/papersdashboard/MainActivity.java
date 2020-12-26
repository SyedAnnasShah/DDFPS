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
    TextView textView;
    EditText user,pass;
//    private View Teacher_drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textView= (TextView)findViewById(R.id.tv_forgotPassword);
//        //assign variables
//        drawerLayout= findViewById(R.id.Teacher_drawer_layout);

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
                                Toast.makeText(MainActivity.this, "SuccessFully Login " + res, Toast.LENGTH_SHORT).show();
                                if(res.getRole().equals("Professor")) {
                                    Intent intent = new Intent(MainActivity.this, Teacher_Dashboard.class);
                                    editor.putString("role", res.getRole());
                                    editor.commit();
                                    startActivity(intent);
                                }
                                else if(res.getRole().equals("Admin")) {
                                    Intent intent = new Intent(MainActivity.this, admin_dashboard.class);
                                    editor.putString("role", res.getRole());
                                    editor.commit();
                                    startActivity(intent);
                                }
                                else if(res.getRole().equals("Director")) {
                                    Intent intent = new Intent(MainActivity.this, director_dashboard.class);
                                    editor.putString("role", res.getRole());
                                    editor.commit();
                                    startActivity(intent);
                                }
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
    private int     id=0;
    private String  userName="";
    private String  pass="";
    private String  Role="";
    private String  Email="";

    String getUserName(){
        return userName;
    }
    void setUserName(String userName) {
        this.userName=userName;
    }
    String getPassword(){
        return pass;
    }
    void setPassword(String pass){
        this.pass= pass;
    }
    String getRole(){
        return Role;
    }
    void setRole(String Role) {
        this.Role=Role;
    }
    String getEmail(){
        return Email;
    }
    void setEmail(String Email) {
        this.Email=Email;
    }
    int getId(){
        return id;
    }
    void setId(int id) {
        this.id=id;
    }
}