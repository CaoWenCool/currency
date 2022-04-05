package com.currency.qrcode.currency.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 自定义的Partitioner
 */
public class MyPartitioner implements Partitioner {
    private Random random =  new Random();
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        // 获取集群中指定Topic的所有分区信息
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic(s);
        int numOfPartition = partitionInfoList.size();
        int partitionNum = 0;
        if (o == null){
            partitionNum = random.nextInt(numOfPartition);
        } else {
            partitionNum = Math.abs(o1.hashCode()) % numOfPartition;
        }
        return partitionNum;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
