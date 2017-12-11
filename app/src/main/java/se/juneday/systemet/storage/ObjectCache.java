package se.juneday.systemet.storage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectCache<T> {

  public enum CACHE_TYPE {
    SINGLE,
    LIST
  };

  CACHE_TYPE cacheType;

  private static final String LOG_TAG = ObjectCache.class.getSimpleName() ;

  private List<T> objects;
  private Context context;
  private Class c;
  private String cacheFileName;
  private long maxDiff = (7 * 24 * 60 * 60 * 1000); // one week

  public ObjectCache(Class c, Context context, CACHE_TYPE type, long timeout){
    this(c, context, type);
    Log.d(LOG_TAG, "Creating " + type.name() + " ObjectCache for: " + c.getCanonicalName() + " timeout: " + timeout);
    maxDiff=timeout;
  }

  public ObjectCache(Class c, Context context, CACHE_TYPE type){
    Log.d(LOG_TAG, "Creating " + type.name() + " ObjectCache for: " + c.getCanonicalName());
    this.context=context;
    PackageManager m = context.getPackageManager();
    String s = context.getPackageName();
    try {
      PackageInfo p = m.getPackageInfo(s, 0);
      s = p.applicationInfo.dataDir;
      cacheFileName = s + "/" + c.getCanonicalName() + "_serializsed";
    } catch (PackageManager.NameNotFoundException e) {
      Log.d(LOG_TAG, "Error, could not build file name for serialization", e);
      throw new ObjectCacheException("Error, could not build file name for serialization", e);
    }
    cacheType = type;
    this.c = c;
  }

  public void set(List<T> objects) {
    Log.d(LOG_TAG, "Set " + (objects!=null?objects.size():0) + " " + c.getCanonicalName() + " objects");
    if (cacheType!=CACHE_TYPE.LIST) {
      throw new ObjectCacheException("Cache not set to store list of objects.");
    }
    this.objects = objects;
  }

  public void setSingle(T object) {
    Log.d(LOG_TAG, "Set single " + c.getCanonicalName() + " object");
    if (cacheType!=CACHE_TYPE.SINGLE) {
      throw new ObjectCacheException("Cache not set to store one single object.");
    }
    ArrayList<T> objectList = new ArrayList<>();
    objectList.add(object);
    this.objects = objects;
  }

  public void commit() {
    Log.d(LOG_TAG, "commit()");
    FileOutputStream fos = null;
    ObjectOutputStream out = null;
    try {
      fos = new FileOutputStream(cacheFileName );
      out = new ObjectOutputStream(fos);
      out.writeObject(objects);
      out.close();
      Log.d(LOG_TAG, "commit() commited " + (objects!=null?objects.size():0) + " " + c.getCanonicalName() + " objects");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void pull() throws ObjectCacheException {
    Log.d(LOG_TAG, "pull()");
    File f = new File(cacheFileName);
    long diff =
        System.currentTimeMillis() - f.lastModified();

    if (diff>maxDiff) {
      this.objects = null;
      Log.d(LOG_TAG, " ... cache timeout()");
      return;
    }

    FileInputStream fis = null;
    ObjectInputStream in = null;
    List<T> tmpObjects = null;
    try {
      fis = new FileInputStream(cacheFileName);
      in = new ObjectInputStream(fis);
      tmpObjects= (List<T>) in.readObject();
      in.close();
    } catch (Exception ex) {
      // If exception it is probably an IOException
      // Either fixed next commit or not at all
      // Cache will not work ... and return null
      // This implementation will however return the cached (in RAM) objects
      ex.printStackTrace();
      return;
    }
    Log.d(LOG_TAG, "pull() pulled back " + (tmpObjects!=null?tmpObjects.size():0) + " " + c.getCanonicalName() + " objects");
    objects = tmpObjects;
    return;
  }

  public List<T> get() throws ObjectCacheException {
    Log.d(LOG_TAG, "get " + (objects!=null?objects.size():0) + " " + c.getCanonicalName() + " objects");
    if (cacheType!=CACHE_TYPE.LIST) {
      throw new ObjectCacheException("Cache not set to store list of objects.");
    }
    return objects;
  }

  public T getSingle() throws ObjectCacheException {
    Log.d(LOG_TAG, "getSingle " + c.getCanonicalName() + " object");
    if (cacheType != CACHE_TYPE.SINGLE) {
      throw new ObjectCacheException("Cache not set to store one singl object.");
    }
    if (objects == null) {
      return null;
    }
    return objects.get(0);
  }
}
