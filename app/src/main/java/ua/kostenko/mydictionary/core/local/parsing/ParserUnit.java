package ua.kostenko.mydictionary.core.local.parsing;

public class ParserUnit {
    private String source;
    private long counter;

    public ParserUnit(String source, long counter) {
        this.source = source;
        this.counter = counter;
    }

    public String getSource() {
        return source;
    }

    public long getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParserUnit that = (ParserUnit) o;
        return counter == that.counter && source.equals(that.source);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (int) (counter ^ (counter >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ParserUnit{" +
                "source='" + source + '\'' +
                ", counter=" + counter +
                '}';
    }
}
