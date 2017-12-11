package se.juneday.systemet.domain;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>Represents a Product in a ProductLine.</p>
 *
 * <p>The data of a Product originally comes from the Systembolaget's
 * open data with their product line. An example product is shown below:
 * <pre>
 *  &lt;artikel&gt;
 *   &lt;nr&gt;152115&lt;/nr&gt;
 *   &lt;Artikelid&gt;1087400&lt;/Artikelid&gt;
 *   &lt;Varnummer&gt;1521&lt;/Varnummer&gt;
 *   &lt;Namn&gt;Williams Caesar Augustus&lt;/Namn&gt;
 *   &lt;Namn2&gt;Lager IPA Hybrid&lt;/Namn2&gt;
 *   &lt;Prisinklmoms&gt;16.90&lt;/Prisinklmoms&gt;
 *   &lt;Pant&gt;1.00&lt;/Pant&gt;
 *   &lt;Volymiml&gt;330.00&lt;/Volymiml&gt;
 *   &lt;PrisPerLiter&gt;51.21&lt;/PrisPerLiter&gt;
 *   &lt;Saljstart&gt;2016-09-01&lt;/Saljstart&gt;
 *   &lt;Utgått&gt;0&lt;/Utgått&gt;
 *   &lt;Varugrupp&gt;Öl&lt;/Varugrupp&gt;
 *    &lt;Typ&gt;Ljus lager&lt;/Typ&gt;
 *   &lt;Stil&gt;Modern stil&lt;/Stil&gt;
 *   &lt;Forpackning&gt;Burk&lt;/Forpackning&gt;
 *   &lt;Forslutning/&gt;
 *   &lt;Ursprung/&gt;
 *   &lt;Ursprunglandnamn&gt;Storbritannien&lt;/Ursprunglandnamn&gt;
 *   &lt;Producent&gt;Williams Brothers&lt;/Producent&gt;
 *   &lt;Leverantor&gt;Brill &amp; Co AB&lt;/Leverantor&gt;
 *   &lt;Argang/&gt;
 *   &lt;Provadargang/&gt;
 *   &lt;Alkoholhalt&gt;4.00%&lt;/Alkoholhalt&gt;
 *   &lt;Sortiment&gt;BS&lt;/Sortiment&gt;
 *   &lt;SortimentText&gt;Övrigt sortiment&lt;/SortimentText&gt;
 *   &lt;Ekologisk&gt;0&lt;/Ekologisk&gt;
 *   &lt;Etiskt&gt;0&lt;/Etiskt&gt;
 *   &lt;Koscher&gt;0&lt;/Koscher&gt;
 *   &lt;RavarorBeskrivning&gt;Kornmalt samt humle av sorterna savinjski goldings, calypso och citra.&lt;/RavarorBeskrivning&gt;
 *  &lt;/artikel&gt;
 * </pre>
 * </p>
 */
public class Product implements Serializable {

  private static final long serialVersionUID = -7021358931007336368L;
  private String name;
  private double price; // SEK
  private double alcohol; // % alcohol by volume
  private int volume; // milliliters
  private int nr; // XML: <nr>nnn</nr> unique nr in the catalog
  private String productGroup; // e.g. <Varugrupp>Okryddad sprit</Varugrupp>
  private String type; // e.g. <Typ>Syrlig öl</Typ>

  /**
   * Defines the interface for objects we can export a
   * Product instance to.
   *
   * Hides the implementation, format and use from this class.
   */
  public interface Exporter {
    void addName(String name);
    void addPrice(double price);
    void addAlcohol(double alcohol);
    void addVolume(int volume);
    void addNr(int nr);
    void addProductGroup(String productGroup);
    void addType(String type);
  }

  /**
   * Defines a Builder for an Object.
   */
  public static class Builder {
    private String name;
    private double price; // SEK
    private double alcohol; // % alcohol by volume
    private int volume; // milliliters
    private int nr; // XML: <nr>nnn</nr> unique nr in the catalog
    private String productGroup; // e.g. <Varugrupp>Okryddad sprit</Varugrupp>
    private String type;

    /**
     * Provides the Product name
     * @param name The name for the Product to build
     * @return this Builder so that you can continue building
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     *
     */
    public Builder price(double price) {
      this.price = price;
      return this;
    }

    /**
     *
     */
    public Builder alcohol(double alcohol) {
      this.alcohol = alcohol;
      return this;
    }

    /**
     *
     */
    public Builder volume(int volume) {
      this.volume = volume;
      return this;
    }

    /**
     *
     */
    public Builder nr(int nr) {
      this.nr = nr;
      return this;
    }

    /**
     *
     */
    public Builder productGroup(String productGroup) {
      this.productGroup = productGroup;
      return this;
    }

    /**
     *
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     *
     */
    public Product build() {
      return new Product(this);
    }
  }

  /**
   * Constructs a Product using a Product.Builder
   *
   * @param builder The builder to use for building this Product
   */
  public Product(Builder builder) {
    this.name = builder.name;
    this.price = builder.price;
    this.alcohol = builder.alcohol;
    this.volume = builder.volume;
    this.nr = builder.nr;
    this.productGroup = builder.productGroup;
    this.type = builder.type;
  }

