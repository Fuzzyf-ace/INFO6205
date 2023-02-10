package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InsertionSortBenchMark {
    private final int runs;
    private final int n;
    private final String orderingSituation;
    private final Supplier<Integer[]> supplier;
    private final Consumer<Integer[]> consumer;


    public InsertionSortBenchMark(int runs, int n, String orderingSituation) {
        this.runs = runs;
        this.n = n;
        this.orderingSituation = orderingSituation;
        this.supplier = new Supplier<Integer[]>() {
            @Override
            public Integer[] get() {
                return randomArrayGenerator(n, orderingSituation);
            }
            private Integer[] randomArrayGenerator(int n, String orderingSituation) {
                Random random = new Random();
                Integer[] array = new Integer[n];
                for (int i = 0; i < array.length; i++) {
                    array[i] = random.nextInt();
                }
                if (orderingSituation.equals("ordered")) {
                    Arrays.sort(array);
                } else if (orderingSituation.equals("partially-ordered") ) {
                    int end = random.nextInt(n - 1) + 1;
                    int start = random.nextInt(end);
                    Arrays.sort(array, start, end);
                } else if (orderingSituation.equals("reverse-ordered")) {
                    Arrays.sort(array, new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            if (o1 == o2) {
                                return 0;
                            }
                            return o1 < o2 ? 1 : -1;
                        }
                    });
                }
                return array;
            }
        };
        this.consumer = new Consumer<Integer[]>() {
            @Override
            public void accept(Integer[] integers) {
                InsertionSort insertionSort = new InsertionSort<>();
                insertionSort.sort(integers, 0, integers.length);
//                System.out.println(Arrays.toString(integers));    //2 additional accept is called for warm-up
            }
        };
    }

    public void runBenchMarks() {
        Benchmark_Timer timer = new Benchmark_Timer<>("InsertionSortBenchmark", consumer);
        System.out.println(orderingSituation + " array inputted, the average time per run is: " + timer.runFromSupplier(supplier, runs) + "ms\n");
    }

    public static void main(String[] args) {
        int base = 1000;
        int runs = 1000;
        for (int i = 0; i < 5; i++) {
            int n = base * (int) Math.pow(2, i);
            System.out.println("-------------------------------------------------------------------");
            System.out.println("InsertionSort: N = " + n);
            new InsertionSortBenchMark(runs, n, "random").runBenchMarks();
            new InsertionSortBenchMark(runs, n, "ordered").runBenchMarks();
            new InsertionSortBenchMark(runs, n, "partially-ordered").runBenchMarks();
            new InsertionSortBenchMark(runs, n, "reverse-ordered").runBenchMarks();
        }
    }
}
