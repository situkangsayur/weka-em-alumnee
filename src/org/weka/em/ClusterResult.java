/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.weka.em;

import java.util.List;

/**
 *
 * @author hendri_k
 */
public class ClusterResult {

    private Integer clusterNum;
    private List<DataResult> datas;

    public ClusterResult(Integer clusterNum, List<DataResult> datas) {
        this.clusterNum = clusterNum;
        this.datas = datas;
    }

    public Integer getClusterNum() {
        return clusterNum;
    }

    public void setClusterNum(Integer clusterNum) {
        this.clusterNum = clusterNum;
    }

    public List<DataResult> getDatas() {
        return datas;
    }

    public void setDatas(List<DataResult> datas) {
        this.datas = datas;
    }
}
