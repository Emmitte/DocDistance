package word2vec.main.java.com.ansj.vec.domain;

public abstract class Neuron implements Comparable<Neuron> {
  public double freq;
  public Neuron parent;
  public int code;
  // ÓïÁÏÔ¤·ÖÀà
  public int category = -1;

  public int compareTo(Neuron neuron) {
    if (this.category == neuron.category) {
      if (this.freq > neuron.freq) {
        return 1;
      } else {
        return -1;
      }
    } else if (this.category > neuron.category) {
      return 1;
    } else {
      return 0;
    }
  }
}
