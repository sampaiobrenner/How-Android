package br.com.matheussbrenner.cadastroprodutos.cores;

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
    private Cor c;

    public EditarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cor_fragment_editar, container, false);

        etDescricao = v.findViewById(R.id.editText_descricao_cor);

        Bundle b = getArguments();
        int id_cor = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdCor(id_cor);

        etDescricao.setText(c.getDescricao());

        Button btnEditar = v.findViewById(R.id.button_editar_cor);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_cor);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_cor);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirmacao_exclusao_registro);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_cor);
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

        c = new Cor();
        c.setId(id);
        c.setDescricao(etDescricao.getText().toString());
        databaseHelper.updateCor(c);
        Toast.makeText(getActivity(), R.string.registro_atualizado_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cor, new ListarFragment()).commit();
    }

    private boolean validar() {
        if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.informe_campo_descricao, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void excluir (int id) {
        c = new Cor();
        c.setId(id);
        databaseHelper.deleteCor(c);
        Toast.makeText(getActivity(), R.string.registro_excluido_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cor, new ListarFragment()).commit();
    }
}