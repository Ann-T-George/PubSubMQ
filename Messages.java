package pubsubmq;

public class Messages implements Comparable<Messages> {
    private String payload;
    private Metadata metadata;

    public Messages(String payload, String publisherID, Priority priority) {
        this.payload = payload;
        this.metadata = new Metadata(publisherID, priority);
    }

    public String getPayload() {
        return payload;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public int compareTo(Messages other) {
        return this.metadata.compareTo(other.metadata);
    }

    public enum Priority {
        High, Medium, Low
    }

    private class Metadata implements Comparable<Metadata> {
        private long timestamp;
        private Priority priority;
        private String publisherID;
        private String messageID;
        public static Integer id_count = 0;
        
        public Metadata(String publisherID, Priority priority) {
            this.timestamp = System.currentTimeMillis();
            this.priority = priority;
            this.publisherID = publisherID;
            this.messageID = id_count.toString();
            id_count = id_count + 1;
        }

        @Override
        public int compareTo(Metadata other) {
            if (this.priority != other.priority) {
                return this.priority.ordinal() - other.priority.ordinal();
            }
            return Long.compare(this.timestamp, other.timestamp);
        }
    }
}

