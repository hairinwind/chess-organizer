package my.chess.organizer.file;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import my.chess.organizer.model.PairedGameCsv;
import my.chess.organizer.model.Player;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFileUtils {

    public static List<Player> readPlayers() {
        Path path = FileSystems.getDefault().getPath("data", "players.csv");
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<Player> cb = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .build();
            return cb.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePairedGameCsv(File csvFile, List<PairedGameCsv> pairedGameCsvs) {
        try (Writer writer  = new FileWriter(csvFile)) {

            HeaderColumnNameMappingStrategy<PairedGameCsv> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(PairedGameCsv.class);
            strategy.setColumnOrderOnWrite(new OrderedComparator(PairedGameCsv.predefinedOrder()));

            StatefulBeanToCsv<PairedGameCsv> sbc = new StatefulBeanToCsvBuilder<PairedGameCsv>(writer)
                    .withMappingStrategy(strategy)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(pairedGameCsvs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
