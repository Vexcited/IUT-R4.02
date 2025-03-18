package iut.java.service;

import iut.java.dto.IndividuDto;
import iut.java.utils.IndividuUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;

public class IndividuService {
    private String fileName;
    private List<IndividuDto> individusList;

    /**
     * Contructeur utilisant un nom de fichier dans le classpath Java
     * @param fileName Nom du fichier dans le classpath
     */
    public IndividuService(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Charge les individus contens dans le fichier CSV du classpath
     */
    private void loadIndividus() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            individusList = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                if (!line.trim().isEmpty() &&
                        !line.startsWith("id,first_name,last_name,title,height,birth_date")) {

                    individusList.add(IndividuUtils.getIndividu(line));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retourne la liste des individus
     * @return La liste des individus
     */
    public List<IndividuDto> getIndividusList() {
        if (individusList == null) {
            loadIndividus();
        }
        return individusList;
    }

    /**
     * Retourne la répartition des titres
     * @return Un objet map dont la clé est le titre et dont la valeur est le nombre d'individus avec ce titre
     */
    public Map<String, Long> getTitleRepartition() {
        return getIndividusList().stream().map(IndividuDto::getTitle)
                .collect(Collectors.groupingBy(identity(), counting()));
    }
}
