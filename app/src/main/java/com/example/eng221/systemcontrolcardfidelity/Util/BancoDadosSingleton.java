package com.example.eng221.systemcontrolcardfidelity.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eng221.systemcontrolcardfidelity.R;

public final class BancoDadosSingleton {

    // Método: O método de conversão de pontos deverá ser informado pela empresa
    // idMetodo 0 = X ponto(s) a cada compra;
    // idMetodo 1 = X ponto(s) a cada um real gasto;
    // idMetodo 2 = X ponto(s) a cada valor arbitrário informado

    private SQLiteDatabase db;
    private static BancoDadosSingleton INSTANCE;
    private final String NOME_BANCO = "sysmtemControlCardFidelity";
    private final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "CREATE TABLE cliente (" +
                    "  idCliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  nome TEXT NOT NULL," +
                    "  email TEXT NOT NULL," +
                    "  CPF TEXT NOT NULL," +
                    "  endereco TEXT NOT NULL," +
                    "  telefone INTEGER NOT NULL," +
                    "  senha TEXT NOT NULL," +
                    "  foto TEXT," +
                    "  typeUssuario INTEGER DEFAULT 0," +
                    "  dataNascimento TEXT," +
                    "  temSessao INTEGER DEFAULT 1 NOT NULL" +
                    ");",

            "INSERT INTO cliente (nome, email, CPF, endereco, telefone, senha) VALUES" +
                    "('Bob', 'bob@gmail.com', '123.456.789-10', 'Rua Oliveira, Bairro Centro, 234, Minas Gerais' , 1122667712, '123456');",

            "CREATE TABLE empresa (" +
                    "  idEmpresa INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  nome TEXT NOT NULL," +
                    "  email TEXT NOT NULL," +
                    "  CPForCNPJ TEXT NOT NULL," +
                    "  endereco TEXT NOT NULL," +
                    "  segmento TEXT NOT NULL," +
                    "  lat INTEGER NOT NULL ," +
                    "  long INTEGER NOT NULL ," +
                    "  senha TEXT NOT NULL," +
                    "  inadimplente INTEGER  DEFAULT 0 NOT NULL ," +
                    "  typeUsuario INTEGER DEFAULT 1," +
                    "  pontos INTEGER NOT NULL," +
                    "  idMetodo INTEGER NOT NULL," +
                    "  reais REAL," +
                    "  telefone INTEGER," +
                    "  avaliacao INTEGER DEFAULT 0 NOT NULL," +
                    "  avaliacaoMetodo INTEGER DEFAULT 0 NOT NULL," +
                    "  temSessao INTEGER DEFAULT 1 NOT NULL," +
                    "  reqExclusao INTEGER DEFAULT 0  NOT NULL," +
                    "  diasParaExlusao INTEGER DEFAULT 15" +
                    ");",

            "INSERT INTO empresa (nome, email, CPForCNPJ, endereco, segmento, lat, long, senha, pontos, idMetodo, reais) VALUES" +
                    "('Amazon', 'amazon@gmail.com', '123.456.789.1011121', 'Brazil' , 'Loja virtual', -21.3343434, 42.1212121, '12345', 10, 0, 0)," +
                    "('Ponto Frio', 'pontofrio@gmail.com', '532.456.789.1011121', 'Brazil' , 'Loja virtual', -20.3343434, 42.1212121, '12345', 5, 1, 1)," +
                    "('Shop Time', 'shoptime@gmail.com', '532.453.765.1011121', 'Brazil' , 'Loja virtual', -20.3343434, 41.1212121, '12345', 10, 2, 10);",

            "CREATE TABLE recompensas (" +
                    "  idRecompeas INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "  idEmpresa INTEGER NOT NULL," +
                    "  pontos INTEGER NOT NULL," +
                    "  idMetodo INTEGER NOT NULL," +
                    "  reais REAL," +
                    "  CONSTRAINT fk_recompensa_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa (idEmpresa)" +
                    ");",

            "CREATE TABLE solicitacoesPontos (" +
                    "  idSolicitacoesPontos INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  idEmpresa INTEGER," +
                    "  idCliente INTEGER," +
                    "  nomeC TEXT NOT NULL," +
                    "  reais REAL NOT NULL," +
                    "  dataCriacao TEXT," +
                    "  CONSTRAINT fk_solicitacoesPontos_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa (idEmpresa)," +
                    "  CONSTRAINT fk_solicitacoesPontos_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)" +
                    ");",

            "CREATE TABLE pontosResgatar (" +
                    "  idPontosResgatar INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  idEmpresa INTEGER," +
                    "  nomeE TEXT NOT NULL," +
                    "  idCliente INTEGER," +
                    "  reais REAL NOT NULL," +
                    "  pontosGanhar INTEGER NOT NULL," +
                    "  codeAlfa TEXT NOT NULL ," +
                    "  qrCode TEXT NOT NULL ," +
                    "  resgatado INTEGER  DEFAULT 0 NOT NULL ," +
                    "  dataCriacao TEXT ," +
                    "  dataResgatado TEXT ," +
                    "  CONSTRAINT fk_pontosResgatar_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa (idEmpresa)," +
                    "  CONSTRAINT fk_pontosResgatar_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)" +
                    ");",

