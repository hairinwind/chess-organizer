package my.chess.organizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PairedGame {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

//    private int gameResult;
//    private boolean player1Absense;
//    private boolean player2Absense;
//    private boolean player3Absense;
//    private boolean player4Absense;

}
