/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.weka.em;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.core.Instances;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author hendri_k
 */
public class App {
		

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        System.out.println("Load data");
        BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/fraud-weka-label.arff"));
        Instances dataTemp = null;
        Instances data = null;
				System.out.print("hello world");
				Float temp = new Float();

        System.out.println("load data is finish");
        Remove filter = new Remove();

        try {
            System.out.println("get instances");
            DataSource source = new DataSource(System.getProperty("user.dir") + "/fraud-weka.arff");
            dataTemp = source.getDataSet();
            dataTemp.setClassIndex(dataTemp.numAttributes()-1);
            
            System.out.println("class index : " + dataTemp.classIndex() );
            filter.setAttributeIndices("" + (dataTemp.classIndex() + 1));
            filter.setInputFormat(dataTemp);    
            Instances dataNoClass = Filter.useFilter(dataTemp, filter);
            
            Normalize normalizeFilter = new Normalize();
            normalizeFilter.setIgnoreClass(true);
            normalizeFilter.setInputFormat(dataNoClass);
            data = Filter.useFilter(dataNoClass, normalizeFilter);
            
            
//            data = new Instances(reader);
            
            reader.close();
            System.out.println("create em algorithm object");
            EM em = new EM();
            System.out.println("set parameter");
            em.setDebug(false);
            em.setDisplayModelInOldFormat(false);
            em.setMaxIterations(1000);
            em.setMinStdDev(0.00001);
            em.setNumClusters(9);
            System.out.println("create model");
            em.buildClusterer(data);

            System.out.println("check instances with model with "
                    + em.getNumClusters() + "cluster; \n "
                    + "and number cluster : " + em.numberOfClusters());

            List<ClusterResult> result = new ArrayList<ClusterResult>();

            for (int i = 0; i < em.getNumClusters(); i++) {
                result.add(
                        new ClusterResult(i,
                                new ArrayList<DataResult>()
                        )
                );

            }

            for (int i = 0; i < dataTemp.numInstances(); i++) {

                int temp = em.clusterInstance(data.instance(i));

                result.get(temp).getDatas().add(
                        new DataResult(dataTemp.instance(i), temp)
                );

                System.out.println(" data : " + dataTemp.instance(i).toString());
                System.out.println(" data ke " + i + " : " + temp);
            }
            
            System.out.println("\n\n");
            
            for (int i = 0; i < result.size(); i++) {
                System.out.println("cluster ke " + i + " : " + result.get(i).getClusterNum());
                for(int j = 0; j < result.get(i).getDatas().size(); j++){
                    Instance tempInstance = result.get(i).getDatas().get(j).getData();
                    int tempCluster = result.get(i).getDatas().get(j).getCounter();
                    System.out.println("          - " + tempInstance.toString() + " => " + tempCluster);
                }
            }
            int min = Integer.MAX_VALUE;
            int pos = 0;
            for (int i = 0; i < result.size(); i++) {
                System.out.println("cluster ke " + i + " : " + result.get(i).getDatas().size());
                if (result.get(i).getDatas().size() < min){
                    min = result.get(i).getDatas().size();
                    pos = i;
                }
            }
            
            System.out.println("the smallest cluster : " + pos);
            int falsePositive = 0;
            int truePositive = 0
            for(int j = 0; j < result.get(pos).getDatas().size(); j++){
                    Instance tempInstance = result.get(pos).getDatas().get(j).getData();
                    int tempCluster = result.get(pos).getDatas().get(j).getCounter();
                    System.out.println("          - " + tempInstance.toString() + " => " + tempCluster);
//                    System.out.println(tempInstance.value(tempInstance.numAttributes() - 1));
                    
                    if (tempInstance.value(tempInstance.numAttributes() - 1) != 1.0){
                         falsePositive += 1;
                    }else{
                        truePositive += 1;
                    }
                    
            }
            int falseNegative = 0;
            int trueNegative = 0;
            for (int i = 0; i < result.size(); i++) {
                
                if (i == pos){
                    continue;
                }
                for(int j = 0; j < result.get(i).getDatas().size(); j++){
                    Instance tempInstance = result.get(i).getDatas().get(j).getData();
                    if(tempInstance.value(tempInstance.numAttributes() - 1) != 0.0){
                        falseNegative += 1;
                    }else{
                        trueNegative += 1;
                    }
                }
            }
            
            System.out.println("false negative : " + falseNegative);
            System.out.println("true negative : " + trueNegative);
            System.out.println("false positive : " + falsePositive);
            System.out.println("true positive : " + truePositive);
            System.out.println("akurasi : " + ((trueNegative + truePositive) /(dataTemp.numInstances()/100)));
            

        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


