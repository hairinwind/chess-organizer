package my.chess.organizer.service;

import my.chess.organizer.file.BinaryFileUtils;

import java.io.File;
import java.util.*;

//@Service
public class PairHistoryService {
    // use bit in each Integer to remember the pair history
    // one integer can save 32 players paired history
    // 0 means not paired before
    // 1 means paired already
    private Map<Integer, List<Integer>> pairHistoryMap;
    private File file;

    public PairHistoryService(File historyFile) {
        this.file = historyFile;
    }

    public Map<Integer, List<Integer>> getNotPairedPlayerFromHistory(List<Integer> playerIds) {
        Map<Integer, List<Integer>> pairHistoryMap = readHistory(playerIds);
        Map<Integer, List<Integer>> availablePlayers = new HashMap<>();
        for (Integer playerId : playerIds) {
            List<Integer> availablePlayerIds = getAvailablePlayerIds(pairHistoryMap.get(playerId));
            availablePlayers.put(playerId, availablePlayerIds);
        }
        return availablePlayers;
    }

    public void updatePairedPlayer(int player1Id, int player2Id) {
        updatePairedPlayerInternal(player1Id, player2Id);
        updatePairedPlayerInternal(player2Id, player1Id);
    }

    private void updatePairedPlayerInternal(int player1Id, int player2Id) {
        List<Integer> historyValues = pairHistoryMap.get(player1Id);
        int index = player2Id / 32;
        int historyValue = historyValues.get(index);
        int position = player2Id % 32 - 1;
        int newHistoryValue = setBitOne(historyValue, position);
        historyValues.set(index, newHistoryValue);
    }

    /**
     * read history and check if new user is added, initialize the history for that user
     * @param playerIds
     * @return
     */
    private Map<Integer, List<Integer>> readHistory(List<Integer> playerIds) {
        if (pairHistoryMap == null) {
            pairHistoryMap = BinaryFileUtils.readFile(file, Map.class);
        }
        if (pairHistoryMap == null) {
            pairHistoryMap = new LinkedHashMap<>();
        }
        // check the history containes all PlayerIds
        for (Integer playerId : playerIds) {
            if (!pairHistoryMap.containsKey(playerId)) {
                initializePlayerHistory(pairHistoryMap, playerId, playerIds.size());
            }
        }
        return pairHistoryMap;
    }

    private void initializePlayerHistory(Map<Integer, List<Integer>> historyMap, Integer playerId, int totalPlayer) {
        List<Integer> history = new ArrayList<>();
        int playerCount = 0;
        while (playerCount < totalPlayer) {
            int minPlayerId = playerCount + 1;
            int maxPlayerId = playerCount + 32;
            int historyValue = 0;
            if (playerId >= minPlayerId && playerId <= maxPlayerId) {
                int position = playerId - minPlayerId;
                historyValue = setBitOne(historyValue, position);
            }
            history.add(historyValue);
            playerCount += 32;
        }
        historyMap.put(playerId, history);
    }

    private List<Integer> getAvailablePlayerIds(List<Integer> historyValues) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < historyValues.size(); i++) {
            int startPlayerId = i * 32;
            int historyValue = historyValues.get(i);
            for (int j = 0; j < 32; j++) {
                if (getBit(historyValue, j) == 0) {
                    result.add(startPlayerId + j + 1);
                }
            }
        }
        return result;
    }

//    public Map<String, List<Integer>> initialize(List<String> playerIds) {
//        int totalPlayer = playerIds.size();
//
//        for (String playerId : playerIds) {
//            initializePlayerHistory(pairHistoryMap, playerId, totalPlayer);
//        }
//        return pairHistoryMap;
//    }

    /**
     * get the bit of the position in int value
     * @param value
     * @param position 0 is the lowest position
     * @return getBit(5,0) returns 1, gitBit(5,1) returns 0, getBit(5,2) returns 1
     */
    private static int getBit(int value, int position) {
        return (value >> position) & 1;
    }

    /**
     * set 1 on position of int value
     * @param value
     * @param position 0 would set 1 on lowest place
     * @return setBitOne(0, 0) return 1
     */
    private static int setBitOne(int value, int position) {
        return (1 << position) | value;
    }
}
