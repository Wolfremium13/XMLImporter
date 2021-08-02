import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllXMLFilesFromFolderPathShould {
    final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    final String nonExistentPath = path + File.separator + "NotFound";

    @Test
    public void read_only_xml_files_from_folder_path() {
        List<Path> paths = new BatchXMLImporter().getAllXMLFilesFromFolderPath(path);
        paths.forEach(System.out::println);
        assertThat(paths.size()).isEqualTo(2);
    }

    @Test
    public void retrieve_empty_list_in_error_case() {
        List<Path> paths = new BatchXMLImporter().getAllXMLFilesFromFolderPath(nonExistentPath);
        paths.forEach(System.out::println);
        assertThat(paths).isEmpty();
    }
}
