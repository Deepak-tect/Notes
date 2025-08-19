# Kubernetes Namespaces

## Overview

Namespaces in Kubernetes provide a way to logically separate and manage resources within a cluster. They are useful for organizing different environments (e.g., development, testing, production) or multi-tenant deployments.

## Use Cases

- **Environment Separation**: Different teams or projects can have isolated resources.
- **Resource Quotas**: Control and limit the resources consumed within a namespace.
- **Access Control**: Define RBAC (Role-Based Access Control) policies per namespace.
- **Multi-Tenancy**: Multiple users can operate in a shared cluster without conflicts.

## Communication Between Services

### Communication Within the Same Namespace

- Services within the same namespace can communicate using the service name.
- Example: A pod can reach a service named `my-service` within its namespace using:
  ```sh
  curl http://my-service:8080
  ```

### Communication Across Different Namespaces

- Services in different namespaces require the full service name format:
  ```sh
  curl http://my-service.my-namespace.svc.cluster.local:8080
  ```
- Example YAML for cross-namespace communication:
  ```yaml
  apiVersion: v1
  kind: Service
  metadata:
    name: my-service
    namespace: my-namespace
  spec:
    selector:
      app: my-app
    ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  ```
### Understanding .svc.cluster.local

#### Is .svc.cluster.local Always the Same?

Yes, .svc.cluster.local is the default cluster domain in Kubernetes, but it can be changed.

### Key Points:

1. Default Domain: By default, Kubernetes uses .svc.cluster.local as the DNS suffix for services inside the cluster.

2. Custom Domain: The cluster administrator can modify this domain using the clusterDomain setting in the CoreDNS configuration.

3. Fully Qualified Domain Name (FQDN):
    * Within a namespace: my-service

    * Across namespaces: my-service.my-namespace

    * Fully qualified: my-service.my-namespace.svc.cluster.local

    * DNS Resolution: If you run kubectl exec -it <pod> -- nslookup my-service, Kubernetes will resolve it to its corresponding ClusterIP.

### When Does It Change?

* If the cluster is configured with a custom domain, like .svc.mycompany.local.

* If an external service mesh (like Istio) modifies the service discovery mechanism.

* In a standard Kubernetes setup, .svc.cluster.local remains the same.
## Commands

### Create a Namespace

```sh
kubectl create namespace my-namespace
```

### List All Namespaces

```sh
kubectl get namespaces
```

### Deploy Resources in a Specific Namespace

```sh
kubectl apply -f my-deployment.yaml --namespace=my-namespace
```

### View Resources in a Namespace

```sh
kubectl get pods --namespace=my-namespace
```

### Delete a Namespace

```sh
kubectl delete namespace my-namespace
```

## Example YAML

### Namespace Definition

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: my-namespace
```

### Deploying a Pod in a Specific Namespace

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
  namespace: my-namespace
spec:
  containers:
  - name: nginx
    image: nginx
```

## Key Points

- **Default Namespace**: If no namespace is specified, resources are created in the `default` namespace.
- **kubectl config set-context**: Set a default namespace for a session to avoid specifying `--namespace` each time.
- **Namespaces do not provide network isolation**: Use NetworkPolicies for network security.

## Conclusion

Namespaces help in managing and organizing Kubernetes resources efficiently. They enable logical separation, access control, and resource management in large-scale deployments.



# Multi-Container Pod in Kubernetes

## Overview
A **multi-container pod** in Kubernetes is a pod that runs multiple containers within the same pod. These containers share networking and storage, allowing them to communicate easily.

## Use Cases

### 1. Sidecar Pattern
A secondary container enhances the main container by providing additional functionality such as logging, monitoring, or security.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: sidecar-pod
spec:
  containers:
  - name: app-container
    image: nginx
  - name: log-collector
    image: busybox
    command: ["sh", "-c", "tail -f /var/log/nginx/access.log"]
```

### 2. Adapter Pattern
A helper container transforms data before sending it to the main container. This is useful when different services communicate using different data formats.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: adapter-pod
spec:
  containers:
  - name: app-container
    image: my-app
  - name: adapter-container
    image: busybox
    command: ["sh", "-c", "cat /data/input | transform > /data/output"]
    volumeMounts:
      - name: shared-data
        mountPath: /data
  volumes:
    - name: shared-data
      emptyDir: {}
```

### 3. Ambassador Pattern
A container acts as a proxy between services, allowing the main application to interact with external services using a unified interface.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: ambassador-pod
spec:
  containers:
  - name: app-container
    image: my-app
  - name: proxy-container
    image: envoyproxy/envoy
    args: ["-c", "/etc/envoy/envoy.yaml"]
```

### 4. Init Container Pattern
A container that runs before the main application starts, performing setup tasks such as database migrations or configuration setups.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: init-container-pod
spec:
  initContainers:
  - name: init-container
    image: busybox
    command: ["sh", "-c", "echo Initializing; sleep 5"]
  containers:
  - name: app-container
    image: nginx
```

