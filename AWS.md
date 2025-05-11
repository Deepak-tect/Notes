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


# 💾 AWS EC2 Instance Storage 

## 📘 What is EC2 Instance Storage?

- **Instance store (a.k.a. ephemeral storage)** is **temporary block-level storage** for EC2 instances.
- It is **physically attached to the host server** and provides **high-speed, low-latency** storage.
- Data persists **only during the instance lifetime** — **deleted on stop, terminate, or failover**.

---

## 🧩 Types of Storage in EC2

| Type               | Description                                              |
|--------------------|----------------------------------------------------------|
| **Instance Store** | Temporary, high-performance, physically-attached storage |
| **EBS (Elastic Block Store)** | Persistent, network-attached block storage        |

> 🔸 Note: Not all EC2 instance types come with instance store volumes.

---

## ⚙️ Use Cases of Instance Store

- High-speed temporary storage needs:
  - Buffer/cache
  - Scratch data
  - Temporary processing (e.g., video rendering)
  - No need for persistence after shutdown

---

## ✅ Advantages of Instance Store

- ⚡ **Very fast** (low latency, high IOPS)
- 💸 **No additional cost** (included with instance)
- 📉 Lower overhead than EBS (no network latency)

---

## ❌ Disadvantages of Instance Store

- 💀 **Data loss on stop/terminate** (non-persistent)
- 🔁 No snapshot support or easy backup
- 🧱 Tied to instance lifecycle — **cannot be detached/moved**
- 📦 Limited instance types support it (e.g., i3, d2, h1)

---

## 🔧 Overcoming Limitations

| Limitation             | Workaround                                                |
|------------------------|------------------------------------------------------------|
| No persistence         | Use **EBS volumes** for persistent storage                 |
| No snapshot/backup     | Sync to **S3** or create backup scripts                    |
| Data loss on reboot    | Store checkpoints or interim state in persistent storage   |
| Limited flexibility    | Combine with EBS or S3 for hybrid approach                 |

---

## 💡 Instance Store vs EBS

| Feature                | Instance Store       | EBS                          |
|------------------------|----------------------|-------------------------------|
| **Persistence**        | ❌ Lost on stop       | ✅ Persistent                  |
| **Performance**        | ⚡ Very fast (local)  | 🚀 Fast but network-attached  |
| **Attachable**         | ❌ Fixed to instance  | ✅ Can be attached/detached    |
| **Snapshots**          | ❌ Not supported      | ✅ Supported                   |
| **Cost**               | ✅ Free (included)    | 💸 Charged separately          |

---

## What About Instance Store?
* Instance store is not included by default.

* Only certain instance types (like i3, d2, h1, c5d, etc.) support instance store volumes.

* Even in those types, you need to explicitly configure and mount the instance store if you want to use it.

## How to Check Where You're Saving:
Run lsblk or df -h in the EC2 terminal:

* EBS volumes usually appear as /dev/xvda, /dev/nvme0n1

* Instance store devices might be /dev/nvme1n1, /dev/sd*, and mounted separately

## 🧠 Summary for Interviews

- Instance store = **ephemeral, high-speed**, **temporary disk** tied to the EC2 host.
- Best for **temporary** workloads, **scratch data**, or **buffer/cache** layers.
- Use **EBS or S3** for persistence, backups, and durability.


# 💾 Amazon EBS (Elastic Block Store)

## 📘 What is EBS?

- **EBS** is durable, block-level storage for EC2 instances.
- It behaves like a virtual hard disk and can store **OS, applications, and data**.
- It is **persistent**, meaning the data survives even when the instance stops or terminates (if not deleted explicitly).

---

## ✅ Advantages of EBS

- 🧱 **Persistent**: Data is retained across reboots/stops.
- ⚙️ **Flexible**: Can attach/detach volumes from instances.
- 🚀 **Scalable**: Resize volumes without downtime.
- 🔐 **Secure**: Supports encryption at rest and in transit.
- ♻️ **Backup Friendly**: Create **snapshots** and restore them anytime.
- 📈 **Performance Options**: Choose volume types based on throughput (gp3, io1, st1, etc.)

---

## ❌ Disadvantages of EBS

