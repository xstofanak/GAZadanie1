package fiit.stuba;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
public class Main {
    private static final GraphBuilder graphBuilder = new GraphBuilder();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // inputs
        System.out.print("Enter degree: ");
        Integer degree = scanner.nextInt();
        System.out.print("Enter number of graphs to be found: ");
        Integer graphsLimit = scanner.nextInt();
        // searching for graphs
        int counter = 0;
        Set<Graph> foundGraphs = new HashSet<>();
        while(counter < graphsLimit) {
            Graph graph = findGraph(degree);
            if(graph != null) {
                foundGraphs.add(graph);
                counter++;
            }
        }
        // printing of found graphs
        AtomicInteger graphCounter = new AtomicInteger();
        List<Integer> partitionIds = new ArrayList<>(Arrays.asList(0, 1, 2));
        foundGraphs.forEach(graph -> {
            System.out.println("Graph " + graphCounter.getAndIncrement() + ":");
            partitionIds.forEach(partitionId -> printVertices(graph.getVertexesOfPartition(partitionId), partitionId));
            System.out.println();
        });
    }

    private static void printVertices(List<Vertex> vertexList, int partitionId) {
        System.out.println("Partition " + partitionId + ":");
        vertexList.stream()
                .sorted(Comparator.comparingInt(Vertex::getID))
                .forEachOrdered(vertex -> {
                    System.out.print("(V" + vertex.getID() + "):\t");
                    vertex.getNeighbours().forEach(vertex1 -> System.out.print("(P" + vertex1.getPartition().getID()
                            + "V" + vertex1.getID() + ");"));
                    System.out.println();
                });
    }

    private static Graph findGraph(int degree) {
        // preparation
        Graph graph = graphBuilder.generateEmptyMaxGraph(degree);
        List<Vertex> partition0 = graph.getVertexesOfPartition(0);
        List<Vertex> partition1 = graph.getVertexesOfPartition(1);
        List<Vertex> partition2 = graph.getVertexesOfPartition(2);
        List<Combination> combinations1 = GraphUtils.createCombinations(partition1, degree);
        List<Combination> combinations2 = GraphUtils.createCombinations(partition2, degree);
        Collections.shuffle(combinations1);
        Collections.shuffle(combinations2);
        // building of graph
        for(Vertex srcVertex : partition0) {
            // partition 0 --> partition 1 and partition 2
            Combination combinationInP1 = findNextCombination(combinations1, graph.getPartitionById(0),
                    graph.getPartitionById(2), degree);
            Combination combinationInP2 = findNextCombination(combinations2, graph.getPartitionById(0),
                    graph.getPartitionById(1), degree);
            if(combinationInP1 != null && combinationInP2 != null) {
                combinationInP1.getVertexSet().forEach(vertex -> graph.addNeighbors(srcVertex, vertex));
                combinationInP2.getVertexSet().forEach(vertex -> graph.addNeighbors(srcVertex, vertex));
                combinationInP1.increaseUsages();
                combinationInP2.increaseUsages();
                // partition 1 <--> partition 2
                Map<Vertex, Combination> listOfCombinationsInP2 = findListOfCombinations(combinations2,
                        graph.getPartitionById(1), graph.getPartitionById(2), combinationInP1, degree, combinationInP2);
                if(listOfCombinationsInP2 != null) {
                    listOfCombinationsInP2.forEach((vertex, combination) ->
                            graph.addNeighbors(vertex, combination.getVertexSet()));
                    listOfCombinationsInP2.values().forEach(Combination::increaseUsages);
                } else {
                    return null;
                }
                Map<Vertex, Combination> listOfCombinationsInP1 = findListOfCombinations(combinations1,
                        graph.getPartitionById(2), graph.getPartitionById(1), combinationInP2, degree, combinationInP1);
                if(listOfCombinationsInP1 != null) {
                    listOfCombinationsInP1.forEach((vertex, combination) ->
                            graph.addNeighbors(vertex, combination.getVertexSet()));
                    listOfCombinationsInP1.values().forEach(Combination::increaseUsages);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return graph;
    }

    private static Map<Vertex, Combination> findListOfCombinations
            (List<Combination> combinationList, Partition srcPartition, Partition dstPartition,
             Combination srcCombination, int degree, Combination mirror) {
        List<List<Combination>> setOfOrders = srcCombination.getVertexSet().stream()
                .map(vertex -> combinationList.stream()
                        .filter(combination -> {
                            Set<Vertex> copy1 = new HashSet<>(combination.getVertexSet());
                            copy1.retainAll(mirror.getVertexSet());
                            boolean returnSign1 = combination.getVertexSet().stream()
                                    .allMatch(neighbor -> getDegreeToPartitionSource(vertex, neighbor, srcPartition)
                                            < degree) && copy1.isEmpty();
                            if(returnSign1) {
                                return combination.getVertexSet().containsAll(vertex.getNeighbours().stream()
                                        .filter(vertex1 -> vertex1.getPartition().equals(dstPartition) &&
                                                !mirror.getVertexSet().contains(vertex1))
                                        .collect(Collectors.toSet()));
                            } else {
                                return false;
                            }
                        })
                        .sorted((o1, o2) -> compareCombinations(o1, o2, srcPartition))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        Map<Vertex, Combination> finalMap = new HashMap<>();
        List<Vertex> srcVertexList = new ArrayList<>(srcCombination.getVertexSet());
        List<Integer> indexes = new ArrayList<>(Collections.nCopies(degree, -1));
        boolean endFlag = false;
        while(!endFlag) {
            c1 : for(int i = 0; i < setOfOrders.size(); i++) {
                List<Combination> opportunities = setOfOrders.get(i);
                Combination actualCombination = null;
                while(actualCombination == null || !checkIntersection(actualCombination, finalMap.values())) {
                    indexes.set(i, indexes.get(i) + 1);
                    if(indexes.get(i) < opportunities.size()) {
                        actualCombination = opportunities.get(indexes.get(i));
                    } else {
                        if(i != 0) {
                            // reset indexes to the next position
                            finalMap.clear();
                            indexes.set(i - 1, indexes.get(i - 1) + 1);
                            indexes.set(i, -1);
                            break c1;
                        } else {
                            return null;
                        }
                    }
                }
                finalMap.put(srcVertexList.get(i), actualCombination);
            }
            if(finalMap.size() == degree) {
                endFlag = true;
            }
        }
        return finalMap;
    }

    private static Combination findNextCombination(List<Combination> combinationList, Partition srcPartition,
                                                   Partition lastPartition, int degree) {
        return combinationList.stream()
                .filter(combination -> combination.getVertexSet().stream().allMatch(neighbor ->
                        getDegreeToPartition(neighbor, srcPartition) < degree))
                .sorted((o1, o2) -> compareCombinations(o1, o2, lastPartition))
                .findFirst()
                .orElse(null);
    }

    private static boolean checkIntersection(Combination actualCombination, Collection<Combination> values) {
        return values.stream()
                .allMatch(combination -> {
                    Set<Vertex> copy = new HashSet<>(combination.getVertexSet());
                    copy.retainAll(actualCombination.getVertexSet());
                    return copy.isEmpty();
                });
    }

    private static int getDegreeToPartition(Vertex vertex, Partition partition) {
        return (int)vertex.getNeighbours().stream()
                .filter(vertex1 -> vertex1.getPartition().equals(partition))
                .count();
    }

    private static int getDegreeToPartitionSource(Vertex srcVertex, Vertex vertex, Partition partition) {
        return (int)vertex.getNeighbours().stream()
                .filter(vertex1 -> vertex1.getPartition().equals(partition) && !vertex1.equals(srcVertex))
                .count();
    }

    private static int compareCombinations(Combination o1, Combination o2, Partition lastPartition) {
        // compare total count of neighbors (less is better)
        Integer neighborsCount1 = o1.getVertexSet().stream()
                .mapToInt(vertex -> (int)vertex.getNeighbours().stream()
                        .filter(vertex1 -> vertex1.getPartition().equals(lastPartition))
                        .count())
                .sum();
        Integer neighborsCount2 = o2.getVertexSet().stream()
                .mapToInt(vertex -> (int)vertex.getNeighbours().stream()
                        .filter(vertex1 -> vertex1.getPartition().equals(lastPartition))
                        .count())
                .sum();
        int comparisonResults1 = neighborsCount1.compareTo(neighborsCount2);
        if(comparisonResults1 == 0) {
            // compare number of distinct vertexes in the last partition (higher is better)
            Long distinctVertexes1 = o1.getVertexSet().stream()
                    .flatMap(vertex -> vertex.getNeighbours().stream()
                            .filter(vertex1 -> vertex1.getPartition().equals(lastPartition)))
                    .distinct()
                    .count();
            Long distinctVertexes2 = o2.getVertexSet().stream()
                    .flatMap(vertex -> vertex.getNeighbours().stream()
                            .filter(vertex1 -> vertex1.getPartition().equals(lastPartition)))
                    .distinct()
                    .count();
            int comparisonResults2 = distinctVertexes1.compareTo(distinctVertexes2) * (-1);
            if(comparisonResults2 == 0) {
                // compare number of usages (less is better)
                return o1.getUsages().compareTo(o2.getUsages());
            } else {
                return comparisonResults2;
            }
        } else {
            return comparisonResults1;
        }
    }
}
