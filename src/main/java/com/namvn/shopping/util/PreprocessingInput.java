package com.namvn.shopping.util;

import com.namvn.shopping.persistence.model.ProductRequestParam;
import com.namvn.shopping.util.constant.ProductContants;


import java.util.HashMap;
import java.util.List;

public class PreprocessingInput {
    public HashMap<String, List<String>> filterNumberPredicateProduct(ProductRequestParam productParam) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> colors = productParam.getColors();
        List<String> sizes = productParam.getSizes();
        List<String> manufacturers = productParam.getManufacturers();
        List<String> materials = productParam.getMaterials();
        List<String> madeIns = productParam.getMaterials();
        if (colors != null && colors.size() > 0) map.put(ProductContants.COLOR, colors);
        if (sizes != null && sizes.size() > 0) map.put(ProductContants.SIZE, sizes);
        if (manufacturers != null && manufacturers.size() > 0) map.put(ProductContants.MANUFACTURER, manufacturers);
        if (materials != null && materials.size() > 0) map.put(ProductContants.MATERIAL, materials);
        if (madeIns != null && madeIns.size() > 0) map.put(ProductContants.MADEIN, madeIns);
        return map;
    }

    public HashMap filterLeftRequestParam(float min, float max, String sortType) {

        HashMap hashMap = new HashMap();
        if (max > 0) {
            hashMap.put("min", min);
            hashMap.put("max", max);
        }
        if (sortType != null) {
            hashMap.put("sortType", sortType);

        }
        return hashMap;    }
}
