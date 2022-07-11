package my.chess.organizer.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.util.List;

@Data
public class PairedGameCsv {
    @CsvBindByName(column = "player1Id")
//    @CsvBindByPosition(position = 0)
    private int player1Id;
    @CsvBindByName(column = "player2Id")
//    @CsvBindByPosition(position = 1)
    private int player2Id;
    @CsvBindByName(column = "player3Id")
//    @CsvBindByPosition(position = 2)
    private int player3Id;
    @CsvBindByName(column = "player4Id")
//    @CsvBindByPosition(position = 3)
    private int player4Id;
    @CsvBindByName(column = "player1Code")
//    @CsvBindByPosition(position = 4)
    private String player1Code;
    @CsvBindByName(column = "player2Code")
//    @CsvBindByPosition(position = 5)
    private String player2Code;
    @CsvBindByName(column = "player3Code")
//    @CsvBindByPosition(position = 6)
    private String player3Code;
    @CsvBindByName(column = "player4Code")
//    @CsvBindByPosition(position = 7)
    private String player4Code;
    @CsvBindByName(column = "player1Name")
//    @CsvBindByPosition(position = 8)
    private String player1Name;
    @CsvBindByName(column = "player2Name")
//    @CsvBindByPosition(position = 9)
    private String player2Name;
    @CsvBindByName(column = "player3Name")
//    @CsvBindByPosition(position = 10)
    private String player3Name;
    @CsvBindByName(column = "player4Name")
//    @CsvBindByPosition(position = 11)
    private String player4Name;
    @CsvBindByName(column = "result")
//    @CsvBindByPosition(position = 12)
    private int result; //1 playe1/2 win, 0 draw, -1 player1/2 lose
    @CsvBindByName(column = "player1Absence")
//    @CsvBindByPosition(position = 13)
    private boolean player1Absence;
    @CsvBindByName(column = "player2Absence")
//    @CsvBindByPosition(position = 14)
    private boolean player2Absence;
    @CsvBindByName(column = "player3Absence")
//    @CsvBindByPosition(position = 15)
    private boolean player3Absence;
    @CsvBindByName(column = "player4Absence")
//    @CsvBindByPosition(position = 16)
    private boolean player4Absence;

    public static PairedGameCsv fromPairedGame(PairedGame pairedGame) {
        PairedGameCsv PairedGameCsvRow = new PairedGameCsv();
        PairedGameCsvRow.setPlayer1Id(pairedGame.getPlayer1().getId());
        PairedGameCsvRow.setPlayer1Code(pairedGame.getPlayer1().getCode());
        PairedGameCsvRow.setPlayer1Name(pairedGame.getPlayer1().getName());
        PairedGameCsvRow.setPlayer2Id(pairedGame.getPlayer2().getId());
        PairedGameCsvRow.setPlayer2Code(pairedGame.getPlayer2().getCode());
        PairedGameCsvRow.setPlayer2Name(pairedGame.getPlayer2().getName());
        PairedGameCsvRow.setPlayer3Id(pairedGame.getPlayer3().getId());
        PairedGameCsvRow.setPlayer3Code(pairedGame.getPlayer3().getCode());
        PairedGameCsvRow.setPlayer3Name(pairedGame.getPlayer3().getName());
        PairedGameCsvRow.setPlayer4Id(pairedGame.getPlayer4().getId());
        PairedGameCsvRow.setPlayer4Code(pairedGame.getPlayer4().getCode());
        PairedGameCsvRow.setPlayer4Name(pairedGame.getPlayer4().getName());
        return PairedGameCsvRow;
    }

    public static List<String> predefinedOrder() {
        return List.of( "player1Id", "player2Id","player3Id", "player4Id",
                        "player1Code", "player2Code","player3Code", "player4Code",
                        "player1Name", "player2Name","player3Name", "player4Name",
                        "result",
                        "player1Absence", "player2Absence","player3Absence", "player4Absence");
    }
}
