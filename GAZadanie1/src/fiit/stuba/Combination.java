package fiit.stuba;

import java.util.Objects;
import java.util.Set;

public class Combination {
    private Integer usages;
    private Set<Vertex> vertexSet;

    Combination(Set<Vertex> vertexSet) {
        this.vertexSet = vertexSet;
        this.usages = 0;
    }

    public Integer getUsages() {
        return usages;
    }

    public Set<Vertex> getVertexSet() {
        return vertexSet;
    }

    public void increaseUsages() {
        usages++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination that = (Combination) o;
        return vertexSet.equals(that.vertexSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexSet);
    }
}
