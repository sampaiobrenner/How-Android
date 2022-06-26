package br.com.matheussbrenner.cadastroprodutos.categorias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.matheussbrenner.cadastroprodutos.R;

public class MainFragment extends Fragment {


    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categoria_fragment_main, container, false);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_categoria, new ListarFragment()).commit();
        }
        Button btnAdicionar = view.findViewById(R.id.button_adicionar_categoria);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_categoria, new AdicionarFragment()).commit();
            }
        });

        Button btnListar = view.findViewById(R.id.button_listar_categoria);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_categoria, new ListarFragment()).commit();
            }
        });

        return view;
    }
}