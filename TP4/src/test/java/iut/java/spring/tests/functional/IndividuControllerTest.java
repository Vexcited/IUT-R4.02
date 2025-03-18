package iut.java.spring.tests.functional;

import iut.java.spring.dto.IndividuDto;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.io.InputStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application.h2.test.properties")
class IndividuControllerTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    private DataSource dataSource;

    private IDatabaseTester tester;

    @BeforeEach
    void init() throws Exception {
        tester = new DataSourceDatabaseTester(dataSource);
        InputStream is = getClass().getClassLoader().getResourceAsStream("ALMONTE--RINGAUD_Mikkel.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    void testGet() throws Exception {
        // ACT
        IndividuDto result = client.get().uri("/individu/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(IndividuDto.class)
            .returnResult().getResponseBody();

        // ASSERT
        InputStream is = getClass().getClassLoader().getResourceAsStream("ALMONTE--RINGAUD_Mikkel.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
        ITable expectedTable = expectedDataSet.getTable("Individu");

        IDataSet databaseDataSet = new DatabaseDataSourceConnection(dataSource).createDataSet();
        ITable actualTable = databaseDataSet.getTable("Individu");

        assertThat(result)
            .isNotNull()
            .satisfies(individu -> {
                assertThat(individu.getId()).isEqualTo(1L);
                assertThat(individu.getFirstName()).isEqualTo("Rikki");
                assertThat(individu.getLastName()).isEqualTo("Scourge");
                assertThat(individu.getTitle()).isEqualTo("Honorable");
                assertThat(individu.getHeight()).isEqualTo(175);
                assertThat(individu.getBirthDate()).isEqualTo(LocalDate.of(1996, 9, 20));
            });

        assertEquals(expectedTable, actualTable);
    }

    @Test
    void testRemove() throws Exception {
        // ACT
        client.delete().uri("/individu/1")
            .exchange()
            .expectStatus().isOk();

        // ASSERT
        IDataSet databaseDataSet = new DatabaseDataSourceConnection(dataSource).createDataSet();
        ITable actualTable = databaseDataSet.getTable("Individu");

        InputStream is = getClass().getClassLoader().getResourceAsStream("expected-after-remove.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
        ITable expectedTable = expectedDataSet.getTable("Individu");

        assertEquals(expectedTable, actualTable);
    }
    @Test
    void testAdd() throws Exception {
        // ARRANGE
        IndividuDto newIndividu = new IndividuDto();
        newIndividu.setFirstName("John");
        newIndividu.setLastName("Doe");
        newIndividu.setTitle("Mr");
        newIndividu.setHeight(180);
        newIndividu.setBirthDate(LocalDate.of(2000, 1, 1));

        // ACT
        IndividuDto result = client.post().uri("/individu")
                .bodyValue(newIndividu)
                .exchange()
                .expectStatus().isOk()
                .expectBody(IndividuDto.class)
                .returnResult()
                .getResponseBody();

        // ASSERT
        assertThat(result)
            .isNotNull()
            .satisfies(individu -> {
                assertThat(individu.getId()).isNotNull();
                assertThat(individu.getFirstName()).isEqualTo("John");
                assertThat(individu.getLastName()).isEqualTo("Doe");
                assertThat(individu.getTitle()).isEqualTo("Mr");
                assertThat(individu.getHeight()).isEqualTo(180);
                assertThat(individu.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
            });

        IDataSet databaseDataSet = new DatabaseDataSourceConnection(dataSource).createDataSet();
        ITable actualTable = databaseDataSet.getTable("Individu");

        InputStream is = getClass().getClassLoader().getResourceAsStream("expected-after-add.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
        ITable expectedTable = expectedDataSet.getTable("Individu");
        ReplacementTable replacementTable = new ReplacementTable(expectedTable);
        replacementTable.addReplacementObject("[ID]", result.getId());

        assertEquals(replacementTable, actualTable);
    }

    @Test
    void testModifyFound() throws Exception {
        // ARRANGE
        IndividuDto modifiedIndividu = new IndividuDto();
        modifiedIndividu.setId(1L);
        modifiedIndividu.setFirstName("Jane");
        modifiedIndividu.setLastName("Smith");
        modifiedIndividu.setTitle("Dr");
        modifiedIndividu.setHeight(165);
        modifiedIndividu.setBirthDate(LocalDate.of(1990, 1, 1));

        // ACT
        client.put().uri("/individu")
            .bodyValue(modifiedIndividu)
            .exchange()
            .expectStatus().isOk();

        // ASSERT
        IDataSet databaseDataSet = new DatabaseDataSourceConnection(dataSource).createDataSet();
        ITable actualTable = databaseDataSet.getTable("Individu");

        InputStream is = getClass().getClassLoader().getResourceAsStream("expected-after-modify.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
        ITable expectedTable = expectedDataSet.getTable("Individu");

        assertEquals(expectedTable, actualTable);
    }

    @Test
    void testModifyNotFound() throws Exception {
        // ARRANGE
        IndividuDto modifiedIndividu = new IndividuDto();
        modifiedIndividu.setId(12L);
        modifiedIndividu.setFirstName("Jane");
        modifiedIndividu.setLastName("Smith");
        modifiedIndividu.setTitle("Dr");
        modifiedIndividu.setHeight(165);
        modifiedIndividu.setBirthDate(LocalDate.of(1990, 1, 1));

        // ACT
        client.put().uri("/individu")
                .bodyValue(modifiedIndividu)
                .exchange()
                .expectStatus().isNotFound();

        // ASSERT
        IDataSet databaseDataSet = new DatabaseDataSourceConnection(dataSource).createDataSet();
        ITable actualTable = databaseDataSet.getTable("Individu");

        InputStream is = getClass().getClassLoader().getResourceAsStream("ALMONTE--RINGAUD_Mikkel.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
        ITable expectedTable = expectedDataSet.getTable("Individu");

        assertEquals(expectedTable, actualTable);
    }
}