- 📡 **Network Dependent**: It’s a network-attached storage, so performance depends on bandwidth and instance type.
- 💸 **Charged Separately**: You pay for EBS volume usage independently of EC2.
- ❗ **AZ Locked**: Can only be attached to instances in the **same Availability Zone**.
- 🧼 **Manual Cleanup**: Volumes persist even after instance termination unless “Delete on termination” is checked.

---

## 🪜 Steps to Set Up & Attach EBS Volume

### 🔧 1. Create EBS Volume
- Go to **EC2 Console → Volumes → Create Volume**
- Choose:
  - Volume Type (e.g., gp3)
  - Size (e.g., 20 GiB)
  - Availability Zone (same as the instance)
  - Encryption (optional)

### 🔗 2. Attach to EC2 Instance
- Go to **Actions → Attach Volume**
- Select the instance ID
- Specify device name (e.g., `/dev/xvdf`)

### 📦 3. Format & Mount in EC2
SSH into your instance and run:
```bash
# Check volume
lsblk

# Format the new volume
sudo mkfs -t ext4 /dev/xvdf

# Create mount point
sudo mkdir /data

# Mount the volume
sudo mount /dev/xvdf /data

# Make it persistent after reboot
echo "/dev/xvdf /data ext4 defaults,nofail 0 2" | sudo tee -a /etc/fstab

```
### 💾 Amazon EBS (Elastic Block Store) - Notes

## 📘 What is EBS?

- **EBS** is durable, block-level storage for EC2 instances.
- It behaves like a virtual hard disk and can store **OS, applications, and data**.
- It is **persistent**, meaning the data survives even when the instance stops or terminates (if not deleted explicitly).

---

## ✅ Advantages of EBS

- 🧱 **Persistent**: Data is retained across reboots/stops.
- ⚙️ **Flexible**: Can attach/detach volumes from instances.
- 🚀 **Scalable**: Resize volumes without downtime.
- 🔐 **Secure**: Supports encryption at rest and in transit.
- ♻️ **Backup Friendly**: Create **snapshots** and restore them anytime.
- 📈 **Performance Options**: Choose volume types based on throughput (gp3, io1, st1, etc.)

---

## ❌ Disadvantages of EBS

- 📡 **Network Dependent**: It’s a network-attached storage, so performance depends on bandwidth and instance type.
- 💸 **Charged Separately**: You pay for EBS volume usage independently of EC2.
- ❗ **AZ Locked**: Can only be attached to instances in the **same Availability Zone**.
- 🧼 **Manual Cleanup**: Volumes persist even after instance termination unless “Delete on termination” is checked.

---

## 🪜 Steps to Set Up & Attach EBS Volume

### 🔧 1. Create EBS Volume
- Go to **EC2 Console → Volumes → Create Volume**
- Choose:
  - Volume Type (e.g., gp3)
  - Size (e.g., 20 GiB)
  - Availability Zone (same as the instance)
  - Encryption (optional)

### 🔗 2. Attach to EC2 Instance
- Go to **Actions → Attach Volume**
- Select the instance ID
- Specify device name (e.g., `/dev/xvdf`)

### 📦 3. Format & Mount in EC2
SSH into your instance and run:
```bash
# Check volume
lsblk

# Format the new volume
sudo mkfs -t ext4 /dev/xvdf

# Create mount point
sudo mkdir /data

# Mount the volume
sudo mount /dev/xvdf /data

# Make it persistent after reboot
echo "/dev/xvdf /data ext4 defaults,nofail 0 2" | sudo tee -a /etc/fstab
```

## 📏 Steps to Resize EBS Volume
### 🧱 A. Resize from AWS Console
1. Go to EC2 → Volumes

2. Select your volume → Click Modify Volume

3. Increase size (e.g., 20 → 30 GiB)

4. Click Modify → Confirm

### 🖥️ B. Extend File System (On EC2)
SSH into your instance and run:
```sh
# Check the new size is visible
lsblk

# Extend the partition (if needed for older volumes)
sudo growpart /dev/xvda 1   # only if using partitions

# Resize filesystem
# For ext4:
sudo resize2fs /dev/xvda1

# For xfs:
sudo xfs_growfs -d /

```

## 📦 Resize Root EBS Volume
Steps:
1. Follow Modify Volume steps above to resize root volume.

2. SSH into EC2 and use lsblk to verify volume size.

3. Resize file system as shown above (use xfs_growfs or resize2fs).

   * For AMIs using xfs (common in Amazon Linux 2, RHEL), use xfs_growfs.
   * For ext4, use resize2fs.

