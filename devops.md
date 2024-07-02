# Kubernetes Deployment Strategies

## 1. Rolling Update (Default)

**Description**: Gradually replaces instances of the old version of the application with the new version, ensuring zero downtime.

**Advantages**:
- No downtime, ensuring continuous availability of the application.
- Controlled update process, reducing the risk of failures.
- Can easily roll back if issues are detected.

**Disadvantages**:
- Slower update process compared to other strategies like recreate.
- Complex to debug issues if they occur during the update since both old and new versions run simultaneously.

**Usage**:
- Suitable for most applications where high availability is critical.
- Ideal for production environments where minimizing downtime is essential.

**Implementation Example**:
```yaml
strategy:
  type: RollingUpdate
  rollingUpdate:
    maxUnavailable: 1
    maxSurge: 1
```

## 2. Recreate

Description: Terminates all instances of the existing application and then creates new instances with the updated version.

**Advantages:**

* Simple and straightforward strategy.
* Ensures a clean environment for the new version.

**Disadvantages:**

* Causes downtime as the old version is completely shut down before the new version is started.
* Not suitable for applications requiring high availability.

**Usage:**

Suitable for development and testing environments.
Useful for stateless applications where downtime is acceptable.

**Implementation Example:**

```yml
strategy:
  type: Recreate
```

## 3. Blue/Green Deployment
Description: Maintains two identical production environments (blue and green). Updates are applied to the inactive environment, and traffic is switched between the environments after validation.

**Advantages:**

* Minimal downtime as traffic is switched almost instantly.
* Easy rollback by switching back to the previous environment.
* Allows thorough testing of the new version in a production-like environment.
**Disadvantages:**

* Resource-intensive as it requires maintaining two complete environments.
* More complex to set up and manage compared to simpler strategies.

**Usage:**

* Suitable for mission-critical applications where downtime must be minimized.
* Useful in environments where thorough validation of the new version is required before going live.

**Implementation Example:**

Requires external tools like Istio or custom scripts to manage traffic switching.

## 4. Canary Deployment

Description: Introduces the new version to a subset of users (a canary group) while leaving the rest on the stable version. If the canary group performs well, the new version is rolled out to the remaining users.

**Advantages:**

* Reduces risk by exposing new versions to a small subset of users initially.
* Issues can be detected early before a full rollout.
* Allows performance monitoring and validation in a real-world environment.

**Disadvantages:**

* Requires more sophisticated monitoring and traffic management.
* Can be complex to implement and manage.

**Usage:**

* Suitable for large-scale applications with a significant user base.
* Ideal for scenarios where gradual exposure and risk mitigation are important.

**Implementation Example:**

Requires external tools like Flagger with Istio or Linkerd to manage traffic splitting and monitoring.\


# What is the purpose of namespaces in Kubernetes?
Namespaces are used to divide cluster resources between multiple users. They provide a mechanism to manage different environments within the same cluster, such as development, testing, and production. Namespaces help to avoid name collisions and manage resource quotas.

**Commands:**

* Create a namespace:

```sh
kubectl create namespace <namespace-name>
```

* List namespaces:

```sh
kubectl get namespaces
```

# How do you scale a Kubernetes deployment?
You can scale a deployment either manually or automatically.

**Manually:**

```sh
kubectl scale deployment <deployment-name> --replicas=<number-of-replicas>
```

**Automatically:**

Use the Horizontal Pod Autoscaler (HPA) to automatically adjust the number of pod replicas based on CPU utilization or other select metrics.

```yml
kubectl autoscale deployment <deployment-name> --cpu-percent=50 --min=1 --max=10
```


# StatefulSet:

A StatefulSet is a Kubernetes workload API object used to manage stateful applications. It provides guarantees about the ordering and uniqueness of pods, making it ideal for applications that require stable, persistent storage, unique network identifiers, or stable, unique ordering.

## Key Features of StatefulSet

### 1. Stable, Unique Pod Names:

Each pod in a StatefulSet gets a unique and stable hostname that follows the pattern <statefulset-name>-<ordinal>.
For example, if the StatefulSet is named mysql, the pods would be named mysql-0, mysql-1, etc.

### 2. Ordered Deployment and Scaling:

Pods in a StatefulSet are created, deleted, and scaled in an ordered, sequential manner.
This ensures that the pods are brought up or down in a specific order, which is important for applications that require this behavior (e.g., databases, distributed systems).

### 3. Stable Storage:

StatefulSet can use Persistent Volume Claims (PVCs) to provide stable, persistent storage to each pod.
Each pod in a StatefulSet gets its own PVC, which is not shared with other pods.

### 4. Ordered, Graceful Rolling Updates:

StatefulSets provide ordered and controlled rolling updates to the pods.
This ensures that updates are applied to each pod sequentially, which is critical for maintaining the application's state.
### Example: StatefulSet for MySQL
Below is an example of a StatefulSet and Service for a MySQL database.

MySQL StatefulSet YAML

