# OficinasApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getOffices**](OficinasApi.md#getOffices) | **GET** /RS_Oficinas | Finds offices by city


<a name="getOffices"></a>
# **getOffices**
> Offices getOffices(ciudad)

Finds offices by city



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import model.*

val apiInstance = OficinasApi()
val ciudad : kotlin.String = ciudad_example // kotlin.String | Needed to filter office information
try {
    val result : Offices = apiInstance.getOffices(ciudad)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OficinasApi#getOffices")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OficinasApi#getOffices")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ciudad** | **kotlin.String**| Needed to filter office information | [optional]

### Return type

[**Offices**](Offices.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