### 🧠 Quick Recap for Interviews
| Feature     | EBS                                   |
| ----------- | ------------------------------------- |
| Persistence | ✅ Yes (across reboot/stop/start)      |
| Backup      | ✅ Snapshots supported                 |
| Resizing    | ✅ Online resizing supported           |
| Attachment  | ✅ Attach/detach to/from EC2 instances |
| Scope       | Limited to same Availability Zone     |
| Use Case    | OS, databases, app data, logs         |


# 🐧 Linux Basics for Working with Amazon EBS

This note covers essential Linux concepts and commands needed to manage and use EBS volumes with EC2 instances — explained in **simple language** with **real-world analogies**.

---

## 📦 1. What are Block Devices?

- A **block device** is a type of storage (like a hard disk, SSD, EBS volume) that stores data in small fixed-size blocks.
- Examples:
  - `/dev/xvda` → Root EBS volume
  - `/dev/xvdf` → Attached data EBS volume

🔎 To see all block devices:
```bash
lsblk
```
🧠 Analogy: Think of it as a bookshelf with books (blocks) you can read/write individually.

## 🗂️ 2. Mounting a Volume

### Why mount?
Linux cannot use a volume until it is mounted (linked) to a folder.

### Mount Steps:
1. Format the volume (like setting up a filing system).

2. Create a mount point (like creating a folder where volume contents appear).

3. Mount the volume to the folder.

🛠 Example:
```sh
sudo mkfs -t ext4 /dev/xvdf       # Format volume
sudo mkdir /data                  # Create mount folder
sudo mount /dev/xvdf /data        # Mount volume
```
🔍 Check success:
```sh
df -h        # Shows mounted filesystems
```

## 🔁 3. Make Mount Persistent (After Reboot)
Linux “forgets” manual mounts after reboot.

To keep it mounted:

1. Edit the /etc/fstab file.

2. Add an entry like:

```sh 
/dev/xvdf  /data  ext4  defaults,nofail  0  2
```
🧠 Analogy: Think of /etc/fstab as your "Startup To-Do List" for disks.

## 📁 4. Understanding Device Paths
* Linux identifies storage using paths like /dev/xvda, /dev/nvme1n1.

* These may vary depending on the instance type (e.g., NVMe on newer instances).

```sh
lsblk        # List devices
df -h        # Show mounted ones
```

## 🧱 5. File Systems
* File system is a method to organize and store files.

* Common types: ext4, xfs

To format a volume:
```sh
sudo mkfs -t ext4 /dev/xvdf
```

## 📌 6. Mount Point
* A mount point is just a folder (like /data, /mnt/storage) where your volume will appear.

To create one:
```sh
sudo mkdir /data
```


## 🔓 7. Permissions & Ownership
To allow users to access the volume:
```sh
sudo chown ec2-user:ec2-user /data   # Change ownership to your user
```
To set permissions:
```sh
sudo chmod 755 /data

```

## 🧠Useful Linux Commands for EBS

| Command                          | Description                                           |
|----------------------------------|-------------------------------------------------------|
| `lsblk`                          | List block devices                                    |
| `df -h`                          | View mounted filesystems                              |
| `mkfs -t ext4`                   | Format a volume                                       |
| `mount /dev/xvdf /data`          | Mount volume to folder                                |
| `mkdir /data`                    | Create mount point                                    |
| `chmod / chown`                  | Set permissions and ownership                         |
| `cat /etc/fstab`                 | View persistent mount configuration                   |
| `sudo growpart`                  | Extend disk partition (when resizing)                 |
| `resize2fs` / `xfs_growfs`       | Resize filesystem on volume                           |

## 💡 Quick Scenario Example
You attach a new EBS volume /dev/xvdf and want to use it:
```sh
lsblk                              # See the volume
sudo mkfs -t ext4 /dev/xvdf        # Format it
sudo mkdir /data                   # Create folder to use it
sudo mount /dev/xvdf /data         # Attach volume to folder
df -h                              # Confirm it's mounted
```
To make it persist after reboot:
```sh
echo "/dev/xvdf /data ext4 defaults,nofail 0 2" | sudo tee -a /etc/fstab
```

## 🧠 Bonus Tips
1. Don't format root volumes! Be careful with device names.

