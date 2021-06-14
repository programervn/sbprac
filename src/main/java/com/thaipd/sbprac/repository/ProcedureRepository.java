package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/*
https://lalitjc.wordpress.com/2013/07/02/different-ways-of-calling-stored-procedure-using-spring/
 */

@Repository
public class ProcedureRepository {
    private static Logger logger = LoggerFactory.getLogger(ProcedureRepository.class);
    @Autowired
    EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;

    public void testCallProcedure() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("test_pkg.in_and_out_test")
                .registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.OUT);
        query.setParameter(1, "ThaiPD");

        query.execute();

        String output = (String) query.getOutputParameterValue(2);
        logger.debug("*********** output stored procedure: {}", output);
    }

    public void testCallProcedureOutList2() {
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("test_pkg.sp_GetPlayer")
                .returningResultSet("returnds",
                        BeanPropertyRowMapper.newInstance(Player.class));

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("ps_PlayerName", "ThaiPD");

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            //return Collections.emptyList();
            logger.info("Not found any !!!");
        } else {
            //return (List) out.get("o_c_book");
            List<Player> playerList = (List) out.get("returnds");
            for (Player p : playerList) {
                logger.info("{}", p);
            }
        }

    }

    public void testCallProcedureOutList() {
        Session session = entityManager.unwrap(Session.class);

        ProcedureCall call = session.createStoredProcedureCall( "test_pkg.sp_GetPlayer");
        call.registerParameter(1, String.class, ParameterMode.IN).bindValue("ThaiPD");
        call.registerParameter(2, Class.class, ParameterMode.REF_CURSOR);

        List<Player> playerList = (List<Player>) call.getResultList();
        for (Player p : playerList) {
            // do something useful
            logger.info("Player: {}", p);
        }
    }

    /*
    OK ref cursor
     */
    public void callStoreProcedureCallableStatement() {
        final String procedureCall = "{call test_pkg.sp_GetPlayer(?, ?)}";
        Connection connection = null;
        CallableStatement callableSt = null;
        try {

            //Get Connection instance from dataSource
            connection = jdbcTemplate.getDataSource().getConnection();
            callableSt = connection.prepareCall(procedureCall);
            callableSt.setString(1, "ThaiPD");
            callableSt.registerOutParameter(2, Types.REF_CURSOR);

            //Call Stored Procedure
            callableSt.execute();
            ResultSet rs = (ResultSet) callableSt.getObject(2);
            while (rs.next ())
                System.out.println (rs.getString ("name"));

            // Close all the resources
            rs.close();


        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            try {
                if (callableSt != null) {
                    callableSt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (connection != null)
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
