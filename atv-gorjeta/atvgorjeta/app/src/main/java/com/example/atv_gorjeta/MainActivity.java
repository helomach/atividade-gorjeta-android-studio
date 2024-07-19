package com.example.atv_gorjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // Variáveis para os campos de entrada
    private EditText valorContaEditText;
    private EditText numeroPessoasEditText;
    private EditText porcentagemGorjetaEditText;
    private Button calcularButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando os componentes da interface
        valorContaEditText = findViewById(R.id.valorTotalContaEditText);
        numeroPessoasEditText = findViewById(R.id.numPessoasEditText);
        porcentagemGorjetaEditText = findViewById(R.id.porcentagemGorjetaEditText);
        calcularButton = findViewById(R.id.calcularButton);

        // Definindo o comportamento do botão de cálculo
        // Quando o botão "Calcular" for clicado, ele chamará o método mostrarDialog
        calcularButton.setOnClickListener(view -> mostrarDialog());
    }

    public void mostrarDialog() {
        // Obter valores dos campos de entrada
        String valorContaString = valorContaEditText.getText().toString().trim();
        String numeroPessoasString = numeroPessoasEditText.getText().toString().trim();
        String porcentagemGorjetaString = porcentagemGorjetaEditText.getText().toString().trim();

        // Validar se todos os campos foram preenchidos
        if (valorContaString.isEmpty() && numeroPessoasString.isEmpty() && porcentagemGorjetaString.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converter valores para os tipos corretos
        double valorConta = Double.parseDouble(valorContaString);
        int numeroPessoas = Integer.parseInt(numeroPessoasString);
        double porcentagemGorjeta = Double.parseDouble(porcentagemGorjetaString);

        // Criar AlertDialog para perguntar ao usuário se ele gostaria de saber o seu valor individual com ou sem gorjeta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção")
                .setMessage("Deseja calcular o valor individual com ou sem gorjeta?")
                .setPositiveButton("Com Gorjeta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calcularComGorjeta(valorConta, numeroPessoas, porcentagemGorjeta);
                    }
                })
                .setNegativeButton("Sem Gorjeta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calcularSemGorjeta(valorConta, numeroPessoas);
                    }
                })
                .show();
    }

    // Método para calcular a gorjeta
    private double calcularGorjeta(double valorConta, double porcentagemGorjeta) {
        return valorConta * (porcentagemGorjeta / 100);
    }

    // Método para calcular o valor individual com gorjeta
    private void calcularComGorjeta(double valorConta, int numeroPessoas, double porcentagemGorjeta) {
        double contaTotal = valorConta + calcularGorjeta(valorConta, porcentagemGorjeta);
        double valorIndividual = contaTotal / numeroPessoas;
        Toast.makeText(getApplicationContext(), "Valor individual com gorjeta: R$ " + String.format("%.2f", valorIndividual), Toast.LENGTH_SHORT).show();
    }

    // Método para calcular o valor individual sem gorjeta
    private void calcularSemGorjeta(double valorConta, int numeroPessoas) {
        double valorIndividual = valorConta / numeroPessoas;
        Toast.makeText(getApplicationContext(), "Valor individual sem gorjeta: R$ " + String.format("%.2f", valorIndividual), Toast.LENGTH_SHORT).show();
    }
}
