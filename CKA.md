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






