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

import static com.mongodb.client.model.Filters.eq; // Importe este

@Service
public class VagasService {

    // Helper para obter a conexão (seguindo seu padrão)
    private MongoDatabase getDatabase() {
        String connectionString = "mongodb://localhost:27017";
        // FIXME: O MongoClient deve ser um Bean gerenciado pelo Spring,
        // criar um por método vaza recursos.
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase("vagas_online");
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

    // --- NOVO PARA O ADMIN (Frontend) ---
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

    // --- NOVO PARA O ADMIN (Frontend) ---
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

    // --- NOVO PARA O ADMIN (Frontend) ---
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

    // --- NOVO PARA O ADMIN (Frontend) ---
    public boolean deleteVaga(String registro) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");
            // Deleta o documento onde o campo "registro" é igual ao id recebido
            collection.deleteOne(eq("registro", registro));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // --- NOVO PARA O APP ANDROID ---
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
                // Converte o Document do Mongo para um objeto Vaga
                return new Gson().fromJson(doc.toJson(), Vaga.class);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // --- NOVO (Para a tela de Edição) ---
    public Vaga updateVaga(String registro, Vaga vaga) {
        try {
            MongoDatabase database = getDatabase();
            MongoCollection<Document> collection = database.getCollection("vagas");

            Gson gson = new Gson();
            Document doc = Document.parse(gson.toJson(vaga));
            doc.remove("registro"); // Não queremos atualizar o ID

            // Cria um Document com todos os campos para $set
            Document updateFields = new Document();
            for (String key : doc.keySet()) {
                updateFields.append(key, doc.get(key));
            }

            // Encontra pelo 'registro' e atualiza os campos
            collection.updateOne(eq("registro", registro), new Document("$set", updateFields));
            return vaga; // Retorna a vaga atualizada
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}




/*public List<Vaga> getAllVagas(){
        List<Vaga> vagaList = new ArrayList<>();
        try {
            String connectionString = "mongodb://localhost:27017";
            MongoClient mongoClient = MongoClients.create(connectionString);

            //Accessando um database (base de dados)
            MongoDatabase database = mongoClient.getDatabase("vagas_online");

            //selecionando uma coleção
            MongoCollection<Document> collection = database.getCollection("vagas");
            MongoCursor<Document> cursor= collection.find().iterator();

            // insere os Documents em um ArrayList de Carro
            while (cursor.hasNext())
                vagaList.add(new Gson().fromJson(cursor.next().toJson(),Vaga.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return vagaList;
    }*/