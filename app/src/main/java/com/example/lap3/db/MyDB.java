package com.example.lap3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lap3.model.ThiSinh;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {
    private static final String tableName="thisinh";
    private static final String SDB="SBD";
    private static final String Name="FullName";
    private static final String DiemToan="DiemToan";
    private static final String DiemLy="DiemLy";
    private static final String DiemHoa="DiemHoa";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate="Create table if not exists "+tableName+" ("
                +SDB+ " Text Primary key, "
                +Name+" Text, "
                +DiemToan+" Double, "
                +DiemHoa+" Double, "
                +DiemLy+" Double)";
        db.execSQL(sqlCreate);

    }

    public Long addNew(ThiSinh thiSinh){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(SDB,thiSinh.getSbd());
        contentValues.put(Name,thiSinh.getHoten());
        contentValues.put(DiemToan,thiSinh.getDt());
        contentValues.put(DiemHoa,thiSinh.getDh());
        contentValues.put(DiemLy,thiSinh.getDl());
        Long id= db.insert(tableName,null,contentValues);
        db.close();
        return id;
    }
    public int updateThiSinh(ThiSinh thiSinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, thiSinh.getHoten());
        values.put(DiemToan, thiSinh.getDt());
        values.put(DiemHoa, thiSinh.getDh());
        values.put(DiemLy, thiSinh.getDl());

        // Updating row
        return db.update(tableName, values, SDB + " = ?", new String[]{thiSinh.getSbd()});
    }

    // Phương thức xóa thi sinh theo id
    public void deleteThiSinh(String sbd) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, SDB + " = ?", new String[]{sbd});
        db.close();
    }

    public void initData(){
        ThiSinh thiSinh=new ThiSinh("SBD01","Nguyen Van A",9.2,8.1,7);
        ThiSinh thiSinh1=new ThiSinh("SBD02","Nguyen Van A1",9,6,9);
        ThiSinh thiSinh2=new ThiSinh("SBD03","Nguyen Van A2",7.2,7,10);
        addNew(thiSinh);
        addNew(thiSinh1);
        addNew(thiSinh2);
    }

    public List<ThiSinh> getAll(){
        List<ThiSinh> thiSinhs=new ArrayList<>();
        String sql="Select * from "+tableName;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                ThiSinh thiSinh =new ThiSinh();
                thiSinh.setSbd(cursor.getString(0));
                thiSinh.setHoten(cursor.getString(1));
                thiSinh.setDt(cursor.getDouble(2));
                thiSinh.setDh(cursor.getDouble(3));
                thiSinh.setDl(cursor.getDouble(4));
                thiSinhs.add(thiSinh);
            }


        }
        return thiSinhs;
    }

    /*public void updateContact(Integer id,Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();

    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+tableName);
        onCreate(db);

    }
}
