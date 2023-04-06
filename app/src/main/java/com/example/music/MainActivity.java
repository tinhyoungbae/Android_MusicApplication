package com.example.music;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends Activity {
    DatabaseActivity databaseActivity;
    //Tạo thông báo
    public static final String CHANNEL_ID = "music";

    public void onClickSkip(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void onClickForgot(){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void onClickRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Xử lý sự kiện đăng nhập
    public void onClickSignIn(){
        EditText getemailmain = findViewById(R.id.emailmain);
        EditText getpasswordmain = findViewById(R.id.passwordmain);
        String email = getemailmain.getText().toString();
        String password = getpasswordmain.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(email != null && password != null && !email.isEmpty() && !password.isEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công.\nĐang chuyển hướng vào Trang chủ.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng!!!\nVui lòng thử lại.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(MainActivity.this, "Email và mật khẩu không được trống!!!", Toast.LENGTH_SHORT).show();
        }
    }

    //Tạo thông báo
    private void createNotificationChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notification = new NotificationChannel(CHANNEL_ID, "chanel music", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(notification);
            }
        }
    }

    //Yêu cầu cấp quyền truy cập vào tệp tin
    public void runtimePermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new readJsonSong().execute("https://nvt-8464.000webhostapp.com/server/getSong.php");

        TextView skip = findViewById(R.id.skip_text);
        TextView forgot = findViewById(R.id.forgotpassword);
        TextView register = findViewById(R.id.register);
        Button logbtn = findViewById(R.id.loginbtn);

        //Cấp quyền
        runtimePermission();
        //Tạo kênh thông báo trên thanh trạng thái
        createNotificationChanel();

        //Xử lý sự kiện chuyển trang
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSkip();
            }
        });

        //Xử lý sự kiện chuyển trang
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgot();
            }
        });

        //Xử lý sự kiện chuyển trang
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });

        //Xử lý sự kiện chuyển trang
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });
    }

    //Đọc JSON
    private class readJsonSong extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DatabaseActivity databaseActivity = new DatabaseActivity(MainActivity.this);
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String name_song = jsonObject.getString("na_song");
                String get_Uri = jsonObject.getString("ur_song");

                addToPlayList addToPlayList = new addToPlayList();
                addToPlayList.setSong_name(name_song);
                addToPlayList.setUrl_song(get_Uri);
                addToPlayList.setSinger_song("Internet Music");
                boolean kq = databaseActivity.InsertToPlayList(addToPlayList);
                if(kq)  Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}