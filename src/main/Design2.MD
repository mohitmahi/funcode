Handout: Identity and Access Management
=======

_Our customers trust us with some of their most sensitive data, but with thousands of systems and employees around the globe, we’ve never been systematic about access control. We want to give employees exactly the right set of privileges they need to do their work._

Welcome! You have been appointed the technical lead of a project to build an identity and access management (IAM) system. At a high level, the components are:
- A way of storing access policies (who can do what);
- An API to query these access policies; and
- An API to manage these access policies.

#### Conceptual Diagram

```
+---------------+
| User jdoe     |
| Groups:       |
|  - engineers  +------+
|  - employees  |      |
|               |      |
+---------------+      |
                       |
                       |
               +-------v---------+                                                          +-----------+
               |                 |                                                          |           |
               | Example Service |                                                          | IAM Admin |
               |                 |                                                          | website   |
               +------+----------+                                                          |           |
                      |                                                                     +-----------+
                      |  +----------------------------------------------------------------------------+ |
                      |  |                                                                            | |
                      |  |                                                                            | |
                      |  |                                                            +---------------+ |
                      |  +--------------------+    +------------+                     |              || |
                      |  ||                   |    |            |    +------------+   | API endpoint || |
                      |  || API endpoint      |    |  Decision  |    |            |   | for updates  <--+
                      |  || for authorization +--->+  logic     +--->+  Policy    +<--+              ||
                      +---> checks            |    |            |    |  database  |   |              ||
                         ||                   |    +------------+    |            |   +---------------+
                         ||                   |                      +------------+                   |
                         +--------------------+                                                       |
                         |                                                                            |
                         |                                                                            |
                         +----------------------------------------------------------------------------+

```

#### Examples of requests that the system should serve
- Example 1: Should [jdoe] be allowed to [list all customers] in our [customer list]?
- Example 2: Should users in the group [engineers] be allowed to [delete administrators] in the [payroll service]?

## Your task
Design a globally distributed system that stores access policies and answers authorization queries like _"should user U be allowed to do action A on service X?"_

**Deliverables:** A system architecture, a set of APIs, and a storage schema.

**Requirements:** Your system should have the following properties:
- Authorization checks are easy to integrate into existing services, even those that are not web applications. (I.e., a solution that depends on HTTP cookies is not suitable.)
- The system facilitates auditing: who did what, when?
- It is easy to create new policies or change existing ones.
- The system should be resilient in the face of outages — no single point of failure.

Assume that _authentication_ is already addressed: every user and service presents a trustworthy certificate of its identity and all connections are appropriately encrypted.