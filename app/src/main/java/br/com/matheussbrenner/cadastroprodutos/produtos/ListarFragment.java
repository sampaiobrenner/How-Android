package br.com.matheussbrenner.cadastroprodutos.produtos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.matheussbrenner.cadastroprodutos.R;

public class ListarFragment extends Fragment {

    public ListarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.produto_fragment_listar, container, false);
    }
}