### 5. Processing Pipeline Pattern
Containers work together in a sequence to process data. This is useful when data needs to be generated, processed, and then stored or transmitted.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pipeline-pod
spec:
  containers:
  - name: data-generator
    image: busybox
    command: ["sh", "-c", "echo data > /data/input"]
    volumeMounts:
      - name: shared-data
        mountPath: /data
  - name: processor
    image: busybox
    command: ["sh", "-c", "cat /data/input | process > /data/output"]
    volumeMounts:
      - name: shared-data
        mountPath: /data
  volumes:
    - name: shared-data
      emptyDir: {}
```

### 6. Shared Data Pattern
Multiple containers share the same volume for data exchange. This is useful when an application requires a producer-consumer model where one container writes data, and another reads it.

#### Example YAML:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: shared-data-pod
spec:
  containers:
  - name: writer
    image: busybox
    command: ["sh", "-c", "echo hello > /data/message"]
    volumeMounts:
      - name: shared-storage
        mountPath: /data
  - name: reader
    image: busybox
    command: ["sh", "-c", "cat /data/message"]
    volumeMounts:
      - name: shared-storage
        mountPath: /data
  volumes:
    - name: shared-storage
      emptyDir: {}
```

## Key Points
- Containers in the same pod **share the same network namespace** (can communicate via `localhost`).
- They can share **volumes** for data exchange.
- Use **initContainers** if a container must complete before others start.
- Different containers can have **distinct roles** but function together within the pod.

## Commands
- Get pod details:
  ```sh
  kubectl describe pod multi-container-pod
  ```
- Check logs of a specific container:
  ```sh
  kubectl logs multi-container-pod -c app-container
  ```
- Check logs of an init container:
  ```sh
  kubectl logs multi-container-pod -c init-container
  ```



## Key Points
- Containers in the same pod **share the same network namespace** (can communicate via `localhost`).
- They can share **volumes** for data exchange.
- Use **initContainers** if a container must complete before others start.

## Commands
- Get pod details:
  ```sh
  kubectl describe pod multi-container-pod
  ```
- Check logs of a specific container:
  ```sh
  kubectl logs multi-container-pod -c app-container
  ```

## Conclusion
Multi-container pods are useful for complex applications needing tightly coupled services. Use them when necessary but avoid unnecessary complexity.

# Kubernetes DaemonSet and CronJob

## DaemonSet

### Overview
A **DaemonSet** ensures that a specific pod runs on all (or some) nodes in a Kubernetes cluster. It is commonly used for logging, monitoring, and network-related tasks.

### Use Cases
- Running **log collection agents** (e.g., Fluentd, Filebeat) on all nodes.
- Deploying **monitoring agents** (e.g., Prometheus Node Exporter, Datadog agents).
- Enabling **network management** tools (e.g., CNI plugins, kube-proxy).
- Running **system-level daemons** that need to exist on every node.

### Example YAML for DaemonSet
```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: node-monitor
  namespace: monitoring
spec:
  selector:
    matchLabels:
      app: node-monitor
  template:
    metadata:
      labels:
        app: node-monitor
    spec:
      containers:
      - name: node-monitor
        image: prom/node-exporter
        ports:
        - containerPort: 9100
```

---

## CronJob

### Overview
A **CronJob** is a Kubernetes resource used to run scheduled tasks, similar to cron jobs in Linux. It is useful for periodic tasks such as backups, report generation, and data synchronization.

### Use Cases
- **Automated backups** of databases or application data.
- **Cleanup jobs** for removing stale data.
- **Scheduled report generation**.
- **Sending periodic notifications or alerts**.

### Example YAML for CronJob
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: database-backup
  namespace: maintenance
spec:
  schedule: "0 3 * * *" # Runs daily at 3 AM
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: backup
            image: mybackup:latest
            args:
            - "/bin/sh"
            - "-c"
            - "pg_dump mydb > /backup/mydb.sql"
          restartPolicy: OnFailure
```

---

## Argo Cron Workflow vs Kubernetes CronJob

Both **Argo Cron Workflow** and **Kubernetes CronJob** are used for scheduling tasks in Kubernetes, but they serve different purposes and have distinct features.

### **1. Kubernetes CronJob**
A **CronJob** in Kubernetes runs scheduled jobs, similar to a Linux cron job. It creates **Job** objects that execute at specified intervals.

#### **Key Features**
- Uses the standard **cron syntax** for scheduling.
- Creates a **Job** object when the schedule is triggered.
- Best for **simple scheduled tasks**, like database backups, log cleanup, and periodic reports.
- Limited to running **single-container jobs** without workflows.

#### **Example YAML (Kubernetes CronJob)**
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: database-backup
spec:
  schedule: "0 3 * * *" # Runs daily at 3 AM
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: backup
            image: mybackup:latest
            args: ["pg_dump mydb > /backup/mydb.sql"]
          restartPolicy: OnFailure
```