  /**
   * This methods allows a Product to export itself to
   * an Exporter object, which could be used to create
   * various representations or other uses of this Product,
   * like for instance, a CSV row, an XML element, an
   * SQL insert statement, a PreparedStatement, a GUI component
   * or whatever.
   *
   * This class remains totally decoupled from the creation
   * of such a representation or object, since we limit ourselves
   * to Exporting to an Exporter object - and Exporter is an
   * interface owned by this class (as a nested static interface).
   *
   * Compare this method to the toString() method - this method
   * allows us to transfer the state of this object to some other
   * object.
   *
   * See: http://www.javaworld.com/article/2072302/core-java/more-on-getters-and-setters.html
   *  by Allen Holub, for reference and the source of this idea.
   *
   * @param builder The Exporter to export ourselves to
   */
  public void export(Exporter builder) {
    builder.addName(name);
    builder.addPrice(price);
    builder.addAlcohol(alcohol);
    builder.addNr(nr);
    builder.addVolume(volume);
    builder.addProductGroup(productGroup);
    builder.addType(type);
  }
  /**
   * A Comparator that orders Products based on their name.
   */
  public static final Comparator<Product> NAME_ORDER = Comparator.comparing(Product::name);
  /**
   * A Comparator that orders Products based on their name, ignoring case.
   */
  public static final Comparator<Product> NAME_CASE_INSENSITIVE_ORDER =
      (p1, p2) -> p1.name().toLowerCase().compareTo(p2.name().toLowerCase());
  /**
   * A Comparator that orders Products based on their price.
   */
  public static final Comparator<Product> PRICE_ORDER = Comparator.comparing(Product::price);
  /**
   * A Comparator that orders Products based on their alcohol level.
   */
  public static final Comparator<Product> ALCOHOL_ORDER = Comparator.comparing(Product::alcohol);
  /**
   * A Comparator that orders Products based on their volume.
   */
  public static final Comparator<Product> VOLUME_ORDER = Comparator.comparing(Product::volume);

  /**
   * Constructs a new Product.
   * @param name The name of this Product
   * @param alcohol The alcohol level (in percent alcohol by weight) of this Product
   * @param price The price of this Product (in SEK)
   * @param volume The volume (in millilitres) of this product
   */
  public Product(String name, double alcohol, double price, int volume) {
    this.name = name;
    this.alcohol = alcohol;
    this.price = price;
    this.volume = volume;
  }

  /**
   * Returns the name of this Product
   * @return The name of this Product
   */
  public String name() { return name; }

  /**
   * Returns the alcohol level of this Product
   * @return The alcohol level of this Product
   */
  public double alcohol() { return alcohol; }

  /**
   * Returns the price of this Product
   * @return The price of this Product
   */
  public double price() { return price; }

  /**
   * Returns the volume of this Product
   * @return The volume of this Product
   */
  public int volume() { return volume; }

  /**
   * Returns the product group of this Product
   * @return The product group of this Product
   */
  public String productGroup() {
    return productGroup;
  }

  /**
   * Returns the product type of this Product
   * @return The product type of this Product
   */
  public String type() {
    return type;
  }

  /**
   * Returns the product number of this Product
   * @return The product number of this Product
   */
  public int nr() {
    return nr;
  }

  /**
   * Returns this Product as a String on the format:
   *<pre>
   *NAME, PERCENT_ALCOHOL%, VOLUME ml, PRICE SEK
   *</pre>
   * For instance:
   *<pre>
   *Renat, 37.50%, 700 ml, 209.00 SEK
   *</pre>
   * @return this Product as a String
   */
  @Override
  public String toString() {
    String type = this.type == null ? "" : " (" + this.type + "),";
    return name + ", " +
        String.format("%.2f", alcohol) + "%, " +
        volume + " ml" + ", " +
        String.format("%.2f", price) + " SEK " +
        productGroup + type + ", Product number: " +
        nr;
  }

  /**
   * Uses name, alcohol, volume, price and nr
   * when deciding if this Product is equal to
   * another Product.
   *
   * Note: We could have settled with nr, since
   * it is the identity of a Product from the Systembolaget
   * source data (the XML file etc).
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (! (other instanceof Product)) {
      return false;
    }
    Product that = (Product)other;
    return this.name.equals(that.name) &&
        this.alcohol == that.alcohol &&
        this.volume == that.volume &&
        this.price == that.price &&
        this.nr == that.nr;
  }

  @Override
  public int hashCode() {
    // Using hash algorithm from
    // Joshua Bloch - Effective Java - on hashcodes
    int code = 17;
    code = 31 * code + name.hashCode();
    long alc = Double.doubleToLongBits(alcohol);
    code = 31 * code + (int) (alc ^ (alc >>> 32));
    code = 31 * code + volume;
    long pri = Double.doubleToLongBits(price);
    code = 31 * code + (int) (pri ^ (pri >>> 32));
    code = 31 * code + nr;
    return code;
  }
}
