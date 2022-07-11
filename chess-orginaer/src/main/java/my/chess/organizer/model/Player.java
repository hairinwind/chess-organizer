package my.chess.organizer.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Player {

    @CsvBindByName(column = "Id")
    private int id;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "status")
    private String status; // null or empty is active

    @CsvBindByName(column = "score")
    private int score;

    @CsvBindByName(column = "matchPlayed")
    private int matchPlayed;

    public boolean isActive() {
        return StringUtils.isBlank(status);
    }

}
