# Design

## Problem Statement
Imagine you work at a startup with many copies of many different services
running in production. You want to help your engineers keep track of metrics
like number of signups, number of requests, number of errors, etc. So you
introduce the concept of "counters". You build a `Track` library that ships
with every service, and anyone can call `Track.increment("some-metric")`
in their code to increment the `some-metric` counter.

This is really useful for quickly spotting anomalies. If you graph the number
of signups over time and it suddenly goes to zero, then we can assume that
something is not going well. This is mostly an operational tool (for example,
we’re not keeping track of precise dollar amounts for accounting or request
counts for rate limiting). Imagine we want to see the number of signups per
minute for the last 6 hours, or how many fraudulent cards were detected hourly
for the last day, or trends in a feature's adoption over the last few weeks.

Starting from just that interface, design everything that happens after
`Track.increment("some-metric")` gets called on a service, from what happens
inside the service to how the data is sent, collected, stored and accessed.
Think about how to support accessing the data, but don’t worry too much about
any actual graphing UI.


## Sample System Diagram
```
+---------------+
| login-service |--> Track.increment("new-signup")
|               |--> Track.increment("new-login")
+---------------+


+-----------------+
| payment-service |  
|                 |--> Track.increment("fraudulent-card")
+-----------------+


+--------------------+
| some-other-service |--> Track.increment("some-metric")
|                    |--> Track.increment("some-other-metric")
+--------------------+
```

## Sample Charts
The final output of the system. For example, these two charts represent
scenarios we might want to display using data from the system:

```
^ number of signups
|
|
|   ▉
| ▉ ▉
| ▉ ▉ ▉
| ▉ ▉ ▉ ▉ ▄
+----------> time

^ number of fraudulent cards
|
|     ▉
|     ▉
|     ▉ 
|     ▉
| ▄ ▉ ▉ ▄
+--------> time
```