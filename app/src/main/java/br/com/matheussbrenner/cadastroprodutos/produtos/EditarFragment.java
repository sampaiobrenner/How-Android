package br.com.matheussbrenner.cadastroprodutos.produtos;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class EditarFragment extends Fragment {

    private EditText etDescricao;
    private DatabaseHelper databaseHelper;
    private Produto p;

    Spinner spMarca;
    ArrayList<Integer> listMarcaId;
    ArrayList<String> listMarcaDescricao;

    Spinner spCategoria;
    ArrayList<Integer> listCategoriaId;
    ArrayList<String> listCategoriaDescricao;

    Spinner spCor;
    ArrayList<Integer> listCorId;
    ArrayList<String> listCorDescricao;

    public EditarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.produto_fragment_editar, container, false);

        etDescricao = v.findViewById(R.id.editText_descricao_produto);
        spMarca = v.findViewById(R.id.spinnerMarca);
        spCategoria = v.findViewById(R.id.spinnerCategoria);
        spCor = v.findViewById(R.id.spinnerCor);

        Bundle b = getArguments();
        int id_produto = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        PreencherMarcas();
        PreencherCores();
        PreencherCategorias();
        p = databaseHelper.getByIdProduto(id_produto);

        etDescricao.setText(p.getDescricao());
        spMarca.setSelection(listMarcaId.indexOf(p.getId_marca()));
        spCor.setSelection(listCorId.indexOf(p.getId_cor()));
        spCategoria.setSelection(listCategoriaId.indexOf(p.getId_categoria()));

        Button btnEditar = v.findViewById(R.id.button_editar_produto);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_produto);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_produto);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirmacao_exclusao_registro);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_produto);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

    private void editar(int id) {
        if (!validar()) return;

        p = new Produto();
        p.setId(id);
        p.setDescricao(etDescricao.getText().toString());
        String descricaoMarca = spMarca.getSelectedItem().toString();
        p.setId_marca(listMarcaId.get(listMarcaDescricao.indexOf(descricaoMarca)));

        String descricaoCor = spCor.getSelectedItem().toString();
        p.setId_cor(listCorId.get(listCorDescricao.indexOf(descricaoCor)));

        String descricaoCategoria = spCategoria.getSelectedItem().toString();
        p.setId_categoria(listCategoriaId.get(listCategoriaDescricao.indexOf(descricaoCategoria)));

        databaseHelper.updateProduto(p);
        Toast.makeText(getActivity(), R.string.registro_atualizado_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_produto, new ListarFragment()).commit();
    }

    private boolean validar() {
        if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.informe_campo_descricao, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void excluir (int id) {
        p = new Produto();
        p.setId(id);
        databaseHelper.deleteProduto(p);
        Toast.makeText(getActivity(), R.string.registro_excluido_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_produto, new ListarFragment()).commit();
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
}