```yaml
# MySQL StatefulSet
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mysql
spec:
  serviceName: "mysql"
  replicas: 3
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:5.7
        ports:
        - containerPort: 3306
          name: mysql
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: "yourpassword"
        volumeMounts:
        - name: mysql-persistent-storage
          mountPath: /var/lib/mysql
  volumeClaimTemplates:
  - metadata:
      name: mysql-persistent-storage
    spec:
      accessModes: [ "ReadWriteOnce" ]
      resources:
        requests:
          storage: 1Gi
---
# MySQL Headless Service
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  ports:
  - port: 3306
    name: mysql
  clusterIP: None
  selector:
    app: mysql

```

# DaemonSet:
A DaemonSet in Kubernetes ensures that a copy of a pod runs on all (or some) nodes in the cluster. DaemonSets are typically used for running background tasks on all nodes, such as logging, monitoring, or networking agents.

## Key Features of DaemonSet

### 1. Ensures One Pod Per Node:

* Ensures that a specified pod is always running on each node.
* Automatically adds a pod to new nodes as they are added to the cluster.
* Deletes the pod from nodes that are removed from the cluster.

### 2. Selective Deployment:

Can be configured to run on a subset of nodes using node selectors or node affinity.

### 3. DaemonSet Updates:

Supports rolling updates, ensuring that updates to the DaemonSet template are applied gradually across nodes.

**Example: DaemonSet for Fluentd**

Below is an example of a DaemonSet configuration to run Fluentd, a log collector, on all nodes.

Fluentd DaemonSet YAML

```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: fluentd
  labels:
    app: fluentd
spec:
  selector:
    matchLabels:
      name: fluentd
  template:
    metadata:
      labels:
        name: fluentd
    spec:
      containers:
      - name: fluentd
        image: fluent/fluentd:v1.11-1
        env:
        - name: FLUENTD_ARGS
          value: "--no-supervisor -q"
        resources:
          limits:
            memory: 200Mi
            cpu: 100m
          requests:
            memory: 200Mi
            cpu: 100m
        volumeMounts:
        - name: varlog
          mountPath: /var/log
        - name: varlibdockercontainers
          mountPath: /var/lib/docker/containers
          readOnly: true
      volumes:
      - name: varlog
        hostPath:
          path: /var/log
      - name: varlibdockercontainers
        hostPath:
          path: /var/lib/docker/containers
```


# Ingress:

An Ingress is a Kubernetes API object that manages external access to services within a cluster, typically via HTTP and HTTPS. It provides a way to define rules for routing traffic to different services based on the request URL, host, or other criteria, enabling functionalities like load balancing, SSL termination, and name-based virtual hosting.


## Key Features of Ingress

### 1. External Access:

* Manages external access to the services within a Kubernetes cluster.
* Routes traffic to the appropriate service based on the request URL.

### 2. Load Balancing:

* Distributes incoming traffic across multiple backend services.
* Provides a single entry point for accessing multiple services.

### 3. SSL Termination:

* Handles SSL/TLS termination, providing secure communication between clients and the Ingress controller.

### Name-Based Virtual Hosting:

Allows multiple services to be accessed through the same IP address but with different hostnames.
Ingress Controller
An Ingress resource must be accompanied by an Ingress controller to fulfill the Ingress rules. Popular Ingress controllers include:

NGINX Ingress Controller
Traefik
HAProxy
Istio
Example: Ingress Resource
Below is an example of an Ingress resource that routes traffic to two different services based on the request URL.

Ingress Resource YAML

```yml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - host: example.com
    http:
      paths:
      - path: /service1
        pathType: Prefix
        backend:
          service:
            name: service1
            port:
              number: 80
      - path: /service2
        pathType: Prefix
        backend:
          service:
            name: service2
            port:
              number: 80
```

### Setting Up an Ingress Controller
To use the Ingress resource, you need to set up an Ingress controller in your cluster. Here is an example of how to set up the NGINX Ingress controller using Helm:

1. Add the NGINX Ingress Controller Helm repository:
```shell
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
```
2. Install the NGINX Ingress Controller:
```shell
helm install nginx-ingress ingress-nginx/ingress-nginx
```

# sample .yml


## Deplyment

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-deployment
  labels:
    app: my-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
      - name: my-container
        image: my-image:latest
        ports:
        - containerPort: 80

```

## Service:
```yml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: my-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80
  type: ClusterIP
```

##  ConfigMap:
```yml
apiVersion: v1
kind: ConfigMap
metadata:
  name: my-config
data:
  key1: value1
  key2: value2
```

## Secret
```yml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  username: dXNlcm5hbWU=  # base64 encoded value
  password: cGFzc3dvcmQ=  # base64 encoded value
```

## PersistentVolumeClaim (PVC)
```yml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 1Gi
```

## pv:
```yml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: my-pv
spec:
  capacity:
    storage: 1Gi
  accessModes:
    - ReadWriteOnce
  hostPath:
    path: "/mnt/data"
```