---

### **2. Argo Cron Workflow**
An **Argo Cron Workflow** is part of **Argo Workflows**, an advanced workflow engine for Kubernetes that allows defining **multi-step workflows**.

#### **Key Features**
- Designed for **complex, multi-step workflows**.
- Can define **DAGs (Directed Acyclic Graphs)** for dependencies between steps.
- Supports **artifacts, parameters, and conditional execution**.
- Provides **workflow templates** for reusable execution logic.
- Uses the **Argo Workflow Controller** instead of the Kubernetes Job Controller.

#### **Example YAML (Argo Cron Workflow)**
```yaml
apiVersion: argoproj.io/v1alpha1
kind: CronWorkflow
metadata:
  name: argo-db-backup
spec:
  schedule: "0 3 * * *" # Runs daily at 3 AM
  workflowSpec:
    entrypoint: backup
    templates:
    - name: backup
      steps:
      - - name: dump-db
          template: dump-db-step
    - name: dump-db-step
      container:
        image: mybackup:latest
        command: ["/bin/sh", "-c"]
        args: ["pg_dump mydb > /backup/mydb.sql"]
```

---

## **Key Differences**
| Feature                | Kubernetes CronJob | Argo Cron Workflow |
|------------------------|-------------------|--------------------|
| **Use Case**           | Simple scheduled tasks | Complex workflows |
| **Multi-Step Execution** | ❌ No | ✅ Yes (DAG support) |
| **Dependency Handling** | ❌ No | ✅ Yes |
| **Artifact Support**   | ❌ No | ✅ Yes |
| **Parallel Execution** | ❌ Limited | ✅ Fully Supported |
| **UI for Monitoring**  | ❌ No | ✅ Yes (Argo UI) |

---

## **Which One to Use?**
- ✅ **Use Kubernetes CronJob** if you need a **single-task scheduled job** like a backup or log rotation.
- ✅ **Use Argo Cron Workflow** if you need **multi-step workflows, dependencies, or advanced automation**.

## Conclusion
- Use **DaemonSet** when you need a pod on every (or specific) node.
- Use **CronJob** for scheduled, periodic tasks.
- Use **Argo Cron Workflow** for complex workflows with multiple steps and dependencies.
- Both resources help automate and manage Kubernetes workloads efficiently.

# 📒 etcd 
## 1. What is etcd?
- A **distributed, reliable key-value store** used by Kubernetes to store **all cluster state**.
- Written in **Go**, uses **Raft consensus algorithm** for consistency.
- It’s the **single source of truth** for the cluster — if it’s lost without backup, the cluster state is lost.

---

## 2. What does etcd store?
- Nodes, Pods, Deployments, Services  
- ConfigMaps, Secrets  
- RBAC configs  
- Network policies  
*(Basically, anything created via the API server)*

---

## 3. How etcd works in Kubernetes
- **Only the API Server talks to etcd directly**.
- Writes & reads go through API server → persisted in etcd.
- For HA clusters, multiple etcd members sync via Raft.

---

## 4. Raft & HA basics
- **Leader** handles writes, followers replicate.
- **Quorum** = majority of members must agree before a write.
- **Odd number of members** (3, 5) → efficient quorum.

---

## 5. etcd in the Control Plane
- Runs as a **static pod** in kubeadm setups:
  ```
  /etc/kubernetes/manifests/etcd.yaml
  ```
- Data directory:
  ```
  /var/lib/etcd
  ```

---

## 6. Data Directory
- Location where etcd stores its **database files** (current state, snapshots, WAL logs).
- Defined by the `--data-dir` flag in etcd manifest.
- Default in kubeadm: `/var/lib/etcd`
- Check location:
  ```bash
  cat /etc/kubernetes/manifests/etcd.yaml | grep data-dir
  ```
- Critical for backup & restore — replacing it restores the cluster state.

---

## 7. etcd Security
- Communication is **TLS-encrypted**.
- Requires:
  ```
  CA cert:     /etc/kubernetes/pki/etcd/ca.crt
  Server cert: /etc/kubernetes/pki/etcd/server.crt
  Server key:  /etc/kubernetes/pki/etcd/server.key
  ```

---

## 8. Key Commands (CKA Must-Know)

**Check health**
```bash
ETCDCTL_API=3 etcdctl --endpoints=https://127.0.0.1:2379 --cacert=/etc/kubernetes/pki/etcd/ca.crt --cert=/etc/kubernetes/pki/etcd/server.crt --key=/etc/kubernetes/pki/etcd/server.key endpoint health
```

