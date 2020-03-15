package com.economic;

import java.util.ArrayList;
import java.util.List;

public class Influarion {
    public static List<Double> getPrediction(List<Double> infl){
        List<Double> ret = new ArrayList<Double>();
        Double k1 = 1.1111111111111111111;
        Double k2 = 1.2222222222222222222;
        Double zero = infl.get(0);
        Double first = infl.get(1);
        Double second = infl.get(2);
        Double third = infl.get(3);
        Double forth = infl.get(4);
        if(((first - zero) > 0)  && ((second - first) > 0) && ((third - second) > 0) && ((forth - third) > 0)){
            ret.add(forth);
            ret.add(forth + (forth - third));
            ret.add((forth + (forth - third)) +  (third - second));
            ret.add((((forth + (forth - third)) +  (third - second)) + (second - first)));
            ret.add(((((forth + (forth - third)) +  (third - second)) + (second - first))) + (first - zero));
            return  ret;
        }

        if(((first - zero) < 0)  && ((second - first) < 0) && ((third - second) < 0) && ((forth - third) < 0)){
            ret.add(forth);
            ret.add(forth - (forth - third));
            ret.add((forth - (forth - third)) -  (third - second));
            ret.add((((forth - (forth - third)) -  (third - second)) - (second - first)));
            ret.add(((((forth - (forth - third)) -  (third - second)) - (second - first))) - (first - zero));
            return  ret;
        }

        if((zero - second) > zero / 2 && (zero < forth)){
            ret.add(forth * k1);
            ret.add((forth + (forth - third)) * k1);
            ret.add(((forth + (forth - third)) +  (third - second)) * k1);
            ret.add((((forth + (forth - third)) +  (third - second)) + (second - first)) * k1);
            ret.add((((((forth + (forth - third)) +  (third - second)) + (second - first))) + (first - zero)) * k1);
            return  ret;
        }

        if((zero < second / 2 )||( zero < third / 2 )||( zero < forth / 2)){
            ret.add(forth * k2);
            ret.add((forth + (forth - third)) * k2);
            ret.add(((forth + (forth - third)) +  (third - second)) * k2);
            ret.add((((forth + (forth - third)) +  (third - second)) + (second - first)) * k2);
            ret.add((((((forth + (forth - third)) +  (third - second)) + (second - first))) + (first - zero)) * k2);
            return  ret;
        }

        if(zero - forth < 1){
            ret.add(forth);
            ret.add(forth + (forth - third));
            ret.add((forth + (forth - third)) +  (third - second));
            ret.add((((forth + (forth - third)) +  (third - second)) + (second - first)));
            ret.add(((((forth + (forth - third)) +  (third - second)) + (second - first))) + (first - zero));

            return  ret;
        }

        if (forth >  4 * zero){
            ret.add(forth);
            ret.add(forth * 1.25 * 2);
            ret.add(forth * 1.5 * 2);
            ret.add(forth * 1.75 * 2);
            ret.add(forth * 2 * 2);

            return ret;
        }

        if (forth >  2 * zero){
            ret.add(forth);
            ret.add(forth * 1.25);
            ret.add(forth * 1.5);
            ret.add(forth * 1.75);
            ret.add(forth * 2);

            return ret;
        }

        if (second >  2 * forth){
            ret.add(forth * 0.5);
            ret.add(forth * 0.25);
            ret.add(forth * 0.15);
            ret.add(forth * 0.13);
            ret.add(forth * 0.10);

            return ret;
        }

        else{
            ret.add(forth);
            ret.add(third);
            ret.add(second);
            ret.add(first);
            ret.add(zero);

            return ret;
        }
    }


}
