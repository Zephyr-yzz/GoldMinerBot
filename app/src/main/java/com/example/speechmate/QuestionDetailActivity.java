package com.example.speechmate;

// QuestionDetailActivity.java

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class QuestionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        toolbar.setNavigationIcon(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        getSupportActionBar().setTitle("问题答案"); // 设置标题


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一个界面
            }
        });

        // 从Intent中获取问题文本
        Intent intent = getIntent();
        String question = intent.getStringExtra("question");


        String answer = getAnswerForQuestion(question);

        // 设置TextView来显示回答
        TextView answerTextView = findViewById(R.id.answer_text_view);
        answerTextView.setText(answer);
    }


    private String getAnswerForQuestion(String question) {
        if (question.equals("怎么创建一个帐户？")) {
            return "在“我的”界面点击“登录/注册”后选择注册，输入个人信息后点击“注册”即可创建一个新账户";
        } else if (question.equals("如何进行录音？")) {
            return "在首页点击右下角录音按钮即可开始录音，开始录音后短按录音按钮为暂停，长按录音按钮为结束录音";
        }
        else if(question.equals("如何查看并编辑历史记录？")) {
            return "通过底部导航栏可进入历史记录页面，用户可以查看语音处理的历史记录，可通过搜索框搜索关键词查找特定历史记录，还可以对语音处理内容和录音文本进行编辑修改，播放录音内容和删除历史记录";
        }
        // 添加更多问题和回答...
        return "Sorry, no answer available for this question.";
    }
}