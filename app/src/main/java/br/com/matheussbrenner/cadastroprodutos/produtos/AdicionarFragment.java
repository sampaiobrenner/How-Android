package br.com.matheussbrenner.cadastroprodutos.produtos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.matheussbrenner.cadastroprodutos.R;
import br.com.matheussbrenner.cadastroprodutos.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    private EditText etDescricao;

    Spinner spMarca;
    ArrayList<Integer> listMarcaId;
    ArrayList<String> listMarcaDescricao;

    Spinner spCategoria;
    ArrayList<Integer> listCategoriaId;
    ArrayList<String> listCategoriaDescricao;

    Spinner spCor;
    ArrayList<Integer> listCorId;
    ArrayList<String> listCorDescricao;

    DatabaseHelper databaseHelper;

    public AdicionarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.produto_fragment_adicionar, container, false);

        etDescricao = v.findViewById(R.id.editText_descricao_produto);
        spMarca = v.findViewById(R.id.spinnerMarca);
        spCategoria = v.findViewById(R.id.spinnerCategoria);
        spCor = v.findViewById(R.id.spinnerCor);

        databaseHelper = new DatabaseHelper(getActivity());
        PreencherMarcas();
        PreencherCores();
        PreencherCategorias();

        Button btnAdicionar = v.findViewById(R.id.button_adicionar_produto);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });
        return v;
    }

    private void PreencherMarcas() {
        listMarcaId = new ArrayList<>();
        listMarcaDescricao = new ArrayList<>();
        databaseHelper.GetAllDescricaoMarca(listMarcaId, listMarcaDescricao);

        ArrayAdapter<String> spMarcaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMarcaDescricao);
        spMarca.setAdapter(spMarcaArrayAdapter);
    }

    private void PreencherCores() {
        listCorId = new ArrayList<>();
        listCorDescricao = new ArrayList<>();
        databaseHelper.GetAllDescricaoCor(listCorId, listCorDescricao);

        ArrayAdapter<String> spCorArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listCorDescricao);
        spCor.setAdapter(spCorArrayAdapter);
    }

    private void PreencherCategorias() {
        listCategoriaId = new ArrayList<>();
        listCategoriaDescricao = new ArrayList<>();
        databaseHelper.GetAllDescricaoCategoria(listCategoriaId, listCategoriaDescricao);

        ArrayAdapter<String> spCategoriaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listCategoriaDescricao);
        spCategoria.setAdapter(spCategoriaArrayAdapter);
    }

    private void adicionar() {
        if (!validar()) return;

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Produto p = new Produto();

        String descricaoMarca = spMarca.getSelectedItem().toString();
        p.setId_marca(listMarcaId.get(listMarcaDescricao.indexOf(descricaoMarca)));

        String descricaoCor = spCor.getSelectedItem().toString();
        p.setId_cor(listCorId.get(listCorDescricao.indexOf(descricaoCor)));

        String descricaoCategoria = spCategoria.getSelectedItem().toString();
        p.setId_categoria(listCategoriaId.get(listCategoriaDescricao.indexOf(descricaoCategoria)));

        p.setDescricao(etDescricao.getText().toString());
        databaseHelper.createProduto(p);
        Toast.makeText(getActivity(), R.string.registro_adicionado_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_produto, new ListarFragment()).commit();
    }

    private boolean validar() {
        if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.informe_campo_descricao, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}