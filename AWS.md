# ☁️ AWS Networking Notes

## 🧱 VPC (Virtual Private Cloud)

- A **virtual network** within AWS where you launch resources (e.g., EC2).
- Define your own **IP address range (CIDR block)**, subnets, route tables, and gateways.
- Default VPC is auto-created per region; you can also create custom VPCs.

### 🔑 Key Points:
- Isolated network environment.
- CIDR block example: `10.0.0.0/16`
- Resources in different VPCs **can't communicate directly** unless peered.

---

## 🌐 Internet Gateway (IGW)

- A **horizontally scaled, highly available component** that allows communication between a VPC and the internet.
- Must be **attached to a VPC** and configured in the **route table**.

### 🔑 Key Points:
- Needed for public internet access.
- Not used for internal VPC communication.
- Only **public subnets** use it.

---

## 🧩 Subnet

- A **segment of a VPC** where you can place AWS resources.
- Subnets are tied to **a single Availability Zone**.

### 🔄 Types of Subnets:
- **Public Subnet**: Has a route to the IGW (internet-facing).
- **Private Subnet**: No direct route to IGW; typically used for DBs, internal services.

### 🔑 Key Points:
- Can span multiple subnets in a VPC (e.g., `10.0.1.0/24`, `10.0.2.0/24`).
- Public or private is determined by the **route table**, not the subnet itself.

---

## 🛣️ Route Table

- Contains rules (routes) that **control where network traffic goes**.

### 🧠 Default Route Table:
- Includes the `local` route automatically:




# Bastion Host in AWS

## What is a Bastion Host?

A **Bastion Host** (also known as a jump box) is a special-purpose instance used to securely access resources in a **private subnet** within a **VPC (Virtual Private Cloud)**. It acts as a bridge for SSH or RDP access, typically configured with:

- **Public IP address**
- **Tight security group rules**
- **Logging and monitoring enabled**

## Why Use a Bastion Host?

- Provides **secure access** to instances in private subnets.
- Reduces attack surface by avoiding exposing internal resources to the internet.
- Enables centralized auditing of access to critical systems.

---

## Architecture
### Example 1: Bastion Host in the Same VPC

```yaml
VPC: 10.0.0.0/16
|
|-- Public Subnet (10.0.1.0/24)
| |-- Bastion Host (EC2 with public IP)
|
|-- Private Subnet (10.0.2.0/24)
|-- Application Server (EC2 with no public IP)

```



**Steps:**

1. Launch a Bastion Host EC2 instance in the **public subnet** with a public IP.
2. Configure **Security Groups**:
   - Bastion Host: Allow inbound SSH (port 22) from your IP.
   - Private EC2: Allow inbound SSH from the Bastion Host's **private IP** or **security group**.
3. SSH Flow:
   - SSH to Bastion Host: `ssh ec2-user@<bastion-public-ip>`
   - Then SSH to private EC2: `ssh ec2-user@<private-ec2-private-ip>`



### Example 2: Bastion Host in a Different VPC (with VPC Peering)

```yaml
VPC-A (10.0.0.0/16) - Bastion VPC
|
|-- Public Subnet (10.0.1.0/24)
| |-- Bastion Host (EC2 with public IP)

VPC-B (192.168.0.0/16) - Application VPC
|
|-- Private Subnet (192.168.1.0/24)
|-- Private EC2 (no public IP)
```

**Steps:**

1. Create **VPC Peering** between VPC-A and VPC-B.
2. Update **Route Tables** in both VPCs to route traffic to each other.
3. Configure **Security Groups**:
   - Bastion Host (in VPC-A): allow SSH from your IP.
   - Private EC2 (in VPC-B): allow SSH from Bastion Host’s private IP or security group.
4. SSH Flow:
   - SSH to Bastion Host: `ssh ec2-user@<bastion-public-ip>`
   - Then SSH to private EC2 in VPC-B: `ssh ec2-user@<private-ec2-private-ip>`

> 💡 Make sure to allow inter-VPC communication in **NACLs** and **Security Groups**.

---

## Best Practices

- Use **key-based authentication**.
- Limit SSH access to specific IP ranges (e.g., your office/home IP).
- Use **Session Manager** (AWS SSM) instead of Bastion Host where possible.
- Enable **CloudTrail** and **VPC Flow Logs** for auditing.
- Keep the Bastion Host updated and patched.

