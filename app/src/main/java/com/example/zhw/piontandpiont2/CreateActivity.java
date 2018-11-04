package com.example.zhw.piontandpiont2;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_create_invite, button_create;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        intView();
    }
    private void intView() {
        button_create_invite = findViewById(R.id.button_create_invite);
        button_create = findViewById(R.id.button_create);
        listView = findViewById(R.id.listView_create);

        button_create_invite.setOnClickListener(this);
        button_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_invite:

                break;

            case R.id.button_create:
                try {
                    getStatus("ss");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    void getStatus(String createRequest) throws JSONException {
        JSONObject jsonObject = new JSONObject(createRequest);
        String status = jsonObject.getString("status");
            Toast.makeText(this,jsonObject.getString("information"),Toast.LENGTH_SHORT).show();

    }

}
