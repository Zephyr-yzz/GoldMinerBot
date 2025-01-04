package com.example.speechmate;

// HelpCenterActivity.java

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;



import java.util.ArrayList;
import java.util.List;

public class HelpCenterActivity extends AppCompatActivity {

    private static final List<String> questions = new ArrayList<>();

    static {
        questions.add("怎么创建一个帐户？");
        questions.add("如何进行录音？");
        questions.add("如何查看并编辑历史记录？");
        // 添加更多问题...
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        toolbar.setNavigationIcon(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标，确保你有这个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        getSupportActionBar().setTitle("帮助中心"); // 设置标题


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一个界面
            }
        });

        ListView questionListView = findViewById(R.id.question_list_view);
        questionListView.setAdapter(new QuestionAdapter(this, questions));
    }

    private static class QuestionAdapter extends ArrayAdapter<String> {

        public QuestionAdapter(HelpCenterActivity context, List<String> questions) {
            super(context, 0, questions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 如果convertView为空，则创建一个新的视图
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_item, parent, false);
            }

            // 获取当前的问题文本
            String question = getItem(position);

            // 查找TextView并设置文本
            TextView questionTextView = convertView.findViewById(R.id.question_text_view);
            questionTextView.setText(question);

            // 查找箭头视图并设置点击监听器
            View arrowView = convertView.findViewById(R.id.arrow_view);
            arrowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 创建一个Intent来启动QuestionDetailActivity
                    Intent intent = new Intent(getContext(), QuestionDetailActivity.class);
                    // 将问题作为额外数据传递给QuestionDetailActivity
                    intent.putExtra("question", question);
                    // 启动活动
                    getContext().startActivity(intent);
                }
            });

            return convertView;
        }
    }
}