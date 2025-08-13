# Hypervisor and Its Types

## What is a Hypervisor?

A **hypervisor** is software that allows you to create and run **virtual machines (VMs)**.  
It lets one physical computer (called **host**) run multiple **virtual computers** (called **guests**) at the same time.

Each virtual machine can run its own operating system and applications — just like a real computer!

---

## Types of Hypervisors

There are **two main types** of hypervisors:

---

### 1. Type 1 Hypervisor (Bare Metal)

- Installed **directly on the hardware**.
- No need for a host operating system.
- More efficient and faster.
- Common in servers and data centers.

**Examples:**
- VMware ESXi
- Microsoft Hyper-V
- Xen
- KVM (used by Linux)

**Simple Example:**

> You install VMware ESXi directly on a physical server.  
> Then, on top of ESXi, you create multiple virtual machines like Ubuntu, Windows, etc.

---

### 2. Type 2 Hypervisor (Hosted)

- Installed **on top of an existing operating system**.
- Easier to set up, mostly used in personal computers.

**Examples:**
- Oracle VirtualBox
- VMware Workstation
- Parallels (for macOS)

**Simple Example:**

> You install VirtualBox on your Windows laptop.  
> Inside VirtualBox, you create a virtual machine and install Ubuntu on it.

---

## Summary Table

| Feature             | Type 1 (Bare Metal)   | Type 2 (Hosted)       |
|---------------------|-----------------------|------------------------|
| Installed on        | Physical Hardware     | Host Operating System  |
| Performance         | High                  | Medium                 |
| Use Case            | Servers, Data Centers | Personal PCs           |
| Example             | VMware ESXi           | VirtualBox             |

