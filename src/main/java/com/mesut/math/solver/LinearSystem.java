package com.mesut.math.solver;

import com.mesut.math.Util;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinearSystem {

    List<lin> list = new ArrayList<>();
    public matrix cmatrix;
    public matrix resultMatrix;
    List<variable> vars;
    func lin;

    public LinearSystem(variable... v) {

    }

    public void add(lin lin) {
        list.add(lin);
    }

    public void add(String eq, double val) {
        func f = func.parse(eq);
        list.add(new lin(f, val));
    }

    public void solve() {
        cmatrix = new matrix();
        vars = new ArrayList<>();

        for (lin lin : list) {
            List<variable> tmp = lin.vars();
            vars.removeAll(tmp);
            vars.addAll(tmp);
        }
        Collections.sort(vars);

        for (lin lin : list) {
            func f = lin.getF();
            double[] carr = new double[vars.size()];
            int index = 0;
            //set all vars except current to zero so that we get the coeff
            for (variable v : vars) {
                double[] all = new double[vars.size()];
                all[vars.indexOf(v)] = 1;
                double c = f.eval(vars.toArray(new variable[0]), all);
                carr[index++] = c;
            }
            cmatrix.addRow(carr);
        }

        //solve
        matrix valMatrix = new matrix();
        for (lin lin : list) {
            valMatrix.addRow(new double[]{lin.val});
        }
        resultMatrix = cmatrix.invert().mul(valMatrix);
    }

    public double get(Object vo) {
        variable v = Util.var(vo);
        return resultMatrix.get(vars.indexOf(v), 0);
    }

    public String printSolution() {
        StringBuilder sb = new StringBuilder();
        for (variable v : vars) {
            sb.append(v).append("=").append(get(v));
            sb.append(" ");
        }
        return sb.toString();
    }

    public func makeLin() {
        if (lin != null) {
            return lin;
        }
        variable v = variable.x;
        lin = cons.ZERO;
        int pow = vars.size() - 1;
        for (variable vr : vars) {
            double coeff = get(vr);
            lin = lin.add(v.pow(pow--).mul(coeff));
        }
        return lin;
    }
}