**List keys**
```bash
ETCDCTL_API=3 etcdctl --endpoints=https://127.0.0.1:2379 --cacert=/etc/kubernetes/pki/etcd/ca.crt --cert=/etc/kubernetes/pki/etcd/server.crt --key=/etc/kubernetes/pki/etcd/server.key get "" --prefix --keys-only
```

**Take backup (snapshot)**
```bash
ETCDCTL_API=3 etcdctl snapshot save /opt/snapshot.db --endpoints=https://127.0.0.1:2379 --cacert=/etc/kubernetes/pki/etcd/ca.crt --cert=/etc/kubernetes/pki/etcd/server.crt --key=/etc/kubernetes/pki/etcd/server.key
```

**Restore from snapshot**
```bash
ETCDCTL_API=3 etcdctl snapshot restore /opt/snapshot.db --data-dir /var/lib/etcd-from-backup
```
- Then **update etcd manifest** (`/etc/kubernetes/manifests/etcd.yaml`) to use the new `--data-dir`.

---

## 9. Best Practices
- Always backup before upgrades.
- Use **fast SSDs** for `/var/lib/etcd`.
- Use odd number of members.
- Keep etcd separate for very large clusters.

---

## 10. Common Interview Q&A
**Q:** What happens if etcd fails?  
**A:** API server can’t read/write state → cluster becomes effectively read-only.

**Q:** Why odd number of nodes?  
**A:** Raft needs quorum; odd numbers avoid tie situations.

**Q:** Who talks directly to etcd?  
**A:** Only the API server.

---

✅ **Memory Hook for CKA**:  
`3P + 3C + 3B`  
- **3P** → Path, Pod type, PKI certs  
- **3C** → Check health, Check keys, Create snapshot  
- **3B** → Backup, Bring back (restore), Best practices  


# 📒 Kubernetes API Server 

## 1. What is the API Server?
- Central **control plane component** in Kubernetes.
- Acts as the **front door** to the cluster — all requests go through it.
- Implements the **Kubernetes API** (REST API).
- Validates requests, processes them, and updates the cluster state in **etcd**.

---

## 2. Responsibilities
1. **Authentication** — verifies identity of the caller.
2. **Authorization** — checks permissions (RBAC, ABAC, Webhook).
3. **Admission Control** — enforces rules or mutates requests.
4. **Validation** — ensures request matches API schema.
5. **Persistence** — writes/reads data from etcd.
6. **Serving API** — exposes `/api` and `/apis` endpoints.

---

## 3. Who Talks to the API Server
- `kubectl`
- Controllers (e.g., kube-controller-manager)
- Kubelet (node agent)
- Scheduler
- External clients (via REST API)

---

## 4. Key Flags & Configuration
- Runs as a **static pod**:
  ```
  /etc/kubernetes/manifests/kube-apiserver.yaml
  ```
- Important flags:
  ```
  --etcd-servers=https://127.0.0.1:2379
  --etcd-cafile=/etc/kubernetes/pki/etcd/ca.crt
  --etcd-certfile=/etc/kubernetes/pki/apiserver-etcd-client.crt
  --etcd-keyfile=/etc/kubernetes/pki/apiserver-etcd-client.key
  --client-ca-file=/etc/kubernetes/pki/ca.crt
  --tls-cert-file=/etc/kubernetes/pki/apiserver.crt
  --tls-private-key-file=/etc/kubernetes/pki/apiserver.key
  --service-cluster-ip-range=10.96.0.0/12
  ```

**Explanation of `--service-cluster-ip-range`**:  
- Defines the **CIDR range for ClusterIPs** of Services inside the cluster.  
- Example: `10.96.0.0/12` → Services get IPs like `10.96.0.1`, `10.96.0.10`, etc.  
- Must match controller-manager and kube-proxy configuration.

---

## 5. Security & Communication
- **TLS everywhere** (API server ↔ clients, ↔ etcd, ↔ kubelet)
- Auth methods: client certs, bearer tokens, service accounts, OIDC
- Authorization modes: RBAC (recommended), ABAC, Webhook

---

## 6. Pod IP vs Service Cluster IP
| Feature         | Pod IP             | Service Cluster IP (API Server IP) |
|----------------|--------------------|-------------------------------------|
| Lifetime       | Ephemeral (per Pod) | Persistent (per Service)           |
| Source         | CNI plugin subnet   | Service CIDR (`--service-cluster-ip-range`) |
| Purpose        | Identify Pod        | Stable access point to a Service    |
| Changes on     | Pod restart         | Only Service deletion/recreate      |

- **Pod IP**: Assigned to a Pod, can change if Pod is recreated.  
- **Cluster IP**: Assigned to a Service, stable inside cluster, e.g., `kubernetes` Service for API server.  

---

## 7. Commands for CKA
**Check API server status**
```bash
kubectl get componentstatuses
kubectl get --raw='/healthz'
```

