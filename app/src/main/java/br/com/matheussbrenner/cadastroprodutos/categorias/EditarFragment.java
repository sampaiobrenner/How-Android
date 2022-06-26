package br.com.matheussbrenner.cadastroprodutos.categorias;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.matheussbrenner.cadastroprodutos.R;
import br.com.matheussbrenner.cadastroprodutos.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    private EditText etDescricao;
    private DatabaseHelper databaseHelper;
    private Categoria c;

    public EditarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoria_fragment_editar, container, false);

        etDescricao = v.findViewById(R.id.editText_descricao_categoria);

        Bundle b = getArguments();
        int id_categoria = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdCategoria(id_categoria);

        etDescricao.setText(c.getDescricao());

        Button btnEditar = v.findViewById(R.id.button_editar_categoria);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_categoria);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_categoria);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirmacao_exclusao_registro);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_categoria);
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

        c = new Categoria();
        c.setId(id);
        c.setDescricao(etDescricao.getText().toString());
        databaseHelper.updateCategoria(c);
        Toast.makeText(getActivity(), R.string.registro_atualizado_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_categoria, new ListarFragment()).commit();
    }

    private boolean validar() {
        if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.informe_campo_descricao, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void excluir (int id) {
        c = new Categoria();
        c.setId(id);
        databaseHelper.deleteCategoria(c);
        Toast.makeText(getActivity(), R.string.registro_excluido_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_categoria, new ListarFragment()).commit();
    }
}