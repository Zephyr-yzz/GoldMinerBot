package com.example.speechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.speechmate.data.entity.DatabaseHelper;
import android.util.Log;

public class eidt extends AppCompatActivity {
    //    设置头像
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 100;
    private EditText editUsername, editPassword, editPhone, editSign;
//    private ImageView imageViewAvatar; // 用于显示头像
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private String username;
    private Uri avatarUri; // 用于存储头像的 URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt);

        // 初始化数据库帮助类
        databaseHelper = new DatabaseHelper(this);

        // 获取 Intent 中传递的用户名
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("EditActivity", "Received username: " + username); // 添加日志

        // 设置 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 启用返回按钮
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标
        getSupportActionBar().setTitle("修改信息");// 设置 Toolbar 标题

        // 初始化 EditText 和 Button
        editUsername = findViewById(R.id.edit_user);
        editPassword = findViewById(R.id.edit_paw);
        editPhone = findViewById(R.id.edit_contact);
        editSign = findViewById(R.id.edit_sign);
        buttonSave = findViewById(R.id.button_save);

        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码不可见
//        imageViewAvatar = findViewById(R.id.imgavatar);

        // 加载用户信息
        loadUserData(username);

        //头像点击事件
//        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 检查权限
//                if (ContextCompat.checkSelfPermission(eidt.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    // 请求权限
//                    ActivityCompat.requestPermissions(eidt.this,
//                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
//                } else {
//                    // 权限已被授予，打开图库
//                    openGallery();
//                }
//            }
//        });

        // 保存按钮点击事件
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

    }

    //    设置头像及权限申请
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void loadUserData(String username) {
        Log.d("EditActivity", "Loading user data for username: " + username); // 添加日志
        if (username == null) {
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursor = databaseHelper.getUser(username);
            if (cursor != null && cursor.moveToFirst()) {
                editUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                editPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("paw")));
                editPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("pho")));
                editSign.setText(cursor.getString(cursor.getColumnIndexOrThrow("sign")));
                String avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar"));
                if (avatar != null) {
                    avatarUri = Uri.parse(avatar);
//                    imageViewAvatar.setImageURI(avatarUri);
                }
            } else {
                Toast.makeText(this, "用户信息加载失败", Toast.LENGTH_SHORT).show();
            }
    }

    private void saveUserData() {
        String newUsername = editUsername.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();
        String newPhone = editPhone.getText().toString().trim();
        String newSign = editSign.getText().toString().trim();
        String newAvatar = avatarUri != null ? avatarUri.toString() : null;

        // 创建AsyncTask来更新用户信息
        UpdateUserTask task = new UpdateUserTask(newUsername, newPassword, newPhone, newSign, newAvatar);
        task.execute();

    }

    //更新用户信息的异步任务
    private class UpdateUserTask extends AsyncTask<Void, Void, Boolean> {
        private String newUsername, newPassword, newPhone, newSign, newAvatar;

        public UpdateUserTask(String newUsername, String newPassword, String newPhone, String newSign, String newAvatar) {
            this.newUsername = newUsername;
            this.newPassword = newPassword;
            this.newPhone = newPhone;
            this.newSign = newSign;
            this.newAvatar = newAvatar; // 新增头像参数
        }


        protected Boolean doInBackground(Void... voids) {
            Log.d("EditActivity", "Updating user in background...");
            Log.d("EditActivity", "Updating user with username: " + username + ", newUsername: " + newUsername + ", password: " + newPassword + ", phone: " + newPhone + ", sign: " + newSign);
            return databaseHelper.updateUser(username, newUsername, newPassword, newPhone, newSign, newAvatar);
        }


        @Override
        protected void onPostExecute(Boolean isUpdated) {
            if (isUpdated) {
                Log.d("EditActivity", "Information updated successfully.");
                Toast.makeText(eidt.this, "信息更新成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("EditActivity", "Information update failed.");
                Toast.makeText(eidt.this, "信息更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            avatarUri = data.getData(); // 获取选中的头像 URI
//            imageViewAvatar.setImageURI(avatarUri); // 更新 ImageView 显示选择的头像
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish(); // 返回上一个活动

                // 返回到上一个 Fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(); // 弹出栈顶的 Fragment
                } else {
                    finish(); // 如果没有 Fragment 在栈中，结束 Activity
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}