**View API versions**
```bash
kubectl api-versions
```

**Check available resources**
```bash
kubectl api-resources
```

**Access API directly**
```bash
kubectl get --raw /api/v1/nodes
```

---

## 8. Common Interview Q&A
**Q:** Who communicates directly with etcd?  
**A:** Only the API server.

**Q:** What happens if API server is down?  
**A:** Cluster becomes unusable — no new changes, components cannot report status.

**Q:** Difference between `/api` and `/apis`?  
**A:** `/api` → core group; `/apis` → named API groups.

---

## 9. Best Practices
- Enable **RBAC**
- Use **HTTPS & valid certs**
- Restrict API server access to trusted networks
- Rotate API server certificates periodically
- Keep `--anonymous-auth=false` in production

---

✅ **Memory Hook for CKA**:  
`3S + 3A + 3P`  
- **3S** → Static pod, Security (TLS), Service range  
- **3A** → AuthN, AuthZ, Admission  
- **3P** → Persist to etcd, Provide API, Protect access  


# Kubernetes Scheduler

## 1. Overview
The Kubernetes Scheduler is a **control plane component** that decides **which node** a newly created Pod should run on.
- It **does not** run the Pod itself — it only **assigns** it to a node.
- Scheduling decisions are based on **resource requirements**, **constraints**, **affinity rules**, and **policies**.

---

## 2. Key Responsibilities
1. **Filtering Nodes**  
   - Removes nodes that don't meet Pod requirements (e.g., insufficient CPU/memory).
   - Considers node taints, node selectors, and affinity rules.
   
2. **Scoring Nodes**  
   - Assigns a score to each feasible node.
   - Chooses the node with the highest score (best fit).

3. **Binding Pod to Node**  
   - Communicates with the API Server to **bind** the Pod to the chosen node.

---

## 3. Scheduling Process
1. **Pod Creation**
   - A Pod without a `nodeName` is created and stored in `etcd` via the API Server.
   
2. **Scheduler Watches**
   - The Scheduler continuously watches the API Server for **unscheduled Pods**.
   
3. **Filtering Phase**
   - Eliminates nodes that:
     - Lack required resources.
     - Do not satisfy affinity rules.
     - Have taints not tolerated by the Pod.

4. **Scoring Phase**
   - Assigns scores based on:
     - **Least requested resources** (prefer less-loaded nodes).
     - **Balanced resource allocation**.
     - **Node affinity/anti-affinity weights**.

5. **Binding**
   - Sends a `Binding` object to the API Server to update the Pod’s `nodeName`.

---

## 4. Scheduler Policies (Custom Scheduling)
- **NodeSelector**
- **Node Affinity / Anti-affinity**
- **Taints and Tolerations**
- **Pod Affinity / Anti-affinity**
- **Resource Requests & Limits**
- **Custom Scheduler** (using `--scheduler-name`)

---

## 5. Common CLI & Debugging Commands
```bash
# View scheduler logs (control plane node)
kubectl logs -n kube-system kube-scheduler-<node-name>

# Get scheduler pod
kubectl get pods -n kube-system -l component=kube-scheduler

# Check pod scheduling events
kubectl describe pod <pod-name>

```

# Kubernetes Controller Manager  

## 1. Overview
The **Controller Manager** is a core Kubernetes control plane component responsible for running controllers that regulate the state of the cluster.  
It ensures that the actual cluster state matches the desired state defined in Kubernetes objects.

- **Binary:** `kube-controller-manager`  
- **Runs on:** Control Plane Node(s)  
- **Purpose:** Automate tasks, watch for changes, reconcile actual state with desired state.

---

## 2. How it Works
Controllers continuously watch the cluster state (via the API Server).

If the observed state deviates from the desired state (as defined in etcd via API Server), controllers take corrective action.

Operates on a control loop model:

1. **Observe** → current state from API Server.  
2. **Compare** → with desired state.  
3. **Act** → make API calls to fix mismatches.

---

## 3. Key Controllers Inside `kube-controller-manager`
Below are the main built-in controllers:

| Controller | Responsibility |
|------------|----------------|
| **Node Controller** | Detects node failures, updates node status, and evicts pods if needed. |
| **Replication Controller** | Ensures the correct number of pod replicas are running. |
| **Deployment Controller** | Manages rolling updates and rollbacks for Deployments. |
| **Endpoint Controller** | Populates Endpoints objects (for Services to connect Pods). |
| **Namespace Controller** | Cleans up resources when a namespace is deleted. |
| **Service Account & Token Controller** | Manages service accounts and their API tokens. |
| **Job Controller** | Monitors and runs Jobs until completion. |
| **StatefulSet Controller** | Ensures correct ordering and uniqueness for StatefulSets. |
| **DaemonSet Controller** | Ensures a copy of a Pod runs on all or specific nodes. |
| **Garbage Collector Controller** | Cleans up dependent objects when the owner object is deleted. |
| **Horizontal Pod Autoscaler (HPA) Controller** | Adjusts pod replicas based on CPU/memory metrics. |

