package iut.java.spring.tests.integration;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.repository.IIndividuRepository;
import iut.java.spring.service.interfaces.IIndividuService;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.h2.test.properties")
class IndividuServiceTest {
    private IDatabaseTester tester;

    @Autowired
    private IIndividuService service;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void init() throws Exception {
        tester = new DataSourceDatabaseTester(dataSource);
        InputStream is = getClass().getClassLoader().getResourceAsStream("ALMONTE--RINGAUD_Mikkel.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    void testGetList() throws Exception {
        // ACT
        List<IndividuDto> result = service.getList();

        // ASSERT
        assertThat(result)
            .isNotNull()
            .hasSize(10)
            .extracting(IndividuDto::getLastName)
            .containsExactlyInAnyOrder(
                "Scourge",
                "Beastall",
                "Saph",
                "Tytler",
                "Lantuff",
                "Oswald",
                "Bailess",
                "Dalli",
                "Eason",
                "Snoxall"
            );
    }
}
