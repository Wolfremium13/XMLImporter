import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import models.Company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class BatchXMLImporter {

    public void importXMLintoDatabase(String folderPath) {
        List<Path> paths = getAllXMLFilesFromFolderPath(folderPath);
        List<Company> companies = getCompanies(paths);
        PostgresConnection connection = new PostgresConnection();
        companies.forEach(connection::insertCompany);
        System.out.println("ok");
    }

    private List<Company> getCompanies(List<Path> paths) {
        return paths.stream()
                .map(path -> {
                    try {
                        return getCompany(path);
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private Company getCompany(Path path) throws JAXBException {
        File file = new File(path.toString());
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Company) jaxbUnmarshaller.unmarshal(file);
    }

    public List<Path> getAllXMLFilesFromFolderPath(String folderPath) {
        try {
            return Files.walk(Path.of(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(filePath ->
                            filePath.toString()
                                    .endsWith(".xml"))
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return List.of();
    }

}
