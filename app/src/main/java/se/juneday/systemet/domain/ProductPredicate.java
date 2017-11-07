package se.juneday.systemet.domain;

import android.util.Log;
import java.security.acl.LastOwnerException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ProductPredicate implements Predicate<Product>{

  private static final String LOG_TAG = ProductPredicate.class.getName();

  @Override
  public boolean test(Product prod) {
    /*
    Log.d(LOG_TAG, " test() [ " + prod +
          " | " + this.operation +
          " | " + this.variable +
          " | " + this.value + "]   ");
    */
    return calcPredicate(variable,
                         operation,
                         value,
                         prod);
  }

  private static boolean calcPredicate(VARIABLES variable,
                                OPERATIONS operation,
                                String value,
                                Product prod) {
    switch (operation) {
    case EQ:
      switch (variable) {
      case PRICE:
        return value.equals(prod.price());
      case ALCOHOL:
        return value.equals(prod.alcohol());
      case TYPE:
        return value.equals(prod.type());
      }
    case GT:
      switch (variable) {
      case PRICE:
        return prod.price() >= Double.parseDouble(value);
      case ALCOHOL:
        return prod.alcohol() >= Double.parseDouble(value);
      case TYPE:
        return prod.type().compareTo(value)>=0;
      }
    case LT:
      switch (variable) {
      case PRICE:
        return prod.price() <= Double.parseDouble(value);
      case ALCOHOL:
        return prod.alcohol() <= Double.parseDouble(value);
      case TYPE:
        return prod.type().compareTo(value)<=0;
      }
    }
    return false;
  }

    
  public enum VARIABLES {
    PRICE("Price"),
    ALCOHOL("Alcohol"),
    TYPE("Type");

    private String name;
    VARIABLES(String name) {
      this.name=name;
    }
    @Override
    public String toString(){
      return name;
    }

  }

  public enum OPERATIONS {
    GT(">"),
    LT("<"),
    EQ("=");

    private String name;
    OPERATIONS(String name) {
      this.name=name;
    }
    @Override
    public String toString(){
      return name;
    }

  }

  private VARIABLES variable;
  private OPERATIONS operation;
  private String value;

  public ProductPredicate() {
    this(VARIABLES.ALCOHOL, OPERATIONS.GT, "");
  }

  public ProductPredicate(VARIABLES variable, OPERATIONS operation) {
    this(variable, operation, "");
  }

  public ProductPredicate(VARIABLES variable, OPERATIONS operation, String value) {
    this.variable = variable;
    this.operation = operation;
    this.value = value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
