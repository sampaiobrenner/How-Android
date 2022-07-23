package br.com.matheussbrenner.cadastroprodutos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import br.com.matheussbrenner.cadastroprodutos.R;
import br.com.matheussbrenner.cadastroprodutos.categorias.Categoria;
import br.com.matheussbrenner.cadastroprodutos.cores.Cor;
import br.com.matheussbrenner.cadastroprodutos.marcas.Marca;
import br.com.matheussbrenner.cadastroprodutos.produtos.Produto;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cadastroprodutos";

    private static final String TABLE_MARCA = "marca";
    private static final String TABLE_COR = "cor";
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String TABLE_PRODUTO = "produto";

    private static final String CREATE_TABLE_MARCA = "CREATE TABLE " + TABLE_MARCA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao VARCHAR(100)); ";

    private static final String CREATE_TABLE_COR = "CREATE TABLE " + TABLE_COR + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao VARCHAR(100)); ";

    private static final String CREATE_TABLE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao VARCHAR(100)); ";

    private static final String CREATE_TABLE_PRODUTO = "CREATE TABLE " + TABLE_PRODUTO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao VARCHAR(100), " +
            "id_categoria INTEGER, " +
            "id_marca INTEGER, " +
            "id_cor INTEGER, " +
            "CONSTRAINT fk_produto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id), " +
            "CONSTRAINT fk_produto_marca FOREIGN KEY (id_marca) REFERENCES marca (id), " +
            "CONSTRAINT fk_produto_cor FOREIGN KEY (id_cor) REFERENCES cor (id))";

    private static final String DROP_TABLE_MARCA = "DROP TABLE IF EXISTS " + TABLE_MARCA;
    private static final String DROP_TABLE_COR = "DROP TABLE IF EXISTS " + TABLE_COR;
    private static final String DROP_TABLE_CATEGORIA = "DROP TABLE IF EXISTS " + TABLE_CATEGORIA;
    private static final String DROP_TABLE_PRODUTO = "DROP TABLE IF EXISTS " + TABLE_PRODUTO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MARCA);
        db.execSQL(CREATE_TABLE_COR);
        db.execSQL(CREATE_TABLE_CATEGORIA);
        db.execSQL(CREATE_TABLE_PRODUTO);

        criarMarcasParaTestes(db);
        criarCoresParaTestes(db);
        criarCategoriasParaTestes(db);
        criarProdutosParaTestes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MARCA);
        db.execSQL(DROP_TABLE_COR);
        db.execSQL(DROP_TABLE_CATEGORIA);
        db.execSQL(DROP_TABLE_PRODUTO);
        onCreate(db);
    }

    /* Início CRUD Marcas */
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
    /* Fim CRUD Marcas */

    /* Início CRUD Cores */
    public long createCor (Cor c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", c.getDescricao());
        long id = db.insert(TABLE_COR, null, cv);
        db.close();
        return id;
    }
    public long updateCor (Cor c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", c.getDescricao());
        long id = db.update(TABLE_COR, cv,"_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }
    public long deleteCor (Cor c) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_COR, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }
    public void getAllCor (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_COR, columns, null, null,null, null, "descricao");
        int[] to = {R.id.textViewIdListarCor, R.id.textViewDescricaoListarCor, };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.cor_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Cor getByIdCor (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_COR, columns, "_id = ?", args,null, null, null);
        data.moveToFirst();
        Cor c = new Cor();
        c.setId(data.getInt(0));
        c.setDescricao(data.getString(1));
        data.close();
        db.close();
        return c;
    }
    /* Fim CRUD Cores */

    /* Início CRUD Categorias */
    public long createCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", c.getDescricao());
        long id = db.insert(TABLE_CATEGORIA, null, cv);
        db.close();
        return id;
    }
    public long updateCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", c.getDescricao());
        long id = db.update(TABLE_CATEGORIA, cv,"_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }
    public long deleteCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CATEGORIA, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }
    public void getAllCategoria (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_CATEGORIA, columns, null, null,null, null, "descricao");
        int[] to = {R.id.textViewIdListarCategoria, R.id.textViewDescricaoListarCategoria, };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.categoria_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Categoria getByIdCategoria (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CATEGORIA, columns, "_id = ?", args,null, null, null);
        data.moveToFirst();
        Categoria c = new Categoria();
        c.setId(data.getInt(0));
        c.setDescricao(data.getString(1));
        data.close();
        db.close();
        return c;
    }
    /* Fim CRUD Categorias */

    /* Início CRUD Produtos */
    public long createProduto (Produto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", p.getDescricao());
        cv.put("id_categoria", p.getId_categoria());
        cv.put("id_marca", p.getId_marca());
        cv.put("id_cor", p.getId_cor());
        long id = db.insert(TABLE_PRODUTO, null, cv);
        db.close();
        return id;
    }
    public long updateProduto (Produto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao", p.getDescricao());
        long id = db.update(TABLE_PRODUTO, cv,"_id = ?", new String[]{String.valueOf(p.getId())});
        db.close();
        return id;
    }
    public long deleteProduto (Produto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_PRODUTO, "_id = ?", new String[]{String.valueOf(p.getId())});
        db.close();
        return id;
    }
    public void getAllProduto (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_PRODUTO, columns, null, null,null, null, "descricao");
        int[] to = {R.id.textViewIdListarProduto, R.id.textViewDescricaoListarProduto, };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.produto_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Produto getByIdProduto (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "descricao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_PRODUTO, columns, "_id = ?", args,null, null, null);
        data.moveToFirst();
        Produto p = new Produto();
        p.setId(data.getInt(0));
        p.setDescricao(data.getString(1));
        data.close();
        db.close();
        return p;
    }
    /* Fim CRUD Produtos */

    private void criarMarcasParaTestes(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("descricao", "Dell");
        db.insert(TABLE_MARCA, null, cv);
        cv.clear();

        cv.put("descricao", "Samsung");
        db.insert(TABLE_MARCA, null, cv);
        cv.clear();

        cv.put("descricao", "Apple");
        db.insert(TABLE_MARCA, null, cv);
        cv.clear();

        cv.put("descricao", "LG");
        db.insert(TABLE_MARCA, null, cv);
        cv.clear();

        cv.put("descricao", "Sony");
        db.insert(TABLE_MARCA, null, cv);
        cv.clear();
    }

    private void criarCoresParaTestes(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("descricao", "Amarelo");
        db.insert(TABLE_COR, null, cv);
        cv.clear();

        cv.put("descricao", "Azul");
        db.insert(TABLE_COR, null, cv);
        cv.clear();

        cv.put("descricao", "Verde");
        db.insert(TABLE_COR, null, cv);
        cv.clear();

        cv.put("descricao", "Vermelho");
        db.insert(TABLE_COR, null, cv);
        cv.clear();

        cv.put("descricao", "Preto");
        db.insert(TABLE_COR, null, cv);
        cv.clear();
    }

    private void criarCategoriasParaTestes(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("descricao", "Eletrônicos");
        db.insert(TABLE_CATEGORIA, null, cv);
        cv.clear();

        cv.put("descricao", "Eletrodomésticos");
        db.insert(TABLE_CATEGORIA, null, cv);
        cv.clear();

        cv.put("descricao", "Games");
        db.insert(TABLE_CATEGORIA, null, cv);
        cv.clear();

        cv.put("descricao", "Livros");
        db.insert(TABLE_CATEGORIA, null, cv);
        cv.clear();

        cv.put("descricao", "Brinquedos");
        db.insert(TABLE_CATEGORIA, null, cv);
        cv.clear();
    }

    private void criarProdutosParaTestes(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("descricao", "Notebook");
        db.insert(TABLE_PRODUTO, null, cv);
        cv.clear();

        cv.put("descricao", "Geladeira");
        db.insert(TABLE_PRODUTO, null, cv);
        cv.clear();

        cv.put("descricao", "PlayStation 5");
        db.insert(TABLE_PRODUTO, null, cv);
        cv.clear();

        cv.put("descricao", "Livro - Código limpo");
        db.insert(TABLE_PRODUTO, null, cv);
        cv.clear();

        cv.put("descricao", "Urso de pelucia");
        db.insert(TABLE_PRODUTO, null, cv);
        cv.clear();
    }

    // Recupera apenas os IDs e nomes das Marcas para o Spinner.
    public void GetAllDescricaoMarca(ArrayList<Integer> listMarcaId, ArrayList<String> listMarcaDescricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_MARCA, columns, null, null, null,
                null, "descricao");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listMarcaId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("descricao");
            listMarcaDescricao.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    // Recupera apenas os IDs e nomes das Cores para o Spinner.
    public void GetAllDescricaoCor(ArrayList<Integer> listCorId, ArrayList<String> listCorDescricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_COR, columns, null, null, null,
                null, "descricao");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listCorId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("descricao");
            listCorDescricao.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    // Recupera apenas os IDs e nomes das Categorias para o Spinner.
    public void GetAllDescricaoCategoria(ArrayList<Integer> listCategoriaId, ArrayList<String> listCategoriaDescricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "descricao"};
        Cursor data = db.query(TABLE_CATEGORIA, columns, null, null, null,
                null, "descricao");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listCategoriaId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("descricao");
            listCategoriaDescricao.add(data.getString(nameColumnIndex));
        }
        db.close();
    }
}