---

## References

- [AWS Bastion Host Documentation](https://docs.aws.amazon.com/quickstart/latest/linux-bastion/welcome.html)
- [VPC Peering](https://docs.aws.amazon.com/vpc/latest/peering/what-is-vpc-peering.html)
- [AWS Systems Manager Session Manager](https://docs.aws.amazon.com/systems-manager/latest/userguide/session-manager.html)



# Classic Load Balancer (CLB) in AWS

## Overview

The **Classic Load Balancer (CLB)** is the original load balancing service provided by AWS under the **Elastic Load Balancing (ELB)** family. It operates at **Layer 4 (TCP)** and **Layer 7 (HTTP/HTTPS)** of the OSI model and was designed to distribute incoming traffic across multiple Amazon EC2 instances.

CLB has now been **largely deprecated** in favor of **Application Load Balancer (ALB)** and **Network Load Balancer (NLB)**, which offer more features and better performance.

---

## Key Features

- Supports **TCP, SSL, HTTP, and HTTPS**.
- Basic load balancing across EC2 instances in **one or more Availability Zones**.
- Provides **health checks** to route traffic only to healthy instances.
- Integrated with **EC2-Classic** and **EC2-VPC** networking.
- Sticky sessions (session affinity) using **application cookies** or **duration-based cookies**.
- SSL termination support.
- Support for **cross-zone load balancing**.

---

## Advantages

- **Simple to set up**: Easy for quick deployments.
- **Integrated with legacy AWS environments**, including EC2-Classic.
- **Supports Layer 4 and Layer 7 protocols** in one load balancer.
- Useful for **basic HTTP or TCP** traffic distribution without complex routing needs.

---

## Limitations

- **Lacks content-based routing**: No path- or host-based routing (unlike ALB).
- **No support for WebSockets or HTTP/2**.
- **Limited visibility and metrics**: Fewer CloudWatch metrics compared to ALB/NLB.
- **Scalability and performance constraints** for high throughput or low latency applications.
- **No IP targeting** or Lambda integration.
- **Less cost-efficient** and feature-rich than modern alternatives.

---

## Why CLB Is No Longer Recommended

1. **Feature Limitation**:
   - Cannot route requests based on host/path, headers, query string, etc.
   - Lacks modern protocol support (e.g., HTTP/2, WebSockets).

2. **Not Suitable for Microservices**:
   - Incompatible with container-based and microservices architecture (no dynamic port support).

3. **Obsolete Design**:
   - Designed before ALB/NLB existed; lacks modularity and modern integration.

4. **Poor Monitoring & Logging**:
   - Limited CloudWatch metrics.
   - No support for AWS WAF or advanced logging.

5. **AWS Recommendations**:
   - AWS **recommends using ALB or NLB** for all new applications.
   - CLB is still supported but **not actively enhanced**.

---

## Migration Recommendation

- Use **Application Load Balancer (ALB)** for:
  - HTTP/HTTPS traffic
  - Path-based or host-based routing
  - WebSocket, HTTP/2
  - Microservices and containerized applications

- Use **Network Load Balancer (NLB)** for:
  - TCP/UDP traffic
  - High-performance, low-latency requirements
  - Static IP support

---


### Classic Load Balancer (CLB) does support Layer 7, but only in a very limited way.
It can inspect HTTP/HTTPS traffic and perform basic functions like:

* SSL termination

* Sticky sessions using application cookies

* Basic health checks with HTTP/HTTPS endpoints

But it does NOT support advanced Layer 7 features, such as:
Path-based routing (e.g., /api/* to one target group and /static/* to another)

* Host-based routing (e.g., api.example.com vs. static.example.com)

* Routing based on headers, query strings, or HTTP methods

Those features are only available in the Application Load Balancer (ALB), which is the true Layer 7 load balancer designed for modern web architectures.

## References

- [Classic Load Balancer Documentation](https://docs.aws.amazon.com/elasticloadbalancing/latest/classic/introduction.html)
- [Elastic Load Balancing Comparison](https://docs.aws.amazon.com/elasticloadbalancing/latest/userguide/what-is-load-balancing.html)
- [AWS CLB Migration Guide](https://docs.aws.amazon.com/elasticloadbalancing/latest/userguide/migrate-classic-elb.html)


# Application Load Balancer (ALB) in AWS

## Overview
Application Load Balancer (ALB) is one of the load balancers provided by AWS under the Elastic Load Balancing (ELB) service. It operates at the **application layer (Layer 7)** of the OSI model and is best suited for **HTTP and HTTPS traffic**.

## Key Features

- **Layer 7 Routing**: Supports advanced routing based on content such as URL path, host headers, query string, and HTTP headers.
- **Target Groups**: Routes traffic to target groups consisting of EC2 instances, ECS containers, Lambda functions, or IP addresses.
- **Host-based and Path-based Routing**: Direct traffic to different services based on the request's host or path.
- **Support for WebSockets**: ALB supports WebSocket protocol for real-time communications.
- **Containerized Application Support**: Works seamlessly with Amazon ECS using dynamic port mapping.
- **SSL Termination**: Supports HTTPS with SSL/TLS certificate management via AWS Certificate Manager (ACM).
- **Health Checks**: Continuously monitors the health of registered targets and routes traffic only to healthy ones.
- **Security Features**: Integrates with AWS WAF and Security Groups for enhanced protection.

## Use Cases

- Microservices-based architecture with multiple services behind a single ALB.
- Hosting multiple domains or subdomains with host-based routing.
- Redirecting HTTP to HTTPS using listener rules.
- Serving both dynamic and static content from different backends.

## Pricing

- Charged based on:
  - **Load balancer hours**
  - **LCU (Load Balancer Capacity Units)**, based on the highest usage among:
    - New connections
    - Active connections
    - Processed bytes
    - Rule evaluations

## ALB vs NLB vs CLB

| Feature               | ALB                        | NLB                        | CLB                        |
|----------------------|----------------------------|----------------------------|----------------------------|
| OSI Layer            | 7 (Application)            | 4 (Transport)              | 4/7 (Classic)              |
| Protocols Supported  | HTTP, HTTPS, WebSocket     | TCP, TLS, UDP              | HTTP, HTTPS, TCP           |
| Content-based Routing| ✅                          | ❌                          | Limited                    |
| WebSocket Support    | ✅                          | ✅                          | ❌                          |
| Use Case             | Web apps, Microservices    | High performance, low latency | Legacy apps           |

## Sample Use in AWS CLI

```bash
aws elbv2 create-load-balancer \
  --name my-alb \
  --subnets subnet-abc123 subnet-def456 \
  --security-groups sg-0123456789abcdef0 \
  --scheme internet-facing \
  --type application \
  --ip-address-type ipv4

```

# Network Load Balancer (NLB) in AWS

## Overview

The **Network Load Balancer (NLB)** is one of the load balancers provided by AWS under the **Elastic Load Balancing (ELB)** family. It operates at **Layer 4 (Transport Layer)** of the OSI model and is designed to handle **millions of requests per second** with ultra-low latency and high throughput.

NLB is best suited for **TCP, UDP, and TLS traffic**, and is optimized for performance and static IP requirements.

---

## Key Features

- **Layer 4 Load Balancing**: Routes traffic based on IP protocol data (not HTTP/S content).
- **High Performance**: Capable of handling sudden and volatile traffic patterns.
- **Static IP Support**: Assign a static IP per Availability Zone or bring your own IP.
- **TLS Termination**: Optional SSL offloading at Layer 4.
- **Zonal Isolation**: Each AZ can have its own IP and targets for fault isolation.
- **Preserve Client IP**: Client IP address is preserved and passed to the backend.
- **Target Types**: Supports EC2 instances, IP addresses, and AWS PrivateLink services.

---

## Advantages

- ✅ **High scalability**: Millions of requests per second.
- ✅ **Ultra-low latency**: Ideal for latency-sensitive applications.
- ✅ **Static IP support**: Each AZ can have a dedicated IP.
- ✅ **Zonal failover**: Isolates failure to a single zone.
- ✅ **TLS termination** (optional): Improves security and simplifies backend configuration.
- ✅ **Preserves source IP**: Useful for logging and firewall rules.
- ✅ **Supports both TCP and UDP protocols** (including gRPC, DNS, SIP, etc.).

---

## Disadvantages

- ❌ **No Layer 7 features**: Cannot do path- or host-based routing.
- ❌ **No native support for sticky sessions** (session affinity).
- ❌ **Limited monitoring**: Fewer CloudWatch metrics compared to ALB.
- ❌ **More complex health checks**: Only TCP or HTTP checks.
- ❌ **More expensive** than ALB for low-throughput apps due to LCU pricing.

---

## Use Cases

Use **NLB** when:

- You need **ultra-low latency** and high performance (e.g., gaming, financial apps).
- You want to **preserve client IP** for backend logic or logs.
- You use **custom protocols or non-HTTP/S traffic** (e.g., TCP/UDP).
- Your application requires **static IPs or BYOIP**.
- You need to **expose services via AWS PrivateLink**.

Avoid NLB if:

- You need content-based routing (use **ALB** instead).
- Your app is purely web-based with HTTP/S and benefits from Layer 7 features.

---

## NLB vs ALB vs CLB

| Feature                     | NLB                     | ALB                        | CLB                        |
|----------------------------|--------------------------|-----------------------------|-----------------------------|
| OSI Layer                  | Layer 4 (TCP/UDP/TLS)    | Layer 7 (HTTP/HTTPS)        | Layer 4/7 (legacy)          |
| Protocols Supported        | TCP, UDP, TLS            | HTTP, HTTPS, WebSocket      | HTTP, HTTPS, TCP            |
| Routing Type               | IP-based                 | Host/Path-based             | Basic                       |
| WebSocket Support          | ✅                        | ✅                          | ❌                          |
| Sticky Sessions            | ❌                        | ✅                          | ✅                          |
| Static IP Support          | ✅                        | ❌                          | ❌                          |
| SSL Termination            | ✅ (optional)             | ✅                          | ✅                          |
| Best Use Case              | High performance, TCP    | Web apps, microservices     | Legacy/basic applications   |

---



## Preserving Client IP in AWS Load Balancers

### Overview

When traffic flows through an AWS Load Balancer, it's important to know whether the **original client IP address** is visible to the backend server. This impacts **security, logging, access control**, and **protocol-specific behavior**.

---

## Summary Table

| Load Balancer | Layer | Preserves Client IP (Network Level) | X-Forwarded-For Header | Notes |
|---------------|-------|--------------------------------------|-------------------------|-------|
| Classic LB (CLB) | Layer 4/7 | ❌ | ✅ (HTTP/HTTPS only) | Client IP seen only in HTTP headers |
| Application LB (ALB) | Layer 7 | ❌ | ✅ (HTTP/HTTPS only) | Backend sees LB IP at network level |
| Network LB (NLB) | Layer 4 | ✅ | Optional | Backend receives true client IP directly |

---

## Why It Matters

Some applications and use cases **require access to the real client IP**, not the load balancer's IP.

### Key Scenarios:
- **IP whitelisting** using security groups or backend firewall rules.
- **Rate limiting** based on client IP.
- **Audit logs** or geo-based analysis.
- **Protocol-specific logic** for TCP/UDP connections.

---

## NLB: True Client IP Preservation

- NLB operates at **Layer 4**, passing TCP/UDP packets without modification.
- It **does not terminate connections** (unless TLS termination is explicitly enabled).
- Backend targets see the **real source IP** as the connection initiator.
- This works for **any protocol**, not just HTTP.

---

## CLB and ALB: Limited Preservation

- These operate at **Layer 7 (HTTP/HTTPS)** for client traffic.
- The **original client IP is replaced** by the load balancer’s IP.
- Client IP is only available via the `X-Forwarded-For` HTTP header.
  - Requires backend parsing.
  - Not available in non-HTTP protocols.
  - Not useful for firewalls or network ACLs.

---

## Visual Diagram



## References

- [AWS NLB Documentation](https://docs.aws.amazon.com/elasticloadbalancing/latest/network/introduction.html)
- [Load Balancer Feature Comparison](https://docs.aws.amazon.com/elasticloadbalancing/latest/userguide/compare-types.html)
- [Pricing](https://aws.amazon.com/elasticloadbalancing/pricing/)



# 🔄 AWS Auto Scaling Group (ASG) - Notes

## 📘 What is an Auto Scaling Group?

- An **Auto Scaling Group (ASG)** is a service that **automatically manages a fleet of EC2 instances**.
- It ensures a specified **number of instances are always running** and can **scale out or in** based on demand.
- It works with **Launch Templates** or **Launch Configurations** to define how new instances should be created.

---

## ❓ Why Use ASG?

- ✅ **High Availability**: Replaces unhealthy instances automatically.
- ✅ **Elasticity**: Scales capacity up/down automatically based on load.
- ✅ **Cost Efficiency**: Run only as many instances as needed.
- ✅ **Fault Tolerance**: Supports multiple AZs to maintain redundancy.
- ✅ **Integration**: Works with Load Balancers, CloudWatch, Target Groups, etc.

---

## ⏱️ When to Use ASG?

- When your application demand **varies over time**.
- For **stateless** services that can be duplicated easily (e.g., web servers).
- When you want **automatic recovery** from EC2 failures.
- When building **resilient, scalable** architectures with **load balancing**.

---

## ⚙️ How ASG Works

1. Define a **Launch Template** (preferred) or **Launch Configuration**.
2. Create an **Auto Scaling Group** with:
   - Min/Max/Desired instance count
   - VPC Subnets (across AZs)
   - Target Group (for Load Balancing, optional)
3. Attach **Scaling Policies** to dynamically adjust instance count.
4. Monitor instance health via EC2 or Load Balancer.

---

## 🧩 Key Components

| Component             | Description                                                             |
|-----------------------|-------------------------------------------------------------------------|
| **Launch Template**   | Blueprint for launching instances (AMI, type, key pair, security group) |
| **Min Size**          | Minimum number of running instances                                     |
| **Max Size**          | Maximum number of allowed instances                                     |
| **Desired Capacity**  | Target number of instances (default count)                              |
| **Health Check Type** | EC2 or ELB-based; helps in replacing unhealthy instances                |
| **Availability Zones**| Where ASG can launch instances                                          |
| **Target Group**      | Required if using ALB/NLB                                               |

---

## 🎚️ Scaling Options

### 1. ⚡ **Dynamic Scaling (Policy-based)**

- **Target Tracking** (Recommended):
  - E.g., Maintain average CPU utilization at 60%
- **Step Scaling**:
  - Add/remove X instances based on alarm threshold breaches.
- **Simple Scaling**:
  - Triggered by a single CloudWatch alarm.

### 2. 🕒 **Scheduled Scaling**

- Predefine **specific times** to scale up/down.
- Useful for predictable load patterns (e.g., scale at 9 AM every weekday).

### 3. 🧠 **Predictive Scaling** (Advanced)

- Uses ML to **forecast future traffic** and scales proactively.
- Needs historical metrics; supported in some AWS regions.

---

## 💡 Best Practices

- Use **Launch Templates** over Launch Configurations (they’re newer and more flexible).
- Distribute across **multiple AZs** for high availability.
- Attach to an **Application Load Balancer** for better traffic distribution.
- Set **grace period** (e.g., 300s) to let EC2 start before health checks.
- Use **lifecycle hooks** for custom actions during instance launch/terminate (e.g., config setup).
- Consider using **instance refresh** to automatically update instances (e.g., with new AMIs).

---

## 🚨 Common Interview Points

| Question | Quick Answer |
|---------|---------------|
| Can ASG replace a crashed instance? | ✅ Yes, using health checks. |
| Can you scale based on custom metrics? | ✅ Yes, via CloudWatch alarms. |
| What's the difference between desired and min size? | Min = minimum always running, Desired = target count right now |
| How does ASG handle AZ failure? | Distributes instances across AZs for fault tolerance. |
| What happens if a scaling policy tries to go beyond Max size? | ❌ It won’t scale beyond Max. Alarm is logged. |

---

## 🧠 Quick Recap

| Term               | Meaning |
|--------------------|---------|
| **ASG**            | Manages instance count |
| **Min/Max/Desired**| Defines instance limits |
| **Health Check**   | EC2 or ELB-based instance validation |
| **Scaling Policy** | Rules for when/how to scale |
| **Launch Template**| Instance config (AMI, instance type, etc.) |
| **Target Group**   | Load balancer grouping of instances |

