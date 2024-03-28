# Kafka

Apache Kafka is an open-source distributed streaming platform designed for building real-time data pipelines and streaming applications. Originally developed by LinkedIn, Kafka was later open-sourced and became a top-level Apache project. It provides a highly scalable, fault-tolerant, and distributed architecture for handling large volumes of streaming data.

Key Features of Apache Kafka:

1. `Distributed Messaging System:` Kafka follows a distributed publish-subscribe messaging model. Producers publish records (messages) to Kafka topics, and consumers subscribe to topics to receive and process these records. This decouples producers from consumers and enables asynchronous message processing.

2. `Scalability:` Kafka is designed to scale horizontally across a cluster of multiple servers (nodes) called brokers. It can handle large volumes of data and support high-throughput data ingestion and processing. Kafka's partitioning mechanism allows data to be distributed across multiple brokers, enabling horizontal scalability and fault tolerance.

3. `Fault Tolerance:` Kafka provides built-in fault tolerance by replicating data across multiple brokers within a Kafka cluster. Each partition of a topic can have multiple replicas, ensuring data durability and availability even in the event of broker failures.

4. `High Throughput and Low Latency:` Kafka is optimized for high throughput and low latency. It can handle millions of messages per second while maintaining low message delivery latency, making it suitable for real-time data processing and event-driven architectures.

# Terms

1. Broker:

   - A Kafka broker is a single instance/node in a Kafka cluster.
   - Brokers are responsible for storing and managing the data, receiving and serving client requests, and replicating data across the cluster.
   - Each broker is identified by a numeric ID and typically runs on a separate server or virtual machine.

2. Topic:

   - A topic is a logical category or feed name to which records (messages) are published by producers and from which records are consumed by consumers.
   - Topics in Kafka are partitioned to allow for parallel processing of data and scalability.
   - Each topic consists of one or more partitions, and each partition is hosted on a specific broker in the cluster.

3. Partition:

   - A partition is a unit of parallelism and scalability in Kafka.
   - Each partition is an ordered, immutable sequence of records (messages) that is continually appended to over time.
   - Partitions allow Kafka to distribute and parallelize data across multiple brokers and consumers.
   - Each partition is replicated across multiple brokers for fault tolerance and high availability.

4. Producer:

   - A producer is an application or process that publishes records (messages) to Kafka topics.
   - Producers send records to specific topics and partitions, optionally specifying a key that determines the partitioning of the record within the topic.

5. Consumer:

   - A consumer is an application or process that reads records (messages) from Kafka topics.
   - Consumers subscribe to specific topics or topic partitions and consume records from them in real-time.
   - Kafka consumers can be part of consumer groups, allowing multiple consumers to work together to process data from a topic.

6. Consumer Group:

   - A consumer group is a logical grouping of Kafka consumers that work together to consume and process data from one or more topics.
   - Each consumer within a group reads from a specific partition, and partitions are dynamically assigned and rebalanced among consumers within the group.
   - Consumer groups provide scalability, fault tolerance, and load balancing for consuming data from Kafka topics.

7. Offset:

   - An offset is a unique identifier assigned to each record (message) within a partition.
   - Offsets are used by consumers to track their position in a partition and resume reading from where they left off in case of failure or restart.
   - Kafka retains records for a configurable retention period, allowing consumers to rewind and replay messages from the past.

8. Replication:

   - Replication is the process of duplicating and synchronizing data across multiple brokers in a Kafka cluster.
     Kafka uses replication to ensure fault tolerance, durability, and data availability in case of broker failures.
   - Each partition in Kafka is replicated across a configurable number of brokers, known as the replication factor.

# About broker:

Kafka broker can host multiple topics. In Kafka, a topic is a logical category or feed name to which records (messages) are published by producers and from which records are consumed by consumers. Each topic consists of one or more partitions, and each partition is hosted on a specific broker within the Kafka cluster. However, `a single broker can host partitions for multiple topics simultaneously`.

Here's how a Kafka broker can handle multiple topics:

- Partition Assignment: When a topic is created in Kafka, its partitions are distributed across the brokers in the cluster. Each partition is assigned to a specific broker, known as the leader for that partition. The leader is responsible for handling all read and write requests for the partition.

- Replication: Kafka employs replication for fault tolerance and data redundancy. Each partition of a topic is replicated across multiple brokers, ensuring that data remains available even if a broker fails. The leader and followers for each partition may reside on different brokers within the cluster.

- Resource Management: Kafka brokers manage resources such as disk storage, memory, and CPU usage to handle data storage and processing for multiple topics. They allocate resources dynamically based on the volume of data and the workload generated by producers and consumers.

- Metadata Management: Kafka brokers maintain metadata about topics, partitions, replicas, and consumer groups within the cluster. This metadata includes information about the topics hosted by each broker, their partitions, leaders and followers, partition assignments for consumer groups, and more. Brokers use this metadata for routing, coordination, and data retrieval.

- Scalability: Kafka brokers are designed to scale horizontally to accommodate growing data volumes and processing requirements. As the number of topics and partitions increases, brokers can dynamically allocate resources and distribute data across the cluster to ensure even load distribution and efficient resource utilization.

Overall, Kafka brokers can efficiently handle data storage, processing, and communication for multiple topics simultaneously, making Kafka a highly scalable and flexible streaming platform for building real-time data pipelines and streaming applications.

# Topic creation:

Suppose we want to create a Kafka topic named "example-topic" with three partitions and a replication factor of 2. This means that Kafka will create three partitions for the topic, and each partition will be replicated across two brokers in the Kafka cluster.

1. Creating the Topic:
   To create the topic using the Kafka command-line tools, you would execute the following command:

   ```
   ./bin/kafka-topics.sh --create --topic example-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 2
   ```

   This command creates the "example-topic" topic with three partitions and a replication factor of 2. Kafka will automatically assign leaders and replicas for each partition based on the specified configuration.

2. Leaders and Replicas:
   After creating the topic, Kafka will assign leaders and replicas for each partition. Let's say the following assignments are made:

* Partition 0:

    * Leader: Broker 1
    * Replicas: Broker 1, Broker 2

* Partition 1:

    * Leader: Broker 2
    * Replicas: Broker 2, Broker 3

* Partition 2:

    * Leader: Broker 3
    * Replicas: Broker 3, Broker 1

In this example, each partition has a leader assigned to one of the brokers, and the partition's data is replicated across two brokers based on the replication factor.

2. In-Sync Replicas (ISR):

    Kafka maintains a set of in-sync replicas (ISR) for each partition. These are replicas that are up-to-date with the leader's data and are considered in sync with the leader.

    * For Partition 0:
        * ISR: Broker 1, Broker 2

    * For Partition 1:
        * ISR: Broker 2, Broker 3

    * For Partition 2:
        * ISR: Broker 3, Broker 1

The ISR ensures fault tolerance and data availability. If a leader fails, one of the in-sync replicas can be promoted to leader to continue serving requests.

In summary, when you create a Kafka topic with multiple partitions and a replication factor, Kafka assigns leaders and replicas for each partition and maintains a set of in-sync replicas (ISR) to ensure fault tolerance and data durability. This architecture allows Kafka to handle large volumes of data reliably and efficiently.


