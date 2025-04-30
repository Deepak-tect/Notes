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