package br.com.matheussbrenner.cadastroprodutos.cores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.matheussbrenner.cadastroprodutos.R;

public class AdicionarFragment extends Fragment {

    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cor_fragment_adicionar, container, false);
    }
}