package one.innovation.digital.application;

import one.innovation.digital.model.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /**Configurar fabrica de conexão que vem do arquivo persistence <persistence-unit name="dio-jpa">**/
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpql-dio");

        /**Gerencia o clico de vida das entidades**/
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Pessoa pessoa = new Pessoa("Rafael Ribeiro", "rafael@rafael.com");
        Pessoa pessoa1 = new Pessoa("Quezia Araujo", "quezia@quezia.com");
        Pessoa pessoa2 = new Pessoa("Moises Araujo", "moises@moises.com");

        /**Obrigatorio quando utiliza-se metodos que alteram o DB**/
        entityManager.getTransaction().begin();

        /**Persistindo alteração no DB**/
        entityManager.persist(pessoa);
        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);


        /**Finaliza a transação perisistindos todos os dados que foram modificados desde o inicio da transação**/
        entityManager.getTransaction().commit();

        // 2.4 - JPQL
        String nome = "Quezia Araujo";

        /**Trazendo somente 1 resultado**/
        String jpql = "select p from Pessoa p where p.nome = :nome";
        Pessoa pessoaJPQL = entityManager
                .createQuery(jpql, Pessoa.class)
                .setParameter("nome", nome)
                .getSingleResult();

        /**Trazendo uma lista como resultado**/
        String jpqlList = "select p from Pessoa p";
        List<Pessoa> pessoaJPQLList = entityManager
                .createQuery(jpqlList, Pessoa.class)
                .getResultList();

        /**Resultados das consultas acima**/
        System.out.println("Consulta pessoaJPQL: " + pessoaJPQL);
        pessoaJPQLList.forEach(Pessoa -> System.out.println("Consulta pessoaJPQLList: " + Pessoa));

        entityManagerFactory.close();
        entityManager.close();
    }
}
