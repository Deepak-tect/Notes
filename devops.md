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