# UsuariosApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUser**](UsuariosApi.md#getUser) | **GET** /RS_Usuarios | Finds user by IdUsuario and Clave


<a name="getUser"></a>
# **getUser**
> Users getUser(idUsuario, clave)

Finds user by IdUsuario and Clave



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import model.*

val apiInstance = UsuariosApi()
val idUsuario : kotlin.String = idUsuario_example // kotlin.String | Needed to filter user information
val clave : kotlin.String = clave_example // kotlin.String | Needed to filter user information
try {
    val result : Users = apiInstance.getUser(idUsuario, clave)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsuariosApi#getUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsuariosApi#getUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idUsuario** | **kotlin.String**| Needed to filter user information |
 **clave** | **kotlin.String**| Needed to filter user information |

### Return type

[**Users**](Users.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

