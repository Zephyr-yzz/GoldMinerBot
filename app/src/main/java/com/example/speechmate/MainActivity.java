package com.example.speechmate;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.speechmate.data.entity.DatabaseHelper;
import com.example.speechmate.databinding.ActivityMainBinding;
import com.example.speechmate.ui.home.HomeFragment;
import com.example.speechmate.ui.history.HistoryFragment;
import com.example.speechmate.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    public static boolean isLogin;
    public static String Name;
    public static String Sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupBottomNavigation();
        databaseHelper = new DatabaseHelper(this);

//        databaseHelper.delete();
//        databaseHelper.GetUser(databaseHelper.getUser("yier"));
        // 检查是否有用户信息传递
//        if (getIntent().getExtras() != null) {
//            String nickname = getIntent().getStringExtra("nickname");
//            String sign = getIntent().getStringExtra("sign");
//
//            // 加载 ProfileFragment
//            ProfileFragment profileFragment = new ProfileFragment();
//            Bundle args = new Bundle();
//            args.putString("nickname", nickname);
//            args.putString("sign", sign);
//            profileFragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, profileFragment) // 确保替换为正确的容器 ID
//                    .commit();
//            //设置导航栏为选中个人中心
//            binding.bottomNavigation.setSelectedItemId(R.id.navigation_profile);
//        } else {
            // 默认加载其他 Fragment
            binding.bottomNavigation.setSelectedItemId(R.id.navigation_home); // 默认选中首页
//        }
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                fragment = new HomeFragment();
            } else if (itemId == R.id.navigation_history) {
                Log.d("MainActivity", "Switching to HistoryFragment"); // 添加日志
                fragment = new HistoryFragment();
            } else if (itemId == R.id.navigation_profile) {
                if(isLogin){
                    fragment = new ProfileFragment();
                    Bundle args = new Bundle();
                    args.putString("nickname", Name);
                    args.putString("sign", Sign);
                    fragment.setArguments(args);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, profileFragment) // 确保替换为正确的容器 ID
//                            .commit();
                }
                else fragment = new ProfileFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
                return true;
            }
            return false;
        });

        // 默认选中首页
        binding.bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onStart(){
        super.onStart();
        if (getIntent().getExtras() != null) {
            Log.d("123","111");
            String nickname = getIntent().getStringExtra("nickname");
            String sign = getIntent().getStringExtra("sign");

            // 加载 ProfileFragment
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putString("nickname", nickname);
            args.putString("sign", sign);
            profileFragment.setArguments(args);

            isLogin = true;
            Name = nickname;
            Sign = sign;

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, profileFragment) // 确保替换为正确的容器 ID
                    .commit();
            //设置导航栏为选中个人中心
            binding.bottomNavigation.setSelectedItemId(R.id.navigation_profile);
        }
    }
}
