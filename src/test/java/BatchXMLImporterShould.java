import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchXMLImporterShould {

    final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    private final PostgresConnection connection = new PostgresConnection();
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {
        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));

    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void print_all_staff_ids_and_contains_one() {
        new BatchXMLImporter().importXMLintoDatabase(path);
        assertThat(systemOutContent.toString().contains("1002")).isTrue();
    }

    @Test
    public void insert_companies_from_xml_files() {
        connection.clearTables();
        new BatchXMLImporter().importXMLintoDatabase(path);
        assertThat(systemOutContent.toString().contains("ok")).isTrue();
    }

    @Test
    public void get_companies_from_database() {
        var companies = connection.getAllCompanies();
        assertThat(companies).isNotEmpty();
    }
}
