package my.chess.organizer.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PairHistoryServiceTest {

    @Test
    public void testNewPlayersHistoryAdded() {
        PairHistoryService  pairHistoryService = new PairHistoryService(new File("src/test/resources", "history0"));
        // history0 does not exist
        // test the history is automatically added
        List<Integer> playerIds = Arrays.asList(1,2);
        Map<Integer, List<Integer>> notPairedPlayerFromHistory = pairHistoryService.getNotPairedPlayerFromHistory(playerIds);

        List<Integer> notPairedPlayersForPlayer1 = notPairedPlayerFromHistory.get(1);
        assertFalse(notPairedPlayersForPlayer1.contains(1));
        assertEquals(31, notPairedPlayersForPlayer1.size());

        List<Integer> notPairedPlayersForPlayer2 = notPairedPlayerFromHistory.get(2);
        assertFalse(notPairedPlayersForPlayer2.contains(2));
        assertEquals(31, notPairedPlayersForPlayer2.size());
    }

    @Test
    public void testPlayersMoreThan32() {
        PairHistoryService  pairHistoryService = new PairHistoryService(new File("src/test/resources", "history0"));

        List<Integer> playerIds = IntStream.range(1, 34).boxed().collect(Collectors.toList());
        Map<Integer, List<Integer>> notPairedPlayerFromHistory = pairHistoryService.getNotPairedPlayerFromHistory(playerIds);

        assertEquals(33, notPairedPlayerFromHistory.size());
        List<Integer> notPairedPlayersForPlayer1 = notPairedPlayerFromHistory.get(1);
        assertFalse(notPairedPlayersForPlayer1.contains(1));
        for (int i = 2; i < 65; i++) {
            assertTrue(notPairedPlayersForPlayer1.contains(i));
        }
        assertEquals(63, notPairedPlayersForPlayer1.size());
    }

    @Test
    public void testUpdatePairedPlayers() {
        PairHistoryService  pairHistoryService = new PairHistoryService(new File("src/test/resources", "history0"));

        List<Integer> playerIds = IntStream.range(1, 34).boxed().collect(Collectors.toList());
        pairHistoryService.getNotPairedPlayerFromHistory(playerIds);

        pairHistoryService.updatePairedPlayer(1,33);

        Map<Integer, List<Integer>> notPairedPlayerFromHistory = pairHistoryService.getNotPairedPlayerFromHistory(playerIds);
        assertFalse(notPairedPlayerFromHistory.get(1).contains(33));
        assertFalse(notPairedPlayerFromHistory.get(33).contains(1));
    }

//
//
//    public static void main(String[] args) {
////        Integer a = new Integer(5);
////        System.out.println(a);
////        System.out.println(Integer.toBinaryString(a));
////        for(int i=0;i<65; i++){
////            System.out.println("bit index: " + i);
////            System.out.println(getBit(a, i));
////        }
////
////        System.out.println(setBitOne(3, 2));
////        System.out.println(setBitOne(0, 0));
////        System.out.println(setBitOne(4, 0));
//
//        List<Player> players = CsvFileUtils.readPlayers();
//        List<String> playerIds = players.stream().map(Player::getId).collect(Collectors.toList());
//        PairHistoryService pairHistory = new PairHistoryService();
//        Map<String, List<Integer>> historyMap = pairHistory.initialize(playerIds);
//        print(historyMap);
//
////        Path path = FileSystems.getDefault().getPath("data", "history");
//        File file = new File("data", "history");
//        BinaryFileUtils.writeFile(historyMap, file);
//
//        Map<String, List<Integer>> historyMapFromFile = BinaryFileUtils.readFile(file,  Map.class);
//        print(historyMapFromFile);
//    }
//
//    private static void print(Map<String, List<Integer>> historyMap) {
//        for(String key: historyMap.keySet()) {
//            System.out.println("player " + key);
////            for (Integer history : historyMap.get(key)) {
////                System.out.println(Integer.toBinaryString(history));
////            }
//            System.out.println(historyMap.get(key));
//        }
//    }

}