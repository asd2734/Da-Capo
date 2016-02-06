package com.alex.dacapo.utils;

/**
 * Created by Solomon on 2/6/2016.
 * Original source: https://www.ee.columbia.edu/~ronw/code/MEAPsoft/doc/html/FFT_8java-source.html
 */

/*
00002  *  Copyright 2006-2007 Columbia University.
00003  *
00004  *  This file is part of MEAPsoft.
00005  *
00006  *  MEAPsoft is free software; you can redistribute it and/or modify
00007  *  it under the terms of the GNU General Public License version 2 as
00008  *  published by the Free Software Foundation.
00009  *
00010  *  MEAPsoft is distributed in the hope that it will be useful, but
00011  *  WITHOUT ANY WARRANTY; without even the implied warranty of
00012  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
00013  *  General Public License for more details.
00014  *
00015  *  You should have received a copy of the GNU General Public License
00016  *  along with MEAPsoft; if not, write to the Free Software
00017  *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
00018  *  02110-1301 USA
00019  *
00020  *  See the file "COPYING" for the text of the license.
00021  */


public class FastFourierTransform {

    int n, m;

    // Lookup tables. Only need to recompute when size of FFT changes.
    double[] cos;
    double[] sin;

    public FastFourierTransform(int pieceSize) {
        this.n = pieceSize;
        this.m = (int) (Math.log(pieceSize) / Math.log(2));

        // Make sure pieceSize is a power of 2
        if (pieceSize != (1 << m))
            throw new RuntimeException("FFT length must be power of 2");

        // precompute tables
        cos = new double[pieceSize / 2];
        sin = new double[pieceSize / 2];

        for (int i = 0; i < pieceSize / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / pieceSize);
            sin[i] = Math.sin(-2 * Math.PI * i / pieceSize);
        }

    }

    public double[] fftCalculator(double[] re, double[] im) {
        if (re.length != im.length) return null;
        FastFourierTransform fft = new FastFourierTransform(re.length);
        fft.fft(re, im);
        double[] overallAmplitudes = new double[re.length];
        for (int i = 0; i < re.length; i++) {
            overallAmplitudes[i] = Math.pow(re[i], 2) + Math.pow(im[i], 2);
        }
        return overallAmplitudes;
    }
    /*00088  ***************************************************************
     00089   * fft.c
     00090   * Douglas L. Jones
     00091   * University of Illinois at Urbana-Champaign
     00092   * January 19, 1992
     00093   * http://cnx.rice.edu/content/m12016/latest/
     00094   *
     00095   *   fft: in-place radix-2 DIT DFT of a complex input
     00096   *
     00097   *   input:
     00098   * n: length of FFT: must be a power of two
     00099   * m: n = 2**m
     00100   *   input/output
     00101   * x: double array of length n with real part of data
     00102   * y: double array of length n with imag part of data
     00103   *
     00104   *   Permission to copy and use this program is granted
     00105   *   as long as this header is included.
     00106   ****************************************************************/
    public void fft(double[] x, double[] y) {
        int i, j, k, n1, n2, a;
        double c, s, t1, t2;

        // Bit-reverse
        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;
            //j is now <= 2*n1. Not really sure what "bit reverse" means.


            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }

        // FFT
        n1 = 0;
        n2 = 1;

        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;

            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);

                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }
    }
}  