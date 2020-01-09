import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    void save(Resume r) {
        if (uuidIsNull(r.uuid)) {
            return;
        }
        storage[counter++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (uuidIsNull(uuid)) return;
        int index = -1;
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("This resume wasn't found!");
            return;
        }
        for (int i = index; i < counter; i++) {
            storage[i] = storage[i + 1];
            }
        counter--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage)
                .limit(counter)
                .toArray(Resume[]::new);
    }

    int size() {
        return counter;
    }

    private boolean uuidIsNull(String uuid) {
        if (Objects.isNull(uuid)) {
            System.out.println("Wrong uuid!");
            return true;
        }
        return false;
    }
}
