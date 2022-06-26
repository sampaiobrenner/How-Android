package br.com.matheussbrenner.cadastroprodutos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import br.com.matheussbrenner.cadastroprodutos.R;
import br.com.matheussbrenner.cadastroprodutos.marcas.Marca;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cadastroprodutos";
    private static final String TABLE_MARCA = "marca";

    private static final String CREATE_TABLE_MARCA = "CREATE TABLE " + TABLE_MARCA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao VARCHAR(100)); ";

    private static final String DROP_TABLE_MARCA = "DROP TABLE IF EXISTS " + TABLE_MARCA;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MARCA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MARCA);
        onCreate(db);
    }

    /* Início CRUD  Marcas */
    public long createMarca (Marca m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", m.getDescricao());
        long id = db.insert(TABLE_MARCA, null, cv);
        db.close();
        return id;
    }
    public long updateMarca (Marca m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", m.getDescricao());
        long id = db.update(TABLE_MARCA, cv,"_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public long deleteMarca (Marca m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_MARCA, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public void getAllMarca (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_MARCA, columns, null, null,null, null, "descricao");
        int[] to = {R.id.textViewIdListarMarca, R.id.textViewDescricaoListarMarca, };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.marca_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Marca getByIdMarca (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_MARCA, columns, "_id = ?", args,null, null, null);
        data.moveToFirst();
        Marca m = new Marca();
        m.setId(data.getInt(0));
        m.setDescricao(data.getString(1));
        data.close();
        db.close();
        return m;
    }
    /* Fim CRUD Marca */
}
