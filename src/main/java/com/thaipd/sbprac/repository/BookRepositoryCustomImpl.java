package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    Class to practise with database stored procedure/function
    ref:
        https://mkyong.com/spring-boot/spring-boot-jdbc-stored-procedure-examples/
        https://www.codejava.net/frameworks/spring/spring-simplejdbccall-examples
 */
@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(BookRepositoryCustomImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    // init SimpleJdbcCall
    @PostConstruct
    void init() {
        // o_name and O_NAME, same
        logger.info("BookRepositoryCustomImpl PostConstruct");
        jdbcTemplate.setResultsMapCaseInsensitive(true);
    }

    /*
    this example call direct stored procedure
        PROCEDURE get_book_by_id(
        p_id IN BOOKS.ID%TYPE,
        o_name OUT BOOKS.NAME%TYPE,
        o_price OUT BOOKS.PRICE%TYPE);
     */
    public Optional<Book> findByIdProc(Long id) {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_id");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);

        Optional result = Optional.empty();

        try {

            Map out = simpleJdbcCall.execute(in);

            if (out != null) {
                Book book = new Book();
                book.setId(id);
                book.setName((String) out.get("O_NAME"));
                book.setPrice((BigDecimal) out.get("O_PRICE"));
                result = Optional.of(book);
                logger.info("Found book id = {}, values={}", id, result);
            } else {
                logger.info("Not found book id = {}", id);
            }

        }  catch (Exception e) {
            // ORA-01403: no data found, or any java.sql.SQLException
            logger.error("Exception {}", e.getMessage());
        }

        return result;
    }

    /*
    this example call direct stored procedure in package
    and get OUT parameter
     */
    public Optional<Book> findByIdPkg(Long id) {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("book_pkg")
                .withProcedureName("get_book_by_id");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);

        Optional result = Optional.empty();

        try {

            Map out = simpleJdbcCall.execute(in);

            if (out != null) {
                Book book = new Book();
                book.setId(id);
                book.setName((String) out.get("O_NAME"));
                book.setPrice((BigDecimal) out.get("O_PRICE"));
                result = Optional.of(book);
                logger.info("Found book id = {}, values={}", id, result);
            } else {
                logger.info("Not found book id = {}", id);
            }

        }  catch (Exception e) {
            // ORA-01403: no data found, or any java.sql.SQLException
            logger.error("Exception {}", e.getMessage());
        }

        return result;
    }

    /*
    this example call direct stored procedure in package
    and get OUT parameter sys refcursor
    PROCEDURE get_book_by_name(
    p_name IN BOOKS.NAME%TYPE,
    o_c_book OUT SYS_REFCURSOR);
     */
    public List<Book> findBookByName(String name) {
        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("book_pkg")
                .withProcedureName("get_book_by_name")
                .returningResultSet("o_c_book",
                        BeanPropertyRowMapper.newInstance(Book.class));

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("p_name", name);

        Map out = simpleJdbcCall.execute(paramaters);

        if (out == null) {
            logger.warn("Not found any book");
            return Collections.emptyList();
        } else {
            return (List) out.get("o_c_book");
        }
    }

    /*
    this example call direct stored procedure in package
    and get multiple OUT parameters include sys refcursor
    PROCEDURE get_book_by_name2(
    p_name IN BOOKS.NAME%TYPE,
    o_retcode OUT INT,
    o_retdesc OUT VARCHAR2,
    o_c_book OUT SYS_REFCURSOR);

    With BeanPropertyRowMapper, the column select may be different with entity
     */
    public List<Book> findBookByName2(String name) {
        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                //package name
                .withCatalogName("book_pkg")
                //calling function
                .withProcedureName("get_book_by_name2")
                .returningResultSet("o_c_book",
                        BeanPropertyRowMapper.newInstance(Book.class));

        //simpleJdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
        //simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("po_retcode", Types.INTEGER));
        //simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("po_retdesc", Types.VARCHAR));

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("p_name", name);

        //Map out = simpleJdbcCall.execute(paramaters);
        Map<String, Object> out = simpleJdbcCall.execute(paramaters);

        if (out == null) {
            logger.warn("Not found any book");
            return Collections.emptyList();
        } else {
            BigDecimal o_retcode = (BigDecimal)out.get("o_retcode");
            //cannot cast to Integer
            //Integer o_retcode = (Integer)out.get("o_retcode");
            String o_retdesc = (String)out.get("o_retdesc");
            logger.info("******o_retcode={}, o_retdesc={}", o_retcode, o_retdesc);
            return (List) out.get("o_c_book");
        }
    }

    /*
    this example call direct stored procedure in package
    and get multiple OUT parameters include sys refcursor
    PROCEDURE get_book_by_name2(
    p_name IN BOOKS.NAME%TYPE,
    o_retcode OUT INT,
    o_retdesc OUT VARCHAR2,
    o_c_book OUT SYS_REFCURSOR);
     */
    public List<Book> findBookByName3(String name) {
        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                //package name
                .withCatalogName("book_pkg")
                //calling function
                .withProcedureName("get_book_by_name3")
                .returningResultSet("o_c_book",
                        new BookRowMapper());

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("p_name", name);

        //Map out = simpleJdbcCall.execute(paramaters);
        Map<String, Object> out = simpleJdbcCall.execute(paramaters);

        if (out == null) {
            logger.warn("Not found any book");
            return Collections.emptyList();
        } else {
            BigDecimal o_retcode = (BigDecimal)out.get("o_retcode");
            //cannot cast to Integer
            //Integer o_retcode = (Integer)out.get("o_retcode");
            String o_retdesc = (String)out.get("o_retdesc");
            logger.info("******o_retcode={}, o_retdesc={}", o_retcode, o_retdesc);
            return (List) out.get("o_c_book");
        }
    }

    /*
    call function and get return value
    with Oracle, function can have Out parameter, currently, cannot get value of out parameter
     */
    public BigDecimal findBookPrice(Long id) {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("book_pkg")
                //calling function
                .withFunctionName("fn_get_price_by_id")
                .withReturnValue();

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", 3L);
        BigDecimal price = simpleJdbcCall.executeFunction(BigDecimal.class, in);
        logger.info("Book id: {}, Price: {}", id, price);
        return price;
    }
}
