package com.example.android.familyinstruction;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class VedioDetailInformation extends AppCompatActivity {

    private VideoView mVedioView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail_information);

        mVedioView = (VideoView)findViewById(R.id.vedio_view);
        mButton = (Button)findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vedioPath = "android.resource://" + getPackageName() + "/" + R.raw.vedio_file1_1;
                Uri uri = Uri.parse(vedioPath);
                mVedioView.setVideoURI(uri);
                mVedioView.start();
            }
        });
    }
}