2. Always backup important data before resizing or modifying volumes.

3. You can use EBS snapshots for backup and restoration.


# 💾 AWS EBS Volume Types – Notes and Use Cases

Amazon EBS (Elastic Block Store) offers multiple types of volumes to match different workload needs. Here's a quick guide to each type, with simple explanations and when to use them.

## 📚 Prerequisites: Basic Terms Explained



### ⚡ What is IOPS (Input/Output Operations Per Second)?
- It measures how fast the volume can read/write small chunks of data.
- Higher IOPS = better performance for databases and real-time apps.
- 📌 Example: If your database reads and writes 200 files per second, you need ~200 IOPS.

### 🚀 What is Throughput?
- It is the **total amount of data** (in MB/s) that can be transferred.
- Useful for **large, sequential reads/writes** like data processing.
- 📌 Example: Copying 1GB log files — throughput matters more than IOPS.

### ⏱️ What is Latency?
- The **delay** before a read/write operation starts.
- Lower latency = faster response time.
- 📌 Example: A database that needs instant access to each record benefits from low latency.

---



---

## 📘 1. General Purpose SSD (gp3)

### ✅ Use When:
- You need **balanced performance and cost**.
- Use for boot volumes, dev/test environments, low-latency apps.

### 📊 Features:
- Baseline performance of 3,000 IOPS.
- You can provision IOPS and throughput separately from storage.

### 🧠 Example Use Case:
> Hosting a WordPress blog or running a web server.

---

## 📘 2. General Purpose SSD (gp2) [Legacy]

### ✅ Use When:
- Older volumes (still supported but gp3 is recommended).
- Performance scales with size (up to 16,000 IOPS).

### 🧠 Example Use Case:
> Legacy systems or migration scenarios.

---

## 🚀 3. Provisioned IOPS SSD (io1 / io2)

### ✅ Use When:
- You need **high performance, low latency, and high durability**.
- Designed for **I/O-intensive applications** like large databases.

### 📊 Features:
- io2 offers higher durability (99.999%).
- Supports up to 64,000 IOPS per volume.

### 🧠 Example Use Case:
> Running an enterprise-grade production **MySQL, Oracle, or SAP HANA** database.

---

## 💽 4. Throughput Optimized HDD (st1)

### ✅ Use When:
- You need **high throughput** but not high IOPS.
- Ideal for **big data** and **sequential workloads**.

### 📊 Features:
- Lower cost.
- Cannot be used as a root volume.

### 🧠 Example Use Case:
> Processing logs, **Hadoop**, or **streaming data analytics**.

---

## 💾 5. Cold HDD (sc1)

### ✅ Use When:
- You have **rarely accessed, cold data**.
- You want **the lowest cost** for infrequent I/O.

### 📊 Features:
- Very low cost per GB.
- Lowest performance (but acceptable for cold storage).

### 🧠 Example Use Case:
> Long-term backups, old archives, infrequent log access.

---

## 📌 6. Magnetic (Standard) [Deprecated for new volumes]

### ✅ Use When:
- Legacy support only.
- Not recommended for new use.

---

## 🧮 Summary Table

| Volume Type | Storage Medium | IOPS | Throughput | Use Case |
|-------------|----------------|------|------------|----------|
| gp3         | SSD            | Up to 16,000 | Up to 1,000 MB/s | General-purpose apps |
| gp2         | SSD            | Scales with size | Moderate | Legacy general-purpose |
| io1/io2     | SSD            | Up to 64,000 | High | Databases & IOPS-intensive |
| st1         | HDD            | Moderate | High | Big data, logs |
| sc1         | HDD            | Low | Low | Cold storage, backups |
| magnetic    | HDD            | Low | Low | Legacy only |

---

## 💡 Tips for Choosing a Volume

- Use **gp3** for most general workloads – it's cost-effective and flexible.
- Use **io2** if you're running **mission-critical databases**.
- Use **st1** or **sc1** for large-scale, low-cost storage.
- **Never use st1/sc1** for boot volumes — they’re not supported as root.

---

## 🧠 Quick Interview Pointers

- **gp3 vs gp2?** → gp3 is newer, allows you to configure IOPS and throughput separately.
- **io2 vs io1?** → io2 offers better durability (good for enterprise).
- **st1 vs sc1?** → st1 is for throughput-heavy access, sc1 is for cold archival.
- **Which can't be root?** → st1 and sc1 can't be used as root volumes.