---

## 4. Interaction with Other Components
- **API Server** → All controllers watch the API Server for state changes.  
- **Scheduler** → Some controllers create pods that the Scheduler assigns to nodes.  
- **Kubelet** → Executes actions like starting/stopping pods on nodes after controller instructions.

---

## 5. Example Scenario
If a Deployment specifies `replicas: 3`:

1. The Deployment Controller sees that only 2 pods are running.  
2. It creates 1 more pod definition in the API Server.  
3. The Scheduler assigns that pod to a node.  
4. Kubelet starts the pod.  
5. Deployment Controller verifies the count is now 3.

---

## 6. Important Flags for `kube-controller-manager`

| Flag | Description |
|------|-------------|
| `--kubeconfig` | Path to kubeconfig for talking to API Server. |
| `--leader-elect` | Enables leader election for HA control plane. |
| `--controllers` | List of controllers to run (can disable some). |
| `--node-monitor-period` | How often to check node health. |
| `--node-monitor-grace-period` | Time to wait before marking a node unhealthy. |
| `--service-account-private-key-file` | Private key for service account token signing. |

# Kubelet 
## 1. Overview
- **Kubelet** is the **primary node agent** that runs on each Kubernetes node.
- It ensures:
  1. Pods are running in a container runtime (like containerd, CRI-O, or Docker).
  2. The containers match the specifications provided by the **Kubernetes API server**.
- It is **not** responsible for managing containers not created by Kubernetes.

---

## 2. Key Responsibilities
1. **Pod Lifecycle Management**
   - Watches for PodSpecs from the API server.
   - Starts, stops, and restarts containers as required.
   - Monitors container health.
   
2. **Node Registration**
   - Registers the node to the Kubernetes cluster.
   - Updates node status in the API server periodically.

3. **Health Monitoring**
   - Uses **liveness probes** and **readiness probes** to check container health.
   - If a container is unhealthy → it is restarted according to the restart policy.

4. **Volume Management**
   - Mounts and unmounts volumes as defined in Pod specs.
   
5. **Image Management**
   - Pulls required container images from container registries.
   - Handles image caching.

6. **PodSync**
   - Syncs desired state (from API server) with actual state (on the node).
   - If a pod is deleted in the API server, Kubelet terminates it on the node.

---

## 3. Communication Flow
- **From API Server → Kubelet**
  - Kubelet watches the API server for pod assignments.
  
- **From Kubelet → API Server**
  - Sends Node status (CPU, Memory, Disk, Conditions).
  - Sends Pod status updates.

---

## 4. Key Flags and Configurations
| Flag | Purpose |
|------|---------|
| `--kubeconfig` | Path to kubeconfig for API server communication |
| `--container-runtime` | Specify runtime (default: containerd) |
| `--pod-manifest-path` | Static pod manifest directory |
| `--fail-swap-on` | Fail if swap is enabled (default: true) |
| `--max-pods` | Limit max number of pods per node |

---

## 5. Static Pods
- **Definition:** Pods managed directly by Kubelet, **not** by API server.
- **Use case:** Critical system components like `kube-apiserver`, `etcd` on control plane nodes.
- **Configuration:**
  - Kubelet watches a directory (`--pod-manifest-path`).
  - Any YAML file placed there is run automatically.

---

## 6. Kubelet & CNI (Container Network Interface)
- Kubelet calls the CNI plugin to configure pod networking.
- Ensures that pod IP addresses are assigned and routes are set.

---

## 7. Kubelet Security
- Authenticates to the API server using certificates.
- Uses TLS for secure communication.
- May use bootstrap tokens for first registration.

---

## 8. Health Endpoints
| Endpoint | Description |
|----------|-------------|
| `:10248/healthz` | Basic kubelet health check |
| `:10255/metrics` | Deprecated, unauthenticated metrics (disable in prod) |
| `:10250` | Authenticated HTTPS endpoint for kubelet API |

---

## 9. Troubleshooting Kubelet
1. **Check logs**
   ```bash
   journalctl -u kubelet -f
   ```
2. Check node status
  ```bash
  kubectl get nodes
  kubectl describe node <node-name>
  ```
3. Validate kubelet config
  ```bash
    ps -ef | grep kubelet
  ```
4. Restart kubelet
  ```bash
    systemctl restart kubelet
  ```
---

# Kube-Proxy

## 1. Overview
- **Kube-Proxy** is a network proxy that runs on each Kubernetes node.
- It maintains network rules to allow communication to **Pods** from:
  - Other pods in the cluster
  - Nodes
  - External clients
- It implements **service abstraction** by providing **ClusterIP**, **NodePort**, and **LoadBalancer** functionality.

