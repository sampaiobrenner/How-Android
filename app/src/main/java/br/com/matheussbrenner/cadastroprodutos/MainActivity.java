package br.com.matheussbrenner.cadastroprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.matheussbrenner.cadastroprodutos.produtos.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainFragment()).commit();
        }
    }
}