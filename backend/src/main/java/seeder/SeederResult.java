package seeder;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SeederResult {
    private final String moduleName;
    private int added;
    private int skipped;
    private int failed;
    private final List<String> errors;

    public SeederResult(String moduleName){
        this.moduleName = moduleName;
        this.errors = new ArrayList<>();
    }

    public SeederResult(){
        this.moduleName = "";
        this.errors = new ArrayList<>();
    }

    /**Increments failed data count if error occurred*/
    public void iFailed(String msg) {
        failed++;
        this.errors.add(msg);
    }

    /**Increments seeded data count*/
    public void iAdded() {
        added++;
    }

    /**Increments skipped data count if already found*/
    public void iSkipped() {
        skipped++;
    }

    public void merge(SeederResult other) {
        if (other == null) {
            return;
        }
        this.added += other.added;
        this.skipped += other.skipped;
        this.failed += other.failed;
        this.errors.addAll(other.errors);
    }

    @Override
    public String toString() {
        return "Seeded: " + moduleName + " {" +
                "added=" + added +
                ", skipped=" + skipped +
                ", failed=" + failed +
                ", errors=" + errors +
                '}';
    }
}
