// IAPIService.aidl
package jp.doberan.sampleapiserver;

// Declare any non-default types here with import statements

// APIService Interface
interface IAPIService {
    // Display dog number
    boolean logNumber(int a);
    // Set number.
    boolean setNumber(int a);
    // Get setted number.
    int getNumber();
}
