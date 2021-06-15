package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Types;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//@Repository
public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(PlayerRepositoryCustomImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    private static final String returnds = "returnds";
    private static final String pResult = "p_result";

    @PostConstruct
    public void init() {
        logger.debug("************************** PostConstruct");
        jdbcTemplate.setResultsMapCaseInsensitive(true);
    }

    public List<Player> procFindAll(int orderByDate) {
        Instant start = Instant.now();
        String packageName = "test_pkg";
        String spName = "p_search_vendor";
        simpleJdbcCall =
                new SimpleJdbcCall(jdbcTemplate).withCatalogName(packageName)
                        .withProcedureName(spName).returningResultSet(returnds,
                        BeanPropertyRowMapper.newInstance(Player.class));
        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("p_order_type", orderByDate).addValue("p_name", "")
                .addValue("p_email", "").addValue("p_address", "")
                .addValue("p_mobile","").addValue("p_status", -1);
        Map out = simpleJdbcCall.execute(paramaters);
        Instant end = Instant.now();
        logger.info("Call db {}.{}, duration={}(ms)", packageName, spName, Duration.between(start, end).toMillis());
        if (out == null) {
            return Collections.emptyList();
        } else {
            List<Player> playerList = (List) out.get(returnds);
            for (Player p : playerList) {
                logger.info("Player: {}", p);
            }
            return (List) out.get(returnds);
        }
    }

    public Integer funcGetCount(Integer i, String s) {
        Instant start = Instant.now();
        String packageName = "test_pkg";
        String spName = "fn_Test1";
        simpleJdbcCall =
                new SimpleJdbcCall(jdbcTemplate)
                        .withCatalogName(packageName)
                        .withFunctionName(spName)
                        .withoutProcedureColumnMetaDataAccess();
        simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("Return_Value", Types.INTEGER));
        simpleJdbcCall.addDeclaredParameter(new SqlParameter("pn_v1", Types.INTEGER));
        simpleJdbcCall.addDeclaredParameter(new SqlParameter("ps_v2", Types.VARCHAR));
        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("pn_v1", i)
                .addValue("ps_v2", s);
        Integer out = simpleJdbcCall.executeFunction(Integer.class, paramaters);
        Instant end = Instant.now();
        logger.info("Call db {}.{}, duration={}(ms)", packageName, spName, Duration.between(start, end).toMillis());
        logger.debug("Function return: {}", out);
        return out;
    }

    public String funcGetCount2(Integer i, String s) {
        Instant start = Instant.now();
        String packageName = "test_pkg";
        String spName = "fn_Test2";
        simpleJdbcCall =
                new SimpleJdbcCall(jdbcTemplate)
                        .withCatalogName(packageName)
                        .withFunctionName(spName)
                        .withoutProcedureColumnMetaDataAccess();
        simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("Return_Value", Types.VARCHAR));
        simpleJdbcCall.addDeclaredParameter(new SqlParameter("pn_v1", Types.INTEGER));
        simpleJdbcCall.addDeclaredParameter(new SqlParameter("ps_v2", Types.VARCHAR));
        simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("po_retcode", Types.INTEGER));
        simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("po_retdesc", Types.VARCHAR));
        Integer po_retcode = 0;
        String po_retdesc = "";
        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("pn_v1", i)
                .addValue("ps_v2", s)
                .addValue("po_retcode", po_retcode)
                .addValue("po_retdesc", po_retdesc);

        String out = simpleJdbcCall.executeFunction(String.class, paramaters);
        po_retcode = (Integer) paramaters.getValue("po_retcode");
        po_retdesc = (String) paramaters.getValue("po_retdesc");
        Instant end = Instant.now();
        logger.info("Call db {}.{}, duration={}(ms)", packageName, spName, Duration.between(start, end).toMillis());
        logger.debug("Function return: {}", out);
        logger.debug("Function output: {}; {}", po_retcode, po_retdesc);
        return out;
    }

    public int deletePlayerCustom(Integer playerNumber) {
        String sql = "delete from player a where a.num = :player_number";
        return jdbcTemplate.update(sql, playerNumber);
    }

    public Player getPlayerById(Long id) {
        String sql = "select * from player a where a.id = :id";
        return jdbcTemplate.queryForObject(sql, new PlayerRowMapper(), new Object[]{id});
    }

    public int getTotalPlayerCount() {
        String sql = "SELECT COUNT(*) FROM player";

        int numOfPlayer = jdbcTemplate.queryForObject(sql, Integer.class);
        return numOfPlayer;
    }
}