---

## 2. Responsibilities
1. **Service Traffic Routing**
   - Routes requests to the correct backend pods for a service.
2. **Load Balancing**
   - Performs basic round-robin load balancing across endpoints.
3. **Maintains Network Rules**
   - Uses **iptables**, **IPVS**, or **userspace** mode to manage traffic.
4. **Supports Cluster Networking**
   - Works with **CNI** plugins to forward traffic correctly.

---

## 3. Modes of Operation
| Mode       | Description |
|------------|-------------|
| **iptables** | Uses iptables rules for routing; recommended for performance. |
| **IPVS**     | Uses Linux IPVS for efficient load balancing; suitable for large clusters. |
| **userspace** | Legacy mode; kube-proxy listens on NodePort and proxies traffic via userspace; not recommended. |

---

## 4. How Kube-Proxy Works
1. Watches the **API server** for **Service** and **Endpoint** objects.
2. Updates network rules to direct traffic to the **active pods**.
3. Handles dynamic updates when pods are added or removed.

**Flow Example (ClusterIP):**
- Client Pod → ClusterIP → Kube-Proxy → One of the backend Pods
- Kube-Proxy updates iptables/IPVS rules automatically.

---

## 5. Key Features
- **Dynamic updates:** Automatically adjusts rules as pods and services change.
- **Traffic forwarding:** Handles TCP, UDP, and SCTP traffic.
- **Load balancing:** Evenly distributes traffic among healthy pods.
- **Supports NodePort and External Load Balancer** integration.

---

## 6. Important Flags for Kube-Proxy
| Flag | Purpose |
|------|---------|
| `--cluster-cidr` | Pod network CIDR (used for routing) |
| `--proxy-mode` | Mode (iptables, ipvs, userspace) |
| `--hostname-override` | Node name for kube-proxy |
| `--masquerade-all` | SNAT traffic leaving the node to external network |
| `--kubeconfig` | Path to kubeconfig for API server |

---

## 7. CKA Exam Focus
- Know the differences between **iptables** and **IPVS** modes.
- Be able to troubleshoot **service connectivity issues**.
- Understand **ClusterIP, NodePort, LoadBalancer traffic flow**.
- Know how to check kube-proxy logs:
```bash
kubectl logs -n kube-system kube-proxy-<node-name>
```

# Kubernetes Scheduling and Pod Placement — Detailed Notes

## 1. Static Pods
- **Definition:** Static Pods are managed directly by the kubelet on a node, not by the API Server.  
- **Use Case:** Typically used to run control plane components (like kube-apiserver, etcd) in clusters set up with `kubeadm` or manually.  
- **Characteristics:**
  - Defined in a manifest file placed in `/etc/kubernetes/manifests/` (default path).  
  - Kubelet monitors this directory and automatically starts/stops pods if the manifest changes.  
  - They **do not go through the scheduler**.  
  - Appear on the API Server as "Mirror Pods" for visibility.  
- **Example:** Running kube-apiserver as a static pod on a control-plane node.

---

## 2. Manual Scheduling
- **Definition:** The process of scheduling pods without using the Kubernetes scheduler.  
- **How:** Create a Pod manifest and set `nodeName` explicitly.  
- **Example:**
  ```yaml
  apiVersion: v1
  kind: Pod
  metadata:
    name: manual-pod
  spec:
    containers:
    - name: nginx
      image: nginx
    nodeName: worker-node1
  ```
- **When to Use:** Testing, debugging scheduling behavior, or when precise control is required.

---

## 3. Taints and Tolerations
- **Taints (applied on nodes):** Prevent pods from being scheduled unless they tolerate the taint.  
  ```bash
  kubectl taint nodes node1 key=value:NoSchedule
  ```  
- **Tolerations (applied on pods):** Allow pods to be scheduled on tainted nodes.  
  ```yaml
  tolerations:
  - key: "key"
    operator: "Equal"
    value: "value"
    effect: "NoSchedule"
  ```
- **Effects:**
  - `NoSchedule`: Pod will not be scheduled on the node.  
  - `PreferNoSchedule`: Scheduler will try to avoid placing pods but not guaranteed.  
  - `NoExecute`: New pods not scheduled, existing pods evicted if they don’t tolerate.  
- **Use Case:** Isolating workloads (e.g., dedicating GPU nodes for ML workloads).

---

## 4. Node Selectors
- **Definition:** A simple way to constrain pods to specific nodes using labels.  
- **Example:**
  ```yaml
  spec:
    nodeSelector:
      disktype: ssd
  ```
- **Limitation:** Only supports exact match labels (no complex expressions).

---

## 5. Node Affinity
- **Definition:** Advanced form of scheduling constraints, similar to nodeSelector but with more expressive rules.  
- **Types:**
  - **RequiredDuringSchedulingIgnoredDuringExecution** → Must match (hard requirement).  
  - **PreferredDuringSchedulingIgnoredDuringExecution** → Best effort (soft requirement).  
