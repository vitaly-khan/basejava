import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private AtomicInteger counter = new AtomicInteger(0);

    void clear() {
        Arrays.fill(storage, null); //faster than: storage = new Resume[10000]???
        counter.set(0);
    }

    void save(Resume r) {
        if (uuidIsNull(r.uuid)) return;
        storage[counter.getAndIncrement()] = r;
    }

    Resume get(String uuid) {
        if (counter.get() == 0 || uuidIsNull(uuid))
            return null;
        for (Resume resume : storage) {
            if (resume == null)
                return null;
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (uuidIsNull(uuid)) return;
        int index = -1;
        for (int i = 0; i < counter.get(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("This resume wasn't found!");
            return;
        }
        for (int i = index; i < counter.get(); i++) {
            storage[i] = storage[i + 1];
        }
        counter.decrementAndGet();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage)
                .limit(counter.get())
                .toArray(Resume[]::new);
    }

    int size() {
        return counter.get();

    }

    private boolean uuidIsNull(String uuid) {
        if (Objects.isNull(uuid)) {
            System.out.println("Wrong uuid!");
            return true;
        }
        return false;
    }
}
