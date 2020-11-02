package com.mesut.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class matrix {
    List<double[]> arr = new ArrayList<>();

    public matrix() {

    }

    public matrix(double[][] arr) {
        for (double[] row : arr) {
            addRow(row);
        }
    }

    public matrix(int r, int c) {
        for (int i = 0; i < r; i++) {
            double[] d = new double[c];
            arr.add(d);
        }
    }

    //(1,2,3),(5,7,5)
    public matrix(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                int idx = str.indexOf(')', i + 1);
                addRow(str.substring(i + 1, idx));
                i = idx;
            }
            else if (c == ',') {
                continue;
            }
        }
    }

    public void set(int r, int c, double val) {
        arr.get(r)[c] = val;
    }

    public void addRow(double[] r) {
        arr.add(r);
    }

    public void addRow(String r) {
        String[] group = r.split(",");
        double[] row = new double[group.length];
        for (int j = 0; j < group.length; j++) {
            row[j] = Double.parseDouble(group[j]);
        }
        arr.add(row);
    }

    @Override
    public String toString() {
        return toString("\n");
    }

    public String toString(String del) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            double[] r = arr.get(i);
            s.append("(");
            for (int j = 0; j < r.length; j++) {
                if ((int) r[j] == r[j]) {
                    s.append((int) r[j]);
                }
                else {
                    s.append(r[j]);
                }

                if (j < r.length - 1) {
                    s.append(",");
                }
            }
            s.append(")");
            if (i < arr.size() - 1) {
                s.append(del);
            }
        }
        return s.toString();
    }

    public int row() {
        return arr.size();
    }

    public int col() {
        if (arr.size() > 0) {
            return arr.get(0).length;
        }
        return 0;
    }

    public double get(int i, int j) {
        return arr.get(i)[j];
    }

    public matrix add(double d) {
        matrix m = new matrix();
        for (double[] r : arr) {
            double[] nr = Arrays.copyOf(r, r.length);
            for (int i = 0; i < r.length; i++) {
                nr[i] += d;
            }
            m.addRow(nr);
        }
        return m;
    }

    public matrix add(matrix m) {
        assert row() == m.row() && col() == m.col();
        matrix nm = new matrix();
        int ri = 0;
        for (double[] r : arr) {
            double[] nr = Arrays.copyOf(r, r.length);
            for (int i = 0; i < r.length; i++) {
                nr[i] += m.get(ri, i);
            }
            ri++;
            nm.addRow(nr);
        }
        return nm;
    }

    public matrix sub(double d) {
        matrix m = new matrix();
        for (double[] r : arr) {
            double[] nr = Arrays.copyOf(r, r.length);
            for (int i = 0; i < r.length; i++) {
                nr[i] -= d;
            }
            m.addRow(nr);
        }
        return m;
    }

    public matrix sub(matrix m) {
        assert row() == m.row() && col() == m.col();
        matrix nm = new matrix();
        int ri = 0;
        for (double[] r : arr) {
            double[] nr = Arrays.copyOf(r, r.length);
            for (int i = 0; i < r.length; i++) {
                nr[i] -= m.get(ri, i);
            }
            ri++;
            nm.addRow(nr);
        }
        return nm;
    }

    public matrix mul(double d) {
        matrix m = new matrix();
        for (double[] r : arr) {
            double[] nr = Arrays.copyOf(r, r.length);
            for (int i = 0; i < r.length; i++) {
                nr[i] *= d;
            }
            m.addRow(nr);
        }
        return m;
    }

    public matrix mul(matrix m) {
        if (col() != m.row()) {
            throw new RuntimeException("matrix col row doesn't match");
        }
        matrix nm = new matrix(row(), m.col());
        double sum = 0;
        for (int c = 0; c < row(); c++) {
            for (int d = 0; d < m.col(); d++) {
                for (int k = 0; k < m.row(); k++) {
                    sum = sum + get(c, k) * m.get(k, d);
                }

                nm.set(c, d, sum);
                sum = 0;
            }
        }
        return nm;
    }

    public matrix invert() {
        double[][] all = new double[arr.size()][];
        for (int i = 0; i < arr.size(); i++) {
            all[i] = arr.get(i);
        }
        return new matrix(invert(all));
    }

    //https://www.sanfoundry.com/java-program-find-inverse-matrix/
    public static double[][] invert(double[][] a) {
        int n = a.length;
        double[][] x = new double[n][n];
        double[][] b = new double[n][n];
        int[] index = new int[n];
        for (int i = 0; i < n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i)
            for (int j = i + 1; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.

    public static void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double[] c = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }
}
