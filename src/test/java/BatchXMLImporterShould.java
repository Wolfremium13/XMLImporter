import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchXmlImporterShould {

    final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    @Test
    public void import_xml_into_database() throws JAXBException, IOException {
        PostgresDao dao = new PostgresDao();
        FileFinder finder = new FileFinder();
        BatchXmlImporter batchXmlImporter = new BatchXmlImporter(dao, finder);
        dao.clearTables();

        batchXmlImporter.importFiles(path);

        var companies = dao.getAllCompanies();
        assertThat(companies).hasSize(2);
    }
}
