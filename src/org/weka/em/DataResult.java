/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.weka.em;

import weka.core.Instance;

/**
 *
 * @author hendri_k
 */
public class DataResult {

    private Instance data;
    private Integer counter;

    public DataResult(Instance data, Integer counter) {
        this.data = data;
        this.counter = counter;
    }
    
    public Instance getData() {
        return data;
    }

    public void setData(Instance data) {
        this.data = data;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
