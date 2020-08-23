
Annotation based message queue publisher. Currently only supports Kafka.

Example usage:

@Broadcast(
    target = "some-topic",
    collection = @BroadcastPair(value = "entityIds", valueSource = ARGUMENT),
    partitionKey = @BroadcastPair(value = "value", valueSource = OBJECT),
    body = {
        @BroadcastPair(value = "id", key = "entityId", valueSource = OBJECT),
        @BroadcastPair(value = "date", key = "displayDate", valueSource = ARGUMENT),
        @BroadcastPair(value = STATIC_VALUE_ENUM, key = "status", valueSource = CONSTANT)
        }
)
public returnValue someMethod(Object ...arguments) {}

Project contains an example broadcaster-starter usage. It is very useful when you need to try kafka publishing easily.
