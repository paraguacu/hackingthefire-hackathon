package bombeiros.mg.gov.br.thefirecall.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class DBController {
    private SQLiteDatabase db;
    private Ocorrencia ocorrencia;
    private SinaisVitais sinaisVitais;

    public DBController(Context context){
        sinaisVitais = new SinaisVitais(context);
        ocorrencia = new Ocorrencia(context);
    }

    public String insereOcorrencia(int id, String telefone, String solicitante, String municipio,
                                   String endereco, int numero, String bairro, String referencia,
                                   String paciente, String sexo, int idade, String queixa,
                                   String observacoes, boolean emergencia, String status, String data, int id_atendente){
        ContentValues valores;
        long resultado;

        db = ocorrencia.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Ocorrencia.ID, id);
        valores.put(Ocorrencia.TELEFONE, telefone);
        valores.put(Ocorrencia.SOLICITANTE, solicitante);
        valores.put(Ocorrencia.MUNICIPIO, municipio);
        valores.put(Ocorrencia.ENDERECO, endereco);
        valores.put(Ocorrencia.NUMERO, numero);
        valores.put(Ocorrencia.BAIRRO, bairro);
        valores.put(Ocorrencia.REFERENCIA, referencia);
        valores.put(Ocorrencia.PACIENTE, paciente);
        valores.put(Ocorrencia.SEXO, sexo);
        valores.put(Ocorrencia.IDADE, idade);
        valores.put(Ocorrencia.QUEIXA, queixa);
        valores.put(Ocorrencia.OBSERVACOES, observacoes);
        valores.put(Ocorrencia.EMERGENCIA, emergencia);
        valores.put(Ocorrencia.STATUS, status);
        valores.put(Ocorrencia.DATA, data);
        valores.put(Ocorrencia.ID_ATENDENTE, id_atendente);



        resultado = db.insert(Ocorrencia.__tablename__, null, valores);
        db.close();

        if (resultado ==-1)
            return "error";
        else
            return "success";
    }

    public Cursor getOcorrencia(int id){
        Cursor cursor;
        String[] campos =  {Ocorrencia.ID, Ocorrencia.EMERGENCIA};
        String where = Ocorrencia.ID + " = " + id;
        db = ocorrencia.getReadableDatabase();
        cursor = db.query(Ocorrencia.__tablename__, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getOcorrencias(){
        Cursor cursor;
        String[] campos =  {Ocorrencia.ID, Ocorrencia.QUEIXA};
        db = ocorrencia.getReadableDatabase();
        cursor = db.query(Ocorrencia.__tablename__, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insereVitais(String titulo, String autor, String editora){
        ContentValues valores;
        long resultado;

        db = sinaisVitais.getWritableDatabase();
        valores = new ContentValues();
        valores.put(SinaisVitais.ID, titulo);
        valores.put(SinaisVitais.ID_OCORRENCIA, autor);
        valores.put(SinaisVitais.PRESSAO, editora);
        valores.put(SinaisVitais.FREQUENCIA_CARDIACA, autor);
        valores.put(SinaisVitais.SATURACAO_OXIGENIO, editora);
        valores.put(SinaisVitais.TEMPERATURA, editora);

        resultado = db.insert(SinaisVitais.__tablename__, null, valores);
        db.close();

        if (resultado ==-1)
            return "error";
        else
            return "success";

    }

    public void alteraRegistroOcorrencia(int id, String titulo, String autor, String editora){
        ContentValues valores;
        String where;

        db = ocorrencia.getWritableDatabase();

        where = Ocorrencia.ID + "=" + id;

        valores = new ContentValues();
//        valores.put(Ocorrencia.TITULO, titulo);
//        valores.put(Ocorrencia.AUTOR, autor);
//        valores.put(Ocorrencia.EDITORA, editora);

        db.update(Ocorrencia.__tablename__,valores,where,null);
        db.close();
    }

    public void deletaRegistroOcorrencia(int id){
        String where = Ocorrencia.ID + " = " + id;
        db = ocorrencia.getReadableDatabase();
        db.delete(Ocorrencia.__tablename__,where,null);
        db.close();
    }

    public void deletaRegistroSinaisVitais(int id){
        String where = SinaisVitais.ID + " = " + id;
        db = sinaisVitais.getReadableDatabase();
        db.delete(SinaisVitais.__tablename__,where,null);
        db.close();
    }
}
