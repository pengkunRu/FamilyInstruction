package com.example.android.familyinstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kun on 2018/5/30.
 */

public class FamilyInstructionVedio extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        ArrayList<VedioMaterial> vedioMaterials =  new ArrayList<VedioMaterial>();

        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));
        vedioMaterials.add(new VedioMaterial("中华家训","刘邦的醒悟","韩昇",R.raw.vedio_file1_1));

        //适配器
        VedioAdapter adapter = new VedioAdapter(this,vedioMaterials);

        ListView listView = (ListView) findViewById(R.id.list_article);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(FamilyInstructionVedio.this,MediaPlayBack.class);
                startActivity(intent);
            }
        });
    }
}
