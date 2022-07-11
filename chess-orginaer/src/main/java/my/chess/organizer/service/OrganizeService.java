package my.chess.organizer.service;

import lombok.extern.slf4j.Slf4j;
import my.chess.organizer.file.CsvFileUtils;
import my.chess.organizer.model.PairedGame;
import my.chess.organizer.model.PairedGameCsv;
import my.chess.organizer.model.Player;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static my.chess.organizer.file.CsvFileUtils.writePairedGameCsv;

@Service
@Slf4j
public class OrganizeService {

    private PairHistoryService pairHistoryService;

    private List<Player> players;

    public OrganizeService() {
        // read players
        log.info("constructor of OrganizeService, read players");
        players = CsvFileUtils.readPlayers();

        pairHistoryService = new PairHistoryService(new File("data/history"));
    }

    public void createGames(int round) {
        List<Integer> playerIds = players.stream().map(Player::getId).collect(Collectors.toList());

        for (int i = 0; i < round; i++) {
            List<PairedGame> pairedGame = createGamesInternal(playerIds);

            // write games into csv
            List<PairedGameCsv> pairedGameCsv = pairedGame.stream()
                    .map(PairedGameCsv::fromPairedGame)
                    .collect(Collectors.toList());

            String fileName = "game-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +"-"+(i+1)+".csv";
            File file = new File("data/games/" + fileName);
            writePairedGameCsv(file, pairedGameCsv);
        }
    }

    private List<PairedGame> createGamesInternal(List<Integer> playerIds) {
        // read paired history
        Map<Integer, List<Integer>> notPairedPlayerFromHistory = pairHistoryService.getNotPairedPlayerFromHistory(playerIds);

        // create paired games
        return createPairedGame(players, notPairedPlayerFromHistory);
    }

    private List<PairedGame> createPairedGame(List<Player> players, Map<Integer, List<Integer>> availablePlayerFromHistory) {
        List <Player> activePlayers = players.stream()
                .filter(Player::isActive)
                .collect(Collectors.toList());

        // sort player by matchPlayed and score
        List<Player> sortedPlayers = sortByMatchPlayedAndScore(activePlayers);

        List<Player[]> pairedPlayersList = new ArrayList<>();
        List<Player> playersPicked = new ArrayList<>();
        if (sortedPlayers.size() < 4) {
            log.info("sortedPlayer size < 4, cannot make any paired game");
        }
        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player1 = sortedPlayers.get(i);
            if (playersPicked.contains(player1)) {
                continue;
            }
            Player[] pairedPlayer = new Player[2];
            for (int j = i +1; j < sortedPlayers.size(); j++) {
                Player player2 = sortedPlayers.get(j);
                if (playersPicked.contains(player2)) {
                    continue;
                }
                if (availablePlayerFromHistory.get(player1.getId()).contains(player2.getId())) {
                    pairedPlayer[0] = player1;
                    pairedPlayer[1] = player2;
                    playersPicked.add(player1);
                    playersPicked.add(player2);
                    pairedPlayersList.add(pairedPlayer);
                    pairHistoryService.updatePairedPlayer(player1.getId(), player2.getId());
                    players.stream()
                            .filter(player -> player.getId() == player1.getId() || player.getId() == player2.getId())
                            .forEach(player -> player.setMatchPlayed(player.getMatchPlayed() + 1));
                    break;
                }
            }
            if (pairedPlayer[1] == null) {
                playersPicked.add(player1);
            }
        }

        return createPairedGamesFromPairedPlayers(pairedPlayersList);
    }

    private List<PairedGame> createPairedGamesFromPairedPlayers(List<Player[]> pairedPlayersList) {
        List<PairedGame> pairedGames = new ArrayList<>();
        for (int i = 0; i < pairedPlayersList.size(); i = i+2) {
            if (i+2 > pairedPlayersList.size()) break;
            Player[] pair1 = pairedPlayersList.get(i);
            Player[] pair2 = pairedPlayersList.get(i+1);
            PairedGame pairedGame = new PairedGame(pair1[0], pair1[1], pair2[0], pair2[1]);
            pairedGames.add(pairedGame);
        }
        return pairedGames;

    }

    private List<Player> sortByMatchPlayedAndScore(List<Player> players) {
        Comparator<Player> compareByMatchPlayedAndScore = Comparator
                .comparing(Player::getMatchPlayed)
                .thenComparing(Player::getScore);
        return players.stream()
                .sorted(compareByMatchPlayedAndScore)
                .collect(Collectors.toList());
    }

    public void updateGameResults() {
        // read games
        // update player data
        // archive data file
    }
}
