# Java Common
Java code that makes life easier.

## Collections

### findOne
Find exactly 1 element in a stream:
```
var value = list.stream()
    .collect(findOne(() -> new RuntimeException("more than 1"))
    .orElseThrow(() -> new RuntimeException("empty"));
```
