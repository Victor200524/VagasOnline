package victor.trabalhovagasonline.vagasonline.services;

import com.google.gson.Gson;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Service;
import victor.trabalhovagasonline.vagasonline.entities.Cargo;
import victor.trabalhovagasonline.vagasonline.entities.Empresa;
import victor.trabalhovagasonline.vagasonline.entities.Interesse;
import victor.trabalhovagasonline.vagasonline.entities.Vaga;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set; // Importe este

@Service
public class VagasService {

    private MongoDatabase getDatabase() {
        String connectionString = "mongodb://localhost:27017";
        // FIXME: Considere gerenciar o MongoClient como um Bean Spring (Singleton)
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase("vagas_online");
    }

    //Verifica se uma vaga (pelo seu registro) possui algum interesse cadastrado
    private boolean vagaHasInteresse(String registro) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("interesses");

            // Procura por qualquer documento em "interesses" onde o campo "vaga.registro" é igual ao registro
            // .first() é otimizado para parar a busca assim que encontrar o primeiro
            Document interesse = collection.find(eq("vaga.registro", registro)).first();

            return interesse != null; // Se encontrou (não é nulo), então tem interesse
        } catch (Exception e) {
            System.out.println(e);
            // Em caso de erro de banco, é mais seguro bloquear a ação (retornar true)
            return true;
        }
    }


    public List<Vaga> getAllVagas(){
        List<Vaga> vagaList = new ArrayList<>();
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");
            MongoCursor<Document> cursor= collection.find().iterator();

            while (cursor.hasNext())
                vagaList.add(new Gson().fromJson(cursor.next().toJson(),Vaga.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return vagaList;
    }

    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("empresas");
            MongoCursor<Document> cursor= collection.find().iterator();

            while (cursor.hasNext())
                empresaList.add(new Gson().fromJson(cursor.next().toJson(),Empresa.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return empresaList;
    }

    public List<Cargo> getAllCargos(){
        List<Cargo> cargoList = new ArrayList<>();
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("cargos");
            MongoCursor<Document> cursor= collection.find().iterator();

            while (cursor.hasNext())
                cargoList.add(new Gson().fromJson(cursor.next().toJson(),Cargo.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return cargoList;
    }

    public Vaga createVaga(Vaga vaga) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");

            Gson gson = new Gson();
            String json = gson.toJson(vaga);
            Document doc = Document.parse(json);

            collection.insertOne(doc);
            return vaga;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean deleteVaga(String registro) {

        if (vagaHasInteresse(registro)) {
            throw new IllegalStateException("Esta vaga tem candidaturas e não pode ser excluída.");
        }

        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");
            collection.deleteOne(eq("registro", registro));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Interesse registerInterest(Interesse interesse) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("interesses");

            Gson gson = new Gson();
            String json = gson.toJson(interesse);
            Document doc = Document.parse(json);

            collection.insertOne(doc);
            return interesse;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Vaga getVagaByRegistro(String registro) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");
            Document doc = collection.find(eq("registro", registro)).first();

            if (doc != null) {
                return new Gson().fromJson(doc.toJson(), Vaga.class);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Vaga updateVaga(String registro, Vaga vaga) {

        if (vagaHasInteresse(registro)) {
            throw new IllegalStateException("Esta vaga tem candidaturas e não pode ser alterada.");
        }

        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");

            Gson gson = new Gson();
            Document doc = Document.parse(gson.toJson(vaga));
            doc.remove("registro");

            Document updateFields = new Document();
            for (String key : doc.keySet()) {
                updateFields.append(key, doc.get(key));
            }

            collection.updateOne(eq("registro", registro), new Document("$set", updateFields));
            return vaga;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}