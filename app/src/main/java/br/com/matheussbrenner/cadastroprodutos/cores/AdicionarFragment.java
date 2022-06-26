package br.com.matheussbrenner.cadastroprodutos.cores;

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
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cor_fragment_adicionar, container, false);

        etDescricao = v.findViewById(R.id.editText_descricao_cor);

        Button btnAdicionar = v.findViewById(R.id.button_adicionar_cor);
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
        Cor c = new Cor();
        c.setDescricao(etDescricao.getText().toString());
        databaseHelper.createCor(c);
        Toast.makeText(getActivity(), R.string.registro_adicionado_com_sucesso, Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cor, new ListarFragment()).commit();
    }

    private boolean validar() {
        if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.informe_campo_descricao, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}