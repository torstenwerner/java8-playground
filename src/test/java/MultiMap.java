import java.util.*;

public class MultiMap<KeyType, ValueType> {
    private final Map<KeyType, List<ValueType>> map = new HashMap<>();

    private static <ValueType> List<ValueType> join(List<ValueType> left, List<ValueType> right) {
        left.addAll(right);
        return left;
    }

    public void add(KeyType key, ValueType value) {
        map.merge(key, Arrays.asList(value), MultiMap::join);
    }

    public List<ValueType> get(KeyType key) {
        return map.get(key);
    }
}
