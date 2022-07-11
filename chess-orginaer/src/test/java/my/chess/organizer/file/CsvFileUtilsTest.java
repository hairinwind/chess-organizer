package my.chess.organizer.file;

import my.chess.organizer.model.PairedGameCsv;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class CsvFileUtilsTest {

    @Test
    public void testWriteGameCsv() {
        PairedGameCsv pairedGameCsv = new PairedGameCsv();
        pairedGameCsv.setPlayer1Id(1);
        pairedGameCsv.setPlayer1Code("A001");
        pairedGameCsv.setPlayer1Name("Player1");
        pairedGameCsv.setPlayer2Id(2);
        pairedGameCsv.setPlayer2Code("A002");
        pairedGameCsv.setPlayer2Name("Player2");
        pairedGameCsv.setPlayer3Id(3);
        pairedGameCsv.setPlayer3Code("A003");
        pairedGameCsv.setPlayer3Name("Player3");
        pairedGameCsv.setPlayer4Id(4);
        pairedGameCsv.setPlayer4Code("A004");
        pairedGameCsv.setPlayer4Name("Player4");
        List<PairedGameCsv> pairedGameCsvs = List.of(pairedGameCsv);
        File file = new File("src/test/resources/games-test.csv");
        CsvFileUtils.writePairedGameCsv(file, pairedGameCsvs);
    }
}