## 📚 Final Thought

Choosing the right volume type is a tradeoff between **performance**, **cost**, and **access patterns**. For most users, **gp3** is a great starting point, and you can scale up to **io2** or switch to **st1/sc1** based on actual usage.

# 🧩 Amazon EBS Snapshots

## 📖 What is an EBS Snapshot?

An **EBS snapshot** is a **point-in-time backup** of an EBS volume, stored in Amazon S3.

- Snapshots are **incremental**: only changed blocks are saved after the first snapshot.
- Can be used to **restore a volume** or **create a new volume** in the same or another region.

---

## ✅ Use Cases

- Backup strategy for EBS volumes (databases, app servers).
- Disaster recovery.
- Clone an environment (dev/test).
- Migrate data across regions.
- Rollback before patching or upgrades.

---

## 🛠️ How to Create a Snapshot

### ✅ Manual (via Console)

1. Go to EC2 > Volumes.
2. Select a volume.
3. Click `Actions > Create Snapshot`.
4. Add description and tags.

### ✅ CLI

```bash
aws ec2 create-snapshot \
  --volume-id vol-0123456789abcdef0 \
  --description "My snapshot"

```
# 🔁 Automate Snapshot Creation

## ✅ Option 1: Amazon Data Lifecycle Manager (DLM)
Go to **EC2 Dashboard > Lifecycle Manager**.

Create a policy to:
- Target specific volume tags.
- Set snapshot frequency (hourly, daily, etc.).
- Retain for N days.

## ✅ Option 2: AWS Backup
- Unified backup service for EC2, EFS, RDS, etc.
- More granular control and centralized reporting.

---

# 🗑️ Snapshot Recycle Bin (Delete Protection)

## 🔒 What It Is:
Lets you recover accidentally deleted snapshots within a defined retention period (1–30 days).

## ✅ How to Use:
- Go to **EC2 > Recycle Bin**.
- Define rules by tag or resource type.
- Deleted snapshots go to Recycle Bin before permanent deletion.

# 🌍 Copy Snapshot to Another Region

## ✅ Why:
- Disaster recovery in another region.
- Launching resources in a different region.

## ✅ Console Steps:
1. Go to **Snapshots**.
2. Select snapshot > **Actions** > **Copy**.
3. Choose **destination region** and configure **encryption**.


# 🔐 Encrypting EBS Snapshots and Volumes

## 🧩 When to Encrypt:
- Compliance (HIPAA, PCI)
- Security best practices
- Encryption is done at-rest, in-transit, and during snapshot restore

## ✅ Encryption Options:
- Use AWS-managed KMS keys
- Use your own customer-managed keys

## 🔁 Behavior:
- If you create a snapshot from an encrypted volume, the snapshot is encrypted.
- If you create a volume from an encrypted snapshot, the new volume is encrypted.
- ❗ You cannot change encryption status after creation — must copy snapshot and enable encryption.

---

# 📊 Advantages of Snapshots
- Easy backup and recovery
- Incremental — cost and time efficient
- Cross-region copy for disaster recovery
- Can be shared with other AWS accounts
- Can restore into any AZ or region

---

# ⚠️ Disadvantages
- Not real-time backup (point-in-time only)
- Initial snapshot may take time and bandwidth
- Encrypted snapshots can’t be shared publicly
- Must manually manage lifecycle if not using DLM

---

# 🧠 Interview & Revision Cheat Sheet

| Feature                    | Key Point                                       |
|----------------------------|-------------------------------------------------|
| Snapshot type              | Point-in-time backup of EBS volume              |
| Incremental                | Yes – only changed data is saved               |
| Automation tools           | DLM, AWS Backup                                |
| Cross-region copy          | Yes – for DR                                   |
| Encryption                 | At-rest and in-transit using KMS               |
| Recycle Bin                | Recover snapshots within 1–30 days             |
| Share snapshot             | Only unencrypted (or encrypted within same org)|
| Restore volume from snapshot | Can restore in any AZ/region                 |

---

# 💡 Best Practices
- Tag volumes for automation policies (e.g., `"Backup=true"`)
- Use DLM or AWS Backup for retention and lifecycle
- Regularly copy critical snapshots to another region
- Use encrypted volumes for sensitive workloads
