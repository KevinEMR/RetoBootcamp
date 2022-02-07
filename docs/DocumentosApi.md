# DocumentosApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDocuments**](DocumentosApi.md#getDocuments) | **GET** /RS_Documentos | Finds documents by &#39;idRegistro&#39; or &#39;correo&#39;
[**putDocument**](DocumentosApi.md#putDocument) | **POST** /RS_Documentos | Puts a document


<a name="getDocuments"></a>
# **getDocuments**
> Documents getDocuments(idRegistro, correo)

Finds documents by &#39;idRegistro&#39; or &#39;correo&#39;



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import model.*

val apiInstance = DocumentosApi()
val idRegistro : kotlin.String = idRegistro_example // kotlin.String | Needed to filter document information. This has priority over correo. With this filter is showen the field Adjunto
val correo : kotlin.String = correo_example // kotlin.String | Needed to filter document information
try {
    val result : Documents = apiInstance.getDocuments(idRegistro, correo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DocumentosApi#getDocuments")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DocumentosApi#getDocuments")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idRegistro** | **kotlin.String**| Needed to filter document information. This has priority over correo. With this filter is showen the field Adjunto | [optional]
 **correo** | **kotlin.String**| Needed to filter document information | [optional]

### Return type

[**Documents**](Documents.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="putDocument"></a>
# **putDocument**
> Put putDocument(body)

Puts a document



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import model.*

val apiInstance = DocumentosApi()
val body : NewDocument =  // NewDocument | 
try {
    val result : Put = apiInstance.putDocument(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DocumentosApi#putDocument")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DocumentosApi#putDocument")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**NewDocument**](NewDocument.md)|  | [optional]

### Return type

[**Put**](Put.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

