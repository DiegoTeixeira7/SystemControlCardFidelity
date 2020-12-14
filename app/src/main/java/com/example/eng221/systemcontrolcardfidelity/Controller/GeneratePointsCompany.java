package com.example.eng221.systemcontrolcardfidelity.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng221.systemcontrolcardfidelity.Model.ControladoraFachadaSingleton;
import com.example.eng221.systemcontrolcardfidelity.R;
import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class GeneratePointsCompany extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int VOLTAR = 1;
    public ArrayList<String> clientes = new ArrayList<>();
    public Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    public ArrayAdapter<String> adapter;
    public int idSolic;
    public int idEmpresa = -1;
    public int idCliente = -1;
    public double Reais = 0;
    public String nomeC = "";
    public String codeAlfanumerico = "";
    public String caminhoQRCodeGerado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_points_company);

        Spinner s = findViewById(R.id.spinnerClientes);
        s.setOnItemSelectedListener(this); //configura método de seleção
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientes);
        s.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();

        adapter.setNotifyOnChange(false);

        try {
            Cursor c = BancoDadosSingleton.getInstance().buscar("solicitacoesPontos", new String[]{"idSolicitacoesPontos","idCliente", "reais", "nomeC"}, "idEmpresa='"+1+"'", "");

            int i = 0;

            while(c.moveToNext()){
                int nomeC = c.getColumnIndex("nomeC");
                int idSolicitacoesPontos = c.getColumnIndex("idSolicitacoesPontos");
                int idCliente = c.getColumnIndex("idCliente");
                int reais = c.getColumnIndex("reais");

                clientes.add(c.getString(nomeC) + " - R$ " + c.getDouble(reais));
                map.put(i, c.getInt(idSolicitacoesPontos));
                i++;
            }

            // Habilitar novamente a notificacao
            adapter.setNotifyOnChange(true);
            // Notifica o Spinner de que houve mudanca no modelo
            adapter.notifyDataSetChanged();

            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Nenhuma solicitação", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add(0, VOLTAR, 1, "Voltar");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == VOLTAR) {
            finish();
            return true;
        }

        return false;
    }

    public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
        Integer idSolicitacao = map.get(posicao);
        if (idSolicitacao != null) {
            idSolic = Integer.parseInt(idSolicitacao.toString());
        }
        TextView cpf = findViewById(R.id.cpfText);
        cpf.setText(ControladoraFachadaSingleton.getInstance().getCliente().getCPF().toString());
        //Toast.makeText(this, "Item: " + clientes.get(posicao) + " id: " + idSolicitacao.toString() , Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView arg0) { }

    public void codes(View view) {
        //Toast.makeText(this,  ControladoraFachadaSingleton.getInstance(1).getEmpresa().exibirEmpresa(), Toast.LENGTH_LONG).show();

        buscaSolicitacoes();

        EditText priceEdt = findViewById(R.id.price);
        String price = priceEdt.getText().toString();

        if(price.equals("")){
            Toast.makeText(this, "Preencha todos os campos!" ,Toast.LENGTH_LONG).show();
        } else {
            double Price = parseDouble(price);

            if (Price != Reais) {
                Toast.makeText(this, "Valor digitado diferente do valor informado pelo cliente", Toast.LENGTH_LONG).show();
            } else {
                int pontosE = ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().getPontos();
                int metodoIdE = ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().getIdMetodo();
                double reaisE = ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().getReais();

                if((metodoIdE != 0) && (metodoIdE != 1) && (metodoIdE != 2)) {
                    Toast.makeText(this, "Método de conversão de pontos não identificado", Toast.LENGTH_LONG).show();
                } else {
                    String tag = view.getTag().toString();
                    TextView alfa = findViewById(R.id.codeAlpha);

                    if (tag.equals("generateCodes")) {
                        codeAlfanumerico = geraCodeAlfa();
                        alfa.setText(codeAlfanumerico);
                        caminhoQRCodeGerado = geraQRCode(codeAlfanumerico);
                    } else if (tag.equals("sendCodes")) {
                        int pontosResgatar = geraPontos(metodoIdE, pontosE, reaisE, Price);

                        if(codeAlfanumerico.equals("") || caminhoQRCodeGerado.equals("")) {
                            Toast.makeText(this, "Problema ao gerar os códigos!", Toast.LENGTH_LONG).show();
                        } else {
                            saveBD(codeAlfanumerico, caminhoQRCodeGerado, pontosResgatar);
                            excluiSolicitacaoBD();
                            priceEdt.setText(null);
                            TextView CPF = findViewById(R.id.cpfText);
                            CPF.setText("");
                            alfa.setText("");
                            recreate();
                            //Toast.makeText(this, "pontosE: " + pontosE + "metodoIdE: " + metodoIdE + "reaisE: " + reaisE +  "\n" + "PontosR: " + pontosResgatar, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    private void buscaSolicitacoes(){
        try {
            Cursor c = BancoDadosSingleton.getInstance().buscar("solicitacoesPontos", new String[]{"idSolicitacoesPontos","idCliente", "idEmpresa", "reais", "nomeC"}, "idSolicitacoesPontos='"+idSolic+"'", "");

            while(c.moveToNext()){
                int nC = c.getColumnIndex("nomeC");
                int idE = c.getColumnIndex("idEmpresa");
                int idC = c.getColumnIndex("idCliente");
                int r = c.getColumnIndex("reais");

                idEmpresa = c.getInt(idE);
                idCliente = c.getInt(idC);
                Reais = c.getDouble(r);
                nomeC = c.getString(nC);
            }

            //Toast.makeText(this, "IdSolic: " + idSolic + " IdEmpersa: " + idEmpresa + " IdCliente: " +  idCliente + " Reais: " + Reais + " NomeC: " + nomeC + "\n",Toast.LENGTH_LONG).show();
            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Nenhuma solicitação", Toast.LENGTH_SHORT).show();
        }
    }

    private int geraPontos(int metodoIdE, int pontosE, double reaisE, double reaisC) {
        // Método: O método de conversão de pontos deverá ser informado pela empresa
        // metodoIdE 0 = X ponto(s) a cada compra;
        // metodoIdE 1 = X ponto(s) a cada um real gasto;
        // metodoIdE 2 = X ponto(s) a cada valor arbitrário informado

        int pontos = 0;

        if(metodoIdE == 0) {
            pontos = pontosE;
        } else if (metodoIdE == 1) {
            pontos = pontosE * (int)(reaisE);
        } else {
            pontos = pontosE * ((int)reaisC/(int)(reaisE));
        }

        return pontos;
    }

    private String geraCodeAlfa() {
        String[] caracteres ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        String codigo="";
        for (int x=0; x<40; x++){
            int c = (int) (Math.random()*caracteres.length);
            codigo += caracteres[c];
        }

        return codigo;
    }

    private String geraQRCode(String alfaNumerico) {
        return alfaNumerico + "string_com_o_caminho";
    }

    private void saveBD(String codeAlfa, String qrCode, int pontoGanhar) {
        String nomeE = ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().getNome();
        ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().pontosResgatar(idCliente, Reais, codeAlfa, qrCode, pontoGanhar,nomeE);
        //Toast.makeText(this, "nomeE: "+nomeE + "idEmpresa: "+idEmpresa, Toast.LENGTH_SHORT).show();

    }

    private void excluiSolicitacaoBD() {
        ControladoraFachadaSingleton.getInstance(idEmpresa).getEmpresa().excluirSolicitacao(idSolic);
    }

}