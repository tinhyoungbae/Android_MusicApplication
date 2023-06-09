package com.example.music;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    //Đóng bàn phím ảo
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button reset_btn = findViewById(R.id.reset_btn);
        EditText get_email = findViewById(R.id.reset_email);
        TextView get_reset_password_content = findViewById(R.id.reset_password_content);

        //Xử lý sự kiện trên Button Đặt lại mật khẩu
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đóng bàn phím ảo
                closeKeyboard();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = get_email.getText().toString();
                //Kiểm tra dữ liệu nhập vào có phải email không
                if (!emailAddress.isEmpty() && emailAddress != null && !emailAddress.contains("@")) {
                    get_reset_password_content.setText("Email bạn vừa nhập không đúng định dạng!!!\nĐây là một email đúng: abc@gmail.com");
                }
                else {
                    //Kiểm tra xem email có đang bỏ trống không
                    if (emailAddress == null || emailAddress.isEmpty()) {
                        get_reset_password_content.setText("Email không được để trống!!!\nVui lòng thử lại.");
                    }
                    else {
                        //Gọi hàm gửi email yêu cầu đặt lại mật khẩu
                        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    get_reset_password_content.setText("Yêu cầu đặt lại mật khẩu thành công.\nVui lòng kiểm tra email và làm theo hướng dẫn.");
                                } else{
                                    get_reset_password_content.setText("Email bạn vừa nhập không tồn tại trên hệ thống!!!\nVui lòng thử lại với một email khác");
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}