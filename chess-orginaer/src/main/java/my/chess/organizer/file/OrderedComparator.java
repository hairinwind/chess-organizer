package my.chess.organizer.file;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderedComparator implements Comparator<String> {

    private List<String> predefinedOrder;

    public OrderedComparator(List<String> predefinedOrder) {
        this.predefinedOrder = predefinedOrder.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    @Override
    public int compare(String o1, String o2) {
        return predefinedOrder.indexOf(o1.toLowerCase()) - predefinedOrder.indexOf(o2.toLowerCase(Locale.ROOT));
    }
}
