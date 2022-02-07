/**
* Challenge
* Services specification.
*
* OpenAPI spec version: 1.0.0
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package com.example.retobootcamp.services.api

import com.example.retobootcamp.services.io.swagger.client.infrastructure.*
import com.example.retobootcamp.services.model.Users

class UsuariosApi(basePath: String = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/") : ApiClient(basePath) {

    /**
    * Finds user by IdUsuario and Clave
    * 
    * @param idUsuario Needed to filter user information 
    * @param clave Needed to filter user information 
    * @return Users
    */
    @Suppress("UNCHECKED_CAST")
    fun getUser(idUsuario: String, clave: String) : Users{
        val localVariableBody: Any? = null
        val localVariableQuery: MultiValueMap = mapOf("idUsuario" to listOf(idUsuario), "clave" to listOf(clave))
        
        val contentHeaders: Map<String, String> = mapOf()
        val acceptsHeaders: Map<String, String> = mapOf("Accept" to "application/json")
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders.putAll(contentHeaders)
        localVariableHeaders.putAll(acceptsHeaders)
        
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/RS_Usuarios",
            query = localVariableQuery,
            headers = localVariableHeaders
        )
        val response = request<Users>(
            localVariableConfig,
            localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as Users
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }

}
