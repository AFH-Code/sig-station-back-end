package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Departement;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.TypeStationP;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public interface PrincipalStationRepository extends JpaRepository<PrincipalStation, Integer>, JpaSpecificationExecutor<PrincipalStation> {

    public default void executeSql(String sqlQuery) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sigminpostel", "postgres", "91122219");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            // Gérer les erreurs de base de données
            e.printStackTrace();
        }
    }
    public Page<PrincipalStation> findAll(Pageable pageable);

    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a WHERE (:type) is null OR (p.type) IN (:type)")
    public Page<PrincipalStation> findByType(
            @Param("type") List<TypeStationP> type,
            @ParameterObject Pageable pageable

    );
    public Page<PrincipalStation> findByOperator(
            @Param("operatorId") Integer operatorId,
            Pageable pageable
    );
   /* @Query("SELECT p FROM PrincipalStation p LEFT JOIN p.operator o WHERE p.operatorName = :operatorName OR o IS NULL")
    public Page<PrincipalStation> findByOperatorName3(
            @Param("operatorName") String operatorName,
            Pageable pageable
    );*/
   /* @Query("SELECT p FROM PrincipalStation p WHERE (p.type IN (:type)) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND (:operatorName is null OR UPPER(p.operatorName) LIKE UPPER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findByOperatorName(
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            String operatorName,
            Pageable pageable
    );*/
    /*@Query("SELECT p FROM PrincipalStation p WHERE (p.type IN (:type)) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND (:operatorName is null OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findByOperatorName(
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @Param("operatorName") String operatorName,
            Pageable pageable
    );*/
    @Query("SELECT p FROM PrincipalStation p WHERE (p.type IN (:type)) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND ((:operatorName is null AND p.operator IS NULL) OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findByOperatorName(
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @Param("operatorName") String operatorName,
            Pageable pageable
    );

    @Override
    Page<PrincipalStation> findAll(Specification<PrincipalStation> specification, Pageable pageable);


    @Query("SELECT p FROM PrincipalStation p WHERE (p.type IN (:type)) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%')))")
    public Page<PrincipalStation> findPrincipalStationSeachByTyp(
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            Pageable pageable
    );

    /*@Query("SELECT p FROM PrincipalStation p JOIN Operator o ON p.operator = o WHERE (p.type IN (:type)) AND (:idOperator is null OR o.id = :idOperator) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%')))")
    public Page<PrincipalStation> findPrincipalStationSeachByType(
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            Pageable pageable
    );*/
    /*@Query("SELECT p FROM PrincipalStation p JOIN Operator o ON p.operator = o WHERE (p.type IN (:type)) AND (:idOperator is null OR o.id = :idOperator OR (o.id = :idOperator AND p.operator IS NULL)) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND (UPPER(p.operatorName) = UPPER((SELECT o2.ownerName FROM Operator o2 WHERE o2.id = :idOperator)))")
    public Page<PrincipalStation> findPrincipalStationSeachByType(
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            Pageable pageable
    );*/
    @Query("SELECT p FROM PrincipalStation p LEFT JOIN p.operator o WHERE (p.type IN (:type)) AND ((:operatorName is null AND p.operator IS NULL) OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%'))) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND ((:idOperator is null) OR (o.id = :idOperator AND UPPER(p.operatorName) = UPPER((SELECT o2.ownerName FROM Operator o2 WHERE o2.id = :idOperator))))")
    public Page<PrincipalStation> findPrincipalStationSeachByType(
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("operatorName") String operatorName,
            @Param("x") String search,
            Pageable pageable
    );




    @Query("SELECT p FROM PrincipalStation p JOIN Operator o ON p.operator = o WHERE (o.id = :id) AND (:x is null OR LOWER(o.ownerName) LIKE LOWER('%' || :x || '%'))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdOperator(
            @Param("id") Integer id,
            //@Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @ParameterObject Pageable pageable

    );


    @Query("SELECT p FROM PrincipalStation p  JOIN Arrondissement a ON p.arrondissement = a WHERE (p.type IN (:type)) AND (a.id = :id) AND (:x is null OR UPPER(p.stationName) LIKE UPPER(concat('%', :x, '%'))) AND (:operatorName is null OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findByOperatorNameAndIdArrondissemnt(
            @Param("id") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @Param("operatorName") String operatorName,
            Pageable pageable
    );
    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a WHERE (p.type IN (:type)) AND (a.id = :id) AND (:x is null OR LOWER(p.stationName) LIKE LOWER('%' || :x || '%'))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdArrondissemnt(
            @Param("id") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @ParameterObject Pageable pageable

    );

    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Operator o ON p.operator = o WHERE (p.type IN (:type)) AND (a.id = :idArrondissement) AND (:idOperator is null OR o.id = :idOperator) AND (:x is null OR LOWER(p.stationName) LIKE LOWER('%' || :x || '%'))")
    public Page<PrincipalStation> findPrincipalStationSearchByIdArrondissemntAndOperator(
            @Param("idArrondissement") Integer idArrondissement,
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @ParameterObject Pageable pageable
    );


    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d WHERE (p.type IN (:type)) AND d.id = :idDepartement AND (:x is null OR LOWER(p.stationName) LIKE LOWER(concat('%', :x, '%'))) AND (:operatorName is null OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdDepartementOperatorName(
            @Param("idDepartement") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @Param("operatorName") String operatorName,
            Pageable pageable
    );
    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d WHERE (p.type IN (:type)) AND d.id = :idDepartement AND (:x is null OR LOWER(p.stationName) LIKE LOWER('%' || :x || '%'))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdDepartement(
            @Param("idDepartement") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @ParameterObject Pageable pageable

    );
    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d JOIN Operator o ON p.operator = o WHERE (p.type IN (:type)) AND d.id = :idDepartement AND (:idOperator is null OR o.id = :idOperator) AND (:search is null OR LOWER(p.stationName) LIKE LOWER('%' || :search || '%'))")
    public Page<PrincipalStation> findPrincipalStationSearchByIdDepartementAndOperator(
            @Param("idDepartement") Integer idDepartement,
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("search") String search,
            @ParameterObject Pageable pageable
    );

    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d JOIN Region r ON d.region = r WHERE (p.type IN (:type)) AND r.id = :idRegion AND (:x is null OR LOWER(p.stationName) LIKE LOWER(concat('%', :x, '%'))) AND (:operatorName is null OR LOWER(p.operatorName) LIKE LOWER(concat('%', :operatorName, '%')))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdRegionOperatorName(
            @Param("idRegion") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @Param("operatorName") String operatorName,
            Pageable pageable
    );
    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d JOIN Region r ON d.region = r WHERE (p.type IN (:type)) AND r.id = :idRegion AND (:x is null OR LOWER(p.stationName) LIKE LOWER('%' || :x || '%'))")
    public Page<PrincipalStation> findPrincipalStationSeachByIdRegion(
            @Param("idRegion") Integer id,
            @Param("type") List<TypeStationP> type,
            @Param("x") String search,
            @ParameterObject Pageable pageable

    );
    @Query("SELECT p FROM PrincipalStation p JOIN Arrondissement a ON p.arrondissement = a JOIN Departement d ON a.departement = d JOIN Region r ON d.region = r JOIN Operator o ON p.operator = o WHERE (p.type IN (:type)) AND r.id = :idRegion AND (:idOperator is null OR o.id = :idOperator) AND (:search is null OR LOWER(p.stationName) LIKE LOWER('%' || :search || '%'))")
    public Page<PrincipalStation> findPrincipalStationSearchByIdRegionAndOperator(
            @Param("idRegion") Integer idRegion,
            @Param("idOperator") Integer idOperator,
            @Param("type") List<TypeStationP> type,
            @Param("search") String search,
            @ParameterObject Pageable pageable
    );
}