            "CREATE TABLE solicitacoesResgate (" +
                    "  idsolicitacoesResgate INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  idEmpresa INTEGER," +
                    "  idCliente INTEGER," +
                    "  idRecompeas INTEGER PRIMARY KEY," +
                    "  codeAlpfa TEXT NOT NULL ," +
                    "  qrCode INTEGER NOT NULL ," +
                    "  validado INTEGER  DEFAULT 0 NOT NULL ," +
                    "  dataCriacao TEXT ," +
                    "  dataValidado TEXT ," +
                    "  CONSTRAINT fk_solicitacoesResgate_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa (idEmpresa)," +
                    "  CONSTRAINT fk_solicitacoesResgate_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)," +
                    "  CONSTRAINT fk_solicitacoesResgate_recompensas FOREIGN KEY (idRecompeas) REFERENCES solicitacoesResgate (idRecompeas)" +
                    ");",

            "CREATE TABLE pontos (" +
                    "  idPontos INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  idEmpresa INTEGER," +
                    "  idCliente INTEGER," +
                    "  pontosTotal INTEGER  DEFAULT 0 NOT NULL," +
                    "  pontosResgatar INTEGER  DEFAULT 0 NOT NULL," +
                    "  CONSTRAINT fk_pontos_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa (idEmpresa)," +
                    "  CONSTRAINT fk_pontos_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)" +
                    ");"
    };

    private BancoDadosSingleton() {
        Context ctx = MyApp.getAppContext();

        // Abre o banco de dados já existente ou então cria um banco novo
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);

        //busca por tabelas existentes no banco = "show tables" do MySQL
        //SELECT * FROM sqlite_master WHERE type = "table"
        Cursor c = buscar("sqlite_master", null, "type = 'table'", "");


        //Cria tabelas do banco de dados caso o mesmo estiver vazio.
        //bancos criados pelo método openOrCreateDatabase() possuem uma tabela padrão "android_metadata"

        if(c.getCount() == 1){
            for(int i = 0; i < SCRIPT_DATABASE_CREATE.length; i++){
                db.execSQL(SCRIPT_DATABASE_CREATE[i]);
            }
            Log.i("BANCO_DADOS", "Criou tabelas do banco e as populou.");
        }

        //Verifica o nome das tabelas criadas no banco.
        while(c.moveToNext()){
            int name = c.getColumnIndex("name");

            Log.i("BANCO_DADOS",c.getString(name));
        }

        c.close();
        Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
    }

    public static BancoDadosSingleton getInstance(){
        if (BancoDadosSingleton.INSTANCE == null){
            BancoDadosSingleton.INSTANCE = new BancoDadosSingleton();
        }
        //abre conexão caso esteja fechada
        BancoDadosSingleton.INSTANCE.abrir();

        return BancoDadosSingleton.INSTANCE;
    }

    // Insere um novo registro
    public long inserir(String tabela, ContentValues valores) {
        long id = db.insert(tabela, null, valores);

        Log.i("BANCO_DADOS", "Cadastrou registro com o id [" + id + "]");
        return id;
    }

    // Atualiza registros
    public int atualizar(String tabela, ContentValues valores, String where) {
        int count = db.update(tabela, valores, where, null);

        Log.i("BANCO_DADOS", "Atualizou [" + count + "] registros");
        return count;
    }

    // Deleta registros
    public int deletar(String tabela, String where) {
        int count = db.delete(tabela, where, null);

        Log.i("BANCO_DADOS", "Deletou [" + count + "] registros");
        return count;
    }

    // Busca registros
    public Cursor buscar(String tabela, String colunas[], String where, String orderBy) {
        Cursor c;
        if(!where.equals(""))
            c = db.query(tabela, colunas, where, null, null, null, orderBy);
        else
            c = db.query(tabela, colunas, null, null, null, null, orderBy);

        Log.i("BANCO_DADOS", "Realizou uma busca e retornou [" + c.getCount() + "] registros.");
        return c;
    }

    // Abre conexão com o banco
    private void abrir() {
        Context ctx = MyApp.getAppContext();

        if(!db.isOpen()) {
            // Abre o banco de dados já existente
            db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
            Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
        }else {
            Log.i("BANCO_DADOS", "Conexão com o banco já estava aberta.");
        }
    }

    // Fecha o banco
    public void fechar() {
        // fecha o banco de dados
        if (db != null && db.isOpen()) {
            db.close();
            Log.i("BANCO_DADOS", "Fechou conexão com o Banco.");
        }
    }
}
