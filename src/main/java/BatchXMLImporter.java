import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import xmlmodels.Company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BatchXmlImporter {

    private final PostgresDao dao;
    private final FileFinder finder;

    BatchXmlImporter(PostgresDao dao, FileFinder finder) {
        this.dao = dao;
        this.finder = finder;
    }


    public void importFiles(String folderPath) throws IOException, JAXBException {
        List<Company> companies = getCompanies(finder.getAllXMLPathsFrom(folderPath));
        companies.forEach(dao::insertCompany);
    }

    private List<Company> getCompanies(List<Path> paths) throws JAXBException {
        ArrayList<Company> companies = new ArrayList<>();
        for (Path path : paths) {
            companies.add(parseCompanyFrom(path));
        }
        return companies;
    }

    private Company parseCompanyFrom(Path path) throws JAXBException {
        File file = new File(path.toString());
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Company) jaxbUnmarshaller.unmarshal(file);
    }

}
