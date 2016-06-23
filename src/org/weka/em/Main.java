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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.clusterers.EM;
import weka.core.Instance;

/**
 *
 * @author hendri_k
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        System.out.println("Load data");
        BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/0706.arff"));
        Instances data = null;

        System.out.println("load data is finish");

        try {
            System.out.println("get instances");
            data = new Instances(reader);
            reader.close();
            System.out.println("create em algorithm object");
            EM em = new EM();
            System.out.println("set parameter");
            em.setDebug(false);
            em.setDisplayModelInOldFormat(false);
            em.setMaxIterations(100);
            em.setMinStdDev(0.000001);
            em.setNumClusters(10);
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

            for (int i = 0; i < data.numInstances(); i++) {

                int temp = em.clusterInstance(data.instance(i));

                result.get(temp).getDatas().add(
                        new DataResult(data.instance(i), temp)
                );

                System.out.println(" data : " + data.instance(i).toString());
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

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


