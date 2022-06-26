package br.com.matheussbrenner.cadastroprodutos.produtos;

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

public class AdicionarFragment extends Fragment {

    private EditText etDescricao;

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

        Button btnAdicionar = v.findViewById(R.id.button_adicionar_produto);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });
        return v;
    }

    private void adicionar() {
        if (!validar()) return;

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Produto p = new Produto();
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