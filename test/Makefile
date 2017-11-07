SYS_PATH=../app/src/main/java/

CLASSPATH=$(SYS_PATH):.

CLASSES= \
  ../app/src/main/java/se/juneday/systemet/storage/ProductUtil.class \
  ../app/src/main/java/se/juneday/systemet/domain/Product.class \
  ../app/src/main/java/se/juneday/systemet/domain/ProductPredicate.class \
  ../app/src/main/java/se/juneday/systemet/storage/ProductStore.class \
  ../app/src/main/java/se/juneday/systemet/storage/FakedProductStore.class \
  android/util/Log.class \
  android/content/Context.class \
  ProductPredicateTest.class

%.class:%.java
	javac -cp $(CLASSPATH) $<

all: $(CLASSES)


run: $(CLASSES)
	java  -cp $(CLASSPATH) ProductPredicateTest

clean:
	rm -f $(CLASSES)

