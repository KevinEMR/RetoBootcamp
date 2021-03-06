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
import com.example.retobootcamp.services.model.Documents
import com.example.retobootcamp.services.model.NewDocument
import com.example.retobootcamp.services.model.Put

class DocumentosApi(basePath: String = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/") : ApiClient(basePath) {

    /**
    * Finds documents by &#39;idRegistro&#39; or &#39;correo&#39;
    * 
    * @param idRegistro Needed to filter document information. This has priority over correo. With this filter is showen the field Adjunto (optional)
    * @param correo Needed to filter document information (optional)
    * @return Documents
    */
    @Suppress("UNCHECKED_CAST")
    fun getDocuments(idRegistro: String? = null, correo: String? = null) : Documents {
        val localVariableBody: Any? = null
        val localVariableQuery: MultiValueMap = if(idRegistro != null){
            if (correo != null){
                mapOf("idRegistro" to listOf(idRegistro), "correo" to listOf(correo))
            }else{
                mapOf("idRegistro" to listOf(idRegistro))
            }
        }else if (correo != null){
            mapOf("correo" to listOf(correo))
        }else{
            mapOf()
        }
        val contentHeaders: Map<String, String> = mapOf()
        val acceptsHeaders: Map<String, String> = mapOf("Accept" to "application/json")
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders.putAll(contentHeaders)
        localVariableHeaders.putAll(acceptsHeaders)
        
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/RS_Documentos",
            query = localVariableQuery,
            headers = localVariableHeaders
        )
        val response = request<Documents>(
            localVariableConfig,
            localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as Documents
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }

    /**
    * Puts a document
    * 
    * @param body  (optional)
    * @return Put
    */
    @Suppress("UNCHECKED_CAST")
    fun putDocument(body: NewDocument) : Put {
        val localVariableBody: Any = body
        val localVariableQuery: MultiValueMap = mapOf()
        
        val contentHeaders: Map<String, String> = mapOf()
        val acceptsHeaders: Map<String, String> = mapOf("Accept" to "application/json")
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders.putAll(contentHeaders)
        localVariableHeaders.putAll(acceptsHeaders)
        
        val localVariableConfig = RequestConfig(
            RequestMethod.POST,
            "/RS_Documentos",
            query = localVariableQuery,
            headers = localVariableHeaders
        )
        val response = request<Put>(
            localVariableConfig,
            localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as Put
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