- **Example:**
  ```yaml
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
          - key: disktype
            operator: In
            values:
            - ssd
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 1
        preference:
          matchExpressions:
          - key: region
            operator: In
            values:
            - us-east1
  ```
- **Advantages over Node Selector:**
  - Supports operators like `In`, `NotIn`, `Exists`, `DoesNotExist`.  
  - Allows soft preferences.  

---

# ✅ Summary
- **Static Pods** → Managed by kubelet, not scheduler.  
- **Manual Scheduling** → Set `nodeName` explicitly in pod spec.  
- **Taints & Tolerations** → Node-level restrictions with pod-level exemptions.  
- **Node Selector** → Simple label-based hard matching.  
- **Node Affinity** → Advanced, flexible scheduling with hard & soft rules.  

# Kubernetes Cluster Upgrade and etcd Backup/Restore

## 1. Cluster Upgrade - 
#### https://kubernetes.io/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/

### Overview
- Upgrading a Kubernetes cluster ensures you get new features, bug fixes, and security patches.  
- In production, upgrades should be carefully planned and tested.  
- Tools like **kubeadm** provide a structured upgrade workflow.

### Upgrade Workflow with kubeadm
1. **Plan Upgrade**
   ```bash
   kubeadm upgrade plan
   ```
   - Shows available versions.  
   - Displays components to upgrade.

2. **Upgrade Control Plane Node**
   - Drain the control plane node:
     ```bash
     kubectl drain <control-plane-node> --ignore-daemonsets
     ```
   - Upgrade kubeadm tool:
     ```bash
     apt-get update && apt-get install -y kubeadm=<version>
     ```
   - Apply upgrade:
     ```bash
     kubeadm upgrade apply <version>
     ```
   - Upgrade kubelet and kubectl:
     ```bash
     apt-get install -y kubelet=<version> kubectl=<version>
     systemctl daemon-reload
     systemctl restart kubelet
     ```
   - Uncordon the node:
     ```bash
     kubectl uncordon <control-plane-node>
     ```

3. **Upgrade Worker Nodes**
   - Drain node:
     ```bash
     kubectl drain <worker-node> --ignore-daemonsets
     ```
   - Upgrade kubeadm, kubelet, kubectl (same as control plane).  
   - Restart kubelet.  
   - Uncordon node:
     ```bash
     kubectl uncordon <worker-node>
     ```

### Best Practices
- Upgrade one node at a time.  
- Always test in a staging environment first.  
- Monitor workloads during upgrade.  
- Ensure **etcd backup** is taken before starting upgrade.

---

## 2. etcd Backup and Restore

#### https://discuss.kubernetes.io/t/backup-and-restore-etcd-database/12889

### Overview
- **etcd** is Kubernetes’ primary datastore — stores cluster state and configuration.  
- Backup and restore is critical for disaster recovery.  

### Backup etcd
1. Run etcdctl snapshot save:
   ```bash
   ETCDCTL_API=3 etcdctl      --endpoints=https://127.0.0.1:2379      --cacert=/etc/kubernetes/pki/etcd/ca.crt      --cert=/etc/kubernetes/pki/etcd/server.crt      --key=/etc/kubernetes/pki/etcd/server.key      snapshot save /opt/snapshot.db
   ```

2. Verify snapshot:
   ```bash
   ETCDCTL_API=3 etcdctl snapshot status /opt/snapshot.db
   ```

### Restore etcd
1. Stop kube-apiserver (so it doesn’t connect to etcd during restore).  
2. Run restore command:
   ```bash
   ETCDCTL_API=3 etcdctl snapshot restore /opt/snapshot.db      --data-dir=/var/lib/etcd-from-backup
   ```
3. Update etcd manifest (usually `/etc/kubernetes/manifests/etcd.yaml`) to point to new data directory:  
   ```yaml
   --data-dir=/var/lib/etcd-from-backup
   ```
4. kubelet will restart etcd with restored data.  
5. Start kube-apiserver again and verify cluster state.

### Best Practices
- Automate periodic etcd snapshots.  
- Store backups in remote storage (S3, GCS, etc.).  
- Test restore procedures regularly.  
- Always back up before upgrades or major changes.

---

# ✅ Summary
- **Cluster Upgrade** → Use `kubeadm upgrade`, upgrade control plane first, then worker nodes, drain & uncordon properly.  
- **etcd Backup** → Use `etcdctl snapshot save`, verify with `snapshot status`.  
- **etcd Restore** → Use `snapshot restore`, update `etcd.yaml` manifest, restart kubelet.  
- **Best Practice** → Always take etcd backup before cluster upgrade.  




# Cheat sheet
```sh
1. Kubect config use-context <cluster-name>

```


