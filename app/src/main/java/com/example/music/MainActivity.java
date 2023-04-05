package com.example.music;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
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

public class MainActivity extends Activity {
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
}