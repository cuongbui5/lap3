package com.example.lap3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lap3.R;
import com.example.lap3.db.MyDB;
import com.example.lap3.model.ThiSinh;


public class AddActivity extends AppCompatActivity {
    private EditText sbd,name,dt,dl,dh;
    private Button btnAdd;
    private MyDB myDB;

    private boolean validate(Double number){
        return number >= 0 && number <= 10;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDB = new MyDB(this, "MyDB", null, 1);
        sbd=findViewById(R.id.editSBD);
        name=findViewById(R.id.editName);
        dt=findViewById(R.id.editDiemToan);
        dl=findViewById(R.id.editDiemLy);
        dh=findViewById(R.id.editDiemHoa);
        btnAdd=findViewById(R.id.btnAddNew);


        btnAdd.setOnClickListener(v -> {
            try {
                double diemToan = Double.parseDouble(dt.getText().toString());
                double diemLy = Double.parseDouble(dl.getText().toString());
                double diemHoa = Double.parseDouble(dh.getText().toString());

                if (!validate(diemToan)||!validate(diemHoa)||!validate(diemLy)|| name.getText().toString().equals("") || sbd.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this,"Thong tin khong hop le??!",Toast.LENGTH_SHORT).show();
                    return;
                }
                ThiSinh thiSinh=new ThiSinh(sbd.getText().toString(),name.getText().toString(),diemToan,diemLy,diemHoa);
                if(btnAdd.getText()=="Lưu"){
                    int id=myDB.updateThiSinh(thiSinh);
                    if(id>0){
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                    return;


                }
                Long id= myDB.addNew(thiSinh);
                if(id!=null){
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(AddActivity.this,"ERR:Thong tin khong hop le!",Toast.LENGTH_SHORT).show();
            }

        });
        Intent intent=getIntent();
        if(intent!=null){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                ThiSinh thiSinh=intent.getSerializableExtra("data", ThiSinh.class);
                if(thiSinh!=null){
                    sbd.setText(thiSinh.getSbd());
                    name.setText(thiSinh.getHoten());
                    dt.setText(thiSinh.getDt()+"");
                    dl.setText(thiSinh.getDl()+"");
                    dh.setText(thiSinh.getDh()+"");
                    btnAdd.setText("Lưu");
                }

            }



        }



    }
}
